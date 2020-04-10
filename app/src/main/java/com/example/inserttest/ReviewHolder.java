package com.example.inserttest;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReviewHolder extends RecyclerView.ViewHolder{
    public TextView t1,t2,t3,t4;
    public RatingBar r1;
    //View mView;
    public ReviewHolder(View itemView){
        super(itemView);
        t1 = (TextView)itemView.findViewById(R.id.email);
        t2 = (TextView)itemView.findViewById(R.id.title);
        t3 = (TextView)itemView.findViewById(R.id.comment);
        //t4 = (TextView)itemView.findViewById(R.id.cost);

        r1 = (RatingBar)itemView.findViewById(R.id.ratingBar);
    }
}
