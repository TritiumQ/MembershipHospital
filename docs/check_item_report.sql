create table if not exists check_item_report
(
    id            int auto_increment comment '检查项报告主键' constraint `PRIMARY`
			primary key,
    check_item_id int          not null comment '检查项编号',
    order_id      int          not null comment '所属预约编号',
    review        varchar(255) null comment '体检项目分析评价',
    constraint check_item_report_check_item_id_fk
        foreign key (check_item_id) references check_item (id),
    constraint check_item_report_order_id_fk
        foreign key (order_id) references `order` (id)
);

