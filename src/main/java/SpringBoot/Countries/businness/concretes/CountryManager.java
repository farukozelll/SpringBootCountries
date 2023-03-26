package SpringBoot.Countries.businness.concretes;

import SpringBoot.Countries.businness.abstracts.CountryServices;
import SpringBoot.Countries.entities.Country;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

@Service
public class CountryManager implements CountryServices {

    private final static String COUNTRY_JSON = "C:\\Users\\faruk\\Downloads\\Countries\\src\\main\\resources\\countries.json";

    public Map<String, Country> countries;

    public CountryManager() {
        loadJson();
    }
    public void loadJson() { // JSON dosyasını okur
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Map<String, Map<String, Object>> countryMaps = objectMapper.readValue(new File(COUNTRY_JSON), new TypeReference<Map<String, Map<String, Object>>>() {});
            countries = new HashMap<>();
            for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                String countryCode = entry.getKey();// anahtar String kısmı countrycode'a atandı
                Map<String, Object> countryMap = entry.getValue(); // her bir ülke için bir Country nesnesi oluşturulur ve bu nesneler bir Map<String, Country> nesnesinde saklanır
                Country country = objectMapper.convertValue(countryMap, Country.class);//Map nesnesindeki özellikleri, Country sınıfındaki alanlarla eşleştirerek yeni bir Country nesnesi oluşturur
                country.setId(countryCode); //country nesnesinin id alanı countryCode'a ayarlanır
                country.setName((String) countryMap.get("name"));
                country.setNativeName((String) countryMap.get("native")); //country nesnesinin nativeName  alanı native'e ayarlanır
                country.setPhone((String) countryMap.get("phone"));
                country.setContinent((String) countryMap.get("continent"));
                country.setCapital((String) countryMap.get("capital"));
                country.setCurrency((String) countryMap.get("currency"));
                country.setLanguages((List<?>) countryMap.get("languages"));
                country.setFlag("http://aedemirsen.bilgimeclisi.com/country_flags/" + countryCode + ".svg"); // flag özelliğini ayarla
                countries.put(countryCode, country);// her bir ülke için oluşturulan country nesnesini countries mapine countryCode key'i ile ekler
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Map<String, Country> getAllCountries() {
        return null;
    }

    @Override
    public Country getCountryByCode(String code) {
        return countries.get(code);
    }

/*    @Override
    public List<Country> getCountriesByPhoneCodeOrderByPhoneCodeAsc(String phoneCode) {
        return countries.stream()
                .filter(country -> country.getPhone().startsWith(phoneCode))
                .sorted(Comparator.comparing(Country::getPhone))
                .collect(Collectors.toList());
    }*/
    @Override
    public List<Country> getCountriesSortedByPhone(boolean ascending) {//Ülke telefon kodlarına göre azalan veya artan sırada tüm ülkeler
        Comparator<Country> comparator = Comparator.comparing(Country::getPhone);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return countries.values().stream().sorted(comparator).collect(Collectors.toList());
    }
    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) {//Ülkenin currency, phoneCode ve continent özelliklerine göre filtrelenmiş ülkeler
        return countries.values().stream()
                .filter(c -> currency == null || c.getCurrency().equals(currency))  //currency'e göre filtrelenmiş ülkeler
                .filter(c -> phone == null || c.getPhone().equals(phone))  // phone'a göre filtrelenmiş ülkeler
                .filter(c -> continent == null || c.getContinent().equals(continent))  //continent'e göre filtrelenmiş ülkeler
                .collect(Collectors.toList());
    }
}
