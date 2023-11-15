package com.example.vinovista.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vinovista.Fragment.Fragment_NhanVien;
import com.example.vinovista.Fragment.Fragment_SanPham;
import com.example.vinovista.Fragment.Fragment_TaiKhoan;
import com.example.vinovista.Fragment.Fragment_ThongKe;
import com.example.vinovista.Fragment.Fragment_TrangChu;

public class Menu_Adapter extends FragmentStateAdapter {
    public Menu_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_TrangChu();
            case 1:
                return new Fragment_NhanVien();
            case 2:
                return new Fragment_SanPham();
            case 3:
                return new Fragment_ThongKe();
            case 4:
                return new Fragment_TaiKhoan();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

