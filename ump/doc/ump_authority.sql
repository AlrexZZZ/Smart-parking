/**
 * 初始化脚本
 */

-- ----------------------------
-- Records of ump_authority
-- ----------------------------
INSERT INTO `ump_authority` VALUES ('1', '公司', null, null, '1', '0', '', '0', 'a', '0',null);
INSERT INTO `ump_authority` VALUES ('2', '注册页面', null, null, '1', '1', '', '0', '/ump/umpcompanys?form', '3',null);
INSERT INTO `ump_authority` VALUES ('3', '服务管理', null, null, '1', '1', '', '0', '/ump/umpcompanyservices/list', '1',null);
INSERT INTO `ump_authority` VALUES ('4', '产品', null, null, '1', '0', '', '0', '', '0',null);
INSERT INTO `ump_authority` VALUES ('5', '产品管理', null, null, '1', '4', '', '0', '/ump/umpproducts?form', '2',null);
INSERT INTO `ump_authority` VALUES ('6', '公司注册', null, null, '1', '1', '', '0', '/ump/umpcompanys/companyRegisterList', '1',null);
INSERT INTO `ump_authority` VALUES ('7', '渠道管理', null, null, '1', '4', '', '0', '/ump/umpproducts/productList', '2',null);
INSERT INTO `ump_authority` VALUES ('8', '行业管理', null, null, '1', '4', '', '0', '/ump/umpparentbusinesstypes?page=1&amp;size=10', '1', null);
INSERT INTO `ump_authority` VALUES ('9', '品牌关键词审核', null, null, '1', '4', '', '0', '/ump/umpbrands/barndList?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('10', '版本管理', null, null, '1', '4', '', '0', '/ump/umpversions/versionList?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('11', '配置管理', null, null, '1', '0', '', '0', '', '1',null);
INSERT INTO `ump_authority` VALUES ('13', '邮箱配置', null, null, '1', '11', '', '0', '/ump/umpconfigs/ec', '0',null);
INSERT INTO `ump_authority` VALUES ('14', '找回密码', null, null, '1', '11', '', '0', '/ump/umpoperators/fp', '0',null);
INSERT INTO `ump_authority` VALUES ('15', '菜单管理', null, null, '1', '0', '', '0', '', '0',null);
INSERT INTO `ump_authority` VALUES ('16', '创建菜单', null, null, '1', '15', '', '0', '/ump/umpauthoritys?form', '0',null);
INSERT INTO `ump_authority` VALUES ('17', '菜单列表', null, null, '1', '15', '', '0', '/ump/umpauthoritys?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('18', '权限管理', null, null, '1', '0', '', '0', '', '0',null);
INSERT INTO `ump_authority` VALUES ('19', '创建权限', null, null, '1', '18', '', '0', '/ump/umproles/create?form', '0',null);
INSERT INTO `ump_authority` VALUES ('20', '权限列表', null, null, '1', '18', '', '0', '/ump/umproles?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('21', '管理中心', null, null, '1', '0', '', '0', '', '0',null);
INSERT INTO `ump_authority` VALUES ('22', '账号管理', null, null, '1', '21', '', '0', '/ump/umpoperators?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('23', '日志管理', null, null, '1', '21', '', '0', '/ump/umplogs?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('24', '组织管理', null, null, '1', '21', '', '0', '/ump/puborganizations?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('25', '应用管理', null, null, '1', '0', '', '0', '', '0',null);
INSERT INTO `ump_authority` VALUES ('26', '微内容', null, null, '1', '25', '', '0', '/ump/wcccontents?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('27', '门店管理', null, null, '1', '25', '', '0', '/ump/wccstores?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('28', '微信管理', null, null, '1', '0', '', '0', '', '0',null);
INSERT INTO `ump_authority` VALUES ('29', '自定义菜单', null, null, '1', '28', '', '0', '/ump/wccmenus?page=1&amp;size=10', '0',null);
INSERT INTO `ump_authority` VALUES ('30', '知识管理', null, null, '1', '0', null, '0', ' ', '0', null);
INSERT INTO `ump_authority` VALUES ('31', '常用文本', null, null, '1', '30', '', '0', '/ump/wcctemplates', '0', null);
INSERT INTO `ump_authority` VALUES ('32', '自动回复', null, null, '1', '30', null, '0', '/ump/wccautokbses', '0', null);
INSERT INTO `ump_authority` VALUES ('33', '欢迎语设置', null, null, '1', '30', null, '0', '/ump/wccwelcomkbses?form', '0', null);
INSERT INTO `ump_authority` VALUES ('34', '统一账号管理', null, null, '1', '21', null, '0', '/ump/puboperators?page=1&amp;size=10', '0', null);
INSERT INTO `ump_authority` VALUES ('35', '子行业管理', null, null, '1', '4', '', '0', '/ump/umpbusinesstypes?page=1&amp;size=10', '1', null);
INSERT INTO `ump_authority` VALUES ('36', 'VOC品牌管理', null, null, '1', '4', '', '0', '/ump/vocbrands?page=1&amp;size=10', '1', null);
INSERT INTO `ump_authority` VALUES ('37', 'VOC词库规则管理', null, null, '1', '4', '', '0', '/ump/vocwordcategorys?page=1&amp;size=10', '1', null);
INSERT INTO `ump_authority` VALUES ('38', 'VOCSKU管理', null, null, '1', '4', '', '0', '/ump/vocskus?page=1&amp;size=10', '1', null);
INSERT INTO `ump_authority` VALUES ('39', '工单类型管理', null, null, '1', '4', '', '0', '/ump/vocworkordertypes?form', '1', null);
INSERT INTO `ump_authority` VALUES ('40', '词库分类管理', null, null, '1', '4', '', '0', '/ump/vocwordcategorys?form', '1', null);
INSERT INTO `ump_authority` VALUES ('41', '账号管理', null, null, '1', '4', '', '0', '/ump/vocaccounts/accountManage', '1', null);
INSERT INTO `ump_authority` VALUES ('42', 'AppKey管理', null, null, '1', '4', '', '0', '/ump/vocappkeys/appKeyManage', '1', null);
/ump/hotwordreports/reportPage 产品热词占比


INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('35', '创建公众号', NULL, NULL, '1', '24', '', '0', '/ump/wccplatformusers?form', '1', '2');
INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('36', '公众号管理', NULL, NULL, '1', '24', '', '0', '/ump/wccplatformusers?page=1&amp;size=10', '1', '2');

INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('32', '添加权限组', NULL, NULL, '1', '31', '', '0', '/ump/pubroles?form', '1', '2');
INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('33', '微信权限列表', NULL, NULL, '1', '31', '', '0', '/ump/pubroles?page=1&amp;size=10', '0', '2');
INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('43', '创建抽奖活动', NULL, NULL, '1', '42', '', '0', '	/ump/wcclotteryactivitys?form', '0', '2');
INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('44', '抽奖活动列表', NULL, NULL, '1', '42', '', '0', '	/ump/wcclotteryactivitys?page=1&amp;size=10', '0', '2');



INSERT INTO `pub_organization` VALUES (1, '2014-11-11', 1, 1, '根', -1, null, 0, 0);
INSERT INTO `wcc_template` VALUES ('1', '', '2014-11-17 14:07:12', null, null, 'nineclient', null, null, 'nineclient', '0', '1', '0');
#产品
INSERT INTO `ump_product` VALUES (1, '2014-11-19 17:18:18', 0, 1, 'UMP', NULL, 0);
INSERT INTO `ump_product` VALUES (2, '2014-11-19 17:18:18', 0, 1, 'VOC', NULL, 0);
INSERT INTO `ump_product` VALUES (3, '2014-11-19 17:18:18', 0, 1, 'WCC', NULL, 0);
#词库管理
INSERT INTO `voc_word_category` VALUES (1, '2014-11-24 13:53:52', 0, '全部', 0, 0, NULL);
INSERT INTO `voc_word_category` VALUES (2, '2014-11-24 14:53:53', 0, '好评', 1, 0, NULL);
INSERT INTO `voc_word_category` VALUES (3, '2014-11-24 14:54:20', 0, '中评', 1, 0, NULL);
INSERT INTO `voc_word_category` VALUES (4, '2014-11-24 14:54:50', 0, '差评', 1, 0, NULL);
INSERT INTO `voc_word_category` VALUES (5, '2014-11-24 14:55:15', 0, '预警', 1, 0, NULL);


INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('25', '创建公众号', NULL, NULL, '1', '24', '', '0', '/ump/wccplatformusers?form', '1', '2');
INSERT INTO `ump`.`ump_authority` (`id`, `display_name`, `image`, `is_deleted`, `is_visable`, `parent_id`, `remark`, `sort`, `url`, `version`, `product`) VALUES ('26', '公众号管理', NULL, NULL, '1', '24', '', '0', '/ump/wccplatformusers?page=1&amp;size=10', '1', '2');


