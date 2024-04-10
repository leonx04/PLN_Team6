package raven.application.form.other;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatClientProperties;
import java.util.List;
import pagination.EventPagination;
import pagination.style.PaginationItemRenderStyle1;

import raven.application.model.SanPhamModel;
import raven.application.service.SanPhamService;

/**
 *
 * @author Raven
 */
public class FormSanPham extends javax.swing.JPanel {

    private DefaultTableModel model = new DefaultTableModel();
    private SanPhamService sprs = new SanPhamService();

// Định nghĩa số lượng bản ghi hiển thị trên mỗi trang
    private static final int RECORDS_PER_PAGE = 10;
    private int currentPage = 1; // Trang hiện tại

    public FormSanPham() {
        initComponents();
        init();
        fillTable(sprs.getAllSP());

        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        JComboBox<String> cboFilterTrangThai = new JComboBox<>(new String[]{"Tất cả", "Còn hàng", "Hết hàng"});
    }

    void fillTable(List<SanPhamModel> listSP) {
        model = (DefaultTableModel) tblSP.getModel();
        model.setRowCount(0);

        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE; // Bắt đầu từ bản ghi thứ startIndex
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, listSP.size()); // Lấy tối đa 10 bản ghi kể từ startIndex

        int index = startIndex + 1; // Số thứ tự của bản ghi đầu tiên trên trang

