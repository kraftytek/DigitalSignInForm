
/**********Import code from existing system*************/

--add clients to client table

--insert into NCRO_WorkOrders.dbo.clients(fname, lname, companyName, phone, phone2, email)
select distinct cFName as fname,
cSurname as lname,
tc.CustomerID as companyName,
'' as phone,
'' as phone2,
'' as email
from tblCustomer as tc
where tc.cFName is not null
and left(cFName, 1) <> ' '
and cFName not in ('.', '0', '1`w', '2503170208', 'a')
order by 1

--add phone numbers to clients
select cs.client_id, cs.companyName, cs.phone, STR(tp.PhoneNumber,10, 0) as new_number
--update NCRO_WorkOrders.dbo.clients set NCRO_WorkOrders.dbo.clients.phone = STR(tp.PhoneNumber,10, 0)
from NCRO_WorkOrders.dbo.clients as cs
inner join tblCustomerPhone as tp
on cs.companyName = tp.CustomerID
inner join zzClientPhones as zz
on cs.client_id = zz.client_id
where PhoneNumber is not null
order by 1 


--add second phone numbers to clients
select cs.client_id, cs.companyName, cs.phone, cs.phone2, STR(tp.PhoneNumber,10, 0) as new_number
--update NCRO_WorkOrders.dbo.clients set NCRO_WorkOrders.dbo.clients.phone = STR(tp.PhoneNumber,10, 0)
from NCRO_WorkOrders.dbo.clients as cs
inner join tblCustomerPhone as tp
on cs.companyName = tp.CustomerID
inner join zzClientPhones2 as zz
on cs.client_id = zz.client_id
where tp.PhoneNumber is not null
and cs.phone <> STR(tp.PhoneNumber,10, 0)
order by 1 


--get single entry phone numbers
select *
--into zzClientPhones
from
(select tc.client_id, count(*) as entry_count
from tblCustomerPhone as tp
inner join NCRO_WorkOrders.dbo.clients as tc
on tp.CustomerID = tc.companyName
where PhoneNumber is not null
group by tc.client_id
) as x
where x.entry_count = 1

--get double entry phone numbers

select *
--into zzClientPhones2
from
(select tc.client_id, count(*) as entry_count
from tblCustomerPhone as tp
inner join NCRO_WorkOrders.dbo.clients as tc
on tp.CustomerID = tc.companyName
where PhoneNumber is not null
group by tc.client_id
) as x
where x.entry_count = 2





--add client work orders to client_service table

insert into NCRO_WorkOrders.dbo.client_service(client_id, work_to_do, work_done, pc_pass, pc_pin, tech_name, desktop, laptop, tablet, charger)

select *
from tblWorkOrder



select *
from NCRO_WorkOrders.dbo.client_service




select *
from tblCustomer