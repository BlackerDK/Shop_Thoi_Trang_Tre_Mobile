package com.example.shop_thoi_trang_mobile.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeigh;

    public SpacingItemDecoration(int verticalSpaceHeigh) {
        this.verticalSpaceHeigh = verticalSpaceHeigh;
    }

    @Override
    public void getItemOffsets(@androidx.annotation.NonNull Rect outRect, @androidx.annotation.NonNull View view, @androidx.annotation.NonNull RecyclerView parent, @androidx.annotation.NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeigh;
    }
}
