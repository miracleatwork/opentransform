/**
 * This is a sample Dependee Class which has methods consumed by various Dependent classes.
 * We have included different types like Generic and Complex Object types as parameters to cover broad scenarios.
 * We have included methods with various modifiers to demonstrate the capabilities and limitations of Transform.
 * 
 */
package com.monolith.legacy;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import javax.json.bind.JsonbException;
import java.util.Collections;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import com.miracle.labs.transform.generated.tools.RESTClient;
;

public class DependeeClass {

	private int  prInt =1;
	public int puInt = 2;
	protected int proInt = 3;
	private int intI=0;
	private String b="";
	private float f=0f;
	private char c='c';
	private Double bd=0d;
	

	private ClassA<String> a = new ClassA<String>();
		public ClassA<String> getA()
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map<String,Object> map = Collections.EMPTY_MAP;
 return new RESTClient().executeRemote("DependeeClass","getA",ClassA.class,"",map);
}
		public void setA(ClassA<String> a)
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map <String,Object> map = new HashMap<>();
 map.put("0",a);
 new RESTClient().executeRemote("DependeeClass","setA",void.class,a,map);
}
		
		public int getInt()
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map<String,Object> map = Collections.EMPTY_MAP;
 return new RESTClient().executeRemote("DependeeClass","getInt",int.class,"",map);
}
		
		public void setInt(int i)
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map <String,Object> map = new HashMap<>();
 map.put("0",i);
 new RESTClient().executeRemote("DependeeClass","setInt",void.class,i,map);
}
		
		public void setInternals(int i, String b, float f, char c, Double bd )
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map <String,Object> map = new HashMap<>();
 map.put("0",i);
 map.put("1",b);
 map.put("2",f);
 map.put("3",c);
 map.put("4",bd);
 new RESTClient().executeRemote("DependeeClass","setInternals",void.class,i+b+f+c+bd,map);
}
		
		public String getInternals(int i)
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map<String,Object> map = Collections.EMPTY_MAP;
 return new RESTClient().executeRemote("DependeeClass","getInternals",String.class,i,map);
}
		
		public String getString()
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map<String,Object> map = Collections.EMPTY_MAP;
 return new RESTClient().executeRemote("DependeeClass","getString",String.class,"",map);
}
		
		private int getPrivateInt()
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map<String,Object> map = Collections.EMPTY_MAP;
 return new RESTClient().executeRemote("DependeeClass","getPrivateInt",int.class,"",map);
}
		
		protected int getProtectedInt()
		throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException{
Map<String,Object> map = Collections.EMPTY_MAP;
 return new RESTClient().executeRemote("DependeeClass","getProtectedInt",int.class,"",map);
}
		
	
	

}
