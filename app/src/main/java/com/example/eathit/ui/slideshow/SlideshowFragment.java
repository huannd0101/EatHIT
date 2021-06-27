package com.example.eathit.ui.slideshow;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eathit.R;
import com.example.eathit.databinding.FragmentSlideshowBinding;


import com.example.eathit.ui.slideshow.Posts.Posts1;
import com.example.eathit.ui.slideshow.Posts.PostsAdapter;

import com.example.eathit.ui.slideshow.API.APIService;
import com.example.eathit.ui.slideshow.DiaLog.ImageFilePath;
import com.example.eathit.ui.slideshow.Posts.Posts1;
import com.example.eathit.ui.slideshow.Posts.PostsAdapter;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

public class SlideshowFragment extends Fragment {

    private static final int SELECT_PICTURE = 1;
    private com.example.eathit.ui.news.NewViewModel newViewModel;
    private FragmentSlideshowBinding binding;

    String result, resultAccount, resultCmt;
    String url = "https://btl-spring-boot.herokuapp.com/api/";
    int idPostsGetAc;

    List<Posts1> listPost;
    PostsAdapter postsAdapter;

    ImageView img_choose_img_to_Post;

    String realPath = null;

    String idAccLogining = "ELEO1624502092692";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        newViewModel = new ViewModelProvider(this).get(com.example.eathit.ui.news.NewViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        binding.imgStatusOnOff.bringToFront();

        //id
        //show All Posts
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listPost = getAllPost();
        Collections.reverse(listPost);


        //Create Posts
        binding.tvCreatePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Create a new Posts", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_create_posts);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtributes = window.getAttributes();
                windowAtributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAtributes);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                ImageView img_avt_createP = dialog.findViewById(R.id.img_avt);
                TextView tv_fullname_createP = dialog.findViewById(R.id.tv_Fullname_createP);
                EditText edt_content_createP = dialog.findViewById(R.id.edt_content_createP);
                LinearLayout add_Image_createP = dialog.findViewById(R.id.add_Image_CreateP);
                Button btn_createP = dialog.findViewById(R.id.btn_createP);
                LinearLayout progress_Postsing = dialog.findViewById(R.id.progress_postsing);
                TextView tv_loadding = dialog.findViewById(R.id.tv_Loading);
                img_choose_img_to_Post = dialog.findViewById(R.id.img_choose_img_to_post);

