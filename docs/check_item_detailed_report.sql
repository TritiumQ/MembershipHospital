create table if not exists check_item_detailed_report
(
    id                     int auto_increment comment '检查项明细报告主键' constraint `PRIMARY`
			primary key,
    value                  varchar(100)  null comment '检查项目明细值',
    error                  int default 0 not null comment '此项是否异常（0：无异常；1：异常）',
    check_item_report_id   int           not null comment '所属检查项报告编号',
    order_id               int           not null comment '所属预约编号',
    check_item_detailed_id int           not null,
    constraint check_item_detailed_report_check_item_detailed_id_fk
        foreign key (check_item_detailed_id) references check_item_detailed (id),
    constraint check_item_detailed_report_check_item_report_id_fk
        foreign key (check_item_report_id) references check_item_report (id),
    constraint check_item_detailed_report_order_id_fk
        foreign key (order_id) references `order` (id)
);

