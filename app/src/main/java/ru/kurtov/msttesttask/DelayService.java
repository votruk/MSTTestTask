package ru.kurtov.msttesttask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DelayService extends Service {
	private int mDelay;
	private double mFirstOperand;
	private double mSecondOperand;

	final static String MY_ACTION = "MY_ACTION";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mDelay = intent.getIntExtra("DELAY", 1);
		mFirstOperand = intent.getDoubleExtra("FIRST", 0);
		mSecondOperand = intent.getDoubleExtra("SECOND", 0);
		MyThread myThread = new MyThread();
		myThread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	public class MyThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			for(int i=0; i<10; i++){
				try {
					Thread.sleep(mDelay*1000);
					Intent intent = new Intent();
					intent.setAction(MY_ACTION);

					double result = mFirstOperand + mSecondOperand;
					intent.putExtra("DATAPASSED", result);
					intent.putExtra("FIRST_OP", mFirstOperand);
					intent.putExtra("SECOND_OP", mSecondOperand);

					sendBroadcast(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}


			stopSelf();
		}

	}

}
