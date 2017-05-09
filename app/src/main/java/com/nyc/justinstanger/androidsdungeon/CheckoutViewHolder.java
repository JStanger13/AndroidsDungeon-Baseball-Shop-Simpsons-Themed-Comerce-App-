package com.nyc.justinstanger.androidsdungeon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by justinstanger on 4/12/17.
 */

public class CheckoutViewHolder extends RecyclerView.ViewHolder{
    public TextView mItemName;
    //public TextView mItemType;
    public ImageView mItemImage;
    public ImageButton mDeleteImage;
    public TextView mQuantity;
    public Button mProcedeCheckout;
    public TextView mCheckoutPrice;


    public CheckoutViewHolder(View itemView){
        super(itemView);

        mDeleteImage = (ImageButton) itemView.findViewById(R.id.delete_item);
        mItemName = (TextView) itemView.findViewById(R.id.checkout_item_name);
        mItemImage = (ImageView) itemView.findViewById(R.id.checkout_image);
        mQuantity = (TextView) itemView.findViewById(R.id.quantity);
        mProcedeCheckout = (Button)itemView.findViewById(R.id.procede_to_checkout);
        mCheckoutPrice = (TextView)itemView.findViewById(R.id.item_price);

    }

}