package com.example.villaromanticacancun.rooms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villaromanticacancun.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private Context roomsContext;
    private ArrayList<RoomsDatabase> roomsList;
    private OnItemRoomClickListener itemListener;

    public RoomsAdapter(Context roomsContext, ArrayList<RoomsDatabase> roomsList, OnItemRoomClickListener listener) {
        this.roomsContext = roomsContext;
        this.roomsList = roomsList;
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_recycler_view, parent, false);
        return new ViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.roomType.setText(roomsList.get(position).getRoomType());
        holder.roomLocation.setText(roomsList.get(position).getRoomLocation());
        holder.roomAbout.setText(roomsList.get(position).getRoomAbout());

        Picasso.get().load(roomsList.get(position).getRoomPhoto()).into(holder.roomPhoto);

    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView roomType, roomLocation, roomAbout;
        ImageView roomPhoto;
        OnItemRoomClickListener onItemRoomClickListener;

        public ViewHolder(@NonNull View itemView, OnItemRoomClickListener onItemRoomClickListener) {
            super(itemView);

            roomType = itemView.findViewById(R.id.textViewTypeRoomRecyclerView);
            roomLocation = itemView.findViewById(R.id.textViewLocationRecyclerView);
            roomAbout = itemView.findViewById(R.id.editTextAboutTheRoomRecyclerView);
            roomPhoto = itemView.findViewById(R.id.imageViewRoomRecyclerView);
            this.onItemRoomClickListener = onItemRoomClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onItemRoomClickListener.onItemRoomClick(getAdapterPosition());

        }
    }

    public interface OnItemRoomClickListener {
        void onItemRoomClick(int position);
    }
}
