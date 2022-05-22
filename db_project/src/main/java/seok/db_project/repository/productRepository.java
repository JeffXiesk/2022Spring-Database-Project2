package seok.db_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import seok.db_project.entity.product;

import java.util.List;

public interface productRepository extends JpaRepository<product, Integer> {
    @Query("SELECT t FROM product t WHERE t.supply_center = ?1 AND t.product_model = ?2")
    List<product> findAllBySupply_centerAndAndProduct_model(String Supply_center, String Product_model);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update product set remain = ?1 where supply_center = ?2 and product_model = ?3")
    void updateRemainOfSupply_centerAndProduct_model(Integer price, String supply_center, String product_model);

    @Transactional
    @Modifying
    @Query("delete from product where supply_center = ?1 and product_model = ?2 and not remain = ?3")
    void deleteDupicate(String s_c, String p_m, Integer remain);

    @Query("select p.product_model from product p group by p.product_model having sum(p.quantity)=sum(p.remain)")
    List<String> findNeverSoldProductCount();

    @Query("select p.product_model as p_m,sum(p.quantity)-sum(p.remain) as sold from product p group by p.product_model")
    List<Object[]> FindFavoriteProductModel();

    @Query("select supply_center as s_c, sum(remain) as sumsto, count(remain) as cnt from product group by supply_center")
    List<Object[]> findSumStockByCenter();

    @Query("select p.supply_center,p.product_model,p.purchase_price,sum(p.remain)\n" +
            "from product as p where p.product_model\n" +
            "in (select m.model from model as m where m.number = ?1)\n" +
            "group by p.supply_center,p.product_model,p.purchase_price")
    List<Object[]> findProductByNumber(String num);

}
