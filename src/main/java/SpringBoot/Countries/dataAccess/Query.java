package SpringBoot.Countries.dataAccess;

public class Query {
    public final static String GET_ALL_COUNTRIES = "SELECT * from countries";
    public final static String GET_COUNTRY_BY_CODE = "SELECT * from countries WHERE id = ?";
    public final static String GET_COUNTRIES_BY_PROPERTIES = "SELECT * FROM countries WHERE 1=1";
    public final static String GET_COUNTRIES_SORTED_BY_PHONE = "SELECT * FROM countries ORDER BY phone ";
    public final static String INSERT_TABLE = "INSERT INTO countries (id, name, native_name, phone, continent, capital, currency, languages, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
}

/*      GET_ALL_COUNTRIES: Tüm ülkeleri almak için kullanılır.
        GET_COUNTRY_BY_CODE: Ülke kodu verildiğinde o ülkenin bilgilerini almak için kullanılır.
        GET_COUNTRIES_BY_PROPERTIES: Ülke özelliklerine göre filtreleme yapmak için kullanılır. Bu özellikler para birimi, telefon kodu ve kıta olabilir.
        GET_COUNTRIES_SORTED_BY_PHONE: Telefon koduna göre ülkeleri sıralamak için kullanılır.
        DROP_TABLE: countries tablosunu silmek için kullanılır.
        CREATE_TABLE: countries tablosunu oluşturmak için kullanılır.
        INSERT_TABLE: Yeni bir ülke ekleme için kullanılır.
 */
