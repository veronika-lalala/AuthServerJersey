package entity;

import java.util.ArrayList;

//это типо DTO
public class User {
    private String login;
    private String password;
    private int id;
    private ArrayList<Crypto> cryptos;
    public User(String login,String password, int id){
        this.login=login;
        this.password=password;
        this.id=id;
        cryptos=new ArrayList<>();
    }
    public String getLogin(){
        return login;
    }

    public String getPassword() {
        return password;
    }
}
