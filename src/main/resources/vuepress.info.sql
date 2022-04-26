/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 8.0.27 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;
CREATE DATABASE vuepress;
use vuepress;

create table `info` (
	`id` bigint (20),
	`pic_name` varchar (1536),
	`pic_local_path` varchar (1536),
	`pic_url` varchar (1536),
	`sha` varchar (1536)
); 
