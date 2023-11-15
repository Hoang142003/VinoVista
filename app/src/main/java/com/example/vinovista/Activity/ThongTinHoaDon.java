package com.example.vinovista.Activity;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Adapter.Adapter_ChiTietHoaDon;

public class ThongTinHoaDon extends AppCompatActivity {
    ImageButton btnQuayLai;
    EditText edtTenKhachHang, edtSoDienThoai;
    TextView tvTongHD, tvNhanVien, tvNgayLap;
    RecyclerView rcvDanhSachSP;
    Adapter_ChiTietHoaDon adapter;
}
