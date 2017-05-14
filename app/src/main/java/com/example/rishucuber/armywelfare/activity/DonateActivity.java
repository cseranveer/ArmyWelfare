package com.example.rishucuber.armywelfare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishucuber.armywelfare.R;
import com.example.rishucuber.armywelfare.initactivities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class DonateActivity extends AppCompatActivity {


    PayPalConfiguration m_configuration;
    // the id is the link to the paypal account, we have to create an app and get its id
    String m_paypalClientId = "AR8n_5PN497y-6JYORJgqgKlMAvg2gllNTFpHsqQRR_rk71akYHVDORyh4MAHWuHbFrgGU9ta7oKagJa";
    Intent m_service;
    int m_paypalRequestCode = 999; // or any number you want
    private Button btpay, btBack;
    private EditText etAmount;
    private int mAmount;
    private TextView tvMartyrName, tvSuccess;
    private String mName;
    private ImageView ivLogout;
    private DatabaseReference mDatabase;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);


        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // sandbox for test, production for real
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration); // configuration above
        startService(m_service); // paypal service, listening to calls to paypal app
        btpay = (Button) findViewById(R.id.pay);
        etAmount = (EditText) findViewById(R.id.amount);
        tvMartyrName = (TextView) findViewById(R.id.martyr_name_donation);
        Bundle extras = getIntent().getExtras();
        mName = extras.getString("name");
        tvMartyrName.setText(mName);
        btpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay(v);
            }
        });
        btBack = (Button) findViewById(R.id.back);
        tvSuccess = (TextView) findViewById(R.id.success);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        ivLogout = (ImageView) findViewById(R.id.iv_logout);
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DonateActivity.this, LoginActivity.class));
            }
        });

    }

    void pay(View view) {
        mAmount = Integer.valueOf(etAmount.getText().toString().trim());
        PayPalPayment payment = new PayPalPayment(new BigDecimal(mAmount), "USD", "Test payment with Paypal",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class); // it's not paypalpayment, it's paymentactivity !
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, m_paypalRequestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == m_paypalRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                // we have to confirm that the payment worked to avoid fraud
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null) {
                    String state = confirmation.getProofOfPayment().getState();

                    if (state.equals("approved")) {// if the payment worked, the state equals approved
                        btBack.setVisibility(View.VISIBLE);
                        tvSuccess.setVisibility(View.VISIBLE);
                        etAmount.setText("");
                        etAmount.setVisibility(View.GONE);
                        btpay.setVisibility(View.GONE);
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else
                    Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void addDonationToFirebase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mCurrentUser = user.getEmail();
        Log.d("Current User email", mCurrentUser);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabaseMartyrs;
        mDatabaseMartyrs = mDatabase.push();
        mDatabaseMartyrs.child("donations.doner_email").setValue(mCurrentUser);


    }
}