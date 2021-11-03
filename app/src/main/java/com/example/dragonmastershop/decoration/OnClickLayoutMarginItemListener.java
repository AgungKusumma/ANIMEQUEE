package com.example.dragonmastershop.decoration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnClickLayoutMarginItemListener {
    void onClick(Context context, View v, int position, int spanIndex, RecyclerView.State state);
}
