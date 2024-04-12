> < [Главная](..%2F..%2FREADME.md)

## 1. Настройка репликации master-slave

Образ: `docker pull bitnami/postgresql:16`

### 1.1 Создать мастер образ

```shell
docker run -dti --name car-master \
    -p 5001:5432 \
    -e POSTGRESQL_REPLICATION_MODE=master \
    -e POSTGRESQL_USERNAME=user \
    -e POSTGRESQL_PASSWORD=password \
    -e POSTGRESQL_DATABASE=car_db \
    -e POSTGRESQL_REPLICATION_USER=repluser \
    -e POSTGRESQL_REPLICATION_PASSWORD=replpassword \
    bitnami/postgresql:16
```

### 1.2 Создать слейв образ

```shell
docker run -dti --name car-slave-001 \
  -p 5101:5432 \
  --link car-master:master \
  -e POSTGRESQL_REPLICATION_MODE=slave \
  -e POSTGRESQL_MASTER_HOST=master \
  -e POSTGRESQL_MASTER_PORT_NUMBER=5001 \
  -e POSTGRESQL_REPLICATION_USER=repluser \
  -e POSTGRESQL_REPLICATION_PASSWORD=replpassword \
  bitnami/postgresql:16
```

### 1.3 Результат выполнения

![create_master_slave_1.png](..%2Fimages%2Freplica_task%2Fcreate_master_slave_1.png)
![create_master_slave_2.png](..%2Fimages%2Freplica_task%2Fcreate_master_slave_2.png)

### 2. Проверка выполнения

1. Запустим в новосозданном контейнере наш сервис по работе автомобилями.
   Данный этап подразумевает под собой создание всех необходимых таблиц:

![create_table_1.png](..%2Fimages%2Freplica_task%2Fcreate_table_1.png)
![create_table_2.png](..%2Fimages%2Freplica_task%2Fcreate_table_2.png)

Данные были настроены на обоих контейнерах.

![check_master_slave_1.png](..%2Fimages%2Freplica_task%2Fcheck_master_slave_1.png)
![check_master_slave_2.png](..%2Fimages%2Freplica_task%2Fcheck_master_slave_2.png)

Добавим данные:

![master_slave_step_1.png](..%2Fimages%2Freplica_task%2Fmaster_slave_step_1.png)
![master_slave_step_2.png](..%2Fimages%2Freplica_task%2Fmaster_slave_step_2.png)
![master_slave_step_3.png](..%2Fimages%2Freplica_task%2Fmaster_slave_step_3.png)