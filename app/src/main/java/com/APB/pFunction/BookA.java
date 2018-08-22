// class for android
package pFunction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.*;
//import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import android.util.*;
import android.widget.*;
import android.os.AsyncTask;
public class BookA
{
	

	public static void createBookMain(String bid) throws Exception{

		//Scanner bidIn = new Scanner(System.in);
		//System.out.print("Set the BID of book:");
	//	bid = bidIn.next();
		int page1 = 0;
		

		//set of bid
	//Toast.makeText(this,"get page");
	page1 = get1Page(bid); // get the number of pages in the book
		// System.out.println("download");
		//downLoadBook(bid,page1); // download book to bufer
	//	System.out.println("finished");

	}

	private static int get1Page(String bid) throws Exception{

		int page = 0;

		//Scanner scan = new Scanner(System.in);
		//	bid = scan.next();
		//get thirst page
	//	System.out.println("connect");
		Document pageBook = Jsoup.connect("http://ka4ka.ru/lib/index.php?")
			.data("mod","read_book")
			.data("bid",bid)
			.data("sym","9000")
			.userAgent("Mozila")
			.post();
		// get the Number of pages in the book information page
	//	System.out.println("scanner");
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

		FileWriter writer = new FileWriter("/storage/64D6-1231/AppProjects/Parser_online_books/src/buferN", false);
		writer.write(String.valueOf(pageBook));
		writer.flush();
		writer.close();
		 
		FileInputStream fs= new FileInputStream("/storage/64D6-1231/AppProjects/Parser_online_books/src/buferN");
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < ch; ++i){
			br.readLine();
			}
		String line32 = br.readLine();
		
	//	String line32 = Files.readAllLines(Paths.get("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer")).get(ch);
        FileWriter writerL = new FileWriter("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer3", false);
        writerL.write(String.valueOf(line32));
        writerL.flush();
        writerL.close();
	//	System.out.println("get page finished - download");
	//	System.out.println(line32);
       
		File buferInput = new File("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer3");
        Document pg = Jsoup.parse(buferInput, "utf-8");
		page = Integer.parseInt(pg.text()
								.substring(0, pg.text()
										   .length() - 1));
//        System.out.println("("+page+")" + "enter this number:");
        Scanner pageFix = new Scanner(System.in);
		String  page1 = pageFix.nextLine();
		return (page);
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

            FileWriter bookPagenumb = new FileWriter("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer1", false);
            bookPagenumb.write(String.valueOf(DBook));
            bookPagenumb.flush();
            bookPagenumb.close();

			BufferedReader br = new BufferedReader(new FileReader("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer1"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer2",true));//открываем для записи файл книги
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
					
					System.out.println(numb + " of " +page);

				}


			}
			br.close();
			bw.close();
		}
		File input = new File("/storage/64D6-1231/AppProjects/Parser_online_books/src/bufer2");
		Document setBook = Jsoup.parse(input,"UTF-8");
		FileWriter fWriter = new FileWriter("/storage/64D6-1231/Books/"+getBook()+".txt", true);
		fWriter.write(String.valueOf(setBook.text()));
		fWriter.flush();


	}



	private static String getBook() throws FileNotFoundException, IOException{
		String nameAuthor ="";
		FileInputStream fs= new FileInputStream("/storage/64D6-1231/AppProjects/Parser_online_books/src/buferN");
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < 16; ++i){
			br.readLine();
			}
		String line32 = br.readLine();
		nameAuthor = nameAuthor+line32+" ";
	//	br.readLine();
		line32 = br.readLine();
		nameAuthor = nameAuthor+line32;
		FileWriter writerL = new FileWriter("/storage/64D6-1231/AppProjects/Parser_online_books/src/buferN", false);
        writerL.write(String.valueOf(nameAuthor));
        writerL.flush();
        writerL.close();
		
		File buferInput = new File("/storage/64D6-1231/AppProjects/Parser_online_books/src/buferN");
        Document pg = Jsoup.parse(buferInput, "utf-8");
		nameAuthor = pg.text()
								.substring(0, pg.text()
										   .length());
		
		
	
		
		
		
		return nameAuthor;
	}

	private static void clearBufer(){
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
		try {
			FileWriter fstream1 = new FileWriter("buferN");// конструктор с одним параметром - для перезаписи
			BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
			out1.write(""); // очищаем, перезаписав поверх пустую строку
			out1.close(); // закрываем
		} catch (Exception e)
		{System.err.println("Error in file cleaning: " + e.getMessage());}
		
	}

	private static String consoleInfo(String text){
		
		return text;
	}

}
	
