# AuthServerJersey
HTTP REST API сервер с системой аутентификации
Реализован с использованием фреймворка Jersey и Grizzly. Для аутентификации исопльзуется JWT токен, все пароли хэшированы.
Сервер ещё не закончен, планируется добавить функционал и организовать хранение данных в бд
_______
## API

### Аутентификация
- **POST /auth/register** - регистрация нового пользователя
  - Body: `{"username": "string", "password": "string"}`
  - Success (201): `{"token": "string"}`
  - Error (400/409): `{"error": "string"}`

- **POST /auth/login** - вход пользователя
  - Body: `{"username": "string", "password": "string"}`
  - Success (200): `{"token": "string"}`
  - Error (400/401): `{"error": "string"}`
