package com.eAuction.e_backend.Controller.pre_auth;

import com.eAuction.e_backend.DTO.AuctionData;
import com.eAuction.e_backend.Service.AuctionProductService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${app.cors.allowed-origins}", methods = { RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT })
@RestController
@RequestMapping("/api/pre-auth/listing")
public class ListingPreAuthController {

    @Autowired
    private AuctionProductService auctionService;

    @GetMapping(path = "/all")
    public List<AuctionData> readProductPreAuth() {
        return auctionService.readAllProduct();
    }

    @GetMapping(path = "{id}")
    public AuctionData readProductByIDPreAuth(@PathVariable(name = "id") Integer id) {
        AuctionData prod = auctionService.readProduct(id);
        return prod;
    }
}
