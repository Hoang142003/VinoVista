package com.example.vinovista.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class  DanhMuc implements Serializable {
    String idDanhMuc,tenDanhMuc,anh;

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tenDanhMuc", tenDanhMuc);
        result.put("anhSanPham", anh);

        return result;
    }

    @Override
    public String toString() {
        return "danhMuc{" +
                "idDanhMuc='" + idDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", anhSanPham='" + anh + '\'' +
                '}';
    }

    public DanhMuc(String idDanhMuc, String tenDanhMuc, String anh) {
        this.idDanhMuc = idDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.anh = anh;
    }

    public DanhMuc() {
    }
}
