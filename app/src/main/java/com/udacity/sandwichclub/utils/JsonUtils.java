package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName = null;
        String image = null;
        String placeOfOrigin = null;
        String description = null;
        List <String> alsoKnownAs = new ArrayList<>();
        List <String> ingredients = new ArrayList<>();
        JSONObject sand_origin = null;

        try {
            sand_origin = new JSONObject(json);
            JSONObject name = sand_origin.getJSONObject("name");
            Log.d("name",String.valueOf(name));

            mainName = name.getString("mainName");
//            Log.d("Mname",String.valueOf(mainName));

            image = sand_origin.getString("image");
//            Log.d("Imag",String.valueOf(image));

            placeOfOrigin = sand_origin.getString("placeOfOrigin");
//            Log.d("Poo",String.valueOf(placeOfOrigin));

            description = sand_origin.getString("description");
//            Log.d("des",String.valueOf(description));

            JSONArray alsoKnownAs_ja = name.getJSONArray("alsoKnownAs");
            addList(alsoKnownAs,alsoKnownAs_ja,alsoKnownAs_ja.length());
//            Log.d("ALso",String.valueOf(alsoKnownAs));

            JSONArray ingredients_ja = (JSONArray) sand_origin.get("ingredients");
            addList(ingredients,ingredients_ja,ingredients_ja.length());
//            Log.d("Ingre",String.valueOf(ingredients));


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

       return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static void addList(List<String> list, JSONArray list_ja, int length) {
        int i = length;
        int j = 0;
        while( j < i ){
            try {

                list.add(list_ja.getString(j));

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            j++;
        }

    }


}
