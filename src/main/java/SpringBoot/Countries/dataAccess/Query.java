package SpringBoot.Countries.dataAccess;

import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class Query {
    private SessionFactory sessionFactory;
    public final static String GET_ALL_COUNTRIES = "SELECT * from countries";
    public final static String GET_COUNTRY_BY_CODE = "SELECT * from countries WHERE id = ?";
    public final static String GET_COUNTRY_BY_NAME = "SELECT * from countries WHERE name = ?";
    public final static String GET_COUNTRIES_BY_PROPERTIES = "SELECT * FROM countries WHERE 1=1";
    public final static String GET_COUNTRIES_SORTED_BY_PHONE = "SELECT * FROM countries ORDER BY phone ";

    public final static String setParameter = "SELECT c FROM countries c WHERE c.id = :kod AND c.name = :kod1 ";
    public final static String INSERT_TABLE = "INSERT INTO countries (id, name, native_name, phone, continent, capital, currency, languages, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public List<Country> getResultList() {
        // session factory'nin oluşturulması
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // session açılması
        Session session = sessionFactory.openSession();

        // sorgunun oluşturulması
        Query query = (Query) session.createQuery(GET_ALL_COUNTRIES, Country.class);

        // sorgunun çalıştırılması ve sonuçların listelenmesi
        List<Country> countries = query.getResultList();

        // session'ın kapatılması
        session.close();

        // sonuçların döndürülmesi
        return countries;
    }

    public void setParameter(String kod, String kod1) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query query = (Query) session.createQuery(setParameter);
            query.setParameter("kod", kod);
            query.setParameter("kod1", kod1);

            List<Country> result = query.getResultList();

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

/*      GET_ALL_COUNTRIES: Tüm ülkeleri almak için kullanılır.
        GET_COUNTRY_BY_CODE: Ülke kodu verildiğinde o ülkenin bilgilerini almak için kullanılır.
        GET_COUNTRIES_BY_PROPERTIES: Ülke özelliklerine göre filtreleme yapmak için kullanılır. Bu özellikler para birimi, telefon kodu ve kıta olabilir.
        GET_COUNTRIES_SORTED_BY_PHONE: Telefon koduna göre ülkeleri sıralamak için kullanılır.
        DROP_TABLE: countries tablosunu silmek için kullanılır.
        CREATE_TABLE: countries tablosunu oluşturmak için kullanılır.
        INSERT_TABLE: Yeni bir ülke ekleme için kullanılır.
 */
