/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import raven.application.service.ThongKeService;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import raven.application.Application;

/**
 *
 * @author dungn
 */
public class FormThongKe extends javax.swing.JPanel {

    private ThongKeService thongKeService;
    private NumberFormat numberFormat;

    public FormThongKe() {
        initComponents();
        numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        init();
        loadBieuDo();
    }

    void init() {
        thongKeService = new ThongKeService();

        // Hiển thị tổng tiền hóa đơn hôm nay
        BigDecimal doanhThuHomNay = thongKeService.getTongTienHoaDonHomnay();
        lblDoanhThuHomNay.setText(doanhThuHomNay != null ? numberFormat.format(doanhThuHomNay) : "0");

        // Hiển thị tổng tiền hóa đơn 7 ngày gần đây
        BigDecimal doanhThu7Ngay = thongKeService.getTongTienHoaDon7NgayGanDay();
        lblDoanhThu7Ngay.setText(doanhThu7Ngay != null ? numberFormat.format(doanhThu7Ngay) : "0");

        // Hiển thị tổng tiền hóa đơn trong tháng
        BigDecimal doanhThuTheoThang = thongKeService.getTongTienHoaDonThangNay();
        lblDoanhThuTheoThang.setText(doanhThuTheoThang != null ? numberFormat.format(doanhThuTheoThang) : "0");

        // Hiển thị tổng tiền hóa đơn trong năm
        BigDecimal doanhThuTheoNam = thongKeService.getTongTienHoaDonNamNay();
        lblDoanhThuTheoNam.setText(doanhThuTheoNam != null ? numberFormat.format(doanhThuTheoNam) : "0");

        // Hiển thị tổng số lượng sản phẩm đã bán
        int soLuongDaBan = thongKeService.getTongSoSanPhamDaBan();
        lblSoLuongDaBan.setText(String.valueOf(soLuongDaBan));
    }

    private void loadBieuDo() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Map<Date, Integer> soHoaDonTungNgay = thongKeService.getSoHoaDonTungNgay();

        // Tạo dataset cho biểu đồ
        CategoryDataset dataset = createDataset(soHoaDonTungNgay);

        // Tạo biểu đồ
        JFreeChart chart = createChart(dataset);

