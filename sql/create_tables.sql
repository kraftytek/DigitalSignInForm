/**********Generate initial tables**************/

/**
tables:

	clients: fname, lname, companyName, phone, phone2, email, client_id(primary_key), creation date, flags

	client_service: workorder_ID (primary_key), client_id(foreign_key), work_to_be_done, work_performed, service_fee_id(foreign_key), desktop(boolean), laptop(boolean), tablet(boolean), charger(boolean)

	service_fees: service_fee_id, service_desc_short, service name, service_cost

	service_link: workorder_id(foreign_key), service_fee_id(foreign_key), link_id(primary_key)

	client_flags: flag_description, flag_id(primary_key), flag_name

**/

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

create table service_fees (
	service_fee_id int not null IDENTITY(1,1) PRIMARY KEY,
	service_desc_short varchar(max),
	service_name varchar(max),
	service_cost decimal(5,2)
	)

create table client_service (
	work_Order_ID int not null IDENTITY(1,1) PRIMARY KEY,
	client_id int FOREIGN KEY (client_id) REFERENCES clients(client_id),
	work_to_do varchar(max),	
	pc_pass varchar(max),
	pc_pin varchar(max),
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
	service_fee_id int FOREIGN KEY (service_fee_id) REFERENCES service_fees(service_fee_id)
	)

create table client_flags (
	flag_id int not null IDENTITY(1,1) PRIMARY KEY,
	flag_description varchar(max),
	flag_name varchar(max)
	)


	