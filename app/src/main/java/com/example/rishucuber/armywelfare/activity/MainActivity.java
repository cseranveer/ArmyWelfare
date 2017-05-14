package com.example.rishucuber.armywelfare.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishucuber.armywelfare.R;
import com.example.rishucuber.armywelfare.fragment.ContactUsFragment;
import com.example.rishucuber.armywelfare.fragment.DonationHistoryFragment;
import com.example.rishucuber.armywelfare.fragment.MartyrFeedFragment;
import com.example.rishucuber.armywelfare.fragment.UserProfileFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView ivProfileImage;
    private TextView tvProfileName, tvMartyerFeed, tvDonationHistory, tvUserProfile, tvContactUs, tvHelp, tvLogout;
    private DrawerLayout mDrawer;
    private NavigationView nView;
    private ImageView mHam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        MartyrFeedFragment martyrFeedFragment = new MartyrFeedFragment();
        martyrFeedFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, martyrFeedFragment).commit();
    }

    private void init() {
        tvProfileName = (TextView) findViewById(R.id.tv_profile_name);
        tvMartyerFeed = (TextView) findViewById(R.id.item_feed);
        tvDonationHistory = (TextView) findViewById(R.id.item_history);
        tvUserProfile = (TextView) findViewById(R.id.item_profile);
        tvContactUs = (TextView) findViewById(R.id.item_contact_us);
        tvHelp = (TextView) findViewById(R.id.item_help);
        tvLogout = (TextView) findViewById(R.id.item_logout);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mHam = (ImageView) findViewById(R.id.ham);
        tvMartyerFeed.setOnClickListener(this);
        tvDonationHistory.setOnClickListener(this);
        tvUserProfile.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        mHam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_feed:
                MartyrFeedFragment martyrFeedFragment = new MartyrFeedFragment();
                martyrFeedFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_frame, martyrFeedFragment).commit();
                mDrawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.item_history:
                DonationHistoryFragment donationHistoryFragment = new DonationHistoryFragment();
                donationHistoryFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_frame, donationHistoryFragment).commit();
                mDrawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.item_profile:
                UserProfileFragment userProfileFragment = new UserProfileFragment();
                userProfileFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_frame, userProfileFragment).commit();
                mDrawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.item_contact_us:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                contactUsFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_frame, contactUsFragment).commit();
                mDrawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.item_logout:

                break;
            case R.id.ham:
                mDrawer.openDrawer(Gravity.LEFT);

                break;
            default:
                break;


        }

    }
}

