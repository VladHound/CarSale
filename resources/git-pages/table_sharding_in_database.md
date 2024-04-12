> < [Главная](..%2F..%2FREADME.md)

## 2. Шардинг (партиционирование) одной таблицы

### 2.1 Данные

Имеются следующие данные:

![sharding.png](..%2Fimages%2Fsharding%2Fsharding.png)

Попробуем сделать репликацию для данной таблицы по состоянию. Т.е. разделить новые, хорошие, отличные, неработающие приложения и т.д. каждую в свою партицию.

### 2.2 Создание партиций

Создадим соотвествующие партиции:

```postgres-sql
create table car_mechanical(
    check (transmission = 'механика')
) inherits (car);

create table car_hybrid(
    check (transmission = 'гибрид')
) inherits (car);

create table car_automatic(
    check (transmission = 'автомат')
) inherits (car);
```

И также может потребоваться добавление триггера, который будет дополнительно фильтровать входные данные от INSERT INTO
по требуемым категориям.

Это достигается следующим кодом:

```postgres-sql
-- Создание триггера для маршрутизации вставок
CREATE OR REPLACE FUNCTION insert_car_trigger()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.transmission = 'механика' THEN
        INSERT INTO car_mechanical VALUES (NEW.*);
    ELSIF NEW.transmission = 'гибрид' THEN
        INSERT INTO car_hybrid VALUES (NEW.*);
    ELSIF NEW.transmission = 'автомат' THEN
        INSERT INTO car_automatic VALUES (NEW.*);
    ELSE
        RAISE EXCEPTION 'Invalid transmission type';
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_car_trigger
BEFORE INSERT ON car
FOR EACH ROW EXECUTE FUNCTION insert_car_trigger();
```

Теперь при добавлении данных будет отрабаывать триггер и добавлять данные в соотвествующие таблицы:

![result.png](..%2Fimages%2Fsharding%2Fresult.png)