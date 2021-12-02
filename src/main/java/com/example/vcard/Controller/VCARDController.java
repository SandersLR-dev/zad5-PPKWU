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





}
