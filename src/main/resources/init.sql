# 区域表
CREATE TABLE `tb_area` (
  `id`  int(2) NOT NULL AUTO_INCREMENT ,
  `name`  varchar(200) NOT NULL COMMENT '名称' ,
  `priority`  int(2) NOT NULL DEFAULT 0 ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  PRIMARY KEY (`id`),
  UNIQUE key `UK_AREA`(`name`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
;


# mysql 2种引擎 INNODB   MYISAM（表级锁，更改记录会锁表。更新完之后另一个才可以更新，读取性能高）

# 用户表
CREATE TABLE `tb_person_info` (
  `id`  int(2) NOT NULL AUTO_INCREMENT,
  `name`  varchar(32) NULL DEFAULT NULL COMMENT '用户姓名' ,
  `profile_img`  varchar(1024) NULL DEFAULT NULL COMMENT '头像' ,
  `email`  varchar(1024) NULL DEFAULT NULL COMMENT '邮箱' ,
  `gender`  varchar(2) NULL DEFAULT NULL COMMENT '性别' ,
  `enable_status`  int(2) NOT NULL DEFAULT 0 COMMENT '状态 0：禁止使用，1允许使用' ,
  `type`  int(1) NOT NULL DEFAULT 1 COMMENT '类型 1.顾客  2.店家  3.超级管理员' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  PRIMARY KEY (`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
;


# 微信授权
CREATE TABLE `tb_wechat_auth` (
  `id`  int(10) NOT NULL AUTO_INCREMENT ,
  `user_id`  int(10) NOT NULL ,
  `open_id`  varchar(255) NOT NULL ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  PRIMARY KEY (`id`),
  CONSTRAINT	`fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE tb_wechat_auth add UNIQUE index(open_id);





# 本地授权
CREATE TABLE `tb_local_auth` (
  `id`  int(10) NOT NULL AUTO_INCREMENT ,
  `user_id`  int(10) NOT NULL ,
  `username`  varchar(128) NOT NULL ,
  `password`  varchar(128) NOT NULL ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_local_profile`(`username`),
  CONSTRAINT	`fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;





CREATE TABLE `tb_head_line` (
  `id`  int(100) NOT NULL AUTO_INCREMENT,
  `name`  varchar(1000)  DEFAULT NULL COMMENT '头条名称' ,
  `link`  varchar(2000)  NOT NULL COMMENT '头条连接' ,
  `img`  varchar(2000) NOT NULL COMMENT '图片' ,
  `priority`  int(2) DEFAULT NULL COMMENT '权重' ,
  `enable_status`  int(2) NOT NULL DEFAULT 0 COMMENT '状态 0：禁止使用，1允许使用' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  PRIMARY KEY (`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# 店铺分类
CREATE TABLE `tb_shop_category` (
  `id`  int(11) NOT NULL AUTO_INCREMENT,
  `name`  varchar(100)  NOT NULL DEFAULT  '' COMMENT '名称' ,
  `desc`  varchar(1000)  DEFAULT  '' COMMENT '描述' ,
  `img`  varchar(2000) DEFAULT NULL COMMENT '图片' ,
  `priority`  int(2) NOT NULL DEFAULT '0' COMMENT '权重' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  `parent_id` int(11) DEFAULT NULL ,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY(`parent_id`) references `tb_shop_category`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# 店铺
CREATE TABLE `tb_shop` (
  `id`  int(2) NOT NULL AUTO_INCREMENT,
  `name`  varchar(256)NOT NULL COMMENT '店铺名称' ,
  `desc`  varchar(1024)DEFAULT NULL COMMENT '描述' ,
  `addr`  varchar(200)DEFAULT NULL COMMENT '地址' ,
  `phone` varchar(128)DEFAULT NULL COMMENT '电话' ,
  `img`  varchar(1024) DEFAULT NULL COMMENT '宣传照' ,
  `priority` int(3) DEFAULT 0 COMMENT '权重' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  `enable_status`  int(2) DEFAULT 0 COMMENT '状态 0：禁止使用，1允许使用' ,
  `advice`  varchar(255) DEFAULT NULL COMMENT '性别' ,
  `area_id` int(5) DEFAULT NULL,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `shop_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_shop_area` foreign key (`area_id`) REFERENCES `tb_area`(`id`),
  CONSTRAINT `fk_shop_person` foreign key (`owner_id`) REFERENCES `tb_person_info`(`id`),
  CONSTRAINT `fk_shop_shopcate` foreign key (`shop_category_id`) REFERENCES `tb_shop_category`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# 商品分类
CREATE TABLE `tb_product_category` (
  `id`  int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(20) DEFAULT NULL ,
  `name`  varchar(100)  NOT NULL DEFAULT  '' COMMENT '名称' ,
  `priority`  int(2) NOT NULL DEFAULT '0' COMMENT '权重' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  primary key (`id`),
  CONSTRAINT `fk_procate_shop` FOREIGN KEY(`shop_id`) references `tb_shop`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# 商品
CREATE TABLE `tb_product` (
  `id`  int(2) NOT NULL AUTO_INCREMENT,
  `name`  varchar(100)NOT NULL COMMENT '名称' ,
  `desc`  varchar(2000)DEFAULT NULL COMMENT '描述' ,
  `img_addr`  varchar(2000)DEFAULT '' COMMENT '图片地址' ,
  `normal_price` varchar(100)DEFAULT NULL ,
  `promotion_price` varchar(100)DEFAULT NULL ,
  `priority` int(3) DEFAULT 0 COMMENT '权重' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `last_edit_time`  datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间' ,
  `enable_status`  int(2) DEFAULT 0  ,
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_procate` foreign key (`product_category_id`) REFERENCES `tb_product_category`(`id`),
  CONSTRAINT `fk_product_shop` foreign key (`shop_id`) REFERENCES `tb_shop`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# 商品图片
CREATE TABLE `tb_product_img` (
  `id`  int(20) NOT NULL AUTO_INCREMENT,
  `product_id` int(2) NOT NULL ,
  `addr`  varchar(2000)NOT NULL COMMENT '地址' ,
  `desc`  varchar(1024)DEFAULT NULL COMMENT '描述' ,
  `priority` int(3) DEFAULT 0 COMMENT '权重' ,
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_proimg_product` foreign key (`product_id`) REFERENCES `tb_product`(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


