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
DROP DATABASE IF EXISTS `chat_online`;
CREATE DATABASE IF NOT EXISTS `chat_online` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `chat_online`;

-- Dumping structure for table chat_online.conversation
DROP TABLE IF EXISTS `conversation`;
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
DROP TABLE IF EXISTS `friendship`;
CREATE TABLE IF NOT EXISTS `friendship` (
  `sender_id` int(11) NOT NULL COMMENT 'mã người gửi',
  `receiver_id` int(11) NOT NULL COMMENT 'mã người nhận',
  `confirm` int(11) DEFAULT NULL COMMENT 'xác nhận: 0 - chưa đồng ý; 1 - đồng ý; 2 - chặn',
  PRIMARY KEY (`sender_id`,`receiver_id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `FK_friend_ship_user` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_friend_ship_user_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='mối quan hệ';

-- Dumping data for table chat_online.friendship: ~6 rows (approximately)
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
REPLACE INTO `friendship` (`sender_id`, `receiver_id`, `confirm`) VALUES
	(1, 2, 1),
	(1, 6, 1),
	(1, 7, 1),
	(3, 1, 1),
	(4, 1, 1),
	(5, 1, 1);
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;

-- Dumping structure for table chat_online.group
DROP TABLE IF EXISTS `group`;
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
	(5, 1, 'tuanpham', 0),
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
DROP TABLE IF EXISTS `message`;
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='tin nhắn';

-- Dumping data for table chat_online.message: ~2 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
REPLACE INTO `message` (`id`, `conversation_id`, `user_id`, `content`, `created_at`, `updated_at`, `is_deleted`) VALUES
	(1, 3, 1, '123', '2017-10-23 22:27:32', NULL, 0),
	(2, 3, 1, 'qưe', '2017-10-23 22:28:03', NULL, 0),
	(3, 3, 1, 'a', '2017-10-23 22:28:08', NULL, 0),
	(4, 5, 1, 'qưe', '2017-10-23 22:30:23', NULL, 0),
	(5, 5, 1, 'q', '2017-10-23 22:30:24', NULL, 0),
	(6, 5, 1, 'aa', '2017-10-23 22:30:26', NULL, 0),
	(7, 5, 1, 'vsvsvwev', '2017-10-23 22:30:27', NULL, 0),
	(8, 5, 1, 'a', '2017-10-23 22:31:12', NULL, 0),
	(9, 5, 1, 'a', '2017-10-23 22:31:13', NULL, 0),
	(10, 5, 1, 'a', '2017-10-23 22:31:13', NULL, 0),
	(11, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(12, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(13, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(14, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(15, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(16, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(17, 5, 1, 'a', '2017-10-23 22:31:14', NULL, 0),
	(18, 5, 1, 'a', '2017-10-23 22:31:15', NULL, 0),
	(19, 5, 1, 'a', '2017-10-23 22:31:15', NULL, 0),
	(20, 5, 1, 'a', '2017-10-23 22:31:15', NULL, 0),
	(21, 5, 1, 'aa', '2017-10-23 22:31:15', NULL, 0),
	(22, 5, 1, 'a', '2017-10-23 22:31:16', NULL, 0),
	(23, 5, 1, 'â', '2017-10-23 22:31:16', NULL, 0),
	(24, 5, 1, 'a', '2017-10-23 22:31:16', NULL, 0),
	(25, 5, 1, 'a', '2017-10-23 22:31:16', NULL, 0),
	(26, 5, 1, 'a', '2017-10-23 22:31:16', NULL, 0),
	(27, 5, 1, 'âa', '2017-10-23 22:31:17', NULL, 0),
	(28, 5, 1, 'a', '2017-10-23 22:31:17', NULL, 0),
	(29, 5, 1, 'â', '2017-10-23 22:31:17', NULL, 0),
	(30, 5, 1, 'x', '2017-10-23 22:31:17', NULL, 0),
	(31, 5, 1, 'xa', '2017-10-23 22:31:17', NULL, 0),
	(32, 5, 1, 'x', '2017-10-23 22:31:18', NULL, 0),
	(33, 5, 1, 'a', '2017-10-23 22:31:18', NULL, 0),
	(34, 5, 1, 'x', '2017-10-23 22:31:19', NULL, 0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- Dumping structure for table chat_online.user
DROP TABLE IF EXISTS `user`;
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
	(2, 'thangbui', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Bùi Đức Thắng', 0, '/avatar/2017/10/23/16/7b4796e0e01be65a.png', 'thangbui@gmail.com', NULL, 'thangbui@gmail.com', 1),
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
