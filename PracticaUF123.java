package practicauf123;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class PracticaUF123 {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner teclat = new Scanner (System.in);
        
        
        File file1 = new File("puntuacio.txt");
        if (file1.createNewFile()){
        }
        
        File file2 = new File("punts_total.txt");
        file2.delete();
        
        
        boolean sortir=false;
        do{
            //Menú del programa
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║Menú programa d'Equips                             ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║1. Visualitzar els equips i les puntuacions        ║");
            System.out.println("║2. Afegir un nou equip amb les seves puntuacions   ║");
            System.out.println("║3. Modificar les dades d’un equip                  ║");
            System.out.println("║4. Visualitzar les dades del líder i del cuer      ║");
            System.out.println("║5. Sortir                                          ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("Tria una opció:");

            
            int opcio=teclat.nextInt();
            
            switch (opcio){
               case 1:
                    visualEquips ();
                    break;
               case 2:
                    afegirNouEqui();
                    break;
               case 3:
                    modiDadesEqui();
                    break;
               case 4:
                    visualDadesLiderCuer();
                    break;
               case 5:
                   sortir=true;
                   break;
               default:
                   System.out.println("L'Opció no és vàlida");
            }
            
        } while (!sortir);
        
        
    }
    int rslt = 1;
    
    //Visualitzar els equips amb les puntuacions
    private static void visualEquips() throws IOException {

        //String bidimencional
        File file1 = new File("puntuacio.txt");
        //Lleguir el que conte
        BufferedReader content1 = new BufferedReader(new FileReader(file1));
        int lines = 0;
        //Saber número de linies que te l'arxiu
        while (content1.readLine() != null) lines++;
        content1.close();
        //Declaro array bidimensional [linies][opcions] per puntuacions i arrray unidimensional per equips
        int[][] puntuacions = new int [lines][5];
        String[] equips = new String[lines];
        BufferedReader content2 = new BufferedReader(new FileReader(file1));
        //Guardo linia del arxiu com a string
        String linia_arxiu = content2.readLine();
        //Loop de processat
        int k = 0;
        while (linia_arxiu != null){
            //Separem valors
            String[] valors = linia_arxiu.split("[,]", 0);
            equips[k] = valors[0];
            for (int i = 1; i < valors.length; i++){
                puntuacions[k][i-1] = Integer.parseInt(valors[i]);
            }
            k++;
            linia_arxiu = content2.readLine();      
        }
        content2.close();
        for (int i = 0; i<lines; i++){
            System.out.println("Equip: "+equips[i]);
            //Calcular punts de cada equip
            int puntuacionstotal = (puntuacions[i][1]*3)+(puntuacions[i][2]*1); 
            System.out.println("PJ: "+puntuacions[i][0]+"  PG: "+puntuacions[i][1]+"  PE: "+puntuacions[i][2]+"  PP: "+puntuacions[i][3]+"  Punts: "+puntuacionstotal);
            //puntuacions[i][4]
            
            
            //puntuacionstotal
            BufferedWriter bw2;
            bw2 = new BufferedWriter (new FileWriter ("punts_total.txt", true));
            bw2.write (puntuacionstotal + "\n");
            bw2.flush();
            bw2.close();
        }
    }
    
    //Afegir nou equip amb les seves puntuacions
    private static void afegirNouEqui() throws IOException {
        BufferedWriter bw;
        //Indicar quin fitxer s'ha d'escriure
        bw = new BufferedWriter (new FileWriter ("puntuacio.txt", true));
        Scanner teclatequip = new Scanner(System.in);
        Scanner teclatpart1 = new Scanner(System.in);
        Scanner teclatpart2 = new Scanner(System.in);
        Scanner teclatpart3 = new Scanner(System.in);
        Scanner teclatpart4 = new Scanner(System.in);
        //Camps on s'introdueixen les dades
        System.out.println("Afegir nou equip:");
        System.out.println("Equip:");
        String equip = teclatequip.nextLine();
        System.out.println("Partits Jugats:");
        String pj = teclatpart1.nextLine();
        System.out.println("Partits Guanyats:");
        String pg = teclatpart2.nextLine();
        System.out.println("Partits Empatats:");
        String pe = teclatpart3.nextLine();
        System.out.println("Partits Perduts:");
        String pp = teclatpart4.nextLine();
        PreparedStatement sentencia = null;
        bw.write (equip + "," + pj + "," + pg + "," + pe + "," + pp);
        bw.newLine();
        bw.flush();
        bw.close();
    }
    
    //Modificar les dades d'un equip, nom i/o puntuacio
    private static void modiDadesEqui() throws  IOException, FileNotFoundException {
        File file1 = new File("puntuacio.txt");
        Scanner scan = new Scanner(file1);
        // llistat on es guarda cada linia
        ArrayList<String> list = new ArrayList<String>();
        int lines = 0; // Variable per guardar el nombre de linies de l'arxiu
        while (scan.hasNextLine()){
            list.add(scan.nextLine());
            lines+=1;
        }
        // Arrays on guardar equip i puntuacions
        int[][] puntuacions = new int [lines][5];
        String[] equips = new String[lines];
        // String on es guardarà el text original
        String textvell = "";
        //Llegim contingut arxiu
        BufferedReader content1 = new BufferedReader(new FileReader(file1));
        String linia_arxiu = content1.readLine();
        int k = 0;
        while (linia_arxiu != null){
            // Guardem com un string sencer larxiu de text amb un salt de linia a cada linia
            textvell += linia_arxiu+"\n";
            // Extraiem valors de cada liniea
            String[] valors = linia_arxiu.split("[,]", 0);
            // Guardem nom equip de la linia extreta
            equips[k] = valors[0];
            // Guardem en un array de bidimensional el valor de cada puntuació
            for (int i = 1; i < valors.length; i++){
                puntuacions[k][i-1] = Integer.parseInt(valors[i]);
            }
            k++;
            // Següent liniea
            linia_arxiu = content1.readLine();      
        }
        content1.close();
        //Imprimir els equips i escollir-ne un amb el teclat
        Scanner teclatpart1 = new Scanner(System.in);
        System.out.println("Quin equip vols modificar?");
        for (int i = 0; i<equips.length; i++){
            System.out.println((i+1)+": "+equips[i]);
        }
        //Mostrar les puntuacions per pantalla
        String indexequip = teclatpart1.nextLine();
        int j = Integer.valueOf(indexequip);
        System.out.println("Aquestes són les puntuacions del " + equips[Integer.valueOf(indexequip)-1]);
        System.out.println("PJ: "+puntuacions[j-1][0]);
        System.out.println("PG: "+puntuacions[j-1][1]);
        System.out.println("PE: "+puntuacions[j-1][2]);
        System.out.println("PP: "+puntuacions[j-1][3]);
        //Escollir el que es vol canviar
        System.out.println("Què vols modificar? (Nom, PG, PE, PP)");
        Scanner teclatpart2 = new Scanner(System.in);
        String parameditat = teclatpart2.nextLine();
        //Introduir el valor
        System.out.println("Nou valor:");
        Scanner teclatpart3 = new Scanner(System.in);
        String nouvalor = teclatpart3.nextLine();
        switch (parameditat){
           case "Nom":
                equips[j-1] = nouvalor;
                break;
           case "PG":
               puntuacions[j-1][1] = Integer.valueOf(nouvalor);
               break;
           case "PE":
                puntuacions[j-1][2] = Integer.valueOf(nouvalor);
                break;
           case "PP":
                puntuacions[j-1][3] = Integer.valueOf(nouvalor);
                break;
           default:
               System.out.println("L'Opció no és vàlida");
            }
        //Guardar dades al fitxer
        String liniaVella = list.get(j-1);
        puntuacions[j-1][0] = puntuacions[j-1][1]+puntuacions[j-1][2]+puntuacions[j-1][3];
        String liniaNova=equips[j-1]+','+puntuacions[j-1][0]+','+puntuacions[j-1][1]+','+puntuacions[j-1][2]+','+puntuacions[j-1][3];
        String textnou = textvell.replaceAll(liniaVella, liniaNova);
        FileWriter writer = new FileWriter("puntuacio.txt");
        writer.write(textnou);
        writer.close();
        System.out.println("Dades actualitzades.");
    }
    
    //Visualitzar les dades del líder i del cuer de la lliga
    private static void visualDadesLiderCuer() throws IOException {
        Scanner fileScanner;
        fileScanner = new Scanner(new File("punts_total.txt"));
        int max = fileScanner.nextInt();
        int min = fileScanner.nextInt();
        while (fileScanner.hasNextInt()) {
            int num = fileScanner.nextInt();
            if (num > max) {
                max=num;
            }
        }
        System.out.println("L'equip líder té: " + max + " Punts.");
        Scanner fileScanner2;
        fileScanner2 = new Scanner(new File("punts_total.txt"));
        while (fileScanner2.hasNextInt()) {
            int num = fileScanner2.nextInt();
            if (num < min) {
                min=num;
            }
        }
        System.out.println("L'equip cuer té: " + min + " Punts.");
        fileScanner2.close();
    }
}