package SpringBoot.Countries.webApi.controllers;

import SpringBoot.Countries.businness.CountryService;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/countries") 
public class CountryController {
    private final CountryService countryService;
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/getCountries") //HTTP GET isteklerine yanıt veren yöntem
    public List<Country>  getAllCountries() throws SQLException {
        return countryService.getAllCountries();
    }


}
