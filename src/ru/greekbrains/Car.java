package ru.greekbrains;

import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    public Car(int speed) {
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    private Race race;
    private int speed;
    private String name;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public void addToRace(Race race) {
        this.race = race;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));

            System.out.println(this.name + " готов");
            race.getStarted().await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            race.setWinner(this);
            race.getFinished().await();
            } catch (InterruptedException | BrokenBarrierException interruptedException) {
                interruptedException.printStackTrace();

        } finally {
            Thread.currentThread().interrupt();
        }
    }
}
