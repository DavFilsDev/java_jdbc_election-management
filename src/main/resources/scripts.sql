create database election_management_db;

create user election_management_db_manager with password 'admin_election_management_db';

grant connect on database election_management_db to election_management_db_manager ;

\c election_management_db;

grant create on schema public to election_management_db_manager;

alter default privileges in schema public
    grant select, insert, update, delete on tables to election_management_db_manager;

alter default privileges in schema public
      grant usage, select, update on sequences to  election_management_db_manager;