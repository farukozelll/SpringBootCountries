package SpringBoot.Countries.businness;

import SpringBoot.Countries.dataAccess.Query;
import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.CountryDao;
import SpringBoot.Countries.repository.CountryDaoImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryServiceIml implements CountryService  {

    @Autowired
    private CountryDao countryDao;
    List<Country> countries = new ArrayList<>();
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";
    @Override
    public void loadJsonToDb() throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countries_db", "root", "12345");
        try {
            // JSON verilerini bir Map nesnesine okuyun.
            Map<String, Map<String, Object>> countryMaps = objectMapper.readValue(new File(COUNTRY_JSON), new TypeReference<Map<String, Map<String, Object>>>() {});

            // Veritabanına veri eklemek için SQL deyimini hazırlayın.
            PreparedStatement statement = connection.prepareStatement(Query.INSERT_TABLE);

            // Map nesnesindeki her giriş üzerinde döngü yapın ve verileri veritabanına ekleyin.
            for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                String countryCode = entry.getKey();
                Map<String, Object> countryMap = entry.getValue();

                // Map nesnesini country nesnesine dönüştürün.
                Country country = objectMapper.convertValue(countryMap, Country.class);
                country.setId(countryCode);
                country.setName((String) countryMap.get("name"));
                country.setNativeName((String) countryMap.get("native"));
                country.setPhone((String) countryMap.get("phone"));
                country.setContinent((String) countryMap.get("continent"));
                country.setCapital((String) countryMap.get("capital"));
                country.setCurrency((String) countryMap.get("currency"));
                country.setFlag("http://aedemirsen.bilgimeclisi.com/country_flags/" + countryCode + ".svg");

                // Map nesnesinin "languages" alanını bir String Listesine dönüştürün ve onu Country nesnesinin languages alanı olarak ayarlayın.
                TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {};
                List<String> languages = objectMapper.readValue(objectMapper.writeValueAsString(countryMap.get("languages")), typeRef);
                country.setLanguages(languages);

                // SQL ifadesinin parametrelerini ayarlayın ve çalıştırın.
                statement.setString(1, country.getId());
                statement.setString(2, country.getName());
                statement.setString(3, country.getNativeName());
                statement.setString(4, country.getPhone());
                statement.setString(5, country.getContinent());
                statement.setString(6, country.getCapital());
                statement.setString(7, country.getCurrency());
                statement.setString(8, String.join(",", country.getLanguages()));
                statement.setString(9, country.getFlag());
                statement.executeUpdate();
            }

            // statement ve bağlantıyı kapatın.
            statement.close();
            connection.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Country> getAllCountries() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countries_db", "root", "12345");
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(Query.GET_ALL_COUNTRIES)) {
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getString("id"));
                country.setName(rs.getString("name"));
                country.setNativeName(rs.getString("native_name"));
                country.setPhone(rs.getString("phone"));
                country.setContinent(rs.getString("continent"));
                country.setCapital(rs.getString("capital"));
                country.setCurrency(rs.getString("currency"));
                String languages = rs.getString("languages");
                country.setLanguages(Arrays.asList(languages.split(",")));
                country.setFlag(rs.getString("flag"));
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }

    @Override
    public List<Country> getCountryByCode(String kod) throws SQLException {

        // Veritabanı bağlantısı oluşturuluyor
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countries_db", "root", "12345");

        try (
                // Veritabanı sorgusu hazırlanıyor
                PreparedStatement stmt = connection.prepareStatement(Query.GET_COUNTRY_BY_CODE)) {

            // Sorgudaki parametre atanıyor
            stmt.setString(1, kod);

            // Sorgu çalıştırılıyor ve sonuçları ResultSet nesnesinde saklanıyor
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Sonuçlardan bir Country nesnesi oluşturuluyor ve veritabanından gelen veriler atanıyor
                Country country = new Country();
                country.setId(rs.getString("id"));
                country.setName(rs.getString("name"));
                country.setNativeName(rs.getString("native_name"));
                country.setPhone(rs.getString("phone"));
                country.setContinent(rs.getString("continent"));
                country.setCapital(rs.getString("capital"));
                country.setCurrency(rs.getString("currency"));

                // Dil bilgisi virgülle ayrılmış bir string olarak saklandığı için, split() yöntemi kullanılarak ayrıştırılıyor ve List şeklinde atanıyor
                String languages = rs.getString("languages");
                country.setLanguages(Arrays.asList(languages.split(",")));

                country.setFlag(rs.getString("flag"));
                // Oluşturulan Country nesnesi, countries listesine ekleniyor
                countries.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // countries listesi, çağırıldığı yere geri döndürülüyor
        return countries;
    }
    @Override
    public List<Country> getCountriesSortedByPhone(boolean ascending) throws SQLException {
        // Telefon numarasına göre sıralanmış ülkeleri getiren SQL sorgusunu oluştur
        String sql = Query.GET_COUNTRIES_SORTED_BY_PHONE + (ascending ? "ASC" : "DESC");
        // Veritabanı bağlantısı oluştur
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countries_db", "root", "12345");

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            // Her bir sorgu sonucu için bir ülke nesnesi oluştur ve listeye ekle
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getString("id"));
                country.setName(rs.getString("name"));
                country.setNativeName(rs.getString("native_name"));
                country.setPhone(rs.getString("phone"));
                country.setContinent(rs.getString("continent"));
                country.setCapital(rs.getString("capital"));
                country.setCurrency(rs.getString("currency"));
                String languages = rs.getString("languages");
                country.setLanguages(Arrays.asList(languages.split(",")));
                country.setFlag(rs.getString("flag"));
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return countries;
    }

    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) throws SQLException {
        // Verilen para birimi, telefon kodu ve kıta bilgilerine göre ülkeleri getiren SQL sorgusunu oluştur
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countries_db", "root", "12345");
        String query = Query.GET_COUNTRIES_BY_PROPERTIES;
        if (currency != null) {
            query += " AND currency = ?";
        }
        if (phone != null) {
            query += " AND phone = ?";
        }
        if (continent != null) {
            query += " AND continent = ?";
        }
        try (
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            int i = 1;
            if (currency != null) {
                pstmt.setString(i++, currency);
            }
            if (phone != null) {
                pstmt.setString(i++, phone);
            }
            if (continent != null) {
                pstmt.setString(i++, continent);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                // Her bir sorgu sonucu için bir ülke nesnesi oluştur ve listeye ekle
                while (rs.next()) {
                    Country country = new Country();
                    country.setId(rs.getString("id"));
                    country.setName(rs.getString("name"));
                    country.setNativeName(rs.getString("native_name"));
                    country.setPhone(rs.getString("phone"));
                    country.setContinent(rs.getString("continent"));
                    country.setCapital(rs.getString("capital"));
                    country.setCurrency(rs.getString("currency"));
                    String languages = rs.getString("languages");
                    country.setLanguages(Arrays.asList(languages.split(",")));
                    country.setFlag(rs.getString("flag"));
                    countries.add(country);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }


}
