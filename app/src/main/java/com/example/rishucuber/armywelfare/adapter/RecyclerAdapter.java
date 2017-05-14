package com.example.rishucuber.armywelfare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rishucuber.armywelfare.R;
import com.example.rishucuber.armywelfare.activity.MartyrView;
import com.example.rishucuber.armywelfare.model.MartyrInformation;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by rishucuber on 21/4/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    List<MartyrInformation> data = Collections.emptyList();
    Context context;
    private CircleImageView martyrImage;


    public RecyclerAdapter(Context context, List<MartyrInformation> data) {
        this.data = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MartyrInformation current = data.get(position);
        holder.name.setText(current.getmName());
        holder.village.setText(current.getmVillage());
        holder.date.setText(current.getmDate());
        holder.donation.setText(current.getmDonation());
        holder.info.setText(current.getmInfo());

        setProfileImage(current.getmName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, village, date, donation, info;

        public MyViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.martyr_name);
            village = (TextView) itemView.findViewById(R.id.martyr_location);
            date = (TextView) itemView.findViewById(R.id.martyr_martyred);
            donation = (TextView) itemView.findViewById(R.id.martyr_donation);
            info = (TextView) itemView.findViewById(R.id.martyr_information);
            martyrImage = (CircleImageView) itemView.findViewById(R.id.martyr_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MartyrView.class);
                    intent.putExtra("name", name.getText());
                    intent.putExtra("village", village.getText());
                    intent.putExtra("date", date.getText());
                    intent.putExtra("info", info.getText());
                    context.startActivity(intent);

                }
            });


        }
    }

    //getting image from fire base storage
    private void setProfileImage(String mMartyrName) {
        // Reference to an image file in Firebase Storage
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference("martyrImages/" + mMartyrName);

// Load the image using Glide
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(martyrImage);


    }

}
