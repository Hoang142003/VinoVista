package com.example.vinovista.Adapter_TrangChuSanPham;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.R;

public class Adapter_hoadon extends RecyclerView.Adapter<Adapter_hoadon.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvten, tvgia, tvsoluong, tvgiasale;
        ImageButton imgbAdd;
        ImageView imganhsp;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvgiasale = itemView.findViewById(R.id.tvGiaSale);
            tvten = itemView.findViewById(R.id.tvTenSP);
            tvgia = itemView.findViewById(R.id.tvGiaSP);
            tvsoluong = itemView.findViewById(R.id.tvSoLuong);
            imganhsp = itemView.findViewById(R.id.imgvSP);

        }
    }
}
