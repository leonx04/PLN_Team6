/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jdesktop.swingx.calendar.DateUtils;
import raven.connect.DBConnect;

public class ThongKeService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public BigDecimal getTongTienHoaDonHomnay() {
        String sql = "SELECT SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND CONVERT(date, HOADON.NgayTao) = CONVERT(date, GETDATE())";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTongTienHoaDon7NgayGanDay() {
        String sql = "SELECT SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND CONVERT(date, HOADON.NgayTao) >= CONVERT(date, DATEADD(day, -7, GETDATE()))";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTongTienHoaDonThangNay() {
        String sql = "SELECT SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND MONTH(HOADON.NgayTao) = MONTH(GETDATE()) "
                + "AND YEAR(HOADON.NgayTao) = YEAR(GETDATE())";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTongTienHoaDonNamNay() {
        String sql = "SELECT SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND YEAR(HOADON.NgayTao) = YEAR(GETDATE())";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public int getTongSoSanPhamDaBan() {
        String sql = "SELECT SUM(HOADONCHITIET.SoLuong) AS TongSoLuong "
                + "FROM HOADONCHITIET "
                + "INNER JOIN HOADON ON HOADONCHITIET.ID_HoaDon = HOADON.ID "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán'";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongSoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<Date, Integer> getSoHoaDonTungNgay() {
        Map<Date, Integer> soHoaDonTungNgay = new HashMap<>();
        String sql = "SELECT CONVERT(date, HOADON.NgayTao) AS NgayTao, COUNT(*) AS SoHoaDon "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "GROUP BY CONVERT(date, HOADON.NgayTao) "
                + "ORDER BY CONVERT(date, HOADON.NgayTao) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Date ngayTao = rs.getDate("NgayTao");
                int soHoaDon = rs.getInt("SoHoaDon");
                soHoaDonTungNgay.put(ngayTao, soHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soHoaDonTungNgay;
    }

    // Số hóa đơn theo ngày
    public Map<Date, Integer> getSoHoaDonTheoNgay() {
        Map<Date, Integer> soHoaDonTheoNgay = new LinkedHashMap<>();
        String sql = "SELECT CONVERT(date, HOADON.NgayTao) AS NgayTao, COUNT(*) AS SoHoaDon "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "GROUP BY CONVERT(date, HOADON.NgayTao) "
                + "ORDER BY CONVERT(date, HOADON.NgayTao) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Date ngayTao = rs.getDate("NgayTao");
                int soHoaDon = rs.getInt("SoHoaDon");
                soHoaDonTheoNgay.put(ngayTao, soHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soHoaDonTheoNgay;
    }

// Số hóa đơn theo tháng
    public Map<String, Integer> getSoHoaDonTheoThang() {
        Map<String, Integer> soHoaDonTheoThang = new LinkedHashMap<>();
        String sql = "SELECT CONCAT(MONTH(HOADON.NgayTao), '/', YEAR(HOADON.NgayTao)) AS ThangNam, COUNT(*) AS SoHoaDon "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "GROUP BY CONCAT(MONTH(HOADON.NgayTao), '/', YEAR(HOADON.NgayTao)) "
                + "ORDER BY CONCAT(MONTH(HOADON.NgayTao), '/', YEAR(HOADON.NgayTao)) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String thangNam = rs.getString("ThangNam");
                int soHoaDon = rs.getInt("SoHoaDon");
                soHoaDonTheoThang.put(thangNam, soHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soHoaDonTheoThang;
    }

// Số hóa đơn theo năm
    public Map<Integer, Integer> getSoHoaDonTheoNam() {
        Map<Integer, Integer> soHoaDonTheoNam = new LinkedHashMap<>();
        String sql = "SELECT YEAR(HOADON.NgayTao) AS Nam, COUNT(*) AS SoHoaDon "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "GROUP BY YEAR(HOADON.NgayTao) "
                + "ORDER BY YEAR(HOADON.NgayTao) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int nam = rs.getInt("Nam");
                int soHoaDon = rs.getInt("SoHoaDon");
                soHoaDonTheoNam.put(nam, soHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soHoaDonTheoNam;
    }

// Tổng tiền hóa đơn theo từng ngày
    public Map<Date, BigDecimal> getTongTienHoaDonTheoNgay(Date ngayBD, Date ngayKT) {
        Map<Date, BigDecimal> tongTienTheoNgay = new LinkedHashMap<>();
        String sql = "SELECT CONVERT(date, HOADON.NgayTao) AS NgayTao, SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND HOADON.NgayTao BETWEEN ? AND ? "
                + "GROUP BY CONVERT(date, HOADON.NgayTao) "
                + "ORDER BY CONVERT(date, HOADON.NgayTao) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBD.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKT.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                Date ngayTao = rs.getDate("NgayTao");
                BigDecimal tongTien = rs.getBigDecimal("TongTien");
                tongTienTheoNgay.put(ngayTao, tongTien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tongTienTheoNgay;
    }

// Tổng tiền hóa đơn theo từng tháng
    public Map<String, BigDecimal> getTongTienHoaDonTheoThang(Date ngayBD, Date ngayKT) {
        Map<String, BigDecimal> tongTienTheoThang = new LinkedHashMap<>();
        String sql = "SELECT CONCAT(MONTH(HOADON.NgayTao), '/', YEAR(HOADON.NgayTao)) AS ThangNam, SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND HOADON.NgayTao BETWEEN ? AND ? "
                + "GROUP BY CONCAT(MONTH(HOADON.NgayTao), '/', YEAR(HOADON.NgayTao)) "
                + "ORDER BY CONCAT(MONTH(HOADON.NgayTao), '/', YEAR(HOADON.NgayTao)) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBD.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKT.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                String thangNam = rs.getString("ThangNam");
                BigDecimal tongTien = rs.getBigDecimal("TongTien");
                tongTienTheoThang.put(thangNam, tongTien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tongTienTheoThang;
    }

// Tổng tiền hóa đơn theo từng năm
    public Map<Integer, BigDecimal> getTongTienHoaDonTheoNam(Date ngayBD, Date ngayKT) {
        Map<Integer, BigDecimal> tongTienTheoNam = new LinkedHashMap<>();
        String sql = "SELECT YEAR(HOADON.NgayTao) AS Nam, SUM(HOADON.TongTien) AS TongTien "
                + "FROM HOADON "
                + "WHERE HOADON.TrangThai = N'Đã thanh toán' "
                + "AND HOADON.NgayTao BETWEEN ? AND ? "
                + "GROUP BY YEAR(HOADON.NgayTao) "
                + "ORDER BY YEAR(HOADON.NgayTao) ASC";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBD.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKT.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                int nam = rs.getInt("Nam");
                BigDecimal tongTien = rs.getBigDecimal("TongTien");
                tongTienTheoNam.put(nam, tongTien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tongTienTheoNam;
    }

}
