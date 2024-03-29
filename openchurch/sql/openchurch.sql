-- uncomment the following line if you want to destroy and re-create an 
-- exisiting openchurch database...

drop database openchurch;

create database openchurch DEFAULT CHARACTER  SET latin1 COLLATE latin1_bin;

USE openchurch;

create table id_generator (
    id_name varchar(50) primary key,
    id_value bigint not null
);

insert into id_generator values ('INV_GEN', 5);

create table openchurch_users (
    id bigint primary key,
    username varchar(50) not null unique,
    preferredNames varchar(500) not null,
    surname varchar(500) not null,
    emailAddress varchar(250) not null,
    homeTelNumber varchar(100),
    workTelNumber varchar(100),
    mobileTelNumber varchar(100),
    password varchar(100) not null
);

insert into openchurch_users values (
	0,
	'guest',
	'Honoured',
	'Guest',
	'no@email.address',
	null,
	null,
	null,
	''
);

insert into openchurch_users values (
	1,
	'admin',
	'Esteemed',
	'Admin',
	'changeme@yourearliest.convinience',
	null,
	null,
	null,
	'eSL/wwP2+1kwLCbERaZx++BYljN8nLfnN2qQ5uhTQLep7HDILk+4gw=='
);

create index idx_openchurch_users on openchurch_users(id);
create index idx_openchurch_users_username on openchurch_users(username);

create table openchurch_addresses (
    id bigint primary key,
    firstLine varchar(250) not null,
    secondLine varchar(250),
    thirdLine varchar(250),
    town varchar(250) not null,
    county varchar(250) not null,
    postCode varchar(250) not null
);

create index idx_openchurch_addresses on openchurch_addresses(id);

create table openchurch_user_addresses (
    parent bigint not null,
    child bigint not null
);

create table openchurch_roles (
    id bigint primary key,
    title varchar(500) not null unique
);

insert into openchurch_roles values(0, 'admin');
insert into openchurch_roles values(1, 'member');

create table openchurch_user_roles (
    parent bigint not null,
    child bigint not null
);

create table openchurch_role_menus (
    parent bigint not null,
    child bigint not null
);

insert into openchurch_role_menus values(0, 0);

insert into openchurch_user_roles values (1,0);

create table openchurch_content_modules (
    id bigint primary key,
    moduleClassName varchar(250),
    deleteable bit
);

create index idx_openchurch_content_modules on openchurch_content_modules(id);

create table openchurch_menu_items (
    id bigint primary key,
    title varchar(500) not null,
    page_id bigint
);

create index idx_openchurch_menu_items on openchurch_menu_items(id);
create index idx_openchurch_menu_items_title on openchurch_menu_items(title);

create table openchurch_pages (
     id bigint primary key,
    name varchar(500) not null,
    title varchar(500) not null
);

insert into openchurch_pages values(0, 'System Properties', 'System Properties');

create index idx_openchurch_pages on openchurch_pages(id);

create table openchurch_page_modules (
    parent bigint not null,
    child bigint not null
);

create table openchurch_menus (
     id bigint primary key,
     name varchar(500) not null,
     priority bigint not null
);

insert into openchurch_menus values(0, 'Admin', 1000);
insert into openchurch_menu_items values(0, 'System Properties', 0); 

create table openchurch_menu_menuitems (
    parent bigint not null,
    child bigint not null
);

insert into openchurch_menu_menuitems values(0, 0);

create table openchurch_properties (
	id bigint primary key,
	name varchar(500) unique,
	property_value LONG VARCHAR
);

create table openchurch_portal_properties (
	id bigint primary key,
	name varchar(500) unique,
	property_value LONG VARCHAR
);


