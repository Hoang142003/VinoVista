package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_Quan_Ly_Danh_Muc extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,ItemTouchListenerHelper{
    private RecyclerView rcvDanhMuc;
    private List<DanhMuc> danhMucList;
    private DanhMucAdapter danhMucAdapter;
    ImageButton imgBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button btnThemDanhMuc;
    EditText edtTimKiem;
    RelativeLayout root_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quan_ly_danh_muc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mnThemDanhMuc = new Intent(Activity_Quan_Ly_Danh_Muc.this, Activity_Them_Danh_Muc.class);
                startActivity(mnThemDanhMuc);
            }
        });
    }

    private void setControl() {
        imgBack = findViewById(R.id.imgBack);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        btnThemDanhMuc = findViewById(R.id.btnThemDanhMuc);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        rcvDanhMuc = findViewById(R.id.rcvDanhMuc);
        root_view = findViewById(R.id.root_view);
        danhMucList = new ArrayList<>();
        LoadDanhMuc();
        danhMucAdapter = new DanhMucAdapter(danhMucList,Activity_Quan_Ly_Danh_Muc.this);
        rcvDanhMuc.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvDanhMuc.addItemDecoration(decoration);
        rcvDanhMuc.setAdapter(danhMucAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        ItemTouchHelper.SimpleCallback simpleCallback = new RecylerViewTouchDanhMuc(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcvDanhMuc);
    }

    private void LoadDanhMuc() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhMuc danhMuc = dataSnapshot.getValue(DanhMuc.class);
                    if(!danhMuc.getTenDanhMuc().equals("Tất cả"))
                    {
                        danhMucList.add(danhMuc);
                    }
                }
                danhMucAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onRefresh() {
        edtTimKiem.setText("");
        LoadDanhMuc();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        new AlertDialog.Builder(Activity_Quan_Ly_Danh_Muc.this)
                .setMessage("Bạn có chắc muốn xóa danh mục này không?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
        if(viewHolder instanceof DanhMucAdapter.DanhMucViewHolder)
        {
            DanhMuc danhMuc = danhMucList.get(viewHolder.getAdapterPosition());
            int deleteIndex = viewHolder.getAdapterPosition();
            String tenDanhMuc = danhMucList.get(viewHolder.getAdapterPosition()).getTenDanhMuc();
            String idDanhMuc = danhMucList.get(viewHolder.getAdapterPosition()).getIdDanhMuc();
            removeDanhMuc(idDanhMuc,deleteIndex);
            Snackbar snackbar = Snackbar.make(root_view,"Đã xóa "+tenDanhMuc,Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    undoDanhMuc(danhMuc,deleteIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        danhMucAdapter.notifyDataSetChanged();
                    }
                }).show();
    }
    public void removeDanhMuc(String idDanhMuc, int index)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
        ref.child(String.valueOf(idDanhMuc)).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Activity_Quan_Ly_Danh_Muc.this, "Delete successfull", Toast.LENGTH_SHORT).show();
            }
        });
        danhMucAdapter.notifyItemRemoved(index);
    }
    public void undoDanhMuc(DanhMuc danhMuc,int index)
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
        ref.child(danhMuc.getIdDanhMuc()).setValue(danhMuc, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Activity_Quan_Ly_Danh_Muc.this, "Hoàn tác thành công", Toast.LENGTH_SHORT).show();
                if(error==null)
                {

                }

            }
        });
    }
}