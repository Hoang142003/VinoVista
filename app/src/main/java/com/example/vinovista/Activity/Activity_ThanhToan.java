package com.example.vinovista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vinovista.Adapter.Adapter_ChiTietHoaDon;
import com.example.vinovista.Adapter.Adapter_ThanhToan_Hoadon;
import com.example.vinovista.Model.ChiTietHoaDon;
import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity_ThanhToan extends AppCompatActivity {
    ImageButton btnQuayLai;
    EditText edtTenKhachHang, edtSoDienThoai;
    TextView tvTongHD, tvNhanVien, tvNgayLap;
    RecyclerView rcvDanhSachSP;
    Adapter_ThanhToan_Hoadon adapter;
    ArrayList<SanPham>arr_hoadon=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
//        arr_hoadon.add(new SanPham("a","a","a","a",1,1,1,2));
//        arr_hoadon.add(new SanPham("a","a","a","a",1,1,1,2));
//        arr_hoadon.add(new SanPham("a","a","a","a",1,1,1,2));
        setControl();
        setEvent();
    }
    private void setEvent() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });

        // Nhận dữ liệu từ Intent
//        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hoaDon");

        // Gán dữ liệu lên các View
        double tongtien=0;
        for(SanPham sanPham:arr_hoadon){
            if(sanPham.getGiaSale()!=0){
                tongtien+=sanPham.getGiaSale();
            }
            else {
                tongtien+=sanPham.getGiaGoc();
            }

        }
        tvTongHD.setText(tongtien + "đ");
        SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.SHARED_PRE, MODE_PRIVATE);
        String id_staff_auto = sharedPreferences.getString(DangNhap.id_staff, "");
        String name_staff = sharedPreferences.getString(DangNhap.name_staff, "");
        tvNhanVien.setText(name_staff);
        Date now=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tvNgayLap.setText(dateFormat.format(now));

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new Adapter_ThanhToan_Hoadon(arr_hoadon);
        rcvDanhSachSP.setAdapter(adapter);

    }

    private void setControl() {
        btnQuayLai = findViewById(R.id.btnQuayLai_TTHD);
        edtTenKhachHang = findViewById(R.id.edtTenKhachHang_TTHD);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai_TTHD);
        tvTongHD = findViewById(R.id.edtTongHD_TTHD);
        tvNhanVien = findViewById(R.id.tvNhanVien_TTHD);
        tvNgayLap = findViewById(R.id.tvNgayLap_TTHD);
        rcvDanhSachSP = findViewById(R.id.rcvDanhSachSP_TTHD);
        rcvDanhSachSP.setLayoutManager(new LinearLayoutManager(this));
    }
}