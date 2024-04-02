/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.ChatLieuModel;
import raven.application.model.ChiTietHoaDonModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.HoaDonModel;
import raven.application.model.KhachHangModel;
import raven.application.model.KichCoModel;
import raven.application.model.MauSacModel;
import raven.application.model.NhanVienModel;
import raven.application.model.SanPhamModel;
import raven.application.model.ThuongHieuModel;
import raven.application.model.VoucherModer;
import raven.connect.DBConnect;

/**
 *
 * @author admin
 */
public class BanHangService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<HoaDonModel> getAllHD() {
        sql = "SELECT        HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan\n"
                + "FROM            HOADON INNER JOIN\n"
                + "                         NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID INNER JOIN\n"
                + "                         KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID INNER JOIN\n"
                + "                         VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID";
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
                        rs.getString(7));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

<<<<<<< HEAD
//    public List<ChiTietHoaDonModel> getAllGH() {
//        sql = "SELECT        SANPHAM.ID AS MaSanPham, SANPHAM.TenSanPham as TenSanPham, SANPHAMCHITIET.GiaBan as DonGia, HOADONCHITIET.SoLuong as SoLuong, HOADONCHITIET.ThanhTien as ThanhTien\n"
//                + "FROM            HOADONCHITIET INNER JOIN\n"
//                + "                         SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\n"
//                + "                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID";
//        try {
//            con = DBConnect.getConnection();
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//            List<ChiTietHoaDonModel> listCTHD = new ArrayList<>();
//            while (rs.next()) {
//                ChiTietHoaDonModel CTHDModel = new ChiTietHoaDonModel(
//                        rs.getString(1), new SanPhamModel(rs.getString(1)),
//                        new MauSacModel(rs.getString(3)), new KichCoModel(rs.getString(4)), new ChatLieuModel(rs.getString(5)), new ThuongHieuModel(rs.getString(6)),
//                        new ChiTietSanPhamModel(rs.getBigDecimal(3)),
//                        rs.getInt(4),
//                        rs.getBigDecimal(5)
//                );
//                listCTHD.add(CTHDModel);
//            }
//            return listCTHD;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public int themSPGioHang(ChiTietHoaDonModel CTHDModel) {
        String sql = "CALL themSPGioHang(?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            if (CTHDModel != null) {
                ps.setObject(1, CTHDModel.getMaSP());
                ps.setObject(2, CTHDModel.getTenSP());
                ps.setObject(3, CTHDModel.getDonGia());
                ps.setObject(4, CTHDModel.getSoLuong());
                ps.setObject(5, CTHDModel.getThanhTien());
                return ps.executeUpdate();
