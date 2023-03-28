package SpringBoot.Countries.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
    private String id;//primary key pk
    private String name;
    @JsonProperty("native")
    private String nativeName;
    private String phone;
    private String continent;
    private String capital;
    private String currency;
    private List<?> languages;
    private String flag;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return this.nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<?> getLanguages() {
        return this.languages;
    }

    public void setLanguages(List<?> languages) {
        this.languages = languages;
    }


    public String getContinent() {
        return this.continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }


    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


}
