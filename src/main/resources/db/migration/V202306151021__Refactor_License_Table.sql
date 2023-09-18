alter table if exists credential
    drop constraint credential_license_id_fk;

alter table if exists request
    drop constraint request_license_id_fk;


drop table if exists license;

create table license
(
    id              int8 generated by default as identity,
    name            varchar(50)    not null,
    website         varchar(50)    not null,
    license_type    varchar(8)    not null,
    description     text,
    logo            bytea,
    cost            numeric(6, 2)  not null,
    currency        varchar(3)     not null,
    duration        integer        not null,
    duration_unit   varchar(6)    not null,
    expires_on      date           not null,
    is_recurring    boolean        not null,
    seats           integer        not null,
    primary key (id)
);

alter table if exists credential
    add constraint credential_license_id_fk foreign key (license_id) references license;

alter table if exists request
    add constraint request_license_id_fk foreign key (license_id) references license;

