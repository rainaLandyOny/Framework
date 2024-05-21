package utils;

import java.lang.annotation.*;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import mg.itu.prom16.*;

public class PackageScanner {
    public static List<Class<?>> getComponents(String packageName, Class<? extends Annotation> annotation) throws Exception{
        List<Class<?>> controllers = new Vector<>();
        String path = Thread.currentThread().getContextClassLoader().getResource(packageName.replace('.', '/')).getPath();
        String decodedPath=URLDecoder.decode(path, "UTF-8");
        File packageDirectory= new File(decodedPath);
        if (packageDirectory.isDirectory()) {
            File[] files = packageDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()&& file.getName().endsWith(".class")) {
                        String className = packageName+"."+file.getName().replace(".class","" );
                        Class<?> currentClass = Class.forName(className);
                        if (currentClass.isAnnotationPresent(annotation)) {
                            controllers.add(currentClass);
                            
                        }
                        
                    }
                }
                
            }
            
        }
        return controllers;
    }
}
