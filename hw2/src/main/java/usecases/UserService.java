package usecases;

import config.JwtUtil;
import entity.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistingUser;
import exceptions.SameLoginException;
import org.mindrot.jbcrypt.BCrypt;
import repo.ram_storage.UserRamStorage;

//контроллер посылает запрос на сервис здесь происходит какая-то бизнес логика
public class UserService implements UserUsecases {
    private static UserService userService;
    private UserRamStorage ramStorage;
    private JwtUtil jwtUtil;
    private UserService(){
        ramStorage=new UserRamStorage();//TODO посмотреть @Inject и может избавиться от конструкторов
        jwtUtil=JwtUtil.getInstance();
    }
    public static UserService getInstance(){
        if (userService==null){
            userService=new UserService();
        }
        return userService;
    }
    @Override
    public String register(String login, String password) throws SameLoginException {
        User existingUser= get(login);
        if (existingUser==null) {//значит пользователя с таким логином нет, всё хорошо
            return handleNewUser(login, password);

        }
        throw new SameLoginException("Пользователь с таким логином уже существует");

    }

    @Override
    public String login(String login, String password) throws NotExistingUser, IncorrectPassword {
        User existingUser = get(login);
        if(existingUser==null){//значит нет пользователя с таким логином
          throw new NotExistingUser("Пользователя с таким логином не существует");

        }
        return handleExistingUser(login,password,existingUser);


    }
    public User get(String username){
        System.out.println(ramStorage);
        return ramStorage.get(username).orElse(null);
    }
    private String handleNewUser(String username,String password){
        String hashed_password= BCrypt.hashpw(password,BCrypt.gensalt());
        ramStorage.save(username,hashed_password);
        return jwtUtil.generateToken(username);

    }
    private String handleExistingUser(String username,String password,User existingUser) throws IncorrectPassword {
        if (BCrypt.checkpw(password,existingUser.getPassword())){
            return jwtUtil.generateToken(username);
        }
        else{
            throw new IncorrectPassword("Пароль неверный");
        }
    }
}
