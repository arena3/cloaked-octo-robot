package com.arena3.cor.PhotoSlide;

import android.os.Handler;

public class ThreadRunAndBlock {

	Handler handler;
	Runnable actionRunnable;
	ThreadRunAndBlockAction action;
	
	public ThreadRunAndBlock(Handler handler, ThreadRunAndBlockAction action) {
		this.handler = handler;
		this.action = action;
	}
	
	public void runAndBlock() {
		
		actionRunnable = new Runnable() {
			
			@Override
			public void run() {
				synchronized (actionRunnable) {
					action.runAction();
					actionRunnable.notify();
				}
				
			}
		};
		
		synchronized (actionRunnable) {
			if ( handler.post(actionRunnable) == true)
			{
			
				try {
					actionRunnable.wait(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					throw new Exception("Failed to most runnable");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
