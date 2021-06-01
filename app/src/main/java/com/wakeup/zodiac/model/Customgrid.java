package com.wakeup.zodiac.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wakeup.zodiac.R;
import com.wakeup.zodiac.customview.Sqarelayout;

public class Customgrid extends BaseAdapter {
    private final int[] imageid;
    private Context mcontext;
    private final String[] name;
    View view;

    public Customgrid(Context context1, String[] name2, int[] image) {
        this.mcontext = context1;
        this.name = name2;
        this.imageid = image;
    }

    public int getCount() {
        return this.name.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("data_loded", "loded");
        View grid = ((LayoutInflater) this.mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.grid, (ViewGroup) null);
        TextView textView = (TextView) grid.findViewById(R.id.gridtext);
        ImageView imageView = (ImageView) grid.findViewById(R.id.gridimage);
        textView.setText(this.name[position]);
        imageView.setImageResource(this.imageid[position]);
        Sqarelayout rlSign = (Sqarelayout) grid.findViewById(R.id.rlSign);
        if (this.mcontext.getSharedPreferences("MyPrefs", 0).getString("Sign", "").equals(this.name[position])) {
            Animation animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(1000);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(-1);
            animation.setRepeatMode(2);
            rlSign.startAnimation(animation);
        } else {
            textView.setText(this.name[position]);
            imageView.setImageResource(this.imageid[position]);
        }
        return grid;
    }
}
