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