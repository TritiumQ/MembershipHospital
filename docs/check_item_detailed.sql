create table if not exists check_item_detailed
(
    id                       int auto_increment comment '检查项明细编号' constraint `PRIMARY`
			primary key,
    name                     varchar(40)   not null comment '检查项细明名称',
    unit                     varchar(20)   null comment '检查项明细单位',
    min_range                double        null comment '检查项细明正常值范围中的最小值',
    max_range                double        null comment '检查项细明正常值范围中的最大值',
    normal_value             varchar(20)   null comment '检查项细明正常值（非数字型）',
    normal_value_description varchar(20)   null comment '检查项验证范围说明文字',
    type                     int default 1 not null comment '检查项明细类型（1：数值围范验证型；2：数值相等验证型；3：无需验证型；4：描述型；5：其它）',
    check_item_id            int           not null comment '所属检查项编号',
    remarks                  varchar(100)  null comment '备注',
    constraint check_item_detailed_check_item_id_fk
        foreign key (check_item_id) references check_item (id)
);

