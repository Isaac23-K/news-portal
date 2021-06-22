package models;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class User {
    private int id;
    private String username ;
    private int idDepartment ;
    private String role ;
    private String position ;

    public User(String username, int idDepartment, String role, String position){
        this.idDepartment= idDepartment;
        this.username = username ;
        this.role = role;
        this.position = position ;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public int getIdDepartment() {
        return idDepartment;
    }
    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {

        this.position = position;
    }

    public static List<User> getAll(){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }
}