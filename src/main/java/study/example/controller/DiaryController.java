package study.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @RequestMapping("/list")
    public String list(Model model){
        return "hello.html";
    }
}
