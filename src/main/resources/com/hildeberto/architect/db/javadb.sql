--liquibase formatted sql

--changeset htmfilho:1
create table properties (
    property_key   varchar(100) not null,
    property_value varchar(255)     null
) engine = innodb;

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

--changeset htmfilho:3
alter table database_element drop column primary_key;

--changeset htmfilho:4
alter table code_artifact drop foreign key fk_layer_artifact;
alter table code_artifact drop index idx_layer_artifact;
alter table code_artifact drop column layer;

--changeset htmfilho:5
create table lifecycle (
    id               integer      not null primary key auto_increment,
    state            varchar(15)  not null,
    state_date       date         not null,
    lifecycle_type   varchar(20)  not null,
    object           integer      not null
) engine = innodb;

alter table database_element add lifecycle_state varchar(15) null;
create index idx_lifecycle_element on database_element (lifecycle_state);

alter table code_artifact add lifecycle_state varchar(15) null;
create index idx_lifecycle_artifact on code_artifact (lifecycle_state);

-- changeset htmfilho:6
alter table code_artifact add cacheable tinyint(1) null;

--changeset htmfilho:7
create table server (
    id          integer       not null primary key auto_increment,
    name        varchar(100)  not null,
    domain_name varchar(255)      null,
    ip_address  varchar(40)       null,
    server_role varchar(20)       null,
    description text              null
) engine = innodb;

create table server_log (
    id               integer      not null primary key auto_increment,
    server           integer      not null,
    name             varchar(20)  not null,
    location         varchar(255) not null,
    filename_pattern varchar(255) not null,
    record_pattern   varchar(255) not null,
    filter           varchar(20)      null
) engine = innodb;

create index idx_server_log on server_log (server);
alter table server_log add constraint fk_server_log foreign key (server) references server (id) on delete cascade;

create table log_record (
    id              bigint       not null primary key auto_increment,
    server          integer      not null,
    log             integer      not null,
    instant         timestamp    not null,
    level           varchar(20)      null,
    product         varchar(100)     null,
    logger_name     varchar(255)     null,
    key_value_pairs varchar(255)     null,
    message         text             null
) engine = innodb;

create index idx_server_log_record on log_record (server);
create index idx_record_log on log_record (log);
alter table log_record add constraint fk_server_log_record foreign key (server) references server (id) on delete cascade;
alter table log_record add constraint fk_record_log foreign key (log) references server (id) on delete cascade;

--changeset htmfilho:8
create table functionality (
    id          integer      not null primary key auto_increment,
    application integer      not null,
    name        varchar(100) not null,
    module      integer          null,
    description text             null
) engine = innodb;

create index idx_application_functionality on functionality (application);
create index idx_module_functionality on functionality (module);
alter table functionality add constraint fk_application_functionality foreign key (application) references application (id) on delete cascade;
alter table functionality add constraint fk_module_functionality foreign key (module) references module (id) on delete set null;

create table action (
    id            integer     not null primary key auto_increment,
    functionality integer     not null,
    name          varchar(30) not null,
    description   text            null
) engine = innodb;

create index idx_functionality_action on action (functionality);
alter table action add constraint fk_functionality_action foreign key (functionality) references functionality (id) on delete cascade;

--changeset htmfilho:9
alter table module add acronym varchar(20) null;
alter table functionality add acronym varchar(20) null;
alter table action add acronym varchar(20) null;

--changeset htmfilho:10
alter table module modify acronym varchar(25);
alter table functionality modify acronym varchar(25);
alter table action modify acronym varchar(25);