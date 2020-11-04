DROP TABLE IF EXISTS closed_loans;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS discussions;
DROP TABLE IF EXISTS wishlist_items;
DROP TABLE IF EXISTS wishlists;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS announcements;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS admins;

CREATE TABLE admins(
	id int primary key auto_increment,
	email varchar(100),
	password varchar(50)
);

CREATE TABLE users(
	id int primary key auto_increment,
	email varchar(100),
	password varchar(50),
	first_name varchar(50),
	last_name varchar(50),
	phone_number varchar(15)
);

CREATE TABLE announcements(
	id int primary key auto_increment,
	owner_id int,
	name varchar(100),
	description  varchar(500),
	location varchar(55),
	category int,
	created_date date default current_timestamp,
	duration int,
	status int,
    price_per_day int,
	foreign key (owner_id) references users(id) on delete set null
);

CREATE TABLE images(
	id int primary key auto_increment,
	image_bytes varbinary(MAX),
	announcement_id int,
	foreign key (announcement_id) references announcements(id) on delete cascade
);

CREATE TABLE wishlists(
	id int primary key auto_increment,
	owner_id int,
	foreign key (owner_id) references users(id) on delete set null
);

CREATE TABLE wishlist_items(
	id int primary key auto_increment,
	wishlist_id int,
	announcement_id int,
	foreign key (wishlist_id) references wishlists(id) on delete cascade,
	foreign key (announcement_id) references announcements(id) on delete cascade
);

CREATE TABLE discussions(
	id int primary key auto_increment,
	interested_user_id int,
	announcement_id int,
	created_date date default current_timestamp,
	foreign key (interested_user_id) references users(id) on delete cascade,
	foreign key (announcement_id) references announcements(id) on delete cascade
);

CREATE TABLE loans(
	id int primary key auto_increment,
	discussion_id int,
	loan_date date default current_timestamp,
	foreign key (discussion_id) references discussions(id) on delete cascade
);

CREATE TABLE closed_loans(
	id int primary key auto_increment,
	loan_id int,
	close_date date default current_timestamp,
	foreign key (loan_id) references loans(id) on delete cascade
);