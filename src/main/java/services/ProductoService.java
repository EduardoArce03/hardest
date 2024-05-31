package services;

import java.util.List;

import business.GestionProductos;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Producto;
@Path("productos")
public class ProductoService {
	@Inject
	private GestionProductos ges;
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Producto autos) {
		try {
			ges.guardar(autos);
			return Response.ok(autos).build();
		}catch(Exception ex) {
			ErrorMessage error = new ErrorMessage(500,"Error al guardar cliente: "+ ex.getMessage());
			
			
			return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Producto autos) {
		try {
			ges.actualizar(autos);
			return Response.ok(autos).build();
		}catch(Exception ex) {
			ErrorMessage error = new ErrorMessage(406,"Error al actualizar cliente: "+ex.getMessage());
			return Response.status(Response.Status.NOT_MODIFIED).entity(error).build();
		}
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrar(@QueryParam("codigo") int codigo) {
		try {
			ges.borrar(codigo);
			return Response.ok(codigo).build();
		}catch(Exception ex) {
			ErrorMessage error = new ErrorMessage(99,"Error al borrar el cliente: "+ ex.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public Response leer(@QueryParam("codigo") int codigo) {
		try{
			System.out.println("cedula " +  codigo);
			Producto cli = ges.getAuto(codigo);
			return Response.ok(cli).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(404, "Cliente no existe: " + e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
	}
	
	@GET
	@Path("{dni}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response leer2(@PathParam("dni") int cedula) {
		try{
			System.out.println("cedula " +  cedula);
			Producto cli = ges.getAuto(cedula);
			return Response.ok(cli).build();
		}catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "Cliente no existe: " + e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	
	public Response getClientes(){
		System.out.println("Extrayendo autos");
		List<Producto> clientes = ges.getAutos();
		try {
			return Response.ok(clientes).build();
		}catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "No se registran clientes");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
	}
}
