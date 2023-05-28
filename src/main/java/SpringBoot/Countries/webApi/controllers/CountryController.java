package SpringBoot.Countries.webApi.controllers;

import SpringBoot.Countries.businness.CountryService;
import SpringBoot.Countries.entities.Country;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Builder
@RestController
@RequestMapping("/api")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable String id) {
        Optional<Country> country = countryService.getCountry(id);
        return country.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }
    @GetMapping("/name-code/{name}")
    // Belirli bir ülke kodu ile ülke bilgisi getiren endpoint.
    public List<Country> getCountryByName(@PathVariable String name) {
        return countryService.getCountryByName(name);
    }
    @GetMapping("/code/{id}")
    // Belirli bir ülke kodu ile ülke bilgisi getiren endpoint.
    public Optional<Country> getCountryByCode(@PathVariable String id) {
        return countryService.getCountryById(id);
    }
    @GetMapping("/filter/phone/{phone}")
    public List<Country> getCountriesByPhoneCode(@PathVariable String phone) {
        return countryService.getCountryByPhoneCode(phone);
    }
    @GetMapping("/sorted")
    // Telefon kodlarına göre sıralanmış ülkeleri getiren endpoint.
    // order parametresi sıralamanın artan veya azalan olacağını belirler.
    public List<Country> getCountriesSortedByPhoneCode(@RequestParam(required = false, defaultValue = "asc") String order) {
        return countryService.orderCountriesByPhoneCode(order);
    }
    @GetMapping("/filter")
    // Belirli özelliklere göre filtrelenmiş ülkeleri getiren endpoint.
    // currency, phone ve continent parametreleri filtreleme kriterlerini belirler.
    public List<Country> getCountriesByProperties(@RequestParam(required = false) String currency,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(required = false) String continent) {
        return countryService.getCountriesByProperties(currency, phone, continent);
    }
    @GetMapping("/one-time-insert")
    public ResponseEntity<String>oneTimeInsert() throws IOException {
        List<Country> allCountries = countryService.getAllCountries();
        if (allCountries.isEmpty()) {
            countryService.insertAllCountries();
            return ResponseEntity.ok("Ülke verileri başarıyla eklendi.");
        }
        else {
            return ResponseEntity.ok("Veritabanı zaten doldurulmuş.");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String id) {
        boolean deleted = countryService.deleteCountry(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Başarılı yanıt, içerik yok
        } else {
            return ResponseEntity.notFound().build(); // Ülke bulunamadı
        }
    }

}
