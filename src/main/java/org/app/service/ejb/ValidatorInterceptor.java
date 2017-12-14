package org.app.service.ejb;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ValidatorInterceptor {
	private static Logger logger = Logger.getLogger(ValidatorInterceptor.class.getName());
	
	@AroundInvoke
	private Object validationCheck(InvocationContext ctx) throws Exception{
		 // Decode invocation context info
	    Class targetBeanClass = ctx.getTarget().getClass();
	    String invokedMethodName = ctx.getMethod().getName();

	    logger.info("#### call of: " + targetBeanClass.getName() + "." + invokedMethodName);
		Object[] parameters = ctx.getParameters();
		Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();
		Integer position = 0;
		for(Object prm: parameters){
			position++;
			logger.info("DEBUG: intercepted parameter " + position +" ==== " + prm);
			parameterMap.put(position, prm);
		}		
	    // Execute validation aspect logic
		if(parameterMap.get(1) == null || parameterMap.get(1).equals(0))
			throw new Exception("Validation Error: Parameter invalid value: " + parameterMap.get(1));
		
		return ctx.proceed();
	}
}
