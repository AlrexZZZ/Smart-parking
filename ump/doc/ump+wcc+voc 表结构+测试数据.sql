/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : ump

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2014-11-28 17:34:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pub_operator
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
  `mobile` varchar(255) NOT NULL,
  `operator_name` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  `pub_role` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f4fl7p55eop0kicbfdkkwdnd3` (`company`),
  KEY `FK_88frecsph45dw7bo5ojr89972` (`organization`),
  KEY `FK_k8by8j9ccxm02jhdc9tqbwwwx` (`pub_role`),
  CONSTRAINT `FK_88frecsph45dw7bo5ojr89972` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`),
  CONSTRAINT `FK_f4fl7p55eop0kicbfdkkwdnd3` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `FK_k8by8j9ccxm02jhdc9tqbwwwx` FOREIGN KEY (`pub_role`) REFERENCES `pub_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_operator
-- ----------------------------
INSERT INTO `pub_operator` VALUES ('1', 'Admin', '0', '0', '2014-11-28 15:59:22', '9@9.99', '1', '13123123123', 'Admin', '123456', null, '0', '1', null, null);

-- ----------------------------
-- Table structure for pub_organization
-- ----------------------------
DROP TABLE IF EXISTS `pub_organization`;
CREATE TABLE `pub_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_organization
-- ----------------------------
INSERT INTO `pub_organization` VALUES ('1', '2014-11-11 00:00:00', '1', '1', '根', '-1', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('2', '2014-11-28 09:53:05', '1', '1', '久科', '1', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('3', '2014-11-28 09:53:18', '1', '1', '研发部', '2', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('4', '2014-11-28 09:53:25', '1', '1', '销售部', '2', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('5', '2014-11-28 09:53:37', '1', '1', '行政部', '2', null, '0', '0');
INSERT INTO `pub_organization` VALUES ('6', '2014-11-28 09:53:55', '1', '1', '研发一部', '3', null, '0', '0');

-- ----------------------------
-- Table structure for pub_role
-- ----------------------------
DROP TABLE IF EXISTS `pub_role`;
CREATE TABLE `pub_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cyfx3c6sy192xhnjj9gwpdxme` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_role
-- ----------------------------
INSERT INTO `pub_role` VALUES ('1', '2014-11-28 16:02:27', '1', '1', null, '公司管理员', '0');

-- ----------------------------
-- Table structure for pub_role_authoritys
-- ----------------------------
DROP TABLE IF EXISTS `pub_role_authoritys`;
CREATE TABLE `pub_role_authoritys` (
  `pub_roles` bigint(20) NOT NULL,
  `authoritys` bigint(20) NOT NULL,
  PRIMARY KEY (`pub_roles`,`authoritys`),
  KEY `FK_q9n0ydai3yrb734mepgh7ri39` (`authoritys`),
  KEY `FK_2pgbtk9qkwv8smp1a29a3pb18` (`pub_roles`),
  CONSTRAINT `FK_2pgbtk9qkwv8smp1a29a3pb18` FOREIGN KEY (`pub_roles`) REFERENCES `pub_role` (`id`),
  CONSTRAINT `FK_q9n0ydai3yrb734mepgh7ri39` FOREIGN KEY (`authoritys`) REFERENCES `ump_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_role_authoritys
-- ----------------------------
INSERT INTO `pub_role_authoritys` VALUES ('1', '0');
INSERT INTO `pub_role_authoritys` VALUES ('1', '1');
INSERT INTO `pub_role_authoritys` VALUES ('1', '2');
INSERT INTO `pub_role_authoritys` VALUES ('1', '3');
INSERT INTO `pub_role_authoritys` VALUES ('1', '4');
INSERT INTO `pub_role_authoritys` VALUES ('1', '5');
INSERT INTO `pub_role_authoritys` VALUES ('1', '6');
INSERT INTO `pub_role_authoritys` VALUES ('1', '7');
INSERT INTO `pub_role_authoritys` VALUES ('1', '8');
INSERT INTO `pub_role_authoritys` VALUES ('1', '9');
INSERT INTO `pub_role_authoritys` VALUES ('1', '10');
INSERT INTO `pub_role_authoritys` VALUES ('1', '11');
INSERT INTO `pub_role_authoritys` VALUES ('1', '12');
INSERT INTO `pub_role_authoritys` VALUES ('1', '13');
INSERT INTO `pub_role_authoritys` VALUES ('1', '14');
INSERT INTO `pub_role_authoritys` VALUES ('1', '15');
INSERT INTO `pub_role_authoritys` VALUES ('1', '16');
INSERT INTO `pub_role_authoritys` VALUES ('1', '17');
INSERT INTO `pub_role_authoritys` VALUES ('1', '18');
INSERT INTO `pub_role_authoritys` VALUES ('1', '19');
INSERT INTO `pub_role_authoritys` VALUES ('1', '20');
INSERT INTO `pub_role_authoritys` VALUES ('1', '21');
INSERT INTO `pub_role_authoritys` VALUES ('1', '22');
INSERT INTO `pub_role_authoritys` VALUES ('1', '23');
INSERT INTO `pub_role_authoritys` VALUES ('1', '24');
INSERT INTO `pub_role_authoritys` VALUES ('1', '25');
INSERT INTO `pub_role_authoritys` VALUES ('1', '26');
INSERT INTO `pub_role_authoritys` VALUES ('1', '27');
INSERT INTO `pub_role_authoritys` VALUES ('1', '28');
INSERT INTO `pub_role_authoritys` VALUES ('1', '29');
INSERT INTO `pub_role_authoritys` VALUES ('1', '30');
INSERT INTO `pub_role_authoritys` VALUES ('1', '31');
INSERT INTO `pub_role_authoritys` VALUES ('1', '32');
INSERT INTO `pub_role_authoritys` VALUES ('1', '33');
INSERT INTO `pub_role_authoritys` VALUES ('1', '34');
INSERT INTO `pub_role_authoritys` VALUES ('1', '35');
INSERT INTO `pub_role_authoritys` VALUES ('1', '36');
INSERT INTO `pub_role_authoritys` VALUES ('1', '37');
INSERT INTO `pub_role_authoritys` VALUES ('1', '38');
INSERT INTO `pub_role_authoritys` VALUES ('1', '39');
INSERT INTO `pub_role_authoritys` VALUES ('1', '40');
INSERT INTO `pub_role_authoritys` VALUES ('1', '41');
INSERT INTO `pub_role_authoritys` VALUES ('1', '42');
INSERT INTO `pub_role_authoritys` VALUES ('1', '43');
INSERT INTO `pub_role_authoritys` VALUES ('1', '44');
INSERT INTO `pub_role_authoritys` VALUES ('1', '45');
INSERT INTO `pub_role_authoritys` VALUES ('1', '46');

-- ----------------------------
-- Table structure for ump_authority
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
  UNIQUE KEY `UK_awvv822xjyusqex1wy0fe2lp6` (`display_name`),
  KEY `FK_q2ufdibosq36g3ai2a80fc0ph` (`product`),
  CONSTRAINT `FK_q2ufdibosq36g3ai2a80fc0ph` FOREIGN KEY (`product`) REFERENCES `ump_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_authority
-- ----------------------------
INSERT INTO `ump_authority` VALUES ('0', '根', null, null, '1', '-1', null, '0', ' ', '0', null);
INSERT INTO `ump_authority` VALUES ('1', '公司管理', null, null, '1', '0', '', '0', 'a', '0', '1');
INSERT INTO `ump_authority` VALUES ('2', '公司注册', null, null, '1', '1', '', '0', '/ump/umpcompanys/companyRegisterList', '1', '1');
INSERT INTO `ump_authority` VALUES ('3', '服务管理', null, null, '1', '1', '', '0', '/ump/umpcompanyservices/list', '1', '1');
INSERT INTO `ump_authority` VALUES ('4', '产品管理', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('5', '产品列表', null, null, '1', '4', '', '0', '/ump/umpproducts?form', '2', '1');
INSERT INTO `ump_authority` VALUES ('6', '渠道管理', null, null, '1', '4', '', '0', '/ump/umpproducts/productList', '2', '1');
INSERT INTO `ump_authority` VALUES ('7', '版本管理', null, null, '1', '4', '', '0', '/ump/umpversions/versionList?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('8', '行业管理', null, null, '1', '4', '', '0', '/ump/umpparentbusinesstypes?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('9', '子行业管理', null, null, '1', '4', '', '0', '/ump/umpbusinesstypes?page=1&amp;size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('10', '品牌关键词审核', null, null, '4', '4', '', '0', '/ump/umpbrands/barndList?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('11', '管理中心', null, null, '1', '0', '', '0', '', '1', '1');
INSERT INTO `ump_authority` VALUES ('12', '创建权限', null, null, '1', '11', '', '0', '/ump/umproles/create?form', '0', '1');
INSERT INTO `ump_authority` VALUES ('13', '权限列表', null, null, '1', '11', '', '0', '/ump/umproles?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('14', '账号管理', null, null, '1', '11', '', '0', '/ump/umpoperators?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('15', '日志管理', null, null, '1', '11', '', '0', '/ump/umplogs?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('16', '组织管理', null, null, '1', '11', '', '0', '/ump/puborganizations?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('17', '邮箱配置', null, null, '1', '11', '', '0', '/ump/umpconfigs/ec', '0', '1');
INSERT INTO `ump_authority` VALUES ('18', '知识管理', null, null, '1', '0', null, '0', ' ', '0', '1');
INSERT INTO `ump_authority` VALUES ('19', '素材管理', null, null, '1', '18', null, '0', '/ump/wccmaterialses', '0', '1');
INSERT INTO `ump_authority` VALUES ('20', '常用文本', null, null, '1', '18', '', '0', '/ump/wcctemplates', '0', '1');
INSERT INTO `ump_authority` VALUES ('21', '自动回复', null, null, '1', '18', null, '0', '/ump/wccautokbses/showList', '0', '1');
INSERT INTO `ump_authority` VALUES ('22', '欢迎语设置', null, null, '1', '18', null, '0', '/ump/wccwelcomkbses?form', '0', '1');
INSERT INTO `ump_authority` VALUES ('23', '粉丝管理', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('24', '微信分组', null, null, '1', '23', null, '0', '/ump/wccgroups/show', '0', '1');
INSERT INTO `ump_authority` VALUES ('25', '粉丝信息', null, null, '1', '23', null, '0', '/ump/wccfriends?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('26', '应用管理', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('27', '微内容', null, null, '1', '26', '', '0', '/ump/wcccontents?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('28', '门店管理', null, null, '1', '26', '', '0', '/ump/wccstores?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('29', '创建抽奖活动', null, null, '1', '26', '', '0', '	/ump/wcclotteryactivitys?form', '0', '1');
INSERT INTO `ump_authority` VALUES ('30', '抽奖活动列表', null, null, '1', '26', '', '0', '	/ump/wcclotteryactivitys?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('31', '微信管理', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('32', '自定义菜单', null, null, '1', '31', '', '0', '/ump/wccmenus?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('33', '创建公众号', null, null, '1', '31', '', '0', '/ump/wccplatformusers?form', '1', '1');
INSERT INTO `ump_authority` VALUES ('34', '公众号管理', null, null, '1', '31', '', '0', '/ump/wccplatformusers?page=1&amp;size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('35', '添加权限组', null, null, '1', '31', '', '0', '/ump/pubroles?form', '1', '1');
INSERT INTO `ump_authority` VALUES ('36', '微信权限列表', null, null, '1', '31', '', '0', '/ump/pubroles?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('37', '统一账号管理', null, null, '1', '31', null, '0', '/ump/puboperators?page=1&amp;size=10', '0', '1');
INSERT INTO `ump_authority` VALUES ('38', 'VOC管理', null, null, '1', '0', '', '0', '', '0', '1');
INSERT INTO `ump_authority` VALUES ('39', '品牌管理', null, null, '1', '38', '', '0', '/ump/vocbrands?page=1&amp;size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('40', '词库规则管理', null, null, '1', '38', '', '0', '/ump/vocwordcategorys?page=1&amp;size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('41', 'SKU管理', null, null, '1', '38', '', '0', '/ump/vocskus?page=1&amp;size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('42', 'SKU属性管理', null, null, '1', '38', '', '0', '/ump/vocskupropertys?page=1&amp;size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('43', '全网搜索', null, null, '1', '38', '', '0', '/ump/voccomments?page=1&size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('44', '店铺管理', null, null, '1', '38', '', '0', '/ump/vocshops?page=1&size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('45', '模板管理', null, null, '1', '38', '', '0', '/ump/voctemplates?page=1&size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('46', '好中差规则管理', null, null, '1', '38', '', '0', '/ump/voccommentlevelrules?page=1&size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('47', '标签管理', null, null, '1', '38', '', '0', '/ump/voctagses?page=1&size=10', '1', '1');
INSERT INTO `ump_authority` VALUES ('48', '组织管理', null, null, '1', '26', '', '0', '/ump/puborganizations?page=1&amp;size=10', '0','1');
INSERT INTO `ump_authority` VALUES ('49', '回复记录', null, null, '1', '38', '', '0', '/ump/voccomments/replyRecordlist', '1', '1');
INSERT INTO `ump_authority` VALUES ('50', '用户设置', null, null, '1', '11', '', '0', '/ump/umpconfigs/wccEmailConfig', '0', '1');

INSERT INTO `ump_authority` VALUES ('100', '售前', null, null, '1', '0', '', '0', '', '0', '2');
INSERT INTO `ump_authority` VALUES ('101', '销售', null, null, '1', '0', '', '0', '', '0', '2');
INSERT INTO `ump_authority` VALUES ('102', '售后', null, null, '1', '0', '', '0', '', '0', '2');
INSERT INTO `ump_authority` VALUES ('103', '市场', null, null, '1', '0', '', '0', '', '0', '3');
INSERT INTO `ump_authority` VALUES ('104', '营销', null, null, '1', '0', '', '0', '', '0', '3');

-- ----------------------------
-- Table structure for ump_brand
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
  CONSTRAINT `FK_6svyt3o41m76m9nya5ekyo8vj` FOREIGN KEY (`business`) REFERENCES `ump_parent_business_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_brand
-- ----------------------------
INSERT INTO `ump_brand` VALUES ('1', '帮宝适', '1', '2014-11-28 10:21:37', '0', '1', '好\r\n很差\r\n一般', '', '0', '1');
INSERT INTO `ump_brand` VALUES ('2', '海飞丝', '1', '2014-11-28 10:22:46', '0', '1', '一般\r\n很差', '', '0', '1');
INSERT INTO `ump_brand` VALUES ('3', '玉兰油', '1', '2014-11-28 10:28:55', '0', '1', '很香\r\n很淡', '', '0', '1');
INSERT INTO `ump_brand` VALUES ('4', '康师傅', '1', '2014-11-28 10:30:09', '0', '1', '矿泉水\r\n农夫山泉', '矿泉水', '0', '5');
INSERT INTO `ump_brand` VALUES ('5', '七匹狼', '1', '2014-11-28 10:44:15', '0', '1', '七匹狼', '七匹狼', '0', '3');
INSERT INTO `ump_brand` VALUES ('6', '康师傅', '1', '2014-11-28 10:45:03', '0', '1', '康师傅', '康师傅', '0', '5');
INSERT INTO `ump_brand` VALUES ('7', '大保健', '1', '2014-11-28 11:57:47', '0', '1', '七匹狼', '大保健', '0', '2');
INSERT INTO `ump_brand` VALUES ('8', '香干子', '1', '2014-11-28 11:57:50', '0', '1', '好吃', '攸县香干，好吃不贵', '0', '1');
INSERT INTO `ump_brand` VALUES ('9', '波司登', '1', '2014-11-28 11:57:51', '0', '1', '波司登', '111', '0', '1');

-- ----------------------------
-- Table structure for ump_business_type
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
  CONSTRAINT `FK_1c8axprmrxjdxcvbkicwktv4n` FOREIGN KEY (`parent_business_type`) REFERENCES `ump_parent_business_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_business_type
-- ----------------------------
INSERT INTO `ump_business_type` VALUES ('1', '护舒宝', '2014-11-27 14:49:44', '0', '1', '', '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('2', '舒蕾', '2014-11-27 14:49:50', '0', '1', '', '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('3', '脑白金', '2014-11-27 14:50:00', '0', '1', '', '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('4', '黄金搭档', '2014-11-27 14:50:11', '0', '1', '', '0', '0', '2');
INSERT INTO `ump_business_type` VALUES ('5', '洗面奶', '2014-11-28 10:27:30', '0', '1', '洗面奶', '1', '0', '1');
INSERT INTO `ump_business_type` VALUES ('6', '婴儿用品', '2014-11-28 10:27:49', '0', '1', '婴儿用品', '0', '0', '1');
INSERT INTO `ump_business_type` VALUES ('7', '散文', '2014-11-28 10:28:05', '0', '1', '散文', '1', '0', '6');
INSERT INTO `ump_business_type` VALUES ('8', '羽绒服', '2014-11-28 10:28:21', '0', '1', '羽绒服', '3', '0', '3');

-- ----------------------------
-- Table structure for ump_channel
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
  CONSTRAINT `FK_iva7ivpdc6hjrktyynef6wwgp` FOREIGN KEY (`product`) REFERENCES `ump_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_channel
-- ----------------------------
INSERT INTO `ump_channel` VALUES ('1', '天猫', null, null, '1', null, '0', '2');
INSERT INTO `ump_channel` VALUES ('2', '淘宝', null, null, '1', null, '0', '2');
INSERT INTO `ump_channel` VALUES ('3', '微信', null, null, '1', null, '0', '3');
INSERT INTO `ump_channel` VALUES ('4', 'UCC', null, null, '1', null, '0', '3');
INSERT INTO `ump_channel` VALUES ('5', '京东', null, null, '1', null, '0', '2');
INSERT INTO `ump_channel` VALUES ('6', '一号店', null, null, '1', null, '0', '2');
INSERT INTO `ump_channel` VALUES ('7', '当当网', null, null, '1', null, '0', '2');

-- ----------------------------
-- Table structure for ump_company
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
  `service_end_time` datetime DEFAULT NULL,
  `service_start_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2vbe7w5wu40hytiswfcv87fvg` (`company_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company
-- ----------------------------
INSERT INTO `ump_company` VALUES ('1', '', '9', '2014-11-28 15:59:21', '9@9.99', '1', '1', '0', '13123123123', '久科', null, null, '1', '9.99.cc', '2');

-- ----------------------------
-- Table structure for ump_company_bussiness_types
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_bussiness_types`;
CREATE TABLE `ump_company_bussiness_types` (
  `ump_company` bigint(20) NOT NULL,
  `bussiness_types` bigint(20) NOT NULL,
  PRIMARY KEY (`ump_company`,`bussiness_types`),
  KEY `FK_js6jv0nw5q7lk4b3sawxgjl99` (`bussiness_types`),
  KEY `FK_4r96ta8n9v4cm1aq1hirmxhbi` (`ump_company`),
  CONSTRAINT `FK_4r96ta8n9v4cm1aq1hirmxhbi` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `FK_js6jv0nw5q7lk4b3sawxgjl99` FOREIGN KEY (`bussiness_types`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_bussiness_types
-- ----------------------------

-- ----------------------------
-- Table structure for ump_company_parent_business_type
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_parent_business_type`;
CREATE TABLE `ump_company_parent_business_type` (
  `company` bigint(20) NOT NULL,
  `parent_business_type` bigint(20) NOT NULL,
  PRIMARY KEY (`company`,`parent_business_type`),
  KEY `FK_duy7jmu7q803k9be9eq4ih76n` (`parent_business_type`),
  KEY `FK_q0vj89a128esgnfm6f31py9gm` (`company`),
  CONSTRAINT `FK_duy7jmu7q803k9be9eq4ih76n` FOREIGN KEY (`parent_business_type`) REFERENCES `ump_parent_business_type` (`id`),
  CONSTRAINT `FK_q0vj89a128esgnfm6f31py9gm` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_parent_business_type
-- ----------------------------
INSERT INTO `ump_company_parent_business_type` VALUES ('1', '1');

-- ----------------------------
-- Table structure for ump_company_products
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_products`;
CREATE TABLE `ump_company_products` (
  `companys` bigint(20) NOT NULL,
  `products` bigint(20) NOT NULL,
  PRIMARY KEY (`companys`,`products`),
  KEY `FK_9gy0caqu0gc7suci1v9j7f3x9` (`products`),
  KEY `FK_e0j4lbh7drmvi3lykya4nsw62` (`companys`),
  CONSTRAINT `FK_9gy0caqu0gc7suci1v9j7f3x9` FOREIGN KEY (`products`) REFERENCES `ump_product` (`id`),
  CONSTRAINT `FK_e0j4lbh7drmvi3lykya4nsw62` FOREIGN KEY (`companys`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_products
-- ----------------------------
INSERT INTO `ump_company_products` VALUES ('1', '3');

-- ----------------------------
-- Table structure for ump_company_service
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_service`;
CREATE TABLE `ump_company_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(255) NOT NULL,
  `company_service_status` int(11) DEFAULT NULL,
  `max_account` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `service_end_time` datetime NOT NULL,
  `service_start_time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  `version_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_service
-- ----------------------------
INSERT INTO `ump_company_service` VALUES ('1', '9', '0', '0', '3', '2014-11-28 16:42:33', '2014-11-28 15:59:21', '0', '3');

-- ----------------------------
-- Table structure for ump_company_service_channels
-- ----------------------------
DROP TABLE IF EXISTS `ump_company_service_channels`;
CREATE TABLE `ump_company_service_channels` (
  `company_services` bigint(20) NOT NULL,
  `channels` bigint(20) NOT NULL,
  PRIMARY KEY (`company_services`,`channels`),
  KEY `FK_cia0ebrdd2tw75d1go0l1lsod` (`channels`),
  KEY `FK_3k7bl3d7n6wmyf1r1iq8ua4ot` (`company_services`),
  CONSTRAINT `FK_3k7bl3d7n6wmyf1r1iq8ua4ot` FOREIGN KEY (`company_services`) REFERENCES `ump_company_service` (`id`),
  CONSTRAINT `FK_cia0ebrdd2tw75d1go0l1lsod` FOREIGN KEY (`channels`) REFERENCES `ump_channel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_company_service_channels
-- ----------------------------
INSERT INTO `ump_company_service_channels` VALUES ('1', '3');

-- ----------------------------
-- Table structure for ump_config
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_config
-- ----------------------------
INSERT INTO `ump_config` VALUES ('1', 'emailConfig', 'smtp.9client.com', 'HOST', '邮箱服务器host', '0');
INSERT INTO `ump_config` VALUES ('2', 'emailConfig', '25', 'PORT', '邮箱服务器port', '0');
INSERT INTO `ump_config` VALUES ('3', 'emailConfig', 'brian.ma@9client.com', 'USERNAME', '邮箱账号', '0');
INSERT INTO `ump_config` VALUES ('4', 'emailConfig', 'ma123456', 'PASSWORD', '邮箱密码', '0');
INSERT INTO `ump_config` VALUES ('5', 'emailConfig', 'help@9client.com', 'SENDER', '邮箱显示发送人', '0');

-- ----------------------------
-- Table structure for ump_dictionary
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
-- Table structure for ump_log
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_log
-- ----------------------------
INSERT INTO `ump_log` VALUES ('1', '1', '1', '0:0:0:0:0:0:0:1', null, '2014-11-28 15:59:46', null, '0');
INSERT INTO `ump_log` VALUES ('2', '1', '1', '0:0:0:0:0:0:0:1', null, '2014-11-28 16:01:48', null, '0');
INSERT INTO `ump_log` VALUES ('3', '1', '1', '0:0:0:0:0:0:0:1', '2014-11-28 16:07:12', '2014-11-28 16:04:14', null, '1');
INSERT INTO `ump_log` VALUES ('4', '1', '1', '0:0:0:0:0:0:0:1', null, '2014-11-28 16:07:16', null, '0');
INSERT INTO `ump_log` VALUES ('5', '1', '1', '0:0:0:0:0:0:0:1', null, '2014-11-28 16:56:16', null, '0');
INSERT INTO `ump_log` VALUES ('6', '1', '1', '0:0:0:0:0:0:0:1', '2014-11-28 16:56:38', '2014-11-28 16:56:36', null, '1');
INSERT INTO `ump_log` VALUES ('7', '1', '1', '0:0:0:0:0:0:0:1', null, '2014-11-28 16:56:43', null, '0');
INSERT INTO `ump_log` VALUES ('8', '1', '1', '0:0:0:0:0:0:0:1', '2014-11-28 17:34:11', '2014-11-28 17:20:35', null, '1');

-- ----------------------------
-- Table structure for ump_operator
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
  `ump_company` bigint(20) DEFAULT NULL,
  `urole` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4ti6x8islm9kljf6p4ymg0a2q` (`company`),
  KEY `FK_5w4gbts2mw1w2dokd4d2c3mb5` (`ump_company`),
  KEY `FK_kk105wm4aj9jopij7ef4al5k8` (`urole`),
  CONSTRAINT `FK_4ti6x8islm9kljf6p4ymg0a2q` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `FK_5w4gbts2mw1w2dokd4d2c3mb5` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`),
  CONSTRAINT `FK_kk105wm4aj9jopij7ef4al5k8` FOREIGN KEY (`urole`) REFERENCES `ump_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_operator
-- ----------------------------
INSERT INTO `ump_operator` VALUES ('1', 'Admin', '1', '0', '2014-11-28 15:59:21', '9@9.99', '1', '13123123123', 'Admin', '123456', null, '3', '1', null, null);

-- ----------------------------
-- Table structure for ump_parent_business_type
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_parent_business_type
-- ----------------------------
INSERT INTO `ump_parent_business_type` VALUES ('1', '日用品', '2014-11-27 14:49:29', '0', '1', '', '0', '0');
INSERT INTO `ump_parent_business_type` VALUES ('2', '保健品', '2014-11-27 14:49:36', '0', '1', '', '0', '0');
INSERT INTO `ump_parent_business_type` VALUES ('3', '服装', '2014-11-28 10:25:36', '0', '1', '服装', '1', '0');
INSERT INTO `ump_parent_business_type` VALUES ('4', '护肤品', '2014-11-28 10:25:47', '0', '1', '护肤品', '2', '0');
INSERT INTO `ump_parent_business_type` VALUES ('5', '食品', '2014-11-28 10:25:57', '0', '1', '食品', '3', '0');
INSERT INTO `ump_parent_business_type` VALUES ('6', '书籍', '2014-11-28 10:26:14', '0', '1', '书籍', '5', '0');

-- ----------------------------
-- Table structure for ump_product
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
INSERT INTO `ump_product` VALUES ('2', '2014-11-19 17:18:18', '0', '1', 'VOC', null, '0');
INSERT INTO `ump_product` VALUES ('3', '2014-11-19 17:18:18', '0', '1', 'WCC', null, '0');

-- ----------------------------
-- Table structure for ump_role
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_role
-- ----------------------------
INSERT INTO `ump_role` VALUES ('1', '2014-11-28 16:59:23', '1', '1', null, null, '公司管理员', '2014-11-28 16:59:23', '1');

-- ----------------------------
-- Table structure for ump_role_ump_authoritys
-- ----------------------------
DROP TABLE IF EXISTS `ump_role_ump_authoritys`;
CREATE TABLE `ump_role_ump_authoritys` (
  `ump_roles` bigint(20) NOT NULL,
  `ump_authoritys` bigint(20) NOT NULL,
  KEY `FK_t1thannsqrpj5hnm289ylm24d` (`ump_authoritys`),
  KEY `FK_mmde4sq0r65qu9c84fp0bvcnf` (`ump_roles`),
  CONSTRAINT `FK_mmde4sq0r65qu9c84fp0bvcnf` FOREIGN KEY (`ump_roles`) REFERENCES `ump_role` (`id`),
  CONSTRAINT `FK_t1thannsqrpj5hnm289ylm24d` FOREIGN KEY (`ump_authoritys`) REFERENCES `ump_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_role_ump_authoritys
-- ----------------------------
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '0');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '1');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '2');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '3');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '4');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '5');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '6');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '7');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '8');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '9');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '10');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '11');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '12');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '13');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '14');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '15');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '16');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '17');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '18');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '19');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '20');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '21');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '22');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '23');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '24');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '25');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '26');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '27');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '28');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '29');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '30');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '31');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '32');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '33');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '34');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '35');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '36');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '37');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '38');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '39');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '40');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '41');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '42');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '43');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '44');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '45');
INSERT INTO `ump_role_ump_authoritys` VALUES ('1', '46');

