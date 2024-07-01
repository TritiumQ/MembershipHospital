create table if not exists family
(
    id       int auto_increment constraint `PRIMARY`
			primary key,
    user_id  varchar(255)  not null,
    name     varchar(255)  not null,
    id_card  varchar(255)  not null,
    birthday varchar(255)  not null,
    phone    varchar(255)  not null,
    sex      int default 1 not null comment '1男；0女',
    constraint family_user_id_fk
        foreign key (user_id) references user (id)
);

