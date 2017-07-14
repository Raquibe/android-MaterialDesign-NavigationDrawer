package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This is a custom view for Navigation Drawer header view. It displays a list of
 * servers user is currently connected to
 *
 * Created by ramiz on 7/15/17.
 */

public class NavDrawerHeaderView extends LinearLayout {
    private ImageView serverIconImageView;
    private TextView serverNameTextView;
    private ImageView downIconImageView;

    public NavDrawerHeaderView(Context context) {
        super(context);
        init();
    }

    public NavDrawerHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavDrawerHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_nav_drawer_header, this, true);

        serverIconImageView = findViewById(R.id.image_view_server_icon);
        serverNameTextView = findViewById(R.id.text_view_server_name);
        downIconImageView = findViewById(R.id.image_view_icon_down);
    }
}
