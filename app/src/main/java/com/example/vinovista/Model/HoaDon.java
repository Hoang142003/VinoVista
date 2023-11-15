package com.example.vinovista.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HoaDon implements Serializable {
    String idHoaDon, tenKhachHang, soDienThoai, ngayMua, nhanVien;
    Double tongHoaDon;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tenKhachHang", tenKhachHang);
        result.put("soDienThoai", soDienThoai);
        result.put("ngayMua", ngayMua);
        result.put("tongHoaDon", tongHoaDon);
        return result;
    }

    @Override
    public String toString() {
        return "hoaDon{" +
                "idHoaDon='" + idHoaDon + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", ngayMua='" + ngayMua + '\'' +
                ", nhanVien='" + nhanVien + '\'' +
                ", tongHoaDon=" + tongHoaDon +
                '}';
    }

    public HoaDon() {
    }

    public HoaDon(String idHoaDon, String tenKhachHang, String soDienThoai, String ngayMua, String nhanVien,Double tongHoaDon) {
        this.idHoaDon = idHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.ngayMua = ngayMua;
        this.nhanVien = nhanVien;
        this.tongHoaDon = tongHoaDon;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Double getTongHoaDon() {
        return tongHoaDon;
    }

    public void setTongHoaDon(Double tongHoaDon) {
        this.tongHoaDon = tongHoaDon;
    }
}
