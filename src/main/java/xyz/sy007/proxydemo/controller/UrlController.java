package xyz.sy007.proxydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.sy007.proxydemo.service.UrlResponseService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlResponseService urlResponseService;

    @PostMapping("/url")
    public ResponseEntity<String> processUrl(@RequestBody Map<String, String> req) {
        boolean isSaved =urlResponseService.processUrl(req.get("url"));
        if(isSaved){ return ResponseEntity.ok("URL saved successfully");}
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save URL");
    }
    @PostMapping("/url2")
    public ResponseEntity<String> processUrl2(@RequestBody Map<String, String> req) {
        boolean isSaved =urlResponseService.processUrl2(req.get("url"));
        if(isSaved){ return ResponseEntity.ok("URL saved successfully");}
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save URL");
    }
    @PostMapping("/url3")
    public ResponseEntity<String> processUrl3(@RequestBody Map<String, String> req) {
        boolean isSaved =urlResponseService.processUrl3(req.get("url"));
        if(isSaved){ return ResponseEntity.ok("URL saved successfully");}
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save URL");
    }
}