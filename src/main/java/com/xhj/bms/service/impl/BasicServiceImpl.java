package com.xhj.bms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * Created by Projack
 */
@Service
public class BasicServiceImpl {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    public URI getServiceUri(String service){
        ServiceInstance instance = loadBalancerClient.choose(service);
        URI uri = instance.getUri();
        if (uri !=null) {
            return uri;
        }
        throw new RuntimeException(service + " is not found!");
    }

}