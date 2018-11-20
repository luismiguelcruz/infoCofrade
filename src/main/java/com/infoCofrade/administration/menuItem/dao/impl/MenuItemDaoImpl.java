package com.infoCofrade.administration.menuItem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.menuItem.dao.MenuItemDao;
import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;

public class MenuItemDaoImpl implements MenuItemDao {
	private static MenuItemDao instance = new MenuItemDaoImpl();
	private Session session;
	
	//private final static Logger logger = Logger.getLogger(BaseDAO.class);

	private MenuItemDaoImpl() {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
	}

	public static MenuItemDao getInstance() {
		if (instance == null) {
			synchronized (MenuItemDaoImpl.class) {
				if (instance == null) {
					instance = new MenuItemDaoImpl();
				}
			}
		}

		return instance;
	}
	
	public synchronized MenuItemVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		MenuItemVO menuItem = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from MenuItemVO");
			if(id != null) {
				queryString.append(" where id = " + id);
			}
			
			menuItem = (MenuItemVO)session.createQuery(queryString.toString()).uniqueResult();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return menuItem;
	}

	public synchronized List<MenuItemVO> findAll(Transaction transaction) {
		List<MenuItemVO> list = new ArrayList<MenuItemVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder(
					"SELECT * FROM `MENU_ITEM` WHERE `idParent` IS NULL ORDER BY `orderToShow`");
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(MenuItemVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}

	public synchronized Long countAll() {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	public synchronized List<MenuItemVO> findUsingTemplate(Transaction transaction,
			MenuItemVO menuItem) {
		List<MenuItemVO> list = new ArrayList<MenuItemVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }

			if (menuItem == null) {
				throw new Exception("DAO Exception: Menu Item is null");
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT * FROM `MENU_ITEM` ");
			StringBuilder conditions = new StringBuilder();
			if (menuItem.getId() != null) {
				conditions.append(" id = " + menuItem.getId() + " AND");
			}
			if (menuItem.getItemName() != null) {
				conditions.append(" UPPER(`itemName`) like '%"
						+ menuItem.getItemName().toUpperCase() + "%' AND");
			}
			if (menuItem.getBeanName() != null) {
				conditions.append(" UPPER(`beanName`) like '%"
						+ menuItem.getBeanName().toUpperCase() + "%' AND");
			}
			if (menuItem.getIdParent() != null) {
				conditions.append(" `idParent` = " + menuItem.getIdParent()
						+ " AND");
			}
			if (menuItem.getOrderToShow() != null) {
				conditions.append(" `orderToShow` = "
						+ menuItem.getOrderToShow() + " AND");
			}
			if (menuItem.getHasChildren() != null) {
				conditions.append(" `hasChildren` = "
						+ menuItem.getHasChildren() + " AND");
			}

			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3)
						+ " ORDER BY `orderToShow`");
			}

			queryString.append(conditions);

			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(MenuItemVO.class);

			list = query.list();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}

	public synchronized Long countUsingTemplate(MenuItemVO menuItem) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	public synchronized Long countByQuery(String query) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	public synchronized void createItem(MenuItemVO menuItem) throws DaoException{
		MenuItemVO it = new MenuItemVO();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = null;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (menuItem == null) {
				throw new Exception("Menu Item is null");
			}

			it.setOrderToShow(menuItem.getOrderToShow());

			// Search if there is any element with the same idParent and orderToShow
			List<MenuItemVO> listAux = this.findUsingTemplate(
					session.getTransaction(), it);
			if (listAux != null) {
				int i = 0;
				while (i < listAux.size()) {
					it = listAux.get(i);
					if (it.getOrderToShow().compareTo(menuItem.getOrderToShow()) == 0) {
						if(it.getIdParent() == null && menuItem.getIdParent() == null){
							throw new Exception(
									"Existe otro elemento de menú con el mismo padre y orden de listado");
						} else if(it.getIdParent() != null && menuItem.getIdParent() != null &&
								it.getIdParent().compareTo(menuItem.getIdParent()) == 0 &&
								(it.getId() == null || menuItem.getId() == null || 
								it.getId().compareTo(menuItem.getId()) != 0)){
							throw new Exception(
									"Existe otro elemento de menú con el mismo padre y orden de listado");
						}
					}
					i++;
				}
			}

			if(menuItem.getId() == null){
				session.save(menuItem);	
				
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ", "Elemento creado correctamente"));
			} else {
				session.merge(menuItem);
			
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ", "Elemento actualizado correctamente"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", "No se pudo crear el elemento"));
			throw new DaoException(ex.getMessage());
		}
	}

	public synchronized void deleteItem(MenuItemVO menuItem) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = null;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (menuItem == null) {
				throw new Exception("Menu Item is null");
			}

			session.delete(menuItem);
			currentTransaction.commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ", "Elemento borrado correctamente"));
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", "Error borrando el elemento de menú"));
		}
	}
}