package pl.football.league.threads;

import javafx.concurrent.Task;

import javax.persistence.EntityManager;

public class AddItemTask extends Task {
    private EntityManager entityManager;
    Object item;
    Buffer b;

    public AddItemTask(EntityManager em, Object item, Buffer b){
        entityManager = em;
        this.item = item;
        this.b = b;
    }

    @Override
    protected Object call() throws Exception {
        System.out.println("Start producer's thread");
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(item);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        if (b != null)
            b.put();

        System.out.println("Finish adding " + item.toString() + " (End prodcuer's thread)");
        return null;
    }
}
