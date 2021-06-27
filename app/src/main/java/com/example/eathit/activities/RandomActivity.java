package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.R;
import com.example.eathit.adapter.FoodAdapter;
import com.example.eathit.adapter.PersonAdapter;
import com.example.eathit.modules.Food;
import com.example.eathit.modules.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    private static final String url = "https://btl-spring-boot.herokuapp.com/api/food/2";
    private static RecyclerView recyclerView;
    private static FoodAdapter foodAdapter;
    private static List<Food> list = new ArrayList<>();
    private static List<Food> listRandom = new ArrayList<>();;
    Button btnRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.recyclerViewRandomFood);
        foodAdapter = new FoodAdapter(listRandom, RandomActivity.this);
        btnRandom = findViewById(R.id.buttonRandom);

        RequestQueue requestQueue = Volley.newRequestQueue(RandomActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String listBook = response;
                        try {
                            JSONArray jsonArray = new JSONArray(listBook);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String NameFood = jsonObject.getString("name");
                                int PriceFood = jsonObject.getInt("oldPrice");
                                list.add(new Food(NameFood, PriceFood));
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RandomActivity.this, RecyclerView.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(foodAdapter);
                            }
                            //        // Chuyển và random
                            Random rand = new Random();
                            int randomNum = rand.nextInt((list.size() - 0) + 1) + 0;
                            listRandom.add(new Food(list.get(randomNum).getNameFood(), list.get(randomNum).getPriceFood()));
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                Toast.makeText(RandomActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RandomActivity.this, "Lỗi cập nhật dữ liệu", Toast.LENGTH_LONG).show();
            }
        });
////        // Chuyển và random
//        Random rand = new Random();
//        int randomNum = rand.nextInt((list.size() - 0) + 1) + 0;
//        listRandom.add(new Food(list.get(randomNum).getNameFood(), list.get(randomNum).getPriceFood()));
        requestQueue.add(stringRequest);
    }
}