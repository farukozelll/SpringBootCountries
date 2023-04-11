package SpringBoot.Countries.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "country")
public class Country {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
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
    private String languages;

    @Column(name = "flag")
    private String flag;

}
