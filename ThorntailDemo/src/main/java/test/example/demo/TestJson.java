package test.example.demo;

import java.util.HashMap;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;



import com.miracle.labs.microservice.transform.generated.ResponseBuilder;
import com.monolith.legacy.ClassA;

public class TestJson {


	public void test() {
		System.out.println(ResponseBuilder.prepareResponse().setMessage("Test Message").toJson());
		ClassA<Integer> a = new ClassA<>();
		a.setValue(5);
		
		Jsonb jsonb = JsonbBuilder.create();
	    String jsonString = jsonb.toJson(a);
	    
	    System.out.println(jsonString);
	    
		System.out.println(ResponseBuilder.prepareResponse().setMessage(a).toJson());
		HashMap<String,Object> map = new HashMap<>();
		map.put("a",a);
		map.put("i", 10);
		map.put("value", "test");
		System.out.println(jsonb.toJson(map));
	}

}
