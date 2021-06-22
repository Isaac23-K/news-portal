package dao;

import models.DB;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNewsDao implements  NewsDao{
    private final Sql2o sql2o;

    public Sql2oNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    //        String sql = "INSERT INTO news(content,idDepartment,usernameid) VALUES (:content,:idDepartment,:usernameid)";
    @Override
    public void add(News news) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO news(content,idDepartment,usernameid) VALUES (:content,:idDepartment,:usernameid)";
            int id = (int) con.createQuery(sql, true)
                    .addParameter("content",news.getContent())
                    .addParameter("idDepartment",news.getIdDepartment())
                    .addParameter("usernameid",news.getUsernameId())
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public List<News> getAllNews() {
        try( Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }

//    @Override
//    public void deleteById(int id) {
//        String sql = "DELETE from news WHERE id=:id"; //raw sql
//        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .addParameter("id", id)
//                    .executeUpdate();
//        } catch (Sql2oException ex){
//            System.out.println(ex);
//        }
//    }

    @Override
    public void clearAll() {
        String sql = "DELETE from news";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public News findById(int newsId) {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM news WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",newsId)
                    .executeAndFetchFirst(News.class);
        }
    }
}