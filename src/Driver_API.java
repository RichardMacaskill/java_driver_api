import org.neo4j.driver.*;

import static org.neo4j.driver.AuthTokenManagers.expirationBased;

import static org.neo4j.driver.GraphDatabase.driver;

public class Driver_API {


    public static void main(String[] args) {

        String uri = System.getenv("NEO4J_URI");
        String password = System.getenv("NEO4J_PWD");

        String user = "moviesuser";
        //var manager = AuthTokenManagers.expirationBased(() -> {

            //return token.expiringAt(timestamp); // a new method on AuthToken introduced for the supplied expiration based AuthTokenManager implementation
  //          return new AuthToken("").expiringAt(timestamp);
//        });
        try (Driver driver = driver(uri, AuthTokens.basic(user, password))) {

            var results = driver.executableQuery("MATCH (m:Movie) return m;")
                    .withConfig(QueryConfig.builder()
                            .withRouting(RoutingControl.READ).build()).
                    execute();

            System.out.println(new StringBuilder().append((long) results.records().size()).append(" records returned").toString());


        }
    }
}