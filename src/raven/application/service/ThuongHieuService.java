/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import raven.application.model.ThuongHieuModel;
import raven.connect.DBConnect;

/**
 *
 * @author dungn
 */
public class ThuongHieuService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<ThuongHieuModel> getALLThuongHieu() {
        sql = "SELECT ID, Ten, MoTa FROM THUONGHIEU";
        List<ThuongHieuModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThuongHieuModel th = new ThuongHieuModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listCL.add(th);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public int insert(ThuongHieuModel th) {
        sql = "INSERT INTO THUONGHIEU(ID, Ten, MoTa,NgayTao, NgaySua, TrangThai) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, th.getID());
            ps.setObject(2, th.getTenTH());
            ps.setObject(3, th.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public int update(ThuongHieuModel th, String ma) {
        sql = "UPDATE THUONGHIEU SET Ten = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, th.getTenTH());
            ps.setObject(2, th.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM THUONGHIEU WHERE ID = ?";
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
}
