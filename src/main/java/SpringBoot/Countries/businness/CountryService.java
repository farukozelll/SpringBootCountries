package SpringBoot.Countries.businness;

import SpringBoot.Countries.entities.Country;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CountryService {

    Country insertCountry(Country country) throws IOException;
    public void insertAllCountries();
    public void addAll(List<Country> countries);
    public List<Country> getAllCountries();
    public Optional<Country> getCountry(String id);
    public Optional<Country> getCountryById(String id);
    public List<Country> getCountryByName(String name);
    public List<Country> getCountryByPhoneCode(String phone);
    public boolean deleteCountry(String id);
    List<Country> orderCountriesByPhoneCode(String order);
    List<Country> getCountriesByProperties(String currency, String phone, String continent);

}
