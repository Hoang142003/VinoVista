package com.example.vinovista.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vinovista.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ThongKe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ThongKe extends Fragment {
    private BarChart barChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_ThongKe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ThongKe.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ThongKe newInstance(String param1, String param2) {
        Fragment_ThongKe fragment = new Fragment_ThongKe();
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
        View view = inflater.inflate(R.layout.fragment__thong_ke, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        // Tạo dữ liệu cho biểu đồ thống kê (Ví dụ: dữ liệu ngẫu nhiên)
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 50));
        entries.add(new BarEntry(2, 70));
        entries.add(new BarEntry(3, 30));
        entries.add(new BarEntry(4, 90));
        entries.add(new BarEntry(5, 60));

        BarDataSet dataSet = new BarDataSet(entries, "Doanh số theo tháng");

        BarData barData = new BarData(dataSet);

        // Cấu hình biểu đồ
        barChart.setData(barData);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.animateY(1000);
    }

    private void setControl(View view) {
        barChart = view.findViewById(R.id.barChart);
    }
}