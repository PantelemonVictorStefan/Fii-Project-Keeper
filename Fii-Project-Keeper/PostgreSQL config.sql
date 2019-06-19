drop table Repositories_ProgrammingLanguages;

drop table users;

drop table Repositories_Years;

drop table repositories;

drop table ProgrammingLanguages;

drop table Years;

drop table projects;

drop table files;

drop table data;

create table users(id serial primary key not null,username varchar(30) not null unique,password int not null ,year varchar(3),role varchar(10) not null);


create table Repositories (id serial primary key not null,subject varchar(30) not null,name varchar(30) not null unique,createdAt date,deadline varchar(10),details varchar(1000),active bool not null);

create table ProgrammingLanguages(id serial primary key not null,language varchar(20) not null);

CREATE TABLE Repositories_ProgrammingLanguages (
  repository_id   int REFERENCES Repositories (id) ON UPDATE CASCADE ON DELETE CASCADE
, language_id int REFERENCES ProgrammingLanguages (id) ON UPDATE CASCADE
, PRIMARY KEY(repository_id,language_id)
);

create table Years(id serial primary key not null,name varchar(30) not null);


CREATE TABLE Repositories_Years (
  repository_id   int REFERENCES Repositories (id) ON UPDATE CASCADE ON DELETE CASCADE
, year_id int REFERENCES Years (id) ON UPDATE CASCADE
, PRIMARY KEY(repository_id,year_id)
);


create table files(id serial primary key not null,filename varchar(255),size bigint);

create table data(file_id int REFERENCES files (id) ON UPDATE CASCADE ON DELETE CASCADE,data bytea);

create table projects(repository_id int REFERENCES repositories (id) ON UPDATE CASCADE ON DELETE CASCADE, user_id int REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE, description varchar(255), presentation_id int REFERENCES files (id) ON UPDATE CASCADE ON DELETE CASCADE, data_id int REFERENCES files (id) ON UPDATE CASCADE ON DELETE CASCADE, PRIMARY KEY(repository_id,user_id));