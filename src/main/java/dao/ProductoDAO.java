package dao;

import java.util.List;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import model.Producto;
@Stateless
public class ProductoDAO {
	
	@PersistenceContext
	private EntityManager em;
	

    
	public void insert(Producto autos) {
		em.merge(autos);
	}
    
	public void update(Producto autos) {
		em.merge(autos);
	}
    
	public void remove(int codigo) {
		Producto autos = em.find(Producto.class, codigo);
		em.remove(autos);
	}
    
	public Producto read(int codigo) {
		Producto autos = em.find(Producto.class, codigo);
		return autos;
	}
    
	public List<Producto> getAll(){
		String jpql = "SELECT c FROM Autos c";
		Query q = em.createQuery(jpql, Producto.class);
		return q.getResultList();
	}
    
	public Producto getAutos(int cedula){
		String jpql = "SELECT c FROM Autos c WHERE c.codigo = :codigo";
		Query q = em.createQuery(jpql, Producto.class);
		q.setParameter("codigo", cedula);
		List<Producto> clientes = q.getResultList();
		if(clientes.size()>0)
			return clientes.get(0);
		return null;
	}
	
}
