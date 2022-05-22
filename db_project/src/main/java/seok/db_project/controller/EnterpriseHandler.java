package seok.db_project.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import seok.db_project.entity.center;
import seok.db_project.entity.enterprise;
import seok.db_project.repository.enterpriseRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enterprise")
@CrossOrigin
public class EnterpriseHandler {
    @Autowired
    private enterpriseRepository enterpriseRepository;

    @GetMapping("/findAll")
    public List<enterprise> findAll(){
        return enterpriseRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<enterprise> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return enterpriseRepository.findAll(pageable);
    }

    @PostMapping("/insert")
    public String save(@RequestBody enterprise user1){
        enterprise result = enterpriseRepository.save(user1);
        return "success";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        enterpriseRepository.deleteAll();
        return "success";
    }

    @PostMapping("/init")
    public String init() {
        enterpriseRepository.deleteAll();
        List<enterprise> list = new ArrayList<>();
        String[] FILE_HEADER = {"id", "name","country","city","supply_center","industry"};
        String filepath = "src/main/resources/static/data/enterprise.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))){
            Iterable<CSVRecord> records = format.parse(in);
            for (CSVRecord record : records){
                enterprise temp = new enterprise();
                temp.setId(Integer.parseInt(record.get("id")));
                temp.setName(record.get("name"));
                temp.setCountry(record.get("country"));
                temp.setCity(record.get("city"));
                temp.setSupply_center(record.get("supply_center"));
                temp.setIndustry(record.get("industry"));
                list.add(temp);
            }
            enterpriseRepository.saveAll(list);
        } catch (IOException e) {
            return "fail";
        }
        return "success";
    }


    @GetMapping("/findbyId")
    public enterprise findbyId(@RequestParam Integer i){
        Optional<enterprise> result = enterpriseRepository.findById(i);
        return result.orElse(null);
    }

//    @GetMapping("/findbyName")
//    public center findbyName(@RequestParam String name){
//        Optional<center> result = centerRepository.findByName(name);
//        return result.orElse(null);
//    }

    @DeleteMapping("/deleteById")
    @Transactional
    public String deleteById(@RequestParam Integer i){
        enterpriseRepository.deleteById(i);
        return "success";
    }

    @DeleteMapping("/deleteByName")
    @Transactional
    public String deleteByName(@RequestParam String name){
        enterpriseRepository.deleteByName(name);
        return "success";
    }
}
