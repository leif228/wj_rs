/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.4.15:3306
 Source Schema         : wjdb

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 29/09/2021 11:12:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area_chang_seq
-- ----------------------------
DROP TABLE IF EXISTS `area_chang_seq`;
CREATE TABLE `area_chang_seq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ascii10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fzw_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `one_tab` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `two_tab` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `three_tab` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_area
-- ----------------------------
DROP TABLE IF EXISTS `bs_area`;
CREATE TABLE `bs_area`  (
  `AREA_ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `AREA_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区代码',
  `CITY_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级市代码',
  `AREA_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市名称',
  `SHORT_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简称',
  `LNG` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `LAT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `SORT` int(0) NULL DEFAULT NULL COMMENT '排序',
  `GMT_CREATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `GMT_MODIFIED` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `MEMO` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DATA_STATE` int(0) NULL DEFAULT NULL COMMENT '状态',
  `TENANT_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`AREA_ID`) USING BTREE,
  INDEX `Index_1`(`AREA_CODE`, `TENANT_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3679 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地区设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_city
-- ----------------------------
DROP TABLE IF EXISTS `bs_city`;
CREATE TABLE `bs_city`  (
  `CITY_ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `CITY_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市代码',
  `CITY_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市名称',
  `SHORT_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简称',
  `PROVINCE_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省代码',
  `LNG` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `LAT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `SORT` int(0) NULL DEFAULT NULL COMMENT '排序',
  `GMT_CREATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `GMT_MODIFIED` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `MEMO` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DATA_STATE` int(0) NULL DEFAULT NULL COMMENT '状态',
  `TENANT_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`CITY_ID`) USING BTREE,
  INDEX `Index_1`(`CITY_CODE`, `TENANT_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 391 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '城市设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_province
-- ----------------------------
DROP TABLE IF EXISTS `bs_province`;
CREATE TABLE `bs_province`  (
  `PROVINCE_ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `PROVINCE_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份代码',
  `PROVINCE_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份名称',
  `SHORT_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简称',
  `LNG` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `LAT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `SORT` int(0) NULL DEFAULT NULL COMMENT '排序',
  `GMT_CREATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `GMT_MODIFIED` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `MEMO` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DATA_STATE` int(0) NULL DEFAULT NULL COMMENT '状态',
  `TENANT_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`PROVINCE_ID`) USING BTREE,
  INDEX `Index_1`(`PROVINCE_CODE`, `TENANT_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省份设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_street
-- ----------------------------
DROP TABLE IF EXISTS `bs_street`;
CREATE TABLE `bs_street`  (
  `STREET_ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `STREET_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '街道代码',
  `AREA_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级区代码',
  `STREET_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '街道名称',
  `SHORT_NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简称',
  `LNG` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `LAT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `SORT` int(0) NULL DEFAULT NULL COMMENT '排序',
  `GMT_CREATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `GMT_MODIFIED` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `MEMO` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DATA_STATE` int(0) NULL DEFAULT NULL COMMENT '状态',
  `TENANT_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`STREET_ID`) USING BTREE,
  INDEX `Index_1`(`STREET_CODE`, `TENANT_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42361 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '街道设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for buss_info
-- ----------------------------
DROP TABLE IF EXISTS `buss_info`;
CREATE TABLE `buss_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `business_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `priority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `business_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `command` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `para` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `txt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for com_dev_opt
-- ----------------------------
DROP TABLE IF EXISTS `com_dev_opt`;
CREATE TABLE `com_dev_opt`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `devtype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'devtype设备类型',
  `dev_company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'dev_company设备厂商',
  `dev_company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'dev_company_name设备厂商名',
  `dev_company_devtype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'dev_company_devtype设备厂商型号',
  `dev_company_devtype_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'dev_company_devtype_name设备名',
  `buss` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'buss业务',
  `ability_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ability_name功能名',
  `way` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'way方式',
  `port_max` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'port_max端口数',
  `at_para` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'at_para AT命令中的参数',
  `command` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'user表id',
  `device_type` int(0) NULL DEFAULT NULL COMMENT '0通用服务器1网关',
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fzwno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '泛在网编号',
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 206 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for devtype
-- ----------------------------
DROP TABLE IF EXISTS `devtype`;
CREATE TABLE `devtype`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `dev_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dev_type_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `main_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `son_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sub_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `end_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for driver_comp
-- ----------------------------
DROP TABLE IF EXISTS `driver_comp`;
CREATE TABLE `driver_comp`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `comp_cn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `comp_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for eventtype_buss_md_info
-- ----------------------------
DROP TABLE IF EXISTS `eventtype_buss_md_info`;
CREATE TABLE `eventtype_buss_md_info`  (
  `id` bigint(0) NOT NULL,
  `event_type_info_id` bigint(0) NULL DEFAULT NULL,
  `buss_id` bigint(0) NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for eventtype_tradecode_md_info
-- ----------------------------
DROP TABLE IF EXISTS `eventtype_tradecode_md_info`;
CREATE TABLE `eventtype_tradecode_md_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `event_type_info_id` bigint(0) NULL DEFAULT NULL,
  `trade_code_info_id` bigint(0) NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fzwno
-- ----------------------------
DROP TABLE IF EXISTS `fzwno`;
CREATE TABLE `fzwno`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `fzw_relation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fzw_device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `devtype_id` int(0) NOT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login_server
-- ----------------------------
DROP TABLE IF EXISTS `login_server`;
CREATE TABLE `login_server`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `server_port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `server_oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ower_server_oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trades` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lft` bigint(0) NOT NULL,
  `rgt` bigint(0) NOT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for node_info_ower
-- ----------------------------
DROP TABLE IF EXISTS `node_info_ower`;
CREATE TABLE `node_info_ower`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `root_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fzwno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `device_type` int(0) NULL DEFAULT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for node_standby
-- ----------------------------
DROP TABLE IF EXISTS `node_standby`;
CREATE TABLE `node_standby`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `node_id` bigint(0) NOT NULL COMMENT '关联node表id',
  `device_id` bigint(0) NULL DEFAULT NULL COMMENT '关联device表id(无用)',
  `type` int(0) NOT NULL DEFAULT 0 COMMENT '0主服务1备份服务',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `devtype_id` int(0) NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_event_info
-- ----------------------------
DROP TABLE IF EXISTS `sds_event_info`;
CREATE TABLE `sds_event_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `event_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_type_info_id` bigint(0) NULL DEFAULT NULL,
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 182 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_event_person_record
-- ----------------------------
DROP TABLE IF EXISTS `sds_event_person_record`;
CREATE TABLE `sds_event_person_record`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `gen_oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `origin_event_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_type_info_id` bigint(0) NULL DEFAULT NULL,
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_event_relation
-- ----------------------------
DROP TABLE IF EXISTS `sds_event_relation`;
CREATE TABLE `sds_event_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `event_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_relation_json` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_trade_oids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `event_manual_oids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_event_type_info
-- ----------------------------
DROP TABLE IF EXISTS `sds_event_type_info`;
CREATE TABLE `sds_event_type_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id id',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型 类型1、健康事件...',
  `event_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '事件名称 健康事件',
  `event_relation_level` int(0) NULL DEFAULT NULL COMMENT '事件关系阀值（0~100） 事件对应关系表权重值（取关系权重值大于该值为该事件相关关系）',
  `weight` int(0) NULL DEFAULT NULL COMMENT '事件权重（0~100） 事件的优先级，数字越大，越优先',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人 事件更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间 事件更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'sds事件类型表 sds事件类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_percom_info
-- ----------------------------
DROP TABLE IF EXISTS `sds_percom_info`;
CREATE TABLE `sds_percom_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id id',
  `type` int(0) NULL DEFAULT NULL COMMENT '类别 1、个人2、组织',
  `self_oid` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'oid关系段 oid',
  `name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `sex` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `card` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份唯一标识',
  `address` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `head_icon_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像路径',
  `major` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主业',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = ' ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_percom_relation
-- ----------------------------
DROP TABLE IF EXISTS `sds_percom_relation`;
CREATE TABLE `sds_percom_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `self_oid` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自己oid',
  `target_oid` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标oid',
  `target_ower_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标oid管理服务器ip',
  `relation_type_info_id` bigint(0) NULL DEFAULT NULL COMMENT '关系类型表id',
  `weight` int(0) NULL DEFAULT NULL COMMENT '联系度（0~10000动态变化）',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = ' ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sds_relation_type_info
-- ----------------------------
DROP TABLE IF EXISTS `sds_relation_type_info`;
CREATE TABLE `sds_relation_type_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id id',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型 类型：1、夫妻2、父母3、兄弟姐妹4、亲戚5、朋友6、员工...',
  `relation_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关系名称 如：夫妻',
  `weight` int(0) NULL DEFAULT NULL COMMENT '关系权重（0~100） 关系的优先级，数字越大，越优先',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人 关系更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间 关系更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'sds关系类型表 sds关系类型表（后期）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_cfg_tbl
-- ----------------------------
DROP TABLE IF EXISTS `system_cfg_tbl`;
CREATE TABLE `system_cfg_tbl`  (
  `key_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `key_value` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `memo` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`key_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置表，存所有系统参数，与代码中的静太变量同步' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tabs_version
-- ----------------------------
DROP TABLE IF EXISTS `tabs_version`;
CREATE TABLE `tabs_version`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `tab_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `version` bigint(0) NULL DEFAULT 1,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_code_info
-- ----------------------------
DROP TABLE IF EXISTS `trade_code_info`;
CREATE TABLE `trade_code_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `class_1st` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `class_2nd` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `class_3rd` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `class_4th` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `explains` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1773 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wjuser
-- ----------------------------
DROP TABLE IF EXISTS `wjuser`;
CREATE TABLE `wjuser`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pass_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `idcard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ower_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_type` int(0) NULL DEFAULT NULL COMMENT '0个人1团体',
  `p_sort` int(0) NULL DEFAULT NULL,
  `c_sort` int(0) NULL DEFAULT NULL,
  `a_sort` int(0) NULL DEFAULT NULL,
  `s_sort` int(0) NULL DEFAULT NULL,
  `creat_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wjuser_ower
-- ----------------------------
DROP TABLE IF EXISTS `wjuser_ower`;
CREATE TABLE `wjuser_ower`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pass_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `idcard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ower_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `head_icon_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_type` int(0) NULL DEFAULT NULL COMMENT '0个人1团体',
  `p_sort` int(0) NULL DEFAULT NULL,
  `c_sort` int(0) NULL DEFAULT NULL,
  `a_sort` int(0) NULL DEFAULT NULL,
  `s_sort` int(0) NULL DEFAULT NULL,
  `trades` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'trade表ids 如：1,3,34',
  `creat_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wjuser_trade
-- ----------------------------
DROP TABLE IF EXISTS `wjuser_trade`;
CREATE TABLE `wjuser_trade`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trades` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'trade表ids 如：1,3,34',
  `updata_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for tree1
-- ----------------------------
DROP VIEW IF EXISTS `tree1`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tree1` AS select `node`.`id` AS `id`,`node`.`name` AS `name`,`node`.`lft` AS `lft`,`node`.`rgt` AS `rgt`,`node`.`creat_time` AS `creat_time` from `node` where (`node`.`id` = 1);

-- ----------------------------
-- View structure for tree2
-- ----------------------------
DROP VIEW IF EXISTS `tree2`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tree2` AS select `n`.`id` AS `id`,`n`.`name` AS `name`,`n`.`lft` AS `lft`,`n`.`rgt` AS `rgt` from (`node` `n` join `tree1` `a`) where (`n`.`lft` between `a`.`lft` and `a`.`rgt`) order by `n`.`lft`;

SET FOREIGN_KEY_CHECKS = 1;
