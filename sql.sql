/*
 Navicat Premium Data Transfer
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `credit_company`
-- ----------------------------
DROP TABLE IF EXISTS `credit_company`;
CREATE TABLE `credit_company` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '公司名称',
  `mobile` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '手机号',
  `app_name` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT 'app名称',
  `example_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '实例名称',
  `address` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '外网连接地址',
  `back_address` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '后台地址',
  `front_address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前台地址(ip)',
  `port` int(10) DEFAULT NULL COMMENT '端口号',
  `user_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `init_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '初始化标识 1：未初始化；0：已初始化',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='回租商户管理表';

-- ----------------------------
--  Table structure for `decision_black_list`
-- ----------------------------
DROP TABLE IF EXISTS `decision_black_list`;
CREATE TABLE `decision_black_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `id_number` varchar(30) NOT NULL COMMENT '身份证号',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `black_list_idnumber_idx` (`id_number`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='黑名单库';

-- ----------------------------
--  Table structure for `decision_company`
-- ----------------------------
DROP TABLE IF EXISTS `decision_company`;
CREATE TABLE `decision_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `app_name` varchar(20) NOT NULL COMMENT '公司APP名称',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人',
  `contact_mobile` varchar(50) NOT NULL COMMENT '联系方式',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `amount` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '余额',
  `api_key` varchar(30) NOT NULL COMMENT 'api key ',
  `api_secret` varchar(30) NOT NULL COMMENT 'api secret',
  `mobile_price` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '运营商报告价格',
  `credit_price` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '多头报告价格',
  `anti_fraud_price` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '反欺诈报告价格',
  `decision_price` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '决策报告价格',
  `real_name_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实名认证价格',
  `sms_code_price` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '验证码短信发送价格',
  `sms_notice_price` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '通知短信发送价格',
  `bank_auth_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '银行卡鉴权价格',
  `taobao_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '淘宝认证价格',
  `liability_price` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '负债共享价格',
  `cloud_phase_price` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '有盾云相价格',
  `decision_score_price` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '决策分数价格',
  `auto_decision_price` decimal(5,3) NOT NULL DEFAULT '0.000' COMMENT '全自动决策价格',
  `mobile_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '运营商权限 1：有 0：无',
  `credit_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '多头报告权限 1：有 0：无',
  `anti_fraud_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '反欺诈报告权限 1：有 0：无',
  `decision_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '决策报告权限 1：有 0：无',
  `real_name_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '实名认证权限 1：有 0：无',
  `sms_code_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '短信发送权限 1：有 0：无',
  `sms_notice_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '通知短信发送权限 1：有 0：无',
  `bank_auth_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '银行卡鉴权权限 1：有 0：无',
  `taobao_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '淘宝报告权限 1：有 0：无',
  `liability_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '负债共享权限 1：有 0：无',
  `cloud_phase_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有盾云相权限 1：有 0：无',
  `decision_score_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '决策分数权限 1：有 0：无',
  `auto_decision_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '全自动决策权限 1：有 0：无',
  `mobile_third_party_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '运营商认证方式 1:新颜 2:魔蝎',
  `taobao_third_party_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '淘宝认证方式 1：新颜 2：魔蝎',
  `auto_decision_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '全自动决策渠道 1：有盾云相',
  `score` int(5) NOT NULL DEFAULT '0' COMMENT '全自动接口分数',
  `low_score` int(5) NOT NULL DEFAULT '0' COMMENT '最低分',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_company_apikey_idx` (`api_key`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='公司表';

-- ----------------------------
--  Records of `decision_company`
-- ----------------------------
BEGIN;
INSERT INTO `decision_company` VALUES ('1', 'test', '1', 'admin', '13333333333', '1', '99769.352', 'YrwTVgdFWuUQ0BR', 'oia4CoaXQ1dG1ubNdRaZ', '0.50', '1.00', '0.50', '0.80', '0.60', '0.032', '0.048', '0.50', '0.50', '0.800', '0.00', '0.00', '0.100', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '510', '500', '2019-01-03 11:16:43', '2019-02-13 11:46:46');
COMMIT;

-- ----------------------------
--  Table structure for `decision_company_menu`
-- ----------------------------
DROP TABLE IF EXISTS `decision_company_menu`;
CREATE TABLE `decision_company_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='公司菜单表';

-- ----------------------------
--  Records of `decision_company_menu`
-- ----------------------------
BEGIN;
INSERT INTO `decision_company_menu` VALUES ('1', '1', '11', '2019-01-03 11:18:48'), ('2', '1', '12', '2019-01-03 11:18:48'), ('3', '1', '13', '2019-01-03 11:18:48'), ('4', '1', '14', '2019-01-03 11:18:48'), ('5', '1', '15', '2019-01-03 11:18:48'), ('6', '1', '1', '2019-01-03 11:18:48'), ('7', '1', '3', '2019-01-03 11:18:48'), ('8', '1', '4', '2019-01-03 11:18:48'), ('9', '1', '5', '2019-01-03 11:18:48'), ('10', '1', '6', '2019-01-03 11:18:48'), ('11', '1', '7', '2019-01-03 11:18:48'), ('12', '1', '8', '2019-01-03 11:18:48'), ('13', '1', '9', '2019-01-03 11:18:48'), ('14', '1', '10', '2019-01-03 11:18:48'), ('15', '5', '11', '2019-02-13 14:36:13'), ('16', '5', '12', '2019-02-13 14:36:13'), ('17', '5', '13', '2019-02-13 14:36:13'), ('18', '5', '14', '2019-02-13 14:36:13'), ('19', '5', '15', '2019-02-13 14:36:13'), ('20', '5', '1', '2019-02-13 14:36:13'), ('21', '5', '3', '2019-02-13 14:36:13'), ('22', '5', '4', '2019-02-13 14:36:13'), ('23', '5', '5', '2019-02-13 14:36:13'), ('24', '5', '6', '2019-02-13 14:36:13'), ('25', '5', '7', '2019-02-13 14:36:13'), ('26', '5', '8', '2019-02-13 14:36:13'), ('27', '5', '9', '2019-02-13 14:36:13'), ('28', '5', '10', '2019-02-13 14:36:13'), ('29', '3', '11', '2019-05-24 12:19:45'), ('30', '3', '12', '2019-05-24 12:19:45'), ('31', '3', '13', '2019-05-24 12:19:45'), ('32', '3', '14', '2019-05-24 12:19:45'), ('33', '3', '15', '2019-05-24 12:19:45'), ('34', '3', '1', '2019-05-24 12:19:45'), ('35', '3', '3', '2019-05-24 12:19:45'), ('36', '3', '4', '2019-05-24 12:19:45'), ('37', '3', '5', '2019-05-24 12:19:45'), ('38', '3', '6', '2019-05-24 12:19:45'), ('39', '3', '7', '2019-05-24 12:19:45'), ('40', '3', '8', '2019-05-24 12:19:45'), ('41', '3', '9', '2019-05-24 12:19:45'), ('42', '3', '10', '2019-05-24 12:19:45');
COMMIT;

-- ----------------------------
--  Table structure for `decision_consume_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_consume_record`;
CREATE TABLE `decision_consume_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `report_type` tinyint(2) NOT NULL COMMENT '报告类型 0:无 1:运营商 2：信贷 3：反欺诈 4：决策',
  `amount` decimal(10,3) NOT NULL COMMENT '消费金额',
  `type` tinyint(2) NOT NULL COMMENT '1：消费 2：充值',
  `description` varchar(200) NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consume_companyid_idx` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消费记录表';

-- ----------------------------
--  Table structure for `decision_credit_config`
-- ----------------------------
DROP TABLE IF EXISTS `decision_credit_config`;
CREATE TABLE `decision_credit_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `description` varchar(30) NOT NULL COMMENT '描述',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `type` tinyint(2) NOT NULL COMMENT '1：首贷 2：复贷 3：全部',
  `is_open` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否开启 1：开启 0：关闭',
  `is_show` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：显示 0：不显示',
  `val` varchar(50) NOT NULL COMMENT '值，反欺诈数据(还款数,借款申请数)',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='多头反欺诈决策参数配置表';

-- ----------------------------
--  Records of `decision_credit_config`
-- ----------------------------
BEGIN;
INSERT INTO `decision_credit_config` VALUES ('1', 'FQZ01', '反欺诈未知风险情况评估', '多平台借贷申请检测', '1', '1', '1', '1', '0,15', '2018-08-04 11:37:03', null), ('2', 'FQZ02', '反欺诈较低风险情况评估', '多平台借贷申请检测', '1', '1', '0', '1', '0,30', '2018-08-04 11:37:03', null), ('3', 'FQZ03', '反欺诈中低风险情况评估', '多平台借贷申请检测', '1', '1', '1', '1', '0,30', '2018-08-04 11:37:03', null), ('4', 'FQZ04', '反欺诈中高风险情况评估', '多平台借贷申请检测', '1', '1', '0', '1', '0,30', '2018-08-04 11:37:03', null), ('5', 'DT01', '申请人最小年龄', '个人基本信息核查', '1', '1', '1', '1', '18', '2018-08-04 11:37:03', null), ('6', 'DT02', '申请人最大年龄', '个人基本信息核查', '1', '1', '0', '1', '45', '2018-08-04 11:37:03', null), ('7', 'DT03', '贷款逾期订单数', '多平台借贷申请检测', '1', '1', '1', '1', '8', '2018-08-04 11:37:03', null), ('8', 'DT04', '近1个月贷款机构失败扣款笔数', '多平台借贷申请检测', '1', '1', '0', '1', '6', '2018-08-04 11:37:03', null), ('9', 'DT05', '近1个月贷款机构失败扣款笔数/贷款逾期订单数', '多平台借贷申请检测', '1', '1', '1', '1', '4', '2018-08-04 11:37:03', null), ('10', 'DT06', '历史成功扣款数-历史失败数', '多平台借贷申请检测', '1', '1', '0', '1', '-5', '2018-08-04 11:37:03', null), ('11', 'FQZ01', '反欺诈未知风险情况评估', '多平台借贷申请检测', '1', '2', '1', '1', '0,15', '2018-08-04 11:37:03', null), ('12', 'FQZ02', '反欺诈较低风险情况评估', '多平台借贷申请检测', '1', '2', '1', '1', '0,30', '2018-08-04 11:37:03', null), ('13', 'FQZ03', '反欺诈中低风险情况评估', '多平台借贷申请检测', '1', '2', '0', '1', '0,30', '2018-08-04 11:37:03', null), ('14', 'FQZ04', '反欺诈中高风险情况评估', '多平台借贷申请检测', '1', '2', '1', '1', '0,30', '2018-08-04 11:37:03', null), ('15', 'DT01', '申请人最小年龄', '个人基本信息核查', '1', '2', '1', '1', '18', '2018-08-04 11:37:03', null), ('16', 'DT02', '申请人最大年龄', '个人基本信息核查', '1', '2', '0', '1', '45', '2018-08-04 11:37:03', null), ('17', 'DT03', '贷款逾期订单数', '多平台借贷申请检测', '1', '2', '1', '1', '8', '2018-08-04 11:37:03', null), ('18', 'DT04', '近1个月贷款机构失败扣款笔数', '多平台借贷申请检测', '1', '2', '1', '1', '6', '2018-08-04 11:37:03', null), ('19', 'DT05', '近1个月贷款机构失败扣款笔数/贷款逾期订单数', '多平台借贷申请检测', '1', '2', '1', '1', '4', '2018-08-04 11:37:03', null), ('20', 'DT06', '历史成功扣款数-历史失败数', '多平台借贷申请检测', '1', '2', '1', '1', '-5', '2018-08-04 11:37:03', null), ('21', 'A04', '手机号关联身份证个数', '运营商信息检测', '1', '3', '1', '0', '3', '2018-08-04 11:37:03', null), ('22', 'A05', '身份证关联手机号个数', '运营商信息检测', '1', '3', '1', '0', '3', '2018-08-04 11:37:03', null), ('23', 'A06', '150天内连续3天无通话记录次数', '运营商信息检测', '1', '3', '1', '0', '5', '2018-08-04 11:37:03', null), ('24', 'A10', '近5个月话费充值都低于金额', '运营商信息检测', '0', '3', '1', '0', '30', '2018-08-04 11:37:03', null), ('25', 'TXL01', '通讯录联系人低于一定数量', '通讯录信息检测', '1', '3', '1', '0', '20', '2018-08-04 11:37:03', null), ('26', 'TXL02', '通讯录敏感信息', '通讯录信息检测', '1', '3', '1', '0', '借,贷-10', '2018-08-04 11:37:03', null), ('27', 'A03', '160天内有无通话记录天数', '通讯录信息检测', '1', '3', '1', '0', '50', '2018-08-04 11:37:03', null), ('28', 'A07', '号码使用时长', '运营商信息检测', '1', '3', '1', '0', '180', '2018-08-04 11:37:03', null), ('29', 'A11', '通讯录与通话记录中前20位匹配号码数', '运营商信息检测', '1', '3', '1', '0', '3', '2018-08-04 11:37:03', null), ('30', 'FQZ06', '设备使用数', '多平台借贷申请检测', '1', '1', '1', '1', '2', '2018-08-04 11:37:03', null), ('31', 'FQZ06', '设备使用数', '多平台借贷申请检测', '1', '2', '1', '1', '2', '2018-08-04 11:37:03', null), ('32', 'DT07', '贷款行为分', '多平台借贷申请检测', '1', '1', '1', '1', '450', '2018-08-04 11:37:03', null), ('33', 'DT07', '贷款行为分', '多平台借贷申请检测', '1', '2', '1', '1', '300', '2018-08-04 11:37:03', null), ('34', 'DT08', '贷款置信度', '多平台借贷申请检测', '1', '1', '1', '1', '60', '2018-08-04 11:37:03', null), ('35', 'DT08', '贷款置信度', '多平台借贷申请检测', '1', '2', '1', '1', '50', '2018-08-04 11:37:03', null), ('36', 'A08', '与法院有通话记录', '运营商信息检测', '1', '3', '1', '0', '0', '2018-08-04 11:37:03', null), ('37', 'A09', '通话记录与110有通话记录', '运营商信息检测', '1', '3', '1', '0', '0', '2018-08-04 11:37:03', null), ('38', 'TXL03', '通讯录敏感信息', '通讯录信息检测', '0', '3', '1', '0', '警官,警察,警方,法官', '2018-09-13 00:13:45', null), ('39', 'HYGZ01', '当前逾期', '行业关注黑名单', '1', '1', '1', '1', '0', '2018-09-13 00:13:45', null), ('40', 'HYGZ01', '当前逾期', '行业关注黑名单', '1', '2', '1', '1', '0', '2018-09-13 00:13:45', null), ('41', 'HYGZ02', '曾经逾期数', '行业关注黑名单', '0', '1', '1', '1', '6', '2018-09-13 00:13:45', null), ('42', 'HYGZ02', '曾经逾期数', '行业关注黑名单', '0', '2', '1', '1', '6', '2018-09-13 00:13:45', null), ('43', 'HYGZ03', '曾经逾期30天以上', '行业关注黑名单', '0', '1', '1', '1', '0', '2018-09-13 00:13:45', null), ('44', 'HYGZ03', '曾经逾期30天以上', '行业关注黑名单', '0', '2', '1', '1', '0', '2018-09-13 00:13:45', null), ('45', 'FQZ07', '反欺诈近一个月实际借款数与近一个月申请借款数', '多平台借贷申请检测', '1', '1', '1', '1', '0,30', '2018-09-13 00:13:45', null), ('46', 'FQZ07', '反欺诈近一个月实际借款数与近一个月申请借款数', '多平台借贷申请检测', '1', '2', '1', '1', '0,40', '2018-09-13 00:13:45', null), ('47', 'FQZ08', '反欺诈近一个月申请借款数/近一个月实际借款数', '多平台借贷申请检测', '1', '1', '1', '1', '45', '2018-09-13 00:13:45', null), ('48', 'FQZ08', '反欺诈近一个月申请借款数/近一个月实际借款数', '多平台借贷申请检测', '1', '2', '1', '1', '100', '2018-09-13 00:13:45', null), ('49', 'DT09', '多头与反欺诈近一个月实际借款数', '多平台借贷申请检测', '0', '1', '1', '1', '0', '2018-09-13 00:13:45', null), ('50', 'DT09', '多头与反欺诈近一个月实际借款数', '多平台借贷申请检测', '0', '2', '1', '1', '0', '2018-09-13 00:13:45', null), ('51', 'DT10', '近1个月失败扣款笔数-近1个月成功扣款笔数', '多平台借贷申请检测', '1', '1', '1', '1', '4', '2018-09-13 00:13:45', null), ('52', 'DT10', '近1个月失败扣款笔数-近1个月成功扣款笔数', '多平台借贷申请检测', '1', '2', '1', '1', '4', '2018-09-13 00:13:45', null), ('53', 'DT11', '贷款放款总订单数-贷款已结清订单数', '多平台借贷申请检测', '0', '1', '1', '1', '15', '2018-09-13 00:13:45', null), ('54', 'DT11', '贷款放款总订单数-贷款已结清订单数', '多平台借贷申请检测', '0', '2', '1', '1', '15', '2018-09-13 00:13:45', null), ('55', 'HMD01', '黑名单库', '黑名单检测', '1', '3', '1', '0', '0', '2018-12-07 11:16:27', null);
COMMIT;

-- ----------------------------
--  Table structure for `decision_credit_config_company`
-- ----------------------------
DROP TABLE IF EXISTS `decision_credit_config_company`;
CREATE TABLE `decision_credit_config_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_id` bigint(20) NOT NULL COMMENT '配置ID',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `is_open` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否开启 1：开启 0：关闭',
  `val` varchar(50) NOT NULL COMMENT '值',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='多头反欺诈商户决策参数配置表';

-- ----------------------------
--  Table structure for `decision_customer_info`
-- ----------------------------
DROP TABLE IF EXISTS `decision_customer_info`;
CREATE TABLE `decision_customer_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL,
  `id_number` varchar(50) NOT NULL COMMENT '身份证号',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `id_address` varchar(200) NOT NULL COMMENT '身份证地址',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_info_id_number_idx` (`id_number`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='回租推送的用户信息';

-- ----------------------------
--  Table structure for `decision_customer_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_customer_record`;
CREATE TABLE `decision_customer_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_number` char(50) NOT NULL COMMENT '用户ID',
  `customer_create_time` datetime NOT NULL COMMENT '用户注册时间',
  `create_time` datetime NOT NULL,
  `company_flag` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_idnumber_customer` (`id_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `decision_hit_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_hit_record`;
CREATE TABLE `decision_hit_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `decision_report_id` bigint(20) NOT NULL COMMENT '关联的 决策报告ID',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `group` tinyint(2) NOT NULL COMMENT '1：内置 2：芝麻信用  3：运营商',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type` tinyint(2) NOT NULL COMMENT '1：首贷  2：复贷',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `hit_record_idx` (`user_id`),
  KEY `hit_record_idx2` (`company_id`,`type`,`create_time`),
  KEY `hit_record_reportid_idx` (`decision_report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='决策命中记录表';

-- ----------------------------
--  Table structure for `decision_login_token`
-- ----------------------------
DROP TABLE IF EXISTS `decision_login_token`;
CREATE TABLE `decision_login_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(50) NOT NULL,
  `api_key` varchar(50) NOT NULL COMMENT 'apiKey',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `usable` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否被使用 1：是 0：否',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='登录token';

-- ----------------------------
--  Table structure for `decision_mobile`
-- ----------------------------
DROP TABLE IF EXISTS `decision_mobile`;
CREATE TABLE `decision_mobile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_back_url` varchar(500) DEFAULT NULL COMMENT '回调地址',
  `extra_info` varchar(800) DEFAULT NULL COMMENT '额外参数',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `id_number` varchar(20) NOT NULL COMMENT '身份证号',
  `mobile` varchar(15) NOT NULL COMMENT '手机号',
  `phone_list` varchar(500) DEFAULT NULL COMMENT '通讯录',
  `original_status` tinyint(2) NOT NULL DEFAULT '2' COMMENT '原始数据状态',
  `report_status` tinyint(2) NOT NULL DEFAULT '2' COMMENT '当前运营商报告状态',
  `mobile_report_data` varchar(500) DEFAULT NULL COMMENT '运营商报告数据',
  `mobile_original_data` varchar(500) DEFAULT NULL COMMENT '运营商原始数据',
  `decision_extend` varchar(200) DEFAULT NULL COMMENT '决策引擎扩展字段',
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mobile_report_userid_idx` (`user_id`,`original_status`,`report_status`),
  KEY `decision_mobile_status_idx` (`original_status`),
  KEY `decision_mobile_report_status_idx` (`report_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='运营商报告数据';

-- ----------------------------
--  Table structure for `decision_mobile_order`
-- ----------------------------
DROP TABLE IF EXISTS `decision_mobile_order`;
CREATE TABLE `decision_mobile_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `channel` tinyint(2) DEFAULT '1' COMMENT '1：新颜  2：魔蝎',
  `order_no` varchar(50) NOT NULL COMMENT '第三方返回的商户ID',
  `phone_list` varchar(300) DEFAULT NULL COMMENT '通讯录',
  `emerg_contacts` text COMMENT '紧急联系人',
  `report_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '报告状态 0：未知 1：成功  2：失败 3：进行中',
  `data_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '数据状态 0：未知 1：成功  2：失败 3：进行中',
  `report_result` varchar(500) DEFAULT NULL COMMENT '报告数据',
  `data_result` varchar(500) DEFAULT NULL COMMENT '原始数据',
  `data_times` int(2) NOT NULL DEFAULT '0' COMMENT '原始数据获取次数',
  `report_times` int(2) NOT NULL DEFAULT '0' COMMENT '报告数据获取次数',
  `callback` varchar(500) NOT NULL COMMENT '回调地址',
  `data_token` varchar(200) DEFAULT NULL COMMENT '原始数据token',
  `report_token` varchar(200) DEFAULT NULL COMMENT '报告数据token',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `taobao_order_orderno` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='运营商订单记录';

-- ----------------------------
--  Table structure for `decision_mobile_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_mobile_record`;
CREATE TABLE `decision_mobile_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求接口的唯一id',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) NOT NULL COMMENT '客户ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '返回结果 1：成功 0：失败',
  `result` longtext COLLATE utf8mb4_unicode_ci COMMENT '返回结果',
  `request_params` longtext COLLATE utf8mb4_unicode_ci COMMENT '请求的参数',
  `request_type` tinyint(2) NOT NULL COMMENT '接口请求类型 1：发送请求 2：查询返回报告',
  `request_order_number` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '查询接口用，记录是哪次的请求，对应orderId',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_check_index` (`request_order_number`,`request_type`,`user_id`,`status`),
  KEY `decision_mobile_record_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='运营商接口调用记录';

-- ----------------------------
--  Table structure for `decision_notice_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_notice_record`;
CREATE TABLE `decision_notice_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_type` tinyint(2) NOT NULL COMMENT '1:运营商 2：多头 3：反欺诈 4：决策 5：淘宝',
  `url` varchar(500) NOT NULL COMMENT '回调地址',
  `result` varchar(500) NOT NULL COMMENT '返回结果',
  `times` smallint(5) NOT NULL DEFAULT '1' COMMENT '通知次数',
  `flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1：返回成功 0：继续通知',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notice_record_idx` (`flag`,`times`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='异步通知记录表';

-- ----------------------------
--  Table structure for `decision_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `decision_order_info`;
CREATE TABLE `decision_order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `order_number` varchar(50) NOT NULL COMMENT '订单编号',
  `sell_price` decimal(10,2) NOT NULL COMMENT '出售价格',
  `rent_period` int(5) NOT NULL COMMENT '租期(天)',
  `apply_time` datetime NOT NULL COMMENT '订单申请时间',
  `loan_date` datetime DEFAULT NULL COMMENT '放款时间',
  `overdue_date` datetime DEFAULT NULL COMMENT '到期时间',
  `cut_real_money` decimal(10,2) DEFAULT NULL COMMENT '还款金额',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款时间',
  `order_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单状态0未审核1审核中2已驳回3已拒绝4审核通过',
  `finance_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '财务状态1：未放款 2：放款中 3：已放款  4：已还款 5：代发失败 6：还款中',
  `persist_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否续期 1：是 0：否',
  `persist_count` int(3) NOT NULL DEFAULT '0' COMMENT '续期次数',
  `equipment_name` varchar(50) DEFAULT NULL COMMENT '设备名称',
  `equipment_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备串码',
  `lend_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '借款状态（0：未贷款1：首贷2：复贷）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_info_order_number_idx` (`order_number`,`status`),
  KEY `order_info_customer_id_idx` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单信息';

-- ----------------------------
--  Table structure for `decision_order_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_order_record`;
CREATE TABLE `decision_order_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `finance_status` tinyint(2) NOT NULL COMMENT '财务状态1：未放款 2：放款中 3：已放款  4：已还款 5：代发失败 6：还款中',
  `order_create_time` datetime NOT NULL COMMENT '订单创建时间',
  `create_time` datetime NOT NULL,
  `company_flag` bigint(20) NOT NULL COMMENT '公司标识',
  `id_number` char(50) NOT NULL COMMENT '用户身份证号',
  `order_status` tinyint(2) NOT NULL COMMENT '订单状态',
  `cut_time` datetime DEFAULT NULL COMMENT '实际还款时间',
  `overdue_time` datetime DEFAULT NULL COMMENT '订单还款截止时间',
  `first_lend` tinyint(2) DEFAULT NULL COMMENT '1:首贷 2:复贷',
  PRIMARY KEY (`id`),
  KEY `decision_index_idnum` (`id_number`),
  KEY `decision_idnumber_idx` (`id_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `decision_persist_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `decision_persist_order_info`;
CREATE TABLE `decision_persist_order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(50) NOT NULL COMMENT '订单号',
  `persist_number` varchar(50) NOT NULL COMMENT '续期订单号',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `main_order_overdue_time` datetime NOT NULL COMMENT '主订单到期日',
  `persist_time` datetime NOT NULL COMMENT '续期时间',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `persist_order_idx` (`persist_number`,`order_number`,`customer_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='回租续期订单明细';

-- ----------------------------
--  Table structure for `decision_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_record`;
CREATE TABLE `decision_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 1：生成中 2：生成成功  3：生成失败',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `id_number` varchar(30) NOT NULL COMMENT '身份证号',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `type` tinyint(2) NOT NULL COMMENT '1：首贷 2：复贷',
  `sesame_score` int(5) NOT NULL COMMENT '芝麻分',
  `phone_list` varchar(500) NOT NULL COMMENT '通讯录',
  `zhima_is_watch` tinyint(2) DEFAULT '0' COMMENT '是否行业关注黑名单 1：是 0：否 2：未知',
  `watch_detail` text COMMENT '芝麻信用行业关注名单',
  `result` text COMMENT '报告结果',
  `result_type` tinyint(2) DEFAULT NULL COMMENT '决策结果类型 1：通过 2：拒绝',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_record_companyid_idx` (`company_id`,`status`),
  KEY `decision_record_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='决策报告表';

-- ----------------------------
--  Table structure for `decision_score_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_score_record`;
CREATE TABLE `decision_score_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `compay_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_condition` varchar(255) DEFAULT NULL COMMENT '用户条件',
  `subject` varchar(255) DEFAULT NULL COMMENT '题目',
  `condition` varchar(255) DEFAULT NULL COMMENT '条件',
  `score` int(10) NOT NULL DEFAULT '0' COMMENT '得分/扣分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='决策分数记录表';

-- ----------------------------
--  Table structure for `decision_scorpion_callback`
-- ----------------------------
DROP TABLE IF EXISTS `decision_scorpion_callback`;
CREATE TABLE `decision_scorpion_callback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request_type` tinyint(2) NOT NULL COMMENT '请求类型：1淘宝 2运营商',
  `callback_type` tinyint(2) NOT NULL COMMENT '回调类型: 1报告 2原始数据',
  `request_param` text NOT NULL COMMENT '请求参数内容（josn格式）',
  `times` smallint(5) NOT NULL DEFAULT '0' COMMENT '通知次数',
  `flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1：处理成功 0：继续处理',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `scorpion_callback_idx` (`times`,`flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='魔蝎运营商回调记录表';

-- ----------------------------
--  Table structure for `decision_sms_send`
-- ----------------------------
DROP TABLE IF EXISTS `decision_sms_send`;
CREATE TABLE `decision_sms_send` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) NOT NULL COMMENT '1：成功 0:失败',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `content` varchar(500) NOT NULL COMMENT '内容',
  `count` int(5) NOT NULL DEFAULT '1' COMMENT '条数',
  `params` varchar(200) DEFAULT NULL COMMENT '额外参数',
  `bench_id` varchar(50) DEFAULT NULL COMMENT '批次id',
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '短信类型 1：验证码类 2：通知类',
  `ret` varchar(500) DEFAULT NULL COMMENT '返回记录',
  `ip` char(15) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `channel` tinyint(2) DEFAULT '1' COMMENT '短信渠道(3：创蓝，4：云通讯)',
  PRIMARY KEY (`id`),
  KEY `sms_send_mobile_idx` (`mobile`,`bench_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='短信发送记录表';

-- ----------------------------
--  Table structure for `decision_taobao_callback`
-- ----------------------------
DROP TABLE IF EXISTS `decision_taobao_callback`;
CREATE TABLE `decision_taobao_callback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `callback_url` varchar(300) NOT NULL COMMENT '回调地址',
  `type` tinyint(2) NOT NULL COMMENT '类型 1：新颜 2：魔蝎',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `taobao_callback_userid_idx` (`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='淘宝认证回调记录';

-- ----------------------------
--  Table structure for `decision_taobao_order`
-- ----------------------------
DROP TABLE IF EXISTS `decision_taobao_order`;
CREATE TABLE `decision_taobao_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `channel` tinyint(2) DEFAULT '1' COMMENT '1：新颜  2：魔蝎',
  `order_no` varchar(50) NOT NULL COMMENT '第三方返回的商户ID',
  `report_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '报告状态 0：未知 1：成功  2：失败 3：进行中',
  `data_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '数据状态 0：未知 1：成功  2：失败 3：进行中',
  `report_result` varchar(500) DEFAULT NULL COMMENT '报告数据',
  `data_result` varchar(500) DEFAULT NULL COMMENT '原始数据',
  `data_times` int(2) NOT NULL DEFAULT '0' COMMENT '原始数据获取次数',
  `report_times` int(2) NOT NULL DEFAULT '0' COMMENT '报告数据获取次数',
  `callback` varchar(500) NOT NULL COMMENT '回调地址',
  `data_token` varchar(200) DEFAULT NULL COMMENT '原始数据token',
  `report_token` varchar(200) DEFAULT NULL COMMENT '报告数据token',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `taobao_order_orderno` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='淘宝订单记录';

-- ----------------------------
--  Table structure for `decision_ud_cloud_phase`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_cloud_phase`;
CREATE TABLE `decision_ud_cloud_phase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `score` int(11) DEFAULT NULL COMMENT '综合行为评分',
  `result` text COMMENT '返回结果',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_ud_record_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾云相记录';

-- ----------------------------
--  Table structure for `decision_ud_credit`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_credit`;
CREATE TABLE `decision_ud_credit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL COMMENT '报告编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `last_modified_time` datetime NOT NULL COMMENT '用户档案最后更新时间',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `address` varchar(500) DEFAULT NULL COMMENT '住址',
  `names` varchar(500) DEFAULT NULL COMMENT '曾用名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `province` varchar(50) DEFAULT NULL COMMENT '出生省',
  `city` varchar(50) DEFAULT NULL COMMENT '出生城市',
  `issuing_agency` varchar(100) DEFAULT NULL COMMENT '签发机构',
  `id_number_mask` varchar(50) DEFAULT NULL COMMENT '身份证掩码',
  `name_credible` varchar(50) DEFAULT NULL COMMENT '可信姓名',
  `mobile_count` int(5) DEFAULT NULL COMMENT '使用手机号个数',
  `link_user_count` int(5) DEFAULT NULL COMMENT '关联用户数',
  `other_link_device_count` int(5) DEFAULT NULL COMMENT '其他关联设备数',
  `partner_mark_count` int(5) DEFAULT NULL COMMENT '商户标记个数',
  `court_dishonest_count` int(5) DEFAULT NULL COMMENT '法院失信',
  `online_dishonest_count` int(5) DEFAULT NULL COMMENT '网贷失信',
  `living_attack_count` int(5) DEFAULT NULL COMMENT '活体攻击行为',
  `user_have_bankcard_count` int(5) DEFAULT NULL COMMENT '名下银行卡数',
  `other_fraud_device_count` int(5) DEFAULT NULL COMMENT '可疑设备',
  `other_living_attack_device_count` int(5) DEFAULT NULL COMMENT '活体攻击设备',
  `partner_user_count` int(5) DEFAULT NULL COMMENT '本商户内用户数',
  `bankcard_count` int(5) DEFAULT NULL COMMENT '关联银行卡数',
  `fraud_device_count` int(5) DEFAULT NULL COMMENT '可疑设备',
  `living_attack_device_count` int(5) DEFAULT NULL COMMENT '活体攻击设备',
  `link_device_count` int(5) DEFAULT NULL COMMENT '使用设备数',
  `actual_loan_platform_count` int(5) DEFAULT NULL COMMENT '实际借款平台数',
  `actual_loan_platform_count_1m` int(5) DEFAULT NULL COMMENT '近1月实际借款平台数',
  `actual_loan_platform_count_3m` int(5) DEFAULT NULL COMMENT '近3月实际借款平台数',
  `actual_loan_platform_count_6m` int(5) DEFAULT NULL COMMENT '近6月实际借款平台数',
  `loan_platform_count` int(5) DEFAULT NULL COMMENT '申请借款平台数',
  `repayment_times_count` int(5) DEFAULT NULL COMMENT '还款次数',
  `repayment_platform_count` int(5) DEFAULT NULL COMMENT '还款平台数',
  `loan_platform_count_1m` int(5) DEFAULT NULL COMMENT '近1月申请借款平台数',
  `loan_platform_count_3m` int(5) DEFAULT NULL COMMENT '近3月申请借款平台数',
  `loan_platform_count_6m` int(5) DEFAULT NULL COMMENT '近6月申请借款平台数',
  `repayment_platform_count_1m` int(5) DEFAULT NULL COMMENT '近1月还款平台数',
  `repayment_platform_count_3m` int(5) DEFAULT NULL COMMENT '近3月还款平台数',
  `repayment_platform_count_6m` int(5) DEFAULT NULL COMMENT '近6月还款平台数',
  `score` int(5) DEFAULT NULL COMMENT '评估模型得分  1- 99分，分值越高风险等级越大 若score=0，表示缺乏足够信息判断风险',
  `risk_evaluation` varchar(50) DEFAULT NULL COMMENT '风险等级',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ud_credit_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾云慧眼数据';

-- ----------------------------
--  Table structure for `decision_ud_credit_devices`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_credit_devices`;
CREATE TABLE `decision_ud_credit_devices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ud_credit_id` bigint(20) NOT NULL COMMENT '有盾报告ID',
  `device_name` varchar(50) DEFAULT NULL COMMENT '设备名',
  `device_id` varchar(100) DEFAULT NULL COMMENT '设备id',
  `device_last_use_date` date DEFAULT NULL COMMENT '设备最后使用日期',
  `device_link_id_count` int(5) DEFAULT NULL COMMENT '设备关联用户数',
  `cheats_device` tinyint(2) DEFAULT NULL COMMENT '是否安装作弊软件 1 表示有 0 表示无',
  `is_rooted` tinyint(2) DEFAULT NULL COMMENT '是否root 1 是 0 不是',
  `fraud_device` tinyint(2) DEFAULT NULL COMMENT '可疑设备  1 是 0 不是',
  `is_using_proxy_port` tinyint(2) DEFAULT NULL COMMENT '是否使用代理  1 是 0 不是',
  `app_instalment_count` int(5) DEFAULT NULL COMMENT '借贷app安装数量',
  `network_type` varchar(50) DEFAULT NULL COMMENT '网络类型',
  `living_attack` tinyint(2) DEFAULT NULL COMMENT '有无活体攻击行为   1 表示有，0 表示无',
  PRIMARY KEY (`id`),
  KEY `ud_credit_device_idx` (`ud_credit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾设备信息';

-- ----------------------------
--  Table structure for `decision_ud_credit_loan_industry`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_credit_loan_industry`;
CREATE TABLE `decision_ud_credit_loan_industry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ud_credit_id` bigint(20) NOT NULL COMMENT '有盾报告ID',
  `actual_loan_platform_count` int(5) DEFAULT NULL COMMENT '实际借款平台数',
  `name` varchar(200) DEFAULT NULL COMMENT '行业名称',
  `loan_platform_count` int(5) DEFAULT NULL COMMENT '申请借款平台数',
  `repayment_times_count` int(5) DEFAULT NULL COMMENT '还款次数',
  `repayment_platform_count` int(5) DEFAULT NULL COMMENT '还款平台数',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ud_credit_loan_industry_creditid_idx` (`ud_credit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾行业借款详情';

-- ----------------------------
--  Table structure for `decision_ud_credit_user_feature`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_credit_user_feature`;
CREATE TABLE `decision_ud_credit_user_feature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ud_credit_id` bigint(20) NOT NULL COMMENT '有盾报告ID',
  `feature_type` varchar(5) NOT NULL COMMENT '特征值',
  `last_modified_date` date DEFAULT NULL COMMENT '最后命中特征日期',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_ud_credit_user_feature_ud_credit_id_index` (`ud_credit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾 用户特征值';

-- ----------------------------
--  Table structure for `decision_ud_features_dic`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_features_dic`;
CREATE TABLE `decision_ud_features_dic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(5) DEFAULT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `decision_ud_features_dic_code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾用户特征字典';

-- ----------------------------
--  Table structure for `decision_ud_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_ud_record`;
CREATE TABLE `decision_ud_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `request_params` text COMMENT '请求参数',
  `type` tinyint(2) NOT NULL COMMENT '1：云慧眼接口 2：实名认证接口 3：有盾银行卡鉴权接口 4：云相',
  `result` text COMMENT '返回结果',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_ud_record_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='有盾调用记录';

-- ----------------------------
--  Table structure for `decision_user`
-- ----------------------------
DROP TABLE IF EXISTS `decision_user`;
CREATE TABLE `decision_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `id_number` varchar(50) NOT NULL COMMENT '身份证号',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_user_id_phone_idx` (`company_id`,`mobile`,`id_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
--  Table structure for `decision_xy_action_radar`
-- ----------------------------
DROP TABLE IF EXISTS `decision_xy_action_radar`;
CREATE TABLE `decision_xy_action_radar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL COMMENT '报告编号',
  `user_id` bigint(20) NOT NULL,
  `id_no` varchar(50) NOT NULL COMMENT '身份证号',
  `id_name` varchar(20) NOT NULL COMMENT '姓名',
  `versions` varchar(20) DEFAULT NULL COMMENT '业务版本号',
  `loans_score` int(10) unsigned DEFAULT NULL COMMENT '贷款行为分:1-1000分表示新颜征信对于该用户的贷款行为的评估',
  `loans_credibility` int(10) DEFAULT NULL COMMENT '贷款行为置信度:1-100表示新颜征信计算出来的贷款行为分的置信度',
  `loans_count` int(10) DEFAULT NULL COMMENT '贷款放款总订单数:1-N表示命中查询主体的总贷款放款订单数(12个月内)',
  `loans_settle_count` int(10) DEFAULT NULL COMMENT '贷款已结清订单数:1-N表示命中查询主体的已结清贷款订单数(12个月内)',
  `loans_overdue_count` int(10) DEFAULT NULL COMMENT '贷款逾期订单数:1-N表示命中查询主体的逾期贷款（M0+）订单数',
  `loans_org_count` int(10) DEFAULT NULL COMMENT '网络贷款类机构数:1-N表示命中查询主体贷款放款的消费金融类机构数(12个月内)',
  `consfin_org_count` int(10) DEFAULT NULL COMMENT '消费金融类机构数1-N表示命中查询主体贷款放款的消费金融类机',
  `loans_cash_count` int(10) DEFAULT NULL COMMENT '网络贷款类机构数:1-N表示命中查询主体贷款放款的网络贷款类机构数(12个月内)',
  `latest_one_month` int(10) DEFAULT NULL COMMENT '近1个月贷款笔数:1-N表示命中查询主体的近1个月的贷款放款笔数',
  `latest_three_month` int(10) DEFAULT NULL COMMENT '近3个月贷款笔数:1-N表示命中查询主体的近3个月的贷款放款笔数',
  `latest_six_month` int(10) DEFAULT NULL COMMENT '近6个月贷款笔数：1-N表示命中查询主体的近6个月的贷款放款笔数',
  `history_suc_fee` int(10) DEFAULT NULL COMMENT '历史贷款机构成功扣款笔数：1-N表示命中查询主体的贷款机构历史成功扣款笔数(12个月内)',
  `history_fail_fee` int(10) DEFAULT NULL COMMENT '历史贷款机构失败扣款笔数：1-N表示命中查询主体的贷款机构历史失败扣款笔数(12个月内)',
  `latest_one_month_suc` int(10) DEFAULT NULL COMMENT '近1个月贷款机构成功扣款笔数：1-N表示命中查询主体的近一个月的贷款机构成功扣款笔数',
  `latest_one_month_fail` int(10) DEFAULT NULL COMMENT '近1个月贷款机构失败扣款笔数 1-N表示命中查询主体的近一个月的贷款机构失',
  `loans_long_time` int(11) DEFAULT NULL COMMENT '信用贷款时长:1-N表示命中查询主体的第一次贷款放款记录至今的天数',
  `loans_latest_time` date DEFAULT NULL COMMENT '最近一次贷款时间:yyyy-mm-dd表示主体的最后一次贷款放款记录时间(12个月内)',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `xy_radar_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='新颜行为雷达报告';

-- ----------------------------
--  Table structure for `decision_xy_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_xy_record`;
CREATE TABLE `decision_xy_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `request_params` text COMMENT '请求参数',
  `type` tinyint(2) NOT NULL COMMENT '1:行为雷达 2：银行卡鉴权',
  `result` text COMMENT '返回结果',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_radar_record_userid_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='新颜调用记录';

-- ----------------------------
--  Table structure for `decision_zhimi_record`
-- ----------------------------
DROP TABLE IF EXISTS `decision_zhimi_record`;
CREATE TABLE `decision_zhimi_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request_id` varchar(50) DEFAULT NULL COMMENT '指迷返回的请求ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `result` text COMMENT '返回结果',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='指迷请求记录表';

-- ----------------------------
--  Table structure for `decision_zmxy_area_dic`
-- ----------------------------
DROP TABLE IF EXISTS `decision_zmxy_area_dic`;
CREATE TABLE `decision_zmxy_area_dic` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `id_prefix` varchar(5) DEFAULT NULL COMMENT '身份证前缀',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='芝麻信用分地区字典表';

-- ----------------------------
--  Records of `decision_zmxy_area_dic`
-- ----------------------------
BEGIN;
INSERT INTO `decision_zmxy_area_dic` VALUES ('1', '安徽', '34'), ('2', '北京', '11'), ('3', '福建', '35'), ('4', '甘肃', '62'), ('5', '广东', '44'), ('6', '广西', '45'), ('7', '贵州', '52'), ('8', '海南', null), ('9', '河北', null), ('10', '河南', null), ('11', '黑龙江', null), ('12', '湖北', null), ('13', '湖南', null), ('14', '吉林', null), ('15', '江苏', null), ('16', '江西', null), ('17', '辽宁', null), ('18', '内蒙古', null), ('19', '宁夏', null), ('20', '青海', null), ('21', '山东', null), ('22', '山西', null), ('23', '陕西', null), ('24', '上海', null), ('25', '四川', null), ('26', '天津', null), ('27', '西藏', null), ('28', '新疆', null), ('29', '云南', null), ('30', '浙江', null), ('31', '重庆', null);
COMMIT;

-- ----------------------------
--  Table structure for `decision_zmxy_config`
-- ----------------------------
DROP TABLE IF EXISTS `decision_zmxy_config`;
CREATE TABLE `decision_zmxy_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '编号',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `zmxy_score` int(5) NOT NULL COMMENT '芝麻分',
  `anti_fraud_score` int(5) NOT NULL COMMENT '反欺诈分',
  `area_id` int(11) NOT NULL COMMENT '地区',
  `is_open` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否开启 1：开启 0：关闭',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='芝麻信用决策参数配置表';

-- ----------------------------
--  Records of `decision_zmxy_config`
-- ----------------------------
BEGIN;
INSERT INTO `decision_zmxy_config` VALUES ('1', 'zmxyD01', '1', '580', '85', '1', '1', '2018-07-31 19:20:16', null), ('2', 'zmxyD02', '1', '580', '85', '2', '1', '2018-07-31 19:21:20', null), ('3', 'zmxyD03', '1', '590', '85', '3', '1', '2018-07-31 19:20:16', null), ('4', 'zmxyD04', '1', '590', '85', '4', '1', '2018-07-31 19:20:16', null), ('5', 'zmxyD05', '1', '580', '80', '5', '1', '2018-07-31 19:20:16', null), ('6', 'zmxyD06', '1', '580', '85', '6', '1', '2018-07-31 19:20:16', null), ('7', 'zmxyD07', '1', '590', '85', '7', '1', '2018-07-31 19:20:16', null), ('8', 'zmxyD08', '1', '600', '85', '8', '1', '2018-07-31 19:20:16', null), ('9', 'zmxyD09', '1', '590', '85', '9', '1', '2018-07-31 19:20:16', null), ('10', 'zmxyD10', '1', '590', '85', '10', '1', '2018-07-31 19:20:16', null), ('11', 'zmxyD11', '1', '580', '85', '11', '1', '2018-07-31 19:20:16', null), ('12', 'zmxyD12', '1', '580', '85', '12', '1', '2018-07-31 19:20:16', null), ('13', 'zmxyD13', '1', '580', '85', '13', '1', '2018-07-31 19:20:16', null), ('14', 'zmxyD14', '1', '580', '85', '14', '1', '2018-07-31 19:20:16', null), ('15', 'zmxyD15', '1', '580', '85', '15', '1', '2018-07-31 19:20:16', null), ('16', 'zmxyD16', '1', '590', '85', '16', '1', '2018-07-31 19:20:16', null), ('17', 'zmxyD17', '1', '580', '85', '17', '1', '2018-07-31 19:20:16', null), ('18', 'zmxyD18', '1', '600', '85', '18', '1', '2018-07-31 19:20:16', null), ('19', 'zmxyD19', '1', '590', '85', '19', '1', '2018-07-31 19:20:16', null), ('20', 'zmxyD20', '1', '600', '85', '20', '1', '2018-07-31 19:20:16', null), ('21', 'zmxyD21', '1', '580', '85', '21', '1', '2018-07-31 19:20:16', null), ('22', 'zmxyD22', '1', '590', '85', '22', '1', '2018-07-31 19:20:16', null), ('23', 'zmxyD23', '1', '580', '85', '23', '1', '2018-07-31 19:20:16', null), ('24', 'zmxyD24', '1', '580', '85', '24', '1', '2018-07-31 19:20:16', null), ('25', 'zmxyD25', '1', '600', '85', '25', '1', '2018-07-31 19:20:16', null), ('26', 'zmxyD26', '1', '580', '80', '26', '1', '2018-07-31 19:20:16', null), ('27', 'zmxyD27', '1', '610', '90', '27', '1', '2018-07-31 19:20:16', null), ('28', 'zmxyD28', '1', '600', '90', '28', '1', '2018-07-31 19:20:16', null), ('29', 'zmxyD29', '1', '590', '85', '29', '1', '2018-07-31 19:20:16', null), ('30', 'zmxyD30', '1', '580', '80', '30', '1', '2018-07-31 19:20:16', null), ('31', 'zmxyD31', '1', '580', '85', '31', '1', '2018-07-31 19:20:16', null);
COMMIT;

-- ----------------------------
--  Table structure for `decision_zmxy_config_company`
-- ----------------------------
DROP TABLE IF EXISTS `decision_zmxy_config_company`;
CREATE TABLE `decision_zmxy_config_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主配置ID',
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1：有效 0：删除',
  `zmxy_score` int(5) NOT NULL COMMENT '芝麻分',
  `anti_fraud_score` int(5) NOT NULL COMMENT '反欺诈分',
  `is_open` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否开启 1：开启 0：关闭',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='芝麻信用商户决策参数配置表';

SET FOREIGN_KEY_CHECKS = 1;
