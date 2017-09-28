package com.xhj.bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import java.net.URI;

/**
 * Created by Administrator on 2017/4/6.
 */
@Controller
public class BasicController {

    @Value("${service.domain.url}")
    protected String SERVICE_DOMAIN_URL;

    @Autowired
    protected LoadBalancerClient loadBalancerClient;

    public URI getServiceUri(String service){
        ServiceInstance instance = loadBalancerClient.choose(service);
        URI uri = instance.getUri();
        if (uri !=null) {
            return uri;
        }
        throw new RuntimeException(service + " is not found!");
    }



}
