package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.vinovista.Model.DanhMuc;
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
    private DanhMucAdapter danhMucAdapter;
    List<DanhMuc> danhMucList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        setControl();
    }

    private void setControl() {
        spnDanhMuc = findViewById(R.id.spnDanhMuc);
        LoadDanhMuc();
        danhMucAdapter = new DanhMucAdapter(Activity_Quan_Ly_San_Pham.this,danhMucList);
        spnDanhMuc.setAdapter(danhMucAdapter);
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
                danhMucAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}