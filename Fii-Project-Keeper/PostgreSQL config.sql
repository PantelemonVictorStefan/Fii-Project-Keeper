create table conturi(id serial primary key not null,username varchar(30) not null unique,password int not null,an varchar(1),tip varchar(10) not null);
create table proiecte (id serial primary key not null,materie varchar(30) not null,nume_proiect varchar(30) not null,deadline varchar(10),ora varchar(5),cod_ani varchar(3) not null,limita_uploaduri int,detalii varchar(1000),activ bool,tranzactie integer not null);
create table permisiuni(id_proiect int references proiecte(id) not null,student varchar(30) references conturi(username) not null,tranzactie integer not null);
create table limbaje(limbaj varchar(30) primary key not null);
create table limbaje_utilizate(id_proiect int references proiecte(id) not null,limbaj varchar(30) not null references limbaje(limbaj),tranzactie integer not null);
create table materii(materie varchar(50) primary key not null);
