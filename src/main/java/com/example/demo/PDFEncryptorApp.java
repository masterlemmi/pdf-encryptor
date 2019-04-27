package com.example.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

@SpringBootApplication
public class PDFEncryptorApp {

	private static final String ENCRYPTED_DIR = "./encrypted/";

	/**
	 *
	 * @param args [-p password] [-f Pdf file(.pdf]
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {


		if(args == null || args.length != 4){
			throw new IllegalArgumentException("Please follow -p [password] -f \"filename.pdf\" format!");
		}

		SpringApplication.run(PDFEncryptorApp.class, args);

		File filename = null;
		String password = null;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
				case "-p":
					password = args[++i];
					break;
				case "-f":
					filename = new File(args[++i]);
					validateFile(filename);
					break;
			}
		}

		encrypt(filename, password);

	}

	private static void validateFile(File file) throws FileNotFoundException {
		if (!file.getName().toUpperCase().endsWith(".PDF")){
			throw new IllegalArgumentException("Only pdf files are allowed");
		}
		if (!file.exists()){
			throw new FileNotFoundException("Cannot locate file " + file.getAbsolutePath());
		}
	}

	public static void encrypt(File pdfFile, String password) throws Exception {
		PDDocument document = PDDocument.load(pdfFile);
		AccessPermission ap = new AccessPermission();
		StandardProtectionPolicy spp = new StandardProtectionPolicy(password, password, ap);
		spp.setEncryptionKeyLength(128);
		spp.setPermissions(ap);
		document.protect(spp);

		if(!new File(ENCRYPTED_DIR).exists()){
			new File(ENCRYPTED_DIR).mkdir();
		}

		File encrypted = new File(ENCRYPTED_DIR + pdfFile.getName());
		encrypted.createNewFile();

		document.save(encrypted);
		document.close();
	}

}