package gatlingTest;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

public class LoadTestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            // Here is the root for all relative URLs
            .baseUrl("http://localhost:8080")
            // Here are the common headers
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    ScenarioBuilder scn = scenario("Pos_customer")
            .exec(http("list_products").get("/products"))
            .pause(1)
            .exec(http("get_product1").get("/products/13284888"))
            .pause(Duration.ofMillis(150))
            .exec(http("get_product2").get("/products/13122155"))
            .pause(Duration.ofMillis(150))
            .exec(http("get_cart1").get("/cart"))
            .pause(Duration.ofMillis(150))
            .exec(http("add_cart_item").post("/cart/add/13122155"))
            .pause(Duration.ofMillis(150))
            .exec(http("get_cart2").get("/cart"))
            .pause(Duration.ofMillis(150))
            .exec(http("create_order").post("/order"))
            .pause(Duration.ofMillis(150))
            .exec(http("get_delivery").get("/delivery"))
            .pause(Duration.ofMillis(150));

    {
        setUp(scn.injectOpen(atOnceUsers(500)).protocols(httpProtocol));
    }
}
