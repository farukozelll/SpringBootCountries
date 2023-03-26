package SpringBoot.Countries.webApi.controllers;

import SpringBoot.Countries.businness.abstracts.CountryServices;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries") // controller /countries kök dizini altında HTTP isteklerine yanıt verir
public class CountryController { //bu sınıf HTTP isteklerini işleyerek CountryService bileşeninin yöntemlerini çağırır ve sonuçları HTTP yanıtı olarak döndürür

    private final CountryServices countryServices;

    @Autowired
    public CountryController(CountryServices countryServices) {
        this.countryServices = countryServices;
    }

    @GetMapping("/getCountries") //HTTP GET isteklerine yanıt veren yöntem
    public Map<String, Country>  getAllCountries() {
        return countryServices.getAllCountries();
    }

    @GetMapping("/{code}") //belirli bir ülkenin koduna (code) göre veritabanından alır
    public Country getCountryByCode(@PathVariable String code) { //pathVariable  HTTP isteğinin yoluna erişim sağlar
        return countryServices.getCountryByCode(code);
    }

    @GetMapping("/sorted")//@RequestParam default değer atamak istenirse defaultValue özelliğini
    public List<Country> getCountriesSortedByPhoneCode(@RequestParam(required = true) boolean ascending) {//veritabanındaki ülkeleri telefon kodlarına göre sıralar ve isteğe bağlı olarak artan veya azalan sıralama yapar. RequestParam HTTP isteğinin sorgu parametrelerine erişim sağlar
        return countryServices.getCountriesSortedByPhone(ascending);
    }
    /*  @GetMapping("/countries/by-phone-code/{phoneCode}")
    public List<Country> getCountriesByPhoneCode(@PathVariable String phoneCode) {
        return countryServices.getCountriesByPhoneCodeOrderByPhoneCodeAsc(phoneCode);
    }*/
    @GetMapping("/filter") //@RequestParam parametresini isteğe bağlı yapmak istenirse required özelliğini false yapılır
    public List<Country> getCountriesByProperties(@RequestParam(required = false) String currency,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(required = false) String continent) {
        return countryServices.getCountriesByProperties(currency, phone, continent);
    }

}