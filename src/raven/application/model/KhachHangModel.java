/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.util.Date;

/**
 *
 * @author OS
 */
public class KhachHangModel {
    private String ma, ten, sodt, diachi, email;
    private Date ngaysinh;
    private boolean gioitinh;

    public KhachHangModel() {
    }

    public KhachHangModel(String ma, String ten, String sodt, String diachi, String email, Date ngaysinh, boolean gioitinh) {
        this.ma = ma;
        this.ten = ten;
        this.sodt = sodt;
        this.diachi = diachi;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
    }

    public KhachHangModel(String ten, String sodt, String diachi, String email, Date ngaysinh, boolean gioitinh) {
        this.ten = ten;
        this.sodt = sodt;
        this.diachi = diachi;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
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

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    
}
