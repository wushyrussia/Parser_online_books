package windows;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Packard Bell on 24.09.2018.
*/
public class BookW {
    String bid="1221";
    String numberOfPages;
    String fullBook;
    String nameOfBook;
    Document pageBook;
    Document book;
    String pageBuff;


    public BookW() throws IOException {
        connectTofirstPage();
        getAuthor();
        getNumberOfPages();


        // getBook();
        cleanDocument();
      //  System.out.println(nameOfBook);



    }

    private void connectTofirstPage() throws IOException {
        System.out.println("connect");
        pageBook = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
                .data("mod", "read_book")
                .data("bid", bid)
                .data("sym", "9000")
                .userAgent("Mozila")
                .post();
        // get the Number of pages in the book information page
    }


    private void connectToPage(String numberOfPages) throws IOException {
        System.out.println("connect");
        pageBook = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
                .data("mod", "read_book")
                .data("bid", bid)
                .data("sym", "9000")
                .data("page",numberOfPages)
                .userAgent("Mozila")
                .post();
        // get the Number of pages in the book information page
    }


    private void getNumberOfPages() {

        pageBuff = String.valueOf(pageBook);//Document to string
        String str[] = pageBuff.split("\n");//split the document into lines - each line in the cell
        pageBuff = str[str.length - 32];//the line contains the number of pages
        Scanner nScann = new Scanner(pageBuff);
        pageBook = Jsoup.parse(nScann.next()+nScann.next()+nScann.next(), "utf-8");
        numberOfPages=pageBook.text(); //text without HTML code
        numberOfPages = numberOfPages.substring(0,numberOfPages.length()-1);//only number page
    }
    private void getBook() throws IOException {
        for(int i=1;i!=Integer.valueOf(numberOfPages);i++){
            connectToPage(String.valueOf(i));


        }
    }
    private void cleanDocument(){
        pageBuff = String.valueOf(pageBook);//Document to string
        String str[] = pageBuff.split("\n");//split the document into lines - each line in the cell
        for(int i=17;i<str.length-40;i++){
            fullBook = fullBook+str[i]+"\n";
        }
    }

    private void getAuthor() throws IOException {
        pageBuff = String.valueOf(pageBook);//Document to string
        String str[] = pageBuff.split("\n");//split the document into lines - each line in the cell
        // Надо получить автора и название книги
    }
}