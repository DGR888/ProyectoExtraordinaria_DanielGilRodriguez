package com.ejemplo.E02RestEjemplo.Rest;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ejemplo.E02RestEjemplo.Entities.Product;
import com.ejemplo.E02RestEjemplo.Models.ProductosModel;

@Path("productos")
public class ProductsRest {
    static ProductosModel products;

    public ProductsRest() {

	try {
	    products = new ProductosModel();
	} catch (SQLException e) {
	    System.err.println("No puedo abrir la conexion con 'Products': " + e.getMessage());
	}
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list
    	(	@QueryParam("filter") String filter,  
            @QueryParam("limit") Integer limit, 
	        @QueryParam("offset") Integer offset,
	        @QueryParam("order") String order) {
	Response respuesta = Response.status(Response.Status.NOT_FOUND).build();
	
	if (products != null) {
	    ArrayList<Product> listaProductos = products.lista(filter, limit, offset,order);
	    if (listaProductos != null) {
		respuesta = Response.status(Response.Status.OK).entity(listaProductos).build();
	    }

	}
	return respuesta;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer id) {
	
	Response respuesta = Response.status(Response.Status.NOT_FOUND).entity("No he encotrado").build();
	
	if (products != null) {
	    Product productos = products.read(id);
	    if (productos != null) {
		respuesta = Response.status(Response.Status.OK).entity(productos).build();
	    }
	}
	return respuesta;
    }
    
        @POST
    /*El método POST se utiliza para enviar una entidad a un recurso en específico, 
     * causando a menudo un cambio en el estado o efectos secundarios en el servidor.*/
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Product productos) {
	Integer idProducto;
	Response response;
	try {
		idProducto = products.insert(productos);
	    response = Response.status(Response.Status.CREATED).entity(idProducto).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.CONFLICT).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }

    @PUT
    /*El metodo PUT reemplaza todas las representaciones 
    actuales del recurso de destino con la carga útil de la petición.*/
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Product productos) {
	Boolean productoActualizado;
	Response response;
	try {
	    productoActualizado = products.update(productos);
	    response = Response.status(Response.Status.OK).entity(productoActualizado).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.NOT_MODIFIED).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }
    
    
    @DELETE
    //El método DELETE borra un recurso en específico.
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
	Boolean productoActualizado;
	Response response;
	try {
		productoActualizado = products.delete(id);
	    response = Response.status(Response.Status.OK).entity(productoActualizado).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.NOT_FOUND).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }

    
}
