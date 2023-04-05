package SpringBoot.Countries.dataAccess;

public class Query {
    public final static String GET_ALL_COUNTRIES = "SELECT * from countries";
    public final static String GET_COUNTRY_BY_CODE = "SELECT * from countries WHERE id = ?";
    public final static String GET_COUNTRIES_BY_PROPERTIES = "SELECT * FROM countries WHERE 1=1";
    public final static String GET_COUNTRIES_SORTED_BY_PHONE = "SELECT * FROM countries ORDER BY phone ";
    public final static String DROP_TABLE = "DROP TABLE IF EXISTS countries";
    public final static String CREATE_TABLE = "CREATE TABLE countries(" +
            "id VARCHAR(2) PRIMARY KEY, name VARCHAR(255) NOT NULL, native_name VARCHAR(255) NOT NULL, phone INT, continent VARCHAR(3) NOT NULL, capital VARCHAR(255) NOT NULL, currency VARCHAR(255) NOT NULL, languages VARCHAR(255) NOT NULL, flag VARCHAR(255) NOT NULL)";
    public final static String INSERT_TABLE = "INSERT INTO countries (id, name, native_name, phone, continent, capital, currency, languages, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
}
