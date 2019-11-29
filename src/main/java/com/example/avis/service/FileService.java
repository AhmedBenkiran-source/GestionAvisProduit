package com.example.avis.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileService {

	private static String UPLOADED_FOLDER = "/resources/images/";

	public ArrayList<String> multiFileUpload(MultipartFile[] files) throws IOException {
		
		ArrayList<String> ListeUrl = new ArrayList<>();

		for (MultipartFile file : files) {
			ListeUrl.add(singleFileUpload(file));

		}

		return ListeUrl;
	}

	public String singleFileUpload(MultipartFile file) throws IOException {

		byte[] bytes = file.getBytes();
		Path path = Paths.get(context.getRealPath(UPLOADED_FOLDER) + file.getOriginalFilename());
		String url =  UPLOADED_FOLDER + path.getFileName() ;
		System.out.println("--------------------------------" + url);

		Files.write(path, bytes);

		return url;
	}

	@Autowired
	ServletContext context;

	public ResponseEntity<List<String>> getImages(String UrlImage) {
		List<String> images = new ArrayList<String>();
		String filesPath = context.getRealPath(UPLOADED_FOLDER);
		File fileFolder = new File(filesPath);
		if (fileFolder != null) {
			for (final File file : fileFolder.listFiles()) {

				if ((file.toString()).equals(UrlImage)) {
					if (!file.isDirectory()) {

						String encodeBase64 = null;
						try {
							String extension = FilenameUtils.getExtension(file.getName());
							FileInputStream fileInputStream = new FileInputStream(file);
							byte[] bytes = new byte[(int) file.length()];
							fileInputStream.read(bytes);
							encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							images.add("data:images/" + extension + ";base64," + encodeBase64);

							fileInputStream.close();
						} catch (Exception e) {
						}
						return new ResponseEntity<List<String>>(images, HttpStatus.OK);

					}
				}

			}
		}
		return null;
	}
}