-- ----------------------------
-- Table structure for ump_version
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
  CONSTRAINT `FK_6jas28yo9lyort769vrf1og76` FOREIGN KEY (`product`) REFERENCES `ump_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_version
-- ----------------------------
INSERT INTO `ump_version` VALUES ('1', '2014-11-27 14:57:30', null, '0', '基本版', '2');
INSERT INTO `ump_version` VALUES ('2', '2014-11-27 14:57:37', null, '0', '高级版', '2');
INSERT INTO `ump_version` VALUES ('3', '2014-11-27 14:57:55', null, '0', '市场版', '3');
INSERT INTO `ump_version` VALUES ('4', '2014-11-27 14:58:10', null, '0', '营销版', '3');

-- ----------------------------
-- Table structure for ump_version_ump_authoritys
-- ----------------------------
DROP TABLE IF EXISTS `ump_version_ump_authoritys`;
CREATE TABLE `ump_version_ump_authoritys` (
  `ump_versions` bigint(20) NOT NULL,
  `ump_authoritys` bigint(20) NOT NULL,
  PRIMARY KEY (`ump_versions`,`ump_authoritys`),
  KEY `FK_cr3s5u9k0tohwjsqgqiqitks1` (`ump_authoritys`),
  KEY `FK_t91elb1sy2i1pwbgmjpbv4nwn` (`ump_versions`),
  CONSTRAINT `FK_cr3s5u9k0tohwjsqgqiqitks1` FOREIGN KEY (`ump_authoritys`) REFERENCES `ump_authority` (`id`),
  CONSTRAINT `FK_t91elb1sy2i1pwbgmjpbv4nwn` FOREIGN KEY (`ump_versions`) REFERENCES `ump_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ump_version_ump_authoritys
