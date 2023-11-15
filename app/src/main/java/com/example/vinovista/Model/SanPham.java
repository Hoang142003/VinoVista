package com.example.vinovista.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SanPham implements Serializable {
    String idSanPham, tenSanPham, moTa, idDanhMuc;
    int soLuong, giaGoc, giaSale, soLuongDaBan;

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

    public SanPham(String idSanPham, String tenSanPham, String moTa, String idDanhMuc, int soLuong, int giaGoc, int giaSale, int soLuongDaBan) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.idDanhMuc = idDanhMuc;
        this.soLuong = soLuong;
        this.giaGoc = giaGoc;
        this.giaSale = giaSale;
        this.soLuongDaBan = soLuongDaBan;
    }
}
