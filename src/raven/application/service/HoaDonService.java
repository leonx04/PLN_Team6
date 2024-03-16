/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.util.List;
import java.sql.*;
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
    
    public List<HoaDonModel> getAll(){
        
    }

    public ArrayList<HoaDonModel> getList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
