--liquibase formatted sql

--changeset bolotina:init

create table users
(
    id bigserial constraint user_pk primary key,
    login varchar,
    password varchar,
    role varchar
);
