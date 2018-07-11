package Pfunct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Scanner;

//import java.io.*;


public class Parser {
    public static void Cparser() throws Exception {
        //connect and download html document

        Document doc = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
.data("mod", "read_book")
                .data("bid","19942")
                .data("sym", "9000")
// and other hidden fields which are being passed in post request.
                .userAgent("Mozilla")
                .post();
        System.out.println(doc);
        M_LineCounter(doc);

    }
   //find count from html document
       private static void M_LineCounter(Document doc) throws Exception {
        //get the number of lines
        int lineCount = 0, commentsCount = 0;

        Scanner input = new Scanner(String.valueOf(doc));
        while (input.hasNextLine()) {
            String data = input.nextLine();

            if (data.startsWith("//")) commentsCount++;

            lineCount++;
        }

        System.out.println("Line Count: " + lineCount + "\t Comments Count: " + commentsCount);

        //   M_Writer(doc,lineCount);

     //      M_NumberPage(lineCount,doc);
    }
  /*  private static void M_NumberPage(int line, Document doco) throws Exception {
        line = line-31; //get the number string with number page
        BufferedReader br = new BufferedReader(doco);
        File f = new File("/root/2/Files.txt");
        BufferedReader fin = new BufferedReader(new FileReader(doco));
        String name;
        String lines;
        System.out.println("Print File "+f.getName()+"? y/n");
        name = br.readLine();
        if(name.equals("y"))
            while ((lines = fin.readLine()) != null) System.out.println(lines);
    }*/
   /* public static void M_Writer(Document doc, int lineNumber) throws IOException {

        try (FileWriter writer = new FileWriter("bufer", false)) {
            // запись всей строки
            writer.write(String.valueOf(doc));
            // запись по символам

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }
    public void  M_line(){


        }*/
}
