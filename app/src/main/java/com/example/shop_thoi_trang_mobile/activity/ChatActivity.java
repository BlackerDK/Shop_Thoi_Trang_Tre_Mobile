package com.example.shop_thoi_trang_mobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ChatAdapter;
import com.example.shop_thoi_trang_mobile.model.ChatMessage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private DatabaseReference chatDatabaseReference;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FirebaseApp.initializeApp(this);
        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, R.layout.chat_item, chatMessages);
        chatListView.setAdapter(chatAdapter);

        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("chats");

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
            ChatMessage chatMessage = new ChatMessage(message, System.currentTimeMillis());
            chatDatabaseReference.push().setValue(chatMessage);
            messageEditText.setText("");
        }
    }
}