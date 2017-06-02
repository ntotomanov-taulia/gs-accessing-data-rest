package hello.tests;


import com.fasterxml.jackson.core.io.JsonStringEncoder;
import hello.client.RestEasyClient;
import hello.tdo.Person;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ClientTests {
  RestEasyClient restEasyClient;
  Map<String, String> headers;
  Invocation.Builder clientBuilder;
  String path = "http://localhost:8080/people";


  @Before
  public void setup() {
    restEasyClient = new RestEasyClient();
    headers = new HashMap<>();
    clientBuilder = restEasyClient.get(path, headers);
  }

  @Test
  public void post() {
    Random random = new Random(100101);
    String firstName = "test1" ;
    long lastName = random.nextLong();
    Person person = new Person();
    person.setFirstName(firstName + "");
    person.setLastName(lastName + "");

    clientBuilder.post(Entity.json(person));

    headers = new HashMap<>();
    headers.put("name", firstName );
    clientBuilder = restEasyClient.get(path+"/search/findByLastName", headers);
    String s = clientBuilder.get(String.class);

    System.out.println("s=" + s);
  }

  @Test
  public void get() {
    String s = clientBuilder.get(String.class);
    System.out.print("s");
  }

  @Test
  public void getAll() {
    String s = clientBuilder.get(String.class);
    System.out.print("s");
  }


}
