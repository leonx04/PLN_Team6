/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.util.ArrayList;
import java.util.List;

import raven.application.model.SanPhamModel;
import raven.connect.DBConnect;

import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author dungn
 */
public class SanPhamService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<SanPhamModel> getAllSP() {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM";
        List<SanPhamModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamModel sp = new SanPhamModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(SanPhamModel sp) {
        sql ="INSERT INTO SANPHAM(ID, TenSanPham, MoTa, NgayTao, NgaySua, TrangThai)VALUES( ?,  ?,  ?,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP,  N'Còn hàng')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sp.getID());
            ps.setObject(2, sp.getTenSP());
            ps.setObject(3, sp.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
