package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinovista.Model.NhanVien;
import com.example.vinovista.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Activity_ThongTin extends AppCompatActivity {
    public static String SHARED_PRE = "shared_pre";
    ImageButton imbAnhNhanVienMoi;
    TextView edtTenNv, edtSoDienThoaiNV, edtDiaChi, edtMatKhau, edtLuong;
    Button btnSave,btnDelete;
    ProgressBar progressBar_NV;
    NhanVien nhanVien;
    private String anh;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Uri currentImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        setControl();
        setEvent();
        nhanVien = (NhanVien) getIntent().getSerializableExtra("NhanVien");
        if (nhanVien != null) {
            fillData(nhanVien);
            anh = nhanVien.getAnh(); // Lưu URL ảnh hiện tại
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        String chuc_vu= sharedPreferences.getString("chuc_vu_auto", "");
        if (chuc_vu.equals("Quản ly")){
            edtTenNv.setEnabled(true);
            edtLuong.setEnabled(true);
        }else if (chuc_vu.equals("Nhân viên")){
            edtTenNv.setEnabled(false);
            edtLuong.setEnabled(false);
        }
    }
    private void setControl() {
        imbAnhNhanVienMoi = findViewById(R.id.imbAnhNhanVienMoi);
        edtTenNv = findViewById(R.id.edtTenNv);
        edtSoDienThoaiNV = findViewById(R.id.edtSoDienThoaiNV);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtLuong = findViewById(R.id.edtLuong);
        btnSave = findViewById(R.id.btnSave);
        btnDelete=findViewById(R.id.btnDelete);
        progressBar_NV=findViewById(R.id.progressBar_NV);
    }

    private void setEvent() {
        imbAnhNhanVienMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), currentImageUri);
                        uploadImageToFirebase(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(Activity_ThongTin.this, "Error in image processing: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Sử dụng ảnh hiện tại nếu người dùng không chọn ảnh mới
                    try {
                        LuuThongTin(nhanVienMoi(anh));
                    } catch (Exception e) {
                        Toast.makeText(Activity_ThongTin.this, "Error in saving employee info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    //sửa thông tin nhân viên
    void fillData(NhanVien nhanVien) {
        edtTenNv.setText(nhanVien.getHoTen());
        edtLuong.setText(String.valueOf(nhanVien.getLuong()));
        edtDiaChi.setText(nhanVien.getDiaChi());
        edtSoDienThoaiNV.setText(nhanVien.getSoDienThoai());
        edtMatKhau.setText(nhanVien.getMatKhau());
        Picasso.get().load(nhanVien.getAnh()).into(imbAnhNhanVienMoi, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                // Ảnh đã được tải, ẩn ProgressBar
                progressBar_NV.setVisibility(View.GONE);
                // Set viền khi đã load ảnh
                //holder.ivAnhNhanVien.setBackgroundResource(R.drawable.border_image_nhanvien);
            }

            @Override
            public void onError(Exception e) {
                // Có lỗi khi tải ảnh, ẩn ProgressBar và có thể hiển thị ảnh lỗi
               // progressBar_NV.setVisibility(View.GONE);
                // Set ảnh lỗi nếu có
                imbAnhNhanVienMoi.setImageResource(R.drawable.person);
            }
        });
    }

    //lưu nhân viên mới
    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } else if (which == 1) {
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                currentImageUri = getImageUri(imageBitmap);
                imbAnhNhanVienMoi.setImageURI(currentImageUri);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                currentImageUri = data.getData();
                imbAnhNhanVienMoi.setImageURI(currentImageUri);
            }
        }
    }

    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImageToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("pictures/" + System.currentTimeMillis() + ".jpg");

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                anh = uri.toString();
                Toast.makeText(Activity_ThongTin.this, "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                try {
                    LuuThongTin(nhanVienMoi(anh));
                } catch (Exception e) {
                    Toast.makeText(Activity_ThongTin.this, "Error in saving employee info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(Activity_ThongTin.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void LuuThongTin(NhanVien nhanVien) {
        if (nhanVien.getSoDienThoai() == null || nhanVien.getSoDienThoai().isEmpty()) {
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NhanVien");
        databaseReference.child(nhanVien.getSoDienThoai()).setValue(nhanVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(Activity_ThongTin.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Activity_ThongTin.this, "Add failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private NhanVien nhanVienMoi(String anhIm) {
        String ten = edtTenNv.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String soDienThoai = edtSoDienThoaiNV.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        String luong = edtLuong.getText().toString();
        int luongInt = luong.isEmpty() ? 0 : Integer.parseInt(luong);

        return new NhanVien(soDienThoai, ten, matKhau, diaChi, anhIm, "1", luongInt);
    }
}
