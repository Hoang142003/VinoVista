package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vinovista.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class QuenMatKhau extends AppCompatActivity {
    Button btnNhanMaOTP;
    EditText edtsdt;
    ProgressBar pbquenmatkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = new Intent(this, Xac_Thuc_OTP.class);
        btnNhanMaOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbquenmatkhau.setVisibility(View.VISIBLE);
                btnNhanMaOTP.setVisibility(View.GONE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84"+edtsdt.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        QuenMatKhau.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                pbquenmatkhau.setVisibility(View.GONE);
                                btnNhanMaOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                pbquenmatkhau.setVisibility(View.GONE);
                                btnNhanMaOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(QuenMatKhau.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                pbquenmatkhau.setVisibility(View.GONE);
                                btnNhanMaOTP.setVisibility(View.VISIBLE);
                                intent.putExtra("sdt",edtsdt.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);

                            }
                        });
                ///Code xác thuc bi loi nhung khong gach
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//            PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                    pbquenmatkhau.setVisibility(View.GONE);
//                    pbquenmatkhau.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onVerificationFailed(@NonNull FirebaseException e) {
//                    pbquenmatkhau.setVisibility(View.GONE);
//                    pbquenmatkhau.setVisibility(View.GONE);
//                    btnNhanMaOTP.setVisibility(View.VISIBLE);
//                    Toast.makeText(QuenMatKhau.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                    pbquenmatkhau.setVisibility(View.GONE);
//                    btnNhanMaOTP.setVisibility(View.VISIBLE);
//                    intent.putExtra("sdt", edtsdt.getText().toString());
//                    intent.putExtra("verificationId", verificationId);
//                    startActivity(intent);
//                }
//            };
//            PhoneAuthOptions options = new PhoneAuthOptions().newBuilder(firebaseAuth)
//                    .setPhoneNumber(edtsdt.getText().toString())
//                    .setTimeout(60L, TimeUnit.SECONDS)
//                    .setActivity(QuenMatKhau.this)
//                    .setCallbacks(callbacks)
//                    .build();
//                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });

    }

    private void setControl() {
        btnNhanMaOTP = findViewById(R.id.btnNhanMaOTP);
        edtsdt = findViewById(R.id.edtsdt);
        pbquenmatkhau = findViewById(R.id.pbquenmatkhau);
    }
}