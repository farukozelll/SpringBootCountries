package SpringBoot.Countries.businness;

import SpringBoot.Countries.entities.Country;

import java.sql.SQLException;
import java.util.List;

public interface CountryService {
    void loadJsonToDb() throws SQLException;//JSON dosyasındaki ülke bilgilerini veritabanına yüklemektir.

    List<Country> getAllCountries() throws SQLException;// tüm ülkeleri bir liste halinde döndürmektir.

    List<Country> getCountryByCode(String kod) throws SQLException;//verilen kod değerine sahip olan ülkeyi döndürmektir.

    List<Country> getCountriesSortedByPhone(boolean ascending) throws SQLException;//veritabanındaki tüm ülkeleri telefon kodlarına göre sıralamak ve belirtilen sıralama yöntemine (artan veya azalan) göre bir liste halinde döndürmektir.

    List<Country> getCountriesByProperties(String currency, String phone, String continent) throws SQLException;//veritabanındaki tüm ülkeler arasından belirli para birimi, telefon kodu ve kıta özelliklerine göre filtrelenmiş bir liste döndürmektir.
}
