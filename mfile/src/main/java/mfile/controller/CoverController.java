package mfile.controller;

import mfile.model.Cover;
import mfile.service.ICoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cover")
public class CoverController implements HealthIndicator {

    @Autowired
    private ICoverService coverService;

    @GetMapping("/{id}")
    public @ResponseBody Cover getCover(@PathVariable String id) {
        return coverService.find( id );
    }


    @Override
    public Health health() {
        List<Cover> coverList = coverService.findAll();

        if(coverList.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
