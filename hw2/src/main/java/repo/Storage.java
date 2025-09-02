package repo;

import java.util.Optional;

//надо понять будет ли это интерфейс только для пользователей или
// можно сделать чтобы это было только для пользователей тогда рам и бж будут его реализовывать
public interface Storage <U> {//TODO rename
    Optional<U> get (String login);
   // void save(U user);
    void save(String login, String hashedPassword);
}