package com.GetApp.Get.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapPageController {

    @GetMapping("/map")
    public String showMapPage() {
        return "map"; // loads map.html
    }
}
