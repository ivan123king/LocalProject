create database houses;
use houses;
#房产表
create table house(
	id BIGINT(20) unsigned not null auto_increment comment '主键ID',
	name varchar(20) not null default '' comment '房产名称',
	type tinyint(1) not null default '0' comment '1：销售，2：出租',
	price int(11) not null comment '单位元',
	images varchar(1024) not null default '' comment '图片地址',
	area int(11) not null default '0' comment '面积',
	beds int(11) not null default '0' comment '卧室数量',
	baths int(11) not null default '0' comment '卫生间数量',
	rating double not null default '0' comment '评级',
	remarks varchar(512) not null default '' comment '房产描述',
	properties varchar(512) not null default '' comment '属性',
	floor_plan varchar(258) not null default '' comment '户型图',
	tags varchar(20) not null default '' comment '标签',
	create_time date not null comment '创建时间',
	city_id int(11) not null default '0' comment '城市名称',
	community_id int(11) not null default '0' comment '小区名称',
	address varchar(20) not null default '' comment '房产地址',
	state tinyint(1) default '1' comment '1：上架，2：下架',
	primary key(id)
);

ALTER TABLE house COMMENT='房产表';

#小区表
create table community(
	id int(11) unsigned not null auto_increment,
	city_cpde varchar(11) not null default '' comment '城市编码',
	name varchar(50) not null default '' comment '小区名称',
	city_name varchar(11) not null default '' comment '城市名称',
	primary key(id)
) comment '小区表';

#用户表
create table user(
	id bigint(20) unsigned not null auto_increment comment '主键',
	name varchar(20) not null default '' comment '姓名',
	phone char(13) not null default '' comment '手机号',
	email varchar(90) not null default '' comment '电子邮件',
	aboutme varchar(250) not null default '' comment '自我介绍',
	passwd varchar(512) not null default '' comment '经过MD5加密的密码',
	avatar varchar(512) not null default '' comment '头像图片',
	type tinyint(1) not null comment '1:普通用户，2：房产经纪人',
	create_time date not null comment '创建时间',
	enable tinyint(1) not null comment '是否启用,1:启用，0：停用',
	agency_id int(11) not null default '0' comment '所属经纪机构',
	primary key(id),
	unique key idx_email(email)
)comment '用户表';

#房产用户表
create table house_user(
	id bigint(20) unsigned not null auto_increment,
	house_id bigint(20) not null comment '房屋ID',
	user_id bigint(20) not null comment '用户ID',
	create_time date not null comment '创建时间',
	type tinyint(1) not null comment '1:售卖，2：收藏',
	primary key(id),
	unique key house_id_user_id_type (house_id,user_id,type)
) comment '房产用户表';

#房产留言表
create table house_msg(
	id bigint(20) unsigned not null auto_increment,
	msg varchar(512) not null default '' comment '消息',
	create_time date not null comment '创建时间',
	agent_id bigint(20) not null comment '经纪人ID',
	house_id bigint(20) not null comment '房屋ID',
	user_name varchar(20) not null default '' comment '用户姓名',
	primary key(id)
) comment '房产留言表';

#经纪机构表
create table agency(
	id int(11) unsigned not null auto_increment,
	name varchar(20) not null default '' comment '经纪机构名称',
	address varchar(100) not null default '' comment '地址',
	phone varchar(30) not null default '' comment '手机',
	email varchar(50) not null default '' comment '电子邮件',
	about_us varchar(100) not null default '' comment '描述',
	mobile varchar(11) not null default '' comment '电话',
	web_site varchar(20) not null default '' comment '网站',
	primary key(id)
) comment '经纪机构表';

#评论表
create table comment(
	id bigint(20) unsigned not null auto_increment,
	content varchar(512) not null default '' comment '评论内容',
	house_id bigint(20) not null comment '房屋ID',
	create_time date not null comment '发布时间戳',
	blog_id int(11) not null comment '博客ID',
	type tinyint(1) not null comment '类型，1：房产评论，2：博客评论',
	user_id bigint(20) not null comment '评论用户',
	primary key(id)
) comment '评论表';

#博客表
create table blog(
	id int(11) unsigned not null auto_increment,
	tags varchar(20) not null default '' comment '标签',
	content text not null comment '内容',
	create_time date not null comment '日期',
	title varchar(20) not null default '' comment '标题',
	cat int(11) default null comment '分类，1：准备买房，2：看房/选房，3：签约/定房，4：全款/贷款，5：缴税/过户，6：入住/交接,7:买房风险',
	primary key(id)
) comment '博客表';