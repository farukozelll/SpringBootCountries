package SpringBoot.Countries.businness.abstracts;

import SpringBoot.Countries.entities.Country;

import java.util.List;
import java.util.Map;

public interface CountryServices {

    Map<String, Country> getAllCountries();
    Country getCountryByCode(String code);
     List<Country> getCountriesSortedByPhone(boolean ascending);
  //  List<Country> getCountriesByPhoneCodeOrderByPhoneCodeAsc(String phoneCode);
    List<Country> getCountriesByProperties(String currency, String phoneCode, String continent);
}
