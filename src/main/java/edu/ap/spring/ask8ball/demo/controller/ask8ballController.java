package edu.ap.spring.ask8ball.demo.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.ap.spring.ask8ball.demo.redis.RedisService;


@Controller
public class ask8ballController {

    @Autowired
    private RedisService service;

    @GetMapping("/addquestion")
    public String index(){
        return "redirect:/addquestion";
    }
    @PostMapping("/addquestion")
    public String question(@RequestParam("question") String question, Model model){
        Random rand = new Random();
        String[] answers = new String[]{"It is certain.", "It is decidedly so.", "Without a doubt.", "Yes - definitely.","Most likely.", "Outlook good.", "Yes."};
        
        ArrayList<String> questions = new ArrayList<>();
        String zelfdevraag = question;
        String answer = "";
        for (String a : this.service.keys("question:" + question + ":*")) {
            questions.add(this.service.getKey(a));
        }
        if (questions.contains(zelfdevraag)) { // countains loopt door u array
            System.out.println("oeps die bestaat wel");
        }else{
            int max = rand.nextInt(answers.length-1);
            int min =0;
            int number = max - min+1;
            int rnd = (int)((Math.random()*number));
            answer = answers[(int) Math.floor(rnd)];
            this.service.setKey("question:" + question + ":" + answer,"");
            System.out.println("vraag word toegevoegd");
        }
        model.addAttribute("answer", answer);
        return "redirect:/addquestion";
    }
}