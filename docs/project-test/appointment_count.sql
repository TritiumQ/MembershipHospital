create view appointment_count as
select `project-test`.`order`.`date` AS `date`, count(`project-test`.`order`.`id`) AS `count`
from `project-test`.`order`
where ((`project-test`.`order`.`date` >= now()) and (`project-test`.`order`.`deprecated` = false))
group by `project-test`.`order`.`date`;

-- comment on column appointment_count.date not supported: 预约日期

