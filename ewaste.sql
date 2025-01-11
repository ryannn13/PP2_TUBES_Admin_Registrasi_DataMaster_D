-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 11, 2025 at 05:50 AM
-- Server version: 8.0.30
-- PHP Version: 8.3.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ewaste`
--

-- --------------------------------------------------------

--
-- Table structure for table `dropbox`
--

CREATE TABLE `dropbox` (
  `dropbox_id` int NOT NULL,
  `lokasi` varchar(255) NOT NULL,
  `kapasitas` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `dropbox`
--

INSERT INTO `dropbox` (`dropbox_id`, `lokasi`, `kapasitas`) VALUES
(1, 'Dropbox A', 100),
(2, 'Dropbox B', 150),
(3, 'Dropbox C', 200);

-- --------------------------------------------------------

--
-- Table structure for table `kategorisampah`
--

CREATE TABLE `kategorisampah` (
  `kategori_id` int NOT NULL,
  `nama_kategori` varchar(255) NOT NULL,
  `poin` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kategorisampah`
--

INSERT INTO `kategorisampah` (`kategori_id`, `nama_kategori`, `poin`) VALUES
(1, 'Elektronik', 10),
(2, 'Organik', 5),
(3, 'Non-Organik', 3),
(4, 'Model', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `penjemputan`
--

CREATE TABLE `penjemputan` (
  `penjemputan_id` int NOT NULL,
  `masyarakat_id` int NOT NULL,
  `kurir_id` int DEFAULT NULL,
  `sampah_id` int NOT NULL,
  `dropbox_id` int DEFAULT NULL,
  `status` enum('pending','dalam penjemputan','selesai') DEFAULT 'pending',
  `tanggal_request` datetime DEFAULT CURRENT_TIMESTAMP,
  `tanggal_selesai` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `penjemputan`
--

INSERT INTO `penjemputan` (`penjemputan_id`, `masyarakat_id`, `kurir_id`, `sampah_id`, `dropbox_id`, `status`, `tanggal_request`, `tanggal_selesai`) VALUES
(1, 3, 4, 4, 1, 'selesai', '2025-01-11 00:29:42', '2025-01-11 00:00:00'),
(2, 3, 4, 5, 3, 'selesai', '2025-01-11 00:33:14', '2025-01-11 00:00:00'),
(3, 3, 4, 6, 2, 'selesai', '2025-01-11 01:27:50', '2025-01-11 00:00:00'),
(4, 3, 4, 7, 1, 'selesai', '2025-01-11 02:01:26', '2025-01-11 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `points`
--

CREATE TABLE `points` (
  `point_id` int NOT NULL,
  `masyarakat_id` int NOT NULL,
  `jumlah` int NOT NULL,
  `tanggal` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `points`
--

INSERT INTO `points` (`point_id`, `masyarakat_id`, `jumlah`, `tanggal`) VALUES
(1, 3, 10, '2025-01-11 02:00:27'),
(2, 3, 10, '2025-01-11 02:01:49'),
(3, 3, 1000, '2025-01-11 02:26:53'),
(4, 3, 10, '2025-01-11 02:27:03');

-- --------------------------------------------------------

--
-- Table structure for table `sampah`
--

CREATE TABLE `sampah` (
  `sampah_id` int NOT NULL,
  `nama_sampah` varchar(255) NOT NULL,
  `jumlah_sampah` int NOT NULL,
  `berat_sampah` float NOT NULL,
  `kategori_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `sampah`
--

INSERT INTO `sampah` (`sampah_id`, `nama_sampah`, `jumlah_sampah`, `berat_sampah`, `kategori_id`) VALUES
(1, 'Televisi', 2, 15.5, 1),
(2, 'Daun Kering', 10, 5, 2),
(3, 'Botol Plastik', 20, 3, 3),
(4, 'Poco X3 Pro', 1, 2, 1),
(5, 'samsung', 2, 10, 1),
(6, 'pocophone 1', 1, 10, 1),
(7, 'jersey adidas limited', 1, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('masyarakat','kurir','admin') NOT NULL,
  `status` enum('pending','active','rejected') DEFAULT 'pending',
  `nama` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nomor_telepon` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `status`, `nama`, `alamat`, `email`, `nomor_telepon`) VALUES
(1, 'admin', '$2a$10$Dow1nW.q/B1Nqv6K/Nr6Ouf1hSkKxF8EjEr2PQcl/PQzU72I5n39S', 'admin', 'active', 'Admin', 'Admin Address', 'admin@example.com', '081234567890'),
(2, 'arielseptiadi', 'admin123', 'admin', 'active', 'Muhamad Ariel Septiadi', 'gerlong', 'arielseptiadi3@gmail.com', '082112488834'),
(3, 'aldi', 'admin123', 'masyarakat', 'active', 'Aldi Maulana Fadilah', 'gerlong', 'aldiciguantengtea@gmail.com', '081113577734'),
(4, 'rian', 'admin123', 'kurir', 'active', 'Rian Hidayat', 'gerlong', 'rianhidayat@gmail.com', '083115499914'),
(5, 'aziz', 'admin123', 'masyarakat', 'active', 'Abdul Aziz', 'gerlong', 'abdulaziz@gmail.com', '082112488834');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dropbox`
--
ALTER TABLE `dropbox`
  ADD PRIMARY KEY (`dropbox_id`);

--
-- Indexes for table `kategorisampah`
--
ALTER TABLE `kategorisampah`
  ADD PRIMARY KEY (`kategori_id`);

--
-- Indexes for table `penjemputan`
--
ALTER TABLE `penjemputan`
  ADD PRIMARY KEY (`penjemputan_id`),
  ADD KEY `masyarakat_id` (`masyarakat_id`),
  ADD KEY `kurir_id` (`kurir_id`),
  ADD KEY `sampah_id` (`sampah_id`),
  ADD KEY `dropbox_id` (`dropbox_id`);

--
-- Indexes for table `points`
--
ALTER TABLE `points`
  ADD PRIMARY KEY (`point_id`),
  ADD KEY `masyarakat_id` (`masyarakat_id`);

--
-- Indexes for table `sampah`
--
ALTER TABLE `sampah`
  ADD PRIMARY KEY (`sampah_id`),
  ADD KEY `kategori_id` (`kategori_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dropbox`
--
ALTER TABLE `dropbox`
  MODIFY `dropbox_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `kategorisampah`
--
ALTER TABLE `kategorisampah`
  MODIFY `kategori_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `penjemputan`
--
ALTER TABLE `penjemputan`
  MODIFY `penjemputan_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `points`
--
ALTER TABLE `points`
  MODIFY `point_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sampah`
--
ALTER TABLE `sampah`
  MODIFY `sampah_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `penjemputan`
--
ALTER TABLE `penjemputan`
  ADD CONSTRAINT `penjemputan_ibfk_1` FOREIGN KEY (`masyarakat_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `penjemputan_ibfk_2` FOREIGN KEY (`kurir_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `penjemputan_ibfk_3` FOREIGN KEY (`sampah_id`) REFERENCES `sampah` (`sampah_id`),
  ADD CONSTRAINT `penjemputan_ibfk_4` FOREIGN KEY (`dropbox_id`) REFERENCES `dropbox` (`dropbox_id`);

--
-- Constraints for table `points`
--
ALTER TABLE `points`
  ADD CONSTRAINT `points_ibfk_1` FOREIGN KEY (`masyarakat_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `sampah`
--
ALTER TABLE `sampah`
  ADD CONSTRAINT `sampah_ibfk_1` FOREIGN KEY (`kategori_id`) REFERENCES `kategorisampah` (`kategori_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
