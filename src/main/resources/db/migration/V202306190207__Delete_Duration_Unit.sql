alter table if exists license
    drop constraint license_duration_unit_fk;

drop table if exists duration_unit;

alter table if exists license
    drop column duration_unit;