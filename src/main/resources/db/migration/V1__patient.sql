create table patients
(
    id                    bigint       not null auto_increment,
    date_of_birth         date         not null,
    doses                 integer      not null,
    e_mail                varchar(100) not null,
    last_vaccination_date datetime(6),
    name                  varchar(50)  not null,
    taj                   char(9)      not null,
    vaccine_type          varchar(20),
    primary key (id)
);
alter table patients
    add constraint tajunique unique (taj);
