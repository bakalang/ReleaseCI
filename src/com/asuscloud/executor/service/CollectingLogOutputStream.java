package com.asuscloud.executor.service;

//import java.util.LinkedList;
//import java.util.List;
import org.apache.commons.exec.LogOutputStream;

public class CollectingLogOutputStream extends LogOutputStream {
//    private final List<String> lines = new LinkedList<String>();
    @Override protected void processLine(String line, int level) {
//    	if	(line.substring(0, 12).matches("^[a-z0-9]+"))
    		System.out.println(line);
//        lines.add(line);
    }   
//    public List<String> getLines() {
//        return lines;
//    }
}
