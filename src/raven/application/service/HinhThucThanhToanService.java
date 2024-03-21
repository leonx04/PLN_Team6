/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.HinhThucThanhToanModel;
import raven.connect.DBConnect;

/**
 *
 * @author admin
 */
public class HinhThucThanhToanService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    
    public List<HinhThucThanhToanModel> getAllHTTT(){
        sql = "SELECT ID, TenHinhThuc, MoTa FROM HINHTHUCTHANHTOAN";
        List<HinhThucThanhToanModel> listHTTT = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                HinhThucThanhToanModel httt = new HinhThucThanhToanModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                listHTTT.add(httt);
            }
            return listHTTT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
