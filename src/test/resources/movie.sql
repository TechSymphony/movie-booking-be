DELETE FROM showtimes;
DELETE FROM movies;
DELETE FROM rooms;
DELETE FROM cinemas;
DELETE FROM movie_genres;

INSERT INTO movies (id, code, caster, description, director, language, name, sub_name, number_of_ratings, poster, rated, release_date, running_time, slug, trailer, end_date, horizontal_poster, sum_of_ratings) VALUES
  (1, 'mv-0001', 'Greta Lee, Teo Yoo', 'Muôn Kiếp Nhân Duyên xoay quanh hai nhân vật chính - Nora (Greta Lee) và Hae Sung (Teo Yoo). Tình bạn thân thiết của họ bị chia cắt khi Nora theo gia đình di cư khỏi Hàn Quốc vào năm 12 tuổi. 20 năm sau, như một mối duyên tiền định, họ gặp lại nhau tại Mỹ', 'Celine', 'Tiếng Anh', 'Past Lives', 'Muôn Kiếp Nhân Duyên', 3, 'https://cdn.discordapp.com/attachments/1159668660340789259/1169947106191093840/both-water.png?ex=65574126&is=6544cc26&hm=033c5de326692af06130dcc0e32cc684a441d99a98eecdc1c8e90cc028c27727&', 13, '2023-10-10', 106, 'past-lives', 'https://www.youtube.com/watch?v=lBdLBY249Do', '2023-11-25', 'https://cdn.discordapp.com/attachments/1159668660340789259/1170792832735387688/2-ga-ran.png?ex=655a54cb&is=6547dfcb&hm=9a0fcbd579af5a185b42278b807c3c3658a38c89ec598febf2d880daae1eb599&', 25);


INSERT INTO cinemas (id, address, city, district, name, description, phone_number, status) VALUES
  (1, '466', 'HCM', '122', 'Galaxy Trung Chanh', 'Hệ thống rạp The Cinema được thiết kế sang trọng và tiện nghi. Khách hàng bước vào không gian rạp sẽ cảm nhận ngay sự thoải mái và tiện lợi từ việc sắp xếp ghế ngồi theo lối kiến trúc hiện đại, tạo nên không gian xem phim tuyệt vời. Mỗi phòng chiếu được trang bị hệ thống âm thanh vòm và màn hình siêu nét, mang đến trải nghiệm hình ảnh và âm thanh sống động, hấp dẫn.', '091222', 'OPENING');

INSERT INTO rooms (id, available_seats, name, total_seats, cinema_id, slug) VALUES
  (1, 150, 'RAP 1-Trung Chánh', 150, 1, NULL);
INSERT INTO rooms (id, available_seats, name, total_seats, cinema_id, slug) VALUES
  (2, 150, 'RAP 2-Trung Chánh', 150, 1, NULL);

INSERT INTO showtimes (id, running_time, start_date, start_time, status, room_id, movie_id) VALUES (1, 140, '2024-06-19', '11:14:05.000000', 1, 1, 1);
INSERT INTO showtimes (id, running_time, start_date, start_time, status, room_id, movie_id) VALUES (2, 140, '2024-06-19', '11:14:05.000000', 2, 1, 1);

INSERT INTO movie_genres (id, name) VALUES
  (1, 'Kinh dị');
INSERT INTO movie_genres (id, name) VALUES
  (2, 'Hài');
INSERT INTO movie_genres (id, name) VALUES
  (3, 'Hoạt hình');
INSERT INTO movie_genres (id, name) VALUES
  (4, 'Hành động');
INSERT INTO movie_genres (id, name) VALUES
  (5, 'Tình cảm');
INSERT INTO movie_genres (id, name) VALUES
  (6, 'Gia đình');
