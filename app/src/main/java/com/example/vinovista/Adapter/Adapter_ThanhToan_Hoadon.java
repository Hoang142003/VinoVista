package com.example.vinovista.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;

import java.util.ArrayList;

public class Adapter_ThanhToan_Hoadon extends RecyclerView.Adapter<Adapter_ThanhToan_Hoadon.ThanhToan_Holder> {
    ArrayList<SanPham> arr_sanpham = new ArrayList<>();

    public Adapter_ThanhToan_Hoadon(ArrayList<SanPham> arr_sanpham) {
        this.arr_sanpham = arr_sanpham;
    }

    @NonNull
    @Override
    public ThanhToan_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chitiethoadon, parent, false);
        return new ThanhToan_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhToan_Holder holder, int position) {
        SanPham sanPham = arr_sanpham.get(position);
        holder.tvTenSP.setText(sanPham.getTenSanPham());
        if (sanPham.getGiaSale() != 0) {
            holder.tvGia.setText("Giá: "+sanPham.getGiaSale() + "đ");
        } else {
            holder.tvGia.setText("Giá: "+sanPham.getGiaGoc() + "đ");
        }
        holder.tvSoLuong.setText("Số lượng: "+sanPham.getSl_dat_hang()+"");
    }

    @Override
    public int getItemCount() {
        return arr_sanpham.size();
    }

    class ThanhToan_Holder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvGia, tvSoLuong; // và các trường khác

        public ThanhToan_Holder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP_TTHD);
            tvGia = itemView.findViewById(R.id.tvGia_TTHD);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong_TTHD);
            // Khởi tạo các trường khác tại đây nếu cần
        }

    }

}
