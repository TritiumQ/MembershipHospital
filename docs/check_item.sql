create table if not exists check_item
(
    id      int auto_increment comment '检查项编号' constraint `PRIMARY`
			primary key,
    name    varchar(30)  not null comment '检查项名称',
    content varchar(200) not null comment '检查项内容',
    meaning varchar(200) null comment '检查项意义',
    remarks varchar(50)  null comment '备注'
);

