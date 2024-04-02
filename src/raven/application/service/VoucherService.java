/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.VoucherModer;
import raven.connect.DBConnect;
import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class VoucherService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<VoucherModer> getAllVoucher() {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia,MoTa, NgayBatDau, NgayKetThuc FROM VOUCHER";
        List<VoucherModer> listVoucher = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8));
                listVoucher.add(voucher);
            }
            return listVoucher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<VoucherModer> getAllVoucherByTrangThai(String trangThai) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia,MoTa FROM VOUCHER WHERE TRANGTHAI = ?";
        List<VoucherModer> listV = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8));
                listV.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listV;
    }

    public List<VoucherModer> getIDByTenVoucher(String ten) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa FROM VOUCHER WHERE TenVoucher = ?";
        List<VoucherModer> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ten);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8));
                list.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public String getNewVoucherID() {
        String newID = "V001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM VOUCHER";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "Voucher" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    public int insert(VoucherModer voucher) {
        sql = "INSERT INTO VOUCHER(ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, NgayBatDau, NgayKetThuc, MoTa, NgayTao, NgaySua, TrangThai)"
                + "VALUES(?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, voucher.getID());
            ps.setObject(2, voucher.getTenVoucher());
            ps.setObject(3, voucher.getSoLuong());
            ps.setObject(4, voucher.getLoaiVoucher());
            ps.setObject(5, voucher.getMucGiamGia());
            ps.setObject(6, voucher.getMoTa());
            ps.setObject(7, voucher.getNgayBatDau());
            ps.setObject(8, voucher.getNgayKetThuc());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(VoucherModer vm, String ma) {
        sql = "UPDATE VOUCHER SET TenVoucher = ?, SoLuong = ?, LoaiVoucher = ?, MucGiamGia = ?, MoTa = ? WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, vm.getTenVoucher());
            ps.setObject(2, vm.getSoLuong());
            ps.setObject(3, vm.getMucGiamGia());
            ps.setObject(4, vm.getMoTa());
            ps.setObject(5, vm.getID());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM VOUCHER WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int getDiscount(String tenV){
        int mucGiamGia = 0;
        sql = "SELECT MucGiamGia  FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenV);
            rs = ps.executeQuery();
            if (rs.next()) {
                mucGiamGia = rs.getInt("mucGiamGia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mucGiamGia;
    }

    public Object getTenVoucher() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getLoaiVoucher() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getMucGiamGia() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
