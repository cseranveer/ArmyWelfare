package com.example.rishucuber.armywelfare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rishucuber.armywelfare.R;
import com.example.rishucuber.armywelfare.adapter.RecyclerAdapter;
import com.example.rishucuber.armywelfare.initactivities.LoginActivity;
import com.example.rishucuber.armywelfare.model.MartyrInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MartyrFeedFragment extends Fragment {

    ArrayList<MartyrInformation> data;
    private RecyclerView recyclerview;
    private RecyclerAdapter adapter;
    private String mMartyrName, mMartyrVillage, mMartyrDate, mMartyrInfo;
    private TextView tvMartyrName, tvMartyrVillage, tvMartyrDate, tvMartyrInfo;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_martyr_feed,null);

        tvMartyrName = (TextView) v.findViewById(R.id.martyr_name);
        tvMartyrVillage = (TextView) v.findViewById(R.id.martyr_village);
        tvMartyrDate = (TextView) v.findViewById(R.id.martyr_martyred);
        tvMartyrInfo = (TextView) v.findViewById(R.id.martyr_info);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);




        getDataFromServer();
        data = new ArrayList<>();
        recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        /* Creating an object of Recycler View Adapter*/


        return v;
    }

    private List<MartyrInformation> getData() {
        MartyrInformation job = new MartyrInformation(mMartyrName, mMartyrVillage, mMartyrDate, "5000", mMartyrInfo);
        data.add(job);
        return data;
    }

    public void init() {

    }

    public void getDataFromServer() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("martyrs");
        ref.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                mMartyrName = String.valueOf(dataSnapshot.child("martyr_name").getValue());
                mMartyrVillage = String.valueOf(dataSnapshot.child("martyr_village").getValue());
                mMartyrDate = String.valueOf(dataSnapshot.child("martyr_martyred").getValue());
                mMartyrInfo = String.valueOf(dataSnapshot.child("martyr_info").getValue());
                Log.d("info", mMartyrInfo);
                adapter = new RecyclerAdapter(getActivity(), getData());
        /*set adapter to the recyclerView*/
                recyclerview.setAdapter(adapter);
                recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue().toString());


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, "i am alive", Toast.LENGTH_SHORT).show();
                System.out.println(dataSnapshot.getValue().toString());

                mMartyrName = String.valueOf(dataSnapshot.child("martyrs/martyr_name").getValue());
                mMartyrVillage = String.valueOf(dataSnapshot.child("martyr_village").getValue());
                mMartyrDate = String.valueOf(dataSnapshot.child("martyr_date").getValue());
                mMartyrInfo = String.valueOf(dataSnapshot.child("martyr_info").getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });*/

    }


}
