package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seok.db_project.entity.model;

public interface modelRepository extends JpaRepository<model,Integer> {
    void deleteByName(String name);

    model findByNumber(String name);

    model findByModel(String name);
}
