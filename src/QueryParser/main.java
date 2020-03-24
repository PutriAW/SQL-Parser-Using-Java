/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueryParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author PUTRI
 */
public class main {

    /**
     * @param tab
     * @param kata
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static boolean cekAttr(String[][] tab, String kata, int x){
        boolean cek = false;
        int i = 0;
        while(i < tab[x].length){
            if (kata.equals(tab[x][i])) {
                cek = true;
            }
            i++;
        }
        return cek;
    }
    
    public static boolean cekAttrJoin(String[][] tab, String kata, int x){
        boolean cek = false;
        int i = 0;
        while(i < tab[x].length && cek == false){
            if (kata.equals(tab[x][i])) {
                cek = true;
            }
            i++;
        }
        return cek;
    }
    
    public static int cekPosTable(String[][] tab, String kata){
        boolean cek = false;
        int i = 0;
        while(i < tab.length && cek == false){
            if (tab[i][0].equals(kata)){
                cek = true;
            }
            i++;
        }
        return i-1;
    }
    
    
    public static String splitUsing(String word){
        String[] gun = word.split("");
        String result = "";
        int i=0;
        while (i < 5){
            result += gun[i];
            i++;
        }
        return result;
    }
    
    public static String getValJoin(String word){
        String[] gun = word.split("");
        String result = "";
        int i= 6;
        while (i < word.length()-2){
            result += gun[i];
            i++;
        }
        return result;
    }
    
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        System.out.println("##QUERY PARSER ");
        System.out.println("##MADE BY");
        System.out.println("##Putri Apriyanti Windya");
        System.out.println("##Mar Ayu Fotina");
        System.out.println("##Hema Ditania");
        System.out.println("##Geyanissa Wanadyawati");
        System.out.println("");
        
        //read data from a file
        String data = "E:\\files\\Teknik Informatika\\semester 4\\SBD\\Tubes SBD\\data.txt";
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        String line = null;
        String[] res = new String[7];
        String[][] tabel = new String[4][7];
            
        try {
            String text = null;
         
            reader = new BufferedReader(new FileReader(data));
            int i = 0, j = 0;
            while((text=reader.readLine()) != null)
            {
                content.append(text).append("\n"); 
                res = text.split(";");
                while (j < tabel[i].length) {
                    tabel[i][j] = res[j];
                    j++;   
                }
                j = 0;
                i++;
            }
        } 
        catch(FileNotFoundException e)
        { 
            System.out.println("File tidak ditemukan");
        }  
        finally
        {
            try
            {
               if(reader != null)
                   reader.close();
             }
            catch(IOException ioe)
            {
                System.out.println("ioe");
            }        
        }
        
        //SQL Statement
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan SQL Statement: ");
        String statement = sc.nextLine();
        System.out.print("");
        
        //check the statement and split it
        if (statement.contains(";")){;
            String[] word = statement.split(" ");
            if (word[0].equals("SELECT")|| word[0].equals("Select")|| word[0].equals("select")){
                String[] attr = word[1].split(",");
                int k = 0, count = 0;
                boolean ckAtr = false;
                while(k < attr.length){
                    int j = 0;
                    while(j < 4 ){
                        int i = 0 ;
                        while(i < tabel[j].length){
                           if(attr[k].equals(tabel[j][i])){
                               count +=1;
                           }
                           i++;
                       } 
                       j++;
                    }
                    k++;
                }
                if ((count-1) == attr.length){
                    if (word[2].equals("FROM") || word[2].equals("From") || word[2].equals("from")){
                        if (word[3] != null) {
                            if (!word[3].contains("Buku") && !word[3].contains("Peminjaman") 
                                    && !word[3].contains("Anggota") && !word[3].contains("Pustakawan")) {
                                System.out.println("Table Not Found");
                            }   	
                        }

                        if(word[4] != null && word[4].equals("JOIN") || word[4].equals("Join") || word[4].equals("join")){
                            String hasil = splitUsing(word[6]);
                            if(hasil.equals("USING") || hasil.equals("Using") || hasil.equals("using")){
                                String hasilj = getValJoin(word[6]);
                                int pos1 = cekPosTable(tabel,word[3]);
                                int pos2 = cekPosTable(tabel,word[5]);
                                boolean cek1 = cekAttrJoin(tabel,hasilj,pos1);
                                boolean cek2 = cekAttrJoin(tabel,hasilj,pos2);
                                if (cek1 == cek2){
                                    System.out.println("Table: "+tabel[pos1][0]);
                                    System.out.println("List Kolom: ");
                                    for (String attr1 : attr) {
                                        boolean cekAtr = cekAttr(tabel, attr1, pos1);
                                        if (cekAtr) {
                                            System.out.println(">>" + attr1);
                                        }
                                    }
                                    System.out.println("");
                                    System.out.println("Table: "+tabel[pos2][0]);
                                    System.out.println("List Kolom: ");
                                    for (String attr1 : attr) {
                                        boolean cekAtr = cekAttr(tabel, attr1, pos2);
                                        if (cekAtr) {
                                            System.out.println(">>" + attr1);
                                        }
                                    }
                                }else{
                                    if (cek1 == false){
                                        System.out.println("SQL ERROR "+hasilj +" NOT FOUND IN "+tabel[pos1][0]);
                                    }else{
                                        System.out.println("SQL ERROR "+hasilj +" NOT FOUND IN "+tabel[pos2][0]);
                                    }
                                }
                            }
                        }
                    }else{
                       System.out.println("SQL ERROR (Syntax Error: Missing or misspelled From)"); 
                    }
                }else{
                    System.out.println("SQL ERROR Atribut Name Unidentified");
                }
            }else{
                    System.out.println("SQL ERROR (Syntax Error: Missing or misspelled Select)");
            }
        }else{
            System.out.println("SQL ERROR ; Tidak ditemukan");
        }
    } 
    
}

