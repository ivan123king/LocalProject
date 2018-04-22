create database human;
use human;
create table userC(
	user_id int primary key auto_increment,
	user_name varchar(20),
	customer_id int
);
alter table userC add status int;
alter table userC add password varchar(20);

create table customer(
	customer_id int primary key auto_increment,
	customer_name varchar(20),
	age int,
	birthday date,
	ID varchar(30),
	address varchar(255),
	status int
);

-- 此处user_id不设计为not null,user_id为空表示用户被管理员删除了
create table blog(
	blog_id INTEGER primary key auto_increment,
	user_id INTEGER,
	content Text,
	blog_date date,
	labels varchar(255)
);
alter table blog add(title varchar(50));
alter table blog add(is_delete int);
alter table blog modify column is_delete int default 0  comment '0表示没有删除，1表示删除';

