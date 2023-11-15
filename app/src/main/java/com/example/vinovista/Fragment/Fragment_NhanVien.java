package com.example.vinovista.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.vinovista.Activity.Activity_ThemNhanVien;
import com.example.vinovista.Adapter.adapter_NhanVien;
import com.example.vinovista.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_NhanVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_NhanVien extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_NhanVien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_NhanVien.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_NhanVien newInstance(String param1, String param2) {
        Fragment_NhanVien fragment = new Fragment_NhanVien();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rcvDanhSachNhanVien;
    private adapter_NhanVien adapter;
    LinearLayout llThemNhanVien;
    ProgressBar progressBar_danhsachnhanvien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__nhan_vien, container, false);
        setControl(view);
        adapter = new adapter_NhanVien(getContext(),progressBar_danhsachnhanvien);
        setEvent();
        Log.e("soLuong", adapter.getItemCount() + "");

        return view;
    }

    private void setEvent() {
        rcvDanhSachNhanVien.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvDanhSachNhanVien.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvDanhSachNhanVien.setAdapter(adapter);
        llThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Activity_ThemNhanVien.class);
                getContext().startActivity(intent);
            }
        });
    }

    private void setControl(View view) {
        rcvDanhSachNhanVien = view.findViewById(R.id.rcvDanhSachNhanVien);
        llThemNhanVien = view.findViewById(R.id.llThemNhanVien);
        progressBar_danhsachnhanvien=view.findViewById(R.id.progressBar_danhsachnhanvien);
    }
}