package raven.application.form.other;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ItemEvent;
import java.io.FileInputStream;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pagination.EventPagination;
import pagination.style.PaginationItemRenderStyle1;

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

    // Định nghĩa số lượng bản ghi hiển thị trên mỗi trang
    private static final int RECORDS_PER_PAGE = 10;
    private int currentPage = 1; // Trang hiện tại

    public FormSanPhamChiTiet() {
        initComponents();
        init();
        fillTable(ctsprp.getAllCTSP());
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        JComboBox<String> cboFilterTrangThai = new JComboBox<>(new String[]{"Tất cả", "Còn hàng", "Hết hàng"});

    }

    void fillTable(List<ChiTietSanPhamModel> listCTSP) {
        model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        Cbo_SanPham();
        Cbo_MauSac();
        Cbo_KichCo();
        Cbo_ThuongHieu();
        Cbo_ChatLieu();

        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE; // Bắt đầu từ bản ghi thứ startIndex
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, listCTSP.size()); // Lấy tối đa 10 bản ghi kể từ startIndex

        int index = startIndex + 1; // Số thứ tự của bản ghi đầu tiên trên trang

        for (int i = startIndex; i < endIndex; i++) {
            ChiTietSanPhamModel ctsp = listCTSP.get(i);
            ctsp.setStt(index++);
            model.addRow(ctsp.toData());
        }
    }

    private void init() {
        // Cài đặt thanh phân trang
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                currentPage = page; // Cập nhật trang hiện tại khi chuyển trang
                fillTable(ctsprp.getAllCTSP()); // Hiển thị dữ liệu cho trang mới
            }
        });
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.setPagegination(1, 10); // Khởi tạo thanh trang với trang đầu tiên và tổng số trang
    }

    // Cập nhật phương thức setPagegination để cập nhật tổng số trang dựa trên số lượng bản ghi
    public void setPagegination(int current, int totalRecords) {
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
        pagination1.setPagegination(current, totalPages);
    }

    // Phương thức này sẽ được gọi khi dữ liệu thay đổi hoặc khi chuyển trang
    void refreshData() {
        int totalRecords = ctsprp.getAllCTSP().size();
        fillTable(ctsprp.getAllCTSP()); // Cập nhật dữ liệu cho bảng
        setPagegination(currentPage, totalRecords); // Cập nhật thanh trang
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

        // Dữ liệu cần thiết từ các trường nhập liệu
        String maCTSP = txtMaCTSP.getText().trim();
        String tenSP = cboTenSP.getSelectedItem().toString().trim();
        String mauSac = cboMauSac.getSelectedItem().toString().trim();
        String kichThuoc = cboKichThuoc.getSelectedItem().toString().trim();
        String chatLieu = cboChatLieu.getSelectedItem().toString().trim();
        String thuongHieu = cboThuonHieu.getSelectedItem().toString().trim();
        String giaBanStr = txtGiaBan.getText().trim();
        String soLuongTonStr = txtSoLuong.getText().trim();
        String moTa = txtMoTaCTSP.getText().trim();

        // Xác định ID tương ứng với tên sản phẩm đã chọn
        List<SanPhamModel> listSP = sprs.getIDByTenSP(tenSP);
        String maSP = null; //Biến này sẽ được sử dụng để lưu trữ ID của sản phẩm tương ứng nếu tìm thấy
        if (listSP.size() > 0) { // Kiểm tra xem danh sách có phần tử không
            maSP = listSP.get(0).getID();// Lấy ID của phần tử đầu tiên nếu danh sách không rỗng
        }

        // Xác định ID tương ứng với tên chất liệu đã chọn
        List<ChatLieuModel> listCL = clrs.getIDByTenCL(chatLieu);
        String maCL = null;
        if (listCL.size() > 0) {
            maCL = listCL.get(0).getID();
        }

        // Xác định ID tương ứng với tên kích cỡ đã chọn
        List<KichCoModel> listKC = kcrs.getIDByTenKC(kichThuoc);
        String maKC = null;
        if (listKC.size() > 0) {
            maKC = listKC.get(0).getID();
        }

        // Xác định ID tương ứng với tên màu sắc đã chọn
        List<MauSacModel> listMS = msrs.getIDByTenMS(mauSac);
        String maMS = null;
        if (listMS.size() > 0) {
            maMS = listMS.get(0).getID();
        }

        // Xác định ID tương ứng với tên kích cỡ đã chọn
        List<ThuongHieuModel> listTH = thrs.getIDByTenTH(thuongHieu);
        String maTH = null;
        if (listTH.size() > 0) {
            maTH = listTH.get(0).getID();
        }

        // Thiết lập dữ liệu trực tiếp vào đối tượng ChiTietSanPhamModel
        ctsp.setID(maCTSP);
        ctsp.setTenSP(new SanPhamModel(maSP, tenSP, null)); // Sử dụng mã sản phẩm thay vì tên
        ctsp.setMauSac(new MauSacModel(maMS, mauSac, null));
        ctsp.setKichCo(new KichCoModel(maKC, kichThuoc, null)); // Sử dụng mã kích cỡ thay vì tên
        ctsp.setChatLieu(new ChatLieuModel(maCL, chatLieu, null));// Sử dụng mã chất liệu thay vì tên
        ctsp.setThuongHieu(new ThuongHieuModel(maTH, thuongHieu, null));
        ctsp.setGiaBan(new BigDecimal(giaBanStr));
        ctsp.setSoLuongTon(Integer.parseInt(soLuongTonStr));
        ctsp.setMoTa(moTa);

        // In dữ liệu đã nhập ra console (nếu cần)
        System.out.println("Mã sản phẩm: " + maCTSP);
        System.out.println("Mã sản phẩm: " + maSP);
        System.out.println("Tên sản phẩm: " + tenSP);
        System.out.println("Màu sắc: " + mauSac);
        System.out.println("Kích thước: " + kichThuoc);
        System.out.println("Chất liệu: " + chatLieu);
        System.out.println("Thương hiệu: " + thuongHieu);
        System.out.println("Giá bán (String): " + giaBanStr);
        System.out.println("Số lượng tồn (String): " + soLuongTonStr);
        System.out.println("Mô tả: " + moTa);

        return ctsp;
    }

    void Cbo_MauSac() {
        List<MauSacModel> listMS = msrs.getALLMauSac();
        String[] cbo = new String[listMS.size()];
        for (int i = 0; i < listMS.size(); i++) {
            cbo[i] = listMS.get(i).getTenMS();
        }
        cboMauSac.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterMauSac.setModel(new DefaultComboBoxModel<>(cbo));

    }

    void Cbo_FilterMauSac() {
        List<MauSacModel> listMS = msrs.getALLMauSac();
        String[] cbo = new String[listMS.size()];
        for (int i = 0; i < listMS.size(); i++) {
            cbo[i] = listMS.get(i).getTenMS();
        }
        cboFilterMauSac.setModel(new DefaultComboBoxModel<>(cbo));

    }

    void Cbo_KichCo() {
        List<KichCoModel> listKC = kcrs.getALLKichCo();
        String[] cbo = new String[listKC.size()];
        for (int i = 0; i < listKC.size(); i++) {
            cbo[i] = listKC.get(i).getTenSize();
        }
        cboKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilterKichCo() {
        List<KichCoModel> listKC = kcrs.getALLKichCo();
        String[] cbo = new String[listKC.size()];
        for (int i = 0; i < listKC.size(); i++) {
            cbo[i] = listKC.get(i).getTenSize();
        }
        cboFilterKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_SanPham() {
        List<SanPhamModel> listSP = sprs.getAllSP();
        String[] cbo = new String[listSP.size()];
        for (int i = 0; i < listSP.size(); i++) {
            cbo[i] = listSP.get(i).getTenSP();
        }
        cboTenSP.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterSP.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilTerSanPham() {
        List<SanPhamModel> listSP = sprs.getAllSP();
        String[] cbo = new String[listSP.size()];
        for (int i = 0; i < listSP.size(); i++) {
            cbo[i] = listSP.get(i).getTenSP();
        }
        cboFilterSP.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_ThuongHieu() {
        List<ThuongHieuModel> listTH = thrs.getALLThuongHieu();
        String[] cbo = new String[listTH.size()];
        for (int i = 0; i < listTH.size(); i++) {
            cbo[i] = listTH.get(i).getTenTH();
        }
        cboThuonHieu.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterThuongHieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilterThuongHieu() {
        List<ThuongHieuModel> listTH = thrs.getALLThuongHieu();
        String[] cbo = new String[listTH.size()];
        for (int i = 0; i < listTH.size(); i++) {
            cbo[i] = listTH.get(i).getTenTH();
        }
        cboFilterThuongHieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_ChatLieu() {
        List<ChatLieuModel> listCL = clrs.getALLChatLieu();
        String[] cbo = new String[listCL.size()];
        for (int i = 0; i < listCL.size(); i++) {
            cbo[i] = listCL.get(i).getTenCL();
        }
        cboChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilterChatLieu() {
        List<ChatLieuModel> listCL = clrs.getALLChatLieu();
        String[] cbo = new String[listCL.size()];
        for (int i = 0; i < listCL.size(); i++) {
            cbo[i] = listCL.get(i).getTenCL();
        }
        cboFilterChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void clear() {
        txtMaCTSP.setText(null);
        txtGiaBan.setText(null);
        txtMoTaCTSP.setText(null);
        txtSoLuong.setText(null);
        txtTimKiem.setText(null);
        fillTable(ctsprp.getAllCTSP());
    }

    private void filter(String querry) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblSPCT.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(querry));
    }

    void openMauSac() {
        new FormMauSac().setVisible(true);
    }

    void openChatlieu() {
        new FormChatLieu().setVisible(true);
    }

    void openThuonHieu() {
        new FormThuongHieu().setVisible(true);
    }

    void openKichThuoc() {
        new FormKichThuoc().setVisible(true);
    }

    private String selectedSanPhamID = null;
    private String selectedFilterSPItem = null;
    private String selectedMauSacID = null;
    private String selectedFilterMSItem = null;
    private String selectedSizeID = null;
    private String selectedFilterSizeItem = null;
    private String selectedChatLieuID = null;
    private String selectedFilterChatLieuItem = null;
    private String selectedThuongHieuID = null;
    private String selectedFilterThuongHieuItem = null;

    public boolean validatef() {
        // Dữ liệu cần thiết từ các trường nhập liệu
        String tenSP = cboTenSP.getSelectedItem().toString().trim();
        String mauSac = cboMauSac.getSelectedItem().toString().trim();
        String kichThuoc = cboKichThuoc.getSelectedItem().toString().trim();
        String chatLieu = cboChatLieu.getSelectedItem().toString().trim();
        String thuongHieu = cboThuonHieu.getSelectedItem().toString().trim();
        String giaBanStr = txtGiaBan.getText().trim();
        String soLuongTonStr = txtSoLuong.getText().trim();
        String moTa = txtMoTaCTSP.getText().trim();

        // Kiểm tra trường nào đó có trống không
        if (tenSP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tên sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (mauSac.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn màu sắc", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (kichThuoc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn kích thước", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (chatLieu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn chất liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (thuongHieu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thương hiệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (giaBanStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập giá bán", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (soLuongTonStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng tồn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (moTa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mô tả", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra giá bán có phải là số và lớn hơn 1000 không
        try {
            BigDecimal giaBan = new BigDecimal(giaBanStr);
            int soLuongTon = Integer.parseInt(soLuongTonStr);
            if (giaBan.compareTo(BigDecimal.valueOf(1000)) <= 0) {
                // Thông báo lỗi
                JOptionPane.showMessageDialog(null, "Giá bán phải lớn hơn 1000", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            // Thông báo lỗi nếu giá bán không phải là số
            JOptionPane.showMessageDialog(null, "Giá bán phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
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
        pagination1 = new raven.pagination.Pagination();

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên sản phẩm:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã SPCT");

        txtMaCTSP.setEditable(false);
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
        jLabel6.setText("Size");

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
        btnAddMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMauSacActionPerformed(evt);
            }
        });

        btnAddKichThuoc.setBackground(new java.awt.Color(0, 204, 204));
        btnAddKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddKichThuoc.setText("+");
        btnAddKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKichThuocActionPerformed(evt);
            }
        });

        btnAddChatLieu.setBackground(new java.awt.Color(0, 204, 204));
        btnAddChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddChatLieu.setText("+");
        btnAddChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatLieuActionPerformed(evt);
            }
        });

        btnAddThuongHieu.setBackground(new java.awt.Color(0, 204, 204));
        btnAddThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddThuongHieu.setText("+");
        btnAddThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThuongHieuActionPerformed(evt);
            }
        });

        cboMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        cboKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuocActionPerformed(evt);
            }
        });

        cboTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTenSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenSPActionPerformed(evt);
            }
        });

        cboChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieuActionPerformed(evt);
            }
        });

        cboThuonHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThuonHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaCTSP)
                    .addComponent(txtSoLuong)
                    .addComponent(txtGiaBan)
                    .addComponent(cboTenSP, 0, 176, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboMauSac, 0, 176, Short.MAX_VALUE)
                    .addComponent(cboKichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboThuonHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAddMauSac)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAddThuongHieu)
                                .addGap(18, 18, 18)))
                        .addComponent(jLabel9))
                    .addComponent(btnAddKichThuoc)
                    .addComponent(btnAddChatLieu))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(btnAddMauSac)
                            .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(btnAddKichThuoc)
                            .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(btnAddChatLieu)
                            .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(btnAddThuongHieu)
                        .addComponent(cboThuonHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tương tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXuatExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImportExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddCTSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateCTSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteCTSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatExcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImportExcel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblSPCT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên sản phẩm", "Màu sắc", "Size", "Chất liệu", "Hãng", "Giá bán", "Số lượng", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSPCT);
        if (tblSPCT.getColumnModel().getColumnCount() > 0) {
            tblSPCT.getColumnModel().getColumn(0).setResizable(false);
            tblSPCT.getColumnModel().getColumn(1).setResizable(false);
            tblSPCT.getColumnModel().getColumn(2).setResizable(false);
            tblSPCT.getColumnModel().getColumn(4).setResizable(false);
            tblSPCT.getColumnModel().getColumn(5).setResizable(false);
            tblSPCT.getColumnModel().getColumn(6).setResizable(false);
            tblSPCT.getColumnModel().getColumn(7).setResizable(false);
            tblSPCT.getColumnModel().getColumn(8).setResizable(false);
            tblSPCT.getColumnModel().getColumn(9).setResizable(false);
        }

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        cboFilterMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterMauSacActionPerformed(evt);
            }
        });

        cboFilterKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterKichThuocActionPerformed(evt);
            }
        });

        cboFilterChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterChatLieuActionPerformed(evt);
            }
        });

        cboFilterThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterThuongHieuActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Trạng thái");

        cboFilterTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Còn hàng", "Hết hàng" }));
        cboFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterTrangThaiActionPerformed(evt);
            }
        });

        cboFilterSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterSPItemStateChanged(evt);
            }
        });
        cboFilterSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilterSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilterKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilterChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cboFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(495, 495, 495)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboFilterSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterSPActionPerformed
        selectedFilterSPItem = (String) cboFilterSP.getSelectedItem();
        String selectedSanPham = selectedFilterSPItem;
        List<SanPhamModel> listSP = sprs.getIDByTenSP(selectedSanPham);
        if (listSP.size() > 0) {
            selectedSanPhamID = listSP.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = ctsprp.searchBySanPhamID(selectedSanPhamID);
            fillTable(listCTSP);
            cboFilterSP.setSelectedItem(selectedFilterSPItem);
        } else {
            selectedSanPhamID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterSP.setSelectedItem(selectedFilterSPItem);
        }
    }//GEN-LAST:event_cboFilterSPActionPerformed

    private void cboFilterSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterSPItemStateChanged

    }//GEN-LAST:event_cboFilterSPItemStateChanged

    private void cboFilterMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterMauSacActionPerformed
        selectedFilterMSItem = (String) cboFilterMauSac.getSelectedItem();
        selectedMauSacID = selectedFilterMSItem;
        List<MauSacModel> listMS = msrs.getIDByTenMS(selectedMauSacID);
        if (listMS.size() > 0) {
            selectedMauSacID = listMS.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = ctsprp.searchByMauSacID(selectedMauSacID);
            fillTable(listCTSP);
            cboFilterMauSac.setSelectedItem(selectedFilterMSItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterMauSac.setSelectedItem(selectedFilterMSItem);
        }
    }//GEN-LAST:event_cboFilterMauSacActionPerformed

    private void cboFilterKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterKichThuocActionPerformed
        selectedFilterSizeItem = (String) cboFilterKichThuoc.getSelectedItem();
        selectedSizeID = selectedFilterSizeItem;
        List<KichCoModel> listKC = kcrs.getIDByTenKC(selectedSizeID);
        if (listKC.size() > 0) {
            selectedSizeID = listKC.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = ctsprp.searchBySizeID(selectedSizeID);
            fillTable(listCTSP);
            cboFilterKichThuoc.setSelectedItem(selectedFilterSizeItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterKichThuoc.setSelectedItem(selectedFilterSizeItem);
        }
    }//GEN-LAST:event_cboFilterKichThuocActionPerformed

    private void cboFilterChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterChatLieuActionPerformed
        selectedFilterChatLieuItem = (String) cboFilterChatLieu.getSelectedItem();
        selectedChatLieuID = selectedFilterChatLieuItem;
        List<ChatLieuModel> listCL = clrs.getIDByTenCL(selectedChatLieuID);
        if (listCL.size() > 0) {
            selectedChatLieuID = listCL.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = ctsprp.searchByChatLieuID(selectedChatLieuID);
            fillTable(listCTSP);
            cboFilterChatLieu.setSelectedItem(selectedFilterChatLieuItem);
        } else {
            selectedChatLieuID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterChatLieu.setSelectedItem(selectedFilterChatLieuItem);
        }
    }//GEN-LAST:event_cboFilterChatLieuActionPerformed

    private void cboFilterThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterThuongHieuActionPerformed
        selectedFilterThuongHieuItem = (String) cboFilterThuongHieu.getSelectedItem();
        selectedThuongHieuID = selectedFilterThuongHieuItem;
        List<ThuongHieuModel> listTH = thrs.getIDByTenTH(selectedThuongHieuID);
        if (listTH.size() > 0) {
            selectedThuongHieuID = listTH.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = ctsprp.searchByThuonghieuID(selectedThuongHieuID);
            fillTable(listCTSP);
            cboFilterThuongHieu.setSelectedItem(selectedFilterThuongHieuItem);
        } else {
            selectedThuongHieuID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterThuongHieu.setSelectedItem(selectedFilterThuongHieuItem); // Thiết lập lại giá trị của combobox
        }
    }//GEN-LAST:event_cboFilterThuongHieuActionPerformed

    private void cboFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterTrangThaiActionPerformed
        String selectedTrangThai = (String) cboFilterTrangThai.getSelectedItem();
        List<ChiTietSanPhamModel> listCTSP;

        if (selectedTrangThai.equals("Còn hàng")) {
            listCTSP = ctsprp.getAllCTSPSoluongLonHon0();
        } else if (selectedTrangThai.equals("Hết hàng")) {
            listCTSP = ctsprp.getAllCTSPSoluong0(); // Sử dụng phương thức getAllCTSPSoluong0 để lấy các sản phẩm có số
            // lượng tồn bằng 0
        } else {
            listCTSP = ctsprp.getAllCTSP();
        }

        fillTable(listCTSP);
    }//GEN-LAST:event_cboFilterTrangThaiActionPerformed

    private void btnAddMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddMauSacActionPerformed
        this.openMauSac();
    }// GEN-LAST:event_btnAddMauSacActionPerformed

    private void btnAddKichThuocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddKichThuocActionPerformed
        this.openKichThuoc();
    }// GEN-LAST:event_btnAddKichThuocActionPerformed

    private void btnAddChatLieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddChatLieuActionPerformed
        this.openChatlieu();

    }// GEN-LAST:event_btnAddChatLieuActionPerformed

    private void btnAddThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddThuongHieuActionPerformed
        this.openThuonHieu();
    }// GEN-LAST:event_btnAddThuongHieuActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboMauSacActionPerformed

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
        if (ctsprp.checkTrungId(txtMaCTSP.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Mã chi tiết sản phẩm đã tồn tại!");
            txtMaCTSP.requestFocus();
            return;
        }
        if (!validatef()) {
            return;
        }
        ChiTietSanPhamModel md = this.readForm();
        String newID = ctsprp.getNewSPCTID();
        md.setID(newID);
        this.ctsprp.insert(md);
        this.fillTable(ctsprp.getAllCTSP());
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        this.clear();
    }// GEN-LAST:event_btnAddCTSPActionPerformed

    private void btnUpdateCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnUpdateCTSPActionPerformed
        int rowSel = tblSPCT.getSelectedRow();
        if (rowSel == -1) {
            // Kiểm tra xem có dòng nào được chọn không
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để cập nhật.");
            return;
        }
        if(!validatef()){
            return;
        }
        // Xác nhận cập nhật
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật sản phẩm này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Nếu không được xác nhận, thoát khỏi phương thức
        }

        ChiTietSanPhamModel md = this.readForm();
        this.ctsprp.update(md);
        this.fillTable(ctsprp.getAllCTSP());
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        this.clear();
    }// GEN-LAST:event_btnUpdateCTSPActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResetActionPerformed
        clear();
    }// GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteCTSPActionPerformed(java.awt.event.ActionEvent evt) {
        // Lấy chỉ mục của dòng được chọn trong bảng
        int rowSel = tblSPCT.getSelectedRow();
        if (rowSel >= 0) {
            // Lấy mã sản phẩm chi tiết từ cột thứ hai (index 1)
            String ma = tblSPCT.getValueAt(rowSel, 1).toString();

            // Hiển thị hộp thoại xác nhận
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Nếu người dùng đồng ý xóa
                if (ctsprp.delete(ma) > 0) {
                    JOptionPane.showMessageDialog(this, "Xoá thành công!");
                    fillTable(ctsprp.getAllCTSP());
                    clear();
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa");
        }
    }

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnImportExcelActionPerformed
        // TODO add your handling code here:
        // 1. Xác định đường dẫn thư mục lưu file mặc định
        String currentDirectoryFilePath = "A:\\";

        // 2. Tạo cửa sổ chọn file cho người dùng
        JFileChooser excelImportChooser = new JFileChooser(currentDirectoryFilePath);

        // 3. Bộ lọc chỉ hiển thị các file Excel
        FileNameExtensionFilter excelFNEF = new FileNameExtensionFilter("Tệp Excel", "xls", "xlsx", "xlsm");
        excelImportChooser.setFileFilter(excelFNEF);

        // 4. Đặt tiêu đề cho cửa sổ chọn file
        excelImportChooser.setDialogTitle("Mở tập tin Excel");

        // 5. Hiển thị cửa sổ chọn file và kiểm tra kết quả
        int excelChooser = excelImportChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {

            // 6. Tạo Workbook và Sheet mới trong Excel
            try {
                XSSFWorkbook excelWorkbook = new XSSFWorkbook(new FileInputStream(excelImportChooser.getSelectedFile()));
                XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

                // 7. Lấy số hàng trong sheet
                int rowCount = excelSheet.getLastRowNum() + 1;

                // 8. Duyệt qua từng hàng trong sheet
                for (int i = 1; i < rowCount; i++) {

                    // 9. Lấy dòng thứ i
                    XSSFRow excelRow = excelSheet.getRow(i);

                    // 10. Duyệt qua từng cột trong dòng
                    for (int j = 0; j < excelRow.getLastCellNum(); j++) {

                        // 11. Lấy ô thứ j trong dòng thứ i
                        XSSFCell excelCell = excelRow.getCell(j);

                        // 12. Lấy giá trị của ô
                        String cellValue = excelCell.getStringCellValue();

                        // 13. Gán giá trị của ô vào bảng
                        int stt = model.getRowCount() + 1;
                        model.addRow(new Object[]{stt, cellValue});
                    }
                }

                // 14. Thông báo nhập dữ liệu thành công
                JOptionPane.showMessageDialog(this, "Nhập dữ liệu thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                // Hiển thị thông báo lỗi cho người dùng
                JOptionPane.showMessageDialog(this, "Lỗi khi mở file Excel: " + e.getMessage());
            }
        } else {
            // 15. Thông báo người dùng hủy chọn file
            JOptionPane.showMessageDialog(this, "Bạn đã hủy chọn file!");
        }
    }// GEN-LAST:event_btnImportExcelActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimKiemKeyReleased
        String query = txtTimKiem.getText();
        filter(query);
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
    private raven.pagination.Pagination pagination1;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaCTSP;
    private javax.swing.JTextArea txtMoTaCTSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
