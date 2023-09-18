alter table if exists credential
    add constraint credential_license_id_fk foreign key (license_id) references license;

alter table if exists request
    add constraint request_license_id_fk foreign key (license_id) references license;

alter table if exists request
    add constraint request_user_id_fk foreign key (user_id) references "user";

alter table if exists "user"
    add constraint user_workplace_id_fk foreign key (workplace_id) references workplace;

alter table if exists license
    add constraint license_currency_id_fk foreign key (currency_id) references currency;

alter table if exists user_role
    add constraint user_role_user_id_fk foreign key (user_id) references "user";

alter table if exists user_role
    add constraint user_role_role_id_fk foreign key (role_id) references role;

alter table if exists workplace
    add constraint workplace_discipline_id_fk foreign key (discipline_id) references discipline;

alter table if exists workplace
    add constraint workplace_location_id_fk foreign key (location_id) references location;