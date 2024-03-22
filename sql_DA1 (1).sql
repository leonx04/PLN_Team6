﻿--CREATE DATABASE DUAN1_SD18406
--USE DUAN1_SD18406
--GO

CREATE TABLE SANPHAM(
	ID VARCHAR(20) PRIMARY KEY,
	TenSanPham NVARCHAR(100) NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
 -- DROP TABLE SANPHAM
GO
CREATE TABLE MAUSAC(
	ID VARCHAR(20) PRIMARY KEY,
	TenMau NVARCHAR(100) NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE MAUSAC
GO
CREATE TABLE SIZE(
	ID VARCHAR(20) PRIMARY KEY,
	Ten NVARCHAR(100) NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE SIZE

GO
CREATE TABLE CHATLIEU(
	ID VARCHAR(20) PRIMARY KEY,
	Ten NVARCHAR(100) NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE CHATLIEU

GO
CREATE TABLE THUONGHIEU(
	ID VARCHAR(20) PRIMARY KEY,
	Ten NVARCHAR(100) NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE THUONGHIEU

GO
CREATE TABLE SANPHAMCHITIET(
	ID VARCHAR(20) PRIMARY KEY,
	ID_SanPham VARCHAR(20) REFERENCES SANPHAM(ID),
	ID_MauSac VARCHAR(20) REFERENCES MAUSAC(ID),
	ID_Size VARCHAR(20) REFERENCES SIZE(ID),
	ID_ChatLieu VARCHAR(20) REFERENCES CHATLIEU(ID),
	ID_ThuongHieu VARCHAR(20) REFERENCES THUONGHIEU(ID),
	GiaBan DECIMAL(20,0) NOT NULL,
	SoLuongTon INT NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
-- DROP TABLE SANPHAMCHITIET
GO
CREATE TABLE NHANVIEN(
	ID VARCHAR(20) PRIMARY KEY,
	HoTen NVARCHAR(50) NOT NULL,
	DiaChi NVARCHAR(100) NOT NULL,
	SoDienThoai VARCHAR(15) NOT NULL,
	Email VARCHAR(30) NOT NULL,
	NamSinh INT NOT NULL,
	GioiTinh NVARCHAR(10) NOT NULL,
	ChucVu NVARCHAR(20) NOT NULL,
	MatKhau VARCHAR(50) NOT NULL,
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE NHANVIEN
GO
CREATE TABLE KHACHHANG(
	ID VARCHAR(20) PRIMARY KEY,
	HoTen NVARCHAR(50) NOT NULL,
	DiaChi NVARCHAR(100) NOT NULL,
	SoDienThoai VARCHAR(15) NOT NULL,
	Email VARCHAR(30) NOT NULL,
	GioiTinh NVARCHAR(10) NOT NULL,
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
-- DROP TABLE KHACHHANG
GO
CREATE TABLE VOUCHER(
	ID VARCHAR(20) PRIMARY KEY,
	TenVoucher NVARCHAR(50) NOT NULL,
	SoLuong INT NOT NULL,
	LoaiVoucher NVARCHAR(50) NOT NULL,
	MucGiamGia DECIMAL(20,3) NOT NULL,
	NgayBatDau DATETIME NOT NULL,
	NgayKetThuc DATETIME NOT NULL,
	MoTa NVARCHAR(255),
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE VOUCHER
GO
CREATE TABLE HOADON(
	ID VARCHAR(20) PRIMARY KEY,
	ID_NhanVien VARCHAR(20) REFERENCES NHANVIEN(ID),
	ID_KhachHang VARCHAR(20) REFERENCES KHACHHANG(ID),
	ID_Voucher VARCHAR(20) REFERENCES VOUCHER(ID),
	HinhThucThanhToan NVARCHAR(50),
	TongTien DECIMAL(20,0) NOT NULL,
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
-- DROP TABLE HOADON
GO
CREATE TABLE HOADONCHITIET(
	ID VARCHAR(20) PRIMARY KEY,
	ID_HoaDon VARCHAR(20) REFERENCES HOADON(ID),
	ID_SanPhamChiTiet VARCHAR(20) REFERENCES SANPHAMCHITIET(ID),
	SoLuong INT NOT NULL,
	DonGia DECIMAL(20,0) NOT NULL,
	ThanhTien DECIMAL(20,0) NOT NULL,
	NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TrangThai NVARCHAR(100) NOT NULL
);
--DROP TABLE HOADONCHITIET
GO
-------------
-------------
INSERT INTO SANPHAM(ID, TenSanPham, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('SP01', N'ICONDENIM Herringbone Collar', N'Họa tiết in logo nổi tinh tế', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SP02', N'ICONDENIM Heritage Line', N'Họa tiết in logo nổi đơn giản nhỏ gọn, tạo điểm nhấn tinh tế', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SP03', N'ICONDENIM Classic Urban', N'Họa tiết in nổi nhỏ nhắn tinh tế', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SP04', N'ICONDENIM Basic With Logo', N'Thiết kế áo đơn giản, không họa tiết', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SP05', N'ICONDENIM Border Raglan', N'Ứng dụng kiểu dáng Raglan theo xu hướng hiện tại', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết hàng')

SELECT * FROM SANPHAM
--
GO
INSERT INTO MAUSAC(ID, TenMau, NgayTao, NgaySua, TrangThai)
VALUES ('MS001', N'Trắng', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn màu'),
       ('MS002', N'Đen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn màu'),
	   ('MS003', N'Xanh', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết màu'),
	   ('MS004', N'Be', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn màu')

SELECT * FROM MAUSAC
--
GO
INSERT INTO SIZE(ID, Ten, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('S001', N'Size S', N'Cân nặng 53-60kg; Ngang vai: 44cm; Rộng ngực: 100cm; Dài áo: 69cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn size'),
       ('S002', N'Size M', N'Cân nặng 60-68kg; Ngang vai: 45cm; Rộng ngực: 104cm; Dài áo: 70cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn size'),
       ('S003', N'Size L', N'Cân nặng 68-78kg; Ngang vai: 46cm; Rộng ngực: 108cm; Dài áo: 71cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn size'),
       ('S004', N'Size XL', N'Cân nặng 78-88kg; Ngang vai: 48cm; Rộng ngực: 112cm; Dài áo: 72cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết size')

SELECT * FROM SIZE
--
GO
INSERT INTO CHATLIEU(ID, Ten, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('CL001', N'Chất vải CVC', N'Chất vải CVC co giãn thoải mái, bề mặt vải thông thoáng', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn'),
       ('CL002', N'Chất liệu Cotton', N'Chất liệu Cotton mềm mại, co giãn, thông thoáng tối đa.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn'),
       ('CL003', N'Chất vải cá sấu', N'Chất vải cá sấu co giãn 2 chiều thoải mái, bề mặt vải thông thoáng.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn'),
       ('CL004', N'Chất vải nhung', N'Chất vải nhung gân mềm mịn, hỗ trợ giữ nhiệt nhẹ.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết')

SELECT * FROM CHATLIEU
--
GO
INSERT INTO THUONGHIEU(ID, Ten, NgayTao, NgaySua, TrangThai)
VALUES ('TH001', N'LACOSTE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hoạt động'),
       ('TH002', N'GUCCI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hoạt động'),
       ('TH003', N'HUGO BOSS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Không hoạt động'),
       ('TH004', N'RALPH LAUREN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Không hoạt động')

SELECT * FROM THUONGHIEU
--
GO
INSERT INTO SANPHAMCHITIET(ID, ID_SanPham, ID_MauSac, ID_Size, ID_ChatLieu, ID_ThuongHieu, GiaBan, SoLuongTon, NgayTao, NgaySua, TrangThai)
VALUES ('SPCT01', 'SP01', 'MS001', 'S004',  'CL004', 'TH003', 1990000, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết hàng'),
       ('SPCT02', 'SP01', 'MS002', 'S003',  'CL001', 'TH001', 1990000, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SPCT03', 'SP01', 'MS001', 'S002',  'CL002', 'TH002', 1890000, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SPCT04', 'SP01', 'MS004', 'S001',  'CL003', 'TH001', 2090000, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SPCT05', 'SP01', 'MS001', 'S002',  'CL004', 'TH002', 2090000, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết hàng'),
       ('SPCT06', 'SP01', 'MS003', 'S003',  'CL001', 'TH004', 1790000, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết hàng'),
       ('SPCT07', 'SP02', 'MS004', 'S002',  'CL002', 'TH002', 1790000, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SPCT08', 'SP03', 'MS001', 'S003',  'CL003', 'TH001', 1790000, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng'),
       ('SPCT09', 'SP05', 'MS002', 'S002',  'CL001', 'TH003', 1690000, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hết hàng'),
       ('SPCT10', 'SP04', 'MS004', 'S001',  'CL002', 'TH001', 1690000, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Còn hàng')
SELECT * FROM SANPHAMCHITIET
--
GO
INSERT INTO NHANVIEN(ID, HoTen, DiaChi, SoDienThoai, Email, NamSinh, GioiTinh, ChucVu, MatKhau, NgayTao, NgaySua, TrangThai)
VALUES ('NV01', N'Cấn Thị Mỹ Linh', N'Hà Nội', '03456255672', 'linhctm@gmail.com', 2002, N'Nữ', N'Nhân viên', '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       ('NV02', N'Nguyễn Xuân Dũng', N'Nghệ An', '0914355252', 'dungnx@gmail.com', 2004, N'Nam', N'Quản lý', '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	   ('NV03', N'Nguyễn Việt Anh', N'Hà Nội', '0357273774', 'anhnv@gmail.com', 2003, N'Nam', N'Nhân viên', '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	   ('NV04', N'Nguyễn Thế Thắng', N'Bắc Ninh', '0328842888', 'thangnt@gmail.com', 2004, N'Nam', N'Nhân viên', '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	   ('NV05', N'Nguyễn Tường Vân', N'Hà Nội', '0975539951', 'vannt@gmail.com', 2004, N'Nữ', N'Nhân viên', '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0)
SELECT * FROM NHANVIEN
--
GO
INSERT INTO KHACHHANG(ID, HoTen, DiaChi, SoDienThoai, Email, GioiTinh, NgayTao, NgaySua, TrangThai)
VALUES ('KH001', N'Nguyễn Hải Sơn', N'Hải Phòng', '0737657274', 'sonnh@gmail.com', N'Nam', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
       ('KH002', N'Vũ Huy Hoàng', N'Hải Phòng', '0784775823', 'hoangvh@gmail.com', N'Nam', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
	   ('KH003', N'Đàm Anh Tuấn', N'Phú Thọ', '0984829943', 'tuanda@gmail.com', N'Nam', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
	   ('KH004', N'Nguyễn Khánh Huyền', N'Hà Nội', '0987377423', 'huyennk@gmail.com', N'Nữ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
	   ('KH005', N'Đinh Hải Yến', N'Nam Định', '0335223366', 'yendh@gmail.com', N'Nữ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')
SELECT * FROM KHACHHANG
--
GO
INSERT INTO VOUCHER(ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, NgayBatDau, NgayKetThuc, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('V001', 'Discount10%', 20, N'Giảm theo phần trăm', 10, '2024-03-18 00:00:00', '2024-04-30 00:00:00', N'Hóa đơn trên 1500K giảm 10%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
       ('V002', 'Discount20%', 15, N'Giảm theo phần trăm', 20, '2024-03-18 00:00:00', '2024-04-30 00:00:00', N'Hóa đơn trên 4000K giảm 20%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
	   ('V003', 'Discount300K', 15, N'Giảm theo giá tiền', 300000, '2024-03-18 00:00:00', '2024-04-30 00:00:00', N'Hóa đơn trên 2000K giảm 300K', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động'),
	   ('V004', 'Discount700K', 10, N'Giảm theo giá tiền', 700000, '2024-03-18 00:00:00', '2024-04-30 00:00:00', N'Hóa đơn trên 3000K giảm 700K', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động')
SELECT * FROM VOUCHER
--
GO
INSERT INTO HOADON(ID, ID_NhanVien, ID_KhachHang, ID_Voucher, HinhThucThanhToan, TongTien, NgayTao, NgaySua, TrangThai)
VALUES ('HD001', 'NV01', 'KH001', 'V002', N'Chuyển khoản', 4696000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HD002', 'NV02', 'KH002', 'V001', N'Tiền mặt', 1701000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HD003', 'NV03', 'KH003', 'V003', N'Tiền mặt',1790000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chờ thanh toán'),
       ('HD004', 'NV04', 'KH004', 'V001', N'Kết hợp',1661000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chờ thanh toán'),
       ('HD005', 'NV01', 'KH005', 'V004', N'Chuyển khoản',2680000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã hủy'),
       ('HD006', 'NV01', 'KH001', 'V001', N'Tiền mặt',1791000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HD007', 'NV02', 'KH002', 'V003', N'Chuyển khoản',3180000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chờ thanh toán'),
       ('HD008', 'NV03', 'KH003', 'V004', N'Kết hợp',4776000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán')   
SELECT * FROM HOADON
--
GO
INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, DonGia, ThanhTien, NgayTao, NgaySua, TrangThai)
VALUES ('HDCT01', 'HD001', 'SPCT02', 2, 1990000, 3980000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT02', 'HD001', 'SPCT03', 1, 1890000, 1890000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT03', 'HD002', 'SPCT04', 1, 2090000, 2090000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT04', 'HD003', 'SPCT07', 2, 1790000, 3580000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chờ thanh toán'),
       ('HDCT05', 'HD004', 'SPCT08', 1, 1790000, 1790000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chờ thanh toán'),
       ('HDCT06', 'HD005', 'SPCT10', 2, 1690000, 3380000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chờ thanh toán'),
       ('HDCT07', 'HD006', 'SPCT02', 1, 1990000, 1990000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã hủy'),
       ('HDCT08', 'HD007', 'SPCT03', 2, 1890000, 3880000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã hủy'),
       ('HDCT09', 'HD008', 'SPCT04', 2, 2090000, 4180000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT10', 'HD008', 'SPCT07', 1, 1790000, 1790000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán')
SELECT * FROM HOADONCHITIET 

SELECT HOADON.ID, HOADON.NgayTao, ID_NhanVien, ID_KhachHang, TenVoucher, TongTien, HinhThucThanhToan FROM HOADON
JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID
WHERE HOADON.TrangThai = N'Đã thanh toán'

--
SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM VOUCHER
--
-- UPDATE VOUCHER SET TenVoucher = ?, SoLuong = ?, LoaiVoucher = ?, MucGiamGia = ?, MoTa = ? WHERE ID = ?
SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia,MoTa FROM VOUCHER
--
SELECT HOADONCHITIET.ID, TenSanPham, THUONGHIEU.Ten, TenMau, SIZE.Ten, CHATLIEU.Ten, DonGia, SoLuong, ThanhTien FROM HOADONCHITIET
JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID
JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID
JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID
JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID
JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID
JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID
--
SELECT MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) AS maxID FROM HOADONCHITIET

SELECT        HOADONCHITIET.ID, SANPHAMCHITIET.ID AS MaSanPhamChiTiet, SANPHAM.TenSanPham, SANPHAMCHITIET.GiaBan
FROM            HOADONCHITIET INNER JOIN
                         SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN
                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID

SELECT        HOADONCHITIET.ID AS MaHDCT, SANPHAMCHITIET.ID AS MaCTSP, SANPHAM.TenSanPham, SANPHAMCHITIET.GiaBan, HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien
FROM            HOADONCHITIET INNER JOIN
                         SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN
                         SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID