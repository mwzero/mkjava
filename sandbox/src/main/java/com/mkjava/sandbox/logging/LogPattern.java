package com.mkjava.sandbox.logging;

import org.apache.log4j.MDC;
import org.apache.log4j.NDC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 
 * Why? 
 * 
 * Web applications or Microservices apppications have to deal with multiple clients simultaneously; those applications are
 * designed and developed as "multi-thread" application to serve multiple requests; 
 * 
 * this makes difficult to debug the application or to make a log analysis from business perspective.
 * 
 * Many logging frameworks allow to push and pull data on the thread context; so each single line of logs is enriched with 
 * useful information for the log analysis, diagnosing problems or application tracing.
 * 
 * The Thread context is manageable both as a map and as a stack:
 * - NDC: Nested Diagnostic Context; the stack;
 * - MDC: Mapped Diagnostic Context; the map;
 * 
 *  NDC and MDC are not supported in SLF4J (but included in slf4j-ext module) but supported by log4j and jBoss logging.
 *  
 *  The Log4j PatternLayout print the contents of the ThreadContext Map and Stack:
 *  %X: full contents of the Map
 *  %X{key}: include the specified key.
 *  %x: full contents of the Stack.
 *  
 *  ## Log4j 2 API
 *  
 *  Log4j 2 merges NDC and MDC into a single [Thread Context](https://logging.apache.org/log4j/2.x/manual/thread-context.html).
 *  
 *  
 *  MDC:
	 	ThreadContext.put("mdcKey1", value4Key1); 
		ThreadContext.put("mdcKey2", value4Key2);
		logger.debug("Message");
		ThreadContext.clear();
	 
	NDC:
		ThreadContext.push("mdcKey1=value4Key1");
		ThreadContext.push("mdcKey2=value4Key2");
		ThreadContext.pop();
		ThreadContext.pop(); 
		ThreadContext.clear();
 *  
 *  
*/
public class LogPattern {
	
	private static final Logger log = LoggerFactory.getLogger(LogPattern.class);
	
	public void mdc() {
		
 		MDC.put("mdcKey1", "value4Key1");
 		MDC.put("mdcKey2", "value4Key2");
		log.debug("mdc logging");
	}
	
	public void ndc() {
		
		NDC.push("mdcKey1=value4Key1");
		NDC.push("mdcKey2=value4Key2");
		log.debug("ndc logging");
		NDC.pop();
		NDC.pop();
		
		NDC.clear();
	}
	
	public static void main(String[] args) {
        new LogPattern().mdc();
        new LogPattern().ndc();
    }

}
