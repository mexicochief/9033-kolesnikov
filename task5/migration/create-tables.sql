-- create schema Games;

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
    developerId  bigint        not null,
    publisherId  bigint        not null
);
alter table game
    add constraint game_pk primary key (id);

alter table game
    add constraint game_developer_id_fk foreign key (developerId) references developer (id);

alter table game
    add constraint game_publisher_id_fk foreign key (publisherId) references publisher (id);