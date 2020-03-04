package com.mlrecommendation.gopi.androidxsamplearchitectureapp.javaConcepts.clint;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

class Example {
     private static String otherMsg = null;
     private static String id = null;
     public static String PLATFORM_CONTENT_DIR = Environment.getExternalStorageDirectory() + File.separator + "ss";

     static {
//          id = UUID.randomUUID().toString();
          id = otherMsg.substring(1);
     }

     public Example(String idd) {
          Example.otherMsg = idd;
     }

     public String getId() {
          return id;
     }

     public static void main(String[] args) {
          Example obj = new Example("werew1");
//          Example.id = "werew";
         System.out.println("id = " + obj.getId());
     }
}