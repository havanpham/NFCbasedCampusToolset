-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 10, 2012 at 05:32 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tv2`
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
('et010101', 'Ly thuyet mach', 'ET'),
('et010102', 'Ly thuyet truyen tin', 'ET'),
('et020103', 'Truong dien tu', 'ET'),
('et020101', 'Mang thong tin', 'ET'),
('et010301', 'Mang may tinh', 'ET'),
('et020303', 'Mang thong tin', 'ET'),
('et020301', 'Xu ly so tin hieu', 'ET'),
('ee010101', 'Vi xu ly', 'EE'),
('ee020301', 'Ky thuat dien', 'EE'),
('ee010102', 'Dieu khien tu dong', 'EE'),
('fl010201', 'English of science', 'FL'),
('fl010103', 'English of Speaking', 'FL'),
('fl020202', 'English of telecommunication', 'FL'),
('fl010302', 'English of Listenning', 'FL'),
('ch010101', 'Hoa hoc dai cuong', 'CH'),
('ch010103', 'Hoa vo co', 'CH'),
('ch020102', 'Hoa huu co', 'CH'),
('ch020103', 'Hoa duoc', 'CH'),
('ch020301', 'Hoa dau', 'CH'),
('ch010301', 'Hoa ly', 'CH');

-- --------------------------------------------------------

--
-- Table structure for table `major`
--

CREATE TABLE IF NOT EXISTS `major` (
  `id_m` varchar(10) COLLATE utf8_bin NOT NULL,
  `name_m` varchar(50) COLLATE utf8_bin NOT NULL,
  `px_x` int(11) NOT NULL,
  `px_y` int(11) NOT NULL,
  PRIMARY KEY (`id_m`),
  KEY `id_m` (`id_m`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `major`
--

INSERT INTO `major` (`id_m`, `name_m`, `px_x`, `px_y`) VALUES
('FL', 'Foreign Language', 296, 347),
('IT', 'Infomation Techology', 156, 311),
('MI', 'Mathematics', 300, 270),
('', 'Chemical Engineering', 305, 234),
('EN', 'Environment', 298, 311),
('ET', 'Electric and Telecommunication', 163, 268),
('EE', 'Electrical Engineering', 165, 233),
('FA', 'Fashion', 151, 347);
