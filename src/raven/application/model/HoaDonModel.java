/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class HoaDonModel {

    public String ID;
    public Date ngayTao;
    public NhanVienModel tenNV = new NhanVienModel();
    public KhachHangModel tenKH = new KhachHangModel();
    public VoucherModer tenVoucher = new VoucherModer();
    public BigDecimal tongTien;
    public String hinhThucThanhToan;
    public String trangThai;
    private int stt;

    public HoaDonModel(String ID, Date ngayTao, NhanVienModel tenNV, KhachHangModel tenKH, VoucherModer tenVoucher, BigDecimal tongTien, String hinhThucThanhToan) {
        this.ID = ID;
        this.ngayTao = ngayTao;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.tenVoucher = tenVoucher;
        this.tongTien = tongTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public HoaDonModel() {
    }

    public HoaDonModel(String ID, Date ngayTao, NhanVienModel tenNV, KhachHangModel tenKH, BigDecimal tongTien, VoucherModer tenVoucher, String hinhThucThanhToan, String trangThai) {
        this.ID = ID;
        this.ngayTao = ngayTao;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.tenVoucher = tenVoucher;
        this.tongTien = tongTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.trangThai = trangThai;
    }

    public HoaDonModel(String ID, Date ngayTao, NhanVienModel tenNV, KhachHangModel tenKH, VoucherModer tenVoucher, BigDecimal tongTien, String hinhThucThanhToan, String trangThai) {

        this.ID = ID;
        this.ngayTao = ngayTao;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.tongTien = tongTien;
        this.tenVoucher = tenVoucher;

        this.hinhThucThanhToan = hinhThucThanhToan;
        this.trangThai = trangThai;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public NhanVienModel getTenNV() {
        return tenNV;
    }

    public void setTenNV(NhanVienModel tenNV) {
        this.tenNV = tenNV;
    }

    public KhachHangModel getTenKH() {
        return tenKH;
    }

    public void setTenKH(KhachHangModel tenKH) {
        this.tenKH = tenKH;
    }

    public VoucherModer getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(VoucherModer tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[]{
            this.stt,
            this.ID,
            this.ngayTao,
            this.tenNV.getHoTen(),
            this.tenKH.getTen(),
            this.tenVoucher.getTenVoucher(),
            this.tongTien,
            this.hinhThucThanhToan};

    }

    public Object[] toData2() {
        return new Object[]{
            this.stt,
            this.ID,
            this.ngayTao,
            this.tenNV.getHoTen(),
            this.tenKH.getTen(),
            this.tenVoucher.getTenVoucher(),
            this.tongTien,
            this.hinhThucThanhToan,
            this.trangThai
        };
    }
}
