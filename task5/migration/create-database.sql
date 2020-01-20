create user gamedatabase with
    password '123';

create database gamedatabase with
    owner = gamedatabase
    encoding = 'UTF-8';

GRANT ALL ON SCHEMA games TO public;