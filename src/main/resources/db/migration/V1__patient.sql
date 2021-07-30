create table patients
(
    id                    bigint       not null auto_increment,
    date_of_birth         date         not null,
    doses                 integer      not null,
    e_mail                varchar(255) not null,
    last_vaccination_date datetime(6),
    taj                   char(9)      not null,
    vaccine_type          integer,
    primary key (id)
);
alter table patients
    add constraint tajunique unique (taj);
