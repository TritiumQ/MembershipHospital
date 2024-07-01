create table if not exists `order`
(
    id          int auto_increment comment '订单编号' constraint `PRIMARY`
			primary key,
    date        date                 not null comment '预约日期',
    user_id     varchar(11)          not null comment '客户编号',
    hospital_id int                  not null comment '所属医院编号',
    package_id  int                  not null comment '所属套餐编号',
    state       int        default 1 not null comment '订单状态（1：未归档；2：已归档）',
    deprecated  tinyint(1) default 0 not null comment '标记删除（0/false: 未删除，1/true: 已删除）',
    pay         int        default 0 not null comment '0:未支付； 1: 已支付',
    family_id   int                  null,
    constraint order_hospital_id_fk
        foreign key (hospital_id) references hospital (id),
    constraint order_package_id_fk
        foreign key (package_id) references package (id),
    constraint order_user_id_fk
        foreign key (user_id) references user (id)
);

