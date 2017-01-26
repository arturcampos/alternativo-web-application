/**
 *
 */
package app.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import app.util.JpaUtil;

/**
 * @author artur.rodrigues
 *
 */
@Transactional
public class DAOImpl<T> implements IDAO<T> {

	protected EntityManager entitymanager;
	protected Class<T> classe;

	/**
	 *
	 * @param clazz
	 */
	public DAOImpl(Class<T> clazz) {
		this.classe = clazz;

		synchronized (DAOImpl.class) {
			if (entitymanager == null) {
				entitymanager = JpaUtil.getEntityManager();
			}
		}
	}

	/**
	 * @param obj
	 *            - Objeto do tipo gen�rico
	 */
	public void save(T obj) {

		entitymanager.persist(obj);
	}

	/**
	 * @param id
	 * @return
	 */
	public T remove(Serializable id) {
		T obj = findById(id);
		if (obj != null) {
			entitymanager.remove(obj);
		}
		return obj;
	}

	/**
	 * @param id
	 * @return
	 */
	public T findById(Serializable id) {
		T obj = (T) entitymanager.find(this.classe, id);
		return obj;
	}

	/**
	 * @return
	 */
	public List<T> findAll() {
		TypedQuery<T> query = entitymanager.createNamedQuery(classe.getSimpleName() + ".findAll", this.classe);
		List<T> results = query.getResultList();

		return results;

	}

	/**
	 * @param obj
	 *            - objeto tipo gen�rico
	 */
	public void update(T obj) {
		entitymanager.merge(obj);
	}

}
