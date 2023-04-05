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
@Table(name="countries")
@Entity
public class Country {
    @Id//primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//alanın otomatik olarak artan bir şekilde (auto-increment) değer alacağını belirtir. strategy parametresi ile artan değerlerin nasıl olacağı belirtilir.
    private String id;

    @Column(name="name")// alanın veritabanı tablosunda hangi sütuna karşılık geldiğini belirtir
    private String name;

    @JsonProperty("native")//JSON serileştirme işlemi sırasında bir alanın ismini belirler.
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
