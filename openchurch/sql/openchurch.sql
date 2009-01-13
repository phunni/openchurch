USE Master;

if exists (select name from sysdatabases where  name = 'openchurch')
drop database openchurch;

create database openchurch;

USE openchurch;

create table id_generator (
    id_name varchar(50) primary key,
    id_value bigint not null
)

insert into id_generator values ('INV_GEN', 0);

create table openchurch_users (
    id bigint primary key,
    username varchar(50) not null unique,
    preferredNames varchar(500) not null,
    surname varchar(500) not null,
    emailAddress varchar(250) not null,
    homeTelNumber varchar(100),
    workTelNumber varchar(100),
    mobileTelNumber varchar(100)
)
