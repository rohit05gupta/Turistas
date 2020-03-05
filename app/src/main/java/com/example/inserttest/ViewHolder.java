package com.example.inserttest;

import androidx.recyclerview.*;
import androidx.recyclerview.widget.*;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView t1,t2;
    public ImageView i1;
    //View mView;
    public ViewHolder(View itemView){
        super(itemView);
        //mView = itemView;
        t1 = (TextView)itemView.findViewById(R.id.name);
        t2 = (TextView)itemView.findViewById(R.id.specification);
        i1 = (ImageView)itemView.findViewById(R.id.image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }


    /*public void setDetails(Context ctx,String name,String image,String specification){
        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mDetailTv = mView.findViewById(R.id.rDescriptionTv);
        ImageView mImageIv = mView.findViewById(R.id.rImageView);

        mTitleTv.setText(name);
        mDetailTv.setText(specification);
        Picasso.get().load(image).into(mImageIv);

    }*/
    private ViewHolder.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
