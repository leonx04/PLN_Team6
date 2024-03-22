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

    public List<SanPhamModel> getAllSPByTrangThai(String trangthai) {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM WHERE TrangThai = ?";
        List<SanPhamModel> listSPTT = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangthai);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamModel sp = new SanPhamModel(
                        rs.getString("1"),
                        rs.getString("2"),
                        rs.getString(3));
                listSPTT.add(sp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return listSPTT;
    }

    public List<SanPhamModel> getIDByTenSP(String tenSP) {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM WHERE TenSanPham = ?";
        List<SanPhamModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenSP);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamModel sp = new SanPhamModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listSP;
    }

    public String getNewSanPhamID() {
        // Mã sản phẩm mặc định
        String newID = "SP01";
        try {
            // Truy vấn SQL để lấy số thứ tự lớn nhất của mã sản phẩm từ cơ sở dữ liệu
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM SANPHAM";
            // trong truy vấn SQL, MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) được sử dụng
            // để lấy số thứ tự lớn nhất của các mã sản phẩm trong cơ sở dữ liệu.
            // SUBSTRING(ID, 3, LEN(ID)) được sử dụng để cắt bỏ ba ký tự đầu tiên của mã sản
            // phẩm (trong trường hợp này là "SP"),
            // sau đó chuyển thành kiểu số nguyên bằng CAST.
            // Kết nối đến cơ sở dữ liệu
            con = DBConnect.getConnection();
            // Tạo đối tượng PreparedStatement từ truy vấn SQL
            ps = con.prepareStatement(sql);
            // Thực hiện truy vấn và lưu kết quả vào ResultSet
            rs = ps.executeQuery();
            // Kiểm tra xem ResultSet có kết quả hay không
            if (rs.next()) {
                // Nếu có kết quả, lấy giá trị số thứ tự lớn nhất từ cột "maxID"
                int maxID = rs.getInt("maxID");
                // Tăng giá trị số thứ tự lên một đơn vị
                maxID++;
                // Tạo mã sản phẩm mới từ số thứ tự lớn nhất và định dạng lại để có hai chữ số
                newID = "SP" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        }
        // Trả về mã sản phẩm mới hoặc mã mặc định nếu có lỗi xảy ra
        return newID;
    }

    public int insert(SanPhamModel sp) {
        sql = "INSERT INTO SANPHAM(ID, TenSanPham, MoTa, NgayTao, NgaySua, TrangThai)VALUES( ?,  ?,  ?,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP,  N'Còn hàng')";
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

    public int update(SanPhamModel sp, String ma) {
        sql = "UPDATE SANPHAM SET TenSanPham = ?, MoTa = ? , NgaySua  = CURRENT_TIMESTAMP WHERE ID = ? ;";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, sp.getTenSP());
            ps.setObject(2, sp.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM SANPHAM WHERE ID = ? ";
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
