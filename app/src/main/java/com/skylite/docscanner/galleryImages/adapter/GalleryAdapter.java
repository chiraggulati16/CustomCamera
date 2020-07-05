package com.skylite.docscanner.galleryImages.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylite.docscanner.R;
import com.skylite.docscanner.databinding.GalleryBinding;
import com.skylite.docscanner.galleryImages.model.GalleryModel;
import com.skylite.docscanner.galleryImages.viewmodel.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    List<GalleryModel> galleryViewModelList;
    private Context context;

    public GalleryAdapter(List<GalleryModel> galleryViewModelList, Context context) {
        this.galleryViewModelList = galleryViewModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GalleryModel galleryViewModel = galleryViewModelList.get(position);
        Glide.with(context).asBitmap().load(galleryViewModel.imagePath).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return galleryViewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
