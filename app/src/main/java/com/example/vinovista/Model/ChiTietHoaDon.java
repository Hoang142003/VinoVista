package com.example.vinovista.Model;

public class ChiTietHoaDon {
    String idHoaDon, idSanPham;
    int soLuong, gia;

    @Override
    public String toString() {
        return "chiTietHoaDon{" +
                "idHoaDon='" + idHoaDon + '\'' +
                ", idSanPham='" + idSanPham + '\'' +
                ", soLuong=" + soLuong +
                ", gia=" + gia +
                '}';
    }

    public ChiTietHoaDon(String idHoaDon, String idSanPham, int soLuong, int gia) {
        this.idHoaDon = idHoaDon;
        this.idSanPham = idSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public ChiTietHoaDon() {
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
