package com.project.backend.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.Entity.Images;
import com.project.backend.Entity.Product;
import com.project.backend.service.Timex;
import com.project.backend.service.impl.Auction_ProductService;

@RestController
@RequestMapping(path = "/auction")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private Auction_ProductService service;
    @Autowired
    private Timex scheduler;
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @GetMapping(path = "/readAll")
    public List<Product> readProduct() {
        return service.readAllProduct();
    }

    @GetMapping(path = "/read/{id}")
    public Product readProductByID(@PathVariable Integer id) {
        Product prod = service.readProduct(id);
        return prod;
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(@RequestParam("name") String productName,
            @RequestParam("price") double productPrice,
            @RequestParam("image") MultipartFile image,
            @RequestParam("dtl") String productDetail,
            @RequestParam("category") String productCategory,
            @RequestParam("created") String createdby,
            @RequestParam("priceinterval") double productPriceInterval,
            @RequestParam(name = "starttime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam(name = "endtime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime) {
        logger.info("Multipartfile is " + image);
        try {
            Product prod = new Product();
            Images img = new Images();
            prod.setName(productName);
            prod.setDtl(productDetail);
            img.setContentType(image.getContentType());
            img.setData(image.getBytes());
            img.setSize(image.getSize());
            prod.setImg(img);
            prod.setPrice(productPrice);
            prod.setCategory(productCategory);
            prod.setIs_active(0);
            prod.setIs_paused(0);
            prod.setIs_closed(0);
            prod.setCreatedby(createdby);
            prod.setUpdatedby(createdby);
            prod.setAuction_start(Timestamp.valueOf(starttime));
            prod.setAuction_end(Timestamp.valueOf(endtime));
            prod.setCreated(new Timestamp(System.currentTimeMillis()));
            prod.setUpdated(new Timestamp(System.currentTimeMillis()));
            service.createProduct(prod, img);
            scheduler.createtimer(prod.getName(), prod.getCreatedby());
            return ResponseEntity.ok("Auction Listing Created");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            logger.info("booom");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create new auction Listing");
        }
    }

    
    
    
    @GetMapping(path = "/image/{id}")
    public ResponseEntity<?> images(@PathVariable("id") Integer id) {
    	Product dataProduct = service.readProduct(id);
    	return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(dataProduct.getImg().getData());
	}
    
    
    
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = false) String productName,
            @RequestParam(name = "price", required = false) Double productPrice,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "dtl", required = false) String productDetail,
            @RequestParam(name = "category", required = false) String productCategory,
            @RequestParam(name="updatedby", required = false) String updatedby,
            @RequestParam(name = "starttime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam(name = "endtime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime,
            @RequestParam(name = "product_is_paused", required = false) Integer isPaused,
            @PathVariable int id) {
        Product prodop = service.readProduct(id);
        if (prodop != null) {
            try {
                Product prod = prodop;
                if (productName != null)
                    prod.setName(productName);
                if (productDetail != null)
                    prod.setDtl(productDetail);
                if (productPrice != null)
                    prod.setPrice(productPrice);
                if (productCategory != null)
                    prod.setCategory(productCategory);
                if (updatedby != null)
                    prod.setUpdatedby(updatedby);
                if (starttime != null)
                    prod.setAuction_start(Timestamp.valueOf(starttime));
                if (endtime != null)
                    prod.setAuction_end(Timestamp.valueOf(endtime));
                if (isPaused != null)
                    prod.setIs_paused(isPaused);
                Images img = prod.getImg();
                String msg;
                msg = service.updateProduct(prod, prod.getImg());
                if (image != null) {
                    img.setContentType(image.getContentType());
                    img.setData(image.getBytes());
                    img.setSize(image.getSize());
                    msg = service.updateProduct(prod, img);
                }
                scheduler.updatetimer(prod);
                return ResponseEntity.ok().body(msg);
            } catch (Exception e) {
                logger.info(e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
    	try {
    		service.deleteProduct(id);
    		scheduler.deletetimer(id);
    		return ResponseEntity.status(HttpStatus.OK).body("Deleted Auction");    		
    	}
    	catch(Exception e){
    		return ResponseEntity.status(HttpStatus.OK).body("Not Found"+e.getMessage());    		
    	}
    }
    @GetMapping(path = "/images/{id}")
    public ResponseEntity<?> getimg(@PathVariable("id") int id){
        Product data = service.readProduct(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data.getImg().getData());
    }
}
