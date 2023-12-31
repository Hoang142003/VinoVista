package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vinovista.Activity.DangNhap;
import com.example.vinovista.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatKhauMoi extends AppCompatActivity {
    Button btnthaydoi;
    EditText edt_mk, edk_nhaplaimk;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_khau_moi);
        setControl();
        setEvent();
    }

    private void setEvent() {
        String sdt = getIntent().getStringExtra("sdt");
        btnthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if (edt_mk.getText().toString().equals(edk_nhaplaimk.getText().toString())) {
                    if (edt_mk.getText().toString().trim().length() >= 8) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NhanVien");
                        reference.child(sdt).child("matKhau").setValue(edt_mk.getText().toString().trim(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                pb.setVisibility(View.GONE);
                                if (error == null) {
                                    Toast.makeText(MatKhauMoi.this, "Sửa mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MatKhauMoi.this, DangNhap.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MatKhauMoi.this, "Sửa mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(MatKhauMoi.this, "Mật khẩu phải ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MatKhauMoi.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl() {
        btnthaydoi = findViewById(R.id.btnthaydoi);
        edt_mk = findViewById(R.id.edt_mk);
        edk_nhaplaimk = findViewById(R.id.edk_nhaplaimk);
        pb = findViewById(R.id.pb);
    }
}