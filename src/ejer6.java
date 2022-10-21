import modelos.Alumno;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class ejer6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Alumno>listaAlumnos = new ArrayList<>();

        int opcion ;
        do {
            opcion = menu(scanner);
            scanner.nextLine();

            switch (opcion){
                case 1: Alumno alumno = crearAlumno(scanner);
                listaAlumnos.add(alumno);
                    break;
                case 2:
                    System.out.println("dime el nombre del archivo");
                    String nombre= scanner.nextLine();
                    try {
                        cargarAlumnos(listaAlumnos,nombre);
                    } catch (IOException e) {
                        System.out.println("Error al leer el fichero");;
                    }
                    break;
                case 3:
                    System.out.println("Dime el nombre del fichero");
                    String nombrefi = scanner.nextLine();
                    try {
                        guadarAlumnos(listaAlumnos,nombrefi);
                    } catch (IOException e) {
                        System.out.println("Error al escribir");
                    }
                    break;
                case 4:
                    muestraAlumnos(listaAlumnos);
                    break;
                default:
                    System.out.println("Fallo");
            }

        }while(opcion!=4);

    }

    private static Alumno crearAlumno(Scanner scanner) {
        System.out.println("dime nombre");
        String nombre = scanner.nextLine();
        System.out.println("dime los apellidos");
        String apellidos = scanner.nextLine();
        System.out.println("Dime el dni");
        String dni = scanner.nextLine();
        return new Alumno(nombre,apellidos,dni);
    }
    private static void cargarAlumnos(ArrayList<Alumno> listaAlumnos, String fileName) throws IOException {


        ProcessBuilder processBuilder = new ProcessBuilder("java","-jar","librerias/hijoLectura.jar");

        processBuilder.redirectErrorStream(true);

        Process proceso = processBuilder.start();

        //conectar con los hijos

        OutputStream os = proceso.getOutputStream(); // esto es para escribir al hijo

        PrintStream ps = new PrintStream(os);

        InputStream is = proceso.getInputStream(); //leer

        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);

        BufferedReader br = new BufferedReader(isr);

        ps.println(fileName);
        ps.flush();//como si pulsaras intro

        if((new File(fileName)).exists()){
            listaAlumnos.clear();
        }
        else
        {
            return;
        }

        String linea;
        while(!(linea = br.readLine()).isEmpty()){
            listaAlumnos.add(new Alumno(linea));
        }


    }

    private static void guadarAlumnos(ArrayList<Alumno> listaAlumnos, String fileName) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("java","-jar","librerias/hijo.jar");
        processBuilder.redirectErrorStream(true);
        Process proceso = processBuilder.start();

       OutputStream outputStream = proceso.getOutputStream();
       PrintStream printStream = new PrintStream(outputStream);

       printStream.println(fileName);
        printStream.flush();

        for (Alumno a: listaAlumnos) {
            printStream.println(a.toString());
            printStream.flush();
        }

        printStream.println();
        printStream.flush();

    }

    private static void muestraAlumnos (ArrayList<Alumno> listaAlumnos) {
        for (Alumno a: listaAlumnos) {
            System.out.println(a.toString());
        }
    }

    private static int menu(Scanner scanner) {
        System.out.println("1.- Crear alumno");
        System.out.println("2.- Cargar alumno");
        System.out.println("3.- Guardar alumno");
        System.out.println("4.- exit ");


        return scanner.nextInt();
    }
}