=======
   public List<HoaDonModel> getAllHD2() {
    sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, SUM(HOADONCHITIET.ThanhTien) AS TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
            + "FROM HOADON\n"
            + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
            + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
            + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
            + "INNER JOIN HOADONCHITIET ON HOADON.ID = HOADONCHITIET.ID_HoaDon\n"
            + "GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
            + "HAVING SUM(HOADONCHITIET.ThanhTien) > 0\n"
            + "UNION ALL\n"
            + "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, 0 AS TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
            + "FROM HOADON\n"
            + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
            + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
            + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
            + "WHERE HOADON.TrangThai = N'Chờ thanh toán'\n" // Thêm điều kiện WHERE vào đây
            + "GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai";
   public List<HoaDonModel> getAllHD2() {
    sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, SUM(HOADONCHITIET.ThanhTien) AS TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
            + "FROM HOADON\n"
            + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
            + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
            + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
            + "INNER JOIN HOADONCHITIET ON HOADON.ID = HOADONCHITIET.ID_HoaDon\n"
            + "GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
            + "HAVING SUM(HOADONCHITIET.ThanhTien) > 0\n"
            + "UNION ALL\n"
            + "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, 0 AS TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
            + "FROM HOADON\n"
            + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
            + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
            + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
            + "WHERE HOADON.TrangThai = N'Chờ thanh toán'\n" // Thêm điều kiện WHERE vào đây
            + "GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai";

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


    public boolean capNhatTongTienHoaDon(String maHoaDon) {
        // Kiểm tra và gán giá trị mặc định là 0 nếu tổng tiền là null
        String sqlUpdateNullTongTien = "UPDATE HOADON SET TongTien = 0 WHERE TongTien IS NULL AND ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlUpdateNullTongTien);
            ps.setString(1, maHoaDon);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Lấy tổng tiền từ bảng HOADONCHITIET
        BigDecimal tongTien = BigDecimal.ZERO;
        String sqlGetTongTien = "SELECT SUM(ThanhTien) AS TongTien FROM HOADONCHITIET WHERE ID_HoaDon = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlGetTongTien);
            ps.setString(1, maHoaDon);
            rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal sum = rs.getBigDecimal("TongTien");
                if (sum != null) {
                    tongTien = sum;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Cập nhật tổng tiền vào bảng HOADON
        String sqlUpdateTongTien = "UPDATE HOADON SET TongTien = ? WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlUpdateTongTien);
            ps.setBigDecimal(1, tongTien);
            ps.setString(2, maHoaDon);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<ChiTietSanPhamModel> getAllCTSP() {
        sql = " SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS TenMau, SIZE.Ten AS TenKichCo, CHATLIEU.Ten AS TenChatLieu, THUONGHIEU.Ten AS TenThuongHieu , SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.SoLuongTon, MAUSAC.MoTa\n"
                + "FROM SANPHAMCHITIET\n"
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE SANPHAMCHITIET.SoLuongTon > 0";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamModel ctsp = new ChiTietSanPhamModel(
                        rs.getString(1), // ID
                        new SanPhamModel(rs.getString(2)), // TenSP
                        new MauSacModel(rs.getString(3)), // MauSac
                        new KichCoModel(rs.getString(4)), // Size
                        new ChatLieuModel(rs.getString(5)), // ChatLieu
                        new ThuongHieuModel(rs.getString(6)), // ThuongHieu
                        rs.getBigDecimal(7), // GiaBan
                        rs.getInt(8), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }

    }

    public int laySoLuongTonByID(String maCTSP) {
        int soLuongTon = 0;
        String sql = "SELECT SoLuongTon FROM SANPHAMCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maCTSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                soLuongTon = rs.getInt("SoLuongTon");
>>>>>>> parent of f305b80 (Update Create Bill and Mouseclicked table Bill  on Form Sell)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ChiTietHoaDonModel> searchByHoaDonID(String idHoaDon) {
        List<ChiTietHoaDonModel> chiTietHoaDons = new ArrayList<>();
        String sql = "SELECT SANPHAMCHITIET.ID AS MaSanPhamChiTiet, SANPHAM.TenSanPham AS TenSanPham, SANPHAMCHITIET.GiaBan AS DonGia, HOADONCHITIET.SoLuong AS SoLuong, HOADONCHITIET.ThanhTien AS ThanhTien "
                +
                "FROM HOADONCHITIET " +
                "INNER JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID " +
                "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID " +
                "WHERE HOADONCHITIET.ID_HoaDon = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idHoaDon);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDonModel chiTietHoaDon = new ChiTietHoaDonModel();
                chiTietHoaDon.setMactsp(new ChiTietSanPhamModel(rs.getString(1)));
                chiTietHoaDon.setTenSP(new SanPhamModel(rs.getString(2)));
                chiTietHoaDon.setDonGia(new ChiTietSanPhamModel(rs.getBigDecimal(3)));
                chiTietHoaDon.setSoLuong(rs.getInt(4));
                chiTietHoaDon.setThanhTien(rs.getBigDecimal(5));
                chiTietHoaDons.add(chiTietHoaDon);
            }
<<<<<<< HEAD
            return chiTietHoaDons;
=======
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietHoaDons;
    }

    public BigDecimal getGiaBanByMaCTSP(String maCTSP) {
        BigDecimal giaBan = null;
        String sql = "SELECT GiaBan FROM SANPHAMCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maCTSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                giaBan = rs.getBigDecimal("GiaBan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giaBan;
    }

    public int themSPGioHang(ChiTietHoaDonModel chiTietHoaDon, String idHoaDon) {
        // Thực hiện các bước để lấy ID mới cho HOADONCHITIET
        String newHDCTID = cthdsv.getNewHDCTByID();

        // Tiến hành chèn dữ liệu vào bảng HOADONCHITIET
        String sql = "INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, ThanhTien, NgayTao, NgaySua, TrangThai) "
                + "VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán')";
                + "VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, newHDCTID);
            ps.setObject(2, idHoaDon); // Sử dụng ID hóa đơn đã chọn
            ps.setObject(3, chiTietHoaDon.getMactsp().getID());
            ps.setObject(4, chiTietHoaDon.getSoLuong());
            ps.setObject(5, chiTietHoaDon.getThanhTien());
            return ps.executeUpdate();
>>>>>>> parent of f305b80 (Update Create Bill and Mouseclicked table Bill  on Form Sell)
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String selectKhachHangLeByMa(String ma, String ten) {
        String maKHle = null;
        String sql = "SELECT id FROM KHACHHANG WHERE HoTen = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            ps.setObject(2, ten);
            rs = ps.executeQuery();
            while (rs.next()) {
                maKHle = rs.getString("id");
            }
            return maKHle;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String selectIDHDBymaNV(String maHD){
        String id = "";
        String sql = "select ID from HOADON where ID_NhanVien = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHD);
            rs = ps.executeQuery();
<<<<<<< HEAD
            while (rs.next()) {                
                id = rs.getString("id");
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
=======
            if (rs.next()) {
                tongTien = rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Kiểm tra và gán giá trị mặc định cho tổng tiền nếu tổng tiền là null
        if (tongTien == null) {
            tongTien = BigDecimal.ZERO; // hoặc giá trị mặc định khác
        }

        // Tiến hành cập nhật tổng tiền thành 0 cho hóa đơn
        String sqlUpdateTongTien = "UPDATE HOADON SET TongTien = 0 WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlUpdateTongTien);
            ps.setString(1, maHoaDon);
            int rowsAffected = ps.executeUpdate();

            // Sau khi cập nhật tổng tiền thành 0, cần cập nhật thông tin này vào cơ sở dữ
            // liệu
            boolean updated = capNhatTongTienHoaDon(maHoaDon);

            return rowsAffected > 0 && updated; // Trả về true nếu cả hai cập nhật đều thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Đóng kết nối
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

>>>>>>> parent of f305b80 (Update Create Bill and Mouseclicked table Bill  on Form Sell)
}
