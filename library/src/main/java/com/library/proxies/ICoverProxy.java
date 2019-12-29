package com.library.proxies;

import com.library.beans.mbooks.cover.CoverBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server",contextId = "coverProxy")
@RibbonClient(name = "microservice-file")
@RequestMapping("/microservice-file/cover")
public interface ICoverProxy {

    @GetMapping("/{id}")
    CoverBean find(@PathVariable("id") String id);
}
