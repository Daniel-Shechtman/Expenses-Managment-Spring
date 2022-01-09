-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Jan 07, 2022 at 12:56 PM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `expence_managment_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) COLLATE utf16_bin NOT NULL,
  `password_hash` varchar(100) COLLATE utf16_bin NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `username`, `password_hash`, `date_created`) VALUES
(14, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-11-30 09:23:48'),
(15, 'dudu1', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-01 18:21:00'),
(16, 'dudu3', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-02 07:15:07'),
(17, 'dudu5', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 17:36:53'),
(18, 'dudu7', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 17:49:02'),
(19, 'azmin', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 17:57:40'),
(20, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:14:35'),
(21, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:15:18'),
(22, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:16:13'),
(23, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:16:29'),
(24, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:20:11'),
(25, 'dudu10', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:23:24'),
(26, '�ªnp�', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-06 20:35:41'),
(27, 'nissim', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-13 09:48:20'),
(28, 'baboo', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-13 09:55:05'),
(29, 'dudu131', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-13 10:45:10'),
(30, 'dudu1234', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-13 10:46:20'),
(31, 'test', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-20 12:54:25'),
(32, 'dudu21', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-20 13:20:18'),
(33, 'dudu211', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-20 21:03:39'),
(34, 'dudu2121', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-20 21:07:00'),
(35, '123456', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-20 21:51:13'),
(36, 'dudu432', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-21 14:41:06'),
(37, 'dudu3211', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2021-12-21 14:47:08'),
(38, 'dudu12', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-02 18:30:34'),
(39, 'dani', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-02 18:40:38'),
(40, 'dudu2', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-03 21:17:28'),
(41, 'dudu123', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-05 17:00:53');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf16_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `account_id`, `name`) VALUES
(1, 0, 'Food'),
(2, 0, 'Household'),
(11, 0, 'Loans'),
(4, 0, 'Automobile'),
(18, 0, 'Travel'),
(25, 35, 'asd'),
(50, 14, 'Test'),
(45, 14, 'Test55');

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
CREATE TABLE IF NOT EXISTS `expenses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `cost` float NOT NULL,
  `currency` varchar(5) COLLATE utf16_bin NOT NULL,
  `info` varchar(100) COLLATE utf16_bin NOT NULL,
  `date_created` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=83 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`id`, `account_id`, `category_id`, `cost`, `currency`, `info`, `date_created`) VALUES
(60, 15, 18, 201222, 'ILS', 'sasasa', 1640620564970),
(69, 14, 1, 45454, 'ILS', 'az', 1641238397187),
(81, 14, 45, 54321, 'EURO', 'asasa', 1641559726908),
(66, 39, 4, 434343, 'USD', 'sasasasa', 1641148889418);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
