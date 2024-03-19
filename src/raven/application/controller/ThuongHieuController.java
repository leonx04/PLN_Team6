package raven.application.controller;

import java.util.List;
import raven.application.model.ThuongHieuModel;
import raven.application.service.ThuongHieuService;

public class ThuongHieuController {
    private ThuongHieuService thuongHieuService;

    public ThuongHieuController() {
        this.thuongHieuService = new ThuongHieuService();
    }

    public List<ThuongHieuModel> getAllThuongHieu() {
        return thuongHieuService.getALLThuongHieu();
    }

    public List<ThuongHieuModel> getThuongHieuByTen(String tenThuongHieu) {
        return thuongHieuService.getIDByTenTH(tenThuongHieu);
    }

    public int addThuongHieu(ThuongHieuModel thuongHieu) {
        return thuongHieuService.insert(thuongHieu);
    }

    public String generateNewThuongHieuID() {
        return thuongHieuService.getNewIDTH();
    }

    public int updateThuongHieu(ThuongHieuModel thuongHieu, String ma) {
        return thuongHieuService.update(thuongHieu, ma);
    }

    public int deleteThuongHieu(String ma) {
        return thuongHieuService.delete(ma);
    }
}
