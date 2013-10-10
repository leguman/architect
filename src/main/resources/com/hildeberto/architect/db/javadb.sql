--liquibase formatted sql

--changeset htmfilho:1
create table application (
    id          integer      not null primary key generated always as identity (start with 1, increment by 1),
    name        varchar(50)  not null,
    description long varchar
);

create table module (
    id           integer      not null primary key generated always as identity (start with 1, increment by 1),
    application  integer      not null,
    name         varchar(50)  not null,
    description  long varchar,
    submodule_of integer
);

alter table module add constraint fk_application_module foreign key (application) references application (id);
alter table module add constraint fk_submodule_of foreign key (submodule_of) references module (id);

create table database_schema (
    id   integer     not null primary key generated always as identity (start with 1, increment by 1),
    name varchar(30) not null
);

create table database_element (
    id              integer      not null primary key generated always as identity (start with 1, increment by 1),
    name            varchar(100) not null,
    element_type    varchar(20)  not null,
    database_schema integer,
    description     long varchar,
    primary_key     varchar(255)
);
create index idx_element_type on database_element (element_type);
alter table database_element add constraint fk_schema_element foreign key (database_schema) references database_schema (id);

create table package (
    id            integer     not null primary key generated always as identity (start with 1, increment by 1),
    name          varchar(50) not null,
    application   integer,
    module        integer
);

alter table package add constraint fk_application_package foreign key (application) references application (id);
alter table package add constraint fk_module_package foreign key (module) references module (id);

create table entity_class (
    id               integer      not null primary key generated always as identity (start with 1, increment by 1),
    database_element integer      not null,
    class_name       varchar(255) not null,
    package          integer,
    application      integer,
    module           integer
);

alter table entity_class add constraint fk_element_entity foreign key (database_element) references database_element (id);
alter table entity_class add constraint fk_package_entity foreign key (package) references package (id);
alter table entity_class add constraint fk_application_entity foreign key (application) references application (id);
alter table entity_class add constraint fk_module_entity foreign key (module) references module (id);
