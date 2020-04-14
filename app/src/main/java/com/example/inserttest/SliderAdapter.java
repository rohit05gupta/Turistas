package com.example.inserttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public  int[] slide_images = {
            R.drawable.route_walkthrough,
            R.drawable.cust_walkthrough,
            R.drawable.location_walkthrough,
            R.drawable.recom_walkthrough
    };

    public  String[] slide_headings = {
            "Shortest Sequence",
            "Customize Route",
            "Nearby Places",
            "Recommendation"
    };

    public  String[] slide_descs = {
            "Save travel time Significantly using shortest sequence connecting all tourist places in a city",
            "Remove places which you don't want to visit and get the new updated shortest travel sequence",
            "See hotels, restaurants, petrol pump, ATM near by any visiting place",
            "See recommendations based on your place of interest and also based on your previous visits"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.walkImage);
        TextView slideHeading = view.findViewById(R.id.walkTextHeading);
        TextView slideDescription = view.findViewById(R.id.walkTextDesc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
