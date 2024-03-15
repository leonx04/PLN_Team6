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

import raven.application.model.MauSacModel;
import raven.connect.DBConnect;

/**
 *
 * @author dungn
 */
public class MauSacService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<MauSacModel> getALLMauSac() {
        sql = "SELECT ID, TenMau, MoTa FROM MAUSAC";
        List<MauSacModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MauSacModel ms = new MauSacModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                listCL.add(ms);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public int insert(MauSacModel ms) {
        sql = "INSERT INTO MAUSAC(ID, TenMau, MoTa,NgayTao, NgaySua, TrangThai) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ms.getID());
            ps.setObject(2, ms.getTenMS());
            ps.setObject(3, ms.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public int update(MauSacModel ms, String ma) {
        sql = "UPDATE MAUSAC SET TenMau = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, ms.getTenMS());
            ps.setObject(2, ms.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM MAUSAC WHERE ID = ?";
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

    public List<MauSacModel> getALLMauSacByTen() {
        sql = "SELECT ID, TenMau, MoTa FROM MAUSAC WHERE TenMau = ? ";
        List<MauSacModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MauSacModel ms = new MauSacModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                listCL.add(ms);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
