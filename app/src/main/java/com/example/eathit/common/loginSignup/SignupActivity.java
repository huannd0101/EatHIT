package com.example.eathit.common.loginSignup;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.eathit.R;
import com.example.eathit.common.fragment.FirstSignupFragment;
import com.example.eathit.common.fragment.ThirdSignupFragment;
import com.example.eathit.databinding.ActivitySignupBinding;

import java.util.ArrayList;


public class SignupActivity extends AppCompatActivity  {

    ActivitySignupBinding binding;
    public ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getFragment(FirstSignupFragment.newInstance());

        if(strings.size() != 0){
            Toast.makeText(this, strings.toString() + "1", Toast.LENGTH_SHORT).show();
        }


    }

    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment,fragment).addToBackStack(FirstSignupFragment.TAG).commit();
    }

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }
}