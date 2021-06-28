package com.example.eathit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eathit.R;
import com.example.eathit.modules.Person;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    List<Person> list;
    Context context;
    iOnClickPersonInvite clickPersonInvite;
    public void setClickPersonInvite(iOnClickPersonInvite iOnClickPersonInvite)
    {
        this.clickPersonInvite = iOnClickPersonInvite;
    }
    public PersonAdapter(List<Person> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_peoples, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String s = list.get(position).getFullName();
        holder.textViewFullNamePerSon.setText(s);
        Glide.with(context).load(list.get(position).getLinkAvatar()).into(holder.imageViewAvatarPerson);
        holder.itemPersonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPersonInvite.iClick(list.get(position));
            }
        });
        holder.imageViewAvatarPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPersonInvite.iClick(list.get(position));
            }
        });
        holder.textViewFullNamePerSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPersonInvite.iClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatarPerson;
        TextView textViewFullNamePerSon;
        LinearLayout itemPersonClick;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageViewAvatarPerson = itemView.findViewById(R.id.avatar_people);
            textViewFullNamePerSon = itemView.findViewById(R.id.full_name_people);
            itemPersonClick = itemView.findViewById(R.id.itemPeopleClick);
        }
    }



}
