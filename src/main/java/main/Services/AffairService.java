package main.Services;

import main.model.Affair;
import main.model.AffairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AffairService {

    private final AffairRepository affairRepository;

    @Autowired
    public AffairService(AffairRepository affairRepository) {
        this.affairRepository = affairRepository;
    }


    public List<Affair> findAll(){
        Iterable<Affair> affairIterable = affairRepository.findAll();
        ArrayList<Affair> list = new ArrayList<>();
        for(Affair affair : affairIterable){
            list.add(affair);
        }
        return list;
    }


    public ResponseEntity findOne(int id){
        Optional<Affair> optionalAffair = affairRepository.findById(id);
        if(!optionalAffair.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalAffair.get(), HttpStatus.OK);


//         Более короткая запись, чем та, что выше !
//        return optionalAffair.map(affair -> new ResponseEntity(affair, HttpStatus.OK))
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }



    @Transactional
    public int create(Affair affair){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        affair.setAddedDate(dateFormat.format(date));
        Affair newAffair = affairRepository.save(affair);
        return newAffair.getId();
    }


    @Transactional
    public ResponseEntity delete(int id){
        if(!affairRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        affairRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity update(Affair newAffair, int id){
        Optional<Affair> optionalAffair = affairRepository.findById(id);
        if(optionalAffair.isPresent()){
            Affair affair = optionalAffair.get();
            affair.setTitle(newAffair.getTitle());
            affair.setDescription(newAffair.getDescription());
            affairRepository.save(affair);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
