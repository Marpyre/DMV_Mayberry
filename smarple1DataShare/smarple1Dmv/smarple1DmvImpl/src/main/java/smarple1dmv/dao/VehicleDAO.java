package smarple1dmv.dao;

import java.util.List;

import javax.persistence.EntityManager;

import smarple1dmv.bo.VehicleRegistration;

public class VehicleDAO implements IVehicleDAO{
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public void createRegistration(VehicleRegistration registration) {
        em.persist(registration);
	}

	@SuppressWarnings("unchecked")
    public List<VehicleRegistration> getRegistrations(int index, int count)  {
	    return (List<VehicleRegistration>)
	        em.createQuery("select v from VehicleRegistration v")
	                             .setFirstResult(index)
	                             .setMaxResults(count)
	                             .getResultList();
	}
	
	@SuppressWarnings("unchecked")
    public List<VehicleRegistration> getRegistrationByMakeModel(String make, String model)  {
	    return (List<VehicleRegistration>)
			em.createQuery(
					"select v from VehicleRegistration v where v.make like :make and v.model like :model")
			.setParameter("make", make).setParameter("model", model).getResultList();
	}
}