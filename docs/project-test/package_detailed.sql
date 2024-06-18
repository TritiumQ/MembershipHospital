create table if not exists package_detailed
(
    id            int auto_increment comment '套餐明细编号（无意义主键）' constraint `PRIMARY`
			primary key,
    package_id    int not null comment '套餐编号',
    check_item_id int not null comment '检查项编号',
    constraint package_detailed_check_item_id_fk
        foreign key (check_item_id) references check_item (id),
    constraint package_detailed_package_id_fk
        foreign key (package_id) references package (id)
);

