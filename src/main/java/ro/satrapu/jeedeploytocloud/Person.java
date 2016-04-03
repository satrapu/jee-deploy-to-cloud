package ro.satrapu.jeedeploytocloud;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Persons")
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    @Setter(AccessLevel.PRIVATE)
    private Integer id;

    @Version
    @Column(name = "Version")
    @Setter(AccessLevel.PRIVATE)
    private Integer version;

    @NotNull
    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "MiddleName", nullable = true)
    private String middleName;

    @NotNull
    @Column(name = "LastName", nullable = false)
    private String lastName;
}
