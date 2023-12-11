import java.util.concurrent.locks.Lock;

class Filosofo implements Runnable {
    private final int id;
    private final Lock garfoEsquerdo;
    private final Lock garfoDireito;

    public Filosofo(int id, Lock garfoEsquerdo, Lock garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep(1000); // Simula o pensamento
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comendo.");
        Thread.sleep(1000); // Simula o ato de comer
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();

                garfoEsquerdo.lock();
                System.out.println("Filósofo " + id + " pegou o garfo esquerdo.");

                garfoDireito.lock();
                System.out.println("Filósofo " + id + " pegou o garfo direito.");

                comer();

                garfoDireito.unlock();
                System.out.println("Filósofo " + id + " largou o garfo direito.");

                garfoEsquerdo.unlock();
                System.out.println("Filósofo " + id + " largou o garfo esquerdo.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}