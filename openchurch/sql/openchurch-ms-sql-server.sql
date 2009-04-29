USE Master;

if exists (select name from sysdatabases where  name = 'openchurch')
drop database openchurch;

create database openchurch;

USE openchurch;

create table id_generator (
    id_name varchar(50) primary key,
    id_value bigint not null
)

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
)

create table openchurch_addresses (
    id bigint primary key,
    firstLine varchar(250) not null,
    secondLine varchar(250),
    thirdLine varchar(250),
    town varchar(250) not null,
    county varchar(250) not null,
    postCode varchar(250) not null
)

create table openchurch_user_addresses (
    parent bigint not null,
    child bigint not null
)

create table openchurch_roles (
    id bigint primary key,
    title varchar(500) not null unique
)

insert into openchurch_roles values(0, 'admin');
insert into openchurch_roles values(1, 'member');

create table openchurch_user_roles (
    parent bigint not null,
    child bigint not null
)

create table openchurch_content_modules (
    id bigint primary key,
    moduleClassName varchar(250),
    deleteable bit
)

create table openchurch_menu_items (
    id bigint primary key,
    title varchar(500) not null,
    page_id bigint
)

create table openchurch_pages (
     id bigint primary key,
    name varchar(500) not null,
    title varchar(500) not null
)

create table openchurch_page_modules (
    parent bigint not null,
    child bigint not null
)

create table openchurch_menus (
     id bigint primary key,
    name varchar(500) not null
)

create table openchurch_menu_menutitems (
    parent bigint not null,
    child bigint not null
)