package Pfunct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import java.io.*;


public class Parser {
    public static void Cparser() throws Exception {
        //connect and download html document

        Document doc = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
.data("mod", "read_book")
                .data("bid","246111")
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
        }System.out.println("Line Count: " + lineCount + "\t Comments Count: " + commentsCount);
           M_Writer(doc, lineCount);
    }
   private static void M_Writer(Document doc, int lineNumber) throws IOException {

        try (FileWriter writer = new FileWriter("bufer", false)) {
            // запись всей строки
            writer.write(String.valueOf(doc));
            // запись по символам

            writer.flush();

            lineNumber = lineNumber-32; //get the number string with number page

            String line32 = Files.readAllLines(Paths.get("bufer")).get(lineNumber);
            System.out.println(line32);


        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

}
