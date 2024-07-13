package com.example.shop_thoi_trang_mobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.ChatAdapter;
import com.example.shop_thoi_trang_mobile.adapter.ChatBoxAdapter;
import com.example.shop_thoi_trang_mobile.model.AuthListResponse;
import com.example.shop_thoi_trang_mobile.model.AuthResponse;
import com.example.shop_thoi_trang_mobile.model.ChatList;
import com.example.shop_thoi_trang_mobile.model.ChatMessage;
import com.example.shop_thoi_trang_mobile.model.User;
import com.example.shop_thoi_trang_mobile.networking.AuthService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference chatDatabaseReference;
    private ArrayList<ChatList> chatLists;
    private ChatAdapter chatAdapter;
    private ListView chatListView;
    private AuthService authService;
    private int[] roomIdList;
    private Button btnBack;
    private ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FirebaseApp.initializeApp(this);
        chatListView = findViewById(R.id.chatListView);
        chatLists = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, R.layout.chat_item, chatLists);
        chatListView.setAdapter(chatAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        bottomNavigationView.setPadding(0, 0, 0, 0);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(ChatActivity.this, activity_product_admin.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(ChatActivity.this, activity_listorder_admin.class);
                } else if (item.getItemId() == R.id.nav_chat) {
                    // Chuyển sang activity Cart (ví dụ)
                    //intent = new Intent(ChatActivity.this, ChatActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(ChatActivity.this, UserProfileActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
        Call<AuthListResponse> call = authService.getUsers();
        call.enqueue(new Callback<AuthListResponse>() {
            @Override
            public void onResponse(Call<AuthListResponse> call, Response<AuthListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        users = response.body().getResult();
                    } else {
                        Log.e("API_ERROR", "Response unsuccessful");
                    }
                }
            }
            @Override
            public void onFailure(Call<AuthListResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error load users", t);
            }
        });
        chatDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatLists.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    int id = Integer.parseInt(postSnapshot.getKey());
                    ChatList chatList = new ChatList(id,"", "", 3);
                    chatDatabaseReference.child(Integer.toString(id)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                ChatMessage chatMessage = postSnapshot.getValue(ChatMessage.class);
                                chatList.setLastMsg(chatMessage.getMessage());
                                if(users != null){
                                    for (User user : users) {
                                        if (user.getUsersId() == id) {
                                            chatList.setName(user.getUsersName());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle database error
                        }
                    });
                    if(chatList.getName().isEmpty()) chatList.setName("Customer with ID "+ String.valueOf(id));
                    chatLists.add(chatList);
                }
                chatAdapter.notifyDataSetChanged();
                chatListView.smoothScrollToPosition(chatLists.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txtId);
                Intent intent = new Intent(ChatActivity.this, ChatBoxActivity.class);
                int roomId = Integer.parseInt(textView.getText().toString());
                intent.putExtra("roomId", roomId);
                startActivity(intent);
            }
        });
/*        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });*/
    }
}
