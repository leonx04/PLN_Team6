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
public class KhachHangModel {

    private String ma, ten, sodt, diachi, email;
    private String gioitinh ;
    private int stt;

    public KhachHangModel() {
    }

    public KhachHangModel(String tenKH) {
        this.ten = tenKH;
    }

    public KhachHangModel(String ma, String ten, String sodt, String diachi, String email, String gioitinh) {
        this.ma = ma;
        this.ten = ten;
        this.sodt = sodt;
        this.diachi = diachi;
        this.email = email;
        this.gioitinh = gioitinh;
    }

    public KhachHangModel(String ten, String sodt, String diachi, String email, Date ngaysinh, String gioitinh) {
        this.ten = ten;
        this.sodt = sodt;
        this.diachi = diachi;
        this.email = email;
        this.gioitinh = gioitinh;
    }

    public Object[] toData() {
        return new Object[]{
            this.stt,
            this.ma,
            this.ten,
            this.sodt,
            this.diachi,
            this.email,
            this.gioitinh
        };
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    private static KhachHangModel selectedKhachHang;
    // Các thuộc tính và phương thức khác
    
    public static KhachHangModel getSelectedKhachHang() {
        return selectedKhachHang;
    }

    public static void setSelectedKhachHang(KhachHangModel kh) {
        selectedKhachHang = kh;
    }

    public Object toLowerCase() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
