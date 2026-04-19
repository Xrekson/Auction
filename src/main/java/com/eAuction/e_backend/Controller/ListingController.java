package com.eAuction.e_backend.Controller;

import com.eAuction.e_backend.DTO.AuctionData;
import com.eAuction.e_backend.Service.AuctionProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
    origins = { "http://localhost:4200", "http://localhost:5173" },
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT }
)
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path="/api/listing")
public class ListingController {
    @Autowired
    private AuctionProductService auctionService;

    @GetMapping(path = "/all")
    public List<AuctionData> readProduct() {
        return auctionService.readAllProduct();
    }
    
    @GetMapping(path = "{id}")
    public AuctionData readProductByID(@PathVariable(name = "id") Integer id) {
        AuctionData prod = auctionService.readProduct(id);
        return prod;
    }


    @PostMapping(path = "/save")
    public ResponseEntity<?> createProduct(@RequestBody AuctionData data) {
        Map<String, String> res = new HashMap<String, String>();
        try {
            AuctionData prod = data;
            prod.setHighestbid(0.0);
            prod.setCreated(LocalDateTime.now());
            prod.setUpdated(LocalDateTime.now());
            auctionService.createProduct(prod);
            res.put("msg", "Auction Listing Created!");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("error", "Failed to create new auction Listing!");
            res.put("path", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                res
            );
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateProduct(
        @RequestParam(name = "name", required = false) String productName,
        @RequestParam(name = "price", required = false) Double productPrice,
        @RequestParam(required = false) String[] image,
        @RequestParam(name = "detail", required = false) String productDetail,
        @RequestParam(name = "highest_bid", required = false) Double hbid,
        @RequestParam(required = false) String updatedby,
        @RequestParam(required = false) @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE_TIME
        ) LocalDateTime starttime,
        @RequestParam(required = false) @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE_TIME
        ) LocalDateTime endtime,
        @RequestParam Integer id
    ) {
        Map<String, String> res = new HashMap<String, String>();
        AuctionData prodop = auctionService.readProduct(id);
        if (prodop != null) {
            try {
                AuctionData prod = prodop;
                if (productName != null) prod.setName(productName);
                if (productDetail != null) prod.setDetail(productDetail);
                if (hbid != null) prod.setHighestbid(hbid);
                if (productPrice != null) prod.setPrice(productPrice);
                if (updatedby != null) prod.setUpdatedby(updatedby);
                if (starttime != null) prod.setAuction_start(starttime);
                if (endtime != null) prod.setAuction_end(endtime);
                String msg;
                msg = auctionService.updateProduct(prod);
                res.put("msg", msg);
                return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                res.put("msg", e.getMessage());
                return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
                ).body(res);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam int id) {
        Map<String, String> res = new HashMap<String, String>();
        try {
            auctionService.deleteProduct(id);
            res.put("msg", "Deleted Auction with ID:" + id + " !");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            res.put("msg", "Not Found Auction with ID:" + id + " !");
            res.put("path", e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }
}
