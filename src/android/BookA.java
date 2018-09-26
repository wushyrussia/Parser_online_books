package android;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Packard Bell on 24.09.2018.
 */
public class BookA {
	private String bid="256850";
	private String url = "http://ka4ka.ru/lib/index.php?";
	private String filePath ="";
	private String numberOfPages;
	private String fullBook = "";
	private String nameOfBook;
	private Document pageBook;
	private Document book;
	private String pageBuff;


	public BookA() throws IOException {
		long startTime = System.currentTimeMillis();

		connectTofirstPage();
		getAuthor();
		getNumberOfPages();
		System.out.println(numberOfPages);
		getFullBook();
		writeFile();
		long timeSpent = System.currentTimeMillis() - startTime;
		System.out.println("программа выполнялась " + timeSpent + " миллисекунд");


	}

	private void connectTofirstPage() throws IOException {
		Scanner scanBid = new Scanner(System.in);
		System.out.println("Enter the Bid:");
		bid = scanBid.next();
		pageBook = Jsoup.connect(url)
				.data("mod", "read_book")
				.data("bid", bid)
				.data("sym", "9000")
				.userAgent("Mozila")
				.post();
		// get the Number of pages in the book information page
	}


	private void connectToPage(String numberOfPages) throws IOException {
		pageBook = Jsoup.connect(url)
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

	private void getFullBook() throws IOException {
		for(int i=1;i!=Integer.valueOf(numberOfPages);i++){
			connectToPage(String.valueOf(i));
			System.out.println("Get "+i+" of "+numberOfPages);
			cleanDocument();
		}
		connectToPage(numberOfPages); //убрать этот костыль
		cleanDocument();
	}

	private void cleanDocument(){ //get Text of the Book
		pageBuff = String.valueOf(pageBook);//Document to string
		String str[] = pageBuff.split("\n");//split the document into lines - each line in the cell
		for(int i=17;i<str.length-40;i++){
			cleanString(str[i]);
		}
	}

	private void getAuthor() throws IOException {
		pageBuff = String.valueOf(pageBook);//Document to string
		String str[] = pageBuff.split("\n");//split the document into lines - each line in the cell
		Document doc = Jsoup.parse(str[17]+str[18]);
		nameOfBook = doc.text();
	}

	private void cleanString(String str){ //clean html code
		book = Jsoup.parse(str);
		fullBook = fullBook+book.text()+"\n";
	}

	private void writeFile() throws IOException { //Create new file on disk
		FileWriter fWriter = new FileWriter(filePath+nameOfBook+".txt", true);
		fWriter.write(fullBook);
		fWriter.flush();
	}
}