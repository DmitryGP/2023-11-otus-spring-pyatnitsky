package org.dgp.hw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPageController {

    @GetMapping("/")
    public String getListPage() {
        return  "list";
    }

}
 