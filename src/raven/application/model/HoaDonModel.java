/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.security.Timestamp;
import java.sql.Date;

/**
 *
 * @author admin
 */
public class HoaDonModel {

    public String ID;
    public String MaNhanVien;
    public String MaKhachHang;
    public String MaVoucher;
    public String MaHinhThucThanhToan;
    public BigDecimal TongTien;
    public LocalDateTime NgayTao;
    public String TrangThai;
    private int STT;

    public HoaDonModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(String MaKhachHang) {
        this.MaKhachHang = MaKhachHang;
    }

    public String getMaVoucher() {
        return MaVoucher;
    }

    public void setMaVoucher(String MaVoucher) {
        this.MaVoucher = MaVoucher;
    }

    public String getMaHinhThucThanhToan() {
        return MaHinhThucThanhToan;
    }

    public void setMaHinhThucThanhToan(String MaHinhThucThanhToan) {
        this.MaHinhThucThanhToan = MaHinhThucThanhToan;
    }

    public BigDecimal getTongTien() {
        return TongTien;
    }

    public void setTongTien(BigDecimal TongTien) {
        this.TongTien = TongTien;
    }

    public LocalDateTime getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(LocalDateTime NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public Object[] toData() {
        return new Object[]{
            this.STT,
            this.NgayTao,
            this.MaNhanVien,
            this.MaKhachHang,
        }
    }
}
