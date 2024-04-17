/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import raven.application.model.VoucherModer;
import raven.connect.DBConnect;
import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class VoucherService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<VoucherModer> getAllVoucher() {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia,MoTa, NgayBatDau, NgayKetThuc , TrangThai FROM VOUCHER";
        List<VoucherModer> listVoucher = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9));
                listVoucher.add(voucher);
            }
            return listVoucher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateStatusVoucher() {
        sql = "UPDATE VOUCHER SET TrangThai = N'Không hoạt động' WHERE NgayKetThuc < GETDATE()";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateActiveVouchers() {
        int updatedCount = 0;
        String sql = "UPDATE VOUCHER SET TrangThai = N'Hoạt động' WHERE NgayKetThuc >= GETDATE()";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            updatedCount = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updatedCount;
    }

    public int updateVoucherStatusByQuantity() {
        sql = "UPDATE VOUCHER SET TrangThai = N'Không hoạt động' WHERE SoLuong = 0";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<VoucherModer> getAllVoucherActive() {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM VOUCHER "
                + "WHERE TrangThai = N'Hoạt động' "
                + "AND NgayBatDau <= GETDATE() "
                + "AND NgayKetThuc > GETDATE()";
        List<VoucherModer> listVoucher = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                listVoucher.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng tài nguyên kết nối
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
        return listVoucher;
    }

    public List<VoucherModer> getAllVoucherByTrangThai(String trangThai) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM VOUCHER "
                + "WHERE TrangThai = ? AND SoLuong > 0";
        List<VoucherModer> listV = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                listV.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng tài nguyên kết nối
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
        return listV;
    }

    public List<VoucherModer> getIDByTenVoucher(String ten) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa FROM VOUCHER WHERE TenVoucher = ?";
        List<VoucherModer> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ten);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8));
                list.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public String getNewVoucherID() {
        String newID = "V001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM VOUCHER";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "V" + String.format("%03d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    public int insert(VoucherModer voucher) {
        sql = "INSERT INTO VOUCHER(ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai, NgayTao, NgaySua)"
                + "VALUES(?,?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, voucher.getID());
            ps.setObject(2, voucher.getTenVoucher());
            ps.setObject(3, voucher.getSoLuong());
            ps.setObject(4, voucher.getLoaiVoucher());
            ps.setObject(5, voucher.getMucGiamGia());
            ps.setObject(6, voucher.getMoTa());
            ps.setObject(7, voucher.getNgayBatDau());
            ps.setObject(8, voucher.getNgayKetThuc());
            ps.setObject(9, voucher.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(VoucherModer voucher) {
        sql = "UPDATE VOUCHER SET TenVoucher = ?, SoLuong = ?, LoaiVoucher = ?, MucGiamGia = ?, NgayBatDau = ?, NgayKetThuc = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP , TrangThai= ? WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, voucher.getTenVoucher());
            ps.setObject(2, voucher.getSoLuong());
            ps.setObject(3, voucher.getLoaiVoucher());
            ps.setObject(4, voucher.getMucGiamGia());
            ps.setObject(5, voucher.getNgayBatDau());
            ps.setObject(6, voucher.getNgayKetThuc());
            ps.setObject(7, voucher.getMoTa());
            ps.setObject(9, voucher.getID());
            ps.setObject(8, voucher.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<VoucherModer> getAllVoucherByNgay(java.sql.Date ngayBD, java.sql.Date ngayKT) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM VOUCHER "
                + "WHERE NgayBatDau BETWEEN ? AND ? "
                + "  AND NgayKetThuc BETWEEN ? AND ?";
        List<VoucherModer> listV = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ngayBD);
            ps.setObject(2, ngayKT);
            ps.setObject(3, ngayBD);
            ps.setObject(4, ngayKT);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                listV.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listV;
    }

    public int delete(String ma) {
        sql = "DELETE FROM VOUCHER WHERE ID = ?";
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

    public String getIdByTenVoucher(String tenVoucher) {
        sql = "SELECT ID FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTenByIDVoucher(String ID) {
        sql = "SELECT TenVoucher FROM VOUCHER WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenVoucher");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getMucGiamGiaByTenVoucher(String tenVoucher) {
        sql = "SELECT MucGiamGia FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("MucGiamGia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLoaiVoucherByTenVoucher(String tenVoucher) {
        sql = "SELECT LoaiVoucher FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("LoaiVoucher");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM VOUCHER WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // If count > 0, then ID already exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkTrungTen(String tenVoucher) {
        sql = "SELECT COUNT(*) AS count FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Nếu count > 0, tức là tên voucher đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkTonTaiHD(String idSPCT) {
        sql = "SELECT COUNT(*) FROM HOADON WHERE ID_Voucher = ?";
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

    public List<VoucherModer> searchVoucherByName(String tenVoucher) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM VOUCHER "
                + "WHERE TenVoucher LIKE ?";
        List<VoucherModer> resultList = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tenVoucher + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                resultList.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng tài nguyên kết nối
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
        return resultList;
    }

}
