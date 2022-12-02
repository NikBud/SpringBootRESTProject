package main.Controllers;

import main.model.Affair;
import main.model.AffairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController
{
    private final AffairRepository affairRepository;

    @Autowired
    public DefaultController(AffairRepository affairRepository) {
        this.affairRepository = affairRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Affair> affairIterable = affairRepository.findAll();
        ArrayList<Affair> affairs = new ArrayList<>();
         for(Affair affair : affairIterable){
             affairs.add(affair);
         }
         model.addAttribute("affairs", affairs);
         model.addAttribute("affairCount", affairs.size());
         return "index";
    }
}
