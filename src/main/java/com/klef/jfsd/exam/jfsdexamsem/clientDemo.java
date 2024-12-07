package com.klef.jfsd.exam.jfsdexamsem;



import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class clientDemo {
    public static void main(String[] args) {
        // Configuration and session setup
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.addAnnotatedClass(Project.class).buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Project project1 = new Project();
            project1.setProjectName("AI Development");
            project1.setDuration(12);
            project1.setBudget(500000);
            project1.setTeamLead("John Doe");

            Project project2 = new Project();
            project2.setProjectName("Web App");
            project2.setDuration(6);
            project2.setBudget(200000);
            project2.setTeamLead("Jane Smith");

            session.save(project1);
            session.save(project2);

            Criteria criteria = session.createCriteria(Project.class);

            
            criteria.setProjection(Projections.rowCount());
            long count = (long) criteria.uniqueResult();
            System.out.println("Total Projects: " + count);

            
            criteria.setProjection(Projections.max("budget"));
            double maxBudget = (double) criteria.uniqueResult();
            System.out.println("Maximum Budget: " + maxBudget);

         
            criteria.setProjection(Projections.min("budget"));
            double minBudget = (double) criteria.uniqueResult();
            System.out.println("Minimum Budget: " + minBudget);

            criteria.setProjection(Projections.sum("budget"));
            double totalBudget = (double) criteria.uniqueResult();
            System.out.println("Total Budget: " + totalBudget);

            
            criteria.setProjection(Projections.avg("budget"));
            double avgBudget = (double) criteria.uniqueResult();
            System.out.println("Average Budget: " + avgBudget);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
