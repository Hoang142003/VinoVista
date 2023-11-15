package com.example.vinovista.Adapter_TrangChuSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vinovista.Adapter.Photo_Adapter;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class trangchusanpham extends AppCompatActivity {
    private RecyclerView rcvSPmua, rcvSpban,rcvSPtot, rcvChiTietDon;
    private Adapter_SanPham sanPhamAdapter;
    ViewPager2 vpiv;
    CircleIndicator3 ci;
    ArrayList<String> sanPhams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_san_pham);
        setContent();
        khoitao();
        setEvent();

    }

    private void khoitao() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SanPham");
        databaseReference.limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    SanPham sanPham = snapshot1.getValue(SanPham.class);
                    sanPhams.add(sanPham.getAnhSanPham());
                }
                //Chuyen anh
                if (sanPhams != null && !sanPhams.isEmpty()) {
                    Photo_Adapter adapter = new Photo_Adapter(sanPhams);
                    vpiv.setAdapter(adapter);
                    ci.setViewPager(vpiv);
                }
                //Tao hieu ung khi chuyen anh
                vpiv.setOffscreenPageLimit(3);
                vpiv.setClipToPadding(false);
                vpiv.setClipChildren(false);
                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f + r * 0.15f);
                    }
                });
                vpiv.setPageTransformer(compositePageTransformer);

                //Tu dong chuyen anh
                AutoSlideImage();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        sanPhamAdapter = new Adapter_SanPham();
        rcvSPmua.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSPmua.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        rcvSPmua.setAdapter(sanPhamAdapter);

        rcvSpban.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSpban.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rcvSpban.setAdapter(sanPhamAdapter);

        rcvSPtot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSPtot.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rcvSPtot.setAdapter(sanPhamAdapter);

        rcvChiTietDon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvChiTietDon.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvChiTietDon.setAdapter(sanPhamAdapter);
    }
    private void AutoSlideImage() {
//        if(mTimer==null){
//            mTimer=new Timer();
//        }
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int curentItem=vpiv.getCurrentItem();
//                        int totalItem=5-1;
//                        if(curentItem<totalItem){
//                            curentItem++;
//                            vpiv.setCurrentItem(curentItem);
//                        }
//                        else{
//                            vpiv.setCurrentItem(0);
//                        }
//                    }
//                });
//            }
//        },500,3000);
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = vpiv.getCurrentItem();
                if (currentItem == sanPhams.size() - 1) {
                    vpiv.setCurrentItem(0);
                } else {
                    vpiv.setCurrentItem(vpiv.getCurrentItem() + 1);
                }
            }
        };
        vpiv.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    private void setContent() {
        rcvChiTietDon = findViewById(R.id.rcvChiTietDon);
        rcvChiTietDon = findViewById(R.id.rcvChiTietDon);
        rcvSpban = findViewById(R.id.rcvSanPhamLuotBan);
        rcvSPmua = findViewById(R.id.rcvSanPhamLuotMua);
        rcvSPtot= findViewById(R.id.rcvSanPhamGiaTot);
        vpiv = findViewById(R.id.vpiv);
        ci = findViewById(R.id.ci);

    }
}