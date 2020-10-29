package com.example.villaromanticacancun.tours;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villaromanticacancun.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ToursAdapter extends RecyclerView.Adapter<ToursAdapter.ViewHolder> {

    private Context toursContext;
    private ArrayList<ToursDatabase> toursList;
    private OnItemTourClickListener itemListener;

    public ToursAdapter(Context toursContext, ArrayList<ToursDatabase> toursList, OnItemTourClickListener itemListener) {
        this.toursContext = toursContext;
        this.toursList = toursList;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tours_recycler_view, parent, false);
        return new ToursAdapter.ViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tourName.setText(toursList.get(position).getTourName());
        holder.tourLocation.setText(toursList.get(position).getTourLocation());
        holder.tourAbout.setText(toursList.get(position).getTourAbout());

        Picasso.get().load(toursList.get(position).getTourPhoto()).into(holder.tourPhoto);

    }

    @Override
    public int getItemCount() {
        return toursList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tourName, tourLocation, tourAbout;
        ImageView tourPhoto;
        OnItemTourClickListener onItemTourClickListener;

        public ViewHolder(@NonNull View itemView, OnItemTourClickListener onItemTourClickListener) {
            super(itemView);

            tourName = itemView.findViewById(R.id.textViewTourRecyclerView);
            tourLocation = itemView.findViewById(R.id.textViewHowFarTourRecyclerView);
            tourAbout = itemView.findViewById(R.id.editTextAboutTheTourRecyclerView);
            tourPhoto = itemView.findViewById(R.id.imageViewTourRecyclerView);
            this.onItemTourClickListener = onItemTourClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemTourClickListener.onItemTourClick(getAdapterPosition());
        }
    }

    public interface OnItemTourClickListener {
        void onItemTourClick(int position);
    }

}
