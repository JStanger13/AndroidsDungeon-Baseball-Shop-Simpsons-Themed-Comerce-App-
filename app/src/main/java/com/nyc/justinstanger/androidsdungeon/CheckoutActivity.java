package com.nyc.justinstanger.androidsdungeon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class CheckoutActivity extends AppCompatActivity {

    private  RecyclerView mRecyclerView;
    private CheckoutRecyclerViewAdapter adapter;
    private  SQLiteHelper helper;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mRecyclerView = (RecyclerView) findViewById(R.id.checkout_recyclerview);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CheckoutRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(adapter);

        helper = SQLiteHelper.getInstance(this);

        findViewById(R.id.procede_to_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Purchase complete", Toast.LENGTH_SHORT).show();
                adapter.resetList();
                helper.resetAllTypes();
            }
        });

    }
}
