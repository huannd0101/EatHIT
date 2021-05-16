package com.example.eathit.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.eathit.common.loginSignup.SignupActivity;
import com.example.eathit.databinding.FragmentThirthSignupBinding;
import com.example.eathit.utilities.Constants;

import java.util.ArrayList;
import java.util.Objects;


public class ThirdSignupFragment extends Fragment {
    public static final String TAG = FirstSignupFragment.class.getName();
    FragmentThirthSignupBinding binding;
    private ArrayList<String> strings;
    private SignupActivity signupActivity;

//    private FirebaseAuth mAuth;
//    FirebaseDatabase database;
//    ProgressDialog progressDialog;


    public static ThirdSignupFragment newInstance(ArrayList<String> strings) {
        ThirdSignupFragment fragment = new ThirdSignupFragment();
        Bundle args = new Bundle();

        args.putStringArrayList(Constants.stringDataFromSecondFragment, strings);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThirthSignupBinding.inflate(inflater, container, false);
        //animation
        initAnimate();

        //firebase
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//
//        //progressDialog
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Creating account");
//        progressDialog.setMessage("We are creating your account");

        //
        signupActivity = (SignupActivity) getActivity();

        //back to second signup fragment
        binding.btnBackSecondSignup.setOnClickListener(v -> {
            if(getFragmentManager() != null){
                getFragmentManager().popBackStack();
            }
        });

        //data from two fragment
        if(getArguments() != null) {
            strings = getArguments().getStringArrayList(Constants.stringDataFromSecondFragment);
        }

        //btn signup
        binding.btnSignUp.setOnClickListener(v -> {
            clickToSignUp();
        });















        return binding.getRoot();
    }

    private void clickToSignUp() {
        String phoneNumber = Objects.requireNonNull(binding.edtPhoneNumber.getText()).toString().trim();
        if(phoneNumber.isEmpty()){
            binding.tilPhoneNumber.setError("You have not entered phone number");
            return;
        }else {
            strings.add(phoneNumber);
            binding.tilPhoneNumber.setError(null);
        }


//        progressDialog.show();
//        mAuth.createUserWithEmailAndPassword
//                (binding.tiedtEmail.getText().toString(), binding.tiedtPass.getText().toString())
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        //tắt dialog sau khi tạo thành công hoặc thất bại
//                        progressDialog.dismiss();
//                        ///
//                        if(task.isSuccessful()){
//
//                            Users user = new Users(binding.tiedtUser.getText().toString(), binding.tiedtEmail.getText().toString(), binding.tiedtPass.getText().toString());
//                            String id = task.getResult().getUser().getUid();
//                            user.setUseeId(id);
//                            database.getReference().child("Users").child(id).setValue(user);
//
//
//                            Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



        signupActivity.setStrings(strings);
    }


    private void initAnimate() {
//        binding.cpp.setTranslationY(800);
        binding.tilPhoneNumber.setTranslationY(800);
        binding.btnSignUp.setTranslationY(800);
        binding.btnLogin.setTranslationY(800);

//        binding.cpp.setAlpha(0);
        binding.tilPhoneNumber.setAlpha(0);
        binding.btnSignUp.setAlpha(0);
        binding.btnLogin.setAlpha(0);

//        binding.cpp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(600).start();
        binding.tilPhoneNumber.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(800).start();
        binding.btnSignUp.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1100).start();
        binding.btnLogin.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1300).start();

        binding.titleCreateAccount.setTranslationX(-800);
        binding.titleCreateAccount.setAlpha(0);
        binding.titleCreateAccount.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();

        binding.tvProcess.setTranslationX(800);
        binding.tvProcess.setAlpha(0);
        binding.tvProcess.animate().translationX(0).alpha(1).setDuration(1800).setStartDelay(500).start();
    }

}