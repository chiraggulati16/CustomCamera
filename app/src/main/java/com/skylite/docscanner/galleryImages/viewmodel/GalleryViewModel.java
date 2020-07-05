package com.skylite.docscanner.galleryImages.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.skylite.docscanner.galleryImages.model.GalleryModel;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewModel {

    public String imagePath;
    private Context context;

    public GalleryViewModel() {
    }

    public GalleryViewModel(GalleryModel galleryModel) {
        this.imagePath = galleryModel.imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



}
