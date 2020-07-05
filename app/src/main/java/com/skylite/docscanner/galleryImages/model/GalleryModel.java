package com.skylite.docscanner.galleryImages.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class GalleryModel {

    public String imagePath;

    public GalleryModel(String imagePath) {
        this.imagePath = imagePath;
    }


}
