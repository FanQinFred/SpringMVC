package cqu.utils;


import cqu.mvc.model.FileEntity;
import cqu.mvc.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	 private static final SessionFactory sessionFactory;
	  

	    static {
	    	
	    	final Configuration configuration = new Configuration();
			configuration.addAnnotatedClass( FileEntity.class );
			configuration.addAnnotatedClass( UserEntity.class );sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	    }
	   
	    public static Session getSession(){
	        return sessionFactory.openSession();
	    }

	  

}
