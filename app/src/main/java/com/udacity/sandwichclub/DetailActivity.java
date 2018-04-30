package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView image_iv;
    private TextView tv_origin_label;
    private TextView origin_tv;
    private TextView tv_ingredients_label;
    private TextView ingredients_tv;
    private TextView tv_also_known_as_label;
    private TextView also_known_tv;
    private TextView tv_description_label;
    private TextView description_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

          image_iv = (ImageView) findViewById(R.id.image_iv);
//        TextView tv_origin_label = (TextView) findViewById(R.id.tv_origin_label);
          origin_tv = (TextView) findViewById(R.id.origin_tv);
//        TextView tv_ingredients_label = (TextView) findViewById(R.id.tv_ingredients_label);
          ingredients_tv = (TextView) findViewById(R.id.ingredients_tv);
//        TextView tv_also_known_as_label = (TextView) findViewById(R.id.tv_also_known_as_label);
          also_known_tv = (TextView) findViewById(R.id.also_known_tv);
//        TextView tv_description_label = (TextView) findViewById(R.id.tv_description_label);
          description_tv = (TextView) findViewById(R.id.description_tv);

//        ImageView ingredientsIv = findViewById(R.id.image_iv);
//
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
//        Log.d("position", String.valueOf(position));

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
//        Log.d("json", json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher)
                .into(image_iv);

        setTitle(sandwich.getMainName());

//    }
    }

     void populateUI(Sandwich sandwich) {
        String aKa = String.valueOf(sandwich.getAlsoKnownAs().isEmpty()? "Not Available" : getString(sandwich.getAlsoKnownAs()));
        also_known_tv.setText(aKa);

        String ingre = String.valueOf((sandwich.getIngredients()).isEmpty()? "Not Available" : getString(sandwich.getIngredients()));
        ingredients_tv.setText(ingre);

        String pOr = sandwich.getPlaceOfOrigin().isEmpty()? "Not Available": sandwich.getPlaceOfOrigin();
        origin_tv.setText(pOr);


        String des = sandwich.getDescription().isEmpty()? "Not Available" : sandwich.getDescription();
        description_tv.setText(des);
    }

    private StringBuilder getString(List<String> array) {
    StringBuilder builder = new StringBuilder();
    for(String s : array){
        builder.append(s+",");
    }
        return builder;
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
