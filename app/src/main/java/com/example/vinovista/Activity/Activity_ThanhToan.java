package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinovista.Adapter.Adapter_ChiTietHoaDon;
import com.example.vinovista.Adapter.Adapter_ThanhToan_Hoadon;
import com.example.vinovista.Adapter_TrangChuSanPham.Adapter_ChiTietDon;
import com.example.vinovista.Messaging.Notification;
import com.example.vinovista.Model.ChiTietHoaDon;
import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.Model.NhanVien;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity_ThanhToan extends AppCompatActivity {
    ImageButton btnQuayLai;
    EditText edtTenKhachHang, edtSoDienThoai,edt_voucher;
    TextView tvTongHD, tvNhanVien, tvNgayLap,tv_giamgia;
    Button btn_xuatfile, btn_Thanhtoan;
    RecyclerView rcvDanhSachSP;
    Adapter_ThanhToan_Hoadon adapter;
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("#");
    double giamgia=0;
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    double tongtien = 0;
    double tongtien_original=0;
    ArrayList<SanPham> arr_sp;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

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
                tongtien += sanPham.getGiaSale() * sanPham.getSl_dat_hang();
            } else {
                tongtien += sanPham.getGiaGoc() * sanPham.getSl_dat_hang();
            }

        }
        tongtien_original=tongtien;
        tvTongHD.setText(formatter.format(tongtien) + "đ");
        SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.SHARED_PRE, MODE_PRIVATE);
        String name_staff = sharedPreferences.getString(DangNhap.name_staff, "");
        tvNhanVien.setText(name_staff);
        tvNgayLap.setText(dateFormat.format(now));

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new Adapter_ThanhToan_Hoadon(arr_sp);
        rcvDanhSachSP.setAdapter(adapter);
        rcvDanhSachSP.setLayoutManager(new LinearLayoutManager(this));
    }
    private void checkStoragePermission() {
        // Kiểm tra xem quyền đã được cấp chưa
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        }
    }
    private void setEvent() {

        DatabaseReference reference_edit_sp = FirebaseDatabase.getInstance().getReference("SanPham");
        DatabaseReference reference_voucher=FirebaseDatabase.getInstance().getReference("Voucher");
        SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.SHARED_PRE, MODE_PRIVATE);
        String id_staff_auto = sharedPreferences.getString(DangNhap.id_staff, "");
        String name_staff = sharedPreferences.getString(DangNhap.name_staff, "");
        String token = sharedPreferences.getString(DangNhap.token, "");
        DatabaseReference reference_hoadon = FirebaseDatabase.getInstance().getReference("HoaDon");
        DatabaseReference reference_chittiet_hoadon = FirebaseDatabase.getInstance().getReference("ChiTietHoaDon");
        btn_xuatfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
                if (edtTenKhachHang.getText().toString().trim().isEmpty()) {
                    edtTenKhachHang.setFocusable(true);
                    edtTenKhachHang.setError("Vui lòng điền đủ thông tin");
                    return;
                } else if (edtSoDienThoai.getText().toString().trim().isEmpty()) {
                    edtSoDienThoai.setFocusable(true);
                    edtSoDienThoai.setError("Vui lòng điền đủ thông tin");
                    return;

                }
                XWPFDocument document = new XWPFDocument();
                // Title
                XWPFRun title = document.createParagraph().createRun();
                title.setText("HÓA ĐƠN");
                title.setFontSize(20);
                title.setBold(true);
                //Khách hàng
                XWPFRun kh = document.createParagraph().createRun();
                kh.setText("Tên khách hàng: " + edtTenKhachHang.getText().toString());

                //Số điện thoại
                XWPFRun sdt = document.createParagraph().createRun();
                sdt.setText("Số điện thoại: " + edtTenKhachHang.getText().toString());

                //Số điện thoại
                XWPFRun list_sp = document.createParagraph().createRun();
                list_sp.setText("Danh sách các sản phẩm:");
                //
                for (SanPham sanPham : arr_sp) {
                    //Sản phẩm
                    XWPFRun sp = document.createParagraph().createRun();
                    sp.setText("Tên sản phẩm: " + sanPham.getTenSanPham());
                    sp.addCarriageReturn();
                    if(sanPham.getGiaSale()!=0){
                        sp.setText("Giá: " + sanPham.getGiaSale());
                    }
                    else {
                        sp.setText("Giá: " + sanPham.getGiaGoc());
                    }

                    sp.addCarriageReturn();
                    sp.setText("Số lượng: " + sanPham.getSl_dat_hang());
                }

                //Tổng tiền
                XWPFRun tien = document.createParagraph().createRun();
                tien.setText("Tổng hóa đơn: " + tongtien + "đ");

                //Tổng tiền
                XWPFRun nv = document.createParagraph().createRun();
                nv.setText("Nhân viên hỗ trợ: " + name_staff);

                //Ngày lập hóa đơn
                XWPFRun ngay = document.createParagraph().createRun();
                ngay.setText("Ngày lập hóa đơn: " + dateFormat.format(now));

                //Thông báo
                XWPFRun tb = document.createParagraph().createRun();
                tb.setText("Hỗ trợ đổi trả trong vòng 30 ngày chưa qua sử dụng, tem còn nguyên vẹn, chưa tháo rời. Không hoàn tiền");
                tb.setBold(true);
                tb.setItalic(true);


                File file1 = new File(Environment.getExternalStorageDirectory(), "Hóa đơn.docx");
                FileOutputStream file = null;
                try {
                    file = new FileOutputStream(file1);
                    document.write(file);
                    document.close();
                    Toast.makeText(Activity_ThanhToan.this, "Xuất file thành công!!!", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(Activity_ThanhToan.this, "Không tìm thấy file", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(Activity_ThanhToan.this, "Xuất file thất bại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });
        edt_voucher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    if(edt_voucher.getText().toString().equals("")){
                        giamgia=0;
                        tv_giamgia.setVisibility(View.GONE);
                        tongtien=tongtien_original;
                        tvTongHD.setText(formatter.format(tongtien)+"đ");
                    }
                    else {
                        reference_voucher.child(edt_voucher.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    giamgia=snapshot.getValue(Double.class);
                                    tv_giamgia.setText("Đã áp dụng voucher giảm giá: "+formatter.format(giamgia)+"%");
                                    tv_giamgia.setVisibility(View.VISIBLE);
                                    tongtien=tongtien-tongtien*(giamgia/100);
                                    tvTongHD.setText(formatter.format(tongtien)+"đ");
                                }
                                else{
                                    giamgia=0;
                                    tv_giamgia.setText("Voucher không hợp lệ !!!");
                                    tv_giamgia.setVisibility(View.VISIBLE);
                                    tongtien=tongtien_original;
                                    tvTongHD.setText(formatter.format(tongtien)+"đ");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        return true;
                    }

                }
                return false;
            }
        });
        btn_Thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenKhachHang.getText().toString().trim().isEmpty()) {
                    edtTenKhachHang.setFocusable(true);
                    edtTenKhachHang.setError("Vui lòng điền đủ thông tin");
                    return;
                } else if (edtSoDienThoai.getText().toString().trim().isEmpty()) {
                    edtSoDienThoai.setFocusable(true);
                    edtSoDienThoai.setError("Vui lòng điền đủ thông tin");
                    return;

                }

                String id_hoadon = reference_hoadon.push().getKey();
                HoaDon hoaDon = new HoaDon(id_hoadon, edtTenKhachHang.getText().toString().trim(), edtSoDienThoai.getText().toString(), dateFormat.format(now), id_staff_auto, Integer.parseInt(formatter.format(tongtien)));
                reference_hoadon.child(hoaDon.getIdHoaDon()).setValue(hoaDon, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        for (SanPham sanPham : arr_sp) {
                            if (sanPham.getGiaSale() != 0) {
                                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon.getIdHoaDon(), sanPham.getIdSanPham(), sanPham.getSl_dat_hang(), sanPham.getGiaSale());
                                reference_chittiet_hoadon.child(hoaDon.getIdHoaDon()).child(sanPham.getIdSanPham()).setValue(chiTietHoaDon);
                            } else {
                                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon.getIdHoaDon(), sanPham.getIdSanPham(), sanPham.getSl_dat_hang(), sanPham.getGiaGoc());
                                reference_chittiet_hoadon.child(hoaDon.getIdHoaDon()).child(sanPham.getIdSanPham()).setValue(chiTietHoaDon);
                            }
                            sanPham.setSoLuong(sanPham.getSoLuong() - sanPham.getSl_dat_hang());
                            sanPham.setSoLuongDaBan(sanPham.getSoLuongDaBan() + sanPham.getSl_dat_hang());
                            reference_edit_sp.child(sanPham.getIdSanPham()).updateChildren(sanPham.toMap());
                        }
                        Notification.setContext(Activity_ThanhToan.this);
                        DatabaseReference reference_taikhoan=FirebaseDatabase.getInstance().getReference("NhanVien");
                        reference_taikhoan.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    NhanVien nhanVien=dataSnapshot.getValue(NhanVien.class);
                                    if(!nhanVien.getToken().equals("")&&nhanVien.getToken()!=null&&nhanVien.getIdLoaiNhanVien().equals("1")){
                                        Log.e("a","a");
                                        Notification.getSendNotificationOrderSuccessFull(nhanVien.getToken(),"Thanh toán","Có hóa đơn mới","normal");
                                    }
                                }
                                Toast.makeText(Activity_ThanhToan.this, "Thanh toán thành công!!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


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
        tv_giamgia = findViewById(R.id.tv_giamgia);
        edt_voucher = findViewById(R.id.edt_voucher);
    }
}