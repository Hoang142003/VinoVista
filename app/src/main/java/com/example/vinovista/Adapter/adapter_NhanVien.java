package com.example.vinovista.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Activity.Activity_ThemNhanVien;
import com.example.vinovista.Model.NhanVien;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class adapter_NhanVien extends RecyclerView.Adapter<adapter_NhanVien.MyViewHolder> {
    ArrayList<NhanVien> danhSachNhanVien = new ArrayList<>();
    Context context;
    ProgressBar progressBar_danhsachnhanvien;

    public adapter_NhanVien(Context context, ProgressBar progressBar_danhsachnhanvien) {
        this.progressBar_danhsachnhanvien = progressBar_danhsachnhanvien;
        this.context = context;
        khoiTao();
    }

    @NonNull
    @Override
    public adapter_NhanVien.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_nhanvien, parent, false);
        return new adapter_NhanVien.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_NhanVien.MyViewHolder holder, int position) {
        NhanVien nhanVien = danhSachNhanVien.get(position);
        holder.tvTenNhanVien.setText(nhanVien.getHoTen());
        holder.tvLoaiNhanVien.setText(setTenLoaiNhanVien(nhanVien));
        Picasso.get().load(nhanVien.getAnh()).into(holder.ivAnhNhanVien, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                // Ảnh đã được tải, ẩn ProgressBar
                holder.progressBar_anh.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                // Có lỗi khi tải ảnh, ẩn ProgressBar và có thể hiển thị ảnh lỗi
                holder.progressBar_anh.setVisibility(View.GONE);
                // Set ảnh lỗi nếu có
                holder.ivAnhNhanVien.setImageResource(R.drawable.person);
            }
        });
        holder.imbChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_ThemNhanVien.class);
                intent.putExtra("NhanVien", nhanVien);
                context.startActivity(intent);
            }
        });
    }

    private String setTenLoaiNhanVien(NhanVien nhanVien) {
        return "1".equals(nhanVien.getIdLoaiNhanVien()) ? "Quản lý" : "Nhân viên";
    }

    @Override
    public int getItemCount() {
        return danhSachNhanVien.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAnhNhanVien;
        ImageButton imbChinhSua;
        TextView tvTenNhanVien;
        TextView tvLoaiNhanVien;
        ProgressBar progressBar_anh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAnhNhanVien = itemView.findViewById(R.id.ivAnhNhanVien);
            imbChinhSua = itemView.findViewById(R.id.imbChinhSuaNhanVien);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvLoaiNhanVien = itemView.findViewById(R.id.tvLoaiNhanVien);
            progressBar_anh = itemView.findViewById(R.id.progressBar_anhnv);
        }
    }

    private void khoiTao() {

        progressBar_danhsachnhanvien.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NhanVien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Dữ liệu mới từ Firebase
                danhSachNhanVien.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);
                    if (nhanVien != null) {
                        danhSachNhanVien.add(nhanVien);
                    }
                }
                notifyDataSetChanged();

                progressBar_danhsachnhanvien.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("adapter_NhanVien", "Lỗi khi đọc dữ liệu từ Firebase", error.toException());
            }
        });
    }
}