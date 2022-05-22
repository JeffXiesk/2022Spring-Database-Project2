package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import seok.db_project.entity.center;

import java.util.List;

public interface centerRepository extends JpaRepository<center,Integer> {
    void deleteByName(String name);

    center findByName(String name);

}
