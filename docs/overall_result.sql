create table if not exists overall_result
(
    id       int auto_increment comment '总检结论项编号' constraint `PRIMARY`
			primary key,
    title    varchar(40)  not null comment '总检结论项标题',
    content  varchar(400) null comment '总检结论项内容',
    order_id int          not null comment '所属预约编号',
    constraint overall_result_order_id_fk
        foreign key (order_id) references `order` (id)
);

