drop database practicedb;
drop user practiceuser;
create user practiceuser with password 'practice';
create database practicedb with template=template0 owner practiceuser;
\connect practicedb;
alter default privileges grant all on tables to practiceuser;
alter default privileges grant all on sequences to practiceuser;

create table practiceTable(
    user_id serial primary key not null,
    firstname varchar(20) not null,
    lastname varchar(20) not null,
    email varchar(20) not null,
    password text not null
);
create table categoryTable(
    category_id serial primary key not null,
    user_id integer not null,
    title varchar(20) not null,
    description varchar(20) not null
);
alter table categoryTable add constraint cat_user_api
foreign key (user_id) references practiceTable(user_id);

create table transactionTable(
transaction_id serial primary key not null,
category_id integer not null,
user_id integer not null,
amount numeric(10,2) not null,
transaction_date bigint not null
);

alter table transactionTable add constraint trans_cat_fk
foreign key (category_id) references categoryTable(category_id);
alter table transactionTable add constraint trans_user_fk
foreign key (user_id) references practiceTable(user_id);

create sequence practiceTable_seq increment 1 start 1;
create sequence categoryTable_seq increment 1 start 1;
create sequence transactionTable_seq increment 1 start 1000;