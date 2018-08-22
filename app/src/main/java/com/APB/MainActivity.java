package com.APB;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import pFunction.*;

import static pFunction.BookA.createBookMain;


public class MainActivity extends Activity 
{
	
	EditText tvOut;
    Button btnOk;
    Button btnCancel;
	
	

  /*  @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);	
	// найдем View-элементы
    
		btnOk.setOnClickListener(this);
	EditText consoleEditText= (EditText) findViewById(R.id.mainEditText2);
	}

	
	// создаем обработчик нажатия
	OnClickListener oclBtnOk = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Меняем текст в TextView (tvOut)
			
			try
			{
			//	TxtBook.createBookMain(bid);
			}
			catch (Exception e)
			{}

		}
		};
		
	

	// присвоим обработчик кнопке OK (btnOk)
	// btnOk.setOnClickListener;
	
    */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// найдем View-элементы
		tvOut = (EditText) findViewById(R.id.mainEditText1);
		btnOk = (Button) findViewById(R.id.mainButton1);
		}

	public void onClick(View v) throws Exception {
		// по id определяем кнопку, вызвавшую этот обработчик
				String bidq = tvOut.getText().toString();

				EditText editText = (EditText)findViewById(R.id.mainEditText1);
			   MyTask bidTM = new MyTask();
			 bidTM.bidTask = bidq;
			 bidTM.doInBackground();
			 editText.setText("finished");
			   
				
		
	}
}//class 2 handle

class MyTask extends AsyncTask<Void, Void, Void> {
	public String bidTask;

	@Override
	protected Void doInBackground(Void... params) {

		BookA TxtBook = new BookA();
		try
		{
			TxtBook.createBookMain(bidTask);
		}
		catch (Exception e)
		{}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);


		//Тут выводим итоговые данные
	}
}
