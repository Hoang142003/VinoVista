package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button btn_xuatfile, btn_Thanhtoan;
    RecyclerView rcvDanhSachSP;
    Adapter_ThanhToan_Hoadon adapter;
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    int tongtien = 0;
    ArrayList<SanPham> arr_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        arr_sp = (ArrayList<SanPham>) getIntent().getSerializableExtra("hoa_don");
        setControl();
        Initialization();
        setEvent();
    }

    private void Initialization() {
        // Nhận dữ liệu từ Intent

        // Gán dữ liệu lên các View
        for (SanPham sanPham : arr_sp) {
            if (sanPham.getGiaSale() != 0) {
                tongtien += sanPham.getGiaSale()*sanPham.getSoLuongDaBan();
            } else {
                tongtien += sanPham.getGiaGoc()*sanPham.getSoLuongDaBan();
            }

        }
        tvTongHD.setText(tongtien + "đ");
        SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.SHARED_PRE, MODE_PRIVATE);
        String name_staff = sharedPreferences.getString(DangNhap.name_staff, "");
        tvNhanVien.setText(name_staff);
        tvNgayLap.setText(dateFormat.format(now));

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new Adapter_ThanhToan_Hoadon(arr_sp);
        rcvDanhSachSP.setAdapter(adapter);
        rcvDanhSachSP.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setEvent() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });
        btn_Thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.SHARED_PRE, MODE_PRIVATE);
                String id_staff_auto = sharedPreferences.getString(DangNhap.id_staff, "");
                DatabaseReference reference_hoadon = FirebaseDatabase.getInstance().getReference("HoaDon");
                DatabaseReference reference_chittiet_hoadon = FirebaseDatabase.getInstance().getReference("ChiTietHoaDon");
                String id_hoadon = reference_hoadon.push().getKey();
                HoaDon hoaDon = new HoaDon(id_hoadon, edtTenKhachHang.getText().toString().trim(), edtSoDienThoai.getText().toString(), dateFormat.format(now), id_staff_auto, tongtien);
                reference_hoadon.child(hoaDon.getIdHoaDon()).setValue(hoaDon, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        for (SanPham sanPham : arr_sp) {
                            if (sanPham.getGiaSale() != 0) {
                                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon.getIdHoaDon(), sanPham.getIdSanPham(), sanPham.getSoLuongDaBan(), sanPham.getGiaSale());
                                reference_chittiet_hoadon.child(hoaDon.getIdHoaDon()).child(sanPham.getIdSanPham()).setValue(chiTietHoaDon);
                            } else {
                                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon.getIdHoaDon(), sanPham.getIdSanPham(), sanPham.getSoLuongDaBan(), sanPham.getGiaGoc());
                                reference_chittiet_hoadon.child(hoaDon.getIdHoaDon()).child(sanPham.getIdSanPham()).setValue(chiTietHoaDon);
                            }

                        }

                    }
                });
            }
        });

    }

    private void setControl() {
        btnQuayLai = findViewById(R.id.btnQuayLai_TTHD);
        edtTenKhachHang = findViewById(R.id.edtTenKhachHang_TTHD);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai_TTHD);
        tvTongHD = findViewById(R.id.edtTongHD_TTHD);
        tvNhanVien = findViewById(R.id.tvNhanVien_TTHD);
        tvNgayLap = findViewById(R.id.tvNgayLap_TTHD);
        rcvDanhSachSP = findViewById(R.id.rcvDanhSachSP_TTHD);
        btn_xuatfile = findViewById(R.id.btn_xuatfile);
        btn_Thanhtoan = findViewById(R.id.btn_Thanhtoan);
    }
}