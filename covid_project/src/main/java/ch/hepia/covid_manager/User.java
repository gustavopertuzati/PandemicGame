package ch.hepia.covid_manager;

import javafx.application.Application;
import javafx.scene.control.Button;

public class User{
    private int id;
    private String username;
    private Button button = new Button();

    public User(int id, String username){
        this.id = id;
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public int getUserId(){
        return this.id;
    }
}