package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vinovista.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Xac_Thuc_OTP extends AppCompatActivity {
    ImageView ivtieptheo;
    EditText edtmaOTP;
    Button btnguilaimaOTP;
    ProgressBar pbxacthuc;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc_otp);
        setControl();
        setEvent();
    }

    private void setEvent() {
        verificationId = getIntent().getStringExtra("verificationId");
        String sdt = getIntent().getStringExtra("sdt");
        Intent intent = new Intent(Xac_Thuc_OTP.this, MatKhauMoi.class);
        ivtieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, edtmaOTP.getText().toString());
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pbxacthuc.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            //   startActivity(intent);
                            Toast.makeText(Xac_Thuc_OTP.this, "Xác thực thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Xac_Thuc_OTP.this, "Mã OTP vừa nhập không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnguilaimaOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbxacthuc.setVisibility(View.VISIBLE);
                btnguilaimaOTP.setVisibility(View.GONE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84" + sdt,
                        60,
                        TimeUnit.SECONDS,
                        Xac_Thuc_OTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                pbxacthuc.setVisibility(View.VISIBLE);
                                btnguilaimaOTP.setVisibility(View.GONE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                pbxacthuc.setVisibility(View.VISIBLE);
                                btnguilaimaOTP.setVisibility(View.GONE);
                                Toast.makeText(Xac_Thuc_OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationIdresent, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                pbxacthuc.setVisibility(View.GONE);
                                btnguilaimaOTP.setVisibility(View.VISIBLE);
                                verificationId = verificationIdresent;

                            }
                        });
            }
        });

    }

    private void setControl() {
        ivtieptheo = findViewById(R.id.ivtieptheo);
        pbxacthuc = findViewById(R.id.pbxacthuc);
        btnguilaimaOTP = findViewById(R.id.btnguilaimaOTP);
        edtmaOTP=findViewById(R.id.edtmaOTP);
    }
}