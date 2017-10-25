-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.14 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for chat_online
CREATE DATABASE IF NOT EXISTS `chat_online` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `chat_online`;

-- Dumping structure for table chat_online.conversation
CREATE TABLE IF NOT EXISTS `conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mã hội thoại',
  `name` varchar(250) DEFAULT NULL COMMENT 'tên hội thoại',
  `avatar_path` varchar(250) DEFAULT NULL COMMENT 'logo nhóm',
  `description` varchar(1000) DEFAULT NULL COMMENT 'mô tả ngắn',
  `frame_color` varchar(50) DEFAULT NULL COMMENT 'màu phông nền',
  `main_icon` varchar(50) DEFAULT NULL COMMENT 'biểu tượng chính',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'thời gian tạo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='lịch sử hội thoại';

-- Dumping data for table chat_online.conversation: ~9 rows (approximately)
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
REPLACE INTO `conversation` (`id`, `name`, `avatar_path`, `description`, `frame_color`, `main_icon`, `created_at`) VALUES
	(1, 'BTL lập trình mạng', NULL, NULL, NULL, NULL, '2017-10-22 21:25:23'),
	(2, 'BTL an toàn mạng', NULL, NULL, NULL, NULL, '2017-10-22 21:25:25'),
	(3, NULL, NULL, '1 5', NULL, NULL, '2017-10-22 21:25:26'),
	(4, 'BTL Phương pháp luận', NULL, NULL, NULL, NULL, '2017-10-22 21:25:28'),
	(5, NULL, NULL, '1 2', NULL, NULL, '2017-10-23 12:58:13'),
	(6, NULL, NULL, '1 3', NULL, NULL, '2017-10-23 16:04:23'),
	(7, NULL, NULL, '1 4', NULL, NULL, '2017-10-23 16:04:58'),
	(8, NULL, NULL, '1 6', NULL, NULL, '2017-10-23 16:05:13'),
	(9, NULL, NULL, '1 7', NULL, NULL, '2017-10-23 16:05:29');
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;

-- Dumping structure for table chat_online.friendship
CREATE TABLE IF NOT EXISTS `friendship` (
  `sender_id` int(11) NOT NULL COMMENT 'mã người gửi',
  `receiver_id` int(11) NOT NULL COMMENT 'mã người nhận',
  `confirm` int(11) DEFAULT '0' COMMENT 'xác nhận: 0 - chưa đồng ý; 1 - đồng ý; 2 - chặn',
  `ignore` int(11) DEFAULT '0' COMMENT 'bỏ qua',
  PRIMARY KEY (`sender_id`,`receiver_id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `FK_friend_ship_user` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_friend_ship_user_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='mối quan hệ';

-- Dumping data for table chat_online.friendship: ~6 rows (approximately)
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
REPLACE INTO `friendship` (`sender_id`, `receiver_id`, `confirm`, `ignore`) VALUES
	(1, 2, 1, 0),
	(1, 6, 1, 0),
	(1, 7, 1, 0),
	(3, 1, 1, 0),
	(4, 1, 1, 0),
	(5, 1, 1, 0);
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;

