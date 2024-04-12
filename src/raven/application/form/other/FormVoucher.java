package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.application.model.VoucherModer;
import raven.application.service.VoucherService;

/**
 *
 * @author admin
 */
public class FormVoucher extends javax.swing.JPanel {

    private DefaultTableModel model = new DefaultTableModel();
    private VoucherService service = new VoucherService();
    private int index = -1;

    public FormVoucher() {
        initComponents();
        this.fillTable(service.getAllVoucher());
        JComboBox<String> cboTrangThai = new JComboBox<>(
                new String[]{"Hoạt động", "Không hoạt động"});
    }

    void fillTable(List<VoucherModer> list) {
        model = (DefaultTableModel) tblVoucher.getModel();
        model.setRowCount(0);
        int index = 1;
        for (VoucherModer voucher : list) {
            voucher.setSTT(index++);
            model.addRow(voucher.toData());
        }
        if (model.getRowCount() > 0) {
            tblVoucher.scrollRectToVisible(tblVoucher.getCellRect(0, 0, true));
            tblVoucher.setRowSelectionInterval(0, 0);
        }
    }

    private VoucherModer readForm() {
        String ma = txtMaVoucher.getText().trim();
        String ten = txtTenVoucher.getText().trim();
        int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        String loai = (String) cboLoaiVoucher.getSelectedItem();
        BigDecimal mucGiam = new BigDecimal(txtMucGiamGia.getText().trim());
        String moTa = txtMoTa.getText().trim();
        java.util.Date ngayBD = dateBD.getDate();
        java.util.Date ngayKT = dateKT.getDate();
        java.sql.Date sqlNgayBD = new java.sql.Date(ngayBD.getTime());
        java.sql.Date sqlNgayKT = new java.sql.Date(ngayKT.getTime());
        String trangThai = (String) cboTrangThai.getSelectedItem();

        VoucherModer voucher = new VoucherModer(ma, ten, soLuong, loai, mucGiam, moTa, sqlNgayBD, sqlNgayKT);
        voucher.setTrangThai(trangThai);
        return voucher;
    }

    void showData(int index) {
        String ID = tblVoucher.getValueAt(index, 1).toString().trim();
        String tenVoucher = tblVoucher.getValueAt(index, 2).toString().trim();
        String soLuong = String.valueOf(tblVoucher.getValueAt(index, 3)).trim();
        String loaiVoucher = tblVoucher.getValueAt(index, 4).toString().trim();
        String mucGiamGia = tblVoucher.getValueAt(index, 5).toString().trim();
        String moTa = tblVoucher.getValueAt(index, 6).toString().trim();
        java.sql.Date ngayBatDau = (java.sql.Date) tblVoucher.getValueAt(index, 7);
        java.sql.Date ngayKetThuc = (java.sql.Date) tblVoucher.getValueAt(index, 8);
        String trangThai = tblVoucher.getValueAt(index, 9).toString().trim();

        txtMaVoucher.setText(ID);
        txtTenVoucher.setText(tenVoucher);
        txtSoLuong.setText(String.valueOf(soLuong));
        cboLoaiVoucher.setSelectedItem(loaiVoucher);
        txtMucGiamGia.setText(mucGiamGia);

        txtMoTa.setText(moTa);
        dateBD.setDate(ngayBatDau);
        dateKT.setDate(ngayKetThuc);
        cboTrangThai.setSelectedItem(trangThai);
    }

