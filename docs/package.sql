create table if not exists package
(
    id    int auto_increment comment '套餐编号' constraint `PRIMARY`
			primary key,
    name  varchar(255) not null comment '套餐名称',
    type  int          not null comment '套餐类型（2：通用套餐； 1：男士餐套；0：女士套餐）',
    price int          not null comment '套餐价格'
);

