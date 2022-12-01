set statement_timeout = 0;
set lock_timeout = 0;
set client_encoding = 'utf8';
set standard_conforming_strings = on;
set check_function_bodies = false;
set client_min_messages = warning;
set row_security = off;

create schema if not exists todo;

alter schema todo owner to todo_app;

set search_path = todo;

create or replace function uuid_generate_v4()
    returns uuid
    language 'c'
    cost 1
    volatile strict 
as '$libdir/uuid-ossp', 'uuid_generate_v4';

create table task (
    id smallserial primary key,
    uuid uuid not null default uuid_generate_v4(),
    title varchar(100) not null,
    description text not null,
    created_at timestamptz not null default current_timestamp,
    completed_at timestamptz null default null,
    deleted_at timestamptz null default null
);

alter table task owner to todo_app;

grant select, update, insert, delete on all tables in schema todo to todo_app;
grant select, update, usage on all sequences in schema todo to todo_app;

create index if not exists todo_task_id_idx on task using btree (id);
create index if not exists todo_task_uuid_idx on task using btree (uuid);
