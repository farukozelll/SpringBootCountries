package SpringBoot.Countries.businness;

import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.CountryRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CountryServiceIml implements CountryService {
    @Autowired
    private CountryRepository countryRepository;
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";
    List<Country> countries = new ArrayList<>();
    @PersistenceContext
    private EntityManager entityManager; // JPA ile çalışan uygulamalarda veritabanı işlemleri
   public CountryServiceIml(EntityManager entityManager) {
       this.entityManager = entityManager;
   }

     /*** Verilen id değerine göre bir ülke döndürür.
     *
     * @param id ülke id'si
     * @return id değerine göre bulunan ülke*/
     @Override
     public Optional<Country> getCountry(String id) {
         return countryRepository.findById(id);
     }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    @Override
    public List<Country> getCountryByName(String name) {
        return countryRepository.findByNameContaining(name);
    }

    @Override
    public Optional<Country> getCountryById(String id) {
        return countryRepository.findById(id);
    }

    @Override
    public List<Country> getCountryByPhoneCode(String phoneCode) {
        return countryRepository.findByPhone(phoneCode);
    }

    @Override
    public List<Country> orderCountriesByPhoneCode(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return countryRepository.findAllByOrderByPhoneAsc();
        } else if ("desc".equalsIgnoreCase(order)) {
            return countryRepository.findAllByOrderByPhoneDesc();
        } else {
            throw new IllegalArgumentException("ascending parametresi 'asc' veya 'desc' olmalidir.");
        }
    }

    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) {
        return countryRepository.findByCurrencyAndPhoneAndContinent(currency, phone, continent);
    }


    //-------------------------------------------------------------------------------------------------
    @Override
    public Country insertCountry(Country country) {
        return entityManager.merge(country);
    }

    @Override
    public void popCountries() {
        if (countryRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, Map<String, Object>> countryMaps = objectMapper.readValue(new File(COUNTRY_JSON), new TypeReference<Map<String, Map<String, Object>>>() {});
                for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                    String countryCode = entry.getKey();
                    Map<String, Object> countryMap = entry.getValue();
                    Country country = objectMapper.convertValue(countryMap, Country.class);
                    country.setId(countryCode);
                    country.setName((String) countryMap.get("name"));
                    country.setNativeName((String) countryMap.get("native"));
                    country.setPhone((String) countryMap.get("phone"));
                    country.setContinent((String) countryMap.get("continent"));
                    country.setCapital((String) countryMap.get("capital"));
                    country.setCurrency((String) countryMap.get("currency"));
                    country.setLanguages(Collections.singletonList(countryMap.get("languages")).toString());
                    country.setFlag("http://aedemirsen.bilgimeclisi.com/country_flags/" + countryCode + ".svg");
                    insertCountry(country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addAll(List<Country> countries) {
        for (Country country : countries) {
            entityManager.merge(country);
        }
        entityManager.flush();
    }

}
