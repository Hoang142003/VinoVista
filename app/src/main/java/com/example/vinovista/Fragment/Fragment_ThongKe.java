package com.example.vinovista.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ThongKe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ThongKe extends Fragment {
    private BarChart barChart;
    private Spinner spinnerStatisticalType, spinnerMonth, spinnerYear;
    private ArrayAdapter<String> adapter, monthAdapter, yearAdapter;


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
        List<String> statisticalTypes = new ArrayList<>();
        statisticalTypes.add("Doanh số bán hàng");
        statisticalTypes.add("Doanh thu nhân viên");
        List<String> statisticalMonth = new ArrayList<>();
        statisticalMonth.add("1");
        statisticalMonth.add("2");
        statisticalMonth.add("3");
        statisticalMonth.add("4");
        statisticalMonth.add("5");
        statisticalMonth.add("6");
        statisticalMonth.add("7");
        statisticalMonth.add("8");
        statisticalMonth.add("9");
        statisticalMonth.add("10");
        statisticalMonth.add("11");
        statisticalMonth.add("12");
        List<String> statisticalYear = new ArrayList<>();
        statisticalYear.add("2022");
        statisticalYear.add("2023");

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statisticalTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatisticalType.setAdapter(adapter);

        monthAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statisticalMonth);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statisticalYear);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        spinnerStatisticalType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi người dùng chọn một loại thống kê
                String selectedStatisticalType = statisticalTypes.get(position);
                // Thực hiện thống kê tương ứng với loại đã chọn
                if (selectedStatisticalType.equals("Doanh số bán hàng")) {
                    // Thực hiện thống kê doanh số bán hàng
                    String selectedMonth = spinnerMonth.getSelectedItem().toString();
                    String selectedYear = spinnerYear.getSelectedItem().toString();

                    // Gọi hàm thực hiện thống kê doanh số bán hàng theo tháng và năm đã chọn
                    calculateRevenueByMonthAndYear(selectedMonth, selectedYear);

                } else if (selectedStatisticalType.equals("Doanh thu nhân viên")) {
                    // Thực hiện thống kê số lượng sản phẩm bán ra
                    String selectedMonth = spinnerMonth.getSelectedItem().toString();
                    String selectedYear = spinnerYear.getSelectedItem().toString();

                    // Gọi hàm thực hiện thống kê số lượng sản phẩm bán ra theo tháng và năm đã chọn
                    calculateRevenueByEmployeeMonthAndYear(selectedMonth, selectedYear);
                }
                // Xử lý các loại thống kê khác nếu cần
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }

    // Hàm thực hiện thống kê doanh thu của nhân viên theo tháng và năm và hiển thị lên BarChart
    private void calculateRevenueByEmployeeMonthAndYear(String selectedMonth, String selectedYear) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hoaDonRef = database.getReference("HoaDon");
        DatabaseReference nhanVienRef = database.getReference("NhanVien");

        nhanVienRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot nhanVienDataSnapshot) {
                // Tạo một danh sách ánh xạ mã nhân viên sang tên Nhân viên
                Map<String, String> employeeNameMap = new HashMap<>();

                for (DataSnapshot nhanVienSnapshot : nhanVienDataSnapshot.getChildren()) {
                    String employeeId = nhanVienSnapshot.getKey();
                    String employeeName = nhanVienSnapshot.child("hoTen").getValue(String.class);

                    if (employeeId != null && employeeName != null) {
                        employeeNameMap.put(employeeId, employeeName);
                    }
                }

                hoaDonRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot hoaDonDataSnapshot) {
                        // Tạo danh sách lưu trữ doanh thu của từng nhân viên trong tháng đã chọn
                        Map<String, Double> employeeRevenues = new HashMap<>();

                        for (DataSnapshot hoaDonSnapshot : hoaDonDataSnapshot.getChildren()) {
                            HoaDon hoaDon = hoaDonSnapshot.getValue(HoaDon.class);

                            if (hoaDon != null && isInSelectedMonthAndYear(hoaDon, selectedMonth, selectedYear)) {
                                // Lấy mã nhân viên từ hóa đơn
                                String employeeId = hoaDon.getNhanVien();
                                double revenue = hoaDon.getTongHoaDon();

                                // Cộng tổng doanh thu của nhân viên vào danh sách
                                if (employeeRevenues.containsKey(employeeId)) {
                                    employeeRevenues.put(employeeId, employeeRevenues.get(employeeId) + revenue);
                                } else {
                                    employeeRevenues.put(employeeId, revenue);
                                }
                            }
                        }

                        // Tạo danh sách dữ liệu cho biểu đồ BarChart
                        List<BarEntry> entries = new ArrayList<>();
                        List<String> labels = new ArrayList<>();

                        int index = 0;
                        for (Map.Entry<String, Double> entry : employeeRevenues.entrySet()) {
                            String employeeId = entry.getKey();
                            double revenue = entry.getValue();

                            // Ánh xạ mã nhân viên sang tên Nhân viên từ danh sách đã tạo
                            String employeeName = employeeNameMap.get(employeeId);

                            entries.add(new BarEntry(index, (float) revenue));

                            // Sử dụng tên Nhân viên làm nhãn trên trục X
                            labels.add(employeeName != null ? employeeName : employeeId);

                            index++;
                        }

                        // Tạo dữ liệu và hiển thị biểu đồ
                        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu nhân viên");
                        BarData barData = new BarData(dataSet);
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                int index = (int) value;
                                if (index >= 0 && index < labels.size()) {
                                    return labels.get(index); // Đặt tên Nhân viên làm nhãn trên trục X
                                }
                                return "";
                            }
                        });
                        xAxis.setGranularity(1f);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setLabelCount(labels.size());
                        xAxis.setLabelRotationAngle(45f);
                        xAxis.setDrawLabels(true);

                        barChart.setData(barData);
                        barChart.getDescription().setEnabled(false);
                        barChart.invalidate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

    // Hàm thực hiện thống kê doanh số bán hàng theo tháng và năm
    private void calculateRevenueByMonthAndYear(String selectedMonth, String selectedYear) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hoaDonRef = database.getReference("HoaDon");

        hoaDonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Tạo danh sách lưu trữ doanh số bán hàng từng ngày trong tháng đã chọn
                Map<Integer, Double> dailyRevenues = new HashMap<>();

                for (DataSnapshot hoaDonSnapshot : dataSnapshot.getChildren()) {
                    HoaDon hoaDon = hoaDonSnapshot.getValue(HoaDon.class);

                    if (hoaDon != null && isInSelectedMonthAndYear(hoaDon, selectedMonth, selectedYear)) {
                        // Lấy ngày từ ngày mua (ví dụ: "16/11/2023 06:29" -> ngày 16)
                        int day = Integer.parseInt(hoaDon.getNgayMua().split("/")[0]);
                        double revenue = hoaDon.getTongHoaDon();

                        // Cộng tổng doanh số bán hàng vào danh sách theo ngày
                        if (dailyRevenues.containsKey(day)) {
                            dailyRevenues.put(day, dailyRevenues.get(day) + revenue);
                        } else {
                            dailyRevenues.put(day, revenue);
                        }
                    }
                }

                // Tạo danh sách dữ liệu cho biểu đồ
                List<BarEntry> entries = new ArrayList<>();
                List<String> labels = new ArrayList<>();

                for (Map.Entry<Integer, Double> entry : dailyRevenues.entrySet()) {
                    int day = entry.getKey();
                    double revenue = entry.getValue();
                    entries.add(new BarEntry(day, (float) revenue));
                    labels.add(String.valueOf(day));
                }

                // Tạo dữ liệu và hiển thị biểu đồ
                BarDataSet dataSet = new BarDataSet(entries, "Doanh số bán hàng");
                BarData barData = new BarData(dataSet);
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new DayAxisValueFormatter(labels));
                xAxis.setGranularity(1f);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                // Đặt tên trục X
                xAxis.setLabelCount(labels.size()); // Đặt số lượng label trên trục X
                xAxis.setLabelRotationAngle(45f); // Đặt góc quay của label (nếu cần)
                xAxis.setDrawLabels(true); // Cho phép hiển thị label trên trục X

                barChart.setData(barData);
                barChart.getDescription().setEnabled(false);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

    // Tạo một lớp ValueFormatter để hiển thị ngày trên trục X
    private class DayAxisValueFormatter extends ValueFormatter {
        private final List<String> labels;

        DayAxisValueFormatter(List<String> labels) {
            this.labels = labels;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            int index = (int) value;
            if (index >= 0 && index < labels.size()) {
                // Trả về ngày tương ứng từ danh sách labels
                return labels.get(index);
            }
            return "";
        }
    }

    // Hàm kiểm tra xem một hóa đơn có nằm trong tháng và năm đã chọn hay không
    private boolean isInSelectedMonthAndYear(HoaDon hoaDon, String selectedMonth, String selectedYear) {
        // Đã cắt ngàyMua để lấy tháng và năm từ ngàyMua
        // Ví dụ: "16/11/2023 06:29" sẽ được cắt thành tháng "11" và năm "2023"
        String[] parts = hoaDon.getNgayMua().split("/");
        if (parts.length >= 3) {
            String month = parts[1];
            String year = parts[2].split(" ")[0];

            return month.equals(selectedMonth) && year.equals(selectedYear);
        }

        return false;
    }

    private void setControl(View view) {
        barChart = view.findViewById(R.id.barChart);
        spinnerStatisticalType = view.findViewById(R.id.spinnerStatisticalType);
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        spinnerYear = view.findViewById(R.id.spinnerYear);
    }
}