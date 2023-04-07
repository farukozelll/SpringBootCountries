package SpringBoot.Countries.webApi.controllers;

import SpringBoot.Countries.businness.CountryService;
import SpringBoot.Countries.businness.CountryServiceJPA;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/countries") 
public class CountryController {
    // CountryService sınıfını kullanmak için dependency injection yapılandırması
    @Autowired
    private CountryService countryService;
    @Autowired
    private CountryServiceJPA countryServicejpa;
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/api/one-time-insert")
    public ResponseEntity<String> oneTimeInsert() {
        try {
            List<Country> allCountries = countryService.getAllCountries();
            if (allCountries.isEmpty()) {
                countryService.insertCountry();
                return ResponseEntity.ok("Veriler başarıyla eklendi.");
            }
            else {
                return ResponseEntity.ok("Veritabanı zaten doldurulmuş.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı hatası.");
        }
    }

    @GetMapping("/all")
// Tüm ülkeleri getiren endpoint.
    public List<Country> getAllCountries() throws SQLException{
        return countryServicejpa.getAllCountries();
    }

    @GetMapping("/code/{kod}")
// Belirli bir ülke kodu ile ülke bilgisi getiren endpoint.
    public List<Country> getCountryByCode(@PathVariable String kod) throws SQLException {
        return countryService.getCountryByCode(kod);
    }

    @GetMapping("/sorted/{ascending}")
// Telefon kodlarına göre sıralanmış ülkeleri getiren endpoint.
// ascending parametresi sıralamanın artan veya azalan olacağını belirler.
    public List<Country> getCountriesSortedByPhoneCode(@RequestParam(required = true, defaultValue = "false") boolean ascending) throws SQLException {
        return countryService.orderCountriesByPhoneCode(ascending);
    }

    @GetMapping("/filter")
// Belirli özelliklere göre filtrelenmiş ülkeleri getiren endpoint.
// currency, phone ve continent parametreleri filtreleme kriterlerini belirler.
    public List<Country> getCountriesByProperties(@RequestParam(required = false) String currency,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(required = false) String continent) throws SQLException {
        return countryService.getCountriesByProperties(currency, phone, continent);
    }

}
