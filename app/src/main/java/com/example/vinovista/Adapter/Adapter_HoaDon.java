package com.example.vinovista.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Activity.ThongTinHoaDon;
import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.R;

import java.util.List;

public class Adapter_HoaDon extends RecyclerView.Adapter<Adapter_HoaDon.HoaDonViewHolder> {
    private List<HoaDon> hoaDonList;

    public Adapter_HoaDon(List<HoaDon> hoaDonList) {
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_quanlyhoadon, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);
        holder.tvTenKH.setText(hoaDon.getTenKhachHang());
        holder.tvTenNV.setText(hoaDon.getNhanVien());
        holder.tvTongBill.setText(String.valueOf(hoaDon.getTongHoaDon()) + "đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThongTinHoaDon.class);
                intent.putExtra("hoaDon", hoaDon);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenKH, tvTenNV, tvTongBill; // và các trường khác

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKH = itemView.findViewById(R.id.tvTenKH_QuanLyHD);
            tvTenNV = itemView.findViewById(R.id.tvTenNV_QuanLyHD);
            tvTongBill = itemView.findViewById(R.id.tvTongBill_QuanLyHD);
        }
    }
}
