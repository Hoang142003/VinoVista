package com.example.vinovista.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinovista.Adapter.Adapter_HoaDon;
import com.example.vinovista.Model.HoaDon;
import com.example.vinovista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_HoaDon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_HoaDon extends Fragment {
    private RecyclerView recyclerViewResults;
    private DatabaseReference mDatabase;
    private List<HoaDon> data = new ArrayList<>();
    EditText editTextSearch;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_HoaDon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_HoaDon.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_HoaDon newInstance(String param1, String param2) {
        Fragment_HoaDon fragment = new Fragment_HoaDon();
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
        View view = inflater.inflate(R.layout.fragment__hoa_don, container, false);
        setControl(view);
        setEvent();
//        mDatabase = FirebaseDatabase.getInstance().getReference("a");
//        mDatabase.setValue("a");
        return view;
    }

    private void setEvent() {
        mDatabase = FirebaseDatabase.getInstance().getReference("HoaDon");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("AAA", "đụ mẹ mày");
                data.clear(); // Clear old data to add new data

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    HoaDon hoaDon = postSnapshot.getValue(HoaDon.class);
                    data.add(hoaDon);
                    Log.e("AAA", hoaDon.getIdHoaDon());
                }

                Adapter_HoaDon adapter = new Adapter_HoaDon(data);
                recyclerViewResults.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl(View view) {

        recyclerViewResults = view.findViewById(R.id.recyclerViewHoaDon);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        editTextSearch = view.findViewById(R.id.editTextSearch);
    }
}