/**********Generate initial tables**************/


create table clients (
	client_id int not null IDENTITY(1,1) PRIMARY KEY,
	fname varchar(max) not null,
	lname varchar(max),
	companyName varchar(max),
	phone varchar(max),
	phone2 varchar(max),
	email varchar(max),
	creation_date varchar(max),
	flags varchar(max)
	)

create table client_service (
	work_Order_ID int not null IDENTITY(1,1) PRIMARY KEY,
	client_id int FOREIGN KEY (client_id) REFERENCES clients(client_id),
	work_to_do varchar(max),	
	work_done varchar(max),
	pc_pass varchar(max),
	pc_pin varchar(max),
	other_equip varchar(max),
	sign_in_date datetime Default(getdate()),
	tech_name varchar(max),
	desktop BIT,
	laptop BIT,
	tablet BIT,
	charger BIT
	)

create table service_link (
	link_id int not null IDENTITY(1,1) PRIMARY KEY,
	work_Order_ID int FOREIGN KEY (work_Order_ID) REFERENCES client_service(work_Order_ID),
	service_fee_id int FOREIGN KEY (service_fee_id) REFERENCES upc_codes(upc_id)
	)

create table client_flags (
	flag_id int not null IDENTITY(1,1) PRIMARY KEY,
	flag_description varchar(max),
	flag_name varchar(max)
	)


create table upc_codes (
	upc_id int identity(1,1) PRIMARY KEY,
	upc_desc varchar(max) not null,
	upc_code varchar(max) not null,
	upc_cost varchar(max) not null
	)

create table users (
	username varchar(max) not null
	)


/*We need to make a single test entry into the client_services table as well as a Connected user in the users table*/

--insert into users(username)
values('Connected');


--insert into client_service(client_id, work_to_do, work_done)
values((select top 1 client_id from clients order by client_id desc),
'test',
'test')





