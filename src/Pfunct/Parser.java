package Pfunct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import java.io.*;


public class Parser {
    public static void M_Cparser() throws Exception {
        String bid;
        System.out.println("Vvedite nomer knigi:");
        Scanner in = new Scanner(System.in);
        bid = in.next();
        //connect and download html document

        Document doc = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
                .data("mod", "read_book")
                .data("bid",bid)
                .data("sym", "9000")
                    // and other hidden fields which are being passed in post request.
                .userAgent("Mozilla")
                .post();
        System.out.println(doc);

        // количество страниц в книге
        int page =  M_LineCounter(doc);

      M_Pars_book(page,bid);



    }
                //нахождение количества строк в документе
       private static int M_LineCounter(Document doc) throws Exception {
        //get the number of lines
        int lineCount1 = 0, commentsCount1 = 0;

        Scanner input1 = new Scanner(String.valueOf(doc));
        while (input1.hasNextLine()) {
            String data1 = input1.nextLine();

            if (data1.startsWith("//")) commentsCount1++;

            lineCount1++;
        }
        System.out.println("Line Count: " + lineCount1 + "\t Comments Count: " + commentsCount1);

           return M_Writer(doc, lineCount1);
    }
   private static int M_Writer(Document doc, int lineNumber) throws Exception {
       //записываем документ в буферный файл
       int q;

       try {
           FileWriter writer = new FileWriter("bufer", false);

           // запись всей строки
           writer.write(String.valueOf(doc));
           // запись по символам

           writer.flush();
           writer.close();

           FileWriter writer1 = new FileWriter("bufer1", false);

           //записали строку с количеством страниц в буферный файл
           lineNumber = lineNumber - 32; //get the number string with number page
           String line32 = Files.readAllLines(Paths.get("bufer")).get(lineNumber);
           writer1.write(String.valueOf(line32));
           String pa = (String.valueOf(line32));
           System.out.println(pa + "  test");

           writer1.flush();
           writer1.close();


           //получаем количество страниц
           File input = new File("bufer1");
           Document pg = Jsoup.parse(input, "utf-8");
           //удаляем лишний символ в строке с количеством страниц и записываем число страниц в переменную
           try {
               q = Integer.parseInt(pg.text().substring(0, pg.text().length() - 1));
               // q - число страниц в книге
               System.out.println(q);
               return q;
           } catch (Exception e) {
               System.out.println("нет такой записи");
           }
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("Нет такой книги");

       } return 0;
   }



    
    
   private static void M_Pars_book(int page,String bid) throws Exception { //загружаем книгу постранично

       try {


           for (int nomb = 1; nomb<=page; nomb++) {
               System.out.println("бууки");
                   Document doc2 = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
                           .data("mod", "read_book")
                           .data("bid", bid)
                           .data("sym", "9000")
                           .data("page", String.valueOf(nomb))
                           // and other hidden fields which are being passed in post request.
                           .userAgent("Mozilla")
                           .post();

               M_Add_pg_to_File(doc2);

           }

           File input = new File("bufer3");
           Document setBook = Jsoup.parse(input,"UTF-8");
           String nameBook = "book";
           FileWriter fWriter = new FileWriter(nameBook+".txt", true);
           fWriter.write(String.valueOf(setBook.text()));
           fWriter.flush();

       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    private static void M_Add_pg_to_File(Document doc2) throws Exception {
        int line = M_LineCounterB(doc2)-40;
        System.out.println("M_Add_pg_to_File" + line );


        FileWriter writer1 = new FileWriter("bufer2", false); //запись текушщей страницы в буфер
        writer1.write(String.valueOf(doc2));
        writer1.flush();
        writer1.close();




        BufferedReader br = new BufferedReader(new FileReader("bufer2"));  //читаем буфер
        BufferedWriter bw = new BufferedWriter(new FileWriter("bufer3",true));//открываем для записи файл книги
        String str;
        System.out.println(line);
         // 40 строк в коде - текст, не относящийся к книге. line - все строки страницы минус 40 лишних
        for(int i = 1; i != line; i++){ // пока мы не дойдем до строки, где начинаеться лишние 40 строк
            str=br.readLine();
            bw.write(str+'\n');//построчно записываем книгу в файл
        }
        br.close();
        bw.close();


    }
 public static void M_clear(){
     try {
         FileWriter fstream1 = new FileWriter("bufer");// конструктор с одним параметром - для перезаписи
         BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
         out1.write(""); // очищаем, перезаписав поверх пустую строку
         out1.close(); // закрываем
     } catch (Exception e)
     {System.err.println("Error in file cleaning: " + e.getMessage());}
     try {
         FileWriter fstream1 = new FileWriter("bufer1");// конструктор с одним параметром - для перезаписи
         BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
         out1.write(""); // очищаем, перезаписав поверх пустую строку
         out1.close(); // закрываем
     } catch (Exception e)
     {System.err.println("Error in file cleaning: " + e.getMessage());}
     try {
         FileWriter fstream1 = new FileWriter("bufer2");// конструктор с одним параметром - для перезаписи
         BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
         out1.write(""); // очищаем, перезаписав поверх пустую строку
         out1.close(); // закрываем
     } catch (Exception e)
     {System.err.println("Error in file cleaning: " + e.getMessage());}
     try {
         FileWriter fstream1 = new FileWriter("bufer3");// конструктор с одним параметром - для перезаписи
         BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
         out1.write(""); // очищаем, перезаписав поверх пустую строку
         out1.close(); // закрываем
     } catch (Exception e)
     {System.err.println("Error in file cleaning: " + e.getMessage());}
 }
    private static int M_LineCounterB(Document doc) throws Exception {
        //get the number of lines
        int lineCount = 0, commentsCount = 0;

        Scanner input = new Scanner(String.valueOf(doc));
        while (input.hasNextLine()) {
            String data = input.nextLine();

            if (data.startsWith("//")) commentsCount++;

            lineCount++;
        }System.out.println("Line Count: " + lineCount + "\t Comments Count: " + commentsCount);

        return lineCount;
    }
       }


