package cqu.mvc.model;


import cqu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.sound.midi.SoundbankResource;
import java.util.List;

public class FileDao {
	public static void addFile(String id, String name,UserEntity user) {
		FileEntity fileEntity = new FileEntity(id, name, user);
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

	public static List<FileEntity> getFileByUser(String username) {

		Session session = HibernateUtil.getSession();
		try {
			Query<FileEntity> q=session.createQuery("from FileEntity where user_name='" + username + "'", FileEntity.class);
			return q.list();
		} finally {
			session.close();
		}

	}

}
