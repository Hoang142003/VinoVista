package com.example.vinovista.Adapter_TrangChuSanPham;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_SanPham extends RecyclerView.Adapter<Adapter_SanPham.MyViewHolder> {

    private ArrayList<SanPham> datalist = new ArrayList<>();

    public Adapter_SanPham() {
        khoi_tao();
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_san_pham, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham dataItem = datalist.get(position);
        holder.tvten.setText(dataItem.getTenSanPham());
        holder.tvgia.setText(String.valueOf(dataItem.getGiaGoc()) + " VND");
        holder.tvsoluong.setText(String.valueOf(dataItem.getSoLuong()));
        holder.tvgiasale.setText(String.valueOf(dataItem.getGiaSale()) + " VND");

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvten, tvgia, tvsoluong,tvgiasale;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvgiasale = itemView.findViewById(R.id.tvGiaSale);
            tvten = itemView.findViewById(R.id.tvTenSP);
            tvgia = itemView.findViewById(R.id.tvGiaSP);
            tvsoluong = itemView.findViewById(R.id.tvSoLuong);

        }
    }
    private void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SanPham");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    if (sanPham != null) {
                        datalist.add(sanPham);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if necessary
            }
        });
    }
}