-- ----------------------------
INSERT INTO `ump_version_ump_authoritys` VALUES ('2', '100');
INSERT INTO `ump_version_ump_authoritys` VALUES ('1', '101');
INSERT INTO `ump_version_ump_authoritys` VALUES ('2', '101');
INSERT INTO `ump_version_ump_authoritys` VALUES ('2', '102');
INSERT INTO `ump_version_ump_authoritys` VALUES ('3', '102');
INSERT INTO `ump_version_ump_authoritys` VALUES ('4', '104');

-- ----------------------------
-- Table structure for voc_account
-- ----------------------------
DROP TABLE IF EXISTS `voc_account`;
CREATE TABLE `voc_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL,
  `cookie` varchar(4000) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `effect_time` datetime DEFAULT NULL,
  `expiry_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_invalid` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `session_key` varchar(200) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_35jc6a7mfx98whc5kq6lca6s2` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_account
-- ----------------------------

-- ----------------------------
-- Table structure for voc_appkey
-- ----------------------------
DROP TABLE IF EXISTS `voc_appkey`;
CREATE TABLE `voc_appkey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appkey` varchar(32) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
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
-- Table structure for voc_brand
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
  `ump_business_type` bigint(20) DEFAULT NULL,
  `ump_company` bigint(20) DEFAULT NULL,
  `ump_parent_business_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_asnt6dp6ufc0f84txc6uh88pu` (`ump_business_type`),
  KEY `FK_m09qidv4emw86buscvqp1sg7s` (`ump_company`),
  KEY `FK_e8vdeco0tmveo7mjq0moj7uce` (`ump_parent_business_type`),
  CONSTRAINT `FK_asnt6dp6ufc0f84txc6uh88pu` FOREIGN KEY (`ump_business_type`) REFERENCES `ump_business_type` (`id`),
  CONSTRAINT `FK_e8vdeco0tmveo7mjq0moj7uce` FOREIGN KEY (`ump_parent_business_type`) REFERENCES `ump_parent_business_type` (`id`),
  CONSTRAINT `FK_m09qidv4emw86buscvqp1sg7s` FOREIGN KEY (`ump_company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_brand
-- ----------------------------
INSERT INTO `voc_brand` VALUES ('1', '玉兰油', '2014-11-28 10:25:50', '0', '1', '很香\r\n很淡', '', '0', '1', '1', null, null, '1');
INSERT INTO `voc_brand` VALUES ('2', '香干子', '2014-11-28 11:51:04', '0', '1', '好吃', '攸县香干，好吃不贵', '0', '1', '1', null, null, '1');
INSERT INTO `voc_brand` VALUES ('3', '波司登', '2014-11-28 11:51:49', '0', '1', '波司登', '111', '0', '1', '1', null, null, '1');
INSERT INTO `voc_brand` VALUES ('4', '大保健', '2014-11-28 11:57:17', '0', '1', '七匹狼', '大保健', '0', '1', '1', null, null, '2');
INSERT INTO `voc_brand` VALUES ('5', '波塞冬', '2014-11-28 17:22:34', '0', '1', '1', '1', '0', '0', '0', null, null, '1');

-- ----------------------------
-- Table structure for voc_brand_ump_channels
-- ----------------------------
DROP TABLE IF EXISTS `voc_brand_ump_channels`;
CREATE TABLE `voc_brand_ump_channels` (
  `voc_brand` bigint(20) NOT NULL,
  `ump_channels` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_brand`,`ump_channels`),
  KEY `FK_601bjejs9elf35a2541yvoe55` (`ump_channels`),
  KEY `FK_auctwk8sosrfqdmhj10x72e43` (`voc_brand`),
  CONSTRAINT `FK_601bjejs9elf35a2541yvoe55` FOREIGN KEY (`ump_channels`) REFERENCES `ump_channel` (`id`),
  CONSTRAINT `FK_auctwk8sosrfqdmhj10x72e43` FOREIGN KEY (`voc_brand`) REFERENCES `voc_brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_brand_ump_channels
-- ----------------------------
INSERT INTO `voc_brand_ump_channels` VALUES ('1', '1');
INSERT INTO `voc_brand_ump_channels` VALUES ('3', '5');
INSERT INTO `voc_brand_ump_channels` VALUES ('4', '5');
INSERT INTO `voc_brand_ump_channels` VALUES ('5', '5');
INSERT INTO `voc_brand_ump_channels` VALUES ('2', '6');

-- ----------------------------
-- Table structure for voc_brand_voc_shops
-- ----------------------------
DROP TABLE IF EXISTS `voc_brand_voc_shops`;
CREATE TABLE `voc_brand_voc_shops` (
  `voc_brands` bigint(20) NOT NULL,
  `voc_shops` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_brands`,`voc_shops`),
  KEY `FK_idarfqjc2kd5mamiyirumbol4` (`voc_shops`),
  KEY `FK_5yji2ednct3xa82xxbfprq4qp` (`voc_brands`),
  CONSTRAINT `FK_5yji2ednct3xa82xxbfprq4qp` FOREIGN KEY (`voc_brands`) REFERENCES `voc_brand` (`id`),
  CONSTRAINT `FK_idarfqjc2kd5mamiyirumbol4` FOREIGN KEY (`voc_shops`) REFERENCES `voc_shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_brand_voc_shops
-- ----------------------------
INSERT INTO `voc_brand_voc_shops` VALUES ('1', '1');
INSERT INTO `voc_brand_voc_shops` VALUES ('3', '4');
INSERT INTO `voc_brand_voc_shops` VALUES ('5', '4');
INSERT INTO `voc_brand_voc_shops` VALUES ('4', '5');
INSERT INTO `voc_brand_voc_shops` VALUES ('2', '7');

-- ----------------------------
-- Table structure for voc_comment
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment`;
CREATE TABLE `voc_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buy_time` datetime DEFAULT NULL,
  `comment_content` varchar(4000) DEFAULT NULL,
  `comment_level` int(11) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_title` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `dispatch_state` int(11) DEFAULT NULL,
  `dispatch_time` datetime DEFAULT NULL,
  `reply_content` varchar(500) DEFAULT NULL,
  `reply_param` varchar(4000) DEFAULT NULL,
  `reply_state` int(11) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `reply_url` varchar(4000) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `comment_category` bigint(20) DEFAULT NULL,
  `dispatch_operator` bigint(20) DEFAULT NULL,
  `goods` bigint(20) DEFAULT NULL,
  `reply_operator` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fqhmpiyxwubx3trfbiu42a9tu` (`comment_category`),
  KEY `FK_jyrwjudx5sh36hy8vlk1x1wfp` (`dispatch_operator`),
  KEY `FK_3rl0vxspv801xuix4yxj5e49e` (`goods`),
  KEY `FK_qcxnujwgxmqn17r6hberhhhri` (`reply_operator`),
  CONSTRAINT `FK_3rl0vxspv801xuix4yxj5e49e` FOREIGN KEY (`goods`) REFERENCES `voc_goods` (`id`),
  CONSTRAINT `FK_fqhmpiyxwubx3trfbiu42a9tu` FOREIGN KEY (`comment_category`) REFERENCES `voc_comment_category` (`id`),
  CONSTRAINT `FK_jyrwjudx5sh36hy8vlk1x1wfp` FOREIGN KEY (`dispatch_operator`) REFERENCES `ump_operator` (`id`),
  CONSTRAINT `FK_qcxnujwgxmqn17r6hberhhhri` FOREIGN KEY (`reply_operator`) REFERENCES `ump_operator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment
-- ----------------------------
INSERT INTO `voc_comment` VALUES ('1', '2014-11-28 11:11:27', '东西很好啊，好评', '0', '2014-11-26 11:11:51', '好评', '2014-11-28 11:11:59', '0', null, null, '1', '0', null, 'http://pampers.tmall.com/shop/view_shop.htm?', '小狼狗', '1', null, null, '1', null);
INSERT INTO `voc_comment` VALUES ('2', '2014-11-18 11:14:33', '东西一般，没想象的好', '1', '2014-11-28 11:14:51', '一般般', '2014-11-28 11:15:09', '1', '2014-11-28 11:15:31', null, '2', '0', null, 'http://pampers.tmall.com/shop/view_shop.htm?', '哮天犬', '1', null, null, '4', null);
INSERT INTO `voc_comment` VALUES ('3', '2014-11-28 11:17:06', '垃圾啊，感觉上当受骗了，以后再也不任性了。。。。', '2', '2014-11-28 11:17:36', '失望', '2014-11-28 11:17:47', '1', '2014-11-28 11:17:54', '亲，我们会提高的，我们的自信是你给的，加油加油！', '3', '1', '2014-11-28 11:18:40', 'http://pampers.tmall.com/shop/view_shop.htm?', '9527', '1', null, null, '3', null);

-- ----------------------------
-- Table structure for voc_comment_category
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
-- Table structure for voc_comment_level_category
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
-- Table structure for voc_comment_level_rule
-- ----------------------------
DROP TABLE IF EXISTS `voc_comment_level_rule`;
CREATE TABLE `voc_comment_level_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_level` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `bussiness_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pmyqu7kicaichk1xev93bqm44` (`bussiness_type`),
  CONSTRAINT `FK_pmyqu7kicaichk1xev93bqm44` FOREIGN KEY (`bussiness_type`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_comment_level_rule
-- ----------------------------
INSERT INTO `voc_comment_level_rule` VALUES ('1', '0', '2014-11-28 14:27:02', '0', '产品很好', '0', '5');
INSERT INTO `voc_comment_level_rule` VALUES ('2', '0', '2014-11-28 14:27:02', '0', '产品很棒', '0', '5');
INSERT INTO `voc_comment_level_rule` VALUES ('3', '0', '2014-11-28 14:27:02', '0', '效果不错', '0', '5');
INSERT INTO `voc_comment_level_rule` VALUES ('4', '0', '2014-11-28 14:27:03', '0', '比想象的好', '0', '5');
INSERT INTO `voc_comment_level_rule` VALUES ('5', '0', '2014-11-28 14:27:15', '0', '没想象的好', '0', '5');
INSERT INTO `voc_comment_level_rule` VALUES ('6', '0', '2014-11-28 14:28:38', '0', '产品很好', '0', '8');
INSERT INTO `voc_comment_level_rule` VALUES ('7', '0', '2014-11-28 14:28:38', '0', '产品很棒', '0', '8');
INSERT INTO `voc_comment_level_rule` VALUES ('8', '0', '2014-11-28 14:28:38', '0', '效果不错', '0', '8');
INSERT INTO `voc_comment_level_rule` VALUES ('9', '0', '2014-11-28 14:28:38', '0', '比想象的好', '0', '8');
INSERT INTO `voc_comment_level_rule` VALUES ('10', '0', '2014-11-28 14:28:38', '0', '态度一般', '0', '8');
INSERT INTO `voc_comment_level_rule` VALUES ('11', '2', '2014-11-28 14:29:28', '0', '态度一般', '0', '1');
INSERT INTO `voc_comment_level_rule` VALUES ('12', '2', '2014-11-28 14:29:29', '0', '态度恶劣', '0', '1');
INSERT INTO `voc_comment_level_rule` VALUES ('13', '2', '2014-11-28 14:29:29', '0', '产品一般', '0', '1');
INSERT INTO `voc_comment_level_rule` VALUES ('14', '2', '2014-11-28 14:29:29', '0', '质量一般', '0', '1');
INSERT INTO `voc_comment_level_rule` VALUES ('15', '2', '2014-11-28 14:29:29', '0', '服务一般', '0', '1');
INSERT INTO `voc_comment_level_rule` VALUES ('16', '2', '2014-11-28 14:29:29', '0', '产品垃圾', '0', '1');
INSERT INTO `voc_comment_level_rule` VALUES ('17', '2', '2014-11-28 14:30:17', '0', '比想象的好', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('18', '2', '2014-11-28 14:30:17', '0', '态度一般', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('19', '2', '2014-11-28 14:30:17', '0', '态度恶劣', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('20', '2', '2014-11-28 14:30:17', '0', '产品一般', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('21', '2', '2014-11-28 14:30:17', '0', '质量一般', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('22', '2', '2014-11-28 14:30:18', '0', '服务一般', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('23', '2', '2014-11-28 14:30:18', '0', '产品垃圾', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('24', '2', '2014-11-28 14:30:23', '0', '产品不行', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('25', '2', '2014-11-28 14:30:23', '0', '没想象的好', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('26', '2', '2014-11-28 14:30:23', '0', '比想象中差', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('27', '2', '2014-11-28 14:30:24', '0', '服务态度差', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('28', '2', '2014-11-28 14:30:24', '0', '质量很差', '0', '2');
INSERT INTO `voc_comment_level_rule` VALUES ('29', '2', '2014-11-28 14:30:24', '0', '次品', '0', '2');

-- ----------------------------
-- Table structure for voc_email
-- ----------------------------
DROP TABLE IF EXISTS `voc_email`;
CREATE TABLE `voc_email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_email
-- ----------------------------

