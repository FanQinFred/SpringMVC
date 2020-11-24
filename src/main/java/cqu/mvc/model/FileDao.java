package cqu.mvc.model;


import cqu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FileDao {
	public static void addFile(String id, String name) {
		FileEntity fileEntity = new FileEntity(id, name);
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(fileEntity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static FileEntity getFileById(String id) {
		Session session = HibernateUtil.getSession();
		try {
			return session.get(FileEntity.class, id);
		} finally {
			session.close();
		}

	}

	public static void deleteById(String id) {

		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			FileEntity fileEntity = session.get(FileEntity.class, id);
			session.delete(fileEntity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
}
