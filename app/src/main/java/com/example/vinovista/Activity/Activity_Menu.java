package com.example.vinovista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.vinovista.Adapter.Menu_Adapter;
import com.example.vinovista.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Activity_Menu extends AppCompatActivity {
    ViewPager2 vp_bottomNavigation;
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setControl();
        Initialization();

    }


    private void Initialization() {
        //Chặn chuyển các fragment bằng vuốt
        vp_bottomNavigation.setUserInputEnabled(false);
        Menu_Adapter menu_adapter = new Menu_Adapter(this);
        vp_bottomNavigation.setAdapter(menu_adapter);
        vp_bottomNavigation.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.menu_trangchu).setChecked(true);
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.menu_nhanvien).setChecked(true);
                        break;
                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.menu_sanpham).setChecked(true);
                        break;
                    case 3:
                        bottomNavigation.getMenu().findItem(R.id.menu_thongke).setChecked(true);
                        break;
                    case 4:
                        bottomNavigation.getMenu().findItem(R.id.menu_taikhoan).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_trangchu) {
                vp_bottomNavigation.setCurrentItem(0);
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.menu_nhanvien) {
                vp_bottomNavigation.setCurrentItem(1);
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.menu_sanpham) {
                vp_bottomNavigation.setCurrentItem(2);
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.menu_thongke) {
                vp_bottomNavigation.setCurrentItem(3);

                overridePendingTransition(0, 0);
            }else if (item.getItemId() == R.id.menu_taikhoan) {
                vp_bottomNavigation.setCurrentItem(4);

                overridePendingTransition(0, 0);
            }
            return true;
        });

    }

    private void setControl() {
        vp_bottomNavigation = findViewById(R.id.vp_bottomNavigation);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
}