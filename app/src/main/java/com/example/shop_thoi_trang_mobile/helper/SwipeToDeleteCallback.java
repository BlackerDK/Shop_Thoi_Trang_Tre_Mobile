package com.example.shop_thoi_trang_mobile.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.adapter.CartAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private CartAdapter mAdapter;

    public SwipeToDeleteCallback(CartAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false; // We don't want to support move actions in this case
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.confirmDelete(position);
    }
}