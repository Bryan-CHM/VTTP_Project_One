-- drop database if exists
drop schema if exists animals;

create schema animals;

use animals;

create table user(
    user_id INT AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,
    primary key (user_id)
);

create table favourite_animals(
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(128),
    animal_type VARCHAR(128),
    active_time VARCHAR(128),
    habitat VARCHAR(128),
    diet VARCHAR(128),
    location VARCHAR(128),
    lifespan INT,
    min_length DECIMAL(10,2),
    max_length DECIMAL(10,2),
    min_weight DECIMAL(10,2),
    max_weight DECIMAL(10,2),
    image_url VARCHAR(256),
    primary key(id),
    constraint fk_user_id
        foreign key(user_id)
        references user(user_id)
)




