package com.inerdstack.galleryselector;

import android.app.Application;

import com.inerdstack.galleryselector.util.GlideImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by wangjie on 2016/10/10.
 */
public class MyApplication extends Application {

    // 主题声明
    private ThemeConfig mTheme;
    // 功能配置声明
    private FunctionConfig mFunConfig;
    // 图片加载类声明
    private ImageLoader mImageLoader;
    // 核心配置声明
    private CoreConfig mCoreConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化GalleryFinal
//        initGalleryFinal();
    }

    /**
     * 初始化GalleryFinal
     */
    private void initGalleryFinal() {
        // 设置主题
        mTheme = new ThemeConfig.Builder()
                .setTitleBarTextColor(R.color.titleBarTextColor) // 标题栏字体颜色
                .setTitleBarBgColor(R.color.titleBarBgColor)    // 标题栏背景颜色
                .setFabNornalColor(R.color.fabNormalColor)  // 浮动按钮颜色
                .setFabPressedColor(R.color.fabPressedColor) // 浮动按钮点击颜色
                .build();

        // 配置功能
        mFunConfig = new FunctionConfig.Builder()
                .setEnableCamera(false) // 禁止拍照
                .setEnableEdit(false) // 禁止图片编辑
                .setEnableCrop(false) // 禁止图片裁剪
                .setEnableRotate(false) // 禁止图片旋转
                .setEnablePreview(true) // 允许图片预览
                .setMutiSelectMaxSize(5) // 配置多选数量
                .build();

        // 配置imageloader
        mImageLoader = new GlideImageLoader();
        mCoreConfig = new CoreConfig.Builder(getApplicationContext(), mImageLoader, mTheme)
                .setFunctionConfig(mFunConfig)
                .build();

    }
}
