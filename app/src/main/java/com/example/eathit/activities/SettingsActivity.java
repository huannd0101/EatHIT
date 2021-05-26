package com.example.eathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.eathit.databinding.ActivitySettingsBinding;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.tvBackToMain.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, Main2Activity.class));
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == binding.langEL.getId()){
                    Toast.makeText(SettingsActivity.this, "Đã chuyển ngôn ngữ tiếng anh", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SettingsActivity.this, "Đã chuyển ngôn ngữ tiếng việt", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}