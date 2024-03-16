/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class HoaDonModel {
    private String ID;
    private String MaNhanVien;
    private String MaKhachHang;
    private String MaVoucher;
    private String MaHinhThucThanhToan;
    private Integer TongTien;
    private LocalDateTime NgayTao;
    private LocalDateTime NgaySua;
    private String TrangThai;
    private int STT;

    public HoaDonModel() {
    }

    public HoaDonModel(String ID, String MaNhanVien, String MaKhachHang, String MaVoucher, String MaHinhThucThanhToan, Integer TongTien, LocalDateTime NgayTao, LocalDateTime NgaySua, String TrangThai, int STT) {
        this.ID = ID;
        this.MaNhanVien = MaNhanVien;
        this.MaKhachHang = MaKhachHang;
        this.MaVoucher = MaVoucher;
        this.MaHinhThucThanhToan = MaHinhThucThanhToan;
        this.TongTien = TongTien;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.TrangThai = TrangThai;
        this.STT = STT;
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

    public Integer getTongTien() {
        return TongTien;
    }

    public void setTongTien(Integer TongTien) {
        this.TongTien = TongTien;
    }

    public LocalDateTime getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(LocalDateTime NgayTao) {
        this.NgayTao = NgayTao;
    }

    public LocalDateTime getNgaySua() {
        return NgaySua;
    }

    public void setNgaySua(LocalDateTime NgaySua) {
        this.NgaySua = NgaySua;
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
}
