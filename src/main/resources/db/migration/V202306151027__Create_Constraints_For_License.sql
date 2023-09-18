alter table if exists license
    add constraint license_currency_fk foreign key (currency) references currency;

alter table if exists license
    add constraint license_duration_unit_fk foreign key (duration_unit) references duration_unit;

alter table if exists license
    add constraint license_license_type_fk foreign key (license_type) references license_type;

alter table if exists license
    add unique(name);
