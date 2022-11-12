package com.example.binder.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binder.R;

import java.util.List;

public class ImageListAdapter extends
        RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    List<Integer> profileImageList;

    public ImageListAdapter(List<Integer> tuneList) {
        profileImageList = tuneList;
    }

    public List<Integer> getProfileImageList() {
        return profileImageList;
    }

    public void setProfileImageList(List<Integer> profileImageList) {
        this.profileImageList = profileImageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_image_list, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(itemView);
        viewHolder.imageView = itemView.findViewById(R.id.imgView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(profileImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return profileImageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
