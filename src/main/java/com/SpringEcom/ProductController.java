package com.SpringEcom;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductService service;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts()
	{
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHH");
		return service.getAllProducts();
	}
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id)
	{
		Product product=this.service.getProductById(id);
		if(product!=null)
			return new ResponseEntity<Product>(product,HttpStatus.OK);
		else
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getProductImage(@PathVariable int productId)
	{
		System.out.println("INSIDE PRODUCT IMAGE-------------------");
		Product product=service.getProductById(productId);
		if(product.getImageData()==null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
	}
	
	@GetMapping("/product/search")
	public ResponseEntity<List<Product>> searchByKeyword(@RequestParam String keyword)
	{
		System.out.println("------------------INSIDE SEARCH");
		return new ResponseEntity<List<Product>>(service.findAllByKeyword(keyword),HttpStatus.OK);
	}
	
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imageFile)
	{
		Product updatedProduct;
		try 
		{
			updatedProduct=service.updateProduct(product,imageFile);
		}catch(IOException e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Product>(updatedProduct,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable int id)
	{
		if(service.getProductById(id)!=null)
			return new ResponseEntity<Product>(service.deleteById(id),HttpStatus.OK);
		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	//SENDING IMAGE FROM CLIENT TO SERVER(HAVE TO MAKE MODIFICATIONS TO THE PRODUCT CLASS {IMAGE NAME,IMAGE TYPE AND IMAGE DATA}) 
		//WE WILL SEND THE JSON DATA AND IMAGE SEPARATELY
		//WE WILL USE REQUEST PART FOR THAT
	@PostMapping("/product")
	public ResponseEntity<?> addProductWithImage(@RequestPart Product product,@RequestPart MultipartFile imageFile)
	{
		Product p;
		try 
		{
			p=service.addProduct(product,imageFile);
		} catch (IOException e) 
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Product>(p,HttpStatus.CREATED);
	}
}
