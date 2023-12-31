package com.example.vinovista.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SanPham implements Serializable {
    String idSanPham, tenSanPham, moTa, idDanhMuc,anhSanPham;
    int soLuong;
    int giaGoc;
    int giaSale;
    int soLuongDaBan;

    public int getSl_dat_hang() {
        return sl_dat_hang;
    }

    public void setSl_dat_hang(int sl_dat_hang) {
        this.sl_dat_hang = sl_dat_hang;
    }

    int sl_dat_hang;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tenSanPham", tenSanPham);
        result.put("moTa", moTa);
        result.put("idDanhMuc", idDanhMuc);
        result.put("soLuong", soLuong);
        result.put("giaGoc", giaGoc);
        result.put("giaSale", giaSale);
        result.put("soLuongDaBan", soLuongDaBan);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanPham)) return false;
        SanPham sanPham = (SanPham) o;
        return getIdSanPham().equals(sanPham.getIdSanPham());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdSanPham());
    }

    @Override
    public String toString() {
        return "sanPham{" +
                "idSanPham='" + idSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", moTa='" + moTa + '\'' +
                ", idDanhMuc='" + idDanhMuc + '\'' +
                ", soLuong=" + soLuong +
                ", giaGoc=" + giaGoc +
                ", giaSale=" + giaSale +
                ", soLuongDaBan=" + soLuongDaBan +
                '}';
    }

    public SanPham() {
    }

    public SanPham(String idSanPham, String tenSanPham, String moTa, String idDanhMuc, String anhSanPham, int soLuong, int giaGoc, int giaSale, int soLuongDaBan) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.idDanhMuc = idDanhMuc;
        this.anhSanPham = anhSanPham;
        this.soLuong = soLuong;
        this.giaGoc = giaGoc;
        this.giaSale = giaSale;
        this.soLuongDaBan = soLuongDaBan;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(int giaGoc) {
        this.giaGoc = giaGoc;
    }

    public int getGiaSale() {
        return giaSale;
    }

    public void setGiaSale(int giaSale) {
        this.giaSale = giaSale;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }
}
