/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import raven.application.model.ChatLieuModel;
import raven.application.model.ChiTietHoaDonModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.KichCoModel;
import raven.application.model.MauSacModel;
import raven.application.model.SanPhamModel;
import raven.application.model.ThuongHieuModel;
import raven.connect.DBConnect;

/**
 *
 * @author admin
 */
public class ChiTietHoaDonService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    List<ChiTietHoaDonModel> listCTHD = new ArrayList<>();

    public List<ChiTietHoaDonModel> getAllCTHD() {
        sql = "SELECT        HOADONCHITIET.ID, SANPHAM.TenSanPham AS TenSP, MAUSAC.TenMau AS TenMS, SIZE.Ten AS TenSize, THUONGHIEU.Ten AS TenTT, CHATLIEU.Ten AS TenCL, SANPHAMCHITIET.GiaBan AS DonGia, \n"
                + "                         HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien\n"
                + "FROM            HOADONCHITIET INNER JOIN\n"
                + "                          SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\n"
                + "                         SANPHAM ON SANPHAM.ID = SANPHAMCHITIET.ID_SanPham INNER JOIN\n"
                + "                         MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN\n"
                + "                         SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN\n"
                + "                         THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN\n"
                + "                         CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel cthh = new ChiTietHoaDonModel(
                        rs.getString(1),
                        new SanPhamModel(rs.getString(2)),
                        new MauSacModel(rs.getString(3)),
                        new KichCoModel(rs.getString(4)),
                        new ChatLieuModel(rs.getString(5)),
                        new ThuongHieuModel(rs.getString(6)),
                        new ChiTietSanPhamModel(rs.getBigDecimal(7)),
                        rs.getInt(8),
                        rs.getBigDecimal(9)
                );
                listCTHD.add(cthh);
            }
            return listCTHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietHoaDonModel> getAllCTHD2() {
        sql = "SELECT        HOADONCHITIET.ID, SANPHAMCHITIET.ID AS Expr1, SANPHAM.TenSanPham, MAUSAC.TenMau, SIZE.Ten, THUONGHIEU.Ten AS Expr2, CHATLIEU.Ten AS Expr3, SANPHAMCHITIET.GiaBan, HOADONCHITIET.SoLuong, \n"
                + "                         HOADONCHITIET.ThanhTien\n"
                + "FROM            HOADONCHITIET INNER JOIN\n"
                + "                         SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\n"
                + "                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID INNER JOIN\n"
                + "                         MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN\n"
                + "                         SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN\n"
                + "                         THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN\n"
                + "                         CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel cthh = new ChiTietHoaDonModel(
                        rs.getString(1),
                        new SanPhamModel(rs.getString(2)),
                        new MauSacModel(rs.getString(3)),
                        new KichCoModel(rs.getString(4)),
                        new ChatLieuModel(rs.getString(5)),
                        new ThuongHieuModel(rs.getString(6)),
                        new ChiTietSanPhamModel(rs.getBigDecimal(7)),
                        rs.getInt(8),
                        rs.getBigDecimal(9)
                );
                listCTHD.add(cthh);
            }
            return listCTHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietHoaDonModel> searchByHoaDonID1(String hoaDonID) {
        System.out.println("id hóa đơn" + hoaDonID);
        List<ChiTietHoaDonModel> listCTHD = new ArrayList<>();
        String sql = "SELECT HOADONCHITIET.ID, SANPHAM.TenSanPham AS TenSP, MAUSAC.TenMau AS TenMS, SIZE.Ten AS TenSize, THUONGHIEU.Ten AS TenTT, CHATLIEU.Ten AS TenCL, SANPHAMCHITIET.GiaBan AS DonGia, "
                + "HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien "
                + "FROM HOADONCHITIET INNER JOIN "
                + "SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN "
                + "SANPHAM ON SANPHAM.ID = SANPHAMCHITIET.ID_SanPham INNER JOIN "
                + "MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN "
                + "SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN "
                + "THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN "
                + "CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "WHERE HOADONCHITIET.ID_HoaDon = ? AND HOADONCHITIET.ID_HoaDon IN (SELECT ID FROM HOADON)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hoaDonID.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDonModel cthd = new ChiTietHoaDonModel(
                            rs.getString("ID"),
                            new SanPhamModel(rs.getString("TenSP")),
                            new MauSacModel(rs.getString("TenMS")),
                            new KichCoModel(rs.getString("TenSize")),
                            new ChatLieuModel(rs.getString("TenCL")),
                            new ThuongHieuModel(rs.getString("TenTT")),
                            new ChiTietSanPhamModel(rs.getBigDecimal("DonGia")),
                            rs.getInt("SoLuong"),
                            rs.getBigDecimal("ThanhTien")
                    );
                    listCTHD.add(cthd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCTHD;
    }

    public List<ChiTietHoaDonModel> searchByHoaDonID(String hoaDonID) {
        System.out.println("id hóa đơn: " + hoaDonID);
        List<ChiTietHoaDonModel> listCTHD = new ArrayList<>();
        String sql = "SELECT HOADONCHITIET.ID, SANPHAM.TenSanPham AS TenSP, MAUSAC.TenMau AS TenMS, SIZE.Ten AS TenSize, THUONGHIEU.Ten AS TenTT, CHATLIEU.Ten AS TenCL, SANPHAMCHITIET.GiaBan AS DonGia, "
                + "HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien "
                + "FROM HOADONCHITIET INNER JOIN "
                + "SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN "
                + "SANPHAM ON SANPHAM.ID = SANPHAMCHITIET.ID_SanPham INNER JOIN "
                + "MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN "
                + "SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN "
                + "THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN "
                + "CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "WHERE HOADONCHITIET.ID_HoaDon = ? AND HOADONCHITIET.ID_HoaDon IN (SELECT ID FROM HOADON)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            // Kiểm tra hoaDonID trước khi sử dụng
            if (hoaDonID != null) {
                ps.setString(1, hoaDonID.trim());
            } else {
                // Xử lý tùy thuộc vào logic của bạn khi hoaDonID là null
                // Ví dụ: không làm gì nếu hoaDonID là null
                return listCTHD;
            }
            System.out.println("id hóa đơn " + hoaDonID);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel chiTietHoaDon = new ChiTietHoaDonModel();
                chiTietHoaDon.setMactsp(new ChiTietSanPhamModel(rs.getString("MaSanPhamChiTiet")));
                chiTietHoaDon.setTenSP(new SanPhamModel(rs.getString("TenSanPham")));
                chiTietHoaDon.setMauSac(new MauSacModel(rs.getString("TenMS")));
                chiTietHoaDon.setSize(new KichCoModel(rs.getString("TenSize")));
                chiTietHoaDon.setThuongHieu(new ThuongHieuModel(rs.getString("TenTT")));
                chiTietHoaDon.setChatLieu(new ChatLieuModel(rs.getString("TenCL")));
                chiTietHoaDon.setDonGia(new ChiTietSanPhamModel(rs.getBigDecimal("DonGia")));
                chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                chiTietHoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                listCTHD.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các resource
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                /* Xử lý exception */ }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                /* Xử lý exception */ }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                /* Xử lý exception */ }
        }
        return listCTHD;
    }

    public String getNewHDCTByID() {
        String newID = "HDCT01";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) AS maxID FROM HOADONCHITIET";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "HDCT" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    public int insert2(ChiTietHoaDonModel hdct) {
        sql = "INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, DonGia, ThanhTien, NgayTao, NgaySua, TrangThai) "
                + "VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, N'Đã thanh toán')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdct.getID());
            ps.setObject(2, hdct.getTenSP().getTenSP());
            ps.setObject(3, hdct.getMauSac().getTenMS());
            ps.setObject(4, hdct.getSize().getTenSize());
            ps.setObject(5, hdct.getChatLieu().getTenCL());
            ps.setObject(6, hdct.getThuongHieu().getTenTH());
            ps.setObject(7, hdct.getDonGia().getGiaBan());
            ps.setObject(8, hdct.getSoLuong());
            ps.setObject(9, hdct.getThanhTien());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update2(ChiTietHoaDonModel hdct) {
        sql = "UPDATE HOADONCHITIET SET ID_HoaDon = ?, ID_SanPhamChiTiet = ?, SoLuong = ?, ThanhTien = ?, NgayTao = CURRENT_TIMESTAMP, NgaySua = CURRENT_TIMESTAMP, TrangThai = ?\n"
                + "WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdct.getTenSP().getTenSP());
            ps.setObject(2, hdct.getMauSac().getTenMS());
            ps.setObject(3, hdct.getSize().getTenSize());
            ps.setObject(4, hdct.getChatLieu().getTenCL());
            ps.setObject(5, hdct.getThuongHieu().getTenTH());
            ps.setObject(6, hdct.getDonGia().getGiaBan());
            ps.setObject(7, hdct.getSoLuong());
            ps.setObject(8, hdct.getThanhTien());
            ps.setObject(9, hdct.getID());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete2(String id) {
        sql = "DELETE FROM HOADONCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