-- Dumping structure for table chat_online.group
CREATE TABLE IF NOT EXISTS `group` (
  `conversation_id` int(11) NOT NULL COMMENT 'mã hội thoại',
  `user_id` int(11) NOT NULL COMMENT 'mã người dùng',
  `nick_name` varchar(50) DEFAULT NULL COMMENT 'nick name người dùng trong cuộc hội thoại',
  `is_participant` int(11) DEFAULT '0' COMMENT 'kiểm tra tham gia: 0 - trong nhóm; 1 - đã rời nhóm',
  PRIMARY KEY (`conversation_id`,`user_id`),
  KEY `conversation_id` (`conversation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FK_conversation_user_conversation` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `FK_conversation_user_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='liên hệ cuộc hội thoại - người dùng (dùng cho cả chat giữa 2 hay nhiều người)';

-- Dumping data for table chat_online.group: ~24 rows (approximately)
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
REPLACE INTO `group` (`conversation_id`, `user_id`, `nick_name`, `is_participant`) VALUES
	(1, 1, 'tuanpham', 0),
	(1, 2, 'thangbui', 0),
	(1, 3, 'hangnguyen', 0),
	(2, 1, 'tuanpham', 0),
	(2, 4, 'quannguyen', 0),
	(2, 5, 'tiennguyen', 0),
	(3, 1, 'tuanpham', 0),
	(3, 5, 'tiennguyen', 0),
	(4, 1, 'tuanpham', 0),
	(4, 2, 'thangbui', 0),
	(4, 3, 'hangnguyen', 0),
	(4, 4, 'quannguyen', 0),
	(4, 6, 'trangnguyen', 0),
	(4, 8, 'hienpham', 0),
	(5, 1, 'Leo Valdez', 0),
	(5, 2, 'thangbui', 0),
	(6, 1, 'tuanpham', 0),
	(6, 3, 'hangnguyen', 0),
	(7, 1, 'tuanpham', 0),
	(7, 4, 'quannguyen', 0),
	(8, 1, 'tuanpham', 0),
	(8, 6, 'trangnguyen', 0),
	(9, 1, 'tuanpham', 0),
	(9, 7, 'datnguyen', 0);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;

-- Dumping structure for table chat_online.message
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mã tin nhắn',
  `conversation_id` int(11) NOT NULL COMMENT 'mã hội thoại',
  `user_id` int(11) NOT NULL COMMENT 'mã người dùng',
  `content` text COMMENT 'nội dung',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'thời gian tạo',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'thời gian cập nhật',
  `is_deleted` int(11) DEFAULT '0' COMMENT 'kiểm tra xóa: 0 - chưa xóa; 1 - đã xóa',
  PRIMARY KEY (`id`),
  KEY `conversation_id` (`conversation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FK_message_conversation` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `FK_message_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COMMENT='tin nhắn';

-- Dumping data for table chat_online.message: ~58 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
REPLACE INTO `message` (`id`, `conversation_id`, `user_id`, `content`, `created_at`, `updated_at`, `is_deleted`) VALUES
	(55, 5, 1, 'hey bro', '2017-10-24 21:56:05', NULL, 0),
	(56, 9, 1, 'ey', '2017-10-24 21:57:37', NULL, 0),
	(57, 9, 1, 'àqwfqf', '2017-10-24 21:57:39', NULL, 0),
	(58, 9, 1, 'a', '2017-10-24 21:57:41', NULL, 0),
	(59, 9, 1, 'a', '2017-10-24 21:57:41', NULL, 0),
	(60, 9, 1, 'a', '2017-10-24 21:57:41', NULL, 0),
	(61, 9, 1, 'a', '2017-10-24 21:57:41', NULL, 0),
	(62, 9, 1, 'a', '2017-10-24 21:57:41', NULL, 0),
	(63, 9, 1, 'â', '2017-10-24 21:57:43', NULL, 0),
	(64, 9, 1, 'a', '2017-10-24 21:57:43', NULL, 0),
	(65, 9, 1, 'a', '2017-10-24 21:57:43', NULL, 0),
	(66, 9, 1, 'a', '2017-10-24 21:57:43', NULL, 0),
	(67, 9, 1, 'a', '2017-10-24 21:57:44', NULL, 0),
	(68, 9, 1, 'â', '2017-10-24 21:57:44', NULL, 0),
	(69, 9, 1, 'a', '2017-10-24 21:57:44', NULL, 0),
	(70, 9, 1, 'a', '2017-10-24 21:57:44', NULL, 0),
	(71, 9, 1, 'a', '2017-10-24 21:57:44', NULL, 0),
	(72, 9, 1, 'a', '2017-10-24 21:57:45', NULL, 0),
	(73, 9, 1, 'a', '2017-10-24 21:57:45', NULL, 0),
	(74, 9, 1, 'vgẻb', '2017-10-24 21:57:45', NULL, 0),
	(75, 9, 1, 'erbẻ', '2017-10-24 21:57:46', NULL, 0),
	(76, 9, 1, 'ten', '2017-10-24 21:57:46', NULL, 0),
	(77, 9, 1, 'rt', '2017-10-24 21:57:46', NULL, 0),
	(78, 9, 1, 'nr', '2017-10-24 21:57:46', NULL, 0),
	(79, 9, 1, 'tn', '2017-10-24 21:57:47', NULL, 0),
	(80, 9, 1, 'rt', '2017-10-24 21:57:47', NULL, 0),
	(81, 9, 1, 'rtn', '2017-10-24 21:57:47', NULL, 0),
	(82, 9, 1, 't', '2017-10-24 21:57:47', NULL, 0),
	(83, 7, 1, 'a', '2017-10-24 22:01:48', NULL, 0),
	(84, 7, 1, 'x', '2017-10-24 22:01:54', NULL, 0),
	(85, 6, 1, 'q', '2017-10-24 22:02:55', NULL, 0),
	(86, 6, 1, 's', '2017-10-24 22:03:04', NULL, 0),
	(87, 9, 7, 'a', '2017-10-24 23:09:45', NULL, 0),
	(88, 9, 7, 'dtndrmdr', '2017-10-24 23:09:47', NULL, 0),
	(89, 3, 1, 'f', '2017-10-24 23:46:15', NULL, 0),
	(90, 3, 1, 'a', '2017-10-24 23:46:17', NULL, 0),
	(91, 3, 1, 'bb', '2017-10-24 23:46:19', NULL, 0),
	(92, 7, 1, 'â\nhehr\n', '2017-10-24 23:54:51', NULL, 0),
	(93, 7, 1, 'nẻgnern\nưe4wnk4n\n', '2017-10-24 23:54:57', NULL, 0),
	(94, 5, 1, 'ựtawn<br>', '2017-10-24 23:57:03', NULL, 0),
	(95, 5, 1, 'a<br>f<br>', '2017-10-24 23:57:05', NULL, 0),
	(96, 5, 1, 'nehn<br>', '2017-10-24 23:57:07', NULL, 0),
	(97, 5, 1, 'hello<br>my name is tuan<br>', '2017-10-24 23:57:18', NULL, 0),
	(98, 5, 1, 'qq<br>', '2017-10-25 00:45:36', NULL, 0),
	(100, 5, 1, '<br>            append("<table>"<br>                    + "<tr>"<br>                    + "<td valign=top>"ue\'>" + message.getNick_nam+ "</tr>"<br>                    + "</table>");', '2017-10-25 00:46:04', NULL, 0),
	(101, 7, 1, 'a<br>v<br>va<br>v<br>va<br>v<br>v<br>', '2017-10-25 00:46:49', NULL, 0),
	(102, 7, 1, 'a<br>', '2017-10-25 00:46:56', NULL, 0),
	(103, 7, 1, 's<br>', '2017-10-25 00:46:57', NULL, 0),
	(104, 7, 1, 'c<br>', '2017-10-25 00:46:58', NULL, 0),
	(105, 8, 1, 'z<br>', '2017-10-25 00:59:51', NULL, 0),
	(106, 8, 1, 'gẹo<br>', '2017-10-25 00:59:54', NULL, 0),
	(107, 5, 2, 'a', '2017-10-25 09:22:43', NULL, 0),
	(108, 5, 2, 'x', '2017-10-25 09:22:45', NULL, 0),
	(109, 5, 1, 'z', '2017-10-25 09:23:21', NULL, 0),
	(110, 3, 1, 'a', '2017-10-25 09:24:55', NULL, 0),
	(111, 6, 1, 'z', '2017-10-25 09:31:06', NULL, 0),
	(112, 3, 1, 'abetbsk<br>', '2017-10-25 09:42:31', NULL, 0),
	(113, 3, 1, 'a<br>evemo<br>', '2017-10-25 09:42:35', NULL, 0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- Dumping structure for table chat_online.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mã người dùng',
  `username` varchar(50) NOT NULL COMMENT 'tên đăng nhập',
  `password_hash` varchar(50) NOT NULL COMMENT 'mật khẩu (lưu dưới dạng mã hóa)',
  `display_name` varchar(50) DEFAULT NULL COMMENT 'tên hiển thị',
  `gender` int(11) NOT NULL COMMENT 'giới tính',
  `avatar_path` varchar(250) DEFAULT NULL COMMENT 'ảnh đại diện',
  `email` varchar(50) DEFAULT NULL COMMENT 'địa chỉ mail',
  `phone_number` varchar(50) DEFAULT NULL COMMENT 'số điện thoại',
  `description` varchar(1000) DEFAULT NULL COMMENT 'mô tả',
  `status` int(11) DEFAULT '0' COMMENT 'trạng thái: 0 - online; 1 - away; 2 - busy; 3 - invisible; 4 - offline',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='người dùng';

-- Dumping data for table chat_online.user: ~8 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `username`, `password_hash`, `display_name`, `gender`, `avatar_path`, `email`, `phone_number`, `description`, `status`) VALUES
	(1, 'tuanpham', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Phạm Mạnh Tuấn', 0, '/avatar/2017/10/06/16/07c0501b9f0ca6de.png', 'tuanpham@gmail.com', NULL, 'tuanpham@gmail.com', 0),
	(2, 'thangbui', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Bùi Đức Thắng', 0, '/avatar/2017/10/23/16/7b4796e0e01be65a.png', 'thangbui@gmail.com', NULL, 'thangbui@gmail.com', 4),
	(3, 'hangnguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Thị Hằng', 1, NULL, 'hangnguyen@gmail.com', NULL, 'hangnguyen@gmail.com', 0),
	(4, 'quannguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Tiến Quân', 0, NULL, 'quannguyen@gmail.com', NULL, 'quannguyen@gmail.com', 0),
	(5, 'tiennguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Khắc Tiến', 0, NULL, 'tiennguyen@gmail.com', NULL, 'tiennguyen@gmail.com', 0),
	(6, 'trangnguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Thị Huyền Trang', 1, NULL, 'trangnguyen@gmail.com', NULL, 'trangnguyen@gmail.com', 0),
	(7, 'datnguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Văn Đạt', 0, NULL, 'datnguyen@gmail.com', NULL, 'datnguyen@gmail.com', 0),
	(8, 'hienpham', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Phạm Thị Thu Hiền', 1, NULL, 'hienpham@gmail.com', NULL, 'hienpham@gmail.com', 0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
