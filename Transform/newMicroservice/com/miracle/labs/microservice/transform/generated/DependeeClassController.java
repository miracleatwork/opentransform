
package com.miracle.labs.microservice.transform.generated;
import javax.ws.rs.*; import javax.ws.rs.core.*;import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
@Path("/DependeeClass")
public class DependeeClassController{
com.monolith.legacy.DependeeClassBridge target = new com.monolith.legacy.DependeeClassBridge();
@GET
@Path("/A")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String getA(){
	return target.getA();
}
@POST
@Path("/A")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String setA(@FormParam("0") String arg0 ){
	return target.setA(arg0);
}
@POST
@Path("/Internals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String setInternals(@FormParam("0") String arg0 ,@FormParam("1") String arg1 ,@FormParam("2") String arg2 ,@FormParam("3") String arg3 ,@FormParam("4") String arg4 ){
	return target.setInternals(arg0,arg1,arg2,arg3,arg4);
}
@GET
@Path("/Internals/{arg0}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String getInternals(@PathParam("arg0") String arg0 ){
	return target.getInternals(arg0);
}
@GET
@Path("/ProtectedInt")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String getProtectedInt(){
	return target.getProtectedInt();
}
@GET
@Path("/String")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String getString(){
	return target.getString();
}
@GET
@Path("/Int")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String getInt(){
	return target.getInt();
}
@POST
@Path("/Int")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
public String setInt(@FormParam("0") String arg0 ){
	return target.setInt(arg0);
}
}
