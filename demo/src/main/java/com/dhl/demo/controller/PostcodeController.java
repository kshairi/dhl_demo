package com.dhl.demo.controller;


import com.dhl.demo.PostcodeDistanceApplication;
import com.dhl.demo.model.PostcodeDistanceRequest;
import com.dhl.demo.model.PostcodeDistanceRequestResponse;
import com.dhl.demo.service.PostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/postcode")
public class PostcodeController {

    @Autowired
    PostcodeService postcodeService;

    @PostMapping(value = "/calculate-distance")
    public @ResponseBody PostcodeDistanceRequestResponse calculatePostcodeDistance(@RequestBody PostcodeDistanceRequest distanceRequest)
    {
        Object response = new Object();
        return postcodeService.calculatePostcodeDistance(distanceRequest);
    }

}
