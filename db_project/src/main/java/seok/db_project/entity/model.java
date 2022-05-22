package seok.db_project.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class model {
    @Id
    private Integer id;
    private String number;
    private String model;
    private String name;
    private Integer unit_price;
}
