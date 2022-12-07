package com.example.binder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binder.Entities.User;
import com.example.binder.MessageActivity;
import com.example.binder.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    List<User> userList;
    private LayoutInflater inflater;
    private Context context;

    public MatchesAdapter(Context context, List<User> userList){
        inflater = LayoutInflater.from(context);
        this.userList = userList;

    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.matches_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.uName.setText(user.getName());
        holder.uAge.setText("24");

        holder.uImage.setImageResource(R.drawable.user);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView uName,uAge;
        ImageView uImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            uName = itemView.findViewById(R.id.uName);
            uAge =  itemView.findViewById(R.id.uAge);
            uImage = itemView.findViewById(R.id.uImage);
        }
    }

}
