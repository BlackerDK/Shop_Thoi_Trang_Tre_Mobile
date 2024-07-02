package com.example.shop_thoi_trang_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.model.Notice;
import com.example.shop_thoi_trang_mobile.model.Order;

import java.util.ArrayList;

public class NotificationAdapter extends  RecyclerView.Adapter<NotificationViewHolder> {

    private Context context;
    private ArrayList<Notice> notices;
    public NotificationAdapter(Context Context, ArrayList<Notice> Notices) {
        this.context = Context;
        this.notices = Notices;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notice notice = notices.get(position);
        holder.notificationTitle.setText(notice.getNoticeTitle());
        holder.notificationBody.setText(notice.getNoticeContent());
        holder.notificationTime.setText(notice.getNoticeStatus());
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }
}
class NotificationViewHolder extends RecyclerView.ViewHolder{

    ImageView notificationImage;
    TextView notificationTitle, notificationBody, notificationTime;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        notificationImage = itemView.findViewById(R.id.notificationImage);
        notificationTitle = itemView.findViewById(R.id.notificationTitle);
        notificationBody = itemView.findViewById(R.id.notificationBody);
        notificationTime = itemView.findViewById(R.id.notificationTime);
    }
}
