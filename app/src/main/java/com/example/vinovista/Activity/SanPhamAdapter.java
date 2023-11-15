package com.example.vinovista.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>{
    private List<SanPham> sanPhamRuouList;
    private int viTri= RecyclerView.NO_POSITION;
    public SanPhamAdapter(List<SanPham> sanPhamRuouList) {
        this.sanPhamRuouList = sanPhamRuouList;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_quan_ly_san_pham,parent,false);

        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamRuouList.get(position);
        if(sanPham == null)
        {
            return;
        }
        holder.tvTenSanPham.setText(sanPham.getTenSanPham());
        holder.tvGiaSanPham.setText(String.valueOf(sanPham.getGiaSale())+" VNĐ");
        holder.tvHangTon.setText("SL: "+String.valueOf(sanPham.getSoLuong()));
        Picasso.get().load(
                        sanPham.getAnhSanPham()
                ).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(holder.ivSanPham);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if(sanPhamRuouList != null)
        {
            return  sanPhamRuouList.size();
        }
        return 0;
    }

    class SanPhamViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTenSanPham, tvGiaSanPham, tvHangTon;
        private ImageView ivSanPham;
        public LinearLayout layout;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGiaSanPham = itemView.findViewById(R.id.tvGiaSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvHangTon = itemView.findViewById(R.id.tvHangTon);
            ivSanPham = itemView.findViewById(R.id.ivSanPham);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
