import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JantarDosFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Lock[] garfos = new ReentrantLock[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new ReentrantLock();
        }

        Thread[] filosofos = new Thread[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Thread(new Filosofo(i, garfos[i], garfos[(i + 1) % numFilosofos]));
            filosofos[i].start();
        }
    }
}