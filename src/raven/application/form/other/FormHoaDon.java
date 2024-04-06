/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.application.model.ChiTietHoaDonModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.HoaDonModel;
import raven.application.service.ChiTietHoaDonService;
import raven.application.service.ChiTietSanPhamService;
import raven.application.service.HoaDonService;

/**
 *
 * @author admin
 */
public class FormHoaDon extends javax.swing.JPanel {

    /**
     * Creates new form FormHoaDon1
     */
    private DefaultTableModel model = new DefaultTableModel();
    private HoaDonService hdsr = new HoaDonService();
    private ChiTietHoaDonService cthd = new ChiTietHoaDonService();
    private ChiTietSanPhamService ctsp = new ChiTietSanPhamService();
    DefaultComboBoxModel<String> dcb1 = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> dcb2 = new DefaultComboBoxModel<>();
    List<HoaDonModel> listHD = new ArrayList<>();
    List<ChiTietSanPhamModel> listSPCT = new ArrayList<>();
    private int index = -1;

    public FormHoaDon() {
        initComponents();
        dcb1 = (DefaultComboBoxModel<String>) cboTrangThaiHD.getModel();
        dcb2 = (DefaultComboBoxModel<String>) cboHinhThuc.getModel();
        loadTrangThai();
        loadHinhThuc();
        this.fillTable(hdsr.getAll());
//        this.fillTable2(cthd.getAllCTHD());
    }

    void loadTrangThai() {
        dcb1.addElement("Đã thanh toán");
        dcb1.addElement("Đã hủy");
        dcb1.addElement("Chờ thanh toán");
    }

    void loadHinhThuc() {
        dcb2.addElement("Tiền mặt");
        dcb2.addElement("Chuyển khoản");
        dcb2.addElement("Kết hợp");
    }

