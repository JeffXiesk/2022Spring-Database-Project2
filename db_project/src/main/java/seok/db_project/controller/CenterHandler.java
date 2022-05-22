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
import seok.db_project.repository.centerRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/center")
@CrossOrigin
public class CenterHandler {
    @Autowired
    private centerRepository centerRepository;

    @GetMapping("/findAll")
    public List<center> findAll() {
        return centerRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<center> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return centerRepository.findAll(pageable);
    }

    @PostMapping("/insert")
    public String save(@RequestBody center c) {
        center result = centerRepository.save(c);
        return "success";
    }

    @PostMapping("/insertAll")
    public String saveAll(@RequestBody List<center> c) {
        List<center> result = centerRepository.saveAll(c);
        return "success";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        centerRepository.deleteAll();
        return "success";
    }


    @PostMapping("/init")
    public String init() {
        centerRepository.deleteAll();
        List<center> list = new ArrayList<>();
        String[] FILE_HEADER = {"id", "name"};
        String filepath = "src/main/resources/static/data/center.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))){
            Iterable<CSVRecord> records = format.parse(in);
            String strID;
            String strName;
            for (CSVRecord record : records){
                strID = record.get("id");
                strName = record.get("name");
                center temp = new center();
                temp.setId(Integer.parseInt(strID));
                temp.setName(strName);
                list.add(temp);
            }
            centerRepository.saveAll(list);
        } catch (IOException e) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/findbyId")
    public center findbyId(@RequestParam Integer i) {
        Optional<center> result = centerRepository.findById(i);
        return result.orElse(null);
    }

    @GetMapping("/findbyName")
    public center findbyName(@RequestParam String name){
        return centerRepository.findByName(name);
    }

    @DeleteMapping("/deleteById")
    @Transactional
    public String deleteById(@RequestParam Integer i) {
        centerRepository.deleteById(i);
        return "success";
    }

    @DeleteMapping("/deleteByName")
    @Transactional
    public String deleteByName(@RequestParam String name) {
        centerRepository.deleteByName(name);
        return "success";
    }
}
