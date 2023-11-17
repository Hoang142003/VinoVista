package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinovista.Adapter_TrangChuSanPham.trangchusanpham;
import com.example.vinovista.Model.LoaiNhanVien;
import com.example.vinovista.Model.NhanVien;
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
    ProgressBar pb;
    public static String SHARED_PRE = "shared_pre";
    public static String id_staff = "id_staff";
    public static String name_staff = "name_staff";
    public static String chuc_vu_auto = "chuc_vu_auto";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        AutoLogin();
        setControl();
        setEvent();

    }
    private void setEvent() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NhanVien");
        DatabaseReference reference_loainv = FirebaseDatabase.getInstance().getReference("LoaiNhanVien");
        btnquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnbanhang.setVisibility(View.GONE);
                btnquanly.setVisibility(View.GONE);
                btnquanly.setVisibility(View.GONE);
                tvquenmatkhau.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                if (edtsdt.getText().toString().trim().isEmpty()) {
                    edtsdt.setFocusable(true);
                    edtsdt.setError("Vui lòng điền đủ thông tin");
                    btnbanhang.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    tvquenmatkhau.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                    return;
                } else if (edtmk.getText().toString().trim().isEmpty()) {
                    edtmk.setFocusable(true);
                    edtmk.setError("Vui lòng điền đủ thông tin");
                    btnbanhang.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    tvquenmatkhau.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                    return;
                } else {
                    reference_loainv.orderByChild("tenLoaiNhanVien").equalTo("Quản lý").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                LoaiNhanVien loaiNhanVien = dataSnapshot.getValue(LoaiNhanVien.class);
                                reference.child(edtsdt.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                                            if (nhanVien.getIdLoaiNhanVien().equals(loaiNhanVien.getIdLoaiNhanVien())) {
                                                if (nhanVien.getMatKhau().equals(edtmk.getText().toString())) {
                                                    btnbanhang.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    tvquenmatkhau.setVisibility(View.VISIBLE);
                                                    pb.setVisibility(View.GONE);
                                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString(id_staff, nhanVien.getSoDienThoai());
                                                    editor.putString(name_staff, nhanVien.getHoTen());
                                                    editor.putString(chuc_vu_auto, loaiNhanVien.getTenLoaiNhanVien());
                                                    editor.apply();
                                                    Intent intent = new Intent(DangNhap.this, Activity_Menu.class);
                                                    startActivity(intent);
                                                } else {
                                                    btnbanhang.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    tvquenmatkhau.setVisibility(View.VISIBLE);
                                                    pb.setVisibility(View.GONE);
                                                    Toast.makeText(DangNhap.this, "Sai mật khẩu!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                btnbanhang.setVisibility(View.VISIBLE);
                                                btnquanly.setVisibility(View.VISIBLE);
                                                btnquanly.setVisibility(View.VISIBLE);
                                                tvquenmatkhau.setVisibility(View.VISIBLE);
                                                pb.setVisibility(View.GONE);
                                                Toast.makeText(DangNhap.this, "Sai số điện thoại!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            btnbanhang.setVisibility(View.VISIBLE);
                                            btnquanly.setVisibility(View.VISIBLE);
                                            btnquanly.setVisibility(View.VISIBLE);
                                            tvquenmatkhau.setVisibility(View.VISIBLE);
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(DangNhap.this, "Sai số điện thoại!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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
                btnbanhang.setVisibility(View.GONE);
                btnquanly.setVisibility(View.GONE);
                btnquanly.setVisibility(View.GONE);
                tvquenmatkhau.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                if (edtsdt.getText().toString().trim().isEmpty()) {
                    edtsdt.setFocusable(true);
                    edtsdt.setError("Vui lòng điền đủ thông tin");
                    btnbanhang.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    tvquenmatkhau.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                    return;
                } else if (edtmk.getText().toString().trim().isEmpty()) {
                    edtmk.setFocusable(true);
                    edtmk.setError("Vui lòng điền đủ thông tin");
                    btnbanhang.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    btnquanly.setVisibility(View.VISIBLE);
                    tvquenmatkhau.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                    return;
                } else {
                    reference_loainv.orderByChild("tenLoaiNhanVien").equalTo("Nhân viên").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                LoaiNhanVien loaiNhanVien = dataSnapshot.getValue(LoaiNhanVien.class);
                                reference.child(edtsdt.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                                            if (nhanVien.getIdLoaiNhanVien().equals(loaiNhanVien.getIdLoaiNhanVien())) {
                                                if (nhanVien.getMatKhau().equals(edtmk.getText().toString())) {
                                                    btnbanhang.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    tvquenmatkhau.setVisibility(View.VISIBLE);
                                                    pb.setVisibility(View.GONE);
                                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString(id_staff, nhanVien.getSoDienThoai());
                                                    editor.putString(name_staff, nhanVien.getHoTen());
                                                    editor.putString(chuc_vu_auto, loaiNhanVien.getTenLoaiNhanVien());
                                                    editor.apply();
                                                    Intent intent = new Intent(DangNhap.this, trangchusanpham.class);
                                                    startActivity(intent);
                                                } else {
                                                    btnbanhang.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    btnquanly.setVisibility(View.VISIBLE);
                                                    tvquenmatkhau.setVisibility(View.VISIBLE);
                                                    pb.setVisibility(View.GONE);
                                                    Toast.makeText(DangNhap.this, "Sai mật khẩu!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                btnbanhang.setVisibility(View.VISIBLE);
                                                btnquanly.setVisibility(View.VISIBLE);
                                                btnquanly.setVisibility(View.VISIBLE);
                                                tvquenmatkhau.setVisibility(View.VISIBLE);
                                                pb.setVisibility(View.GONE);
                                                Toast.makeText(DangNhap.this, "Sai số điện thoại!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            btnbanhang.setVisibility(View.VISIBLE);
                                            btnquanly.setVisibility(View.VISIBLE);
                                            btnquanly.setVisibility(View.VISIBLE);
                                            tvquenmatkhau.setVisibility(View.VISIBLE);
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(DangNhap.this, "Sai số điện thoại!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

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
    private void AutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        String id_staff_auto = sharedPreferences.getString("id_staff", "");
        String chuc_vu_auto = sharedPreferences.getString("chuc_vu_auto", "");
        switch (chuc_vu_auto.toLowerCase()) {
            case "quản lý":
                Intent intent_laocong = new Intent(DangNhap.this, Activity_Menu.class);
                intent_laocong.putExtra("id_staff", id_staff_auto);
                startActivity(intent_laocong);
                finish();
                break;
            case "nhân viên":
                Intent intent_letan = new Intent(DangNhap.this, trangchusanpham.class);
                intent_letan.putExtra("id_staff", id_staff_auto);
                startActivity(intent_letan);
                finish();
                break;
            default:
                break;
        }
    }
    private void setControl() {
        tvquenmatkhau = findViewById(R.id.tvquenmatkhau);
        edtsdt = findViewById(R.id.edtsdt);
        edtmk = findViewById(R.id.edtmk);
        btnquanly = findViewById(R.id.btnquanly);
        btnbanhang = findViewById(R.id.btnbanhang);
        pb = findViewById(R.id.pb);
    }
}