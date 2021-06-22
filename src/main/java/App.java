import com.google.gson.Gson;
import dao.*;
import exceptions.ApiException;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Sql2oNewsDao NewsDao;
        Sql2oUserDao UserDao;
        Sql2oDepartmentsDao DepartmentDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/company_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "isaac", "kaptoge");

        //String connectionString = "jdbc:postgresql://ec2-52-7-115-250.compute-1.amazonaws.com:5432/dc8bft1u67afbm";
        //Sql2o sql2o = new Sql2o(connectionString, "vqcxtbeajknnjo", "f0fec41d48de0c81e637f82c77add701d154742d9f1298adca316fc0558a4b69");


        DepartmentDao = new Sql2oDepartmentsDao(sql2o);
        UserDao = new Sql2oUserDao(sql2o);
        NewsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();

        get("/", "application/json", (req, res) ->
                "{\"message\":\"Welcome to the Organisation news api.\"}");


        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            UserDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });


        get("/users", "application/json", (req, res) -> { //accept a request in format JSON from an app
            User user = gson.fromJson(req.body(),User.class);
            UserDao.add(user);
            res.status(201);
            return gson.toJson(UserDao.getAll());//send it back to be displayed
        });

        get("/users/:id", "application/json", (req, res) -> {
            int userid = Integer.parseInt(req.params("id"));
            User usersToFind = UserDao.getUserById(userid);
            if(usersToFind == null) {
                throw new ApiException (404, String.format("No user with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(usersToFind);
        });
        exception(ApiException.class, (exception, request, response) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });
        after((request, response) ->{
            response.type("application/json");
        });

        get("/departments","application/json" ,(req, res) -> {
            Department department = gson.fromJson(req.body(),Department.class);
            DepartmentDao.add(department);
            res.status(201);
            return gson.toJson(DepartmentDao.getAllDepartment());
        });

        post("/departments/new","application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(),Department.class);
            DepartmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        get("/news","application/json", (request, response) -> {
            System.out.println(NewsDao.getAllNews());
            return gson.toJson(NewsDao.getAllNews());
        });

        get("/news/:id","application/json",(request, response) -> {
            int newsId = Integer.parseInt(request.params("id"));
            News newsToFind = NewsDao.findById(newsId);
            if (newsToFind==null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists",request.params("id")));
            }
            return gson.toJson(newsToFind);
        });

        post("/news/new","application/json", (req, res) -> {
            News news = gson.fromJson(req.body(),News.class);
            NewsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });
    }
}
//postgresql-colorful-35883
