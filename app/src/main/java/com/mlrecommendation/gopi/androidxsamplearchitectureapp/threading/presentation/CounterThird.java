package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

public class CounterThird {
    int requestCount = 0;

    public void firstMethod() {
        synchronized (this) {
            int id = 2;
            Student student = new Student("gopi", 2);
            incrementStudentId(student, id);
            requestCount++;
            System.out.println("requestCounter value is " + requestCount + " student id is " + student.getId());
        }
    }

    private void incrementStudentId(Student student, int id) {
        student.setId(++id);
    }

    public static void main(String[] args) throws InterruptedException {
        CounterThird obj = new CounterThird();
        new Thread(() -> { obj.firstMethod(); }).start();

        new Thread(() -> { obj.firstMethod(); }).start();

        Thread.sleep(5000);
    }
}