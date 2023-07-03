package com.asj.besyTest.besyRefereneces;

import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import com.asj.besyTest.model.entities.Emoji;
import com.asj.besyTest.model.entities.Quote;
import com.besysoft.besyreferences.BesyReferences;
import com.besysoft.besyreferences.entities.PostgreSQLDatabase;
import com.besysoft.besyreferences.entities.WebServiceRest;
import com.besysoft.besyreferences.exception.BesyReferencesException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class ReferencesServices implements CommandLineRunner {
    private final RestTemplate httpClient = new RestTemplate();
    private final Gson gson = new Gson();

    public ChuckNorrisJoke webServiceChuckNorrisCall() {
        System.out.println("Entro al webServiceCall");
        BesyReferences besyReferences = new BesyReferences();
        ChuckNorrisJoke chuckNorrisJoke = null;

        try {
            /*Besy reference*/
            WebServiceRest wsr = (WebServiceRest) besyReferences.getExternalResource("chuckNorrisWebService");
            String webServiceChuckNorrisUrl = wsr.getUrl();

            System.out.println("chuckNorris webServiceUrl: "+webServiceChuckNorrisUrl);

            chuckNorrisJoke = httpClient.getForObject(webServiceChuckNorrisUrl, ChuckNorrisJoke.class);


            System.out.println(chuckNorrisJoke);
            return chuckNorrisJoke;
        } catch (Exception e) {
            System.out.println("Se produjo un error consultando BesyReferences: " + e);
            throw new RuntimeException("Error procesando besy webService");
        }
    }

    public Emoji webServiceEmojiCall() {
        System.out.println("Entro al webServiceCall");
        BesyReferences besyReferences = new BesyReferences();

        try {
            WebServiceRest emojiServiceRest = (WebServiceRest) besyReferences.getExternalResource("emojiWebService");
            String url = emojiServiceRest.getUrl();
            System.out.println("emoji webServiceUrl: "+url);

            // Create HttpHeaders and set the desired headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", "WGoxlw+6K3UicE5XDWd2yg==zjxxYvpVM75yli8Q");
            headers.set("Accept", "*/*");
            System.out.println("headers: " + headers);

            // Set the headers to the request entity
            HttpEntity<Emoji> httpEntity = new HttpEntity<>(headers);
            System.out.println("http entity: " + httpEntity);

            ResponseEntity<Emoji> emojiRsponseEntity = httpClient.exchange(url, HttpMethod.GET, httpEntity, Emoji.class);
            System.out.println("Emoji resp ent: "+emojiRsponseEntity);
            
            if(emojiRsponseEntity.getStatusCode().is2xxSuccessful()) {
                //String jsonBody = emojiRsponseEntity.getBody();
                //System.out.println("Jso body: "+ jsonBody);
                //Emoji emoji = gson.fromJson(jsonBody, Emoji.class);
                Emoji emoji = emojiRsponseEntity.getBody();
                return emoji;
            }


            //System.out.println(emoji);
            return null;
        } catch (Exception e) {
            System.out.println("Se produjo un error consultando BesyReferences: " + e);
            throw new RuntimeException("Error procesando besy webService");
        }
    }

    public Quote quoteServiceEmojiCall() {
        System.out.println("Entro al webServiceCall");
        BesyReferences besyReferences = new BesyReferences();

        try {
            WebServiceRest quoteServiceRest = (WebServiceRest) besyReferences.getExternalResource("quoteWebService");
            String url = quoteServiceRest.getUrl();
            System.out.println("quote Url: "+url);

            // Create HttpHeaders and set the desired headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", "WGoxlw+6K3UicE5XDWd2yg==zjxxYvpVM75yli8Q");
            headers.set("accept", "application/json");
            System.out.println("headers: " + headers);

            // Set the headers to the request entity
            HttpEntity<Emoji> httpEntity = new HttpEntity<>(headers);
            System.out.println("http entity: " + httpEntity);

            ResponseEntity<String> quoteRsponseEntity = httpClient.exchange(url, HttpMethod.GET, httpEntity, String.class);
            System.out.println("Quote resp ent: " + quoteRsponseEntity);

            if(quoteRsponseEntity.getStatusCode().is2xxSuccessful()) {
                String jsonBody = quoteRsponseEntity.getBody();
                System.out.println("Json body: "+ jsonBody);
                //Type listType = new TypeToken<List<MyObject>>(){}.getType();
                //Quote quote = gson.fromJson(jsonBody, Quote.class);
                List<Quote> quoteCollection = (List) gson.fromJson(jsonBody, new TypeToken<Collection<Quote>>(){});
                Quote quote = quoteCollection.get(0);
                System.out.println("quote: " + quote);

                return quote;
            }


            //System.out.println(emoji);
            return null;
        } catch (Exception e) {
            System.out.println("Se produjo un error consultando BesyReferences: " + e);
            throw new RuntimeException("Error procesando besy webService");
        }
    }

    public DataSource dataSource() {
        BesyReferences besyReferences = new BesyReferences();
        DataSource dataSource = null;

        try {
            PostgreSQLDatabase postgreDB = (PostgreSQLDatabase) besyReferences.getExternalResource("besyDB");
            return (DataSource) postgreDB.getDataSource();

            /*PoolProperties poolProperties = new PoolProperties();
            poolProperties.setUrl(postgreDB.getUrl());
            poolProperties.setUsername(postgreDB.getUsername());
            poolProperties.setPassword(postgreDB.getPassword());
            poolProperties.setDriverClassName(postgreDB.getDriver());

            // Inicializa el driver
            Class.forName(postgreDB.getDriver());

            poolProperties.setTestOnBorrow(true);
            poolProperties.setValidationQuery("SELECT * FROM state");

            dataSource = new DataSource(poolProperties);*/

            //System.out.println("Connecting to database: "+ postgreDB.getUsername());

        }
        catch (BesyReferencesException e) {
            System.out.println("Se produjo un error consultando BesyReferences"+ e);
        }
        /*catch (ClassNotFoundException e) {
            System.out.println("Error en la inicialización de la base de datos"+ e);
        }*/
        catch (Exception e) {
            System.out.println("Error en la inicialización de la base de datos"+ e);
        }

        return dataSource;

    };


    @Override
    public void run(String... args) throws Exception {
        DataSource dataSource = dataSource();
        System.out.println("data source: " + dataSource);
    }

}
