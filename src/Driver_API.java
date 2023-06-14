import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.neo4j.driver.GraphDatabase.driver;

public class Driver_API {


    public static void main(String[] args) {
        String uri =  System.getenv("NEO4J_URI");
        String password = System.getenv("NEO4J_PWD");

//        List<String> list = new ArrayList<String>();
//        list.add("user1");
//        list.add("user2");
//        list.add("user3");
//        list.add("user4");
//        list.add("user5");

        List<String> list = new ArrayList<String>();
        list.add("moviesuser");
//        list.add("neo4j");
//        list.add("footballtransfersuser");
//        list.add("moviesuser");
//        list.add("footballtransfersuser");

        String[] user = list.toArray(new String[0]);


        DoStuffWithSessions(uri, user, password);

    }

    private static void DoStuffWithSessions(String URI, String[] userNames, String Password) {
        AuthToken[] myTokens = new AuthToken[5];
        for (int i = 0; i < 1; i++) {
            myTokens[i] = AuthTokens.basic(userNames[i], Password);
        }
        Driver driver = driver(URI);


            for (int k = 0; k < 1; k++) {
                for (int j = 0; j < 100; j++) {
                try (var session = driver.session(Session.class, myTokens[k]);) {

                    var greeting = session.executeRead(tx -> {

                        var query = new Query("MATCH (m:Movie) return m.title as greeting;");
                        var result = tx.run(query);
                        return result.list().toString();
                    });
                    System.out.println(greeting + " " + j + " " + k)   ;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}


