package com.example.vinovista.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinovista.Model.ChiTietHoaDon;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Adapter_ChiTietHoaDon extends RecyclerView.Adapter<Adapter_ChiTietHoaDon.ChiTietHoaDonViewHolder> {
    private List<ChiTietHoaDon> chiTietHoaDonList;

    public Adapter_ChiTietHoaDon(List<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }

    @NonNull
    @Override
    public Adapter_ChiTietHoaDon.ChiTietHoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chitiethoadon, parent, false);
        return new ChiTietHoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonViewHolder holder, int position) {
        ChiTietHoaDon item = chiTietHoaDonList.get(position);

        // Truy vấn thông tin sản phẩm từ cơ sở dữ liệu Firebase dựa trên id_sanpham
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("SanPham").child(item.getIdSanPham());
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy thông tin sản phẩm từ dataSnapshot
                    String tenSanPham = dataSnapshot.child("tenSanPham").getValue(String.class);

                    // Hiển thị thông tin sản phẩm trên ViewHolder
                    holder.tvTenSP.setText(tenSanPham);
                    holder.tvGia.setText(item.getGia() + "đ");
                    holder.tvSoLuong.setText("Số lượng: " + item.getSoLuong());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi ở đây nếu cần
            }
        });
    }



    @Override
    public int getItemCount() {
        return chiTietHoaDonList.size();
    }

    public class ChiTietHoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvGia, tvSoLuong; // và các trường khác

        public ChiTietHoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP_TTHD);
            tvGia = itemView.findViewById(R.id.tvGia_TTHD);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong_TTHD);
            // Khởi tạo các trường khác tại đây nếu cần
        }
    }
}
