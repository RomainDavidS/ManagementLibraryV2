package com.library.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "zuul-server",contextId = "emailProxy")
@RibbonClient(name = "microservice-email")
@RequestMapping("/microservice-email")
public interface IEmailProxy {
}
