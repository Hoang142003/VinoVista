package com.example.vinovista.Adapter_TrangChuSanPham;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;

import java.util.ArrayList;

public class Adapter_ChiTietDon extends RecyclerView.Adapter<Adapter_ChiTietDon.MyViewHolder>{
    public Adapter_ChiTietDon() {
        this.data = data;
    }

    private ArrayList<SanPham> data = new ArrayList<>();

    public ArrayList<SanPham> getData() {
        return data;
    }

    public void setData(ArrayList<SanPham> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Adapter_ChiTietDon.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_don, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ChiTietDon.MyViewHolder holder, int position) {
        holder.tvTenCTD.setText(data.get(position).getTenSanPham());
        if (data.get(position).getGiaSale()!=0){
            holder.tvGiaCTD.setText(data.get(position).getGiaSale()+"");
        }else {
            holder.tvGiaCTD.setText(data.get(position).getGiaGoc()+"");
        }
      holder.edtSonguoi.setText(data.get(position).getSoLuongDaBan()+"");
    }

    @Override
    public int getItemCount() {
        if(data!=null){
            return data.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTru,ivCong;
        TextView tvTenCTD,tvGiaCTD;
        EditText edtSonguoi;
        ImageButton imgbDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTru = itemView.findViewById(R.id.ivTru);
            ivCong = itemView.findViewById(R.id.ivCong);
            tvTenCTD = itemView.findViewById(R.id.tvTenCTD);
            tvGiaCTD = itemView.findViewById(R.id.tvGiaCTD);
            edtSonguoi = itemView.findViewById(R.id.edtSonguoi);
            imgbDelete = itemView.findViewById(R.id.imgbDelete);


        }
    }
}
