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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.backend.Entity.Category;
import com.project.backend.Entity.Listing;
import com.project.backend.service.AuctionProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ListingController {
    @Autowired
    private AuctionProductService service;
    private static final Logger logger = LogManager.getLogger(ListingController.class);
    @GetMapping(path = "/listings")
    public List<Listing> readProduct() {
        return service.readAllProduct();
    }
    @GetMapping(path = "/listing")
    public Listing readProductByID( @RequestParam Integer id) {
        Listing prod = service.readProduct(id);
        return prod;
    }
    @PostMapping(path = "/listing/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestParam("name") String productName,
            @RequestParam("price") double productPrice,
            @RequestParam String[] image,
            @RequestParam("detail") String productDetail,
            @RequestParam("category") Category productCategory,
            @RequestParam(name="created", required = false) String createdby,
            @RequestParam("priceinterval") double productPriceInterval,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime) {
        Map<String ,String> res = new HashMap<String, String>();
        try {
            Listing prod = new Listing();
            prod.setName(productName);
            prod.setDetail(productDetail);
            prod.setImg(image);
            prod.setHighestbid(0.0);
            prod.setPrice(productPrice);
            prod.setCategory(productCategory);
            prod.setPriceInterval(productPriceInterval);
            prod.setCreatedby(createdby);
            prod.setUpdatedby(createdby);
            prod.setAuction_start(Timestamp.valueOf(starttime));
            prod.setAuction_end(Timestamp.valueOf(endtime));
            prod.setCreated(new Timestamp(System.currentTimeMillis()));
            prod.setUpdated(new Timestamp(System.currentTimeMillis()));
            service.createProduct(prod);
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

    @PutMapping(path = "/listing/update")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = false) String productName,
            @RequestParam(name = "price", required = false) Double productPrice,
            @RequestParam(required = false) String[] image,
            @RequestParam(name = "detail", required = false) String productDetail,
            @RequestParam(name = "highest_bid", required = false) Double hbid,
            @RequestParam(name = "category", required = false) Category productCategory,
            @RequestParam(required = false) String updatedby,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime,
            @RequestParam Integer id) {
    	Map<String ,String> res = new HashMap<String, String>();
        Listing prodop = service.readProduct(id);
        if (prodop != null) {
            try {
                Listing prod = prodop;
                if (productName != null)
                    prod.setName(productName);
                if (productDetail != null)
                    prod.setDetail(productDetail);
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
                String msg;
                	msg = service.updateProduct(prod);
                	res.put("msg", msg);
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
    @PostMapping(path = "/listing/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam int id) {
    	Map<String ,String> res = new HashMap<String, String>();
    	try {
    		service.deleteProduct(id);
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
