package com.vegaasen.http.jetty.jersey.rest.controller;

import com.vegaasen.http.jetty.storage.SimpleStorage;
import com.vegaasen.http.jetty.utils.PatheticRandomUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * Just an small test controller using standard jersey
 *
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
@Path("/test/")
public final class TestController {

    public static final String RESPONSE_SIMPLE_JERSEY = "SimpleJerseyResponse";

    @GET
    @Path("simple")
    @Produces(MediaType.TEXT_PLAIN)
    public Response simpleRequest() {
        return Response.ok().entity(RESPONSE_SIMPLE_JERSEY).build();
    }

    @GET
    @Path("simpleWithParams/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response simpleRequestWithSomeParams(
            @QueryParam("ping") final String ping,
            @PathParam("id") final String id
    ) {
        if (ping == null || ping.isEmpty()) {
            return Response.status(500).entity("Missing ping!:-O").build();
        }
        return Response.ok().entity(String.format("ping: {%s} for id: {%s}", ping, id)).build();
    }

    @POST
    @Path("simplePostWithParams")
    @Produces(MediaType.TEXT_PLAIN)
    public Response simplePostWithParams(
            @QueryParam("thing") final String thing
    ) {
        if (thing == null || thing.isEmpty()) {
            return Response.status(500).entity("No thing! :-O").build();
        }
        final long stamp = System.currentTimeMillis();
        SimpleStorage.INSTANCE.addThing(stamp, thing);
        return Response.ok().lastModified(new Date(stamp)).entity(Long.toString(stamp)).build();
    }

    @GET
    @Path("simpleThatProducesJson")
    @Produces(MediaType.APPLICATION_JSON)
    public Response simpleThatProducesJson() {
        return Response.ok().entity(PatheticRandomUtils.getSomeRandomThings()).build();
    }

    @GET
    @Path("simpleThatProducesXml")
    @Produces(MediaType.APPLICATION_XML)
    public Response simpleThatProducesXml() {
        return Response.ok().entity(PatheticRandomUtils.getSomeRandomThings()).build();
    }

}
