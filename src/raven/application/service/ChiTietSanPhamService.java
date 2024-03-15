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
        sql = " SELECT        SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET_1.GiaBan, SANPHAMCHITIET_1.SoLuongTon, \n"
                + "                         SANPHAMCHITIET_1.MoTa\n"
                + "FROM            SANPHAMCHITIET INNER JOIN\n"
                + "                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID INNER JOIN\n"
                + "                         MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN\n"
                + "                         SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN\n"
                + "                         CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID INNER JOIN\n"
                + "                         THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN\n"
                + "                         SANPHAMCHITIET AS SANPHAMCHITIET_1 ON SANPHAM.ID = SANPHAMCHITIET_1.ID_SanPham AND MAUSAC.ID = SANPHAMCHITIET_1.ID_MauSac AND SIZE.ID = SANPHAMCHITIET_1.ID_Size AND \n"
                + "                         CHATLIEU.ID = SANPHAMCHITIET_1.ID_ChatLieu AND THUONGHIEU.ID = SANPHAMCHITIET_1.ID_ThuongHieu";

        List<ChiTietSanPhamModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MauSacModel ms = new MauSacModel(null, rs.getString(3), null);
                SanPhamModel sp = new SanPhamModel(null, rs.getString(2),null);
                KichCoModel kc = new KichCoModel(null, rs.getString(4),null);
                ChatLieuModel cl = new ChatLieuModel(null, rs.getString(5),null);
                ThuongHieuModel th = new ThuongHieuModel(null, rs.getString(6),null);
                ChiTietSanPhamModel ctsp = new ChiTietSanPhamModel(
                        rs.getString(1),
                        sp,
                        ms,
                        kc,
                        cl,
                        th,
                        rs.getBigDecimal(7),
                        rs.getInt(8),
                        rs.getString(9));
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }

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
}
