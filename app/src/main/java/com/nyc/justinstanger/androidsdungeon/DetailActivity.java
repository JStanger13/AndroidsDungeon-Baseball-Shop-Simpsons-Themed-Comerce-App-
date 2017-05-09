package com.nyc.justinstanger.androidsdungeon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.nyc.justinstanger.androidsdungeon.R.id.parent;
import static java.security.AccessController.getContext;

public class DetailActivity extends AppCompatActivity {
    public static final String SELECTED_ID_KEY = "detailIdKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        long id = getIntent().getLongExtra(SELECTED_ID_KEY, -1);

        final Item item = SQLiteHelper.getInstance(this).getItemById(id, this);

/*
        if (item == null) {
            Toast.makeText(this, "Unable to load selected item", Toast.LENGTH_SHORT).show();
            finish();

        }
*/

        // Show item details in this activity's views
        ((TextView) findViewById(R.id.detail_item_description)).setText(item.getDescription());
        ((TextView) findViewById(R.id.detail_item_name)).setText(item.getName());
        ((TextView) findViewById(R.id.detail_item_type)).setText(item.getType());
        ((TextView) findViewById(R.id.detail_item_price)).setText("$" + Integer.toString((int)item.getPrice())+ ".00");


        findViewById(R.id.detail_item_image).setBackgroundResource(item.getImage());


        ((ImageButton) findViewById(R.id.detail_add_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add by adding 1 to the quantity column in SQLite
                SQLiteHelper.getInstance(v.getContext()).addToCart(item, 1);
                Toast.makeText(v.getContext(), "added to cart", Toast.LENGTH_SHORT).show();

                // Can change 1 to user input.
                //CHANGE PRICE TO MULTIPLY QUANTITY

            }
        });

        ((ImageButton) findViewById(R.id.detail_go_to_cart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent checkoutIntent = new Intent(v.getContext(), CheckoutActivity.class);
                v.getContext().startActivity(checkoutIntent);

            }
        });

        ((ImageButton) findViewById(R.id.detail_go_back_to_store)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(mainIntent);
            }
        });



















    }
}
