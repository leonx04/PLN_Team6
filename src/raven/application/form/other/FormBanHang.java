package raven.application.form.other;

import com.itextpdf.text.*;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.FocusEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import javax.swing.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.application.model.Auth;

import raven.application.model.ChatLieuModel;
import raven.application.model.ChiTietHoaDonModel;
import raven.application.model.ChiTietSanPhamModel;
import raven.application.model.HoaDonModel;
import raven.application.model.KhachHangModel;
import raven.application.model.KichCoModel;
import raven.application.model.MauSacModel;
import raven.application.model.NhanVienModel;
import raven.application.model.SanPhamModel;
import raven.application.model.ThuongHieuModel;
import raven.application.model.VoucherModer;
import raven.application.service.BanHangService;
import raven.application.service.ChatLieuService;
import raven.application.service.ChiTietHoaDonService;
import raven.application.service.ChiTietSanPhamService;
import raven.application.service.HoaDonService;
import raven.application.service.KichCoService;
import raven.application.service.MauSacService;
import raven.application.service.SanPhamService;
import raven.application.service.ThuongHieuService;
import raven.application.service.VoucherService;
import raven.toast.Notifications;

/**
 *
 * @author Raven
 */
public class FormBanHang extends javax.swing.JPanel {

    private DefaultTableModel model = new DefaultTableModel();
    private BanHangService bhrs = new BanHangService();
    private ChiTietSanPhamService ctsprp = new ChiTietSanPhamService();
    private SanPhamService sprs = new SanPhamService();
    private KichCoService kcrs = new KichCoService();
    private MauSacService msrs = new MauSacService();
    private ThuongHieuService thrs = new ThuongHieuService();
    private ChatLieuService clrs = new ChatLieuService();
    private HoaDonService hdrs = new HoaDonService();
    private ChiTietHoaDonService cthdrs = new ChiTietHoaDonService();
    private VoucherService vcrs = new VoucherService();
    private String selectedThuongHieuID = null;
    private String selectedFilterThuongHieuItem = null;
    private String selectedSizeID = null;
    private String selectedFilterSizeItem = null;
    private String selectedMauSacID = null;
    private String selectedFilterMSItem = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public FormBanHang() {
        initComponents();
        txtTenKH.setText("Khách bán lẻ");
        initCBOHTTT();
        initCBOVoucher();
        fillTable(bhrs.getAllCTSP());
        fillTable2(bhrs.getHoaDonChoThanhToan());
        txtTenNV.setText(Auth.user.getHoTen());
        lb1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        JComboBox<String> cboTrangThai = new JComboBox<>(
                new String[]{"Tất cả", "Chờ thanh toán", "Đã thanh toán", "Đã hủy"});

    }

    // Phương thức để cập nhật tên khách hàng trên JPanel
    public void updateTenKhachHang(String tenKhachHang) {
        txtTenKH.setText(tenKhachHang); // Cập nhật tên khách hàng
    }

    private void initCBOHTTT() {
        // Định nghĩa mảng chứa các lựa chọn cho combobox
        String[] options = {"Kết hợp cả hai", "Tiền mặt", "Chuyển khoản"};

        // Khởi tạo combobox với các giá trị từ mảng options
        cboHTTT.setModel(new DefaultComboBoxModel<>(options));

    }

    private void initCBOVoucher() {
        // Định nghĩa mảng chứa các lựa chọn cho combobox
        String[] options = {"Discount10%", "Discount20%", "Discount300K", "Discount700K"};

        // Khởi tạo combobox với các giá trị từ mảng options
        cboVoucher.setModel(new DefaultComboBoxModel<>(options));
    }

    private void fillTable(List<ChiTietSanPhamModel> listCTSP) {
        model = (DefaultTableModel) tblCTSP.getModel();
        model.setRowCount(0);
        Cbo_MauSac();
        Cbo_KichCo();
        Cbo_ThuongHieu();
        for (ChiTietSanPhamModel ctsp : listCTSP) {
            model.addRow(ctsp.toData2());
        }
    }

    private void showData(int index) {
        String maHD = String.valueOf(tblHoaDon.getValueAt(index, 1)).trim();
        String tenNV = String.valueOf(tblHoaDon.getValueAt(index, 3)).trim();
        String tenKH = String.valueOf(tblHoaDon.getValueAt(index, 4)).trim();

        String tenVC = String.valueOf(tblHoaDon.getValueAt(index, 5)).trim();
        String tongTien = String.valueOf(tblHoaDon.getValueAt(index, 6)).trim();
        String HTTT = String.valueOf(tblHoaDon.getValueAt(index, 7)).trim();

        txtMaHD.setText(maHD);

        txtTenKH.setText(tenKH);
        txtTenNV.setText(tenNV);
        cboVoucher.setSelectedItem(tenVC);
        cboHTTT.setSelectedItem(HTTT);
        txtTongTien.setText(tongTien);
    }

    HoaDonModel read() {
        HoaDonModel hdm = new HoaDonModel();
        hdm.setID(txtMaHD.getText().trim());
        hdm.setTenNV(new NhanVienModel(txtTenNV.getText().trim()));
        hdm.setTenKH(new KhachHangModel(txtTenKH.getText().trim()));

        // Kiểm tra xem mục đã được chọn trong cboHTGG có tồn tại không
        if (cboVoucher.getSelectedIndex() != -1) {
            hdm.setTenVoucher(new VoucherModer(cboVoucher.getSelectedItem().toString().trim()));
        } else {
            // Nếu không có mục nào được chọn, đặt tên voucher thành null
            hdm.setTenVoucher(null);
        }

        // Kiểm tra xem có giá trị hợp lệ trong cboHTTT không
        if (cboHTTT.getSelectedIndex() != -1) {
            hdm.setHinhThucThanhToan(cboHTTT.getSelectedItem().toString().trim());
        } else {
            // Nếu không có giá trị hợp lệ, đặt hình thức thanh toán thành null
            hdm.setHinhThucThanhToan(null);
        }

        // Giá trị tổng tiền sẽ phụ thuộc vào dữ liệu từ bảng hoặc các trường khác trong
        // giao diện người dùng
        // Ở đây tôi giả sử rằng giá trị tổng tiền là 0, bạn có thể thay đổi thành giá
        // trị phù hợp
        hdm.setTongTien(BigDecimal.ZERO);

        return hdm;
    }

