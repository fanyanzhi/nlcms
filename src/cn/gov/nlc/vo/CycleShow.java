package cn.gov.nlc.vo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.context.SmartLifecycle;

public class CycleShow implements SmartLifecycle{

	@Override
	public void start() {
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				System.out.println(1111);
			}
			
		}, 1, 1, TimeUnit.SECONDS);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
		
	}


}
