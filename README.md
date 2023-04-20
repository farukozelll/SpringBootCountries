# Spring Boot RestApi ve Docker

Bu uygulama, dünya ülkelerinin bilgilerini içeren bir veri kaynağından veri toplar ve kullanıcılara bu verileri sunar. Bu uygulama, Java dilinde yazılmıştır ve Spring Framework kullanılarak geliştirilmiştir.

## Özellikler
- Ülkelerinin adı, nüfusu, başkenti, para birimi, telefon kodu ve dilleri gibi bilgileri görüntüleme
- Veri tabanına dünya ülkelerinin bilgilerini yükleme
- Para birimi, telefon kodu ve kıta gibi özelliklere göre ülkeleri filtreleme
- Telefon koduna göre ülkeleri artan ya da azalan sırada sıralama

## Gereksinimler
- Java 8 veya daha yüksek
- Spring Framework 5 veya daha yüksek
- MySQL veya başka bir veri tabanı yönetim sistemi

## Kullanılan Teknolojiler
- Java Spring Boot
- JPA
- ORM
- Hibernate
- Hibernate Criteria API

## Kurulum
1. Depoyu yerel bilgisayarınıza kopyalayın.
2. MySQL veri tabanı oluşturun ve bağlantı bilgilerini `application.properties` dosyasına ekleyin.
3. Proje dizininde, `mvn clean install` komutunu çalıştırın.
4. Uygulamayı başlatmak için, `java -jar target/country-application.jar` komutunu kullanın.

## Nasıl Kurulur ve Çalıştırılır?
1. Projeyi indirin veya kopyalayın.
2. Mysql veritabanınızı lokal ortama kurun ve ayağa kaldırın.
3. DBeaver gibi bir araç kullanarak, `Country` tablosunu oluşturun ve tüm ülkeleri `insert` edin. Bu işlemi ana programdan bağımsız olarak gerçekleştirebilirsiniz.
4. Proje klasörü içerisinde bulunan `application.properties` dosyasını açın ve veritabanı bilgilerinizi güncelleyin.
5. Projeyi bir Java IDE'si ile açın ve uygulamayı çalıştırın.
6. Postman gibi bir araç kullanarak, API'nin tüm özelliklerini test edin.

## Daha Fazla Bilgi
Daha fazla bilgi için lütfen projenin kodunu ve dokümantasyonunu inceleyin. Herhangi bir sorunuz veya öneriniz varsa, lütfen bize ulaşın.

### Katkıda Bulunma
Bu proje açık kaynaklı bir projedir ve her türlü katkıya açıktır. Lütfen bir pull request göndermeden önce değişikliklerinizi tartışmak için bir konu açın.
