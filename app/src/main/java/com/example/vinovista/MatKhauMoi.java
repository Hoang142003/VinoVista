package com.example.vinovista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MatKhauMoi extends AppCompatActivity {
    Button btnthaydoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_khau_moi);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent =new Intent(this, DangNhap.class);
        btnthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnthaydoi=findViewById(R.id.btnthaydoi);
    }
}