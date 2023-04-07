package SpringBoot.Countries.entities;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor// Lombok kütüphanesinin bir özelliği olan bu annotation, sınıfın tüm alanları için bir constructor oluşturur. Bu sayede her alan için tek tek constructor tanımlamak gerekmez.
//@Data
@NoArgsConstructor//Yine Lombok kütüphanesinin bir özelliği olan bu annotation, parametresiz bir constructor oluşturur.
@Getter
@Setter
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
    @ElementCollection
    @CollectionTable(name = "country_languages", joinColumns = @JoinColumn(name = "country_id"))
    @Column(name = "language")
    private List<String> languages;
    @Column(name = "flag")
    private String flag;

    // getter ve setter metodları

    // toString() metodunun oluşturulması

}