-- ----------------------------
-- Table structure for voc_goods
-- ----------------------------
DROP TABLE IF EXISTS `voc_goods`;
CREATE TABLE `voc_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `goods_id` varchar(255) DEFAULT NULL,
  `goods_number` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `match_score` int(11) DEFAULT NULL,
  `match_time` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `channel` bigint(20) DEFAULT NULL,
  `shop` bigint(20) DEFAULT NULL,
  `voc_brand` bigint(20) DEFAULT NULL,
  `voc_goods_property` bigint(20) DEFAULT NULL,
  `voc_sku` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rnqgj0woy2wvi1d2b0s5ffwr9` (`channel`),
  KEY `FK_ctlj13xaeyoj5crq7o589a961` (`shop`),
  KEY `FK_en2g74f1nv8bdnwgomg872xqb` (`voc_brand`),
  KEY `FK_skimwuorrqlqogcq4vbrqrtdm` (`voc_goods_property`),
  KEY `FK_osjm2xkqm7pcw485urhwicgiq` (`voc_sku`),
  CONSTRAINT `FK_ctlj13xaeyoj5crq7o589a961` FOREIGN KEY (`shop`) REFERENCES `voc_shop` (`id`),
  CONSTRAINT `FK_en2g74f1nv8bdnwgomg872xqb` FOREIGN KEY (`voc_brand`) REFERENCES `voc_brand` (`id`),
  CONSTRAINT `FK_osjm2xkqm7pcw485urhwicgiq` FOREIGN KEY (`voc_sku`) REFERENCES `voc_sku` (`id`),
  CONSTRAINT `FK_rnqgj0woy2wvi1d2b0s5ffwr9` FOREIGN KEY (`channel`) REFERENCES `ump_channel` (`id`),
  CONSTRAINT `FK_skimwuorrqlqogcq4vbrqrtdm` FOREIGN KEY (`voc_goods_property`) REFERENCES `voc_goods_property` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_goods
-- ----------------------------
INSERT INTO `voc_goods` VALUES ('1', '2014-11-28 00:00:00', '1', '1', '1', null, null, 'Olay玉兰油多效修护保湿面霜50g*2瓶 保湿补水淡纹抗皱', null, 'http://detail.tmall.com/item.htm?spm=a1z10.3.w4011-3530545634.126.RmAAfR&id=17798564300&rn=373f6e3964f2d0282cb13a016f633572&abbucket=12', '0', '1', '1', null, null, null);
INSERT INTO `voc_goods` VALUES ('2', '2014-11-28 00:00:00', '2', '2', '1', null, null, 'Olay玉兰油水漾动力弹嫩免洗睡眠面膜130g 补水保湿', null, 'http://detail.tmall.com/item.htm?spm=a1z10.3.w4011-3530545634.131.RmAAfR&id=17217914469&rn=373f6e3964f2d0282cb13a016f633572&abbucket=12', '0', '1', '1', null, null, null);
INSERT INTO `voc_goods` VALUES ('3', '2014-11-28 00:00:00', '2', '2', '1', null, null, '七匹狼长袖T恤 男装2014秋装新品 ', null, 'http://item.jd.com/1035373420.html', '0', '5', '5', null, null, null);
INSERT INTO `voc_goods` VALUES ('4', '2014-11-28 00:00:00', '3', '3', '1', null, null, '七匹狼棉衣', null, 'http://item.jd.com/1028448486.html', '0', '5', '5', null, null, null);
INSERT INTO `voc_goods` VALUES ('5', '2014-11-28 00:00:00', '3', '3', '1', null, null, '傻小子麻辣香干 70克 傻小子精品香干 湖南特产 湘味熟食 玉峰', null, 'http://item.jd.com/1040971631.html', '0', '5', '7', null, null, null);

-- ----------------------------
-- Table structure for voc_goods_property
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
  CONSTRAINT `FK_sqx4nn8mjupol66tjbbxag9ri` FOREIGN KEY (`ump_business_type`) REFERENCES `ump_business_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_goods_property
-- ----------------------------
INSERT INTO `voc_goods_property` VALUES ('1', '2014-11-28 11:55:31', '尺寸', null, 'cm', 'number', '0', '1');

-- ----------------------------
-- Table structure for voc_goods_property_value
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
  CONSTRAINT `FK_55o5bec6bua0hiruved2vi8vm` FOREIGN KEY (`voc_sku`) REFERENCES `voc_sku` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_goods_property_value
-- ----------------------------
INSERT INTO `voc_goods_property_value` VALUES ('1', '尺寸', '20cm', '0', '1');
INSERT INTO `voc_goods_property_value` VALUES ('2', '尺寸', '20', '0', '2');

-- ----------------------------
-- Table structure for voc_shop
-- ----------------------------
DROP TABLE IF EXISTS `voc_shop`;
CREATE TABLE `voc_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `channel` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6ai8qtbxqqhqsrcmw8h5ynb9x` (`channel`),
  CONSTRAINT `FK_6ai8qtbxqqhqsrcmw8h5ynb9x` FOREIGN KEY (`channel`) REFERENCES `ump_channel` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_shop
-- ----------------------------
INSERT INTO `voc_shop` VALUES ('1', '2014-11-21 00:00:00', '1', '玉兰油官方旗舰店', 'http://olay.tmall.com/shop/view_shop.htm', '0', '1');
INSERT INTO `voc_shop` VALUES ('2', '2014-11-28 00:00:00', '1', '帮宝适官方旗舰店', 'http://pampers.tmall.com/shop/view_shop.htm', '0', '1');
INSERT INTO `voc_shop` VALUES ('3', '2014-11-28 00:00:00', '1', 'SKII官方旗舰店', 'http://skii.tmall.com/shop/view_shop.htm', '0', '1');
INSERT INTO `voc_shop` VALUES ('4', '2014-11-28 00:00:00', '1', '波司登旗舰店', 'http://search.jd.com/Search?keyword=%E6%B3%A2%E5%8F%B8%E7%99%BB&enc=utf-8&suggest=0', '0', '5');
INSERT INTO `voc_shop` VALUES ('5', '2014-11-28 00:00:00', '1', '七匹狼专卖店', 'http://search.jd.com/Search?keyword=%E4%B8%83%E5%8C%B9%E7%8B%BC&enc=utf-8', '0', '5');
INSERT INTO `voc_shop` VALUES ('6', '2014-11-28 00:00:00', '1', '书虫', 'http://www.dangdang.com/', '0', '7');
INSERT INTO `voc_shop` VALUES ('7', '2014-11-28 00:00:00', '1', '攸县香干', 'http://search.yhd.com/c0-0/k%25E6%2594%25B8%25E5%258E%25BF%25E9%25A6%2599%25E5%25B9%25B2/1/?tp=1.0.12.0.3.KboDFpQ', '0', '6');

-- ----------------------------
-- Table structure for voc_shop_goods
-- ----------------------------
DROP TABLE IF EXISTS `voc_shop_goods`;
CREATE TABLE `voc_shop_goods` (
  `voc_shop` bigint(20) NOT NULL,
  `goods` bigint(20) NOT NULL,
  PRIMARY KEY (`voc_shop`,`goods`),
  UNIQUE KEY `UK_l16o7vtuw95eojhj96msclboi` (`goods`),
  KEY `FK_l16o7vtuw95eojhj96msclboi` (`goods`),
  KEY `FK_me7rroe8863p68g18r6u9cay8` (`voc_shop`),
  CONSTRAINT `FK_l16o7vtuw95eojhj96msclboi` FOREIGN KEY (`goods`) REFERENCES `voc_goods` (`id`),
  CONSTRAINT `FK_me7rroe8863p68g18r6u9cay8` FOREIGN KEY (`voc_shop`) REFERENCES `voc_shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_shop_goods
-- ----------------------------

-- ----------------------------
-- Table structure for voc_sku
-- ----------------------------
DROP TABLE IF EXISTS `voc_sku`;
CREATE TABLE `voc_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `sku_code` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ump_business_type` bigint(20) DEFAULT NULL,
  `voc_brand` bigint(20) DEFAULT NULL,
  `voc_sku_property` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qhhg4xxy6pcuv12a8t33augto` (`ump_business_type`),
  KEY `FK_qwyu30idt3tqjgcw1w9g8eb7x` (`voc_brand`),
  KEY `FK_dxg7raycs3iarbm0mbt6x3082` (`voc_sku_property`),
  CONSTRAINT `FK_dxg7raycs3iarbm0mbt6x3082` FOREIGN KEY (`voc_sku_property`) REFERENCES `voc_sku_property` (`id`),
  CONSTRAINT `FK_qhhg4xxy6pcuv12a8t33augto` FOREIGN KEY (`ump_business_type`) REFERENCES `ump_business_type` (`id`),
  CONSTRAINT `FK_qwyu30idt3tqjgcw1w9g8eb7x` FOREIGN KEY (`voc_brand`) REFERENCES `voc_brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_sku
-- ----------------------------
INSERT INTO `voc_sku` VALUES ('1', '2014-11-28 11:59:25', '0', '1', '新品上市', 'utf8', '0', '1', '3', '2');
INSERT INTO `voc_sku` VALUES ('2', '2014-11-28 12:00:48', '0', '1', '爆品', 'ufo', '0', '1', '3', '1');

-- ----------------------------
-- Table structure for voc_sku_property
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_sku_property
-- ----------------------------
INSERT INTO `voc_sku_property` VALUES ('1', '2014-11-28 11:59:58', '0', '1', '新品', '新品上市', '0');
INSERT INTO `voc_sku_property` VALUES ('2', '2014-11-28 12:00:09', '0', '1', '二等品', '二等品上市', '0');

-- ----------------------------
-- Table structure for voc_template
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
  `bussiness_type` bigint(20) DEFAULT NULL,
  `voc_word_category` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m13r01cepqbhxwcvuqfbwh10d` (`brand`),
  KEY `FK_8sy5ctp0nj92kwd87gn5nmgu1` (`bussiness_type`),
  KEY `FK_lxqvl0rlp69tx47okjxyns5tt` (`voc_word_category`),
  CONSTRAINT `FK_8sy5ctp0nj92kwd87gn5nmgu1` FOREIGN KEY (`bussiness_type`) REFERENCES `ump_business_type` (`id`),
  CONSTRAINT `FK_lxqvl0rlp69tx47okjxyns5tt` FOREIGN KEY (`voc_word_category`) REFERENCES `voc_word_category` (`id`),
  CONSTRAINT `FK_m13r01cepqbhxwcvuqfbwh10d` FOREIGN KEY (`brand`) REFERENCES `ump_brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_template
-- ----------------------------
INSERT INTO `voc_template` VALUES ('1', '0', '亲，感谢你对本产品的支持，我们会更加努力，让您更加满意！', '2014-11-28 11:28:16', '1', '1', '产品效果好', '0', '3', '5', '6');
INSERT INTO `voc_template` VALUES ('2', '0', '亲，谢谢你对本产品的支持，我们会更加努力！', '2014-11-28 11:30:21', '1', '1', '质量好', '0', '5', '8', '6');
INSERT INTO `voc_template` VALUES ('3', '2', '亲，我们会改进产品！', '2014-11-28 11:32:27', '1', '1', '质量不好', '2', '5', '8', '12');

-- ----------------------------
-- Table structure for voc_template_category
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
-- Table structure for voc_template_rule
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
  CONSTRAINT `FK_dj1yrsu864lyvvro0bkmb3nr3` FOREIGN KEY (`template`) REFERENCES `voc_template` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_template_rule
-- ----------------------------
INSERT INTO `voc_template_rule` VALUES ('1', '2014-11-28 11:29:06', '产品很好产品很棒', '0', '1');
INSERT INTO `voc_template_rule` VALUES ('2', '2014-11-28 11:32:49', '服务态度差态度恶劣', '0', '3');

-- ----------------------------
-- Table structure for voc_word_category
-- ----------------------------
DROP TABLE IF EXISTS `voc_word_category`;
CREATE TABLE `voc_word_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_word_category
-- ----------------------------
INSERT INTO `voc_word_category` VALUES ('1', '2014-11-24 13:53:52', '0', '全部', '0', '0');
INSERT INTO `voc_word_category` VALUES ('2', '2014-11-24 14:53:53', '0', '好评', '1', '0');
INSERT INTO `voc_word_category` VALUES ('3', '2014-11-24 14:54:20', '0', '中评', '1', '0');
INSERT INTO `voc_word_category` VALUES ('4', '2014-11-24 14:54:50', '0', '差评', '1', '0');
INSERT INTO `voc_word_category` VALUES ('5', '2014-11-24 14:55:15', '0', '预警', '1', '0');
INSERT INTO `voc_word_category` VALUES ('6', '2014-11-28 11:22:41', '0', '产品', '2', '0');
INSERT INTO `voc_word_category` VALUES ('7', '2014-11-28 11:22:47', '0', '态度好', '2', '1');
INSERT INTO `voc_word_category` VALUES ('8', '2014-11-28 11:23:01', '0', '质量', '2', '0');
INSERT INTO `voc_word_category` VALUES ('9', '2014-11-28 11:23:17', '0', '产品', '3', '0');
INSERT INTO `voc_word_category` VALUES ('10', '2014-11-28 11:23:23', '0', '服务', '3', '0');
INSERT INTO `voc_word_category` VALUES ('11', '2014-11-28 11:23:28', '0', '质量', '3', '0');
INSERT INTO `voc_word_category` VALUES ('12', '2014-11-28 11:23:33', '0', '产品', '4', '0');
INSERT INTO `voc_word_category` VALUES ('13', '2014-11-28 11:23:40', '0', '服务', '4', '0');
INSERT INTO `voc_word_category` VALUES ('14', '2014-11-28 11:23:44', '0', '质量', '4', '0');

-- ----------------------------
-- Table structure for voc_word_expressions
-- ----------------------------
DROP TABLE IF EXISTS `voc_word_expressions`;
CREATE TABLE `voc_word_expressions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `audit_status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `voc_word_catagory` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rqtg6on0pqfyryf3igdegv856` (`voc_word_catagory`),
  CONSTRAINT `FK_rqtg6on0pqfyryf3igdegv856` FOREIGN KEY (`voc_word_catagory`) REFERENCES `voc_word_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_word_expressions
-- ----------------------------
INSERT INTO `voc_word_expressions` VALUES ('1', '0', '2014-11-28 11:24:01', '0', '产品很好', '0', '6');
INSERT INTO `voc_word_expressions` VALUES ('2', '0', '2014-11-28 11:24:11', '0', '产品很棒', '0', '6');
INSERT INTO `voc_word_expressions` VALUES ('3', '0', '2014-11-28 11:24:22', '0', '效果不错', '0', '8');
INSERT INTO `voc_word_expressions` VALUES ('4', '0', '2014-11-28 11:24:38', '0', '比想象的好', '0', '8');
INSERT INTO `voc_word_expressions` VALUES ('5', '0', '2014-11-28 11:25:15', '0', '态度一般', '0', '10');
INSERT INTO `voc_word_expressions` VALUES ('6', '0', '2014-11-28 11:25:29', '0', '态度恶劣', '0', '13');
INSERT INTO `voc_word_expressions` VALUES ('7', '0', '2014-11-28 11:25:38', '0', '产品一般', '0', '9');
INSERT INTO `voc_word_expressions` VALUES ('8', '0', '2014-11-28 11:25:45', '0', '质量一般', '0', '11');
INSERT INTO `voc_word_expressions` VALUES ('9', '0', '2014-11-28 11:25:52', '0', '服务一般', '0', '10');
INSERT INTO `voc_word_expressions` VALUES ('10', '0', '2014-11-28 11:26:05', '0', '产品垃圾', '0', '12');
INSERT INTO `voc_word_expressions` VALUES ('11', '0', '2014-11-28 11:26:10', '0', '产品不行', '0', '12');
INSERT INTO `voc_word_expressions` VALUES ('12', '0', '2014-11-28 11:26:17', '0', '没想象的好', '0', '12');
INSERT INTO `voc_word_expressions` VALUES ('13', '0', '2014-11-28 11:26:25', '0', '比想象中差', '0', '12');
INSERT INTO `voc_word_expressions` VALUES ('14', '0', '2014-11-28 11:26:38', '0', '服务态度差', '0', '13');
INSERT INTO `voc_word_expressions` VALUES ('15', '0', '2014-11-28 11:26:47', '0', '质量很差', '0', '14');
INSERT INTO `voc_word_expressions` VALUES ('16', '0', '2014-11-28 11:27:00', '0', '次品', '0', '14');

-- ----------------------------
-- Table structure for voc_work_order
-- ----------------------------
DROP TABLE IF EXISTS `voc_work_order`;
CREATE TABLE `voc_work_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_reason` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voc_work_order
-- ----------------------------

-- ----------------------------
-- Table structure for voc_work_order_type
-- ----------------------------
DROP TABLE IF EXISTS `voc_work_order_type`;
CREATE TABLE `voc_work_order_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
-- Table structure for wcc_activities
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
-- Table structure for wcc_answer
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_answer
-- ----------------------------

