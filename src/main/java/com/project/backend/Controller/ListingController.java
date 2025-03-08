package com.project.backend.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.Entity.Images;
import com.project.backend.Entity.Listing;
import com.project.backend.service.Timex;
import com.project.backend.service.impl.Auction_ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ListingController {
    @Autowired
    private Auction_ProductService service;
    @Autowired
    private Timex scheduler;
    private static final Logger logger = LogManager.getLogger(ListingController.class);
    @GetMapping(path = "/readAll")
    public List<Listing> readProduct() {
        return service.readAllProduct();
    }
    @GetMapping(path = "/read/{id}")
    public Listing readProductByID(@PathVariable Integer id) {
        Listing prod = service.readProduct(id);
        return prod;
    }
    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestParam("name") String productName,
            @RequestParam("price") double productPrice,
            @RequestParam("image") MultipartFile image,
            @RequestParam("dtl") String productDetail,
            @RequestParam("category") String productCategory,
            @RequestParam(name="created", required = false) String createdby,
            @RequestParam("priceinterval") double productPriceInterval,
            @RequestParam(name = "starttime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam(name = "endtime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime) {
        logger.info("Multipartfile is " + image);
        Map<String ,String> res = new HashMap<String, String>();
        try {
            Listing prod = new Listing();
            Images img = new Images();
            prod.setName(productName);
            prod.setDtl(productDetail);
            img.setContentType(image.getContentType());
            img.setData(image.getBytes());
            img.setSize(image.getSize());
            prod.setImg(img);
            prod.setHighestbid(0.0);
            prod.setPrice(productPrice);
            prod.setCategory(productCategory);
            prod.setPriceInterval(productPriceInterval);
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
            res.put("msg", "Auction Listing Created!");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            logger.info("booom");
            res.put("error", "Failed to create new auction Listing!");
            res.put("path",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping(path = "/image/{id}")
    public ResponseEntity<?> images(@PathVariable("id") Integer id) {
    	Listing dataProduct = service.readProduct(id);
    	return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(dataProduct.getImg().getData());
	}
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = false) String productName,
            @RequestParam(name = "price", required = false) Double productPrice,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "dtl", required = false) String productDetail,
            @RequestParam(name = "highest_bid", required = false) Double hbid,
            @RequestParam(name = "category", required = false) String productCategory,
            @RequestParam(name="updatedby", required = false) String updatedby,
            @RequestParam(name = "starttime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam(name = "endtime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime,
            @RequestParam(name = "product_is_paused", required = false) Integer isPaused,
            @PathVariable int id) {
    	Map<String ,String> res = new HashMap<String, String>();
        Listing prodop = service.readProduct(id);
        if (prodop != null) {
            try {
                Listing prod = prodop;
                if (productName != null)
                    prod.setName(productName);
                if (productDetail != null)
                    prod.setDtl(productDetail);
                if (hbid != null)
                    prod.setHighestbid(hbid);
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
                if (image != null) {
                    img.setContentType(image.getContentType());
                    img.setData(image.getBytes());
                    img.setSize(image.getSize());
                    msg = service.updateProduct(prod, img);
                    scheduler.updatetimer(prod);
                    res.put("msg", msg);
                }else {
                	msg = service.updateProduct(prod, prod.getImg());
                	res.put("msg", msg);
                }
                return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                logger.info(e.getMessage());
                res.put("msg", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
    	Map<String ,String> res = new HashMap<String, String>();
    	try {
    		service.deleteProduct(id);
    		scheduler.deletetimer(id);
    		res.put("msg", "Deleted Auction with ID:"+id+" !");
    		return ResponseEntity.status(HttpStatus.OK).body(res);    		
    	}
    	catch(Exception e){
    		res.put("msg", "Not Found Auction with ID:"+id+" !");
    		res.put("path", e.getMessage());
    		return ResponseEntity.status(HttpStatus.OK).body(res);    		
    	}
    }
}
