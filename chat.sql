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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='lịch sử hội thoại';

-- Dumping data for table chat_online.conversation: ~13 rows (approximately)
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
REPLACE INTO `conversation` (`id`, `name`, `avatar_path`, `description`, `frame_color`, `main_icon`, `created_at`) VALUES
	(1, NULL, NULL, '1 2', NULL, NULL, '2017-10-30 16:18:15'),
	(2, NULL, NULL, '1 3', NULL, NULL, '2017-11-08 10:37:09'),
	(3, NULL, NULL, '1 7', NULL, NULL, '2017-11-08 13:02:18'),
	(4, NULL, NULL, '1 5', NULL, NULL, '2017-11-08 13:03:13'),
	(5, NULL, NULL, '1 8', NULL, NULL, '2017-11-08 13:03:21'),
	(7, 'Bài tập lớn lập trình mạng', NULL, NULL, NULL, NULL, '2017-11-08 23:50:14'),
	(8, 'Pro', NULL, NULL, NULL, NULL, '2017-11-09 03:01:12'),
	(9, NULL, NULL, '2 3', NULL, NULL, '2017-11-17 16:53:26'),
	(10, NULL, NULL, '2 5', NULL, NULL, '2017-11-17 16:55:10'),
	(11, NULL, NULL, '2 7', NULL, NULL, '2017-11-17 16:55:48'),
	(12, NULL, NULL, '3 8', NULL, NULL, '2017-11-17 16:56:22'),
	(13, NULL, NULL, '3 6', NULL, NULL, '2017-11-17 16:56:49'),
	(14, NULL, NULL, '4 5', NULL, NULL, '2017-11-24 10:21:41');
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;

-- Dumping structure for table chat_online.friendship
DROP TABLE IF EXISTS `friendship`;
CREATE TABLE IF NOT EXISTS `friendship` (
  `sender_id` int(11) NOT NULL COMMENT 'mã người gửi',
  `receiver_id` int(11) NOT NULL COMMENT 'mã người nhận',
  `confirm` int(11) DEFAULT '0' COMMENT 'xác nhận: 0 - chưa đồng ý; 1 - đồng ý;',
  `ignore` int(11) DEFAULT '0' COMMENT 'bỏ qua',
  PRIMARY KEY (`sender_id`,`receiver_id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='mối quan hệ';

-- Dumping data for table chat_online.friendship: ~14 rows (approximately)
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
REPLACE INTO `friendship` (`sender_id`, `receiver_id`, `confirm`, `ignore`) VALUES
	(1, 3, 1, 0),
	(1, 7, 1, 0),
	(2, 1, 1, 0),
	(2, 3, 1, 0),
	(2, 5, 1, 0),
	(2, 7, 1, 0),
	(3, 6, 1, 0),
	(3, 8, 1, 0),
	(5, 1, 1, 0),
	(5, 3, 0, 0),
	(5, 4, 1, 0),
	(8, 1, 1, 0),
	(8, 2, 0, 0),
	(9, 1, 0, 0);
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
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='liên hệ cuộc hội thoại - người dùng (dùng cho cả chat giữa 2 hay nhiều người)';

-- Dumping data for table chat_online.group: ~29 rows (approximately)
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
REPLACE INTO `group` (`conversation_id`, `user_id`, `nick_name`, `is_participant`) VALUES
	(1, 1, 'Phạm Mạnh Tuấn', 0),
	(1, 2, 'Bùi Đức Thắng', 0),
	(2, 1, 'Phạm Mạnh Tuấn', 0),
	(2, 3, 'Nguyễn Thị Hằng', 0),
	(3, 1, 'Phạm Mạnh Tuấn', 0),
	(3, 7, 'Nguyễn Văn Đạt', 0),
	(4, 1, 'Phạm Mạnh Tuấn', 0),
	(4, 5, 'Nguyễn Khắc Tiến', 0),
	(5, 1, 'Phạm Mạnh Tuấn', 0),
	(5, 8, 'Phạm Thị Thu Hiền', 0),
	(7, 1, 'Phạm Mạnh Tuấn', 0),
	(7, 2, 'Bùi Đức Thắng', 0),
	(7, 3, 'Nguyễn Thị Hằng', 0),
	(8, 1, 'Phạm Mạnh Tuấn', 0),
	(8, 2, 'Bùi Đức Thắng', 0),
	(8, 5, 'Nguyễn Khắc Tiến', 0),
	(8, 7, 'Nguyễn Văn Đạt', 0),
	(9, 2, 'Bùi Đức Thắng', 0),
	(9, 3, 'Nguyễn Thị Hằng', 0),
	(10, 2, 'Bùi Đức Thắng', 0),
	(10, 5, 'Nguyễn Khắc Tiến', 0),
	(11, 2, 'Bùi Đức Thắng', 0),
	(11, 7, 'Nguyễn Văn Đạt', 0),
	(12, 3, 'Nguyễn Thị Hằng', 0),
	(12, 8, 'Phạm Thị Thu Hiền', 0),
	(13, 3, 'Nguyễn Thị Hằng', 0),
	(13, 6, 'Nguyễn Thị Huyền Trang', 0),
	(14, 4, 'Nguyễn Tiến Quân', 0),
	(14, 5, 'Nguyễn Khắc Tiến', 0);
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
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='tin nhắn';

-- Dumping data for table chat_online.message: ~3 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
REPLACE INTO `message` (`id`, `conversation_id`, `user_id`, `content`, `created_at`, `updated_at`, `is_deleted`) VALUES
	(35, 7, 1, 'jbkl<br>', '2017-11-24 16:41:39', NULL, 0),
	(36, 1, 2, 'adas<br>', '2017-11-24 16:41:46', NULL, 0),
	(37, 1, 1, 'drgdrg<br>', '2017-11-24 16:41:48', NULL, 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='người dùng';

-- Dumping data for table chat_online.user: ~9 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `username`, `password_hash`, `display_name`, `gender`, `avatar_path`, `email`, `phone_number`, `description`, `status`) VALUES
	(1, 'tuanpham', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Phạm Mạnh Tuấn', 0, '/avatar/2017/11/17/16/bb73044a99ad0b64.png', 'tuanpham@gmail.com', NULL, 'beoiarreai', 4),
	(2, 'thangbui', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Bùi Đức Thắng', 0, '/avatar/2017/11/17/16/c09a2be66e8158f0.png', 'thangbui@gmail.com', NULL, 'kffafn', 0),
	(3, 'hangnguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Thị Hằng', 1, '/avatar/2017/11/17/17/b3d14a8d374a75fd.png', 'hangnguyen@gmail.com', NULL, 'hello', 0),
	(4, 'quannguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Tiến Quân', 0, NULL, 'quannguyen@gmail.com', NULL, 'aaaaaaa', 4),
	(5, 'tiennguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Khắc Tiến', 0, '/avatar/2017/11/17/17/c18072d01feab715.png', 'tiennguyen@gmail.com', NULL, 'alo 123...', 0),
	(6, 'trangnguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Thị Huyền Trang', 1, NULL, 'trangnguyen@gmail.com', NULL, 'bbbbbbbbbb', 4),
	(7, 'datnguyen', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Nguyễn Văn Đạt', 0, NULL, 'datnguyen@gmail.com', NULL, 'cccccccccc', 4),
	(8, 'hienpham', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Phạm Thị Thu Hiền', 1, NULL, 'hienpham@gmail.com', NULL, 'ddddddd', 4),
	(9, '123456', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'ád', 0, '/avatar/2017/11/24/16/bdb5b55900088bb6.png', '123456@gmail.com', '', 'segseg', 4);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
