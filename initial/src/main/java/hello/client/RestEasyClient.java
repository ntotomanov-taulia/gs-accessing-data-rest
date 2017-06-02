package hello.client;

import hello.tdo.Person;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class RestEasyClient {
  Client client;
  WebTarget webTarget;
  Builder clientBuilder;

  public RestEasyClient() {
    this.client = new ResteasyClientBuilder().connectionPoolSize(100).establishConnectionTimeout(30, TimeUnit.SECONDS).socketTimeout(30, TimeUnit.SECONDS).build();
  }

  public Builder get(String path, Map queryParams) {
    webTarget = client.target(path);

    //register ClientResponse and ClientRequest Filters
    BiConsumer<String, String> biConsumer = (String key, String value) -> {
      webTarget = webTarget.queryParam(key, value);
    };
    queryParams.forEach(biConsumer);

    clientBuilder = webTarget.request();
    setHeaders(new HashMap<String, String>());
    return clientBuilder;
  }

  public Builder setHeaders(Map<String, String> headers) {
    //Define basic authentication credential values
    String username = "Mary";
    String password = "password";

    String usernameAndPassword = username + ":" + password;
    String authorizationHeaderName = "Authorization";
    String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
    clientBuilder.header(authorizationHeaderName, authorizationHeaderValue);
    clientBuilder.header("Content-Type", MediaType.APPLICATION_JSON);

    for (String key : headers.keySet()) {
      clientBuilder.header(key, headers.get(key));
    }
    return clientBuilder;
  }

  public void close() {
    try {
      client.close();
    } catch (Exception e) {
      System.err.print("ERROR CLOSING");
    }

  }
}
