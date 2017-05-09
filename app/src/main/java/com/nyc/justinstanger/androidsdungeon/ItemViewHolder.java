package com.nyc.justinstanger.androidsdungeon;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by justinstanger on 4/8/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder{
    //Main Activity
    public ImageButton mGoToCheckout;
    public ImageView mItemImage;


    //Detail Activity
    public TextView mItemName;
    public TextView mItemPrice;
    public TextView mItemType;
    public ImageButton mAddItem;
    public ImageView mDetailItemImage;


    public ItemViewHolder(View itemView){
        super(itemView);

        //Main Activity
        mGoToCheckout = (ImageButton) itemView.findViewById(R.id.go_to_checkout);
        mItemImage = (ImageView) itemView.findViewById(R.id.item_image);

        //Detail Activity
        mDetailItemImage = (ImageView) itemView.findViewById(R.id.detail_item_image);

        mAddItem = (ImageButton) itemView.findViewById(R.id.detail_add_item);
        mItemName = (TextView) itemView.findViewById(R.id.detail_item_name);
        mItemPrice = (TextView) itemView.findViewById(R.id.detail_item_price);
        mItemType = (TextView) itemView.findViewById(R.id.detail_item_type);



    }

}
