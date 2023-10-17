package org.vinyas;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/laptops")
public class LaptopResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptop() {
        List<Laptop> laptopList=Laptop.listAll();

        return Response.ok(laptopList).build();
    }
    @GET
    @Path("/{id}")

    @Produces(MediaType.APPLICATION_JSON)
    public Response saveLaptop(@PathParam("id") Long id) {
        Laptop laptop=Laptop.findById(id);
        return Response.ok(laptop).build();

    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveLaptop(Laptop laptop) {
        Laptop.persist(laptop);
        if(laptop.isPersistent()){
            return Response.created(URI.create("/laptops/"+laptop.id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("id")Long id, Laptop updateLaptop) {
        Optional<Laptop> optionalLaptop=Laptop.findByIdOptional(id);
        if(optionalLaptop.isPresent()) {
        Laptop dbLaptop=optionalLaptop.get();
        if(Objects.nonNull(updateLaptop.getName())) {
        dbLaptop.setName(updateLaptop.getName());
        }
            if(Objects.nonNull(updateLaptop.getBrand())) {
                dbLaptop.setBrand(updateLaptop.getBrand());
            }
            if(Objects.nonNull(updateLaptop.getRam())) {
                dbLaptop.setRam(updateLaptop.getRam());
            }
            if(Objects.nonNull(updateLaptop.getExternalStorage())) {
                dbLaptop.setExternalStorage(updateLaptop.getExternalStorage());
            }
        dbLaptop.persist();
        if(dbLaptop.isPersistent()) {
        return Response.created(URI.create("/laptops/"+id)).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        }else {return Response.status(Response.Status.BAD_REQUEST).build();


        }

    }
    @DELETE
    @Path("/{id}")

    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLaptop(@PathParam("id") Long id) {
        boolean isDeleted=Laptop.deleteById(id);
        if(isDeleted) {
            return Response.noContent().build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();

        }

    }
}