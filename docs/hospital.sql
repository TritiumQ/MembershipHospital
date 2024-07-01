create table if not exists hospital
(
    id             int auto_increment comment '医院编号' constraint `PRIMARY`
			primary key,
    name           varchar(30)  not null comment '医院名称',
    picture        mediumtext   not null comment '医院图片',
    telephone      varchar(20)  not null comment '医院电话',
    address        varchar(100) not null comment '医院地址',
    business_hours varchar(100) not null comment '营业时间',
    deadline       varchar(30)  not null comment '采血截止时间',
    rule           varchar(30)  not null comment '预约人数规则',
    state          int          not null comment '医院状态（1：正常；2：其他）'
);

