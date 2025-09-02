package usecases;

import exceptions.IncorrectPassword;
import exceptions.NotExistingUser;
import exceptions.SameLoginException;

public interface UserUsecases {
    String register (String login, String password) throws SameLoginException;
    String login(String login, String password) throws NotExistingUser, IncorrectPassword;

}