        // Hiển thị biểu đồ trong panelBieuDo
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        panelBieuDo.removeAll();
        panelBieuDo.setLayout(new BorderLayout());
        panelBieuDo.add(chartPanel, BorderLayout.CENTER);
        panelBieuDo.revalidate();
        panelBieuDo.repaint();
    }

    private CategoryDataset createDataset(Map<Date, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Date, Integer> entry : data.entrySet()) {
            Date ngay = entry.getKey();
            int soHoaDon = entry.getValue();
            dataset.addValue(soHoaDon, "Số hóa đơn", new SimpleDateFormat("dd/MM/yyyy").format(ngay));
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ số hóa đơn đã thanh toán theo ngày",
                "Ngày", "Số hóa đơn",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    private void loadBieuDoSoHoaDonTheoNgay() {
        Map<Date, Integer> soHoaDonTheoNgay = thongKeService.getSoHoaDonTheoNgay();
        CategoryDataset dataset = createDatasetSoHoaDonTheoNgay(soHoaDonTheoNgay);
        JFreeChart chart = createChartSoHoaDonTheoNgay(dataset);
        displayChart(chart);
    }

    private void loadBieuDoSoHoaDonTheoThang() {
        Map<String, Integer> soHoaDonTheoThang = thongKeService.getSoHoaDonTheoThang();
        CategoryDataset dataset = createDatasetSoHoaDonTheoThang(soHoaDonTheoThang);
        JFreeChart chart = createChartSoHoaDonTheoThang(dataset);
        displayChart(chart);
    }

    private void loadBieuDoSoHoaDonTheoNam() {
        Map<Integer, Integer> soHoaDonTheoNam = thongKeService.getSoHoaDonTheoNam();
        CategoryDataset dataset = createDatasetSoHoaDonTheoNam(soHoaDonTheoNam);
        JFreeChart chart = createChartSoHoaDonTheoNam(dataset);
        displayChart(chart);
    }

    private void loadBieuDoTongTienHoaDonTheoNgay() {
        Date ngayBD = chonNgayBD.getDate();
        Date ngayKT = chonNgayKT.getDate();
        Map<Date, BigDecimal> tongTienTheoNgay = thongKeService.getTongTienHoaDonTheoNgay(ngayBD, ngayKT);
        CategoryDataset dataset = createDatasetTongTienTheoNgay(tongTienTheoNgay);
        JFreeChart chart = createChartTongTienTheoNgay(dataset);
        displayChart(chart);
    }

    private void loadBieuDoTongTienHoaDonTheoThang() {
        Date ngayBD = chonNgayBD.getDate();
        Date ngayKT = chonNgayKT.getDate();
        Map<String, BigDecimal> tongTienTheoThang = thongKeService.getTongTienHoaDonTheoThang(ngayBD, ngayKT);
        CategoryDataset dataset = createDatasetTongTienTheoThang(tongTienTheoThang);
        JFreeChart chart = createChartTongTienTheoThang(dataset);
        displayChart(chart);
    }

    private void loadBieuDoTongTienHoaDonTheoNam() {
        Date ngayBD = chonNgayBD.getDate();
        Date ngayKT = chonNgayKT.getDate();
        Map<Integer, BigDecimal> tongTienTheoNam = thongKeService.getTongTienHoaDonTheoNam(ngayBD, ngayKT);
        CategoryDataset dataset = createDatasetTongTienTheoNam(tongTienTheoNam);
        JFreeChart chart = createChartTongTienTheoNam(dataset);
        displayChart(chart);
    }

    private void displayChart(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        panelBieuDo.removeAll();
        panelBieuDo.setLayout(new BorderLayout());
        panelBieuDo.add(chartPanel, BorderLayout.CENTER);
        panelBieuDo.revalidate();
        panelBieuDo.repaint();
    }

    private CategoryDataset createDatasetSoHoaDonTheoNgay(Map<Date, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Date, Integer> entry : data.entrySet()) {
            Date ngay = entry.getKey();
            int soHoaDon = entry.getValue();
            dataset.addValue(soHoaDon, "Số hóa đơn", new SimpleDateFormat("dd/MM/yyyy").format(ngay));
        }
        return dataset;
    }

    private JFreeChart createChartSoHoaDonTheoNgay(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ số hóa đơn theo ngày",
                "Ngày", "Số hóa đơn",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    private CategoryDataset createDatasetSoHoaDonTheoThang(Map<String, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            String thang = entry.getKey();
            int soHoaDon = entry.getValue();
            dataset.addValue(soHoaDon, "Số hóa đơn", thang);
        }
        return dataset;
    }

    private JFreeChart createChartSoHoaDonTheoThang(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ số hóa đơn theo tháng",
                "Tháng", "Số hóa đơn",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    private CategoryDataset createDatasetSoHoaDonTheoNam(Map<Integer, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            int nam = entry.getKey();
            int soHoaDon = entry.getValue();
            dataset.addValue(soHoaDon, "Số hóa đơn", String.valueOf(nam));
        }
        return dataset;
    }

    private JFreeChart createChartSoHoaDonTheoNam(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ số hóa đơn theo năm",
                "Năm", "Số hóa đơn",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    private CategoryDataset createDatasetTongTienTheoNgay(Map<Date, BigDecimal> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Date, BigDecimal> entry : data.entrySet()) {
            Date ngay = entry.getKey();
            BigDecimal tongTien = entry.getValue();
            dataset.addValue(tongTien.doubleValue(), "Tổng tiền", new SimpleDateFormat("dd/MM/yyyy").format(ngay));
        }
        return dataset;
    }

    private JFreeChart createChartTongTienTheoNgay(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ tổng tiền hóa đơn theo ngày",
                "Ngày", "Tổng tiền (VND)",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

        return chart;
    }

    private CategoryDataset createDatasetTongTienTheoThang(Map<String, BigDecimal> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, BigDecimal> entry : data.entrySet()) {
            String thang = entry.getKey();
            BigDecimal tongTien = entry.getValue();
            dataset.addValue(tongTien.doubleValue(), "Tổng tiền", thang);
        }
        return dataset;
    }

    private JFreeChart createChartTongTienTheoThang(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ tổng tiền hóa đơn theo tháng",
                "Tháng", "Tổng tiền (VND)",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

        return chart;
    }

    private CategoryDataset createDatasetTongTienTheoNam(Map<Integer, BigDecimal> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, BigDecimal> entry : data.entrySet()) {
            int nam = entry.getKey();
            BigDecimal tongTien = entry.getValue();
            dataset.addValue(tongTien.doubleValue(), "Tổng tiền", String.valueOf(nam));
        }
        return dataset;
    }

    private JFreeChart createChartTongTienTheoNam(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ tổng tiền hóa đơn theo năm",
                "Năm", "Tổng tiền (VND)",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

        return chart;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lb = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblDoanhThuHomNay = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblDoanhThu7Ngay = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblDoanhThuTheoThang = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblDoanhThuTheoNam = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblSoLuongDaBan = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        panelBieuDo = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        chonNgayBD = new com.toedter.calendar.JDateChooser();
        chonNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        cboLuaChon = new javax.swing.JComboBox<>();

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lb.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lb.setText("Thống kê");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addContainerGap(1176, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Doanh thu hôm nay");

        lblDoanhThuHomNay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDoanhThuHomNay.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblDoanhThuHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblDoanhThuHomNay)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setForeground(new java.awt.Color(124, 244, 154));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Doanh thu theo 7 ngày gần đây");

        lblDoanhThu7Ngay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDoanhThu7Ngay.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblDoanhThu7Ngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblDoanhThu7Ngay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setForeground(new java.awt.Color(124, 180, 144));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Doanh thu theo tháng");

        lblDoanhThuTheoThang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDoanhThuTheoThang.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addComponent(lblDoanhThuTheoThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblDoanhThuTheoThang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Doanh thu theo năm");

        lblDoanhThuTheoNam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDoanhThuTheoNam.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addComponent(lblDoanhThuTheoNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblDoanhThuTheoNam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Số lượng sản phẩm đã bán");

        lblSoLuongDaBan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoLuongDaBan.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSoLuongDaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(lblSoLuongDaBan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panelBieuDo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelBieuDoLayout = new javax.swing.GroupLayout(panelBieuDo);
        panelBieuDo.setLayout(panelBieuDoLayout);
        panelBieuDoLayout.setHorizontalGroup(
            panelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1249, Short.MAX_VALUE)
        );
        panelBieuDoLayout.setVerticalGroup(
            panelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc theo thời gian", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Từ thời gian:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Đến thời gian");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(chonNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chonNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(chonNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chonNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cboLuaChon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLuaChon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số hóa đơn theo ngày", "Số hóa đơn theo tháng", "Số hóa đơn theo năm", "Tổng tiền hóa đơn theo từng ngày", "Tổng tiền hóa đơn theo từng  tháng", "Tổng tiền hóa đơn theo từng năm" }));
        cboLuaChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLuaChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboLuaChon, 0, 302, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(cboLuaChon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboLuaChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLuaChonActionPerformed
        int index = cboLuaChon.getSelectedIndex();
        switch (index) {
            case 0: // Số hóa đơn theo ngày
                loadBieuDoSoHoaDonTheoNgay();
                break;
            case 1: // Số hóa đơn theo tháng
                loadBieuDoSoHoaDonTheoThang();
                break;
            case 2: // Số hóa đơn theo năm
                loadBieuDoSoHoaDonTheoNam();
                break;
            case 3: // Tổng tiền hóa đơn theo từng ngày
                loadBieuDoTongTienHoaDonTheoNgay();
                break;
            case 4: // Tổng tiền hóa đơn theo từng tháng
                loadBieuDoTongTienHoaDonTheoThang();
                break;
            case 5: // Tổng tiền hóa đơn theo từng năm
                loadBieuDoTongTienHoaDonTheoNam();
                break;
        }
    }//GEN-LAST:event_cboLuaChonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Application.showForm(new FormThongKe());
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboLuaChon;
    private com.toedter.calendar.JDateChooser chonNgayBD;
    private com.toedter.calendar.JDateChooser chonNgayKT;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lblDoanhThu7Ngay;
    private javax.swing.JLabel lblDoanhThuHomNay;
    private javax.swing.JLabel lblDoanhThuTheoNam;
    private javax.swing.JLabel lblDoanhThuTheoThang;
    private javax.swing.JLabel lblSoLuongDaBan;
    private javax.swing.JPanel panelBieuDo;
    // End of variables declaration//GEN-END:variables
}
