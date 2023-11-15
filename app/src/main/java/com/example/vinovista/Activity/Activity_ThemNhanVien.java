package com.example.vinovista.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinovista.Adapter.PasswordEncoder;
import com.example.vinovista.Model.NhanVien;
import com.example.vinovista.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Activity_ThemNhanVien extends AppCompatActivity {
    ImageButton imbAnhNhanVienMoi;
    TextView edtTenNv;
    TextView edtSoDienThoaiNV;
    TextView edtDiaChi;
    TextView edtMatKhau;
    TextView edtLuong;
    Button btnSave;
    private String anh = null;
    NhanVien nhanVien;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Uri currentImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        Intent intent = getIntent();
        nhanVien = (NhanVien) intent.getSerializableExtra("nhanVien");
        setControl();
        setEvent();
    }

    void fillData(NhanVien nhanVien) {
        edtTenNv.setText(nhanVien.getHoTen());
        edtTenNv.setEnabled(false);
        edtLuong.setText(String.valueOf(nhanVien.getLuong()));
        edtDiaChi.setText(nhanVien.getDiaChi());
        edtLuong.setText(nhanVien.getLuong());
        edtLuong.setEnabled(false);
        edtMatKhau.setText("");

    }

    private void setControl() {
        imbAnhNhanVienMoi = findViewById(R.id.imbAnhNhanVienMoi);
        edtTenNv = findViewById(R.id.edtTenNv);
        edtSoDienThoaiNV = findViewById(R.id.edtSoDienThoaiNV);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtLuong = findViewById(R.id.edtLuong);
        btnSave = findViewById(R.id.btnSave);
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
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void luuThongTin(NhanVien nhanVien) {
        // lấy số điện thoại từ đối tượng nhân viên
        String soDienThoai = nhanVien.getSoDienThoai();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NhanVien");
        // dùng số điện thoại là key
        databaseReference.child(soDienThoai).setValue(nhanVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(Activity_ThemNhanVien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Activity_ThemNhanVien.this, "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    NhanVien nhanVienMoi() {
        String ten = edtTenNv.getText().toString(),
                diaChi = edtDiaChi.getText().toString(),
                soDienThoai = edtSoDienThoaiNV.getText().toString(),
                matKhau = PasswordEncoder.encrypt(edtMatKhau.getText().toString()),
                loaiNhanVien = "1",
                luong = String.valueOf(edtLuong.getText());
        anh = anh;
        NhanVien nhanVien = new NhanVien(soDienThoai, ten, matKhau, diaChi, anh, loaiNhanVien, Integer.parseInt(luong));
        return nhanVien;
    }

    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } else if (which == 1) {
                    // Gallery
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
                // Handle image from Camera
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Convert bitmap to URI
                currentImageUri = getImageUri(imageBitmap);
                imbAnhNhanVienMoi.setImageURI(currentImageUri);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Handle image from Gallery
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
                anh = uri.toString();  // Update the anh variable with the URL
                Toast.makeText(Activity_ThemNhanVien.this, "Image uploaded successfully!", Toast.LENGTH_SHORT).show();

                luuThongTin(nhanVienMoi());
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(Activity_ThemNhanVien.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
        });
    }

}
