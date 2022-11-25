package com.example.binder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binder.Entities.Message;
import com.example.binder.MessageActivity;
import com.example.binder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    public static final int MSG_LEFT=0;
    public static final int MSG_RIGHT=1;

    List<Message> messages;
    private LayoutInflater inflater;
    private Context context;

    FirebaseUser firebaseUser;

    public MessagesAdapter(Context context, List<Message> messages){
        inflater = LayoutInflater.from(context);
        this.messages = messages;

    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_RIGHT) {
            View view = inflater.inflate(R.layout.msg_item_right, parent, false);
            MessagesAdapter.ViewHolder viewHolder = new MessagesAdapter.ViewHolder(view);
            return viewHolder;
        }
        else {
            View view = inflater.inflate(R.layout.msg_item_left, parent, false);
            MessagesAdapter.ViewHolder viewHolder = new MessagesAdapter.ViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.msg_text.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView msg_text;
        ImageView uImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            msg_text =  itemView.findViewById(R.id.message);
            uImage = itemView.findViewById(R.id.uImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_RIGHT;
        }
        else {
            return MSG_LEFT;
        }
    }
}

