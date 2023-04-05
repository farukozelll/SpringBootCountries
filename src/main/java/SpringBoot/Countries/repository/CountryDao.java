package SpringBoot.Countries.repository;

import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryDao{
    @Autowired
    private final DataSource dataSource;

    public CountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addCountry(Country country) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO countries (id, name, native_name, phone, continent, capital, currency, languages, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";
    public List<Country> getAllCountries() {
        return null;
    }

}
