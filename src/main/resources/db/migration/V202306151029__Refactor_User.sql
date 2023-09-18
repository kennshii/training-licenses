alter table if exists "user"
    drop constraint user_workplace_id_fk;

alter table if exists user_role
    drop constraint user_role_user_id_fk;

alter table if exists request
    drop constraint request_user_id_fk;


drop table if exists "user";

create table "user"(
    id                  int8 generated by default as identity,
    first_name          varchar(50)    not null,
    last_name           varchar(50)    not null,
    workplace_id        int8           not null,
    registration_date   date           not null,
    is_active           boolean        not null,
    last_active         date           not null,
    primary key (id)
);

alter table if exists "user"
    add constraint user_workplace_id_fk foreign key (workplace_id) references workplace;

alter table if exists user_role
    add constraint user_role_user_id_fk foreign key (user_id) references "user";

alter table if exists request
    add constraint request_user_id_fk foreign key (user_id) references "user";



