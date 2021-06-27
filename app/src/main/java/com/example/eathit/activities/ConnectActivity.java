package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.R;
import com.example.eathit.adapter.PersonAdapter;
import com.example.eathit.modules.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConnectActivity extends AppCompatActivity {
    private static final String url = "https://btl-spring-boot.herokuapp.com/api/accounts/";
    private static RecyclerView recyclerView;
    private static PersonAdapter personAdapter;
    private static List<Person> list = new ArrayList<>();
    private static List<Person> listConnect = new ArrayList<>();;
    EditText edtGetCodeConnect;
    Button btnConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Objects.requireNonNull(getSupportActionBar()).hide();
        
        recyclerView = findViewById(R.id.recycleViewListPeople);

        personAdapter = new PersonAdapter(listConnect, ConnectActivity.this);
        edtGetCodeConnect = findViewById(R.id.editTextEnterCode);
        btnConnect = findViewById(R.id.buttonConnect);
        RequestQueue requestQueue = Volley.newRequestQueue(ConnectActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String listBook = response;
                btnConnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONArray jsonArray = new JSONArray(listBook);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String FullName = jsonObject.getString("fullname");
                                String LinkAvatar = jsonObject.getString("linkAvt");
                                String idUser = jsonObject.getString("id");
                                list.add(new Person(FullName, LinkAvatar));
                                if(idUser.compareToIgnoreCase(edtGetCodeConnect.getText().toString()) == 0)
                                {
                                    listConnect.add(new Person(list.get(i).getFullName(), list.get(i).getLinkAvatar()));
                                }
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConnectActivity.this, RecyclerView.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ConnectActivity.this, linearLayoutManager.getOrientation());
                                //recyclerView.addItemDecoration(dividerItemDecoration);
                                recyclerView.setAdapter(personAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Toast.makeText(ConnectActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConnectActivity.this, "Lỗi cập nhật dữ liệu", Toast.LENGTH_LONG).show();
            }
        });
//        for(int i = 0; i < list.size();i++)
//        {
//            if(list.get(i).getId().compareToIgnoreCase(edtGetCodeConnect.getText().toString()) == 0)
//            {
//                listConnect.add(new Person(list.get(i).getFullName(), list.get(i).getLinkAvatar()));
//            }
//        }
        requestQueue.add(stringRequest);
    }
}