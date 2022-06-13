package velykyi.vladyslav.SOAPClientDemo.repository;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        countries.clear();
        Country ua = new Country();
        Country gb = new Country();
        Country fr = new Country();

        ua.setName("Ukraine");
        ua.setCapital("Kyiv");
        ua.setPopulation(44);
        ua.setCurrency(Currency.UAN);
        gb.setName("Great Britain");
        gb.setCapital("London");
        gb.setPopulation(100);
        gb.setCurrency(Currency.GBP);
        fr.setName("France");
        fr.setCapital("Paris");
        fr.setPopulation(60);
        fr.setCurrency(Currency.EUR);

        countries.put(ua.getName(), ua);
        countries.put(gb.getName(), gb);
        countries.put(fr.getName(), fr);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");

        return countries.get(name);
    }

    public Country addCountry(String name, String capital, int population, Currency currency) {
        Assert.notNull(name, "The country's name must not be null");
        Assert.notNull(capital, "The country's capital must not be null");
        Assert.notNull(currency, "The country's currency must not be null");

        Country country = new Country();
        country.setName(name);
        country.setCapital(capital);
        country.setPopulation(population);
        country.setCurrency(currency);

        countries.put(country.getName(), country);
        return country;
    }

    public Country removeCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");

        return countries.remove(name);
    }
}