    private boolean checkValidate() {
        // Kiểm tra độ dài và không để trống tên voucher
        String tenVoucher = txtTenVoucher.getText().trim();
        if (tenVoucher.isEmpty() || tenVoucher.length() > 50) {
            JOptionPane.showMessageDialog(this, "Tên voucher không được trống và không được vượt quá 50 ký tự.");
            txtTenVoucher.requestFocus();
            return false;
        }

        // Kiểm tra số lượng là số nguyên dương, không vượt quá 999
        String soLuongStr = txtSoLuong.getText().trim();
        if (soLuongStr.isEmpty() || !soLuongStr.matches("\\d+") || soLuongStr.contains("-")) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương không vượt quá 999 và không chứa ký tự đặc biệt.");
            txtSoLuong.requestFocus();
            return false;
        }
        int soLuong = Integer.parseInt(soLuongStr);
        if (soLuong <= 0 || soLuong > 999) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương từ 1 đến 999.");
            txtSoLuong.requestFocus();
            return false;
        }

        // Kiểm tra mức giảm giá là số, không âm, không vượt quá 20 ký tự
        String mucGiamGiaStr = txtMucGiamGia.getText().trim();
        if (mucGiamGiaStr.isEmpty() || !mucGiamGiaStr.matches("^\\d*\\.?\\d+$") || mucGiamGiaStr.contains("-") || mucGiamGiaStr.length() > 20) {
            JOptionPane.showMessageDialog(this, "Mức giảm giá phải là số không âm và không vượt quá 20 ký tự.");
            txtMucGiamGia.requestFocus();
            return false;
        }
        BigDecimal mucGiamGia = new BigDecimal(mucGiamGiaStr);
        if (mucGiamGia.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "Mức giảm giá không được âm và không vượt quá 20 ký tự.");
            txtMucGiamGia.requestFocus();
            return false;
        }

        // Kiểm tra mô tả không vượt quá 255 ký tự và không để trống
        String moTa = txtMoTa.getText().trim();
        if (moTa.isEmpty() || moTa.length() > 255) {
            JOptionPane.showMessageDialog(this, "Mô tả không được trống và không vượt quá 255 ký tự.");
            txtMoTa.requestFocus();
            return false;
        }

        return true; // Nếu các điều kiện đều đúng
    }

    public void cbo_TrangThai() {
        String trangThai = "";
        if (cboTrangThai.getSelectedItem().equals("Hoạt động")) {
            trangThai = "Hoạt động";
        } else {
            trangThai = "Không hoạt động";
        }
        List<VoucherModer> list = service.getAllVoucherByTrangThai(trangThai);
        fillTable(list);
    }

    void clear() {
        txtMaVoucher.setText("");
        txtTenVoucher.setText("");
        txtSoLuong.setText("");
        cboLoaiVoucher.setSelectedIndex(0);
        txtMucGiamGia.setText("");
        txtMoTa.setText("");
//        dateBD.setDate(null);
//        dateKT.setDate(null);
        cboTrangThai.setSelectedIndex(0);
    }

    private void filter(String sa) {
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        tblVoucher.setRowSorter(tableRowSorter);
        tableRowSorter.setRowFilter(RowFilter.regexFilter("?i" + sa));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaVoucher = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenVoucher = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMucGiamGia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboLoaiVoucher = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        dateBD = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        dateKT = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVoucher = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnLoc = new javax.swing.JButton();
        dateKT1 = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        dateBD1 = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        cboTrangThai1 = new javax.swing.JComboBox<>();

        setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1295, 713));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1250, 40));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Chương trình Voucher");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(1057, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết lập Voucher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 400));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(750, 350));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã Voucher : ");

        txtMaVoucher.setEditable(false);
        txtMaVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVoucherActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên Voucher :");

        txtTenVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Loại Voucher :");

        txtMucGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMucGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMucGiamGiaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mức giảm giá :");

        cboLoaiVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLoaiVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm giá theo phần trăm", "Giảm giá theo giá tiền" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Số Lượng :");

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLoaiVoucher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMucGiamGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtTenVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboLoaiVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thời gian sử dụng ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(600, 400));

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 200));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Thời gian bắt đầu :");

        dateBD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Thời gian kết thúc :");

        dateKT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Trạng thái Voucher");

        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoạt động", "Không hoạt động" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Mô tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dateBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboTrangThai, 0, 166, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(dateKT, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(dateKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        btnThem.setBackground(new java.awt.Color(0, 204, 51));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(153, 153, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnHuy.setBackground(new java.awt.Color(255, 0, 0));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 204, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(btnThem)
                        .addGap(38, 38, 38)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnLamMoi)
                        .addGap(38, 38, 38)
                        .addComponent(btnHuy)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnLamMoi)
                    .addComponent(btnSua)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(1040, 275));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Danh sách Voucher");

        tblVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Voucher", "Tên Voucher", "Số Lượng", "Loại Voucher", "Mức giảm giá", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVoucher.setMaximumSize(new java.awt.Dimension(400, 40));
        tblVoucher.setMinimumSize(new java.awt.Dimension(0, 0));
        tblVoucher.setPreferredSize(new java.awt.Dimension(300, 40));
        tblVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVoucherMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblVoucher);
        if (tblVoucher.getColumnModel().getColumnCount() > 0) {
            tblVoucher.getColumnModel().getColumn(0).setResizable(false);
            tblVoucher.getColumnModel().getColumn(1).setResizable(false);
            tblVoucher.getColumnModel().getColumn(2).setResizable(false);
            tblVoucher.getColumnModel().getColumn(3).setResizable(false);
            tblVoucher.getColumnModel().getColumn(4).setResizable(false);
            tblVoucher.getColumnModel().getColumn(5).setResizable(false);
            tblVoucher.getColumnModel().getColumn(6).setResizable(false);
            tblVoucher.getColumnModel().getColumn(7).setResizable(false);
            tblVoucher.getColumnModel().getColumn(8).setResizable(false);
        }

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Tìm kiếm Voucher :");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnLoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        dateKT1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Thời gian kết thúc :");

        dateBD1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Thời gian bắt đầu :");

        cboTrangThai1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoạt động", "Không hoạt động" }));
        cboTrangThai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThai1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(cboTrangThai1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(dateBD1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(dateKT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLoc)
                        .addComponent(jLabel15))
                    .addComponent(dateBD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(cboTrangThai1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateKT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaVoucherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaVoucherActionPerformed

    private void txtMucGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMucGiamGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMucGiamGiaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkValidate()) {
            int row = tblVoucher.getSelectedRow();
            if (row >= 0) {
                VoucherModer voucher = readForm();

                int result = service.update(voucher);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    fillTable(service.getAllVoucher());
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn voucher để sửa!");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherMouseClicked
        // TODO add your handling code here:
        index = tblVoucher.getSelectedRow();
        this.showData(index);

    }//GEN-LAST:event_tblVoucherMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnThemMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        VoucherModer voucher = readForm();
        if (service.checkTrungID(voucher.getID())) {
            JOptionPane.showMessageDialog(this, "Mã voucher  đã tồn tại!");
            return;
        }
        if (service.checkTrungTen(voucher.getTenVoucher())) {
            JOptionPane.showMessageDialog(this, "Tên voucher  đã tồn tại!");
            return;
        }
        // Kiểm tra ngày bắt đầu và ngày kết thúc
        java.util.Date ngayBD = dateBD.getDate();
        java.util.Date ngayHT = new java.util.Date(); // Ngày hiện tại
        if (ngayBD == null || ngayBD.before(ngayHT)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải là ngày hiện tại hoặc sau ngày hiện tại.");
            dateBD.requestFocus();
            return;
        }

        java.util.Date ngayKT = dateKT.getDate();
        if (ngayKT == null || ngayKT.before(ngayBD)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trống và phải là ngày sau ngày bắt đầu.");
            dateKT.requestFocus();
            return;
        }
        voucher.setID(service.getNewVoucherID());
        int result = service.insert(voucher);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            fillTable(service.getAllVoucher());
            clear();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        this.clear();
        fillTable(service.getAllVoucher());
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnHuyActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        String sa = txtTimKiem.getText();
        filter(sa);
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblVoucher.getSelectedRow();
        if (row >= 0) {
            String ID = tblVoucher.getValueAt(row, 1).toString();
            if (service.checkTonTaiHD(ID)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa khách hàng vì đang tồn tại trong bảng hóa đơn.");
                return;
            }
            int option = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn xóa vucher này không ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (service.delete(ID) > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!!");
                    fillTable(service.getAllVoucher());
                    clear();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn voucher để xóa");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        java.util.Date ngayBD = dateBD1.getDate();
        java.util.Date ngayKT = dateKT1.getDate();

        if (ngayBD != null && ngayKT != null) {
            java.sql.Date sqlNgayBD = new java.sql.Date(ngayBD.getTime());
            java.sql.Date sqlNgayKT = new java.sql.Date(ngayKT.getTime());
            List<VoucherModer> list = service.getAllVoucherByNgay(sqlNgayBD, sqlNgayKT);
            fillTable(list);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc");
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void cboTrangThai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThai1ActionPerformed
        String trangThai = (String) cboTrangThai1.getSelectedItem();
        List<VoucherModer> list = service.getAllVoucherByTrangThai(trangThai);
        fillTable(list);
    }//GEN-LAST:event_cboTrangThai1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoaiVoucher;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboTrangThai1;
    private com.toedter.calendar.JDateChooser dateBD;
    private com.toedter.calendar.JDateChooser dateBD1;
    private com.toedter.calendar.JDateChooser dateKT;
    private com.toedter.calendar.JDateChooser dateKT1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblVoucher;
    private javax.swing.JTextField txtMaVoucher;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtMucGiamGia;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenVoucher;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
