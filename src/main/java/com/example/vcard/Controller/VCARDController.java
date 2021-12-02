package com.example.vcard.Controller;


import com.example.vcard.SiteGetter;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class VCARDController {

    @GetMapping("/{text}")
    public String vCardGetter(@PathVariable String text) throws IOException {
        SiteGetter siteGetter = new SiteGetter();

        return siteGetter.getPanoramaFirm(text);
    }

    @GetMapping(value = "/vcard/", produces = {"text/vcard"})
    public String getVCard(@RequestParam String name,
                           @RequestParam String telephone,
                           @RequestParam String email,
                           @RequestParam String website,
                           @RequestParam String street,
                           @RequestParam String postalCode,
                           @RequestParam String city){

        SiteGetter siteGetter = new SiteGetter();

        return siteGetter.createVCard(name,email,website,telephone,street,postalCode,city);

    }



}
