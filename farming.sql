-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 16, 2020 at 06:06 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `farming`
--

-- --------------------------------------------------------

--
-- Table structure for table `agri_centers`
--

CREATE TABLE IF NOT EXISTS `agri_centers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ac_centre` varchar(200) NOT NULL,
  `ac_desc` varchar(200) NOT NULL,
  `ac_address` text NOT NULL,
  `ac_phone` varchar(200) NOT NULL,
  `ac_email` varchar(200) NOT NULL,
  `ac_image` tinytext NOT NULL,
  `ac_latitude` varchar(200) NOT NULL,
  `ac_longitude` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `agri_centers`
--

INSERT INTO `agri_centers` (`id`, `ac_centre`, `ac_desc`, `ac_address`, `ac_phone`, `ac_email`, `ac_image`, `ac_latitude`, `ac_longitude`) VALUES
(1, 'Dell Service Center', 'Computer Store', '2nd Floor Sankarasseri Arcade, 41/1749, Chittoor Rd, Near Suchendra Hospital, Kacheripady, Kochi, Kerala 682018', '072930 03991', 'dellservice@gmail.com', '2020-04-16-09-04-33poster3.jpg', '9.955326', '76.242426');

-- --------------------------------------------------------

--
-- Table structure for table `argriculture_news`
--

CREATE TABLE IF NOT EXISTS `argriculture_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `headline` varchar(200) NOT NULL,
  `image` tinytext NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `argriculture_news`
--

INSERT INTO `argriculture_news` (`id`, `headline`, `image`, `description`) VALUES
(1, 'asdasd', '2020-03-29-06-52-54wallpapers.jpeg', 'asdasd'),
(2, 'asdasdasdasdas', '2020-04-16-09-17-03poster4.jpg', 'asdasdas l,;ll;,a sdl;,;las,d '),
(3, 'headmin', '2020-04-16-09-18-44poster2.png', 'sub\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `crops`
--

CREATE TABLE IF NOT EXISTS `crops` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `image` tinytext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `crops`
--

INSERT INTO `crops` (`id`, `name`, `image`) VALUES
(1, 'asdas', '2020-03-29-06-57-39l.jpg'),
(2, 'asdasd', '2020-04-16-09-19-28recipefinder.png'),
(3, 'asasd', '2020-04-16-09-20-02social-media-refer-friend-concept_23-2148260460.jpg'),
(4, 'asdasd', '2020-04-16-09-20-124108753360_9c225d5663_b.jpg'),
(5, 'tomato', '2020-04-16-09-20-18set-young-people-emotions_23-2148234447.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `crop_lifecycle`
--

CREATE TABLE IF NOT EXISTS `crop_lifecycle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `week` varchar(200) NOT NULL,
  `lifecycle` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `crop_lifecycle`
--

INSERT INTO `crop_lifecycle` (`id`, `cid`, `week`, `lifecycle`) VALUES
(1, 1, 'week 1', 'asdasdasdasd'),
(2, 1, 'week 2', 'asdasd klnkkjhj sj  hjsjad fjhhj sfjh hjksdnmbmnbmnb  ');

-- --------------------------------------------------------

--
-- Table structure for table `my_crop`
--

CREATE TABLE IF NOT EXISTS `my_crop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `status` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `my_crop`
--

INSERT INTO `my_crop` (`id`, `uid`, `cid`, `status`) VALUES
(1, 1, 5, ''),
(2, 1, 1, ''),
(3, 12, 1, ''),
(4, 12, 5, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_market`
--

CREATE TABLE IF NOT EXISTS `tbl_market` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crop` varchar(200) NOT NULL,
  `year` varchar(200) NOT NULL,
  `month` varchar(200) NOT NULL,
  `district` varchar(200) NOT NULL,
  `price` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `tbl_market`
--

INSERT INTO `tbl_market` (`id`, `crop`, `year`, `month`, `district`, `price`) VALUES
(1, 'tomato', '2018', 'january', 'alapuzha', '300'),
(2, 'tomato', '2018', 'january', 'kochi', '400'),
(3, 'tomato', '2018', 'january', 'edapally', '350.50'),
(4, 'tomato', '2018', 'january', 'qq', '320'),
(5, 'tomato', '2018', 'january', 'test 1', '200'),
(6, 'tomato', '2018', 'january', 'test 2', '280');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_query`
--

CREATE TABLE IF NOT EXISTS `tbl_query` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `question` varchar(200) NOT NULL,
  `qdate` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_query`
--

INSERT INTO `tbl_query` (`id`, `uid`, `question`, `qdate`) VALUES
(1, 1, 'testing ', '16-04-20');

-- --------------------------------------------------------

--
-- Table structure for table `tb_query_answer`
--

CREATE TABLE IF NOT EXISTS `tb_query_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `answer` text NOT NULL,
  `adate` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `tb_query_answer`
--

INSERT INTO `tb_query_answer` (`id`, `qid`, `uid`, `answer`, `adate`) VALUES
(1, 1, 1, 'ans', '16-04-20'),
(2, 1, 1, 'tes', '16-04-20'),
(3, 1, 1, 'kgdkzggkxkgdksydydkydsysoy\njgzkgzktzkzkzgzkttzt\ntzitztsiysiysztsizitzitxitzitzt\ntitzzittzzuttztuzzgzkxgkxjgxti\ntiztizigxgxitztizruzgzzutiztgkzjituzttziuzt\nitsitzzkgztizitikgzt', '16-04-20'),
(4, 1, 1, 'kgdkzggkxkgdksydydkydsysoy\njgzkgzktzkzkzgzkttzt\ntzitztsiysiysztsizitzitxitzitzt\ntitzzittzzuttztuzzgzkxgkxjgxti\ntiztizigxgxitztizruzgzzutiztgkzjituzttziuzt\nitsitzzkgztizitikgzt', '16-04-20'),
(5, 1, 1, 'dyiyidoydyodyodyofyof\nxyioxyyoxyoxyodyodyofgi\nxtiyoxyixiyxyixyixixyyixyox\nxtiyoxyixiyxyixyixixyyixyox\nxtutixtixtixitxyixyixyixyixyixtixitx', '16-04-20');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(255) NOT NULL,
  `Lname` varchar(200) NOT NULL,
  `phone_no` varchar(13) NOT NULL,
  `email` varchar(255) NOT NULL,
  `image` tinytext NOT NULL,
  `location` varchar(200) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `name`, `Lname`, `phone_no`, `email`, `image`, `location`, `latitude`, `longitude`, `status`) VALUES
(1, 'abin', '12345', 'User', 'User', '7012656981', 'abin.ck.9@gmail.com', '2020-03-29-04-10-04user.png', '', '9.954365', '76.242402', 'q'),
(12, 'test', 'test', 'test', 'test', 'test', 'test', '', '', '9.95952174763167', '76.24050233118827', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
