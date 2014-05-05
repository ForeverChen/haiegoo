/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.5.17 : Database - hai_commons
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hai_commons` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `hai_commons`;

/*Table structure for table `enums` */

DROP TABLE IF EXISTS `enums`;

CREATE TABLE `enums` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `clazz` varchar(64) NOT NULL DEFAULT '' COMMENT '枚举类,全名,如:com.haiegoo.commons.enums.State',
  `key` varchar(32) NOT NULL DEFAULT '' COMMENT '枚举键,英文',
  `code` int(11) NOT NULL DEFAULT '0' COMMENT '枚举编码,数字',
  `text` varchar(255) NOT NULL DEFAULT '' COMMENT '枚举名称,一般为中文，用于显示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_clazz_key` (`clazz`,`key`),
  UNIQUE KEY `uqe_clazz_code` (`clazz`,`code`),
  UNIQUE KEY `uqe_clazz_text` (`clazz`,`text`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='后台管理系统功能模块表';

/*Data for the table `enums` */

insert  into `enums`(`id`,`clazz`,`key`,`code`,`text`) values (1,'com.haiegoo.commons.enums.YesNo','NO',0,'否'),(2,'com.haiegoo.commons.enums.YesNo','YES',1,'是'),(3,'com.haiegoo.commons.enums.State','ENABLE',1,'启用'),(4,'com.haiegoo.commons.enums.State','DISABLE',2,'禁用'),(5,'com.haiegoo.commons.enums.State','DELETED',3,'删除'),(18,'com.haiegoo.ucenter.enums.Gender','MALE',1,'男'),(19,'com.haiegoo.ucenter.enums.Gender','FEMALE',2,'女');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
