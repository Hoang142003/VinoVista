package com.example.vinovista.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Adapter.Adapter_ChiTietHoaDon;
import com.example.vinovista.Model.ChiTietHoaDon;
import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongTinHoaDon extends AppCompatActivity {
    ImageButton btnQuayLai;
    EditText edtTenKhachHang, edtSoDienThoai;
    TextView tvTongHD, tvNhanVien, tvNgayLap;
    RecyclerView rcvDanhSachSP;
    Adapter_ChiTietHoaDon adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtinhoadon);
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
        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hoaDon");

        // Gán dữ liệu lên các View
        edtTenKhachHang.setText(hoaDon.getTenKhachHang());
        edtSoDienThoai.setText(hoaDon.getSoDienThoai());
        tvTongHD.setText(hoaDon.getTongHoaDon() + "đ");
        // Lấy mã Nhân viên từ hóa đơn
        String maNhanVien = hoaDon.getNhanVien();

        // Khởi tạo tham chiếu đến cơ sở dữ liệu Firebase
        DatabaseReference nhanVienRef = FirebaseDatabase.getInstance().getReference("NhanVien");

        // Thực hiện truy vấn để lấy tên Nhân viên
        nhanVienRef.child(maNhanVien).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy tên Nhân viên từ dataSnapshot
                    String tenNhanVien = dataSnapshot.child("hoTen").getValue(String.class);

                    // Hiển thị tên Nhân viên lên TextView tvNhanVien
                    tvNhanVien.setText(tenNhanVien + " - " + hoaDon.getNhanVien());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

        tvNgayLap.setText((hoaDon.getNgayMua()));

        // Giả sử bạn có phương thức này để lấy danh sách chi tiết hóa đơn dựa trên id_hoadon
        List<ChiTietHoaDon> listChiTiet = getDataFromDatabase(hoaDon.getIdHoaDon());

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new Adapter_ChiTietHoaDon(listChiTiet);
        rcvDanhSachSP.setAdapter(adapter);

    }

    private List<ChiTietHoaDon> getDataFromDatabase(String hoaDonId) {
        List<ChiTietHoaDon> listChiTiet = new ArrayList<>();

        // Khởi tạo tham chiếu đến cơ sở dữ liệu
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ChiTietHoaDon");

        // Thực hiện truy vấn dựa trên id_hoadon
        databaseReference.child(hoaDonId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chiTietSnapshot : dataSnapshot.getChildren()) {
                    ChiTietHoaDon chiTiet = chiTietSnapshot.getValue(ChiTietHoaDon.class);
                    listChiTiet.add(chiTiet);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi ở đây
                Log.w("FirebaseError", "loadPost:onCancelled", databaseError.toException());
            }
        });

        return listChiTiet;
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
