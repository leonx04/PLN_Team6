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

    public List<ChiTietHoaDonModel> getAllCTHD() {
        sql = "SELECT HOADONCHITIET.ID, TenSanPham, THUONGHIEU.Ten, TenMau, SIZE.Ten, CHATLIEU.Ten, DonGia, SoLuong, ThanhTien FROM HOADONCHITIET\r\n"
                + //
                "JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID\r\n"
                + //
                "JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\r\n"
                + //
                "JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\r\n"
                + //
                "JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\r\n"
                + //
                "JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\r\n"
                + //
                "JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID";
        List<ChiTietHoaDonModel> listHDCT = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel cthh = new ChiTietHoaDonModel(
                        rs.getString(1),
                        new SanPhamModel(rs.getString(2)),
                        new ThuongHieuModel(rs.getString(3)),
                        new MauSacModel(rs.getString(4)),
                        new KichCoModel(rs.getString(5)),
                        new ChatLieuModel(rs.getString(6)),
                        rs.getBigDecimal(7),
                        rs.getInt(8),
                        rs.getBigDecimal(9)
                );
                listHDCT.add(cthh);
            }
            return listHDCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietHoaDonModel> searchByHoaDonID(String hoaDonID) {
        sql = "SELECT HOADONCHITIET.ID, TenSanPham, THUONGHIEU.Ten, TenMau, SIZE.Ten, CHATLIEU.Ten, DonGia, SoLuong, ThanhTien FROM HOADONCHITIET\r\n"
                + //
                "JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID\r\n"
                + //
                "JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\r\n"
                + //
                "JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\r\n"
                + //
                "JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\r\n"
                + //
                "JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\r\n"
                + //
                "JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\r\n"
                + //
                "WHERE HOADONCHITIET.ID = ?";
        List<ChiTietHoaDonModel> listCTHD = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel cthh = new ChiTietHoaDonModel(
                        rs.getString(1),
                        new SanPhamModel(rs.getString(2)),
                        new ThuongHieuModel(rs.getString(3)),
                        new MauSacModel(rs.getString(4)),
                        new KichCoModel(rs.getString(5)),
                        new ChatLieuModel(rs.getString(6)),
                        rs.getBigDecimal(7),
                        rs.getInt(8),
                        rs.getBigDecimal(9)
                );
                listCTHD.add(cthh);
            }
            return listCTHD;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }
    }
    public String getNewHDCTByID(){
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
//    public insert(ChiTietHoaDonModel cthh){
//        sql = "INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, DonGia, ThanhTien, NgayTao, NgaySua, TrangThai) "
//                + "VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, N'Đã thanh toán')";
//        
//    }
}
