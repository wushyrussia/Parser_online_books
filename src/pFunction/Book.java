package pFunction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public class Book
{
	
	public static void createBookMain() throws Exception{
		
		Scanner bidIn = new Scanner(System.in);
		System.out.print("Set the BID of book:");
	String bid = bidIn.next();
	int page1 = 0;
	
		//set of bid
		System.out.println("get page");
		page1 = get1Page(bid); // get the number of pages in the book
		System.out.println("download");
		downLoadBook(bid,page1); // download book to bufer
		System.out.println("finished");
		
	}
	
	private static int get1Page(String bid) throws Exception{
		
		int page = 0;
		
		//Scanner scan = new Scanner(System.in);
	//	bid = scan.next();
		//get thirst page
		System.out.println("connect");
		Document pageBook = Jsoup.connect("http://www.ka4ka.ru/lib/index.php?")
		.data("mod","")
		.data("bid",bid)
		.data("sym","9000")
		.userAgent("Mozila")
		.post();
		// get the Number of pages in the book information page
		System.out.println("scanner");
		int ch = 0;
		Scanner input = new Scanner(String.valueOf(pageBook));
		while(input.hasNextLine()){
			String data = input.nextLine();
			int count = 0;
			if(data.startsWith("//")){
				
			 count++;
			}
			ch++;
			 }
			 ch = ch - 32;

		FileWriter writer = new FileWriter("bufer", false);
		writer.write(String.valueOf(pageBook));
		writer.flush();
		writer.close();
			String line32 = Files.readAllLines(Paths.get("bufer")).get(ch);
			System.out.println("get page finished - download");
		System.out.println(line32);
			Document pg = new Document(line32);//jsoup 
			page = Integer.parseInt(pg.text()
			.substring(0, pg.text()
			.length() - 1));
			
		return page;
	}
	
	private static void downLoadBook(String bid,int page) throws IOException{
		
		for (int numb =1; numb <= page; numb++){
		Document DBook = Jsoup.connect("http://www.ka4ka.ru/lib/index.php?")
		.data("mod","read_book")
		.data("bid", bid)
		.data("sym","9000")
		.data("page",String.valueOf(numb))
		.userAgent("Mozila")
		.post();
		
		String str;
		
			Scanner input = new Scanner(String.valueOf(DBook));
			int ch = 0;
			while(input.hasNextLine()){
				String data = input.nextLine();
				int count = 0;
				if(data.startsWith("//")){

					count++;
				}
				ch++;
			}


			BufferedReader br = new BufferedReader(new FileReader(String.valueOf(DBook)));
			BufferedWriter bw = new BufferedWriter(new FileWriter("bufer",true));//открываем для записи файл книги
		// 40 строк в коде - текст, не относящийся к книге. line - все строки страницы минус 40 лишних
            for(int i = 1; i != ch-40; i++){ // пока мы не дойдем до строки, где начинаеться лишние 40 строк
			if (i<16){
             int x = 0;	
			}else{ 
			if (i == ch-39){
				str=br.readLine();
				File string = new File(str);
				Document setBook = Jsoup.parse(string,"UTF-8");
				str = setBook.text();
				bw.write(String.valueOf(setBook.text())+'\n');
			   }else{
			str=br.readLine();
		    File string = new File(str);
			Document setBook = Jsoup.parse(string,"UTF-8");
            bw.write(String.valueOf(setBook.text())+'\n');//построчно записываем книгу в файл
			double persent = page/100;
			persent = numb/persent;
			     System.out.println(persent + "%");
			}
			}
        }
        br.close();
        bw.close();
		}
		
		
	}
	
	private static void getBook(){}
	
	private static void setAthor(){}
	
	private static void setNameBook(){}
	
}
