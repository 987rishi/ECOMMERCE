package com.SpringEcom;

import java.io.IOException;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;

	//TO SEND STATUS CODES(HTTP) ALONG WITH DATA WE CAN ENCAPSULATE OUR DATA WITHEN A RESPONSE ENTITY OBJECT
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
	}

	public Product getProductById(int id) {
		
		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile image) throws IOException {
		product.setImageName(image.getOriginalFilename());
		product.setImageType(image.getContentType());
		product.setImageData(image.getBytes());
		repo.save(product);
		return repo.findById(product.getId()).orElse(null);
	}

	public Product updateProduct(Product product, MultipartFile image) throws IOException {
		return addProduct(product, image);
	}

	public Product deleteById(int id) {
		Product product=repo.findById(id).orElse(null);
		if(product!=null)	
			repo.deleteById(id);
		return product;
	}

	public List<Product> findAllByKeyword(String keyword) {
	
		return repo.findAllByKeyword(keyword);
	}
	
	
	
	
}
