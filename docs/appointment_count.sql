create view appointment_count as
select row_number() OVER (ORDER BY `project-test`.`order`.`hospital_id`,`project-test`.`order`.`date` ) AS `v_id`,
       `project-test`.`order`.`date`                                                                    AS `date`,
       `project-test`.`order`.`hospital_id`                                                             AS `hospital_id`,
       count(`project-test`.`order`.`id`)                                                               AS `count`
from `project-test`.`order`
where ((`project-test`.`order`.`date` >= now()) and (`project-test`.`order`.`deprecated` = false))
group by `project-test`.`order`.`date`, `project-test`.`order`.`hospital_id`;

-- comment on column appointment_count.date not supported: 预约日期

-- comment on column appointment_count.hospital_id not supported: 所属医院编号

