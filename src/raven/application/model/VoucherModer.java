/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class VoucherModer {
    public String ID;
    public String tenVoucher;
    public Integer soLuong;
    public String loaiVoucher;
    public BigDecimal mucGiamGia;
    public String moTa;
    public Date ngayBatDau;
    public Date ngayKetThuc;
    public String trangThai;
    private int STT;

    public VoucherModer() {
    }

    
    public VoucherModer(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }
    public String getLoaiVoucher() {
        return loaiVoucher;
    }

    public VoucherModer(BigDecimal mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }
    

    public VoucherModer(String ID, String tenVoucher, int soLuong, String loaiVoucher, BigDecimal mucGiamGia, String moTa, Date ngayBatDau, Date ngayKetThuc) {
        this.ID = ID;
        this.tenVoucher = tenVoucher;
        this.soLuong = soLuong;
        this.loaiVoucher = loaiVoucher;
        this.mucGiamGia = mucGiamGia;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }


    public void setLoaiVoucher(String loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public BigDecimal getMucGiamGia() {
        return mucGiamGia;
    }

    public void setMucGiamGia(BigDecimal mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }



    public Object[] toData(){
        return new Object[]{
            this.STT,
            this.ID,
            this.tenVoucher,
            this.soLuong,
            this.loaiVoucher,
            this.mucGiamGia,
            this.moTa
        };
    }
}
