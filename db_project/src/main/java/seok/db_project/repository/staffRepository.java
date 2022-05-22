package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seok.db_project.entity.staff;

import java.util.List;

public interface staffRepository extends JpaRepository<staff,Integer> {
    void deleteByName(String name);

    staff findByName(String name);

    staff findByNumber(Integer number);

    @Query("select type as Type,count(type) as Count from staff group by type")
    List<Object[]> getAllStaffCount();
}
