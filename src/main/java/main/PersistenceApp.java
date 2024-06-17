package main;

import entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceApp {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceAppPU");
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			Factura factura1 = new Factura();

			factura1.setNumero(12);
			factura1.setFecha("10/08/2024");

			Domicilio dom = new Domicilio("san martin",1222);
			Cliente cliente = new Cliente("pablo","muñoz",25763849);
			cliente.setDomicilio(dom);
			dom.setCliente(cliente);

			factura1.setCliente(cliente);

			Categoria perecederos = new Categoria("perecederos");
			Categoria lacteos = new Categoria("lacteos");
			Categoria limpieza = new Categoria("limpieza");

			Articulo art1 = new Articulo(200,"yogurt ser sabor frutilla",20);
			Articulo art2 = new Articulo(300,"detergente Magistral",80);

			art1.getCategorias().add(perecederos);
			art1.getCategorias().add(lacteos);
			lacteos.getArticulos().add(art1);
			perecederos.getArticulos().add(art1);

			art2.getCategorias().add(limpieza);
			limpieza.getArticulos().add(art2);

			DetalleFactura det1 = new DetalleFactura();

			det1.setArticulo(art1);
			det1.setCantidad(2);
			det1.setSubtotal(40);

			art1.getDetalle().add(det1);
			factura1.getDetalles().add(det1);
			det1.setFactura(factura1);

			DetalleFactura det2 = new DetalleFactura();
			det2.setArticulo(art2);
			det2.setCantidad(1);
			det2.setSubtotal(80);

			art2.getDetalle().add(det2);
			factura1.getDetalles().add(det2);
			det2.setFactura(factura1);

			factura1.setTotal(120);
			em.persist(factura1);

			//em.persist(cliente);


			em.flush();

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();

	}

}
