alter table if exists request
drop constraint request_user_id_fk;

alter table if exists request
    add constraint request_user_id_fk foreign key (user_id) references "user" on delete cascade;


alter table if exists "user"
drop constraint user_workplace_id_fk;

alter table if exists "user"
    add constraint user_workplace_id_fk foreign key (workplace_id) references workplace on delete cascade;


alter table if exists workplace
drop constraint workplace_discipline_id_fk;

alter table if exists workplace
drop constraint workplace_location_id_fk;

alter table if exists workplace
    add constraint workplace_location_id_fk foreign key (location_id) references location on delete cascade;

alter table if exists workplace
    add constraint workplace_discipline_id_fk foreign key (discipline_id) references discipline on delete cascade;

