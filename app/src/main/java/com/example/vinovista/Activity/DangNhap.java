package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhap extends AppCompatActivity {
    TextView tvquenmatkhau;
    EditText edtsdt, edtmk;
    Button btnquanly, btnbanhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        setControl();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("a");
        reference.setValue("a");
        setEvent();
    }

    private void setEvent() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NhanVien");
        btnquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtsdt.getText().toString().trim().isEmpty()) {
                    edtsdt.setFocusable(true);
                    edtsdt.setError("Vui lòng điền đủ thông tin");
                    return;
                } else if (edtmk.getText().toString().trim().isEmpty()) {
                    edtsdt.setFocusable(true);
                    edtsdt.setError("Vui lòng điền đủ thông tin");
                    return;
                } else {
                    reference.child(edtsdt.getText().toString().trim()).orderByChild("").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

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
                Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
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