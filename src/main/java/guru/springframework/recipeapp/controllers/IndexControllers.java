package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexControllers {

    @RequestMapping({"","/","index"})
    public String getIndexPage(){
        System.out.println("Some string to print fhhhf");
        return "index";
    }
}
