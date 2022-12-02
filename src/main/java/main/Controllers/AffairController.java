package main.Controllers;

import main.Services.AffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Affair;

import java.util.List;


@RestController
public class AffairController
{
    private final AffairService affairService;

    @Autowired
    public AffairController(AffairService affairService) {
        this.affairService = affairService;
    }

    @GetMapping("/affairs")
    public List<Affair> list() {
        return affairService.findAll();
    }


    @PostMapping("/affairs")
    public int add(Affair affair) {
        return affairService.create(affair);
    }


    @GetMapping("/affairs/{id}")
    public ResponseEntity getAffair(@PathVariable int id) {
        return affairService.findOne(id);
    }


    @RequestMapping(value = "/affairs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAffair(@PathVariable int id){
        return affairService.delete(id);
    }


    @RequestMapping(value = "/affairs/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateAffair(Affair newAffair, @PathVariable int id){
        return affairService.update(newAffair, id);
    }

}
