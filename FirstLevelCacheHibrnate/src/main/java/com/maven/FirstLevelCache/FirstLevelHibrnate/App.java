package com.maven.FirstLevelCache.FirstLevelHibrnate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Avtar a=null;
    	
    	Configuration config= new Configuration().configure().addAnnotatedClass(Avtar.class);
        
        ServiceRegistry registry= new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry(); 
        
        SessionFactory sf= config.buildSessionFactory(registry);
        
//        Session session1=sf.openSession();
        
//        session1.beginTransaction();
//        
//        a= (Avtar)session1.get(Avtar.class, 101);
//        
//        System.out.println(a);
//        
//        a= (Avtar)session1.get(Avtar.class, 101);   // value coming from first level cache second time
//        
//        System.out.println(a);
        
       
        
        Session session1=sf.openSession();
        session1.beginTransaction();
        Query q1=session1.createQuery("from Avtar where aid=101");  // using queries
        q1.setCacheable(true);
        a= (Avtar)q1.uniqueResult();
        

//      a=(Avtar)session1.get(Avtar.class, 101);                    // using get()
        
        System.out.println(a);
        session1.getTransaction().commit();
//        session1.close();                       when use queries don't close sessions
        
        Session session2=sf.openSession();                             // using queries
        session2.beginTransaction();
        Query q2=session1.createQuery("from Avtar where aid=101");   
        q2.setCacheable(true);
        a=(Avtar)q2.uniqueResult();
        
        
//      a=(Avtar)session2.get(Avtar.class, 101);                     // using get()
        System.out.println(a);
        session2.getTransaction().commit();   // value of same query coming from two different sessions second time i.e from database.
//        session2.close();
        
        
        
    }
}
