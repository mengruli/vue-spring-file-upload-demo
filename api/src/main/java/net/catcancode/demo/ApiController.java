package net.catcancode.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class ApiController {
	
	@Value("${file.dir}")
	private String fileDir;
	
	@RequestMapping(value="/files", method=RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("files") MultipartFile[] files,
			@RequestParam("data") String json) throws IOException
	{
		
		// save files
		for(MultipartFile f : files)
		{
			
			// create new file
			String name = f.getOriginalFilename();
			File newFile = new File(fileDir + name);
			newFile.createNewFile();
			
			System.out.println("Writing file: " + fileDir + name);
			// write
			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(f.getBytes());
			fos.close();
		}
		
		//TODO: Deserialize the json string to whatever model it should map to
		System.out.println("Check what string you got...");
		System.out.println(json);
		
		
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
}
