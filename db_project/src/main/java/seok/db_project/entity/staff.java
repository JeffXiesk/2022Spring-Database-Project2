package seok.db_project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class staff {
    @Id
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer number;
    private String supply_center;
    private String mobile_number;
    private String type;
}

