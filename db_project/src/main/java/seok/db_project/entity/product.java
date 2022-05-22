package seok.db_project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class product {
    @Id
    private Integer id;
    private String supply_center;
    private String product_model;
    private Integer supply_staff;
    private String date;
    private Integer purchase_price;
    private Integer quantity;
    private Integer remain;
}
