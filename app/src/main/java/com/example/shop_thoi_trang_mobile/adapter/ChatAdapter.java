package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.ChatList;
import com.example.shop_thoi_trang_mobile.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatList> {

    private Context mContext;
    private int mResource;

    public ChatAdapter(@NonNull Context context, int resource, @NonNull List<ChatList> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String lastMessage = getItem(position).getLastMsg();
        String name = getItem(position).getName();
        int unreadMessage = getItem(position).getUnreadMsg();
        int id = getItem(position).getId();
        ChatList chatList = new ChatList(id, name, lastMessage, unreadMessage);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView lastMessageTextView = convertView.findViewById(R.id.lastMessage);
        //TextView unreadMessageTextView = convertView.findViewById(R.id.unseenMessages);
        TextView nameTextView = convertView.findViewById(R.id.name);
        TextView idTextView = convertView.findViewById(R.id.txtId);
        lastMessageTextView.setText(lastMessage);
//        unreadMessageTextView.setText(String.valueOf(unreadMessage));
//        if (unreadMessage == 0) {
//            unreadMessageTextView.setVisibility(View.GONE);
//        } else {
//            unreadMessageTextView.setVisibility(View.VISIBLE);
//        }
        nameTextView.setText(name);
        idTextView.setText(String.valueOf(id));
        return convertView;
    }
}
//    private final List<ChatList> chatLists;
//    private final Context context;
//
//    public ChatAdapter(List<ChatList> chatLists, Context context) {
//        this.chatLists = chatLists;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new MyViewHolder(View.inflate(context, R.layout.chat_item, null));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
//        ChatList chatList = chatLists.get(position);
//        holder.name.setText(chatList.getName());
//        holder.lastMessage.setText(chatList.getLastMsg());
//        holder.unreadMessage.setText(chatList.getUnreadMsg());
//        if(chatList.getUnreadMsg() == 0){
//            holder.unreadMessage.setVisibility(View.GONE);
//        }else {
//            holder.unreadMessage.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return chatLists.size();
//    }
//    static class MyViewHolder extends RecyclerView.ViewHolder{
//        private TextView name;
//        private TextView lastMessage;
//        private TextView unreadMessage;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.name);
//            lastMessage = itemView.findViewById(R.id.lastMessage);
//            unreadMessage = itemView.findViewById(R.id.unseenMessages);
//        }
//    }
//}
