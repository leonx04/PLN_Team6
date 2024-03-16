/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.controller;

import java.util.List;
import raven.application.model.SanPhamModel;
import raven.application.service.SanPhamService;

/**
 *
 * @author dungn
 */
public class SanPhamController {
    private SanPhamService sanPhamService;

    public SanPhamController() {
        this.sanPhamService = new SanPhamService();
    }

    public List<SanPhamModel> getAllSanPham() {
        return sanPhamService.getAllSP();
    }

    public List<SanPhamModel> getSanPhamByTrangThai(String trangThai) {
        return sanPhamService.getAllSPByTrangThai(trangThai);
    }

    public List<SanPhamModel> getSanPhamByTenSP(String tenSP) {
        return sanPhamService.getIDByTenSP(tenSP);
    }

    public String generateNewSanPhamID() {
        return sanPhamService.getNewSanPhamID();
    }

    public int addSanPham(SanPhamModel sanPham) {
        return sanPhamService.insert(sanPham);
    }

    public int updateSanPham(SanPhamModel sanPham, String ma) {
        return sanPhamService.update(sanPham, ma);
    }

    public int deleteSanPham(String ma) {
        return sanPhamService.delete(ma);
    }
}
