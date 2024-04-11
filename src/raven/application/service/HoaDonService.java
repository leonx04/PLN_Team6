/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import com.toedter.calendar.JDateChooser;
import java.math.BigDecimal;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import raven.application.model.HoaDonModel;
import raven.application.model.KhachHangModel;
import raven.application.model.NhanVienModel;
import raven.application.model.VoucherModer;
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
    List<HoaDonModel> listHD = new ArrayList<>();

    public List<HoaDonModel> getAll() {
        String sql = "SELECT DISTINCT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
                + "FROM HOADON\n"
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID ORDER BY NgayTao DESC";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getAllByTrangThaiAndHinhThuc(String trangThai, String tenHinhThuc) {
        sql = "SELECT        HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan\n"
                + "FROM            HOADON INNER JOIN\n"
                + "                         NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID INNER JOIN\n"
                + "                         KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID INNER JOIN\n"
                + "                         VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "						 WHERE HOADON.TRANGTHAI = ? and HinhThucThanhToan = ?";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            ps.setString(2, tenHinhThuc);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel hoaDonModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        new VoucherModer(rs.getString(5)),
                        rs.getBigDecimal(6),
                        rs.getString(7));
                listHD.add(hoaDonModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonModel> findDate(JDateChooser d1, JDateChooser d2) {
        List<HoaDonModel> listHD = new ArrayList<>(); // Khởi tạo danh sách hóa đơn trước khi sử dụng
        String sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan\n"
                + "FROM HOADON INNER JOIN\n"
                + "NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID INNER JOIN\n"
                + "KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID INNER JOIN\n"
                + "VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "WHERE HOADON.NgayTao BETWEEN ? AND ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (d1.getDate() != null && d2.getDate() != null) {
                ps.setDate(1, new java.sql.Date(d1.getDate().getTime())); // Thiết lập tham số cho thời gian bắt đầu
                ps.setDate(2, new java.sql.Date(d2.getDate().getTime())); // Thiết lập tham số cho thời gian kết thúc
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        HoaDonModel hoaDonModel = new HoaDonModel(
                                rs.getString(1),
                                rs.getDate(2),
                                new NhanVienModel(rs.getString(3)),
                                new KhachHangModel(rs.getString(4)),
                                new VoucherModer(rs.getString(5)),
                                rs.getBigDecimal(6),
                                rs.getString(7),
                                rs.getString(8)
                        );
                        listHD.add(hoaDonModel);
                    }
                }
                return listHD;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonModel> searchByHoaDon(String IDHD) {
        sql = "SELECT        HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan\n"
                + "FROM            HOADON INNER JOIN\n"
                + "                         NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID INNER JOIN\n"
                + "                         KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID INNER JOIN\n"
                + "                         VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "						 WHERE HOADON.ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, IDHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel hoaDonModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        new VoucherModer(rs.getString(5)),
                        rs.getBigDecimal(6),
                        rs.getString(7));
                listHD.add(hoaDonModel);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNewHD() {
        String newID = "HD001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) AS maxID FROM HOADON";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "HD" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    public int insert(HoaDonModel hdm) {
        sql = "INSERT INTO HOADON(ID, ID_NhanVien, ID_KhachHang, ID_Voucher, HinhThucThanhToan, TongTien, NgayTao, NgaySua, TrangThai) "
                + "VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdm.getID());
            ps.setObject(2, hdm.getNgayTao());
            ps.setObject(3, hdm.getTenNV().getId());
            ps.setObject(4, hdm.getTenKH().getMa());
            ps.setObject(5, hdm.getTenVoucher().getID());
            ps.setObject(6, hdm.getTongTien());
            ps.setObject(7, hdm.getHinhThucThanhToan());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(HoaDonModel hdm) {
        sql = "UPDATE HOADON SET ID_NhanVien = ?, ID_KhachHang = ?, ID_Voucher = ?, HinhThucThanhToan = ?, TongTien = ?, NgayTao = CURRENT_TIMESTAMP, NgaySua = CURRENT_TIMESTAMP, TrangThai = ?\n"
                + "WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdm.getNgayTao());
            ps.setObject(2, hdm.getTenNV().getId());
            ps.setObject(3, hdm.getTenKH().getMa());
            ps.setObject(4, hdm.getTenVoucher().getID());
            ps.setObject(6, hdm.getTongTien());
            ps.setObject(5, hdm.getHinhThucThanhToan());
            ps.setObject(7, hdm.getID());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String ID) {
        sql = "DELETE FROM HOADON WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<HoaDonModel> getDaThanhToanHoaDon() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Đã thanh toán'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getDaHuyHoaDon() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Đã hủy'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getHoaDonChoThanhToan() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Chờ thanh toán'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getHDTienMat() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "                VOUCHER.TenVoucher,\n"
                + "                HOADON.TongTien,\n"
                + "                HOADON.HinhThucThanhToan,\n"
                + "                HOADON.TrangThai\n"
                + "                                FROM HOADON\n"
                + "                                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "                                WHERE HOADON.HinhThucThanhToan = N'Tiền mặt'\n"
                + "                                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getHDTienChuyenKhoan() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "                VOUCHER.TenVoucher,\n"
                + "                HOADON.TongTien,\n"
                + "                HOADON.HinhThucThanhToan,\n"
                + "                HOADON.TrangThai\n"
                + "                                FROM HOADON\n"
                + "                                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "                                WHERE HOADON.HinhThucThanhToan = N'Chuyển khoản'\n"
                + "                                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getHDKetHop() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "                VOUCHER.TenVoucher,\n"
                + "                HOADON.TongTien,\n"
                + "                HOADON.HinhThucThanhToan,\n"
                + "                HOADON.TrangThai\n"
                + "                                FROM HOADON\n"
                + "                                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "                                WHERE HOADON.HinhThucThanhToan = N'Kết hợp'\n"
                + "                                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<HoaDonModel> listHD = new ArrayList<>();
            while (rs.next()) {
                HoaDonModel hdModel = new HoaDonModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new NhanVienModel(rs.getString(3)),
                        new KhachHangModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
