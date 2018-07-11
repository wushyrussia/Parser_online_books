package Pfunct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Scanner;

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
        lineCounter(doc);

    }
   //find count from html document
       private static void lineCounter(Document doc) throws Exception {

        int lineCount = 0, commentsCount = 0;

        Scanner input = new Scanner(String.valueOf(doc));
        while (input.hasNextLine()) {
            String data = input.nextLine();

            if (data.startsWith("//")) commentsCount++;

            lineCount++;
        }

     //   lineCount = lineCount-31;
        System.out.println("Line Count: " + lineCount + "\t Comments Count: " + commentsCount);
    }

}