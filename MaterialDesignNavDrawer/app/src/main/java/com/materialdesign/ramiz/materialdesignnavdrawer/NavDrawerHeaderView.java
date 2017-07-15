package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    @NonNull
    private PopupMenu popupMenu;

    @NonNull
    private ArrayList<BackendHost> backendHosts = new ArrayList<>();

    @Nullable
    private OnItemSelectedListener onItemSelectedListener = null;

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

    /**
     * Initializes this view by inflating layout resource
     * and setting views references
     */
    public void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_nav_drawer_header, this, true);

        serverIconImageView = findViewById(R.id.image_view_server_icon);
        serverNameTextView = findViewById(R.id.text_view_server_name);
        downIconImageView = findViewById(R.id.image_view_icon_down);

        popupMenu = new PopupMenu(getContext(), this);
        setListeners();
        handleEmptyHostsListState();
        populateDummyData();
    }

    public void populateDummyData() {
        ArrayList<BackendHost> backendHosts = new ArrayList<>();
        BackendHost backendHost = new BackendHost();
        backendHost.name = "Ramiz-MBP";
        backendHost.client = Preferences.Clients.BLUETOOTH;

        BackendHost backendHost1 = new BackendHost();
        backendHost1.name = "Ramiz-MBP-Wifi";
        backendHost1.client = Preferences.Clients.TCP;

        backendHosts.add(backendHost);
        backendHosts.add(backendHost1);
        populateData(backendHosts);
    }

    /**
     * Initializes NavDrawerHeaderView with data passed
     * @param backendHosts  list of hosts to load into view
     */
    public void populateData(@NonNull ArrayList<BackendHost> backendHosts) {
        //let's clear existing data
        this.backendHosts.clear();
        this.popupMenu.getMenu().clear();
        //add new data
        this.backendHosts.addAll(backendHosts);

        //if there are no hosts then update
        //header view to show no servers text
        if (this.backendHosts.isEmpty()) {
            handleEmptyHostsListState();
            return;
        }

        //update PopMenu based on new data passed
        updatePopupMenuView();

        //update header view to show first host's details
        updateNavDrawerHeaderView(backendHosts.get(0));
    }

    private void updatePopupMenuView() {
        //if there are more than 1 hosts in the list
        //then display add a popup menu
        if (this.backendHosts.size() > 1) {
            //make down arrow icon image view visible to indicate
            //that there is a popup menu
            downIconImageView.setVisibility(VISIBLE);
            //initialize popup menu with new data
            for (int i = 0; i < backendHosts.size(); ++i) {
                popupMenu.getMenu().add(0, i, i, backendHosts.get(i).name);
            }
        } else {
            //hide down arrow icon as there is only one item
            downIconImageView.setVisibility(INVISIBLE);
        }
    }

    public void setListeners() {
        //register listener directly with parent class
        //as we are overriding setOnClickListener method
        //in current class
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backendHosts.size() > 1) {
                    popupMenu.show();
                }
            }
        });

        //register listener with our PopMenu that appears when user clicks servers header view
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //get the selected host
                BackendHost backendHost = backendHosts.get(item.getItemId());
                updateNavDrawerHeaderView(backendHost);


                //notify the listener, if any
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(backendHost);
                }

                return true;
            }
        });
    }

    /**
     * This method updates the view with empty state text and icon
     * It updates the view that user sees when opening nav drawer and not the popup menu
     */
    private void handleEmptyHostsListState() {
        serverIconImageView.setImageResource(R.drawable.main_wifi);
        serverNameTextView.setText(getContext().getString(R.string.menu_left_servers_none));
        downIconImageView.setVisibility(INVISIBLE);
    }

    /**
     * This method updates the header view with data passed.
     * It updates the view that user sees when opening nav drawer and not the popup menu
     * @param backendHost host model with which to update header view
     */
    private void updateNavDrawerHeaderView(BackendHost backendHost) {
        //update icon and title of parent view to which this PopMenu is anchored
        serverNameTextView.setText(backendHost.name);
        //check if host is Wifi or bluetooth and pick the right icon
        if (backendHost.client.equals(Preferences.Clients.BLUETOOTH)) {
            serverIconImageView.setImageResource(R.drawable.main_bt);
        } else {
            serverIconImageView.setImageResource(R.drawable.main_wifi);
        }
    }

    //we don't anyone else to listen to this event because
    // we provide a custom OnItemSelectedListener for outside world
    // so
    //we have overridden it with empty body
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {

    }

    /**
     * Notifies the listener when a server is selected
     * @param onItemSelectedListener
     */
    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(BackendHost backendHost);
    }
}
