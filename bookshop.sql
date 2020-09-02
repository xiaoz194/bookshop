/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : bookshop

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 15/03/2020 21:14:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dsc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` int(11) NULL DEFAULT NULL,
  `cid` tinyint(4) NULL DEFAULT NULL,
  `collected` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '朱自清散文集', '朱自清', '朱自清的唯美散文集', '/imgs/zzqswj.jpg', 150, 1, 0);
INSERT INTO `book` VALUES (2, '斗破苍穹', '唐家三少', '玄幻小说，讲述萧炎的成长之路', '/imgs/dpcq.jpg', 120, 1, 0);
INSERT INTO `book` VALUES (7, '哈利波特', 'jk洛林', '讲述魔法世界的故事', '/imgs/hlbt.jpg', 1, 1, 0);
INSERT INTO `book` VALUES (8, '魔法禁书目录', '陆尘', '魔法禁书目录', '/imgs/b.jpg', 100, 6, 0);
INSERT INTO `book` VALUES (9, '网游之天下无双', '失落叶', '发生在苏州的都市爱情故事', '/imgs/123.jpg', 80, 1, 0);
INSERT INTO `book` VALUES (10, '我的朋友很少', '雨城', '讲述校园青春故事，日本超人气轻小说', '/imgs/222.jpg', 120, 1, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '散文');
INSERT INTO `category` VALUES (2, '历史');
INSERT INTO `category` VALUES (3, '古典名著');
INSERT INTO `category` VALUES (4, '国外名著');
INSERT INTO `category` VALUES (5, '都市');
INSERT INTO `category` VALUES (6, '玄幻');
INSERT INTO `category` VALUES (7, '轻小说');
INSERT INTO `category` VALUES (8, '测试2');
INSERT INTO `category` VALUES (9, '测试3');
INSERT INTO `category` VALUES (14, '言情');

-- ----------------------------
-- Table structure for ordr
-- ----------------------------
DROP TABLE IF EXISTS `ordr`;
CREATE TABLE `ordr`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `bid` tinyint(4) NULL DEFAULT NULL,
  `uid` tinyint(4) NULL DEFAULT NULL,
  `time` datetime NULL DEFAULT NULL,
  `count` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `flag` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ordr
-- ----------------------------
INSERT INTO `ordr` VALUES (1, 1, 1, '2020-03-13 02:48:46', '1', 1);
INSERT INTO `ordr` VALUES (3, 1, 1, '2020-03-13 13:11:07', '1', 0);
INSERT INTO `ordr` VALUES (9, 1, 3, '2020-03-15 01:46:53', '1', 1);
INSERT INTO `ordr` VALUES (12, 1, 1, '2020-03-15 02:18:51', '1', 0);
INSERT INTO `ordr` VALUES (13, 1, 1, '2020-03-15 02:19:50', '1', 0);
INSERT INTO `ordr` VALUES (14, 1, 1, '2020-03-15 02:20:16', '1', 0);
INSERT INTO `ordr` VALUES (15, 1, 1, '2020-03-15 02:22:32', '1', 0);
INSERT INTO `ordr` VALUES (16, 1, 1, '2020-03-15 02:24:50', '1', 0);
INSERT INTO `ordr` VALUES (17, 2, 3, '2020-03-15 02:34:36', '1', 1);
INSERT INTO `ordr` VALUES (18, 7, 3, '2020-03-15 02:35:38', '1', 0);
INSERT INTO `ordr` VALUES (19, 8, 3, '2020-03-15 02:38:28', '1', 0);
INSERT INTO `ordr` VALUES (21, 7, 1, '2020-03-15 03:52:19', '1', 0);
INSERT INTO `ordr` VALUES (22, 9, 3, '2020-03-15 05:16:42', '2', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tell` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bid` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhangsan', '123123', '重庆市南岸区', '13355629021', NULL);
INSERT INTO `user` VALUES (2, 'lisi', '123123', '重庆市渝北区', '13999202020', NULL);
INSERT INTO `user` VALUES (3, 'hanser', '123123', '成都市武侯区', '13598289313', NULL);
INSERT INTO `user` VALUES (4, 'lingyuan', '123123', '南京市秦淮区', '15883908673', NULL);
INSERT INTO `user` VALUES (5, 'jack', '123456', '杭州市西湖区', '15987675389', NULL);
INSERT INTO `user` VALUES (6, 'yhs', '123123', '宁波市xx区', '13856299809', NULL);
INSERT INTO `user` VALUES (7, 'mark', '123123', '苏州市姑苏区', '13955629970', NULL);
INSERT INTO `user` VALUES (8, 'kd', '123123', '重庆市江北区', '15789795389', NULL);
INSERT INTO `user` VALUES (9, 'qss', '123123', '重庆市江北区', '18867571231', NULL);
INSERT INTO `user` VALUES (10, 'xy', '123123', '南京市玄武区', '13378679980', NULL);
INSERT INTO `user` VALUES (11, 'cty', '123123', '南京市玄武区', '13288907790', NULL);
INSERT INTO `user` VALUES (12, 'ceshi', 'ceshi', 'ceshi ', '1111111', NULL);
INSERT INTO `user` VALUES (13, 'ad', '11', '南京市雨花区', '13322990021', NULL);

SET FOREIGN_KEY_CHECKS = 1;
