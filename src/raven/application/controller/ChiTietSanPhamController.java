package raven.application.controller;

import java.util.List;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.service.ChiTietSanPhamService;

public class ChiTietSanPhamController {
    private ChiTietSanPhamService chiTietSanPhamService;

    public ChiTietSanPhamController() {
        this.chiTietSanPhamService = new ChiTietSanPhamService();
    }

    public List<ChiTietSanPhamModel> getAllChiTietSanPham() {
        return chiTietSanPhamService.getAllCTSP();
    }

    public String generateNewChiTietSanPhamID() {
        return chiTietSanPhamService.getNewSPCTID();
    }

    public int addChiTietSanPham(ChiTietSanPhamModel chiTietSanPham) {
        return chiTietSanPhamService.insert(chiTietSanPham);
    }

    public int updateChiTietSanPham(ChiTietSanPhamModel chiTietSanPham) {
        return chiTietSanPhamService.update(chiTietSanPham);
    }

    public int deleteChiTietSanPham(String id) {
        return chiTietSanPhamService.delete(id);
    }
}
