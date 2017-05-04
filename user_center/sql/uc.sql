ALTER TABLE `user` ADD `sina` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '新浪昵称';
ALTER TABLE `user_contract` ADD `order_id` int(11) NOT NULL DEFAULT '0' COMMENT '订单id';