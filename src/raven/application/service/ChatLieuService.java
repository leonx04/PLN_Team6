/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.ChatLieuModel;
import raven.connect.DBConnect;

/**
 *
 * @author dungn
 */
public class ChatLieuService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<ChatLieuModel> getALLChatLieu() {
        sql = "SELECT ID, Ten, MoTa FROM  CHATLIEU";
        List<ChatLieuModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieuModel cl = new ChatLieuModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)

                );
                listCL.add(cl);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public int insert(ChatLieuModel cl) {
        sql = "INSERT INTO CHATLIEU(ID, Ten, MoTa,NgayTao, NgaySua, TrangThai) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, cl.getID());
            ps.setObject(2, cl.getTenCL());
            ps.setObject(3, cl.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public int update(ChatLieuModel cl, String ma) {
        sql = "UPDATE CHATLIEU SET Ten = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, cl.getTenCL());
            ps.setObject(2, cl.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM CHATLIEU WHERE ID = ?";
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
