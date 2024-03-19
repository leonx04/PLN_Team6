/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import raven.application.model.HoaDonModel;
import raven.connect.DBConnect;

/**
 *
 * @author admin
 */
public class HoaDonService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
<<<<<<< HEAD
    List<HoaDonModel> listHD = new ArrayList<>();

    public List<HoaDonModel> getAll() {
        sql = "SELECT HOADON.ID, HOADON.NgayTao, ID_NhanVien, ID_KhachHang, TenVoucher, TongTien, TenHinhThuc FROM HOADON\n"
                + "JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "JOIN HINHTHUCTHANHTOAN ON HOADON.ID_HinhThucThanhToan = HINHTHUCTHANHTOAN.ID";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
//                HoaDonModel hd = new HoaDonModel(
//                        rs.getString(1),
//                        rs.getDate(2),
//                        rs.getString(3),
//                        rs.getString(4),
//                        rs.getString(5),
//                        rs.getBigDecimal(6),
//                        rs.getString(7)
//                );
//                listHD.add(hd);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HoaDonModel getRow(int row) {
        return listHD.get(row);
=======

    public List<HoaDonModel> getAll() {

        return null;
    }

    public ArrayList<HoaDonModel> getList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
>>>>>>> main
    }

    public List<HoaDonModel> getAllByTrangThai(String trangThai) {
        sql = "SELECT HOADON.ID, HOADON.NgayTao, ID_NhanVien, ID_KhachHang, TenVoucher, TongTien, TenHinhThuc FROM HOADON\n"
                + "JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "JOIN HINHTHUCTHANHTOAN ON HOADON.ID_HinhThucThanhToan = HINHTHUCTHANHTOAN.ID\n"
                + "WHERE HOADON.TrangThai = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            rs = ps.executeQuery();
            while (rs.next()) {
//                HoaDonModel hd = new HoaDonModel(
//                        rs.getString(1),
//                        rs.getDate(2),
//                        rs.getString(3),
//                        rs.getString(4),
//                        rs.getString(5),
//                        rs.getBigDecimal(6),
//                        rs.getString(7)
//                );
//                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listHD;
    }
    
}
