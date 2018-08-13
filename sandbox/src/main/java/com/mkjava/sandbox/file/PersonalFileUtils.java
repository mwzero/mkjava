package com.mkjava.sandbox.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonalFileUtils {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
	
	public void listFiles(String folderPath, String[] extensions) {
		
		File file = new File(folderPath);
		String[] files = file.list(new  FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				
				String extension = FilenameUtils.getExtension(name);
				
				//How test if an array contains a certain value?
				return Arrays.asList(extensions).contains(extension);
			}
		});
		
		for ( String fileName : files ) {
			
			
			File file2Move = new File(folderPath + File.separator + fileName);
			
			String newFileName = fileName.replace("[DivX.Ita].", "");
			File file2Rename = new File("F:\\movies2\\" + newFileName);
			
			//How move a file from one location to another?
			try {
				FileUtils.moveFile(file2Move, file2Rename);
				//Files.move(Paths.get(file2Rename), Paths.get(file2Rename), StandardCopyOption.REPLACE_EXISTING);
				//file2Move.renameTo(file2Rename)
				log.error("file [{}] renamed to [{}] correctly.", fileName, newFileName);
			} catch ( IOException err) {
				log.error("file [{}] not renamed to [{}] correctly. Err [{}]", fileName, newFileName, ExceptionUtils.getRootCauseMessage(err));
			}
		}
	}
	
	public static void main(String[] args) {
		
        new PersonalFileUtils().listFiles("F:\\movies", new String[] {"avi", "mp4"});
        
        
        
    }
}
