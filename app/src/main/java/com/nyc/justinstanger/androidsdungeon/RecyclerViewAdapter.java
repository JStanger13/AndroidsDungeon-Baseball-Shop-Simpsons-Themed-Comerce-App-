package com.nyc.justinstanger.androidsdungeon;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static com.nyc.justinstanger.androidsdungeon.R.id.image;
import static com.nyc.justinstanger.androidsdungeon.R.id.parent;

/**
 * Created by justinstanger on 4/8/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Item> mItemList;

    public RecyclerViewAdapter(final List<Item> itemList){mItemList = itemList;}

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_store, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(parentView);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final Item currentItem = mItemList.get(position);

        holder.mItemImage.setBackgroundResource(currentItem.getImage());

        //holder.mItemName.setText(currentItem.getName());

        //int resId = context.getResources().getIdentifier("picture1","drawable", context.getPackageName());
        //image.setImageResource(resId);


        //Log.d(TAG, "onBindViewHolder: binding");


        //holder.mItemName.setText(currentItem.getName());
        //holder.mItemPrice.setText("$" + String.valueOf(currentItem.getPrice()));
        //holder.mItemImage.setBackground(currentItem.getImage());

/*
        holder.mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add by adding 1 to the quantity column in SQLite
            SQLiteHelper.getInstance(v.getContext()).addToCart(currentItem, 1);



                Toast.makeText(v.getContext(), "added to cart", Toast.LENGTH_SHORT).show();

                // Can change 1 to user input.
                //CHANGE PRICE TO MULTIPLY QUANTITY

            }
        });

*/
        holder.mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //intent.putExtra(DetailActivity.SELECTED_BOOK_ID_KEY, currentBook.getId());

                Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
                detailIntent.putExtra(DetailActivity.SELECTED_ID_KEY, currentItem.getId());

                v.getContext().startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    public void changeList(List<Item> list) {
        mItemList=list;
        notifyDataSetChanged();
    }

}

