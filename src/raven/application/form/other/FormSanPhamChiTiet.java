package raven.application.form.other;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import java.math.BigDecimal;
import java.util.List;

import raven.application.model.ChatLieuModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.KichCoModel;
import raven.application.model.MauSacModel;
import raven.application.model.SanPhamModel;
import raven.application.model.ThuongHieuModel;
import raven.application.service.ChatLieuService;
import raven.application.service.ChiTietSanPhamService;
import raven.application.service.KichCoService;
import raven.application.service.MauSacService;
import raven.application.service.SanPhamService;
import raven.application.service.ThuongHieuService;

/**
 *
 * @author dungn
 */
public class FormSanPhamChiTiet extends javax.swing.JPanel {

        private DefaultTableModel model = new DefaultTableModel();
        private ChiTietSanPhamService ctsprp = new ChiTietSanPhamService();
        private SanPhamService sprs = new SanPhamService();
        private KichCoService kcrs = new KichCoService();
        private MauSacService msrs = new MauSacService();
        private ThuongHieuService thrs = new ThuongHieuService();
        private ChatLieuService clrs = new ChatLieuService();
        private int index = -1;

        DefaultComboBoxModel dcb_MS;
        DefaultComboBoxModel dcb_CL;
        DefaultComboBoxModel dcb_TH;
        DefaultComboBoxModel dcb_KC;
        DefaultComboBoxModel dcb_SP;

        public FormSanPhamChiTiet() {
                initComponents();
                this.fillTable(ctsprp.getAllCTSP());
                lb.putClientProperty(FlatClientProperties.STYLE, ""
                                + "font:$h1.font");

        }

        void fillTable(List<ChiTietSanPhamModel> listCTSP) {
                model = (DefaultTableModel) tblSPCT.getModel();
                model.setRowCount(0);
                Cbo_SanPham();
                Cbo_MauSac();
                Cbo_KichCo();
                Cbo_ThuongHieu();
                Cbo_ChatLieu();
                int index = 1;
                for (ChiTietSanPhamModel ctsp : listCTSP) {
                        ctsp.setStt(index++);
                        model.addRow(ctsp.toData());
                }
        }

        void showData(int index) {
                String maCTSP = String.valueOf(tblSPCT.getValueAt(index, 1)).trim();
                String TenSP = String.valueOf(tblSPCT.getValueAt(index, 2)).trim();
                String MauSac = String.valueOf(tblSPCT.getValueAt(index, 3)).trim();
                String KichThuoc = String.valueOf(tblSPCT.getValueAt(index, 4)).trim();
                String ChatLieu = String.valueOf(tblSPCT.getValueAt(index, 5)).trim();
                String ThuongHieu = String.valueOf(tblSPCT.getValueAt(index, 6)).trim();
                String GiaBan = String.valueOf(tblSPCT.getValueAt(index, 7)).trim();
                String SoLuong = String.valueOf(tblSPCT.getValueAt(index, 8)).trim();
                String MoTa = String.valueOf(tblSPCT.getValueAt(index, 9)).trim();
                // System.out.println(String.valueOf(tblSPCT.getValueAt(index, 9)).trim());
                txtMaCTSP.setText(maCTSP);
                txtGiaBan.setText(String.valueOf(GiaBan));
                txtMoTaCTSP.setText(MoTa);
                txtSoLuong.setText(String.valueOf(SoLuong));
                cboChatLieu.setSelectedItem(ChatLieu);
                cboThuonHieu.setSelectedItem(ThuongHieu);
                cboTenSP.setSelectedItem(TenSP);
                cboMauSac.setSelectedItem(MauSac);
                cboKichThuoc.setSelectedItem(KichThuoc);
        }

