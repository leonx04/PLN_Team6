/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.util.List;
import raven.application.model.NhanVienModel;
import raven.connect.DBConnect;
import raven.connect.JdbcHelper;

/**
 *
 * @author acer
 */
public class NhanVienService extends NhanVienService_Model<NhanVienModel, String>{

    String insert_sql = "INSERT INTO NhanVien(ID, HoTen, DiaChi, SoDienThoai, Email, NamSinh, GioiTinh, ChucVu, MatKhau, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TrangThai) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    String update_sql = "UPDATE NhanVien SET ID =?, HoTen =?, VaiTro =?, DiaChi=?, SoDienThoai=?, Email=?, NamSinh=?, GioiTinh=?, ChucVu=? MatKhau=? CURRENT_TIMESTAMP=?, CURRENT_TIMESTAMP=?, TrangThai=?   WHERE ID =?";
    String delete_sql = "delete from NhanVien where ID=?";
    String select_all_sql = "SELECT * FROM NhanVien";
    String select_by_id_sql = "SELECT * FROM NhanVien WHERE ID =?";

    @Override
    public void insert(NhanVienModel entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(NhanVienModel entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(NhanVienModel entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhanVienModel> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public NhanVienModel selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhanVienModel> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    
    
}
