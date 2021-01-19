set statement_timeout = 0;
set lock_timeout = 0;
set client_encoding = 'utf8';
set standard_conforming_strings = on;
set check_function_bodies = false;
set client_min_messages = warning;
set row_security = off;

set search_path = todo;

insert into task (
    uuid,
    title,
    description
)
values
    ('846cba17-06da-4c63-9d61-23f729f75f13','Shopping list','This is for making the groceries'),
    ('7cb19f48-aa32-4058-b0dd-3a0c69b2d781', 'Home duties', 'Cleaning my room, doing the, etc.');
