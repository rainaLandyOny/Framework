package mg.itu.prom16;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.PackageScanner;

public class FrontController extends HttpServlet{
    // private static String controllerPackage = "controller";
    // public static ArrayList<Class<?>> checkClasses(File directory, String packageName)throws Exception{
    //     ArrayList<Class<?>> classes = new ArrayList<>();
    //         String path=packageName.replaceAll("[.]","/");
    //         String decodePath=URLDecoder.decode(path, "UTF-8");
    //         URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);
    //         directory = new File(packageUrl.toURI());
    //         File[] files = directory.listFiles();
    //         for (File file : files){
    //             if (file.isDirectory()) {
    //                 classes.addAll(checkClasses(file, packageName+"."+file.getName()));
    //             }else if(file.getName().endsWith(".class")){
    //                 String ClassName = packageName + "."+ file.getName().substring(0, file.getName().length()-6);
    //                 Class<?> clazz = class.forName(className);
    //                 if(clazz.isAnnotationPresent(mg.itu.prom16.AnnotationController))classes.add(clazz);
    //             }

    //         }
    //         return classes;
    private List<Class<?>> controllers;
    private boolean isChecked;

    public List<Class<?>> getControllers(){
        return this.controllers;
    }
    public void setControllers(List<Class<?>> controllers){
        this.controllers= controllers;
    }
    public boolean isChecked(){
        return this.isChecked;
    }
    public void setisChecked(boolean check){
        this.isChecked = check;
    }

    
    public void initVariables(){
        String packageName = getServletConfig().getInitParameter("controllerPage");
        List<Class<?>> classes = PackageScanner.getComponents(packageName, Controller.class);
        this.setControllers(classes);
    }
    @Override
    public void init() throws ServletException{
        try {
        this.initVariables();
        this.setisChecked(false);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws IOException {
        PrintWriter out= response.getWriter();
        try{
            if (!this.isChecked()) {
                this.initVariables();
                this.setisChecked(true);
            }
            out.println("URL : "+request.getRequestURL().toString());
            out.println("Nombre de Controllers"+ this.getControllers().size());
            out.println("List controllers :");
            for (Class<?> currentClass : this.getControllers()) {
                out.println(currentClass.getName());
            }
        }
            catch(Exception e){
                out.println(e.getMessage());
            }
        }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}