INSERT INTO cinemas (id, address, city, district, name, description, phone_number, status)
VALUES (1, '466', 'HCM', '122', 'Galaxy Trung Chanh',
        'Hệ thống rạp The Cinema được thiết kế sang trọng và tiện nghi. Khách hàng bước vào không gian rạp sẽ cảm nhận ngay sự thoải mái và tiện lợi từ việc sắp xếp ghế ngồi theo lối kiến trúc hiện đại, tạo nên không gian xem phim tuyệt vời. Mỗi phòng chiếu được trang bị hệ thống âm thanh vòm và màn hình siêu nét, mang đến trải nghiệm hình ảnh và âm thanh sống động, hấp dẫn.',
        '091222', 'OPENING');

INSERT INTO rooms (id, available_seats, name, total_seats, cinema_id, slug)
VALUES (1, 150, 'RAP 1-Trung Chánh', 150, 1, NULL);
INSERT INTO rooms (id, available_seats, name, total_seats, cinema_id, slug)
VALUES (2, 150, 'RAP 2-Trung Chánh', 150, 1, NULL);
INSERT INTO `seat_types` (`id`, `name`, `price`)
VALUES (1, 'Normal', 50000),
       (2, 'VIP', 70000),
       (3, 'string', 0);
INSERT INTO `seats` (`id`, `row_name`, `row_index`, `status`, `room_id`, `seat_type_id`)
VALUES (1, 'A', 1, 1, 1, 1),
       (2, 'A', 2, 1, 1, 1),
       (3, 'A', 3, 1, 1, 1);

