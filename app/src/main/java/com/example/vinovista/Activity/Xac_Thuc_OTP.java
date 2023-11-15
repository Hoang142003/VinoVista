package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
    ImageView ivtieptheo, iv_back;
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
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivtieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbxacthuc.setVisibility(View.VISIBLE);
                btnguilaimaOTP.setVisibility(View.GONE);
                ivtieptheo.setVisibility(View.GONE);
                iv_back.setVisibility(View.GONE);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, edtmaOTP.getText().toString());
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pbxacthuc.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                //   startActivity(intent);
                                pbxacthuc.setVisibility(View.GONE);
                                btnguilaimaOTP.setVisibility(View.VISIBLE);
                                ivtieptheo.setVisibility(View.VISIBLE);
                                iv_back.setVisibility(View.VISIBLE);
                                intent.putExtra("sdt",sdt);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Xac_Thuc_OTP.this, "Mã OTP vừa nhập không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            pbxacthuc.setVisibility(View.GONE);
                            btnguilaimaOTP.setVisibility(View.VISIBLE);
                            ivtieptheo.setVisibility(View.VISIBLE);
                            iv_back.setVisibility(View.VISIBLE);
                            Toast.makeText(Xac_Thuc_OTP.this, "Xác thực không thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        btnguilaimaOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vô hiệu hóa button sau khi được nhấn
                btnguilaimaOTP.setEnabled(false);
                // Tạo một ColorStateList với màu mới
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.gray));

// Đặt màu mới cho nút button
                btnguilaimaOTP.setBackgroundTintList(colorStateList);

                // Đặt thời gian chờ (ví dụ: 10 giây)
                long countdownMillis = 60000;

                // Tạo một CountDownTimer để đếm ngược thời gian
                CountDownTimer countDownTimer = new CountDownTimer(countdownMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Cập nhật văn bản của button với thời gian còn lại
                        String timeRemaining = "Gửi lại sau (" + millisUntilFinished / 1000 + " giây)";
                        btnguilaimaOTP.setText(timeRemaining);
                    }

                    @Override
                    public void onFinish() {
                        // Khi đếm ngược kết thúc, kích hoạt lại button và cập nhật văn bản
                        btnguilaimaOTP.setEnabled(true);
                        btnguilaimaOTP.setText("Gửi lại OTP");
                        btnguilaimaOTP.setBackgroundTintList(null);
                    }
                };

                // Bắt đầu đếm ngược
                countDownTimer.start();
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
        edtmaOTP = findViewById(R.id.edtmaOTP);
        iv_back = findViewById(R.id.iv_back);
    }
}