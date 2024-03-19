/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.util.Date;

/**
 *
 * @author acer
 */
public class NhanVienModel {
    private String id_NhanVien;
    private String hoTen;
    private boolean gioiTinh;
    private Date namSinh ;
    private String diaChi ;
    private String sdt ;
    private String trangThai ;
    private String matkhau ;
    private String email;
    private String chucVu ;

    public NhanVienModel() {
    }

    public NhanVienModel(String id_NhanVien, String hoTen, boolean gioiTinh, Date namSinh, String diaChi, String sdt, String trangThai, String matkhau, String email, String chucVu) {
        this.id_NhanVien = id_NhanVien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.namSinh = namSinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThai = trangThai;
        this.matkhau = matkhau;
        this.email = email;
        this.chucVu = chucVu;
    }

    public String getId_NhanVien() {
        return id_NhanVien;
    }

    public void setId_NhanVien(String id_NhanVien) {
        this.id_NhanVien = id_NhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Date namSinh) {
        this.namSinh = namSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public String toString() {
        return "NhanVienModel{" + "id_NhanVien=" + id_NhanVien + ", hoTen=" + hoTen + ", gioiTinh=" + gioiTinh + ", namSinh=" + namSinh + ", diaChi=" + diaChi + ", sdt=" + sdt + ", trangThai=" + trangThai + ", matkhau=" + matkhau + ", email=" + email + ", chucVu=" + chucVu + '}';
    }
    
    
}
