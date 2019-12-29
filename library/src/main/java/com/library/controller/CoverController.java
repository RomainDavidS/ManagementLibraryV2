package com.library.controller;

import com.library.beans.mbooks.cover.CoverBean;
import com.library.service.mfile.ICoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cover")
public class CoverController {

    @Autowired
    private ICoverService coverService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadCover(@PathVariable("id") String id){

        CoverBean cover = coverService.findCover( id );
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cover.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cover.getFileName() + "\"")
                .body(new ByteArrayResource(cover.getData()));

    }


}
