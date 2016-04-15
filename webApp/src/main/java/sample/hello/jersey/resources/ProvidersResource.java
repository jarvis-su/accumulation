package sample.hello.jersey.resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sample.hello.jersey.bean.Provider;
import sample.hello.jersey.storage.ProviderStore;

@Path("/providers")
public class ProvidersResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Provider> getProviders() {
		List<Provider> providers = new ArrayList<>();
		providers.addAll( ProviderStore.getStore().values() );
		return providers;
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = ProviderStore.getStore().size();
		return String.valueOf(count);
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newContact(
			@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("address") String address,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Provider c = new Provider(id,name,address);
		ProviderStore.getStore().put(id, c);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		Response.created(uri).build();
		
		servletResponse.sendRedirect("../static/html/new_provider.html");
	}
	
	@Path("{provider}")
	public ProviderResource getProvider(
			@PathParam("provider") String provider) {
		return new ProviderResource(uriInfo, request, provider);
	}
	
}
