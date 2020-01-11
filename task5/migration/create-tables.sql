create schema gamedatabase;

create table developer
(
    id          bigserial     not null,
    name        varchar(256)  not null,
    description varchar(4096) not null
);
ALTER TABLE developer
    add constraint developer_pk primary key (id);

create table publisher
(
    id   bigserial    not null,
    name varchar(256) not null

);
alter table publisher
    add constraint publisher_pk primary key (id);

create table game
(
    id           bigserial     not null,
    name         varchar(256)  not null,
    description  varchar(4096) not null,
    release_date date          not null,
    developerId  bigserial     not null,
    publisherId  bigserial     not null
);
alter table game
    add constraint game_pk primary key (id);