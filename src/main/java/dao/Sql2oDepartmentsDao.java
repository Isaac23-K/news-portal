package dao;

import models.DB;
import models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentDao{
    private final Sql2o sql2o;

    public Sql2oDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments(nameOfDepartment, detail, numberEmployees) VALUES (:nameOfDepartment, :detail, :numberEmployees);";
        try (Connection con = DB.sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public List<Department> getAllDepartment() {
        try( Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