        for (int i = startIndex; i < endIndex; i++) {
            SanPhamModel sp = listSP.get(i);
            sp.setStt(index++);
            model.addRow(sp.toData());
        }

    }

    private void init() {
        // Cài đặt thanh phân trang
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                currentPage = page; // Cập nhật trang hiện tại khi chuyển trang
                fillTable(sprs.getAllSP()); // Hiển thị dữ liệu cho trang mới
            }
        });
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.setPagegination(1, 100); // Khởi tạo thanh trang với trang đầu tiên và tổng số trang
    }

    // Cập nhật phương thức setPagegination để cập nhật tổng số trang dựa trên số lượng bản ghi
    public void setPagegination(int current, int totalRecords) {
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
        pagination1.setPagegination(current, totalPages);
    }

    // Phương thức này sẽ được gọi khi dữ liệu thay đổi hoặc khi chuyển trang
    void refreshData() {
        int totalRecords = sprs.getAllSP().size();
        fillTable(sprs.getAllSP()); // Cập nhật dữ liệu cho bảng
        setPagegination(currentPage, totalRecords); // Cập nhật thanh trang
    }

    void showData(int index) {
        String ID = tblSP.getValueAt(index, 1).toString().trim();
        String tenSP = tblSP.getValueAt(index, 2).toString().trim();
        String moTa = tblSP.getValueAt(index, 3).toString().trim();

        txtMaSP.setText(ID);
        txtTenSP.setText(tenSP);
        txtMoTa.setText(moTa);
    }

    SanPhamModel read() {
        SanPhamModel sp = new SanPhamModel();
        sp.setID(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setMoTa(txtMoTa.getText());
        return sp;
    }

    void clear() {
        txtMaSP.setText(null);
        txtTenSP.setText(null);
        txtMoTa.setText(null);
        txtTimKiem.setText(null);
    }

    private boolean checkForm() {
        if (txtTenSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm !", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            txtTenSP.requestFocus();
            return false;
        }

        if (txtMoTa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả sản phẩm !", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            txtMoTa.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Lọc dữ liệu trong bảng theo biểu thức chính quy.
     *
     * @param query Biểu thức chính quy sử dụng để lọc dữ liệu.
     */
    private void filter(String query) {
        // Tạo một đối tượng TableRowSorter và gán cho bảng tblSP.
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        tblSP.setRowSorter(tableRowSorter);

        // Thiết lập bộ lọc sử dụng biểu thức chính quy và áp dụng cho TableRowSorter.
        // Sử dụng biểu thức chính quy không phân biệt chữ hoa chữ thường
        tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lb = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        btnAddSP = new javax.swing.JButton();
        btnUpdateSP = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDeleteSP = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        Cbo_TrangThai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        pagination1 = new raven.pagination.Pagination();

        setPreferredSize(new java.awt.Dimension(1295, 713));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lb.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lb.setText("Sản Phẩm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã sản phẩm:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên sản phẩm:");

        txtMaSP.setEditable(false);
        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mô tả:");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(60, 60, 60)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(57, 57, 57)
                        .addComponent(txtTenSP)))
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tương tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnAddSP.setBackground(new java.awt.Color(0, 204, 51));
        btnAddSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddSP.setText("Thêm sản phẩm");
        btnAddSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSPActionPerformed(evt);
            }
        });

        btnUpdateSP.setBackground(new java.awt.Color(0, 153, 153));
        btnUpdateSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateSP.setText("Cập nhật sản phẩm");
        btnUpdateSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSPActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 153, 153));
        btnReset.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnDeleteSP.setBackground(new java.awt.Color(255, 0, 0));
        btnDeleteSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteSP.setText("Xóa");
        btnDeleteSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSP)
                .addGap(27, 27, 27)
                .addComponent(btnUpdateSP)
                .addGap(28, 28, 28)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeleteSP)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Mô tả sản phẩm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSP);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        Cbo_TrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Cbo_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Còn hàng", "Hết Hàng" }));
        Cbo_TrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cbo_TrangThaiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Trạng thái");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(Cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(552, 552, 552))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Cbo_TrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cbo_TrangThaiActionPerformed
        String selectedTrangThai = (String) Cbo_TrangThai.getSelectedItem();
        List<SanPhamModel> listCTSP;

        if (selectedTrangThai.equals("Còn hàng")) {
            listCTSP = sprs.getAllSPSoluongLonHon0();
        } else if (selectedTrangThai.equals("Hết hàng")) {
            listCTSP = sprs.getAllSPSoluong0(); // Sử dụng phương thức getAllCTSPSoluong0 để lấy các sản phẩm có số lượng tồn bằng 0
        } else {
            listCTSP = sprs.getAllSP();
        }

        fillTable(listCTSP);
    }//GEN-LAST:event_Cbo_TrangThaiActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimKiemKeyReleased
        String query = txtTimKiem.getText();
        filter(query);
    }// GEN-LAST:event_txtTimKiemKeyReleased

    private void btnUpdateSPActionPerformed(java.awt.event.ActionEvent evt) {
        // Kiểm tra dữ liệu nhập vào
        if (!checkForm()) {
            return;
        } else {
            // Đọc dữ liệu từ form
            SanPhamModel spmd = this.read();
            SanPhamService sprs = new SanPhamService();
            int index = tblSP.getSelectedRow();

            int option = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn cập nhật thông tin sản phẩm này?", "Xác nhận cập nhật",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (sprs.update(spmd, spmd.getID()) > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thành công");
                    fillTable(sprs.getAllSP());
                    clear();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
                }
            }
        }
    }

    private void btnDeleteSPActionPerformed(java.awt.event.ActionEvent evt) {
        // Lấy chỉ mục của dòng được chọn trong bảng
        int rowDC = tblSP.getSelectedRow();
        if (rowDC >= 0) {
            // Lấy ID của sản phẩm từ cột thứ hai (index 1)
            String ID = tblSP.getValueAt(rowDC, 1).toString();

            // Hiển thị hộp thoại xác nhận
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (sprs.delete(ID) > 0) {
                    JOptionPane.showMessageDialog(this, "Xoá thành công sản phẩm!");
                    fillTable(sprs.getAllSP());
                    clear();
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa");
        }
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResetActionPerformed
        this.clear();
    }// GEN-LAST:event_btnResetActionPerformed

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblSPMouseClicked
        // TODO add your handling code here:
        int row = this.tblSP.getSelectedRow();
        if (model.getRowCount() > 0 && evt.getClickCount() == 1) {
            txtMaSP.setText(this.tblSP.getValueAt(row, 1).toString().trim());
            txtTenSP.setText(this.tblSP.getValueAt(row, 2).toString().trim());
            txtMoTa.setText(this.tblSP.getValueAt(row, 3).toString().trim());
        }
    }// GEN-LAST:event_tblSPMouseClicked

    private void btnAddSPActionPerformed(java.awt.event.ActionEvent evt) {
        String newID = sprs.getNewSanPhamID();
        SanPhamModel spmd = this.read();
        if (!checkForm()) {
            return;
        } else {
            spmd.setID(newID);
            if (sprs.insert(spmd) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                this.fillTable(sprs.getAllSP());
                clear();
            }
        }
    }// GEN-LAST:event_btnAddSPActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cbo_TrangThai;
    private javax.swing.JButton btnAddSP;
    private javax.swing.JButton btnDeleteSP;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdateSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb;
    private raven.pagination.Pagination pagination1;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
