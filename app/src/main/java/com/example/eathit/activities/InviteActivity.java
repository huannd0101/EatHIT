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
import com.example.eathit.adapter.iOnClickPersonInvite;
import com.example.eathit.modules.Person;
import com.example.eathit.notification.FcmNotificationsSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InviteActivity extends AppCompatActivity {
    private static final String url = "https://btl-spring-boot.herokuapp.com/api/accounts/";
    private static RecyclerView recyclerView;
    private static PersonAdapter personAdapter;
    private static List<Person> list;
    Button btnSendInvitation;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        Objects.requireNonNull(getSupportActionBar()).hide();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String nameOfMe = user.getDisplayName();

        //callPeopleToInvite();


        recyclerView = findViewById(R.id.recyclerViewInvitePeople);
        list = new ArrayList<>();
        personAdapter = new PersonAdapter(list, InviteActivity.this);

        RequestQueue requestQueue = Volley.newRequestQueue(InviteActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String listBook = response;

                try {
                    JSONArray jsonArray = new JSONArray(listBook);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String FullName = jsonObject.getString("fullname");
                        String LinkAvatar = jsonObject.getString("linkAvt");
                        list.add(new Person(FullName, LinkAvatar));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InviteActivity.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(personAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(InviteActivity.this, "C???p nh???t d??? li???u th??nh c??ng", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InviteActivity.this, "L???i c???p nh???t d??? li???u", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
        personAdapter.setClickPersonInvite(new iOnClickPersonInvite() {
            @Override
            public void iClick(Person person) {
                Toast.makeText(InviteActivity.this, "B???n ??ang ch???n " + person.getFullName() + " ", Toast.LENGTH_SHORT).show();

            }
        });




        FirebaseMessaging.getInstance().subscribeToTopic("all");
        btnSendInvitation = findViewById(R.id.buttonSendInvitation);
        btnSendInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String tittleNotification;
                    if(getPlaceToInvite().matches(""))
                    {
                        tittleNotification = nameOfMe + " Aloo b???n ??i";
                    }else{
                        tittleNotification = nameOfMe+   " b???n ??iii, ra " + getPlaceToInvite() + " ngay ??i";
                    }

                    String messageNotification = randomMessage(getFoodToInvite());
                    FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/all", tittleNotification, messageNotification, getApplicationContext(), InviteActivity.this);
                    fcmNotificationsSender.SendNotifications();
                    Toast.makeText(InviteActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void callPeopleToInvite()
    {
        recyclerView = findViewById(R.id.recyclerViewInvitePeople);
        list = new ArrayList<>();
        personAdapter = new PersonAdapter(list, InviteActivity.this);

        RequestQueue requestQueue = Volley.newRequestQueue(InviteActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String listBook = response;

                try {
                    JSONArray jsonArray = new JSONArray(listBook);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String FullName = jsonObject.getString("fullname");
                        String LinkAvatar = jsonObject.getString("linkAvt");
                        list.add(new Person(FullName, LinkAvatar));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InviteActivity.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(personAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(InviteActivity.this, "C???p nh???t d??? li???u th??nh c??ng", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InviteActivity.this, "L???i c???p nh???t d??? li???u", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);

    }
    public String getFoodToInvite()
    {
        EditText food = findViewById(R.id.editTextOption);
        return food.getText().toString();
    }
// C??m rang d??a b??
    public String getPlaceToInvite()
    {
        EditText place = findViewById(R.id.editTextPlace);
        return  place.getText().toString();
    }
    public String randomMessage(String food)
    {
        List<String> messageInvite = new ArrayList<>();
        messageInvite.add("B???n c?? mu???n ??i ??n " + food + " kh??ng n??o? Nhanh nhanh nh??");
        messageInvite.add("C?? ng?????i bao b???n ??i ch??n " + food + " ????y n??y, ch??? nh??? l???i kh??ng ??i???");
        messageInvite.add("G???i b???n l?? Sliver, v?? l??c n??o t??i c??ng mu???n ???????c B???c ????i m??n " + food + " ahihi");
        messageInvite.add("L??m t?? " + food + " kh??ng t??nh y??u ??iiiii");
        messageInvite.add("G???i b???n l?? Sliver, v?? l??c n??o t??i c??ng mu???n ???????c B???c ????i m??n " + food + " ahihi");
        messageInvite.add("C?? th???c m???i v???c ???????c ?????o, " + food + " l?? s??? l???a ch???n kh??ng t???i ch???? ??i th??i");
        messageInvite.add("??ang ????i, mu???n ch??n " + food + " n??n nh???n b???n ?????y th???n t??i ???, ??i lu??n th??i n??o");
        messageInvite.add("?????c g?? ???????c m???i c??u b???a " + food + " nh??? ahihi");
        messageInvite.add("G???i b???n l?? Sliver, v?? l??c n??o t??i c??ng mu???n ???????c B???c ????i m??n " + food + " ahihi");
        Random rand = new Random();
        int randomNum = rand.nextInt((messageInvite.size() - 0) + 1) + 0;
        return messageInvite.get(randomNum);
    }
}