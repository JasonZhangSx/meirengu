ALTER TABLE
`user_center`.`user`
	CHANGE `register_time` `register_time` DATETIME DEFAULT '1970-01-01 08:00:00' NOT NULL COMMENT '注册时间',
	CHANGE `login_time` `login_time` DATETIME DEFAULT '1970-01-01 08:00:00' NOT NULL COMMENT '登录时间',
	CHANGE `last_login_time` `last_login_time` DATETIME DEFAULT '1970-01-01 08:00:00' NOT NULL COMMENT '上次登录时间';

ALTER TABLE `user` ADD `sina` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '新浪昵称';
ALTER TABLE `user_contract` ADD `order_id` int(11) NOT NULL DEFAULT '0' COMMENT '订单id';

CREATE TABLE `user_expand` (
   `Id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户id',
   `register_from` tinyint(3) NOT NULL DEFAULT '0' COMMENT '注册来源：1:pc，2:h5，3:ios，4:android，5：三方',
   `register_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '注册时间',
   `login_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '登录时间',
   `last_login_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '上次登录时间',
   `login_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '登陆ip',
   `last_login_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '上次登录ip',
   `login_from` tinyint(3) NOT NULL DEFAULT '0' COMMENT '登陆来源：1:pc，2:h5，3:ios，4:android，5：三方',
   `login_num` int(10) NOT NULL DEFAULT '0' COMMENT '登陆次数',
   `is_auth` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否认证，默认为0未认证，1已认证',
   `is_buy` tinyint(1) DEFAULT '0' COMMENT '会员是否有购买权限 1为开启 0为关闭',
   `is_allow_inform` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许举报(1可以/0不可以)',
   `is_allow_talk` tinyint(1) NOT NULL DEFAULT '0' COMMENT '会员是否有咨询和发送站内信的权限 1为开启 0为关闭',
   `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '会员的开启状态 1为开启 0为关闭',
   PRIMARY KEY (`Id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息扩展表';


CREATE TABLE `user_idcard` (
   `Id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户id',
   `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '用户账号',
   `realname` varchar(50) NOT NULL DEFAULT '' COMMENT '实名',
   `bank_code` varchar(20) NOT NULL DEFAULT '' COMMENT '银行卡编码',
   `bank_idcard` varchar(18) NOT NULL DEFAULT '' COMMENT '银行卡',
   `bank_phone` varchar(11) NOT NULL DEFAULT '' COMMENT '预留手机号',
   `idcard` varchar(18) NOT NULL DEFAULT '' COMMENT '身份证',
   `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认',
   `invest_conditions` tinyint(3) NOT NULL DEFAULT '0' COMMENT '投资人条件：0未认证 1专业投资人 2:近三年年收入不低于30w 3: 金融资产不低于100w',
   `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00',
   `update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00',
   `operate_account` varchar(20) NOT NULL DEFAULT '' COMMENT '操作账号',
   PRIMARY KEY (`Id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户银行卡表'

INSERT INTO `user_center`.`user_expand` (
	`user_id`,
	`register_from`,
	`register_time`,
	`login_time`,
	`last_login_time`,
	`login_ip`,
	`last_login_ip`,
	`login_from`,
	`login_num`,
	`is_auth`,
	`is_buy`,
	`is_allow_inform`,
	`is_allow_talk`,
	`state`
) SELECT
	user_id,
	register_from,
	register_time,
	login_time,
	last_login_time,
	login_ip,
	last_login_ip,
	login_from,
	login_num,
	is_auth,
	is_buy,
	is_allow_inform,
	is_allow_talk,
	state
FROM
	`user_center`.`user`;

select * from user_expand;

INSERT INTO `user_center`.`user_idcard`(
	`user_id`,
	`phone`,
	`realname`,
	`bank_code`,
	`bank_idcard`,
	`bank_phone`,
	`idcard`,
	`is_default`,
	`invest_conditions`,
	`create_time`,
	`update_time`,
	`operate_account`
)
SELECT
	user_id,
	phone,
	realname,
	bank_code,
	bank_idcard,
	bank_phone,
	idcard,
	1,
	invest_conditions,
    now(),
	now(),
	phone
FROM `user_center`.`user`;

select * from user_idcard;

update `user` set `avatar` = "portrait/1.jpg" where  `avatar` = "portrait/1491015127467.jpg";
update `user` set `avatar` = "portrait/2.jpg" where  `avatar` = "portrait/1491015569805.jpg";

delete from `user` where `phone` in (15835132986,18516888365,18810716960);

SELECT
	*
FROM
	`user`
WHERE
	`user_id` = "165444096";

INSERT INTO `user_center`.`user` (
	`user_id`,
	`nickname`,
	`realname`,
	`phone`,
	`idcard`,
	`password`,
	`bank_code`,
	`bank_idcard`,
	`bank_phone`,
	`avatar`,
	`sex`,
	`birthday`,
	`email`,
	`qq`,
	`wx`,
	`sina`,
	`area_id`,
	`register_from`,
	`register_time`,
	`login_time`,
	`last_login_time`,
	`login_ip`,
	`last_login_ip`,
	`login_from`,
	`login_num`,
	`wx_openid`,
	`wx_info`,
	`qq_openid`,
	`qq_info`,
	`sina_openid`,
	`sina_info`,
	`is_auth`,
	`is_buy`,
	`is_allow_inform`,
	`is_allow_talk`,
	`state`,
	`invest_conditions`
)
VALUES
	(
		'165444096',
		'MRG_1001',
		'',
		'13800001001',
		'',
		'1000:3e8f171f8bf2d4f9d60edca46cda1cae05e07e7d2fbc9105:14b9f594cf8b110dcb191ac4aba8222b',
		'0',
		'',
		'',
		'portrait/1.jpg',
		'0',
		'2017-05-09',
		'',
		'',
		'',
		'',
		'0',
		'1',
		'2017-05-09 00:00:00',
		'2017-05-10 00:00:00',
		'2017-05-10 00:00:00',
		'127.0.0.1',
		'127.0.0.1',
		'1',
		'697',
		'',
		'',
		'',
		'',
		'',
		'',
		'0',
		'1',
		'1',
		'1',
		'1',
		'1'
	);





