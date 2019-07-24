/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : hqyjbookmanager

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-07-24 19:05:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `isbn` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `price` double DEFAULT NULL,
  `lended_count` int(11) DEFAULT '0',
  `lended` tinyint(4) DEFAULT '0',
  `author` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `publisher` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `pub_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('3', 'Java', '996-995-779-999', '45', '1', '0', '王权海', '邮电', '2011-02-22');
INSERT INTO `t_book` VALUES ('4', 'C#', '995-114-222-1545', '32.2', '1', '0', '哈韩', '邮电', '2015-02-22');
INSERT INTO `t_book` VALUES ('5', 'Unity游戏开发', '122-999-777-656', '150', '1', '0', 'Unity中国', 'Unity', '2011-11-22');
INSERT INTO `t_book` VALUES ('6', 'C++', '159-88-7776-256', '12', '0', '0', '李军', '中国', '2000-10-21');

-- ----------------------------
-- Table structure for t_lend
-- ----------------------------
DROP TABLE IF EXISTS `t_lend`;
CREATE TABLE `t_lend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_uid` int(11) DEFAULT NULL,
  `fk_bid` int(11) DEFAULT NULL,
  `lend_date` date DEFAULT NULL,
  `back_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of t_lend
-- ----------------------------
INSERT INTO `t_lend` VALUES ('1', '7', '3', '2019-07-24', '2019-07-24');
INSERT INTO `t_lend` VALUES ('2', '7', '4', '2019-07-24', '2019-07-24');
INSERT INTO `t_lend` VALUES ('3', '7', '5', '2019-07-24', '2019-07-24');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `register_date` date DEFAULT NULL,
  `normal` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('7', '谭宇', '123456', '男', '13072889556', '2019-07-23', '1');
INSERT INTO `t_user` VALUES ('8', '谭宇2', '123', '女', '15982282255', '2019-07-24', '1');
