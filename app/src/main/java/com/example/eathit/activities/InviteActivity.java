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
                Toast.makeText(InviteActivity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InviteActivity.this, "Lỗi cập nhật dữ liệu", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
        personAdapter.setClickPersonInvite(new iOnClickPersonInvite() {
            @Override
            public void iClick(Person person) {
                Toast.makeText(InviteActivity.this, "Bạn đang chọn " + person.getFullName() + " ", Toast.LENGTH_SHORT).show();

            }
        });




        FirebaseMessaging.getInstance().subscribeToTopic("all");
        btnSendInvitation = findViewById(R.id.buttonSendInvitation);
        btnSendInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String tittleNotification = nameOfMe + " bạn ơiii";
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
                        //    FullName =

                        list.add(new Person(FullName, LinkAvatar));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InviteActivity.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
//                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(InviteActivity.this, linearLayoutManager.getOrientation());
//                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(personAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(InviteActivity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InviteActivity.this, "Lỗi cập nhật dữ liệu", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);

    }
    public String getFoodToInvite()
    {
        EditText food = findViewById(R.id.editTextOption);
        return food.getText().toString();
    }

    public String randomMessage(String food)
    {
        List<String> messageInvite = new ArrayList<>();
        messageInvite.add("Bạn có muốn đi ăn " + food + " không nào? Nhanh nhanh nhá");
        messageInvite.add("Có người bao bạn đi chén " + food + " đây này, chả nhẽ lại không đi???");
        messageInvite.add("Làm tí " + food + " không tình yêu ơiiiii");
        messageInvite.add("Có thực mới vực được đạo, " + food + " là sự lựa chọn không tồi chứ? Đi thôi");
        messageInvite.add("Đang đói, muốn chén " + food + " nên nhắn bạn đấy thần tài ạ, đi luôn thôi nào");
        messageInvite.add("It can be a bug but I LOVE YOU, would you like some " + food);
        Random rand = new Random();
        int randomNum = rand.nextInt((messageInvite.size() - 0) + 1) + 0;
        return messageInvite.get(randomNum);
    }
}