        ChiTietSanPhamModel readForm() {
                ChiTietSanPhamModel ctsp = new ChiTietSanPhamModel();

                String maCTSP = txtMaCTSP.getText().trim();
                System.out.println("Mã sản phẩm: " + maCTSP);
                ctsp.setID(maCTSP);

                String tenSP = cboTenSP.getSelectedItem().toString().trim();
                System.out.println("Tên sản phẩm: " + tenSP);
                SanPhamModel sanPhamModel = new SanPhamModel(null, tenSP, null);
                System.out.println("SanPhamModel: " + sanPhamModel);
                ctsp.setTenSP(sanPhamModel);

                String mauSac = cboMauSac.getSelectedItem().toString().trim();
                System.out.println("Màu sắc: " + mauSac);
                MauSacModel mauSacModel = new MauSacModel(null, mauSac,null);
                System.out.println("MauSacModel: " + mauSacModel);
                ctsp.setMauSac(mauSacModel);

                String kichThuoc = cboKichThuoc.getSelectedItem().toString().trim();
                System.out.println("Kích thước: " + kichThuoc);
                KichCoModel kichCoModel = new KichCoModel(null, kichThuoc,null);
                System.out.println("KichCoModel: " + kichCoModel);
                ctsp.setKichCo(kichCoModel);

                String chatLieu = cboChatLieu.getSelectedItem().toString().trim();
                System.out.println("Chất liệu: " + chatLieu);
                ChatLieuModel chatLieuModel = new ChatLieuModel(null, chatLieu,null);
                System.out.println("ChatLieuModel: " + chatLieuModel);
                ctsp.setChatLieu(chatLieuModel);

                String thuongHieu = cboThuonHieu.getSelectedItem().toString().trim();
                System.out.println("Thương hiệu: " + thuongHieu);
                ThuongHieuModel thuongHieuModel = new ThuongHieuModel(null, thuongHieu,null);
                System.out.println("ThuongHieuModel: " + thuongHieuModel);
                ctsp.setThuongHieu(thuongHieuModel);

                String giaBanStr = txtGiaBan.getText().trim();
                System.out.println("Giá bán (String): " + giaBanStr);
                BigDecimal giaBan = new BigDecimal(giaBanStr);
                System.out.println("Giá bán (BigDecimal): " + giaBan);
                ctsp.setGiaBan(giaBan);

                String soLuongTonStr = txtSoLuong.getText().trim();
                System.out.println("Số lượng tồn (String): " + soLuongTonStr);
                int soLuongTon = Integer.parseInt(soLuongTonStr);
                System.out.println("Số lượng tồn (int): " + soLuongTon);
                ctsp.setSoLuongTon(soLuongTon);

                String moTa = txtMoTaCTSP.getText().trim();
                System.out.println("Mô tả: " + moTa);
                ctsp.setMoTa(moTa);

                return ctsp;
        }

        void Cbo_MauSac() {
                List<MauSacModel> listMS = msrs.getALLMauSac();
                String[] cbo = new String[listMS.size()];
                for (int i = 0; i < listMS.size(); i++) {
                        cbo[i] = listMS.get(i).getTenMS();
                }
                cboMauSac.setModel(new DefaultComboBoxModel<>(cbo));
                // cboFilterMauSac.setModel(new DefaultComboBoxModel<>(cbo));

        }

        void Cbo_KichCo() {
                List<KichCoModel> listKC = kcrs.getALLChatLieu();
                String[] cbo = new String[listKC.size()];
                for (int i = 0; i < listKC.size(); i++) {
                        cbo[i] = listKC.get(i).getTenSize();
                }
                cboKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
                // cboFilterKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
        }

        void Cbo_SanPham() {
                List<SanPhamModel> listSP = sprs.getAllSP();
                String[] cbo = new String[listSP.size()];
                for (int i = 0; i < listSP.size(); i++) {
                        cbo[i] = listSP.get(i).getTenSP();
                }
                cboTenSP.setModel(new DefaultComboBoxModel<>(cbo));
                // cboFilterSP.setModel(new DefaultComboBoxModel<>(cbo));
        }

        void Cbo_ThuongHieu() {
                List<ThuongHieuModel> listTH = thrs.getALLThuongHieu();
                String[] cbo = new String[listTH.size()];
                for (int i = 0; i < listTH.size(); i++) {
                        cbo[i] = listTH.get(i).getTenTH();
                }
                cboThuonHieu.setModel(new DefaultComboBoxModel<>(cbo));
                // cboFilterThuongHieu.setModel(new DefaultComboBoxModel<>(cbo));
        }

