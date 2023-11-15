package com.example.vinovista.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NhanVien implements Serializable {
    String soDienThoai, matKhau, diaChi, anh, hoTen,idLoaiNhanVien;
        double luong;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("matKhau", matKhau);
        result.put("diaChi", diaChi);
        result.put("anhSanPham", anh);
        result.put("hoTen", hoTen);
        result.put("luong", luong);
        result.put("idLoaiNhanVien", idLoaiNhanVien);
        return result;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "soDienThoai='" + soDienThoai + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", anhSanPham='" + anh + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", idLoaiNhanVien='" + idLoaiNhanVien + '\'' +
                ", luong=" + luong +
                '}';
    }

    public NhanVien() {
    }

    public NhanVien(String soDienThoai, String matKhau, String diaChi, String anh, String hoTen, String idLoaiNhanVien, double luong) {
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.diaChi = diaChi;
        this.anh = anh;
        this.hoTen = hoTen;
        this.idLoaiNhanVien = idLoaiNhanVien;
        this.luong = luong;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }
}
