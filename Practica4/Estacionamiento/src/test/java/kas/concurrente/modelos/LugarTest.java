package kas.concurrente.modelos;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LugarTest {
    Semaphore semaforo;
    Lugar lugar;
    List<Thread> hilos;
    static final int numHilos = 15;

    @BeforeEach
    void setUp() throws InterruptedException{
        lugar = new Lugar(1);
        semaforo = new Semaphore(1);
    }

    @Test
    void constructorTest(){
        assertTrue(lugar.getId() == 1 && lugar.getDisponible() == true);
    }

    @Test
    void estacionaTest() throws InterruptedException{
        lugar.estaciona();
        assertTrue(lugar.getDisponible());
    }

    @Test
    void vecesEstacionado() throws InterruptedException{
        lugar.estaciona();
        lugar.estaciona();
        assertTrue(lugar.getVecesEstacionado() == 2);
    }

    @Test
    void cantidadPermisivos(){
        assertTrue(lugar.getSemaforo().availablePermits() == 1);
    }
}



