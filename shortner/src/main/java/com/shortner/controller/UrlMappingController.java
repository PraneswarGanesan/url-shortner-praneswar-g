package com.shortner.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shortner.service.UrlMappingService;
import com.shortner.service.UserService;

import lombok.AllArgsConstructor;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

import com.shortner.dto.*;
import com.shortner.models.*;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService uService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> shortenUrl(@RequestBody Map<String,String> request, Principal principal) {
        String orginalUrl = request.get("orginalUrl");
        User user = uService.findByUsername(principal.getName());
        //call service
        UrlMappingDTO urldto=urlMappingService.shortenUrl(orginalUrl, user);
        return ResponseEntity.ok(urldto);

        
    }
   @GetMapping("/myurls")
   @PreAuthorize("hasRole('USER')")
   public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal){
      User user = uService.findByUsername(principal.getName());
      List<UrlMappingDTO> urls = urlMappingService.geturlByUser(user);
      return ResponseEntity.ok(urls);
   }
   @GetMapping("/analytics/{shortUrl}")
   @PreAuthorize("hasRole('USER')")
   public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl, @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate, Principal principal)
   {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> urlAnalytics = urlMappingService.getUrlAnalytics(shortUrl, start, end);
        return ResponseEntity.ok(urlAnalytics);
   }
   @GetMapping("/totalClicks")
   @PreAuthorize("hasRole('USER')")
   public ResponseEntity<Map<LocalDate, Long>> getTotalClicks(
       @RequestParam("startDate") String startDate,
       @RequestParam("endDate") String endDate,
       Principal principal
   ) {
       DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
       LocalDate start = LocalDate.parse(startDate, formatter);
       LocalDate end = LocalDate.parse(endDate, formatter);
   
       User user = uService.findByUsername(principal.getName());
       Map<LocalDate, Long> urlAnalytics = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
       return ResponseEntity.ok(urlAnalytics);
   }
   

}
