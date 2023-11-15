package com.example.vinovista.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vinovista.Model.DanhMuc;
import com.example.vinovista.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Activity_Sua_Danh_Muc extends AppCompatActivity {
    ImageView ivDanhMuc;
    ImageButton btn_back;
    EditText edtTenDanhMuc;
    Button btnHoanTat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://vinovista-ae7f9.appspot.com/pictures");
                StorageReference storage_ref=storageReference.child("image"+System.currentTimeMillis()+".png");

                //Upload anh
                // Get the data from an ImageView as bytes
                ivDanhMuc.setDrawingCacheEnabled(true);
                ivDanhMuc.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) ivDanhMuc.getDrawable()).getBitmap();
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


                        Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoLink = uri.toString();
                                //Sau khi dang anh xong thi se them san pham
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhMuc");
                                Intent intent = getIntent();
                                DanhMuc danhMuc = (DanhMuc) intent.getSerializableExtra("id_danh_muc");
                                danhMuc.setTenDanhMuc(edtTenDanhMuc.getText().toString());
                                danhMuc.setAnh(photoLink);
                                ref.child(danhMuc.getIdDanhMuc()).setValue(danhMuc, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(Activity_Sua_Danh_Muc.this, "Sửa danh mục thành công", Toast.LENGTH_SHORT).show();
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


    private void setControl() {
        ivDanhMuc = findViewById(R.id.ivDanhMuc);
        btn_back = findViewById(R.id.btn_back);
        edtTenDanhMuc = findViewById(R.id.edtTenDanhMuc);
        btnHoanTat = findViewById(R.id.btnHoanTat);
        Intent intent = getIntent();
        DanhMuc danhMuc = (DanhMuc) intent.getSerializableExtra("id_danh_muc");
        edtTenDanhMuc.setText(danhMuc.getTenDanhMuc().toString());
        Picasso.get().load(
                        danhMuc.getAnh()
                ).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(ivDanhMuc);
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                Picasso.get().load(imageUri).into(ivDanhMuc);
            }
        }
    });

}