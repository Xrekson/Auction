package com.project.backend.service;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backend.BackendApplication;
import com.project.backend.Entity.Product;
import com.project.backend.service.impl.Auction_ProductService;

public class Compi extends TimerTask {
	Auction_ProductService service;
	Integer id;
	Logger logger = LogManager.getLogger(BackendApplication.class);
	public Compi(Integer id,Auction_ProductService service) {
		this.service = service;
		logger.info("CLoser created for listing id"+service.readProduct(id).getId());
		this.id=id;
	}
	@Override
	public void run() {
		Product dataProduct = service.readProduct(id);
		logger.info("Closing listing id:"+dataProduct.getId()+" name:"+dataProduct.getName());
		dataProduct.setIs_closed(1);
		dataProduct.setIs_active(0);
		dataProduct.setIs_paused(0);
		service.updateProduct(dataProduct, dataProduct.getImg());
	}
}
