package seok.db_project.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seok.db_project.entity.allorder;
import seok.db_project.entity.enterprise;
import seok.db_project.entity.product;
import seok.db_project.entity.staff;
import seok.db_project.repository.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/allrepo")
@CrossOrigin
public class AllRepoHandler {
    @Autowired
    private allorderRepository allorderRepository;

    @Autowired
    private productRepository productRepository;

    @Autowired
    private staffRepository staffRepository;

    @Autowired
    private enterpriseRepository enterpriseRepository;

    @Autowired
    private centerRepository centerRepository;

    @Autowired
    private contractRepository contractRepository;

    @Autowired
    private modelRepository modelRepository;

    @PostMapping("/clearall")
    public String clearall() {
        allorderRepository.deleteAll();
        productRepository.deleteAll();
        staffRepository.deleteAll();
        enterpriseRepository.deleteAll();
        centerRepository.deleteAll();
        contractRepository.deleteAll();
        modelRepository.deleteAll();
        return "success";
    }
}
