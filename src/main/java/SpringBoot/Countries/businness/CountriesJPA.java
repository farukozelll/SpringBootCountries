package SpringBoot.Countries.businness;

import SpringBoot.Countries.entities.Country;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CountriesJPA {
    Country insertCountry(Country country) throws SQLException;
    List<Country> getAllCountries() throws SQLException;// tüm ülkeleri bir liste halinde döndürmektir.
    Optional<Country> getCountry(String id);
    List<Country> getCountryByName(String kod) throws SQLException;//verilen kod değerine sahip olan ülkeyi döndürmektir.
    Optional<Country> getCountryById(String kod) throws SQLException;//verilen kod değerine sahip olan ülkeyi döndürmektir.
    List<Country> getCountryByPhoneCode(String kod) throws SQLException;//verilen kod değerine sahip olan ülkeyi döndürmektir.
    List<Country> orderCountriesByPhoneCode(boolean ascending) throws SQLException;//veritabanındaki tüm ülkeleri telefon kodlarına göre sıralamak ve belirtilen sıralama yöntemine (artan veya azalan) göre bir liste halinde döndürmektir.
    List<Country> getCountriesByProperties(String currency, String phone, String continent) throws SQLException;
}
