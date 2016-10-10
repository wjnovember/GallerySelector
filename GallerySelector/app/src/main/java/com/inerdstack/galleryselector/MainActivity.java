package com.inerdstack.galleryselector;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.inerdstack.galleryselector.adapter.PhotoAdapter;
import com.inerdstack.galleryselector.listener.GlidePauseOnScrollListener;
import com.inerdstack.galleryselector.util.Constants;
import com.inerdstack.galleryselector.util.GlideImageLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gv_photo)
    GridView mGvPhoto;

    private PhotoAdapter mPhotoAdapter;

    // 主题配置
    private ThemeConfig mThemeConfig;
    // 图片加载器
    private cn.finalteam.galleryfinal.ImageLoader mGlidImgLoader;
    // 滚动监听事件
    private PauseOnScrollListener mPauseOnScrollListener;
    // 功能配置
    private FunctionConfig mFunctionConfig;
    // 核心配置
    private CoreConfig mCoreConfig;

    // 选中的图片列表
    private List<PhotoInfo> mPhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 使用ButterKnife框架
        ButterKnife.bind(this);

        // 初始化选中的图片列表
        initSelectedPhoto();
    }

    /**
     * 初始化选中的图片列表
     */
    private void initSelectedPhoto() {
        // 添加占位图片
        addEmptyPhoto(mPhotoList);

        // 初始化适配器
        mPhotoAdapter = new PhotoAdapter(MainActivity.this, mPhotoList);
        // 设置适配器
        mGvPhoto.setAdapter(mPhotoAdapter);
        // “添加”图片点击事件
        mGvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取最后的下标
                int lastIndex = mPhotoAdapter.getCount() - 1;
                // 如果当前点击的是最后一张图片(“添加”图片)
                if (position == lastIndex) {
                    // 启动GalleryFinal
                    startGlideGalleryFinal();
                }
            }
        });
    }

    /**
     * 添加一个占位图片
     * @param list
     */
    private void addEmptyPhoto(List<PhotoInfo> list) {
        // 声明图片类
        PhotoInfo photoInfo = new PhotoInfo();
        // 设置标记
        photoInfo.setPhotoPath(Constants.ADD_PIC);
        // 起到占位的作用
        list.add(photoInfo);
    }

    /**
     * 启动GalleryFinal
     */
    private void startGlideGalleryFinal() {
        // 设置主题
        mThemeConfig = new ThemeConfig.Builder()
                .setTitleBarBgColor(R.color.titleBarBgColor) // 设置标题栏背景颜色
                .setTitleBarTextColor(R.color.titleBarTextColor)    // 设置标题栏文字颜色
                .setFabNornalColor(R.color.fabNormalColor)  // 设置浮动按钮常规颜色
                .setFabPressedColor(R.color.fabPressedColor)    // 设置浮动按钮点击颜色
                .build();

        // 初始化图片加载器
        mGlidImgLoader = new GlideImageLoader();

        // 初始化监听事件
        mPauseOnScrollListener = new GlidePauseOnScrollListener(false, true);

        // 初始化功能配置
        FunctionConfig.Builder funConBuilder = new FunctionConfig.Builder();
        // 设置最多可选择5张照图片
        funConBuilder.setMutiSelectMaxSize(5);
        // 设置图片不可编辑
        funConBuilder.setEnableEdit(false);
        // 设置图片不可旋转
        funConBuilder.setEnableRotate(false);
        // 设置图片不可裁剪
        funConBuilder.setEnableCrop(false);
        // 设置不可通过照相选择照片
        funConBuilder.setEnableCamera(false);
        // 设置添加过滤集合，过滤掉之前选中的图片
//        funConBuilder.setFilter(mPhotoList);
        // 不过滤图片，而是将之前选中的图片设置为选中状态
        funConBuilder.setSelected(mPhotoList);
        // 设置可预览
        funConBuilder.setEnablePreview(true);
        // 功能配置
        mFunctionConfig = funConBuilder.build();

        // 初始化核心配置
        mCoreConfig = new CoreConfig.Builder(MainActivity.this, mGlidImgLoader, mThemeConfig)
                .setFunctionConfig(mFunctionConfig) // 添加功能配置
                .setPauseOnScrollListener(mPauseOnScrollListener) // 滑动停止加载事件
                .setNoAnimcation(true) // 无特效动画
                .build();

        // 实例化GalleryFinal
        GalleryFinal.init(mCoreConfig);
        // 多图片选择打开相册
        GalleryFinal.openGalleryMuti(Constants.REQUEST_CODE_GALLERY, mFunctionConfig,
                mOnHandlerResultCallback);
        // 初始化图片加载器
        initImageLoader(MainActivity.this);
    }

    /**
     * 初始化图片加载器
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        // 图片加载器配置
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        // 设置线程优先级
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        // 禁止内存缓存
        config.denyCacheImageMultipleSizesInMemory();
        // 设置磁盘缓存文件名生成器
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        // 设置磁盘缓存大小
        config.diskCacheSize(20 * 1024 * 1024);
        // 设置任务进程执行顺序：先进后出
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        // 调试使用，若是发布版，需要移除代码
        config.writeDebugLogs();

        // 初始化图片加载器
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 回调处理
     */
    private GalleryFinal.OnHanlderResultCallback mOnHandlerResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            // 如果有选择图片
            if (resultList != null) {
                Log.i("haha", "select num " + resultList.size());
                if (resultList.size() < Constants.PIC_MAX_QUANTITY) {
                    // 添加占位图片
                    addEmptyPhoto(resultList);
                }

                // 清除原来列表中的图片
                mPhotoList.clear();
                // 返回图片列表
                mPhotoList.addAll(resultList);
                Log.i("haha", "total num " + mPhotoList.size());
                // 刷新页面
                mPhotoAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            // 错误提示
            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
