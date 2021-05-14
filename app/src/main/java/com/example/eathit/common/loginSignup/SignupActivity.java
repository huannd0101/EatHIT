package com.example.eathit.common.loginSignup;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.example.eathit.R;
import com.example.eathit.common.events.FirstFragmentSignupListener;
import com.example.eathit.common.fragment.FirstSignupFragment;
import com.example.eathit.common.fragment.SecondSignupFragment;
import com.example.eathit.databinding.ActivitySignupBinding;
import com.example.eathit.utilities.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity  {

    ActivitySignupBinding binding;
    public ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getFragment(FirstSignupFragment.newInstance());





    }

    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment,fragment).addToBackStack(FirstSignupFragment.TAG).commit();
    }

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }
}