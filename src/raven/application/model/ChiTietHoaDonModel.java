/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class ChiTietHoaDonModel {
    public String ID;
    public SanPhamModel tenSP = new SanPhamModel();
    public ThuongHieuModel thuongHieu = new ThuongHieuModel();
    public MauSacModel mauSac = new MauSacModel();
    public KichCoModel size = new KichCoModel();
    public ChatLieuModel chatLieu = new ChatLieuModel();
    public BigDecimal donGia;
    public int soLuong;
    public BigDecimal thanhTien;
    private int stt;

    public ChiTietHoaDonModel(String ID, SanPhamModel tenSP, ThuongHieuModel thuongHieu, MauSacModel mauSac, KichCoModel size, ChatLieuModel chatLieu, BigDecimal donGia, int soLuong, BigDecimal thanhTien) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.thuongHieu = thuongHieu;
        this.mauSac = mauSac;
        this.size = size;
        this.chatLieu = chatLieu;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public ChiTietHoaDonModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public SanPhamModel getTenSP() {
        return tenSP;
    }

    public void setTenSP(SanPhamModel tenSP) {
        this.tenSP = tenSP;
    }

    public ThuongHieuModel getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(ThuongHieuModel thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public MauSacModel getMauSac() {
        return mauSac;
    }

    public void setMauSac(MauSacModel mauSac) {
        this.mauSac = mauSac;
    }

    public KichCoModel getSize() {
        return size;
    }

    public void setSize(KichCoModel size) {
        this.size = size;
    }

    public ChatLieuModel getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(ChatLieuModel chatLieu) {
        this.chatLieu = chatLieu;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData(){
        return new Object[]{
            this.stt,
            this.ID,
            this.tenSP.getTenSP(),
            this.thuongHieu.getTenTH(),
            this.mauSac.getTenMS(),
            this.size.getTenSize(),
            this.chatLieu.getTenCL(),
            this.donGia,
            this.soLuong,
            this.thanhTien
        };
    }
}
