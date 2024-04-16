/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

/**
 *
 * @author dungn
 */
public class KichCoModel {
    public String ID;
    public String TenSize;
    public String MoTa;
    public int stt;

    public KichCoModel() {
    }

    public KichCoModel(String ID, String TenSize, String MoTa) {
        this.ID = ID;
        this.TenSize = TenSize;
        this.MoTa = MoTa;
    }

    public KichCoModel(String kichco) {
        this.TenSize = kichco;
    }

    public String getID() {
        return ID;
    }

    public String getTenSize() {
        return TenSize;
    }

    public String getMoTa() {
        return MoTa;
    }

    public int getStt() {
        return stt;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenSize(String TenSize) {
        this.TenSize = TenSize;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[]{
            this.stt,
            this.ID,
            this.TenSize,
            this.MoTa
        };
    }

}