    void fillTable(List<HoaDonModel> list) {
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        int index = 1;
        for (HoaDonModel hoaDonModel : list) {
            hoaDonModel.setStt(index++);
            model.addRow(hoaDonModel.toData());
        }
        if (model.getRowCount() > 0) {
            tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(0, 0, true));
            tblHoaDon.setRowSelectionInterval(0, 0);
        }
    }

    void fillTable2(List<ChiTietHoaDonModel> listHD) {
        model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        // Xóa hết các dòng hiện tại trong bảng
        model.setRowCount(0);
        for (ChiTietHoaDonModel cthd : listHD) {

            Object[] rowData = {
                model.getRowCount() + 1,
                cthd.getID(),
                cthd.getTenSP().getTenSP(),
                cthd.getMauSac().getTenMS(),
                cthd.getSize().getTenSize(),
                cthd.getThuongHieu().getTenTH(),
                cthd.getChatLieu().getTenCL(),
                cthd.getDonGia().getGiaBan(),
                cthd.getSoLuong(),
                cthd.getThanhTien()
            };

            // Thêm dòng vào model của bảng
            model.addRow(rowData);
        }
    }

    private void fillChiTietHoaDonTable(String hoaDonID) {
        try {
            List<ChiTietHoaDonModel> chiTietHoaDons = cthd.searchByHoaDonID(hoaDonID);
            if (chiTietHoaDons == null || chiTietHoaDons.isEmpty()) {
                System.out.println("Không tìm thấy chi tiết hóa đơn cho ID: " + hoaDonID);
                return;
            }

            fillTable2(chiTietHoaDons);
            System.out.println("Đã chạy qua hàm fill");
        } catch (Exception ex) {
            System.out.println("Đã xảy ra lỗi khi điền dữ liệu vào bảng chi tiết hóa đơn: " + ex.getMessage());
            ex.printStackTrace(); // In ra stack trace để debug
        }
    }

    void locHoaDon() {
        String trangThai = null;
        String tenHinhThuc = null;
        if (cboHinhThuc.getItemCount() == 3 && cboTrangThaiHD.getItemCount() == 4) {
            if (cboTrangThaiHD.getSelectedItem().equals("Tất cả")) {
                this.fillTable(hdsr.getAll());
            } else if (cboTrangThaiHD.getSelectedItem().equals("Đã thanh toán")) {
                trangThai = "Đã thanh toán";
            } else if (cboTrangThaiHD.getSelectedItem().equals("Chờ thanh toán")) {
                trangThai = "Chờ thanh toán";
            } else if (cboTrangThaiHD.getSelectedItem().equals("Đã hủy")) {
                trangThai = "Đã hủy";
            }
            if (cboHinhThuc.getSelectedItem().equals("Kết hợp")) {
                tenHinhThuc = "Kết hợp";
            } else if (cboHinhThuc.getSelectedItem().equals("Tiền mặt")) {
                tenHinhThuc = "Tiền mặt";
            } else {
                tenHinhThuc = cboHinhThuc.getSelectedItem().toString();
            }
            this.fillTable(hdsr.getAllByTrangThaiAndHinhThuc(trangThai, tenHinhThuc));

        }

    }

    void showData(int index) {
        String maHD = String.valueOf(tblHoaDon.getValueAt(index, 1)).trim();
        String tenNV = String.valueOf(tblHoaDon.getValueAt(index, 3)).trim();
        String tenKH = String.valueOf(tblHoaDon.getValueAt(index, 4)).trim();

        String tenVC = String.valueOf(tblHoaDon.getValueAt(index, 5)).trim();
        String tongTien = String.valueOf(tblHoaDon.getValueAt(index, 6)).trim();
        String HTTT = String.valueOf(tblHoaDon.getValueAt(index, 7)).trim();

    }
    // Biến để lưu trữ ID của hóa đơn được chọn
    private String selectedHoaDonID;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiemHD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cboTrangThaiHD = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboHinhThuc = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dateBatDau = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        dateKetThuc = new com.toedter.calendar.JDateChooser();
        btnLoc = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSeachHDCT = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1295, 713));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(881, 238));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tìm kiếm hóa đơn: ");

        txtTimKiemHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHDActionPerformed(evt);
            }
        });

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Ngày Tạo", "Tên Nhân Viên", "Tên Khách Hàng", "Tên Voucher", "Tổng Tiền", "Hình thức thanh toán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Trạng thái hóa đơn:");

        cboTrangThaiHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThaiHD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTrangThaiHDItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Hình thức thanh toán:");

        cboHinhThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHinhThuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHinhThucItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Từ :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Đến :");

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(dateBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(dateKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dateKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(btnLoc))
                                .addGap(2, 2, 2))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dateBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(1295, 500));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Hóa Đơn Chi Tiết");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tìm kiếm :");

        txtSeachHDCT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSeachHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSeachHDCTActionPerformed(evt);
            }
        });

        tblHoaDonChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MaHDCT", "SanPham", "MauSac", "Size", "ThuongHieu", "ChatLieu", "DonGia", "SoLuong", "ThanhTien"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setPreferredSize(new java.awt.Dimension(1900, 200));
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonChiTiet);
        if (tblHoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tblHoaDonChiTiet.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(4).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(5).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(6).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(7).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(8).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(6, 6, 6)
                        .addComponent(txtSeachHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(txtSeachHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(106, 40));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Hóa Đơn");
        jLabel1.setMaximumSize(new java.awt.Dimension(88, 22));
        jLabel1.setMinimumSize(new java.awt.Dimension(88, 22));
        jLabel1.setPreferredSize(new java.awt.Dimension(88, 22));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int row = tblHoaDon.getSelectedRow();
        if (row >= 0) {
            String hoaDonID = tblHoaDon.getValueAt(row, 1).toString();
            if (hoaDonID != null && !hoaDonID.isEmpty()) {
                // Gọi phương thức searchByHoaDonID với hoaDonID đã được kiểm tra
                List<ChiTietHoaDonModel> listCTHD = cthd.searchByHoaDonID(hoaDonID);
                // Tiếp tục xử lý kết quả
                fillTable2(listCTHD);
            } else {
                System.out.println("Hóa đơn ID không hợp lệ");
                // Xử lý tùy theo logic của bạn khi hoaDonID là null hoặc rỗng
            }
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        if (dateBatDau.getDateFormatString().toString().trim().isEmpty() || dateKetThuc.getDateFormatString().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đủ ngày và giờ");
            return;
        } else {
            List<HoaDonModel> listHD = hdsr.findDate(dateBatDau, dateBatDau);
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0);
            int stt = 1;
            for (HoaDonModel hoaDonModel : listHD) {
                String trangThai = "";
                String trangThaiValue = hoaDonModel.getTrangThai();
                if (trangThaiValue == "Đã thanh toán") {
                    trangThai = "Đã thanh toán";
                } else if (trangThaiValue == "Chờ thanh toán") {
                    trangThai = "Chờ thanh toán";
                } else if (trangThaiValue == "Đã hủy") {
                    trangThai = "Đã hủy";
                }
            }
            for (HoaDonModel hdModel : listHD) {
                hdModel.setStt(index++);
                model.addRow(hdModel.toData());
            }
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void txtTimKiemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHDActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelHD = (DefaultTableModel) tblHoaDon.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelHD);
        tblHoaDon.setRowSorter(trs);

        String IDHD = txtTimKiemHD.getText().trim();
        trs.setRowFilter(new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (entry.getStringValue(i).toLowerCase().contains(IDHD.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        });
    }//GEN-LAST:event_txtTimKiemHDActionPerformed

    private void txtSeachHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSeachHDCTActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelHDCT = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelHDCT);
        tblHoaDonChiTiet.setRowSorter(trs);

        String hoaDonID = txtSeachHDCT.getText().trim();
        trs.setRowFilter(new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (entry.getStringValue(i).toLowerCase().contains(hoaDonID.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }

        });
    }//GEN-LAST:event_txtSeachHDCTActionPerformed

    private void cboHinhThucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboHinhThucItemStateChanged
        // TODO add your handling code here:
        locHoaDon();
    }//GEN-LAST:event_cboHinhThucItemStateChanged

    private void cboTrangThaiHDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTrangThaiHDItemStateChanged
        // TODO add your handling code here:
        locHoaDon();
    }//GEN-LAST:event_cboTrangThaiHDItemStateChanged

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JComboBox<String> cboHinhThuc;
    private javax.swing.JComboBox<String> cboTrangThaiHD;
    private com.toedter.calendar.JDateChooser dateBatDau;
    private com.toedter.calendar.JDateChooser dateKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTextField txtSeachHDCT;
    private javax.swing.JTextField txtTimKiemHD;
    // End of variables declaration//GEN-END:variables
}
