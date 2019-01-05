/*
Navicat MySQL Data Transfer

Source Server         : gyy_db
Source Server Version : 50717
Source Host           : 192.168.88.221:3306
Source Database       : gyy_db

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-02 17:00:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_workticket_fire
-- ----------------------------
DROP TABLE IF EXISTS `tb_workticket_fire`;
CREATE TABLE `tb_workticket_fire` (
  `C_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `C_WORKTICKET_ID` bigint(20) DEFAULT NULL COMMENT '工作票表的id',
  `C_OTHER_EXECUTOR_ID` bigint(20) DEFAULT NULL COMMENT '动火执行人1ID',
  `C_OTHER_EXECUTOR` varchar(64) DEFAULT NULL COMMENT '动火执行人1名字',
  `C_OTHER_CODE` varchar(64) DEFAULT NULL COMMENT '操作证编号1',
  `C_OTHER_EXECUTOR_TWO` varchar(64) DEFAULT NULL COMMENT '动火执行人2',
  `C_OTHER_CODE_TWO` varchar(64) DEFAULT NULL COMMENT '操作证编号2',
  `C_OTHER_EXECUTOR_THREE` varchar(64) DEFAULT NULL COMMENT '动火执行人3',
  `C_OTHER_CODE_THREE` varchar(64) DEFAULT NULL COMMENT '操作证编号3',
  `C_OTHER_STYLE` varchar(128) DEFAULT NULL COMMENT '动火方式',
  `C_OTHER_STYLE_OTHER` varchar(128) DEFAULT NULL COMMENT '动火方式其他',
  `C_OTHER_APPROVE_FIRE_ID` bigint(20) DEFAULT NULL COMMENT '审核人，消防管理部门负责人ID',
  `C_OTHER_APPROVE_FIRE` varchar(64) DEFAULT NULL COMMENT '审核人，消防管理部门负责人',
  `C_OTHER_APPROVE_FIRE_DATE` datetime DEFAULT NULL,
  `C_OTHER_APPROVE_SAFE_ID` bigint(20) DEFAULT NULL COMMENT '审核人，安检部门负责人ID',
  `C_OTHER_APPROVE_SAFE` varchar(64) DEFAULT NULL COMMENT '审核人，安检部门负责人',
  `C_SIGNER_ID` bigint(20) DEFAULT NULL COMMENT '工作票签发人ID（签发）',
  `C_SIGNER_NAME` varchar(64) DEFAULT NULL COMMENT '工作票签发人名字（签发）',
  `C_SIGNER_DATE` datetime DEFAULT NULL COMMENT '签发日期（签发）',
  `C_APPROVE_STARTTIME` datetime DEFAULT NULL COMMENT '批准工作时间开始(批准人）',
  `C_APPROVE_ENDTIME` datetime DEFAULT NULL COMMENT '批准工作时间结束(批准人）',
  `C_DUTY_MONITOR_ID` bigint(20) DEFAULT NULL COMMENT 'ID(批准人）',
  `C_DUTY_MONITOR_NAME` varchar(64) DEFAULT NULL COMMENT '姓名(批准人）\r\n            (批准人）',
  `C_DUTY_MONTITOR_DATE` datetime DEFAULT NULL COMMENT '批准人填写时间',
  `C_OTHER_INSTRUMENT` varchar(128) DEFAULT NULL COMMENT '使用仪器',
  `C_FIRE_USER_NAME_ID` bigint(20) DEFAULT NULL,
  `C_ONDUTY_ID` bigint(20) DEFAULT NULL,
  `C_ONDUTY_NAME` varchar(255) DEFAULT NULL,
  `C_WORK_DISCLOSURE` varchar(255) DEFAULT NULL COMMENT '安全交底',
  `C_ONDUTY_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `C_OTHER_EXECUTOR_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '测试，动火执行人签名ID',
  `C_OTHER_EXECUTOR_SIGN` varchar(64) DEFAULT NULL COMMENT '测试，动火执行人签名',
  `C_OTHER_FIRE_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '测试，消防监护人签名ID',
  `C_OTHER_FIRE_SIGN` varchar(64) DEFAULT NULL COMMENT '测试，消防监护人签名',
  `C_OTHER_FIRE_SIGN_DATE` datetime DEFAULT NULL COMMENT '消防监护填写时间',
  `C_OTHER_PIC_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '测试，动火工作负责人签名ID',
  `C_OTHER_PIC_SIGN` varchar(64) DEFAULT NULL COMMENT '测试，动火工作负责人签名',
  `C_OTHER_GROUP_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '测试，动火部门负责人签名ID',
  `C_OTHER_GROUP_SIGN` varchar(64) DEFAULT NULL COMMENT '测试，动火部门负责人签名',
  `C_OTHER_SAFE_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '测试，安监部门负责人ID',
  `C_OTHER_SAFE_SIGN` varchar(64) DEFAULT NULL COMMENT '测试，安监部门负责人',
  `C_OTHER_SAFE_SIGN_DATE` datetime DEFAULT NULL COMMENT '安监部门负责人填写时间',
  `C_OTHER_LEDER_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '测试，安全生产领导签名ID',
  `C_OTHER_LEDER_SIGN` varchar(64) DEFAULT NULL COMMENT '测试，安全生产领导签名',
  `C_OTHER_LEDER_SIGN_DATE` datetime DEFAULT NULL COMMENT '生产领导填写时间',
  `C_OTHEREND_EXECUTOR_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '结束*动火执行人签名ID',
  `C_OTHEREND_EXECUTOR_SIGN` varchar(64) DEFAULT NULL COMMENT '结束*动火执行人签名',
  `C_OTHEREND_FIRE_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '结束*消防监护人签名ID',
  `C_OTHEREND_FIRE_SIGN` varchar(64) DEFAULT NULL COMMENT '结束*消防监护人签名',
  `C_REMARK` text COMMENT '备注',
  `C_OTHER` text COMMENT '其他事项',
  `C_MEET_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '工作票会签人id（签发）',
  `C_MEET_SIGN_NAME` varchar(64) DEFAULT NULL COMMENT '会签人名字（签发）',
  `C_MEET_SIGN_DATE` datetime DEFAULT NULL COMMENT '会签日期（签发）',
  `C_SAFE_DIRCTOR_ID` bigint(20) DEFAULT NULL COMMENT '安全总监id（审批）',
  `C_SAFE_DIRCTOR_NAME` varchar(64) DEFAULT NULL COMMENT '安全总监（审批）',
  `C_SAFE_DIRCTOR_DATE` datetime DEFAULT NULL COMMENT '安全总监填写时间',
  `C_END_STAND` bigint(20) DEFAULT NULL COMMENT '未拆除（组）',
  `C_END_STAND_INDEX` varchar(128) DEFAULT NULL COMMENT '已拆除（组）',
  `C_END_HAND` varchar(64) DEFAULT NULL COMMENT '接地线总（组）',
  `C_FIRE_USER_ID` bigint(20) DEFAULT NULL COMMENT '动火负责人',
  `C_FIRE_USER_NAME` varchar(64) DEFAULT NULL COMMENT '动火负责人姓名',
  `C_RUN_FIRE_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '许可*消防监护人',
  `C_RUN_FIRE_SIGN_NAME` varchar(64) DEFAULT NULL COMMENT '许可*消防监护人姓名',
  `C_RUN_SAFE_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '许可*安监部门负责人',
  `C_RUN_SAFE_SIGN` varchar(64) DEFAULT NULL COMMENT '许可*安监部门负责人',
  `C_RUN_SAFE_DIRECTOR_ID` bigint(20) DEFAULT NULL COMMENT '许可*安全总监',
  `C_RUN_SAFE_DIRECTOR` varchar(64) DEFAULT NULL COMMENT '许可*安全总监',
  `C_RUN_LEDER_SIGN_ID` bigint(20) DEFAULT NULL COMMENT '分管生产领导（许可）',
  `C_RUN_LEDER_SIGN` varchar(64) DEFAULT NULL COMMENT '分管生产领导（许可）',
  `C_OTHER_APPROVE_SAFE_DATE` datetime DEFAULT NULL COMMENT '安监部门负责人填写时间',
  PRIMARY KEY (`C_ID`),
  KEY `FK_Reference_7` (`C_WORKTICKET_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='动火工作票TB_WORKTICKET_FIRE';
