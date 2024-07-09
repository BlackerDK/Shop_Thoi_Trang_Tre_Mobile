package com.example.shop_thoi_trang_mobile.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.ChatMessage;

import java.util.List;

public class ChatBoxAdapter extends ArrayAdapter<ChatMessage> {

    private Context mContext;
    private int mResource;

    public ChatBoxAdapter(@NonNull Context context, int resource, @NonNull List<ChatMessage> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String message = getItem(position).getMessage();
        String name = getItem(position).getName();
        long timestamp = getItem(position).getTimestamp();
        int userid = getItem(position).getUserid();
        ChatMessage chatMessage = new ChatMessage(userid, name, message, timestamp);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        TextView timestampTextView = convertView.findViewById(R.id.timestampTextView);
        TextView nameTextView = convertView.findViewById(R.id.name);
        messageTextView.setText(chatMessage.getMessage());
        timestampTextView.setText(android.text.format.DateFormat.format("dd-MM-yyyy (HH:mm:ss)", chatMessage.getTimestamp()));
        nameTextView.setText(chatMessage.getName());
        return convertView;
    }
}