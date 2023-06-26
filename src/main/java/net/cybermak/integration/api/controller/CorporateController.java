package net.cybermak.integration.api.controller;


import net.cybermak.integration.api.model.Response;
import net.cybermak.integration.api.model.TicketDetails;
import net.cybermak.integration.api.service.CorporateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/corporate")
public class CorporateController {
    @Autowired
    CorporateService corporateService;
    final Logger logger = LoggerFactory.getLogger(CorporateController.class);

    public CorporateController(CorporateService corporateService) {
        this.corporateService = corporateService;
    }

    @PostMapping(value = "/corporateInfo",  produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response> callBSSCorporateAPI(@RequestBody TicketDetails ticketDetails) throws ParseException {
        logger.info("Called the callBSSCorporateAPI method");
        logger.info(ticketDetails.toString());
        String tempResponse = corporateService.getBSSCorporateResponse(ticketDetails);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        logger.info("------------------------------------------BSS-Corporate Output Flag-----------------------------: " + tempResponse);
        Response response = new Response(tempResponse);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response,HttpStatus.OK);
        logger.info(responseEntity.toString());
        return responseEntity;
    }

    @GetMapping("/get")
    @ResponseBody
    public String get() {
        logger.info("called the get method");
        return "{\"getstudents\": \"getting all students\"}";
    }


    @RequestMapping(
            value = "/getallstudents",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public ResponseEntity<String> getAllStudents() {
        logger.info("called the getAllStudents method");
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>("{\"students\": \"Get all students\"}",
                httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/1", produces = MediaType.ALL_VALUE)
    public String getIt() {
        return "Success";
    }

}
