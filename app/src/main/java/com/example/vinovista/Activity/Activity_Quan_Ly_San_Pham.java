package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_Quan_Ly_San_Pham extends AppCompatActivity {
    private Spinner spnDanhMuc;
    private DanhMucSpinerAdapter danhMucSpinerAdapter;
    List<DanhMuc> danhMucList = new ArrayList<>();
    private RecyclerView rcvSanPham;
    private List<SanPham> sanPhamRuouList;
    private SanPhamAdapter sanPhamAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button btnSanPhamMoi;
    EditText edtTimKiem;
    RelativeLayout root_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        setControl();
    }

    private void setControl() {
        spnDanhMuc = findViewById(R.id.spnDanhMuc);
        btnSanPhamMoi = findViewById(R.id.btnSanPhamMoi);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        rcvSanPham = findViewById(R.id.rcvSanPham);
        LoadDanhMuc();
        danhMucSpinerAdapter = new DanhMucSpinerAdapter(Activity_Quan_Ly_San_Pham.this,danhMucList);
        spnDanhMuc.setAdapter(danhMucSpinerAdapter);

    }
    private void LoadSanPham() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SanPham");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhamRuouList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPhamRuou = dataSnapshot.getValue(SanPham.class);
                    sanPhamRuouList.add(sanPhamRuou);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void LoadDanhMuc() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhMuc danhMuc = dataSnapshot.getValue(DanhMuc.class);
                    danhMucList.add(danhMuc);
                }
                danhMucSpinerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}