package seok.db_project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class contract {
    @Id
    private Integer id;
    private String contract_num;
    private String enterprise;
    private String manager;
    private String supply_center;
}
