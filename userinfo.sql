/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 8.0.16 : Database - userinfo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`userinfo` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `userinfo`;

/*Table structure for table `t_authority` */

DROP TABLE IF EXISTS `t_authority`;

CREATE TABLE `t_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `creat_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_authority_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd76g7hi6t33707ir7al86y9vi` (`parent_authority_id`),
  CONSTRAINT `FKd76g7hi6t33707ir7al86y9vi` FOREIGN KEY (`parent_authority_id`) REFERENCES `t_authority` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `t_authority` */

insert  into `t_authority`(`id`,`authority_name`,`code`,`creat_time`,`status`,`update_time`,`url`,`parent_authority_id`) values (1,'主菜单','menu','2019-07-15 17:03:54',1,'2019-07-15 17:03:54','/menu',NULL),(2,'权限管理','menu_authority','2019-07-16 14:03:09',1,'2019-07-16 14:03:09','/menu_authority',1),(3,'权限查询','button_find','2019-07-16 14:06:36',1,'2019-07-16 14:06:36','/authority/find',2),(4,'权限修改','button_update','2019-07-16 14:08:04',1,'2019-07-16 14:08:04','/authority/update',2),(5,'角色管理','menu_role','2019-07-16 14:10:43',1,'2019-07-16 14:10:43','/menu_role',1),(6,'角色新增','button_add','2019-07-16 14:12:57',1,'2019-07-16 14:12:57','/role/add',5),(7,'角色查找','button_find','2019-07-16 14:14:30',1,'2019-07-16 14:14:30','/role/find',5),(8,'角色修改','button_update','2019-07-16 14:16:56',1,'2019-07-16 14:16:56','/role/update',5),(9,'角色删除','button_delete','2019-07-16 14:16:19',1,'2019-07-16 14:16:19','/role/delete',5),(10,'用户管理','menu_user','2019-07-16 14:18:08',1,'2019-07-16 14:18:08','/menu_user',1),(11,'用户新增','button_add','2019-07-16 14:18:58',1,'2019-07-16 14:18:58','/user/add',10),(12,'用户查找','button_find','2019-07-16 14:20:17',1,'2019-07-16 14:20:17','/user/find',10),(13,'用户修改','button_update','2019-07-16 14:20:54',1,'2019-07-16 14:20:54','/user/update',10),(14,'用户删除','button_delete','2019-07-16 14:21:11',1,'2019-07-16 14:21:11','/user/delete',10);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creat_time` datetime DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK417k0qm3hs2ygsnm90rrcuj83` (`parent_role_id`),
  CONSTRAINT `FK417k0qm3hs2ygsnm90rrcuj83` FOREIGN KEY (`parent_role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`creat_time`,`role_name`,`status`,`update_time`,`parent_role_id`) values (1,'2019-07-16 14:30:21','董事长',1,'2019-07-16 14:30:21',NULL),(2,'2019-07-16 14:44:29','总经理',1,'2019-07-16 14:44:29',1),(3,'2019-07-16 14:48:45','财务部经理',1,'2019-07-16 14:48:45',2),(4,'2019-07-16 14:52:17','财务部主管',1,'2019-07-16 14:52:17',3),(5,'2019-07-16 14:52:56','财务部组长',1,'2019-07-16 14:52:56',4),(6,'2019-07-16 15:00:56','财务部成员',1,'2019-07-16 15:00:56',5),(8,'2019-07-16 15:19:04','销售部经理',1,'2019-07-16 15:19:04',2),(9,'2019-07-16 15:21:27','销售部总管',1,'2019-07-16 15:21:27',8),(10,'2019-07-16 15:22:18','销售部组长',1,'2019-07-16 15:22:18',9),(11,'2019-07-16 15:22:55','销售部成员',1,'2019-07-16 15:22:55',10);

/*Table structure for table `t_role_authority` */

DROP TABLE IF EXISTS `t_role_authority`;

CREATE TABLE `t_role_authority` (
  `authority_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKej7eeolya4yimf2c163rnnoi9` (`role_id`),
  KEY `FK7thtwmk7w5yay0k6q7nob0t6t` (`authority_id`),
  CONSTRAINT `FK7thtwmk7w5yay0k6q7nob0t6t` FOREIGN KEY (`authority_id`) REFERENCES `t_authority` (`id`),
  CONSTRAINT `FKej7eeolya4yimf2c163rnnoi9` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role_authority` */

insert  into `t_role_authority`(`authority_id`,`role_id`) values (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(14,3),(7,4),(12,4),(13,4),(7,5),(12,5),(13,5),(12,6),(13,6),(1,8),(2,8),(3,8),(4,8),(5,8),(6,8),(7,8),(8,8),(9,8),(10,8),(11,8),(12,8),(13,8),(14,8),(7,9),(12,9),(13,9),(7,10),(12,10),(13,10),(12,11),(13,11);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `creat_time` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`account`,`age`,`creat_time`,`password`,`phone`,`status`,`update_time`,`user_name`) values (1,'Sakura',3,'2019-07-16 15:28:54','000','18700000000',1,'2019-07-16 15:28:54','一只秋秋'),(2,'Dawn',23,'2019-07-16 15:29:44','111','18711111111',1,'2019-07-16 15:29:44','一只空空');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKa9c8iiy6ut0gnx491fqx4pxam` (`role_id`),
  KEY `FKq5un6x7ecoef5w1n39cop66kl` (`user_id`),
  CONSTRAINT `FKa9c8iiy6ut0gnx491fqx4pxam` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKq5un6x7ecoef5w1n39cop66kl` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`user_id`,`role_id`) values (1,1),(1,2),(1,3),(2,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
