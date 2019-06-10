drop table Repositories_ProgrammingLanguages;

drop table users;

drop table Repositories_Years;

drop table repositories;

drop table ProgrammingLanguages;

drop table Years;

create table users(id serial primary key not null,username varchar(30) not null unique,password int not null ,year varchar(3),role varchar(10) not null);


create table Repositories (id serial primary key not null,subject varchar(30) not null,project_name varchar(30) not null unique,deadline varchar(10),details varchar(1000),active bool not null);

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


insert into Years(name) values ('An1'),('An2'),('An3'),('MLC1'),('MOC1'),('MSD1'),('MIS1'),('MSI1'),('MLC2'),('MOC2'),('MSD2'),('MIS2'),('MSI2');




old
drop table permisiuni;
drop table proiecte;
drop table limbaje_utilizate;

create table proiecte (id serial primary key not null,materie varchar(30) not null,nume_proiect varchar(30) not null,deadline varchar(10),ora varchar(5),cod_ani varchar(3) not null,limita_uploaduri int,detalii varchar(1000),activ bool,tranzactie integer not null);

create table permisiuni(id_proiect int references proiecte(id) not null,student varchar(30) references conturi(username) not null,tranzactie integer not null);

create table limbaje_utilizate(id_proiect int references proiecte(id) not null,limbaj varchar(30) not null references limbaje(limbaj),tranzactie integer not null);