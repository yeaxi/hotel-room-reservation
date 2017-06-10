package ua.dudka.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rostislav Dudka
 */

@Controller
public class IndexController {


    @GetMapping("/")
    public String getPage() {
        return "index";
    }
}