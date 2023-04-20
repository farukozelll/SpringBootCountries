package SpringBoot.Countries.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "countries")
public class Country {
    @Id
    private String id;
    private String name;
    @Column(name = "native_name")
    private String nativeName;
    private String phone;
    private String continent;
    private String capital;
    private String currency;
    private String languages;
    private String flag;



    // Getter ve Setter'lar (Lombok tarafından otomatik olarak üretilir)

}
