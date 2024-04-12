# CAR SALE

Приложение Car Sale предоставляет следующие возможности:

- Поиск автомобилей по параметрам
- Сравнение автомобилей
- Оформление сделки купли-продажи

## Технологии

Backend:

- Spring Boot (Data, Security, ...), Java 17, Postgresql 15, docker, Kafka, Redis, JWT

## Задания

1. Настроить репликацию master - slave на одной из БД и распределить запросы приложения между репликами

Реализация: [replic_master_slave.md](resources%2Fgit-pages%2Freplic_master_slave.md)

2. Сделать шардинг (партиционирование) одной таблицы

Реализация: [table_sharding_in_database.md](resources%2Fgit-pages%2Ftable_sharding_in_database.md)