-- ----------------------------
-- Table structure for wcc_autokbs
-- ----------------------------
DROP TABLE IF EXISTS `wcc_autokbs`;
CREATE TABLE `wcc_autokbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
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
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_r78vo8pbamttnjttx102er2q` (`platform_user`),
  CONSTRAINT `FK_r78vo8pbamttnjttx102er2q` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_autokbs
-- ----------------------------
INSERT INTO `wcc_autokbs` VALUES ('1', '1', 'admin', '您也好', '2014-11-28 11:20:05', null, '0', '你好', '0', null, '0', '0', '您好~有什么可以帮您', '0', '1');
INSERT INTO `wcc_autokbs` VALUES ('6', '1', 'admin', '久科，上海久科', '2014-11-28 11:31:10', null, '0', '久科', '0', '1', '0', '0', '欢迎来到久科', '0', '1');
INSERT INTO `wcc_autokbs` VALUES ('7', '1', 'admin', 'wcc 你不悔的选择', '2014-11-28 11:33:55', null, '0', 'wcc', '0', '6', '0', '0', 'wcc', '0', '1');

-- ----------------------------
-- Table structure for wcc_award_info
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
  `win_rate` double NOT NULL,
  `lottery_activity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7exhwewph0g1fpv5uu39fc5g3` (`lottery_activity`),
  CONSTRAINT `FK_7exhwewph0g1fpv5uu39fc5g3` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_award_info
-- ----------------------------
INSERT INTO `wcc_award_info` VALUES ('1', '恭喜您获得山寨手机一步', '1', 'iPhone6s', '1', null, '1', '0', '10', '3');
INSERT INTO `wcc_award_info` VALUES ('2', '恭喜您获得山地车', '2', '山地车', '5', null, '1', '0', '10', '3');
INSERT INTO `wcc_award_info` VALUES ('3', '恭喜你获得洗衣机一台', '3', '洗衣机', '5', null, '1', '0', '10', '3');
INSERT INTO `wcc_award_info` VALUES ('4', '恭喜你获得电风扇一台', '4', '电风扇', '5', null, '1', '0', '10', '3');
INSERT INTO `wcc_award_info` VALUES ('5', '恭喜您获得扑克牌', '5', '扑克牌', '5', null, '1', '0', '10', '3');
INSERT INTO `wcc_award_info` VALUES ('6', '再来一次把~~', '6', '再来一次', '5', null, '1', '0', '40', '3');

-- ----------------------------
-- Table structure for wcc_chat_recourds
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
-- Table structure for wcc_comment
-- ----------------------------
DROP TABLE IF EXISTS `wcc_comment`;
CREATE TABLE `wcc_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_comment
-- ----------------------------

-- ----------------------------
-- Table structure for wcc_community
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
-- Table structure for wcc_content
-- ----------------------------
DROP TABLE IF EXISTS `wcc_content`;
CREATE TABLE `wcc_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `click_count` int(11) NOT NULL,
  `content` varchar(4000) NOT NULL,
  `content_url` varchar(4000) DEFAULT NULL,
  `insert_time` datetime NOT NULL,
  `is_check` tinyint(1) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_prize_commit` tinyint(1) DEFAULT NULL,
  `is_visible` tinyint(1) DEFAULT NULL,
  `praise_count` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ch8ik9f377armnv1a4ryi9deg` (`title`),
  KEY `FK_o3adxukfkj73wjpb18ceq8pnb` (`platform_user`),
  CONSTRAINT `FK_o3adxukfkj73wjpb18ceq8pnb` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_content
-- ----------------------------
INSERT INTO `wcc_content` VALUES ('1', '0', '一次抽奖的活动', 'www.test.com', '2014-11-28 10:10:37', '1', '1', '1', '1', '0', '抽奖活动', '1', '0', '1');
INSERT INTO `wcc_content` VALUES ('2', '0', '一次问卷的活动', 'www.test.com', '2014-11-28 10:11:08', '1', '1', '1', '1', '0', '问卷活动', '1', '0', '1');

-- ----------------------------
-- Table structure for wcc_friend
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
  PRIMARY KEY (`id`),
  KEY `FK_lr4j1sjiyit93ejkok6plowjx` (`organization`),
  KEY `FK_1s8ft7v6f5oo7irh2390jxc0v` (`platform_user`),
  KEY `FK_ju7bbqmvx7fc26xor78kifna` (`wgroup`),
  CONSTRAINT `FK_1s8ft7v6f5oo7irh2390jxc0v` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `FK_ju7bbqmvx7fc26xor78kifna` FOREIGN KEY (`wgroup`) REFERENCES `wcc_group` (`id`),
  CONSTRAINT `FK_lr4j1sjiyit93ejkok6plowjx` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_friend
