package com.example.eathit.common.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.common.loginSignup.LoginActivity;
import com.example.eathit.common.loginSignup.SignupActivity;
import com.example.eathit.databinding.FragmentThirthSignupBinding;
import com.example.eathit.utilities.Constants;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

public class ThirdSignupFragment extends Fragment {
    public static final String TAG = FirstSignupFragment.class.getName();
    FragmentThirthSignupBinding binding;
    private ArrayList<String> strings;
    private SignupActivity signupActivity;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;

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
        auth = FirebaseAuth.getInstance();


        //progressDialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");

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


        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
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

        RequestQueue requestQueue= Volley.newRequestQueue(requireContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", strings.get(1));
            jsonObject.put("password", strings.get(2));
            jsonObject.put("role", "");
            jsonObject.put("fullname", strings.get(0));
            jsonObject.put("gender", strings.get(4));
            jsonObject.put("email", strings.get(1));
        }catch (JSONException e){
            e.printStackTrace();
        }

        String stringBody = jsonObject.toString();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://btl-spring-boot.herokuapp.com/api/accounts/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        auth.createUserWithEmailAndPassword(strings.get(1), strings.get(2))
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                        Toast.makeText(signupActivity, "Thành công ở firebase", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnCanceledListener(new OnCanceledListener() {
                                    @Override
                                    public void onCanceled() {

                                    }
                                });

                        startActivity(new Intent(getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(signupActivity, "Create account failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset = utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                if(stringBody == null){
                    return null;
                }
                try {
                    return stringBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);


        signupActivity.setStrings(strings);


    }

    /*
    binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bật dialog
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword
                        (binding.tiedtEmail.getText().toString(), binding.tiedtPass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //tắt dialog sau khi tạo thành công hoặc thất bại
                                progressDialog.dismiss();
                                ///
                                if(task.isSuccessful()){

                                    Users user = new Users(binding.tiedtUser.getText().toString(), binding.tiedtEmail.getText().toString(), binding.tiedtPass.getText().toString());
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);


                                    Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });//Xong phần đăng ký tài khoản
    * */

















    private void initAnimate() {
        binding.tilPhoneNumber.setTranslationY(800);
        binding.btnSignUp.setTranslationY(800);
        binding.btnLogin.setTranslationY(800);

        binding.tilPhoneNumber.setAlpha(0);
        binding.btnSignUp.setAlpha(0);
        binding.btnLogin.setAlpha(0);

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