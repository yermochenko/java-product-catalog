@echo off
set Path=%Path%;D:\programs\pgsql\bin
set PGPASSWORD=root
psql --username=root --dbname=postgres --file=create_db.sql
psql --username=root --dbname=store_db --file=create_tables.sql
psql --username=root --dbname=store_db --command="\encoding UTF8" --file=fill_tables_test.sql
pause