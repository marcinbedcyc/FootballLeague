package pl.football.league.threads;

import javafx.concurrent.Task;

import javax.persistence.EntityManager;

/**
 * Współbieżny wątek/task dodający element do bazy danych. Producent przy komunikacji z aplikacją.
 *
 * @author Marcin Cyc
 * @see javafx.concurrent.Task
 */
public class AddItemTask extends Task {
    /**
     * Menadżer encji odpowiedzialny za komunikację z bazą danych.
     */
    private EntityManager entityManager;
    /**
     * Element dodawany do bazy danych.
     */
    private Object item;
    /**
     * Bufor służący do powiadomienia o zakończeniu działania.
     *
     * @see pl.football.league.threads.Buffer
     */
    private Buffer b;

    /**
     * Tworzy nowy task dodawania obiektu do bazy.
     * @param em menadżer encji(komunikacja z bazą)
     * @param item dodawany obiekt
     * @param b bufor powiadamiający
     */
    public AddItemTask(EntityManager em, Object item, Buffer b){
        entityManager = em;
        this.item = item;
        this.b = b;
    }

    /**
     * Uruchamiana task dodający element do bazy danych. Wypisuje informację do konsoli o rozpoczęciu i zazkończeniu
     * działania. Po dodaniu elementu zapełnia bufor i kończy wątek.
     *
     * @return zawsze null
     * @throws Exception  Nieprzechwycony wątek, który może wystąpić podczas operacji w tle
     */
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
