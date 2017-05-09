package com.nyc.justinstanger.androidsdungeon;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerViewAdapter adapter;
    SQLiteHelper helper;
    List<Item> items;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        DBAssetHelper dbSetup = new DBAssetHelper(this);
        dbSetup.getReadableDatabase();

        SQLiteHelper helper = SQLiteHelper.getInstance(this);

        //items = helper.getAllTypes();
        items = helper.getInstance(this).getAllTypes(getApplicationContext());

        Log.d(TAG, "onCreate:" + items.size());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(items);
        mRecyclerView.setAdapter(adapter);

        findViewById(R.id.comicicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = SQLiteHelper.getInstance(v.getContext()).getAllComics(getApplicationContext());
                ((TextView) findViewById(R.id.store_tite)).setText("comics");

                adapter.changeList(items);
            }
        });

        findViewById(R.id.filmicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = SQLiteHelper.getInstance(v.getContext()).getAllMovies(getApplicationContext());
                ((TextView) findViewById(R.id.store_tite)).setText("movies");

                adapter.changeList(items);

            }
        });

        findViewById(R.id.collectableicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = SQLiteHelper.getInstance(v.getContext()).getAllCollectables(getApplicationContext());
                ((TextView) findViewById(R.id.store_tite)).setText("collectables");
                adapter.changeList(items);
            }
        });

        findViewById(R.id.allitems).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = SQLiteHelper.getInstance(v.getContext()).getAllTypes(getApplicationContext());
                ((TextView) findViewById(R.id.store_tite)).setText("all items");

                adapter.changeList(items);
            }
        });
        findViewById(R.id.go_to_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkoutIntent = new Intent(v.getContext(), CheckoutActivity.class);
                v.getContext().startActivity(checkoutIntent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

            List<Item> searchName = helper.getInstance(this).searchSimilarNames(query, getApplicationContext());
            adapter.changeList(searchName);
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);

            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            ComponentName componentName = new ComponentName(this,MainActivity.class);

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

            return true;
        }


}





