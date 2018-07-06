package com.mkjava.sandbox.logging;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPattern {
	
	private static final Logger log = LoggerFactory.getLogger(LogPattern.class);
	
	public void onlyOne() {
		
		MDC.put("identifyingToken", "TesOne");
		log.debug("TestLogPattern");
	}
	
	public static void main(String[] args) {
        new LogPattern().onlyOne();
    }

}
