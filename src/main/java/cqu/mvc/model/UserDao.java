package cqu.mvc.model;

import cqu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserDao {
	public static void addUser(String name, String pwd) {

		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		userEntity.setPwd(pwd);
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(userEntity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public static UserEntity getUserByName(String name) {
		Session session = HibernateUtil.getSession();
		try {
			return session.get(UserEntity.class, name);
		} finally {
			session.close();
		}

	}
}
