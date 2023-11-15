package com.example.vinovista.Model;

public class LoaiNhanVien {
    String idLoaiNhanVien,tenLoaiNhanVien;

    @Override
    public String toString() {
        return "LoaiNhanVien{" +
                "idLoaiNhanVien='" + idLoaiNhanVien + '\'' +
                ", tenLoaiNhanVien='" + tenLoaiNhanVien + '\'' +
                '}';
    }

    public LoaiNhanVien(String idLoaiNhanVien, String tenLoaiNhanVien) {
        this.idLoaiNhanVien = idLoaiNhanVien;
        this.tenLoaiNhanVien = tenLoaiNhanVien;
    }

    public LoaiNhanVien() {
    }

    public String getIdLoaiNhanVien() {
        return idLoaiNhanVien;
    }

    public void setIdLoaiNhanVien(String idLoaiNhanVien) {
        this.idLoaiNhanVien = idLoaiNhanVien;
    }

    public String getTenLoaiNhanVien() {
        return tenLoaiNhanVien;
    }

    public void setTenLoaiNhanVien(String tenLoaiNhanVien) {
        this.tenLoaiNhanVien = tenLoaiNhanVien;
    }
}
