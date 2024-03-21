create table if not exists authors (
    id bigserial,
    full_name varchar(255) not null,
    primary key (id)
);

create table if not exists genres (
    id bigserial,
    name varchar(255) not null,
    primary key (id)
);

create table if not exists books (
    id bigserial,
    title varchar(255) not null,
    author_id bigint not null references authors (id) on delete cascade,
    genre_id bigint not null references genres(id) on delete cascade,
    primary key (id)
);

create table if not exists comments (
    id bigserial,
    text varchar(255) not null,
    book_id bigint not null references books (id) on delete cascade,
    primary key (id)
);

-- Security --

create table if not exists users
(
    id bigserial,
    username varchar(50) unique not null,
    password varchar(60) not null,
    primary key (id)
);
create table if not exists roles
(
    id bigserial,
    name varchar(50) not null,
    primary key (id)
);

create table if not exists user_role
(
    user_id bigint not null references users(id) on delete cascade,
    role_id bigint not null references roles(id) on delete cascade
);

-- ACL --

create table if not exists acl_sid (
  id bigint not null auto_increment,
  principal tinyint not null,
  sid varchar(100) not null,
  primary key (id),
  unique (sid,principal)
);

create table if not exists acl_class (
  id bigint not null auto_increment,
  class varchar(255) not null,
  primary key (id),
  unique (class)
);

create table if not exists acl_object_identity (
  id bigint not null auto_increment,
  object_id_class bigint not null,
  object_id_identity bigint not null,
  parent_object bigint default null,
  owner_sid bigint default null,
  entries_inheriting tinyint not null,
  primary key (id),
  unique (object_id_class,object_id_identity)
);

alter table acl_object_identity
add foreign key (parent_object) references acl_object_identity (id);
alter table acl_object_identity
add foreign key (object_id_class) references acl_class (id);
alter table acl_object_identity
add foreign key (owner_sid) references acl_sid (id);

create table if not exists acl_entry (
                                         id bigint not null auto_increment,
                                         acl_object_identity bigint not null,
                                         ace_order int not null,
                                         sid bigint not null,
                                         mask int not null,
                                         granting tinyint not null,
                                         audit_success tinyint not null,
                                         audit_failure tinyint not null,
                                         primary key (id),
                                         unique (acl_object_identity,ace_order)
);
alter table acl_entry
add foreign key (acl_object_identity) references acl_object_identity(id);
alter table acl_entry
add foreign key (sid) references acl_sid(id);