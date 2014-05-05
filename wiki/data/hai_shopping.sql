/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.5.17 : Database - hai_shopping
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hai_shopping` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `hai_shopping`;

/*Table structure for table `brand` */

DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '品牌名称',
  `logo` varchar(255) NOT NULL DEFAULT '' COMMENT '品牌logo图片',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '品牌链接的URL',
  `description` varchar(2000) NOT NULL DEFAULT '' COMMENT '品牌介绍',
  `seo_title` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的标题',
  `seo_keywords` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的关键字',
  `seo_description` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的描述',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_name` (`name`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `brand` */

insert  into `brand`(`id`,`name`,`logo`,`url`,`description`,`seo_title`,`seo_keywords`,`seo_description`,`sort`) values (9,'bbb1','WEB-RES/imgs/6976e4ff3f832126c4064d920811b006.jpg','111','bbb','bb','bb','bb',11),(11,'aaa','WEB-RES/imgs/144de2119df36a4889e4b8920699e261.jpg','http://bbs.csdn.net/topics/350188307','','','','',9);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `pid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父id',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '树的路径,只能三级',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '类目名称',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '类目链接的URL',
  `seo_title` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的标题',
  `seo_keywords` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的关键字',
  `seo_description` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的描述',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_pid_name` (`pid`,`name`),
  KEY `idx_pid` (`pid`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`pid`,`path`,`name`,`url`,`seo_title`,`seo_keywords`,`seo_description`,`sort`) values (16,0,'16','aaa','','aaa','aaa','aaa',18),(17,16,'16/17','bbb1','','bbb1','bb1','bb1',17),(18,0,'18','ccc','aaa','ccc','cc','cc',16),(20,17,'16/17/20','ccc','ccc','cc','cc','ccc',20),(21,18,'18/21','c1','','','','',21),(23,21,'18/21/23','c2','1','1','1','1',23);

/*Table structure for table `picture` */

DROP TABLE IF EXISTS `picture`;

CREATE TABLE `picture` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `pid` bigint(20) unsigned NOT NULL COMMENT '父ID',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '树的路径',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '节点分类：0：目录、1：图片',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '名称',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'MD5唯一标识串',
  `url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '图片URL',
  `rel_url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '实际的物理地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_md5` (`md5`),
  UNIQUE KEY `uqe_path_name` (`path`,`name`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `picture` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品的自增id',
  `brand_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '品牌id',
  `cate_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品的分类',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '商品的标题',
  `sub_name` varchar(512) NOT NULL DEFAULT '' COMMENT '商品的副标题',
  `image` varchar(255) NOT NULL DEFAULT '' COMMENT '商品主图',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `weight` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品的重量，以千克为单位',
  `stock_number` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '库存数量',
  `min_buy_number` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '最小购买数',
  `max_buy_number` int(11) unsigned NOT NULL DEFAULT '10' COMMENT '最大购买数',
  `sell_count` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品总销量',
  `unit` varchar(8) NOT NULL DEFAULT '' COMMENT '商品的单位，如：只、件',
  `pc_desc` varchar(8000) NOT NULL DEFAULT '' COMMENT '电脑端商品详细',
  `mobile_desc` varchar(8000) NOT NULL DEFAULT '' COMMENT '手机端商品详细',
  `link_comment` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '关联评论，商品的id',
  `seller_note` varchar(255) NOT NULL DEFAULT '' COMMENT '商品的商家备注，仅商家可见',
  `seo_title` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的标题',
  `seo_keywords` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的关键字',
  `seo_description` varchar(255) NOT NULL DEFAULT '' COMMENT '搜索引擎SEO的描述',
  `create_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '更新时间',
  `delete_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '删除时间',
  `publish_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '首次发布时间',
  `up_shelf_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '最后上架时间',
  `off_shelf_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '最后下架时间',
  `sale_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '销售状态。0：待销售、1：销售中、2：已售完、3、手动下架、4：系统下架。。枚举:com.haiegoo.shopping.enums.SaleState',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据状态: 1,启用 2,禁用 3,删除。枚举:com.haiegoo.commons.enums.State',
  PRIMARY KEY (`id`),
  KEY `idx_cate_id` (`cate_id`),
  KEY `idx_brand_id` (`brand_id`),
  KEY `idx_name` (`name`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_update_time` (`update_time`),
  KEY `idx_delete_time` (`delete_time`),
  KEY `idx_publish_time` (`publish_time`),
  KEY `idx_up_shelf_time` (`up_shelf_time`),
  KEY `idx_off_shelf_time` (`off_shelf_time`),
  KEY `idx_sale_state` (`sale_state`),
  KEY `idx_state` (`state`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`id`,`brand_id`,`cate_id`,`name`,`sub_name`,`image`,`price`,`weight`,`stock_number`,`min_buy_number`,`max_buy_number`,`sell_count`,`unit`,`pc_desc`,`mobile_desc`,`link_comment`,`seller_note`,`seo_title`,`seo_keywords`,`seo_description`,`create_time`,`update_time`,`delete_time`,`publish_time`,`up_shelf_time`,`off_shelf_time`,`sale_state`,`state`) values (2,11,23,'测试商品','','','0.00','0.00',0,1,10,0,'','','',0,'','','','','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','2014-04-23 11:55:18','2014-04-24 23:42:24','2014-04-24 23:42:18',1,1),(3,1,0,'下架的商品1','','','0.00','0.00',0,1,10,0,'','','',0,'','','','','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','2014-04-23 15:10:24','2014-04-23 15:15:05','2014-04-23 15:15:12',3,1),(4,0,0,'下架的商品2','','','0.00','0.00',0,1,10,0,'','','',0,'','','','','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','2014-04-23 15:15:46','2014-04-23 16:30:27','2014-04-23 16:30:31',3,1),(5,0,0,'待售的商品','','','0.00','0.00',0,1,10,0,'','','',0,'','','','','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','2014-04-23 11:54:52',0,1),(6,0,0,'售完的商品','','','0.00','0.00',0,1,10,0,'','','',0,'','','','','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','1000-01-01 00:00:00','2014-04-23 11:54:40',2,1),(7,0,0,'已删除的商品','','','0.00','0.00',0,1,10,0,'','','',0,'','','','','1000-01-01 00:00:00','1000-01-01 00:00:00','2014-04-23 16:27:12','2014-04-23 16:26:43','2014-04-23 16:26:43','2014-04-23 16:26:48',3,3);

/*Table structure for table `product_imgs` */

DROP TABLE IF EXISTS `product_imgs`;

CREATE TABLE `product_imgs` (
  `product_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '详情图URL',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '分类：0：列表、1：详情、2：大图',
  `sort` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '图片显示的顺序',
  PRIMARY KEY (`product_id`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product_imgs` */

/*Table structure for table `product_props` */

DROP TABLE IF EXISTS `product_props`;

CREATE TABLE `product_props` (
  `product_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID',
  `props_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性ID',
  `props_value` varchar(2048) NOT NULL DEFAULT '' COMMENT '属性值; 当属性值有ID时记录ID，没有ID时记录字符串。多个以逗号隔开',
  PRIMARY KEY (`product_id`,`props_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product_props` */

/*Table structure for table `product_specs` */

DROP TABLE IF EXISTS `product_specs`;

CREATE TABLE `product_specs` (
  `product_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID',
  `spec_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '规格ID',
  `spec_value` varchar(2048) NOT NULL DEFAULT '' COMMENT '规格值;记录规格值的ID，多个以逗号隔开',
  PRIMARY KEY (`product_id`,`spec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product_specs` */

/*Table structure for table `property` */

DROP TABLE IF EXISTS `property`;

CREATE TABLE `property` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `pid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父id',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '树的路径，只有两级： 一级：属性、二级：属性值',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '属性名称或属性值',
  `input_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '输入类型，只有记录为属性时有效; 0、文本框；1、可编辑下拉框；2、不可编辑下拉框；3，单选框；4，复选框。枚举com.haiegoo.shopping.enums.InputType',
  `cate_path` varchar(255) NOT NULL DEFAULT '' COMMENT '所隶属的类目树',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_pid_name` (`pid`,`name`),
  KEY `idx_pid` (`pid`),
  KEY `idx_sort` (`sort`),
  KEY `idx_cate_pate` (`cate_path`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `property` */

insert  into `property`(`id`,`pid`,`path`,`name`,`input_type`,`cate_path`,`sort`) values (11,0,'11','aaa',1,'16/17/20',11),(12,11,'11/12','a11',0,'',13),(13,11,'11/13','a2',0,'',12),(14,0,'14','bbb',1,'16/17/20',14),(15,14,'14/15','b12',0,'',15),(16,14,'14/16','b21',0,'',16),(20,0,'20','ccc1',0,'18/21/23',20);

/*Table structure for table `specification` */

DROP TABLE IF EXISTS `specification`;

CREATE TABLE `specification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `pid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父id',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '树的路径， 只有两级；一级：规格，二级：规格值',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '规格名称或规格值',
  `has_image` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有图片；1：是，0：否',
  `cate_path` varchar(255) NOT NULL DEFAULT '' COMMENT '所隶属的类目树',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_pid_name` (`pid`,`name`),
  KEY `idx_pid` (`pid`),
  KEY `idx_sort` (`sort`),
  KEY `idx_cate_pate` (`cate_path`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `specification` */

insert  into `specification`(`id`,`pid`,`path`,`name`,`has_image`,`cate_path`,`sort`) values (2,0,'2','bbb',1,'16/17',19),(3,0,'3','ccc',0,'18/21/23',3),(10,2,'2/10','11121',0,'',10),(11,2,'2/11','222',0,'',11),(14,3,'3/14','c312',0,'',14),(19,0,'19','ffff',0,'16/17/20',2),(20,19,'19/20','f1',0,'',20),(23,19,'19/23','f3',0,'',23);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
