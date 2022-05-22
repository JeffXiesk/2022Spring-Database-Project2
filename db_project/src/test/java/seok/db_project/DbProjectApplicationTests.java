package seok.db_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seok.db_project.entity.allorder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest(properties = "spring.datasource.type=com.zaxxer.hikari.HikariDataSource")
class DbProjectApplicationTests {

    @Autowired
    private DataSource dataSource;


    @Test
    public void test1() throws SQLException {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());

        System.out.println("catalog:" + dataSource);
    }

    @Test
    public void test2() throws SQLException {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        System.out.println("catalog:" + dataSource);
    }

    @Test
    public void test3() throws SQLException {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        System.out.println("catalog:" + dataSource.getConnection().getCatalog());
    }

    @Test
    public void test4() throws SQLException {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        System.out.println("catalog:" + dataSource.getConnection().getCatalog());
    }

}
