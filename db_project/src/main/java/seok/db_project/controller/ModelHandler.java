package seok.db_project.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import seok.db_project.entity.enterprise;
import seok.db_project.entity.model;
import seok.db_project.repository.modelRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/model")
@CrossOrigin
public class ModelHandler {
    @Autowired
    private modelRepository modelRepository;

    @GetMapping("/findAll")
    public List<model> findAll(){
        return modelRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<model> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return modelRepository.findAll(pageable);
    }

    @PostMapping("/insert")
    public String save(@RequestBody model model1){
        model result = modelRepository.save(model1);
        return "success";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        modelRepository.deleteAll();
        return "success";
    }

    @PostMapping("/init")
    public String init() {
        modelRepository.deleteAll();
        List<model> list = new ArrayList<>();
        String[] FILE_HEADER = {"id", "number","model","name","unit_price"};
        String filepath = "src/main/resources/static/data/model.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))){
            Iterable<CSVRecord> records = format.parse(in);
            for (CSVRecord record : records){
                model temp = new model();
                temp.setId(Integer.parseInt(record.get("id")));
                temp.setNumber(record.get("number"));
                temp.setModel(record.get("model"));
                temp.setName(record.get("name"));
                temp.setUnit_price(Integer.parseInt(record.get("unit_price")));
                list.add(temp);
            }
            modelRepository.saveAll(list);
        } catch (IOException e) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/findbyId")
    public model findbyId(@RequestParam Integer i){
        Optional<model> result = modelRepository.findById(i);
        return result.orElse(null);
    }

    @GetMapping("/findbyNumber")
    public model findbyNumber(@RequestParam String number){
        return modelRepository.findByNumber(number);
    }

    @GetMapping("/findbyModel")
    public model findbyModel(@RequestParam String model){
        return modelRepository.findByModel(model);
    }


    @DeleteMapping("/deleteById")
    @Transactional
    public String deleteById(@RequestParam Integer i){
        modelRepository.deleteById(i);
        return "success";
    }

    @DeleteMapping("/deleteByName")
    @Transactional
    public String deleteByName(@RequestParam String name){
        modelRepository.deleteByName(name);
        return "success";
    }
}
