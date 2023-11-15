package com.example.vinovista.Adapter_TrangChuSanPham;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.R;

public class trangchusanpham extends AppCompatActivity {
    private RecyclerView rcvSPmua, rcvSpban, rcvSPtot, rcvChiTietDon;
    private adapter_sanpham sanPhamAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContent();
        setEvent();
    }

    private void setEvent() {
        rcvSPmua.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSPmua.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        sanPhamAdapter = new adapter_sanpham();
        rcvSPmua.setAdapter(sanPhamAdapter);
        rcvSpban.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSpban.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        sanPhamAdapter = new adapter_sanpham();
        rcvSpban.setAdapter(sanPhamAdapter);
        rcvSPtot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSPtot.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        sanPhamAdapter = new adapter_sanpham();
        rcvSPtot.setAdapter(sanPhamAdapter);
        rcvChiTietDon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvChiTietDon.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        sanPhamAdapter = new adapter_sanpham();
        rcvChiTietDon.setAdapter(sanPhamAdapter);
    }


    private void setContent() {
        rcvChiTietDon = findViewById(R.id.rcvChiTietDon);
        rcvChiTietDon = findViewById(R.id.rcvChiTietDon);
        rcvSpban = findViewById(R.id.rcvSanPhamLuotBan);
        rcvSPmua = findViewById(R.id.rcvSanPhamLuotMua);
        rcvSPtot = findViewById(R.id.rcvSanPhamGiaTot);

    }
}
