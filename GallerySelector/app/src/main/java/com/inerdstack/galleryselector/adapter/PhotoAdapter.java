package com.inerdstack.galleryselector.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inerdstack.galleryselector.R;
import com.inerdstack.galleryselector.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.DeviceUtils;

/**
 * Created by wangjie on 2016/10/10.
 */
public class PhotoAdapter extends BaseAdapter {

    // 图片列表
    private List<PhotoInfo> mList;
    // 布局填充器
    private LayoutInflater mInflater;
    // 上下文
    private Context mContext;
    // 屏幕宽度
    private int mScreenWidth;

    public PhotoAdapter(Activity activity, List<PhotoInfo> list) {
        this.mList = list;
        this.mContext = activity;
        this.mInflater = LayoutInflater.from(activity);
        this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 显示选项
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(cn.finalteam.galleryfinal.R.drawable.ic_gf_default_photo)
                .showImageForEmptyUri(cn.finalteam.galleryfinal.R.drawable.ic_gf_default_photo)
                .showImageOnLoading(cn.finalteam.galleryfinal.R.drawable.ic_gf_default_photo)
                .build();
        // 初始化ImageView
        ImageView imgPhoto = new ImageView(mContext);
        // 设置图片尺寸
        setImgSize(imgPhoto);
        // 不调整图片宽高比例
        imgPhoto.setAdjustViewBounds(false);
        // 设置缩放
        imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // 如果当前不是最后一张图片，则显示系统选中的图片
        // 得到图片类
        PhotoInfo photoInfo = mList.get(position);
        // 获取图片路径
        String path = photoInfo.getPhotoPath();

        // 如果为占位图片
        if (TextUtils.equals(path, Constants.ADD_PIC)) {
            // 加载添加按钮
            imgPhoto.setImageResource(com.inerdstack.galleryselector.R.drawable.add_icon);
        } else {
            // 防止报错： java.lang.IllegalStateException: ImageLoader must be init with configuration before using
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
            // 加载图片
            ImageLoader.getInstance().displayImage("file:/" + path, imgPhoto, options);
        }
        // 返回图片
        return imgPhoto;
    }

    /**
     * 设置图片尺寸
     *
     * @param img
     */
    private void setImgSize(ImageView img) {
        // GridView的横向边距和
        int marginSide = DeviceUtils.dip2px(mContext, 40);
        // 屏幕宽度三分之一作为图片的高度，间距为15
        int width = (mScreenWidth - marginSide) / 3;
        // 设置图片高度=宽度
        int height = width;
        // 设置布局参数
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        // 设置布局参数
        img.setLayoutParams(lp);
    }
}
