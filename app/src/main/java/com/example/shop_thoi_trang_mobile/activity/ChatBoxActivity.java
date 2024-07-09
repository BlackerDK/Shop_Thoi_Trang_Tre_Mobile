package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ChatBoxAdapter;
import com.example.shop_thoi_trang_mobile.model.ChatMessage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatBoxActivity extends AppCompatActivity {

    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton, btnBack;
    private DatabaseReference chatDatabaseReference;
    private ChatBoxAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;
    private int roomId;
    private int role;
    private int loginId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);
        FirebaseApp.initializeApp(this);
        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        btnBack = findViewById(R.id.btnBack);
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatBoxAdapter(this, R.layout.chatbox_item, chatMessages);
        chatListView.setAdapter(chatAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        loginId = sharedPreferences.getInt("userId", 0);
        role = sharedPreferences.getInt("role", 0);
        if(role == 3) {
            roomId = loginId;
        }
        else{
            Intent intent = getIntent();
            roomId = intent.getIntExtra("roomId", 0);
        }
        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(Integer.toString(roomId));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role == 3) {
                    startActivity(new Intent(ChatBoxActivity.this, HomeActivity.class));
                }else{
                    startActivity(new Intent(ChatBoxActivity.this, ChatActivity.class));
                }
                finish();
            }});
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        chatDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatMessages.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ChatMessage chatMessage = postSnapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        if(roomId == chatMessage.getUserid() && role == 3){
                            chatMessage.setName("Me:");
                        }else if(roomId != chatMessage.getUserid() && role == 3){
                            chatMessage.setName("Shop:");
                        }else if(roomId == chatMessage.getUserid() && role != 3){
                            chatMessage.setName("Customer:");
                        }else if(roomId != chatMessage.getUserid() && role != 3){
                            chatMessage.setName("Me:");
                        }
                    }
                    chatMessages.add(chatMessage);
                }
                chatAdapter.notifyDataSetChanged();
                chatListView.smoothScrollToPosition(chatMessages.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(loginId,message,System.currentTimeMillis());
            chatDatabaseReference.push().setValue(chatMessage);
            messageEditText.setText("");
        }
    }
}