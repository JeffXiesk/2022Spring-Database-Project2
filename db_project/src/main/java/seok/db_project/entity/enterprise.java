package seok.db_project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class enterprise {
    @Id
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String supply_center;
    private String industry;
}

