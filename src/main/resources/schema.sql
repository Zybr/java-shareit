-- drop tables
drop table if exists comments;
drop table if exists bookings;
drop table if exists items;
drop table if exists requests;
drop table if exists users;


-- create tables

create table users
(
    id    bigint generated always as identity primary key,
    name  varchar(50) not null,
    email varchar(50) not null unique check (btrim(email) <> '')
);

create table requests
(
    id           bigint generated always as identity primary key,
    description  varchar(255) not null check (btrim(description) <> ''),
    requestor_id bigint       not null references users (id) on delete cascade
);

create table items
(
    id           bigint generated always as identity primary key,
    name         varchar(50)  not null unique check (btrim(name) <> ''),
    description  varchar(255) not null check (btrim(description) <> ''),
    is_available boolean      not null default true,
    owner_id     bigint       not null references users (id) on delete cascade,
    request_id   bigint references requests (id)
);

create table bookings
(
    id         bigint generated always as identity primary key,
    start_time timestamp without time zone not null,
    end_time   timestamp without time zone not null,
    item_id    bigint                      not null references items (id) on delete cascade,
    booker_id  bigint                      not null references users (id) on delete cascade,
    status     smallint                    not null
);

create table comments
(
    id         bigint generated always as identity primary key,
    text       varchar(255)                not null check (btrim(text) <> ''),
    item_id    bigint                      not null references items (id) on delete cascade,
    author_id  bigint                      not null references users (id) on delete cascade,
    created_at timestamp without time zone not null default current_timestamp
);

