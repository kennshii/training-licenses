alter table if exists credential
    drop constraint credential_license_id_fk;

alter table if exists request
    drop constraint request_license_id_fk;


alter table if exists credential
    add constraint credential_license_id_fk foreign key (license_id) references license on delete cascade;

alter table if exists request
    add constraint request_license_id_fk foreign key (license_id) references license on delete cascade;