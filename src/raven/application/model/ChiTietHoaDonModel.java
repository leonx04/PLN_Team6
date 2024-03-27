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
    public ChiTietSanPhamModel mactsp = new ChiTietSanPhamModel();
    public SanPhamModel tenSP = new SanPhamModel();
    public MauSacModel mauSac = new MauSacModel();
    public KichCoModel size = new KichCoModel();
    public ChatLieuModel chatLieu = new ChatLieuModel();
    public ThuongHieuModel thuongHieu = new ThuongHieuModel();
    public ChiTietSanPhamModel ctsp = new ChiTietSanPhamModel(BigDecimal.ONE);
    public int soLuong;
    public BigDecimal thanhTien;
    public SanPhamModel maSP = new SanPhamModel();
    private int stt;

    public ChiTietHoaDonModel(String ID,
            SanPhamModel tenSP,
            MauSacModel mauSacModel,
            KichCoModel kichCoModel,
            ChatLieuModel chatLieuModel,
            ThuongHieuModel thuongHieuModel,
            ChiTietSanPhamModel donGia,
            int soLuong,
            BigDecimal thanhTien) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.mauSac = mauSacModel;
        this.size = kichCoModel;
        this.chatLieu = chatLieuModel;
        this.thuongHieu = thuongHieuModel;
        this.ctsp = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.maSP = maSP;
    }

    public ChiTietHoaDonModel() {
    }
    
    public ChiTietHoaDonModel(String ID, SanPhamModel tenSP, ChiTietSanPhamModel donGia, int soLuong, BigDecimal thanhTien) {
       this.ID = ID;
       this.tenSP = tenSP;
       this.ctsp = donGia;
       this.soLuong = soLuong;
       this.thanhTien = thanhTien;
    }

    public ChiTietHoaDonModel(ChiTietSanPhamModel mactsp, SanPhamModel tenSP, ChiTietSanPhamModel donGia, int soLuong, BigDecimal thanhTien) {
       this.mactsp = mactsp;
       this.tenSP = tenSP;
       this.ctsp = donGia;
       this.soLuong = soLuong;
       this.thanhTien = thanhTien;
    }

    public ChiTietSanPhamModel getMactsp() {
        return mactsp;
    }

    public ChiTietSanPhamModel getCtsp() {
        return ctsp;
    }

    public void setMactsp(ChiTietSanPhamModel mactsp) {
        this.mactsp = mactsp;
    }

    public void setCtsp(ChiTietSanPhamModel ctsp) {
        this.ctsp = ctsp;
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

    public ThuongHieuModel getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(ThuongHieuModel thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public ChiTietSanPhamModel getDonGia() {
        return ctsp;
    }

    public void setDonGia(ChiTietSanPhamModel donGia) {
        this.ctsp = donGia;
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

    public SanPhamModel getMaSP() {
        return maSP;
    }

    public void setMaSP(SanPhamModel maSP) {
        this.maSP = maSP;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
    
    

    public Object[] toData2() {
        return new Object[]{
            this.stt,
            this.ID,
            this.tenSP.getTenSP(),
            this.mauSac.getTenMS(),
            this.size.getTenSize(),
            this.chatLieu.getTenCL(),
            this.thuongHieu.getTenTH(),
            this.ctsp.getGiaBan(),
            this.soLuong,
            this.thanhTien
        };
    }
//
//    @Override
//    public String toString() {
//        return "ChiTietHoaDonModel{" + "ID=" + ID + ", tenSP=" + tenSP + ", mauSac=" + mauSac + ", size=" + size + ", chatLieu=" + chatLieu + ", thuongHieu=" + thuongHieu + ", ctsp=" + ctsp + ", soLuong=" + soLuong + ", thanhTien=" + thanhTien + ", stt=" + stt + '}';
//    }

    @Override
    public String toString() {
        return "ChiTietHoaDonModel{" + "ID=" + ID + ", mactsp=" + mactsp + ", tenSP=" + tenSP + ", mauSac=" + mauSac + ", size=" + size + ", chatLieu=" + chatLieu + ", thuongHieu=" + thuongHieu + ", ctsp=" + ctsp + ", soLuong=" + soLuong + ", thanhTien=" + thanhTien + ", maSP=" + maSP + ", stt=" + stt + '}';
    }
    

    public Object[] toData4() {
        return new Object[]{
            this.mactsp,
            this.tenSP.getTenSP(),
            this.ctsp.getGiaBan(),
            this.soLuong,
            this.thanhTien
        };
    }

}
