create database library;
use library;

#drop database library;

create table teacher (
	id bigint primary key not null auto_increment,
    teacher_id bigint unique not null,
    first_name varchar(50) not null,
    second_name varchar(50) not null,
    teacher_descr varchar(1000),
    siteReference varchar(30)
);

create table room (
	id bigint primary key not null auto_increment,
	room_id bigint unique not null,
    room_name varchar(50) not null,
    room_descr varchar(1000)
);

create table subject (
	id bigint primary key not null auto_increment,
    subject_id bigint unique not null,
    subject_name varchar(255) not null,
    subject_language varchar(50) not null,
    subject_descr varchar(1000)

);

create table lesson (
        id bigint primary key not null auto_increment,
        lesson_number bigint not null
        day_of_week bigint not null

	    teacher_id bigint not null,
	    subject_id bigint not null,
        room_id bigint not null,

        foreign key(teacher_id) references teacher(teacher_id) on delete cascade,
        foreign key(subject_id) references subject(subject_id) on delete cascade,
        foreign key(room_id) references room(room_id) on delete cascade
);