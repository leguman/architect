--liquibase formatted sql

--changeset htmfilho:1
create table properties (
    property_key   varchar(100) not null,
    property_value varchar(255)     null
) engine = MyISAM;

alter table properties add constraint pk_properties primary key (property_key);

create table application (
    id          integer      not null primary key auto_increment,
    name        varchar(50)  not null,
    description text             null
) engine = innodb;

create table module (
    id           integer      not null primary key auto_increment,
    application  integer      not null,
    name         varchar(50)  not null,
    description  text             null,
    submodule_of integer          null
) engine = innodb;

create index idx_application_module on module (application);
create index idx_submodule_of on module (submodule_of);
alter table module add constraint fk_application_module foreign key (application) references application (id) on delete cascade;
alter table module add constraint fk_submodule_of foreign key (submodule_of) references module (id) on delete set null;

create table database_instance (
    id          integer     not null primary key auto_increment,
    name        varchar(50) not null,
    description text            null
) engine = innodb;

create table database_schema (
    id           integer     not null primary key auto_increment,
    name         varchar(30) not null,
    database_ins integer     not null,
    description  text            null
) engine = innodb;

create index idx_instance_schema on database_schema (database_ins);
alter table database_schema add constraint fk_instance_schema foreign key (database_ins) references database_instance (id) on delete cascade;

create table database_element (
    id              integer      not null primary key auto_increment,
    name            varchar(100) not null,
    element_type    varchar(20)  not null,
    database_ins    integer      not null,
    database_schema integer          null,
    description     text             null,
    primary_key     varchar(255)     null
) engine = innodb;

create index idx_instance_element on database_element (database_ins);
create index idx_schema_element on database_element (database_schema);
create index idx_element_type on database_element (element_type);
alter table database_element add constraint fk_instance_element foreign key (database_ins) references database_instance (id) on delete cascade;
alter table database_element add constraint fk_schema_element foreign key (database_schema) references database_schema (id) on delete set null;

create table package (
    id            integer     not null primary key auto_increment,
    name          varchar(50) not null,
    application   integer     not null,
    module        integer         null,
    description   text            null
) engine = innodb;

create index idx_application_package on package (application);
create index idx_module_package on package (module);
alter table package add constraint fk_application_package foreign key (application) references application (id) on delete cascade;
alter table package add constraint fk_module_package foreign key (module) references module (id) on delete set null;

create table entity_class (
    id               integer      not null primary key auto_increment,
    class_name       varchar(255) not null,
    application      integer      not null,  
    module           integer          null,
    package          integer          null,
    database_element integer          null,
    description      text             null
) engine = innodb;

create index idx_application_entity on entity_class (application);
create index idx_module_entity on entity_class (module);
create index idx_package_entity on entity_class (package);
create index idx_database_element_entity on entity_class (database_element);
alter table entity_class add constraint fk_application_entity foreign key (application) references application (id) on delete cascade;
alter table entity_class add constraint fk_module_entity foreign key (module) references module (id) on delete set null;
alter table entity_class add constraint fk_package_entity foreign key (package) references package (id) on delete set null;
alter table entity_class add constraint fk_database_element_entity foreign key (database_element) references database_element (id) on delete set null;

--changeset htmfilho:2
create table layer (
    id          integer      not null primary key auto_increment,
    name        varchar(50)  not null,
    description text             null
) engine = innodb;

alter table package add layer integer null;
create index idx_layer_package on package (layer);
alter table package add constraint fk_layer_package foreign key (layer) references layer (id) on delete set null;

drop table if exists entity_class;

create table code_artifact (
    id               integer      not null primary key auto_increment,
    name             varchar(255) not null,
    artifact_type    varchar(20)  not null,
    application      integer      not null,
    module           integer          null,
    package          integer          null,
    layer            integer          null,
    description      text             null,
    database_element integer          null
) engine = innodb;

create index idx_application_artifact on code_artifact (application);
create index idx_module_artifact on code_artifact (module);
create index idx_package_artifact on code_artifact (package);
create index idx_layer_artifact on code_artifact (layer);
create index idx_database_element_artifact on code_artifact (database_element);
alter table code_artifact add constraint fk_application_artifact foreign key (application) references application (id) on delete cascade;
alter table code_artifact add constraint fk_module_artifact foreign key (module) references module (id) on delete set null;
alter table code_artifact add constraint fk_package_artifact foreign key (package) references package (id) on delete set null;
alter table code_artifact add constraint fk_layer_artifact foreign key (layer) references layer (id) on delete set null;
alter table code_artifact add constraint fk_database_element_artifact foreign key (database_element) references database_element (id) on delete set null;
