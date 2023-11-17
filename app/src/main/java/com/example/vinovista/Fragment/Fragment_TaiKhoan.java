package com.example.vinovista.Fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinovista.Activity.Activity_ThongTin;
import com.example.vinovista.Activity.DangNhap;
import com.example.vinovista.Adapter.adapter_NhanVien;
import com.example.vinovista.Model.NhanVien;
import com.example.vinovista.R;
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
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TaiKhoan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TaiKhoan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_TaiKhoan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TaiKhoan.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TaiKhoan newInstance(String param1, String param2) {
        Fragment_TaiKhoan fragment = new Fragment_TaiKhoan();
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
    public static String SHARED_PRE = "shared_pre";
    ImageButton imbAnhNhanVienMoi;
    TextView edtTenNv, edtSoDienThoaiNV, edtDiaChi, edtMatKhau, edtLuong;
    Button btnSave, btnLogout;
    ProgressBar progressBar_NV;
    private String anh, idNhanVien;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Uri currentImageUri = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__tai_khoan, container, false);
        setControl(view);
        setEvent();

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
            idNhanVien = sharedPreferences.getString("id_staff", "");
            if (!idNhanVien.isEmpty()) {
                getProfile(idNhanVien);
            }
        }

        return view;
    }

    private void setEvent() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences(DangNhap.SHARED_PRE, getActivity().MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                getActivity().finish();
                                Intent intent = new Intent(getContext(), DangNhap.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không", null)
                        .setIcon(R.drawable.warning)
                        .show();

            }
        });

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
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), currentImageUri);
                        uploadImageToFirebase(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Error in image processing: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Sử dụng ảnh hiện tại nếu người dùng không chọn ảnh mới
                    try {
                        LuuThongTin(nhanVienMoi(anh));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error in saving employee info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void setControl(View view) {
        imbAnhNhanVienMoi = view.findViewById(R.id.imbAnhNhanVienMoi_NV_profile);
        edtTenNv = view.findViewById(R.id.edtTenNv_NV_profile);
        edtSoDienThoaiNV = view.findViewById(R.id.edtSoDienThoaiNV_NV_profile);
        edtDiaChi = view.findViewById(R.id.edtDiaChi_NV_profile);
        edtMatKhau = view.findViewById(R.id.edtMatKhau_NV_profile);
        edtLuong = view.findViewById(R.id.edtLuong_NV_profile);
        btnSave = view.findViewById(R.id.btnSave_NV_profile);
        btnLogout = view.findViewById(R.id.btnLogout_NV_profile);
        progressBar_NV = view.findViewById(R.id.progressBar_NV_profile_xx);
    }

    void getProfile(String idNhanVien) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NhanVien").child(idNhanVien);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NhanVien nhanVien1 = snapshot.getValue(NhanVien.class);
                    Picasso.get().load(nhanVien1.getAnh()).into(imbAnhNhanVienMoi, new com.squareup.picasso.Callback() {
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
                    edtTenNv.setText(nhanVien1.getHoTen());
                    edtDiaChi.setText(nhanVien1.getDiaChi());
                    edtLuong.setText(String.valueOf(nhanVien1.getLuong()));
                    edtMatKhau.setText(nhanVien1.getMatKhau());
                    edtSoDienThoaiNV.setText(nhanVien1.getSoDienThoai());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //lưu nhân viên mới
    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
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
                Toast.makeText(getContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                try {
                    LuuThongTin(nhanVienMoi(anh));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error in saving employee info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void LuuThongTin(NhanVien nhanVien) {
        if (nhanVien.getSoDienThoai() == null || nhanVien.getSoDienThoai().isEmpty()) {
            Toast.makeText(getContext(), "Phone number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NhanVien");
        databaseReference.child(nhanVien.getSoDienThoai()).setValue(nhanVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Add failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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