package edu.ap.spring.ask8ball.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.ap.spring.ask8ball.demo.redis.RedisService;


@Controller
public class ask8ballController {

    @Autowired
    private RedisService service;

    // 
    @GetMapping("/addquestion")
    public String index(){
        return "redirect:/addquestion";
    }
    @PostMapping("/addquestion")
    public String question(@RequestParam("question") String question){
        ArrayList<String> questions = new ArrayList<>();
        String zelfdevraag = question;
        for (String a : this.service.keys("question:" + question + ":*")) {
            questions.add(this.service.getKey(a));
        }
        if (questions.contains(zelfdevraag)) { // countains loopt door u array
            System.out.println("oeps die bestaat wel");
        }else{
            this.service.setKey("question:" + question + ":" + answer);
            System.out.println("vraag word toegevoegd");
        }
        return "redirect:/addquestion";
    }
}