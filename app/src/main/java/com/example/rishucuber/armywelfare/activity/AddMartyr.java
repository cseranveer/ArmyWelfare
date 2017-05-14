package com.example.rishucuber.armywelfare.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rishucuber.armywelfare.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.rishucuber.armywelfare.R.id.bt_addmartyr;

public class AddMartyr extends AppCompatActivity {
    private EditText mMartyrName, mMartyrVillage, mMartyrDate, mMartyrInfo;
    private CircleImageView mProfileImage;
    private Button mAddMartyr;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private CircleImageView imageView;
    private static int PICK_IMAGE_REQUEST = 1;
    ProgressDialog pd;
    Uri filepath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://armywelfare-32865.appspot.com/martyrImages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_martyr);
        init();
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");

        mAddMartyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addMartyrToFireBase();
                uploadImage();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*"
                );
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

    }

    private void init() {
        mMartyrName = (EditText) findViewById(R.id.martyr_name);
        mMartyrVillage = (EditText) findViewById(R.id.martyr_village);
        mMartyrDate = (EditText) findViewById(R.id.martyred_on);
        mMartyrInfo = (EditText) findViewById(R.id.martyr_info);
        mProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        mAddMartyr = (Button) findViewById(bt_addmartyr);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imageView = (CircleImageView) findViewById(R.id.profile_image);
    }

    private void addMartyrToFireBase() {

        DatabaseReference mDatabaseMartyrs;
        mDatabaseMartyrs = mDatabase.child("martyrs").push();
        mDatabaseMartyrs.child("martyr_name").setValue(mMartyrName.getText().toString().trim());
        mDatabaseMartyrs.child("martyr_village").setValue(mMartyrVillage.getText().toString().trim());
        mDatabaseMartyrs.child("martyr_martyred").setValue(mMartyrDate.getText().toString().trim());
        mDatabaseMartyrs.child("martyr_info").setValue(mMartyrInfo.getText().toString().trim());
        progressBar.setVisibility(View.GONE);
    }

    private void uploadImage() {
        StorageReference childRef = storageRef.child(mMartyrName.getText().toString());

        //uploading the image
        UploadTask uploadTask = childRef.putFile(filepath);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(AddMartyr.this, "Upload successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddMartyr.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);

                //Setting image to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
