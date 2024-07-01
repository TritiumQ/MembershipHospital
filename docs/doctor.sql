create table if not exists doctor
(
    id        int auto_increment comment '医生编号' constraint `PRIMARY`
			primary key,
    code      varchar(20) not null comment '医生编码',
    real_name varchar(20) not null comment '真实姓名',
    password  varchar(20) not null comment '密码',
    sex       int         not null comment '性别',
    dept_no   int         not null comment '所属科室（1：检验科；2：内科；3：外科）',
    email     varchar(50) not null,
    constraint docCode
        unique (code)
);

