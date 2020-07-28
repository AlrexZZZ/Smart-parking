/*
Navicat MySQL Data Transfer

Source Server         : localTest
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : wx_test

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2016-12-30 19:12:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cbd_wcc_proprietor`
-- ----------------------------
DROP TABLE IF EXISTS `cbd_wcc_proprietor`;
CREATE TABLE `cbd_wcc_proprietor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `certification_time` datetime DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `doorplate` varchar(300) DEFAULT NULL,
  `friend_pk` varchar(50) DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `is_ensured` varchar(50) DEFAULT NULL,
  `message_code` varchar(100) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `reserved_field1` varchar(300) DEFAULT NULL,
  `reserved_field2` varchar(300) DEFAULT NULL,
  `tempdoorplate` varchar(300) DEFAULT NULL,
  `tempname` varchar(200) DEFAULT NULL,
  `tempphone` varchar(50) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  `tempappartment` bigint(20) DEFAULT NULL,
  `wcc_friend` bigint(20) DEFAULT NULL,
  `wccappartment` bigint(20) DEFAULT NULL,
  `wccplatformuser` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tc0fr66th97j2aiw3p65e7q00` (`company`),
  KEY `FK_rlxva2de7tirab47asykq1yuk` (`organization`),
  KEY `FK_b6w0i8p6q6iorpig1emr1fdsb` (`tempappartment`),
  KEY `FK_lrpn617ffafsmvmq6772t36k6` (`wcc_friend`),
  KEY `FK_8nb4trhfqljsasah4r0wdl2b2` (`wccappartment`),
  KEY `FK_jrsvj5ipjpag02bk49ijcj9a5` (`wccplatformuser`),
  CONSTRAINT `cbd_wcc_proprietor_ibfk_1` FOREIGN KEY (`wccappartment`) REFERENCES `wcc_appartment` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_ibfk_2` FOREIGN KEY (`tempappartment`) REFERENCES `wcc_appartment` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_ibfk_3` FOREIGN KEY (`wccplatformuser`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_ibfk_4` FOREIGN KEY (`wcc_friend`) REFERENCES `wcc_friend` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_ibfk_5` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_ibfk_6` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cbd_wcc_proprietor
-- ----------------------------

-- ----------------------------
-- Table structure for `cbd_wcc_proprietor_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `cbd_wcc_proprietor_platform_users`;
CREATE TABLE `cbd_wcc_proprietor_platform_users` (
  `cbd_wcc_proprietor` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`cbd_wcc_proprietor`,`platform_users`),
  KEY `FK_5dw6yaoajwbs48vg1hp2b99ev` (`platform_users`),
  KEY `FK_d4u0qoskk8phd4a18r18ibncj` (`cbd_wcc_proprietor`),
  CONSTRAINT `cbd_wcc_proprietor_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_platform_users_ibfk_2` FOREIGN KEY (`cbd_wcc_proprietor`) REFERENCES `cbd_wcc_proprietor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cbd_wcc_proprietor_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `cbd_wcc_proprietor_wcc_fees`
-- ----------------------------
DROP TABLE IF EXISTS `cbd_wcc_proprietor_wcc_fees`;
CREATE TABLE `cbd_wcc_proprietor_wcc_fees` (
  `cbd_wcc_proprietor` bigint(20) NOT NULL,
  `wcc_fees` bigint(20) NOT NULL,
  PRIMARY KEY (`cbd_wcc_proprietor`,`wcc_fees`),
  UNIQUE KEY `UK_ku8tick75x54wihufvxn4dfjx` (`wcc_fees`),
  KEY `FK_ku8tick75x54wihufvxn4dfjx` (`wcc_fees`),
  KEY `FK_mti49ina1c1shlk07qqwkpaa1` (`cbd_wcc_proprietor`),
  CONSTRAINT `cbd_wcc_proprietor_wcc_fees_ibfk_1` FOREIGN KEY (`wcc_fees`) REFERENCES `wcc_fees` (`id`),
  CONSTRAINT `cbd_wcc_proprietor_wcc_fees_ibfk_2` FOREIGN KEY (`cbd_wcc_proprietor`) REFERENCES `cbd_wcc_proprietor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cbd_wcc_proprietor_wcc_fees
-- ----------------------------

-- ----------------------------
-- Table structure for `comment_trend`
-- ----------------------------
DROP TABLE IF EXISTS `comment_trend`;
CREATE TABLE `comment_trend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_num` bigint(20) NOT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_type` varchar(255) DEFAULT NULL,
  `comment_type_percent` double NOT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment_trend
-- ----------------------------

-- ----------------------------
-- Table structure for `copy_of_score`
-- ----------------------------
DROP TABLE IF EXISTS `copy_of_score`;
CREATE TABLE `copy_of_score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `stu_no_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of copy_of_score
-- ----------------------------

-- ----------------------------
-- Table structure for `hot_words_detail`
-- ----------------------------
DROP TABLE IF EXISTS `hot_words_detail`;
CREATE TABLE `hot_words_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_id` bigint(20) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `hot_words_name` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hot_words_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `label_ratio_report`
-- ----------------------------
DROP TABLE IF EXISTS `label_ratio_report`;
CREATE TABLE `label_ratio_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `big_brands` varchar(255) DEFAULT NULL,
  `brand_nname` varchar(255) DEFAULT NULL,
  `cheap` varchar(255) DEFAULT NULL,
  `comment_types` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `fast_goods` varchar(255) DEFAULT NULL,
  `goods_package` varchar(255) DEFAULT NULL,
  `is_good` varchar(255) DEFAULT NULL,
  `label_date` datetime DEFAULT NULL,
  `label_time` datetime DEFAULT NULL,
  `platform_name` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of label_ratio_report
-- ----------------------------

-- ----------------------------
-- Table structure for `operator_user`
-- ----------------------------
DROP TABLE IF EXISTS `operator_user`;
CREATE TABLE `operator_user` (
  `pk` varchar(32) NOT NULL,
  `companypk` varchar(255) DEFAULT NULL,
  `deleteip` varchar(255) DEFAULT NULL,
  `deletepk` varchar(255) DEFAULT NULL,
  `deletetime` datetime DEFAULT NULL,
  `insertip` varchar(255) DEFAULT NULL,
  `insertpk` varchar(255) DEFAULT NULL,
  `inserttime` datetime DEFAULT NULL,
  `isdelete` int(11) DEFAULT NULL,
  `updateip` varchar(255) DEFAULT NULL,
  `updatepk` varchar(255) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `active` int(11) DEFAULT NULL,
  `autoallocate` int(11) DEFAULT NULL,
  `creationdate` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `encryptedpassword` varchar(255) DEFAULT NULL,
  `lastlogouttime` datetime DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `maxchat` int(11) DEFAULT NULL,
  `modificationdate` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `onlinetime` int(11) DEFAULT NULL,
  `orgpk` varchar(255) DEFAULT NULL,
  `plainpassword` varchar(255) DEFAULT NULL,
  `rolepk` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `userstar` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator_user
-- ----------------------------

-- ----------------------------
-- Table structure for `product_problem_rand_list`
-- ----------------------------
DROP TABLE IF EXISTS `product_problem_rand_list`;
CREATE TABLE `product_problem_rand_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_tag_name` varchar(255) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_type` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `increase_count` bigint(20) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_problem_rand_list
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_operator`
-- ----------------------------
DROP TABLE IF EXISTS `pub_operator`;
CREATE TABLE `pub_operator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `auto_allocate` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `max_auto` int(11) DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `operator_name` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `ump_channl_id` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `voc_account_id` bigint(20) DEFAULT NULL,
  `voc_brand_id` bigint(20) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  `pub_role` bigint(20) DEFAULT NULL,
  `platform_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f4fl7p55eop0kicbfdkkwdnd3` (`company`) USING BTREE,
  KEY `FK_88frecsph45dw7bo5ojr89972` (`organization`) USING BTREE,
  KEY `FK_k8by8j9ccxm02jhdc9tqbwwwx` (`pub_role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of pub_operator
-- ----------------------------
INSERT INTO `pub_operator` VALUES ('1', 'admin', '1', '0', '2015-04-24 16:22:25', '405086981@qq.com', '1', null, '18817713998', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '2', null, null, null);
INSERT INTO `pub_operator` VALUES ('2', 'admin', '1', '0', '2015-04-24 16:31:15', '641818239@qq.com', '1', null, '18217656807', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '3', null, null, null);
INSERT INTO `pub_operator` VALUES ('3', 'admin', '1', '0', '2015-05-07 09:30:52', 'zhzoli1234@126.com', '1', null, '13918045596', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '4', null, null, null);
INSERT INTO `pub_operator` VALUES ('4', 'admin', '1', '0', '2015-05-11 16:50:17', '88569210@qq.com', '1', null, '13764559476', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '5', null, null, null);
INSERT INTO `pub_operator` VALUES ('5', 'kelly', '1', null, '2015-05-20 11:15:28', 'kelly.li@9client.com', '1', null, '13764559476', 'kelly', 'e10adc3949ba59abbe56e057f20f883e', null, null, '1', null, null, '4', '6', '2', null);
INSERT INTO `pub_operator` VALUES ('6', 'admin', '1', '0', '2015-06-09 10:43:13', '88569210@qq.com', '1', null, '13764559476', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '6', null, null, null);
INSERT INTO `pub_operator` VALUES ('7', 'admin', '1', '0', '2015-06-09 10:54:37', 'kelly.li@9client.com', '1', null, '13764559476', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '1', null, null, '8', null, null, null);
INSERT INTO `pub_operator` VALUES ('8', 'kelly', '1', null, '2015-06-09 11:17:38', '88569210@qq.com', '1', null, '13764559476', 'kelly', 'e10adc3949ba59abbe56e057f20f883e', null, null, '15', null, null, '8', '8', '3', '0');
INSERT INTO `pub_operator` VALUES ('9', '001', '1', null, '2015-06-11 16:50:26', '88569210@qq.com', '1', null, '13764559476', '001', 'e10adc3949ba59abbe56e057f20f883e', null, null, '5', null, null, '8', '7', '3', '0');
INSERT INTO `pub_operator` VALUES ('10', '002', '1', null, '2015-06-11 16:54:26', '88569210@qq.com', '1', null, '13764559476', '002', 'e10adc3949ba59abbe56e057f20f883e', null, null, '5', null, null, '8', '7', '3', '0');
INSERT INTO `pub_operator` VALUES ('12', 'admin', '1', '0', '2015-06-18 14:15:19', '983313911@qq.com', '1', null, '18817713998', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '11', null, null, null);
INSERT INTO `pub_operator` VALUES ('13', 'susie', '1', null, '2015-06-29 15:21:18', '360198016@qq.com', '1', null, '13012875810', 'susie', 'e10adc3949ba59abbe56e057f20f883e', null, null, '17', null, null, '8', '8', '3', '1');
INSERT INTO `pub_operator` VALUES ('17', 'admin', '1', '0', '2015-10-09 15:02:19', 'jade.lin@9client.com', '1', null, '13380888990', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '16', null, null, null);
INSERT INTO `pub_operator` VALUES ('18', 'admin', '1', '0', '2016-04-28 10:58:21', 'tailong@163.com', '1', null, '18817781992', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '17', null, null, null);
INSERT INTO `pub_operator` VALUES ('19', 'cheally6', '1', null, '2016-10-28 22:01:01', '40508323@qq.com', '1', null, '18817713990', 'ff', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '17', '16', '4', null);
INSERT INTO `pub_operator` VALUES ('20', 'cheally', '1', null, '2016-10-28 22:02:56', '40508323@qq.com', '1', null, '18817713990', 'cheally', 'e10adc3949ba59abbe56e057f20f883e', null, null, '0', null, null, '17', '16', '5', null);

-- ----------------------------
-- Table structure for `pub_operator_channels`
-- ----------------------------
DROP TABLE IF EXISTS `pub_operator_channels`;
CREATE TABLE `pub_operator_channels` (
  `pub_operator` bigint(20) NOT NULL,
  `channels` bigint(20) NOT NULL,
  PRIMARY KEY (`pub_operator`,`channels`),
  KEY `FK_dj4kpyigmvv8xoucdhts9nf5f` (`channels`),
  KEY `FK_q75jattlxodi93jgfhcs7a831` (`pub_operator`),
  CONSTRAINT `pub_operator_channels_ibfk_1` FOREIGN KEY (`channels`) REFERENCES `ump_channel` (`id`),
  CONSTRAINT `pub_operator_channels_ibfk_2` FOREIGN KEY (`pub_operator`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_operator_channels
-- ----------------------------
INSERT INTO `pub_operator_channels` VALUES ('8', '3');

-- ----------------------------
-- Table structure for `pub_operator_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `pub_operator_platform_users`;
CREATE TABLE `pub_operator_platform_users` (
  `pub_operator` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  `pub_operators` bigint(20) NOT NULL,
  PRIMARY KEY (`pub_operator`,`platform_users`),
  KEY `FK_f99h6hh50x028cml0x4ltcwdy` (`platform_users`),
  KEY `FK_t7duop0uufbms612c8jtw4hp3` (`pub_operator`),
  KEY `FK_bl01s1w3ljkkw643938br9nja` (`pub_operators`),
  CONSTRAINT `pub_operator_platform_users_ibfk_1` FOREIGN KEY (`pub_operators`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `pub_operator_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `pub_operator_platform_users_ibfk_3` FOREIGN KEY (`pub_operator`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_operator_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_operator_voc_accounts`
-- ----------------------------
DROP TABLE IF EXISTS `pub_operator_voc_accounts`;
CREATE TABLE `pub_operator_voc_accounts` (
  `pub_operator` bigint(20) NOT NULL,
  `voc_accounts` bigint(20) NOT NULL,
  PRIMARY KEY (`pub_operator`,`voc_accounts`),
  KEY `FK_h2yc8fqli5fjb11clg30fp8hu` (`voc_accounts`),
  KEY `FK_1dbo6d6poovhh0gq1t0y813vt` (`pub_operator`),
  CONSTRAINT `pub_operator_voc_accounts_ibfk_1` FOREIGN KEY (`pub_operator`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `pub_operator_voc_accounts_ibfk_2` FOREIGN KEY (`voc_accounts`) REFERENCES `voc_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_operator_voc_accounts
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_operator_voc_brands`
-- ----------------------------
DROP TABLE IF EXISTS `pub_operator_voc_brands`;
CREATE TABLE `pub_operator_voc_brands` (
  `pub_operator` bigint(20) NOT NULL,
  `voc_brands` bigint(20) NOT NULL,
  PRIMARY KEY (`pub_operator`,`voc_brands`),
  KEY `FK_85r5iikly9t44yk799a1qb23i` (`voc_brands`),
  KEY `FK_tmb3uo4bnxrms39nut0iu7ll6` (`pub_operator`),
  CONSTRAINT `pub_operator_voc_brands_ibfk_1` FOREIGN KEY (`voc_brands`) REFERENCES `voc_brand` (`id`),
  CONSTRAINT `pub_operator_voc_brands_ibfk_2` FOREIGN KEY (`pub_operator`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_operator_voc_brands
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_operator_voc_shops`
-- ----------------------------
DROP TABLE IF EXISTS `pub_operator_voc_shops`;
CREATE TABLE `pub_operator_voc_shops` (
  `pub_operator` bigint(20) NOT NULL,
  `voc_shops` bigint(20) NOT NULL,
  PRIMARY KEY (`pub_operator`,`voc_shops`),
  KEY `FK_6sonlsrwv977nray8xvy0fdej` (`voc_shops`),
  KEY `FK_6blr2s9ryncq9ca6cp6pyakmt` (`pub_operator`),
  CONSTRAINT `pub_operator_voc_shops_ibfk_1` FOREIGN KEY (`pub_operator`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `pub_operator_voc_shops_ibfk_2` FOREIGN KEY (`voc_shops`) REFERENCES `voc_shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_operator_voc_shops
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_organization`
-- ----------------------------
DROP TABLE IF EXISTS `pub_organization`;
CREATE TABLE `pub_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_organization
-- ----------------------------
INSERT INTO `pub_organization` VALUES ('1', '2', '2015-04-24 16:37:05', '1', '1', '广东交行', '0', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('2', '2', '2015-04-24 16:37:13', '1', '1', '销售部', '1', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('3', '2', '2015-04-24 16:37:21', '1', '1', '研发部', '1', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('4', '3', '2015-04-27 14:11:11', '1', '1', '广州交行', '0', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('5', '4', '2015-05-12 16:39:09', '1', '1', '交行社区微信项目部', '0', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('6', '4', '2015-05-18 11:39:12', '1', '1', '交行项目', '5', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('7', '8', '2015-06-09 11:16:07', '1', '1', 'qq群', '0', null, '0', '1');
INSERT INTO `pub_organization` VALUES ('8', '8', '2016-02-29 11:26:28', '1', '1', '总部', '33', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('9', '8', '2016-02-29 11:27:05', '1', '1', '华东大区', '8', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('10', '8', '2016-02-29 11:27:14', '1', '1', '西南大区', '8', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('12', '8', '2016-03-12 15:56:22', '1', '1', 'hello', '0', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('14', '8', '2016-03-12 16:03:33', '1', '1', '我', '7', null, '0', '1');
INSERT INTO `pub_organization` VALUES ('15', '8', '2016-03-14 10:54:23', '1', '1', '1', '14', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('16', '17', '2016-05-11 11:10:33', '1', '1', '徐家汇', '157', null, '0', '1');

-- ----------------------------
-- Table structure for `pub_role`
-- ----------------------------
DROP TABLE IF EXISTS `pub_role`;
CREATE TABLE `pub_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_role
-- ----------------------------
INSERT INTO `pub_role` VALUES ('1', '4', '2015-05-12 16:51:06', '1', '1', null, '物管小组', '0');
INSERT INTO `pub_role` VALUES ('2', '4', '2015-05-14 10:18:21', '1', '1', null, '管理员组', '4');
INSERT INTO `pub_role` VALUES ('3', '8', '2015-06-09 11:16:30', '1', '1', null, '管理员', '7');
INSERT INTO `pub_role` VALUES ('4', '17', '2016-09-03 13:13:08', '1', '1', null, 'info', '0');
INSERT INTO `pub_role` VALUES ('5', '17', '2016-10-28 22:02:28', '1', '1', null, 'cvxvdv', '0');

-- ----------------------------
-- Table structure for `pub_role_authoritys`
-- ----------------------------
DROP TABLE IF EXISTS `pub_role_authoritys`;
CREATE TABLE `pub_role_authoritys` (
  `pub_roles` bigint(20) NOT NULL,
  `authoritys` bigint(20) NOT NULL,
  KEY `FK_q9n0ydai3yrb734mepgh7ri39` (`authoritys`),
  KEY `FK_2pgbtk9qkwv8smp1a29a3pb18` (`pub_roles`),
  CONSTRAINT `pub_role_authoritys_ibfk_1` FOREIGN KEY (`pub_roles`) REFERENCES `pub_role` (`id`),
  CONSTRAINT `pub_role_authoritys_ibfk_2` FOREIGN KEY (`authoritys`) REFERENCES `ump_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_role_authoritys
-- ----------------------------
INSERT INTO `pub_role_authoritys` VALUES ('3', '0');
INSERT INTO `pub_role_authoritys` VALUES ('3', '25');
INSERT INTO `pub_role_authoritys` VALUES ('3', '26');
INSERT INTO `pub_role_authoritys` VALUES ('3', '27');
INSERT INTO `pub_role_authoritys` VALUES ('3', '29');
INSERT INTO `pub_role_authoritys` VALUES ('3', '76');
INSERT INTO `pub_role_authoritys` VALUES ('3', '156');
INSERT INTO `pub_role_authoritys` VALUES ('3', '30');
INSERT INTO `pub_role_authoritys` VALUES ('3', '155');
INSERT INTO `pub_role_authoritys` VALUES ('3', '36');
INSERT INTO `pub_role_authoritys` VALUES ('3', '37');
INSERT INTO `pub_role_authoritys` VALUES ('3', '38');
INSERT INTO `pub_role_authoritys` VALUES ('3', '41');
INSERT INTO `pub_role_authoritys` VALUES ('3', '42');
INSERT INTO `pub_role_authoritys` VALUES ('3', '43');
INSERT INTO `pub_role_authoritys` VALUES ('3', '154');
INSERT INTO `pub_role_authoritys` VALUES ('3', '80');
INSERT INTO `pub_role_authoritys` VALUES ('3', '81');
INSERT INTO `pub_role_authoritys` VALUES ('3', '83');
INSERT INTO `pub_role_authoritys` VALUES ('3', '109');
INSERT INTO `pub_role_authoritys` VALUES ('4', '157');
INSERT INTO `pub_role_authoritys` VALUES ('4', '25');
INSERT INTO `pub_role_authoritys` VALUES ('4', '26');
INSERT INTO `pub_role_authoritys` VALUES ('4', '27');
INSERT INTO `pub_role_authoritys` VALUES ('4', '29');
INSERT INTO `pub_role_authoritys` VALUES ('4', '76');
INSERT INTO `pub_role_authoritys` VALUES ('4', '30');
INSERT INTO `pub_role_authoritys` VALUES ('4', '32');
INSERT INTO `pub_role_authoritys` VALUES ('4', '75');
INSERT INTO `pub_role_authoritys` VALUES ('4', '155');
INSERT INTO `pub_role_authoritys` VALUES ('4', '165');
INSERT INTO `pub_role_authoritys` VALUES ('4', '166');
INSERT INTO `pub_role_authoritys` VALUES ('4', '177');
INSERT INTO `pub_role_authoritys` VALUES ('4', '36');
INSERT INTO `pub_role_authoritys` VALUES ('4', '37');
INSERT INTO `pub_role_authoritys` VALUES ('4', '38');
INSERT INTO `pub_role_authoritys` VALUES ('5', '157');
INSERT INTO `pub_role_authoritys` VALUES ('5', '25');
INSERT INTO `pub_role_authoritys` VALUES ('5', '27');
INSERT INTO `pub_role_authoritys` VALUES ('5', '29');
INSERT INTO `pub_role_authoritys` VALUES ('5', '76');
INSERT INTO `pub_role_authoritys` VALUES ('5', '30');
INSERT INTO `pub_role_authoritys` VALUES ('5', '32');
INSERT INTO `pub_role_authoritys` VALUES ('5', '75');
INSERT INTO `pub_role_authoritys` VALUES ('5', '84');
INSERT INTO `pub_role_authoritys` VALUES ('5', '155');
INSERT INTO `pub_role_authoritys` VALUES ('5', '177');
INSERT INTO `pub_role_authoritys` VALUES ('5', '178');
INSERT INTO `pub_role_authoritys` VALUES ('5', '179');
INSERT INTO `pub_role_authoritys` VALUES ('5', '180');
INSERT INTO `pub_role_authoritys` VALUES ('5', '181');
INSERT INTO `pub_role_authoritys` VALUES ('5', '36');
INSERT INTO `pub_role_authoritys` VALUES ('5', '37');
INSERT INTO `pub_role_authoritys` VALUES ('5', '38');
INSERT INTO `pub_role_authoritys` VALUES ('5', '43');

-- ----------------------------
-- Table structure for `report_autokbs`
-- ----------------------------
DROP TABLE IF EXISTS `report_autokbs`;
CREATE TABLE `report_autokbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_time` datetime DEFAULT NULL,
  `key_word` varchar(255) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_autokbs
-- ----------------------------
INSERT INTO `report_autokbs` VALUES ('1', '2016-05-10 20:08:50', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('2', '2016-05-11 15:23:21', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('3', '2016-05-15 11:49:06', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('4', '2016-05-18 15:26:18', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('5', '2016-05-18 17:05:02', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('6', '2016-05-23 14:11:51', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('7', '2016-05-23 14:12:41', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('8', '2016-05-23 14:12:48', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('9', '2016-05-23 14:21:48', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('10', '2016-05-23 14:22:31', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('11', '2016-06-03 10:34:50', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('12', '2016-06-03 11:18:43', '', '2', '0');
INSERT INTO `report_autokbs` VALUES ('13', '2016-06-27 15:55:31', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('14', '2016-06-27 16:03:57', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('15', '2016-06-27 16:07:12', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('16', '2016-06-27 17:29:58', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('17', '2016-06-27 17:30:37', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('18', '2016-07-20 16:03:13', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('19', '2016-07-31 11:52:57', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('20', '2016-07-31 11:52:58', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('21', '2016-07-31 11:53:32', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('22', '2016-07-31 11:59:11', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('23', '2016-09-03 15:22:12', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('24', '2016-09-14 16:52:24', '', '1', '0');
INSERT INTO `report_autokbs` VALUES ('25', '2016-09-14 16:52:50', '', '1', '0');

-- ----------------------------
-- Table structure for `report_comment_day`
-- ----------------------------
DROP TABLE IF EXISTS `report_comment_day`;
CREATE TABLE `report_comment_day` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_bad` int(11) DEFAULT NULL,
  `comment_better` int(11) DEFAULT NULL,
  `comment_good` int(11) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `date_day` datetime DEFAULT NULL,
  `date_day_str` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_comment_day
-- ----------------------------

-- ----------------------------
-- Table structure for `report_comment_month`
-- ----------------------------
DROP TABLE IF EXISTS `report_comment_month`;
CREATE TABLE `report_comment_month` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_bad` int(11) DEFAULT NULL,
  `comment_better` int(11) DEFAULT NULL,
  `comment_good` int(11) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `date_day` datetime DEFAULT NULL,
  `date_month_str` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_comment_month
-- ----------------------------

-- ----------------------------
-- Table structure for `report_comment_ratio_trend_month`
-- ----------------------------
DROP TABLE IF EXISTS `report_comment_ratio_trend_month`;
CREATE TABLE `report_comment_ratio_trend_month` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `common_comment` bigint(20) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `good_comment` bigint(20) DEFAULT NULL,
  `negative_comment` bigint(20) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_comment_ratio_trend_month
-- ----------------------------

-- ----------------------------
-- Table structure for `report_comment_ratio_trend_year`
-- ----------------------------
DROP TABLE IF EXISTS `report_comment_ratio_trend_year`;
CREATE TABLE `report_comment_ratio_trend_year` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `common_comment` bigint(20) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `good_comment` bigint(20) DEFAULT NULL,
  `negative_comment` bigint(20) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_comment_ratio_trend_year
-- ----------------------------

-- ----------------------------
-- Table structure for `report_comment_year`
-- ----------------------------
DROP TABLE IF EXISTS `report_comment_year`;
CREATE TABLE `report_comment_year` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_bad` int(11) DEFAULT NULL,
  `comment_better` int(11) DEFAULT NULL,
  `comment_good` int(11) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `date_day` datetime DEFAULT NULL,
  `date_year_str` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_comment_year
-- ----------------------------

-- ----------------------------
-- Table structure for `report_content_channel`
-- ----------------------------
DROP TABLE IF EXISTS `report_content_channel`;
CREATE TABLE `report_content_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `platform_user` bigint(20) DEFAULT NULL,
  `ref_date` datetime DEFAULT NULL,
  `share_count` int(11) DEFAULT NULL,
  `share_scene` int(11) DEFAULT NULL,
  `share_user` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_content_channel
-- ----------------------------
INSERT INTO `report_content_channel` VALUES ('1', '4', '2016-07-15 00:00:00', '2', '1', '2', '0');
INSERT INTO `report_content_channel` VALUES ('2', '4', '2016-07-15 00:00:00', '9', '2', '7', '0');
INSERT INTO `report_content_channel` VALUES ('3', '4', '2016-07-16 00:00:00', '1', '2', '1', '0');
INSERT INTO `report_content_channel` VALUES ('4', '4', '2016-07-18 00:00:00', '2', '1', '1', '0');

-- ----------------------------
-- Table structure for `report_friends_from`
-- ----------------------------
DROP TABLE IF EXISTS `report_friends_from`;
CREATE TABLE `report_friends_from` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cancel_user` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `new_user` int(11) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `ref_date` datetime DEFAULT NULL,
  `user_source` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_friends_from
-- ----------------------------
INSERT INTO `report_friends_from` VALUES ('1', '0', '2016-03-14 07:50:42', '4', '27', '2016-03-13 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('2', '418', '2016-07-20 17:21:06', '254', '4', '2016-07-19 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('3', '2', '2016-07-20 17:21:06', '86', '4', '2016-07-19 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('4', '1', '2016-07-20 17:21:06', '221', '4', '2016-07-19 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('5', '0', '2016-07-20 17:21:06', '139', '4', '2016-07-19 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('6', '0', '2016-07-20 17:21:06', '1', '4', '2016-07-19 00:00:00', '43', '0');
INSERT INTO `report_friends_from` VALUES ('7', '751', '2016-07-20 17:50:07', '634', '4', '2016-07-12 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('8', '1', '2016-07-20 17:50:07', '244', '4', '2016-07-12 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('9', '3', '2016-07-20 17:50:07', '371', '4', '2016-07-12 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('10', '0', '2016-07-20 17:50:07', '64', '4', '2016-07-12 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('11', '0', '2016-07-20 17:50:07', '1', '4', '2016-07-12 00:00:00', '43', '0');
INSERT INTO `report_friends_from` VALUES ('12', '996', '2016-07-20 17:50:08', '285', '4', '2016-07-13 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('13', '1', '2016-07-20 17:50:08', '134', '4', '2016-07-13 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('14', '3', '2016-07-20 17:50:08', '630', '4', '2016-07-13 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('15', '1', '2016-07-20 17:50:08', '40', '4', '2016-07-13 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('16', '0', '2016-07-20 17:50:08', '3', '4', '2016-07-13 00:00:00', '43', '0');
INSERT INTO `report_friends_from` VALUES ('17', '426', '2016-07-20 17:50:08', '197', '4', '2016-07-14 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('18', '2', '2016-07-20 17:50:08', '90', '4', '2016-07-14 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('19', '2', '2016-07-20 17:50:08', '104', '4', '2016-07-14 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('20', '0', '2016-07-20 17:50:08', '9', '4', '2016-07-14 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('21', '0', '2016-07-20 17:50:08', '1', '4', '2016-07-14 00:00:00', '43', '0');
INSERT INTO `report_friends_from` VALUES ('22', '1169', '2016-07-20 17:50:08', '881', '4', '2016-07-15 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('23', '1', '2016-07-20 17:50:08', '50', '4', '2016-07-15 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('24', '7', '2016-07-20 17:50:08', '299', '4', '2016-07-15 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('25', '0', '2016-07-20 17:50:08', '8', '4', '2016-07-15 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('26', '0', '2016-07-20 17:50:08', '2', '4', '2016-07-15 00:00:00', '43', '0');
INSERT INTO `report_friends_from` VALUES ('27', '1274', '2016-07-20 17:50:08', '396', '4', '2016-07-16 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('28', '0', '2016-07-20 17:50:08', '18', '4', '2016-07-16 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('29', '5', '2016-07-20 17:50:08', '505', '4', '2016-07-16 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('30', '0', '2016-07-20 17:50:08', '21', '4', '2016-07-16 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('31', '0', '2016-07-20 17:50:08', '1', '4', '2016-07-16 00:00:00', '43', '0');
INSERT INTO `report_friends_from` VALUES ('32', '209', '2016-07-20 17:50:08', '14', '4', '2016-07-17 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('33', '0', '2016-07-20 17:50:08', '4', '4', '2016-07-17 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('34', '0', '2016-07-20 17:50:08', '9', '4', '2016-07-17 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('35', '0', '2016-07-20 17:50:08', '2', '4', '2016-07-17 00:00:00', '30', '0');
INSERT INTO `report_friends_from` VALUES ('36', '476', '2016-07-20 17:50:08', '246', '4', '2016-07-18 00:00:00', '0', '0');
INSERT INTO `report_friends_from` VALUES ('37', '0', '2016-07-20 17:50:08', '47', '4', '2016-07-18 00:00:00', '1', '0');
INSERT INTO `report_friends_from` VALUES ('38', '1', '2016-07-20 17:50:08', '144', '4', '2016-07-18 00:00:00', '17', '0');
INSERT INTO `report_friends_from` VALUES ('39', '0', '2016-07-20 17:50:08', '16', '4', '2016-07-18 00:00:00', '30', '0');

-- ----------------------------
-- Table structure for `report_friends_sum`
-- ----------------------------
DROP TABLE IF EXISTS `report_friends_sum`;
CREATE TABLE `report_friends_sum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cumulate_user` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `ref_date` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_friends_sum
-- ----------------------------
INSERT INTO `report_friends_sum` VALUES ('1', '8', '1', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('2', '8', '1', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('3', '8', '1', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('4', '8', '1', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('5', '8', '1', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('6', '8', '1', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('7', '8', '1', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('8', '32', '2', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('9', '32', '2', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('10', '32', '2', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('11', '32', '2', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('12', '32', '2', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('13', '32', '2', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('14', '32', '2', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('15', '22', '3', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('16', '22', '3', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('17', '22', '3', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('18', '22', '3', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('19', '22', '3', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('20', '22', '3', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('21', '22', '3', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('22', '1', '4', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('23', '1', '4', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('24', '1', '4', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('25', '1', '4', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('26', '1', '4', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('27', '1', '4', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('28', '1', '4', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('29', '10', '5', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('30', '10', '5', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('31', '10', '5', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('32', '10', '5', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('33', '10', '5', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('34', '10', '5', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('35', '10', '5', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('36', '176', '7', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('37', '176', '7', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('38', '176', '7', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('39', '175', '7', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('40', '175', '7', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('41', '175', '7', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('42', '175', '7', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('43', '2', '11', '2016-03-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('44', '2', '11', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('45', '2', '11', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('46', '2', '11', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('47', '2', '11', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('48', '2', '11', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('49', '2', '11', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('50', '8', '1', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('51', '31', '2', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('52', '22', '3', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('53', '1', '4', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('54', '10', '5', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('55', '1', '7', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('56', '2', '11', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('57', '176', '25', '2016-03-03 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('58', '176', '25', '2016-03-04 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('59', '175', '25', '2016-03-05 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('60', '175', '25', '2016-03-06 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('61', '175', '25', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('62', '175', '25', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('63', '175', '25', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('64', '8', '1', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('65', '31', '2', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('66', '22', '3', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('67', '1', '4', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('68', '10', '5', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('69', '1', '7', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('70', '2', '11', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('71', '175', '25', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('72', '8', '1', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('73', '31', '2', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('74', '22', '3', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('75', '1', '4', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('76', '10', '5', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('77', '1', '7', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('78', '2', '11', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('79', '175', '25', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('80', '8', '1', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('81', '31', '2', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('82', '22', '3', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('83', '1', '4', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('84', '10', '5', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('85', '1', '7', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('86', '2', '11', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('87', '175', '25', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('88', '8', '1', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('89', '31', '2', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('90', '22', '3', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('91', '1', '4', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('92', '10', '5', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('93', '1', '7', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('94', '2', '11', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('95', '175', '25', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('96', '0', '27', '2016-03-07 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('97', '0', '27', '2016-03-08 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('98', '0', '27', '2016-03-09 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('99', '0', '27', '2016-03-10 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('100', '0', '27', '2016-03-11 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('101', '0', '27', '2016-03-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('102', '4', '27', '2016-03-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('103', '3', '2', '2016-06-02 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('104', '12679', '4', '2016-07-19 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('105', '12816', '4', '2016-07-12 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('106', '12907', '4', '2016-07-13 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('107', '12878', '4', '2016-07-14 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('108', '12941', '4', '2016-07-15 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('109', '12603', '4', '2016-07-16 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('110', '12423', '4', '2016-07-17 00:00:00', '0');
INSERT INTO `report_friends_sum` VALUES ('111', '12399', '4', '2016-07-18 00:00:00', '0');

-- ----------------------------
-- Table structure for `report_hot_word`
-- ----------------------------
DROP TABLE IF EXISTS `report_hot_word`;
CREATE TABLE `report_hot_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `hot_word_name` varchar(255) DEFAULT NULL,
  `increment` bigint(20) NOT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_hot_word
-- ----------------------------

-- ----------------------------
-- Table structure for `report_message`
-- ----------------------------
DROP TABLE IF EXISTS `report_message`;
CREATE TABLE `report_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msg_count` int(11) DEFAULT NULL,
  `msg_type` int(11) DEFAULT NULL,
  `msg_user` int(11) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `ref_date` datetime DEFAULT NULL,
  `ref_hour` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_message
-- ----------------------------
INSERT INTO `report_message` VALUES ('1', '1', '1', '1', '1', '2016-03-04 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('2', '2', '1', '2', '27', '2016-03-13 00:00:00', '17', '0', '0');
INSERT INTO `report_message` VALUES ('3', '2', '1', '2', '27', '2016-03-13 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('4', '1', '1', '1', '4', '2016-07-19 00:00:00', '9', '0', '0');
INSERT INTO `report_message` VALUES ('5', '12', '1', '10', '4', '2016-07-19 00:00:00', '10', '0', '0');
INSERT INTO `report_message` VALUES ('6', '1', '1', '1', '4', '2016-07-19 00:00:00', '11', '0', '0');
INSERT INTO `report_message` VALUES ('7', '2', '1', '1', '4', '2016-07-19 00:00:00', '12', '0', '0');
INSERT INTO `report_message` VALUES ('8', '9', '1', '6', '4', '2016-07-19 00:00:00', '13', '0', '0');
INSERT INTO `report_message` VALUES ('9', '3', '1', '2', '4', '2016-07-19 00:00:00', '15', '0', '0');
INSERT INTO `report_message` VALUES ('10', '8', '1', '3', '4', '2016-07-19 00:00:00', '20', '0', '0');
INSERT INTO `report_message` VALUES ('11', '1', '2', '1', '4', '2016-07-19 00:00:00', '20', '0', '0');
INSERT INTO `report_message` VALUES ('12', '2', '1', '1', '4', '2016-07-19 00:00:00', '22', '0', '0');
INSERT INTO `report_message` VALUES ('13', '103', '1', '42', '4', '2016-07-12 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('14', '4', '2', '4', '4', '2016-07-12 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('15', '108', '1', '43', '4', '2016-07-13 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('16', '1', '2', '1', '4', '2016-07-13 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('17', '1', '3', '1', '4', '2016-07-13 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('18', '50', '1', '18', '4', '2016-07-14 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('19', '3', '2', '2', '4', '2016-07-14 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('20', '109', '1', '65', '4', '2016-07-15 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('21', '6', '2', '5', '4', '2016-07-15 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('22', '43', '1', '28', '4', '2016-07-16 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('23', '4', '2', '3', '4', '2016-07-16 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('24', '9', '1', '7', '4', '2016-07-17 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('25', '1', '2', '1', '4', '2016-07-17 00:00:00', null, '1', '0');
INSERT INTO `report_message` VALUES ('26', '27', '1', '16', '4', '2016-07-18 00:00:00', null, '1', '0');

-- ----------------------------
-- Table structure for `report_message_distribute`
-- ----------------------------
DROP TABLE IF EXISTS `report_message_distribute`;
CREATE TABLE `report_message_distribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count_interval` int(11) DEFAULT NULL,
  `msg_user` int(11) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `ref_date` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_message_distribute
-- ----------------------------
INSERT INTO `report_message_distribute` VALUES ('1', '1', '2', '1', '2016-02-24 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('2', '1', '2', '1', '2016-02-25 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('3', '1', '1', '1', '2016-02-26 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('4', '1', '1', '1', '2016-03-04 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('5', '1', '1', '7', '2016-02-24 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('6', '1', '2', '27', '2016-03-13 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('7', '1', '41', '4', '2016-07-12 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('8', '2', '1', '4', '2016-07-12 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('9', '3', '2', '4', '2016-07-12 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('10', '1', '41', '4', '2016-07-13 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('11', '2', '2', '4', '2016-07-13 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('12', '3', '1', '4', '2016-07-13 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('13', '1', '17', '4', '2016-07-14 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('14', '2', '1', '4', '2016-07-14 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('15', '3', '1', '4', '2016-07-14 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('16', '1', '65', '4', '2016-07-15 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('17', '2', '2', '4', '2016-07-15 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('18', '1', '30', '4', '2016-07-16 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('19', '1', '8', '4', '2016-07-17 00:00:00', '0');
INSERT INTO `report_message_distribute` VALUES ('20', '1', '16', '4', '2016-07-18 00:00:00', '0');

-- ----------------------------
-- Table structure for `report_oper_count`
-- ----------------------------
DROP TABLE IF EXISTS `report_oper_count`;
CREATE TABLE `report_oper_count` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `operator_name` varchar(255) DEFAULT NULL,
  `reply_num` int(11) NOT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_oper_count
-- ----------------------------

-- ----------------------------
-- Table structure for `report_purchase_motive`
-- ----------------------------
DROP TABLE IF EXISTS `report_purchase_motive`;
CREATE TABLE `report_purchase_motive` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `business_type_id` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `if_has` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `label_name` varchar(255) DEFAULT NULL,
  `proportion` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `total_score` float DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_purchase_motive
-- ----------------------------

-- ----------------------------
-- Table structure for `report_sku_range`
-- ----------------------------
DROP TABLE IF EXISTS `report_sku_range`;
CREATE TABLE `report_sku_range` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(255) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_type` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `increment_comment` bigint(20) NOT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_sku_range
-- ----------------------------

-- ----------------------------
-- Table structure for `report_tag_increment_trend`
-- ----------------------------
DROP TABLE IF EXISTS `report_tag_increment_trend`;
CREATE TABLE `report_tag_increment_trend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `date_day` datetime DEFAULT NULL,
  `date_day_str` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `tag_increment_num` varchar(255) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_tag_increment_trend
-- ----------------------------

-- ----------------------------
-- Table structure for `score`
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_score` int(11) NOT NULL,
  `insert_time` datetime DEFAULT NULL,
  `items` varchar(255) DEFAULT NULL,
  `mi_score` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `srange` int(11) NOT NULL,
  `sstage` varchar(255) DEFAULT NULL,
  `stu_name` varchar(255) DEFAULT NULL,
  `stu_no` varchar(255) DEFAULT NULL,
  `test_score` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('1', '1', '2016-08-31 17:13:42', '项目方法', '12', '222', '1', 'B级', '李天涯1', '1011', '12');
INSERT INTO `score` VALUES ('2', '2', '2016-09-10 17:32:29', '无', '1', '85', '12', 'A级别', '王大锤', '1002', '12');

-- ----------------------------
-- Table structure for `task_type_logs`
-- ----------------------------
DROP TABLE IF EXISTS `task_type_logs`;
CREATE TABLE `task_type_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `item_id` varchar(40) DEFAULT NULL,
  `task_type` int(3) DEFAULT NULL,
  `is_success` int(3) DEFAULT NULL,
  `error_msg` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=6844 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_type_logs
-- ----------------------------

-- ----------------------------
-- Table structure for `tdormbulid`
-- ----------------------------
DROP TABLE IF EXISTS `tdormbulid`;
CREATE TABLE `tdormbulid` (
  `dorm_build_id` int(11) NOT NULL AUTO_INCREMENT,
  `dorm_build_detail` varchar(255) DEFAULT NULL,
  `dorm_build_name` varchar(255) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  PRIMARY KEY (`dorm_build_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tdormbulid
-- ----------------------------

-- ----------------------------
-- Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', '111', 'Lero', '男', '123');

-- ----------------------------
-- Table structure for `t_dorm`
-- ----------------------------
DROP TABLE IF EXISTS `t_dorm`;
CREATE TABLE `t_dorm` (
  `dormId` int(11) NOT NULL AUTO_INCREMENT,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(20) DEFAULT NULL,
  `dormType` varchar(20) DEFAULT NULL,
  `dormNumber` int(11) DEFAULT NULL,
  `dormTel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dorm
-- ----------------------------
INSERT INTO `t_dorm` VALUES ('1', '1', '220', '男', '6', '110');

-- ----------------------------
-- Table structure for `t_dormbuild`
-- ----------------------------
DROP TABLE IF EXISTS `t_dormbuild`;
CREATE TABLE `t_dormbuild` (
  `dormBuildId` int(11) NOT NULL AUTO_INCREMENT,
  `dormBuildName` varchar(20) DEFAULT NULL,
  `dormBuildDetail` varchar(50) DEFAULT NULL,
  `lng` double(50,6) DEFAULT NULL,
  `lat` double(50,6) DEFAULT NULL,
  PRIMARY KEY (`dormBuildId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dormbuild
-- ----------------------------
INSERT INTO `t_dormbuild` VALUES ('1', '一号停车点11', '255', '102.696591', '25.007066');
INSERT INTO `t_dormbuild` VALUES ('4', 'B区2号站点', '122', '102.606591', '25.107066');
INSERT INTO `t_dormbuild` VALUES ('5', 'c区计站', '155', '102.596591', '24.607066');
INSERT INTO `t_dormbuild` VALUES ('6', '孵化站点5号区域', '656', '102.686591', '24.907066');
INSERT INTO `t_dormbuild` VALUES ('7', '5号站点xxx区域', '50', '102.656591', '25.307066');
INSERT INTO `t_dormbuild` VALUES ('10', '民江苏一号', '355', '102.640591', '25.717066');
INSERT INTO `t_dormbuild` VALUES ('12', '新加泊车点', '500', '102.659239', '25.520720');

-- ----------------------------
-- Table structure for `t_dormmanager`
-- ----------------------------
DROP TABLE IF EXISTS `t_dormmanager`;
CREATE TABLE `t_dormmanager` (
  `dormManId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormManId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dormmanager
-- ----------------------------
INSERT INTO `t_dormmanager` VALUES ('2', 'manager2', '123', '4', '小张', '男', '123');
INSERT INTO `t_dormmanager` VALUES ('3', 'manager3', '123', '1', '小李', '女', '123');
INSERT INTO `t_dormmanager` VALUES ('4', 'manager4', '123', '5', '小陈', '男', '123');
INSERT INTO `t_dormmanager` VALUES ('5', 'manager5', '123', '1', '小宋', '男', '123');
INSERT INTO `t_dormmanager` VALUES ('7', 'manager6', '123', '1', '呵呵 ', '女', '123');
INSERT INTO `t_dormmanager` VALUES ('8', 'manager1', '123', '6', '小白', '男', '123');
INSERT INTO `t_dormmanager` VALUES ('9', 'manager7', '123', '7', '哈哈', '女', '123');
INSERT INTO `t_dormmanager` VALUES ('10', 'chb', '111', '10', '李旺', '男', '18812211222');

-- ----------------------------
-- Table structure for `t_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `studentNumber` varchar(20) DEFAULT NULL,
  `studentName` varchar(30) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `detail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_record
-- ----------------------------
INSERT INTO `t_record` VALUES ('1', '002', '李四', '4', '120', '2014-01-01', '123');
INSERT INTO `t_record` VALUES ('3', '007', '测试1', '1', '221', '2014-08-11', '123');
INSERT INTO `t_record` VALUES ('4', '005', '赵起', '4', '220', '2014-08-12', '...');
INSERT INTO `t_record` VALUES ('5', '006', '王珂珂', '4', '111', '2014-08-12', '00');
INSERT INTO `t_record` VALUES ('6', '004', '李进', '6', '220', '2014-08-12', '....');
INSERT INTO `t_record` VALUES ('7', '004', '李进', '6', '220', '2014-08-12', '22');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `studentId` int(11) NOT NULL AUTO_INCREMENT,
  `stuNum` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `s1` double DEFAULT NULL,
  `e1` double DEFAULT NULL,
  `s2` double DEFAULT NULL,
  `e2` double DEFAULT NULL,
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('34', '888888', '123', '刘红', '1', '45001', '男', '18812211222', '102.690563', '25.075715', '25.078268', '102.711475');

-- ----------------------------
-- Table structure for `ump_authority`
-- ----------------------------
DROP TABLE IF EXISTS `ump_authority`;
CREATE TABLE `ump_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(50) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `product` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_q2ufdibosq36g3ai2a80fc0ph` (`product`) USING BTREE,
  CONSTRAINT `ump_authority_ibfk_1` FOREIGN KEY (`product`) REFERENCES `ump_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_authority
-- ----------------------------
INSERT INTO `ump_authority` VALUES ('1', '公司管理', null, null, '1', '0', '', '0', 'a', '0', '1');
INSERT INTO `ump_authority` VALUES ('2', '公司注册', null, null, '1', '1', '', '0', '/ump/umpcompanys/listPage?', '1', '1');
INSERT INTO `ump_authority` VALUES ('3', '服务管理', null, null, '1', '1', '', '0', '/ump/umpcompanyservices/list?', '1', '1');
INSERT INTO `ump_authority` VALUES ('4', '产品管理', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('7', '版本管理', null, null, '1', '4', '', '0', '/ump/umpversions/listPage?', '0', '1');
INSERT INTO `ump_authority` VALUES ('8', '行业管理', null, null, '1', '4', '', '0', '/ump/umpparentbusinesstypes/listPage?active=P', '1', '1');
INSERT INTO `ump_authority` VALUES ('9', '品牌关键词管理', null, null, '1', '4', '', '0', '/ump/umpbrands/listPage?active=U', '0', '1');
INSERT INTO `ump_authority` VALUES ('10', '管理中心', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('12', '权限管理', null, null, '1', '10', '', '0', '/ump/umproles?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('13', '账号管理', null, null, '1', '10', '', '0', '/ump/umpoperators?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('14', '登录日志', null, null, '1', '10', '', '0', '/ump/umplogs?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('15', '邮箱配置', null, null, '1', '10', '', '0', '/ump/umpconfigs/ec?', '0', '1');
INSERT INTO `ump_authority` VALUES ('25', '知识管理', null, null, '1', '0', '25', '0', ' ', '0', '3');
INSERT INTO `ump_authority` VALUES ('26', '常用文本', null, null, '1', '25', '', '0', '/ump/wcctemplates?', '0', '3');
INSERT INTO `ump_authority` VALUES ('27', '素材管理', null, null, '1', '25', null, '0', '/ump/wccmaterialses?', '0', '3');
INSERT INTO `ump_authority` VALUES ('29', '自动回复', null, null, '1', '25', null, '0', '/ump/wccwelcomkbses?form', '0', '3');
INSERT INTO `ump_authority` VALUES ('30', '应用管理', null, null, '1', '0', '30', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('31', '互动消息', null, null, '1', '41', null, '0', '/ump/friendRecord/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('32', '门店管理', null, null, '1', '30', '', '0', '/ump/wccstores?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('34', '抽奖活动', null, null, '1', '30', '', '0', '	/ump/wcclotteryactivitys?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('35', '微内容', null, null, '1', '30', '', '0', '/ump/wcccontents?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('36', '微信管理', null, null, '1', '0', '36', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('37', '公众号管理', null, null, '1', '36', '', '0', '/ump/wccplatformusers?page=1&amp;size=10', '1', '3');
INSERT INTO `ump_authority` VALUES ('38', '自定义菜单', null, null, '1', '36', '', '0', '/ump/wccmenus?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('41', '粉丝管理', null, null, '1', '0', '41', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('42', '粉丝分组', null, null, '1', '41', null, '0', '/ump/wccgroups/show?', '0', '3');
INSERT INTO `ump_authority` VALUES ('43', '粉丝信息', null, null, '1', '41', null, '0', '/ump/wccfriends?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('75', '审核', null, null, '1', '32', '', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('76', '审核', null, null, '1', '29', '', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('78', '实用工具', '', null, '1', '30', '', '0', '/ump/wccutils?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('79', '实用工具', '', null, '1', '10', '', '0', '/ump/wccutils/umpList?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('80', '运营报告', '', null, '1', '0', '75', '0', ' ', '0', '3');
INSERT INTO `ump_authority` VALUES ('81', '粉丝报表', '', null, '1', '80', '', '0', '/ump/reportfriends/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('82', '条件群发', '', null, '1', '30', '', '0', '/ump/wccmassmessage/massMessage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('83', '内容报表', '', null, '1', '80', '', '0', '/ump/reportMassPicText/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('84', '调查问卷', null, null, '1', '30', null, '0', 'http://ur.qq.com/login.html?s_url=http://ur.qq.com/index.html', '0', '3');
INSERT INTO `ump_authority` VALUES ('85', '微社区', null, null, '1', '30', null, '0', 'http://mp.wsq.qq.com/cp ', '0', '3');
INSERT INTO `ump_authority` VALUES ('86', '模块管理', null, null, '1', '0', '30', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('87', '项目管理', null, null, '1', '0', '75', '0', 'http://www.baidu.com', '0', '3');
INSERT INTO `ump_authority` VALUES ('88', '文明生活', null, null, '1', '86', null, '0', '/ump/cultureLife/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('89', '社区信息', null, null, '1', '87', '', '0', '/ump/cdbWccAppartment/cdbWccAppartment?', '0', '3');
INSERT INTO `ump_authority` VALUES ('90', '业主信息', null, null, '1', '86', '', '0', '/ump/wccproprietor/queryproprietor?', '0', '3');
INSERT INTO `ump_authority` VALUES ('92', '我的一卡通', null, null, '1', '0', '75', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('93', '一卡通管理', null, null, '1', '92', '75', '0', '/ump/myCard/TestMyCard?', '0', '3');
INSERT INTO `ump_authority` VALUES ('96', '社区其他信息', null, null, '1', '97', '75', '0', '/ump/cdbWccAppartmentFu/TestCbdWccAppartmentFu?', '0', '3');
INSERT INTO `ump_authority` VALUES ('97', '项目管理', null, null, '1', '0', '75', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('98', '社区信息', null, null, '1', '97', '75', '0', '/ump/cdbWccAppartment/TestCdbWccAppartment?', '0', '3');
INSERT INTO `ump_authority` VALUES ('99', '生活助手', null, null, '1', '86', null, '0', '/ump/lifeHelper/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('100', '周边特惠', null, null, '1', '0', '75', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('101', '周边特惠', null, null, '1', '100', '75', '0', '/ump/Alliancestore/TestAlliancestore?', '0', '3');
INSERT INTO `ump_authority` VALUES ('102', '理财产品', null, null, '1', '0', '75', '0', ' ', '0', '3');
INSERT INTO `ump_authority` VALUES ('103', '理财产品', null, null, '1', '102', '75', '0', '/ump/financialProduct/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('104', '物管费信息', null, null, '1', '86', null, '0', '/ump/wccFees/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('105', '联系我们', '', null, '1', '86', '', '0', '/ump/contactUs/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('106', '故障报修', null, null, '1', '0', '75', '0', ' ', '0', '3');
INSERT INTO `ump_authority` VALUES ('107', '故障报修', null, null, '1', '106', '', '0', '/ump/faultServiceType/faultServiceType?', '0', '3');
INSERT INTO `ump_authority` VALUES ('108', '报修信息', null, null, '1', '106', null, '0', '/ump/faultService/faultService?', '0', '3');
INSERT INTO `ump_authority` VALUES ('109', '消息报表', null, null, '1', '80', '75', '0', '/ump/reportmessages/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('110', '图片添加', '', null, '1', '86', '75', '0', '/ump/ImgSave/TestImgSave?', '0', '3');
INSERT INTO `ump_authority` VALUES ('111', 'TEST', '', null, '1', '86', '', '0', '/ump/wccanusers/lists?', '0', '3');
INSERT INTO `ump_authority` VALUES ('150', '理财群发', '', null, '1', '102', '', '0', '/ump/wccmassmessage/massMessage2?', '0', '3');
INSERT INTO `ump_authority` VALUES ('152', '周边特惠类型', '', null, '1', '100', '75', '0', '/ump/AlliancestoreType/TestAlliancestoreType?', '0', '3');
INSERT INTO `ump_authority` VALUES ('154', '二维码管理', null, null, '1', '41', null, '0', '/ump/qrCodeManage/showQrManageTable?', '0', '3');
INSERT INTO `ump_authority` VALUES ('155', '群发管理', null, null, '1', '30', null, '0', '/ump/wccmassmessage/massMessage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('156', '素材管理(总部)', null, null, '1', '25', '75', '0', '/ump/wccofficematerialses?', '0', '3');
INSERT INTO `ump_authority` VALUES ('157', '根部', null, null, '1', '-1', null, '0', ' ', '0', null);
INSERT INTO `ump_authority` VALUES ('158', '公众号回复报表', null, null, '1', '80', null, '0', '/ump/reportReply/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('159', '官微及竞品发布内容报表', null, null, '1', '80', null, '0', '/ump/reportArticle/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('160', '品牌管理', null, null, '1', '36', null, '0', '/ump/cherybrands/?page=1&amp;size=10', '0', '3');
INSERT INTO `ump_authority` VALUES ('161', '用户统计报表', null, null, '1', '80', null, '0', '/ump/reportOperator/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('162', '经销商使用统计报表', null, null, '1', '80', null, '0', '/ump/reporterpuse/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('163', '素材经销商转发情况查询', null, null, '1', '80', null, '0', '/ump/reportForward/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('164', '单个经销商转发率查询 ', null, null, '1', '80', null, '0', '/ump/reportSingleDealerSendMaterFr/reportPage?', '0', '3');
INSERT INTO `ump_authority` VALUES ('165', '理财产品管理', null, null, '1', '30', null, '0', '/ump/wccProduct/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('166', '产品预约详情', null, null, '1', '30', null, '0', '/ump/wccProductResult/showResult?', '0', '3');
INSERT INTO `ump_authority` VALUES ('177', '专属二维码', null, null, '1', '30', null, '0', '/ump/serviceAdvisor/showRecord?', '0', '3');
INSERT INTO `ump_authority` VALUES ('178', '学生绑定信息', null, null, '1', '300', null, '0', ' /ump/bdingStuInfoResult/showStuResult?', '0', '3');
INSERT INTO `ump_authority` VALUES ('179', '维修报名登记', null, null, '1', '300', null, '0', ' /ump/bdingStuInfoResult/showRepairInfo?', '0', '3');
INSERT INTO `ump_authority` VALUES ('180', '候选预报名信息', null, null, '1', '300', null, '0', '/ump/bdingStuInfoResult/showEnListInfo?', '0', '3');
INSERT INTO `ump_authority` VALUES ('181', '操行分管理', null, null, '1', '300', null, '0', '/ump/scoreController/showScoreRecord?', '0', '3');

-- ----------------------------
-- Table structure for `ump_brand`
-- ----------------------------
DROP TABLE IF EXISTS `ump_brand`;
CREATE TABLE `ump_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(30) NOT NULL,
  `check_status` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) NOT NULL,
  `key_name` varchar(4000) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `business` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6svyt3o41m76m9nya5ekyo8vj` (`business`),
  CONSTRAINT `ump_brand_ibfk_1` FOREIGN KEY (`business`) REFERENCES `ump_parent_business_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_brand
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_business_type`
-- ----------------------------
DROP TABLE IF EXISTS `ump_business_type`;
CREATE TABLE `ump_business_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_name` varchar(30) NOT NULL,
  `create_time` datetime NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `parent_business_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dvwmqrwy09lmp8pdtfvs8beil` (`business_name`),
  KEY `FK_1c8axprmrxjdxcvbkicwktv4n` (`parent_business_type`),
  CONSTRAINT `ump_business_type_ibfk_1` FOREIGN KEY (`parent_business_type`) REFERENCES `ump_parent_business_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_business_type
-- ----------------------------
INSERT INTO `ump_business_type` VALUES ('4', '冰箱', '2014-11-27 14:50:11', '0', '1', '', '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('5', '电视机', '2015-01-07 15:32:49', '0', '1', '', '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('7', '空调', '2014-11-29 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('9', '洗衣机', '2014-12-02 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('10', '家庭影音', '2014-12-03 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('12', '热水器', '2014-12-06 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('13', '消毒柜', '2014-12-07 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('14', '家电配件', '2014-12-08 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('15', '取暖器', '2014-12-09 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('16', '净水器', '2014-12-10 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('17', '加湿器', '2014-12-11 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('18', '吸尘器', '2014-12-12 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('19', '电话机', '2014-12-13 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('20', '清洁机', '2014-12-14 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('21', '除湿机', '2014-12-16 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('22', '电风扇', '2014-12-17 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('23', '饮水机', '2014-12-19 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('24', '其他家用电器', '2014-12-20 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('25', '电压力锅', '2014-12-21 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('26', '电饭煲', '2014-12-22 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('27', '料理/榨汁机', '2014-12-23 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('28', '电水壶/热水瓶', '2014-12-24 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('29', '其他厨房电器', '2014-12-25 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('30', '剃/脱毛器', '2014-12-26 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('31', '电吹风', '2014-12-27 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('32', '个人护理', '2014-12-28 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('33', '按摩器', '2014-12-29 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('34', '血压计', '2014-12-31 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('35', '血糖仪', '2015-01-01 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('36', '其他健康电器', '2015-01-02 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('37', '五金家装', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('38', '奶粉', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('39', '尿裤', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('40', '婴儿洗护', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('41', '婴儿清洁', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('42', '其他洗护', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('43', '喂养用品', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('44', '婴儿电器', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('45', '维生素', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('46', '营养辅食', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('47', '婴儿车', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('48', '餐椅摇椅', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('49', '婴儿床', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('50', '安全防护', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('51', '家居服饰', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('52', '玩具乐器', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('53', '孕妈衣服', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('54', '待产/新生', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('55', '孕妈洗护', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('56', '孕期营养', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('57', '外衣/上衣', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('58', '裙裤/套装', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('59', '鞋子', '2015-01-03 14:50:11', '0', '1', null, '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('60', '银行', '2015-05-11 16:47:58', '0', '1', '', '0', '0', '3');

-- ----------------------------
-- Table structure for `ump_channel`
-- ----------------------------
DROP TABLE IF EXISTS `ump_channel`;
CREATE TABLE `ump_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `product` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iva7ivpdc6hjrktyynef6wwgp` (`product`),
  CONSTRAINT `ump_channel_ibfk_1` FOREIGN KEY (`product`) REFERENCES `ump_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_channel
-- ----------------------------
INSERT INTO `ump_channel` VALUES ('3', '多客服助手', null, null, '1', null, '0', '3');

-- ----------------------------
-- Table structure for `ump_company`
-- ----------------------------
DROP TABLE IF EXISTS `ump_company`;
CREATE TABLE `ump_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `company_code` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `max_account` int(11) NOT NULL,
  `mobile_phone` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `service_end_time` datetime DEFAULT NULL,
  `service_start_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `virtual_account` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2vbe7w5wu40hytiswfcv87fvg` (`company_code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company
-- ----------------------------
INSERT INTO `ump_company` VALUES ('1', '上海宜山路', '9', '2014-12-03 16:03:55', 'test@test.com', '1', '1', '11', '15001330968', '上海久科', '', '2015-02-26 16:04:50', '2014-12-01 16:05:00', '1', 'www.nineclient', '0', null);
INSERT INTO `ump_company` VALUES ('8', '广州', 'jiaohang', '2015-06-09 10:54:37', 'kelly.li@9client.com', '1', '1', '0', '13764559476', '广州交行', null, null, null, '1', 'www.nineclient', '1', null);
INSERT INTO `ump_company` VALUES ('11', 'shanghai', 'chb', '2015-06-18 14:15:19', '983313911@qq.com', '1', '1', '0', '18817713998', '9client', null, null, null, '1', 'www.baidu.com', '0', null);
INSERT INTO `ump_company` VALUES ('12', 'root', 's474450', '2015-10-08 17:35:20', '554512338@qq.com', '1', '0', '0', '15038023195', '测试5', null, null, null, '0', '', '0', null);
INSERT INTO `ump_company` VALUES ('14', 'root', 's45678', '2015-10-08 17:42:40', 'xiaoyingtao500@qq.com', '1', '0', '0', '15038023195', '测试3', null, null, null, '0', '', '1', null);
INSERT INTO `ump_company` VALUES ('15', '', 's56789', '2015-10-09 09:40:36', '15038023195@sina.cn', '1', '0', '0', '15038023195', '测试6', null, null, null, '0', '', '0', null);
INSERT INTO `ump_company` VALUES ('16', '', 'test', '2015-10-09 15:02:19', 'jade.lin@9client.com', '1', '1', '0', '13380888990', 'test', null, null, null, '1', '', '2', null);
INSERT INTO `ump_company` VALUES ('17', '泰隆银行大厦', 'tailong', '2016-04-28 10:58:21', 'tailong@163.com', '1', '1', '0', '18817781992', '泰隆银行', null, null, null, '1', 'www.baidu.com', '0', null);

-- ----------------------------
-- Table structure for `ump_company_bussiness_types`
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_bussiness_types`;
CREATE TABLE `ump_company_bussiness_types` (
  `ump_company` bigint(20) NOT NULL,
  `bussiness_types` bigint(20) NOT NULL,
  PRIMARY KEY (`ump_company`,`bussiness_types`),
  KEY `FK_js6jv0nw5q7lk4b3sawxgjl99` (`bussiness_types`),
  KEY `FK_4r96ta8n9v4cm1aq1hirmxhbi` (`ump_company`),
  CONSTRAINT `ump_company_bussiness_types_ibfk_1` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `ump_company_bussiness_types_ibfk_2` FOREIGN KEY (`bussiness_types`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_bussiness_types
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_company_parent_business_type`
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_parent_business_type`;
CREATE TABLE `ump_company_parent_business_type` (
  `company` bigint(20) NOT NULL,
  `parent_business_type` bigint(20) NOT NULL,
  PRIMARY KEY (`company`,`parent_business_type`),
  KEY `FK_duy7jmu7q803k9be9eq4ih76n` (`parent_business_type`),
  KEY `FK_q0vj89a128esgnfm6f31py9gm` (`company`),
  CONSTRAINT `ump_company_parent_business_type_ibfk_1` FOREIGN KEY (`parent_business_type`) REFERENCES `ump_parent_business_type` (`id`),
  CONSTRAINT `ump_company_parent_business_type_ibfk_2` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_parent_business_type
-- ----------------------------
INSERT INTO `ump_company_parent_business_type` VALUES ('17', '3');

-- ----------------------------
-- Table structure for `ump_company_service`
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_service`;
CREATE TABLE `ump_company_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(255) NOT NULL,
  `company_service_status` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `max_account` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `service_end_time` datetime NOT NULL,
  `service_start_time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  `version_id` bigint(20) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_service
-- ----------------------------
INSERT INTO `ump_company_service` VALUES ('7', 'jiaohang', '2', '2015-06-09 10:54:37', '5', '3', '2018-08-15 00:00:00', '2015-06-09 00:00:00', '2', '4', null);
INSERT INTO `ump_company_service` VALUES ('8', 'chb', '0', '2015-06-18 14:15:19', '5', '3', '2015-06-25 14:15:19', '2015-06-18 14:15:19', '0', '4', null);
INSERT INTO `ump_company_service` VALUES ('9', 's474450', '0', '2015-10-08 17:35:20', '5', '3', '2015-10-15 17:35:20', '2015-10-08 17:35:20', '0', '3', null);
INSERT INTO `ump_company_service` VALUES ('11', 's45678', '0', '2015-10-08 17:42:41', '5', '3', '2015-10-15 17:42:40', '2015-10-08 17:42:40', '0', '3', null);
INSERT INTO `ump_company_service` VALUES ('12', 's56789', '0', '2015-10-09 09:40:36', '5', '3', '2015-10-16 09:40:36', '2015-10-09 09:40:36', '0', '3', null);
INSERT INTO `ump_company_service` VALUES ('13', 'test', '0', '2015-10-09 15:02:19', '5', '3', '2015-10-24 00:00:00', '2015-10-10 00:00:00', '1', '3', null);
INSERT INTO `ump_company_service` VALUES ('14', 'tailong', '2', '2016-04-28 10:58:21', '5', '3', '2080-05-05 00:00:00', '2016-04-28 00:00:00', '1', '4', null);

-- ----------------------------
-- Table structure for `ump_company_service_channels`
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_service_channels`;
CREATE TABLE `ump_company_service_channels` (
  `company_services` bigint(20) NOT NULL,
  `channels` bigint(20) NOT NULL,
  PRIMARY KEY (`company_services`,`channels`),
  KEY `FK_cia0ebrdd2tw75d1go0l1lsod` (`channels`),
  KEY `FK_3k7bl3d7n6wmyf1r1iq8ua4ot` (`company_services`),
  CONSTRAINT `ump_company_service_channels_ibfk_1` FOREIGN KEY (`company_services`) REFERENCES `ump_company_service` (`id`),
  CONSTRAINT `ump_company_service_channels_ibfk_2` FOREIGN KEY (`channels`) REFERENCES `ump_channel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_service_channels
-- ----------------------------
INSERT INTO `ump_company_service_channels` VALUES ('14', '3');

-- ----------------------------
-- Table structure for `ump_config`
-- ----------------------------
DROP TABLE IF EXISTS `ump_config`;
CREATE TABLE `ump_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key_type` varchar(255) DEFAULT NULL,
  `key_value` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_config
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `ump_dictionary`;
CREATE TABLE `ump_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `type_code` varchar(200) DEFAULT NULL,
  `type_name` varchar(200) DEFAULT NULL,
  `type_title` varchar(200) DEFAULT NULL,
  `type_title_ch` varchar(200) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_log`
-- ----------------------------
DROP TABLE IF EXISTS `ump_log`;
CREATE TABLE `ump_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(120) NOT NULL,
  `is_status` tinyint(1) DEFAULT NULL,
  `login_ip` varchar(50) NOT NULL,
  `login_out_time` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=625 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_log
-- ----------------------------
INSERT INTO `ump_log` VALUES ('1', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:13:28', null, '0');
INSERT INTO `ump_log` VALUES ('2', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:14:02', null, '0');
INSERT INTO `ump_log` VALUES ('3', 'admin', '1', '192.168.253.1', '2016-03-07 17:34:29', '2016-03-07 17:23:23', null, '1');
INSERT INTO `ump_log` VALUES ('4', 'admin', '1', '192.168.253.1', '2016-03-07 17:37:08', '2016-03-07 17:34:16', null, '1');
INSERT INTO `ump_log` VALUES ('5', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:35:38', null, '0');
INSERT INTO `ump_log` VALUES ('6', 'admin', '1', '192.168.253.1', '2016-03-07 17:38:01', '2016-03-07 17:37:17', null, '1');
INSERT INTO `ump_log` VALUES ('7', 'admin', '1', '192.168.11.59', null, '2016-03-07 17:37:55', null, '0');
INSERT INTO `ump_log` VALUES ('8', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:38:53', null, '0');
INSERT INTO `ump_log` VALUES ('9', 'admin', '1', '192.168.11.59', '2016-03-07 17:42:08', '2016-03-07 17:41:56', null, '1');
INSERT INTO `ump_log` VALUES ('10', 'admin', '1', '192.168.11.59', '2016-03-07 17:43:14', '2016-03-07 17:42:17', null, '1');
INSERT INTO `ump_log` VALUES ('11', 'admin', '1', '192.168.11.59', null, '2016-03-07 17:43:24', null, '0');
INSERT INTO `ump_log` VALUES ('12', 'admin', '1', '192.168.11.59', null, '2016-03-07 17:49:16', null, '0');
INSERT INTO `ump_log` VALUES ('13', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:49:59', null, '0');
INSERT INTO `ump_log` VALUES ('14', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:52:29', null, '0');
INSERT INTO `ump_log` VALUES ('15', 'admin', '1', '192.168.253.1', null, '2016-03-07 17:54:40', null, '0');
INSERT INTO `ump_log` VALUES ('16', 'admin', '1', '192.168.12.65', null, '2016-03-07 18:17:06', null, '0');
INSERT INTO `ump_log` VALUES ('17', 'admin', '1', '192.168.253.1', null, '2016-03-07 18:28:46', null, '0');
INSERT INTO `ump_log` VALUES ('18', 'admin', '1', '192.168.253.1', null, '2016-03-07 19:47:36', null, '0');
INSERT INTO `ump_log` VALUES ('19', 'admin', '1', '192.168.253.1', null, '2016-03-07 19:54:54', null, '0');
INSERT INTO `ump_log` VALUES ('20', 'admin', '1', '192.168.253.1', null, '2016-03-07 20:14:10', null, '0');
INSERT INTO `ump_log` VALUES ('21', 'admin', '1', '192.168.253.1', null, '2016-03-07 22:29:58', null, '0');
INSERT INTO `ump_log` VALUES ('22', 'admin', '1', '192.168.253.1', null, '2016-03-07 23:34:20', null, '0');
INSERT INTO `ump_log` VALUES ('23', 'admin', '1', '192.168.253.1', null, '2016-03-07 23:49:09', null, '0');
INSERT INTO `ump_log` VALUES ('24', 'admin', '1', '192.168.253.1', null, '2016-03-07 23:51:14', null, '0');
INSERT INTO `ump_log` VALUES ('25', 'admin', '1', '192.168.253.1', null, '2016-03-07 23:58:59', null, '0');
INSERT INTO `ump_log` VALUES ('26', 'admin', '1', '192.168.253.1', null, '2016-03-08 00:22:56', null, '0');
INSERT INTO `ump_log` VALUES ('27', 'admin', '1', '192.168.253.1', null, '2016-03-08 00:32:07', null, '0');
INSERT INTO `ump_log` VALUES ('28', 'admin', '1', '192.168.253.1', null, '2016-03-08 00:43:33', null, '0');
INSERT INTO `ump_log` VALUES ('29', 'admin', '1', '192.168.253.1', null, '2016-03-08 09:16:55', null, '0');
INSERT INTO `ump_log` VALUES ('30', 'admin', '1', '192.168.253.1', null, '2016-03-08 09:17:14', null, '0');
INSERT INTO `ump_log` VALUES ('31', 'admin', '1', '192.168.11.59', null, '2016-03-08 09:34:24', null, '0');
INSERT INTO `ump_log` VALUES ('32', 'admin', '1', '192.168.253.1', null, '2016-03-08 09:38:37', null, '0');
INSERT INTO `ump_log` VALUES ('33', 'admin', '1', '192.168.11.59', null, '2016-03-08 09:50:29', null, '0');
INSERT INTO `ump_log` VALUES ('34', 'admin', '1', '192.168.253.1', null, '2016-03-08 09:53:53', null, '0');
INSERT INTO `ump_log` VALUES ('35', 'admin', '1', '192.168.11.59', null, '2016-03-08 09:57:09', null, '0');
INSERT INTO `ump_log` VALUES ('36', 'admin', '1', '192.168.253.1', null, '2016-03-08 10:00:02', null, '0');
INSERT INTO `ump_log` VALUES ('37', 'admin', '1', '192.168.11.59', null, '2016-03-08 10:20:31', null, '0');
INSERT INTO `ump_log` VALUES ('38', 'admin', '1', '192.168.11.59', null, '2016-03-08 10:21:05', null, '0');
INSERT INTO `ump_log` VALUES ('39', 'admin', '1', '192.168.253.1', null, '2016-03-08 10:21:24', null, '0');
INSERT INTO `ump_log` VALUES ('40', 'admin', '1', '192.168.11.59', null, '2016-03-08 10:28:18', null, '0');
INSERT INTO `ump_log` VALUES ('41', 'admin', '1', '10.117.203.129', null, '2016-03-08 11:04:05', null, '0');
INSERT INTO `ump_log` VALUES ('42', 'admin', '1', '192.168.253.1', null, '2016-03-08 11:21:24', null, '0');
INSERT INTO `ump_log` VALUES ('43', 'admin', '1', '192.168.253.1', null, '2016-03-08 11:40:04', null, '0');
INSERT INTO `ump_log` VALUES ('44', 'admin', '1', '192.168.11.59', null, '2016-03-08 11:41:52', null, '0');
INSERT INTO `ump_log` VALUES ('45', 'admin', '1', '192.168.11.59', null, '2016-03-08 11:49:14', null, '0');
INSERT INTO `ump_log` VALUES ('46', 'admin', '1', '192.168.253.1', null, '2016-03-08 11:52:33', null, '0');
INSERT INTO `ump_log` VALUES ('47', 'admin', '1', '192.168.253.1', null, '2016-03-08 11:57:13', null, '0');
INSERT INTO `ump_log` VALUES ('48', 'admin', '1', '192.168.11.59', null, '2016-03-08 13:39:55', null, '0');
INSERT INTO `ump_log` VALUES ('49', 'admin', '1', '192.168.253.1', null, '2016-03-08 13:40:13', null, '0');
INSERT INTO `ump_log` VALUES ('50', 'admin', '1', '192.168.11.59', null, '2016-03-08 15:16:45', null, '0');
INSERT INTO `ump_log` VALUES ('51', 'admin', '1', '192.168.253.1', null, '2016-03-08 15:30:04', null, '0');
INSERT INTO `ump_log` VALUES ('52', 'admin', '1', '192.168.253.1', null, '2016-03-08 15:48:07', null, '0');
INSERT INTO `ump_log` VALUES ('53', 'admin', '1', '192.168.253.1', null, '2016-03-08 18:02:35', null, '0');
INSERT INTO `ump_log` VALUES ('54', 'admin', '1', '192.168.253.1', null, '2016-03-08 18:03:15', null, '0');
INSERT INTO `ump_log` VALUES ('55', 'admin', '1', '192.168.253.1', null, '2016-03-08 18:04:12', null, '0');
INSERT INTO `ump_log` VALUES ('56', 'admin', '1', '192.168.253.1', null, '2016-03-08 18:07:18', null, '0');
INSERT INTO `ump_log` VALUES ('57', 'admin', '1', '192.168.253.1', null, '2016-03-08 18:11:46', null, '0');
INSERT INTO `ump_log` VALUES ('58', 'admin', '1', '192.168.253.1', null, '2016-03-08 20:06:23', null, '0');
INSERT INTO `ump_log` VALUES ('59', 'admin', '1', '192.168.253.1', null, '2016-03-08 21:20:28', null, '0');
INSERT INTO `ump_log` VALUES ('60', 'admin', '1', '192.168.11.59', null, '2016-03-09 09:59:56', null, '0');
INSERT INTO `ump_log` VALUES ('61', 'admin', '1', '192.168.11.59', null, '2016-03-09 10:30:07', null, '0');
INSERT INTO `ump_log` VALUES ('62', 'admin', '1', '192.168.11.59', null, '2016-03-09 11:04:49', null, '0');
INSERT INTO `ump_log` VALUES ('63', 'admin', '1', '192.168.253.1', null, '2016-03-09 11:14:46', null, '0');
INSERT INTO `ump_log` VALUES ('64', 'admin', '1', '192.168.253.1', null, '2016-03-09 11:20:04', null, '0');
INSERT INTO `ump_log` VALUES ('65', 'admin', '1', '192.168.253.1', null, '2016-03-09 11:34:51', null, '0');
INSERT INTO `ump_log` VALUES ('66', 'admin', '1', '192.168.11.59', null, '2016-03-09 11:48:06', null, '0');
INSERT INTO `ump_log` VALUES ('67', 'admin', '1', '192.168.253.1', null, '2016-03-09 11:54:02', null, '0');
INSERT INTO `ump_log` VALUES ('68', 'admin', '1', '192.168.11.59', null, '2016-03-09 12:01:16', null, '0');
INSERT INTO `ump_log` VALUES ('69', 'admin', '1', '192.168.11.59', null, '2016-03-09 13:41:59', null, '0');
INSERT INTO `ump_log` VALUES ('70', 'admin', '1', '192.168.253.1', null, '2016-03-09 13:45:22', null, '0');
INSERT INTO `ump_log` VALUES ('71', 'admin', '1', '192.168.253.1', null, '2016-03-09 14:34:53', null, '0');
INSERT INTO `ump_log` VALUES ('72', 'admin', '1', '192.168.253.1', null, '2016-03-09 15:18:24', null, '0');
INSERT INTO `ump_log` VALUES ('73', 'admin', '1', '192.168.253.1', null, '2016-03-09 15:41:37', null, '0');
INSERT INTO `ump_log` VALUES ('74', 'admin', '1', '192.168.12.65', null, '2016-03-09 15:48:56', null, '0');
INSERT INTO `ump_log` VALUES ('75', 'admin', '1', '192.168.253.1', null, '2016-03-09 16:12:00', null, '0');
INSERT INTO `ump_log` VALUES ('76', 'admin', '1', '192.168.253.1', null, '2016-03-09 16:26:59', null, '0');
INSERT INTO `ump_log` VALUES ('77', 'admin', '1', '192.168.253.1', null, '2016-03-09 16:45:07', null, '0');
INSERT INTO `ump_log` VALUES ('78', 'admin', '1', '192.168.253.1', null, '2016-03-09 16:49:03', null, '0');
INSERT INTO `ump_log` VALUES ('79', 'admin', '1', '192.168.253.1', null, '2016-03-09 16:53:10', null, '0');
INSERT INTO `ump_log` VALUES ('80', 'admin', '1', '192.168.253.1', null, '2016-03-09 17:06:00', null, '0');
INSERT INTO `ump_log` VALUES ('81', 'admin', '1', '192.168.253.1', null, '2016-03-09 17:15:19', null, '0');
INSERT INTO `ump_log` VALUES ('82', 'admin', '1', '192.168.11.59', null, '2016-03-09 17:41:47', null, '0');
INSERT INTO `ump_log` VALUES ('83', 'admin', '1', '192.168.11.59', null, '2016-03-09 18:19:34', null, '0');
INSERT INTO `ump_log` VALUES ('84', 'admin', '1', '192.168.253.1', null, '2016-03-09 18:46:01', null, '0');
INSERT INTO `ump_log` VALUES ('85', 'admin', '1', '192.168.11.59', null, '2016-03-09 19:19:42', null, '0');
INSERT INTO `ump_log` VALUES ('86', 'admin', '1', '192.168.253.1', null, '2016-03-09 19:23:14', null, '0');
INSERT INTO `ump_log` VALUES ('87', 'admin', '1', '192.168.253.1', null, '2016-03-09 19:25:00', null, '0');
INSERT INTO `ump_log` VALUES ('88', 'admin', '1', '192.168.11.59', null, '2016-03-09 19:32:24', null, '0');
INSERT INTO `ump_log` VALUES ('89', 'admin', '1', '192.168.253.1', null, '2016-03-09 19:40:12', null, '0');
INSERT INTO `ump_log` VALUES ('90', 'admin', '1', '192.168.253.1', null, '2016-03-09 19:44:12', null, '0');
INSERT INTO `ump_log` VALUES ('91', 'admin', '1', '192.168.11.59', null, '2016-03-09 19:46:43', null, '0');
INSERT INTO `ump_log` VALUES ('92', 'admin', '1', '192.168.11.59', null, '2016-03-09 19:58:35', null, '0');
INSERT INTO `ump_log` VALUES ('93', 'admin', '1', '192.168.253.1', null, '2016-03-09 20:00:16', null, '0');
INSERT INTO `ump_log` VALUES ('94', 'admin', '1', '10.117.203.129', '2016-03-09 20:20:54', '2016-03-09 20:05:03', null, '1');
INSERT INTO `ump_log` VALUES ('95', 'admin', '1', '192.168.253.1', '2016-03-09 20:18:03', '2016-03-09 20:15:37', null, '1');
INSERT INTO `ump_log` VALUES ('96', 'admin', '1', '192.168.253.1', '2016-03-09 20:19:12', '2016-03-09 20:18:49', null, '1');
INSERT INTO `ump_log` VALUES ('97', 'admin', '1', '192.168.253.1', '2016-03-09 20:20:21', '2016-03-09 20:19:56', null, '1');
INSERT INTO `ump_log` VALUES ('98', 'susie', '1', '10.117.203.129', null, '2016-03-09 20:23:26', null, '0');
INSERT INTO `ump_log` VALUES ('99', 'kelly', '1', '10.117.203.129', null, '2016-03-09 20:23:42', null, '0');
INSERT INTO `ump_log` VALUES ('100', 'admin', '1', '10.117.203.129', '2016-03-09 20:25:23', '2016-03-09 20:24:48', null, '1');
INSERT INTO `ump_log` VALUES ('101', 'kelly', '1', '10.117.203.129', null, '2016-03-09 20:25:36', null, '0');
INSERT INTO `ump_log` VALUES ('102', 'susie', '1', '10.117.203.129', null, '2016-03-09 20:25:50', null, '0');
INSERT INTO `ump_log` VALUES ('103', 'kelly', '1', '10.117.203.129', null, '2016-03-09 20:26:55', null, '0');
INSERT INTO `ump_log` VALUES ('104', 'admin', '1', '10.117.203.129', '2016-03-09 20:28:17', '2016-03-09 20:27:37', null, '1');
INSERT INTO `ump_log` VALUES ('105', 'kelly', '1', '10.117.203.129', '2016-03-09 20:29:33', '2016-03-09 20:28:41', null, '1');
INSERT INTO `ump_log` VALUES ('106', 'admin', '1', '10.117.203.129', null, '2016-03-09 20:29:45', null, '0');
INSERT INTO `ump_log` VALUES ('107', 'kelly', '1', '192.168.253.1', null, '2016-03-09 20:30:31', null, '0');
INSERT INTO `ump_log` VALUES ('108', 'admin', '1', '10.117.203.129', null, '2016-03-09 20:49:02', null, '0');
INSERT INTO `ump_log` VALUES ('109', 'admin', '1', '10.117.203.129', null, '2016-03-09 20:52:09', null, '0');
INSERT INTO `ump_log` VALUES ('110', 'admin', '1', '10.117.203.129', null, '2016-03-09 20:52:48', null, '0');
INSERT INTO `ump_log` VALUES ('111', 'admin', '1', '192.168.253.1', null, '2016-03-09 22:04:43', null, '0');
INSERT INTO `ump_log` VALUES ('112', 'admin', '1', '192.168.253.1', null, '2016-03-09 22:28:01', null, '0');
INSERT INTO `ump_log` VALUES ('113', 'admin', '1', '10.117.203.129', null, '2016-03-09 23:04:40', null, '0');
INSERT INTO `ump_log` VALUES ('114', 'admin', '1', '192.168.253.1', null, '2016-03-09 23:26:17', null, '0');
INSERT INTO `ump_log` VALUES ('115', 'kelly', '1', '10.117.203.129', null, '2016-03-10 09:28:00', null, '0');
INSERT INTO `ump_log` VALUES ('116', 'admin', '1', '10.117.203.129', null, '2016-03-10 09:31:08', null, '0');
INSERT INTO `ump_log` VALUES ('117', 'admin', '1', '10.117.203.129', null, '2016-03-10 09:32:13', null, '0');
INSERT INTO `ump_log` VALUES ('118', 'admin', '1', '10.117.203.129', null, '2016-03-10 09:36:01', null, '0');
INSERT INTO `ump_log` VALUES ('119', 'admin', '1', '10.117.203.129', null, '2016-03-10 09:37:42', null, '0');
INSERT INTO `ump_log` VALUES ('120', 'admin', '1', '192.168.253.1', null, '2016-03-10 09:51:40', null, '0');
INSERT INTO `ump_log` VALUES ('121', 'admin', '1', '192.168.11.59', null, '2016-03-10 10:41:29', null, '0');
INSERT INTO `ump_log` VALUES ('122', 'admin', '1', '192.168.253.1', null, '2016-03-10 10:42:17', null, '0');
INSERT INTO `ump_log` VALUES ('123', 'admin', '1', '192.168.253.1', null, '2016-03-10 10:56:09', null, '0');
INSERT INTO `ump_log` VALUES ('124', 'admin', '1', '192.168.253.1', null, '2016-03-10 10:58:57', null, '0');
INSERT INTO `ump_log` VALUES ('125', 'admin', '1', '192.168.253.1', null, '2016-03-10 11:03:49', null, '0');
INSERT INTO `ump_log` VALUES ('126', 'admin', '1', '192.168.253.1', null, '2016-03-10 11:12:24', null, '0');
INSERT INTO `ump_log` VALUES ('127', 'admin', '1', '192.168.253.1', null, '2016-03-10 11:16:28', null, '0');
INSERT INTO `ump_log` VALUES ('128', 'admin', '1', '192.168.253.1', null, '2016-03-10 11:33:22', null, '0');
INSERT INTO `ump_log` VALUES ('129', 'admin', '1', '192.168.253.1', null, '2016-03-10 13:59:24', null, '0');
INSERT INTO `ump_log` VALUES ('130', 'admin', '1', '192.168.11.59', null, '2016-03-10 14:14:41', null, '0');
INSERT INTO `ump_log` VALUES ('131', 'admin', '1', '192.168.11.59', null, '2016-03-10 14:15:34', null, '0');
INSERT INTO `ump_log` VALUES ('132', 'admin', '1', '192.168.253.1', null, '2016-03-10 14:54:05', null, '0');
INSERT INTO `ump_log` VALUES ('133', 'admin', '1', '192.168.253.1', null, '2016-03-10 15:07:12', null, '0');
INSERT INTO `ump_log` VALUES ('134', 'admin', '1', '192.168.11.59', null, '2016-03-10 15:32:31', null, '0');
INSERT INTO `ump_log` VALUES ('135', 'admin', '1', '192.168.253.1', null, '2016-03-10 15:52:12', null, '0');
INSERT INTO `ump_log` VALUES ('136', 'admin', '1', '192.168.11.59', null, '2016-03-10 15:54:02', null, '0');
INSERT INTO `ump_log` VALUES ('137', 'admin', '1', '192.168.253.1', null, '2016-03-10 15:57:28', null, '0');
INSERT INTO `ump_log` VALUES ('138', 'admin', '1', '192.168.253.1', null, '2016-03-10 15:58:32', null, '0');
INSERT INTO `ump_log` VALUES ('139', 'admin', '1', '192.168.11.59', null, '2016-03-10 16:02:57', null, '0');
INSERT INTO `ump_log` VALUES ('140', 'admin', '1', '192.168.253.1', null, '2016-03-10 16:03:19', null, '0');
INSERT INTO `ump_log` VALUES ('141', 'admin', '1', '10.117.203.129', null, '2016-03-10 16:15:10', null, '0');
INSERT INTO `ump_log` VALUES ('142', 'admin', '1', '192.168.11.59', null, '2016-03-10 16:28:20', null, '0');
INSERT INTO `ump_log` VALUES ('143', 'admin', '1', '10.117.203.129', null, '2016-03-10 16:53:41', null, '0');
INSERT INTO `ump_log` VALUES ('144', 'admin', '1', '192.168.11.59', null, '2016-03-10 17:18:10', null, '0');
INSERT INTO `ump_log` VALUES ('145', 'admin', '1', '192.168.253.1', null, '2016-03-10 17:31:38', null, '0');
INSERT INTO `ump_log` VALUES ('146', 'admin', '1', '192.168.253.1', null, '2016-03-10 18:24:41', null, '0');
INSERT INTO `ump_log` VALUES ('147', 'admin', '1', '192.168.253.1', null, '2016-03-10 18:27:30', null, '0');
INSERT INTO `ump_log` VALUES ('148', 'admin', '1', '192.168.253.1', null, '2016-03-10 19:08:56', null, '0');
INSERT INTO `ump_log` VALUES ('149', 'admin', '1', '192.168.253.1', null, '2016-03-10 19:13:58', null, '0');
INSERT INTO `ump_log` VALUES ('150', 'admin', '1', '192.168.11.59', null, '2016-03-10 19:33:14', null, '0');
INSERT INTO `ump_log` VALUES ('151', 'admin', '1', '192.168.253.1', null, '2016-03-10 19:33:57', null, '0');
INSERT INTO `ump_log` VALUES ('152', 'admin', '1', '192.168.253.1', null, '2016-03-10 19:48:09', null, '0');
INSERT INTO `ump_log` VALUES ('153', 'admin', '1', '192.168.253.1', null, '2016-03-10 21:51:13', null, '0');
INSERT INTO `ump_log` VALUES ('154', 'admin', '1', '192.168.253.1', null, '2016-03-10 22:28:25', null, '0');
INSERT INTO `ump_log` VALUES ('155', 'admin', '1', '192.168.253.1', null, '2016-03-10 22:43:12', null, '0');
INSERT INTO `ump_log` VALUES ('156', 'admin', '1', '192.168.253.1', null, '2016-03-10 22:54:04', null, '0');
INSERT INTO `ump_log` VALUES ('157', 'admin', '1', '192.168.253.1', null, '2016-03-10 23:06:20', null, '0');
INSERT INTO `ump_log` VALUES ('158', 'admin', '1', '192.168.253.1', null, '2016-03-10 23:09:07', null, '0');
INSERT INTO `ump_log` VALUES ('159', 'admin', '1', '192.168.253.1', null, '2016-03-10 23:11:11', null, '0');
INSERT INTO `ump_log` VALUES ('160', 'admin', '1', '192.168.253.1', null, '2016-03-10 23:20:39', null, '0');
INSERT INTO `ump_log` VALUES ('161', 'admin', '1', '192.168.253.1', null, '2016-03-10 23:34:44', null, '0');
INSERT INTO `ump_log` VALUES ('162', 'admin', '1', '192.168.253.1', null, '2016-03-11 09:13:30', null, '0');
INSERT INTO `ump_log` VALUES ('163', 'admin', '1', '192.168.11.59', null, '2016-03-11 09:31:20', null, '0');
INSERT INTO `ump_log` VALUES ('164', 'admin', '1', '192.168.11.59', null, '2016-03-11 09:36:07', null, '0');
INSERT INTO `ump_log` VALUES ('165', 'admin', '1', '192.168.253.1', null, '2016-03-11 09:36:06', null, '0');
INSERT INTO `ump_log` VALUES ('166', 'admin', '1', '192.168.11.59', null, '2016-03-11 09:50:33', null, '0');
INSERT INTO `ump_log` VALUES ('167', 'admin', '1', '192.168.253.1', null, '2016-03-11 11:55:48', null, '0');
INSERT INTO `ump_log` VALUES ('168', 'admin', '1', '192.168.11.59', null, '2016-03-11 12:50:52', null, '0');
INSERT INTO `ump_log` VALUES ('169', 'admin', '1', '192.168.253.1', null, '2016-03-11 13:49:48', null, '0');
INSERT INTO `ump_log` VALUES ('170', 'admin', '1', '192.168.11.59', null, '2016-03-11 14:20:48', null, '0');
INSERT INTO `ump_log` VALUES ('171', 'admin', '1', '192.168.253.1', '2016-03-11 14:48:52', '2016-03-11 14:44:18', null, '1');
INSERT INTO `ump_log` VALUES ('172', 'kelly', '1', '192.168.253.1', '2016-03-11 14:55:12', '2016-03-11 14:49:05', null, '1');
INSERT INTO `ump_log` VALUES ('173', 'admin', '1', '192.168.253.1', null, '2016-03-11 14:50:33', null, '0');
INSERT INTO `ump_log` VALUES ('174', 'admin', '1', '192.168.253.1', null, '2016-03-11 14:55:25', null, '0');
INSERT INTO `ump_log` VALUES ('175', 'admin', '1', '192.168.11.59', null, '2016-03-11 14:55:34', null, '0');
INSERT INTO `ump_log` VALUES ('176', 'admin', '1', '192.168.253.1', null, '2016-03-11 14:57:30', null, '0');
INSERT INTO `ump_log` VALUES ('177', 'admin', '1', '192.168.253.1', null, '2016-03-11 14:57:57', null, '0');
INSERT INTO `ump_log` VALUES ('178', 'admin', '1', '192.168.253.1', null, '2016-03-11 14:58:16', null, '0');
INSERT INTO `ump_log` VALUES ('179', 'admin', '1', '192.168.11.59', null, '2016-03-11 15:28:47', null, '0');
INSERT INTO `ump_log` VALUES ('180', 'admin', '1', '192.168.11.59', null, '2016-03-11 15:52:59', null, '0');
INSERT INTO `ump_log` VALUES ('181', 'admin', '1', '192.168.11.59', null, '2016-03-11 16:04:41', null, '0');
INSERT INTO `ump_log` VALUES ('182', 'admin', '1', '192.168.11.59', null, '2016-03-11 16:06:29', null, '0');
INSERT INTO `ump_log` VALUES ('183', 'admin', '1', '192.168.253.1', null, '2016-03-11 16:10:17', null, '0');
INSERT INTO `ump_log` VALUES ('184', 'admin', '1', '192.168.253.1', null, '2016-03-11 16:17:48', null, '0');
INSERT INTO `ump_log` VALUES ('185', 'admin', '1', '192.168.253.1', null, '2016-03-11 16:35:54', null, '0');
INSERT INTO `ump_log` VALUES ('186', 'admin', '1', '192.168.253.1', null, '2016-03-11 16:47:23', null, '0');
INSERT INTO `ump_log` VALUES ('187', 'admin', '1', '192.168.253.1', null, '2016-03-11 16:59:56', null, '0');
INSERT INTO `ump_log` VALUES ('188', 'admin', '1', '10.117.203.129', null, '2016-03-11 17:21:59', null, '0');
INSERT INTO `ump_log` VALUES ('189', 'admin', '1', '192.168.253.1', null, '2016-03-11 17:57:17', null, '0');
INSERT INTO `ump_log` VALUES ('190', 'admin', '1', '192.168.253.1', null, '2016-03-11 18:15:44', null, '0');
INSERT INTO `ump_log` VALUES ('191', 'admin', '1', '192.168.253.1', null, '2016-03-11 18:19:55', null, '0');
INSERT INTO `ump_log` VALUES ('192', 'admin', '1', '192.168.253.1', null, '2016-03-11 18:25:29', null, '0');
INSERT INTO `ump_log` VALUES ('193', 'admin', '1', '192.168.253.1', null, '2016-03-11 18:28:30', null, '0');
INSERT INTO `ump_log` VALUES ('194', 'admin', '1', '192.168.253.1', null, '2016-03-11 18:30:32', null, '0');
INSERT INTO `ump_log` VALUES ('195', 'admin', '1', '192.168.11.78', null, '2016-03-11 18:53:57', null, '0');
INSERT INTO `ump_log` VALUES ('196', 'admin', '1', '192.168.253.1', null, '2016-03-11 18:55:36', null, '0');
INSERT INTO `ump_log` VALUES ('197', 'admin', '1', '10.117.203.129', null, '2016-03-12 00:25:40', null, '0');
INSERT INTO `ump_log` VALUES ('198', 'admin', '1', '10.117.203.129', null, '2016-03-12 00:35:10', null, '0');
INSERT INTO `ump_log` VALUES ('199', 'admin', '1', '192.168.11.59', null, '2016-03-12 10:26:48', null, '0');
INSERT INTO `ump_log` VALUES ('200', 'admin', '1', '10.117.203.129', null, '2016-03-12 10:59:41', null, '0');
INSERT INTO `ump_log` VALUES ('201', 'admin', '1', '192.168.11.59', null, '2016-03-12 11:04:31', null, '0');
INSERT INTO `ump_log` VALUES ('202', 'admin', '1', '192.168.253.1', null, '2016-03-12 11:24:57', null, '0');
INSERT INTO `ump_log` VALUES ('203', 'admin', '1', '192.168.253.1', null, '2016-03-12 11:29:18', null, '0');
INSERT INTO `ump_log` VALUES ('204', 'admin', '1', '192.168.253.1', null, '2016-03-12 11:30:06', null, '0');
INSERT INTO `ump_log` VALUES ('205', 'admin', '1', '192.168.11.59', null, '2016-03-12 11:50:57', null, '0');
INSERT INTO `ump_log` VALUES ('206', 'admin', '1', '192.168.11.59', null, '2016-03-12 12:06:54', null, '0');
INSERT INTO `ump_log` VALUES ('207', 'admin', '1', '192.168.253.1', null, '2016-03-12 12:07:24', null, '0');
INSERT INTO `ump_log` VALUES ('208', 'admin', '1', '192.168.253.1', null, '2016-03-12 14:20:31', null, '0');
INSERT INTO `ump_log` VALUES ('209', 'admin', '1', '10.117.203.129', null, '2016-03-12 14:22:38', null, '0');
INSERT INTO `ump_log` VALUES ('210', 'admin', '1', '192.168.11.59', null, '2016-03-12 14:24:35', null, '0');
INSERT INTO `ump_log` VALUES ('211', 'admin', '1', '192.168.11.59', null, '2016-03-12 14:45:30', null, '0');
INSERT INTO `ump_log` VALUES ('212', 'admin', '1', '192.168.253.1', null, '2016-03-12 14:54:29', null, '0');
INSERT INTO `ump_log` VALUES ('213', 'susie', '1', '192.168.253.1', null, '2016-03-12 15:05:25', null, '0');
INSERT INTO `ump_log` VALUES ('214', 'susie', '1', '192.168.253.1', null, '2016-03-12 15:09:15', null, '0');
INSERT INTO `ump_log` VALUES ('215', 'susie', '1', '192.168.253.1', null, '2016-03-12 15:15:20', null, '0');
INSERT INTO `ump_log` VALUES ('216', 'admin', '1', '10.117.203.129', null, '2016-03-12 15:40:51', null, '0');
INSERT INTO `ump_log` VALUES ('217', 'admin', '1', '10.117.203.129', null, '2016-03-12 15:43:18', null, '0');
INSERT INTO `ump_log` VALUES ('218', 'admin', '1', '10.117.203.129', null, '2016-03-12 15:46:43', null, '0');
INSERT INTO `ump_log` VALUES ('219', 'admin', '1', '10.117.203.129', '2016-03-12 17:01:06', '2016-03-12 15:55:52', null, '1');
INSERT INTO `ump_log` VALUES ('220', 'admin', '1', '192.168.11.59', null, '2016-03-12 16:13:49', null, '0');
INSERT INTO `ump_log` VALUES ('221', 'susie', '1', '192.168.253.1', null, '2016-03-12 16:24:30', null, '0');
INSERT INTO `ump_log` VALUES ('222', 'admin', '1', '192.168.253.1', '2016-03-12 16:50:26', '2016-03-12 16:50:06', null, '1');
INSERT INTO `ump_log` VALUES ('223', 'susie', '1', '192.168.253.1', null, '2016-03-12 16:50:38', null, '0');
INSERT INTO `ump_log` VALUES ('224', 'susie', '1', '192.168.253.1', null, '2016-03-12 16:52:27', null, '0');
INSERT INTO `ump_log` VALUES ('225', 'susie', '1', '192.168.253.1', null, '2016-03-12 16:54:34', null, '0');
INSERT INTO `ump_log` VALUES ('226', 'admin', '1', '192.168.11.59', null, '2016-03-12 16:55:54', null, '0');
INSERT INTO `ump_log` VALUES ('227', 'susie', '1', '192.168.253.1', null, '2016-03-12 16:59:43', null, '0');
INSERT INTO `ump_log` VALUES ('228', 'kelly', '1', '10.117.203.129', null, '2016-03-12 17:01:29', null, '0');
INSERT INTO `ump_log` VALUES ('229', 'susie', '1', '192.168.253.1', null, '2016-03-12 17:02:15', null, '0');
INSERT INTO `ump_log` VALUES ('230', 'susie', '1', '192.168.253.1', null, '2016-03-12 17:11:55', null, '0');
INSERT INTO `ump_log` VALUES ('231', 'susie', '1', '192.168.253.1', null, '2016-03-12 17:23:25', null, '0');
INSERT INTO `ump_log` VALUES ('232', 'susie', '1', '192.168.253.1', null, '2016-03-12 18:11:04', null, '0');
INSERT INTO `ump_log` VALUES ('233', 'susie', '1', '192.168.253.1', null, '2016-03-12 18:19:31', null, '0');
INSERT INTO `ump_log` VALUES ('234', 'susie', '1', '192.168.253.1', null, '2016-03-12 18:58:55', null, '0');
INSERT INTO `ump_log` VALUES ('235', 'susie', '1', '192.168.253.1', null, '2016-03-12 19:23:11', null, '0');
INSERT INTO `ump_log` VALUES ('236', 'susie', '1', '192.168.253.1', null, '2016-03-12 22:30:25', null, '0');
INSERT INTO `ump_log` VALUES ('237', 'admin', '1', '192.168.253.1', null, '2016-03-12 22:48:59', null, '0');
INSERT INTO `ump_log` VALUES ('238', 'admin', '1', '192.168.253.1', null, '2016-03-12 22:56:33', null, '0');
INSERT INTO `ump_log` VALUES ('239', 'susie', '1', '192.168.253.1', null, '2016-03-12 23:03:23', null, '0');
INSERT INTO `ump_log` VALUES ('240', 'susie', '1', '192.168.253.1', null, '2016-03-12 23:21:09', null, '0');
INSERT INTO `ump_log` VALUES ('241', 'susie', '1', '192.168.253.1', null, '2016-03-12 23:28:34', null, '0');
INSERT INTO `ump_log` VALUES ('242', 'susie', '1', '10.117.203.129', null, '2016-03-13 11:57:49', null, '0');
INSERT INTO `ump_log` VALUES ('243', 'admin', '1', '192.168.1.11', null, '2016-03-13 12:02:36', null, '0');
INSERT INTO `ump_log` VALUES ('244', 'susie', '1', '192.168.253.1', null, '2016-03-13 12:09:56', null, '0');
INSERT INTO `ump_log` VALUES ('245', 'admin', '1', '192.168.1.11', null, '2016-03-13 12:51:30', null, '0');
INSERT INTO `ump_log` VALUES ('246', 'susie', '1', '192.168.253.1', null, '2016-03-13 13:26:47', null, '0');
INSERT INTO `ump_log` VALUES ('247', 'admin', '1', '192.168.253.1', null, '2016-03-13 13:27:22', null, '0');
INSERT INTO `ump_log` VALUES ('248', 'admin', '1', '192.168.1.11', null, '2016-03-13 13:31:00', null, '0');
INSERT INTO `ump_log` VALUES ('249', 'susie', '1', '192.168.253.1', null, '2016-03-13 13:32:44', null, '0');
INSERT INTO `ump_log` VALUES ('250', 'admin', '1', '192.168.1.11', null, '2016-03-13 14:33:50', null, '0');
INSERT INTO `ump_log` VALUES ('251', 'admin', '1', '192.168.1.11', null, '2016-03-13 15:03:49', null, '0');
INSERT INTO `ump_log` VALUES ('252', 'susie', '1', '192.168.253.1', null, '2016-03-13 15:21:36', null, '0');
INSERT INTO `ump_log` VALUES ('253', 'admin', '1', '192.168.1.11', null, '2016-03-13 15:32:39', null, '0');
INSERT INTO `ump_log` VALUES ('254', 'admin', '1', '192.168.253.1', null, '2016-03-13 15:55:49', null, '0');
INSERT INTO `ump_log` VALUES ('255', 'admin', '1', '192.168.1.11', null, '2016-03-13 15:58:22', null, '0');
INSERT INTO `ump_log` VALUES ('256', 'admin', '1', '192.168.1.11', null, '2016-03-13 16:10:04', null, '0');
INSERT INTO `ump_log` VALUES ('257', 'admin', '1', '192.168.253.1', null, '2016-03-13 16:13:51', null, '0');
INSERT INTO `ump_log` VALUES ('258', 'admin', '1', '192.168.253.1', null, '2016-03-13 16:14:14', null, '0');
INSERT INTO `ump_log` VALUES ('259', 'admin', '1', '192.168.253.1', null, '2016-03-13 16:23:12', null, '0');
INSERT INTO `ump_log` VALUES ('260', 'admin', '1', '192.168.253.1', null, '2016-03-13 16:23:40', null, '0');
INSERT INTO `ump_log` VALUES ('261', 'admin', '1', '192.168.253.1', null, '2016-03-13 16:34:02', null, '0');
INSERT INTO `ump_log` VALUES ('262', 'admin', '1', '192.168.253.1', null, '2016-03-13 16:34:26', null, '0');
INSERT INTO `ump_log` VALUES ('263', 'admin', '1', '192.168.1.11', null, '2016-03-13 17:13:27', null, '0');
INSERT INTO `ump_log` VALUES ('264', 'susie', '1', '10.117.203.129', null, '2016-03-13 21:12:15', null, '0');
INSERT INTO `ump_log` VALUES ('265', 'admin', '1', '10.117.203.129', null, '2016-03-13 21:12:40', null, '0');
INSERT INTO `ump_log` VALUES ('266', 'admin', '1', '192.168.253.1', null, '2016-03-13 21:59:21', null, '0');
INSERT INTO `ump_log` VALUES ('267', 'admin', '1', '192.168.253.1', null, '2016-03-13 22:24:40', null, '0');
INSERT INTO `ump_log` VALUES ('268', 'admin', '1', '192.168.253.1', null, '2016-03-13 22:31:25', null, '0');
INSERT INTO `ump_log` VALUES ('269', 'admin', '1', '10.117.203.129', null, '2016-03-13 23:11:47', null, '0');
INSERT INTO `ump_log` VALUES ('270', 'admin', '1', '10.117.203.129', null, '2016-03-14 09:10:01', null, '0');
INSERT INTO `ump_log` VALUES ('271', 'admin', '1', '192.168.253.1', null, '2016-03-14 09:13:22', null, '0');
INSERT INTO `ump_log` VALUES ('272', 'admin', '1', '192.168.253.1', null, '2016-03-14 09:22:47', null, '0');
INSERT INTO `ump_log` VALUES ('273', 'admin', '1', '192.168.253.1', null, '2016-03-14 09:22:51', null, '0');
INSERT INTO `ump_log` VALUES ('274', 'admin', '1', '192.168.253.1', null, '2016-03-14 09:35:13', null, '0');
INSERT INTO `ump_log` VALUES ('275', 'admin', '1', '192.168.253.1', null, '2016-03-14 09:39:54', null, '0');
INSERT INTO `ump_log` VALUES ('276', 'admin', '1', '10.117.203.129', null, '2016-03-14 09:39:52', null, '0');
INSERT INTO `ump_log` VALUES ('277', 'admin', '1', '10.117.203.129', null, '2016-03-14 09:42:36', null, '0');
INSERT INTO `ump_log` VALUES ('278', 'admin', '1', '192.168.11.59', '2016-03-14 23:44:29', '2016-03-14 09:42:39', null, '1');
INSERT INTO `ump_log` VALUES ('279', 'admin', '1', '192.168.253.1', null, '2016-03-14 09:45:57', null, '0');
INSERT INTO `ump_log` VALUES ('280', 'admin', '1', '192.168.11.59', null, '2016-03-14 09:50:00', null, '0');
INSERT INTO `ump_log` VALUES ('281', 'admin', '1', '192.168.11.59', '2016-03-14 09:50:42', '2016-03-14 09:50:33', null, '1');
INSERT INTO `ump_log` VALUES ('282', 'kelly', '1', '192.168.11.59', '2016-03-14 09:53:29', '2016-03-14 09:51:00', null, '1');
INSERT INTO `ump_log` VALUES ('283', 'admin', '1', '192.168.11.59', null, '2016-03-14 09:53:41', null, '0');
INSERT INTO `ump_log` VALUES ('284', 'admin', '1', '10.117.203.129', null, '2016-03-14 10:09:56', null, '0');
INSERT INTO `ump_log` VALUES ('285', 'admin', '1', '192.168.11.59', null, '2016-03-14 10:19:45', null, '0');
INSERT INTO `ump_log` VALUES ('286', 'admin', '1', '10.117.203.129', null, '2016-03-14 10:24:01', null, '0');
INSERT INTO `ump_log` VALUES ('287', 'admin', '1', '192.168.253.1', null, '2016-03-14 10:24:19', null, '0');
INSERT INTO `ump_log` VALUES ('288', 'admin', '1', '10.117.203.129', null, '2016-03-14 10:24:59', null, '0');
INSERT INTO `ump_log` VALUES ('289', 'admin', '1', '10.117.203.129', null, '2016-03-14 10:25:12', null, '0');
INSERT INTO `ump_log` VALUES ('290', 'admin', '1', '10.117.203.129', '2016-03-14 10:30:32', '2016-03-14 10:29:10', null, '1');
INSERT INTO `ump_log` VALUES ('291', 'admin', '1', '10.117.203.129', null, '2016-03-14 10:41:36', null, '0');
INSERT INTO `ump_log` VALUES ('292', 'admin', '1', '10.117.203.129', null, '2016-03-14 11:00:10', null, '0');
INSERT INTO `ump_log` VALUES ('293', 'admin', '1', '10.117.203.129', null, '2016-03-14 11:00:27', null, '0');
INSERT INTO `ump_log` VALUES ('294', 'admin', '1', '10.117.203.129', null, '2016-03-14 11:05:58', null, '0');
INSERT INTO `ump_log` VALUES ('295', 'admin', '1', '192.168.253.1', null, '2016-03-14 11:46:03', null, '0');
INSERT INTO `ump_log` VALUES ('296', 'admin', '1', '192.168.253.1', null, '2016-03-14 13:44:42', null, '0');
INSERT INTO `ump_log` VALUES ('297', 'admin', '1', '192.168.11.59', '2016-03-14 14:22:28', '2016-03-14 13:52:07', null, '1');
INSERT INTO `ump_log` VALUES ('298', 'admin', '1', '192.168.253.1', null, '2016-03-14 14:15:40', null, '0');
INSERT INTO `ump_log` VALUES ('299', 'kelly', '1', '192.168.11.59', '2016-03-14 14:30:17', '2016-03-14 14:23:16', null, '1');
INSERT INTO `ump_log` VALUES ('300', 'admin', '1', '192.168.253.1', null, '2016-03-14 14:23:41', null, '0');
INSERT INTO `ump_log` VALUES ('301', 'admin', '1', '192.168.11.59', '2016-03-14 14:36:15', '2016-03-14 14:30:27', null, '1');
INSERT INTO `ump_log` VALUES ('302', 'admin', '1', '192.168.11.59', '2016-03-14 14:38:24', '2016-03-14 14:36:29', null, '1');
INSERT INTO `ump_log` VALUES ('303', 'admin', '1', '192.168.11.59', '2016-03-14 14:44:59', '2016-03-14 14:38:32', null, '1');
INSERT INTO `ump_log` VALUES ('304', 'kelly', '1', '192.168.11.59', '2016-03-14 14:51:10', '2016-03-14 14:45:31', null, '1');
INSERT INTO `ump_log` VALUES ('305', 'admin', '1', '192.168.11.59', null, '2016-03-14 14:51:21', null, '0');
INSERT INTO `ump_log` VALUES ('306', 'admin', '1', '10.117.203.129', null, '2016-03-14 15:52:05', null, '0');
INSERT INTO `ump_log` VALUES ('307', 'admin', '1', '192.168.253.1', null, '2016-03-14 16:33:39', null, '0');
INSERT INTO `ump_log` VALUES ('308', 'admin', '1', '192.168.253.1', null, '2016-03-14 17:15:29', null, '0');
INSERT INTO `ump_log` VALUES ('309', 'admin', '1', '192.168.11.59', null, '2016-03-14 17:26:23', null, '0');
INSERT INTO `ump_log` VALUES ('310', 'admin', '1', '192.168.11.59', null, '2016-03-14 17:36:37', null, '0');
INSERT INTO `ump_log` VALUES ('311', 'admin', '1', '192.168.253.1', null, '2016-03-14 17:46:05', null, '0');
INSERT INTO `ump_log` VALUES ('312', 'admin', '1', '192.168.11.59', null, '2016-03-14 17:58:03', null, '0');
INSERT INTO `ump_log` VALUES ('313', 'admin', '1', '192.168.253.1', null, '2016-03-14 18:37:19', null, '0');
INSERT INTO `ump_log` VALUES ('314', 'admin', '1', '192.168.253.1', null, '2016-03-14 18:43:15', null, '0');
INSERT INTO `ump_log` VALUES ('315', 'admin', '1', '192.168.253.1', null, '2016-03-14 19:13:01', null, '0');
INSERT INTO `ump_log` VALUES ('316', 'admin', '1', '192.168.11.59', null, '2016-03-14 19:50:44', null, '0');
INSERT INTO `ump_log` VALUES ('317', 'admin', '1', '192.168.253.1', null, '2016-03-14 20:08:17', null, '0');
INSERT INTO `ump_log` VALUES ('318', 'admin', '1', '192.168.11.59', null, '2016-03-14 20:19:01', null, '0');
INSERT INTO `ump_log` VALUES ('319', 'admin', '1', '192.168.253.1', null, '2016-03-14 20:24:41', null, '0');
INSERT INTO `ump_log` VALUES ('320', 'admin', '1', '192.168.253.1', null, '2016-03-14 20:46:56', null, '0');
INSERT INTO `ump_log` VALUES ('321', 'admin', '1', '192.168.253.1', null, '2016-03-14 21:11:01', null, '0');
INSERT INTO `ump_log` VALUES ('322', 'admin', '1', '192.168.253.1', null, '2016-03-14 21:27:35', null, '0');
INSERT INTO `ump_log` VALUES ('323', 'admin', '1', '192.168.253.1', null, '2016-03-14 21:46:56', null, '0');
INSERT INTO `ump_log` VALUES ('324', 'admin', '1', '192.168.11.59', null, '2016-03-14 21:47:47', null, '0');
INSERT INTO `ump_log` VALUES ('325', 'admin', '1', '192.168.253.1', null, '2016-03-14 22:20:14', null, '0');
INSERT INTO `ump_log` VALUES ('326', 'admin', '1', '192.168.11.78', null, '2016-03-14 22:26:29', null, '0');
INSERT INTO `ump_log` VALUES ('327', 'admin', '1', '192.168.11.59', null, '2016-03-14 22:45:54', null, '0');
INSERT INTO `ump_log` VALUES ('328', 'admin', '1', '192.168.253.1', null, '2016-03-14 23:08:43', null, '0');
INSERT INTO `ump_log` VALUES ('329', 'admin', '1', '192.168.11.78', null, '2016-03-14 23:16:27', null, '0');
INSERT INTO `ump_log` VALUES ('330', 'admin', '1', '192.168.11.78', null, '2016-03-14 23:17:20', null, '0');
INSERT INTO `ump_log` VALUES ('331', 'admin', '1', '192.168.11.78', null, '2016-03-14 23:19:13', null, '0');
INSERT INTO `ump_log` VALUES ('332', 'admin', '1', '192.168.11.78', null, '2016-03-14 23:20:40', null, '0');
INSERT INTO `ump_log` VALUES ('333', 'admin', '1', '192.168.11.78', '2016-03-14 23:53:42', '2016-03-14 23:22:34', null, '1');
INSERT INTO `ump_log` VALUES ('334', 'admin', '1', '10.117.68.37', null, '2016-03-14 23:36:57', null, '0');
INSERT INTO `ump_log` VALUES ('335', 'admin', '1', '192.168.253.1', null, '2016-03-14 23:53:43', null, '0');
INSERT INTO `ump_log` VALUES ('336', 'admin', '1', '192.168.11.78', '2016-03-15 00:48:48', '2016-03-14 23:53:55', null, '1');
INSERT INTO `ump_log` VALUES ('337', 'admin', '1', '192.168.253.1', null, '2016-03-15 01:09:49', null, '0');
INSERT INTO `ump_log` VALUES ('338', 'admin', '1', '192.168.253.1', null, '2016-03-15 01:51:50', null, '0');
INSERT INTO `ump_log` VALUES ('339', 'admin', '1', '192.168.253.1', null, '2016-03-15 10:12:56', null, '0');
INSERT INTO `ump_log` VALUES ('340', 'admin', '1', '192.168.253.1', null, '2016-03-15 10:24:39', null, '0');
INSERT INTO `ump_log` VALUES ('341', 'admin', '1', '192.168.253.1', null, '2016-03-15 10:25:24', null, '0');
INSERT INTO `ump_log` VALUES ('342', 'admin', '1', '192.168.253.1', null, '2016-03-15 11:04:07', null, '0');
INSERT INTO `ump_log` VALUES ('343', 'admin', '1', '192.168.253.1', null, '2016-03-15 11:08:01', null, '0');
INSERT INTO `ump_log` VALUES ('344', 'admin', '1', '192.168.253.1', null, '2016-03-15 14:38:25', null, '0');
INSERT INTO `ump_log` VALUES ('345', 'admin', '1', '192.168.253.1', null, '2016-03-15 14:39:14', null, '0');
INSERT INTO `ump_log` VALUES ('346', 'admin', '1', '192.168.253.1', null, '2016-03-15 14:44:32', null, '0');
INSERT INTO `ump_log` VALUES ('347', 'admin', '1', '192.168.253.1', null, '2016-03-15 14:57:20', null, '0');
INSERT INTO `ump_log` VALUES ('348', 'admin', '1', '192.168.253.1', null, '2016-03-15 14:57:28', null, '0');
INSERT INTO `ump_log` VALUES ('349', 'admin', '1', '192.168.253.1', null, '2016-03-15 15:02:24', null, '0');
INSERT INTO `ump_log` VALUES ('350', 'admin', '1', '192.168.253.1', null, '2016-03-15 17:11:07', null, '0');
INSERT INTO `ump_log` VALUES ('351', 'admin', '1', '192.168.253.1', null, '2016-03-15 18:58:44', null, '0');
INSERT INTO `ump_log` VALUES ('352', 'admin', '1', '192.168.253.1', null, '2016-03-15 19:55:03', null, '0');
INSERT INTO `ump_log` VALUES ('353', 'admin', '1', '192.168.253.1', null, '2016-03-15 20:12:15', null, '0');
INSERT INTO `ump_log` VALUES ('354', 'admin', '1', '192.168.253.1', null, '2016-03-15 20:17:07', null, '0');
INSERT INTO `ump_log` VALUES ('355', 'admin', '1', '192.168.253.1', null, '2016-03-15 20:30:22', null, '0');
INSERT INTO `ump_log` VALUES ('356', 'admin', '1', '192.168.12.50', null, '2016-03-15 20:59:01', null, '0');
INSERT INTO `ump_log` VALUES ('357', 'admin', '1', '192.168.12.50', null, '2016-03-15 21:42:24', null, '0');
INSERT INTO `ump_log` VALUES ('358', 'admin', '1', '192.168.12.50', null, '2016-03-15 21:50:57', null, '0');
INSERT INTO `ump_log` VALUES ('359', 'admin', '1', '192.168.12.50', null, '2016-03-15 21:57:29', null, '0');
INSERT INTO `ump_log` VALUES ('360', 'admin', '1', '192.168.12.50', null, '2016-03-15 22:09:07', null, '0');
INSERT INTO `ump_log` VALUES ('361', 'admin', '1', '192.168.12.50', null, '2016-03-15 22:13:33', null, '0');
INSERT INTO `ump_log` VALUES ('362', 'admin', '1', '192.168.12.50', null, '2016-03-15 22:26:06', null, '0');
INSERT INTO `ump_log` VALUES ('363', 'admin', '1', '192.168.12.50', null, '2016-03-15 22:30:58', null, '0');
INSERT INTO `ump_log` VALUES ('364', 'admin', '1', '192.168.12.50', null, '2016-03-15 22:33:06', null, '0');
INSERT INTO `ump_log` VALUES ('365', 'admin', '1', '192.168.12.50', null, '2016-03-15 23:34:48', null, '0');
INSERT INTO `ump_log` VALUES ('366', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:00:36', null, '0');
INSERT INTO `ump_log` VALUES ('367', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:04:41', null, '0');
INSERT INTO `ump_log` VALUES ('368', 'admin', '1', '192.168.253.1', '2016-03-16 10:33:44', '2016-03-16 10:12:35', null, '1');
INSERT INTO `ump_log` VALUES ('369', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:33:53', null, '0');
INSERT INTO `ump_log` VALUES ('370', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:35:26', null, '0');
INSERT INTO `ump_log` VALUES ('371', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:44:36', null, '0');
INSERT INTO `ump_log` VALUES ('372', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:50:10', null, '0');
INSERT INTO `ump_log` VALUES ('373', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:51:03', null, '0');
INSERT INTO `ump_log` VALUES ('374', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:52:06', null, '0');
INSERT INTO `ump_log` VALUES ('375', 'admin', '1', '192.168.253.1', null, '2016-03-16 10:57:51', null, '0');
INSERT INTO `ump_log` VALUES ('376', 'admin', '1', '192.168.253.1', null, '2016-03-16 11:03:22', null, '0');
INSERT INTO `ump_log` VALUES ('377', 'admin', '1', '192.168.253.1', null, '2016-03-16 13:42:09', null, '0');
INSERT INTO `ump_log` VALUES ('378', 'admin', '1', '192.168.253.1', null, '2016-03-16 13:47:31', null, '0');
INSERT INTO `ump_log` VALUES ('379', 'admin', '1', '192.168.253.1', null, '2016-03-16 13:52:21', null, '0');
INSERT INTO `ump_log` VALUES ('380', 'admin', '1', '192.168.253.1', null, '2016-03-16 13:57:25', null, '0');
INSERT INTO `ump_log` VALUES ('381', 'admin', '1', '192.168.253.1', null, '2016-03-16 15:15:14', null, '0');
INSERT INTO `ump_log` VALUES ('382', 'admin', '1', '192.168.253.1', null, '2016-03-16 15:44:47', null, '0');
INSERT INTO `ump_log` VALUES ('383', 'admin', '1', '192.168.11.78', null, '2016-03-16 15:45:06', null, '0');
INSERT INTO `ump_log` VALUES ('384', 'admin', '1', '192.168.253.1', null, '2016-03-16 16:01:15', null, '0');
INSERT INTO `ump_log` VALUES ('385', 'admin', '1', '10.117.203.129', null, '2016-03-16 17:16:46', null, '0');
INSERT INTO `ump_log` VALUES ('386', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:37:20', null, '0');
INSERT INTO `ump_log` VALUES ('387', 'admin', '1', '192.168.11.56', '2016-04-28 10:44:10', '2016-04-28 10:41:04', null, '1');
INSERT INTO `ump_log` VALUES ('388', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:47:12', null, '0');
INSERT INTO `ump_log` VALUES ('389', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:47:17', null, '0');
INSERT INTO `ump_log` VALUES ('390', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:48:09', null, '0');
INSERT INTO `ump_log` VALUES ('391', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:48:15', null, '0');
INSERT INTO `ump_log` VALUES ('392', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:52:21', null, '0');
INSERT INTO `ump_log` VALUES ('393', 'admin', '1', '192.168.11.56', null, '2016-04-28 10:52:27', null, '0');
INSERT INTO `ump_log` VALUES ('394', 'admin', '1', '192.168.11.56', '2016-04-28 10:56:43', '2016-04-28 10:53:18', null, '1');
INSERT INTO `ump_log` VALUES ('395', 'admin', '1', '192.168.11.56', '2016-04-28 10:56:58', '2016-04-28 10:56:52', null, '1');
INSERT INTO `ump_log` VALUES ('396', 'admin', '1', '192.168.11.56', '2016-04-28 11:06:08', '2016-04-28 10:59:42', null, '1');
INSERT INTO `ump_log` VALUES ('397', 'admin', '1', '192.168.11.56', '2016-04-28 11:07:14', '2016-04-28 11:06:32', null, '1');
INSERT INTO `ump_log` VALUES ('398', 'admin', '1', '192.168.11.56', '2016-04-28 11:12:13', '2016-04-28 11:08:43', null, '1');
INSERT INTO `ump_log` VALUES ('399', 'admin', '1', '192.168.11.56', null, '2016-04-28 11:12:20', null, '0');
INSERT INTO `ump_log` VALUES ('400', 'admin', '1', '192.168.11.56', '2016-04-28 11:20:59', '2016-04-28 11:20:28', null, '1');
INSERT INTO `ump_log` VALUES ('401', 'admin', '1', '192.168.11.56', null, '2016-04-28 17:50:31', null, '0');
INSERT INTO `ump_log` VALUES ('402', 'admin', '1', '192.168.243.1', null, '2016-05-04 15:15:43', null, '0');
INSERT INTO `ump_log` VALUES ('403', 'admin', '1', '192.168.243.1', null, '2016-05-04 16:53:18', null, '0');
INSERT INTO `ump_log` VALUES ('404', 'admin', '1', '192.168.243.1', null, '2016-05-04 16:59:35', null, '0');
INSERT INTO `ump_log` VALUES ('405', 'admin', '1', '192.168.243.1', null, '2016-05-04 17:09:26', null, '0');
INSERT INTO `ump_log` VALUES ('406', 'admin', '1', '192.168.243.1', '2016-05-05 15:45:13', '2016-05-05 15:44:39', null, '1');
INSERT INTO `ump_log` VALUES ('407', 'admin', '1', '192.168.243.1', null, '2016-05-05 15:45:20', null, '0');
INSERT INTO `ump_log` VALUES ('408', 'admin', '1', '192.168.243.1', null, '2016-05-05 15:53:11', null, '0');
INSERT INTO `ump_log` VALUES ('409', 'admin', '1', '192.168.243.1', null, '2016-05-05 17:45:12', null, '0');
INSERT INTO `ump_log` VALUES ('410', 'admin', '1', '192.168.243.1', '2016-05-05 19:45:09', '2016-05-05 19:31:23', null, '1');
INSERT INTO `ump_log` VALUES ('411', 'admin', '1', '192.168.243.1', '2016-05-05 19:46:23', '2016-05-05 19:45:20', null, '1');
INSERT INTO `ump_log` VALUES ('412', 'admin', '1', '192.168.243.1', null, '2016-05-05 19:46:30', null, '0');
INSERT INTO `ump_log` VALUES ('413', 'admin', '1', '192.168.243.1', null, '2016-05-05 20:03:33', null, '0');
INSERT INTO `ump_log` VALUES ('414', 'admin', '1', '192.168.243.1', null, '2016-05-06 13:42:28', null, '0');
INSERT INTO `ump_log` VALUES ('415', 'admin', '1', '169.254.253.51', null, '2016-05-06 18:06:27', null, '0');
INSERT INTO `ump_log` VALUES ('416', 'admin', '1', '169.254.253.51', null, '2016-05-06 18:15:33', null, '0');
INSERT INTO `ump_log` VALUES ('417', 'admin', '1', '169.254.253.51', null, '2016-05-06 18:35:29', null, '0');
INSERT INTO `ump_log` VALUES ('418', 'admin', '1', '192.168.243.1', null, '2016-05-09 00:40:21', null, '0');
INSERT INTO `ump_log` VALUES ('419', 'admin', '1', '192.168.243.1', null, '2016-05-09 01:03:12', null, '0');
INSERT INTO `ump_log` VALUES ('420', 'admin', '1', '192.168.243.1', null, '2016-05-09 01:35:03', null, '0');
INSERT INTO `ump_log` VALUES ('421', 'admin', '1', '192.168.243.1', null, '2016-05-09 01:48:55', null, '0');
INSERT INTO `ump_log` VALUES ('422', 'admin', '1', '192.168.243.1', null, '2016-05-09 01:58:57', null, '0');
INSERT INTO `ump_log` VALUES ('423', 'admin', '1', '192.168.243.1', null, '2016-05-09 02:09:09', null, '0');
INSERT INTO `ump_log` VALUES ('424', 'admin', '1', '192.168.243.1', null, '2016-05-09 02:11:40', null, '0');
INSERT INTO `ump_log` VALUES ('425', 'admin', '1', '192.168.243.1', null, '2016-05-09 10:30:56', null, '0');
INSERT INTO `ump_log` VALUES ('426', 'admin', '1', '192.168.243.1', null, '2016-05-09 11:18:09', null, '0');
INSERT INTO `ump_log` VALUES ('427', 'admin', '1', '192.168.243.1', null, '2016-05-09 11:20:26', null, '0');
INSERT INTO `ump_log` VALUES ('428', 'admin', '1', '192.168.243.1', null, '2016-05-09 11:40:33', null, '0');
INSERT INTO `ump_log` VALUES ('429', 'admin', '1', '192.168.243.1', null, '2016-05-09 11:58:22', null, '0');
INSERT INTO `ump_log` VALUES ('430', 'admin', '1', '192.168.243.1', null, '2016-05-09 13:11:11', null, '0');
INSERT INTO `ump_log` VALUES ('431', 'admin', '1', '192.168.243.1', null, '2016-05-09 16:27:00', null, '0');
INSERT INTO `ump_log` VALUES ('432', 'admin', '1', '192.168.243.1', null, '2016-05-09 16:55:22', null, '0');
INSERT INTO `ump_log` VALUES ('433', 'admin', '1', '192.168.243.1', null, '2016-05-09 17:20:04', null, '0');
INSERT INTO `ump_log` VALUES ('434', 'admin', '1', '192.168.243.1', null, '2016-05-09 18:50:42', null, '0');
INSERT INTO `ump_log` VALUES ('435', 'admin', '1', '192.168.243.1', null, '2016-05-09 18:59:25', null, '0');
INSERT INTO `ump_log` VALUES ('436', 'admin', '1', '192.168.243.1', null, '2016-05-09 19:46:35', null, '0');
INSERT INTO `ump_log` VALUES ('437', 'admin', '1', '192.168.243.1', null, '2016-05-09 19:48:12', null, '0');
INSERT INTO `ump_log` VALUES ('438', 'admin', '1', '192.168.243.1', null, '2016-05-09 20:05:19', null, '0');
INSERT INTO `ump_log` VALUES ('439', 'admin', '1', '192.168.243.1', '2016-05-09 21:28:55', '2016-05-09 20:08:47', null, '1');
INSERT INTO `ump_log` VALUES ('440', 'admin', '1', '192.168.243.1', '2016-05-09 21:29:23', '2016-05-09 21:29:04', null, '1');
INSERT INTO `ump_log` VALUES ('441', 'admin', '1', '192.168.243.1', null, '2016-05-09 21:29:28', null, '0');
INSERT INTO `ump_log` VALUES ('442', 'admin', '1', '192.168.243.1', null, '2016-05-09 21:30:09', null, '0');
INSERT INTO `ump_log` VALUES ('443', 'admin', '1', '192.168.243.1', null, '2016-05-10 11:00:42', null, '0');
INSERT INTO `ump_log` VALUES ('444', 'admin', '1', '192.168.243.1', null, '2016-05-10 18:21:45', null, '0');
INSERT INTO `ump_log` VALUES ('445', 'admin', '1', '192.168.243.1', null, '2016-05-10 18:43:49', null, '0');
INSERT INTO `ump_log` VALUES ('446', 'admin', '1', '192.168.243.1', null, '2016-05-10 18:47:14', null, '0');
INSERT INTO `ump_log` VALUES ('447', 'admin', '1', '192.168.243.1', null, '2016-05-10 19:15:23', null, '0');
INSERT INTO `ump_log` VALUES ('448', 'admin', '1', '192.168.243.1', null, '2016-05-10 19:28:29', null, '0');
INSERT INTO `ump_log` VALUES ('449', 'admin', '1', '192.168.243.1', null, '2016-05-10 19:36:22', null, '0');
INSERT INTO `ump_log` VALUES ('450', 'admin', '1', '192.168.243.1', null, '2016-05-10 19:41:35', null, '0');
INSERT INTO `ump_log` VALUES ('451', 'admin', '1', '192.168.243.1', null, '2016-05-10 19:46:41', null, '0');
INSERT INTO `ump_log` VALUES ('452', 'admin', '1', '192.168.243.1', null, '2016-05-10 19:48:21', null, '0');
INSERT INTO `ump_log` VALUES ('453', 'admin', '1', '192.168.243.1', null, '2016-05-10 20:00:38', null, '0');
INSERT INTO `ump_log` VALUES ('454', 'admin', '1', '192.168.243.1', null, '2016-05-10 20:06:58', null, '0');
INSERT INTO `ump_log` VALUES ('455', 'admin', '1', '192.168.243.1', '2016-05-11 10:33:23', '2016-05-11 10:32:26', null, '1');
INSERT INTO `ump_log` VALUES ('456', 'admin', '1', '192.168.243.1', '2016-05-11 10:34:10', '2016-05-11 10:33:29', null, '1');
INSERT INTO `ump_log` VALUES ('457', 'admin', '1', '192.168.243.1', null, '2016-05-11 10:34:17', null, '0');
INSERT INTO `ump_log` VALUES ('458', 'admin', '1', '192.168.243.1', null, '2016-05-12 16:15:39', null, '0');
INSERT INTO `ump_log` VALUES ('459', 'admin', '1', '192.168.243.1', null, '2016-05-12 18:12:00', null, '0');
INSERT INTO `ump_log` VALUES ('460', 'admin', '1', '192.168.243.1', null, '2016-05-13 09:45:40', null, '0');
INSERT INTO `ump_log` VALUES ('461', 'admin', '1', '192.168.243.1', null, '2016-05-14 19:13:03', null, '0');
INSERT INTO `ump_log` VALUES ('462', 'admin', '1', '192.168.243.1', null, '2016-05-14 19:15:54', null, '0');
INSERT INTO `ump_log` VALUES ('463', 'admin', '1', '192.168.243.1', null, '2016-05-15 11:49:36', null, '0');
INSERT INTO `ump_log` VALUES ('464', 'admin', '1', '192.168.243.1', null, '2016-05-15 12:11:20', null, '0');
INSERT INTO `ump_log` VALUES ('465', 'admin', '1', '192.168.243.1', null, '2016-05-16 10:48:18', null, '0');
INSERT INTO `ump_log` VALUES ('466', 'admin', '1', '192.168.243.1', null, '2016-05-16 13:57:29', null, '0');
INSERT INTO `ump_log` VALUES ('467', 'admin', '1', '192.168.243.1', null, '2016-05-16 17:53:01', null, '0');
INSERT INTO `ump_log` VALUES ('468', 'admin', '1', '192.168.243.1', null, '2016-05-17 15:32:47', null, '0');
INSERT INTO `ump_log` VALUES ('469', 'admin', '1', '192.168.243.1', null, '2016-05-17 16:57:11', null, '0');
INSERT INTO `ump_log` VALUES ('470', 'admin', '1', '192.168.243.1', null, '2016-05-18 15:23:07', null, '0');
INSERT INTO `ump_log` VALUES ('471', 'admin', '1', '192.168.243.1', null, '2016-05-18 15:35:12', null, '0');
INSERT INTO `ump_log` VALUES ('472', 'admin', '1', '192.168.243.1', null, '2016-05-18 17:05:57', null, '0');
INSERT INTO `ump_log` VALUES ('473', 'admin', '1', '192.168.1.22', null, '2016-05-20 10:08:39', null, '0');
INSERT INTO `ump_log` VALUES ('474', 'admin', '1', '192.168.243.1', '2016-05-23 00:05:00', '2016-05-22 23:58:36', null, '1');
INSERT INTO `ump_log` VALUES ('475', 'admin', '1', '192.168.243.1', null, '2016-05-23 00:05:09', null, '0');
INSERT INTO `ump_log` VALUES ('476', 'admin', '1', '192.168.243.1', null, '2016-05-23 00:18:01', null, '0');
INSERT INTO `ump_log` VALUES ('477', 'admin', '1', '192.168.243.1', null, '2016-05-23 00:32:29', null, '0');
INSERT INTO `ump_log` VALUES ('478', 'admin', '1', '192.168.243.1', null, '2016-05-23 00:35:31', null, '0');
INSERT INTO `ump_log` VALUES ('479', 'admin', '1', '192.168.243.1', null, '2016-05-23 00:41:08', null, '0');
INSERT INTO `ump_log` VALUES ('480', 'admin', '1', '192.168.1.22', null, '2016-05-23 14:10:15', null, '0');
INSERT INTO `ump_log` VALUES ('481', 'admin', '1', '192.168.243.1', null, '2016-05-27 11:31:49', null, '0');
INSERT INTO `ump_log` VALUES ('482', 'admin', '1', '192.168.243.1', null, '2016-05-30 12:05:59', null, '0');
INSERT INTO `ump_log` VALUES ('483', 'admin', '1', '192.168.243.1', null, '2016-06-03 10:08:23', null, '0');
INSERT INTO `ump_log` VALUES ('484', 'admin', '1', '192.168.243.1', null, '2016-06-03 10:41:36', null, '0');
INSERT INTO `ump_log` VALUES ('485', 'admin', '1', '192.168.243.1', '2016-06-07 18:12:29', '2016-06-07 16:28:15', null, '1');
INSERT INTO `ump_log` VALUES ('486', 'admin', '1', '192.168.243.1', '2016-06-07 19:06:32', '2016-06-07 19:05:45', null, '1');
INSERT INTO `ump_log` VALUES ('487', 'admin', '1', '192.168.243.1', null, '2016-06-07 19:06:55', null, '0');
INSERT INTO `ump_log` VALUES ('488', 'admin', '1', '192.168.243.1', null, '2016-06-07 19:07:50', null, '0');
INSERT INTO `ump_log` VALUES ('489', 'admin', '1', '192.168.243.1', null, '2016-06-07 19:23:36', null, '0');
INSERT INTO `ump_log` VALUES ('490', 'admin', '1', '192.168.243.1', null, '2016-06-07 19:26:06', null, '0');
INSERT INTO `ump_log` VALUES ('491', 'admin', '1', '192.168.243.1', null, '2016-06-13 14:04:44', null, '0');
INSERT INTO `ump_log` VALUES ('492', 'admin', '1', '192.168.243.1', null, '2016-06-13 17:44:51', null, '0');
INSERT INTO `ump_log` VALUES ('493', 'admin', '1', '192.168.243.1', null, '2016-06-13 18:20:01', null, '0');
INSERT INTO `ump_log` VALUES ('494', 'admin', '1', '192.168.243.1', null, '2016-06-13 18:27:28', null, '0');
INSERT INTO `ump_log` VALUES ('495', 'admin', '1', '192.168.243.1', null, '2016-06-14 10:22:29', null, '0');
INSERT INTO `ump_log` VALUES ('496', 'admin', '1', '192.168.243.1', null, '2016-06-14 10:39:39', null, '0');
INSERT INTO `ump_log` VALUES ('497', 'admin', '1', '192.168.243.1', null, '2016-06-14 10:43:41', null, '0');
INSERT INTO `ump_log` VALUES ('498', 'admin', '1', '192.168.243.1', null, '2016-06-14 14:45:53', null, '0');
INSERT INTO `ump_log` VALUES ('499', 'admin', '1', '192.168.243.1', null, '2016-06-14 15:00:34', null, '0');
INSERT INTO `ump_log` VALUES ('500', 'admin', '1', '192.168.243.1', null, '2016-06-14 15:05:12', null, '0');
INSERT INTO `ump_log` VALUES ('501', 'admin', '1', '192.168.243.1', null, '2016-06-14 15:26:33', null, '0');
INSERT INTO `ump_log` VALUES ('502', 'admin', '1', '192.168.243.1', null, '2016-06-14 15:37:54', null, '0');
INSERT INTO `ump_log` VALUES ('503', 'admin', '1', '192.168.243.1', null, '2016-06-14 15:57:48', null, '0');
INSERT INTO `ump_log` VALUES ('504', 'admin', '1', '192.168.243.1', null, '2016-06-14 16:06:54', null, '0');
INSERT INTO `ump_log` VALUES ('505', 'admin', '1', '192.168.243.1', null, '2016-06-14 16:10:35', null, '0');
INSERT INTO `ump_log` VALUES ('506', 'admin', '1', '192.168.243.1', null, '2016-06-14 17:09:34', null, '0');
INSERT INTO `ump_log` VALUES ('507', 'admin', '1', '192.168.243.1', null, '2016-06-14 17:16:35', null, '0');
INSERT INTO `ump_log` VALUES ('508', 'admin', '1', '192.168.243.1', null, '2016-06-14 17:35:45', null, '0');
INSERT INTO `ump_log` VALUES ('509', 'admin', '1', '192.168.243.1', null, '2016-06-14 17:40:22', null, '0');
INSERT INTO `ump_log` VALUES ('510', 'admin', '1', '192.168.243.1', null, '2016-06-14 17:55:06', null, '0');
INSERT INTO `ump_log` VALUES ('511', 'admin', '1', '192.168.243.1', null, '2016-06-14 18:07:41', null, '0');
INSERT INTO `ump_log` VALUES ('512', 'admin', '1', '192.168.243.1', null, '2016-06-14 18:12:13', null, '0');
INSERT INTO `ump_log` VALUES ('513', 'admin', '1', '192.168.243.1', null, '2016-06-15 17:12:24', null, '0');
INSERT INTO `ump_log` VALUES ('514', 'admin', '1', '192.168.243.1', null, '2016-06-15 17:55:40', null, '0');
INSERT INTO `ump_log` VALUES ('515', 'admin', '1', '192.168.243.1', null, '2016-06-15 17:58:14', null, '0');
INSERT INTO `ump_log` VALUES ('516', 'admin', '1', '192.168.243.1', null, '2016-06-16 15:11:42', null, '0');
INSERT INTO `ump_log` VALUES ('517', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:03:49', null, '0');
INSERT INTO `ump_log` VALUES ('518', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:13:22', null, '0');
INSERT INTO `ump_log` VALUES ('519', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:15:23', null, '0');
INSERT INTO `ump_log` VALUES ('520', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:24:54', null, '0');
INSERT INTO `ump_log` VALUES ('521', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:41:12', null, '0');
INSERT INTO `ump_log` VALUES ('522', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:49:48', null, '0');
INSERT INTO `ump_log` VALUES ('523', 'admin', '1', '192.168.243.1', null, '2016-06-16 16:59:47', null, '0');
INSERT INTO `ump_log` VALUES ('524', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:04:10', null, '0');
INSERT INTO `ump_log` VALUES ('525', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:07:36', null, '0');
INSERT INTO `ump_log` VALUES ('526', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:14:21', null, '0');
INSERT INTO `ump_log` VALUES ('527', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:25:37', null, '0');
INSERT INTO `ump_log` VALUES ('528', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:33:54', null, '0');
INSERT INTO `ump_log` VALUES ('529', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:39:57', null, '0');
INSERT INTO `ump_log` VALUES ('530', 'admin', '1', '192.168.243.1', null, '2016-06-16 17:48:09', null, '0');
INSERT INTO `ump_log` VALUES ('531', 'admin', '1', '192.168.243.1', null, '2016-06-17 10:51:59', null, '0');
INSERT INTO `ump_log` VALUES ('532', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:08:29', null, '0');
INSERT INTO `ump_log` VALUES ('533', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:13:37', null, '0');
INSERT INTO `ump_log` VALUES ('534', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:19:06', null, '0');
INSERT INTO `ump_log` VALUES ('535', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:34:22', null, '0');
INSERT INTO `ump_log` VALUES ('536', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:38:59', null, '0');
INSERT INTO `ump_log` VALUES ('537', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:41:25', null, '0');
INSERT INTO `ump_log` VALUES ('538', 'admin', '1', '192.168.243.1', null, '2016-06-17 11:46:02', null, '0');
INSERT INTO `ump_log` VALUES ('539', 'admin', '1', '192.168.243.1', null, '2016-06-21 15:44:02', null, '0');
INSERT INTO `ump_log` VALUES ('540', 'admin', '1', '192.168.243.1', null, '2016-06-21 16:10:23', null, '0');
INSERT INTO `ump_log` VALUES ('541', 'admin', '1', '192.168.1.11', '2016-06-21 17:10:47', '2016-06-21 17:10:27', null, '1');
INSERT INTO `ump_log` VALUES ('542', 'admin', '1', '192.168.243.1', null, '2016-06-27 15:35:39', null, '0');
INSERT INTO `ump_log` VALUES ('543', 'admin', '1', '192.168.243.1', null, '2016-06-27 15:57:56', null, '0');
INSERT INTO `ump_log` VALUES ('544', 'admin', '1', '192.168.243.1', null, '2016-06-27 16:34:52', null, '0');
INSERT INTO `ump_log` VALUES ('545', 'admin', '1', '192.168.243.1', null, '2016-06-27 16:50:06', null, '0');
INSERT INTO `ump_log` VALUES ('546', 'admin', '1', '192.168.243.1', null, '2016-06-27 17:26:31', null, '0');
INSERT INTO `ump_log` VALUES ('548', 'admin', '1', '192.168.1.13', null, '2016-07-20 10:33:18', null, '0');
INSERT INTO `ump_log` VALUES ('549', 'admin', '1', '192.168.1.13', null, '2016-07-20 10:41:14', null, '0');
INSERT INTO `ump_log` VALUES ('550', 'admin', '1', '192.168.1.13', null, '2016-07-20 14:45:24', null, '0');
INSERT INTO `ump_log` VALUES ('551', 'admin', '1', '192.168.1.13', null, '2016-07-20 15:22:32', null, '0');
INSERT INTO `ump_log` VALUES ('552', 'admin', '1', '192.168.1.13', null, '2016-07-20 15:35:03', null, '0');
INSERT INTO `ump_log` VALUES ('553', 'admin', '1', '192.168.1.13', null, '2016-07-20 15:40:10', null, '0');
INSERT INTO `ump_log` VALUES ('554', 'admin', '1', '192.168.1.13', null, '2016-07-20 15:49:37', null, '0');
INSERT INTO `ump_log` VALUES ('555', 'admin', '1', '192.168.1.13', null, '2016-07-20 16:02:09', null, '0');
INSERT INTO `ump_log` VALUES ('556', 'admin', '1', '192.168.1.13', null, '2016-07-20 16:07:53', null, '0');
INSERT INTO `ump_log` VALUES ('557', 'admin', '1', '192.168.1.13', null, '2016-07-20 16:12:26', null, '0');
INSERT INTO `ump_log` VALUES ('558', 'admin', '1', '192.168.1.13', null, '2016-07-20 16:19:42', null, '0');
INSERT INTO `ump_log` VALUES ('559', 'admin', '1', '192.168.1.13', null, '2016-07-20 17:42:37', null, '0');
INSERT INTO `ump_log` VALUES ('560', 'admin', '1', '192.168.243.1', null, '2016-07-31 11:49:58', null, '0');
INSERT INTO `ump_log` VALUES ('561', 'admin', '1', '192.168.243.1', null, '2016-07-31 13:12:59', null, '0');
INSERT INTO `ump_log` VALUES ('562', 'admin', '1', '192.168.243.1', null, '2016-07-31 13:40:36', null, '0');
INSERT INTO `ump_log` VALUES ('563', 'admin', '1', '192.168.11.141', null, '2016-09-02 13:58:50', null, '0');
INSERT INTO `ump_log` VALUES ('564', 'admin', '1', '192.168.11.141', null, '2016-09-02 14:00:41', null, '0');
INSERT INTO `ump_log` VALUES ('565', 'admin', '1', '192.168.11.141', null, '2016-09-02 16:00:56', null, '0');
INSERT INTO `ump_log` VALUES ('566', 'admin', '1', '192.168.243.1', null, '2016-09-03 13:12:24', null, '0');
INSERT INTO `ump_log` VALUES ('567', 'admin', '1', '192.168.243.1', null, '2016-09-03 15:10:59', null, '0');
INSERT INTO `ump_log` VALUES ('568', 'admin', '1', '192.168.243.1', null, '2016-09-03 15:19:27', null, '0');
INSERT INTO `ump_log` VALUES ('569', 'admin', '1', '192.168.243.1', null, '2016-09-03 16:25:39', null, '0');
INSERT INTO `ump_log` VALUES ('570', 'admin', '1', '192.168.243.1', '2016-09-03 17:09:27', '2016-09-03 17:08:59', null, '1');
INSERT INTO `ump_log` VALUES ('571', 'admin', '1', '192.168.243.1', null, '2016-09-03 17:09:34', null, '0');
INSERT INTO `ump_log` VALUES ('572', 'admin', '1', '192.168.243.1', null, '2016-09-03 17:12:30', null, '0');
INSERT INTO `ump_log` VALUES ('573', 'admin', '1', '192.168.243.1', null, '2016-09-03 17:24:39', null, '0');
INSERT INTO `ump_log` VALUES ('574', 'admin', '1', '192.168.243.1', null, '2016-09-03 17:26:05', null, '0');
INSERT INTO `ump_log` VALUES ('575', 'admin', '1', '192.168.243.1', null, '2016-09-03 17:39:39', null, '0');
INSERT INTO `ump_log` VALUES ('576', 'admin', '1', '192.168.243.1', null, '2016-09-03 17:54:50', null, '0');
INSERT INTO `ump_log` VALUES ('577', 'admin', '1', '192.168.243.1', null, '2016-09-03 18:04:07', null, '0');
INSERT INTO `ump_log` VALUES ('578', 'admin', '1', '192.168.243.1', null, '2016-09-03 18:30:06', null, '0');
INSERT INTO `ump_log` VALUES ('579', 'admin', '1', '192.168.243.1', null, '2016-09-03 18:32:43', null, '0');
INSERT INTO `ump_log` VALUES ('580', 'admin', '1', '192.168.243.1', null, '2016-09-03 18:36:54', null, '0');
INSERT INTO `ump_log` VALUES ('581', 'admin', '1', '192.168.243.1', null, '2016-09-03 18:41:58', null, '0');
INSERT INTO `ump_log` VALUES ('582', 'admin', '1', '192.168.243.1', '2016-09-03 18:46:33', '2016-09-03 18:44:49', null, '1');
INSERT INTO `ump_log` VALUES ('583', 'admin', '1', '192.168.243.1', null, '2016-09-04 13:08:52', null, '0');
INSERT INTO `ump_log` VALUES ('584', 'admin', '1', '192.168.243.1', '2016-09-04 14:37:03', '2016-09-04 14:36:55', null, '1');
INSERT INTO `ump_log` VALUES ('585', 'admin', '1', '192.168.243.1', '2016-09-04 14:37:39', '2016-09-04 14:37:09', null, '1');
INSERT INTO `ump_log` VALUES ('586', 'admin', '1', '192.168.243.1', null, '2016-09-04 14:37:51', null, '0');
INSERT INTO `ump_log` VALUES ('587', 'admin', '1', '192.168.243.1', null, '2016-09-04 14:52:05', null, '0');
INSERT INTO `ump_log` VALUES ('588', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:11:14', null, '0');
INSERT INTO `ump_log` VALUES ('589', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:35:27', null, '0');
INSERT INTO `ump_log` VALUES ('590', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:37:02', null, '0');
INSERT INTO `ump_log` VALUES ('591', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:41:17', null, '0');
INSERT INTO `ump_log` VALUES ('592', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:43:49', null, '0');
INSERT INTO `ump_log` VALUES ('593', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:43:54', null, '0');
INSERT INTO `ump_log` VALUES ('594', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:46:18', null, '0');
INSERT INTO `ump_log` VALUES ('595', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:48:08', null, '0');
INSERT INTO `ump_log` VALUES ('596', 'admin', '1', '192.168.243.1', null, '2016-09-04 15:59:08', null, '0');
INSERT INTO `ump_log` VALUES ('597', 'admin', '1', '192.168.243.1', null, '2016-09-04 16:02:47', null, '0');
INSERT INTO `ump_log` VALUES ('598', 'admin', '1', '192.168.243.1', null, '2016-09-04 16:04:49', null, '0');
INSERT INTO `ump_log` VALUES ('599', 'admin', '1', '192.168.243.1', '2016-09-04 17:27:49', '2016-09-04 17:27:33', null, '1');
INSERT INTO `ump_log` VALUES ('600', 'admin', '1', '192.168.243.1', null, '2016-09-04 17:27:55', null, '0');
INSERT INTO `ump_log` VALUES ('601', 'admin', '1', '192.168.243.1', null, '2016-09-04 17:44:25', null, '0');
INSERT INTO `ump_log` VALUES ('602', 'admin', '1', '192.168.243.1', null, '2016-09-04 17:51:49', null, '0');
INSERT INTO `ump_log` VALUES ('603', 'admin', '1', '192.168.243.1', null, '2016-09-04 17:53:05', null, '0');
INSERT INTO `ump_log` VALUES ('604', 'admin', '1', '192.168.243.1', null, '2016-09-04 18:09:47', null, '0');
INSERT INTO `ump_log` VALUES ('605', 'admin', '1', '192.168.243.1', null, '2016-09-10 15:09:14', null, '0');
INSERT INTO `ump_log` VALUES ('606', 'admin', '1', '192.168.243.1', '2016-09-10 17:05:15', '2016-09-10 17:05:11', null, '1');
INSERT INTO `ump_log` VALUES ('607', 'admin', '1', '192.168.243.1', '2016-09-10 17:05:37', '2016-09-10 17:05:20', null, '1');
INSERT INTO `ump_log` VALUES ('608', 'admin', '1', '192.168.243.1', null, '2016-09-10 17:05:43', null, '0');
INSERT INTO `ump_log` VALUES ('609', 'admin', '1', '192.168.243.1', null, '2016-09-10 17:28:37', null, '0');
INSERT INTO `ump_log` VALUES ('610', 'admin', '1', '192.168.11.141', '2016-09-14 16:16:55', '2016-09-14 16:15:46', null, '1');
INSERT INTO `ump_log` VALUES ('611', 'admin', '1', '192.168.11.141', null, '2016-09-14 16:17:05', null, '0');
INSERT INTO `ump_log` VALUES ('612', 'admin', '1', '192.168.11.141', null, '2016-09-14 16:47:14', null, '0');
INSERT INTO `ump_log` VALUES ('613', 'admin', '1', '192.168.243.1', '2016-09-14 22:26:27', '2016-09-14 22:24:52', null, '1');
INSERT INTO `ump_log` VALUES ('614', 'admin', '1', '192.168.243.1', null, '2016-09-14 22:35:31', null, '0');
INSERT INTO `ump_log` VALUES ('615', 'admin', '1', '192.168.243.1', null, '2016-09-14 22:36:59', null, '0');
INSERT INTO `ump_log` VALUES ('616', 'admin', '1', '192.168.243.1', null, '2016-09-14 22:37:38', null, '0');
INSERT INTO `ump_log` VALUES ('617', 'admin', '1', '192.168.243.1', null, '2016-10-01 10:28:36', null, '0');
INSERT INTO `ump_log` VALUES ('618', 'admin', '1', '192.168.243.1', '2016-10-28 22:01:12', '2016-10-28 22:00:17', null, '1');
INSERT INTO `ump_log` VALUES ('619', 'cheally6', '1', '192.168.243.1', null, '2016-10-28 22:01:19', null, '0');
INSERT INTO `ump_log` VALUES ('620', 'admin', '1', '192.168.243.1', null, '2016-10-28 22:01:33', null, '0');
INSERT INTO `ump_log` VALUES ('621', 'admin', '1', '192.168.243.1', '2016-10-28 22:04:26', '2016-10-28 22:02:05', null, '1');
INSERT INTO `ump_log` VALUES ('622', 'admin', '1', '192.168.11.141', null, '2016-12-30 16:22:18', null, '0');
INSERT INTO `ump_log` VALUES ('623', 'admin', '1', '192.168.11.141', null, '2016-12-30 16:26:36', null, '0');
INSERT INTO `ump_log` VALUES ('624', 'admin', '1', '192.168.11.141', '2016-12-30 16:38:23', '2016-12-30 16:37:48', null, '1');

-- ----------------------------
-- Table structure for `ump_operator`
-- ----------------------------
DROP TABLE IF EXISTS `ump_operator`;
CREATE TABLE `ump_operator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `auto_allocate` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `operator_name` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `urole` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4ti6x8islm9kljf6p4ymg0a2q` (`company`),
  KEY `FK_kk105wm4aj9jopij7ef4al5k8` (`urole`),
  CONSTRAINT `ump_operator_ibfk_1` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `ump_operator_ibfk_2` FOREIGN KEY (`urole`) REFERENCES `ump_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_operator
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_parent_business_type`
-- ----------------------------
DROP TABLE IF EXISTS `ump_parent_business_type`;
CREATE TABLE `ump_parent_business_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_name` varchar(30) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2lf7xvvs27qn5h1ib8hl6psc1` (`business_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_parent_business_type
-- ----------------------------
INSERT INTO `ump_parent_business_type` VALUES ('1', '家用电器', '2015-01-10 10:26:28', '0', '1', null, '0', '1');
INSERT INTO `ump_parent_business_type` VALUES ('2', '母婴', '2015-01-10 10:27:05', '0', '1', null, '0', '0');
INSERT INTO `ump_parent_business_type` VALUES ('3', '金融', '2015-05-11 16:47:49', '0', '1', '', '0', '0');

-- ----------------------------
-- Table structure for `ump_product`
-- ----------------------------
DROP TABLE IF EXISTS `ump_product`;
CREATE TABLE `ump_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_product
-- ----------------------------
INSERT INTO `ump_product` VALUES ('1', '2014-11-19 17:18:18', '0', '1', 'UMP', null, '0');
INSERT INTO `ump_product` VALUES ('3', '2014-11-19 17:18:18', '0', '1', '微信多账号管理', null, '0');

-- ----------------------------
-- Table structure for `ump_role`
-- ----------------------------
DROP TABLE IF EXISTS `ump_role`;
CREATE TABLE `ump_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `role_name` varchar(30) NOT NULL,
  `start_time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6hjdjk952q5gcuoxk2kykhlue` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_role
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_role_ump_authoritys`
-- ----------------------------
DROP TABLE IF EXISTS `ump_role_ump_authoritys`;
CREATE TABLE `ump_role_ump_authoritys` (
  `ump_roles` bigint(20) NOT NULL,
  `ump_authoritys` bigint(20) NOT NULL,
  KEY `FK_t1thannsqrpj5hnm289ylm24d` (`ump_authoritys`),
  KEY `FK_mmde4sq0r65qu9c84fp0bvcnf` (`ump_roles`),
  CONSTRAINT `ump_role_ump_authoritys_ibfk_1` FOREIGN KEY (`ump_roles`) REFERENCES `ump_role` (`id`),
  CONSTRAINT `ump_role_ump_authoritys_ibfk_2` FOREIGN KEY (`ump_authoritys`) REFERENCES `ump_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_role_ump_authoritys
-- ----------------------------

-- ----------------------------
-- Table structure for `ump_version`
-- ----------------------------
DROP TABLE IF EXISTS `ump_version`;
CREATE TABLE `ump_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_name` varchar(255) DEFAULT NULL,
  `product` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6jas28yo9lyort769vrf1og76` (`product`),
  CONSTRAINT `ump_version_ibfk_1` FOREIGN KEY (`product`) REFERENCES `ump_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_version
-- ----------------------------
INSERT INTO `ump_version` VALUES ('3', '2015-10-10 18:55:10', null, '25', '基本版', '3');
INSERT INTO `ump_version` VALUES ('4', '2016-09-14 16:16:52', null, '34', '高级版', '3');

-- ----------------------------
-- Table structure for `ump_version_ump_authoritys`
-- ----------------------------
DROP TABLE IF EXISTS `ump_version_ump_authoritys`;
CREATE TABLE `ump_version_ump_authoritys` (
  `ump_versions` bigint(20) NOT NULL,
  `ump_authoritys` bigint(20) NOT NULL,
  PRIMARY KEY (`ump_versions`,`ump_authoritys`),
  KEY `FK_cr3s5u9k0tohwjsqgqiqitks1` (`ump_authoritys`),
  KEY `FK_t91elb1sy2i1pwbgmjpbv4nwn` (`ump_versions`),
  CONSTRAINT `ump_version_ump_authoritys_ibfk_1` FOREIGN KEY (`ump_authoritys`) REFERENCES `ump_authority` (`id`),
  CONSTRAINT `ump_version_ump_authoritys_ibfk_2` FOREIGN KEY (`ump_versions`) REFERENCES `ump_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_version_ump_authoritys
-- ----------------------------
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '25');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '27');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '29');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '30');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '32');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '36');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '37');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '38');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '43');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '75');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '76');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '84');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '155');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '177');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '178');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '179');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '180');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '181');

-- ----------------------------
-- Table structure for `voc_account`
-- ----------------------------
DROP TABLE IF EXISTS `voc_account`;
CREATE TABLE `voc_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL,
  `approve_code` varchar(255) DEFAULT NULL,
  `approve_time` datetime DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `cookie` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `effect_time` datetime DEFAULT NULL,
  `expiry_time` datetime DEFAULT NULL,
  `is_approve` tinyint(1) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_valid` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `result` varchar(4000) DEFAULT NULL,
  `session_key` varchar(200) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `ump_channel` bigint(20) DEFAULT NULL,
  `voc_app_key` bigint(20) DEFAULT NULL,
  `voc_shop` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rgf3lvxbutst5b4wegqao8qkf` (`ump_channel`),
  KEY `FK_9fjba9su43ney08s40do3a5tb` (`voc_app_key`),
  KEY `FK_piab4uq6i9ddvdfup2ilpwfdg` (`voc_shop`),
  CONSTRAINT `voc_account_ibfk_1` FOREIGN KEY (`voc_app_key`) REFERENCES `voc_appkey` (`id`),
  CONSTRAINT `voc_account_ibfk_2` FOREIGN KEY (`voc_shop`) REFERENCES `voc_shop` (`id`),
  CONSTRAINT `voc_account_ibfk_3` FOREIGN KEY (`ump_channel`) REFERENCES `ump_channel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_account
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_appkey`
-- ----------------------------
DROP TABLE IF EXISTS `voc_appkey`;
CREATE TABLE `voc_appkey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appkey` varchar(32) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `redirect_url` varchar(255) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_appkey
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_brand`
-- ----------------------------
DROP TABLE IF EXISTS `voc_brand`;
CREATE TABLE `voc_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `key_name` varchar(4000) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ump_company` bigint(20) DEFAULT NULL,
  `ump_parent_business_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m09qidv4emw86buscvqp1sg7s` (`ump_company`),
  KEY `FK_e8vdeco0tmveo7mjq0moj7uce` (`ump_parent_business_type`),
  CONSTRAINT `voc_brand_ibfk_1` FOREIGN KEY (`ump_parent_business_type`) REFERENCES `ump_parent_business_type` (`id`),
  CONSTRAINT `voc_brand_ibfk_2` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_brand
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_brand_ump_channels`
-- ----------------------------
DROP TABLE IF EXISTS `voc_brand_ump_channels`;
CREATE TABLE `voc_brand_ump_channels` (
  `voc_brands` bigint(20) NOT NULL,
  `ump_channels` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_brands`,`ump_channels`),
  KEY `FK_601bjejs9elf35a2541yvoe55` (`ump_channels`),
  KEY `FK_2anijksfd4l38o3e3wkgxdtfu` (`voc_brands`),
  CONSTRAINT `voc_brand_ump_channels_ibfk_1` FOREIGN KEY (`voc_brands`) REFERENCES `voc_brand` (`id`),
  CONSTRAINT `voc_brand_ump_channels_ibfk_2` FOREIGN KEY (`ump_channels`) REFERENCES `ump_channel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_brand_ump_channels
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_comment`
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment`;
CREATE TABLE `voc_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buy_time` datetime DEFAULT NULL,
  `child_trade_id` varchar(255) DEFAULT NULL,
  `comment_content` varchar(4000) DEFAULT NULL,
  `comment_id` varchar(4000) DEFAULT NULL,
  `comment_level` int(11) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_title` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `dispatch_state` int(11) DEFAULT NULL,
  `dispatch_time` datetime DEFAULT NULL,
  `reply_content` varchar(500) DEFAULT NULL,
  `reply_state` int(11) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `reply_url` varchar(4000) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `dispatch_operator` bigint(20) DEFAULT NULL,
  `goods` bigint(20) DEFAULT NULL,
  `reply_operator` bigint(20) DEFAULT NULL,
  `trial` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jyrwjudx5sh36hy8vlk1x1wfp` (`dispatch_operator`),
  KEY `FK_3rl0vxspv801xuix4yxj5e49e` (`goods`),
  KEY `FK_qcxnujwgxmqn17r6hberhhhri` (`reply_operator`),
  CONSTRAINT `voc_comment_ibfk_1` FOREIGN KEY (`goods`) REFERENCES `voc_goods` (`id`),
  CONSTRAINT `voc_comment_ibfk_2` FOREIGN KEY (`dispatch_operator`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `voc_comment_ibfk_3` FOREIGN KEY (`reply_operator`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_comment_category`
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment_category`;
CREATE TABLE `voc_comment_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `deepth` bigint(20) NOT NULL,
  `display_name` varchar(2000) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_leaf` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(4000) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment_category
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_comment_copy`
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment_copy`;
CREATE TABLE `voc_comment_copy` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `buy_time` datetime DEFAULT NULL,
  `comment_content` varchar(4000) DEFAULT NULL,
  `comment_id` varchar(4000) DEFAULT NULL,
  `comment_level` int(11) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_title` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `dispatch_state` int(11) DEFAULT NULL,
  `dispatch_time` datetime DEFAULT NULL,
  `reply_content` varchar(500) DEFAULT NULL,
  `reply_state` int(11) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `reply_url` varchar(4000) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `dispatch_operator` bigint(20) DEFAULT NULL,
  `goods` bigint(20) DEFAULT NULL,
  `reply_operator` bigint(20) DEFAULT NULL,
  `reply_param` varchar(4000) DEFAULT NULL,
  `tag_id` bigint(20) DEFAULT NULL,
  `child_trade_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jyrwjudx5sh36hy8vlk1x1wfp` (`dispatch_operator`) USING BTREE,
  KEY `FK_3rl0vxspv801xuix4yxj5e49e` (`goods`) USING BTREE,
  KEY `FK_qcxnujwgxmqn17r6hberhhhri` (`reply_operator`) USING BTREE,
  CONSTRAINT `voc_comment_copy_ibfk_1` FOREIGN KEY (`goods`) REFERENCES `voc_goods` (`id`),
  CONSTRAINT `voc_comment_copy_ibfk_2` FOREIGN KEY (`dispatch_operator`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `voc_comment_copy_ibfk_3` FOREIGN KEY (`reply_operator`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of voc_comment_copy
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_comment_level_category`
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment_level_category`;
CREATE TABLE `voc_comment_level_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(500) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment_level_category
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_comment_level_rule`
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment_level_rule`;
CREATE TABLE `voc_comment_level_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_level` int(11) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `bussiness_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pmyqu7kicaichk1xev93bqm44` (`bussiness_type`),
  CONSTRAINT `voc_comment_level_rule_ibfk_1` FOREIGN KEY (`bussiness_type`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment_level_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_comment_voc_tags`
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment_voc_tags`;
CREATE TABLE `voc_comment_voc_tags` (
  `voc_comments` bigint(20) NOT NULL,
  `voc_tags` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_comments`,`voc_tags`),
  KEY `FK_o5iy2qe7h109sp09glqlgd7iq` (`voc_tags`),
  KEY `FK_tdjyq5089i1ngerdx1l5tp77h` (`voc_comments`),
  CONSTRAINT `voc_comment_voc_tags_ibfk_1` FOREIGN KEY (`voc_tags`) REFERENCES `voc_tags` (`id`),
  CONSTRAINT `voc_comment_voc_tags_ibfk_2` FOREIGN KEY (`voc_comments`) REFERENCES `voc_comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment_voc_tags
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_email`
-- ----------------------------
DROP TABLE IF EXISTS `voc_email`;
CREATE TABLE `voc_email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `operator_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `ump_company_id` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_email
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_email_group`
-- ----------------------------
DROP TABLE IF EXISTS `voc_email_group`;
CREATE TABLE `voc_email_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `ump_company_id` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_email_group
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_email_group_voc_emails`
-- ----------------------------
DROP TABLE IF EXISTS `voc_email_group_voc_emails`;
CREATE TABLE `voc_email_group_voc_emails` (
  `voc_email_groups` bigint(20) NOT NULL,
  `voc_emails` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_email_groups`,`voc_emails`),
  KEY `FK_np5739jh8o7v7xqqli8n36c5u` (`voc_emails`),
  KEY `FK_3ew0f1d00whue4li0vdj2xtqj` (`voc_email_groups`),
  CONSTRAINT `voc_email_group_voc_emails_ibfk_1` FOREIGN KEY (`voc_email_groups`) REFERENCES `voc_email_group` (`id`),
  CONSTRAINT `voc_email_group_voc_emails_ibfk_2` FOREIGN KEY (`voc_emails`) REFERENCES `voc_email` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_email_group_voc_emails
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_goods`
-- ----------------------------
DROP TABLE IF EXISTS `voc_goods`;
CREATE TABLE `voc_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bussiness_type_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `goods_id` varchar(255) DEFAULT NULL,
  `goods_is_deleted` varchar(255) DEFAULT NULL,
  `goods_number` varchar(255) DEFAULT NULL,
  `goods_shelves_status` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `match_score` int(11) DEFAULT NULL,
  `match_time` datetime DEFAULT NULL,
  `match_type` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `channel` bigint(20) DEFAULT NULL,
  `shop` bigint(20) DEFAULT NULL,
  `ump_company` bigint(20) DEFAULT NULL,
  `voc_brand` bigint(20) DEFAULT NULL,
  `voc_goods_property` bigint(20) DEFAULT NULL,
  `voc_sku` bigint(20) DEFAULT NULL,
  `trial` varchar(255) DEFAULT NULL,
  `shop_source` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rnqgj0woy2wvi1d2b0s5ffwr9` (`channel`) USING BTREE,
  KEY `FK_ctlj13xaeyoj5crq7o589a961` (`shop`) USING BTREE,
  KEY `FK_okfrq3tbc9ybmrnpr917d9me2` (`ump_company`) USING BTREE,
  KEY `FK_en2g74f1nv8bdnwgomg872xqb` (`voc_brand`) USING BTREE,
  KEY `FK_skimwuorrqlqogcq4vbrqrtdm` (`voc_goods_property`) USING BTREE,
  KEY `FK_osjm2xkqm7pcw485urhwicgiq` (`voc_sku`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of voc_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_goods_property`
-- ----------------------------
DROP TABLE IF EXISTS `voc_goods_property`;
CREATE TABLE `voc_goods_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `property_name` varchar(255) NOT NULL,
  `property_value` varchar(255) DEFAULT NULL,
  `unit` varchar(40) NOT NULL,
  `value_type` varchar(40) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `ump_business_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sqx4nn8mjupol66tjbbxag9ri` (`ump_business_type`),
  CONSTRAINT `voc_goods_property_ibfk_1` FOREIGN KEY (`ump_business_type`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_goods_property
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_goods_property_value`
-- ----------------------------
DROP TABLE IF EXISTS `voc_goods_property_value`;
CREATE TABLE `voc_goods_property_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `property_value` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `voc_sku` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_55o5bec6bua0hiruved2vi8vm` (`voc_sku`),
  CONSTRAINT `voc_goods_property_value_ibfk_1` FOREIGN KEY (`voc_sku`) REFERENCES `voc_sku` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_goods_property_value
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_key_word`
-- ----------------------------
DROP TABLE IF EXISTS `voc_key_word`;
CREATE TABLE `voc_key_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_type_id` bigint(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `voc_brand_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_key_word
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_shop`
-- ----------------------------
DROP TABLE IF EXISTS `voc_shop`;
CREATE TABLE `voc_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `channel` bigint(20) DEFAULT NULL,
  `shopdesc` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6ai8qtbxqqhqsrcmw8h5ynb9x` (`channel`),
  CONSTRAINT `voc_shop_ibfk_1` FOREIGN KEY (`channel`) REFERENCES `ump_channel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_shop
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_shop_voc_brands`
-- ----------------------------
DROP TABLE IF EXISTS `voc_shop_voc_brands`;
CREATE TABLE `voc_shop_voc_brands` (
  `voc_shops` bigint(20) NOT NULL,
  `voc_brands` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_shops`,`voc_brands`),
  KEY `FK_as64kguv8hf1el5wvjb4v0uxr` (`voc_brands`),
  KEY `FK_8ef4ieeg2ce2pfs8lreygxncg` (`voc_shops`),
  CONSTRAINT `voc_shop_voc_brands_ibfk_1` FOREIGN KEY (`voc_shops`) REFERENCES `voc_shop` (`id`),
  CONSTRAINT `voc_shop_voc_brands_ibfk_2` FOREIGN KEY (`voc_brands`) REFERENCES `voc_brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_shop_voc_brands
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_sku`
-- ----------------------------
DROP TABLE IF EXISTS `voc_sku`;
CREATE TABLE `voc_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `sku_code` varchar(255) DEFAULT NULL,
  `sku_model` varchar(255) DEFAULT NULL,
  `sku_origin` varchar(255) DEFAULT NULL,
  `sku_package` varchar(255) DEFAULT NULL,
  `sku_type` varchar(255) DEFAULT NULL,
  `sku_weight` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ump_business_type` bigint(20) DEFAULT NULL,
  `voc_brand` bigint(20) DEFAULT NULL,
  `voc_sku_property` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qhhg4xxy6pcuv12a8t33augto` (`ump_business_type`),
  KEY `FK_qwyu30idt3tqjgcw1w9g8eb7x` (`voc_brand`),
  KEY `FK_dxg7raycs3iarbm0mbt6x3082` (`voc_sku_property`),
  CONSTRAINT `voc_sku_ibfk_1` FOREIGN KEY (`voc_sku_property`) REFERENCES `voc_sku_property` (`id`),
  CONSTRAINT `voc_sku_ibfk_2` FOREIGN KEY (`ump_business_type`) REFERENCES `ump_business_type` (`id`),
  CONSTRAINT `voc_sku_ibfk_3` FOREIGN KEY (`voc_brand`) REFERENCES `voc_brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_sku
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_sku_property`
-- ----------------------------
DROP TABLE IF EXISTS `voc_sku_property`;
CREATE TABLE `voc_sku_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `property_detail` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ump_company` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lp6f0ooxbmswa2ykhuu1i3s1h` (`ump_company`),
  CONSTRAINT `voc_sku_property_ibfk_1` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_sku_property
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_tags`
-- ----------------------------
DROP TABLE IF EXISTS `voc_tags`;
CREATE TABLE `voc_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `bussiness_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qn8oimuhn85ab2xispfgkpl3u` (`bussiness_type`),
  CONSTRAINT `voc_tags_ibfk_1` FOREIGN KEY (`bussiness_type`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_tags
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_template`
-- ----------------------------
DROP TABLE IF EXISTS `voc_template`;
CREATE TABLE `voc_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_level` int(11) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `brand` bigint(20) DEFAULT NULL,
  `voc_word_category` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m13r01cepqbhxwcvuqfbwh10d` (`brand`),
  KEY `FK_lxqvl0rlp69tx47okjxyns5tt` (`voc_word_category`),
  CONSTRAINT `voc_template_ibfk_1` FOREIGN KEY (`voc_word_category`) REFERENCES `voc_word_category` (`id`),
  CONSTRAINT `voc_template_ibfk_2` FOREIGN KEY (`brand`) REFERENCES `voc_brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_template
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_template_category`
-- ----------------------------
DROP TABLE IF EXISTS `voc_template_category`;
CREATE TABLE `voc_template_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_template_category
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_template_rule`
-- ----------------------------
DROP TABLE IF EXISTS `voc_template_rule`;
CREATE TABLE `voc_template_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `template` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dj1yrsu864lyvvro0bkmb3nr3` (`template`),
  CONSTRAINT `voc_template_rule_ibfk_1` FOREIGN KEY (`template`) REFERENCES `voc_template` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_template_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_warning_time`
-- ----------------------------
DROP TABLE IF EXISTS `voc_warning_time`;
CREATE TABLE `voc_warning_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `warning_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_warning_time
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_word_category`
-- ----------------------------
DROP TABLE IF EXISTS `voc_word_category`;
CREATE TABLE `voc_word_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_id` bigint(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_business_type` tinyint(1) DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_word_category
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_word_expressions`
-- ----------------------------
DROP TABLE IF EXISTS `voc_word_expressions`;
CREATE TABLE `voc_word_expressions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `audit_status` int(11) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `voc_word_catagory` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rqtg6on0pqfyryf3igdegv856` (`voc_word_catagory`),
  CONSTRAINT `voc_word_expressions_ibfk_1` FOREIGN KEY (`voc_word_catagory`) REFERENCES `voc_word_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_word_expressions
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_work_order`
-- ----------------------------
DROP TABLE IF EXISTS `voc_work_order`;
CREATE TABLE `voc_work_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_reason` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `voc_work_order_emergency` int(11) DEFAULT NULL,
  `voc_email_group` bigint(20) DEFAULT NULL,
  `voc_work_order_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rd5slfcy3v1s1kn2mqbuo22xq` (`voc_email_group`),
  KEY `FK_be3u2iu9baxng971dybnrkpla` (`voc_work_order_type`),
  CONSTRAINT `voc_work_order_ibfk_1` FOREIGN KEY (`voc_work_order_type`) REFERENCES `voc_work_order_type` (`id`),
  CONSTRAINT `voc_work_order_ibfk_2` FOREIGN KEY (`voc_email_group`) REFERENCES `voc_email_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_work_order
-- ----------------------------

-- ----------------------------
-- Table structure for `voc_work_order_type`
-- ----------------------------
DROP TABLE IF EXISTS `voc_work_order_type`;
CREATE TABLE `voc_work_order_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_work_order_type
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_activities`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_activities`;
CREATE TABLE `wcc_activities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activities_name` varchar(400) NOT NULL,
  `activities_topic` varchar(400) NOT NULL,
  `activities_url` varchar(4000) NOT NULL,
  `activity_city` varchar(4000) NOT NULL,
  `activity_province` varchar(4000) NOT NULL,
  `begin_time` datetime NOT NULL,
  `code_validtime` datetime DEFAULT NULL,
  `end_time` datetime NOT NULL,
  `image_url` varchar(4000) NOT NULL,
  `is_audit` tinyint(1) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_activities
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_alliancestore`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_alliancestore`;
CREATE TABLE `wcc_alliancestore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` varchar(255) DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `organization_pk` varchar(255) DEFAULT NULL,
  `store_address` varchar(4000) DEFAULT NULL,
  `store_intro` varchar(4000) DEFAULT NULL,
  `store_lat` varchar(500) DEFAULT NULL,
  `store_lng` varchar(500) DEFAULT NULL,
  `store_name` varchar(500) DEFAULT NULL,
  `store_phone` varchar(500) DEFAULT NULL,
  `store_pic` varchar(4000) DEFAULT NULL,
  `store_type` varchar(500) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type_str` varchar(500) DEFAULT NULL,
  `wcc_alliancestore_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c15spl1tjj5xp1yp8taaosvsx` (`wcc_alliancestore_type`),
  CONSTRAINT `wcc_alliancestore_ibfk_1` FOREIGN KEY (`wcc_alliancestore_type`) REFERENCES `wcc_alliancestore_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_alliancestore
-- ----------------------------
INSERT INTO `wcc_alliancestore` VALUES ('16', null, null, null, null, null, null, null, null, null, '广州市天河区天润路37号润华大厦', '<h3 class=\"new-tit\" style=\"font-size:16px;color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;background-color:#FFFFFF;\">\r\n	<span class=\"name\" style=\"color:#FF8400;\"> \r\n	<p class=\"p1\">\r\n		刷交行卡满200送10元，并享98折优惠\r\n	</p>\r\n</span> \r\n</h3>\r\n<h3 class=\"new-tit\" style=\"font-size:16px;color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;background-color:#FFFFFF;\">\r\n	<span class=\"name\" style=\"color:#FF8400;\"><img src=\"/ump/attached/wx_image/8/20150617/20150617173654_200.jpg\" alt=\"\" /><img src=\"/ump/attached/wx_image/8/20150617/20150617173703_195.jpg\" alt=\"\" /><br />\r\n</span> \r\n</h3>', '23.149418', '113.341572', '[7店通用] 香蔓美肌·SPA', '020-38471165', null, '4', null, null, null, '1', '4');
INSERT INTO `wcc_alliancestore` VALUES ('17', null, null, null, null, null, null, null, null, null, '增城市莲园路43号2楼', '<h2 class=\"sub-title\" style=\"font-size:16px;color:#666666;font-weight:normal;font-family:\'Microsoft YaHei\', arial, sans-serif;background-color:#FFFFFF;\">\r\n</h2>\r\n<h2 class=\"sub-title\" style=\"font-size:16px;color:#666666;font-weight:normal;font-family:\'Microsoft YaHei\', arial, sans-serif;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:\'Microsoft YaHei\';font-size:14px;\"><strong>仅售69元，价值291元丝特沃染发套餐，免费WiFi，免费停车位，男女通用！位于莲花市场入口的发新社，时尚、健康的精致便民服务为宗旨！</strong></span> \r\n</h2>\r\n<p>\r\n	————————————————————————\r\n</p>\r\n<p style=\"color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;font-size:14px;background-color:#FFFFFF;\">\r\n	<strong></strong> \r\n</p>\r\n<p style=\"color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;font-size:14px;background-color:#FFFFFF;\">\r\n	<strong><span style=\"font-family:\'Microsoft YaHei\';\">温馨提示：</span></strong> \r\n</p>\r\n<p style=\"color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:\'Microsoft YaHei\';\"><strong>1、专业、快速、时尚，洗剪吹按正常流程服务，不偷工减料，请放心使用</strong></span> \r\n</p>\r\n<p style=\"color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:\'Microsoft YaHei\';\"><strong>2、进店请先到前台验证</strong></span> \r\n</p>\r\n<p style=\"color:#333333;font-family:\'Hiragino Sans GB\', \'Microsoft YaHei\', simsun;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:\'Microsoft YaHei\';\"><strong>3、到店指定赵添导师服务需加收30元</strong></span> \r\n</p>\r\n<h2 class=\"sub-title\" style=\"font-size:16px;color:#666666;font-weight:normal;font-family:\'Microsoft YaHei\', arial, sans-serif;background-color:#FFFFFF;\">\r\n	<img src=\"/ump/attached/wx_image/8/20150617/20150617174234_75.jpg\" alt=\"\" /><br />\r\n</h2>\r\n<p>\r\n	<br />\r\n</p>', '23.294049', '113.838769', '[挂绿广场/泰富广场] 发新社', '13392635983', null, '3', null, null, null, '1', '3');
INSERT INTO `wcc_alliancestore` VALUES ('18', null, null, null, null, null, null, null, null, null, '天河区猎德大道31号', '<p>\r\n	<span style=\"font-size:18px;font-family:\'Microsoft YaHei\';color:#E53333;\"><strong>刷交行卡满200送一份盐焗鸡爪</strong></span><span style=\"line-height:1.5;font-size:18px;font-family:\'Microsoft YaHei\';color:#E53333;\"><strong>，并享受全场95折优惠！</strong></span> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<img src=\"http://121.199.10.83/ump/attached/wx_image/8/20150617/20150617172151_244.jpg\" /> \r\n</p>', '23.127008', '113.339757', '[兴盛路/跑马场] 花城湾牛肉火锅', '020-87587995', null, '1', null, null, null, '1', '1');
INSERT INTO `wcc_alliancestore` VALUES ('20', null, null, null, null, null, null, null, null, null, '广东省广州市天河区体育东路35号507室', '<p>\r\n	<span style=\"font-family:\'Microsoft YaHei\';font-size:18px;color:#E53333;\"><strong>全场9折优惠，</strong></span><span style=\"line-height:1.5;font-family:\'Microsoft YaHei\';font-size:18px;color:#E53333;\"><strong>充值10000元起享8折优惠！</strong></span>\r\n</p>\r\n<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150617/20150617195351_19.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<span style=\"line-height:1.5;font-family:\'Microsoft YaHei\';font-size:18px;color:#E53333;\"><strong><br />\r\n</strong></span> \r\n</p>', '23.135964', '113.334226', '苗方清颜祛痘(体育东路店)', '020-87539446', null, '4', null, null, null, '1', '4');
INSERT INTO `wcc_alliancestore` VALUES ('21', null, null, null, null, null, null, null, null, null, '广州市越秀区环市东路326号之一广东亚洲大酒店一楼7号', '<p class=\"mb10 cmp-txtshow\" id=\"textShowMore\" style=\"color:#333333;font-family:\'Segoe UI\', Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"color:#E53333;font-family:\'Microsoft YaHei\';font-size:16px;line-height:22px;background-color:#FFFFFF;\">公司</span><span style=\"font-family:\'Microsoft YaHei\';font-size:16px;color:#E53333;\">主营高级男女装服饰，</span><span style=\"color:#E53333;font-family:\'Microsoft YaHei\';font-size:16px;line-height:22px;background-color:#FFFFFF;\">秉承“顾客至上，锐意进取”的经营理念，坚持“客户第一”的原则为广大客户提供优质的服务，欢迎惠顾！</span><br />\r\n<img src=\"/ump/attached/wx_image/8/20150617/20150617200758_883.jpg\" alt=\"\" /> \r\n</p>\r\n<p class=\"mb10 cmp-txtshow\" id=\"textShowMore\" style=\"color:#333333;font-family:\'Segoe UI\', Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\r\n	<br />\r\n</p>', '23.144956', '113.286183', '广州越秀区意卡纳服饰商行', '13922773899', null, '2', null, null, null, '1', '2');
INSERT INTO `wcc_alliancestore` VALUES ('25', null, null, null, null, null, null, null, null, null, '天河区兴盛路8号汇峰苑102号商铺', '<p style=\"margin-left:0cm;\">\r\n	<strong>持交行卡全店商品享“<span>8</span>折”优惠！</strong>\r\n</p>\r\n<p style=\"margin-left:0cm;\">\r\n	<strong><img src=\"/ump/attached/wx_image/8/20150617/20150617192447_473.jpg\" width=\"320\" height=\"300\" alt=\"\" /><br />\r\n</strong>\r\n</p>', '23.125698', '113.337004', '广州市天河区珠江新城恒欣饮食店', '020-38325020', null, '1', null, null, null, '1', '1');

-- ----------------------------
-- Table structure for `wcc_alliancestore_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_alliancestore_platform_users`;
CREATE TABLE `wcc_alliancestore_platform_users` (
  `wcc_alliancestore` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_alliancestore`,`platform_users`),
  KEY `FK_jdswk6kdlyb8m8fga2i53oo5c` (`platform_users`),
  KEY `FK_scinmgrhg0tkw61wi8h93jogh` (`wcc_alliancestore`),
  CONSTRAINT `wcc_alliancestore_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_alliancestore_platform_users_ibfk_2` FOREIGN KEY (`wcc_alliancestore`) REFERENCES `wcc_alliancestore` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_alliancestore_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_alliancestore_type`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_alliancestore_type`;
CREATE TABLE `wcc_alliancestore_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `img` varchar(500) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_alliancestore_type
-- ----------------------------
INSERT INTO `wcc_alliancestore_type` VALUES ('1', '/ump/attached/wx_image/8/20150703/20150703184037_546.jpg', '美食餐饮');
INSERT INTO `wcc_alliancestore_type` VALUES ('2', '/ump/attached/wx_image/8/20150629/20150629111212_904.jpg', '商家服务');
INSERT INTO `wcc_alliancestore_type` VALUES ('3', '/ump/attached/wx_image/8/20150629/20150629111258_618.jpg', '便民服务');
INSERT INTO `wcc_alliancestore_type` VALUES ('4', '/ump/attached/wx_image/8/20150629/20150629111315_534.jpg', '美容养生');

-- ----------------------------
-- Table structure for `wcc_answer`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_answer`;
CREATE TABLE `wcc_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(4000) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `user_name` varchar(200) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_answer
-- ----------------------------
INSERT INTO `wcc_answer` VALUES ('1', '123', null, null, null, null);
INSERT INTO `wcc_answer` VALUES ('2', '456', null, null, null, null);
INSERT INTO `wcc_answer` VALUES ('3', '789', null, null, null, null);

-- ----------------------------
-- Table structure for `wcc_an_user`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_an_user`;
CREATE TABLE `wcc_an_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `an_name` varchar(4000) DEFAULT NULL,
  `an_pwd` varchar(400) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_an_user
-- ----------------------------
INSERT INTO `wcc_an_user` VALUES ('1', 'admin', null, '北京');
INSERT INTO `wcc_an_user` VALUES ('2', 'shanghai', null, '上海');
INSERT INTO `wcc_an_user` VALUES ('3', 'guangzhou', null, '广州');
INSERT INTO `wcc_an_user` VALUES ('4', 'shenzhen', null, '深圳');
INSERT INTO `wcc_an_user` VALUES ('5', 'xiamen', null, '厦门');
INSERT INTO `wcc_an_user` VALUES ('6', 'zhengzhou', null, '郑州');
INSERT INTO `wcc_an_user` VALUES ('7', 'wuhan', null, '武汉');
INSERT INTO `wcc_an_user` VALUES ('8', 'nanjing', null, '南京');
INSERT INTO `wcc_an_user` VALUES ('9', 'nanchang', null, '南昌');
INSERT INTO `wcc_an_user` VALUES ('10', 'hefei', null, '合肥');
INSERT INTO `wcc_an_user` VALUES ('11', 'changsha', null, '长沙');
INSERT INTO `wcc_an_user` VALUES ('12', 'shijiazhuang', null, '石家庄');

-- ----------------------------
-- Table structure for `wcc_appartment`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_appartment`;
CREATE TABLE `wcc_appartment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_pk` varchar(255) DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `item_address` varchar(500) DEFAULT NULL,
  `item_intro` varchar(4000) DEFAULT NULL,
  `item_lat` varchar(500) DEFAULT NULL,
  `item_lng` varchar(500) DEFAULT NULL,
  `item_name` varchar(500) DEFAULT NULL,
  `item_phone` varchar(255) DEFAULT NULL,
  `item_pick` varchar(4000) DEFAULT NULL,
  `item_status` varchar(500) DEFAULT NULL,
  `item_time` datetime DEFAULT NULL,
  `organization_pk` varchar(255) DEFAULT NULL,
  `platform_pk` varchar(255) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_appartment
-- ----------------------------
INSERT INTO `wcc_appartment` VALUES ('20', null, null, null, null, null, null, null, null, '广州市平云路177号', '兰亭荟', '23.129379', '113.35737', '兰亭荟', '020-55555555', '/ump/attached/wx_image/8/20150615/20150615112831_680.jpg', '2', '2015-01-01 00:00:00', null, null, null, null, null, null);
INSERT INTO `wcc_appartment` VALUES ('21', null, null, null, null, null, null, null, null, '天河天源路', '保利林海山庄', '23.191129', '113.368767', '保利林海山庄', '020-55555555', '/ump/attached/wx_image/8/20150615/20150615112937_811.jpg', '1', '2006-01-01 00:00:00', null, null, null, null, null, null);
INSERT INTO `wcc_appartment` VALUES ('22', null, null, null, null, null, null, null, null, '天河都市兰亭花园', '都市兰亭花园', '23.124904', '113.368851', '都市兰亭花园', '020-55555555', '/ump/attached/wx_image/8/20150617/20150617195210_778.jpg', '1', '2015-06-16 00:00:00', null, null, null, null, null, null);
INSERT INTO `wcc_appartment` VALUES ('24', null, null, null, null, null, null, null, null, '上海市沪清平公路1481', '项目介绍', '31.188812', '121.363137', '海天花园', '020-55555555', '/ump/attached/wx_image/8/20150703/20150703105746_419.jpg', '2', '2015-03-26 00:00:00', null, null, null, null, null, null);
INSERT INTO `wcc_appartment` VALUES ('25', null, null, null, null, null, null, null, null, '沪清平公路1388号', '财智社区', '31.188812', '121.363137', '康虹花园', '13111111111', '/ump/attached/wx_image/8/20150703/20150703113316_263.jpg', '1', '2010-07-01 00:00:00', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `wcc_appartment_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_appartment_platform_users`;
CREATE TABLE `wcc_appartment_platform_users` (
  `wcc_appartment` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_appartment`,`platform_users`),
  KEY `FK_3jcjbp0utxk7cbuwxsfpnxd8f` (`platform_users`),
  KEY `FK_i6alvce94li4r6p8wjyr382yv` (`wcc_appartment`),
  CONSTRAINT `wcc_appartment_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_appartment_platform_users_ibfk_2` FOREIGN KEY (`wcc_appartment`) REFERENCES `wcc_appartment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_appartment_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_appartment_wcc_itemtherinfo`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_appartment_wcc_itemtherinfo`;
CREATE TABLE `wcc_appartment_wcc_itemtherinfo` (
  `wcc_appartment` bigint(20) NOT NULL,
  `wcc_itemtherinfo` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_appartment`,`wcc_itemtherinfo`),
  UNIQUE KEY `UK_asy3ef5fu51x0h8wiiswgn6c6` (`wcc_itemtherinfo`),
  KEY `FK_asy3ef5fu51x0h8wiiswgn6c6` (`wcc_itemtherinfo`),
  KEY `FK_mw9sj71dka9qrkb9xxl1o4glf` (`wcc_appartment`),
  CONSTRAINT `wcc_appartment_wcc_itemtherinfo_ibfk_1` FOREIGN KEY (`wcc_itemtherinfo`) REFERENCES `wcc_itemotherinfo` (`id`),
  CONSTRAINT `wcc_appartment_wcc_itemtherinfo_ibfk_2` FOREIGN KEY (`wcc_appartment`) REFERENCES `wcc_appartment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_appartment_wcc_itemtherinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_autokbs`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_autokbs`;
CREATE TABLE `wcc_autokbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_review` int(11) DEFAULT NULL,
  `key_word` varchar(4000) DEFAULT NULL,
  `match_way` int(11) DEFAULT NULL,
  `related_issues` varchar(4000) DEFAULT NULL,
  `reply_type` int(11) DEFAULT NULL,
  `show_way` int(11) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `author` bigint(20) DEFAULT NULL,
  `materials` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mklnnrlf83718vyafh9es7hor` (`author`),
  KEY `FK_hsqxy7j8tvfw5nptm9x31o02l` (`materials`),
  KEY `FK_r78vo8pbamttnjttx102er2q` (`platform_user`),
  CONSTRAINT `wcc_autokbs_ibfk_1` FOREIGN KEY (`materials`) REFERENCES `wcc_materials` (`id`),
  CONSTRAINT `wcc_autokbs_ibfk_2` FOREIGN KEY (`author`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `wcc_autokbs_ibfk_3` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_autokbs
-- ----------------------------
INSERT INTO `wcc_autokbs` VALUES ('1', '1', '您查询的学分为 560分，恭喜恭喜~', '2016-07-31 11:51:38', null, '1', '查询学分', '0', '', '0', '1', '学分查询', '1', '18', null, '1');
INSERT INTO `wcc_autokbs` VALUES ('2', '1', '恭喜您申请维修成功~', '2016-07-31 11:52:29', null, '1', '申请维修', '0', '', '0', '0', '申请维修', '1', '18', null, '1');

-- ----------------------------
-- Table structure for `wcc_award_info`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_award_info`;
CREATE TABLE `wcc_award_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `award_info` varchar(4000) NOT NULL,
  `award_level` int(11) NOT NULL,
  `award_name` varchar(400) NOT NULL,
  `award_num` int(11) NOT NULL,
  `cdkey` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `win_rate` int(11) NOT NULL,
  `lottery_activity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7exhwewph0g1fpv5uu39fc5g3` (`lottery_activity`),
  CONSTRAINT `wcc_award_info_ibfk_1` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_award_info
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_chat_recourds`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_chat_recourds`;
CREATE TABLE `wcc_chat_recourds` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_time` datetime DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_chat_recourds
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_chery_article`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_chery_article`;
CREATE TABLE `wcc_chery_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_content` varchar(255) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `platform_account` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_chery_article
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_chery_brands`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_chery_brands`;
CREATE TABLE `wcc_chery_brands` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_chery_brands
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_city`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_city`;
CREATE TABLE `wcc_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `wcc_province` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sh6vk69wx4086qb20hq4vw4l3` (`wcc_province`),
  CONSTRAINT `wcc_city_ibfk_1` FOREIGN KEY (`wcc_province`) REFERENCES `wcc_province` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1479 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_city
-- ----------------------------
INSERT INTO `wcc_city` VALUES ('1000', '东城', '10');
INSERT INTO `wcc_city` VALUES ('1001', '西城', '10');
INSERT INTO `wcc_city` VALUES ('1002', '崇文', '10');
INSERT INTO `wcc_city` VALUES ('1003', '宣武', '10');
INSERT INTO `wcc_city` VALUES ('1004', '朝阳', '10');
INSERT INTO `wcc_city` VALUES ('1005', '丰台', '10');
INSERT INTO `wcc_city` VALUES ('1006', '石景山', '10');
INSERT INTO `wcc_city` VALUES ('1007', '海淀', '10');
INSERT INTO `wcc_city` VALUES ('1008', '门头沟', '10');
INSERT INTO `wcc_city` VALUES ('1009', '房山', '10');
INSERT INTO `wcc_city` VALUES ('1010', '通州', '10');
INSERT INTO `wcc_city` VALUES ('1011', '顺义', '10');
INSERT INTO `wcc_city` VALUES ('1012', '昌平', '10');
INSERT INTO `wcc_city` VALUES ('1013', '大兴', '10');
INSERT INTO `wcc_city` VALUES ('1014', '怀柔', '10');
INSERT INTO `wcc_city` VALUES ('1015', '平谷', '10');
INSERT INTO `wcc_city` VALUES ('1016', '密云', '10');
INSERT INTO `wcc_city` VALUES ('1017', '延庆', '10');
INSERT INTO `wcc_city` VALUES ('1018', '黄浦', '11');
INSERT INTO `wcc_city` VALUES ('1019', '卢湾', '11');
INSERT INTO `wcc_city` VALUES ('1020', '徐汇', '11');
INSERT INTO `wcc_city` VALUES ('1021', '长宁', '11');
INSERT INTO `wcc_city` VALUES ('1022', '静安', '11');
INSERT INTO `wcc_city` VALUES ('1023', '普陀', '11');
INSERT INTO `wcc_city` VALUES ('1024', '闸北', '11');
INSERT INTO `wcc_city` VALUES ('1025', '虹口', '11');
INSERT INTO `wcc_city` VALUES ('1026', '杨浦', '11');
INSERT INTO `wcc_city` VALUES ('1027', '闵行', '11');
INSERT INTO `wcc_city` VALUES ('1028', '宝山', '11');
INSERT INTO `wcc_city` VALUES ('1029', '嘉定', '11');
INSERT INTO `wcc_city` VALUES ('1030', '浦东新', '11');
INSERT INTO `wcc_city` VALUES ('1031', '金山', '11');
INSERT INTO `wcc_city` VALUES ('1032', '松江', '11');
INSERT INTO `wcc_city` VALUES ('1033', '青浦', '11');
INSERT INTO `wcc_city` VALUES ('1034', '南汇', '11');
INSERT INTO `wcc_city` VALUES ('1035', '奉贤', '11');
INSERT INTO `wcc_city` VALUES ('1036', '崇明', '11');
INSERT INTO `wcc_city` VALUES ('1037', '和平', '12');
INSERT INTO `wcc_city` VALUES ('1038', '河东', '12');
INSERT INTO `wcc_city` VALUES ('1039', '河西', '12');
INSERT INTO `wcc_city` VALUES ('1040', '南开', '12');
INSERT INTO `wcc_city` VALUES ('1041', '河北', '12');
INSERT INTO `wcc_city` VALUES ('1042', '红桥', '12');
INSERT INTO `wcc_city` VALUES ('1043', '塘沽', '12');
INSERT INTO `wcc_city` VALUES ('1044', '汉沽', '12');
INSERT INTO `wcc_city` VALUES ('1045', '大港', '12');
INSERT INTO `wcc_city` VALUES ('1046', '东丽', '12');
INSERT INTO `wcc_city` VALUES ('1047', '西青', '12');
INSERT INTO `wcc_city` VALUES ('1048', '津南', '12');
INSERT INTO `wcc_city` VALUES ('1049', '北辰', '12');
INSERT INTO `wcc_city` VALUES ('1050', '武清', '12');
INSERT INTO `wcc_city` VALUES ('1051', '宝坻', '12');
INSERT INTO `wcc_city` VALUES ('1052', '宁河', '12');
INSERT INTO `wcc_city` VALUES ('1053', '静海', '12');
INSERT INTO `wcc_city` VALUES ('1054', '蓟', '12');
INSERT INTO `wcc_city` VALUES ('1055', '万州', '13');
INSERT INTO `wcc_city` VALUES ('1056', '涪陵', '13');
INSERT INTO `wcc_city` VALUES ('1057', '渝中', '13');
INSERT INTO `wcc_city` VALUES ('1058', '大渡口', '13');
INSERT INTO `wcc_city` VALUES ('1059', '江北', '13');
INSERT INTO `wcc_city` VALUES ('1060', '沙坪坝', '13');
INSERT INTO `wcc_city` VALUES ('1061', '九龙坡', '13');
INSERT INTO `wcc_city` VALUES ('1062', '南岸', '13');
INSERT INTO `wcc_city` VALUES ('1063', '北碚', '13');
INSERT INTO `wcc_city` VALUES ('1064', '万盛', '13');
INSERT INTO `wcc_city` VALUES ('1065', '双桥', '13');
INSERT INTO `wcc_city` VALUES ('1066', '渝北', '13');
INSERT INTO `wcc_city` VALUES ('1067', '巴南', '13');
INSERT INTO `wcc_city` VALUES ('1068', '黔江', '13');
INSERT INTO `wcc_city` VALUES ('1069', '长寿', '13');
INSERT INTO `wcc_city` VALUES ('1070', '綦江', '13');
INSERT INTO `wcc_city` VALUES ('1071', '潼南', '13');
INSERT INTO `wcc_city` VALUES ('1072', '铜梁', '13');
INSERT INTO `wcc_city` VALUES ('1073', '大足', '13');
INSERT INTO `wcc_city` VALUES ('1074', '荣昌', '13');
INSERT INTO `wcc_city` VALUES ('1075', '璧山', '13');
INSERT INTO `wcc_city` VALUES ('1076', '梁平', '13');
INSERT INTO `wcc_city` VALUES ('1077', '城口', '13');
INSERT INTO `wcc_city` VALUES ('1078', '丰都', '13');
INSERT INTO `wcc_city` VALUES ('1079', '垫江', '13');
INSERT INTO `wcc_city` VALUES ('1080', '武隆', '13');
INSERT INTO `wcc_city` VALUES ('1081', '忠', '13');
INSERT INTO `wcc_city` VALUES ('1082', '开', '13');
INSERT INTO `wcc_city` VALUES ('1083', '云阳', '13');
INSERT INTO `wcc_city` VALUES ('1084', '奉节', '13');
INSERT INTO `wcc_city` VALUES ('1085', '巫山', '13');
INSERT INTO `wcc_city` VALUES ('1086', '巫溪', '13');
INSERT INTO `wcc_city` VALUES ('1087', '石柱土家族自治', '13');
INSERT INTO `wcc_city` VALUES ('1088', '秀山土家族苗族自治', '13');
INSERT INTO `wcc_city` VALUES ('1089', '酉阳土家族苗族自治', '13');
INSERT INTO `wcc_city` VALUES ('1090', '彭水苗族土家族自治', '13');
INSERT INTO `wcc_city` VALUES ('1091', '江津', '13');
INSERT INTO `wcc_city` VALUES ('1092', '合川', '13');
INSERT INTO `wcc_city` VALUES ('1093', '永川', '13');
INSERT INTO `wcc_city` VALUES ('1094', '南川', '13');
INSERT INTO `wcc_city` VALUES ('1095', '石家庄', '14');
INSERT INTO `wcc_city` VALUES ('1096', '唐山', '14');
INSERT INTO `wcc_city` VALUES ('1097', '秦皇岛 ', '14');
INSERT INTO `wcc_city` VALUES ('1098', '邯郸', '14');
INSERT INTO `wcc_city` VALUES ('1099', '邢台', '14');
INSERT INTO `wcc_city` VALUES ('1100', '保定', '14');
INSERT INTO `wcc_city` VALUES ('1101', '张家口', '14');
INSERT INTO `wcc_city` VALUES ('1102', '承德', '14');
INSERT INTO `wcc_city` VALUES ('1103', '沧州', '14');
INSERT INTO `wcc_city` VALUES ('1104', '廊坊 ', '14');
INSERT INTO `wcc_city` VALUES ('1105', '衡水', '14');
INSERT INTO `wcc_city` VALUES ('1106', '太原', '15');
INSERT INTO `wcc_city` VALUES ('1107', '大同', '15');
INSERT INTO `wcc_city` VALUES ('1108', '阳泉', '15');
INSERT INTO `wcc_city` VALUES ('1109', '长治', '15');
INSERT INTO `wcc_city` VALUES ('1110', '晋城', '15');
INSERT INTO `wcc_city` VALUES ('1111', '朔州 ', '15');
INSERT INTO `wcc_city` VALUES ('1112', '晋中', '15');
INSERT INTO `wcc_city` VALUES ('1113', '运城', '15');
INSERT INTO `wcc_city` VALUES ('1114', '忻州', '15');
INSERT INTO `wcc_city` VALUES ('1115', '临汾', '15');
INSERT INTO `wcc_city` VALUES ('1116', '吕梁', '15');
INSERT INTO `wcc_city` VALUES ('1117', '呼和浩特', '16');
INSERT INTO `wcc_city` VALUES ('1118', '包头 ', '16');
INSERT INTO `wcc_city` VALUES ('1119', '乌海', '16');
INSERT INTO `wcc_city` VALUES ('1120', '赤峰', '16');
INSERT INTO `wcc_city` VALUES ('1121', '通辽', '16');
INSERT INTO `wcc_city` VALUES ('1122', '鄂尔多斯', '16');
INSERT INTO `wcc_city` VALUES ('1123', '呼伦贝尔', '16');
INSERT INTO `wcc_city` VALUES ('1124', '乌兰察布', '16');
INSERT INTO `wcc_city` VALUES ('1125', '锡林郭勒盟', '16');
INSERT INTO `wcc_city` VALUES ('1126', '巴彦淖尔', '16');
INSERT INTO `wcc_city` VALUES ('1127', '阿拉善盟', '16');
INSERT INTO `wcc_city` VALUES ('1128', '兴安盟 ', '16');
INSERT INTO `wcc_city` VALUES ('1129', '沈阳', '17');
INSERT INTO `wcc_city` VALUES ('1130', '大连', '17');
INSERT INTO `wcc_city` VALUES ('1131', '鞍山', '17');
INSERT INTO `wcc_city` VALUES ('1132', '抚顺', '17');
INSERT INTO `wcc_city` VALUES ('1133', '本溪', '17');
INSERT INTO `wcc_city` VALUES ('1134', '丹东', '17');
INSERT INTO `wcc_city` VALUES ('1135', '锦州 ', '17');
INSERT INTO `wcc_city` VALUES ('1136', '葫芦岛', '17');
INSERT INTO `wcc_city` VALUES ('1137', '营口', '17');
INSERT INTO `wcc_city` VALUES ('1138', '盘锦', '17');
INSERT INTO `wcc_city` VALUES ('1139', '阜新', '17');
INSERT INTO `wcc_city` VALUES ('1140', '辽阳', '17');
INSERT INTO `wcc_city` VALUES ('1141', '铁岭', '17');
INSERT INTO `wcc_city` VALUES ('1142', '朝阳 ', '17');
INSERT INTO `wcc_city` VALUES ('1143', '长春', '18');
INSERT INTO `wcc_city` VALUES ('1144', '吉林', '18');
INSERT INTO `wcc_city` VALUES ('1145', '四平', '18');
INSERT INTO `wcc_city` VALUES ('1146', '辽源', '18');
INSERT INTO `wcc_city` VALUES ('1147', '通化', '18');
INSERT INTO `wcc_city` VALUES ('1148', '白山', '18');
INSERT INTO `wcc_city` VALUES ('1149', '松原 ', '18');
INSERT INTO `wcc_city` VALUES ('1150', '白城', '18');
INSERT INTO `wcc_city` VALUES ('1151', '延边朝鲜', '18');
INSERT INTO `wcc_city` VALUES ('1152', '哈尔滨', '19');
INSERT INTO `wcc_city` VALUES ('1153', '齐齐哈尔', '19');
INSERT INTO `wcc_city` VALUES ('1154', '鹤岗', '19');
INSERT INTO `wcc_city` VALUES ('1155', '双鸭山', '19');
INSERT INTO `wcc_city` VALUES ('1156', '鸡西 ', '19');
INSERT INTO `wcc_city` VALUES ('1157', '大庆', '19');
INSERT INTO `wcc_city` VALUES ('1158', '伊春', '19');
INSERT INTO `wcc_city` VALUES ('1159', '牡丹江', '19');
INSERT INTO `wcc_city` VALUES ('1160', '佳木斯', '19');
INSERT INTO `wcc_city` VALUES ('1161', '七台河', '19');
INSERT INTO `wcc_city` VALUES ('1162', '黑河', '19');
INSERT INTO `wcc_city` VALUES ('1163', '绥化 ', '19');
INSERT INTO `wcc_city` VALUES ('1164', '大兴安岭', '19');
INSERT INTO `wcc_city` VALUES ('1165', '南京', '20');
INSERT INTO `wcc_city` VALUES ('1166', '无锡', '20');
INSERT INTO `wcc_city` VALUES ('1167', '徐州', '20');
INSERT INTO `wcc_city` VALUES ('1168', '常州', '20');
INSERT INTO `wcc_city` VALUES ('1169', '苏州', '20');
INSERT INTO `wcc_city` VALUES ('1170', '南通 ', '20');
INSERT INTO `wcc_city` VALUES ('1171', '连云港', '20');
INSERT INTO `wcc_city` VALUES ('1172', '淮安', '20');
INSERT INTO `wcc_city` VALUES ('1173', '盐城', '20');
INSERT INTO `wcc_city` VALUES ('1174', '扬州', '20');
INSERT INTO `wcc_city` VALUES ('1175', '镇江', '20');
INSERT INTO `wcc_city` VALUES ('1176', '泰州', '20');
INSERT INTO `wcc_city` VALUES ('1177', '宿迁 ', '20');
INSERT INTO `wcc_city` VALUES ('1178', '杭州', '21');
INSERT INTO `wcc_city` VALUES ('1179', '宁波', '21');
INSERT INTO `wcc_city` VALUES ('1180', '温州', '21');
INSERT INTO `wcc_city` VALUES ('1181', '嘉兴', '21');
INSERT INTO `wcc_city` VALUES ('1182', '湖州', '21');
INSERT INTO `wcc_city` VALUES ('1183', '绍兴', '21');
INSERT INTO `wcc_city` VALUES ('1184', '金华 ', '21');
INSERT INTO `wcc_city` VALUES ('1185', '衢州', '21');
INSERT INTO `wcc_city` VALUES ('1186', '舟山', '21');
INSERT INTO `wcc_city` VALUES ('1187', '台州', '21');
INSERT INTO `wcc_city` VALUES ('1188', '丽水', '21');
INSERT INTO `wcc_city` VALUES ('1189', '合肥', '22');
INSERT INTO `wcc_city` VALUES ('1190', '芜湖', '22');
INSERT INTO `wcc_city` VALUES ('1191', '蚌埠 ', '22');
INSERT INTO `wcc_city` VALUES ('1192', '淮南', '22');
INSERT INTO `wcc_city` VALUES ('1193', '马鞍山', '22');
INSERT INTO `wcc_city` VALUES ('1194', '淮北', '22');
INSERT INTO `wcc_city` VALUES ('1195', '铜陵', '22');
INSERT INTO `wcc_city` VALUES ('1196', '安庆', '22');
INSERT INTO `wcc_city` VALUES ('1197', '黄山', '22');
INSERT INTO `wcc_city` VALUES ('1198', '滁州 ', '22');
INSERT INTO `wcc_city` VALUES ('1199', '阜阳', '22');
INSERT INTO `wcc_city` VALUES ('1200', '宿州', '22');
INSERT INTO `wcc_city` VALUES ('1201', '巢湖', '22');
INSERT INTO `wcc_city` VALUES ('1202', '六安', '22');
INSERT INTO `wcc_city` VALUES ('1203', '亳州', '22');
INSERT INTO `wcc_city` VALUES ('1204', '池州', '22');
INSERT INTO `wcc_city` VALUES ('1205', '宣城 ', '22');
INSERT INTO `wcc_city` VALUES ('1206', '福州', '23');
INSERT INTO `wcc_city` VALUES ('1207', '厦门', '23');
INSERT INTO `wcc_city` VALUES ('1208', '莆田', '23');
INSERT INTO `wcc_city` VALUES ('1209', '三明', '23');
INSERT INTO `wcc_city` VALUES ('1210', '泉州', '23');
INSERT INTO `wcc_city` VALUES ('1211', '漳州', '23');
INSERT INTO `wcc_city` VALUES ('1212', '南平 ', '23');
INSERT INTO `wcc_city` VALUES ('1213', '龙岩', '23');
INSERT INTO `wcc_city` VALUES ('1214', '宁德', '23');
INSERT INTO `wcc_city` VALUES ('1215', '南昌', '24');
INSERT INTO `wcc_city` VALUES ('1216', '景德镇', '24');
INSERT INTO `wcc_city` VALUES ('1217', '萍乡', '24');
INSERT INTO `wcc_city` VALUES ('1218', '新余', '24');
INSERT INTO `wcc_city` VALUES ('1219', '九江 ', '24');
INSERT INTO `wcc_city` VALUES ('1220', '鹰潭', '24');
INSERT INTO `wcc_city` VALUES ('1221', '赣州', '24');
INSERT INTO `wcc_city` VALUES ('1222', '吉安', '24');
INSERT INTO `wcc_city` VALUES ('1223', '宜春', '24');
INSERT INTO `wcc_city` VALUES ('1224', '抚州', '24');
INSERT INTO `wcc_city` VALUES ('1225', '上饶', '24');
INSERT INTO `wcc_city` VALUES ('1226', '济南 ', '25');
INSERT INTO `wcc_city` VALUES ('1227', '青岛', '25');
INSERT INTO `wcc_city` VALUES ('1228', '淄博', '25');
INSERT INTO `wcc_city` VALUES ('1229', '枣庄', '25');
INSERT INTO `wcc_city` VALUES ('1230', '东营', '25');
INSERT INTO `wcc_city` VALUES ('1231', '潍坊', '25');
INSERT INTO `wcc_city` VALUES ('1232', '烟台', '25');
INSERT INTO `wcc_city` VALUES ('1233', '威海 ', '25');
INSERT INTO `wcc_city` VALUES ('1234', '济宁', '25');
INSERT INTO `wcc_city` VALUES ('1235', '泰安', '25');
INSERT INTO `wcc_city` VALUES ('1236', '日照', '25');
INSERT INTO `wcc_city` VALUES ('1237', '莱芜', '25');
INSERT INTO `wcc_city` VALUES ('1238', '德州', '25');
INSERT INTO `wcc_city` VALUES ('1239', '临沂', '25');
INSERT INTO `wcc_city` VALUES ('1240', '聊城 ', '25');
INSERT INTO `wcc_city` VALUES ('1241', '滨州', '25');
INSERT INTO `wcc_city` VALUES ('1242', '菏泽', '25');
INSERT INTO `wcc_city` VALUES ('1243', '郑州', '26');
INSERT INTO `wcc_city` VALUES ('1244', '开封', '26');
INSERT INTO `wcc_city` VALUES ('1245', '洛阳', '26');
INSERT INTO `wcc_city` VALUES ('1246', '平顶山', '26');
INSERT INTO `wcc_city` VALUES ('1247', '焦作 ', '26');
INSERT INTO `wcc_city` VALUES ('1248', '鹤壁', '26');
INSERT INTO `wcc_city` VALUES ('1249', '新乡', '26');
INSERT INTO `wcc_city` VALUES ('1250', '安阳', '26');
INSERT INTO `wcc_city` VALUES ('1251', '濮阳', '26');
INSERT INTO `wcc_city` VALUES ('1252', '许昌', '26');
INSERT INTO `wcc_city` VALUES ('1253', '漯河', '26');
INSERT INTO `wcc_city` VALUES ('1254', '三门峡 ', '26');
INSERT INTO `wcc_city` VALUES ('1255', '南阳', '26');
INSERT INTO `wcc_city` VALUES ('1256', '商丘', '26');
INSERT INTO `wcc_city` VALUES ('1257', '信阳', '26');
INSERT INTO `wcc_city` VALUES ('1258', '周口', '26');
INSERT INTO `wcc_city` VALUES ('1259', '驻马店', '26');
INSERT INTO `wcc_city` VALUES ('1260', '济源', '26');
INSERT INTO `wcc_city` VALUES ('1261', '武汉 ', '27');
INSERT INTO `wcc_city` VALUES ('1262', '黄石', '27');
INSERT INTO `wcc_city` VALUES ('1263', '襄樊', '27');
INSERT INTO `wcc_city` VALUES ('1264', '十堰', '27');
INSERT INTO `wcc_city` VALUES ('1265', '荆州', '27');
INSERT INTO `wcc_city` VALUES ('1266', '宜昌', '27');
INSERT INTO `wcc_city` VALUES ('1267', '荆门', '27');
INSERT INTO `wcc_city` VALUES ('1268', '鄂州 ', '27');
INSERT INTO `wcc_city` VALUES ('1269', '孝感', '27');
INSERT INTO `wcc_city` VALUES ('1270', '黄冈', '27');
INSERT INTO `wcc_city` VALUES ('1271', '咸宁', '27');
INSERT INTO `wcc_city` VALUES ('1272', '随州', '27');
INSERT INTO `wcc_city` VALUES ('1273', '仙桃', '27');
INSERT INTO `wcc_city` VALUES ('1274', '天门', '27');
INSERT INTO `wcc_city` VALUES ('1275', '潜江 ', '27');
INSERT INTO `wcc_city` VALUES ('1276', '神农架', '27');
INSERT INTO `wcc_city` VALUES ('1277', '恩施土家', '27');
INSERT INTO `wcc_city` VALUES ('1278', '长沙', '28');
INSERT INTO `wcc_city` VALUES ('1279', '株洲', '28');
INSERT INTO `wcc_city` VALUES ('1280', '湘潭', '28');
INSERT INTO `wcc_city` VALUES ('1281', '衡阳', '28');
INSERT INTO `wcc_city` VALUES ('1282', '邵阳 ', '28');
INSERT INTO `wcc_city` VALUES ('1283', '岳阳', '28');
INSERT INTO `wcc_city` VALUES ('1284', '常德', '28');
INSERT INTO `wcc_city` VALUES ('1285', '张家界', '28');
INSERT INTO `wcc_city` VALUES ('1286', '益阳', '28');
INSERT INTO `wcc_city` VALUES ('1287', '郴州', '28');
INSERT INTO `wcc_city` VALUES ('1288', '怀化', '28');
INSERT INTO `wcc_city` VALUES ('1289', '娄底 ', '28');
INSERT INTO `wcc_city` VALUES ('1290', '湘西土家', '28');
INSERT INTO `wcc_city` VALUES ('1291', '永州', '28');
INSERT INTO `wcc_city` VALUES ('1292', '广州', '29');
INSERT INTO `wcc_city` VALUES ('1293', '深圳', '29');
INSERT INTO `wcc_city` VALUES ('1294', '珠海', '29');
INSERT INTO `wcc_city` VALUES ('1295', '汕头', '29');
INSERT INTO `wcc_city` VALUES ('1296', '韶关 ', '29');
INSERT INTO `wcc_city` VALUES ('1297', '佛山', '29');
INSERT INTO `wcc_city` VALUES ('1298', '江门', '29');
INSERT INTO `wcc_city` VALUES ('1299', '湛江', '29');
INSERT INTO `wcc_city` VALUES ('1300', '茂名', '29');
INSERT INTO `wcc_city` VALUES ('1301', '肇庆', '29');
INSERT INTO `wcc_city` VALUES ('1302', '惠州', '29');
INSERT INTO `wcc_city` VALUES ('1303', '梅州 ', '29');
INSERT INTO `wcc_city` VALUES ('1304', '汕尾', '29');
INSERT INTO `wcc_city` VALUES ('1305', '河源', '29');
INSERT INTO `wcc_city` VALUES ('1306', '阳江', '29');
INSERT INTO `wcc_city` VALUES ('1307', '清远', '29');
INSERT INTO `wcc_city` VALUES ('1308', '东莞', '29');
INSERT INTO `wcc_city` VALUES ('1309', '中山', '29');
INSERT INTO `wcc_city` VALUES ('1310', '潮州 ', '29');
INSERT INTO `wcc_city` VALUES ('1311', '揭阳', '29');
INSERT INTO `wcc_city` VALUES ('1312', '云浮', '29');
INSERT INTO `wcc_city` VALUES ('1313', '南宁', '30');
INSERT INTO `wcc_city` VALUES ('1314', '柳州', '30');
INSERT INTO `wcc_city` VALUES ('1315', '桂林', '30');
INSERT INTO `wcc_city` VALUES ('1316', '梧州', '30');
INSERT INTO `wcc_city` VALUES ('1317', '北海 ', '30');
INSERT INTO `wcc_city` VALUES ('1318', '防城港', '30');
INSERT INTO `wcc_city` VALUES ('1319', '钦州', '30');
INSERT INTO `wcc_city` VALUES ('1320', '贵港', '30');
INSERT INTO `wcc_city` VALUES ('1321', '玉林', '30');
INSERT INTO `wcc_city` VALUES ('1322', '百色', '30');
INSERT INTO `wcc_city` VALUES ('1323', '贺州', '30');
INSERT INTO `wcc_city` VALUES ('1324', '河池 ', '30');
INSERT INTO `wcc_city` VALUES ('1325', '来宾', '30');
INSERT INTO `wcc_city` VALUES ('1326', '崇左', '30');
INSERT INTO `wcc_city` VALUES ('1327', '海口', '31');
INSERT INTO `wcc_city` VALUES ('1328', '三亚', '31');
INSERT INTO `wcc_city` VALUES ('1329', '五指山', '31');
INSERT INTO `wcc_city` VALUES ('1330', '琼海', '31');
INSERT INTO `wcc_city` VALUES ('1331', '儋州 ', '31');
INSERT INTO `wcc_city` VALUES ('1332', '文昌', '31');
INSERT INTO `wcc_city` VALUES ('1333', '万宁', '31');
INSERT INTO `wcc_city` VALUES ('1334', '东方', '31');
INSERT INTO `wcc_city` VALUES ('1335', '澄迈', '31');
INSERT INTO `wcc_city` VALUES ('1336', '定安', '31');
INSERT INTO `wcc_city` VALUES ('1337', '屯昌', '31');
INSERT INTO `wcc_city` VALUES ('1338', '临高 ', '31');
INSERT INTO `wcc_city` VALUES ('1339', '白沙黎族', '31');
INSERT INTO `wcc_city` VALUES ('1340', '江黎族自', '31');
INSERT INTO `wcc_city` VALUES ('1341', '乐东黎族 ', '31');
INSERT INTO `wcc_city` VALUES ('1342', '陵水黎族', '31');
INSERT INTO `wcc_city` VALUES ('1343', '保亭黎族', '31');
INSERT INTO `wcc_city` VALUES ('1344', '琼中黎族 ', '31');
INSERT INTO `wcc_city` VALUES ('1345', '西沙群岛', '31');
INSERT INTO `wcc_city` VALUES ('1346', '南沙群岛', '31');
INSERT INTO `wcc_city` VALUES ('1347', '中沙群岛 ', '31');
INSERT INTO `wcc_city` VALUES ('1348', '成都', '32');
INSERT INTO `wcc_city` VALUES ('1349', '自贡', '32');
INSERT INTO `wcc_city` VALUES ('1350', '攀枝花', '32');
INSERT INTO `wcc_city` VALUES ('1351', '泸州', '32');
INSERT INTO `wcc_city` VALUES ('1352', '德阳', '32');
INSERT INTO `wcc_city` VALUES ('1353', '绵阳', '32');
INSERT INTO `wcc_city` VALUES ('1354', '广元 ', '32');
INSERT INTO `wcc_city` VALUES ('1355', '遂宁', '32');
INSERT INTO `wcc_city` VALUES ('1356', '内江', '32');
INSERT INTO `wcc_city` VALUES ('1357', '乐山', '32');
INSERT INTO `wcc_city` VALUES ('1358', '南充', '32');
INSERT INTO `wcc_city` VALUES ('1359', '宜宾', '32');
INSERT INTO `wcc_city` VALUES ('1360', '广安', '32');
INSERT INTO `wcc_city` VALUES ('1361', '达州 ', '32');
INSERT INTO `wcc_city` VALUES ('1362', '眉山', '32');
INSERT INTO `wcc_city` VALUES ('1363', '雅安', '32');
INSERT INTO `wcc_city` VALUES ('1364', '巴中', '32');
INSERT INTO `wcc_city` VALUES ('1365', '资阳', '32');
INSERT INTO `wcc_city` VALUES ('1366', '阿坝藏族', '32');
INSERT INTO `wcc_city` VALUES ('1367', '甘孜藏族', '32');
INSERT INTO `wcc_city` VALUES ('1368', '凉山彝族 ', '32');
INSERT INTO `wcc_city` VALUES ('1369', '贵阳', '33');
INSERT INTO `wcc_city` VALUES ('1370', '六盘水', '33');
INSERT INTO `wcc_city` VALUES ('1371', '遵义', '33');
INSERT INTO `wcc_city` VALUES ('1372', '安顺', '33');
INSERT INTO `wcc_city` VALUES ('1373', '铜仁', '33');
INSERT INTO `wcc_city` VALUES ('1374', '毕节', '33');
INSERT INTO `wcc_city` VALUES ('1375', '黔西南布 ', '33');
INSERT INTO `wcc_city` VALUES ('1376', '黔东南苗', '33');
INSERT INTO `wcc_city` VALUES ('1377', '黔南布依', '33');
INSERT INTO `wcc_city` VALUES ('1378', '昆明', '34');
INSERT INTO `wcc_city` VALUES ('1379', '曲靖', '34');
INSERT INTO `wcc_city` VALUES ('1380', '玉溪', '34');
INSERT INTO `wcc_city` VALUES ('1381', '保山', '34');
INSERT INTO `wcc_city` VALUES ('1382', '昭通 ', '34');
INSERT INTO `wcc_city` VALUES ('1383', '丽江', '34');
INSERT INTO `wcc_city` VALUES ('1384', '思茅', '34');
INSERT INTO `wcc_city` VALUES ('1385', '临沧', '34');
INSERT INTO `wcc_city` VALUES ('1386', '文山壮族', '34');
INSERT INTO `wcc_city` VALUES ('1387', '红河哈尼', '34');
INSERT INTO `wcc_city` VALUES ('1388', '西双版纳', '34');
INSERT INTO `wcc_city` VALUES ('1389', '楚雄彝族', '34');
INSERT INTO `wcc_city` VALUES ('1390', '大理白族', '34');
INSERT INTO `wcc_city` VALUES ('1391', '德宏傣族', '34');
INSERT INTO `wcc_city` VALUES ('1392', '怒江傈傈 ', '34');
INSERT INTO `wcc_city` VALUES ('1393', '迪庆藏族', '34');
INSERT INTO `wcc_city` VALUES ('1394', '拉萨', '35');
INSERT INTO `wcc_city` VALUES ('1395', '那曲', '35');
INSERT INTO `wcc_city` VALUES ('1396', '昌都', '35');
INSERT INTO `wcc_city` VALUES ('1397', '山南', '35');
INSERT INTO `wcc_city` VALUES ('1398', '日喀则', '35');
INSERT INTO `wcc_city` VALUES ('1399', '阿里 ', '35');
INSERT INTO `wcc_city` VALUES ('1400', '林芝', '35');
INSERT INTO `wcc_city` VALUES ('1401', '西安', '36');
INSERT INTO `wcc_city` VALUES ('1402', '铜川', '36');
INSERT INTO `wcc_city` VALUES ('1403', '宝鸡', '36');
INSERT INTO `wcc_city` VALUES ('1404', '咸阳', '36');
INSERT INTO `wcc_city` VALUES ('1405', '渭南', '36');
INSERT INTO `wcc_city` VALUES ('1406', '延安 ', '36');
INSERT INTO `wcc_city` VALUES ('1407', '汉中', '36');
INSERT INTO `wcc_city` VALUES ('1408', '榆林', '36');
INSERT INTO `wcc_city` VALUES ('1409', '安康', '36');
INSERT INTO `wcc_city` VALUES ('1410', '商洛', '36');
INSERT INTO `wcc_city` VALUES ('1411', '兰州', '37');
INSERT INTO `wcc_city` VALUES ('1412', '金昌', '37');
INSERT INTO `wcc_city` VALUES ('1413', '白银 ', '37');
INSERT INTO `wcc_city` VALUES ('1414', '天水', '37');
INSERT INTO `wcc_city` VALUES ('1415', '嘉峪关', '37');
INSERT INTO `wcc_city` VALUES ('1416', '武威', '37');
INSERT INTO `wcc_city` VALUES ('1417', '张掖', '37');
INSERT INTO `wcc_city` VALUES ('1418', '平凉', '37');
INSERT INTO `wcc_city` VALUES ('1419', '酒泉', '37');
INSERT INTO `wcc_city` VALUES ('1420', '庆阳 ', '37');
INSERT INTO `wcc_city` VALUES ('1421', '定西', '37');
INSERT INTO `wcc_city` VALUES ('1422', '陇南', '37');
INSERT INTO `wcc_city` VALUES ('1423', '临夏回族', '37');
INSERT INTO `wcc_city` VALUES ('1424', '甘南藏族', '37');
INSERT INTO `wcc_city` VALUES ('1425', '西宁', '38');
INSERT INTO `wcc_city` VALUES ('1426', '海东', '38');
INSERT INTO `wcc_city` VALUES ('1427', '海北藏族 ', '38');
INSERT INTO `wcc_city` VALUES ('1428', '黄南藏族', '38');
INSERT INTO `wcc_city` VALUES ('1429', '海南藏族', '38');
INSERT INTO `wcc_city` VALUES ('1430', '果洛藏族 ', '38');
INSERT INTO `wcc_city` VALUES ('1431', '玉树藏族', '38');
INSERT INTO `wcc_city` VALUES ('1432', '海西蒙古', '38');
INSERT INTO `wcc_city` VALUES ('1433', '银川', '39');
INSERT INTO `wcc_city` VALUES ('1434', '石嘴山', '39');
INSERT INTO `wcc_city` VALUES ('1435', '吴忠', '39');
INSERT INTO `wcc_city` VALUES ('1436', '固原', '39');
INSERT INTO `wcc_city` VALUES ('1437', '中卫 ', '39');
INSERT INTO `wcc_city` VALUES ('1438', '乌鲁木齐', '40');
INSERT INTO `wcc_city` VALUES ('1439', '克拉玛依', '40');
INSERT INTO `wcc_city` VALUES ('1440', '石河子', '40');
INSERT INTO `wcc_city` VALUES ('1441', '阿拉尔', '40');
INSERT INTO `wcc_city` VALUES ('1442', '图木舒克', '40');
INSERT INTO `wcc_city` VALUES ('1443', '五家渠', '40');
INSERT INTO `wcc_city` VALUES ('1444', '吐鲁番 ', '40');
INSERT INTO `wcc_city` VALUES ('1445', '哈密', '40');
INSERT INTO `wcc_city` VALUES ('1446', '和田', '40');
INSERT INTO `wcc_city` VALUES ('1447', '阿克苏', '40');
INSERT INTO `wcc_city` VALUES ('1448', '喀什', '40');
INSERT INTO `wcc_city` VALUES ('1449', '克孜勒苏', '40');
INSERT INTO `wcc_city` VALUES ('1450', '巴音郭楞', '40');
INSERT INTO `wcc_city` VALUES ('1451', '昌吉回族 ', '40');
INSERT INTO `wcc_city` VALUES ('1452', '博尔塔拉', '40');
INSERT INTO `wcc_city` VALUES ('1453', '伊犁哈萨', '40');
INSERT INTO `wcc_city` VALUES ('1454', '塔城', '40');
INSERT INTO `wcc_city` VALUES ('1455', '阿勒泰', '40');
INSERT INTO `wcc_city` VALUES ('1456', '台北', '43');
INSERT INTO `wcc_city` VALUES ('1457', '高雄', '43');
INSERT INTO `wcc_city` VALUES ('1458', '基隆 ', '43');
INSERT INTO `wcc_city` VALUES ('1459', '台中', '43');
INSERT INTO `wcc_city` VALUES ('1460', '台南', '43');
INSERT INTO `wcc_city` VALUES ('1461', '新竹', '43');
INSERT INTO `wcc_city` VALUES ('1462', '嘉义', '43');
INSERT INTO `wcc_city` VALUES ('1463', '台北', '43');
INSERT INTO `wcc_city` VALUES ('1464', '宜兰', '43');
INSERT INTO `wcc_city` VALUES ('1465', '新竹', '43');
INSERT INTO `wcc_city` VALUES ('1466', '桃园', '43');
INSERT INTO `wcc_city` VALUES ('1467', '苗栗', '43');
INSERT INTO `wcc_city` VALUES ('1468', '台中', '43');
INSERT INTO `wcc_city` VALUES ('1469', '彰化', '43');
INSERT INTO `wcc_city` VALUES ('1470', '南投', '43');
INSERT INTO `wcc_city` VALUES ('1471', '嘉义', '43');
INSERT INTO `wcc_city` VALUES ('1472', '云林', '43');
INSERT INTO `wcc_city` VALUES ('1473', '台南', '43');
INSERT INTO `wcc_city` VALUES ('1474', '高雄', '43');
INSERT INTO `wcc_city` VALUES ('1475', '屏东', '43');
INSERT INTO `wcc_city` VALUES ('1476', '台东', '43');
INSERT INTO `wcc_city` VALUES ('1477', '花莲', '43');
INSERT INTO `wcc_city` VALUES ('1478', '澎湖', '43');

-- ----------------------------
-- Table structure for `wcc_comment`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_comment`;
CREATE TABLE `wcc_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `content_title` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `friend_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `plat_form_id` bigint(20) DEFAULT NULL,
  `plat_form_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_community`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_community`;
CREATE TABLE `wcc_community` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `community_name` varchar(200) NOT NULL,
  `community_url` varchar(400) NOT NULL,
  `head_image` varchar(400) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `official_image` varchar(400) NOT NULL,
  `official_nick_name` varchar(400) NOT NULL,
  `topic_sort` int(11) NOT NULL,
  `topic_status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eqy8m2qggk5x5vnqlm34x4wlt` (`community_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_community
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_contact_us`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_contact_us`;
CREATE TABLE `wcc_contact_us` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `back_img` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `contact_unit` varchar(255) DEFAULT NULL,
  `contact_way` varchar(255) DEFAULT NULL,
  `detail_content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_time_str` varchar(255) DEFAULT NULL,
  `other_url` varchar(255) DEFAULT NULL,
  `plat_form_account` varchar(255) DEFAULT NULL,
  `plat_form_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_contact_us
-- ----------------------------
INSERT INTO `wcc_contact_us` VALUES ('22', '', null, '冼村街道办事处（金穗路398）', '020-87530701', null, '2015-06-17 08:13:07', '', null, null, null, '4');
INSERT INTO `wcc_contact_us` VALUES ('23', '', null, '天河派出所', '020-38690190', null, '2015-06-17 08:13:31', '', null, null, null, '3');
INSERT INTO `wcc_contact_us` VALUES ('24', '', null, '省有线电视', '020-82320890', null, '2015-06-17 08:13:57', '', null, null, null, '4');
INSERT INTO `wcc_contact_us` VALUES ('25', '', null, '冼村居委', '020-87504390', null, '2015-06-17 08:14:24', '', null, null, null, '3');
INSERT INTO `wcc_contact_us` VALUES ('26', '', null, '天河区教育局', '020-38622829', null, '2015-06-17 20:15:27', '', null, null, null, '0');
INSERT INTO `wcc_contact_us` VALUES ('27', '', null, '对讲门铃电话', '020-38696800', null, '2015-06-17 20:15:55', '', null, null, null, '0');
INSERT INTO `wcc_contact_us` VALUES ('28', '', null, '煤气抢险', '020-85515920', null, '2015-06-17 08:16:55', '', null, null, null, '1');
INSERT INTO `wcc_contact_us` VALUES ('29', '', null, '中国电信', '10000', null, '2015-06-18 09:56:55', '', null, null, null, '3');
INSERT INTO `wcc_contact_us` VALUES ('105', '', null, '2', '2', null, '2015-07-10 16:46:33', '', null, null, null, '0');
INSERT INTO `wcc_contact_us` VALUES ('106', '', null, '3', '3', null, '2015-07-10 16:46:38', '', null, null, null, '0');

-- ----------------------------
-- Table structure for `wcc_contact_us_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_contact_us_platform_users`;
CREATE TABLE `wcc_contact_us_platform_users` (
  `wcc_contact_us` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_contact_us`,`platform_users`),
  KEY `FK_pn9oj8en9hauefv44ht485p0f` (`platform_users`),
  KEY `FK_nynqxsmymnls2r98i6yw6b45j` (`wcc_contact_us`),
  CONSTRAINT `wcc_contact_us_platform_users_ibfk_1` FOREIGN KEY (`wcc_contact_us`) REFERENCES `wcc_contact_us` (`id`),
  CONSTRAINT `wcc_contact_us_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_contact_us_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_content`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_content`;
CREATE TABLE `wcc_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `click_count` int(11) NOT NULL,
  `comment_count` int(11) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `content` longtext NOT NULL,
  `content_url` varchar(4000) DEFAULT NULL,
  `insert_time` datetime NOT NULL,
  `is_check` int(11) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_prize_commit` tinyint(1) DEFAULT NULL,
  `is_visible` tinyint(1) DEFAULT NULL,
  `praise_count` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_content
-- ----------------------------
INSERT INTO `wcc_content` VALUES ('1', '0', '0', '4', '<p>\r\n	是的撒旦是\r\n</p>\r\n<p>\r\n	<img src=\"/ump/attached/wx_image/4/20150518/20150518111725_131.jpg\" alt=\"\" />\r\n</p>', null, '2015-05-18 11:17:32', '0', '0', '1', '1', '0', '测试', 'admin', '0');
INSERT INTO `wcc_content` VALUES ('2', '0', '0', '4', '1啊飒飒大', null, '2015-06-02 11:46:47', '0', '0', '1', '1', '0', '微内容', 'admin', '0');
INSERT INTO `wcc_content` VALUES ('3', '0', '0', '8', '<img src=\"/ump/attached/wx_image/8/20150624/20150624152012_500.jpg\" alt=\"\" />', null, '2015-06-24 15:20:16', '0', '0', '1', '1', '0', '111', 'admin', '0');

-- ----------------------------
-- Table structure for `wcc_cp_application`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_application`;
CREATE TABLE `wcc_cp_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agentid` varchar(255) DEFAULT NULL,
  `api_group` varchar(255) DEFAULT NULL,
  `appid` varchar(255) DEFAULT NULL,
  `auto_switch` tinyint(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `install_count` int(11) DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `menu_switch` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `round_logo_url` varchar(255) DEFAULT NULL,
  `square_logo_url` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `enterprise` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_crwvq5bqbwh8w5pm46lmnt3q4` (`enterprise`),
  CONSTRAINT `wcc_cp_application_ibfk_1` FOREIGN KEY (`enterprise`) REFERENCES `wcc_cp_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_application
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_application_cp_menus`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_application_cp_menus`;
CREATE TABLE `wcc_cp_application_cp_menus` (
  `wcc_cp_application` bigint(20) NOT NULL,
  `cp_menus` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_cp_application`,`cp_menus`),
  UNIQUE KEY `UK_3go0spt20rky9tg625btt6ay2` (`cp_menus`),
  KEY `FK_3go0spt20rky9tg625btt6ay2` (`cp_menus`),
  KEY `FK_jwin4swn1uvufbvol31hrq7h2` (`wcc_cp_application`),
  CONSTRAINT `wcc_cp_application_cp_menus_ibfk_1` FOREIGN KEY (`cp_menus`) REFERENCES `wcc_cp_menu` (`id`),
  CONSTRAINT `wcc_cp_application_cp_menus_ibfk_2` FOREIGN KEY (`wcc_cp_application`) REFERENCES `wcc_cp_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_application_cp_menus
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_application_mass_message`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_application_mass_message`;
CREATE TABLE `wcc_cp_application_mass_message` (
  `wcc_cp_application` bigint(20) NOT NULL,
  `mass_message` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_cp_application`,`mass_message`),
  UNIQUE KEY `UK_oq8vd1koo2579543p64o2q573` (`mass_message`),
  KEY `FK_oq8vd1koo2579543p64o2q573` (`mass_message`),
  KEY `FK_sgrtyilbd6e5l6qpj9bi79exq` (`wcc_cp_application`),
  CONSTRAINT `wcc_cp_application_mass_message_ibfk_1` FOREIGN KEY (`mass_message`) REFERENCES `wcc_cp_mass_message` (`id`),
  CONSTRAINT `wcc_cp_application_mass_message_ibfk_2` FOREIGN KEY (`wcc_cp_application`) REFERENCES `wcc_cp_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_application_mass_message
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_application_organizes`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_application_organizes`;
CREATE TABLE `wcc_cp_application_organizes` (
  `applications` bigint(20) NOT NULL,
  `organizes` bigint(20) NOT NULL,
  KEY `FK_n4xrvb7arw57pwb15mr8cuki7` (`organizes`),
  KEY `FK_kkoc1k3kh9bbmhl13vc5s99sa` (`applications`),
  CONSTRAINT `wcc_cp_application_organizes_ibfk_1` FOREIGN KEY (`applications`) REFERENCES `wcc_cp_application` (`id`),
  CONSTRAINT `wcc_cp_application_organizes_ibfk_2` FOREIGN KEY (`organizes`) REFERENCES `wcc_cp_organize` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_application_organizes
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_application_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_application_users`;
CREATE TABLE `wcc_cp_application_users` (
  `wcc_cp_application` bigint(20) NOT NULL,
  `users` bigint(20) NOT NULL,
  KEY `FK_4bq4dt7qa4xwjm1drhiyq3eqy` (`users`),
  KEY `FK_artu22ivetsel5no6a6mwtjlv` (`wcc_cp_application`),
  CONSTRAINT `wcc_cp_application_users_ibfk_1` FOREIGN KEY (`users`) REFERENCES `wcc_cp_user` (`id`),
  CONSTRAINT `wcc_cp_application_users_ibfk_2` FOREIGN KEY (`wcc_cp_application`) REFERENCES `wcc_cp_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_application_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_autokbs`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_autokbs`;
CREATE TABLE `wcc_cp_autokbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_review` int(11) DEFAULT NULL,
  `key_word` varchar(4000) DEFAULT NULL,
  `match_way` int(11) DEFAULT NULL,
  `related_issues` varchar(4000) DEFAULT NULL,
  `reply_type` int(11) DEFAULT NULL,
  `show_way` int(11) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `application` bigint(20) DEFAULT NULL,
  `author` bigint(20) DEFAULT NULL,
  `materials` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_omjcd5dw6aoeenvv5c3gbw99f` (`application`),
  KEY `FK_q1a5po04syq7qkqwpk70nopgn` (`author`),
  KEY `FK_kdj9fqwpr2cifkjcmjdnj8m5j` (`materials`),
  CONSTRAINT `wcc_cp_autokbs_ibfk_1` FOREIGN KEY (`materials`) REFERENCES `wcc_cp_materials` (`id`),
  CONSTRAINT `wcc_cp_autokbs_ibfk_2` FOREIGN KEY (`application`) REFERENCES `wcc_cp_application` (`id`),
  CONSTRAINT `wcc_cp_autokbs_ibfk_3` FOREIGN KEY (`author`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_autokbs
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_enterprise`;
CREATE TABLE `wcc_cp_enterprise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) DEFAULT NULL,
  `corp_agent_max` varchar(255) DEFAULT NULL,
  `corp_name` varchar(255) DEFAULT NULL,
  `corp_round_logo_url` varchar(255) DEFAULT NULL,
  `corp_square_logo_url` varchar(255) DEFAULT NULL,
  `corp_type` varchar(255) DEFAULT NULL,
  `corp_user_max` varchar(255) DEFAULT NULL,
  `corp_wxqrcode` varchar(255) DEFAULT NULL,
  `corpid` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expires_in` bigint(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `permanent_code` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_olhqh2yumdoxclwowx61adx65` (`company`),
  CONSTRAINT `wcc_cp_enterprise_ibfk_1` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_mass_message`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_mass_message`;
CREATE TABLE `wcc_cp_mass_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(5000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `invalid_msg` varchar(255) DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `media_id` varchar(255) DEFAULT NULL,
  `msg_id` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `send_msg` varchar(255) DEFAULT NULL,
  `sex` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `application` bigint(20) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `material` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9skkn7uysr55x61pm2jjwa8c7` (`application`),
  KEY `FK_mxdx1sxf2f74q2csg4iva14ak` (`company`),
  KEY `FK_5vi22o07w4byg138wom85d8ay` (`material`),
  CONSTRAINT `wcc_cp_mass_message_ibfk_1` FOREIGN KEY (`material`) REFERENCES `wcc_cp_materials` (`id`),
  CONSTRAINT `wcc_cp_mass_message_ibfk_2` FOREIGN KEY (`application`) REFERENCES `wcc_cp_application` (`id`),
  CONSTRAINT `wcc_cp_mass_message_ibfk_3` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_mass_message
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_mass_message_organizes`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_mass_message_organizes`;
CREATE TABLE `wcc_cp_mass_message_organizes` (
  `mass_messages` bigint(20) NOT NULL,
  `organizes` bigint(20) NOT NULL,
  PRIMARY KEY (`mass_messages`,`organizes`),
  KEY `FK_gy0olsp8m3ob0yargkelf9y4k` (`organizes`),
  KEY `FK_t4d8nu4g0xb5u1k8whpvbeh1` (`mass_messages`),
  CONSTRAINT `wcc_cp_mass_message_organizes_ibfk_1` FOREIGN KEY (`organizes`) REFERENCES `wcc_cp_organize` (`id`),
  CONSTRAINT `wcc_cp_mass_message_organizes_ibfk_2` FOREIGN KEY (`mass_messages`) REFERENCES `wcc_cp_mass_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_mass_message_organizes
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_mass_message_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_mass_message_users`;
CREATE TABLE `wcc_cp_mass_message_users` (
  `mass_messages` bigint(20) NOT NULL,
  `users` bigint(20) NOT NULL,
  PRIMARY KEY (`mass_messages`,`users`),
  KEY `FK_hn6cii5wb4jwgv6lytmxlcnur` (`users`),
  KEY `FK_it1a5toatykjay9hjofi616nq` (`mass_messages`),
  CONSTRAINT `wcc_cp_mass_message_users_ibfk_1` FOREIGN KEY (`users`) REFERENCES `wcc_cp_user` (`id`),
  CONSTRAINT `wcc_cp_mass_message_users_ibfk_2` FOREIGN KEY (`mass_messages`) REFERENCES `wcc_cp_mass_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_mass_message_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_materials`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_materials`;
CREATE TABLE `wcc_cp_materials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_id` int(11) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  `description` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `material_id` varchar(255) DEFAULT NULL,
  `material_type` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `remake_url` varchar(4000) DEFAULT NULL,
  `resource_url` varchar(4000) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  `text_url` varchar(256) DEFAULT NULL,
  `thumbnail_url` varchar(4000) DEFAULT NULL,
  `title` varchar(400) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `url_boolean` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_materials
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_materials_wcc_cp_applications`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_materials_wcc_cp_applications`;
CREATE TABLE `wcc_cp_materials_wcc_cp_applications` (
  `wcc_cp_materials` bigint(20) NOT NULL,
  `wcc_cp_applications` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_cp_materials`,`wcc_cp_applications`),
  KEY `FK_i5n0so4ecqjdeqolrm75c4xt1` (`wcc_cp_applications`),
  KEY `FK_sihtroi5purnf5brpuphvs4yf` (`wcc_cp_materials`),
  CONSTRAINT `wcc_cp_materials_wcc_cp_applications_ibfk_1` FOREIGN KEY (`wcc_cp_applications`) REFERENCES `wcc_cp_application` (`id`),
  CONSTRAINT `wcc_cp_materials_wcc_cp_applications_ibfk_2` FOREIGN KEY (`wcc_cp_materials`) REFERENCES `wcc_cp_materials` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_materials_wcc_cp_applications
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_menu`;
CREATE TABLE `wcc_cp_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `content_select` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `flag` bigint(20) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `menu_name` varchar(100) NOT NULL,
  `mkey` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `type` bigint(20) NOT NULL,
  `url` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `application` bigint(20) DEFAULT NULL,
  `materials` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m3ya3mp4bcxnagu3b0385ao3k` (`application`),
  KEY `FK_mxmjyafk5pa4wyyunycbflba9` (`materials`),
  CONSTRAINT `wcc_cp_menu_ibfk_1` FOREIGN KEY (`application`) REFERENCES `wcc_cp_application` (`id`),
  CONSTRAINT `wcc_cp_menu_ibfk_2` FOREIGN KEY (`materials`) REFERENCES `wcc_cp_materials` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_organize`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_organize`;
CREATE TABLE `wcc_cp_organize` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `depart_id` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `isdelete` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `enterprise` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4vgynvw2qpklobr9wjggvvawc` (`enterprise`),
  CONSTRAINT `wcc_cp_organize_ibfk_1` FOREIGN KEY (`enterprise`) REFERENCES `wcc_cp_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_organize
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_record_msg`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_record_msg`;
CREATE TABLE `wcc_cp_record_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agentid` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `from_user_name` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_validated` tinyint(1) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `location_x` double DEFAULT NULL,
  `location_y` double DEFAULT NULL,
  `media_id` varchar(255) DEFAULT NULL,
  `msg_id` bigint(20) DEFAULT NULL,
  `msg_type` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `scale` double DEFAULT NULL,
  `thumb_media_id` varchar(255) DEFAULT NULL,
  `to_user_app_name` varchar(255) DEFAULT NULL,
  `to_user_corp_name` varchar(255) DEFAULT NULL,
  `to_user_name` varchar(255) DEFAULT NULL,
  `to_user_record` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `wcc_cp_materials_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_record_msg
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_user`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_user`;
CREATE TABLE `wcc_cp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `extattr` varchar(255) DEFAULT NULL,
  `isdelete` tinyint(1) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `weixinid` varchar(255) DEFAULT NULL,
  `enterprise` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cs8owqfakscm0ty925xeok2ax` (`enterprise`),
  CONSTRAINT `wcc_cp_user_ibfk_1` FOREIGN KEY (`enterprise`) REFERENCES `wcc_cp_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_user
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_user_applications`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_user_applications`;
CREATE TABLE `wcc_cp_user_applications` (
  `wcc_cp_user` bigint(20) NOT NULL,
  `applications` bigint(20) NOT NULL,
  KEY `FK_qugr79gygkpqhm1u6nshbs315` (`applications`),
  KEY `FK_qyvmbdm8iiyejcsd61wtcjst6` (`wcc_cp_user`),
  CONSTRAINT `wcc_cp_user_applications_ibfk_1` FOREIGN KEY (`applications`) REFERENCES `wcc_cp_application` (`id`),
  CONSTRAINT `wcc_cp_user_applications_ibfk_2` FOREIGN KEY (`wcc_cp_user`) REFERENCES `wcc_cp_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_user_applications
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_user_organizes`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_user_organizes`;
CREATE TABLE `wcc_cp_user_organizes` (
  `users` bigint(20) NOT NULL,
  `organizes` bigint(20) NOT NULL,
  KEY `FK_i6ndin973l6ui4j45xi0nw77r` (`organizes`),
  KEY `FK_asntrpwwjberyh73uf31euu63` (`users`),
  CONSTRAINT `wcc_cp_user_organizes_ibfk_1` FOREIGN KEY (`users`) REFERENCES `wcc_cp_user` (`id`),
  CONSTRAINT `wcc_cp_user_organizes_ibfk_2` FOREIGN KEY (`organizes`) REFERENCES `wcc_cp_organize` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_user_organizes
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_cp_welcomkbs`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_cp_welcomkbs`;
CREATE TABLE `wcc_cp_welcomkbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_customer` tinyint(1) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `reply_type` int(11) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `application` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6xhf7f9c5v73dhwhuqrcpcnsv` (`application`),
  CONSTRAINT `wcc_cp_welcomkbs_ibfk_1` FOREIGN KEY (`application`) REFERENCES `wcc_cp_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_cp_welcomkbs
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_credential_type`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_credential_type`;
CREATE TABLE `wcc_credential_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_time_str` varchar(255) DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  `type_number` varchar(255) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_credential_type
-- ----------------------------
INSERT INTO `wcc_credential_type` VALUES ('8', null, null, null, null, null, null, null, null, '居民身份证', '15', null, null, null);
INSERT INTO `wcc_credential_type` VALUES ('9', null, null, null, null, null, null, null, null, '临时居民身份证', '16', null, null, null);
INSERT INTO `wcc_credential_type` VALUES ('10', null, null, null, null, null, null, null, null, '军人身份证', '17', null, null, null);
INSERT INTO `wcc_credential_type` VALUES ('11', null, null, null, null, null, null, null, null, '武警身份证', '18', null, null, null);
INSERT INTO `wcc_credential_type` VALUES ('12', null, null, null, null, null, null, null, null, '通行证', '19', null, null, null);
INSERT INTO `wcc_credential_type` VALUES ('13', null, null, null, null, null, null, null, null, '护照', '20', null, null, null);

-- ----------------------------
-- Table structure for `wcc_culture_life`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_culture_life`;
CREATE TABLE `wcc_culture_life` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `background_img` varchar(255) DEFAULT NULL,
  `background_tip` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `detail_content` varchar(4000) DEFAULT NULL,
  `detail_title` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `list_img` varchar(255) DEFAULT NULL,
  `list_tip` varchar(255) DEFAULT NULL,
  `other_url` varchar(255) DEFAULT NULL,
  `plat_form_account` varchar(255) DEFAULT NULL,
  `plat_form_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `insert_time_str` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_culture_life
-- ----------------------------
INSERT INTO `wcc_culture_life` VALUES ('2', '', '', null, '', null, '2015-05-18 15:54:57', '', '', null, null, null, '0', '');
INSERT INTO `wcc_culture_life` VALUES ('3', '/ump/attached/wx_image/4/20150518/20150518155608_177.jpg', '测试赛', null, '啊啊啊大大说啊大大', null, '2015-05-18 15:56:22', '/ump/attached/wx_image/4/20150518/20150518155612_425.jpg', '啊啊啊', null, null, null, '0', '');
INSERT INTO `wcc_culture_life` VALUES ('26', '', '', null, '<p style=\"text-indent:2em;\">\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\">夏季的酷热使人难耐，空调、电风扇也都转了起来。因为使用这些电器而造成的火灾、触电事故每年都有发生，怎样既安全又科学地用电，是每个家庭必须注意的大事。</span> \r\n</p>\r\n<p style=\"text-align:center;\">\r\n	<img src=\"/ump/attached/wx_image/8/20150615/20150615160602_755.jpg\" alt=\"\" /> \r\n</p>\r\n<span style=\"color:inherit;\"><strong>注意事项</strong></span><span></span> \r\n<p>\r\n	<span style=\"font-family:微软雅黑;font-size:15px;\"><strong><span style=\"color:#C1272D;\">►</span></strong>用电线路及电气设备绝缘必须良好，灯头、插座、开关等的带电部分绝对不能外露，以防触电。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong> 严格按照用电器具的容量选用适当截面的导线,不要盲目增加用电器具,使导线过负荷运行。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong>保险丝选用要合理，切忌用钢丝、铝丝或铁丝代替，以防发生火灾。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\">E 电熨斗、电吹风、电炉等家用电器使用后要及时断开电源。这些是发热电器，极容易引发火灾！</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong>电热褥火灾。电热褥的电热器材是外包绝缘材料的金属电热丝。不能折叠、揉搓，以免产生断路或短路故障。长时间通电，可能使铺垫过热引起火灾。所以，人长时间离开时应关掉电源。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong>手上沾水或出汗过多时，不要接触电线插销和使用灯头开关的电灯。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong>电线破损、断落时，千万不要触碰，应及时断开电源，然后速找电工修理。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong>发现有人触电，要先用绝缘体如干燥的木棒挑开电源，切忌在电源未与触电者分离的状态下拖拉触电者。</span> \r\n</p>\r\n<p>\r\n	<span style=\"font-size:15px;font-family:微软雅黑;\"><strong><strong><span style=\"color:#C1272D;\">►</span></strong></strong> 对于家里的小孩，千万要看管好，不要让其随便触摸带电设备，以免造成危险！</span> \r\n</p>', '小区用电安全须知', '2015-06-11 01:42:43', '/ump/attached/wx_image/8/20150615/20150615160413_449.jpg', '小区用电安全须知', null, null, null, '3', '');
INSERT INTO `wcc_culture_life` VALUES ('27', '', '', null, '<p style=\"text-indent:2em;\">\r\n	<span style=\"font-size:15px;\">随着城市居民生活水平不断提高，越来越多的业主在闲暇之时饲养起了宠物。饲养宠物可以调剂人的情感，给人带来愉快、活力和希望，增添生活乐趣，但随着宠物的增多，也给居民的日常生活带来了不良的影响：宠物伤人事件时有发生，狂犬病发病率居高不下，宠物的排泄物和排遗物影响环境，宠物的叫声严重干扰正常生活。对于饲养宠物的利弊和规范管理已成为业主关注的热点问题。</span> \r\n</p>\r\n<p style=\"text-align:center;\">\r\n	<img src=\"/ump/attached/wx_image/8/20150615/20150615160651_110.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;background-color:#E36C09;\">注意事项</span> \r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\">●凡饲养犬或其他需办证饲养的宠物的住户须办理与住所地址一致的登记证明、防疫证明、年检证明或其他相关证明，并到物业管理处登记备案。</span> \r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\">●小区内各业主饲养的宠物（包括犬和猫），应按市兽医防疫部门的规定定期到市兽医防疫部门或授权的下属市兽医防疫机构为宠物注射疫苗。</span> \r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\"><span style=\"font-family:微软雅黑;\">●</span>小区内各业主不得饲养烈性犬和大型犬。</span> \r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\">●小区内各业主携带犬只、猫等宠物到户外活动时，应做到以下几点：</span> \r\n</p>\r\n<ol class=\" list-paddingleft-2\">\r\n	<li>\r\n		<p style=\"color:inherit;\">\r\n			<span style=\"font-size:15px;\">携犬出户时，应当束犬链，由完全民事行为能力人牵领，注意避让老年人、残疾人、孕妇和儿童；</span> \r\n		</p>\r\n	</li>\r\n	<li>\r\n		<p style=\"color:inherit;\">\r\n			<span style=\"font-size:15px;\">携犬或其他易对他人人身安全造成危害的宠物乘坐电梯应避开乘梯的高峰时间，并采取有效的防护措施以保证其他乘梯人的人身安全。</span> \r\n		</p>\r\n	</li>\r\n	<li>\r\n		<p style=\"color:inherit;\">\r\n			<span style=\"font-size:15px;\">宠物不可随处大、小便，宠物饲养人应文明遛宠物并携带宠物粪便袋或垃圾袋，及时清除宠物所排泄的粪便。</span> \r\n		</p>\r\n	</li>\r\n</ol>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\">●小区内居民养犬、养猫及鸟类等宠物不得妨害他人，犬吠、猫叫、鸟鸣影响他人正常工作和休息时，饲养人应当采取有效措施予以制止。</span> \r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\">●宠物咬伤或抓伤他人时，饲养人应当立即带伤者到附近的卫生防疫机构或医院进行治疗和注射狂犬病疫苗，并承担相关责任。</span> \r\n</p>\r\n<p style=\"color:inherit;\">\r\n	<span style=\"font-size:15px;\">●在物业区域内，物业公司将不定期进行灭鼠药投放、虫害喷药等工作，请各宠物饲养人留意您的宠物安全，以免发生意外事件。</span> \r\n</p>\r\n<div>\r\n	<br />\r\n</div>', '文明饲养宠物', '2015-06-11 01:43:15', '/ump/attached/wx_image/8/20150615/20150615160629_694.jpg', '文明饲养宠物', null, null, null, '3', '');
INSERT INTO `wcc_culture_life` VALUES ('28', '', '', null, '<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150615/20150615160808_568.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	为保持本小区干净、整洁，使住户享有优雅舒适的生活环境，特制定本公约。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>小区实行垃圾分类丢弃管理，各住户倒垃圾时应按规定分类装袋，并于规定时间内将垃圾袋放到设置的垃圾桶内。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>不准乱倒垃圾、污水、杂物，不准随地吐痰和大小便。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>公共场所不准乱涂画、乱张贴、乱搭挂（如广告、标语、传单等），违者必须限期清理现场、复原。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>饲养宠物的住户应妥善管理好自己的宠物，不得让宠物大声嚎叫、伤及他人，不得让宠物在公共场所大小便。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>严禁践踏、占用绿地，损坏、涂改建筑小品。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>不准随意移动垃圾桶的摆放位置。住户淋花、晾晒拖把时应注意不要将水滴到楼下。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>住户在装修过程中勿将装修垃圾及废物弃于走廊及公共区域，不得与生活垃圾混淆，要保持环境清洁，做到日产日清。\r\n</p>\r\n<p>\r\n	<strong><span style=\"color:#C1272D;\">►</span></strong>保持小区安静，不得在公众休息时间（12：00~14：00，20：00~8：00）制造噪音影响他人休息。\r\n</p>\r\n<div>\r\n	<br />\r\n</div>', '小区公共场所使用须知', '2015-06-11 01:44:06', '/ump/attached/wx_image/8/20150615/20150615160756_405.jpg', '小区公共场所使用须知', null, null, null, '3', '');
INSERT INTO `wcc_culture_life` VALUES ('29', '', '', null, '<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150618/20150618175152_537.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<span style=\"color:#727171;font-family:sans-serif;font-size:16px;line-height:1.5;background-color:#FFFFFF;\">近日社区接到很多投诉，该栋业主/住户经常将生活垃圾、脏水、烟头、剩饭菜、烂水果、女性用品、甚至粪便等物品抛到阳台外面，这种不文明的行为严重影响了楼下商铺及住户的生活，社区管理处也多次上门劝说无效。</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	《广州市城市市容和环境卫生管理规定》早有规定，“乱倒生活废弃物的，从高空、建（构）筑物向外掷物、泼水的，随意抛弃死禽畜的，责令其处理干净并按每次（头）处以100元以上200元以下的罚款。高空掷物造成人身伤害的，视情节轻重，依法追究责任。”\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	社区管理处再次呼吁广大业主住户：一方面自我约束和教育家人杜绝高空抛物现象；另一方面，做好相互监督，对高空抛物的行为予以强烈谴责，并及时通知管理处出面制止。请配合和支持管理处工作人员做好以下几点：\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	一、不要从阳台、窗户等位置抛弃任何物品；\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	二、不要在窗外悬挂花篮、晾晒物品；\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	三、请勿在窗台摆放花盆、衣架及堆放杂物等；\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	四、阳台放置的花盆、清洁工具等物品，请固定好，以防被风吹落；\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	五、教育家中小孩不要随手将玩具等物品往楼下扔。请各位业主、住户珍爱我们的家园，杜绝高空抛物事件发生，为创造优美、舒适、和谐的生活环境而努力。\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;background-color:#FFFFFF;\">\r\n	<br />\r\n</p>\r\n<p class=\"MsoNormal\" style=\"color:#727171;font-family:sans-serif;font-size:16px;text-align:right;background-color:#FFFFFF;\">\r\n	交通银行财智社区\r\n</p>\r\n<p>\r\n	<br />\r\n</p>', '拒绝高空抛物 共建和谐社区', '2015-06-18 05:31:38', '/ump/attached/wx_image/8/20150618/20150618174651_266.jpg', '拒绝高空抛物，你行动了吗？', null, null, null, '5', '');
INSERT INTO `wcc_culture_life` VALUES ('30', '', '', null, '<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150618/20150618183009_270.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<p style=\"margin-left:0cm;text-indent:18.0pt;\">\r\n		在小区花园、广场等公共场所，经常会有居民因为晨练或者娱乐活动产生高分贝噪音，影响周围居民生活，引发多起纠纷。\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		为了创建和谐社区，特制定以下公约：\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		1、社区健身队每天早6:00至8:00、晚18:00至20:00活动，在社区范围内的步行广场、住宅区内健身场地等公共场所开展娱乐健身活动时，设备音量不得大于60分贝；\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		2、午间12:00至14:00、夜间20:00以后，停止使用任何扰民的家装工具，避免对周围居民造成环境噪声污染；\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		3、不在住宅区和公共场所大声喧哗或进行其他产生环境噪声、影响他人生活、学习的活动；\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		4、严禁在社区内任意摆摊，禁止宠物狗在楼道、小区内犬吠扰民；\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		5、严禁小区内长时间停留废品收购人员，禁止废品收购人员在楼间院落用喇叭或扩音设备叫卖扰民；\r\n	</p>\r\n	<p style=\"text-indent:18pt;\">\r\n		6、辖区的商业网点开展商业活动，不使用高音量喇叭或扩音设备。\r\n	</p>\r\n</p>', '社区为防止噪音 制定“居民公约”，共治噪音', '2015-06-18 18:31:10', '/ump/attached/wx_image/8/20150618/20150618182946_555.jpg', '社区为防止噪音 制定“居民公约”，共治噪音', null, null, null, '0', '');
INSERT INTO `wcc_culture_life` VALUES ('32', '', '', null, '<img src=\"/ump/attached/wx_image/8/20150707/20150707110403_514.jpg\" alt=\"\" />', '美好生活', '2015-07-07 11:04:08', '/ump/attached/wx_image/8/20150707/20150707110133_709.jpg', '美好生活', null, null, null, '0', '');
INSERT INTO `wcc_culture_life` VALUES ('33', null, null, null, null, null, null, null, '上海1', null, null, null, '3', null);
INSERT INTO `wcc_culture_life` VALUES ('34', null, null, null, null, null, null, null, '上海2', null, null, null, '3', null);
INSERT INTO `wcc_culture_life` VALUES ('35', null, null, null, null, null, null, null, '上海3', null, null, null, '3', null);
INSERT INTO `wcc_culture_life` VALUES ('36', null, null, null, null, null, null, null, '上海4', null, null, null, '3', null);
INSERT INTO `wcc_culture_life` VALUES ('37', null, null, null, null, null, null, null, '上海5', null, null, null, '3', null);

-- ----------------------------
-- Table structure for `wcc_culture_life_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_culture_life_platform_users`;
CREATE TABLE `wcc_culture_life_platform_users` (
  `wcc_culture_life` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_culture_life`,`platform_users`),
  KEY `FK_5tcr6lpeidum50h3h6420qei5` (`platform_users`),
  KEY `FK_8edad1tiub0ql6vt8vscv9mnv` (`wcc_culture_life`),
  CONSTRAINT `wcc_culture_life_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_culture_life_platform_users_ibfk_2` FOREIGN KEY (`wcc_culture_life`) REFERENCES `wcc_culture_life` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_culture_life_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_dictionary`;
CREATE TABLE `wcc_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` double DEFAULT NULL,
  `platform_pk` varchar(255) DEFAULT NULL,
  `typecode` varchar(255) DEFAULT NULL,
  `typemark` varchar(255) DEFAULT NULL,
  `typename` varchar(255) DEFAULT NULL,
  `typetitle` varchar(255) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mrwassvwgdee91n0g7f4mgh76` (`typecode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_dictionary
-- ----------------------------
INSERT INTO `wcc_dictionary` VALUES ('1', null, null, null, null, null, null, null, null, '0', '基础逻辑', '否', 'BaseLogic', null, null, null, '0');
INSERT INTO `wcc_dictionary` VALUES ('2', null, null, null, null, null, null, null, null, '1', '基础逻辑', '是', 'BaseLogic', null, null, null, '0');

-- ----------------------------
-- Table structure for `wcc_en_list`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_en_list`;
CREATE TABLE `wcc_en_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area` varchar(200) DEFAULT NULL,
  `dept` varchar(200) DEFAULT NULL,
  `des` varchar(1000) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `for_dept` varchar(200) DEFAULT NULL,
  `gender` varchar(200) DEFAULT NULL,
  `good_at` varchar(200) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `stu_no` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_en_list
-- ----------------------------
INSERT INTO `wcc_en_list` VALUES ('2', 'fdsf', 'fdsf', 'fdf', 'fdsf', 'fsdf', '男', 'fd', '2016-09-04 17:07:44', null, 'sdfdf', '1002');

-- ----------------------------
-- Table structure for `wcc_fees`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_fees`;
CREATE TABLE `wcc_fees` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `cellphone` varchar(255) DEFAULT NULL,
  `door_num` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_time_str` varchar(255) DEFAULT NULL,
  `item_pk` bigint(20) DEFAULT NULL,
  `month` datetime DEFAULT NULL,
  `month_str` varchar(255) DEFAULT NULL,
  `other_url` varchar(255) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `cbd_wcc_proprietor` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g36iujbfuxkcb9s5ep8fv88sh` (`cbd_wcc_proprietor`),
  CONSTRAINT `wcc_fees_ibfk_1` FOREIGN KEY (`cbd_wcc_proprietor`) REFERENCES `cbd_wcc_proprietor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_fees
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_fees_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_fees_platform_users`;
CREATE TABLE `wcc_fees_platform_users` (
  `wcc_fees` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_fees`,`platform_users`),
  KEY `FK_fqsg23uclglu82pprp63edu1l` (`platform_users`),
  KEY `FK_ocxjllgsaoi38a9or3ppnrnwt` (`wcc_fees`),
  CONSTRAINT `wcc_fees_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_fees_platform_users_ibfk_2` FOREIGN KEY (`wcc_fees`) REFERENCES `wcc_fees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_fees_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_financial_product`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_financial_product`;
CREATE TABLE `wcc_financial_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `arrival_date` datetime DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `expected_yield` varchar(255) DEFAULT NULL,
  `financial_name` varchar(255) DEFAULT NULL,
  `increasing_money` bigint(20) DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_time_str` varchar(255) DEFAULT NULL,
  `investment_horizon` varchar(255) DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `max_money` bigint(20) DEFAULT NULL,
  `min_money` bigint(20) DEFAULT NULL,
  `product_code` varchar(255) DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `risk_level` varchar(255) DEFAULT NULL,
  `sale_begin_date` datetime DEFAULT NULL,
  `sale_end_date` datetime DEFAULT NULL,
  `theme_image` varchar(255) DEFAULT NULL,
  `theme_image_text` varchar(255) DEFAULT NULL,
  `total_money` bigint(20) DEFAULT NULL,
  `total_number` int(11) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `value_date` datetime DEFAULT NULL,
  `is_using` tinyint(1) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `ck` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_financial_product
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_financial_product_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_financial_product_platform_users`;
CREATE TABLE `wcc_financial_product_platform_users` (
  `wcc_financial_product` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_financial_product`,`platform_users`),
  KEY `FK_93sx5vfylhkkn4nkyewjy5ogd` (`platform_users`),
  KEY `FK_suxde6yfj3m9sj095hekvj56y` (`wcc_financial_product`),
  CONSTRAINT `wcc_financial_product_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_financial_product_platform_users_ibfk_2` FOREIGN KEY (`wcc_financial_product`) REFERENCES `wcc_financial_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_financial_product_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_financial_user`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_financial_user`;
CREATE TABLE `wcc_financial_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `credential_number` varchar(255) DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `credential_type` bigint(20) DEFAULT NULL,
  `wcc_friend` bigint(20) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `platform_users` varchar(255) DEFAULT NULL,
  `ck` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3r3cvlq5mwuqil7x05b9e8ypq` (`credential_type`),
  KEY `FK_56mj4drv8ln2asb5uqludc4bs` (`wcc_friend`),
  CONSTRAINT `wcc_financial_user_ibfk_1` FOREIGN KEY (`credential_type`) REFERENCES `wcc_credential_type` (`id`),
  CONSTRAINT `wcc_financial_user_ibfk_2` FOREIGN KEY (`wcc_friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_financial_user
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_financial_user_wcc_financial_products`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_financial_user_wcc_financial_products`;
CREATE TABLE `wcc_financial_user_wcc_financial_products` (
  `wcc_financial_user` bigint(20) NOT NULL,
  `wcc_financial_products` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_financial_user`,`wcc_financial_products`),
  KEY `FK_bad4iy79hfjynbr4f9cnbql4s` (`wcc_financial_products`),
  KEY `FK_la5e95vsb8t2gtuprder65q2i` (`wcc_financial_user`),
  CONSTRAINT `wcc_financial_user_wcc_financial_products_ibfk_1` FOREIGN KEY (`wcc_financial_products`) REFERENCES `wcc_financial_product` (`id`),
  CONSTRAINT `wcc_financial_user_wcc_financial_products_ibfk_2` FOREIGN KEY (`wcc_financial_user`) REFERENCES `wcc_financial_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_financial_user_wcc_financial_products
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_friend`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_friend`;
CREATE TABLE `wcc_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area` varchar(400) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `from_type` int(11) DEFAULT NULL,
  `head_img` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_validated` tinyint(1) DEFAULT NULL,
  `message_end_time` datetime DEFAULT NULL,
  `nick_name` varchar(500) DEFAULT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `remark_name` varchar(4000) DEFAULT NULL,
  `send_email_time` datetime DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `signature` varchar(4000) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `user_info` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `wgroup` bigint(20) DEFAULT NULL,
  `wccqrcode` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `FK_lr4j1sjiyit93ejkok6plowjx` (`organization`),
  KEY `FK_1s8ft7v6f5oo7irh2390jxc0v` (`platform_user`),
  KEY `FK_ju7bbqmvx7fc26xor78kifna` (`wgroup`),
  KEY `FK_o2lna4cwcpmg1b47erxyxschf` (`wccqrcode`),
  CONSTRAINT `wcc_friend_ibfk_1` FOREIGN KEY (`wccqrcode`) REFERENCES `wcc_qr_code` (`id`),
  CONSTRAINT `wcc_friend_ibfk_2` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_friend_ibfk_3` FOREIGN KEY (`wgroup`) REFERENCES `wcc_group` (`id`),
  CONSTRAINT `wcc_friend_ibfk_4` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_friend
-- ----------------------------
INSERT INTO `wcc_friend` VALUES ('1', '中国湖南永州', '永州', '中国', '0', 'http://wx.qlogo.cn/mmopen/10ibV7ZFZ0voJODyP0IWbe3z5piblpyFVle7licqSQ00sbEzgIG94N4SdzcEN2oBH2Yldpkqo05dzYk8AsNbMgCn1RDs7RicP4gc/0', '2016-04-28 17:51:41', '1', '1', null, 'remember+to+ME%C2%AE', 'oo33Ssj2N3hTb7PTgwPKBM3Qtsbg', '湖南', null, null, '0', null, '2015-08-12 15:16:19', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('2', '中国上海嘉定', '嘉定', '中国', '0', 'http://wx.qlogo.cn/mmopen/LPLYlyQ5GAqk3ic9wfrXGpH4ib4Zia5yt9o2n3x599HS8zDiaUcJIKDEXHjSwTzJ3zxr23OjIcAZUlD4y1YPvibaRkQ/0', '2016-04-28 17:51:41', '1', '1', null, 'aria', 'oo33SsvEFLLXFa1nVgS-Xy4y8GL8', '上海', null, null, '1', null, '2015-11-23 15:10:49', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('3', '', '', '', '0', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM7hdRGVn6ibJUyS9c6ugGgsAPAhuZqKkAKK7N46GmkHDdNL2WHLwS8JPmlRGRfgadLgicZh7nrTGpaY9mMYv2N6BHq9sCb7HyGbU/0', '2016-04-28 17:51:41', '1', '1', null, '%E8%B5%B5%E8%B5%B5', 'oo33SsiicFjUM7SgXt_U8FMDzxSE', '', null, null, '1', null, '2016-04-21 14:57:28', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('4', '中国上海', '', '中国', '0', 'http://wx.qlogo.cn/mmopen/0B9DibWuVFwxowlrztUOyRbOYLqV0wlJE7Fb0sfBTt1w41L7llgRTwbN8bw5o9YymBmBQKicJNe9PH28q2h7BIxdYiceSr6mUlH/0', '2016-04-28 17:51:41', '1', '1', null, 'Emp', 'oo33Sspe-zrQJmlPhBzBG_1bf4-o', '上海', null, null, '0', null, '2016-04-06 11:20:30', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('5', '澳大利亚堪培拉', '', '澳大利亚', '1', 'http://wx.qlogo.cn/mmopen/10ibV7ZFZ0vpZZYA3VUb4Lf3MKlnblbIQ6Le6F3Sff1ohLqC9P6ORl5WkvF8K1eWicM4Zvl9XUlQRhL4icdcSk4v66w5sJtCSfia/0', '2016-04-28 17:51:41', '1', '1', '2016-12-30 16:02:28', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', '堪培拉', null, null, '0', null, '2016-09-10 15:13:53', null, '28', null, '1', '5', null);
INSERT INTO `wcc_friend` VALUES ('6', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/vlxMKIZ8tXCHmbhmYGhlDvtNib5T5iaFP5VUeDZ61p4OVzgu2fthAiaQsQYBdJItw2brCzWJwLSWKX8G1D1YCCNxLChz90lMFg4/0', '2016-04-28 17:51:41', '1', '1', null, '%E9%9B%AA%E7%A2%A7%E5%85%91%E5%8F%AF%E4%B9%90', 'oo33SstSMLhwj5cQhn0Z_QeNezPA', '上海', null, null, '0', null, '2016-02-24 16:36:40', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('7', '中国江苏无锡', '无锡', '中国', '0', 'http://wx.qlogo.cn/mmopen/0B9DibWuVFwxmzwU5nlqu47yr6NVV7wyhpugLHECcgic2Uqf96zb2icmicZZj5fh3V92740QPQk0qKcUtibiaNdQwuy58pibNSwvnXU/0', '2016-04-28 17:51:41', '1', '1', null, '%E6%98%95%E5%AE%9D%E8%B4%9D', 'oo33SslEtft74FhUxV49tkjqRLq8', '江苏', null, null, '0', null, '2016-02-14 20:50:23', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('8', '中国福建漳州', '漳州', '中国', '0', 'http://wx.qlogo.cn/mmopen/LPLYlyQ5GApxSQyCD1r2ibQ3waCNk8coYUZMEkaOopoRGGueG5qUdAN2XeGpjmmiba4AOlzn3DBQDiaxuDbUibE0djFaDJ5lZJKz/0', '2016-04-28 17:51:41', '1', '1', null, '%E5%88%B6%E5%86%B7%E7%BB%B4%E4%BF%AE%E5%86%B0%E7%AE%B1%E7%A9%BA%E8%B0%83', 'oo33SsqVy-zLHUnV6FAjqjoOSe0o', '福建', null, null, '0', null, '2015-10-24 23:01:23', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('9', '中国上海', '', '中国', '1', 'http://wx.qlogo.cn/mmopen/LPLYlyQ5GArNO0z4KawatvQqiasx81nFH1yaRuuK0zNNDMx6CibKSru65nAW1IlicoSywvRTVV3W8SnyiaPh9pbIvBlPWATYoHa3/0', '2016-04-28 17:51:41', '1', '1', null, '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'oo33Ssmn15x6dyjMTmrBntTVJSmA', '上海', null, null, '1', null, '2016-06-27 16:18:24', null, '1', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('10', '', '', '', '1', '', '2016-05-10 19:16:20', '1', '1', '2016-06-03 10:34:50', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', '', null, null, '2', null, '2016-06-03 10:11:33', null, '24', null, '2', null, null);
INSERT INTO `wcc_friend` VALUES ('11', '中国上海金山', '金山', '中国', '1', 'http://wx.qlogo.cn/mmopen/5aZAWRoxGD6vyuR9GN2iajP25Dhvz4mdPpxjkkcqUyK4dZSia7QtTjKR4KGd8iaVQr70bMWicEsQuvmto31rABtTOw/0', '2016-05-10 20:42:26', '1', '1', null, 'Arsenal', 'oq4ytwTRsE4J3AmAUU_NW5Iiuiy0', '上海', null, null, '0', null, '2016-05-10 20:39:48', null, '0', null, '2', null, null);
INSERT INTO `wcc_friend` VALUES ('12', '澳大利亚堪培拉', '', '澳大利亚', '1', 'http://wx.qlogo.cn/mmopen/5aZAWRoxGD4Ey2ath00fgqGQxVAaFPIEdnaJyFeEZJoFeL0w8iatAYuaxwNISvQD8nx5ickbTib86IWHIxm6WkhoCNicFnCibc7Gw/0', '2016-05-10 20:43:45', '1', '1', '2016-06-03 11:18:43', 'Mrchan', 'oq4ytwVdtbSk8qzb2gknqQmb-JJQ', '堪培拉', null, null, '0', null, '2016-05-10 20:41:07', null, '1', null, '2', null, null);
INSERT INTO `wcc_friend` VALUES ('13', '中国上海金山', '金山', '中国', '1', 'http://wx.qlogo.cn/mmopen/10ibV7ZFZ0voLJukUws6Mrq92F1CibJiaeHhT1FDVYNLibSIoA9cDakXZJCCqu8jZtG1ZDLhGGyWjp0baaEnQXFP7A/0', '2016-05-20 10:13:36', '1', '1', null, 'Arsenal', 'oo33Ssj3L1PWG7WEwvl4O4Aldfcw', '上海', null, null, '0', null, '2016-05-20 10:10:57', null, '0', null, '1', null, null);
INSERT INTO `wcc_friend` VALUES ('14', '中国福建福州', '福州', '中国', '1', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM6QSM7LCTLGibbqCpvxMhRwHXG4VGr3iapqW0KPjyTg4WczzDq17T3icRRFs1Q0XliatOfU4H71s0SCQXQLKGiazKibkhpCRfrtr82xM/0', '2016-10-01 10:32:17', '1', '1', null, '%E3%80%96%E2%98%86%F0%9F%8F%83%F0%9F%8F%83%F0%9F%92%8E%F0%9F%92%8E%E2%98%86%E3%80%97', 'oIBCFs1wTeMgO2plON9bzS_nzKOE', '福建', null, null, '0', null, '2016-10-01 10:36:44', null, '3', null, '5', null, null);
INSERT INTO `wcc_friend` VALUES ('15', '澳大利亚堪培拉', '', '澳大利亚', '1', 'http://wx.qlogo.cn/mmopen/TPBckpB32ct1xNvP8YFRUvD9j1VF7ic2nhqJCWxuvLB4Nia0BVgRgA4f77AoTXficWvfRvxyTJNJFGCqXCvF6uVTBXkib0ibYUCc4/0', '2016-10-01 10:34:47', '1', '1', null, 'Mrchan', 'oIBCFsxbUdAsQf3f99t4p5mmcty8', '堪培拉', null, null, '0', null, '2016-10-01 10:31:29', null, '0', null, '5', null, null);

-- ----------------------------
-- Table structure for `wcc_friends_appointment`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_friends_appointment`;
CREATE TABLE `wcc_friends_appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `farea` varchar(200) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `fnick_name` varchar(200) DEFAULT NULL,
  `fopen_id` varchar(100) DEFAULT NULL,
  `fphone` varchar(50) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `product_type` varchar(100) DEFAULT NULL,
  `result` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_friends_appointment
-- ----------------------------
INSERT INTO `wcc_friends_appointment` VALUES ('2', '静安区静安寺', '张英杰1', '%E5%BC%A0%E8%8B%B1%E6%9D%B0', 'opFCTw4fGJ3DWKziZfOwY_z_OflI', '13472555942', '2016-05-20 16:16:51', '生意缺钱', '融资缺钱', '待处理');
INSERT INTO `wcc_friends_appointment` VALUES ('10', '静安区静安寺', '张英杰2', '%E5%BC%A0%E8%8B%B1%E6%9D%B0', 'opFCTw4fGJ3DWKziZfOwY_z_OflI', '13472555942', '2016-05-20 16:16:51', '生意缺钱', '融资缺钱', '待处理');
INSERT INTO `wcc_friends_appointment` VALUES ('11', '长宁区天山', 'dfd', null, null, '18817713445', '2016-05-27 11:54:39', '钱好贷产品2', '融资缺钱', '待处理');

-- ----------------------------
-- Table structure for `wcc_friend_wcc_financial_user`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_friend_wcc_financial_user`;
CREATE TABLE `wcc_friend_wcc_financial_user` (
  `wcc_friend` bigint(20) NOT NULL,
  `wcc_financial_user` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_friend`,`wcc_financial_user`),
  UNIQUE KEY `UK_33gmkcllu9bwumowora0qsmmw` (`wcc_financial_user`),
  KEY `FK_33gmkcllu9bwumowora0qsmmw` (`wcc_financial_user`),
  KEY `FK_6y8joy98orhaaqoclnosimu9h` (`wcc_friend`),
  CONSTRAINT `wcc_friend_wcc_financial_user_ibfk_1` FOREIGN KEY (`wcc_financial_user`) REFERENCES `wcc_financial_user` (`id`),
  CONSTRAINT `wcc_friend_wcc_financial_user_ibfk_2` FOREIGN KEY (`wcc_friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_friend_wcc_financial_user
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_friend_wcc_recommend_info`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_friend_wcc_recommend_info`;
CREATE TABLE `wcc_friend_wcc_recommend_info` (
  `wcc_friend` bigint(20) NOT NULL,
  `wcc_recommend_info` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_friend`,`wcc_recommend_info`),
  UNIQUE KEY `UK_77q8r3ajh28puef4b3ncypqil` (`wcc_recommend_info`),
  KEY `FK_77q8r3ajh28puef4b3ncypqil` (`wcc_recommend_info`),
  KEY `FK_bqd42qn3dfs2wseiqkli2r0ev` (`wcc_friend`),
  CONSTRAINT `wcc_friend_wcc_recommend_info_ibfk_1` FOREIGN KEY (`wcc_recommend_info`) REFERENCES `wcc_recommend_info` (`id`),
  CONSTRAINT `wcc_friend_wcc_recommend_info_ibfk_2` FOREIGN KEY (`wcc_friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_friend_wcc_recommend_info
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_group`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_group`;
CREATE TABLE `wcc_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `name` varchar(300) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_eemvihqtaxxdftpp4tf1gmppw` (`platform_user`),
  CONSTRAINT `wcc_group_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_group
-- ----------------------------
INSERT INTO `wcc_group` VALUES ('1', '0', '2016-05-05 15:45:40', '1', 'fdfd', '0', null);
INSERT INTO `wcc_group` VALUES ('2', '0', '2016-05-05 15:46:30', '1', 'fdfd', '0', null);
INSERT INTO `wcc_group` VALUES ('4', '0', '2016-05-05 15:46:36', '1', 'dfdg', '0', null);
INSERT INTO `wcc_group` VALUES ('5', '101', '2016-05-05 15:46:36', '1', '位置组', '1', '1');
INSERT INTO `wcc_group` VALUES ('6', '0', '2016-05-05 15:46:51', '1', 'dfd', '0', null);

-- ----------------------------
-- Table structure for `wcc_group_message`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_group_message`;
CREATE TABLE `wcc_group_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area` varchar(400) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `digest` varchar(4000) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `media_url` varchar(400) DEFAULT NULL,
  `msg_num` int(11) NOT NULL,
  `send_status` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `title` varchar(400) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_group_message
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_group_mess_friend`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_group_mess_friend`;
CREATE TABLE `wcc_group_mess_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_group_mess_friend
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_img_save`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_img_save`;
CREATE TABLE `wcc_img_save` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `img_path` varchar(500) DEFAULT NULL,
  `type` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_img_save
-- ----------------------------
INSERT INTO `wcc_img_save` VALUES ('1', '/ump/attached/wx_image/8/20150629/20150629171851_732.jpg', '1');
INSERT INTO `wcc_img_save` VALUES ('2', '/ump/attached/wx_image/8/20150629/20150629180154_861.jpg', '2');
INSERT INTO `wcc_img_save` VALUES ('3', '/ump/attached/wx_image/8/20150702/20150702175431_948.jpg', '1');
INSERT INTO `wcc_img_save` VALUES ('4', '/ump/attached/wx_image/8/20150702/20150702175536_684.jpg', '2');
INSERT INTO `wcc_img_save` VALUES ('5', '/ump/attached/wx_image/8/20150702/20150702175555_119.jpg', '3');
INSERT INTO `wcc_img_save` VALUES ('6', '/ump/attached/wx_image/8/20150702/20150702175611_389.jpg', '3');
INSERT INTO `wcc_img_save` VALUES ('7', '/ump/attached/wx_image/8/20150708/20150708141123_857.jpg', '4');

-- ----------------------------
-- Table structure for `wcc_img_save_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_img_save_platform_users`;
CREATE TABLE `wcc_img_save_platform_users` (
  `wcc_img_save` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_img_save`,`platform_users`),
  KEY `FK_ro1rxh4uqhgi5l03a9f5r0t8v` (`platform_users`),
  KEY `FK_s8sx1psslo2j7vrrcnwu4kecd` (`wcc_img_save`),
  CONSTRAINT `wcc_img_save_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_img_save_platform_users_ibfk_2` FOREIGN KEY (`wcc_img_save`) REFERENCES `wcc_img_save` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_img_save_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_inter_active_app`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_inter_active_app`;
CREATE TABLE `wcc_inter_active_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_open` tinyint(1) DEFAULT NULL,
  `keyword` varchar(4000) NOT NULL,
  `name` varchar(400) NOT NULL,
  `url` varchar(4000) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_inter_active_app
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_itemotherinfo`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_itemotherinfo`;
CREATE TABLE `wcc_itemotherinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_pk` varchar(255) DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `intro_title` varchar(4000) DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `organization_pk` varchar(255) DEFAULT NULL,
  `other_intro` varchar(4000) DEFAULT NULL,
  `other_pic` varchar(4000) DEFAULT NULL,
  `other_title` varchar(500) DEFAULT NULL,
  `other_type` varchar(500) DEFAULT NULL,
  `platform_pk` varchar(255) DEFAULT NULL,
  `public_time` datetime DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `item_pk` bigint(20) DEFAULT NULL,
  `url` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_74ekgbuh5b27du4i7i9py0b2s` (`item_pk`),
  CONSTRAINT `wcc_itemotherinfo_ibfk_1` FOREIGN KEY (`item_pk`) REFERENCES `wcc_appartment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_itemotherinfo
-- ----------------------------
INSERT INTO `wcc_itemotherinfo` VALUES ('21', null, null, null, null, null, null, null, '小区基本概况', null, null, '<p>\r\n	<span style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;line-height:23.2727279663086px;background-color:#FFFFFF;\"><img src=\"/ump/attached/wx_image/8/20150615/20150615113041_978.jpg\" width=\"580\" height=\"300\" alt=\"\" /></span> \r\n</p>\r\n<p>\r\n	<span style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;line-height:23.2727279663086px;background-color:#FFFFFF;\"><br />\r\n</span> \r\n</p>\r\n<p>\r\n	<span style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;line-height:23.2727279663086px;background-color:#FFFFFF;\"><span style=\"background-color:#FF9900;\"><span style=\"background-color:#FFFFFF;\"> </span>兰亭荟苑东面为40米宽的平云路、</span><span style=\"color:#E53333;\">华南快速干线，高等人民法院和兰亭上东、华颖轩隔路相望；南面临近广州无线电集团总部；西面临</span>10米宽规划路，与星汇雅苑隔路相对；北面临10米宽规划路，与规划中的小学，跑马地花园相近<strong></strong>。据了解，兰亭荟苑立面以新古典主义风格为主调，以高贵的石材质感，强调垂直的线条，并配以古典风格的装饰<span style=\"font-family:SimHei;\">。</span><strong><em><u><span style=\"font-family:SimHei;\">强调新古典风格的强烈视觉印象和华美高尚的气质。</span><span style=\"font-size:24px;font-family:SimHei;\"></span></u></em></strong></span> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>', '/ump/attached/wx_image/8/20150615/20150615120620_617.jpg', '小区基本概况', '2', null, '2015-07-07 15:29:51', null, null, null, '20', 'createItme');
INSERT INTO `wcc_itemotherinfo` VALUES ('22', null, null, null, null, null, null, null, '地理位置与周边', null, null, '<p>\r\n	<span style=\"color:inherit;font-size:18px;\"><strong><span class=\"135brush\" style=\"color:#FFFFFF;background-color:#374AAE;\">周边设施</span></strong></span> \r\n</p>\r\n<p>\r\n	<span style=\"font-family:Arial;font-size:16px;color:#3E3E3E;line-height:1.5;background-color:#FFFFFF;\"><strong><br />\r\n</strong></span>\r\n</p>\r\n<p>\r\n	<span style=\"font-family:Arial;font-size:16px;color:#3E3E3E;line-height:1.5;background-color:#FFFFFF;\"><strong>-----中小学-----</strong></span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"> 猎德小学、冼村小学、天河实验小学、华南师范大学附属南国学校、天河中学、先烈东路小学珠江新城校区、五羊中学</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<br />\r\n<span style=\"font-family:Arial;font-size:16px;\"> -----幼儿园-----</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">海运明月幼儿园、珠江新城海滨幼儿园、名雅苑幼儿园、体育东路幼儿园、五羊新城军区政治部幼儿园</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><br />\r\n</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><strong>-----商场-----</strong></span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">广百商场、友谊商店、高德置地、天河城、正佳广场、太古汇</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><br />\r\n</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><strong>-----医院-----</strong></span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">广州华侨医院、广州妇女儿童医疗中心、广州市第十二人民医院</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><br />\r\n</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><strong>-----银行-----</strong></span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:微软雅黑;font-size:14px;background-color:#FFFFFF;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">工商银行、民生银行、交通银行、浦发银行、中国银行、农业银行、光大银行</span> \r\n</p>', '/ump/attached/wx_image/8/20150615/20150615152808_293.jpg', '小区地理位置与周边', '2', null, '2015-06-18 14:10:19', null, null, null, '20', 'createItmeDitu');
INSERT INTO `wcc_itemotherinfo` VALUES ('23', null, null, null, null, null, null, null, '小区物管信息', null, null, '<p style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;text-indent:2em;background-color:#FFFFFF;\">\r\n	<span style=\"font-size:15px;\">广电物业是中国物业管理协会理事单位、广东省物业管理协会常务理事单位、广州市物业管理协会副会长单位。</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;text-indent:2em;background-color:#FFFFFF;\">\r\n	<span style=\"font-size:15px;\"><br />\r\n</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;text-indent:2em;background-color:#FFFFFF;\">\r\n	<img src=\"/ump/attached/wx_image/8/20150703/20150703163309_267.jpg\" alt=\"\" /> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;text-indent:2em;background-color:#FFFFFF;\">\r\n	<span style=\"font-size:15px;\"><br />\r\n</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;text-indent:2em;background-color:#FFFFFF;\">\r\n	<span style=\"font-size:15px;\">广州市同行中率先通过国际ISO9001新版质量管理体系和国际ISO14001环境保护体系双重认证、OHSAS18001认证。</span> \r\n</p>\r\n<p style=\"color:#3E3E3E;font-family:\'Helvetica Neue\', Helvetica, \'Hiragino Sans GB\', \'Microsoft YaHei\', Arial, sans-serif;font-size:16px;text-indent:2em;background-color:#FFFFFF;\">\r\n	<span style=\"font-size:15px;\"> 目前，公司是国内第一家加入物业管理国际金钥匙联盟的企业，管理服务面积超过1000万平方米，共60余个项目服务中心，员工人数达2300多人，控股两家子公司，通过全委、顾问、合作、管理输出等，服务区域正以华南板块为核心，不断向内地大中城市辐射，并以每年30%左右的速度向外拓展规模、稳步发展，成为中国华南地区最具竞争力的物业管理企业之一。</span> \r\n</p>', '/ump/attached/wx_image/8/20150615/20150615153750_190.jpg', '小区物管信息', '2', null, '2015-07-06 14:16:43', null, null, null, '20', 'createItmeWg');
INSERT INTO `wcc_itemotherinfo` VALUES ('24', null, null, null, null, null, null, null, '项目介绍', null, null, '<img src=\"/ump/attached/wx_image/8/20150615/20150615154539_38.jpg\" alt=\"\" /> \r\n<p>\r\n	<br />\r\n</p>\r\n<p style=\"text-indent:2em;\">\r\n	<span style=\"font-size:15px;\"><br />\r\n</span> \r\n</p>\r\n<p style=\"text-indent:2em;\">\r\n	<span style=\"font-size:15px;\">保利林海山庄定位于广州市最高的城市半山生态社区的保利林海山庄，是保利地产继白云山庄、林语山庄之后的第三个“山庄”系列产品，定位为广州最高的半山高级公寓，以75平方米至110平方米的两房和三房单位为主。配有一定量的130平方米大三房及少量的更大面积大户型单位。该项目地势较高，可俯瞰华南植物园以及周边山景，对内则可望园林景观，定位为广州最高的半山高级公寓，以75平方米至110平方米的两房和三房单位为主。</span> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#FFFFFF;background-color:#E36C09;\">基本信息</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#000000;\">区域板块：华南植物园<br />\r\n项目位置：龙洞华南植物园东北角(地处华南植物园正门对面内进)<br />\r\n交通状况：28、39、84、534、535、564路公交；地铁六号线<br />\r\n建筑类型：塔楼<br />\r\n物业类别：普通住宅<br />\r\n开发商：保利房地产(集团)股份有限公司<br />\r\n物业公司：保利广州物业管理公司<br />\r\n物业费：1.8元/平方/月 </span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<br />\r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#FFFFFF;background-color:#E36C09;\">价格信息</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#000000;\">均价：7800<br />\r\n起价：7800<br />\r\n价格说明：均价7800元/平方米</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<br />\r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#FFFFFF;background-color:#E36C09;\">周边信息</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#000000;\">周边商业：工商银行、广发银行、农业银行、农村信用社、交通银行</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#000000;\">周边医院：武警医院、龙洞人民医院</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#000000;\">周边学校：华南理工大学、华南农业大学、广东工业大学北校区</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<span style=\"color:#000000;\">周边公园：龙洞植物园</span> \r\n</p>\r\n<p style=\"color:#595959;font-size:15px;\">\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>', '/ump/attached/wx_image/8/20150709/20150709101610_294.jpg', '项目介绍', '1', null, '2015-07-09 10:16:16', null, null, null, '21', 'newCreateItme');
INSERT INTO `wcc_itemotherinfo` VALUES ('25', null, null, null, null, null, null, null, '户型信息', null, null, '<img src=\"/ump/attached/wx_image/8/20150617/20150617195540_151.jpg\" width=\"580\" height=\"300\" alt=\"\" /> <img src=\"/ump/attached/wx_image/8/20150617/20150617195601_661.jpg\" width=\"580\" height=\"300\" alt=\"\" />', '/ump/attached/wx_image/8/20150709/20150709101813_490.jpg', '户型信息', '1', null, '2015-07-09 10:41:16', null, null, null, '21', 'newCreateImg');
INSERT INTO `wcc_itemotherinfo` VALUES ('27', null, null, null, null, null, null, null, '都市兰亭花园', null, null, '<p>\r\n	<span style=\"background-color:#FFE500;\"><img src=\"/ump/attached/wx_image/8/20150617/20150617195210_778.jpg\" width=\"320\" height=\"240\" alt=\"\" /><br />\r\n</span> \r\n</p>\r\n<p>\r\n	<span style=\"background-color:#FFE500;\">楼盘介绍</span> \r\n</p>\r\n都市兰亭花园位于员村二横路百佳超市员村地铁站旁边，都市兰亭其地理位置优越、交通便利、环境优雅，是质量较高、配套完善的优秀住宅小区。小区内绿化率面积大，居民素质高，物业管理规范。小区由多层花园洋房和小高层电梯所组成，环境相当的好，入住率非常高。<br />\r\n小区升值潜无限力无限，现在员村已打造成国际金融区，现在已在改造建设当中，买的是现在成就的是未来。<br />\r\n<br />\r\n<span style=\"background-color:#FFE500;\">楼盘配套</span><br />\r\n区配套齐全,每栋楼下都有地下停车场,小区还有幼儿园,户外游泳池,儿童游乐室,健身房等,最为主要的就是旁边的天河公园,家门口的公园,无限的休闲空间,家庭游乐的好去处.<br />', '/ump/attached/wx_image/8/20150709/20150709101831_96.jpg', '项目介绍', '1', null, '2015-07-09 10:18:36', null, null, null, '22', 'newCreateItme');
INSERT INTO `wcc_itemotherinfo` VALUES ('28', null, null, null, null, null, null, null, '都市兰亭花园户型', null, null, '<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150618/20150618190148_45.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150618/20150618190159_366.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>', '/ump/attached/wx_image/8/20150709/20150709101904_217.jpg', '户型信息', '1', null, '2015-07-09 10:19:07', null, null, null, '22', 'newCreateImg');
INSERT INTO `wcc_itemotherinfo` VALUES ('30', null, null, null, null, null, null, null, '我的美丽社区', null, null, '人人人肉肉肉肉肉肉肉肉肉肉人人人人人人人人人人人人人人', '/ump/attached/wx_image/8/20150703/20150703140208_499.jpg', '小区基本概况', '2', null, '2015-07-03 14:26:09', null, null, null, '24', 'createItme');
INSERT INTO `wcc_itemotherinfo` VALUES ('33', null, null, null, null, null, null, null, '地理位置', null, null, '77777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777', '/ump/attached/wx_image/8/20150703/20150703145800_63.jpg', '小区地理位置与周边', '2', null, '2015-07-03 14:58:55', null, null, null, '24', 'createItmeDitu');

-- ----------------------------
-- Table structure for `wcc_item_img`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_item_img`;
CREATE TABLE `wcc_item_img` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(4000) DEFAULT NULL,
  `wcc_itemotherinfo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fwecjj3sgpbfhwxeyrwta2c3x` (`wcc_itemotherinfo`),
  CONSTRAINT `wcc_item_img_ibfk_1` FOREIGN KEY (`wcc_itemotherinfo`) REFERENCES `wcc_itemotherinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_item_img
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_leavemsg_record`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_leavemsg_record`;
CREATE TABLE `wcc_leavemsg_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `msg_content` varchar(4000) NOT NULL,
  `msg_time` datetime NOT NULL,
  `msg_user_name` varchar(4000) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_leavemsg_record
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_life_helper`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_life_helper`;
CREATE TABLE `wcc_life_helper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `background_img` varchar(255) DEFAULT NULL,
  `background_tip` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `detail_content` varchar(4000) DEFAULT NULL,
  `detail_title` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_time_str` varchar(255) DEFAULT NULL,
  `list_img` varchar(255) DEFAULT NULL,
  `list_tip` varchar(255) DEFAULT NULL,
  `other_url` varchar(255) DEFAULT NULL,
  `plat_form_account` varchar(255) DEFAULT NULL,
  `plat_form_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_life_helper
-- ----------------------------
INSERT INTO `wcc_life_helper` VALUES ('25', '/ump/attached/wx_image/8/20150615/20150615161149_756.jpg', '天气预报', null, '', null, '2015-06-11 01:46:04', '', null, '', 'http://m.weather.com.cn/mweather/101020100.shtml', null, null, '3');
INSERT INTO `wcc_life_helper` VALUES ('26', '/ump/attached/wx_image/8/20150615/20150615161207_914.jpg', '快递查询', null, '', null, '2015-06-11 01:46:49', '', null, '', 'http://m.kuaidi100.com/#input', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('27', '/ump/attached/wx_image/8/20150615/20150615161840_999.jpg', '火车票', null, '', null, '2015-06-11 01:47:37', '', null, '', 'http://wap.tieyou.com/sina/index_yupiao.html?pos=63&vt=4', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('28', '/ump/attached/wx_image/8/20150615/20150615161852_168.jpg', '个税计算', null, '', null, '2015-06-11 01:48:18', '', null, '', 'http://dp.sina.cn/dpool/tools/money/single.php?city_id=1&flag=per_tax&pos=63&vt=4', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('29', '/ump/attached/wx_image/8/20150615/20150615162026_744.jpg', '房贷计算', null, '', null, '2015-06-11 01:48:49', '', null, '', 'http://house.sina.cn/touch/tools/house_loan.html', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('30', '/ump/attached/wx_image/8/20150615/20150615161949_800.jpg', '医疗保险', null, '', null, '2015-06-11 01:49:47', '', null, '', 'http://dp.sina.cn/dpool/tools/money/single.php?flag=health_per&pos=63&vt=4', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('31', '/ump/attached/wx_image/8/20150615/20150615162002_429.jpg', '养老保险', null, '', null, '2015-06-11 01:50:13', '', null, '', 'http://dp.sina.cn/dpool/tools/money/single.php?flag=old_per&pos=63&vt=4', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('32', '/ump/attached/wx_image/8/20150615/20150615162011_467.jpg', '彩票开奖', null, '', null, '2015-06-11 01:51:22', '', null, '', 'http://loto.sina.cn/i/open.do?label=1&sid=fc055b3a-d72c-41bf-96bc-b8e436ea79ea&agentId=233258&vt=3', null, null, '2');
INSERT INTO `wcc_life_helper` VALUES ('33', '/ump/attached/wx_image/8/20150618/20150618184010_325.jpg', '食物相克', null, '食物相克', null, '2015-06-17 02:32:27', '', null, '食物相克', 'http://m.46644.com/foodke/?tpltype=weixin', null, null, '3');
INSERT INTO `wcc_life_helper` VALUES ('34', '/ump/attached/wx_image/8/20150617/20150617202916_571.jpg', '开心汇', null, '', null, '2015-06-17 08:29:19', '', null, '', 'http://m.kaixinhui.com/?ctype=graphic', null, null, '1');
INSERT INTO `wcc_life_helper` VALUES ('40', '/ump/attached/wx_image/8/20150618/20150618184426_230.jpg', '个税计算', null, '个税计算', null, '2015-06-18 18:44:37', '', null, '个税计算', 'http://m.46644.com/loan/person.php?tpltype=weixin', null, null, '0');
INSERT INTO `wcc_life_helper` VALUES ('41', '/ump/attached/wx_image/8/20150618/20150618184706_784.jpg', '算预产期', null, '算预产期', null, '2015-06-18 18:47:26', '', null, '算预产期', 'http://m.46644.com/pregnant/calculate.php?tpltype=weixin', null, null, '0');
INSERT INTO `wcc_life_helper` VALUES ('42', '/ump/attached/wx_image/8/20150618/20150618185059_620.jpg', '电视节目', null, '', null, '2015-06-18 18:51:02', '', null, '', 'http://m.46644.com/tv/?tpltype=weixin', null, null, '0');
INSERT INTO `wcc_life_helper` VALUES ('43', '/ump/attached/wx_image/8/20150618/20150618185225_754.jpg', '单位换算', null, '', null, '2015-06-18 18:52:28', '', null, '', 'http://m.46644.com/unitconvert/?tpltype=weixin', null, null, '0');
INSERT INTO `wcc_life_helper` VALUES ('44', '/ump/attached/wx_image/8/20150618/20150618185506_864.jpg', '空气指数', null, '', null, '2015-06-18 18:55:09', '', null, '', 'http://m.46644.com/aqi/?tpltype=weixin', null, null, '0');
INSERT INTO `wcc_life_helper` VALUES ('45', null, null, null, null, null, '2016-05-09 01:30:15', '', null, null, null, null, null, '0');
INSERT INTO `wcc_life_helper` VALUES ('46', null, null, null, null, null, '2016-05-09 01:30:56', '', null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for `wcc_life_helper_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_life_helper_platform_users`;
CREATE TABLE `wcc_life_helper_platform_users` (
  `wcc_life_helper` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_life_helper`,`platform_users`),
  KEY `FK_ox7x6gl4jk9mb1cag3p94ijxt` (`platform_users`),
  KEY `FK_2x9b4cjvg6wksijlfwv11gwrc` (`wcc_life_helper`),
  CONSTRAINT `wcc_life_helper_platform_users_ibfk_1` FOREIGN KEY (`wcc_life_helper`) REFERENCES `wcc_life_helper` (`id`),
  CONSTRAINT `wcc_life_helper_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_life_helper_platform_users
-- ----------------------------
INSERT INTO `wcc_life_helper_platform_users` VALUES ('45', '1');
INSERT INTO `wcc_life_helper_platform_users` VALUES ('46', '1');

-- ----------------------------
-- Table structure for `wcc_lottery_activity`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_lottery_activity`;
CREATE TABLE `wcc_lottery_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_end_info` varchar(255) DEFAULT NULL,
  `activity_introduction` varchar(4000) NOT NULL,
  `activitynstart_info` varchar(255) DEFAULT NULL,
  `activity_name` varchar(400) NOT NULL,
  `activity_remark` varchar(255) DEFAULT NULL,
  `activity_type` int(11) DEFAULT NULL,
  `award_msg` varchar(255) DEFAULT NULL,
  `cost_point` int(11) NOT NULL,
  `effective_number` int(11) NOT NULL,
  `end_time` datetime NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `insert_time` datetime NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visibale` tinyint(1) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `lottery_day_num` int(11) NOT NULL,
  `lottery_num` int(11) NOT NULL,
  `num_end_info` varchar(255) DEFAULT NULL,
  `repeat_award_reply` varchar(4000) NOT NULL,
  `start_time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  `visitor_number` int(11) NOT NULL,
  `ump_company` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c4dio0iwf3cx4my13adi0d6j1` (`ump_company`),
  CONSTRAINT `wcc_lottery_activity_ibfk_1` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_lottery_activity
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_malfunctions`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_malfunctions`;
CREATE TABLE `wcc_malfunctions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(500) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `deal_date` datetime DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_dealed` varchar(500) DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `mal_pic` varchar(500) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `organization_pk` varchar(255) DEFAULT NULL,
  `phone` varchar(500) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `item_pk` bigint(20) DEFAULT NULL,
  `malfunction_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3dk96m8chhr93xudg8rymrgq3` (`item_pk`),
  KEY `FK_afrf78ikdx3rc13twuvhmowra` (`malfunction_type`),
  CONSTRAINT `wcc_malfunctions_ibfk_1` FOREIGN KEY (`item_pk`) REFERENCES `wcc_appartment` (`id`),
  CONSTRAINT `wcc_malfunctions_ibfk_2` FOREIGN KEY (`malfunction_type`) REFERENCES `wcc_malfunction_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_malfunctions
-- ----------------------------
INSERT INTO `wcc_malfunctions` VALUES ('13', '166666', null, '2015-07-07 16:06:47', null, null, null, null, null, null, '已处理', null, '/ump/ump/cbdwccui/images/20150707040632image.jpg', '啦啦啦', null, '13764559476', '是否是个头不想在内iiiiiiiiiiiiiiii', null, null, null, '20', '6');

-- ----------------------------
-- Table structure for `wcc_malfunction_type`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_malfunction_type`;
CREATE TABLE `wcc_malfunction_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `item_pk` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rtd54cejur2p710n4af84qjes` (`item_pk`),
  CONSTRAINT `wcc_malfunction_type_ibfk_1` FOREIGN KEY (`item_pk`) REFERENCES `wcc_appartment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_malfunction_type
-- ----------------------------
INSERT INTO `wcc_malfunction_type` VALUES ('3', '电灯保修', null, '20');
INSERT INTO `wcc_malfunction_type` VALUES ('4', '水管保修', null, '20');
INSERT INTO `wcc_malfunction_type` VALUES ('6', '楼面漏水', null, '20');
INSERT INTO `wcc_malfunction_type` VALUES ('7', '漏水', null, '20');

-- ----------------------------
-- Table structure for `wcc_mass_message`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mass_message`;
CREATE TABLE `wcc_mass_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `error_count` int(11) NOT NULL,
  `filter_count` int(11) NOT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `media_id` varchar(255) DEFAULT NULL,
  `msg_id` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `send_count` int(11) NOT NULL,
  `sex` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `totle_count` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `company` bigint(20) DEFAULT NULL,
  `material` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  `provicne` bigint(20) DEFAULT NULL,
  `platform_id` bigint(20) DEFAULT NULL,
  `activitysn` varchar(4000) DEFAULT NULL,
  `audit_remark` varchar(4000) DEFAULT NULL,
  `expected_time` datetime DEFAULT NULL,
  `group_str` varchar(255) DEFAULT NULL,
  `label` varchar(4000) DEFAULT NULL,
  `regionarea` varchar(4000) DEFAULT NULL,
  `pub_organization` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2nhy70ndts8eye4ua0vpkfh7l` (`company`),
  KEY `FK_g77i1tfcjb83fny328dlp3s9j` (`material`),
  KEY `FK_bjacovkhs39d5opfxsprui2gc` (`platform_user`),
  KEY `FK_ky57ws92lpfg9bju995c717dc` (`provicne`),
  KEY `FK_7uqx32qwy489jmpb36iu4v1jd` (`pub_organization`),
  CONSTRAINT `wcc_mass_message_ibfk_1` FOREIGN KEY (`pub_organization`) REFERENCES `pub_organization` (`id`),
  CONSTRAINT `wcc_mass_message_ibfk_2` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `wcc_mass_message_ibfk_3` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_mass_message_ibfk_4` FOREIGN KEY (`material`) REFERENCES `wcc_materials` (`id`),
  CONSTRAINT `wcc_mass_message_ibfk_5` FOREIGN KEY (`provicne`) REFERENCES `wcc_province` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mass_message
-- ----------------------------
INSERT INTO `wcc_mass_message` VALUES ('1', 'rewr111111', '0', '0', '2016-07-20 14:51:20', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('2', 'dd', '0', '0', '2016-07-20 15:02:56', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('3', 'ffdgfgfgf', '0', '0', '2016-07-20 15:03:24', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('4', 'defrf', '0', '0', '2016-07-20 15:06:08', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('5', 'fdffdf', '0', '0', '2016-07-20 15:07:43', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('6', null, '0', '0', '2016-07-20 15:09:37', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('7', null, '0', '0', '2016-07-20 15:10:31', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('8', null, '0', '0', '2016-07-20 15:11:34', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('9', 'fddgfdg', '0', '0', '2016-07-20 15:11:58', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('10', 'dsfsdfdsfsdfefef', '0', '0', '2016-07-20 15:18:38', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('11', 'fgfdsf3r33545', '0', '0', '2016-07-20 15:22:52', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('12', 'testtt~', '0', '0', '2016-07-20 15:36:16', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('13', null, '0', '0', '2016-07-20 15:40:27', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('14', null, '0', '0', '2016-07-20 15:43:00', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('15', null, '0', '0', '2016-07-20 15:44:55', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('16', null, '0', '0', '2016-07-20 15:49:58', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('17', null, '0', '0', '2016-07-20 15:51:43', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('18', 'ffgdfsdfdfdfd', '0', '0', '2016-07-20 16:02:30', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('19', 'fdgdgfffffffffffff', '0', '0', '2016-07-20 16:04:31', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('20', '123333333333333', '0', '0', '2016-07-20 16:05:01', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('21', 'dsfffffffffffffffffffffff', '0', '0', '2016-07-20 16:05:53', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('22', 'dfghjjjjjjjjjjjjjjjjj', '0', '0', '2016-07-20 16:10:07', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('23', '234569543asdfghjgfd', '0', '0', '2016-07-20 16:12:41', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('24', 'fdfdfdfd1111111111', '0', '0', '2016-07-20 16:21:03', '1', null, null, null, null, '0', '-1', '5', '0', '1', '0', '17', null, '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('25', null, '0', '0', '2016-07-20 16:25:10', '1', 'LwtQ_biNVsKdIVpwZNJnnT2OUU4dr3i-pJfxllovrhW_nLS3mUPzkoobHpUwVNiE', null, null, null, '0', '-1', '5', '0', '5', '0', '17', '255', '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mass_message` VALUES ('26', null, '0', '0', '2016-07-20 16:25:26', '1', 'j3RKhY0ZlutLwqK3pqD1zVSVI6uUez6hcgHpJJciuHBnQJUb1z23I6KnSlHW7Ihs', null, null, null, '0', '-1', '5', '0', '5', '0', '17', '255', '1', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `wcc_mass_message_citys`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mass_message_citys`;
CREATE TABLE `wcc_mass_message_citys` (
  `wcc_mass_message` bigint(20) NOT NULL,
  `citys` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_mass_message`,`citys`),
  KEY `FK_7rin9lxnpfyr6nvj1llo8lohe` (`citys`),
  KEY `FK_tqkjb5qrefg73mnjkfciw8a11` (`wcc_mass_message`),
  CONSTRAINT `wcc_mass_message_citys_ibfk_1` FOREIGN KEY (`citys`) REFERENCES `wcc_city` (`id`),
  CONSTRAINT `wcc_mass_message_citys_ibfk_2` FOREIGN KEY (`wcc_mass_message`) REFERENCES `wcc_mass_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mass_message_citys
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_mass_message_friends`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mass_message_friends`;
CREATE TABLE `wcc_mass_message_friends` (
  `mass_messages` bigint(20) NOT NULL,
  `friends` bigint(20) NOT NULL,
  PRIMARY KEY (`mass_messages`,`friends`),
  KEY `FK_v768o5nj0lwcrfx1gr2h7aen` (`friends`),
  KEY `FK_as4hsbpstb9xt50mhjk2lxuef` (`mass_messages`),
  CONSTRAINT `wcc_mass_message_friends_ibfk_1` FOREIGN KEY (`mass_messages`) REFERENCES `wcc_mass_message` (`id`),
  CONSTRAINT `wcc_mass_message_friends_ibfk_2` FOREIGN KEY (`friends`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mass_message_friends
-- ----------------------------
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '1');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '2');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '3');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '4');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '5');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '6');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '7');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '8');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '9');
INSERT INTO `wcc_mass_message_friends` VALUES ('1', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('2', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('3', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('4', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('5', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('9', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('10', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('11', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('12', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('19', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('20', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('21', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('22', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('23', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('24', '13');
INSERT INTO `wcc_mass_message_friends` VALUES ('26', '13');

-- ----------------------------
-- Table structure for `wcc_mass_message_groups`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mass_message_groups`;
CREATE TABLE `wcc_mass_message_groups` (
  `wcc_mass_message` bigint(20) NOT NULL,
  `groups` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_mass_message`,`groups`),
  KEY `FK_4l6ti6wt0a572tbcnm4x3es01` (`groups`),
  KEY `FK_ny1w0h39m8qbnp8djbds6hsnw` (`wcc_mass_message`),
  CONSTRAINT `wcc_mass_message_groups_ibfk_1` FOREIGN KEY (`groups`) REFERENCES `wcc_group` (`id`),
  CONSTRAINT `wcc_mass_message_groups_ibfk_2` FOREIGN KEY (`wcc_mass_message`) REFERENCES `wcc_mass_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mass_message_groups
-- ----------------------------
INSERT INTO `wcc_mass_message_groups` VALUES ('6', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('7', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('8', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('13', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('14', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('15', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('16', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('17', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('18', '5');
INSERT INTO `wcc_mass_message_groups` VALUES ('25', '5');

-- ----------------------------
-- Table structure for `wcc_mass_pic_text`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mass_pic_text`;
CREATE TABLE `wcc_mass_pic_text` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_to_fav_count` int(11) NOT NULL,
  `add_to_fav_user` int(11) NOT NULL,
  `int_page_read_count` int(11) NOT NULL,
  `int_page_read_user` int(11) NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `mass_id` bigint(20) DEFAULT NULL,
  `msgid` varchar(255) DEFAULT NULL,
  `ori_page_read_count` int(11) NOT NULL,
  `ori_page_read_user` int(11) NOT NULL,
  `ref_date` datetime DEFAULT NULL,
  `share_count` int(11) NOT NULL,
  `share_user` int(11) NOT NULL,
  `stat_date` datetime DEFAULT NULL,
  `target_user` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_k6pngnf7iatitc0rhl5p8fslx` (`platform_user`),
  CONSTRAINT `wcc_mass_pic_text_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mass_pic_text
-- ----------------------------
INSERT INTO `wcc_mass_pic_text` VALUES ('1', '0', '0', '24', '24', '0', null, '2650934054_1', '0', '0', '2016-07-19 00:00:00', '0', '0', null, '0', '寻找中奖的您？！', '3', '4');

-- ----------------------------
-- Table structure for `wcc_materials`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_materials`;
CREATE TABLE `wcc_materials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  `description` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `material_id` varchar(255) DEFAULT NULL,
  `material_type` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `remake_url` varchar(4000) DEFAULT NULL,
  `resource_url` varchar(4000) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  `thumbnail_url` varchar(4000) DEFAULT NULL,
  `title` varchar(400) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `url_boolean` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `wcc_platform_users` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  `code_status` tinyint(1) NOT NULL,
  `flag_office_source` varchar(100) DEFAULT NULL,
  `is_necessary_send` varchar(255) DEFAULT NULL,
  `source_url` varchar(4000) DEFAULT NULL,
  `wcc_material_attr` int(11) DEFAULT NULL,
  `office_material_id` varchar(255) DEFAULT NULL,
  `pub_operator` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c8mddavtc1uwrk7slrfat1wni` (`wcc_platform_users`) USING BTREE,
  KEY `FK_te9fq543orr54rhgvwxhk3mcv` (`pid`) USING BTREE,
  KEY `FK_dyxomjc81rc6v6c55u1he45gk` (`pub_operator`),
  CONSTRAINT `wcc_materials_ibfk_1` FOREIGN KEY (`pub_operator`) REFERENCES `pub_operator` (`id`),
  CONSTRAINT `wcc_materials_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `wcc_materials` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of wcc_materials
-- ----------------------------
INSERT INTO `wcc_materials` VALUES ('1', '4', '问题', '问题', '2015-05-18 11:41:48', null, null, null, null, '', null, '99', '/ump/attached/wx_image/4/20150518/20150518114142_676.jpg', '测试', 'susie', '4', null, '0', '2', '2', null, null, '2', '1', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('2', '4', null, null, '2015-05-18 17:12:00', null, 'K5NYs0-57KnBUzkruNmlNLZndWzoBy8FokccrIah0bJupcqwhgQHeGasG6-jLcnW', '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150518/20150518171200_578.jpg', 'Desert', null, '1', '2015-05-18 17:12:31', null, '1', '2', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('3', '4', null, null, '2015-05-27 18:48:23', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150527/20150527184823_267.jpg', '48e837eejw1era74jhxcsj20o20o2mzr', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('4', '4', null, null, '2015-05-27 18:48:30', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150527/20150527184830_413.jpg', '48e837eejw1erlbty2e8rj20k00k0q7c', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('8', '4', null, null, '2015-05-28 18:42:55', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150528/20150528184255_527.jpg', 'Penguins', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('16', '4', null, null, '2015-06-08 15:26:10', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150608/20150608152610_108.jpg', '48e837eejw1erlbty2e8rj20k00k0q7c', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('17', '4', null, null, '2015-06-08 15:26:16', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150608/20150608152615_968.jpg', '48e837eejw1era74jhxcsj20o20o2mzr', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('18', '4', null, null, '2015-06-08 15:26:20', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150608/20150608152620_215.jpg', '5881315fjw1es0s9qdwpxj20ku0fmjud', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('19', '4', null, null, '2015-06-08 15:26:26', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150608/20150608152625_797.jpg', 'Chrysanthemum', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('20', '4', null, null, '2015-06-08 15:26:31', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150608/20150608152631_552.jpg', 'Desert', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('21', '4', null, null, '2015-06-08 15:26:37', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/4/20150608/20150608152637_317.jpg', 'Jellyfish', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('23', '8', '', '欢迎进入业主通道', '2015-06-09 13:47:10', null, null, null, null, 'http://gzjh.9client.com/ump/pageProprietor/proprietor_web?gzjh=', null, '99', '/ump/attached/wx_image/8/20150615/20150615174236_343.jpg', '业主认证', '', '4', null, '0', '8', '7', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('24', '8', '', '', '2015-06-09 13:49:26', null, null, null, null, 'http://gzjh.9client.com/ump/pageFees/showLifeDetail?gzjh=', null, '99', '/ump/attached/wx_image/8/20150618/20150618142317_636.jpg', '物管费缴纳', '', '4', null, '0', '6', '7', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('30', '8', '', null, '2015-06-10 14:45:07', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208032432&idx=1&sn=66161fef4c18a30da2e56d7ddaf83880#rd', null, null, '/ump/attached/wx_image/8/20150617/20150617203528_245.jpg', '米兰世博中国馆主题卡，首发上市限量发行', '', '4', null, '1', '6', '7', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('35', '8', '', null, '2015-06-10 15:38:18', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208027623&idx=1&sn=acf9789757632bcb4689c8022f3128eb#rd', null, null, '/ump/attached/wx_image/8/20150618/20150618164716_296.jpg', '关于门禁系统升级改造的通知', '', '4', null, '0', '23', '7', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('44', '8', '', '', '2015-06-15 14:21:23', null, null, null, null, 'http://gzjh.9client.com/ump/pageFinancialProduct/showFinancialProduct?gzjh=', null, '99', '/ump/attached/wx_image/8/20150615/20150615142103_551.jpg', '理财预约', '', '4', null, '0', '5', '7', null, null, '11', '1', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('192', '8', '', null, '2015-06-19 00:37:24', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208027623&idx=2&sn=dce31b40d585aae39284be45402b3320#rd', null, null, '/ump/attached/wx_image/8/20150615/20150615155216_358.jpg', '关于2015年5月4日停电通知', '', '6', null, '1', '0', '7', '35', null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('193', '8', '', null, '2015-06-19 00:37:24', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208027623&idx=3&sn=307f4181330f4cf48494e81287856011#rd', null, null, '/ump/attached/wx_image/8/20150617/20150617172639_244.jpg', '关于小区进行消杀工作的通知', '', '6', null, '1', '0', '7', '35', null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('194', '8', '', null, '2015-06-19 00:37:24', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208027623&idx=4&sn=8f0f405a1f4044333c17bab059753916#rd', null, null, '/ump/attached/wx_image/8/20150617/20150617173435_274.jpg', '关于暴风雨天气做好安全的提示', '', '6', null, '0', '0', '7', '35', null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('197', '8', '', '', '2015-06-19 09:35:49', null, null, null, null, 'http://www.baidu.com', null, '99', '/ump/attached/wx_image/8/20150619/20150619093538_407.jpg', '222', '222', '4', null, '0', '0', '7', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('198', '8', '', '', '2015-06-19 09:35:49', null, null, null, null, 'http://www.baidu.com', null, '99', '/ump/attached/wx_image/8/20150619/20150619093538_407.jpg', '222', '222', '4', null, '0', '2', '3', null, null, '12', '1', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('199', '8', '时发斯蒂芬', '防守打法', '2015-06-29 17:00:51', null, null, null, null, 'http://gzjh.9client.com/ump/pageProprietor/proprietor_web?gzjh=', null, '99', '/ump/attached/wx_image/8/20150629/20150629170047_142.jpg', '业主认证', '发发大', '4', null, '0', '5', '3', null, null, '15', '1', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('205', '8', null, null, '2015-07-07 14:18:06', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20150707/20150707141806_783.jpg', '87a71606gw1ep7oisfn5ej20cm0d8tay', null, '1', null, null, '0', '7', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('206', '8', '', '发萨法撒发斯蒂芬', '2015-07-08 10:35:29', null, null, null, null, 'http://ur.qq.com/survey.html?type=survey&id=48256&hash=e5f6', null, '99', '/ump/attached/wx_image/8/20150708/20150708103527_348.jpg', 'EMP。。。', 'emp', '4', null, '0', '4', '3', null, null, '17', '1', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('209', '8', '', null, '2015-07-08 11:05:50', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208032432&idx=2&sn=fbc01f23a1457fcac52120c53ccd8d77#rd', null, null, '/ump/attached/wx_image/8/20150617/20150617203703_830.jpg', '2015年最红星期五超市活动', '', '6', null, '0', '0', '7', '30', null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('210', '8', '', null, '2015-07-08 11:05:50', null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA5Mjk4Mjk2MA==&mid=208032432&idx=3&sn=725d8ccaef570ac6b43f1e8518ea96d9#rd', null, null, '/ump/attached/wx_image/8/20150617/20150617203823_109.jpg', '交通银行“芯”优惠，屈臣氏满100立减38！', '', '6', null, '0', '0', '7', '30', null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('211', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('212', '8', '111111', '111111', '2016-03-12 15:23:19', null, null, null, null, 'http://1111111111111111', null, '99', '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111', '111', '4', null, '0', '0', '25', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('214', '8', '111111', '111111', '2016-03-12 15:25:47', null, null, null, null, 'http://1111111111111111', null, '99', '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111', '111', '4', null, '0', '0', '10', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('215', '8', '111111', '111111', '2016-03-12 15:25:48', null, null, null, null, 'http://1111111111111111', null, '99', '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111', '111', '4', null, '0', '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('216', null, 'cehsizhengwen11', 'ceshizhaiyao11', null, null, null, null, null, 'http://www.baidu.com11', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'ceshi11', 'cehsizuozhe11', null, null, '0', '0', '27', null, null, null, '0', null, null, 'http://www.sohu.com11', null, null, null);
INSERT INTO `wcc_materials` VALUES ('217', null, 'test', 'ssssssssssssssssss', null, null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA3NjQ1MTgzMg==&mid=200729420&idx=1&sn=22b4c5ffd723843260d8ca63b8eb37f9#rd', null, null, '/ump/attached/wx_image/8/20160314/20160314135509_381.jpg', '测试素材', 'test', null, null, '1', '0', '27', null, null, null, '0', null, null, 'http://mp.weixin.qq.com/s?__biz=MzA3NjQ1MTgzMg==&mid=200729420&idx=1&sn=22b4c5ffd723843260d8ca63b8eb37f9#rd', null, null, null);
INSERT INTO `wcc_materials` VALUES ('218', '8', '群发测试。。。。<br />', '群发测试。。。。', '2016-03-14 14:53:44', null, null, null, null, 'http://www.baidu.com', null, null, '/ump/attached/wx_image/8/20160314/20160314145254_338.jpg', '群发测试', '', '4', null, '0', '0', '27', null, null, null, '0', null, null, '', null, null, null);
INSERT INTO `wcc_materials` VALUES ('219', '8', '444444444444', '33333333333333', '2016-03-14 20:54:27', null, null, null, null, 'http://222222222222', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111111111', '2222222222', '4', null, '1', '0', '25', null, null, null, '0', null, null, 'http://3333333333333333', '2', null, null);
INSERT INTO `wcc_materials` VALUES ('220', '8', '444444444444', '33333333333333', '2016-03-14 20:54:27', null, null, null, null, 'http://222222222222', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111111111', '2222222222', '4', null, '1', '0', '10', null, null, null, '0', null, null, 'http://3333333333333333', '2', null, null);
INSERT INTO `wcc_materials` VALUES ('221', '8', '444444444444', '33333333333333', '2016-03-14 20:54:28', null, null, null, null, 'http://222222222222', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111111111', '2222222222', '4', null, '1', '0', '7', null, null, null, '0', null, null, 'http://3333333333333333', '2', null, null);
INSERT INTO `wcc_materials` VALUES ('222', '8', '444444444444', '33333333333333', '2016-03-14 20:54:28', null, null, null, null, 'http://222222222222', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111111111', '2222222222', '4', null, '1', '1', '3', null, null, null, '0', null, null, 'http://3333333333333333', '1', null, null);
INSERT INTO `wcc_materials` VALUES ('223', '8', 'ceshia2222222', 'cehsia222222', '2016-03-14 20:58:01', null, null, null, null, 'http://4tttttttttt222222222222', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'ceshia222', 'ceshia2222', '4', null, '1', '1', '27', null, null, null, '0', null, null, 'http://6666666666666yyyyyyyyy222222222222', '0', null, null);
INSERT INTO `wcc_materials` VALUES ('224', null, 'cehsizhengwen更改', 'ceshizhaiyao更改', null, null, null, null, null, 'http://www.baidu.com22222', null, null, '/ump/attached/wx_image/8/20160311/20160311170222_334.jpg', 'ceshi更改', 'cehsizuozhe更改', null, null, '1', '0', '27', null, null, null, '0', null, null, 'http://www.sohu.com33333', null, null, null);
INSERT INTO `wcc_materials` VALUES ('225', null, '更改test', '更改ssssssssssssssssss', null, null, null, null, null, 'http://mp.weixin.qq.com/s?__biz=MzA3NjQ1MTgzMg==&mid=200729420&idx=1&sn=22b4c5ffd723843260d8ca63b8eb37f9#rd', null, null, '/ump/attached/wx_image/8/20160314/20160314135509_381.jpg', 'genggai测试素材', '更改test', null, null, '1', '0', '27', null, null, null, '0', null, null, 'http://mp.weixin.qq.com/s?__biz=MzA3NjQ1MTgzMg==&mid=200729420&idx=1&sn=22b4c5ffd723843260d8ca63b8eb37f9#rd', null, null, null);
INSERT INTO `wcc_materials` VALUES ('226', null, '1111111111111111cehsizhengwen', '11111111111ceshizhaiyao', null, null, null, null, null, 'http://www.baidu.com1111', null, null, '/ump/attached/wx_image/8/20160311/20160311170222_334.jpg', '11111111111111ceshi', '11111111111111cehsizuozhe', null, null, '0', '0', '27', null, null, null, '0', '1457918068959', null, 'http://www.sohu.com11111', null, null, null);
INSERT INTO `wcc_materials` VALUES ('227', null, '111111111111cehsizhengwen', '111111111111ceshizhaiyao', null, null, null, null, null, 'http://1111111111http://www.baidu.com', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '11111111111ceshi', '111111111111cehsizuozhe', null, null, '0', '0', '27', null, null, null, '0', '1457918068959', null, 'http://111111111111http://www.sohu.com', null, null, null);
INSERT INTO `wcc_materials` VALUES ('228', null, 'zhengwenkerry002', 'kerry002', null, null, null, null, null, 'http://kerrycom002', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'kerry002', 'kerry002', null, null, '1', '0', '7', null, null, null, '0', '1457963805845', null, 'http://comkerry002', null, null, null);
INSERT INTO `wcc_materials` VALUES ('229', null, 'zhengwenkerry002', 'kerry002', null, null, null, null, null, 'http://kerrycom002', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'kerry002', 'kerry002', null, null, '1', '0', '7', null, null, null, '0', '1457963805845', null, 'http://comkerry002', null, null, null);
INSERT INTO `wcc_materials` VALUES ('230', null, 'zhengwenkerry003', 'kerry003', null, null, null, null, null, 'http://kerrycom003', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'kerry003', 'kerry003', null, null, '1', '0', '27', null, null, null, '0', '1457963805845', null, 'http://comkerry003', null, null, null);
INSERT INTO `wcc_materials` VALUES ('231', null, '111111111cehsizhengwen', '11111ceshizhaiyao', null, null, null, null, null, 'http://www.baidu.com1111111', null, null, '/ump/attached/wx_image/8/20160311/20160311170222_334.jpg', '111111ceshi', '111111cehsizuozhe', '4', null, '0', '0', '27', null, null, null, '0', '1457918068959', null, 'http://www.sohu.com111111111', null, null, null);
INSERT INTO `wcc_materials` VALUES ('232', null, '111cehsizhengwen', '111ceshizhaiyao', null, null, null, null, null, 'http://www.baidu.com111', null, null, '/ump/attached/wx_image/8/20160311/20160311170222_334.jpg', '111ceshi', '111cehsizuozhe', '4', null, '0', '0', '27', null, null, null, '0', '1457918068959', null, 'http://www.sohu.com111', null, null, null);
INSERT INTO `wcc_materials` VALUES ('233', null, '11zhengwenkerry', '11kerry001', null, null, null, null, null, 'http://kerrycom111', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', '111kerry001', '111kerry001', '4', null, '1', '0', '27', null, null, null, '0', '1457963805845', null, 'http://comkerry1111', null, null, null);
INSERT INTO `wcc_materials` VALUES ('234', '8', 'aa', null, '2016-03-15 19:06:39', null, null, null, null, '', null, null, '/ump/attached/wx_image/8/20160311/20160311170222_334.jpg', 'aa', 'aa', '4', null, '1', '0', null, null, null, null, '0', '1458039999820', null, '', '1', null, null);
INSERT INTO `wcc_materials` VALUES ('235', '8', 'bb', null, '2016-03-15 19:06:39', null, null, null, null, '', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'bb', 'bb', '6', null, '1', '0', null, '234', null, null, '0', '1458039999923', '2', null, '1', null, null);
INSERT INTO `wcc_materials` VALUES ('236', '8', 'aa', null, '2016-03-15 19:08:11', null, null, null, null, '', null, null, '/ump/attached/wx_image/8/20160311/20160311170222_334.jpg', 'aa', 'aa', '4', null, '1', '0', null, null, null, null, '0', '1458040091353', null, '', '1', null, null);
INSERT INTO `wcc_materials` VALUES ('237', '8', 'bb', null, '2016-03-15 19:08:11', null, null, null, null, '', null, null, '/ump/attached/wx_image/8/20160311/20160311184043_434.jpg', 'bb', 'bb', '6', null, '1', '0', null, '236', null, null, '0', '1458040091462', '2', null, '1', null, null);
INSERT INTO `wcc_materials` VALUES ('238', '8', null, null, '2016-03-16 11:49:43', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316114942_249.jpg', 'Lighthouse', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('239', '8', null, null, '2016-03-16 11:49:43', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316114942_249.jpg', 'Lighthouse', null, '1', null, null, '0', '26', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('240', '8', null, null, '2016-03-16 11:49:43', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316114942_249.jpg', 'Lighthouse', null, '1', null, null, '0', '25', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('241', '8', null, null, '2016-03-16 11:49:43', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316114942_249.jpg', 'Lighthouse', null, '1', null, null, '0', '24', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('242', '8', null, null, '2016-03-16 11:49:43', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316114942_249.jpg', 'Lighthouse', null, '1', null, null, '0', '23', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('243', '8', null, null, '2016-03-16 11:49:43', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316114942_249.jpg', 'Lighthouse', null, '1', null, null, '0', '22', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('244', '8', null, null, '2016-03-16 13:42:48', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316134248_480.jpg', 'Hydrangeas', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('245', '8', null, null, '2016-03-16 13:45:03', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316134502_927.jpg', 'Jellyfish', null, '1', null, null, '0', '25', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('246', '8', null, null, '2016-03-16 14:07:26', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316140725_796.jpg', 'Koala', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('247', '8', null, null, '2016-03-16 14:25:01', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316142454_974.jpg', 'Hydrangeas', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('248', '8', null, null, '2016-03-16 14:25:40', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316142538_607.jpg', 'Jellyfish', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('249', '8', null, null, '2016-03-16 14:26:03', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316142601_291.jpg', 'Koala', null, '1', null, null, '0', '26', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('250', '8', null, null, '2016-03-16 15:32:37', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316153234_395.jpg', 'Tulips', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('251', '8', null, null, '2016-03-16 15:47:11', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316154709_953.jpg', 'Jellyfish', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('252', '8', null, null, '2016-03-16 15:55:11', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316155509_648.jpg', 'Jellyfish', null, '1', null, null, '0', '27', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('253', '8', null, null, '2016-03-16 15:57:17', null, null, '.jpg', null, null, null, null, '/ump/attached/wx_image/8/20160316/20160316155714_925.jpg', 'Koala', null, '1', null, null, '0', '3', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('254', '17', '对方说的发第三方地方', '对方说的发第三方地方', '2016-05-05 15:56:21', null, null, null, null, '', null, '99', '/ump/attached/wx_image/17/20160505/20160505155616_365.jpg', 'some files', 'no person', '4', null, '0', '0', '1', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `wcc_materials` VALUES ('255', '17', '第三方对方对方答复', '第三方对方对方答复', '2016-05-05 15:58:14', null, null, null, null, '', null, '99', '/ump/attached/wx_image/17/20160720/20160720162432_764.jpg', '医疗质量报告', 'no person11', '4', null, '0', '1', '1', null, null, '2', '1', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `wcc_member`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_member`;
CREATE TABLE `wcc_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_binding` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_member_code` int(11) DEFAULT NULL,
  `is_send_mail` int(11) DEFAULT NULL,
  `is_visiable` tinyint(1) DEFAULT NULL,
  `member_email` varchar(255) NOT NULL,
  `member_id` varchar(128) DEFAULT NULL,
  `member_name` varchar(128) DEFAULT NULL,
  `member_phone` varchar(13) NOT NULL,
  `nick_name` varchar(128) DEFAULT NULL,
  `remark` varchar(3000) DEFAULT NULL,
  `scene_id` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of wcc_member
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_membership_level`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_membership_level`;
CREATE TABLE `wcc_membership_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_membership_level
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_member_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_member_platform_users`;
CREATE TABLE `wcc_member_platform_users` (
  `wcc_member` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_member`,`platform_users`),
  KEY `FK_i7ynuc7odf3i7pesg40bk26ya` (`platform_users`) USING BTREE,
  KEY `FK_trxgg9gvyi81gufk6l7ftesuj` (`wcc_member`) USING BTREE,
  CONSTRAINT `wcc_member_platform_users_ibfk_1` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_member_platform_users_ibfk_2` FOREIGN KEY (`wcc_member`) REFERENCES `wcc_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of wcc_member_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_member_wcc_friends`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_member_wcc_friends`;
CREATE TABLE `wcc_member_wcc_friends` (
  `wcc_member` bigint(20) NOT NULL,
  `wcc_friends` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_member`,`wcc_friends`),
  KEY `FK_rc45b4bluva6cqokficp1dk8r` (`wcc_friends`),
  KEY `FK_matv4l66rfgakbo7etfsw99ep` (`wcc_member`),
  CONSTRAINT `wcc_member_wcc_friends_ibfk_1` FOREIGN KEY (`wcc_member`) REFERENCES `wcc_member` (`id`),
  CONSTRAINT `wcc_member_wcc_friends_ibfk_2` FOREIGN KEY (`wcc_friends`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_member_wcc_friends
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_menu`;
CREATE TABLE `wcc_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `content_select` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `flag` bigint(20) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `menu_name` varchar(100) NOT NULL,
  `mkey` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `type` bigint(20) NOT NULL,
  `url` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `materials` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5o25sw1gda86kvvg4yf6caonk` (`materials`),
  KEY `FK_2u9sxel5py0ijjjv072jfrmkf` (`platform_user`),
  CONSTRAINT `wcc_menu_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_menu_ibfk_2` FOREIGN KEY (`materials`) REFERENCES `wcc_materials` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_menu
-- ----------------------------
INSERT INTO `wcc_menu` VALUES ('2', '17', null, '0', '2016-05-10 19:08:38', '0', '1', '融资缺钱', null, '0', null, '1', '3', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1', '2', null, '2');
INSERT INTO `wcc_menu` VALUES ('3', '17', null, '0', '2016-05-13 10:06:42', '0', '1', '钱生钱', null, '0', null, '1', '3', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=2', '1', null, '2');
INSERT INTO `wcc_menu` VALUES ('4', '17', null, '0', '2016-05-13 10:08:54', '0', '1', '网点信息', null, '0', null, '1', '3', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showProductStroe', '3', null, '2');
INSERT INTO `wcc_menu` VALUES ('7', '17', null, '0', '2016-06-27 15:59:20', '0', '1', '信息绑定', null, '0', null, '1', '3', 'http://cheally.tunnel.qydev.com/ump/pageBding/showBding', '3', null, '1');
INSERT INTO `wcc_menu` VALUES ('8', '17', null, '0', '2016-06-27 16:35:42', '0', '1', '预报名表', null, '0', null, '1', '3', 'http://cheally.tunnel.qydev.com/ump/pageBding/showEnlist', '4', null, '1');
INSERT INTO `wcc_menu` VALUES ('9', '17', null, '0', '2016-09-03 15:27:49', '0', '1', '维修登记', null, '0', null, '1', '3', 'http://cheally.tunnel.qydev.com/ump/pageBding/showRepairPage', '0', null, '1');
INSERT INTO `wcc_menu` VALUES ('10', '17', null, '0', '2016-10-01 10:32:44', '0', '1', '信息绑定', null, '0', null, '1', '3', 'http://cheally.tunnel.qydev.com/ump/pageBding/showBding', '0', null, '5');
INSERT INTO `wcc_menu` VALUES ('11', '17', null, '0', '2016-10-01 10:33:04', '0', '1', '预报名表', null, '0', null, '1', '3', 'http://cheally.tunnel.qydev.com/ump/pageBding/showEnlist', '0', null, '5');
INSERT INTO `wcc_menu` VALUES ('12', '17', null, '0', '2016-10-01 10:33:26', '0', '1', '维修登记', null, '0', null, '1', '3', 'http://cheally.tunnel.qydev.com/ump/pageBding/showRepairPage', '0', null, '5');

-- ----------------------------
-- Table structure for `wcc_message`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_message`;
CREATE TABLE `wcc_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msg` varchar(4000) DEFAULT NULL,
  `msg_type` int(11) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `date_time` int(11) NOT NULL,
  `file_size` varchar(100) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `resource_url` varchar(4000) DEFAULT NULL,
  `service_platform` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `thumbnail_url` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_message
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_mycard`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mycard`;
CREATE TABLE `wcc_mycard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `card_intro` varchar(500) DEFAULT NULL,
  `card_name` varchar(500) DEFAULT NULL,
  `card_pic` varchar(500) DEFAULT NULL,
  `card_time` datetime DEFAULT NULL,
  `card_title` varchar(4000) DEFAULT NULL,
  `card_url` varchar(500) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `content_title` varchar(8000) DEFAULT NULL,
  `delete_ip` varchar(255) DEFAULT NULL,
  `delete_pk` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `insert_ip` varchar(255) DEFAULT NULL,
  `insert_pk` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `organization_pk` varchar(255) DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `update_pk` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `jr_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mycard
-- ----------------------------
INSERT INTO `wcc_mycard` VALUES ('12', null, null, '/ump/attached/wx_image/8/20150618/20150618143912_11.jpg', '2015-06-10 13:32:34', '我的一卡通', 'http://www.bankcomm.com/BankCommSite/uploadFiles/zhcx.1382096367256/index.html?infoId1376533756100', null, '<p>\r\n	<img src=\"/ump/attached/wx_image/8/20150615/20150615163310_647.jpg\" alt=\"\" title=\"\" align=\"\" width=\"320\" height=\"164\" /> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:宋体;\"><img src=\"/ump/attached/wx_image/8/20150615/20150615163358_518.jpg\" alt=\"\" title=\"\" align=\"\" width=\"320\" height=\"166\" /></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;\"><strong>——————————————————</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>功能</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>1</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>：</strong></span><span style=\"font-family:Arial;font-size:18px;line-height:1.5;color:#FFFFFF;background-color:#000000;\"><strong>身份识别应用</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">在“芯”片中加载您的社区身份识别功能，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">可出入社区门禁等公共区域，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">实现实名制管理。</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\"><br />\r\n</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>功能</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>2</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>：</strong></span><span style=\"font-family:Arial;font-size:18px;line-height:1.5;color:#FFFFFF;background-color:#000000;\"><strong>金融账户</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">标准</span><span style=\"font-family:Arial;font-size:16px;\">IC</span><span style=\"font-family:Arial;font-size:16px;\">卡具有存取款、消费、结算、理财等，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">所有银行金融</span><span style=\"font-family:Arial;font-size:16px;\">IC</span><span style=\"font-family:Arial;font-size:16px;\">借记卡功能；</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">挂</span><span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">件卡具有备用金、电子现金帐户。</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<br />\r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>便捷</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>1</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>：</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>电子现金账户</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">具有“</span><span style=\"font-family:Arial;font-size:16px;\">Quick</span><span style=\"font-family:Arial;font-size:16px;\">闪付</span><span style=\"font-family:Arial;font-size:16px;\">Pass</span><span style=\"font-family:Arial;font-size:16px;\">”标识的金邻卡，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">在银联闪付标识的</span><span style=\"font-family:Arial;font-size:16px;\">POS</span><span style=\"font-family:Arial;font-size:16px;\">机可“嘀”卡</span><span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">消费，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">免除您携带现金、找零、伪钞等风险。</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><br />\r\n</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>便捷</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>2</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>：</strong></span><span style=\"font-family:Arial;font-size:18px;line-height:1.5;color:#FFFFFF;background-color:#000000;\"><strong>日常生活费用代缴</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">签约交行“缴费通”业务，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">轻松办理物业费、水费、电费、煤气费、</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">有线电视费等各类日常生活费用签约代扣。</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">只需一次签约，让您省时又轻松！</span><span></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<br />\r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>安全：</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>IC</strong></span><span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>“芯”技术</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">采用</span><span style=\"font-family:Arial;font-size:16px;\">PBOC2.0</span><span style=\"font-family:Arial;font-size:16px;\">芯片技术，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">进行数据存储加密解密，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\">有</span><span style=\"font-family:Arial;font-size:16px;\">效防范信息被复制与</span><span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">篡改，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">杜绝“克隆卡”。</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;\"><br />\r\n</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:18px;color:#FFFFFF;background-color:#000000;\"><strong>放心：</strong></span><span style=\"font-family:Arial;font-size:18px;line-height:1.5;color:#FFFFFF;background-color:#000000;\"><strong>实名制管理</strong></span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">实现人员出入社区实名制登记，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">大幅提升社区安全管理等级，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">就像您的社</span><span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">区小管家，</span> \r\n</p>\r\n<p class=\"MsoNormal\" style=\"text-indent:5.25pt;\">\r\n	<span style=\"font-family:Arial;font-size:16px;line-height:1.5;\">安全又放心！</span> \r\n</p>\r\n<p>\r\n	<strong>——————————————————</strong> \r\n</p>', null, null, null, null, null, null, null, null, null, null, null, 'http://www.95559.com.cn/BankCommSite/default.shtml');
INSERT INTO `wcc_mycard` VALUES ('13', null, null, null, null, '我的', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `wcc_mycard` VALUES ('14', null, null, null, null, '一卡通', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `wcc_mycard_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_mycard_platform_users`;
CREATE TABLE `wcc_mycard_platform_users` (
  `wcc_mycard` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_mycard`,`platform_users`),
  KEY `FK_s1ydnh6l1agh62fd26nwepnql` (`platform_users`),
  KEY `FK_a1khkpqtqtjh7eyeka22wm8jd` (`wcc_mycard`),
  CONSTRAINT `wcc_mycard_platform_users_ibfk_1` FOREIGN KEY (`wcc_mycard`) REFERENCES `wcc_mycard` (`id`),
  CONSTRAINT `wcc_mycard_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_mycard_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_offc_ativity`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_offc_ativity`;
CREATE TABLE `wcc_offc_ativity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(400) NOT NULL,
  `start_time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_offc_ativity
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_office_materials`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_office_materials`;
CREATE TABLE `wcc_office_materials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_id` int(11) DEFAULT NULL,
  `code_status` tinyint(1) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  `description` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_necessary_send` varchar(255) DEFAULT NULL,
  `material_id` varchar(255) DEFAULT NULL,
  `material_type` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `remake_url` varchar(4000) DEFAULT NULL,
  `resource_url` varchar(4000) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  `thumbnail_url` varchar(4000) DEFAULT NULL,
  `title` varchar(400) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `url_boolean` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `wcc_material_attr` int(11) DEFAULT NULL,
  `wcc_platform_users` bigint(20) DEFAULT NULL,
  `source_url` varchar(4000) DEFAULT NULL,
  `flag_office_source` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5eat40mqte3ek3ut97woxndb4` (`wcc_platform_users`),
  CONSTRAINT `wcc_office_materials_ibfk_1` FOREIGN KEY (`wcc_platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_office_materials
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_option`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_option`;
CREATE TABLE `wcc_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `question_no` int(11) NOT NULL,
  `sort` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_option
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_plate`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_plate`;
CREATE TABLE `wcc_plate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `plate_name` varchar(200) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dhfpi2h3k9gcptu0ydo5tysni` (`plate_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_plate
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_platform_user`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_platform_user`;
CREATE TABLE `wcc_platform_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(200) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `app_secret` varchar(255) DEFAULT NULL,
  `head_image` varchar(500) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_validation` tinyint(1) DEFAULT NULL,
  `login_info` varchar(255) DEFAULT NULL,
  `nickname` varchar(400) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `platform_id` varchar(32) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `author_state` int(11) DEFAULT '0',
  `authorizer_acces_token` varchar(255) DEFAULT NULL,
  `authorizer_refresh_token` varchar(255) DEFAULT NULL,
  `creat_time` datetime DEFAULT NULL,
  `expires_in` bigint(20) DEFAULT NULL,
  `func_info` varchar(255) DEFAULT NULL,
  `qrcode_url` varchar(255) DEFAULT NULL,
  `verify_type_info` int(11) NOT NULL DEFAULT '0',
  `wccplatform_id` bigint(20) NOT NULL DEFAULT '0',
  `authority` varchar(255) DEFAULT NULL,
  `platform_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1g955iqh3jmgdfbq3dhri53h2` (`company`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of wcc_platform_user
-- ----------------------------
INSERT INTO `wcc_platform_user` VALUES ('1', 'cheally', 'wx2c94749984dbf67d', 'f53c0f378c473d3b103d0f62dfa86c1e', null, '2016-04-28 17:51:38', '1', '1', null, null, null, 'gh_627c82f47019', null, '0', '17', null, null, null, null, null, null, null, null, '0', '0', null, '1');
INSERT INTO `wcc_platform_user` VALUES ('2', 'yun', 'wx0ae48f6f1ded7e64', '5873e22797d53e1b92cfd0f2a7bdd1a2', null, '2016-05-10 18:47:03', '0', '1', null, null, null, 'gh_f10533b03b64', null, '1', '17', null, null, null, null, null, null, null, null, '0', '0', null, '1');
INSERT INTO `wcc_platform_user` VALUES ('3', '全渠道服务营销平台', 'wxe335b5051b21639c', 'ddf9a89dc73d05b40fb8aaa1e5a85f6c', null, '2016-05-14 19:14:03', '0', '1', null, null, null, 'gh_ba4288fd8e75', null, '1', '17', null, null, null, null, null, null, null, null, '0', '0', null, '1');
INSERT INTO `wcc_platform_user` VALUES ('4', '泰隆银行', 'wxfc4861803aca8fd8', 'eac07f371fc63d7bdb1fb120356a15d7', null, '2016-05-14 19:17:04', '0', '1', null, null, null, 'gh_ebf628b06239', null, '1', '17', null, null, null, null, null, null, null, null, '0', '0', null, '1');
INSERT INTO `wcc_platform_user` VALUES ('5', '警务化管理', 'wx63577ac1752e1793', '568def59e62e5d1d08a03fa511505ddb', null, '2016-10-01 10:32:15', '0', '1', null, null, null, 'gh_5d9f2819ca16', null, '1', '17', null, null, null, null, null, null, null, null, '0', '0', null, '1');

-- ----------------------------
-- Table structure for `wcc_plat_form`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_plat_form`;
CREATE TABLE `wcc_plat_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(4000) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `plate_type` varchar(200) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b91jwmxi37af776dfnyrvb75u` (`plate_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_plat_form
-- ----------------------------
INSERT INTO `wcc_plat_form` VALUES ('1', '拥有消息与自定义菜单权限', '0', '订阅号', null);
INSERT INTO `wcc_plat_form` VALUES ('2', '服务号所拥有的权限', '0', '服务号', null);

-- ----------------------------
-- Table structure for `wcc_praise`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_praise`;
CREATE TABLE `wcc_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `creat_time` datetime DEFAULT NULL,
  `friend_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `plat_form_id` bigint(20) DEFAULT NULL,
  `praise_status` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_praise
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_province`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_province`;
CREATE TABLE `wcc_province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_province
-- ----------------------------
INSERT INTO `wcc_province` VALUES ('10', '北京');
INSERT INTO `wcc_province` VALUES ('11', '上海');
INSERT INTO `wcc_province` VALUES ('12', '天津');
INSERT INTO `wcc_province` VALUES ('13', '重庆');
INSERT INTO `wcc_province` VALUES ('14', '河北');
INSERT INTO `wcc_province` VALUES ('15', '山西');
INSERT INTO `wcc_province` VALUES ('16', '内蒙古 ');
INSERT INTO `wcc_province` VALUES ('17', '辽宁');
INSERT INTO `wcc_province` VALUES ('18', '吉林');
INSERT INTO `wcc_province` VALUES ('19', '黑龙江');
INSERT INTO `wcc_province` VALUES ('20', '江苏');
INSERT INTO `wcc_province` VALUES ('21', '浙江');
INSERT INTO `wcc_province` VALUES ('22', '安徽');
INSERT INTO `wcc_province` VALUES ('23', '福建');
INSERT INTO `wcc_province` VALUES ('24', '江西');
INSERT INTO `wcc_province` VALUES ('25', '山东');
INSERT INTO `wcc_province` VALUES ('26', '河南');
INSERT INTO `wcc_province` VALUES ('27', '湖北');
INSERT INTO `wcc_province` VALUES ('28', '湖南');
INSERT INTO `wcc_province` VALUES ('29', '广东');
INSERT INTO `wcc_province` VALUES ('30', '广西');
INSERT INTO `wcc_province` VALUES ('31', '海南');
INSERT INTO `wcc_province` VALUES ('32', '四川');
INSERT INTO `wcc_province` VALUES ('33', '贵州');
INSERT INTO `wcc_province` VALUES ('34', '云南');
INSERT INTO `wcc_province` VALUES ('35', '西藏');
INSERT INTO `wcc_province` VALUES ('36', '陕西');
INSERT INTO `wcc_province` VALUES ('37', '甘肃');
INSERT INTO `wcc_province` VALUES ('38', '青海');
INSERT INTO `wcc_province` VALUES ('39', '宁夏');
INSERT INTO `wcc_province` VALUES ('40', '新疆');
INSERT INTO `wcc_province` VALUES ('41', '香港');
INSERT INTO `wcc_province` VALUES ('42', '澳门');
INSERT INTO `wcc_province` VALUES ('43', '台湾');

-- ----------------------------
-- Table structure for `wcc_purchase_info`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_purchase_info`;
CREATE TABLE `wcc_purchase_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cell_phone` varchar(255) DEFAULT NULL,
  `friend_id` bigint(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_purchase_info
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_qrcode`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_qrcode`;
CREATE TABLE `wcc_qrcode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` int(11) NOT NULL,
  `code_id` varchar(4000) DEFAULT NULL,
  `code_url` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `expire_seconds` float NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_use` tinyint(1) DEFAULT NULL,
  `scene_id` int(11) NOT NULL,
  `use_type` varchar(30) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `wcc_stroe_id` bigint(20) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mj8n40rgbt4kgm2k88kjnm2pj` (`company`),
  KEY `FK_m1l9iupvn4umft0ge1y8hyj6r` (`platform_user`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_qrcode
-- ----------------------------
INSERT INTO `wcc_qrcode` VALUES ('1', '2', 'http://weixin.qq.com/q/-Ek8STblj1X2lbq7amVn', '1462435094718.jpg', '2016-05-05 15:58:14', '-1', '1', '1', '2', '6', '0', null, '17', '1');
INSERT INTO `wcc_qrcode` VALUES ('2', '2', 'http://weixin.qq.com/q/kDp1Qg3lRyo_T6G70hZv', '1462936331819.jpg', '2016-05-11 11:12:11', '-1', '1', '1', '3', '2', '5', '3', '17', '2');
INSERT INTO `wcc_qrcode` VALUES ('3', '2', 'http://weixin.qq.com/q/2Toxm1jlcSoIiugQlhZv', '1463285572080.jpg', '2016-05-15 12:12:52', '-1', '1', '1', '4', '2', '1', '4', '17', '2');

-- ----------------------------
-- Table structure for `wcc_qr_code`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_qr_code`;
CREATE TABLE `wcc_qr_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actity_img` varchar(255) DEFAULT NULL,
  `activity_name` varchar(255) DEFAULT NULL,
  `area_info` varchar(255) DEFAULT NULL,
  `area_or_acticity_des` varchar(10000) DEFAULT NULL,
  `code_attr` varchar(255) DEFAULT NULL,
  `code_local_path` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `code_type` varchar(255) DEFAULT NULL,
  `code_url` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `expire_time` int(11) DEFAULT NULL,
  `is_create_code` varchar(255) DEFAULT NULL,
  `scene_id` int(11) NOT NULL,
  `scene_str` varchar(255) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7cf7fu1txkgk60tmh10yqe3em` (`organization`),
  CONSTRAINT `wcc_qr_code_ibfk_1` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_qr_code
-- ----------------------------
INSERT INTO `wcc_qr_code` VALUES ('5', '/ump/attached/wx_image/8/20160302/20160302171041_84.jpg', 'test2', '上海-黄浦', 'test2', '临时', null, null, '活动', null, '2016-03-02 17:11:10', 'mila.wang@163.com', '3', 'true', '0', null, null);
INSERT INTO `wcc_qr_code` VALUES ('10', '/ump/attached/wx_image/8/20160313/20160313143840_612.jpg', '32532523', '山东-聊城 ', 'xcvxvbzxvzxvx<br />', '永久', 'D:\\cherry_ump\\ump\\target\\m2e-wtp\\web-resources\\attached\\wxQrcode', null, '活动', '/ump/attached/wxQrcode/null', '2016-03-13 14:39:48', 'mila.wang@9client.com', null, 'true', '2', 'eGaZBkWKGgrHFNA6Yw9S1reSslexRV97O1CEnlaUPCXKA4IhwwFKIzIYtNsQNP5k', null);
INSERT INTO `wcc_qr_code` VALUES ('11', '/ump/attached/wx_image/8/20160313/20160313150423_280.jpg', 'gdgvdd', '北京-西城', 'dgdds', '永久', 'D:\\cherry_ump\\ump\\target\\m2e-wtp\\web-resources\\attached\\wxQrcode', null, '活动', '/ump/attached/wxQrcode/1457853075495.jpg', '2016-03-13 15:11:34', 'mila.wang@9client.com', null, 'true', '3', 'DeWvstoQ8AlWaSP24Vs2E2usyvZfSNe6GUMHLz8qRuiPtXcZHojyORBf0honzF9x', null);

-- ----------------------------
-- Table structure for `wcc_qr_code_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_qr_code_platform_users`;
CREATE TABLE `wcc_qr_code_platform_users` (
  `wcc_qr_code` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_qr_code`,`platform_users`),
  KEY `FK_pbusko6im845n3pka49paxhxl` (`platform_users`),
  KEY `FK_103q3rcq5unjhmxriumsmulsl` (`wcc_qr_code`),
  CONSTRAINT `wcc_qr_code_platform_users_ibfk_1` FOREIGN KEY (`wcc_qr_code`) REFERENCES `wcc_qr_code` (`id`),
  CONSTRAINT `wcc_qr_code_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_qr_code_platform_users
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_question`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_question`;
CREATE TABLE `wcc_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(400) DEFAULT NULL,
  `question_no` varchar(50) DEFAULT NULL,
  `question_type` int(11) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_question
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_questionnaire`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_questionnaire`;
CREATE TABLE `wcc_questionnaire` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `questionnaire_code` varchar(200) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(400) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `visable_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_questionnaire
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_recommend_info`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_recommend_info`;
CREATE TABLE `wcc_recommend_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area` varchar(255) DEFAULT NULL,
  `car_type` varchar(255) DEFAULT NULL,
  `cellphone` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `rec_car_code` varchar(255) DEFAULT NULL,
  `rec_person` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `wcc_friend` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jrbv3jd6ko62a8jgr0pa55c26` (`wcc_friend`),
  CONSTRAINT `wcc_recommend_info_ibfk_1` FOREIGN KEY (`wcc_friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_recommend_info
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_record_msg`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_record_msg`;
CREATE TABLE `wcc_record_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `friend_group` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_validated` tinyint(1) DEFAULT NULL,
  `msg_from` int(11) NOT NULL,
  `nick_name` varchar(500) DEFAULT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `plat_form_account` varchar(255) DEFAULT NULL,
  `plat_form_id` varchar(255) DEFAULT NULL,
  `province` varchar(200) DEFAULT NULL,
  `record_content` varchar(4000) DEFAULT NULL,
  `record_friend_id` bigint(20) DEFAULT NULL,
  `remark_name` varchar(4000) DEFAULT NULL,
  `to_user_insert_time` datetime DEFAULT NULL,
  `to_user_record` varchar(4000) DEFAULT NULL,
  `user_info` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_record_msg
-- ----------------------------
INSERT INTO `wcc_record_msg` VALUES ('1', '4', '4', '2015-05-18 17:11:38', null, null, '0', '%E8%BF%99%E6%98%AF%E4%B8%AA%E9%94%A4%E5%AD%90', 'o-MKajmTFGDPK0CsVLxkrfKUmNcg', 'kelly测试号', '2', '', '/::|', '20', null, '2015-05-18 17:11:39', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('2', '4', '4', '2015-05-18 17:11:45', null, null, '0', '%E8%BF%99%E6%98%AF%E4%B8%AA%E9%94%A4%E5%AD%90', 'o-MKajmTFGDPK0CsVLxkrfKUmNcg', 'kelly测试号', '2', '', 'bbb', '20', null, '2015-05-18 17:11:45', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('3', '4', '4', '2015-05-18 17:12:18', null, null, '0', '%E8%BF%99%E6%98%AF%E4%B8%AA%E9%94%A4%E5%AD%90', 'o-MKajmTFGDPK0CsVLxkrfKUmNcg', 'kelly测试号', '2', '', '/::|/::|/::|', '20', null, '2015-05-18 17:12:19', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[3]测试问题\n[4]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('4', '4', '4', '2015-05-18 17:12:29', null, null, '1', '%E8%BF%99%E6%98%AF%E4%B8%AA%E9%94%A4%E5%AD%90', 'o-MKajmTFGDPK0CsVLxkrfKUmNcg', 'kelly测试号', '2', '', '3', '20', null, '2015-05-18 17:12:31', '/ump/attached/wx_image/4/20150518/20150518171200_578.jpg', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('5', '4', '4', '2015-05-22 11:42:12', null, null, '0', '%E8%BF%99%E6%98%AF%E4%B8%AA%E9%94%A4%E5%AD%90', 'o-MKajmTFGDPK0CsVLxkrfKUmNcg', 'kelly测试号', '2', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86d76dfd99c90192&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=11#wechat_redirect', '20', null, '2015-05-22 11:42:12', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]测试问题\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('6', '4', '12', '2015-05-22 11:43:39', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb35e13aa2e538250&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=51#wechat_redirect', '41', null, '2015-05-22 11:43:39', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('7', '4', '12', '2015-05-22 13:38:48', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb35e13aa2e538250&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=61#wechat_redirect', '41', null, '2015-05-22 13:38:48', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('8', '4', '12', '2015-05-22 14:46:46', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb35e13aa2e538250&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=61#wechat_redirect', '41', null, '2015-05-22 14:46:46', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('9', '4', '12', '2015-05-25 13:56:17', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb35e13aa2e538250&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=51#wechat_redirect', '41', null, '2015-05-25 13:56:18', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('10', '4', '12', '2015-05-25 14:04:00', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb35e13aa2e538250&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=51#wechat_redirect', '41', null, '2015-05-25 14:04:01', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('11', '4', '12', '2015-05-25 14:25:40', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb35e13aa2e538250&redirect_uri=http://info.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=51#wechat_redirect', '41', null, '2015-05-25 14:25:40', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('12', '4', '12', '2015-05-26 14:28:03', null, null, '0', 'styx', 'oQhw9s8KU2zOIe-iuw-Q5osYz-eY', '交行', '3', '', 'http://gzjh.9client.com/ump/pageProprietor/proprietor_web', '41', null, '2015-05-26 14:28:03', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('13', '4', '12', '2015-05-28 11:58:44', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx22f033174def97d0&redirect_uri=http://gzjh.tunnel.mobi/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=221#wechat_redirect', '55', null, '2015-05-28 11:58:44', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('14', '4', '12', '2015-05-28 14:09:25', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx22f033174def97d0&redirect_uri=http://gzjh.tunnel.mobi/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=241#wechat_redirect', '55', null, '2015-05-28 14:09:26', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('15', '4', '12', '2015-05-28 17:19:17', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', 'http://gzjh.9client.com/ump/login.jspx', '55', null, '2015-05-28 17:19:17', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('16', '4', '12', '2015-05-28 17:26:00', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'opnU9s7NawMV2cvRQ5Cj2CUrMTPI', '交行', '3', '', 'http://gzjh.9client.com/ump/login.jspx', '74', null, '2015-05-28 17:26:00', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('17', '2', '1', '2015-05-28 17:28:38', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'oo33Sspe-zrQJmlPhBzBG_1bf4-o', 'cheally', '1', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2c94749984dbf67d&redirect_uri=http://gzjh.tunnel.mobi/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=351#wechat_redirect', '73', null, '2015-05-28 17:28:38', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('18', '3', '19', '2015-05-28 19:08:48', null, null, '0', 'Mrchan', 'ofwixuEws5C_NrG245LFu1owIbmI', 'emp', '4', '堪培拉', 'http://cheally.tunnel.mobi/ump/pageFees/showLifeDetail?gzjh=&friendId=78', '78', null, '2015-05-28 19:08:49', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('19', '4', '12', '2015-05-28 20:21:53', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', '还是说', '55', null, '2015-05-28 20:21:54', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('20', '4', '12', '2015-06-01 17:19:20', null, null, '0', '__%E9%98%BF%E5%8D%9F%E4%B8%B6%EF%B9%8C', 'opnU9s3DmTDnHDohFawYLZZMWShg', '交行', '3', '山东', '/:@x', '58', null, '2015-06-01 17:19:20', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('21', '4', '12', '2015-06-01 17:19:57', null, null, '0', '__%E9%98%BF%E5%8D%9F%E4%B8%B6%EF%B9%8C', 'opnU9s3DmTDnHDohFawYLZZMWShg', '交行', '3', '山东', '/::-S', '58', null, '2015-06-01 17:19:58', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('22', '4', '12', '2015-06-03 18:28:44', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', 'nsnsns', '55', null, '2015-06-03 18:28:44', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('23', '4', '12', '2015-06-08 13:45:57', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', '淡淡的', '55', null, '2015-06-08 13:45:57', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('24', '8', '45', '2015-06-09 15:50:20', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'oJIXksjfdO-6Q0KORfc7K3ffvHHc', '久科订阅号', '6', '湖南', '..', '102', null, '2015-06-09 15:50:21', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('25', '8', '45', '2015-06-09 16:25:30', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'oJIXksjCSuUWLK2M7gDlXFsW9ZkA', '久科订阅号', '6', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd7a9aff8d4e19d39&redirect_uri=http://gzjh.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=761#wechat_redirect', '121', null, '2015-06-09 16:25:30', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('26', '8', '45', '2015-06-09 16:35:22', null, null, '0', 'VVIP', 'oJIXksl7iPokWiR30avLVUAhGPnI', '久科订阅号', '6', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd7a9aff8d4.e19d39&redirect_uri=http://gzjh.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=771#wechat_redirect', '122', null, '2015-06-09 16:35:22', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('27', '8', '45', '2015-06-09 16:35:45', null, null, '0', 'VVIP', 'oJIXksl7iPokWiR30avLVUAhGPnI', '久科订阅号', '6', '', 'http://gzjh.9client.com/ump/pageFees/showLifeDetail?gzjh=&friendId=122', '122', null, '2015-06-09 16:35:46', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('28', '8', '51', '2015-06-18 11:31:52', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '1', '145', null, '2015-06-18 11:31:52', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('29', '8', '51', '2015-06-18 11:49:34', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '1', '135', null, '2015-06-18 11:49:34', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('30', '8', '51', '2015-06-18 15:51:16', null, null, '0', 'Sue.L', 'osCxbuA6zxIfFVyCEypG1hkCo7BA', '交通银行财智社区', '7', '上海', '刚刚好', '156', null, '2015-06-18 15:51:17', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('31', '8', '51', '2015-06-18 16:15:40', null, null, '0', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '1', '134', null, '2015-06-18 16:15:41', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('32', '8', '51', '2015-06-18 16:16:04', null, null, '0', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '[1]', '134', null, '2015-06-18 16:16:04', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('33', '8', '51', '2015-06-18 16:16:20', null, null, '0', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '查看社区公告', '134', null, '2015-06-18 16:16:20', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[3]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('34', '8', '51', '2015-06-18 16:18:58', null, null, '0', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '体验交行最红星期五', '134', null, '2015-06-18 16:18:58', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[4]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('35', '8', '51', '2015-06-18 16:40:55', null, null, '0', 'Sue.L', 'osCxbuA6zxIfFVyCEypG1hkCo7BA', '交通银行财智社区', '7', '上海', '不好好', '156', null, '2015-06-18 16:40:55', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('36', '8', '51', '2015-06-18 16:58:51', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '理财', '135', null, '2015-06-18 16:58:51', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('37', '8', '51', '2015-06-18 16:59:27', null, null, '4', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '理财', '135', null, '2015-06-18 16:59:28', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('38', '8', '51', '2015-06-18 17:00:17', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '我最红', '135', null, '2015-06-18 17:00:18', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[2]理财\n[3]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('39', '8', '51', '2015-06-18 17:39:50', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageFinancialProduct/showFinancialProduct?gzjh=&friendId=145', '145', null, '2015-06-18 17:39:50', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('40', '8', '51', '2015-06-18 18:08:35', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageLifeHelper/showLifeDetail?id=35', '145', null, '2015-06-18 18:08:35', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[3]理财\n[4]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('41', '8', '51', '2015-06-18 20:00:51', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-18 20:00:51', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('42', '8', '51', '2015-06-19 00:24:19', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:24:20', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('43', '8', '51', '2015-06-19 00:31:02', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:31:03', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('44', '8', '51', '2015-06-19 00:31:42', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:31:43', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('45', '8', '51', '2015-06-19 00:33:22', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:33:22', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('46', '8', '51', '2015-06-19 00:34:58', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:34:58', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('47', '8', '51', '2015-06-19 00:42:51', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:42:51', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('48', '8', '51', '2015-06-19 00:39:50', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:43:00', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('49', '8', '51', '2015-06-19 00:39:55', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:43:00', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('50', '8', '51', '2015-06-19 00:50:17', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 00:50:17', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('51', '8', '51', '2015-06-19 00:50:38', null, null, '4', '.', 'osCxbuM3t4_9Y1lIT55htgqa2_WI', '交通银行财智社区', '7', '巴黎', '理财', '150', null, '2015-06-19 00:53:48', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('52', '8', '51', '2015-06-19 00:50:43', null, null, '4', '.', 'osCxbuM3t4_9Y1lIT55htgqa2_WI', '交通银行财智社区', '7', '巴黎', '理财', '150', null, '2015-06-19 00:53:53', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('53', '8', '51', '2015-06-19 00:50:49', null, null, '4', '.', 'osCxbuM3t4_9Y1lIT55htgqa2_WI', '交通银行财智社区', '7', '巴黎', '理财', '150', null, '2015-06-19 00:53:53', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('54', '8', '51', '2015-06-19 01:11:43', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:11:43', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('55', '8', '51', '2015-06-19 01:19:55', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:19:55', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('56', '8', '51', '2015-06-19 01:19:55', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:19:59', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('57', '8', '51', '2015-06-19 01:19:55', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:19:59', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('58', '8', '51', '2015-06-19 01:17:18', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:23:08', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('59', '8', '51', '2015-06-19 01:23:38', null, null, '0', '%E6%B2%AA%E4%B8%8A%E6%B5%B7%E9%A3%8E', 'osCxbuKDETBHNTG7epWhcdObXF1I', '交通银行财智社区', '7', '上海', '利好', '143', null, '2015-06-19 01:26:17', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[3]理财\n[4]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('60', '8', '51', '2015-06-19 01:23:45', null, null, '4', '%E6%B2%AA%E4%B8%8A%E6%B5%B7%E9%A3%8E', 'osCxbuKDETBHNTG7epWhcdObXF1I', '交通银行财智社区', '7', '上海', '理财', '143', null, '2015-06-19 01:26:18', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('61', '8', '51', '2015-06-19 01:23:32', null, null, '0', '%E6%B2%AA%E4%B8%8A%E6%B5%B7%E9%A3%8E', 'osCxbuKDETBHNTG7epWhcdObXF1I', '交通银行财智社区', '7', '上海', 'hhe', '143', null, '2015-06-19 01:26:41', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('62', '8', '51', '2015-06-19 01:27:43', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:27:44', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('63', '8', '51', '2015-06-19 01:38:04', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 01:41:13', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('64', '8', '51', '2015-06-19 08:26:03', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '社区', '151', null, '2015-06-19 08:26:03', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('65', '8', '51', '2015-06-19 08:26:19', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '特惠', '151', null, '2015-06-19 08:26:20', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[3]理财\n[4]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('66', '8', '51', '2015-06-19 08:36:31', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '3', '151', null, null, null, null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('67', '8', '51', '2015-06-19 08:36:40', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '来看看', '151', null, '2015-06-19 08:36:40', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[5]理财\n[6]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('68', '8', '51', '2015-06-19 08:36:54', null, null, '4', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '5', '151', null, '2015-06-19 08:36:54', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('69', '8', '51', '2015-06-19 08:57:26', null, null, '4', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '理财', '135', null, '2015-06-19 08:57:26', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('70', '8', '51', '2015-06-19 09:14:28', null, null, '4', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '理财', '153', null, '2015-06-19 09:14:28', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('71', '8', '51', '2015-06-19 09:18:30', null, null, '4', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '活动', '135', null, '2015-06-19 09:18:30', '30', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('72', '8', '51', '2015-06-19 09:18:30', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', null, '135', null, '2015-06-19 09:18:30', '相关问题：\n[1]理财\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('73', '8', '51', '2015-06-19 09:40:26', null, null, '4', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '社区', '135', null, '2015-06-19 09:40:26', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('74', '8', '51', '2015-06-19 09:40:31', null, null, '4', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '1', '135', null, '2015-06-19 09:40:31', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('75', '8', '51', '2015-06-19 09:40:38', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '2', '135', null, '2015-06-19 09:40:38', '真抱歉，我不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[2]理财\n[3]活动\n[4]余额\n[5]物管费\n[6]绑定\n[7]办卡\n[8]社区\n[9]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('76', '8', '51', '2015-06-19 09:40:52', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '7', '135', null, '2015-06-19 09:40:52', '尊敬的用户，请您输入“姓名＋手机”，我们在五个工作日内有专业客户经理与您联系，感谢您对交通银行的支持！', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('77', '8', '51', '2015-06-19 09:40:57', null, null, '4', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '8', '135', null, '2015-06-19 09:40:57', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('78', '8', '51', '2015-06-19 09:41:09', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '6', '135', null, null, null, null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('79', '8', '51', '2015-06-19 09:45:53', null, null, '4', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '理财', '134', null, '2015-06-19 09:45:53', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('80', '8', '51', '2015-06-19 09:46:01', null, null, '4', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '公告', '134', null, '2015-06-19 09:46:01', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('81', '8', '51', '2015-06-19 09:55:48', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '办卡', '135', null, '2015-06-19 09:55:48', '尊敬的用户，请您输入“姓名＋手机”，我们在五个工作日内有专业客户经理与您联系，感谢您对交通银行的支持！', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('82', '8', '51', '2015-06-19 09:56:05', null, null, '0', '%E6%9B%B9%E6%9C%9D', 'osCxbuO0W1_FApvE2LLLio_jApHk', '交通银行财智社区', '7', '广东', '余额', '135', null, '2015-06-19 09:56:05', '尊敬的用户，请您关注微信号【交通银行微银行】或输入【Bank-95599】进行账户查询！关注后可查看信用卡办理进度、查询积分、查看更多专属优惠等内容！\r\n\r\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('83', '8', '51', '2015-06-19 11:15:48', null, null, '4', '%E7%BD%97%E6%99%93%E5%9F%B9', 'osCxbuKE2Cw86lVrz2LKwLq2GnXo', '交通银行财智社区', '7', '广东', '社区', '159', null, '2015-06-19 11:15:48', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('84', '8', '51', '2015-06-19 11:15:49', null, null, '4', '%E6%A2%A6%E6%83%B3%E7%9A%84%E5%A4%A9%E7%A9%BA', 'osCxbuB3ouYU7k7yIPInWu6A-vYM', '交通银行财智社区', '7', '广东', '社区', '160', null, '2015-06-19 11:15:49', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('85', '8', '51', '2015-06-19 11:15:50', null, null, '4', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '社区', '151', null, '2015-06-19 11:15:50', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('86', '8', '51', '2015-06-19 11:16:01', null, null, '4', '%E7%BD%97%E6%99%93%E5%9F%B9', 'osCxbuKE2Cw86lVrz2LKwLq2GnXo', '交通银行财智社区', '7', '广东', '物管费', '159', null, '2015-06-19 11:16:01', '24', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('87', '8', '51', '2015-06-19 11:16:02', null, null, '4', '%E5%88%A9%E7%BE%A4', 'osCxbuBsI8VQevxqspuv5-DmCU2c', '交通银行财智社区', '7', '广东', '社区', '134', null, '2015-06-19 11:16:02', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('88', '8', '51', '2015-06-24 10:00:00', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '把把', '151', null, '2015-06-24 10:00:00', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[7]理财\n[8]活动\n[9]余额\n[10]物管费\n[11]绑定\n[12]办卡\n[13]社区', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('89', '8', '51', '2015-06-24 10:02:16', null, null, '4', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '社区', '151', null, '2015-06-24 10:02:16', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('90', '8', '51', '2015-06-24 10:02:46', null, null, '4', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '社区', '151', null, '2015-06-24 10:02:46', '35', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('91', '8', '51', '2015-06-24 10:05:15', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '交行', '151', null, '2015-06-24 10:05:15', '交行\n相关问题：\n[14]余额\n[15]绑定\n[16]办卡\n[17]社区\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('92', '8', '51', '2015-06-24 10:05:15', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', null, '151', null, '2015-06-24 10:05:15', '相关问题：\n[14]余额\n[15]绑定\n[16]办卡\n[17]社区\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('93', '8', '51', '2015-06-24 10:28:16', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '交行', '153', null, '2015-06-24 10:28:16', '交行\n相关问题：\n[1]余额\n[2]绑定\n[3]办卡\n[4]社区\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('94', '8', '51', '2015-06-24 10:28:16', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', null, '153', null, '2015-06-24 10:28:16', '相关问题：\n[1]余额\n[2]绑定\n[3]办卡\n[4]社区\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('95', '8', '51', '2015-06-24 10:28:22', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '1', '153', null, '2015-06-24 10:28:23', '尊敬的用户，请您关注微信号【交通银行微银行】或输入【Bank-95599】进行账户查询！关注后可查看信用卡办理进度、查询积分、查看更多专属优惠等内容！\r\n\r\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('96', '8', '51', '2015-06-24 10:29:55', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '123446', '153', null, '2015-06-24 10:29:55', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[5]理财\n[6]活动\n[7]余额\n[8]物管费\n[9]绑定\n[10]办卡\n[11]社区\n[12]交行', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('97', '8', '51', '2015-06-24 10:31:30', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '健康快乐', '151', null, '2015-06-24 10:31:30', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[18]理财\n[19]活动\n[20]余额\n[21]物管费\n[22]绑定\n[23]办卡\n[24]社区\n[25]交行', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('98', '8', '51', '2015-06-24 10:31:50', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '交行', '153', null, '2015-06-24 10:31:50', '交行\n相关问题：\n[13]余额\n[14]绑定\n[15]办卡\n[16]社区\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('99', '8', '51', '2015-06-24 10:31:50', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', null, '153', null, '2015-06-24 10:31:50', '相关问题：\n[13]余额\n[14]绑定\n[15]办卡\n[16]社区\n', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('100', '8', '51', '2015-06-24 10:31:55', null, null, '0', 'ronie', 'osCxbuF6YF21XJTaZqHHDel-tagw', '交通银行财智社区', '7', '上海', '啦啦啦', '153', null, '2015-06-24 10:31:55', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[17]理财\n[18]活动\n[19]余额\n[20]物管费\n[21]绑定\n[22]办卡\n[23]社区\n[24]交行', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('101', '8', '51', '2015-06-24 13:32:09', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '不把', '151', null, '2015-06-24 13:32:09', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[26]理财\n[27]活动\n[28]余额\n[29]物管费\n[30]绑定\n[31]办卡\n[32]社区\n[33]交行', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('102', '8', '51', '2015-06-24 14:30:31', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '0', '151', null, '2015-06-24 14:30:32', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[34]理财\n[35]活动\n[36]余额\n[37]物管费\n[38]绑定\n[39]办卡\n[40]社区\n[41]交行', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('103', '8', '51', '2015-06-24 14:54:33', null, null, '4', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '1', '151', null, '2015-06-24 14:54:33', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('104', '8', '51', '2015-06-24 14:54:55', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '菜单', '151', null, '2015-06-24 14:54:55', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[42]理财\n[43]活动\n[44]余额\n[45]物管费\n[46]绑定\n[47]办卡\n[48]社区\n[49]交行', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('105', '8', '51', '2015-06-24 15:01:26', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '菜单', '151', null, '2015-06-24 15:01:26', 'Hi，亲，您提的词我们正在十万火急编译答案中，请尝试换个问题吧！~\r\n回复［社区］：查看社区公告\r\n回复［业主］：进行业主认证\r\n回复［理财］：专享社区理财服务\r\n回复［物管费］：查看物管费缴纳详情\n[50]理财\n[51]活动\n[52]余额\n[53]物管费\n[54]绑定\n[55]办卡\n[56]社区', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('106', '8', '51', '2015-06-24 15:03:48', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '菜单', '145', null, '2015-06-24 15:03:49', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('107', '8', '51', '2015-06-24 15:04:06', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '刘经理', '151', null, '2015-06-24 15:04:06', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[57]理财\n[58]活动\n[59]余额\n[60]物管费\n[61]绑定\n[62]办卡\n[63]社区\n[64]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('108', '8', '51', '2015-06-24 15:04:11', null, null, '4', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '1', '145', null, '2015-06-24 15:04:11', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('109', '8', '51', '2015-06-24 15:12:41', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '菜单', '151', null, '2015-06-24 15:12:41', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[65]理财\n[66]活动\n[67]余额\n[68]物管费\n[69]绑定\n[70]办卡\n[71]社区\n[72]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('110', '8', '51', '2015-06-29 17:47:16', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageCommunity/createCommunity?platId=7', '145', null, '2015-06-29 17:47:16', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('111', '8', '51', '2015-06-29 17:47:38', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageFinancialProduct/showFinancialProduct?gzjh=&friendId=145', '145', null, '2015-06-29 17:47:38', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[9]理财\n[10]活动\n[11]余额\n[12]物管费\n[13]绑定\n[14]办卡\n[15]社区\n[16]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('112', '8', '51', '2015-06-29 18:00:06', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageProprietor/proprietor_web?gzjh=&friendId=145&platId=7', '145', null, '2015-06-29 18:00:06', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('113', '8', '51', '2015-06-29 18:04:43', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageFinancialProduct/showFinancialProduct?gzjh=&friendId=145&platId=7', '145', null, '2015-06-29 18:04:43', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[9]理财\n[10]活动\n[11]余额\n[12]物管费\n[13]绑定\n[14]办卡\n[15]社区\n[16]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('114', '8', '51', '2015-07-03 10:06:27', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageProprietor/proprietor_web?gzjh=&friendId=145', '145', null, '2015-07-03 10:06:27', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('115', '8', '51', '2015-07-03 10:26:25', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '菜单', '145', null, '2015-07-03 10:26:26', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('116', '8', '12', '2015-07-03 16:32:46', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'opnU9s7NawMV2cvRQ5Cj2CUrMTPI', '交行', '3', '', 'http://gzjh.9client.com/ump/pageCommunity/createItme?id=21&appId=20', '74', null, '2015-07-03 16:32:46', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('117', '8', '51', '2015-07-07 14:16:13', null, null, '0', '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'osCxbuGYJb2JUSB0JkNYDuWbxxc8', '交通银行财智社区', '7', '上海', '菜单', '151', null, '2015-07-07 14:16:13', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('118', '8', '12', '2015-07-07 15:21:34', null, null, '0', 'Sue.L', 'opnU9s5fue1k29mUor5KoPw7FPqI', '交行', '3', '上海', 'http://gzjh.9client.com/ump/pageFinancialProduct?form&financialProductId=13&friendId=&platId=3', '55', null, '2015-07-07 15:21:34', '', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('119', '8', '51', '2015-07-09 10:34:03', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageCommunity/newCreateImg?id=25&appId=21', '145', null, '2015-07-09 10:34:03', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('120', '8', '51', '2015-07-10 14:09:58', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://121.199.10.83/ump/pageCultureLife/showCulture', '145', null, '2015-07-10 14:09:59', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[1]理财\n[2]活动\n[3]余额\n[4]物管费\n[5]绑定\n[6]办卡\n[7]社区\n[8]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('121', '8', '51', '2015-07-10 14:15:12', null, null, '4', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '1', '145', null, '2015-07-10 14:15:12', '44', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('122', '8', '51', '2015-07-10 14:15:24', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://9client/ump/wccmaterialses/showdetail/44?friendId=145', '145', null, '2015-07-10 14:15:24', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[9]理财\n[10]活动\n[11]余额\n[12]物管费\n[13]绑定\n[14]办卡\n[15]社区\n[16]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('123', '8', '51', '2015-07-10 14:15:59', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '就快', '145', null, '2015-07-10 14:15:59', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[17]理财\n[18]活动\n[19]余额\n[20]物管费\n[21]绑定\n[22]办卡\n[23]社区\n[24]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('124', '8', '51', '2015-07-10 14:18:37', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '_', '145', null, '2015-07-10 14:18:37', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[25]理财\n[26]活动\n[27]余额\n[28]物管费\n[29]绑定\n[30]办卡\n[31]社区\n[32]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('125', '8', '51', '2015-07-10 14:18:52', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', '了', '145', null, '2015-07-10 14:18:52', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[33]理财\n[34]活动\n[35]余额\n[36]物管费\n[37]绑定\n[38]办卡\n[39]社区\n[40]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('126', '8', '51', '2015-07-10 14:19:09', null, null, '0', '%E5%B1%A0%E5%A4%AB', 'osCxbuNCNJlGJTLx3I8uMy01w6Vc', '交通银行财智社区', '7', '上海', 'http://gzjh.9client.com/ump/pageCommunity/createCommunity?platId=7', '145', null, '2015-07-10 14:19:10', '真抱歉，小久不是很理解您的需求，请尝试换个词吧~您也可以输入感兴趣的内容前的编号获取更多详细信息哦~\n[41]理财\n[42]活动\n[43]余额\n[44]物管费\n[45]绑定\n[46]办卡\n[47]社区\n[48]人工客服', null, '0', null);
INSERT INTO `wcc_record_msg` VALUES ('127', '17', '0', '2016-05-10 20:08:50', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'http://cheallychb.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1', '10', null, '2016-05-10 20:08:50', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('128', '17', '0', '2016-05-11 15:23:21', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'http://www.w3school.com.cn/tiy/t.asp?f=html5_geolocation', '10', null, '2016-05-11 15:23:22', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('129', '17', '0', '2016-05-15 11:49:06', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'http://cheallychb.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1', '10', null, '2016-05-15 11:49:07', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('130', '17', '0', '2016-05-18 15:26:18', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0ae48f6f1ded7e64&redirect_uri=http://tlbank021.9client.com/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=21#wechat_redirecthttp://cheallychb.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1', '10', null, '2016-05-18 15:26:19', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('131', '17', '0', '2016-05-18 17:05:02', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showProductStroe?friendId=10&openId=oq4ytwZbC1hz8kdpEU02w-Gk-Yxc&state=2&code=031LTHrM0hq4Bi2Am8sM0IqDrM0LTHr-', '10', null, '2016-05-18 17:05:03', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('132', '17', '0', '2016-05-23 14:11:51', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'https://m.baidu.com/?from=844b&vit=fps', '10', null, '2016-05-23 14:12:01', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('133', '17', '0', '2016-05-23 14:12:41', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=2&friendId=10&openId=oq4ytwZbC1hz8kdpEU02w-Gk-Yxc&state=2&code=041oA1C60JMrKv1Ht6C60wG0C60oA1CN', '10', null, '2016-05-23 14:12:55', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('134', '17', '0', '2016-05-23 14:12:48', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=2&friendId=10&openId=oq4ytwZbC1hz8kdpEU02w-Gk-Yxc&state=2&code=041oA1C60JMrKv1Ht6C60wG0C60oA1CN', '10', null, '2016-05-23 14:13:21', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('135', '17', '0', '2016-05-23 14:21:48', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'https://m.baidu.com/?from=844b&vit=fps', '10', null, '2016-05-23 14:21:52', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('136', '17', '0', '2016-05-23 14:22:31', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', 'https://m.baidu.com/?from=844b&vit=fps', '10', null, '2016-05-23 14:22:35', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('137', '17', '0', '2016-06-03 10:34:50', null, null, '0', '%E6%B8%85%E6%99%A8', 'oq4ytwZbC1hz8kdpEU02w-Gk-Yxc', 'yun', '2', '', '11', '10', null, '2016-06-03 10:34:50', '', null, '0', '2');
INSERT INTO `wcc_record_msg` VALUES ('138', '17', '0', '2016-06-03 11:18:43', null, null, '0', 'Mrchan', 'oq4ytwVdtbSk8qzb2gknqQmb-JJQ', 'yun', '2', '堪培拉', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showProductStroe?friendId=12&openId=oq4ytwVdtbSk8qzb2gknqQmb-JJQ&state=2&code=031SlycQ1wGAMW0CgmdQ1X2xcQ1SlycV', '12', null, '2016-06-03 11:18:44', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('139', '17', '5', '2016-06-27 15:55:31', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'http://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1&friendId=5&openId=oo33SsmOcJjSpuJo-CxrECRTvTMI&state=1&code=031VZXnp0KKAZb10NHnp09uZnp0VZXnO', '5', null, '2016-06-27 15:55:32', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('140', '17', '5', '2016-06-27 16:03:57', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2c94749984dbf67d&redirect_uri=http://qycloud.ngrok.cc/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=71#wechat_redirecthttp://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1', '5', null, '2016-06-27 16:03:58', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('141', '17', '5', '2016-06-27 16:07:12', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2c94749984dbf67d&redirect_uri=http://qycloud.ngrok.cc/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=71#wechat_redirecthttp://qycloud.ngrok.cc/ump/pageTLProduct/showTLProduct?type=1', '5', null, '2016-06-27 16:07:12', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('142', '17', '5', '2016-06-27 17:29:58', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2c94749984dbf67d&redirect_uri=http://qycloud.ngrok.cc/ump/wxController/dispacher&response_type=code&scope=snsapi_base&state=81#wechat_redirecthttp://play.9where.com/bank_hongbao', '5', null, '2016-06-27 17:29:59', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('143', '17', '5', '2016-06-27 17:30:37', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'http://play.9where.com/bank_hongbao/?ACTIVITY=&friendId=5&openId=oo33SsmOcJjSpuJo-CxrECRTvTMI&state=1&code=041NH2E81jPsxV15EbD81552E81NH2ER', '5', null, '2016-06-27 17:30:37', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('144', '17', '5', '2016-07-20 16:03:13', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '庆祝一下', '5', null, '2016-07-20 16:03:14', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('145', '17', '5', '2016-07-31 11:52:58', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '查询学分', '5', null, '2016-07-31 11:52:59', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('146', '17', '5', '2016-07-31 11:52:57', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '查询学分', '5', null, '2016-07-31 11:52:59', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('147', '17', '5', '2016-07-31 11:53:32', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '申请维修', '5', null, '2016-07-31 11:53:33', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('148', '17', '5', '2016-07-31 11:59:11', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'http://demo.mycodes.net/shouji/qinggong/', '5', null, '2016-07-31 11:59:12', '[1]申请维修', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('149', '17', '5', '2016-07-31 12:03:40', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '1', '5', null, '2016-07-31 12:03:40', '', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('150', '17', '5', '2016-09-03 15:22:12', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', 'http://127.0.0.1/ump/pageBding/showBding?friendId=5&openId=oo33SsmOcJjSpuJo-CxrECRTvTMI&state=1&code=021TwxcY1PQ3B616UudY1r2xcY1TwxcD', '5', null, '2016-09-03 15:22:13', '[2]申请维修', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('151', '17', '5', '2016-09-14 16:52:24', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '到', '5', null, '2016-09-14 16:52:25', '[3]申请维修', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('152', '17', '5', '2016-09-14 16:52:50', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '的', '5', null, '2016-09-14 16:52:50', '[4]申请维修', null, '0', '0');
INSERT INTO `wcc_record_msg` VALUES ('153', '17', '5', '2016-09-14 16:52:58', null, null, '0', 'Mrchan', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', 'cheally', '1', '堪培拉', '4', '5', null, '2016-09-14 16:52:58', '', null, '0', '0');

-- ----------------------------
-- Table structure for `wcc_repair_reg`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_repair_reg`;
CREATE TABLE `wcc_repair_reg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(200) DEFAULT NULL,
  `area` varchar(200) DEFAULT NULL,
  `dept` varchar(200) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `fnick_name` varchar(200) DEFAULT NULL,
  `fphone` varchar(50) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_back` varchar(200) DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `problem_des` varchar(1000) DEFAULT NULL,
  `problem_type` varchar(200) DEFAULT NULL,
  `stu_no` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_repair_reg
-- ----------------------------
INSERT INTO `wcc_repair_reg` VALUES ('1', '天台和释放', 'fsdffff901', '软件工程', '二呆1', null, '1512321323', '2016-09-04 14:00:17', '是', null, '非共和国非可很快就。', '软件故障', '1001');

-- ----------------------------
-- Table structure for `wcc_sa_qr_code`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_sa_qr_code`;
CREATE TABLE `wcc_sa_qr_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_name` varchar(100) DEFAULT NULL,
  `code_url` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `remark` varchar(6000) DEFAULT NULL,
  `sa_mail_address` varchar(255) DEFAULT NULL,
  `sa_name` varchar(255) DEFAULT NULL,
  `sa_phone` varchar(255) DEFAULT NULL,
  `scene_str` varchar(255) DEFAULT NULL,
  `wcc_store` bigint(20) DEFAULT NULL,
  `state` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gqa9xj6y66f8jmlp3bic5ifbx` (`wcc_store`),
  CONSTRAINT `wcc_sa_qr_code_ibfk_1` FOREIGN KEY (`wcc_store`) REFERENCES `wcc_store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_sa_qr_code
-- ----------------------------
INSERT INTO `wcc_sa_qr_code` VALUES ('16', null, '/ump/attached/wxQrcode/1465898228850.jpg', '2016-06-14 17:57:04', null, '318877671@qq.com', 'cheally', '18817718223', 'oHjEzVNoz0XvNuKhd0xM2zKy8f53GjTX9Ui48XDeahZBC90r19Mm6lB26Qa1Zb53', '4', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('17', null, '/ump/attached/wxQrcode/1465899176156.jpg', '2016-06-14 18:12:49', null, 'tommy.zhou@9client', 'tommy', '15999999999', 'r2CoSEoYp7NIc6vA55Mo0XHwuHuPiVIkbamMwia6CmjBs7iVqZmBQsqm3CzDaqmX', '4', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('20', null, '/ump/attached/wxQrcode/1466065022282.jpg', '2016-06-16 16:18:06', null, 'kelly_lqq@163.com', 'kelly', '156666666', 'wO8IdmwcXPLLece16bfGBTqSeF41wFp3h4e6KxfDmGPq2h9y9ywsOOLF9EV7osom', '1', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('21', null, '/ump/attached/wxQrcode/1466065022617.jpg', '2016-06-16 16:18:10', null, 'cheally.chen@9client.com', '陆小凤', '1222222', 'olEWGK0nDZyFBtKYV7aVzT7dHE1Wl6ktloucKWOPQc7eYS1TswopnPS44KiQq4SS', '1', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('22', null, '/ump/attached/wxQrcode/1466065530957.jpg', '2016-06-16 16:25:25', null, '99999999@qq.com', '史泰龙1199999999', '18817718223', 'Ia5SvyELM0v0xpsKMGYDT7ixxr4RfnfEmGfDgBlUH4YdeYRL3OCDDf12lKYSNlfc', '1', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('23', null, '/ump/attached/wxQrcode/1466065610273.jpg', '2016-06-16 16:26:06', null, '11111111111@qq.com', '史泰龙88888', '159008888811', 'AzStopb0QLURTFHW7Dyhs13l48Z17FvQvgr8gCexKAmss0BQ59hZz4el0q3uNSuP', '1', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('24', null, '/ump/attached/wxQrcode/1466065733419.jpg', '2016-06-16 16:28:37', null, 'djfldfj@sina.com', '卢成飞', '1899999999', 'XgFuq9YMA6V2M7M8EWo9DxGDFkmcrUXU7tbGvND6nfWUYiePWdn1xMfnAsIo3Qbf', '1', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('25', null, '/ump/attached/wxQrcode/1466070617002.jpg', '2016-06-16 17:50:14', null, '383838383@qq.com', '刀白凤', '19990999', 'v6PwN5flltu0KMFIceslFwYaXvRPHyNO1MTFjHFWcTmxjkYvsXYnzQ22UixYhEG5', '3', '1');
INSERT INTO `wcc_sa_qr_code` VALUES ('26', null, '/ump/attached/wxQrcode/1466070617265.jpg', '2016-06-16 17:50:17', null, '1222@qq.com', '段正淳', '11111111', 'GlFTseNKXsZSen5AHa3YuLGHNjZNAasmv4HnI8d4Od8MBIjrfvAqEBhCWFqGF2eQ', '4', '1');

-- ----------------------------
-- Table structure for `wcc_sa_qr_code_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_sa_qr_code_platform_users`;
CREATE TABLE `wcc_sa_qr_code_platform_users` (
  `wcc_sa_qr_code` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_sa_qr_code`,`platform_users`),
  KEY `FK_fu7l9i9y2qlr1rthuh7r2ht2v` (`platform_users`),
  KEY `FK_bkooxdahlb1sixt2snbuexix3` (`wcc_sa_qr_code`),
  CONSTRAINT `wcc_sa_qr_code_platform_users_ibfk_1` FOREIGN KEY (`wcc_sa_qr_code`) REFERENCES `wcc_sa_qr_code` (`id`),
  CONSTRAINT `wcc_sa_qr_code_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_sa_qr_code_platform_users
-- ----------------------------
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('16', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('17', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('20', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('21', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('22', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('23', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('24', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('25', '1');
INSERT INTO `wcc_sa_qr_code_platform_users` VALUES ('26', '1');

-- ----------------------------
-- Table structure for `wcc_sncode`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_sncode`;
CREATE TABLE `wcc_sncode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `award_level` int(11) NOT NULL,
  `award_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `sn_code` varchar(200) NOT NULL,
  `sn_remark` varchar(4000) DEFAULT NULL,
  `sn_status` int(11) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `award_info` bigint(20) DEFAULT NULL,
  `friend` bigint(20) DEFAULT NULL,
  `lottery_activity` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s0moqts5m2x54u4vk1e4u9bgs` (`award_info`),
  KEY `FK_b5knfn1q4ghcajvp1httyvb9n` (`friend`),
  KEY `FK_a7eck8jlj6wr4yqfoo2wvln7` (`lottery_activity`),
  KEY `FK_21uctyaf6lbpqpoa0wme20som` (`platform_user`),
  CONSTRAINT `wcc_sncode_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_sncode_ibfk_2` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`),
  CONSTRAINT `wcc_sncode_ibfk_3` FOREIGN KEY (`friend`) REFERENCES `wcc_friend` (`id`),
  CONSTRAINT `wcc_sncode_ibfk_4` FOREIGN KEY (`award_info`) REFERENCES `wcc_award_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_sncode
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_store`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_store`;
CREATE TABLE `wcc_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_send_mail` tinyint(1) NOT NULL,
  `is_store_code` tinyint(1) DEFAULT NULL,
  `is_visiable` tinyint(1) DEFAULT NULL,
  `plat_form_name` varchar(255) DEFAULT NULL,
  `scene_id` varchar(255) DEFAULT NULL,
  `store_addres` varchar(200) NOT NULL,
  `store_auditing` int(11) NOT NULL,
  `store_big_image_url` varchar(255) DEFAULT NULL,
  `store_code_url` varchar(500) DEFAULT NULL,
  `store_email` varchar(255) NOT NULL,
  `store_laty` varchar(200) NOT NULL,
  `store_lngx` varchar(200) NOT NULL,
  `store_name` varchar(200) NOT NULL,
  `store_phone` varchar(100) NOT NULL,
  `store_remark` varchar(4000) DEFAULT NULL,
  `store_small_image_url` varchar(255) DEFAULT NULL,
  `store_text` varchar(4000) DEFAULT NULL,
  `store_url` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dl5wkb7s2dwruboska57c9itv` (`company`),
  KEY `FK_ebv1f139vlio56cyoq1phl28r` (`organization`),
  CONSTRAINT `wcc_store_ibfk_1` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `wcc_store_ibfk_2` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_store
-- ----------------------------
INSERT INTO `wcc_store` VALUES ('1', '2015-07-06 17:50:19', '1', '0', '0', null, null, '', '宜山路1798号', '1', '/ump/attached/wx_image/8/20150706/20150706175013_149.jpg', null, '88569210@qq.com', '31.1739', '121.395544', '交行银行分行', '13764559476', null, null, '宜山路合川路', 'www.baidu.com', '1', '8', '7');
INSERT INTO `wcc_store` VALUES ('3', '2016-05-11 15:49:19', '1', '0', '1', null, null, '3,', '徐汇区法院', '0', '/ump/attached/wx_image/17/20160511/20160511154759_848.jpg', null, '912121@qq.com', '31.196081', '121.43619', '上海市闵行店', '18817713998', null, null, '营业时间早上九点，到晚上八点', '', '9', '17', '16');
INSERT INTO `wcc_store` VALUES ('4', '2016-05-15 12:12:49', '1', '0', '1', null, null, '4,', '上海市闵行区宜山路1698号', '0', '', null, '912121@qq.com', '31.174286', '121.39726', '上海市徐家汇店', '18817713998', null, null, '9~81', 'http://www.baodu.com', '4', '17', '16');

-- ----------------------------
-- Table structure for `wcc_store_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_store_platform_users`;
CREATE TABLE `wcc_store_platform_users` (
  `wcc_store` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_store`,`platform_users`),
  KEY `FK_edl72s4h6019h0jthxayci9f4` (`platform_users`),
  KEY `FK_2gsn9oxmr0wbf67l4g55fi0t` (`wcc_store`),
  CONSTRAINT `wcc_store_platform_users_ibfk_1` FOREIGN KEY (`wcc_store`) REFERENCES `wcc_store` (`id`),
  CONSTRAINT `wcc_store_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_store_platform_users
-- ----------------------------
INSERT INTO `wcc_store_platform_users` VALUES ('3', '2');
INSERT INTO `wcc_store_platform_users` VALUES ('4', '2');

-- ----------------------------
-- Table structure for `wcc_stu`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_stu`;
CREATE TABLE `wcc_stu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) DEFAULT NULL,
  `fnick_name` varchar(200) DEFAULT NULL,
  `fphone` varchar(50) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `stu_no` varchar(200) DEFAULT NULL,
  `stu_colleage` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_stu
-- ----------------------------
INSERT INTO `wcc_stu` VALUES ('17', '旺季12', null, '124989494', null, null, '2221881199', '化学系123');
INSERT INTO `wcc_stu` VALUES ('18', '', null, '', '2016-09-10 15:07:12', '', '', '');
INSERT INTO `wcc_stu` VALUES ('19', '', null, '', '2016-09-14 16:50:14', 'oo33SsmOcJjSpuJo-CxrECRTvTMI', '', '');

-- ----------------------------
-- Table structure for `wcc_surveys`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_surveys`;
CREATE TABLE `wcc_surveys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `big_img` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `explains` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `is_raffle` tinyint(1) DEFAULT NULL,
  `issue` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `raffle_url` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `plat_form` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p9inc8wm440ap39l66x3a5ekt` (`company`),
  KEY `FK_5ulvqaqdk06sdt95kli3wuwf5` (`plat_form`),
  CONSTRAINT `wcc_surveys_ibfk_1` FOREIGN KEY (`plat_form`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_surveys_ibfk_2` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_surveys
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_survey_content`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_survey_content`;
CREATE TABLE `wcc_survey_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `is_null` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  `survey` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ob3sbg6h5j9vou0sbl6m1xjlk` (`company`),
  KEY `FK_o45ustplg10u8pgiyp46aphf7` (`organization`),
  KEY `FK_iecv5xiutu5cjwvjjnpbel0yg` (`survey`),
  CONSTRAINT `wcc_survey_content_ibfk_1` FOREIGN KEY (`survey`) REFERENCES `wcc_surveys` (`id`),
  CONSTRAINT `wcc_survey_content_ibfk_2` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`),
  CONSTRAINT `wcc_survey_content_ibfk_3` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_survey_content
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_survey_option`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_survey_option`;
CREATE TABLE `wcc_survey_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `options` varchar(1024) DEFAULT NULL,
  `options_type` int(11) NOT NULL,
  `question` varchar(255) DEFAULT NULL,
  `wcc_surveys` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_19txxg4vhjsk8h1wc73mty25q` (`wcc_surveys`),
  CONSTRAINT `wcc_survey_option_ibfk_1` FOREIGN KEY (`wcc_surveys`) REFERENCES `wcc_surveys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_survey_option
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_survey_result`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_survey_result`;
CREATE TABLE `wcc_survey_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `select_option` varchar(255) DEFAULT NULL,
  `wcc_friend` bigint(20) DEFAULT NULL,
  `wcc_survey` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dyou2qch36o9f4scdesuloqfo` (`wcc_friend`),
  KEY `FK_bl4uddn334mbn1vi8i1avbfct` (`wcc_survey`),
  CONSTRAINT `wcc_survey_result_ibfk_1` FOREIGN KEY (`wcc_survey`) REFERENCES `wcc_surveys` (`id`),
  CONSTRAINT `wcc_survey_result_ibfk_2` FOREIGN KEY (`wcc_friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_survey_result
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_template`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_template`;
CREATE TABLE `wcc_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `sort` varchar(40) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `use_count` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `name` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6qtxtlxeul6g21mlkp0os2dcj` (`name`),
  KEY `FK_1rvt9om0opww3igmkyjndbhg4` (`platform_user`),
  CONSTRAINT `wcc_template_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_template_ibfk_2` FOREIGN KEY (`name`) REFERENCES `pub_operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_template
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_topic`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_topic`;
CREATE TABLE `wcc_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_count` int(11) NOT NULL,
  `content` varchar(4000) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_official` tinyint(1) DEFAULT NULL,
  `praise_count` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `topic_image` varchar(4000) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_topic
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_tproducts`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_tproducts`;
CREATE TABLE `wcc_tproducts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applay_step` varchar(6000) DEFAULT NULL,
  `back_image` varchar(100) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `p_advantage` varchar(6000) DEFAULT NULL,
  `p_info` varchar(6000) DEFAULT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `thumb_image` varchar(100) DEFAULT NULL,
  `product_type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_tproducts
-- ----------------------------
INSERT INTO `wcc_tproducts` VALUES ('5', '<div id=\"SwitchContent2\" class=\"center_nrq\">\r\n	<div class=\"txt_14px\">\r\n		<p style=\"text-align:justify;\">\r\n			<b><img src=\"http://www.zjtlcb.com/site1/list/gryw/dkyw/cyt/images/2013/05/06/9F356F1ECDC3AA9D77D6454957C44C50.jpg\" /></b> \r\n		</p>\r\n		<p style=\"text-align:justify;\">\r\n			<b></b> \r\n		</p>\r\n		<p>\r\n			<b>1</b><b>．提交申请资料</b> \r\n		</p>\r\n		<p>\r\n			基本资料：身份证、营业执照、我行或他行前六个月对账单；\r\n		</p>\r\n		<p>\r\n			辅助资料：房产证、租房合同、行驶证、其他证明经营情况和信用情况的资料。\r\n		</p>\r\n		<p>\r\n			<b>2</b><b>．快速实地调查</b> \r\n		</p>\r\n		<p>\r\n			快速实地核实生产经营情况。\r\n		</p>\r\n		<p>\r\n			<b>3</b><b>．系统审核：</b> \r\n		</p>\r\n		<p>\r\n			快速决定贷款额度与期限。\r\n		</p>\r\n		<p>\r\n			<b>4</b><b>．签订合同</b> \r\n		</p>\r\n		<p>\r\n			借贷双方签订借款合同。\r\n		</p>\r\n		<p>\r\n			<b>5</b><b>．放款</b> \r\n		</p>\r\n		<p>\r\n			贷款资金及时入账。\r\n		</p>\r\n	</div>\r\n</div>\r\n<div id=\"SwitchContent3\" class=\"center_nrq\">\r\n	<div class=\"txt_14px\">\r\n		<p>\r\n			生产经营正常的广大小微企业主、个体经营户等实体经济经营者。\r\n		</p>\r\n	</div>\r\n</div>', '/ump/attached/wx_image/17/20160513/20160513100257_617.jpg', '2016-05-13 10:05:33', '<div id=\"tag\">\r\n	<div id=\"SwitchContent1\" class=\"center_nrq\">\r\n		<div class=\"txt_14px\">\r\n			<p>\r\n				广大小微企业主、个体工商户不时面临资金周转困难，\r\n			</p>\r\n			<p>\r\n				银行抵质押贷款耗时久、手续烦，\r\n			</p>\r\n			<p>\r\n				民间借款利率高，资金中介费用多、无保障。\r\n			</p>\r\n			<p style=\"text-indent:21pt;\">\r\n				泰隆银行专为小微企业主、个体工商户推出小额生意贷款“创业通”!\r\n			</p>\r\n			<p style=\"text-indent:21pt;\">\r\n				准入门槛低、无需抵质押、办理速度快、还款方式多、没有手续费，是您生意的好助手\r\n			</p>\r\n		</div>\r\n	</div>\r\n</div>\r\n<!--相关业务-->', '<div id=\"tag\">\r\n	<div id=\"SwitchContent1\" class=\"center_nrq\">\r\n		<div class=\"txt_14px\">\r\n			<p>\r\n				广大小微企业主、个体工商户不时面临资金周转困难，\r\n			</p>\r\n			<p>\r\n				银行抵质押贷款耗时久、手续烦，\r\n			</p>\r\n			<p>\r\n				民间借款利率高，资金中介费用多、无保障。\r\n			</p>\r\n			<p style=\"text-indent:21pt;\">\r\n				泰隆银行专为小微企业主、个体工商户推出小额生意贷款“创业通”!\r\n			</p>\r\n			<p style=\"text-indent:21pt;\">\r\n				准入门槛低、无需抵质押、办理速度快、还款方式多、没有手续费，是您生意的好助手。\r\n			</p>\r\n			<p style=\"text-indent:24pt;\">\r\n				“创业通”全新升级，更加贴合客户需求：\r\n			</p>\r\n			<p style=\"text-indent:24pt;\">\r\n				1．网上循环贷：一次授信，网上“24小时”随借随还，无缝链接您的生意资金需求。\r\n			</p>\r\n			<p style=\"text-indent:24pt;\">\r\n				2．灵活分期还：您可根据自己生意淡旺季或资金流动情况自主定制灵活的分期还款计划，绝不打乱您的资金安排。\r\n			</p>\r\n		</div>\r\n	</div>\r\n</div>\r\n<!--相关业务-->', '好理贷产品1', '/ump/attached/wx_image/17/20160513/20160513100252_928.jpg', '2');
INSERT INTO `wcc_tproducts` VALUES ('6', '<div id=\"SwitchContent2\" class=\"center_nrq\">\r\n	<div class=\"txt_14px\">\r\n		<p style=\"text-align:justify;\">\r\n			<b><img src=\"http://www.zjtlcb.com/site1/list/gryw/dkyw/cyt/images/2013/05/06/9F356F1ECDC3AA9D77D6454957C44C50.jpg\" /></b>\r\n		</p>\r\n		<p style=\"text-align:justify;\">\r\n			<b></b>\r\n		</p>\r\n		<p>\r\n			<b>1</b><b>．提交申请资料</b>\r\n		</p>\r\n		<p>\r\n			基本资料：身份证、营业执照、我行或他行前六个月对账单；\r\n		</p>\r\n		<p>\r\n			辅助资料：房产证、租房合同、行驶证、其他证明经营情况和信用情况的资料。\r\n		</p>\r\n		<p>\r\n			<b>2</b><b>．快速实地调查</b>\r\n		</p>\r\n		<p>\r\n			快速实地核实生产经营情况。\r\n		</p>\r\n		<p>\r\n			<b>3</b><b>．系统审核：</b>\r\n		</p>\r\n		<p>\r\n			快速决定贷款额度与期限。\r\n		</p>\r\n		<p>\r\n			<b>4</b><b>．签订合同</b>\r\n		</p>\r\n		<p>\r\n			借贷双方签订借款合同。\r\n		</p>\r\n		<p>\r\n			<b>5</b><b>．放款</b>\r\n		</p>\r\n		<p>\r\n			贷款资金及时入账。\r\n		</p>\r\n	</div>\r\n</div>\r\n<div id=\"SwitchContent3\" class=\"center_nrq\">\r\n	<div class=\"txt_14px\">\r\n		<p>\r\n			生产经营正常的广大小微企业主、个体经营户等实体经济经营者。\r\n		</p>\r\n	</div>\r\n</div>', '/ump/attached/wx_image/17/20160513/20160513101034_782.jpg', '2016-05-13 10:11:10', '<div id=\"SwitchContent2\" class=\"center_nrq\">\r\n	<div class=\"txt_14px\">\r\n		<p style=\"text-align:justify;\">\r\n			<b><img src=\"http://www.zjtlcb.com/site1/list/gryw/dkyw/cyt/images/2013/05/06/9F356F1ECDC3AA9D77D6454957C44C50.jpg\" /></b>\r\n		</p>\r\n		<p style=\"text-align:justify;\">\r\n			<b></b>\r\n		</p>\r\n		<p>\r\n			<b>1</b><b>．提交申请资料</b>\r\n		</p>\r\n		<p>\r\n			基本资料：身份证、营业执照、我行或他行前六个月对账单；\r\n		</p>\r\n		<p>\r\n			辅助资料：房产证、租房合同、行驶证、其他证明经营情况和信用情况的资料。\r\n		</p>\r\n		<p>\r\n			<b>2</b><b>．快速实地调查</b>\r\n		</p>\r\n		<p>\r\n			快速实地核实生产经营情况。\r\n		</p>\r\n		<p>\r\n			<b>3</b><b>．系统审核：</b>\r\n		</p>\r\n		<p>\r\n			快速决定贷款额度与期限。\r\n		</p>\r\n		<p>\r\n			<b>4</b><b>．签订合同</b>\r\n		</p>\r\n		<p>\r\n			借贷双方签订借款合同。\r\n		</p>\r\n		<p>\r\n			<b>5</b><b>．放款</b>\r\n		</p>\r\n		<p>\r\n			贷款资金及时入账。\r\n		</p>\r\n	</div>\r\n</div>\r\n<div id=\"SwitchContent3\" class=\"center_nrq\">\r\n	<div class=\"txt_14px\">\r\n		<p>\r\n			生产经营正常的广大小微企业主、个体经营户等实体经济经营者。\r\n		</p>\r\n	</div>\r\n</div>', '<div id=\"tag\">\r\n	<div id=\"SwitchContent1\" class=\"center_nrq\">\r\n		<div class=\"txt_14px\">\r\n			<p>\r\n				广大小微企业主、个体工商户不时面临资金周转困难，\r\n			</p>\r\n			<p>\r\n				银行抵质押贷款耗时久、手续烦，\r\n			</p>\r\n			<p>\r\n				民间借款利率高，资金中介费用多、无保障。\r\n			</p>\r\n			<p style=\"text-indent:21pt;\">\r\n				泰隆银行专为小微企业主、个体工商户推出小额生意贷款“创业通”!\r\n			</p>\r\n			<p style=\"text-indent:21pt;\">\r\n				准入门槛低、无需抵质押、办理速度快、还款方式多、没有手续费，是您生意的好助手。\r\n			</p>\r\n			<p style=\"text-indent:24pt;\">\r\n				“创业通”全新升级，更加贴合客户需求：\r\n			</p>\r\n			<p style=\"text-indent:24pt;\">\r\n				1．网上循环贷：一次授信，网上“24小时”随借随还，无缝链接您的生意资金需求。\r\n			</p>\r\n			<p style=\"text-indent:24pt;\">\r\n				2．灵活分期还：您可根据自己生意淡旺季或资金流动情况自主定制灵活的分期还款计划，绝不打乱您的资金安排。\r\n			</p>\r\n		</div>\r\n	</div>\r\n	<div id=\"SwitchContent2\" class=\"center_nrq\">\r\n		<div class=\"txt_14px\">\r\n			<p style=\"text-align:justify;\">\r\n				<br />\r\n			</p>\r\n		</div>\r\n	</div>\r\n</div>\r\n<!--相关业务-->', '钱好贷产品2', '/ump/attached/wx_image/17/20160513/20160513101029_13.jpg', '1');
INSERT INTO `wcc_tproducts` VALUES ('7', '<span>1.产品介绍：五元起存，开户时由储户约定存期和月存金额，存期分一年、三年、五年三档。存期内逐月存入固定金额，如有漏存应在次月补存。该储蓄可提前支取，但不得部分提前支取，提前支取按支取日挂牌活期利率计算。</span><br />\r\n<span>2.产品特点：适合每月有固定资金结余的储户；持有效身份证件均可在柜面办理。</span><br />\r\n<span>3.申办流程：持有效身份证件到我行柜面办理。</span>', '/ump/attached/wx_image/17/20160513/20160513101216_378.jpg', '2016-05-13 10:13:27', '<span>1.产品介绍：五元起存，开户时由储户约定存期和月存金额，存期分一年、三年、五年三档。存期内逐月存入固定金额，如有漏存应在次月补存。该储蓄可提前支取，但不得部分提前支取，提前支取按支取日挂牌活期利率计算。</span><br />\r\n<span>2.产品特点：适合每月有固定资金结余的储户；持有效身份证件均可在柜面办理。</span><br />\r\n<span>3.申办流程：持有效身份证件到我行柜面办理。</span>', '<span style=\"background-color:#FF9900;\">1.产品介绍：五元起存，开户时由储户约定存期和月存金额，存期分一年、三年、五年三档。存期内逐月存入固定金额，如有漏存应在次月补存。该储蓄可提前支取，但不得部分提前支取，提前支取按支取日挂牌活期利率计算。</span><br />\r\n<span style=\"background-color:#FF9900;\"> 2.产品特点：适合每月有固定资金结余的储户；持有效身份证件均可在柜面办理。</span><br />\r\n<span style=\"background-color:#FF9900;\"> 3.申办流程：持有效身份证件到我行柜面办理。</span>', '投资理财1', '/ump/attached/wx_image/17/20160513/20160513101210_74.jpg', '2');
INSERT INTO `wcc_tproducts` VALUES ('8', '<span style=\"color:#FF9900;\">1.产品介绍：五十元起存，类同零存整取定期储蓄存款方式。累计本金最高为二万元，存期分为一年、三年、六年三档，每月固定存入。特点：利率优惠，利息免税。一年期、三年期教育储蓄按开户日同档次整存整取定期储蓄利率计息；六年期按开户当日五年期整存整取定期储蓄存款利率计息。</span><br />\r\n<span style=\"color:#FF9900;\">2.产品特点：教育储蓄适合对象是在校小学四年级（含）以上的学生。</span><br />\r\n<span style=\"color:#FF9900;\">3.申办流程：持有效身份证件及相关证明到我行柜面办理。</span><br />\r\n<span style=\"color:#FF9900;\">4.注意事项：享受教育储蓄利率优惠的条件是：储户提供接受非义务教育的录取通知书原件或学校开具的相应证明原件。</span>', '/ump/attached/wx_image/17/20160513/20160513101405_848.jpg', '2016-05-13 10:14:47', '<span>1.产品介绍：五十元起存，类同零存整取定期储蓄存款方式。累计本金最高为二万元，存期分为一年、三年、六年三档，每月固定存入。特点：利率优惠，利息免税。一年期、三年期教育储蓄按开户日同档次整存整取定期储蓄利率计息；六年期按开户当日五年期整存整取定期储蓄存款利率计息。</span><br />\r\n<span>2.产品特点：教育储蓄适合对象是在校小学四年级（含）以上的学生。</span><br />\r\n<span>3.申办流程：持有效身份证件及相关证明到我行柜面办理。</span><br />\r\n<span>4.注意事项：享受教育储蓄利率优惠的条件是：储户提供接受非义务教育的录取通知书原件或学校开具的相应证明原件。</span>', '<span style=\"color:#E56600;\">1.产品介绍：五十元起存，类同零存整取定期储蓄存款方式。累计本金最高为二万元，存期分为一年、三年、六年三档，每月固定存入。特点：利率优惠，利息免税。一年期、三年期教育储蓄按开户日同档次整存整取定期储蓄利率计息；六年期按开户当日五年期整存整取定期储蓄存款利率计息。</span><br />\r\n<span style=\"color:#E56600;\"> 2.产品特点：教育储蓄适合对象是在校小学四年级（含）以上的学生。</span><br />\r\n<span style=\"color:#E56600;\"> 3.申办流程：持有效身份证件及相关证明到我行柜面办理。</span><br />\r\n<span style=\"color:#E56600;\"> 4.注意事项：享受教育储蓄利率优惠的条件是：储户提供接受非义务教育的录取通知书原件或学校开具的相应证明原件。</span>', '零存整取', '/ump/attached/wx_image/17/20160513/20160513101400_741.jpg', '2');

-- ----------------------------
-- Table structure for `wcc_tproducts_platform_users`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_tproducts_platform_users`;
CREATE TABLE `wcc_tproducts_platform_users` (
  `wcc_tproducts` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_tproducts`,`platform_users`),
  KEY `FK_p0owbcu0pbkksqesms0yih870` (`platform_users`),
  KEY `FK_4qftjvtbr75u0mvco3ad73w4m` (`wcc_tproducts`),
  CONSTRAINT `wcc_tproducts_platform_users_ibfk_1` FOREIGN KEY (`wcc_tproducts`) REFERENCES `wcc_tproducts` (`id`),
  CONSTRAINT `wcc_tproducts_platform_users_ibfk_2` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_tproducts_platform_users
-- ----------------------------
INSERT INTO `wcc_tproducts_platform_users` VALUES ('5', '2');
INSERT INTO `wcc_tproducts_platform_users` VALUES ('6', '2');
INSERT INTO `wcc_tproducts_platform_users` VALUES ('7', '2');
INSERT INTO `wcc_tproducts_platform_users` VALUES ('8', '2');

-- ----------------------------
-- Table structure for `wcc_user`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user`;
CREATE TABLE `wcc_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_count` int(11) NOT NULL,
  `first_topic_time` datetime NOT NULL,
  `given_comment_count` int(11) NOT NULL,
  `given_praise_count` int(11) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_official` tinyint(1) DEFAULT NULL,
  `last_topic_comment` varchar(200) NOT NULL,
  `last_topic_time` datetime NOT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `praise_count` int(11) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `user_image` varchar(300) NOT NULL,
  `user_name` varchar(200) NOT NULL,
  `user_topic_count` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d1agvrewwh4krle3vtv4txg5k` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_user_fields`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user_fields`;
CREATE TABLE `wcc_user_fields` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `field_name_ch` varchar(400) DEFAULT NULL,
  `field_nameen` varchar(400) DEFAULT NULL,
  `is_com_box_value` tinyint(1) DEFAULT NULL,
  `is_combox` tinyint(1) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_email` tinyint(1) DEFAULT NULL,
  `is_key` tinyint(1) DEFAULT NULL,
  `is_query_type` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user_fields
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_user_field_value`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user_field_value`;
CREATE TABLE `wcc_user_field_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `user_field_value` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user_field_value
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_user_lottery`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user_lottery`;
CREATE TABLE `wcc_user_lottery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `lotter_day` datetime DEFAULT NULL,
  `lotter_day_num` int(11) NOT NULL,
  `lottery_number` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `friend` bigint(20) DEFAULT NULL,
  `lottery_activity` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h6oplvgkjgteavt9a4t77q883` (`friend`),
  KEY `FK_ffi8a8cwooqqcba4dsiktwcp1` (`lottery_activity`),
  KEY `FK_19pvcutoj04oaawk796n0kmbp` (`platform_user`),
  CONSTRAINT `wcc_user_lottery_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_user_lottery_ibfk_2` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`),
  CONSTRAINT `wcc_user_lottery_ibfk_3` FOREIGN KEY (`friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user_lottery
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_user_name`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user_name`;
CREATE TABLE `wcc_user_name` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `tab_name` varchar(30) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mdiruak4coklie4ke3k7ju26b` (`tab_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user_name
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_user_pagetemp`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user_pagetemp`;
CREATE TABLE `wcc_user_pagetemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `page_title` varchar(200) NOT NULL,
  `pro_name` varchar(4000) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `tem_image` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t6cj389tlxjc1f3gajpe8aguu` (`page_title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user_pagetemp
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_utility`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_utility`;
CREATE TABLE `wcc_utility` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(300) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `url` varchar(400) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_utility
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_utils`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_utils`;
CREATE TABLE `wcc_utils` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `util_desc` varchar(255) NOT NULL,
  `util_name` varchar(200) NOT NULL,
  `util_url` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_aa2ida3ndthxs5prupbjts48s` (`util_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_utils
-- ----------------------------

-- ----------------------------
-- Table structure for `wcc_welcomkbs`
-- ----------------------------
DROP TABLE IF EXISTS `wcc_welcomkbs`;
CREATE TABLE `wcc_welcomkbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_customer` tinyint(1) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `reply_type` int(11) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `materials` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kikdxgjixhv8l3ayb56xt64hi` (`materials`),
  KEY `FK_b2r8srdyppo7j6ikjab085fof` (`platform_user`),
  CONSTRAINT `wcc_welcomkbs_ibfk_1` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `wcc_welcomkbs_ibfk_2` FOREIGN KEY (`materials`) REFERENCES `wcc_materials` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_welcomkbs
-- ----------------------------
INSERT INTO `wcc_welcomkbs` VALUES ('1', '1', '您好，我是小xia0', null, '0', null, '0', null, '1', '0', null, '1');

-- ----------------------------
-- Table structure for `zz_test`
-- ----------------------------
DROP TABLE IF EXISTS `zz_test`;
CREATE TABLE `zz_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of zz_test
-- ----------------------------
