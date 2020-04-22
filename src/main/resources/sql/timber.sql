CREATE TABLE `timber_consumer_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `consumer_name` varchar(128) DEFAULT NULL COMMENT '客户名称',
  `tel` varchar(64) DEFAULT NULL COMMENT '电话号',
  `remarks` varchar(2048) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(11) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(11) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='客户信息表';

CREATE TABLE `timber_consumer_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` bigint(11) DEFAULT NULL COMMENT '客户ID',
  `consumer_name` varchar(128) DEFAULT NULL COMMENT '客户名称',
  `consumer_address` varchar(1028) DEFAULT NULL COMMENT '客户地址',
  `tel` varchar(64) DEFAULT NULL COMMENT '客户电话',
  `access_time_date` date DEFAULT NULL COMMENT '操作时间',
  `install_date` date DEFAULT NULL COMMENT '客户安装时间',
  `contract_money` decimal(11,2) DEFAULT '0.00' COMMENT '客户合同金额',
  `money_one` decimal(11,2) DEFAULT '0.00' COMMENT '客户一期款',
  `money_two` decimal(11,2) DEFAULT '0.00' COMMENT '客户二期款',
  `money_three` decimal(11,2) DEFAULT '0.00' COMMENT '客户三期款',
  `disburse_money_one` decimal(11,2) DEFAULT '0.00' COMMENT '支出一期款',
  `factory_payment_money` decimal(11,2) DEFAULT '0.00' COMMENT '厂家打款',
  `gift` varchar(1024) DEFAULT NULL COMMENT '赠品',
  `gift_money` decimal(11,2) DEFAULT '0.00' COMMENT '赠品金额',
  `service_charge_money` decimal(11,2) DEFAULT '0.00' COMMENT '手续费',
  `kick_line_money` decimal(11,2) DEFAULT '0.00' COMMENT '踢脚线',
  `accessories_money` decimal(11,2) DEFAULT '0.00' COMMENT '辅料',
  `hardware_lock_money` decimal(11,2) DEFAULT NULL COMMENT '五金锁具',
  `sales_commission_money` decimal(11,2) DEFAULT '0.00' COMMENT '销售提成',
  `channel_commission_money` decimal(11,2) DEFAULT '0.00' COMMENT '渠道提成',
  `freight_money` decimal(11,2) DEFAULT '0.00' COMMENT '运费',
  `upstairs_fee_money` decimal(11,2) DEFAULT '0.00' COMMENT '上楼费',
  `install_fee_money` decimal(11,2) DEFAULT '0.00' COMMENT '安装费',
  `create_user_id` bigint(11) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(11) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='客户销售登记表';


CREATE TABLE `timber_self_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `access_time_date` date DEFAULT NULL COMMENT '记录时间',
  `descs` text COMMENT '摘要',
  `money` decimal(11,2) DEFAULT NULL COMMENT '金额',
  `type` int(11) DEFAULT NULL COMMENT '类型(1.收入，2.支出)',
  `create_user_id` bigint(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_user_id` bigint(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='个人收支登记表';

CREATE TABLE `timber_user_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `account_number` varchar(64) DEFAULT NULL COMMENT '账号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_user_id` bigint(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_user_id` bigint(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';


insert into `timber_user_info` (`id`, `name`, `account_number`, `password`, `create_user_id`, `create_date`, `update_user_id`, `update_date`) values('1','小波','admin','admin','1','2019-10-18 11:39:12','1','2019-10-18 11:39:16');


