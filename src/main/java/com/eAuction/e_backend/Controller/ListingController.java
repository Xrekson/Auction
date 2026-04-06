package com.eAuction.e_backend.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eAuction.e_backend.DTO.AuctionData;
import com.eAuction.e_backend.Entity.Listing;
import com.eAuction.e_backend.service.AuctionProductService;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost:5173"},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
public class ListingController {

    private final AuctionProductService auctionService;
    private final ObjectMapper json;

    // Constructor injection – recommended
    public ListingController(AuctionProductService auctionService,
                             ObjectMapper json) {
        this.auctionService = auctionService;
        this.json = json;
    }
    
    @GetMapping(path = "/listings")
    public List<AuctionData> readProduct() {
        return auctionService.readAllProduct();
    }
    @GetMapping(path = "/pre-auth/listings")
    public List<AuctionData> readProductPreLogin() {
        return auctionService.readAllProduct();
    }
    @GetMapping(path = "/listing/{id}")
    public Listing readProductByID( @PathVariable(name = "id") Integer id) {
        Listing prod = auctionService.readProduct(id);
        return prod;
    }
    @GetMapping(path = "/pre-auth/listing/{id}")
    public Listing readProductByIDPreLogin( @PathVariable(name = "id") Integer id) {
        Listing prod = auctionService.readProduct(id);
        return prod;
    }
    @PostMapping(path = "/listing/save")
    public ResponseEntity<?> createProduct(@RequestBody String data) {
        Map<String ,String> res = new HashMap<String, String>();
        try {
            Listing prod = new Listing();
            prod = json.readValue(data, Listing.class);
            prod.setHighestbid(0.0);
            prod.setCreated(LocalDateTime.now());
            prod.setUpdated(LocalDateTime.now());
            auctionService.createProduct(prod);
            res.put("msg", "Auction Listing Created!");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
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
            @RequestParam(required = false) String updatedby,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starttime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endtime,
            @RequestParam Integer id) {
    	Map<String ,String> res = new HashMap<String, String>();
        Listing prodop = auctionService.readProduct(id);
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
                if (updatedby != null)
                    prod.setUpdatedby(updatedby);
                if (starttime != null)
                    prod.setAuction_start(starttime);
                if (endtime != null)
                    prod.setAuction_end(endtime);
                String msg;
                	msg = auctionService.updateProduct(prod);
                	res.put("msg", msg);
                return ResponseEntity.ok().body(res);
            } catch (Exception e) {
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
    		auctionService.deleteProduct(id);
    		res.put("msg", "Deleted Auction with ID:"+id+" !");
    		return ResponseEntity.status(HttpStatus.OK).body(res);    		
    	}
    	catch(Exception e){
    		res.put("msg", "Not Found Auction with ID:"+id+" !");
    		res.put("path", e.getMessage());
    		return ResponseEntity.status(HttpStatus.OK).body(res);    		
    	}
    }
    @PostMapping(path = "/category/create")
    public ResponseEntity<?> createProduct(@RequestParam int id) {
    	Map<String ,String> res = new HashMap<String, String>();
    	try {
    		auctionService.deleteProduct(id);
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
