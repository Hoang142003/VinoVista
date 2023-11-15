package com.example.vinovista.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.DanhMucViewHolder> {
    List<DanhMuc> danhMucList;
    private Context context;

    public DanhMucAdapter(List<DanhMuc> danhMucList,Context context) {
        this.danhMucList = danhMucList;
        this.context = context;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_danh_muc,parent,false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = danhMucList.get(position);
        if(danhMuc == null)
        {
            return;
        }
        holder.tvDanhMuc.setText(danhMuc.getTenDanhMuc());
        Picasso.get().load(
                        danhMuc.getAnh()
                ).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(holder.ivDanhMuc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mnSuaDanhMuc = new Intent(context, Activity_Sua_Danh_Muc.class);
                mnSuaDanhMuc.putExtra("id_danh_muc",danhMucList.get(position));
                context.startActivity(mnSuaDanhMuc);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(danhMucList != null)
        {
            return  danhMucList.size();
        }
        return 0;
    }

    class DanhMucViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tvDanhMuc;
        private ImageView ivDanhMuc;
        public RelativeLayout layout;
        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDanhMuc = itemView.findViewById(R.id.tvDanhMuc);
            ivDanhMuc = itemView.findViewById(R.id.ivDanhMuc);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
