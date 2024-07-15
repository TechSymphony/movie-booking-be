-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 10, 2024 at 09:08 PM
-- Server version: 10.6.17-MariaDB
-- PHP Version: 8.1.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_dev`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--
use db_dev;
CREATE TABLE `bills` (
  `id` binary(16) NOT NULL,
  `payment_at` datetime(6) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `user_id` binary(16) NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `change_point` int(11) DEFAULT NULL,
  `cancel_reason` varchar(255) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `cancel_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill_status`
--

CREATE TABLE `bill_status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cinemas`
--

CREATE TABLE `cinemas` (
  `id` int(10) UNSIGNED NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `phone_number` varchar(255) NOT NULL,
  `status` enum('CLOSED','MAINTAINED','OPENING') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cinemas_seq`
--

CREATE TABLE `cinemas_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer_order`
--

CREATE TABLE `customer_order` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer_order_seq`
--

CREATE TABLE `customer_order_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(10) UNSIGNED NOT NULL,
  `code` varchar(255) NOT NULL,
  `caster` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sub_name` varchar(255) DEFAULT NULL,
  `number_of_ratings` int(11) DEFAULT NULL,
  `poster` varchar(255) DEFAULT NULL,
  `rated` int(10) UNSIGNED DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `running_time` int(11) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `trailer` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `horizontal_poster` varchar(255) DEFAULT NULL,
  `sum_of_ratings` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movies_images`
--

