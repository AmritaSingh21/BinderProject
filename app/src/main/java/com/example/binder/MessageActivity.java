package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binder.Adapters.MessagesAdapter;
import com.example.binder.Entities.Message;
import com.example.binder.Entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ImageView uImg;
    TextView uName;
    ImageButton btnSend;
    EditText message_text;
    MessagesAdapter messagesAdapter;
    List<Message> messages;
    RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        uImg = findViewById(R.id.uImage);
        uName = findViewById(R.id.uName);
        btnSend = findViewById(R.id.btn_send);
        message_text = findViewById(R.id.msg_text);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        String userID = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                uName.setText(user.getName());
                readMessage(firebaseUser.getUid(),userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message_text.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(),userID,msg);
                }
                else{
                    Toast.makeText(MessageActivity.this,"You can't send empty message.",
                            Toast.LENGTH_SHORT).show();
                }
                message_text.setText("");
            }
        });
    }

    private void sendMessage(String sender,String receiver,String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hm = new HashMap<>();
        hm.put("sender",sender);
        hm.put("receiver",receiver);
        hm.put("message",message);

        reference.child("Messages").push().setValue(hm);
    }

    private void readMessage(String myId,String userId){
        messages = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
                    if(message.getReceiver().equals(userId)&&message.getSender().equals(myId)
                            ||message.getReceiver().equals(myId)&&message.getSender().equals(userId)){
                        messages.add(message);
                    }
                    messagesAdapter = new MessagesAdapter(MessageActivity.this,messages);
                    recyclerView.setAdapter(messagesAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}