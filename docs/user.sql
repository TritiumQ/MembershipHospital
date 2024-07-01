create table if not exists user
(
    id        varchar(11)  not null comment '用户编号（手机号码）' constraint `PRIMARY`
			primary key,
    password  varchar(300) not null comment '密码',
    real_name varchar(20)  not null comment '真实姓名',
    sex       int          not null comment '用户性别（1：男；0女）',
    id_card   varchar(18)  not null comment '身份证号',
    birthday  date         not null comment '出生日期',
    type      int          not null comment '用户类型（1：普通用户；2：东软内部员工；3：其他）'
);