CREATE TABLE `movies_images` (
  `id` int(10) UNSIGNED NOT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movies_seq`
--

CREATE TABLE `movies_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie_genre`
--

CREATE TABLE `movie_genre` (
  `movie_genre_id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie_genres`
--

CREATE TABLE `movie_genres` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie_genres_seq`
--

CREATE TABLE `movie_genres_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `permissions`
--

CREATE TABLE `permissions` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `decription` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `permissions_seq`
--

CREATE TABLE `permissions_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles_seq`
--

CREATE TABLE `roles_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles_users`
--

CREATE TABLE `roles_users` (
  `role_id` int(11) NOT NULL,
  `users_id` binary(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role_permission`
--

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `id` int(10) UNSIGNED NOT NULL,
  `code` varchar(255) NOT NULL,
  `available_seats` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `total_seats` int(11) DEFAULT NULL,
  `cinema_id` int(10) UNSIGNED NOT NULL,
  `slug` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rooms_seq`
--

CREATE TABLE `rooms_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seats`
--

CREATE TABLE `seats` (
  `id` int(11) NOT NULL,
  `row_name` varchar(255) DEFAULT NULL,
  `row_index` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT 1,
  `room_id` int(10) UNSIGNED NOT NULL,
  `seat_type_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seats_seq`
--

CREATE TABLE `seats_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seat_types`
--

CREATE TABLE `seat_types` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seat_types_seq`
--

CREATE TABLE `seat_types_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `showtimes`
--

CREATE TABLE `showtimes` (
  `id` int(10) UNSIGNED NOT NULL,
  `running_time` int(11) DEFAULT NULL,
  `start_date` date NOT NULL,
  `start_time` time(6) NOT NULL,
  `status` tinyint(1) DEFAULT 1,
  `room_id` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `movie_id` int(10) UNSIGNED DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `showtimes_seq`
--

CREATE TABLE `showtimes_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `id` binary(16) NOT NULL,
  `bill_id` binary(16) DEFAULT NULL,
  `showtime_id` int(10) UNSIGNED NOT NULL,
  `seat_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `verify` bit(1) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `verify_account` varchar(255) DEFAULT NULL,
  `verify_mail` varchar(255) DEFAULT NULL,
  `verify_pass` varchar(255) DEFAULT NULL,
  `gender` enum('FEMALE','MALE','UNKNOWN') NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `provider` enum('LOCAL','GOOGLE') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_bills_users` (`user_id`),
  ADD KEY `FK7s2dh8hdb6rthogn42qb1a3aw` (`status`) USING BTREE;

--
-- Indexes for table `bill_status`
--
ALTER TABLE `bill_status`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `cinemas`
--
ALTER TABLE `cinemas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer_order`
--
ALTER TABLE `customer_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `movie_id` (`code`);

--
-- Indexes for table `movies_images`
--
ALTER TABLE `movies_images`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `FK_movies_images_movies` (`movie_id`);

--
-- Indexes for table `movie_genre`
--
ALTER TABLE `movie_genre`
  ADD PRIMARY KEY (`movie_genre_id`,`movie_id`) USING BTREE,
  ADD KEY `FKg7f38h6umffo51no9ywq91438` (`movie_id`),
  ADD KEY `FK87qryjrya5r6wn0su5fl0ynu6` (`movie_genre_id`) USING BTREE;

--
-- Indexes for table `movie_genres`
--
ALTER TABLE `movie_genres`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `roles_users`
--
ALTER TABLE `roles_users`
  ADD PRIMARY KEY (`role_id`,`users_id`),
  ADD UNIQUE KEY `UK_snx09g5kxfrdi207cl1jhjmev` (`users_id`);

--
-- Indexes for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD PRIMARY KEY (`role_id`,`permission_id`),
  ADD KEY `FK2xn8qv4vw30i04xdxrpvn3bdi` (`permission_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `room_cinema` (`cinema_id`);

--
-- Indexes for table `seats`
--
ALTER TABLE `seats`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `FKfu2c510l2cl8lblj3x3x4o0vl` (`room_id`),
  ADD KEY `FK3cwtx4s7f8dbcrghl8uv2s08d` (`seat_type_id`);

--
-- Indexes for table `seat_types`
--
ALTER TABLE `seat_types`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `showtimes`
--
ALTER TABLE `showtimes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrumrrbei9jppryk4teoyoetit` (`room_id`),
  ADD KEY `FKeltpyuei1d5g3n6ikpsjwwil6` (`movie_id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo0u22315eoxdv59tn6wsdn8b1` (`showtime_id`),
  ADD KEY `FK4qm1s4orf1onvmo4y5qsbl5mv` (`seat_id`) USING BTREE,
  ADD KEY `FKaqpjta99er6ahhyyq5689cyw4` (`bill_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill_status`
--
ALTER TABLE `bill_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cinemas`
--
ALTER TABLE `cinemas`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movies_images`
--
ALTER TABLE `movies_images`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `seats`
--
ALTER TABLE `seats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `seat_types`
--
ALTER TABLE `seat_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `showtimes`
--
ALTER TABLE `showtimes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `FK_bills_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `movies_images`
--
ALTER TABLE `movies_images`
  ADD CONSTRAINT `FK_movies_images_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

--
-- Constraints for table `movie_genre`
--
ALTER TABLE `movie_genre`
  ADD CONSTRAINT `FK87qryjrya5r6wn0su5fl0ynu6` FOREIGN KEY (`movie_genre_id`) REFERENCES `movie_genres` (`id`),
  ADD CONSTRAINT `FKg7f38h6umffo51no9ywq91438` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

--
-- Constraints for table `roles_users`
--
ALTER TABLE `roles_users`
  ADD CONSTRAINT `FK4glr8k8swy5nti6n5x35ofucj` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKsmos02hm32191ogexm2ljik9x` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Constraints for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `FK2xn8qv4vw30i04xdxrpvn3bdi` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  ADD CONSTRAINT `FKtfgq8q9blrp0pt1pvggyli3v9` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Constraints for table `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `room_cinema` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`id`);

--
-- Constraints for table `seats`
--
ALTER TABLE `seats`
  ADD CONSTRAINT `FK3cwtx4s7f8dbcrghl8uv2s08d` FOREIGN KEY (`seat_type_id`) REFERENCES `seat_types` (`id`),
  ADD CONSTRAINT `FKg993pi7ucgy616icmddq8u335` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

--
-- Constraints for table `showtimes`
--
ALTER TABLE `showtimes`
  ADD CONSTRAINT `FKeltpyuei1d5g3n6ikpsjwwil6` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  ADD CONSTRAINT `FKrumrrbei9jppryk4teoyoetit` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FK4qm1s4orf1onvmo4y5qsbl5mv` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`id`),
  ADD CONSTRAINT `FKaqpjta99er6ahhyyq5689cyw4` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKo0u22315eoxdv59tn6wsdn8b1` FOREIGN KEY (`showtime_id`) REFERENCES `showtimes` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
