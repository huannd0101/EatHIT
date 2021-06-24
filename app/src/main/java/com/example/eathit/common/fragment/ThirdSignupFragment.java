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
            jsonObject.put("password", strings.get(3));
            jsonObject.put("role", "");
            jsonObject.put("fullname", strings.get(0));
            jsonObject.put("gender", strings.get(5));
            jsonObject.put("email", strings.get(2));
        }catch (JSONException e){
            e.printStackTrace();
        }

        String stringBody = jsonObject.toString();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://btl-spring-boot.herokuapp.com/api/accounts/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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