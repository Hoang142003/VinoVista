package com.example.vinovista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DangNhap extends AppCompatActivity {
    TextView tvquenmatkhau;
    EditText edtsdt, edtmk;
    Button btnquanly,btnbanhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = new Intent(this, QuenMatKhau.class);
        btnquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnbanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    private void setControl() {
        tvquenmatkhau = findViewById(R.id.tvquenmatkhau);
        edtsdt = findViewById(R.id.edtsdt);
        edtmk = findViewById(R.id.edtmk);
        btnquanly = findViewById(R.id.btnquanly);
        btnbanhang = findViewById(R.id.btnbanhang);
    }
}