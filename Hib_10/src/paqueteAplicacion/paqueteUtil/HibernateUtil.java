package paqueteAplicacion.paqueteUtil;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
 
	private static final SessionFactory factoriaSesiones = buildSessionFactory(); 
	// Session session = sessions.openSession();
	// private static final SessionFactory sessionFactory = buildSessionFactory();
 
	private static SessionFactory buildSessionFactory() {
		try {
			return new AnnotationConfiguration().configure().buildSessionFactory();
			// Create the SessionFactory from hibernate.cfg.xml
			// return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 
	public static SessionFactory getSessionFactory() {
		return factoriaSesiones;
	}
 
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
 
}