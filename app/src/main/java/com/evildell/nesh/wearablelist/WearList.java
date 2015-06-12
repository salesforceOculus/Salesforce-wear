package com.evildell.nesh.wearablelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evildell.nesh.wearablelist.R;

import java.util.ArrayList;

public class WearList extends Activity implements WearableListView.ClickListener  {

    private static ArrayList<Integer> mListItems;
    private static ArrayList<String> mListNames;
    private TextView mListHeader;
    private WearableListView mListView;
    private MyListAdapter mAdapter;
    private float mDefaultCircleRadius;
    private float mSelectedCircleRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_list);

        mDefaultCircleRadius = 100.0f;
        mSelectedCircleRadius = 100.0f;

        // Sample image set for the list
        mListItems = new ArrayList<Integer>();
        mListItems.add(R.drawable.ic_action_attach);
        mListItems.add(R.drawable.ic_action_call);
        mListItems.add(R.drawable.ic_action_locate);
        mListItems.add(R.drawable.ic_action_mail);
        mListItems.add(R.drawable.ic_action_microphone);
        mListItems.add(R.drawable.ic_action_photo);
        mListItems.add(R.drawable.ic_action_star);
        mListItems.add(R.drawable.ic_action_user);
        mListItems.add(R.drawable.ic_action_video);

        mListNames = new ArrayList<String>();
        mListNames.add("Attach");
        mListNames.add("Call");
        mListNames.add("Locate");
        mListNames.add("Mail");
        mListNames.add("Microphone");
        mListNames.add("Photo");
        mListNames.add("Star");
        mListNames.add("User");
        mListNames.add("Video");

        // This is our list header
        mListHeader = (TextView) findViewById(R.id.header);

        // Get the list component from the layout of the activity
        mListView = (WearableListView) findViewById(R.id.wearable_List);

        // Assign an adapter to the list
        mAdapter = new MyListAdapter();
        mListView.setAdapter(mAdapter);

        mListView.setClickListener(WearList.this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
      //  Toast.makeText(this, String.format("You selected item #%s", viewHolder.getPosition()+1), Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(WearList.this, WearItem.class);
        myIntent.putExtra("name", mListNames.get(viewHolder.getPosition())); //Optional parameters
        myIntent.putExtra("image", mListItems.get(viewHolder.getPosition()));
        WearList.this.startActivity(myIntent);
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    public class MyListAdapter extends WearableListView.Adapter {

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WearableListView.ViewHolder(new MyItemView(WearList.this));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
            MyItemView itemView = (MyItemView) viewHolder.itemView;

            TextView txtView = (TextView) itemView.findViewById(R.id.text);
            txtView.setText(mListNames.get(i));

            Integer resourceId = mListItems.get(i);
            CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.image);
            imgView.setImageResource(resourceId);
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }
    }

    private final class MyItemView extends FrameLayout implements WearableListView.Item {

        final CircledImageView imgView;
        final TextView txtView;
        private float mScale;
        private final int mFadedCircleColor;
        private final int mChosenCircleColor;

        public MyItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.advanced_item_layout, this);
            imgView = (CircledImageView) findViewById(R.id.image);
            txtView = (TextView) findViewById(R.id.text);

            mFadedCircleColor = getResources().getColor(R.color.grey);
            mChosenCircleColor = getResources().getColor(R.color.grey);
        }

        @Override
        public float getProximityMinValue() {
            return mDefaultCircleRadius;
        }

        @Override
        public float getProximityMaxValue() {
            return mSelectedCircleRadius;
        }

        @Override
        public float getCurrentProximityValue() {
            return mScale;
        }

        @Override
        public void setScalingAnimatorValue(float value) {
            mScale = value;
            imgView.setCircleRadius(mScale);
            imgView.setCircleRadiusPressed(mScale);
        }

        @Override
        public void onScaleUpStart() {
            imgView.setAlpha(1f);
            txtView.setAlpha(1f);
            imgView.setCircleColor(mChosenCircleColor);
        }

        @Override
        public void onScaleDownStart() {
            imgView.setAlpha(0.5f);
            txtView.setAlpha(0.5f);
            imgView.setCircleColor(mFadedCircleColor);
        }
    }
}