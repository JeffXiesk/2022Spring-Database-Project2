package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seok.db_project.entity.enterprise;

public interface enterpriseRepository extends JpaRepository<enterprise,Integer> {
    void deleteByName(String name);

    enterprise findByName(String name);

}
