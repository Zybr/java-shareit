-- drop tables
drop table if exists comments;
drop table if exists booking;
drop table if exists items;
drop table if exists requests;
drop table if exists users;


-- create tables

create table users
(
    id    bigint generated always as identity primary key,
    name  varchar(50) not null unique,
    email varchar(50) not null unique
);

create table requests
(
    id           bigint generated always as identity primary key,
    description  varchar(255) not null,
    requestor_id bigint       not null references users (id)
);

create table items
(
    id           bigint generated always as identity primary key,
    name         varchar(50)  not null unique,
    description  varchar(255) not null,
    is_available boolean      not null default true,
    owner_id     bigint       not null references users (id),
    request_id   bigint references requests (id)
);

create table booking
(
    id         bigint generated always as identity primary key,
    start_date timestamp without time zone not null,
    end_date   timestamp without time zone not null,
    item_id    bigint                      not null references items (id),
    booker_id  bigint                      not null references users (id),
    status     smallint                    not null
);

create table comments
(
    id        bigint generated always as identity primary key,
    text      varchar(255) not null,
    item_id   bigint       not null references items (id),
    author_id bigint       not null references users (id)
);
