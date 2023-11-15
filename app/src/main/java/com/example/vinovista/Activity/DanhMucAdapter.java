package com.example.vinovista.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhMucAdapter extends BaseAdapter {
    private Context context;
    private List<DanhMuc> danhMucList;

    public DanhMucAdapter(Context context, List<DanhMuc> danhMucList) {
        this.context = context;
        this.danhMucList = danhMucList;
    }

    @Override
    public int getCount() {
        return danhMucList != null ? danhMucList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.custom_danh_muc_spiner,viewGroup,false);
        TextView tvDanhMuc = rootView.findViewById(R.id.tvDanhMuc);
        ImageView ivDanhMuc = rootView.findViewById(R.id.ivDanhMuc);
        tvDanhMuc.setText(danhMucList.get(i).getTenDanhMuc());
        Picasso.get().load(danhMucList.get(i).getAnh()).into(ivDanhMuc);
        return rootView;
    }
}
