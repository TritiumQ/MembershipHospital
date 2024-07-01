create table if not exists year_report
(
    id      int auto_increment constraint `PRIMARY`
			primary key,
    user_id varchar(255)   not null,
    year    int            not null,
    outlay  decimal(12, 2) not null,
    constraint year_report_user_id_fk
        foreign key (user_id) references user (id)
);

