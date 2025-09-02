package controllers;



public interface Auth <A,R>{
    A register(R r);
    A login(R r);
}
