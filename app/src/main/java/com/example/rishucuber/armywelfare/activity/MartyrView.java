package com.example.rishucuber.armywelfare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rishucuber.armywelfare.R;
import com.example.rishucuber.armywelfare.initactivities.LoginActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MartyrView extends AppCompatActivity {


    TextView mTvMartyrName, mTvMartyrVillage, mTvMartyrDate, mTvMartyrInfo, mMartyredOn;
    LinearLayout mLlayout;

    private String mName, mVillage, mDate, mDonation, mInfo;
    private ProgressBar progressBar;
    private View mViewLine;
    private CircleImageView mProfileImage;
    private Button btDonate;
    private ImageView ivLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martyr_view);
        init();
        Bundle extras = getIntent().getExtras();
        mName = extras.getString("name");
        mVillage = extras.getString("village");
        mDate = extras.getString("date");
        mDonation = extras.getString("userId");
        mInfo = extras.getString("info");
        setProfileImage();
        setValuesToTextViews();
        btDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MartyrView.this,DonateActivity.class);
                intent.putExtra("name",mName);
                startActivity(intent);

            }
        });
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MartyrView.this, LoginActivity.class));
            }
        });

    }

    private void setValuesToTextViews() {


        mTvMartyrName.setText(mName);
        mTvMartyrVillage.setText(mVillage);
        mTvMartyrDate.setText(mDate);
        mTvMartyrInfo.setText(mInfo);
        btDonate.setText("Donate To "+mName);


    }

    private void init() {
        mTvMartyrName = (TextView) findViewById(R.id.martyr_name);
        mTvMartyrVillage = (TextView) findViewById(R.id.martyr_location);
        mTvMartyrDate = (TextView) findViewById(R.id.martyr_martyred);
        mTvMartyrInfo = (TextView) findViewById(R.id.martyr_info);
        mViewLine = findViewById(R.id.view_line);
        mMartyredOn = (TextView) findViewById(R.id.tv_martyredOn);
        mProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        btDonate = (Button) findViewById(R.id.donate);
        ivLogout = (ImageView) findViewById(R.id.iv_logout);




    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    //getting image from fire base storage
    private void setProfileImage() {
        // Reference to an image file in Firebase Storage
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference("martyrImages/"+mName);

// Load the image using Glide
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(mProfileImage);


    }
}
