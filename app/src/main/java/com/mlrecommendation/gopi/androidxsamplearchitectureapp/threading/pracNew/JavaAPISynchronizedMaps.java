package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class JavaAPISynchronizedMaps {

    public void synchronizedMaps() {
        final HashMap hashMap = new HashMap();
        hashMap.put("a",1);
        hashMap.put("b",2);
        hashMap.put("c",3);

        Map m = Collections.synchronizedMap(hashMap);
        Set s = m.keySet();  // Needn't be in synchronized block
        synchronized (m) {  // Synchronizing on m, not s!
            ThreadUtils.sleep(250);
            Iterator i = s.iterator(); // Must be in synchronized block
            while (i.hasNext()) {
                print(i.next() + "");
                hashMap.put("www",56);  // ConcurrentModificationException
            }
        }
    }

    public void concurrentHashMap() {
        final ConcurrentHashMap hashMap = new ConcurrentHashMap();
        hashMap.put("a",1);
        hashMap.put("b",2);
        hashMap.put("c",3);

        Iterator i = hashMap.keySet().iterator(); // Must be in synchronized block
        while (i.hasNext()) {
            print(i.next() + "");
            hashMap.put("www",56);  // ConcurrentModificationException
        }

        System.out.println("www = " + hashMap.get("www"));
       /* Map m = Collections.synchronizedMap(hashMap);
        Set s = m.keySet();  // Needn't be in synchronized block
        synchronized (m) {  // Synchronizing on m, not s!
            ThreadUtils.sleep(250);

        }*/
    }

    public static void main(String[] args) {
        JavaAPISynchronizedMaps obj = new JavaAPISynchronizedMaps();
//        obj.synchronizedMaps();
        obj.concurrentHashMap();
    }
}
