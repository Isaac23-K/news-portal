package models;

import java.util.Objects;

public class News {
    private int id ;
    private String content ;
    private String usernameid;
    private int idDepartment ;

    public News(String content, int idDepartment,String usernameid){
        this.content= content ;
        this.idDepartment= idDepartment;
        this.usernameid= usernameid;
    }


    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsernameId() {
        return usernameid;
    }

    public void setUsernameId(String usernameid) {
        this.usernameid = usernameid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDepartment() {
        return idDepartment;
    }
    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if (!(o instanceof News)) return  false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals ( idDepartment, news.idDepartment ) &&
                Objects.equals ( usernameid, news.usernameid )&&
                Objects.equals ( content, news.content ) ;
    }
    @Override
    public int hashCode() {
        return Objects.hash ( id, idDepartment, content);
    }
}