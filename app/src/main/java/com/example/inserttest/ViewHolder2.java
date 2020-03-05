package com.example.inserttest;
import androidx.recyclerview.*;
import androidx.recyclerview.widget.*;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ViewHolder2 extends RecyclerView.ViewHolder {

    public TextView t1,t2,t3,t4,t5;
    public ImageView i1;
    public Button b1,b2;
    //View mView;
    public ViewHolder2(View itemView){
        super(itemView);
        //mView = itemView;
        t1 = (TextView)itemView.findViewById(R.id.name);
        t2 = (TextView)itemView.findViewById(R.id.specification);
        i1 = (ImageView)itemView.findViewById(R.id.image);

        t3 = (TextView)itemView.findViewById(R.id.description);
        t4 = (TextView)itemView.findViewById(R.id.view);
        t5 = (TextView)itemView.findViewById(R.id.reach);

        b1 = (Button)itemView.findViewById(R.id.visit);
        b2 = (Button)itemView.findViewById(R.id.review);
    }


    /*public void setDetails(Context ctx,String name,String image,String specification){
        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mDetailTv = mView.findViewById(R.id.rDescriptionTv);
        ImageView mImageIv = mView.findViewById(R.id.rImageView);

        mTitleTv.setText(name);
        mDetailTv.setText(specification);
        Picasso.get().load(image).into(mImageIv);

    }*/

}

