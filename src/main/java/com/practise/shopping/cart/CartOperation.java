package com.practise.shopping.cart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartOperation {

	@Autowired
	CartRepository cartRepo;
	@Autowired
	ProductRepository prodRepo;

	
	//@RequestMapping(path="/User/cart", method = RequestMethod.GET)
	@GetMapping(path="/User/cart")
	public Iterable<Cart> showCartDetails() {
		return cartRepo.findAll();
	}
	
	
	@GetMapping(path="/Products")
	public Iterable<Product> showProducts() {
		return prodRepo.findAll();
	}
	
	@GetMapping("/User/products/{prod}")
	public ResponseEntity<Product> getProduct(@PathVariable("prod") Integer prodID)
	{
		ResponseEntity<Product> response;
				Optional<Product> prd=prodRepo.findById(prodID);
		if(prd.isPresent())
		{
			response= new ResponseEntity<Product>(prd.get(),HttpStatus.OK);
		}
		else
			response= new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		
		return response;
	}
	
	@PostMapping("/User/Product")
	public ResponseEntity<String> addProduct(@RequestBody Product prod)
	{
		prodRepo.save(prod);
		ResponseEntity<String> res= new ResponseEntity<String>("Product Added to the DB",HttpStatus.OK);
		return  res;
	}
	
	@GetMapping("/User/Product")
	public Iterable<Product> showProduct()
	{
		return prodRepo.findAll();
	}
	
	
	@PostMapping("/User/cart/{prod}")
	public ResponseEntity<String> addToCart(@PathVariable("prod") Integer prod_id, @RequestBody Cart cart)
	{
		Optional<Product> prd=prodRepo.findById(prod_id);
		ResponseEntity<String> response = null;
		Optional<Cart> crt=cartRepo.findById(cart.getCartID());
		if(prd.isPresent())
		{
			//Product prdObj=prd.get();
			Product prdObj=prd.get();
			List<Product> prodList = new ArrayList<Product>();
			prodList.add(prdObj);
            Date date = Calendar.getInstance().getTime(); 
            if(crt.get().getCreationDate() !=null && !(crt.get().getCreationDate().isEmpty()))
            {
    			cart.setCreationDate(crt.get().getCreationDate());
    			System.out.println("should be old date now as the record exist");	

            }
            else
            {
    			cart.setCreationDate(date.toString());
    			System.out.println("current date time stamp  "+crt.get().getCreationDate());	

            }
            
			cart.setProd(prodList);
			int prdNumber=crt.get().getNumberOfProd();
			int Prdcartid=cart.getCartID();
			System.out.println("Cart id about to add in product table is:::  "+ Prdcartid);	
			System.out.println("Total product is:::  "+ prdNumber);	
			cart.setNumberOfProd(prdNumber+1);
			prdObj.setCart(cart);
			cartRepo.save(cart);
			prodRepo.save(prdObj);
			response= new ResponseEntity<String>("Added to the cart",HttpStatus.OK);
			
		}
		else
		{
			response= new ResponseEntity<String>("Record not present",HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	@GetMapping("/User/cart/{cartid}")
	public ResponseEntity<Cart> getCartDetails(@PathVariable("cartid") Integer cartid)
	{
		Optional<Cart> ct=cartRepo.findById(cartid);
		ResponseEntity<Cart> response;
		if(ct.isPresent())
		{
			response= new ResponseEntity<Cart>(ct.get(),HttpStatus.OK);
		}
		else
		{
			response= new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);

		}
		return response;
	}
}
