/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.NhanVienModel;
import raven.connect.DBConnect;
import raven.connect.JdbcHelper;

/**
 *
 * @author acer
 */
public class NhanVienService {

    String insert_sql = "INSERT INTO [dbo].[NHANVIEN]([ID],[HoTen] ,[DiaChi] ,[SoDienThoai] ,[Email] ,[NamSinh] ,\n"
            + "			[GioiTinh] ,[ChucVu] ,[MatKhau] ,[NgayTao] ,[TrangThai])\n"
            + "     VALUES (?,?,?,?,?,?,?,?,?,GETDATE(),'0');";
    String update_sql = "UPDATE [dbo].[NHANVIEN] SET [ID] = ? , [HoTen] = ? ,[DiaChi] = ?  ,[SoDienThoai] = ?  ,[Email] = ? ,[NamSinh] = ? ,\n"
            + "		[GioiTinh] = ? ,[ChucVu] = ? ,[MatKhau] = ?  ,[NgaySua] = GETDATE()  WHERE id = ?";
    String delete_sql = "UPDATE [dbo].[NHANVIEN] SET [TrangThai] = 1 WHERE id = ?";
    String slect_by_id_sql = "SELECT * FROM NhanVien WHERE id = ? ";

    String slect_all = "SELECT * FROM NhanVien";
    String slect_all_0_sql = "SELECT * FROM NhanVien WHERE TrangThai = '0'";
    String slect_all_1_sql = "SELECT * FROM NhanVien WHERE TrangThai = '1'";

    public List<NhanVienModel> Search(String input) {
        List<NhanVienModel> listNV = new ArrayList<>();
        if (input == null) {
            return selectAll();
        }
        for (NhanVienModel x : selectAll()) {
            if (x.getId().contains(input)
                    || x.getHoTen().contains(input)
                    || x.getChucVu().contains(input)
                    || x.getDiaChi().contains(input)
                    || x.getEmail().contains(input)
                    || x.getSdt().contains(input)) {
                listNV.add(x);
            }
        }
        return listNV;
    }
    
    public String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedBytes = md.digest(password.getBytes("UTF-8"));

        // Chuyển đổi byte array thành chuỗi hex
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean insert(NhanVienModel entity) {
        try {
            String hashedPassword = hashPassword(entity.getMatKhau()); // Mã hóa mật khẩu
            JdbcHelper.update(insert_sql, entity.getId(), entity.getHoTen(), entity.getDiaChi(), entity.getSdt(),
                    entity.getEmail(), entity.getNamSinh(), entity.getGioiTinh(), entity.getChucVu(), hashedPassword);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(NhanVienModel entity) {
        try {
            String hashedPassword = hashPassword(entity.getMatKhau()); // Mã hóa mật khẩu
            JdbcHelper.update(update_sql, entity.getId(), entity.getHoTen(), entity.getDiaChi(), entity.getSdt(), entity.getEmail(),
                    entity.getNamSinh(), entity.getGioiTinh(), entity.getChucVu(), hashedPassword, entity.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    public NhanVienModel selectById(String id) {
        List<NhanVienModel> list = selectBySql(slect_by_id_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<NhanVienModel> selectAll_0() {
        return selectBySql(slect_all_0_sql);
    }

    public List<NhanVienModel> selectAll_1() {
        return selectBySql(slect_all_1_sql);
    }

    public List<NhanVienModel> selectAll() {
        return selectBySql(slect_all);
    }

    public List<NhanVienModel> selectBySql(String sql, Object... args) {
        List<NhanVienModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVienModel entity = new NhanVienModel();
                entity.setId(rs.getString("ID"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setSdt(rs.getString("SoDienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setNamSinh(rs.getInt("NamSinh"));
                entity.setGioiTinh(rs.getString("GioiTinh"));
                entity.setChucVu(rs.getString("ChucVu"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setTrangThai(rs.getString("TrangThai"));

                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    
}
