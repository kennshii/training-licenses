alter table if exists license_history
    add constraint license_history_license_id_fk foreign key (license_id) references license on delete cascade;