package com.example.eathit.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eathit.R;
import com.example.eathit.application.SocketApplication;
import com.example.eathit.common.loginSignup.LoginActivity;
import com.example.eathit.databinding.ActivityMain2Binding;
import com.example.eathit.utilities.Constants;
import com.facebook.login.LoginManager;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.DONUT)
public class Main2Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    Socket mSocket;
    SocketApplication socketApplication;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;


    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        socketApplication = (SocketApplication) getApplication();
        mSocket = socketApplication.getSocket();

        setSupportActionBar(binding.appBarMain2.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //button navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);
        //b???t ?????u code t??? ????y nh?? :v b??n tr??n l?? config drawer navigation v?? button navigation


        textToSpeech = new TextToSpeech(Main2Activity.this, this);
        //

        TextView menuUsername = navigationView.getHeaderView(0).findViewById(R.id.tvNameUser);
        menuUsername.setText(user.getDisplayName());
        TextView menuEmail = navigationView.getHeaderView(0).findViewById(R.id.tvGmailUser);
        menuEmail.setText(user.getEmail());

        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.imgImageUser);
        Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.ic_baseline_person_24).into(imageView);


        findViewById(R.id.imgMenuConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, ConnectActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.cvInvite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, InviteActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.cvRandom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, RandomActivity.class);
                startActivity(intent);
            }
        });
    }


    //t???o menu :v
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    //x??? l?? khi click v??o menu
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chats:
//                Toast.makeText(getApplicationContext(), "Chats", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
//                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, SettingsActivity.class));
                break;
            case R.id.action_helps:
                speak(); //n??i
                break;
            case R.id.action_QRCode:
                openScannerActivity();
                break;
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                isOnline("offline");
                auth.signOut();
                Intent intent1 = new Intent(Main2Activity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
//                textToSpeech.speak("B???n kh??ng th??? logout :)", TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //navigation...
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //speak
    private void speak() {
        Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("vi"));
        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say some thing: ");
        try {
            startActivityForResult(intent1, Constants.RESULT_SPEECH);
        } catch (Exception e) {
            Toast.makeText(Main2Activity.this, "Kh??ng th??? d??ng ch???c n??ng n??y", Toast.LENGTH_LONG).show();
            ;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> hi = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //x??? l?? text sau khi n??i
                    if (hi.get(0).contains("open") ||
                            hi.get(0).contains("chat") ||
                            hi.get(0).contains("friend") ||
                            hi.get(0).contains("nh???n tin")) {
                        Intent intent = new Intent(Main2Activity.this, ChatActivity.class);

                        startActivity(intent);
                    }
                    textToSpeech = new TextToSpeech(Main2Activity.this, Main2Activity.this::onInit);
                }
                break;
        }

        //QR Code
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // kh??ng qu??t ??c m?? QR ho???c kh??ng ???????c c???p ph??p truy c???p CAMERA
                Toast.makeText(this, "Kh??ng th???y m?? QR Code", Toast.LENGTH_SHORT).show();
            } else {
                String content = result.getContents();// n???i dung m?? QR
                String format = result.getFormatName();//?????nh d???ng c???a m?? QR
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
            textToSpeech.setLanguage(new Locale("vi"));
        } else {
            Toast.makeText(Main2Activity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void isOnline(String isOnline) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("isOnline", isOnline);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
            database.getReference().child("Users")
                    .child(user.getUid())
                    .updateChildren(hashMap);
    }


    //   //Qu??t m?? QR
    private void openScannerActivity() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Qu??t m?? QR");// h?????ng d???n
        integrator.setOrientationLocked(true);
        integrator.setTimeout(30000);//gi???i h???n th???i gian qu??t
        integrator.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnline("online");
    }

    @Override
    protected void onStop() {
        super.onStop();
//        isOnline("offline");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOnline("offline");
    }
}