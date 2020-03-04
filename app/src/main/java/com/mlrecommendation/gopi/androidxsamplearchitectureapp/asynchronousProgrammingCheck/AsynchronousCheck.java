package com.mlrecommendation.gopi.androidxsamplearchitectureapp.asynchronousProgrammingCheck;

import java.util.UUID;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.startThread;

public class AsynchronousCheck {

    static volatile String token = "";


    public static void main(String[] args) {

        startThread(() -> {
            while (true) {
                System.out.println("Create stuff");
                token = UUID.randomUUID().toString();
                sleep(100);
            }
        });


        while (true) {
            final String lastToken = token;
            sleep(1000);
            startThread(() -> {
                if (lastToken.equals(token)) {
                    System.out.println("Destroying stuff");
                    token = "";
                }
//               sleep(1000);
            });
        }

    }
}
