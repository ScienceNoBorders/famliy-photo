/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : ruoyi-vue3-max

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2022-07-06 17:12:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app_demo`
-- ----------------------------
DROP TABLE IF EXISTS `app_demo`;
CREATE TABLE `app_demo` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `name` varchar(50) DEFAULT NULL COMMENT '会员名称',
  `user_type` varchar(10) DEFAULT NULL COMMENT '会员级别',
  `sex` varchar(10) DEFAULT NULL COMMENT '会员性别',
  `label` varchar(100) DEFAULT NULL COMMENT '会员标签',
  `avatar` varchar(100) DEFAULT NULL COMMENT '会员勋章',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8 COMMENT='app示例';

-- ----------------------------
-- Records of app_demo
-- ----------------------------
