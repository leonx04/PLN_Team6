/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.math.BigDecimal;

/**
 *
 * @author dungn
 */
public class ChiTietSanPhamModel {

    public String ID;
    public SanPhamModel tenSP = new SanPhamModel();
    public MauSacModel mauSac = new MauSacModel();
    public KichCoModel kichCo = new KichCoModel();
    public ChatLieuModel chatLieu = new ChatLieuModel();
    public ThuongHieuModel thuongHieu = new ThuongHieuModel();
    public BigDecimal giaBan;
    public int SoLuongTon;
    public String MoTa;
    public int stt;

    public ChiTietSanPhamModel(String ID, SanPhamModel tenSP, MauSacModel mauSac, KichCoModel kichCo,
            ChatLieuModel chatLieu, ThuongHieuModel thuongHieu, BigDecimal giaBan, int SoLuongTon, String MoTa) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.chatLieu = chatLieu;
        this.thuongHieu = thuongHieu;
        this.giaBan = giaBan;
        this.SoLuongTon = SoLuongTon;
        this.MoTa = MoTa;
    }

    public ChiTietSanPhamModel(BigDecimal donGia) {
        this.giaBan = donGia;
    }

    public ChiTietSanPhamModel() {
    }
    

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getID() {
        return ID;
    }

    public SanPhamModel getTenSP() {
        return tenSP;
    }

    public MauSacModel getMauSac() {
        return mauSac;
    }

    public KichCoModel getKichCo() {
        return kichCo;
    }

    public ChatLieuModel getChatLieu() {
        return chatLieu;
    }

    public ThuongHieuModel getThuongHieu() {
        return thuongHieu;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public int getSoLuongTon() {
        return SoLuongTon;
    }

    public int getStt() {
        return stt;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenSP(SanPhamModel tenSP) {
        this.tenSP = tenSP;
    }

    public void setMauSac(MauSacModel mauSac) {
        this.mauSac = mauSac;
    }

    public void setKichCo(KichCoModel kichCo) {
        this.kichCo = kichCo;
    }

    public void setChatLieu(ChatLieuModel chatLieu) {
        this.chatLieu = chatLieu;
    }

    public void setThuongHieu(ThuongHieuModel thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public void setSoLuongTon(int SoLuongTon) {
        this.SoLuongTon = SoLuongTon;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[] { this.stt, this.ID, this.tenSP.getTenSP(), this.mauSac.getTenMS(),
                this.kichCo.getTenSize(), this.chatLieu.getTenCL(), this.thuongHieu.getTenTH(), this.giaBan,
                this.SoLuongTon, this.MoTa };
    }
    
    public Object[] toData2() {
        return new Object[] {  this.ID, this.tenSP.getTenSP(), this.mauSac.getTenMS(),
                this.kichCo.getTenSize(), this.chatLieu.getTenCL(), this.thuongHieu.getTenTH(), this.giaBan,
                this.SoLuongTon, this.MoTa };
    }
}
