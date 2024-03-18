package kas.concurrente.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
<<<<<<< HEAD
       
=======
        //initHilos();
>>>>>>> 8eeb54a9c09f3c612a04e4f59cd28c5a01a93c5e
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

    /**
     * AGREGA 2 TEST MAS
     * TEST bien hechos
     */
}



