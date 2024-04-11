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

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<KhachHangModel> getALLKH() {
        sql = " SELECT ID,HoTen,SoDienThoai,DiaChi,Email,GioiTinh FROM KHACHHANG ";
        List<KhachHangModel> listKH = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHangModel kh = new KhachHangModel(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6));
                listKH.add(kh);

            }
            return listKH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Trả về true nếu ID đã tồn tại trong cơ sở dữ liệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Mặc định trả về false nếu có lỗi xảy ra
    }

    public boolean checkTrungTen(String ten) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE HoTen = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ten);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Trả về true nếu tên đã tồn tại trong cơ sở dữ liệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Mặc định trả về false nếu có lỗi xảy ra
    }

    public boolean checkTrungEmail(String email) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE Email = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Trả về true nếu email đã tồn tại trong cơ sở dữ liệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Mặc định trả về false nếu có lỗi xảy ra
    }

    public boolean checkTrungSDT(String sdt) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE SoDienThoai = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Trả về true nếu số điện thoại đã tồn tại trong cơ sở dữ liệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Mặc định trả về false nếu có lỗi xảy ra
    }

    public String getNewKhachHangID() {
        // Mã sản phẩm mặc định
        String newID = "KH001";
        try {
            // Truy vấn SQL để lấy số thứ tự lớn nhất của mã sản phẩm từ cơ sở dữ liệu
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) AS maxID FROM KHACHHANG";
            // trong truy vấn SQL, MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) được sử dụng
            // để lấy số thứ tự lớn nhất của các mã sản phẩm trong cơ sở dữ liệu.
            // SUBSTRING(ID, 5, LEN(ID)) được sử dụng để cắt bỏ ba ký tự đầu tiên của mã sản
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
                newID = "KH" + String.format("%03d", maxID);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        }
        // Trả về mã sản phẩm mới hoặc mã mặc định nếu có lỗi xảy ra
        return newID;
    }

    public int insert(KhachHangModel kh) {
        sql = "INSERT INTO KHACHHANG(ID,HoTen,SoDienThoai,DiaChi,Email,GioiTinh,NgayTao, NgaySua, TrangThai)VALUES( ?,  ?,  ?, ?,?,?, CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP,  N'Hoạt động')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getMa());
            ps.setObject(2, kh.getTen());
            ps.setObject(3, kh.getSodt());
            ps.setObject(4, kh.getDiachi());
            ps.setObject(5, kh.getEmail());
            ps.setObject(6, kh.getGioitinh());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Hàm cập nhật thông tin khách hàng
    public int update(KhachHangModel kh) {
        sql = "UPDATE KHACHHANG SET HoTen = ?, SoDienThoai = ?, DiaChi = ?, Email = ?, GioiTinh = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, kh.getTen());
            ps.setString(2, kh.getSodt());
            ps.setString(3, kh.getDiachi());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getGioitinh());
            ps.setString(6, kh.getMa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getIDByTen(String ten) {
        sql = "SELECT ID FROM KHACHHANG WHERE HoTen = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ten);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHangModel findBySDT(String sdt) {
        sql = "SELECT ID, HoTen, SoDienThoai, DiaChi, Email, GioiTinh FROM KHACHHANG WHERE SoDienThoai = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new KhachHangModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
