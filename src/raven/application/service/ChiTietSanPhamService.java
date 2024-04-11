/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.ChatLieuModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.KichCoModel;
import raven.application.model.MauSacModel;
import raven.application.model.SanPhamModel;
import raven.application.model.ThuongHieuModel;
import raven.connect.DBConnect;

/**
 *
 * @author dungn
 */
public class ChiTietSanPhamService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<ChiTietSanPhamModel> getAllCTSP() {
        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            // Kết nối đến cơ sở dữ liệu
            con = DBConnect.getConnection();

            // Câu truy vấn SQL để lấy thông tin các sản phẩm chi tiết
            String sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau, SIZE.Ten, CHATLIEU.Ten, THUONGHIEU.Ten, SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.MoTa "
                    + "FROM SANPHAMCHITIET "
                    + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                    + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                    + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                    + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                    + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID";

            // Tạo đối tượng PreparedStatement từ câu truy vấn SQL
            ps = con.prepareStatement(sql);

            // Thực hiện truy vấn và lưu kết quả vào ResultSet
            rs = ps.executeQuery();

            // Duyệt qua các bản ghi trong ResultSet và thêm vào danh sách kết quả
            while (rs.next()) {
                ChiTietSanPhamModel ctsp = new ChiTietSanPhamModel();
                ctsp.setID(rs.getString(1));
                ctsp.setTenSP(new SanPhamModel(rs.getString(2)));
                ctsp.setMauSac(new MauSacModel(rs.getString(3)));
                ctsp.setKichCo(new KichCoModel(rs.getString(4)));
                ctsp.setChatLieu(new ChatLieuModel(rs.getString(5)));
                ctsp.setThuongHieu(new ThuongHieuModel(rs.getString(6)));
                ctsp.setSoLuongTon(rs.getInt(7));
                ctsp.setGiaBan(rs.getBigDecimal(8));
                ctsp.setMoTa(rs.getString(9));
                listCTSP.add(ctsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các đối tượng ResultSet, PreparedStatement và Connection
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
        // Trả về danh sách các sản phẩm chi tiết
        return listCTSP;
    }

    public List<ChiTietSanPhamModel> getAllCTSPSoluong0() {
        String sql = "SELECT "
                + "SANPHAMCHITIET.ID, "
                + "SANPHAM.TenSanPham, "
                + "MAUSAC.TenMau AS MauSac, "
                + "SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, "
                + "THUONGHIEU.Ten AS ThuongHieu, "
                + "SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, "
                + "SANPHAMCHITIET.MoTa "
                + "FROM "
                + "SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE SANPHAMCHITIET.SoLuongTon = 0"; // Chỉ lấy các sản phẩm chi tiết có số lượng tồn bằng 0

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int soLuongTon = rs.getInt("SoLuongTon");
                if (soLuongTon == 0) { // Chỉ thêm vào danh sách nếu số lượng tồn là 0
                    ChiTietSanPhamModel ctsp = new ChiTietSanPhamModel(
                            rs.getString("ID"), // ID
                            new SanPhamModel(rs.getString("TenSanPham")), // TenSP
                            new MauSacModel(rs.getString("MauSac")), // MauSac
                            new KichCoModel(rs.getString("Size")), // Size
                            new ChatLieuModel(rs.getString("ChatLieu")), // ChatLieu
                            new ThuongHieuModel(rs.getString("ThuongHieu")), // ThuongHieu
                            rs.getBigDecimal("GiaBan"), // GiaBan
                            soLuongTon, // SoLuongTon
                            rs.getString("MoTa")); // MoTa
                    listCTSP.add(ctsp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTSP;
    }

    public List<ChiTietSanPhamModel> getAllCTSPSoluongLonHon0() {
        String sql = "SELECT \n"
                + "    SANPHAMCHITIET.ID,\n"
                + "    SANPHAM.TenSanPham,\n"
                + "    MAUSAC.TenMau AS MauSac,\n"
                + "    SIZE.Ten AS Size,\n"
                + "    CHATLIEU.Ten AS ChatLieu,\n"
                + "    THUONGHIEU.Ten AS ThuongHieu,\n"
                + "    SANPHAMCHITIET.GiaBan,\n"
                + "    SANPHAMCHITIET.SoLuongTon,\n"
                + "    SANPHAMCHITIET.MoTa\n"
                + "FROM \n"
                + "    SANPHAMCHITIET\n"
                + "INNER JOIN \n"
                + "    SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN \n"
                + "    MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN \n"
                + "    SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN \n"
                + "    CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN \n"
                + "    THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE \n"
                + "    SANPHAMCHITIET.SoLuongTon > 0";

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
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietSanPhamModel> searchBySanPhamID(String sanPhamID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE SANPHAM.ID = ?";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sanPhamID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietSanPhamModel> searchByMauSacID(String mausacID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE MAUSAC.ID = ?";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, mausacID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietSanPhamModel> searchBySizeID(String sizeID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE SIZE.ID = ?";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sizeID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietSanPhamModel> searchByChatLieuID(String chatLieuID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE CHATLIEU.ID = ?";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, chatLieuID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietSanPhamModel> searchByThuonghieuID(String thuonghieuID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE THUONGHIEU.ID = ?";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, thuonghieuID);
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
            e.printStackTrace();
            return null;
        }
    }

    public String getNewSPCTID() {
        // Mã sản phẩm mặc định
        String newID = "SPCT01";
        try {
            // Kết nối đến cơ sở dữ liệu
            con = DBConnect.getConnection();

            // Tạo truy vấn SQL để lấy số thứ tự lớn nhất của mã sản phẩm từ cơ sở dữ liệu
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) AS maxID FROM SANPHAMCHITIET";

            // Tạo đối tượng PreparedStatement từ truy vấn SQL
            ps = con.prepareStatement(sql);

            // Thực hiện truy vấn và lưu kết quả vào ResultSet
            rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả hay không
            if (rs.next()) {
                // Nếu có kết quả, lấy giá trị số thứ tự lớn nhất từ cột "maxID"
                int maxID = rs.getInt("maxID");

                // Tạo mã sản phẩm mới từ số thứ tự lớn nhất
                if (maxID < 99) {
                    // Nếu maxID nhỏ hơn 99, tăng giá trị số thứ tự lên một đơn vị
                    maxID++;
                } else {
                    // Nếu maxID đạt đến 99, tự động tăng mã lên SPCT100
                    maxID = 100;
                }

                // Định dạng lại mã sản phẩm mới để có hai chữ số
                newID = "SPCT" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        } finally {
            // Đóng các đối tượng ResultSet, PreparedStatement và Connection
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
        // Trả về mã sản phẩm mới hoặc mã mặc định nếu có lỗi xảy ra
        return newID;
    }

    public int insert(ChiTietSanPhamModel ctsp) {
        sql = "INSERT INTO SANPHAMCHITIET(ID, ID_SanPham, ID_MauSac, ID_Size, ID_ChatLieu, ID_ThuongHieu, GiaBan, SoLuongTon, MoTa, NgayTao, NgaySua, TrangThai)\r\n"
                + //
                "VALUES (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, N'Còn hàng')";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ctsp.getID());
            ps.setObject(2, ctsp.getTenSP().getID());
            ps.setObject(3, ctsp.getMauSac().getID());
            ps.setObject(4, ctsp.getKichCo().getID());
            ps.setObject(5, ctsp.getChatLieu().getID());
            ps.setObject(6, ctsp.getThuongHieu().getID());
            ps.setObject(7, ctsp.getGiaBan());
            ps.setObject(8, ctsp.getSoLuongTon());
            ps.setObject(9, ctsp.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkTrungId(String id) {
        sql = "SELECT COUNT(*) AS count FROM SANPHAMCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkTrungIdCTSP(String maSPM) {
        String sql = "SELECT ID FROM SANPHAMCHITIET WHERE ID = ?";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSPM);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Nếu có kết quả trả về => Mã SP đã tồn tại
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Không tìm thấy mã SP trùng
        return false;
    }

    public int update(ChiTietSanPhamModel ctsp) {
        sql = "UPDATE SANPHAMCHITIET SET ID_SanPham=?, ID_MauSac=?, ID_Size=?, ID_ChatLieu=?, ID_ThuongHieu=?, GiaBan=?, SoLuongTon=?, MoTa=?, NgaySua = CURRENT_TIMESTAMP WHERE ID=?";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ctsp.getTenSP().getID());
            ps.setObject(2, ctsp.getMauSac().getID());
            ps.setObject(3, ctsp.getKichCo().getID());
            ps.setObject(4, ctsp.getChatLieu().getID());
            ps.setObject(5, ctsp.getThuongHieu().getID());
            ps.setObject(6, ctsp.getGiaBan());
            ps.setObject(7, ctsp.getSoLuongTon());
            ps.setObject(8, ctsp.getMoTa());
            ps.setObject(9, ctsp.getID());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String id) {
        String sql = "DELETE FROM SANPHAMCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkTonTaiHDCT(String idSPCT) {
        sql = "SELECT COUNT(*) FROM HOADONCHITIET WHERE ID_SanPhamChiTiet = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idSPCT);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
