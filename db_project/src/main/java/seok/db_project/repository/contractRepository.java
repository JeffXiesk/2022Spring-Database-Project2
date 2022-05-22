package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import seok.db_project.entity.contract;

import java.util.List;

public interface contractRepository extends JpaRepository<contract,Integer> {
    @Query("select count(contract_num) from contract")
    public int getContractCount();

    @Query("select c.enterprise,c.manager,c.supply_center from contract c where c.contract_num = ?1")
    public List<String[]> getContractInfo_1(String contract_num);

    @Query("select b.product_model,s.name,b.quantity,m.unit_price,b.estimated_delivery_date,b.lodgement_date\n" +
            "from allorder b join staff s on s.number = b.salesman_num join enterprise e on e.name = b.enterprise\n" +
            "join product p on (p.product_model = b.product_model and p.supply_center = e.supply_center ) \n" +
            "join model m on m.model = b.product_model where b.contract_num = ?1 order by b.product_model,s.name")
    public List<Object[]> getContractInfo_2(String contract_num);
}
