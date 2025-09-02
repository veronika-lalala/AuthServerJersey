package controllers;

import exceptions.IncorrectPassword;
import exceptions.NotExistingUser;
import exceptions.SameLoginException;
import requests.RegLogRequest;
import response.ErrorResponse;
import response.RegLogResponse;
import usecases.UserService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")//это говорит о том что данный класс находится на таком URI
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController implements Auth<Response,RegLogRequest>{
    private final UserService userService=UserService.getInstance();
    @POST
    @Path("/register")
    @Override
    public Response register(RegLogRequest request){
        if (checkUsername(request.getUsername()) && checkPassword(request.getPassword())) {//значит имя и пароль корректны надо пробовать дать токен
            try {
                String token = userService.register(request.getUsername(), request.getPassword());
                return Response.status(Response.Status.OK)
                        .entity(new RegLogResponse(token))
                        .build();
            } catch (SameLoginException e) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse(("Пользователь с таким логином уже существует")))
                        .build();
            }
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Пустые или состоящие только из пробелов логин и пароль не принимаются"))
                    .build();
        }

    }
    @POST
    @Path("/login")
    @Override
    public Response login(RegLogRequest request){
        System.out.println(request.getUsername());
        if (checkUsername(request.getUsername()) && checkPassword(request.getPassword())) {
            try {
                String token=userService.login(request.getUsername(), request.getPassword());
                return  Response.status(Response.Status.OK)
                        .entity(new RegLogResponse(token))
                        .build();
            } catch (NotExistingUser e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse(("Такого пользователя не существует")))
                        .build();
            } catch (IncorrectPassword e) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Неверный пароль"))
                        .build();
            }
        }
        else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Пустые или состоящие только из пробелов логин и пароль не принимаются"))
                    .build();
        }
    }
    private boolean checkUsername(String username){
        if (username==null || username.trim().isEmpty()){//трим удаляет пробелы чекаем что не пользностью из пробелов состоит
            return false;
        }
        return true;
    }
    private boolean checkPassword(String password){
        //трим удаляет пробелы чекаем что не пользностью из пробелов состоит
        return password != null && !password.trim().isEmpty();

    }

}
