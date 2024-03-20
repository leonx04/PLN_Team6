/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.application.model.NhanVienModel;
import raven.application.service.NhanVienService;

/**
 *
 * @author acer
 */
public class FormNhanVien extends javax.swing.JPanel {

    /**
     * Creates new form FormNhanVien
     */
    private NhanVienService nhanVienService = new NhanVienService();
    int index = 0;

    private List<NhanVienModel> listNV = new ArrayList<>();
    
    public FormNhanVien() {
        initComponents();
        listNV = nhanVienService.selectAll();
        loadData(listNV);
    }

    public void loadData(List<NhanVienModel> nhanVienModels) {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            for (NhanVienModel nv : nhanVienModels) {
                String trangThai = "";
                if (nv.getTrangThai().equals("0")) {
                    trangThai = "Đang làm";
                } else if (nv.getTrangThai().equals("1")) {
                    trangThai = "Nghỉ làm";
                }

                Object[] row = {
                    nv.getId(),
                    nv.getHoTen(),
                    nv.getDiaChi(),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getNamSinh(),
                    nv.getGioiTinh(),
                    nv.getChucVu(),
                    nv.getMatKhau(),
                    trangThai
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData0() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVienModel> nhanViens = nhanVienService.selectAll_0();
            for (NhanVienModel nv : nhanViens) {
                String trangThai = "";
                if (nv.getTrangThai().equals("0")) {
                    trangThai = "Đang làm";
                } else if (nv.getTrangThai().equals("1")) {
                    trangThai = "Nghỉ làm";
                }

                Object[] row = {
                    nv.getId(),
                    nv.getHoTen(),
                    nv.getDiaChi(),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getNamSinh(),
                    nv.getGioiTinh(),
                    nv.getChucVu(),
                    nv.getMatKhau(),
                    trangThai
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData1() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVienModel> nhanViens = nhanVienService.selectAll_1();
            for (NhanVienModel nv : nhanViens) {
                String trangThai = "";
                if (nv.getTrangThai().equals("0")) {
                    trangThai = "Đang làm";
                } else if (nv.getTrangThai().equals("1")) {
                    trangThai = "Nghỉ làm";
                }

                Object[] row = {
                    nv.getId(),
                    nv.getHoTen(),
                    nv.getDiaChi(),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getNamSinh(),
                    nv.getGioiTinh(),
                    nv.getChucVu(),
                    nv.getMatKhau(),
                    trangThai
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void setModel(NhanVienModel model) {
        txtID.setText(model.getId());
        txtHoTen.setText(model.getHoTen());
        txtSDT.setText(model.getSdt());
        txtEmail.setText(model.getEmail());
        txtNamSinh.setText(String.valueOf(model.getNamSinh()));
        txtDiaChi.setText(model.getDiaChi());
        txtMatKhau.setText(model.getMatKhau());

        String gioiTinh = model.getGioiTinh();
        if (gioiTinh != null && gioiTinh.equals("Nam")) {
            rbNam.setSelected(true);
        } else if (gioiTinh != null && gioiTinh.equals("Nữ")) {
            rbNu.setSelected(true);
        }

        String chucVu = model.getChucVu();
        if (chucVu != null && chucVu.equals("Nhân viên")) {
            rbNhanVien.setSelected(true);
        } else if (chucVu != null && chucVu.equals("Quản lý")) {
            rbQuanLy.setSelected(true);
        }

    }

    NhanVienModel getModel() {
        NhanVienModel model = new NhanVienModel();
        model.setId(txtID.getText());
        model.setHoTen(txtHoTen.getText());
        model.setDiaChi(txtDiaChi.getText());
        model.setEmail(txtEmail.getText());
        model.setSdt(txtSDT.getText());
        model.setNamSinh(Integer.parseInt(txtNamSinh.getText()));
        model.setMatKhau(new String(txtMatKhau.getPassword()));

        String gioiTinh;
        if (rbNam.isSelected()) {
            gioiTinh = "Nam";
        } else if (rbNu.isSelected()) {
            gioiTinh = "Nữ";
        } else {
            gioiTinh = "";
        }
        model.setGioiTinh(gioiTinh);

        String chucVu;
        if (rbQuanLy.isSelected()) {
            chucVu = "Quản lý";
        } else if (rbNhanVien.isSelected()) {
            chucVu = "Nhân viên";
        } else {
            chucVu = "";
        }
        model.setChucVu(chucVu);

        return model;
    }

    void updateStatus() {
        boolean edit = (this.index >= 0);
        // Trạng thái form
        txtID.setEditable(!edit);
    }

    void clerForm() {
        listNV = nhanVienService.selectAll();
        loadData(listNV);
        this.setModel(new NhanVienModel());
        this.updateStatus();
        index = -1;
        updateStatus();
    }

    void insert() {
        if (isAnyFieldEmpty()) {
            JOptionPane.showMessageDialog(this, "Có trường đang bị trống");
            return;
        }

        if (isIDAlreadyExists()) {
            JOptionPane.showMessageDialog(this, "Không để trùng ID");
            return;
        }

        String email = txtEmail.getText();
        if (isInvalidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ.");
            return;
        }

        String phoneNumber = txtSDT.getText();
        if (isInvalidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.");
            return;
        }

        String input = txtNamSinh.getText();
        if (isInvalidInput(input)) {
            JOptionPane.showMessageDialog(this, "Năm sinh chỉ được nhập số và không quá 4 số");
            return;
        }

        NhanVienModel model = getModel();
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm mới?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (nhanVienService.insert(model)) {
                listNV = nhanVienService.selectAll();
                loadData(listNV);
                JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
                this.clerForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
            }
        }
    }

    void update() {
        if (isAnyFieldEmpty()) {
            JOptionPane.showMessageDialog(this, "Có trường đang bị trống");
            return;
        }

        String phoneNumber = txtSDT.getText();
        if (isInvalidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.");
            return;
        }

        String email = txtEmail.getText();
        if (isInvalidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ.");
            return;
        }

        String input = txtNamSinh.getText();
        if (isInvalidInput(input)) {
            JOptionPane.showMessageDialog(this, "Năm sinh chỉ được nhập số và không quá 4 số");
            return;
        }

        NhanVienModel model = getModel();
        setModel(model);

        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (nhanVienService.update(model)) {
                listNV = nhanVienService.selectAll();
                loadData(listNV);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        }
    }

    void delete() {
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String manv = txtID.getText();
            if (manv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên muốn xóa!");
                return;
            }

            try {
                nhanVienService.delete(manv);
                listNV = nhanVienService.selectAll();
                loadData(listNV);
                this.clerForm();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    boolean isInvalidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return !email.matches(emailRegex);
    }

    boolean isAnyFieldEmpty() {
        return txtID.getText().isEmpty()
                || txtHoTen.getText().isEmpty()
                || txtDiaChi.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtSDT.getText().isEmpty()
                || txtMatKhau.getText().isEmpty()
                || txtNamSinh.getText().isEmpty();
    }

    boolean isIDAlreadyExists() {
        String id = txtID.getText();
        ArrayList<NhanVienModel> list = (ArrayList<NhanVienModel>) this.nhanVienService.selectAll();
        for (NhanVienModel model : list) {
            if (id.equals(model.getId() + "")) {
                return true;
            }
        }
        return false;
    }

    boolean isInvalidInput(String input) {
        return !input.matches("\\d{1,4}");
    }

    boolean isInvalidPhoneNumber(String phoneNumber) {
        return !phoneNumber.matches("\\d{10}");
    }

    void edit() {
        String maNV = (String) tblNhanVien.getValueAt(this.index, 0);
        NhanVienModel model = nhanVienService.selectById(maNV);
        if (model != null) {
            setModel(model);
            this.updateStatus();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtNamSinh = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        rbNam = new javax.swing.JRadioButton();
        rbNu = new javax.swing.JRadioButton();
        txtMatKhau = new javax.swing.JPasswordField();
        rbNhanVien = new javax.swing.JRadioButton();
        rbQuanLy = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rbDangLam = new javax.swing.JRadioButton();
        rbDaNghiViec = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Quản Lý Nhân Viên");

        txtTimKiem.setToolTipText("");
        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Danh Sách Nhân Viên:");

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Nhân Viên", "Họ Tên", "Địa Chỉ", "SĐT", "Email", "Năm Sinh", "Giới Tính", "Chức Vụ", "Mật Khẩu", "Trạng Thái"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhanVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel13)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID Nhân Viên: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Họ Tên:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Giới Tính:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Năm Sinh:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Địa Chỉ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("SĐT:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Mật Khẩu:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Chức Vụ:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Email:");

        buttonGroup2.add(rbNam);
        rbNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbNam.setText("Nam");
        rbNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNamActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbNu);
        rbNu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbNu.setText("Nữ");

        buttonGroup3.add(rbNhanVien);
        rbNhanVien.setText("Nhân Viên");

        buttonGroup3.add(rbQuanLy);
        rbQuanLy.setText("Quản Lý");

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThem.setBackground(new java.awt.Color(0, 204, 51));
        btnThem.setText("Thêm Nhân Viên");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 153, 153));
        btnSua.setText("Sửa Nhân Viên");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(153, 153, 153));
        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setText("Nghỉ Việc");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Form điền thông tin nhân viên:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID)
                    .addComponent(txtHoTen)
                    .addComponent(txtNamSinh)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbNam)
                        .addGap(37, 37, 37)
                        .addComponent(rbNu)
                        .addGap(0, 42, Short.MAX_VALUE))
                    .addComponent(txtDiaChi))
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addComponent(txtEmail)
                        .addComponent(txtMatKhau))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbQuanLy)))
                .addGap(32, 32, 32)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(rbNam)
                            .addComponent(rbNu)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNamSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(rbNhanVien)
                            .addComponent(rbQuanLy))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Trạng Thái:");

        buttonGroup1.add(rbDangLam);
        rbDangLam.setText("Đang làm");
        rbDangLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDangLamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbDaNghiViec);
        rbDaNghiViec.setText("Đã nghỉ việc");
        rbDaNghiViec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDaNghiViecActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tìm Kiếm:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(365, 365, 365)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(247, 247, 247)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(rbDangLam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbDaNghiViec))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rbDangLam)
                    .addComponent(rbDaNghiViec)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 971, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbNamActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
         insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clerForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMousePressed
        if (evt.getClickCount() == 1) {
            this.index = tblNhanVien.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblNhanVienMousePressed

    private void rbDangLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDangLamActionPerformed
        // TODO add your handling code here:
        loadData0();
    }//GEN-LAST:event_rbDangLamActionPerformed

    private void rbDaNghiViecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDaNghiViecActionPerformed
        // TODO add your handling code here:
        loadData1();
    }//GEN-LAST:event_rbDaNghiViecActionPerformed

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        // TODO add your handling code here:
        listNV = nhanVienService.Search(txtTimKiem.getText());
        loadData(listNV);
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbDaNghiViec;
    private javax.swing.JRadioButton rbDangLam;
    private javax.swing.JRadioButton rbNam;
    private javax.swing.JRadioButton rbNhanVien;
    private javax.swing.JRadioButton rbNu;
    private javax.swing.JRadioButton rbQuanLy;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtID;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNamSinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
