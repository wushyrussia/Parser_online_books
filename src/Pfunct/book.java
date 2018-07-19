package Pfunct;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
public class book
{
	public static void M_CreateBookMain() throws Exception{
		System.out.print("Set the BID of book:");
		String bid = null;
		int page = 0;
		//set of bid
		page = M_Get1Page(bid); // get the number of pages in the book
		M_DownLoadBook(bid,page); // download book to bufer 
		
	}
	private static int M_Get1Page(String bid) throws Exception{
		
		int page = 0;
		
		Scanner scan = new Scanner(System.in);
		bid = scan.next();
		//get thirst page
		Document pageBook = Jsoup.connect("http://www.ka4ka.ru/lib/index.php")
		.data("mod","")
		.data("bid",bid)
		.data("sym","9000")
		.userAgent("Mozila")
		.post();
		// get the Number of pages in the book information page
		int ch = 0;
		Scanner input = new Scanner(String.valueOf(pageBook));
		while(input.hasNextLine()){
			String data = input.nextLine();
			int count = 0;
			if(data.startsWith("//")) count++;
			
			ch++;
			 }
			 ch = ch - 32;
			String line32 = Files.readAllLines(Paths.get(String.valueOf(pageBook))).get(ch);
			
			Document pg = new Document(line32);//jsoup 
			page = Integer.parseInt(pg.text().substring(0, pg.text().length() - 1));
		return page;
	}
	private static void M_DownLoadBook(String bid,int page) throws IOException{
		Document DBook = Jsoup.connect("http://www.ka4ka.ru/lib/index.php")
		.data("mod","read_book")
		.data("bid", bid)
		.data("sym","9000")
		.userAgent("Mozila")
		.post();
		
	}
	private static void M_GetBook(){}
	private static void M_SetAthor(){}
	private static void M_SetNameBook(){}
	
}
