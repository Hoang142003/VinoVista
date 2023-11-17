package com.example.vinovista.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vinovista.Activity.Activity_Quan_Ly_Danh_Muc;
import com.example.vinovista.Activity.Activity_ThemSanPhamRuou;
import com.example.vinovista.Activity.DanhMucSpinerAdapter;
import com.example.vinovista.Activity.ItemTouchListenerHelper;
import com.example.vinovista.Activity.RecyclerViewTouch;
import com.example.vinovista.Activity.SanPhamAdapter;
import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_SanPham#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_SanPham extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ItemTouchListenerHelper {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spnDanhMuc;
    private DanhMucSpinerAdapter danhMucSpinerAdapter;
    List<DanhMuc> danhMucList = new ArrayList<>();
    private RecyclerView rcvSanPham;
    private List<SanPham> sanPhamRuouList;
    private SanPhamAdapter sanPhamAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button btnSanPhamMoi, btnQuanLyDanhMuc;
    EditText edtTimKiem;
    RelativeLayout root_view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_SanPham() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_SanPham.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_SanPham newInstance(String param1, String param2) {
        Fragment_SanPham fragment = new Fragment_SanPham();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__san_pham, container, false);
        LoadDanhMuc();
        setControl(view);
        setEvent(view);
        return view;
    }

    private void setEvent(View view) {
        btnSanPhamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mnThemSanPham = new Intent(getActivity(), Activity_ThemSanPhamRuou.class);
                startActivity(mnThemSanPham);
            }
        });
        btnQuanLyDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mhDanhMuc = new Intent(getActivity(), Activity_Quan_Ly_Danh_Muc.class);
                startActivity(mhDanhMuc);
            }
        });

    }

    private void setControl(View view) {
        spnDanhMuc = view.findViewById(R.id.spnDanhMuc);
        btnSanPhamMoi = view.findViewById(R.id.btnSanPhamMoi);
        btnQuanLyDanhMuc = view.findViewById(R.id.btnQuanLyDanhMuc);
        edtTimKiem = view.findViewById(R.id.edtTimKiem);
        rcvSanPham = view.findViewById(R.id.rcvSanPham);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        root_view = view.findViewById(R.id.root_view);
        danhMucSpinerAdapter = new DanhMucSpinerAdapter(getActivity(),danhMucList);
        spnDanhMuc.setAdapter(danhMucSpinerAdapter);
        sanPhamRuouList = new ArrayList<>();
        LoadSanPham();
        sanPhamAdapter = new SanPhamAdapter(sanPhamRuouList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvSanPham.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvSanPham.addItemDecoration(decoration);
        rcvSanPham.setAdapter(sanPhamAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadSanPham();
                edtTimKiem.setText("");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerViewTouch(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcvSanPham);
    }
    private void LoadSanPham() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SanPham");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhamRuouList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPhamRuou = dataSnapshot.getValue(SanPham.class);
                    sanPhamRuouList.add(sanPhamRuou);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void LoadDanhMuc() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhMuc danhMuc = dataSnapshot.getValue(DanhMuc.class);
                    danhMucList.add(danhMuc);
                }
                danhMucSpinerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Bạn có chắc muốn xóa sản phẩm này không?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SanPham sanPhamRuou = sanPhamRuouList.get(viewHolder.getAdapterPosition());
                        int deleteIndex = viewHolder.getAdapterPosition();
                        String tenSanPham = sanPhamRuouList.get(viewHolder.getAdapterPosition()).getTenSanPham();
                        String idSanPham = sanPhamRuouList.get(viewHolder.getAdapterPosition()).getIdSanPham();
                        removeSanPham(idSanPham,deleteIndex);
                        Snackbar snackbar = Snackbar.make(root_view,"Đã xóa "+tenSanPham,Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                undoSanPham(sanPhamRuou,deleteIndex);
                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();

                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                }).show();
    }
    public void undoSanPham(SanPham sanPhamRuou,int index)
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SanPham");
        ref.child(sanPhamRuou.getIdSanPham()).setValue(sanPhamRuou, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Hoàn tác thành công", Toast.LENGTH_SHORT).show();
                if(error==null)
                {

                }

            }
        });
    }
    public void removeSanPham(String idSanPham, int index)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SanPham");
        ref.child(String.valueOf(idSanPham)).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Delete successfull", Toast.LENGTH_SHORT).show();
            }
        });
        sanPhamAdapter.notifyItemRemoved(index);
    }
}