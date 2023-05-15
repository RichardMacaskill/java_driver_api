import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.neo4j.driver.GraphDatabase.driver;

public class Driver_API {


    public static void main(String[] args) {
        String uri = System.getenv("NEO4J_URI");
        String password = System.getenv("NEO4J_PWD");

        List<String> list = new ArrayList<String>();
        list.add("moviesuser");
        list.add("neo4j");
        list.add("footballtransfersuser");
        list.add("moviesuser");
        
        String[] user = list.toArray(new String[0]);

        DoStuff(uri,user,password);

    }
    private static void DoStuff(String URI,String[] userNames, String Password)
    {

        var manager = AuthTokenManagers.expirationBased(() -> {
            Date now = new Date(new Date().getTime());
            Date expDate = new Date(new Date().getTime() +100); //java.util.Date

            Random rand = new Random();
            int randomNum = rand.nextInt(4);
           // System.out.println(now.toString());
            System.out.println(expDate.toString());

            var token = AuthTokens.basic(userNames[randomNum], Password);
            return token.expiringAt(expDate.getTime()); // a new method on AuthToken introduced for the supplied expiration based AuthTokenManager implementation
        });


        try (Driver driver = driver(URI, manager)) {

            for(int i=0; i < 1000 ; i++) {
                var results = driver.executableQuery("MATCH (m:Movie) return m;")
                        .withConfig(QueryConfig.builder().withRouting(RoutingControl.READ).build())
                        .execute();

                System.out.println(new StringBuilder().append((long) results.records().size()).append(" records returned").toString());

                try {
                    Thread.sleep(101);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }

        }
    }



}