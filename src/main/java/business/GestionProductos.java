package business;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import dao.ProductoDAO;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Producto;
@Stateless
public class GestionProductos {
	@Inject
	private ProductoDAO dao;

	private final Tracer tracer = GlobalTracer.get();

	//@Override
	public void guardar(Producto autos) {
		Span span = tracer.buildSpan("guardar").start();
		try (Scope scope = tracer.scopeManager().activate(span)) {
		dao.insert(autos);
		}
		catch (Exception e) {
			span.log(e.getMessage());
			throw e;
		}finally {
			span.finish();
		}
	}
	
	///@Override
	public void actualizar(Producto autos) {
		Producto aut = dao.getAutos(autos.getCodigo());
		Span span = tracer.buildSpan("actualizar").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
		if(aut != null) {
			dao.update(autos);
		}
		
        }catch (Exception e) {
			span.log(e.getMessage());
			throw e;
		}
        finally {
			span.finish();
		}
	}
	//@Override
	public Producto getAuto(int dni) throws Exception {
		Producto aut = dao.getAutos(dni);
		Span span = tracer.buildSpan("getAuto").start();
		try (Scope scope = tracer.scopeManager().activate(span)) {
		if(aut != null) {
			return aut;
		}else {
			return null;
		}}catch(Exception e){
			span.log(e.getMessage());
			throw e;
		}finally{
			span.finish();
		}
	}
	//@Override
	public List<Producto> getAutos() {
		Span span = tracer.buildSpan("listar").start();
		try{try (Scope scope = tracer.scopeManager().activate(span)) {
				return dao.getAll();
			
			}catch(Exception ex){
				span.log(ex.getMessage());
				throw ex;
			}
			finally{
				span.finish();
			}
		}catch(Exception e){
			return null;
		}
		
	}
	//@Override
	public void borrar(int id) {
		Span span = tracer.buildSpan("eliminar").start();
		try {
			Producto out = dao.getAutos(id);
			
	        try (Scope scope = tracer.scopeManager().activate(span)) {
			if(out !=null) {
				dao.remove(out.getCodigo());
			}else {
				System.out.println("No existe");
			}
		}catch(Exception s) {
			s.printStackTrace();
		}}
		catch (Exception e) {
			span.log(e.getMessage());
			throw e;
		}finally{
			span.finish();
		}
	}
}
