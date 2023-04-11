package SpringBoot.Countries.webApi.controllers;

import SpringBoot.Countries.businness.CountryService;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/phone-code/{phoneCode}")
    public List<Country> getCountriesByPhoneCode(@PathVariable String phoneCode) {
        return countryService.getCountryByPhoneCode(phoneCode);
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
    @GetMapping("/api/one-time-insert")
    public ResponseEntity<String>oneTimeInsert(Country country) throws IOException {
        List<Country> allCountries = countryService.getAllCountries();
        if (allCountries.isEmpty()) {
            countryService.insertCountry(country);
            return ResponseEntity.ok("Veriler başarıyla eklendi.");
        }
        else {
            return ResponseEntity.ok("Veritabanı zaten doldurulmuş.");
        }
    }

}
