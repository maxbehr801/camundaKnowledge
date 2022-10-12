package info.maxbehr.bpm.externaltaskworker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRestController {

    @GetMapping("/all")
    public String testeAlles() {
        return "das ist alles";
    }
}
