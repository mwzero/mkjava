package com.mkjava.sandbox.tracing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ProbabilisticSampler;

import io.opentracing.Span;

public class OpentracingClient {


    private OpentracingClient() {
    }

    private void sayHello() throws InterruptedException {
    	
    	io.opentracing.Tracer tracer = new Configuration("INC3211459", 
    			new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
    			new Configuration.ReporterConfiguration(false, "192.168.99.100", 9412, 1000, 10000))
    	        .getTracer();
    	
    	Span span1 = tracer.buildSpan("create").start();
    	Thread.sleep(1000);
    	
    	Span span2 = tracer.buildSpan("saveDB").asChildOf(span1).start();
    	Thread.sleep(1000);
    	span2.finish();
    	
    	Span span3 = tracer.buildSpan("SLA").asChildOf(span1).start();
    	Thread.sleep(1000);
    	
    	Span span33 = tracer.buildSpan("CalculatingActions").asChildOf(span3).start();
    	Thread.sleep(2000);
    	span33.finish();
    	
    	span3.finish();
    	
    	Span span4 = tracer.buildSpan("approver").asChildOf(span1).start();
    	Thread.sleep(1000);
    	span4.finish();
    	
    	span1.finish();
    	
    }
    
    private void sayHelloToDB() throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection connection = null;
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/listfeeds", "feeds", "pwdfeeds");
    	String query = "insert into feeds (icon, type, source, title, latLong, description, address , url ,Date, city, guid) values (?, ?, ?, ?, point(?,?),?, ?, ?, ?, ?,?)";

 	    PreparedStatement preparedStmt = connection.prepareStatement(query);

	    preparedStmt.setInt(1, 1); 
	    preparedStmt.setString(2,""); 
	    preparedStmt.execute();
	    

    }
    	
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
        new OpentracingClient().sayHello();
        new OpentracingClient().sayHelloToDB();
    }
}
