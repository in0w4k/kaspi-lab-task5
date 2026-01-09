# Homework Task 5 (CRUD Product)

Проект реализует CRUD операции для сущности Product (id, name, price).

## Технологический стек
- Java 21
- Spring Boot 3.5.9
- PostgreSQL
- Flyway (миграции БД)
- MapStruct & Lombok

## Как запустить

### Предварительные требования
- Установленный Docker Desktop
- Java 17+

### 1. Запуск базы данных
В корне проекта выполните команду:
```bash
docker-compose up -d
```

### 2. Запуск приложения
Запустите **Task5Application.java** через IntelliJ IDEA 

Приложение будет доступно по адресу: http://localhost:8080/api/products

## API Эндпоинты

| HTTP Метод | URL Путь                    | Описание                       |
|------------|-----------------------------|--------------------------------|
| **GET**    | `/api/products`             | Получить список всех продуктов |
| **GET**    | `/api/products/{id}`        | Получить один продукт по ID    |
| **POST**   | `/api/products/add`         | Создать новый продукт          |
| **PUT**    | `/api/products/update/{id}` | Обновить данные продукта       |
| **DELETE** | `/api/products/delete/{id}` | Удалить продукт                |

### Пример тела запроса для POST и PUT:

```json
{
  "name": "Product Name",
  "price": 999.99
}
```