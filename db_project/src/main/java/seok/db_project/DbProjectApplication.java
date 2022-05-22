package seok.db_project;

import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
public class DbProjectApplication {

    public static void main(String[] args) {
        ApplicationContext a = SpringApplication.run(DbProjectApplication.class, args);
        DataSource dataSource = a.getBean(javax.sql.DataSource.class);
        System.out.println("DATABASE: " + dataSource);

        int active_count = 0;
        int idle_count = 0;
        int total_count = 0;

//        while (true) {
//            HikariPool hikariPool = (HikariPool) new DirectFieldAccessor(dataSource).getPropertyValue("pool");
//            if (active_count != hikariPool.getActiveConnections() || idle_count != hikariPool.getIdleConnections() || total_count != hikariPool.getTotalConnections()) {
//                active_count = hikariPool.getActiveConnections();
//                idle_count = hikariPool.getIdleConnections();
//                total_count = hikariPool.getTotalConnections();
//                System.out.println("--------------------------------");
//                System.out.println("ActiveConnection count is :" + hikariPool.getActiveConnections());
//                System.out.println("IdelConnection count is :" + hikariPool.getIdleConnections());
//                System.out.println("TotalConnection count is :" + hikariPool.getTotalConnections());
//                System.out.println("--------------------------------");
//            }
//        }
    }
}
