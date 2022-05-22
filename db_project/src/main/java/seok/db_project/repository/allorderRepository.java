package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import seok.db_project.entity.allorder;

import java.util.List;

public interface allorderRepository extends JpaRepository<allorder,String> {
    @Transactional
    @Query("select distinct a.contract_num,a.enterprise,s.name,e.supply_center from allorder a join enterprise e\n" +
            "on e.name=a.enterprise join staff s on s.number = a.contract_manager")
    List<String[]> findAllInfo();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update allorder set quantity = ?1 where salesman_num = ?2 and contract_num = ?3 and product_model = ?4")
    void updateQuantityOfSalesmenAndModel(Integer q, Integer s_n, String contrac_num, String product_model);

    @Query("select a from allorder a where a.salesman_num= ?1 and a.contract_num = ?2 and a.product_model = ?3")
    allorder findOrder(Integer s_n,String con_num, String model);

    @Transactional
    @Modifying
    @Query("delete from allorder where contract_num = ?1 and product_model = ?2 and salesman_num = ?3")
    void deleteOrder(String c_n,String p_m,Integer s_n);

    @Query("select a from allorder a where a.contract_num = ?1 and a.salesman_num = ?2")
    List<allorder> findByContract_numAndSalesman_num(String contract_num, Integer salesman_num);

    @Query("select count(id) from allorder")
    int getOrderCount ();

}
