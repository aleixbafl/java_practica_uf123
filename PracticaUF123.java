package practicauf123;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class PracticaUF123 {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner teclat = new Scanner (System.in);
        
        
        File file1 = new File("puntuacio.txt");
        if (file1.createNewFile()){
        }
        
        File file2 = new File("equips.txt");
        file2.delete();
        File file3 = new File("punts_total.txt");
        file3.delete();
        
        
        boolean sortir=false;
        do{
            //Menú del programa
            System.out.println("╔════════════════════════════════════════════════════╗");
            System.out.println("║Menú programa d'Equips                                                              ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║1. Visualitzar els equips i les puntuacions                                         ║");
            System.out.println("║2. Afegir un nou equip amb les seves puntuacions                                    ║");
            System.out.println("║3. Modificar les dades d’un equip                                                   ║");
            System.out.println("║4. Visualitzar les dades del líder i del cuer                                       ║");
            System.out.println("║5. Sortir                                                                           ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
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
        //Lleguir i imprimir per pantalla el que conte
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
            int puntuacionstotal = (puntuacions[i][1]*3)+(puntuacions[i][3]*1); 
            System.out.println("PJ: "+puntuacions[i][0]+"  PG: "+puntuacions[i][1]+"  PE: "+puntuacions[i][2]+"  PP: "+puntuacions[i][3]+"  Punts: "+puntuacionstotal);
            //puntuacions[i][4]
            
            BufferedWriter bw;
            bw = new BufferedWriter (new FileWriter ("equips.txt", true));
            bw.write (equips[i] + ",");
            bw.flush();
            bw.close();
            //puntuacionstotal
            BufferedWriter bw2;
            bw2 = new BufferedWriter (new FileWriter ("punts_total.txt", true));
            bw2.write (puntuacionstotal + ",");
            bw2.flush();
            bw2.close();
        }
    }
    
    //Afegir nou equip amb les seves puntuacions
    private static void afegirNouEqui() throws IOException {
        BufferedWriter bw;
        //Indicar quin fitxer s'ha d'escriure
        bw = new BufferedWriter (new FileWriter ("puntuacio.txt", true));
        Scanner teclat = new Scanner(System.in);
        //Camps on s'introdueixen les dades
        System.out.println("Afegir nou equip:");
        System.out.println("Equip:");
        String equip = teclat.nextLine();
        System.out.println("Partits Jugats:");
        String pj = teclat.nextLine();
        System.out.println("Partits Guanyats:");
        String pg = teclat.nextLine();
        System.out.println("Partits Empatats:");
        String pe = teclat.nextLine();
        System.out.println("Partits Perduts:");
        String pp = teclat.nextLine();
        PreparedStatement sentencia = null;
        //Escriu al fitxer indicat les dades introduïdes anteriorment
        bw.write (equip + "," + pj + "," + pg + "," + pe + "," + pp);
        bw.newLine();
        bw.flush();
        bw.close();
    }
    
    //Modificar les dades d'un equip, nom i/o puntuacio
    private static void modiDadesEqui() throws  IOException {
        
    }
    
    //Visualitzar les dades del líder i del cuer de la lliga
    private static void visualDadesLiderCuer() throws IOException {
        File file1 = new File("equips.txt");
        BufferedReader content1 = new BufferedReader(new FileReader(file1));
        String linia_arxiu1 = content1.readLine();
        String[] valors1 = linia_arxiu1.split(",", 0);
        File file2 = new File("punts_total.txt");
        BufferedReader content2 = new BufferedReader(new FileReader(file2));
        String linia_arxiu2 = content2.readLine();
        String[] valors2 = linia_arxiu2.split(",", 0);
        System.out.println(linia_arxiu1 + "\n" + linia_arxiu2);
    }
}