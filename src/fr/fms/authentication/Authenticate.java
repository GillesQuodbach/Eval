package fr.fms.authentication;

import fr.fms.dao.CustomerDao;
import fr.fms.dao.Dao;
import fr.fms.dao.DaoFactory;
import fr.fms.dao.UserDao;
import fr.fms.entities.Customer;
import fr.fms.entities.User;

public class Authenticate {
	private Dao<Customer> customerDao = DaoFactory.getCustomerDao();
	private Dao<User> userDao = DaoFactory.getUserDao();

	/**
	 * méthode qui vérifie si login et pwd correspond à un utilisateur en base
	 * @param log
	 * @param pwd
	 * @return id de l'utilisateur, 0 si non trouvé
	 */
	public int existUser(String log, String pwd) {
		User user = ((UserDao)userDao).findUserByCredentials(log,pwd);
		if(user != null )	return user.getId();
		return 0;
	}
	
	/**
	 * méthode qui vérifie si login et pwd correspond à un ADMIN en base
	 * @param log
	 * @param pwd
	 * @return id de l'ADMIN, 0 si non trouvé
	 */
	public String existAdmin(String log, String pwd) {
		User user = ((UserDao)userDao).findUserByCredentials(log,pwd);
		if(user != null )	return user.getRole();
		return "Not an admin";
	}
	
	/**
	 * méthode qui vérifie si login correspond à un utilisateur en base
	 * @param log
	 * @return id de l'utilisateur, 0 si non trouvé
	 */
	public int existUser(String log) {
		User user = ((UserDao)userDao).findUserByLogin(log);
		if(user != null )	return user.getId();
		return 0;
	}

	/**
	 * méthode qui renvoi un client correspondant à un email (unique en base)
	 * @param email 
	 * @return client
	 */
	public Customer existCustomerByEmail(String email) {
		return ((CustomerDao)customerDao).findCustomerByEmail(email);
	}
	/**
	 * méthode qui crée un utilisateur
	 * @param email, password
	 */
	public void addUser(String email, String password) {
		userDao.create(new User(email, password));		
	}
	/**
	 * méthode qui crée un client
	 * @param customer
	 */
	public boolean addCustomer(Customer customer) {
		return customerDao.create(customer);		
	}
}
