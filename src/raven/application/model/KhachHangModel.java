/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

/**
 *
 * @author OS
 */
public class KhachHangModel {
    String ma, ten, gioitinh, sodt, diachi, email, ngaysinh;

    public KhachHangModel() {
    }

    public KhachHangModel(String ma, String ten, String gioitinh, String sodt, String diachi, String email, String ngaysinh) {
        this.ma = ma;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.sodt = sodt;
        this.diachi = diachi;
        this.email = email;
        this.ngaysinh = ngaysinh;
    }

    public KhachHangModel(String ten, String gioitinh, String sodt, String diachi, String email, String ngaysinh) {
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.sodt = sodt;
        this.diachi = diachi;
        this.email = email;
        this.ngaysinh = ngaysinh;
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

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
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

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
    
    
}
