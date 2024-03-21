/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

/**
 *
 * @author admin
 */
public class HinhThucThanhToanModel {
    public String ID;
    public String tenHinhThuc;
    public String moTa;

    public HinhThucThanhToanModel() {
    }

    public HinhThucThanhToanModel(String ID, String tenHinhThuc, String moTa) {
        this.ID = ID;
        this.tenHinhThuc = tenHinhThuc;
        this.moTa = moTa;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenHinhThuc() {
        return tenHinhThuc;
    }

    public void setTenHinhThuc(String tenHinhThuc) {
        this.tenHinhThuc = tenHinhThuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
