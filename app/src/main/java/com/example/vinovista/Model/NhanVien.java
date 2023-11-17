package com.example.vinovista.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NhanVien implements Serializable {
    String soDienThoai, matKhau, diaChi, anh, hoTen, idLoaiNhanVien,token;
    int luong;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NhanVien)) return false;
        NhanVien nhanVien = (NhanVien) o;
        return getSoDienThoai().equals(nhanVien.getSoDienThoai());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSoDienThoai());
    }

    public String getIdLoaiNhanVien() {
        return idLoaiNhanVien;
    }

    public void setIdLoaiNhanVien(String idLoaiNhanVien) {
        this.idLoaiNhanVien = idLoaiNhanVien;
    }

    public NhanVien(String soDienThoai, String hoTen, String matKhau, String diaChi, String anh, String idLoaiNhanVien, int luong) {
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

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }
}
