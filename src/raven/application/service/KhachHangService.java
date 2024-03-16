/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import raven.application.model.KhachHangModel;
import raven.connect.DBConnect;

public class KhachHangService {
    
    public List<KhachHangModel> getAll(){
        String sql = "select ID,HoTen,DiaChi,SoDienThoai,Email,GioiTinh from KHACHHANG";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            List<KhachHangModel> list = new ArrayList<>();
            while(rs.next()) {
                KhachHangModel kh = new KhachHangModel();
                kh.setMa(rs.getString(1));
                kh.setTen(rs.getString(2));
                kh.setDiachi(rs.getString(3));
                kh.setSodt(rs.getString(4));
                kh.setEmail(rs.getString(5));
                kh.setGioitinh(rs.getString(6));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
//    public List<NhanVien> getAll(){
//        String sql="Select MaNV,TenNV,MatKhau,VaiTro from NhanVien";
//        try (Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            List<NhanVien> list = new ArrayList<>();
//            while(rs.next()){
//                NhanVien nv = new NhanVien();
//                nv.setMa(rs.getString(1));
//                nv.setTen(rs.getString(2));
//                nv.setMatKhau(rs.getString(3));
//                nv.setVaiTro(rs.getBoolean(4));
//                list.add(nv);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    
}
