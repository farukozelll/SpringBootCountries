package SpringBoot.Countries.entities;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
//@Data
@NoArgsConstructor
@Getter
@Setter
@Table(name="countries")
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;//primary key

    @Column(name="name")
    private String name;

    @Column(name = "native_name")
    private String nativeName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "continent")
    private String continent;

    @Column(name = "capital")
    private String capital;

    @Column(name = "currency")
    private String currency;

    @Column(name = "languages")
    private List<String> languages;

    @Column(name = "flag")
    private String flag;

}
