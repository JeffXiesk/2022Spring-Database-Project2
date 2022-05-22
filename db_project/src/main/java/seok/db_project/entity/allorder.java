package seok.db_project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class allorder {
    @Id
    private Integer id;
    private String contract_num;
    private String enterprise;
    private String product_model;
    private Integer quantity;
    private Integer contract_manager;
    private String contract_date;
    private String estimated_delivery_date;
    private String lodgement_date;
    private Integer salesman_num;
    private String contract_type;
}
