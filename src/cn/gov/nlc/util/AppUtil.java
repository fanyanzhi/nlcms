package cn.gov.nlc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppUtil implements ApplicationContextAware {

	private static ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		setAppContext(applicationContext);
	}

	public static Object getBean(String beanId) {
		return appContext.getBean(beanId);
	}

	public static ApplicationContext getAppContext() {
		return appContext;
	}

	public static void setAppContext(ApplicationContext appContext) {
		AppUtil.appContext = appContext;
	}
}