        void Cbo_ChatLieu() {
                List<ChatLieuModel> listCL = clrs.getALLChatLieu();
                String[] cbo = new String[listCL.size()];
                for (int i = 0; i < listCL.size(); i++) {
                        cbo[i] = listCL.get(i).getTenCL();
                }
                cboChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
                // cboFilterChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel2 = new javax.swing.JPanel();
                lb = new javax.swing.JLabel();
                jPanel1 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                txtMaCTSP = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                txtSoLuong = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                txtGiaBan = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                txtMoTaCTSP = new javax.swing.JTextArea();
                btnAddMauSac = new javax.swing.JButton();
                btnAddKichThuoc = new javax.swing.JButton();
                btnAddChatLieu = new javax.swing.JButton();
                btnAddThuongHieu = new javax.swing.JButton();
                cboMauSac = new javax.swing.JComboBox<>();
                cboKichThuoc = new javax.swing.JComboBox<>();
                cboTenSP = new javax.swing.JComboBox<>();
                cboChatLieu = new javax.swing.JComboBox<>();
                cboThuonHieu = new javax.swing.JComboBox<>();
                jPanel3 = new javax.swing.JPanel();
                btnAddCTSP = new javax.swing.JButton();
                btnUpdateCTSP = new javax.swing.JButton();
                btnReset = new javax.swing.JButton();
                btnDeleteCTSP = new javax.swing.JButton();
                btnXuatExcel = new javax.swing.JButton();
                btnImportExcel = new javax.swing.JButton();
                jPanel6 = new javax.swing.JPanel();
                jScrollPane4 = new javax.swing.JScrollPane();
                tblSPCT = new javax.swing.JTable();
                jLabel12 = new javax.swing.JLabel();
                txtTimKiem = new javax.swing.JTextField();
                cboFilterMauSac = new javax.swing.JComboBox<>();
                cboFilterKichThuoc = new javax.swing.JComboBox<>();
                cboFilterChatLieu = new javax.swing.JComboBox<>();
                cboFilterThuongHieu = new javax.swing.JComboBox<>();
                jLabel14 = new javax.swing.JLabel();
                cboFilterTrangThai = new javax.swing.JComboBox<>();
                cboFilterSP = new javax.swing.JComboBox<>();

                setPreferredSize(new java.awt.Dimension(1295, 713));

                jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

                lb.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
                lb.setText("Sản Phẩm Chi Tiết");

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(lb)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(lb)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED),
                                "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

                jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel1.setText("Tên sản phẩm:");

                jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel2.setText("Mã SPCT");

                txtMaCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

                jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel3.setText("Giá bán");

                txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

                jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel4.setText("Số lượng tồn");
                jLabel4.setToolTipText("");

                txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

                jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel5.setText("Màu sắc");

                jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel6.setText("Kích thước");

                jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel7.setText("Chất liệu");

                jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel8.setText("Thương hiệu");

                jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel9.setText("Mô tả");

                txtMoTaCTSP.setColumns(20);
                txtMoTaCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtMoTaCTSP.setRows(5);
                jScrollPane1.setViewportView(txtMoTaCTSP);

                btnAddMauSac.setBackground(new java.awt.Color(0, 204, 204));
                btnAddMauSac.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnAddMauSac.setText("+");

                btnAddKichThuoc.setBackground(new java.awt.Color(0, 204, 204));
                btnAddKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnAddKichThuoc.setText("+");

                btnAddChatLieu.setBackground(new java.awt.Color(0, 204, 204));
                btnAddChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnAddChatLieu.setText("+");

                btnAddThuongHieu.setBackground(new java.awt.Color(0, 204, 204));
                btnAddThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnAddThuongHieu.setText("+");

                cboMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cboMauSac.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboMauSacActionPerformed(evt);
                        }
                });

                cboKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cboKichThuoc.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboKichThuocActionPerformed(evt);
                        }
                });

                cboTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboTenSP.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cboTenSP.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboTenSPActionPerformed(evt);
                        }
                });

                cboChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cboChatLieu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboChatLieuActionPerformed(evt);
                        }
                });

                cboThuonHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboThuonHieu.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(22, 22, 22)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel1)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(jLabel4)
                                                                                .addComponent(jLabel3))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(txtMaCTSP,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                176, Short.MAX_VALUE)
                                                                                .addComponent(txtSoLuong)
                                                                                .addComponent(txtGiaBan)
                                                                                .addComponent(cboTenSP, 0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addGap(51, 51, 51)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jLabel6)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(jLabel8))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(cboMauSac, 0, 176,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(cboKichThuoc, 0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(cboChatLieu, 0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(cboThuonHieu,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                171,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(btnAddMauSac)
                                                                                                                                .addGap(19, 19, 19))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                .addComponent(btnAddThuongHieu)
                                                                                                                                                .addGap(18, 18, 18)))
                                                                                                .addComponent(jLabel9))
                                                                                .addComponent(btnAddKichThuoc)
                                                                                .addComponent(btnAddChatLieu))
                                                                .addGap(27, 27, 27)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                221,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(58, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel1)
                                                                                                                .addComponent(jLabel5)
                                                                                                                .addComponent(jLabel9)
                                                                                                                .addComponent(btnAddMauSac)
                                                                                                                .addComponent(cboMauSac,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(cboTenSP,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel2)
                                                                                                                .addComponent(txtMaCTSP,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(jLabel6)
                                                                                                                .addComponent(btnAddKichThuoc)
                                                                                                                .addComponent(cboKichThuoc,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(txtSoLuong,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(jLabel4)
                                                                                                                .addComponent(jLabel7)
                                                                                                                .addComponent(btnAddChatLieu)
                                                                                                                .addComponent(cboChatLieu,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addComponent(jScrollPane1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(txtGiaBan,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(jLabel3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                16,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(jLabel8)
                                                                                                .addComponent(btnAddThuongHieu)
                                                                                                .addComponent(cboThuonHieu,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(53, Short.MAX_VALUE)));

                jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED),
                                "Tương tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

                btnAddCTSP.setBackground(new java.awt.Color(0, 204, 51));
                btnAddCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                btnAddCTSP.setText("Thêm sản phẩm");
                btnAddCTSP.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnAddCTSPActionPerformed(evt);
                        }
                });

                btnUpdateCTSP.setBackground(new java.awt.Color(0, 153, 153));
                btnUpdateCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                btnUpdateCTSP.setText("Cập nhật sản phẩm");
                btnUpdateCTSP.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnUpdateCTSPActionPerformed(evt);
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

                btnDeleteCTSP.setBackground(new java.awt.Color(255, 0, 0));
                btnDeleteCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                btnDeleteCTSP.setText("Xóa sản phẩm");
                btnDeleteCTSP.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDeleteCTSPActionPerformed(evt);
                        }
                });

                btnXuatExcel.setBackground(new java.awt.Color(0, 204, 204));
                btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                btnXuatExcel.setText("Xuất Excel");
                btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXuatExcelActionPerformed(evt);
                        }
                });

                btnImportExcel.setBackground(new java.awt.Color(204, 204, 0));
                btnImportExcel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                btnImportExcel.setText("Import Excel");
                btnImportExcel.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImportExcelActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(btnAddCTSP,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(btnUpdateCTSP,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                193, Short.MAX_VALUE)
                                                                                .addComponent(btnReset,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(btnDeleteCTSP,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(btnXuatExcel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(btnImportExcel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(btnAddCTSP)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnUpdateCTSP)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnReset)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDeleteCTSP)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnXuatExcel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnImportExcel)
                                                                .addContainerGap(17, Short.MAX_VALUE)));

                jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED),
                                "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

                tblSPCT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null, null }
                                },
                                new String[] {
                                                "STT", "Mã CTSP", "Tên sản phẩm", "Màu sắc", "Kích thước",
                                                "Chất liệu", "Hãng", "Giá bán", "Số lượng", "Mô tả", "Chọn"
                                }));
                tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblSPCTMouseClicked(evt);
                        }
                });
                jScrollPane4.setViewportView(tblSPCT);

                jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                jLabel12.setText("Tìm kiếm");

                txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemKeyReleased(evt);
                        }
                });

                cboFilterMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboFilterMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                cboFilterKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboFilterKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                cboFilterChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboFilterChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                cboFilterThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboFilterThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel14.setText("Trạng thái");

                cboFilterTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Hoạt động", "Không hoạt động" }));

                cboFilterSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                cboFilterSP.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane4)
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel12)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(txtTimKiem,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                218,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cboFilterSP,
                                                                                                                0,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(cboFilterMauSac,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cboFilterKichThuoc,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cboFilterChatLieu,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cboFilterThuongHieu,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                130,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jLabel14)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(cboFilterTrangThai,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                171,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));
                jPanel6Layout.setVerticalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel12)
                                                                                .addComponent(txtTimKiem,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cboFilterMauSac,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cboFilterKichThuoc,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cboFilterChatLieu,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cboFilterThuongHieu,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel14)
                                                                                .addComponent(cboFilterTrangThai,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cboFilterSP,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(22, 22, 22)
                                                                .addComponent(jScrollPane4,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                291, Short.MAX_VALUE)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel6,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(jPanel1,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jPanel3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(jPanel1,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel3,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jPanel6,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(19, 19, 19)));
        }// </editor-fold>//GEN-END:initComponents

        private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboMauSacActionPerformed
                // TODO add your handling code here:
                // MauSacModel msmd = (MauSacModel) cboMauSac.getSelectedItem();
                // String sel = msmd.getTenMS();
                // List<ChiTietSanPhamModel> listMS = msrs.getALLMauSac(ten);
        }// GEN-LAST:event_cboMauSacActionPerformed

        private void cboKichThuocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboKichThuocActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_cboKichThuocActionPerformed

        private void cboTenSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboTenSPActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_cboTenSPActionPerformed

        private void cboChatLieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboChatLieuActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_cboChatLieuActionPerformed

        private void btnAddCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddCTSPActionPerformed
                // TODO add your handling code here:
                ChiTietSanPhamModel md = this.readForm();
                this.ctsprp.insert(md);
                this.fillTable(ctsprp.getAllCTSP());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
        }// GEN-LAST:event_btnAddCTSPActionPerformed

        private void btnUpdateCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnUpdateCTSPActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnUpdateCTSPActionPerformed

        private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResetActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnResetActionPerformed

        private void btnDeleteCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteCTSPActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnDeleteCTSPActionPerformed

        private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXuatExcelActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnXuatExcelActionPerformed

        private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnImportExcelActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnImportExcelActionPerformed

        private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimKiemKeyReleased
                // TODO add your handling code here:
        }// GEN-LAST:event_txtTimKiemKeyReleased

        private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblSPCTMouseClicked
                // TODO add your handling code here:
                index = tblSPCT.getSelectedRow();
                this.showData(index);
        }// GEN-LAST:event_tblSPCTMouseClicked

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnAddCTSP;
        private javax.swing.JButton btnAddChatLieu;
        private javax.swing.JButton btnAddKichThuoc;
        private javax.swing.JButton btnAddMauSac;
        private javax.swing.JButton btnAddThuongHieu;
        private javax.swing.JButton btnDeleteCTSP;
        private javax.swing.JButton btnImportExcel;
        private javax.swing.JButton btnReset;
        private javax.swing.JButton btnUpdateCTSP;
        private javax.swing.JButton btnXuatExcel;
        private javax.swing.JComboBox<String> cboChatLieu;
        private javax.swing.JComboBox<String> cboFilterChatLieu;
        private javax.swing.JComboBox<String> cboFilterKichThuoc;
        private javax.swing.JComboBox<String> cboFilterMauSac;
        private javax.swing.JComboBox<String> cboFilterSP;
        private javax.swing.JComboBox<String> cboFilterThuongHieu;
        private javax.swing.JComboBox<String> cboFilterTrangThai;
        private javax.swing.JComboBox<String> cboKichThuoc;
        private javax.swing.JComboBox<String> cboMauSac;
        private javax.swing.JComboBox<String> cboTenSP;
        private javax.swing.JComboBox<String> cboThuonHieu;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel12;
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
        private javax.swing.JPanel jPanel6;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JLabel lb;
        private javax.swing.JTable tblSPCT;
        private javax.swing.JTextField txtGiaBan;
        private javax.swing.JTextField txtMaCTSP;
        private javax.swing.JTextArea txtMoTaCTSP;
        private javax.swing.JTextField txtSoLuong;
        private javax.swing.JTextField txtTimKiem;
        // End of variables declaration//GEN-END:variables
}
