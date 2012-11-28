package com.arena3.cor.PhotoSlide;

import android.app.Activity;

public class BlockingOnUIRunnable {

	public class Context {
		public Object data;
		
		public Context(Object data) {
			this.data = data;
		}
	}
	
	private Activity activity;
	private BlockingOnUIRunnableListener listener;
	private Runnable uiRunnable;
	
	public BlockingOnUIRunnable(Activity activity, BlockingOnUIRunnableListener listener) {
		this.activity = activity;
		this.listener = listener;
		
		this.uiRunnable = new Runnable() {
			
			@Override
			public void run() {
				if ( BlockingOnUIRunnable.this.listener != null) {
					BlockingOnUIRunnable.this.listener.onRunOnUIThread();
				}
				
				synchronized (this) {
					this.notify();
				}
			}
		};
	}
	
	public void startOnUIAndWait() {
		synchronized (this.uiRunnable) {
			this.activity.runOnUiThread(this.uiRunnable);
			try {
				this.uiRunnable.wait(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
