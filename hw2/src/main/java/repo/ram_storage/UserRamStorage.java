package repo.ram_storage;

import entity.User;
import repo.Storage;

import java.util.*;


public class UserRamStorage implements Storage<User> {
    private Map<String,User> allUsers;
    public UserRamStorage(){
        allUsers=new HashMap<>();
    }

    @Override
    public Optional<User> get(String login) {//метод чтобы достать пользователя для проверки паролей
        return Optional.ofNullable(allUsers.get(login));
    }

    @Override
    public void save(String login, String hashedPassword) {
        User user=new User(login,hashedPassword,allUsers.size());//первый пользователь будет иметь id=0 который как ра равен пусто мапе
        allUsers.put(login,user);

    }


}
