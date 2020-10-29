package com.example.villaromanticacancun.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villaromanticacancun.R;
import com.example.villaromanticacancun.rooms.RoomsAdapter;
import com.example.villaromanticacancun.rooms.RoomsDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Context galleryContext;
    private ArrayList<GalleryDatabase> galleryList;

    public GalleryAdapter (Context galleryContext, ArrayList<GalleryDatabase> galleryList) {
        this.galleryContext = galleryContext;
        this.galleryList = galleryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_recycler_view, parent, false);
        return new GalleryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(galleryList.get(position).getGalleryPhoto()).into(holder.galleryPhoto);


    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryPhoto = itemView.findViewById(R.id.imageViewGalleryRecyclerView);

        }
    }
}