                edt_content_createP.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        btn_createP.setBackgroundColor(getResources().getColor(R.color.dis_Like));
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        btn_createP.setBackgroundColor(getResources().getColor(R.color.like));
                        if (edt_content_createP.getText().toString().trim().compareTo("") == 0) {
                            btn_createP.setBackgroundColor(getResources().getColor(R.color.dis_Like));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (edt_content_createP.getText().toString().trim().compareTo("") == 0) {
                            btn_createP.setBackgroundColor(getResources().getColor(R.color.dis_Like));
                        }
                    }
                });

                btn_createP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String contentP = edt_content_createP.getText().toString().trim();
                        if (contentP.compareTo("") == 0)
                            Toast.makeText(getContext(), "Hãy điền nội dung cho bài viết bạn nhé! Mãi yêu nè kkk", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getContext(), contentP, Toast.LENGTH_SHORT).show();
                            progress_Postsing.setVisibility(View.VISIBLE);
                            Animation loadingAnim = new AlphaAnimation(1, 0);
                            loadingAnim.setDuration(500);
                            loadingAnim.setRepeatCount(Animation.INFINITE);
                            loadingAnim.setRepeatMode(Animation.REVERSE);
                            tv_loadding.startAnimation(loadingAnim);
                            if (realPath != null) {
                                API_Create_Posts(realPath);
                            }else {
                                API_Create_Posts_No_Image();
                            }

                        }

                    }

                    private void API_Create_Posts_No_Image(){
                        String contentP = edt_content_createP.getText().toString().trim();
                        RequestBody requestContentP = RequestBody.create(MediaType.parse("multipart/form-data"), contentP);

                        APIService.apiService.create_only_content(idAccLogining, requestContentP).enqueue(new Callback<Posts1>() {
                            @Override
                            public void onResponse(Call<Posts1> call, retrofit2.Response<Posts1> response) {
                                Toast.makeText(getContext(), "possts oke", Toast.LENGTH_SHORT).show();
                                listPost = getAllPost();
                                Collections.reverse(listPost);
                                Log.d("ps", "onResponse: " + response);

                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Posts1> call, Throwable t) {
                                Toast.makeText(getContext(), "posst fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    private void API_Create_Posts(String path) {
                        String contentP = edt_content_createP.getText().toString().trim();
                        RequestBody requestContentP = RequestBody.create(MediaType.parse("multipart/form-data"), contentP);

                        File file = new File(path);
                        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData("img", file.getName(), requestBodyAvt);

                        APIService.apiService.createPostsWhitePhoto(idAccLogining, requestContentP, multipartBodyAvt).enqueue(new Callback<Posts1>() {
                            @Override
                            public void onResponse(Call<Posts1> call, retrofit2.Response<Posts1> response) {
                                Toast.makeText(getContext(), "possts oke", Toast.LENGTH_SHORT).show();
                                listPost = getAllPost();
                                Collections.reverse(listPost);
                                Log.d("ps", "onResponse: " + response);

                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Posts1> call, Throwable t) {
                                Toast.makeText(getContext(), "posst fail", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

//                FirebaseAuth.getInstance().getCurrentUser();

                ///choose image
                add_Image_createP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Chọn ảnh nào bạn ơi: " + FirebaseAuth.getInstance().getCurrentUser(), Toast.LENGTH_SHORT).show();
                        requestPermission();
                    }
                });

                dialog.show();
            }
        });


        return binding.getRoot();
    }


    private void requestPermission() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            realPath = ImageFilePath.getPath(getContext(), uri);
//                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

            Log.i("dm", "onActivityResult: file path : " + realPath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                img_choose_img_to_Post.setVisibility(View.VISIBLE);
                img_choose_img_to_Post.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }


    private List<Posts1> getAllPost() {
        List<Posts1> listPost1 = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "posts", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = response;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idPosts = jsonObject.getInt("idPosts");
                        idPostsGetAc = idPosts;
                        String content = jsonObject.getString("content");
                        String imgLink = jsonObject.getString("imgLink");
                        String createAt = jsonObject.getString("creaetAt");
                        String updateAt = jsonObject.getString("updateAt");
                        int likes = jsonObject.getInt("likes");

                        JSONObject account = jsonObject.getJSONObject("account");
                        String id = account.getString("id");
                        String username = account.getString("username");
                        String role = account.getString("role");
                        String fullname = account.getString("fullname");
                        String gender = account.getString("gender");
                        boolean status = account.getBoolean("status");
                        String linkAvt = account.getString("linkAvt");
                        String email = account.getString("email");
                        String creaetAt1 = account.getString("creaetAt");
                        String updateAt1 = account.getString("updateAt");
                        Friend account1 = new Friend(id, username, role, fullname, gender, status, linkAvt, email, creaetAt1, updateAt1);

                        Posts1 posts1 = new Posts1(idPosts, content, imgLink, createAt, updateAt, likes, account1);
                        listPost1.add(posts1);

                    }
                    LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_left_to_right);
                    binding.revPosts.setLayoutAnimation(layoutAnimationController);

                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    Collections.reverse(listPost1);
                    postsAdapter = new PostsAdapter(listPost1, getContext());
                    binding.revPosts.setLayoutManager(linearLayoutManager1);
                    binding.revPosts.setAdapter(postsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result = "error";
                Toast.makeText(getContext(), "call api with Volley fail", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        return listPost1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}