    private void Cbo_MauSac() {
        List<MauSacModel> listMS = msrs.getALLMauSac();
        String[] cbo = new String[listMS.size()];
        for (int i = 0; i < listMS.size(); i++) {
            cbo[i] = listMS.get(i).getTenMS();
        }
        cboMauSac.setModel(new DefaultComboBoxModel<>(cbo));

    }

    private void Cbo_KichCo() {
        List<KichCoModel> listKC = kcrs.getALLKichCo();
        String[] cbo = new String[listKC.size()];
        for (int i = 0; i < listKC.size(); i++) {
            cbo[i] = listKC.get(i).getTenSize();
        }
        cboSize.setModel(new DefaultComboBoxModel<>(cbo));
    }

    private void Cbo_ThuongHieu() {
        List<ThuongHieuModel> listTH = thrs.getALLThuongHieu();
        String[] cbo = new String[listTH.size()];
        for (int i = 0; i < listTH.size(); i++) {
            cbo[i] = listTH.get(i).getTenTH();
        }
        cboHang.setModel(new DefaultComboBoxModel<>(cbo));
    }

    private void fillTable2(List<HoaDonModel> list) {
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0); // Xóa tất cả các dòng cũ trước khi điền dữ liệu mới
        if (list != null && !list.isEmpty()) { // Kiểm tra list không rỗng
            for (int i = 0; i < list.size(); i++) {
                HoaDonModel hoaDonModel = list.get(i);
                Object[] rowData = {
                    i + 1, // Số thứ tự (STT)
                    hoaDonModel.getID(),
                    hoaDonModel.getNgayTao(),
                    hoaDonModel.getTenNV().getHoTen(),
                    hoaDonModel.getTenKH().getTen(),
                    hoaDonModel.getTenVoucher().getTenVoucher(),
                    hoaDonModel.getTongTien(),
                    hoaDonModel.getHinhThucThanhToan(),
                    hoaDonModel.getTrangThai()
                };
                model.addRow(rowData); // Thêm dòng mới vào bảng
            }
        }
        return;
    }

    private void fillToTable(List<ChiTietHoaDonModel> chiTietHoaDons) {
        model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);

        for (ChiTietHoaDonModel chiTietHoaDon : chiTietHoaDons) {
            Object[] rowData = {
                chiTietHoaDon.getMactsp().getID(),
                chiTietHoaDon.getTenSP().getTenSP(),
                chiTietHoaDon.getDonGia().getGiaBan(),
                chiTietHoaDon.getSoLuong(),
                chiTietHoaDon.getThanhTien()

            };
            model.addRow(rowData);
        }
    }

    private void cleanForm() {
        txtMaHD.setText(null);
        // txtMaKH.setText(null);
        txtTenKH.setText("Khách bán lẻ");
        txtTongTien.setText(null);
        txtThanhToan.setText(null);
        txtTienMat.setText(null);
        txtTienCK.setText(null);
        txtTienThua.setText(null);
    }

    private void refreshGioHangTable() {
        // Kiểm tra xem có hàng nào được chọn trong bảng không
        int rowIndex = tblHoaDon.getSelectedRow();
        // Kiểm tra xem có hàng nào được chọn không
        if (rowIndex >= 0) {
            // Lấy id hóa đơn từ hàng được chọn
            String idHoaDon = tblHoaDon.getValueAt(rowIndex, 1).toString();
            // Tìm kiếm chi tiết hóa đơn theo id hóa đơn
            List<ChiTietHoaDonModel> chiTietHoaDons = bhrs.searchByHoaDonID(idHoaDon);
            // Đổ dữ liệu vào bảng
            fillToTable(chiTietHoaDons);
        } else {
            // Hiển thị thông báo nếu không có hàng nào được chọn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng từ bảng hóa đơn!");
        }
    }

    // Hàm để điền dữ liệu vào bảng chi tiết hóa đơn dựa trên ID hóa đơn được chọn
    private void fillChiTietHoaDonTable(String idHoaDon) {
        List<ChiTietHoaDonModel> chiTietHoaDons = bhrs.searchByHoaDonID(idHoaDon);
        fillToTable(chiTietHoaDons);
        System.out.println("Đã chạy qua hàm fill");
    }

    // Biến để lưu trữ ID của hóa đơn được chọn
    private String selectedHoaDonID;

    private void xoaMemHD(String hoaDonID) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).equals(hoaDonID)) {
                model.removeRow(i);
                break; // Sau khi loại bỏ, thoát khỏi vòng lặp
            }
        }
    }

    void openKhachhang() {
        new FormAddKhachHang().setVisible(true);
    }

    private void TimKiemSPCT(String querry) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblCTSP.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(querry));
    }

    private BigDecimal discountVoucher(String selectedVoucher, BigDecimal tongTien) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (selectedVoucher.equals("Discount10%")) {
            discountAmount = tongTien.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
        } else if (selectedVoucher.equals("Discount20%")) {
            discountAmount = tongTien.multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100));
        } else if (selectedVoucher.equals("Discount300K")) {
            discountAmount = new BigDecimal("300000");
        } else if (selectedVoucher.equals("Discount700K")) {
            discountAmount = new BigDecimal("700000");
        }
        return tongTien.subtract(discountAmount);
    }

    private void updateHoaDonTrangThai(String hoaDonID) {
        if (hoaDonID == null || hoaDonID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng cung cấp ID hóa đơn hợp lệ.");
            return;
        }

        try {
            int updated = bhrs.updateBillStatus(hoaDonID, "Đã thanh toán");
            if (updated > 0) {
                JOptionPane.showMessageDialog(this,
                        "Hóa đơn đã được chuyển sang trạng thái 'Đã thanh toán'.");
                // Thực hiện các thao tác khác nếu cần
                // Lấy thông tin hóa đơn
                HoaDonModel hoaDon = bhrs.getHoaDonByID(hoaDonID);

                // Gọi phương thức exportToPDF với đối số HoaDonModel và JTable
                exportToPDF(hoaDon, tblGioHang);

                openPDFFile();
            } else {
                JOptionPane.showMessageDialog(this, "Không có hóa đơn nào được cập nhật.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }

    private void exportToPDF(HoaDonModel hoaDon, JTable tblGioHang) {
        Document document = new Document(PageSize.A5);

        try {
            // Đường dẫn đến tập tin PDF
            String filePath = "A:\\PDF\\hoa_don.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Mở tài liệu để bắt đầu thêm nội dung
            document.open();

            // Thiết lập font chữ
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 23, Font.BOLD);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);

            // Tiêu đề hoá đơn
            Paragraph title = new Paragraph("HOÁ ĐƠN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Định dạng tiền tệ
            DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");

            // Tạo bảng để chứa thông tin hoá đơn
            PdfPTable table = new PdfPTable(2); // 2 cột (label và value)
            table.setWidthPercentage(90); // Chiều rộng bảng là 90% của trang
            table.setSpacingBefore(20); // Khoảng cách trước bảng là 20 điểm

            // Thêm các mục thông tin hoá đơn vào bảng
            addTableRow(table, "Mã Hóa Đơn", hoaDon.getID(), normalFont, Element.ALIGN_LEFT, Element.ALIGN_RIGHT);
            addTableRow(table, "Tên Nhân Viên", hoaDon.getTenNV().getHoTen(), normalFont, Element.ALIGN_LEFT,
                    Element.ALIGN_RIGHT);
            addTableRow(table, "Tên Khách Hàng", hoaDon.getTenKH().getTen(), normalFont, Element.ALIGN_LEFT,
                    Element.ALIGN_RIGHT);
            addTableRow(table, "Tổng Tiền", decimalFormat.format(hoaDon.getTongTien()), normalFont, Element.ALIGN_LEFT,
                    Element.ALIGN_RIGHT);
            addTableRow(table, "Voucher", hoaDon.getTenVoucher().getTenVoucher(), normalFont, Element.ALIGN_LEFT,
                    Element.ALIGN_RIGHT);
            addTableRow(table, "Thanh Toán", decimalFormat.format(Double.parseDouble(txtThanhToan.getText())),
                    normalFont, Element.ALIGN_LEFT, Element.ALIGN_RIGHT);
            addTableRow(table, "Hình Thức Thanh Toán", hoaDon.getHinhThucThanhToan(), normalFont, Element.ALIGN_LEFT,
                    Element.ALIGN_RIGHT);
            addTableRow(table, "Tiền Thừa", decimalFormat.format(Double.parseDouble(txtTienThua.getText())), normalFont,
                    Element.ALIGN_LEFT, Element.ALIGN_RIGHT);
            addTableRow(table, "Ngày tạo hóa đơn", dateFormat.format(new Date()), normalFont, Element.ALIGN_LEFT,
                    Element.ALIGN_RIGHT);

            // Thêm bảng vào tài liệu PDF
            document.add(table);

            // Thêm tiêu đề cho bảng chi tiết sản phẩm
            Paragraph productTitle = new Paragraph("CHI TIẾT ĐƠN HÀNG", normalFont);
            productTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(productTitle);

            // Tạo bảng mới cho chi tiết sản phẩm
            PdfPTable productTable = new PdfPTable(4); // 4 cột (tên, giá, số lượng, thành tiền)
            productTable.setWidthPercentage(90); // Chiều rộng bảng là 90% của trang
            productTable.setSpacingBefore(10); // Khoảng cách trước bảng là 10 điểm

            // Thêm tiêu đề cột cho bảng chi tiết sản phẩm
            addProductTableHeader(productTable, normalFont);

            // Lấy dữ liệu từ JTable
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String tenSanPham = model.getValueAt(i, 1).toString();
                String giaBan = model.getValueAt(i, 2).toString();
                String soLuong = model.getValueAt(i, 3).toString();
                String thanhTien = model.getValueAt(i, 4).toString();
                addProductTableRow(productTable, tenSanPham, giaBan, soLuong, thanhTien, normalFont);
            }

            // Thêm bảng chi tiết sản phẩm vào tài liệu PDF
            document.add(productTable);

            // Cảm ơn khách hàng
            document.add(new Paragraph("\n\n")); // Khoảng cách giữa các dòng
            Paragraph thankYou = new Paragraph("--  Xin chân thành cảm ơn quý khách --", new Font(baseFont, 14));
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            // Cảm ơn khách hàng
            document.add(new Paragraph("\n")); // Khoảng cách giữa các dòng
            Paragraph lienHe = new Paragraph("Nếu có vấn đề xin vui lòng liên hệ chúng tôi: 0123456789",
                    new Font(baseFont, 11));
            lienHe.setAlignment(Element.ALIGN_CENTER);
            document.add(lienHe);

            // Đóng tài liệu PDF
            document.close();

            JOptionPane.showMessageDialog(null, "Xuất hoá đơn sang PDF thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất hoá đơn sang PDF: " + e.getMessage());
        }
    }

    private void addTableRow(PdfPTable table, String label, String value, Font font, int labelAlignment,
            int valueAlignment) {
        // Định dạng đối tượng label
        Paragraph labelParagraph = new Paragraph(label + ": ", font);
        labelParagraph.setAlignment(labelAlignment);

        // Định dạng đối tượng value
        Paragraph valueParagraph = new Paragraph(value, font);
        valueParagraph.setAlignment(valueAlignment);

        // Thêm label và value vào các ô của hàng trong bảng
        PdfPCell labelCell = new PdfPCell(labelParagraph);
        PdfPCell valueCell = new PdfPCell(valueParagraph);

        // Thiết lập canh lề cho các ô
        labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        labelCell.setBorder(Rectangle.NO_BORDER);

        valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        valueCell.setHorizontalAlignment(valueAlignment);
        valueCell.setBorder(Rectangle.NO_BORDER);

        // Thêm các ô vào hàng trong bảng
        table.addCell(labelCell);
        table.addCell(valueCell);

        // Thêm dòng line màu đen ngăn cách giữa các hàng
        table.addCell(createSeparatorCell(2));
    }

    private void addProductTableHeader(PdfPTable table, Font font) {
        String[] headers = {"Tên sản phẩm", "Giá bán", "Số lượng", "Thành tiền"};

        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, font));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }

    private void addProductTableRow(PdfPTable table, String tenSanPham, String giaBan, String soLuong, String thanhTien,
            Font font) {
        table.addCell(new Phrase(tenSanPham, font));
        // Định dạng tiền tệ
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        // Định dạng tiền cho cột giá bán
        double giaBanDouble = Double.parseDouble(giaBan);
        PdfPCell giaBanCell = new PdfPCell(new Phrase(decimalFormat.format(giaBanDouble) + " VNĐ", font));
        giaBanCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(giaBanCell);

        table.addCell(new Phrase(soLuong, font));

        // Định dạng tiền cho cột thành tiền
        double thanhTienDouble = Double.parseDouble(thanhTien);
        PdfPCell thanhTienCell = new PdfPCell(new Phrase(decimalFormat.format(thanhTienDouble) + " VNĐ", font));
        thanhTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(thanhTienCell);
    }

    private PdfPCell createSeparatorCell(int colSpan) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.TOP); // Chỉ có border trên (tạo dòng line màu đen)
        cell.setBorderColor(BaseColor.BLACK); // Màu đen cho border
        cell.setColspan(colSpan); // Số cột của ô (bằng số cột của bảng)
        return cell;
    }

    private void openPDFFile() {
        try {
            String filePath = "A:\\PDF\\hoa_don.pdf";
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy file PDF.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mở file PDF: " + e.getMessage());
        }
    }

    private boolean validateSDT() {
        // Validate txtTimSDT
        String timSDT = txtTimSDT.getText().trim();
        if (timSDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại !", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            txtTimSDT.requestFocus();
            return false;
        }
        if (!timSDT.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được chứa các chữ số!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            txtTimSDT.requestFocus();
            return false;
        }
        if (timSDT.length() > 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được vượt quá 10 ký tự!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            txtTimSDT.requestFocus();
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lb1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnClear = new javax.swing.JButton();
        btnHuyDon = new javax.swing.JButton();
        btnTaoHD = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnDeleteGH = new javax.swing.JButton();
        selectAll = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        btnOpenKH = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTimSDT = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTienMat = new javax.swing.JTextField();
        txtTienCK = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnSuccesHoaDon = new javax.swing.JButton();
        cboHTTT = new javax.swing.JComboBox<>();
        cboVoucher = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCTSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboHang = new javax.swing.JComboBox<>();

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lb1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lb1.setText("Bán hàng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb1)
                .addContainerGap(1150, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Ngày Tạo", "Nhân viên", "Khách hàng", "Voucher", "Tổng tiền", "Hình thức thanh toán", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false
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
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(8).setResizable(false);
        }

        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chờ thanh toán", "Tất cả", "Đã thanh toán", "Đã hủy" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(153, 255, 153));
        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/clean.png"))); // NOI18N
        btnClear.setToolTipText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnHuyDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyDon.setText("Hủy đơn");
        btnHuyDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDonActionPerformed(evt);
            }
        });

        btnTaoHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTaoHD.setText("Tạo hóa đơn");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTaoHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHuyDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHuyDon)
                        .addComponent(btnTaoHD))
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm chi tiết", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(1).setResizable(false);
            tblGioHang.getColumnModel().getColumn(2).setResizable(false);
            tblGioHang.getColumnModel().getColumn(3).setResizable(false);
            tblGioHang.getColumnModel().getColumn(4).setResizable(false);
        }

        btnDeleteGH.setBackground(new java.awt.Color(0, 204, 204));
        btnDeleteGH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteGH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/trash.png"))); // NOI18N
        btnDeleteGH.setToolTipText("Xóa sản phẩm ở giỏ hàng");
        btnDeleteGH.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteGHActionPerformed(evt);
            }
        });

        selectAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        selectAll.setText("Tất cả");
        selectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(selectAll)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteGH, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteGH)
                    .addComponent(selectAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tên khách hàng");

        txtTenKH.setEditable(false);
        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnOpenKH.setBackground(new java.awt.Color(0, 153, 204));
        btnOpenKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnOpenKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/customer.png"))); // NOI18N
        btnOpenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenKHActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tìm kiếm ");

        txtTimSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimSDT.setToolTipText("Nhập số điện thoại khách hàng");

        btnTimKiem.setBackground(new java.awt.Color(51, 255, 51));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/computer.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenKH)
                    .addComponent(txtTimSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiem))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOpenKH)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tên nhân viên");

        txtTenNV.setEditable(false);
        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenNV.setEnabled(false);


        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Mã hóa đơn");

        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N


        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Tổng tiền");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Voucher");

        txtTongTien.setEditable(false);
        txtTongTien.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Thanh toán");

        txtThanhToan.setEditable(false);
        txtThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        txtThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhToanActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Hình thức thanh toán");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Tiền mặt");

        txtTienMat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTienCK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Tiền chuyển khoản");

        txtTienThua.setEditable(false);
        txtTienThua.setBackground(new java.awt.Color(255, 255, 255));
        txtTienThua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Tiền thừa");

        btnSuccesHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuccesHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/succes.png"))); // NOI18N
        btnSuccesHoaDon.setText("Xác nhận thanh toán");
        btnSuccesHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuccesHoaDonActionPerformed(evt);
            }
        });

        cboHTTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHTTT.setToolTipText("");
        cboHTTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHTTTItemStateChanged(evt);
            }
        });

        cboVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboVoucher.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboVoucherItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(txtTienCK, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSuccesHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongTien)
                            .addComponent(txtMaHD)
                            .addComponent(txtThanhToan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboVoucher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTienMat, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(cboHTTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSuccesHoaDon)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã CTSP", "Tên sản phẩm", "Màu sắc", "Size", "Chất liệu", "Hãng", "Giá bán", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblCTSP);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Màu sắc");

        cboMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        cboSize.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Size");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Hãng");

        cboHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimKiemKeyReleased
        String query = txtTimKiem.getText();
        TimKiemSPCT(query);
    }// GEN-LAST:event_txtTimKiemKeyReleased

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboTrangThaiActionPerformed
        String selectedTrangThai = (String) cboTrangThai.getSelectedItem();
        List<HoaDonModel> listHD;

        if (selectedTrangThai.equals("Tất cả")) {
            listHD = bhrs.getAllHD1();
        } else if (selectedTrangThai.equals("Đã thanh toán")) {
            listHD = bhrs.getDaThanhToanHoaDon();
        } else if (selectedTrangThai.equals("Đã hủy")) {
            listHD = bhrs.getDaHuyHoaDon();
        } else if (selectedTrangThai.equals("Chờ thanh toán")) {
            listHD = bhrs.getHoaDonChoThanhToan();
        } else {
            listHD = bhrs.getAllHD1();
        }

        fillTable2(listHD);
    }// GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnOpenKHActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOpenKHActionPerformed
        this.openKhachhang();
    }// GEN-LAST:event_btnOpenKHActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboMauSacActionPerformed
        selectedFilterMSItem = (String) cboMauSac.getSelectedItem();
        selectedMauSacID = selectedFilterMSItem;
        List<MauSacModel> listMS = msrs.getIDByTenMS(selectedMauSacID);
        if (listMS.size() > 0) {
            selectedMauSacID = listMS.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = bhrs.getAllCTSPByColorID(selectedMauSacID);
            fillTable(listCTSP);
            cboMauSac.setSelectedItem(selectedFilterMSItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboMauSac.setSelectedItem(selectedFilterMSItem);
        }
    }// GEN-LAST:event_cboMauSacActionPerformed

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboSizeActionPerformed
        selectedFilterSizeItem = (String) cboSize.getSelectedItem();
        selectedSizeID = selectedFilterSizeItem;
        List<KichCoModel> listKC = kcrs.getIDByTenKC(selectedSizeID);
        if (listKC.size() > 0) {
            selectedSizeID = listKC.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = bhrs.getAllCTSPBySizeID(selectedSizeID);
            fillTable(listCTSP);
            cboSize.setSelectedItem(selectedFilterSizeItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboSize.setSelectedItem(selectedFilterSizeItem);
        }
    }// GEN-LAST:event_cboSizeActionPerformed

    private void cboHangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboHangActionPerformed
        selectedFilterThuongHieuItem = (String) cboHang.getSelectedItem();
        selectedThuongHieuID = selectedFilterThuongHieuItem;
        List<ThuongHieuModel> listTH = thrs.getIDByTenTH(selectedThuongHieuID);
        if (listTH.size() > 0) {
            selectedThuongHieuID = listTH.get(0).getID();
            List<ChiTietSanPhamModel> listCTSP = bhrs.getAllCTSPByBrandID(selectedThuongHieuID);
            fillTable(listCTSP);
            cboHang.setSelectedItem(selectedFilterThuongHieuItem);
        } else {
            selectedThuongHieuID = null;
            fillTable(ctsprp.getAllCTSP());
            cboHang.setSelectedItem(selectedFilterThuongHieuItem); // Thiết lập lại giá trị của combobox
        }
    }// GEN-LAST:event_cboHangActionPerformed

    private void txtThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtThanhToanActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_txtThanhToanActionPerformed

    private void btnSuccesHoaDonActionPerformed(java.awt.event.ActionEvent evt) {
        // Lấy tổng tiền từ txtTongTien
        BigDecimal tongTien = new BigDecimal(txtTongTien.getText());

        // Lấy giá trị của voucher từ cboVoucher
        String voucher = cboVoucher.getSelectedItem().toString();

        // Áp dụng giảm giá từ voucher
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (voucher.equals("Discount10%")) {
            discountAmount = tongTien.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
        } else if (voucher.equals("Discount20%")) {
            discountAmount = tongTien.multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100));
        } else if (voucher.equals("Discount300K")) {
            discountAmount = new BigDecimal("300000");
        } else if (voucher.equals("Discount700K")) {
            discountAmount = new BigDecimal("700000");
        }

        BigDecimal tongTienSauGiamGia = tongTien.subtract(discountAmount);

        // Hiển thị tổng tiền sau khi áp dụng giảm giá lên txtThanhToan
        txtThanhToan.setText(tongTienSauGiamGia.toString());

        // Tính số tiền thừa hoặc thiếu dựa trên hình thức thanh toán được chọn
        BigDecimal tienMat = BigDecimal.ZERO;
        BigDecimal chuyenKhoan = BigDecimal.ZERO;

        String hinhThucThanhToan = cboHTTT.getSelectedItem().toString();
        if (hinhThucThanhToan.equals("Tiền mặt")) {
            if (validateTienMat()) {
                tienMat = new BigDecimal(txtTienMat.getText());
            } else {
                return;
            }
        } else if (hinhThucThanhToan.equals("Chuyển khoản")) {
            if (validateTienCK()) {
                chuyenKhoan = new BigDecimal(txtTienCK.getText());
            } else {
                return;
            }
        } else if (hinhThucThanhToan.equals("Kết hợp cả hai")) {
            if (validateTienMat() && validateTienCK()) {
                tienMat = new BigDecimal(txtTienMat.getText());
                chuyenKhoan = new BigDecimal(txtTienCK.getText());
            } else {
                return;
            }
        }

        BigDecimal tienThua = tienMat.add(chuyenKhoan).subtract(tongTienSauGiamGia);
        txtTienThua.setText(tienThua.toString());

        // Update hình thức thanh toán cho hóa đơn
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            tblHoaDon.setValueAt(hinhThucThanhToan, selectedRow, 7);
        }

        // Cập nhật hình thức thanh toán vào cơ sở dữ liệu
        if (selectedHoaDonID != null && !selectedHoaDonID.isEmpty()) {
            boolean updated = bhrs.updateHTTTHoaDon(selectedHoaDonID, hinhThucThanhToan);
            if (!updated) {
                JOptionPane.showMessageDialog(this, "Cập nhật hình thức thanh toán không thành công!");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để thanh toán.");
            return;
        }

        // Nếu tiền thừa không nhỏ hơn 0, cập nhật trạng thái của hóa đơn
        if (tienThua.compareTo(BigDecimal.ZERO) >= 0) {
            if (selectedHoaDonID != null && !selectedHoaDonID.isEmpty()) {
                updateHoaDonTrangThai(selectedHoaDonID);
                xoaMemHD(selectedHoaDonID); // Xóa hóa đơn đã thanh toán khỏi bảng
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để thanh toán.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Số tiền nhập vào không được nhỏ hơn số tiền phải thanh toán.");
            txtTienThua.setText("0");
            return;
        }

        // Hiển thị kết quả
        txtThanhToan.setText(tongTienSauGiamGia.toString());
        txtTienThua.setText(tienThua.toString());
        fillTable2(bhrs.getHoaDonChoThanhToan());
        cleanForm();
    }

    private boolean validateTienMat() {
        String tienMatStr = txtTienMat.getText().trim();

        // Kiểm tra độ dài không vượt quá 20 ký tự
        if (tienMatStr.length() > 20) {
            JOptionPane.showMessageDialog(this, "Số tiền mặt không được vượt quá 20 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra xem chỉ chứa các ký tự số và không chứa ký tự "-"
        if (!tienMatStr.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Số tiền mặt phải là số và không được chứa ký tự đặc biệt.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Chuyển đổi thành số BigDecimal để kiểm tra số không âm
        BigDecimal tienMat = new BigDecimal(tienMatStr);
        if (tienMat.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "Số tiền mặt không được là số âm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateTienCK() {
        String tienCKStr = txtTienCK.getText().trim();

        // Kiểm tra độ dài không vượt quá 20 ký tự
        if (tienCKStr.length() > 20) {
            JOptionPane.showMessageDialog(this, "Số tiền chuyển khoản không được vượt quá 20 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra xem chỉ chứa các ký tự số và không chứa ký tự "-"
        if (!tienCKStr.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Số tiền chuyển khoản phải là số và không được chứa ký tự đặc biệt.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Chuyển đổi thành số BigDecimal để kiểm tra số không âm
        BigDecimal tienCK = new BigDecimal(tienCKStr);
        if (tienCK.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "Số tiền chuyển khoản không được là số âm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Hàm validate tiền thừa
    private void validateTienThua() {
        BigDecimal tienThua = new BigDecimal(txtTienThua.getText());
        if (tienThua.compareTo(BigDecimal.ZERO) < 0) {
            // Nếu tiền thừa nhỏ hơn 0, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Số tiền thừa không thể nhỏ hơn 0.");
            // Nếu tiền thừa nhỏ hơn 0, đặt giá trị của txtTienThua thành 0
            txtTienThua.setText("");
        }
    }

    private void cboVoucherItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cboVoucherItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String voucher = (String) cboVoucher.getSelectedItem();

            // Kiểm tra xem txtTongTien có giá trị không
            if (!txtTongTien.getText().isEmpty()) {
                // Lấy tổng tiền từ txtTongTien
                BigDecimal tongTien = new BigDecimal(txtTongTien.getText());

                // Tính toán tổng tiền sau khi áp dụng giảm giá
                BigDecimal tongTienSauGiamGia = discountVoucher(voucher, tongTien);

                // Hiển thị tổng tiền sau khi áp dụng giảm giá lên txtThanhToan
                txtThanhToan.setText(tongTienSauGiamGia.toString());
            } else {
                // Nếu txtTongTien không có giá trị, bạn có thể hiển thị một thông báo hoặc xử
                // lý theo cách khác
                // JOptionPane.showMessageDialog(this, "Vui lòng nhập tổng tiền trước khi áp
                // dụng voucher", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }// GEN-LAST:event_cboVoucherItemStateChanged

    private String idKhachHang;

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        String sdt = txtTimSDT.getText().trim();

        if (!validateSDT()) {
            return;
        }

        KhachHangModel kh = bhrs.findBySDT(sdt);
        if (kh != null) {
            txtTenKH.setText(kh.getTen());
            // Lấy ID khách hàng từ tên khách hàng đã tìm thấy
            idKhachHang = bhrs.getIDByTen(kh.getTen());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng");
        }
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void btnDeleteGHActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        int[] selectedRows = tblGioHang.getSelectedRows();

        // Kiểm tra xem checkbox selectAll đã được chọn hay không
        boolean selectAllActivated = selectAll.isSelected();

        if (selectAllActivated && selectedHoaDonID != null) {
            // Lấy danh sách chi tiết hoá đơn của hoá đơn đã chọn trước khi xóa
            List<ChiTietHoaDonModel> chiTietHoaDons = bhrs.searchByHoaDonID(selectedHoaDonID);

            // Xóa toàn bộ hoá đơn chi tiết
            int result = bhrs.xoaToanBoHoaDonChiTiet(selectedHoaDonID);
            if (result <= 0) {
                JOptionPane.showMessageDialog(this, "Xóa toàn bộ hoá đơn chi tiết thất bại!");
                return;
            }

            // Cập nhật số lượng tồn của từng sản phẩm chi tiết sau khi xóa
            for (ChiTietHoaDonModel chiTietHoaDon : chiTietHoaDons) {
                String maSanPhamChiTiet = chiTietHoaDon.getMactsp().getID();
                int soLuong = chiTietHoaDon.getSoLuong();

                // Lấy số lượng tồn hiện tại của sản phẩm chi tiết
                int soLuongTonHienTai = bhrs.laySoLuongTonByID(maSanPhamChiTiet);

                // Cập nhật số lượng tồn mới
                bhrs.updateSoLuongTon(maSanPhamChiTiet, soLuongTonHienTai + soLuong);
            }

            // Cập nhật tổng tiền của hóa đơn thành 0
            boolean update = bhrs.updateBillWhileDeleteALL(selectedHoaDonID);
            fillChiTietHoaDonTable(selectedHoaDonID);
            cleanForm();
        } else {
            // Xóa từng sản phẩm chi tiết được chọn
            for (int selectedRow : selectedRows) {
                String maSanPhamChiTiet = model.getValueAt(selectedRow, 0).toString();
                int soLuong = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());

                // Cập nhật số lượng tồn của sản phẩm
                bhrs.updateSoLuongTon(maSanPhamChiTiet, bhrs.laySoLuongTonByID(maSanPhamChiTiet) + soLuong);

                // Xóa dữ liệu từ cơ sở dữ liệu
                int result = bhrs.xoaHoaDonChiTiet(maSanPhamChiTiet, selectedHoaDonID);
                if (result <= 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    return;
                }

                // Xóa hàng từ bảng sau khi xóa dữ liệu từ cơ sở dữ liệu
                model.removeRow(selectedRow);
            }
        }

        // Cập nhật lại bảng hiển thị
        fillTable2(bhrs.getHoaDonChoThanhToan()); // Cập nhật lại bảng hoá đơn chính
        fillTable(bhrs.getAllCTSP()); // Cập nhật lại bảng sản phẩm chi tiết

        // Thông báo xóa thành công
        JOptionPane.showMessageDialog(this, "Xóa thành công!");

        // Tắt sự kiện "Select All"
        selectAll.setSelected(false);
    }

    private void selectAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectAllActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model.setValueAt(selectAll.isSelected(), i, 5); // Đặt trạng thái của cột "Chọn" thành giá trị
            // của checkbox selectAll cho tất cả các hàng
        }
    }// GEN-LAST:event_selectAllActionPerformed

    private void tblGioHangMouseClicked(MouseEvent evt) {
        int column = tblGioHang.columnAtPoint(evt.getPoint()); // Lấy cột được nhấp vào

        // Kiểm tra xem sự kiện selectAll đã được kích hoạt hay không
        boolean selectAllActivated = selectAll.isSelected();

        // Nếu cột được nhấp vào là cột "Chọn" và selectAll được kích hoạt
        if (column == 4 && selectAllActivated) {
            int selectedRow = tblGioHang.rowAtPoint(evt.getPoint()); // Lấy hàng được nhấp vào
            if (selectedRow >= 0) {
                // Lấy giá trị của cột "Chọn" khi một dòng được chọn
                Object value = tblGioHang.getValueAt(selectedRow, column);
                if (value instanceof Boolean) {
                    boolean selected = (boolean) value;
                    // Đảo ngược trạng thái của cột "Chọn"
                    tblGioHang.setValueAt(!selected, selectedRow, column);
                }
            }
        }
    }

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {
        int rowIndex = tblHoaDon.getSelectedRow();
        if (rowIndex >= 0) {
            // Hiển thị dữ liệu của hoá đơn được chọn
            this.showData(rowIndex);
            selectedHoaDonID = tblHoaDon.getValueAt(rowIndex, 1).toString(); // Lấy ID hóa đơn được chọn từ cột thứ hai

             // Kiểm tra trạng thái của hoá đơn
             String trangThai = tblHoaDon.getValueAt(rowIndex, 8).toString().trim();
             if (trangThai.equals("Đã thanh toán") || trangThai.equals("Đã hủy")) {
             // Nếu trạng thái là "Đã thanh toán" hoặc "Đã hủy", tắt đi các nút
             btnHuyDon.setEnabled(false);
             btnSuccesHoaDon.setEnabled(false);
             btnDeleteGH.setEnabled(false);
             } else {
             // Nếu trạng thái không phải là "Đã thanh toán" hoặc "Đã hủy", bật lại các nút
             btnHuyDon.setEnabled(true);
             btnSuccesHoaDon.setEnabled(true);
             btnDeleteGH.setEnabled(true);
             }
            System.out.println("BẠN ĐÃ NHẤN:  " + selectedHoaDonID);

            // Cập nhật lại bảng tblGioHang với dữ liệu của hóa đơn đã chọn
            fillChiTietHoaDonTable(selectedHoaDonID);

            // Lấy giá trị của cboVoucher được chọn
            String selectedVoucher = null;
            if (cboVoucher.getSelectedItem() != null) {
                selectedVoucher = cboVoucher.getSelectedItem().toString();
            }

            // Kiểm tra giá trị của cboVoucher và tính toán lại giá trị của txtThanhToan
            if (selectedVoucher != null) {
                BigDecimal tongTien = new BigDecimal(txtTongTien.getText());
                BigDecimal thanhToan = discountVoucher(selectedVoucher, tongTien);
                txtThanhToan.setText(thanhToan.toString());
            }
        } else {
            // Nếu không có hoá đơn nào được chọn, làm sạch form và tắt các nút
            cleanForm();
            btnHuyDon.setEnabled(true);
            btnSuccesHoaDon.setEnabled(true);
            btnDeleteGH.setEnabled(true);
        }
    }

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTaoHDActionPerformed
        if (Auth.isLogin()) {
            NhanVienModel nhanVien = Auth.user;
            String idNhanVien = nhanVien.getId();

            if (idKhachHang == null) {
                // Nếu không tìm thấy ID từ tên khách hàng, sử dụng ID mặc định là "KH00"
                idKhachHang = "KH00";
            }

            // Gọi phương thức tạo hóa đơn với ID nhân viên và ID khách hàng
            int result = bhrs.taoHoaDon(idNhanVien, idKhachHang);

            // Xử lý kết quả
            if (result == 1) {
                fillTable2(bhrs.getAllHD1());
                fillTable2(bhrs.getHoaDonChoThanhToan());
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể tạo hóa đơn");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập");
        }

    }// GEN-LAST:event_btnTaoHDActionPerformed

    private void btnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyDonActionPerformed
        // Lấy chỉ mục của hóa đơn được chọn trên bảng
        int index = tblHoaDon.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để hủy");
            return;
        }

        // Lấy ID của hóa đơn được chọn
        selectedHoaDonID = tblHoaDon.getValueAt(index, 1).toString();

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy hóa đơn không", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Thực hiện hủy hóa đơn
            if (bhrs.huyHDByID("Đã hủy", selectedHoaDonID)) {
                // Nếu hủy thành công, thông báo và cập nhật lại bảng hóa đơn
                JOptionPane.showMessageDialog(this, "Hủy thành công");

                // Lấy danh sách chi tiết hoá đơn của hoá đơn bị hủy
                List<ChiTietHoaDonModel> chiTietHoaDons = bhrs.searchByHoaDonID(selectedHoaDonID);

                // Cập nhật số lượng tồn của từng sản phẩm chi tiết và xoá các hoá đơn chi tiết
                for (ChiTietHoaDonModel chiTietHoaDon : chiTietHoaDons) {
                    String maSanPhamChiTiet = chiTietHoaDon.getMactsp().getID();
                    int soLuong = chiTietHoaDon.getSoLuong();

                    // Cập nhật số lượng tồn mới
                    bhrs.updateSoLuongTon(maSanPhamChiTiet, bhrs.laySoLuongTonByID(maSanPhamChiTiet) + soLuong);

                    // Xoá hoá đơn chi tiết
                    bhrs.xoaHoaDonChiTiet(maSanPhamChiTiet, selectedHoaDonID);
                    boolean update = bhrs.updateBillWhileDeleteALL(selectedHoaDonID);
                }
                fillTable2(bhrs.getHoaDonChoThanhToan());
                fillToTable(chiTietHoaDons);
                cleanForm();

            } else {
                JOptionPane.showMessageDialog(this, "Hủy thất bại");
            }
        }
    }// GEN-LAST:event_btnHuyDonActionPerformed

    private void cboHTTTItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cboHTTTItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedOption = (String) cboHTTT.getSelectedItem();

            if (selectedOption.equals("Tiền mặt")) {
                txtTienCK.setText(null);
                txtTienCK.setEnabled(false); // Tắt ô txtTienCK
                txtTienMat.setEnabled(true);
            } else if (selectedOption.equals("Chuyển khoản")) {
                txtTienMat.setText(null);
                txtTienMat.setEnabled(false); // Tắt ô txtTienMat
                txtTienCK.setEnabled(true);
            } else if (selectedOption.equals("Kết hợp cả hai")) {
                txtTienMat.setEnabled(true);
                txtTienCK.setEnabled(true);
            }
        }
    }// GEN-LAST:event_cboHTTTItemStateChanged

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnClearActionPerformed
        int rowIndex = tblHoaDon.getSelectedRow();
        if (rowIndex >= 0) {
            tblHoaDon.removeRowSelectionInterval(rowIndex, rowIndex);
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            model.setRowCount(0);
            selectedHoaDonID = null;
            fillTable(bhrs.getAllCTSP());
            fillTable2(bhrs.getHoaDonChoThanhToan());
        } else {
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            model.setRowCount(0);
        }
        fillTable(bhrs.getAllCTSP());
        cleanForm();
    }// GEN-LAST:event_btnClearActionPerformed

    private void tblCTSPMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tblCTSP.getSelectedRow();
        if (selectedHoaDonID == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hoá đơn trước khi thêm sản phẩm vào giỏ hàng!");
            return;
        }

        if (selectedRow == -1) {
            return; // Không có hàng nào được chọn
        }

        String productID = tblCTSP.getValueAt(selectedRow, 0).toString();
        BigDecimal unitPrice = bhrs.getGiaBanByMaCTSP(productID);

        if (unitPrice == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy giá bán cho sản phẩm này!");
            return;
        }

        JTextField txtSoLuong = new JTextField();
        Object[] message = {"Nhập số lượng:", txtSoLuong};

        int option = JOptionPane.showConfirmDialog(this, message, "Nhập số lượng", JOptionPane.OK_CANCEL_OPTION);
        txtSoLuong.requestFocus();

        if (option == JOptionPane.OK_OPTION) {
            String soLuongStr = txtSoLuong.getText();
            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
                return;
            }

            try {
                int quantity = Integer.parseInt(soLuongStr);
                int currentQuantity = bhrs.laySoLuongTonByID(productID);

                if (quantity > currentQuantity) {
                    JOptionPane.showMessageDialog(this, "Số lượng nhập vào vượt quá số lượng tồn của sản phẩm!");
                    return;
                }

                ChiTietHoaDonModel existingCTHD = bhrs.kiemTraTrungSanPhamChiTiet(productID, selectedHoaDonID);

                if (existingCTHD != null) {
                    int newQuantity = existingCTHD.getSoLuong() + quantity;
                    BigDecimal newTotal = unitPrice.multiply(BigDecimal.valueOf(newQuantity));
                    int updatedRows = bhrs.updateSoLuongVaThanhTienHoaDonChiTiet(existingCTHD.getID(), newQuantity,
                            newTotal);

                    if (updatedRows > 0) {
                        int remainingQuantity = currentQuantity - quantity;
                        bhrs.updateSoLuongTon(productID, remainingQuantity);
                        refreshGioHangTable();
                        boolean updated = bhrs.capNhatTongTienHoaDon(selectedHoaDonID);
                        fillTable2(bhrs.getHoaDonChoThanhToan());
                        fillTable(bhrs.getAllCTSP());
                        if (updated) {
                            // Cập nhật lại bảng giỏ hàng
                            fillChiTietHoaDonTable(selectedHoaDonID);
                            JOptionPane.showMessageDialog(this, "Cập nhật tổng tiền hóa đơn thành công!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Cập nhật tổng tiền hóa đơn thất bại!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật số lượng và thành tiền thất bại!");
                    }
                } else {
                    ChiTietHoaDonModel chiTietHoaDon = new ChiTietHoaDonModel();
                    chiTietHoaDon.setMactsp(new ChiTietSanPhamModel(productID));
                    chiTietHoaDon.setSoLuong(quantity);
                    chiTietHoaDon.setThanhTien(unitPrice.multiply(BigDecimal.valueOf(quantity)));

                    int result = bhrs.themSPGioHang(chiTietHoaDon, selectedHoaDonID);
                    if (result > 0) {
                        int remainingQuantity = currentQuantity - quantity;
                        bhrs.updateSoLuongTon(productID, remainingQuantity);

                        JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ hàng thành công!");
                        boolean updated = bhrs.capNhatTongTienHoaDon(selectedHoaDonID);
                        refreshGioHangTable();
                        fillTable2(bhrs.getHoaDonChoThanhToan());
                        fillTable(bhrs.getAllCTSP());
                        if (updated) {
                            // Cập nhật lại bảng giỏ hàng
                            fillChiTietHoaDonTable(selectedHoaDonID);
                            JOptionPane.showMessageDialog(this, "Cập nhật tổng tiền hóa đơn thành công!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Cập nhật tổng tiền hóa đơn thất bại!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ hàng thất bại!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDeleteGH;
    private javax.swing.JButton btnHuyDon;
    private javax.swing.JButton btnOpenKH;
    private javax.swing.JButton btnSuccesHoaDon;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JComboBox<String> cboHang;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lb1;
    private javax.swing.JCheckBox selectAll;
    private javax.swing.JTable tblCTSP;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JTextField txtTienCK;
    private javax.swing.JTextField txtTienMat;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimSDT;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
