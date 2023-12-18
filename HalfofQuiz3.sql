--Q1:
select max(e.salary)
from employees e join employees f 
on f.salary > e.salary;

--Q2:
select d.department_name, avg(e.salary)
from employees e join departments d using (department_id)
group by d.department_name
having avg(e.salary) > 7000
order by 2;