-- ----------------------------
INSERT INTO `wcc_friend` VALUES ('1', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMdHibtalHykhuP1mcEMjBibUGAibuN10VxaibovV0fhjyfqzYy7NwGliaI8puNZh1FLfOef5ibMO4Kd0TBQ/0', '2014-11-28 10:07:23', '1', '1', null, 'jade', 'of9GTjns2okJ2iIOVzobmZT2GxIA', '上海', null, null, '1', null, '2014-09-04 10:40:22', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('2', '中国湖南长沙', '长沙', '中国', '0', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLCoUY7GFqHLGHjXDFelL08V7wwW6zrVRE04CYutPGbib8wtIWJFLB3OCs7U1Mm6qWicWUtZvIFcSseg/0', '2014-11-28 10:07:23', '1', '1', null, '%E6%BD%87%E6%B9%98%E5%A4%9C%E9%9B%A8', 'of9GTjmwRiOHROK5zBYV3NAEMU68', '湖南', null, null, '0', null, '2014-07-09 13:34:16', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('3', '中国福建泉州', '泉州', '中国', '0', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLC5yUnsUMaxbrmZyTHkxgDRjkyaBuQicOiaGwicmeHUS9A5XAvPhYlpxbNSgXibHuSA4YvJVzRVnLU9jg/0', '2014-11-28 10:07:23', '1', '1', null, 'Allure+Love%E2%9C%88%EF%B8%8F%E2%98%81%EF%B8%8F', 'of9GTjr2180lFkre3L3NRxMIFG1Q', '福建', null, null, '0', null, '2014-04-02 00:29:49', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('4', '中国北京朝阳', '朝阳', '中国', '0', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLDWwIzVLhX2OJSYn0PsI9ZVfblvx6otS53uia2Tgz7K4BDeFXCSgL4c15QlytYRZgiabicWcnu0aic9rg/0', '2014-11-28 10:07:23', '1', '1', null, '%EE%8C%AE%E6%B3%A2%E5%A6%9E+%E2%99%A5', 'of9GTjuMm8H9lNBeH50jDIoLyxdk', '北京', null, null, '1', null, '2014-11-25 12:12:42', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('5', '中国上海', '', '中国', '0', 'http://wx.qlogo.cn/mmopen/PiajxSqBRaELamG5hMBB60ElSzcNSRQ3dgxALzpcOydDmpDxkufqv0FZjLJ2QV5m3ia6gBAk77ZoDd6FoxUcfp3g/0', '2014-11-28 10:07:23', '1', '1', null, 'MU+%F0%9F%98%92', 'of9GTjkfPBUFIpRAynDA3jDj41dk', '上海', null, null, '1', null, '2014-10-17 14:31:27', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('6', '中国上海徐汇', '徐汇', '中国', '0', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLDyF3ehZbZvBtY2rRuqeuzgjdS3qDrXxcrFA8IIZOKWby60BibaxOp9XJgDQjxZTFBy44cVbqqLbbw/0', '2014-11-28 10:07:24', '1', '1', null, '%E6%B2%AA%E4%B8%8A%E6%B5%B7%E9%A3%8E', 'of9GTjvUFwDVGu07gl4kFv3_nNQY', '上海', null, null, '0', null, '2014-04-24 16:21:56', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('7', '中国上海杨浦', '杨浦', '中国', '0', 'http://wx.qlogo.cn/mmopen/PiajxSqBRaEI2nbThF45ER6Sqb8LloW9biaaKnZdmA0PwbvmYSBRXWLKJgA37VtbjdziaibH9BtMZfCRIUv6zfjEbQ/0', '2014-11-28 10:07:24', '1', '1', null, '%E4%BB%A3%E7%A0%81%E5%A6%82%E8%AF%97', 'of9GTjjS1Uumu4_rGFpk3j6EcHYs', '上海', null, null, '0', null, '2014-03-20 17:56:38', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('8', '中国上海金山', '金山', '中国', '0', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM7hreTKpqD5hYBF2llVy6Y1gVxzldKowKMlzvEfJdqVJXU5laIguAhHcKejjZPw2iafLB7RiciaLTpdjfd0l4LBFuF/0', '2014-11-28 10:07:24', '1', '1', null, '1234', 'of9GTjmbRKDReIZ4QISc2sBhmeSc', '上海', null, null, '0', null, '2014-11-20 13:50:09', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('9', '', '', '', '0', '', '2014-11-28 10:07:24', '1', '1', null, 'm111', 'of9GTjlSRWCrGxOuVK0fe5uDj7KI', '', null, null, '2', null, '2013-12-18 22:39:29', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('10', '', '', '', '0', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLAdTCkv5UqZdqe0xXRtyc549ySEB5yhnQmPQA3PEGeNdRbfeia5eP1aAQGb6nE6Oj8jSe6IoMNicyFw/0', '2014-11-28 10:07:24', '1', '1', null, '%E4%BD%A0%E5%A5%BD', 'of9GTjhmo71ZqsNIusjbY_LWTahA', '', null, null, '2', null, '2014-01-23 06:43:57', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('11', '', '', '', '0', '', '2014-11-28 10:07:24', '1', '1', null, '%E5%8F%96%E9%92%B1', 'of9GTjoUx6eotDxBxuJdOMUGRYw4', '', null, null, '2', null, '2013-12-19 17:05:29', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('12', '中国上海松江', '松江', '中国', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX8px2e2v4bERNFvia9EwdgibLzhBk03r9YE36pmrDn1SBs77td6y12icasW7Pw9AFkEccOoqCzTQQGBGZktco4cPYE/0', '2014-11-28 10:07:24', '1', '1', null, 'Run', 'of9GTjm8KYCWYY9R3cIX4x8jLRak', '上海', null, null, '0', null, '2014-08-09 17:55:12', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('13', '中国上海浦东新区', '浦东新区', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7IByO3PzywjxNVgiaSMS6JHDyRN2qnjeHeTWMnfGtW2smZhvW9HcbZgmk42I8ZRlWnEQL1UQbibibfz/0', '2014-11-28 10:07:24', '1', '1', null, '%E8%A2%81%E5%B0%8F%E6%96%90', 'of9GTjvu7d4ICxICREa7LmpPmgew', '上海', null, null, '1', null, '2013-12-23 15:56:06', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('14', '中国上海', '', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrIvaZiaN9OPYyQZibq2DnlanZHpjrUSMVcZPZWK8mTTQqyXq3a2VibiciajYFib2DNzj98D8xV6kX41FVR/0', '2014-11-28 10:07:24', '1', '1', null, 'Mona', 'of9GTjjAg2UtGdoWQqPHS_L_KkC0', '上海', null, null, '1', null, '2013-12-24 13:54:16', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('15', '中国湖南长沙', '长沙', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrAa1THpZqibTCicmrcsnia4zXDBQxPIsFz6nU82mUyC52wongfAHUtrZh5BHab0eWcickFphS7loXrKJ/0', '2014-11-28 10:07:24', '1', '1', null, '%E9%A3%8E', 'of9GTjgbP2ywUZGs0_QfmVo_zqqM', '湖南', null, null, '0', null, '2014-01-08 17:14:16', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('16', '中国河南濮阳', '濮阳', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrBIYkib2BcO2pDjkYBpjicv0ZxGOJnOiaAA773uVw6Pk1MYy1YKT9FbXwib5iaWichWnnFNtcry1iawEMrV/0', '2014-11-28 10:07:25', '1', '1', null, 'cedric', 'of9GTjsPSrLiw8OdENf2HmLQE1Dw', '河南', null, null, '0', null, '2014-08-23 16:34:58', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('17', '中国香港九龙城区', '', '中国香港', '0', 'http://wx.qlogo.cn/mmopen/PiajxSqBRaEIbGOTcME6SJWnUGgt6aYfxTL1S3UPTDphZFm57xoiatMRJxPwEdR3s0ibiaAxge1icebxRiaFznZ5fQYzGibfTWguOT0ia4IghuC4YjE/0', '2014-11-28 10:07:25', '1', '1', null, '%E4%B8%96%E7%95%8C%E4%B8%8A%E6%9C%80%E4%BC%9F%E5%A4%A7%E7%9A%84%E6%8E%A8%E9%94%80%E5%91%98', 'of9GTjoKLH97kGuSppboLTpW9_qM', '九龙城区', null, null, '0', null, '2014-04-04 19:31:05', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('18', '', '', '', '0', '', '2014-11-28 10:07:25', '1', '1', null, 'knight', 'of9GTjjaQ_H3GAJzbrBizXolwyIM', '', null, null, '2', null, '2013-12-17 10:49:05', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('19', '中国甘肃酒泉', '酒泉', '中国', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX8iaicQ9zK5D75aPTYthia3t6oYeanorhxlDtiaV8bIf4P4oMibcJbQaRFmft77wHiby3SQsICml8wia9y14EGY7kWQDOW/0', '2014-11-28 10:07:25', '1', '1', null, '%EF%B9%8F%E8%B5%B0%E5%88%B0%E4%BA%86%E6%9C%AB%E8%B7%AF+%E7%BB%88%E6%88%90%E9%99%8C%E8%B7%AF', 'of9GTjkqQTKdib_dIfhFp2d4yHKg', '甘肃', null, null, '1', null, '2014-05-05 02:01:24', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('20', '蒙古', '', '蒙古', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrF1bj3VTaYKuV6tQaTtS7pb9KLoibkibMQ7kLea5jpOZpzdMLKczW5nqO7ciaKLksCTEefQNf9Wu3yS/0', '2014-11-28 10:07:25', '1', '1', null, '%E4%B8%8A%E5%96%84%E5%8D%87%E5%93%A5', 'of9GTjtdkdjSVsX4n6SR2jFwhMfs', '', null, null, '0', null, '2014-07-09 19:21:27', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('21', '中国新疆昌吉', '昌吉', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7H4dU9z8GerG3QYkGl8iaz3MufBn3VQl2JFh7pHJKG31Df3yneLUX1ib5lic68zwiaZiaW7kTTYABgDibD/0', '2014-11-28 10:07:25', '1', '1', null, '%E5%A6%82%E6%9E%9C%E7%88%B1%E4%BD%A0.%E6%88%91%E4%BC%9A%E7%9A%84', 'of9GTjl2C4Ipd2rZUc069RP-H71k', '新疆', null, null, '0', null, '2013-12-15 17:14:10', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('22', '中国上海', '', '中国', '0', '', '2014-11-28 10:07:25', '1', '1', null, '%E6%B5%B7%E9%A3%8E', 'of9GTjhqcbSbqCzDyYYhfCjgqVgk', '上海', null, null, '2', null, '2014-04-24 16:22:49', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('23', '中国湖北随州', '随州', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrIiciaxAJK1XibRn93icvDypucR3OaOSuRJDiaz8RfKzicKuSPXciaRBzRrPMyRZop1d47Lt8vTVoIic4Dun/0', '2014-11-28 10:07:25', '1', '1', null, 'sea.xiang', 'of9GTjhecjc-jEt4w0iKRZ1krYbY', '湖北', null, null, '0', null, '2014-07-10 15:01:48', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('24', '中国江苏南京', '南京', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7LsS6BwKLILcyap8JDSBrOyrWUdkhtU7aMyIGqazpyiaeCJbVOMkNib0x9yGCick4ypoicuPbNXPAOiaK/0', '2014-11-28 10:07:26', '1', '1', null, '%E5%B9%B8%E7%A6%8F%E6%9C%89%E7%BA%A6+%E6%98%8E%E6%9C%88', 'of9GTjoKe3iDD73hMt5AzVwzn2iw', '江苏', null, null, '1', null, '2014-03-13 17:49:35', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('25', '中国北京海淀', '海淀', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrAlmxlibz2InuILtIj1EzvbpaHz522UqnHicODbu20dN5QcHkOGtOnshomzUI63Orrd0SXicAeDCicYd/0', '2014-11-28 10:07:26', '1', '1', null, '%E9%97%B5%E6%AD%A3%E9%81%93', 'of9GTjirwxDKIMw-ovGRFxVTxQh0', '北京', null, null, '0', null, '2014-01-18 12:17:45', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('26', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7JuoKUWCRqW1on1Y9uZLQk6gjbPJN2MfORjgbXJ70p4oyQ9GcNMBMCUSehib4eQRECNGkfMND0mhx/0', '2014-11-28 10:07:26', '1', '1', null, '%E9%9D%92%E6%98%A5%E6%97%A0%E6%82%94', 'of9GTjkeB9A2uhayC7uDSZAR8piQ', '上海', null, null, '1', null, '2013-12-25 18:00:10', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('27', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrM8IqU3hhibIlgpaXkj52CVZPRFLQggOn1tIaQmwIdictXOmDNZicR8JRvSoN3ZnMPgsPdONnCriaBdv/0', '2014-11-28 10:07:26', '1', '1', null, 'Flora', 'of9GTjofJ1v-yg0zfxSsnkf-7064', '上海', null, null, '1', null, '2014-10-18 11:46:45', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('28', '澳大利亚北部地区达尔文', '达尔文', '澳大利亚', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvRyVCOMmeZyT700PyAgVJO4PVsd5O1bzIE8NANJjN8ibWjqIloQicS7oZdeSgWibK3w1h8TO3aC9pLy9v6icGqiao3Uq/0', '2014-11-28 10:07:26', '1', '1', null, '%E8%93%93%E8%95%BE', 'of9GTjq_jNCr9G8yW734k9mtipSY', '北部地区', null, null, '0', null, '2014-08-22 09:46:07', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('29', '中国上海浦东新区', '浦东新区', '中国', '0', 'http://wx.qlogo.cn/mmopen/OWMzmr9GyzH0espWo64KrjibCeOOkMUc18VS1bKWXwLYRKZQrbM7mibwHq1UMScgN3Kk3lwzUAibSGspGufBObfoWraIbPaBuMu/0', '2014-11-28 10:07:26', '1', '1', null, '%E5%88%98%E6%99%BA', 'of9GTjjdmCUXD_LcXHf56qO1YM2U', '上海', null, null, '0', null, '2013-12-23 15:56:27', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('30', '中国新疆克孜勒苏', '克孜勒苏', '中国', '0', 'http://wx.qlogo.cn/mmopen/VFtzTjDMJyWPHQv2pWoyAPrSadSBSA6bdFRhSr802DandAR6Tm3picKibJ47319wJ2CrwN2AFLhKibatIZbZjDZGVe9QC8xw9US/0', '2014-11-28 10:07:26', '1', '1', null, 'K++++G', 'of9GTjkIQWyhtj0A0aI8hG2tPN-Q', '新疆', null, null, '0', null, '2014-02-10 22:45:11', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('31', '中国河北保定', '保定', '中国', '0', 'http://wx.qlogo.cn/mmopen/OWMzmr9GyzH0espWo64KruJiazVFKIW5kxKSKv78d7TtHQlHTqaLkcuSZKe61GG0VlLpabhyyyB9icALp1VwNage7GF5aVXJZC/0', '2014-11-28 10:07:26', '1', '1', null, '%E9%93%AD', 'of9GTjrrL7BjKeCR3M-PaemBJPAY', '河北', null, null, '2', null, '2014-01-22 15:08:21', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('32', '中国广东深圳', '深圳', '中国', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX9JlAwXM5pRb59IeGee0Bm6YLkpdevdvTn2vJz45K4vlZ1zVC4WxSLCnPXIoT3SeL2HB6pibOORKTg0QP9p6PjkO/0', '2014-11-28 10:07:26', '1', '1', null, '%E8%90%A7%E6%B9%98', 'of9GTjkcKYsL8jRaRkzdbbgesFCk', '广东', null, null, '1', null, '2014-01-14 14:13:51', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('33', '希腊', '', '希腊', '0', 'http://wx.qlogo.cn/mmopen/mg17NE1abT9uVFPaVia8KCjrSEtcWgVyyLUgbib0puKn7OL5ZxiadPtmMatE4lgBCUWdBGcDqruSWRPs9cB0KDsGiaHa9wEG2PBs/0', '2014-11-28 10:07:26', '1', '1', null, '%E2%9C%A8%F0%9F%92%9D%E6%96%87%E5%90%9B%F0%9F%91%91%E2%9C%A8', 'of9GTjoXDoQfQ2rv4yDQ0O7DvsT0', '', null, null, '1', null, '2014-04-23 18:40:59', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('34', '', '', '', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX9iaQzWibfV5BXw4XiajT7szM2ibMicAkHhybacJibnicDayicibaD9Rw6goR9pax4avVnptQqBPY0h866a57A2ogr5U0kBr/0', '2014-11-28 10:07:27', '1', '1', null, '%E8%8B%8F', 'of9GTjjT6WvV67O-kLMPwHtnax2s', '', null, null, '2', null, '2014-10-24 18:14:54', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('35', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTqkuhUhAbZnjPI773eagpvoJms9Qn6XdvpY8efIv0wpd3wPS2JfZrhhhOP7JKVxECQE8iccELs2buyATfEP0W2N/0', '2014-11-28 10:07:27', '1', '1', null, '%E8%89%BE%E5%8A%9B', 'of9GTjobNs5wb7vkeXbBJSlP-vEU', '上海', null, null, '0', null, '2014-06-26 15:44:15', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('36', '中国台湾嘉义县', '', '中国台湾', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrB3xR87cz8L6Wy5NWSduAq2DHplDllPrsG8s4gWu741lMQ51dcibJDpvWhib8CYegkNBE8cOITR6ks/0', '2014-11-28 10:07:27', '1', '1', null, '%E5%AE%89%E5%A6%AE', 'of9GTjjtBp1ge80ntUatcG4Dpm30', '嘉义县', null, null, '1', null, '2014-01-05 12:12:22', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('37', '中国重庆城口', '城口', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfQtVFZyrymBoQ3ibBr0NEUdyeia4PEVEjFvNMclZXn3yMMYrM3T3o8LPCOdF3sdQyv5GKJVyvHB2fykl4x5HLkJd/0', '2014-11-28 10:07:27', '1', '1', null, '%E3%82%9E%E6%8A%B9%E4%B8%8D%E5%8E%BB%E7%9A%84%E4%BC%A4', 'of9GTjllOOGBsD5C0jDx0H6VWbuY', '重庆', null, null, '1', null, '2014-03-13 05:16:02', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('38', '中国上海浦东新区', '浦东新区', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrKwcNVCuXjRia74HcFnibfzaG12GcwHySOhwcIFBwEJOVCPuYfZD5kwqlt1UDKe407lQvKgo61iaicsQ/0', '2014-11-28 10:07:27', '1', '1', null, 'FT-%E5%86%AF%E9%9F%AC', 'of9GTjpv4Ov3CjvU1E9GbZ7Uw-hw', '上海', null, null, '0', null, '2014-04-08 09:26:39', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('39', '中国上海松江', '松江', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvQib09ZxY0jLsNvgdEYkVqjhqz4V1K4H2icZnvaM3saibuTHKFtJqJTial9KUhj5evUlrPMxsAIc6NXJaGDdwBQbBkd/0', '2014-11-28 10:07:27', '1', '1', null, '%E4%B8%8B%E5%BC%A6%E6%9C%88', 'of9GTjqwGicFP-KrrKKPM3GccxpY', '上海', null, null, '1', null, '2014-08-22 18:06:52', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('40', '中国上海闵行', '闵行', '中国', '0', '', '2014-11-28 10:07:27', '1', '1', null, '%E4%BD%95%E5%8F%91%E6%96%8C', 'of9GTjtSWWOo9IYMiAyKB7O0Kr-g', '上海', null, null, '0', null, '2014-01-14 14:05:51', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('41', '', '', '', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX8px2e2v4bERArLAnag65iaNQLyjcCTPUeukY5VtWiclbJrE69DOt9FsfpogXZpOYesVC8XtcXQ7oPBJloR224sRic/0', '2014-11-28 10:07:27', '1', '1', null, '%E8%91%B1%E5%A4%B4%E7%8C%AB', 'of9GTjk2ADDYJGYrdiKH8WGtmkAo', '', null, null, '2', null, '2014-07-08 10:44:45', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('42', '中国新疆克孜勒苏', '克孜勒苏', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTdeexjApb3UPftEhgqJW730jMiaSjgUPsCibcIy2G1O9329Eyh9YV0AKaYkCtMh0prTftdUusxTiayQTlGt6Ychrf/0', '2014-11-28 10:07:27', '1', '1', null, 'KG', 'of9GTjtXwW1x1X3WvCq95bKWLuXM', '新疆', null, null, '1', null, '2014-02-13 22:39:25', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('43', '中国上海徐汇', '徐汇', '中国', '0', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM7fiauHkAOzGRfhibsO6PwAmJNTp7JAIlSribgNjeSs5ayvUIeEema9npad0nUicWsB1fumgP0R0v46L3bATuC4Kn3WpaKfrtP3NSM/0', '2014-11-28 10:07:27', '1', '1', null, 'Betty', 'of9GTjnGciYUUAWMY3J2zoN2lT8A', '上海', null, null, '1', null, '2014-07-24 16:16:07', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('44', '中国上海', '', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrOXVMyV0u8M7icuCdLlAibjlmAwm8iafnQ1mSaNx4U47kR5xYt5XSiauHYKSYp4TwtukEhW03WABWUeU/0', '2014-11-28 10:07:28', '1', '1', null, '%E5%A4%8F%E9%9B%A8%E5%87%89', 'of9GTjvwGAXMZL7SL6YKVXwSVSMg', '上海', null, null, '0', null, '2014-07-02 14:58:55', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('45', '美国', '', '美国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7Jpp1VlbhTDfugSOkqczCslFKJp9WUshdJ539RvcLLsf97ib943oHxt1JpwM24goHhX6gX5BLYQyG/0', '2014-11-28 10:07:28', '1', '1', null, '%E6%9C%88%E6%BB%A1%E8%A5%BF%E6%A5%BC', 'of9GTjmQeLw-eNtq11P96C2Zj-6c', '', null, null, '0', null, '2014-08-22 11:46:45', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('46', '泽西岛', '', '泽西岛', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX8px2e2v4bERK5m0ZUW6c7IFFhnOSqO6pBC9JaKvDsicJ43CdS0TG5BTI8OFFr32ngVPCicibicj3mWhvDbG3D7f9uf/0', '2014-11-28 10:07:28', '1', '1', null, '%E8%A5%BF%E8%B4%9D', 'of9GTjjDRBC-WlFDN0XgbCABVNlc', '', null, null, '0', null, '2014-06-26 13:54:51', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('47', '中国陕西汉中', '汉中', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7GAe1HDrTiaP7NOU8jyadqibzrOuclVqc5O3gKlRelmaKHkI133LI90I6uetDgFX604tpnSvTJ1NJd/0', '2014-11-28 10:07:28', '1', '1', null, 'setsuna', 'of9GTjrwiIC4MfI07biBh0Puj6LI', '陕西', null, null, '0', null, '2013-09-10 11:07:03', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('48', '澳大利亚', '', '澳大利亚', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMebGf6w7Gs9BoJgmvpGygsPJLekoFNQjUnB05NL5z9kk2LcDrh5nGzZXtQJUVndlXR67KknibZ7ZsLRntmXHkuwS/0', '2014-11-28 10:07:29', '1', '1', null, 'winnie', 'of9GTjiz0QYP8gNDhtJAKuLW-Xsg', '', null, null, '1', null, '2014-03-31 12:01:49', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('49', '中国北京朝阳', '朝阳', '中国', '0', 'http://wx.qlogo.cn/mmopen/nImAPdJIyMsk1DLbHv4YgjnLY1y5cias96HqE0rHqKdibDHAnUnLj0e2xXI6daUpN2QZ8xydDYjJFxxnP5jxeRXnMEvnpFDwWk/0', '2014-11-28 10:07:29', '1', '1', null, '%E4%BD%A0%E6%98%AF%E6%88%91%E7%9A%84%E5%88%9B%E5%8F%AF%E8%B4%B4', 'of9GTjiuabyUxn8TvdGiYQpmWzuU', '北京', null, null, '1', null, '2014-02-10 20:48:26', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('50', '中国上海', '', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrHRzhjgqJ5tabyfVqqFyxPS6ZIO1Kt4bwLsSt0SRUANcC4v9UBGDup8BwNn2KzF0OaE7iaI8uR6T4/0', '2014-11-28 10:07:29', '1', '1', null, '%E8%8E%89%E7%90%BC', 'of9GTjpQyQqdOm356yx1s4kJbGQs', '上海', null, null, '2', null, '2013-12-23 16:17:27', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('51', '中国上海浦东新区', '浦东新区', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTLabIRNp2VSrVMalC2SOR1eiagt2hIbCrrkWZ9EiaAj0gBVaztNiaCFktoWSmubzSxkGssJfKw39pxIBzgqvyGfdH/0', '2014-11-28 10:07:29', '1', '1', null, '%E9%84%A2%E6%81%A9%E6%9D%A5%EE%84%90John', 'of9GTjglrXCOrZPiBCXRv0hhbvV0', '上海', null, null, '0', null, '2014-08-05 18:18:17', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('52', '中国上海黄浦', '黄浦', '中国', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX8px2e2v4bERHEzJWPtlh8Y737EyW74KsmhatPxB1LNVogzb0J7AgkkR5enJSBxxkM6t8icztue7FWasE9s0o0lm/0', '2014-11-28 10:07:29', '1', '1', null, '%E4%B8%8D%E6%8A%BC%E8%BD%A6or%E6%8A%BC%E8%BD%A6', 'of9GTjiRpaOz4e_uKjbDL-nPtVxI', '上海', null, null, '0', null, '2014-08-30 12:38:36', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('53', '马尔代夫南蒂拉杜马蒂', '', '马尔代夫', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMcOFIzIOjdjibjGcl8PUGiaJ5g0NvdWfDUbUrgwYe6BfLwY2o0t7aSSjQLDBg2Zaia9V1c6YXSA9qeUBRvDYTPXu5q/0', '2014-11-28 10:07:29', '1', '1', null, '*%E4%BC%9A%E7%AC%91%E7%9A%84%E7%9C%BC%E7%9D%9B%E2%9C%94', 'of9GTjhH-B250Pkwq8iNb_k7smGw', '南蒂拉杜马蒂', null, null, '0', null, '2014-01-04 02:55:34', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('54', '中国上海', '', '中国', '0', 'http://wx.qlogo.cn/mmopen/22YD2oBcVUYialQbib8QD7CJVZABu8OnPKHMEnQWQVrTXUV08F2SNZiaNSiaMJiaN4GcR4D2pmRIicbaMfLU3vKHHN0DfVN8cZb2xia/0', '2014-11-28 10:07:30', '1', '1', null, '%E7%99%BD%E7%9A%AE%E6%9D%BE', 'of9GTjuoZXBNh5oo2NDcMOma82V8', '上海', null, null, '1', null, '2014-08-27 09:25:07', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('55', '中国湖北武汉', '武汉', '中国', '0', 'http://wx.qlogo.cn/mmopen/A9W46sgLQX8px2e2v4bERFzUCRomSyCslo1zNYuUvaqX8ibLsSAxBE1Yum1gRyH1jAsyiahQodS9h8s34TqpXUXqZq4IzBUQibm/0', '2014-11-28 10:07:30', '1', '1', null, '%EF%BC%81%EF%BC%81%EF%BC%81', 'of9GTjq_RgyIPrjgR_rWCFhtZh-4', '湖北', null, null, '0', null, '2014-07-09 21:14:52', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('56', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/1cwAlbjgbMfSobiaVEbsW7DO0U0RPunUsm3KLDoas0x8hiatYHDIIibHxD2mwsGTDrG9bibqetKvYsBib5eiclR2sBkibicI7zTtyWCE/0', '2014-11-28 10:07:30', '1', '1', null, 'ronie', 'of9GTjlDu59SaOMVjU_3fjQDklr8', '上海', null, null, '0', null, '2014-01-14 10:55:02', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('57', '吉尔吉斯斯坦', '', '吉尔吉斯斯坦', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrBxOypYRPRe5pNib9MoRyT3dNBfQbu7GhIJYCgSR3EEWVXz5zn6FGvvfL6JKYagFE2JwsP7aO4Piau/0', '2014-11-28 10:07:30', '1', '1', null, '%E0%A6%90%E2%9C%8E%E2%84%B3%E0%B9%93%E2%82%AF%E3%8E%95%EF%B9%8F%E2%9C%8DkG%EE%8C%AE+%CE%B6%E0%B8%B1%CD%A1%E2%9C%BE', 'of9GTjqyiFgQCexBSvku5qp_KNUA', '', null, null, '0', null, '2014-03-07 01:19:55', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('58', '中国北京东城', '东城', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTwZm4QWeOMJPUKn6Cp0VCicPicogWng2RZYIXhxic046v7SA4InJ7vDyKzicdiazs5uyehEcsiabKLRqMmBRic663ob7b/0', '2014-11-28 10:07:30', '1', '1', null, '%E7%BA%AF%E7%BE%8E', 'of9GTjoBG6_BxXrbPVXQt_TdtsRo', '北京', null, null, '1', null, '2013-12-10 23:08:02', null, '0', null, '1', null);
INSERT INTO `wcc_friend` VALUES ('59', '中国上海闵行', '闵行', '中国', '0', 'http://wx.qlogo.cn/mmopen/4O7qWGZMRvTL1QQYynDMrMH5x9WqicQl451q2CljzyuqP8rsLo27rXzjOO9F3zjDd2ODWxHnKicib8kq1mjibT33Jn5dib7nzsgvd/0', '2014-11-28 10:07:30', '1', '1', null, '%E6%97%A0', 'of9GTjiKeVCenC2mmnYvAQ9c_9O4', '上海', null, null, '0', null, '2014-11-03 17:37:47', null, '0', null, '1', null);

-- ----------------------------
-- Table structure for wcc_group
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
  CONSTRAINT `FK_eemvihqtaxxdftpp4tf1gmppw` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_group
-- ----------------------------
INSERT INTO `wcc_group` VALUES ('1', '0', '2014-11-28 10:07:21', '1', '默认组', '0', '1');
INSERT INTO `wcc_group` VALUES ('2', '0', '2014-11-28 10:07:21', '1', '屏蔽组', '0', '1');
INSERT INTO `wcc_group` VALUES ('3', '0', '2014-11-28 10:07:21', '1', '星标组', '0', '1');
INSERT INTO `wcc_group` VALUES ('4', '0', '2014-11-28 10:07:21', '1', '赠品驱动组', '0', '1');
INSERT INTO `wcc_group` VALUES ('5', '0', '2014-11-28 10:07:22', '1', 'groupNameTest', '0', '1');
INSERT INTO `wcc_group` VALUES ('6', '0', '2014-11-28 10:07:22', '1', '活动热衷组', '0', '1');
INSERT INTO `wcc_group` VALUES ('7', '0', '2014-11-28 10:07:22', '1', 'rrrtest', '2', '1');
INSERT INTO `wcc_group` VALUES ('8', '0', '2014-11-28 10:07:22', '1', '分组不能使用', '0', '1');
INSERT INTO `wcc_group` VALUES ('9', '0', '2014-11-28 10:07:22', '1', '群发测试', '0', '1');
INSERT INTO `wcc_group` VALUES ('10', '0', '2014-11-28 10:07:22', '1', '11', '0', '1');
INSERT INTO `wcc_group` VALUES ('11', '0', '2014-11-28 10:07:22', '1', '<22>', '0', '1');
INSERT INTO `wcc_group` VALUES ('12', '0', '2014-11-28 10:07:22', '1', '女组', '0', '1');
INSERT INTO `wcc_group` VALUES ('13', '0', '2014-11-28 10:07:22', '1', '天龙八部', '0', '1');
INSERT INTO `wcc_group` VALUES ('14', '0', '2014-11-28 10:07:22', '1', '水电费水电费', '0', '1');
INSERT INTO `wcc_group` VALUES ('15', '0', '2014-11-28 10:07:22', '1', '测试分组', '0', '1');
INSERT INTO `wcc_group` VALUES ('16', '0', '2014-11-28 10:07:22', '1', '获取分组', '0', '1');
INSERT INTO `wcc_group` VALUES ('17', '0', '2014-11-28 10:07:22', '1', '群发组', '0', '1');
INSERT INTO `wcc_group` VALUES ('19', '0', '2014-11-28 10:07:22', '1', 'devs', '1', '1');
INSERT INTO `wcc_group` VALUES ('20', '0', '2014-11-28 10:07:22', '1', '回家', '0', '1');
INSERT INTO `wcc_group` VALUES ('21', '0', '2014-11-28 10:07:23', '1', '测试群发', '0', '1');
INSERT INTO `wcc_group` VALUES ('22', '0', '2014-11-28 10:07:23', '1', 's', '0', '1');
INSERT INTO `wcc_group` VALUES ('23', '0', '2014-11-28 10:07:23', '1', '1111111113223', '0', '1');
INSERT INTO `wcc_group` VALUES ('24', '0', '2014-11-28 10:07:23', '1', 'addd', '1', '1');
INSERT INTO `wcc_group` VALUES ('25', '0', '2014-11-28 10:07:23', '1', '11111', '0', '1');
INSERT INTO `wcc_group` VALUES ('26', '0', '2014-11-28 10:07:23', '1', 'ceshiceshi', '0', '1');
INSERT INTO `wcc_group` VALUES ('27', '0', '2014-11-28 10:07:23', '1', '久科总公司', '0', '1');
INSERT INTO `wcc_group` VALUES ('28', '140', '2014-11-28 10:34:48', '1', 'addtest123', '1', '1');

-- ----------------------------
-- Table structure for wcc_group_message
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
-- Table structure for wcc_group_mess_friend
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
-- Table structure for wcc_inter_active_app
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
-- Table structure for wcc_leavemsg_record
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
-- Table structure for wcc_lottery_activity
-- ----------------------------
DROP TABLE IF EXISTS `wcc_lottery_activity`;
CREATE TABLE `wcc_lottery_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_end_info` varchar(255) DEFAULT NULL,
  `activity_introduction` varchar(4000) NOT NULL,
  `activity_name` varchar(400) NOT NULL,
  `activity_remark` varchar(255) DEFAULT NULL,
  `activity_type` int(11) DEFAULT NULL,
  `award_msg` varchar(255) DEFAULT NULL,
  `cost_point` int(11) DEFAULT NULL,
  `effective_number` int(11) DEFAULT NULL,
  `end_time` datetime NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visibale` tinyint(1) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `lottery_num` int(11) NOT NULL,
  `num_end_info` varchar(255) DEFAULT NULL,
  `repeat_award_reply` varchar(4000) NOT NULL,
  `start_time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  `visitor_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_lottery_activity
-- ----------------------------
INSERT INTO `wcc_lottery_activity` VALUES ('3', '活动已结束', '这是一次大转盘活动', '大转盘', null, '0', null, '0', '0', '2014-11-29 11:00:31', null, '1', '1', null, '3', '已满十八次...', '恭喜您未中奖...', '2014-12-05 11:00:26', '0', '0');

-- ----------------------------
-- Table structure for wcc_lottery_activity_platform_users
-- ----------------------------
DROP TABLE IF EXISTS `wcc_lottery_activity_platform_users`;
CREATE TABLE `wcc_lottery_activity_platform_users` (
  `lottery_activity` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`lottery_activity`,`platform_users`),
  KEY `FK_sf0wh7hmsr20hxu1ilivrf22` (`platform_users`),
  KEY `FK_mkbbdfu7wou7bhov8eng9nn8m` (`lottery_activity`),
  CONSTRAINT `FK_mkbbdfu7wou7bhov8eng9nn8m` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`),
  CONSTRAINT `FK_sf0wh7hmsr20hxu1ilivrf22` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_lottery_activity_platform_users
-- ----------------------------
INSERT INTO `wcc_lottery_activity_platform_users` VALUES ('3', '1');

-- ----------------------------
-- Table structure for wcc_materials
-- ----------------------------
DROP TABLE IF EXISTS `wcc_materials`;
CREATE TABLE `wcc_materials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `remake_url` varchar(4000) DEFAULT NULL,
  `resource_url` varchar(4000) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  `thumbnail_url` varchar(4000) DEFAULT NULL,
  `title` varchar(400) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url_boolean` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_materials
-- ----------------------------
INSERT INTO `wcc_materials` VALUES ('1', '内容', '2014-11-28 11:42:53', null, '', '', '', '', '', '', '1', null, '0');
INSERT INTO `wcc_materials` VALUES ('2', '内容2', null, null, null, null, null, null, null, null, '2', null, '0');
INSERT INTO `wcc_materials` VALUES ('3', '3', null, null, null, null, null, null, null, null, '3', null, '0');
INSERT INTO `wcc_materials` VALUES ('4', '4', null, null, null, null, null, null, null, null, '4', null, '0');
INSERT INTO `wcc_materials` VALUES ('5', '5', null, null, null, null, null, null, null, null, '5', null, '0');
INSERT INTO `wcc_materials` VALUES ('6', '6', null, null, null, null, null, null, null, null, '2', null, '0');
INSERT INTO `wcc_materials` VALUES ('7', '8', null, null, null, null, null, null, null, null, '3', null, '0');
INSERT INTO `wcc_materials` VALUES ('8', '9', null, null, null, null, null, null, null, null, '4', null, '0');
INSERT INTO `wcc_materials` VALUES ('9', '0', null, null, null, null, null, null, null, null, '5', null, '0');
INSERT INTO `wcc_materials` VALUES ('10', null, null, null, null, null, null, null, null, null, '6', null, '0');

-- ----------------------------
-- Table structure for wcc_membership_level
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
-- Table structure for wcc_menu
-- ----------------------------
DROP TABLE IF EXISTS `wcc_menu`;
CREATE TABLE `wcc_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `flag` bigint(20) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `menu_name` varchar(100) NOT NULL,
  `mkey` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `token` varchar(4000) DEFAULT NULL,
  `type` bigint(20) NOT NULL,
  `url` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_menu
-- ----------------------------
INSERT INTO `wcc_menu` VALUES ('1', null, '2014-11-24 13:53:52', '0', null, 'nineclient', null, '0', null, '0', null, '0', null, null);
INSERT INTO `wcc_menu` VALUES ('2', '这是一款产品', '2014-11-28 11:15:54', '0', '1', '产品介绍', 'key', '1', null, '1', null, '2', null, '0');
INSERT INTO `wcc_menu` VALUES ('3', 'wcc是款好产品', '2014-11-28 11:17:45', '0', '1', 'WCC', 'kkk', '2', null, '2', null, '2', null, '0');

-- ----------------------------
-- Table structure for wcc_message
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
-- Table structure for wcc_offc_ativity
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
-- Table structure for wcc_option
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
-- Table structure for wcc_plate
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
-- Table structure for wcc_platform_user
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
  PRIMARY KEY (`id`),
  KEY `FK_1g955iqh3jmgdfbq3dhri53h2` (`company`),
  CONSTRAINT `FK_1g955iqh3jmgdfbq3dhri53h2` FOREIGN KEY (`company`) REFERENCES `ump_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_platform_user
-- ----------------------------
INSERT INTO `wcc_platform_user` VALUES ('1', 'nine9client', 'wxe7684adbd962c3ec', 'a3f25224b48d19b8bf318195be6a1e23', null, '2014-11-28 10:07:19', '1', '1', null, null, '123', 'gh_61acc278f665', '久科', '0', null);

-- ----------------------------
-- Table structure for wcc_praise
-- ----------------------------
DROP TABLE IF EXISTS `wcc_praise`;
CREATE TABLE `wcc_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `type` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_praise
-- ----------------------------

-- ----------------------------
-- Table structure for wcc_qrcode
-- ----------------------------
DROP TABLE IF EXISTS `wcc_qrcode`;
CREATE TABLE `wcc_qrcode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` int(11) DEFAULT NULL,
  `code_id` varchar(4000) DEFAULT NULL,
  `code_url` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `expire_seconds` float NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_use` tinyint(1) DEFAULT NULL,
  `use_type` varchar(30) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_qrcode
-- ----------------------------

-- ----------------------------
-- Table structure for wcc_question
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
-- Table structure for wcc_questionnaire
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
-- Table structure for wcc_sncode
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
  `version` int(11) DEFAULT NULL,
  `award_info` bigint(20) DEFAULT NULL,
  `lottery_activity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s0moqts5m2x54u4vk1e4u9bgs` (`award_info`),
  KEY `FK_a7eck8jlj6wr4yqfoo2wvln7` (`lottery_activity`),
  CONSTRAINT `FK_a7eck8jlj6wr4yqfoo2wvln7` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`),
  CONSTRAINT `FK_s0moqts5m2x54u4vk1e4u9bgs` FOREIGN KEY (`award_info`) REFERENCES `wcc_award_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_sncode
-- ----------------------------
INSERT INTO `wcc_sncode` VALUES ('1', '1', null, '1', '765292fa6d114e1babedced0e34fc9eb', null, '0', '0', '1', '3');
INSERT INTO `wcc_sncode` VALUES ('2', '2', null, '1', 'af30aa689c2f4da7b0bbd92095dfa0dc', null, '0', '0', '2', '3');
INSERT INTO `wcc_sncode` VALUES ('3', '2', null, '1', '16991591bf4f479687f80bc37520d9e9', null, '0', '0', '2', '3');
INSERT INTO `wcc_sncode` VALUES ('4', '2', null, '1', '485c94ab289a422bbe728db98e4e4810', null, '0', '0', '2', '3');
INSERT INTO `wcc_sncode` VALUES ('5', '2', null, '1', '671ebd7c0b46467b9f0fd474bc9b5130', null, '0', '0', '2', '3');
INSERT INTO `wcc_sncode` VALUES ('6', '2', null, '1', 'f90340b9429d466abac0bf575a63b99c', null, '0', '0', '2', '3');
INSERT INTO `wcc_sncode` VALUES ('7', '3', null, '1', '680ea531861c4ecd80b68021176d9444', null, '0', '0', '3', '3');
INSERT INTO `wcc_sncode` VALUES ('8', '3', null, '1', '0ed902ca3f6f4d189aa2471464ea318b', null, '0', '0', '3', '3');
INSERT INTO `wcc_sncode` VALUES ('9', '3', null, '1', '51ada0707d20417490b3d49b98e4f412', null, '0', '0', '3', '3');
INSERT INTO `wcc_sncode` VALUES ('10', '3', null, '1', '17ac1ccbf0ce45a1abaa66c78c65b6c8', null, '0', '0', '3', '3');
INSERT INTO `wcc_sncode` VALUES ('11', '3', null, '1', '69b6e2e980804448b012edda25dfb946', null, '0', '0', '3', '3');
INSERT INTO `wcc_sncode` VALUES ('12', '4', null, '1', '5f71507282954a6cba5795fbd05db62f', null, '0', '0', '4', '3');
INSERT INTO `wcc_sncode` VALUES ('13', '4', null, '1', '975ea0979e0a4d248495ff88265c5b03', null, '0', '0', '4', '3');
INSERT INTO `wcc_sncode` VALUES ('14', '4', null, '1', '0d6b41b49df34dcb8f461ce948875a88', null, '0', '0', '4', '3');
INSERT INTO `wcc_sncode` VALUES ('15', '4', null, '1', '95b589f4370c4fcfa167afeb89e2baa0', null, '0', '0', '4', '3');
INSERT INTO `wcc_sncode` VALUES ('16', '4', null, '1', '6cb32eacd74a4ebe92fc262d5a54ff59', null, '0', '0', '4', '3');
INSERT INTO `wcc_sncode` VALUES ('17', '5', null, '1', '9ed65552402a4e4e9c200b1cb10513cb', null, '0', '0', '5', '3');
INSERT INTO `wcc_sncode` VALUES ('18', '5', null, '1', 'a885891f4f06493f8796df8a157516f0', null, '0', '0', '5', '3');
INSERT INTO `wcc_sncode` VALUES ('19', '5', null, '1', '207c9999173c4edbb176218b6632d793', null, '0', '0', '5', '3');
INSERT INTO `wcc_sncode` VALUES ('20', '5', null, '1', '1899e9bff0b04e5abe3c6c8afdc23b61', null, '0', '0', '5', '3');
INSERT INTO `wcc_sncode` VALUES ('21', '5', null, '1', 'd2206e01b9c04816a220310604005eea', null, '0', '0', '5', '3');
INSERT INTO `wcc_sncode` VALUES ('22', '6', null, '1', 'e3a63d328852478bb5da5c4ee9889b55', null, '0', '0', '6', '3');
INSERT INTO `wcc_sncode` VALUES ('23', '6', null, '1', 'a7c3351d2a9643358f86c74216bcf620', null, '0', '0', '6', '3');
INSERT INTO `wcc_sncode` VALUES ('24', '6', null, '1', '12bbb8645370422d96b7fcc68a40ca38', null, '0', '0', '6', '3');
INSERT INTO `wcc_sncode` VALUES ('25', '6', null, '1', '051589dc1c8443c79229ec83887c44f7', null, '0', '0', '6', '3');
INSERT INTO `wcc_sncode` VALUES ('26', '6', null, '1', '2b09132f7b8e407a926424f67e400117', null, '0', '0', '6', '3');

-- ----------------------------
-- Table structure for wcc_store
-- ----------------------------
DROP TABLE IF EXISTS `wcc_store`;
CREATE TABLE `wcc_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_send_mail` tinyint(1) NOT NULL,
  `is_visiable` tinyint(1) DEFAULT NULL,
  `store_addres` varchar(200) NOT NULL,
  `store_auditing` int(11) NOT NULL,
  `store_big_image_url` varchar(4000) NOT NULL,
  `store_code` varchar(100) DEFAULT NULL,
  `store_code_url` varchar(200) DEFAULT NULL,
  `store_email` varchar(255) NOT NULL,
  `store_laty` varchar(200) NOT NULL,
  `store_lngx` varchar(200) NOT NULL,
  `store_name` varchar(200) NOT NULL,
  `store_phone` varchar(100) NOT NULL,
  `store_remark` varchar(4000) DEFAULT NULL,
  `store_small_image_url` varchar(4000) NOT NULL,
  `store_text` varchar(4000) DEFAULT NULL,
  `store_url` varchar(200) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `organization` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ebv1f139vlio56cyoq1phl28r` (`organization`),
  CONSTRAINT `FK_ebv1f139vlio56cyoq1phl28r` FOREIGN KEY (`organization`) REFERENCES `pub_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_store
-- ----------------------------
INSERT INTO `wcc_store` VALUES ('1', '2014-11-28 10:14:24', '1', '0', null, '漕宝路', '1', '1', 'test', 'www.baidi.com', '9.99@9.com', '31.166937', '121.406655', '久科', '134123123123', null, '1', '一号分店', '1', '0', '6');

-- ----------------------------
-- Table structure for wcc_store_platform_users
-- ----------------------------
DROP TABLE IF EXISTS `wcc_store_platform_users`;
CREATE TABLE `wcc_store_platform_users` (
  `wcc_stores` bigint(20) NOT NULL,
  `platform_users` bigint(20) NOT NULL,
  PRIMARY KEY (`wcc_stores`,`platform_users`),
  KEY `FK_edl72s4h6019h0jthxayci9f4` (`platform_users`),
  KEY `FK_5ior1dfrv4mbm3ii9mk2l11hd` (`wcc_stores`),
  CONSTRAINT `FK_5ior1dfrv4mbm3ii9mk2l11hd` FOREIGN KEY (`wcc_stores`) REFERENCES `wcc_store` (`id`),
  CONSTRAINT `FK_edl72s4h6019h0jthxayci9f4` FOREIGN KEY (`platform_users`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_store_platform_users
-- ----------------------------
INSERT INTO `wcc_store_platform_users` VALUES ('1', '1');

-- ----------------------------
-- Table structure for wcc_template
-- ----------------------------
DROP TABLE IF EXISTS `wcc_template`;
CREATE TABLE `wcc_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `is_visable` tinyint(1) DEFAULT NULL,
  `name` varchar(300) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `sort` varchar(40) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `use_count` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_template
-- ----------------------------
INSERT INTO `wcc_template` VALUES ('1', '', '2014-11-17 14:07:12', null, null, 'nineclient', null, null, 'nineclient', '0', '1', '0');
INSERT INTO `wcc_template` VALUES ('2', null, '2014-11-28 11:11:56', null, null, 'admin', '1', null, 'wcc', '0', '0', '0');
INSERT INTO `wcc_template` VALUES ('3', null, '2014-11-28 11:12:04', null, null, 'admin', '1', null, 'voc', '0', '0', '0');
INSERT INTO `wcc_template` VALUES ('4', 'wcc是wcc\n', '2014-11-28 11:12:26', null, null, 'admin', '2', '0', 'wcc是什么', '1', '0', '0');
INSERT INTO `wcc_template` VALUES ('5', null, '2014-11-28 11:12:38', null, null, 'admin', '3', null, '常用咨询', '0', '0', '0');
INSERT INTO `wcc_template` VALUES ('6', '好用，耐用，有用', '2014-11-28 11:13:00', null, null, 'admin', '5', '1', '产品特点', '1', '0', '0');
INSERT INTO `wcc_template` VALUES ('7', '这是菜单帮助', '2014-11-28 11:13:27', null, null, 'admin', '1', '', '帮助', '1', '0', '0');
INSERT INTO `wcc_template` VALUES ('8', null, '2014-11-28 11:18:40', null, null, 'admin', '1', null, 'ucc', '0', '0', '0');

-- ----------------------------
-- Table structure for wcc_topic
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
-- Table structure for wcc_user
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
-- Table structure for wcc_user_fields
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
-- Table structure for wcc_user_field_value
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
-- Table structure for wcc_user_lottery
-- ----------------------------
DROP TABLE IF EXISTS `wcc_user_lottery`;
CREATE TABLE `wcc_user_lottery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `lottery_number` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `friend` bigint(20) DEFAULT NULL,
  `lottery_activity` bigint(20) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h6oplvgkjgteavt9a4t77q883` (`friend`),
  KEY `FK_ffi8a8cwooqqcba4dsiktwcp1` (`lottery_activity`),
  KEY `FK_19pvcutoj04oaawk796n0kmbp` (`platform_user`),
  CONSTRAINT `FK_19pvcutoj04oaawk796n0kmbp` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`),
  CONSTRAINT `FK_ffi8a8cwooqqcba4dsiktwcp1` FOREIGN KEY (`lottery_activity`) REFERENCES `wcc_lottery_activity` (`id`),
  CONSTRAINT `FK_h6oplvgkjgteavt9a4t77q883` FOREIGN KEY (`friend`) REFERENCES `wcc_friend` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_user_lottery
-- ----------------------------

-- ----------------------------
-- Table structure for wcc_user_name
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
-- Table structure for wcc_user_pagetemp
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
-- Table structure for wcc_utility
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
-- Table structure for wcc_welcomkbs
-- ----------------------------
DROP TABLE IF EXISTS `wcc_welcomkbs`;
CREATE TABLE `wcc_welcomkbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `reply_type` int(11) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `platform_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b2r8srdyppo7j6ikjab085fof` (`platform_user`),
  CONSTRAINT `FK_b2r8srdyppo7j6ikjab085fof` FOREIGN KEY (`platform_user`) REFERENCES `wcc_platform_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wcc_welcomkbs
-- ----------------------------
