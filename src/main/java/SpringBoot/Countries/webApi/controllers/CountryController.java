package SpringBoot.Countries.webApi.controllers;

import SpringBoot.Countries.businness.abstracts.CountryServices;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries") 
public class CountryController { 

    private final CountryServices countryServices;

    @Autowired
    public CountryController(CountryServices countryServices) {
        this.countryServices = countryServices;
    }

    @GetMapping("/getCountries") 
    public Map<String, Country>  getAllCountries() {
        return countryServices.getAllCountries();
    }

    @GetMapping("/{code}") 
    public Country getCountryByCode(@PathVariable String code) { 
        return countryServices.getCountryByCode(code);
    }

    @GetMapping("/sorted")//@RequestParam default değer atamak istenirse defaultValue özelliğini
    public List<Country> getCountriesSortedByPhoneCode(@RequestParam(required = true) boolean ascending) {
        return countryServices.getCountriesSortedByPhone(ascending);
    }
    /*  @GetMapping("/countries/by-phone-code/{phoneCode}")
    public List<Country> getCountriesByPhoneCode(@PathVariable String phoneCode) {
        return countryServices.getCountriesByPhoneCodeOrderByPhoneCodeAsc(phoneCode);
    }*/
    @GetMapping("/filter")
    public List<Country> getCountriesByProperties(@RequestParam(required = false) String currency,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(required = false) String continent) {
        return countryServices.getCountriesByProperties(currency, phone, continent);
    }

}
