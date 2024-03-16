
package raven.application.model;

/**
 *
 * @author dungn
 */
public class SanPhamModel {
    public String ID;
    public String tenSP;
    public String MoTa;
    public String TrangThai;
    private int stt;

    public SanPhamModel() {
    }

    public SanPhamModel(String ID, String tenSP, String MoTa) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.MoTa = MoTa;
    }

    public SanPhamModel(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getID() {
        return ID;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getMoTa() {
        return MoTa;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[] {
                this.stt,
                this.ID,
                this.tenSP,
                this.MoTa
        };
    }

}
