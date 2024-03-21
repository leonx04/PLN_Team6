/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import raven.application.model.KhachHangModel;
import raven.connect.DBConnect;

public class KhachHangService {
    SimpleDateFormat format_date = new SimpleDateFormat("yyyy/MM/dd");
    Connection con = DBConnect.getConnection();
    List<KhachHangModel> list = new ArrayList<>();
    public List<KhachHangModel> getAll() {
        List<KhachHangModel> list = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String sql = "SELECT ID, HoTen, GioiTinh, SoDienThoai, DiaChi, Email, NgaySinh FROM KHACHHANG";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHangModel khm = new KhachHangModel();
                khm.setMa(rs.getString(1));
                khm.setTen(rs.getString(2));
                khm.setGioitinh(rs.getBoolean(3));
                khm.setSodt(rs.getString(4));
                khm.setDiachi(rs.getString(5));
                khm.setEmail(rs.getString(6));
                String ngaySinh = rs.getString(7);
                list.add(khm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    
    public int Them(KhachHangModel khm){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            String sql="insert into KHACHHANG(ID,HoTen,GioiTinh,SoDienThoai,DiaChi,Email,NgaySinh) values (?,?,?,?,?,?,?)";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, khm.getMa());
            ps.setString(2, khm.getTen());
            ps.setBoolean(3, khm.isGioitinh());
            ps.setString(4, khm.getSodt());
            ps.setString(5, khm.getDiachi());
            ps.setString(6, khm.getEmail());
            ps.setString(7, format_date.format(khm.getNgaysinh()));
            if (ps.executeUpdate() >0) {
                System.out.println("Thêm thành công");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Lỗi"+e.toString());
        }
        finally{
            try {
                con.close();
                ps.close();
            } catch (Exception e) {
            }
        }
        return -1;
    }
    
    public KhachHangModel getKhachHangByID(String id){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        KhachHangModel khm = new KhachHangModel();
        try {
            String sql = "select * from KHACHHANG where ID= ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            KhachHangModel kh = new KhachHangModel();
            while (rs.next()) {
                khm.setMa(rs.getString(1));
                khm.setTen(rs.getString(2));
                khm.setGioitinh(rs.getBoolean(3));
                khm.setSodt(rs.getString(4));
                khm.setDiachi(rs.getString(5));
                khm.setEmail(rs.getString(6));
                String ngaySinh = rs.getString(7);
                return kh;
            }
        } catch (Exception e) {
            System.out.println("Lỗi"+e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    public int Sua(KhachHangModel khm){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            String sql="update KHACHHANG set HoTen= ?,GioiTinh= ?,SoDienThoai= ?,DiaChi= ?,Email= ?,NgaySinh= ?, where ID= ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(7, khm.getMa());
            ps.setString(1, khm.getTen());
            ps.setBoolean(2, khm.isGioitinh());
            ps.setString(3, khm.getSodt());
            ps.setString(4, khm.getDiachi());
            ps.setString(5, khm.getEmail());
            ps.setString(6, format_date.format(khm.getNgaysinh()));
            if (ps.executeUpdate() >0) {
                System.out.println("Sửa ngon");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Lỗi"+e.toString());
        }
        finally{
            try {
                con.close();
                ps.close();
            } catch (Exception e) {
            }
        }
        return -1;
    }
}
