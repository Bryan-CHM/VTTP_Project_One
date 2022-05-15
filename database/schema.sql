-- drop database if exists
drop schema if exists savings;

create schema savings;

use savings;

create table user(
    user_id INT AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,
    savings INT DEFAULT '0',
    primary key (user_id)
);




