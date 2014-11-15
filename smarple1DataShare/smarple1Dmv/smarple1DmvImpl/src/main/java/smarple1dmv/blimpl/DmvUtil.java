package smarple1dmv.blimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import smarple1dmv.bo.Location;
import smarple1dmv.bo.Person;
import smarple1dmv.bo.Photo;
import smarple1dmv.bo.PhysicalDetails;
import smarple1dmv.bo.Residence;
import smarple1dmv.bo.VehicleRegistration;

public class DmvUtil {

	private EntityManager em;

	public DmvUtil() {
	}

	public DmvUtil(EntityManager em) {
		this.em = em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public void resetDB() {
		Query query = em.createQuery("select p from Photo p");
		for (Photo p : (List<Photo>) query.getResultList()) {
			em.remove(p);
		}

		em.flush();

		query = em.createQuery("select pd from PhysicalDetails pd");
		for (PhysicalDetails pd : (List<PhysicalDetails>) query.getResultList()) {
			em.remove(pd);
		}

		em.flush();

		query = em.createQuery("select r from Residence r");
		for (Residence r : (List<Residence>) query.getResultList()) {
			em.remove(r);
		}

		em.flush();

		query = em.createQuery("select l from Location l");
		for (Location l : (List<Location>) query.getResultList()) {
			em.remove(l);
		}

		em.flush();

		query = em.createQuery("select vr from VehicleRegistration vr");
		for (VehicleRegistration reg : (List<VehicleRegistration>) query
				.getResultList()) {
			reg.getOwners().clear();
			em.remove(reg);
		}

		query = em.createQuery("select p from Person p");
		for (Person person : (List<Person>) query.getResultList()) {
			em.remove(person);
		}

		em.flush();
	}
}
