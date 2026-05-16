Pet Management System

REST API приложение для управления пользователями и их питомцами. Построено на Spring Boot с использованием in-memory хранилища.

Функциональность


Users (`/api/users`)

| Метод | Endpoint | Описание | Статус ответа |
| POST | `/api/users` | Создать пользователя | 201 Created |
| GET | `/api/users` | Получить всех пользователей | 200 OK |
| GET | `/api/users/{id}` | Получить пользователя по ID | 200 OK |
| PUT | `/api/users/{id}` | Обновить пользователя | 200 OK |
| DELETE | `/api/users/{id}` | Удалить пользователя | 200 OK |

Pets (`/api/pets`)

| Метод | Endpoint | Описание | Статус ответа |
| POST | `/api/pets` | Создать питомца | 201 Created |
| GET | `/api/pets` | Получить всех питомцев | 200 OK |
| GET | `/api/pets/{id}` | Получить питомца по ID | 200 OK |
| PUT | `/api/pets/{id}` | Обновить питомца | 200 OK |
| DELETE | `/api/pets/{id}` | Удалить питомца | 200 OK |

Запрос:
curl -X POST http://localhost:8084/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "age": 25
  }'