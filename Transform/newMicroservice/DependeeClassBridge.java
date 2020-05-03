
package com.monolith.legacy;
import com.miracle.labs.microservice.transform.generated.ResponseBuilder;
import javax.ws.rs.FormParam;
import com.miracle.labs.transform.generated.tools.JsonConvertor;
public class DependeeClassBridge{
DependeeClass target = new DependeeClass();
public String getA(){


	return ResponseBuilder.getMessageResponseJsonString( target.getA());
}
public String setA(String arg0 ){

com.monolith.legacy.ClassA<java.lang.String> a0 = JsonConvertor.getClassFromJsonString(arg0, com.monolith.legacy.ClassA.class);

	 target.setA(a0);
return ResponseBuilder.getDefaultResponseJsonString();
}
public String setInternals(String arg0 ,String arg1 ,String arg2 ,String arg3 ,String arg4 ){

int a0 = JsonConvertor.getClassFromJsonString(arg0, int.class);
java.lang.String a1 = JsonConvertor.getClassFromJsonString(arg1, java.lang.String.class);
float a2 = JsonConvertor.getClassFromJsonString(arg2, float.class);
char a3 = JsonConvertor.getClassFromJsonString(arg3, char.class);
java.lang.Double a4 = JsonConvertor.getClassFromJsonString(arg4, java.lang.Double.class);

	 target.setInternals(a0,a1,a2,a3,a4);
return ResponseBuilder.getDefaultResponseJsonString();
}
public String getInternals(String arg0 ){

int a0 = JsonConvertor.getClassFromJsonString(arg0, int.class);

	return ResponseBuilder.getMessageResponseJsonString( target.getInternals(a0));
}
public String getProtectedInt(){


	return ResponseBuilder.getMessageResponseJsonString( target.getProtectedInt());
}
public String getString(){


	return ResponseBuilder.getMessageResponseJsonString( target.getString());
}
public String getInt(){


	return ResponseBuilder.getMessageResponseJsonString( target.getInt());
}
public String setInt(String arg0 ){

int a0 = JsonConvertor.getClassFromJsonString(arg0, int.class);

	 target.setInt(a0);
return ResponseBuilder.getDefaultResponseJsonString();
}
}
