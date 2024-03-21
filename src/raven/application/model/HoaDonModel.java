/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.security.Timestamp;
import java.sql.Date;

/**
 *
 * @author admin
 */
public class HoaDonModel {
    public String ID;
    public Date ngayTao;
    public NhanVienModel tenNV = new NhanVienModel();
    public BigDecimal tongTien;
}
