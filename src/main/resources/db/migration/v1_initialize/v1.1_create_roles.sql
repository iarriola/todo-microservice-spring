do $$
begin
  perform * from pg_roles r
  where r.rolname = 'todo_app';
  if not found then
    create role todo_app with password 'todo_app';
  end if;
end;
$$ language plpgsql;

alter role todo_app with nosuperuser inherit nocreaterole nocreatedb login noreplication;

alter role todo_app set search_path to todo;
