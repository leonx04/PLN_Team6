/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.ChatLieuModel;
import raven.application.model.ChiTietHoaDonModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.HoaDonModel;
import raven.application.model.KhachHangModel;
import raven.application.model.KichCoModel;
import raven.application.model.MauSacModel;
import raven.application.model.NhanVienModel;
import raven.application.model.SanPhamModel;
import raven.application.model.ThuongHieuModel;
import raven.application.model.VoucherModer;
import raven.connect.DBConnect;

/**
 *
 * @author admin
 */
public class BanHangService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<HoaDonModel> getAllHD() {
        sql = "SELECT        HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan\n"
                + "FROM            HOADON INNER JOIN\n"
                + "                         NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID INNER JOIN\n"
                + "                         KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID INNER JOIN\n"
                + "                         VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<ChiTietHoaDonModel> getAllGH() {
//        sql = "SELECT        SANPHAM.ID AS MaSanPham, SANPHAM.TenSanPham as TenSanPham, SANPHAMCHITIET.GiaBan as DonGia, HOADONCHITIET.SoLuong as SoLuong, HOADONCHITIET.ThanhTien as ThanhTien\n"
//                + "FROM            HOADONCHITIET INNER JOIN\n"
//                + "                         SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\n"
//                + "                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID";
//        try {
//            con = DBConnect.getConnection();
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//            List<ChiTietHoaDonModel> listCTHD = new ArrayList<>();
//            while (rs.next()) {
//                ChiTietHoaDonModel CTHDModel = new ChiTietHoaDonModel(
//                        rs.getString(1), new SanPhamModel(rs.getString(1)),
//                        new MauSacModel(rs.getString(3)), new KichCoModel(rs.getString(4)), new ChatLieuModel(rs.getString(5)), new ThuongHieuModel(rs.getString(6)),
//                        new ChiTietSanPhamModel(rs.getBigDecimal(3)),
//                        rs.getInt(4),
//                        rs.getBigDecimal(5)
//                );
//                listCTHD.add(CTHDModel);
//            }
//            return listCTHD;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public int themSPGioHang(ChiTietHoaDonModel CTHDModel) {
        String sql = "CALL themSPGioHang(?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            if (CTHDModel != null) {
                ps.setObject(1, CTHDModel.getMaSP());
                ps.setObject(2, CTHDModel.getTenSP());
                ps.setObject(3, CTHDModel.getDonGia());
                ps.setObject(4, CTHDModel.getSoLuong());
                ps.setObject(5, CTHDModel.getThanhTien());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ChiTietHoaDonModel> selectHDCTByMaHD(String maHDCT) {
        List<ChiTietHoaDonModel> listHDCT = new ArrayList<>();
        String sql = "SELECT        HOADONCHITIET.ID AS MaHDCT, SANPHAM.TenSanPham as TenSanPham, SANPHAMCHITIET.GiaBan as DonGia, HOADONCHITIET.SoLuong as SoLuong, HOADONCHITIET.ThanhTien as ThanhTien\\n\"\n"
                + "           + \"FROM            HOADONCHITIET INNER JOIN\\n\"\n"
                + "               + \"                         SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\\n\"\n"
                + "                + \"                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "WHERE HOADON.ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel CTHDModel = new ChiTietHoaDonModel(
                        rs.getString(1),
                        new SanPhamModel(rs.getString(2)),
                        new ChiTietSanPhamModel(rs.getBigDecimal(3)),
                        rs.getInt(4),
                        rs.getBigDecimal(5),
                        new HoaDonModel(rs.getString(6))
                );
                listHDCT.add(CTHDModel);
            }
            return listHDCT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
