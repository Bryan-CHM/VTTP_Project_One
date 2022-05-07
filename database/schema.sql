-- drop database if exists
drop schema if exists savings;

create schema savings;

use savings;

create table user(
    username VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,
    primary key (username)
);


