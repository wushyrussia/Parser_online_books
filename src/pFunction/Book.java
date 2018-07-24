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
		Document pageBook = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
		.data("mod","read_book")
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
        FileWriter writerL = new FileWriter("bufer", false);
        writerL.write(String.valueOf(line32));
        writerL.flush();
        writerL.close();
			System.out.println("get page finished - download");
		System.out.println(line32);
        File buferInput = new File("bufer");
        Document pg = Jsoup.parse(buferInput, "utf-8");
			page = Integer.parseInt(pg.text()
			.substring(0, pg.text()
			.length() - 1));
        System.out.println("("+page+")");
        Scanner pageFix = new Scanner(System.in);
       String  page1 = pageFix.nextLine();
		return Integer.parseInt(page1);
	}
	
	private static void downLoadBook(String bid,int page) throws IOException{
		
		for (int numb =1; numb <= page; numb++){
		Document DBook = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
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

            FileWriter bookPagenumb = new FileWriter("bufer1", false);
            bookPagenumb.write(String.valueOf(DBook));
            bookPagenumb.flush();
            bookPagenumb.close();

			BufferedReader br = new BufferedReader(new FileReader("bufer1"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("bufer2",true));//открываем для записи файл книги
		// 40 строк в коде - текст, не относящийся к книге. line - все строки страницы минус 40 лишних
            for(int i = 1; i != ch-42; i++){ // пока мы не дойдем до строки, где начинаеться лишние 40 строк
			if (i<17){
                str=br.readLine();
			}else {
			/*if (i == ch-40){
				str=br.readLine();
				bw.write(str+'\n');
			   }else{*/
                str = br.readLine();

                bw.write(str + '\n');//построчно записываем книгу в файл
                float persent = page / 100;
                System.out.println(persent + "procent");
                persent = numb / persent;
                System.out.println(persent + " of " +page);

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
