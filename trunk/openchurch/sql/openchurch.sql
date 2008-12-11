USE Master;

if exists (select name from sysdatabases where  name = 'openchurch')
drop database openchurch;

create database openchurch;

USE openchurch;

create table openchurch_users (
    id int primary key,
    userid varchar(50) not null,
    preferredNames varchar(500) not null,
    surname varchar(500) not null,
)