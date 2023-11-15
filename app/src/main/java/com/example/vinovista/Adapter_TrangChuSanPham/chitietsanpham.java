package com.example.vinovista.Adapter_TrangChuSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.ChiTietHoaDon;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class chitietsanpham extends AppCompatActivity {
    private DatabaseReference chiTietDonRef;
    RecyclerView recyclerView;
    Adapter_SanPhamLuotMua sanPhamAdapter;
    ImageButton imageButton;
    TextView tvTenSP,tvMota,tvGiaSp;
    Button btnThem;


    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        setControl();
        setEvent();

        // Khởi tạo DatabaseReference cho chi tiết đơn
        chiTietDonRef = FirebaseDatabase.getInstance().getReference().child("ChiTietDonSanPham");
        Intent intent = getIntent();
        String tenSanPham = intent.getStringExtra("tensanpham");
        int giaGoc = intent.getIntExtra("giagoc",0);
        String moTa = intent.getStringExtra("motasanpham");
        String anh = intent.getStringExtra("anhsanpham");

        // Sử dụng dữ liệu nhận được
        tvTenSP.setText(tenSanPham);
        tvGiaSp.setText(String.valueOf(giaGoc));
        tvMota.setText(moTa);
        Picasso.get().load(anh).into(imageView);
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một object chi tiết đơn mới
                ChiTietHoaDon chiTietDon = new ChiTietHoaDon();

                // Thêm vào Firebase
                chiTietDonRef.push().setValue(chiTietDon)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(chitietsanpham.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                // Nếu muốn thêm xong có thể chuyển về màn hình khác hoặc thực hiện các công việc khác
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(chitietsanpham.this, "Thêm thất bại! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }


    private void setControl() {
        imageButton = findViewById(R.id.imgbAdd);
        recyclerView = findViewById(R.id.rcvSanPhamDanhMuc);
        imageView = findViewById(R.id.imgthemSP);
        tvMota = findViewById(R.id.tvMota);
        tvTenSP = findViewById(R.id.tvTenSpchitiet);
        tvGiaSp = findViewById(R.id.tvGiaSp);
        btnThem = findViewById(R.id.btnThem);


    }
}
