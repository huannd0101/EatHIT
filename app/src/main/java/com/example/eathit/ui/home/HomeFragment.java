package com.example.eathit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eathit.R;
import com.example.eathit.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.tvViewAll.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Huân Đẹp trai", Toast.LENGTH_SHORT).show();
        });

        Picasso.get().load(currentUser.getPhotoUrl()).placeholder(R.drawable.ic_baseline_person_24).into(binding.imgUser);
        binding.tvUserName.setText("Welcome " + currentUser.getDisplayName() + "!");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}