alter table if exists license
drop constraint license_currency_id_fk;

drop table if exists currency;

create table currency
(
    name            varchar(3),
    primary key (name)
);