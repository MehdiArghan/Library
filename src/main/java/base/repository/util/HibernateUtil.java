package base.repository.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private HibernateUtil() {
    }

    private static final StandardServiceRegistry STANDARD_SERVICE_REGISTRY = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml").build();
    private static final Metadata METADATA = new MetadataSources()
            .getMetadataBuilder(STANDARD_SERVICE_REGISTRY).build();
    private static final SessionFactory SESSION_FACTORY = METADATA.getSessionFactoryBuilder().build();


    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}