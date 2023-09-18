alter table if exists request
    add constraint request_status_fk foreign key (status) references request_status;
