package com.evildell.nesh.wearablelist;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WearItem extends Activity{

    private ImageView mImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_wear_list);

        mImgView = (ImageView) findViewById(R.id.img);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            Toast.makeText(this, String.format(extras.getString("name")), Toast.LENGTH_SHORT).show();
            mImgView.setImageDrawable(getResources().getDrawable(extras.getInt("image")));
        }
    }
}
