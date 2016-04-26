/**
 * 
 */
package app.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * @author artur.rodrigues
 *
 */
public class DaoImpl<T> implements Dao<T> {

	protected EntityManager entitymanager;
	protected Class<T> classe;

	public DaoImpl(Class<T> clazz) {
		this.classe = clazz;

		synchronized (DaoImpl.class) {
			if (entitymanager == null) {
				entitymanager = JpaUtil.getEntityManager();
			}
		}
	}

	public void save(T obj) {
		EntityTransaction tx = entitymanager.getTransaction();
		tx.begin();
		entitymanager.persist(obj);
		tx.commit();
	}

	public T remove(Serializable id) {
		T obj = findById(id);
		if (obj != null) {
			EntityTransaction tx = entitymanager.getTransaction();
			tx.begin();
			entitymanager.remove(obj);
			tx.commit();
		}
		return obj;
	}

	public T findById(Serializable id) {
		T obj = (T) entitymanager.find(this.classe, id);
		return obj;
	}

	public List<T> findAll() {
		TypedQuery<T> query = entitymanager.createNamedQuery(classe.getSimpleName() + ".findAll", this.classe);
		List<T> results = query.getResultList();

		return results;

	}

	public void update(T obj) {
		EntityTransaction tx = entitymanager.getTransaction();
		tx.begin();
		entitymanager.merge(obj);
		tx.commit();
	}

}
