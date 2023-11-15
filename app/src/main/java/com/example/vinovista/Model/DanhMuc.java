package com.example.vinovista.Model;

import java.util.HashMap;
import java.util.Map;

public class DanhMuc {
    String idDanhMuc,tenDanhMuc,anh;
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tenDanhMuc", tenDanhMuc);
        result.put("anh", anh);

        return result;
    }

    @Override
    public String toString() {
        return "danhMuc{" +
                "idDanhMuc='" + idDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", anh='" + anh + '\'' +
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
