/**********************Merge duplicate clients*******************/


select distinct x.*
--into zzAllDupes
from
(
select client_id, fname, lname, phone
from clients
) as x
inner join 
(
select client_id, fname, lname, phone
from clients
) as y
on x.fname = y.fname
and x.lname = y.lname
and x.phone = y.phone
and x.client_id <> y.client_id
order by 1 




--get all duplicate clients

SELECT 
        client_id, 
        fname, 
        lname, 
        phone, 
        ROW_NUMBER() OVER (
            PARTITION BY 
                fname, 
                lname, 
                phone
            ORDER BY 
                fname, 
                lname, 
                phone
        ) as row_num
		--into zzDupestoDel
     FROM clients
	 order by 1

--remove non dupes from list

select zz2.*,
case
	WHEN zz2.row_num = 1
		then 'keep'
	else 'delete'
end as dupe
--into dupechecker
from zzDupestoDel as zz1
inner join zzDupestoDel as zz2
on zz1.fname = zz2.fname
and zz1.lname = zz2.lname
and zz1.phone = zz2.phone
and zz1.client_id <> zz2.client_id



select cs.client_id as orig_id
from client_service as cs
inner join clients as c
on cs.client_id = c.client_id
inner join dupechecker as dc
on c.fname = dc.fname
and c.lname = dc.lname
and c.phone = dc.phone
where dc.dupe = 'delete'









