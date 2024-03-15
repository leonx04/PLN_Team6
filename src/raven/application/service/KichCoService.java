/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.KichCoModel;
import raven.connect.DBConnect;
/**
 *
 * @author dungn
 */
public class KichCoService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<KichCoModel> getALLChatLieu() {
        sql = "SELECT ID, Ten, MoTa FROM SIZE";
        List<KichCoModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KichCoModel kc = new KichCoModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)

                );
                listCL.add(kc);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public int insert(KichCoModel kc) {
        sql = "INSERT INTO SIZE(ID, Ten, MoTa,NgayTao, NgaySua, TrangThai) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kc.getID());
            ps.setObject(2, kc.getTenSize());
            ps.setObject(3, kc.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public int update(KichCoModel kc, String ma) {
        sql = "UPDATE SIZE SET Ten = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, kc.getTenSize());
            ps.setObject(2, kc.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM SIZE WHERE ID = ?";
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
