package com.asj.besyTest.besyRefereneces;

import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import com.asj.besyTest.model.entities.Emoji;
import com.besysoft.besyreferences.BesyReferences;
import com.besysoft.besyreferences.entities.PostgreSQLDatabase;
import com.besysoft.besyreferences.entities.WebServiceRest;
import com.besysoft.besyreferences.exception.BesyReferencesException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReferencesServices implements CommandLineRunner {
    private final RestTemplate httpClient = new RestTemplate();

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

            //ResponseEntity<?> chuckNorrisJokeResponse = httpClient.getForEntity(webServiceChuckNorrisUrl, String.class);

            //System.out.println("chucknorris responesEntity: "+chuckNorrisJokeResponse);
            //System.out.println("chucknorris responesEntity statusCode: "+chuckNorrisJokeResponse.getStatusCode());

            //chuckNorrisJoke = chuckNorrisJokeResponse.getBody();

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

            // Set the headers to the request entity
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<?> emojiRsponseEntity = httpClient.getForEntity(url, String.class, entity);
            //Emoji emoji = httpClient.getForObject(url, Emoji.class, entity);
            System.out.println("Emoji resp ent: "+emojiRsponseEntity);

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
