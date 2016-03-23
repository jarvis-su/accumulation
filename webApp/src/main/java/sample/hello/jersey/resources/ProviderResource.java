package sample.hello.jersey.resources;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import sample.hello.jersey.bean.Provider;
import sample.hello.jersey.storage.ProviderStore;
import sample.hello.jersey.util.ParamUtil;

import com.sun.jersey.api.NotFoundException;

public class ProviderResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String provider;
	
	public ProviderResource(UriInfo uriInfo, Request request, String providerId) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.provider = providerId;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Provider getProvider() {
		Provider cont = ProviderStore.getStore().get(provider);
		if(cont==null)
			throw new NotFoundException("No such Provider.");
		return cont;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putProvider(JAXBElement<Provider> jaxbProvider) {
		Provider c = jaxbProvider.getValue();
		return putAndGetResponse(c);
	}
	
	@PUT
	public Response putContact(@Context HttpHeaders herders, byte[] in) {
		Map<String,String> params = ParamUtil.parse(new String(in));
		Provider c = new Provider(params.get("id"), params.get("name"), params.get("address"));
		return putAndGetResponse(c);
	}
	
	private Response putAndGetResponse(Provider c) {
		Response res;
		if(ProviderStore.getStore().containsKey(c.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		ProviderStore.getStore().put(c.getId(), c);
		return res;
	}
	
	@DELETE
	public void deleteContact() {
		Provider c = ProviderStore.getStore().remove(provider);
		if(c==null)
			throw new NotFoundException("No such Provider.");
	}
}
