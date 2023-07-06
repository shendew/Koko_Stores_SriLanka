package com.kingdew.kokostoressrilanka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kingdew.kokostoressrilanka.Adapters.StoreAdaper;
import com.kingdew.kokostoressrilanka.Models.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class HomeActivity extends AppCompatActivity {
    JSONArray jsonArray;
    ArrayList<String> cities,categories;
    Spinner location_spinner,cate_spinner;
    RecyclerView StoresRView;
    ArrayList<Store> stores,backup;
    StoreAdaper adaper;
    EditText search_edit;
    LinearLayout catLay;
    ImageView search_img,filter_img,more;
    RelativeLayout searchLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        location_spinner=findViewById(R.id.location_spinner);
        cate_spinner=findViewById(R.id.cate_spinner);
        StoresRView=findViewById(R.id.StoresRView);
        search_edit=findViewById(R.id.search_edit);
        search_img=findViewById(R.id.search_img);
        filter_img=findViewById(R.id.filter);
        catLay=findViewById(R.id.catLay);
        searchLay=findViewById(R.id.searchLay);
        more=findViewById(R.id.more);

        cities=new ArrayList<>();
        categories=new ArrayList<>();
        stores=new ArrayList<>();
        backup=new ArrayList<>();
        StoresRView.setHasFixedSize(true);
        StoresRView.setLayoutManager(new GridLayoutManager(this,2));

        adaper=new StoreAdaper(HomeActivity.this,stores);
        StoresRView.setAdapter(adaper);

        String data="https://sheets.googleapis.com/v4/spreadsheets/1N6FJZhN6m8QWN4og-SKDlCFmELr6Jlaze3IIcfgaMok/values/list?key=AIzaSyDUQhU5HNyF5WsZHe_0BjPbQxc2uVk5Qng";

        getDistrics();
        getCategories();
        getStores();
        filter_img.setOnClickListener(view -> {
            if (catLay.getVisibility()==View.VISIBLE){
                catLay.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.filter);
                params.topMargin=10;
                params.leftMargin=30;
                params.rightMargin=30;
                searchLay.setLayoutParams(params);
            }else{
                catLay.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.catLay);
                params.topMargin=10;
                params.leftMargin=20;
                params.rightMargin=20;
                searchLay.setLayoutParams(params);
            }
        });

        cate_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (cate_spinner.getSelectedItem().equals("All")){
                    if (stores.size() != backup.size()){
                        stores.clear();
                        stores=backup;
                    }
                    location_spinner.setSelection(0);
                }else{
                    location_spinner.setSelection(0);
                    stores.clear();
                    ArrayList<Store> categoryM=new ArrayList<>();
                    for (int j = 0; j < backup.size(); j++) {
                        //Toast.makeText(HomeActivity.this, ""+cate_spinner.getSelectedItem().toString()+"_____"+backup.get(j).getShopCategory(), Toast.LENGTH_SHORT).show();
                            if (cate_spinner.getSelectedItem().toString().equals(backup.get(j).getShopCategory())) {
                                categoryM.add(backup.get(j));
                            }

                    }

                    stores.addAll(categoryM);
                    adaper.UpdateStores(stores);
                    adaper.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (location_spinner.getSelectedItem().equals("All")){
                    //getStores();
                }else{
                    stores.clear();
                    stores.addAll(backup);
                    ArrayList<Store> locationM=new ArrayList<>();
                    for (int j = 0; j < stores.size(); j++) {
                        String[] locs=stores.get(j).getShopLocation().split(",");
                        for (String loc : locs) {

                            if (location_spinner.getSelectedItem().toString().equals(loc)) {
                                locationM.add(stores.get(j));
                            }
                        }
                    }
                    stores.clear();
                    stores.addAll(locationM);
                    adaper.UpdateStores(stores);
                    adaper.notifyDataSetChanged();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        search_edit.addTextChangedListener(new TextWatcher() {
            ArrayList<Store> modified=new ArrayList<>();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                modified.clear();
                String charSequence=editable.toString().toLowerCase();
                for (int j = 0; j < stores.size(); j++) {
                    if (stores.get(j).getShopName().toLowerCase().contains(charSequence) || stores.get(j).getShopAddress().toLowerCase().contains(charSequence) || stores.get(j).getShopCategory().toLowerCase().contains(charSequence) || stores.get(j).getShopLocation().toLowerCase().contains(charSequence) || stores.get(j).getShopTags().toLowerCase().contains(charSequence)){
                        modified.add(stores.get(j));
                    }
                }
                adaper=new StoreAdaper(HomeActivity.this,modified);
                StoresRView.setAdapter(adaper);
                adaper.notifyDataSetChanged();
            }
        });
        more.setOnClickListener(view -> {
            startActivity(new Intent(this,AboutActivity.class));
        });



    }

    private void getStores(){
        if (!stores.isEmpty()){
            stores.clear();
        }
        if (!backup.isEmpty()){
            backup.clear();
        }
        String cities_link="https://sheets.googleapis.com/v4/spreadsheets/1N6FJZhN6m8QWN4og-SKDlCFmELr6Jlaze3IIcfgaMok/values/list?key=AIzaSyDUQhU5HNyF5WsZHe_0BjPbQxc2uVk5Qng";
        RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, cities_link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("values");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                IntStream.range(1, jsonArray.length())
                        .forEach(i -> {
                            try {
                                JSONArray json = jsonArray.getJSONArray(i);
                                stores.add(new Store(json.getString(0),json.getString(1),json.getString(2),json.getString(3),json.getString(4),json.getString(5),json.getString(6),json.getString(7),json.getString(8),json.getString(9)));
                                backup.add(new Store(json.getString(0),json.getString(1),json.getString(2),json.getString(3),json.getString(4),json.getString(5),json.getString(6),json.getString(7),json.getString(8),json.getString(9)));

                            } catch (Exception e) {}
                        });


                        adaper.UpdateStores(stores);
                        adaper.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }



    private void getDistrics(){
        String cities_link="https://sheets.googleapis.com/v4/spreadsheets/1N6FJZhN6m8QWN4og-SKDlCFmELr6Jlaze3IIcfgaMok/values/cities?key=AIzaSyDUQhU5HNyF5WsZHe_0BjPbQxc2uVk5Qng";
        RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, cities_link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("values");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                IntStream.range(1, jsonArray.length())
                        .forEach(i -> {
                            try {
                                JSONArray json = jsonArray.getJSONArray(i);
                                cities.add(json.getString(2));
                            } catch (Exception e) {}
                        });



                ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_spinner_dropdown_item,cities);
                location_spinner.setAdapter(karant_adapter);
                location_spinner.setSelection(0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void getCategories(){
        String cities_link="https://sheets.googleapis.com/v4/spreadsheets/1N6FJZhN6m8QWN4og-SKDlCFmELr6Jlaze3IIcfgaMok/values/categories?key=AIzaSyDUQhU5HNyF5WsZHe_0BjPbQxc2uVk5Qng";
        RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, cities_link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("values");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                IntStream.range(1, jsonArray.length())
                        .forEach(i -> {
                            try {
                                JSONArray json = jsonArray.getJSONArray(i);
                                categories.add(json.getString(1));
                            } catch (Exception e) {}
                        });



                ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_spinner_dropdown_item,categories);
                cate_spinner.setAdapter(karant_adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}