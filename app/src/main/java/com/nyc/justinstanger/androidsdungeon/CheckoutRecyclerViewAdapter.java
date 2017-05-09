package com.nyc.justinstanger.androidsdungeon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by justinstanger on 4/12/17.
 */

public class CheckoutRecyclerViewAdapter extends RecyclerView.Adapter<CheckoutViewHolder> {
    List<Item> mCheckoutItemList;
    SQLiteHelper helper;

    public CheckoutRecyclerViewAdapter(Context context) {
        helper = SQLiteHelper.getInstance(context);
        mCheckoutItemList = helper.getListOfItemsInCart(context);
    }

    public void resetList(){
        mCheckoutItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CheckoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_checkout, parent, false);
        CheckoutViewHolder viewHolder = new CheckoutViewHolder(parentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CheckoutViewHolder holder, final int position) {


        //int resId = context.getResources().getIdentifier("picture1","drawable", context.getPackageName());
        //image.setImageResource(resId);


        //Log.d(TAG, "onBindViewHolder: binding");


        final Item currentItem = mCheckoutItemList.get(position);
        holder.mItemName.setText(currentItem.getName());
        holder.mQuantity.setText((Integer.toString(currentItem.getQuantity())) + " added");
        holder.mCheckoutPrice.setText("$" + Float.toString((float) currentItem.getPrice() * (int)currentItem.getQuantity()) + "0");
        holder.mItemImage.setBackgroundResource(currentItem.getImage());


        //holder.mItemPrice.setText("$" + String.valueOf(currentItem.getPrice()));

       /*

        */


        holder.mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add by adding 1 to the quantity column in SQLite

                changeCheckoutList(currentItem, holder.getAdapterPosition());

                helper.deleteFromCart(currentItem, 1);


                Toast.makeText(v.getContext(), "deleted from cart", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

                // Can change 1 to user input.
                //CHANGE PRICE TO MULTIPLY QUANTITY

            }
        });
    }


    @Override
    public int getItemCount() {
        return mCheckoutItemList.size();
    }

    public void changeCheckoutList(Item currentItem, int position) {

        if(currentItem.getQuantity()==1)
        {

         mCheckoutItemList.remove(position);
            notifyItemRemoved(position);
        }
       /*
        else
        {
            //TODO update textview
            //notifyitemchanged(position);
        }
*/

//        mCheckoutItemList = list;
//        notifyDataSetChanged();
    }

}