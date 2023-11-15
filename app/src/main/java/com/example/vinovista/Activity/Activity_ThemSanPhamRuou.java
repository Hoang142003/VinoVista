package com.example.vinovista.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.Model.SanPham;
import com.example.vinovista.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Activity_ThemSanPhamRuou extends AppCompatActivity {
    Spinner spnLoai;
    private String idLoai = "2";
    private DanhMucSpinerAdapter danhMucSpinerAdapter;
    List<DanhMuc> danhMucList = new ArrayList<>();
    ImageView ivSanPham;
    ImageButton btn_back;
    EditText edtMaSanPham,edtTenSanPham, edtMoTa, edtGiaNhap, edtGiaBan, edtSoLuong;
    Button btnHoanTat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham_ruou);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ivSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spnLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idLoai = danhMucList.get(i).getIdDanhMuc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://vinovista-ae7f9.appspot.com/pictures");
                StorageReference storage_ref=storageReference.child("image"+System.currentTimeMillis()+".png");

                //Upload anh
                // Get the data from an ImageView as bytes
                ivSanPham.setDrawingCacheEnabled(true);
                ivSanPham.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) ivSanPham.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storage_ref.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...

                        Toast.makeText(Activity_ThemSanPhamRuou.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoLink = uri.toString();
                                //Sau khi dang anh xong thi se them san pham
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SanPham");
                                String idSanPham = ref.push().getKey();
                                SanPham sanPhamRuou = new SanPham(idSanPham,edtTenSanPham.getText().toString(),edtMoTa.getText().toString(),idLoai,photoLink,Integer.valueOf(edtSoLuong.getText().toString()),Integer.valueOf(edtGiaNhap.getText().toString()),Integer.valueOf(edtGiaBan.getText().toString()),0);
                                ref.child(sanPhamRuou.getIdSanPham()).setValue(sanPhamRuou, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(Activity_ThemSanPhamRuou.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                        if(error==null)
                                        {
                                            finish();
                                        }

                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                Picasso.get().load(imageUri).into(ivSanPham);
            }
        }
    });
    private void setControl() {
        LoadDanhMuc();
        spnLoai = findViewById(R.id.spnLoai);
        ivSanPham = findViewById(R.id.ivSanPham);
        btn_back = findViewById(R.id.btn_back);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtMoTa = findViewById(R.id.edtMoTa);
        edtGiaNhap = findViewById(R.id.edtGiaNhap);
        edtGiaBan = findViewById(R.id.edtGiaBan);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnHoanTat = findViewById(R.id.btnHoanTat);
        danhMucSpinerAdapter = new DanhMucSpinerAdapter(Activity_ThemSanPhamRuou.this,danhMucList);
        spnLoai.setAdapter(danhMucSpinerAdapter);
    }
    private void LoadDanhMuc() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhMuc danhMuc = dataSnapshot.getValue(DanhMuc.class);
                    if(!danhMuc.getTenDanhMuc().equals("Tất cả"))
                    {
                        danhMucList.add(danhMuc);
                    }

                }
                danhMucSpinerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}