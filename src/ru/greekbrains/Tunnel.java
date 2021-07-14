package ru.greekbrains;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static Semaphore semaphore;

    public Tunnel(int capacity) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров и " + capacity + " вместимостью";
        semaphore = new Semaphore(capacity, false);
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
