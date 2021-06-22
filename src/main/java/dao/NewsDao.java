package dao;
import models.News;
import java.util.List;

public interface NewsDao {
    //create
    void add(News news);

    //read

    List<News> getAllNews();

    //delete
    // void deleteById(int id);
    void clearAll();

    News findById(int newsId);
}
