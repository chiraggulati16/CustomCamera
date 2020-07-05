package com.skylite.docscanner.galleryImages;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.skylite.docscanner.R;
import com.skylite.docscanner.galleryImages.adapter.GalleryAdapter;
import com.skylite.docscanner.galleryImages.model.GalleryModel;
import com.skylite.docscanner.galleryImages.viewmodel.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowGalleryActivity extends AppCompatActivity {

    private GalleryAdapter galleryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gallery);

        initViews();
        settingAdapter();
    }

    private void settingAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        galleryAdapter = new GalleryAdapter(getAllImagesList(), this);
        recyclerView.setAdapter(galleryAdapter);

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    public List<GalleryModel> getAllImagesList() {

        List<GalleryModel> galleryModels = new ArrayList<>();

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                ,MediaStore.Images.Media.DATE_ADDED};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null,
                null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            GalleryModel galleryViewModel = new GalleryModel(absolutePathOfImage);
            galleryModels.add(galleryViewModel);
        }

        Log.d("size ===", galleryModels.size()+" ");
        return galleryModels;
    }
}