package com.example.api.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.api.model.Tarefa;

import jakarta.annotation.PostConstruct;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@SuppressWarnings("deprecation")
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public Map<String, String> uploadFile(MultipartFile multipartFile) throws IOException {
		String fileUrl = "";
		File file = convertMultiPartToFile(multipartFile);
		String fileName = generateFileName(multipartFile);
		fileUrl = endpointUrl + "/" + fileName;
		uploadFileTos3bucket(fileName, file);
		file.delete();
		Map<String, String> dadosArquivo = Map.of("fileUrl", fileUrl, "fileName", fileName);
		return dadosArquivo;
	}

	public String processaArquivo(MultipartFile multipartFile, Tarefa tarefa) {
		String fileUrl = "";
		try {
			Map<String, String> dadosArquivo = uploadFile(multipartFile);
			fileUrl = dadosArquivo.get("fileUrl");
			tarefa.setFotoS3(dadosArquivo.get("fileUrl"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileUrl;
	}


//	private void deleteFileFromS3Bucket(String fileName, File file) {
//		s3client.deleteObject(
//				new DeleteObjectRequest(bucketName, fileName));
//	}
}
