package com.inerdstack.galleryselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

public class MainActivity extends AppCompatActivity {

    // 主题声明
    private ThemeConfig mTheme;
    // 功能配置声明
    private FunctionConfig mFunConfig;
    // 图片加载类声明
    private ImageLoader mImageLoader;
    // 核心配置声明
    private CoreConfig mCoreConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 初始化GalleryFinal
        initGalleryFinal();
    }

    /**
     * 初始化GalleryFinal
     */
    private void initGalleryFinal() {
        // 设置主题
        mTheme = new ThemeConfig.Builder()
                .build();

        // 配置功能
        mFunConfig = new FunctionConfig.Builder()
                .setEnableCamera(false) // 禁止拍照
                .setEnableEdit(false) // 禁止图片编辑
                .setEnableCrop(false) // 禁止图片裁剪
                .setEnableRotate(false) // 禁止图片旋转
                .setEnablePreview(true) // 允许图片预览
                .build();

        // 配置imageloader
//        mImageLoader = new UILImageLoader();
//        mCoreConfig = new CoreConfig.Builder(MainActivity.this, new Ima)

    }
}
