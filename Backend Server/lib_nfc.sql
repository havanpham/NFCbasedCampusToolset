-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 30, 2012 at 05:00 PM
-- Server version: 5.1.33
-- PHP Version: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lib_nfc`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `id` varchar(10) COLLATE utf8_bin NOT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `major` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `name`, `major`) VALUES
('et010101', 'Ly thuyet mach', 'et'),
('et010102', 'Ly thuyet truyen tin', 'et'),
('et020103', 'Truong dien tu', 'et'),
('et020101', 'Mang thong tin', 'et'),
('et010301', 'Mang may tinh', 'et'),
('et020303', 'Mang thong tin', 'et'),
('et020301', 'Xu ly so tin hieu', 'et'),
('ee010101', 'Vi xu ly', 'ee'),
('ee020301', 'Ky thuat dien', 'ee'),
('ee010102', 'Dieu khien tu dong', 'ee'),
('fl010201', 'English of science', 'fl'),
('fl010103', 'English of Speaking', 'fl'),
('fl020202', 'English of telecommunication', 'fl'),
('fl010302', 'English of Listenning', 'fl'),
('ch010101', 'Hoa hoc dai cuong', 'ch'),
('ch010103', 'Hoa vo co', 'ch'),
('ch020102', 'Hoa huu co', 'ch'),
('ch020103', 'Hoa duoc', 'ch'),
('ch020301', 'Hoa dau', 'ch'),
('ch010301', 'Hoa ly', 'ch');

-- --------------------------------------------------------

--
-- Table structure for table `major`
--

CREATE TABLE IF NOT EXISTS `major` (
  `id_m` varchar(10) COLLATE utf8_bin NOT NULL,
  `name_m` varchar(50) COLLATE utf8_bin NOT NULL,
  `px_x` float NOT NULL,
  `px_y` float NOT NULL,
  PRIMARY KEY (`id_m`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `major`
--

INSERT INTO `major` (`id_m`, `name_m`, `px_x`, `px_y`) VALUES
('et', 'Electronic and Telecommunication', 100, 100),
('ee', 'Electrical Engineering', 100, 200),
('ph', 'Physic', 100, 300),
('mi', 'Mathematics', 100, 400),
('ch', 'Chemical Engineering', 200, 100),
('fl', 'Foreign Language', 200, 200),
('ms', 'material science and technology', 200, 200),
('en', 'Environment', 200, 300);
