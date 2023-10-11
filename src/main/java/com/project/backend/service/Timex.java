package com.project.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.backend.BackendApplication;
import com.project.backend.Entity.Product;
import com.project.backend.Repository.ProductRepository;
import com.project.backend.service.impl.Auction_ProductService;

@Component
public class Timex {
    @Autowired
    private ProductRepository prepo;
    @Autowired
    private Auction_ProductService service;
    Timer mpx = new Timer();
    Map<Integer, TimerTask> schedule = new HashMap<>();
    Logger logger = LogManager.getLogger(BackendApplication.class);

    public void createtimer(String name,String username) {
        Product data = (Product)prepo.findAll().stream().filter(datax->{
            if(datax.getName()==name&&datax.getCreatedby()==username){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toList()).get(0);
        Compi taskx = new Compi(data.getId(),service);
        Starting tasky = new Starting(data.getId(), service);
        mpx.schedule(tasky, data.getAuction_start());
        mpx.schedule(taskx,  data.getAuction_end());
        schedule.put(data.getId()+100000,taskx);
	    schedule.put(data.getId()+200000,tasky);
    }
    public void updatetimer(Product prod){
        schedule.get(prod.getId()+100000).cancel();
        schedule.get(prod.getId()+200000).cancel();
        logger.info(prod.getId());
		schedule.remove(prod.getId()+100000);
		schedule.remove(prod.getId()+200000);
		Compi taskx = new Compi(prod.getId(),service);
		Starting tasky = new Starting(prod.getId(), service);
	    mpx.schedule(taskx,  prod.getAuction_end());
	    mpx.schedule(tasky, prod.getAuction_start());
	    schedule.put(prod.getId()+100000,taskx);
	    schedule.put(prod.getId()+200000,tasky);
    }
    public void deletetimer(Integer id){
        schedule.get(id+100000).cancel();
        schedule.get(id+200000).cancel();
    	schedule.remove(id+100000);
    	schedule.remove(id+200000);
    }
}
