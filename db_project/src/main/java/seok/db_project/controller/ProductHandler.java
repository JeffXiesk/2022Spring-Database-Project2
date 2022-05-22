package seok.db_project.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import seok.db_project.entity.*;
import seok.db_project.repository.centerRepository;
import seok.db_project.repository.modelRepository;
import seok.db_project.repository.productRepository;
import seok.db_project.repository.staffRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductHandler {
    @Autowired
    private productRepository productRepository;

    @Autowired
    private staffRepository staffRepository;

    @Autowired
    private centerRepository centerRepository;

    @Autowired
    private modelRepository modelRepository;

    @GetMapping("/findAll")
    public List<product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<product> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productRepository.findAll(pageable);
    }

    @PostMapping("/stockIn")
    public String stockIn() {
        productRepository.deleteAll();
        List<product> list = new ArrayList<>();
        String[] FILE_HEADER = {"id", "supply_center", "product_model", "supply_staff", "date", "purchase_price", "quantity"};
        String filepath = "src/main/resources/static/data/final/task1_stockin.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
            Iterable<CSVRecord> records = format.parse(in);
            String strId, strSupply_center, strProduct_model, strSupply_staff, strDate, strPurchase_price, strQuantity;
            for (CSVRecord record : records) {
                strId = record.get("id");
                strSupply_center = record.get("supply_center");
                strProduct_model = record.get("product_model");
                strSupply_staff = record.get("supply_staff");
                strDate = record.get("date");
                strPurchase_price = record.get("purchase_price");
                strQuantity = record.get("quantity");
                product temp = new product();
//                staffRepository.findByName()
                staff now = staffRepository.findByNumber(Integer.parseInt(strSupply_staff));
                if (now == null)
                    continue;
                if (!now.getSupply_center().equals(strSupply_center))
                    continue;
                if (!now.getType().equals("Supply Staff"))
                    continue;
                if (centerRepository.findByName(strSupply_center) == null)
                    continue;
                if (modelRepository.findByModel(strProduct_model) == null)
                    continue;
                temp.setId(Integer.parseInt(strId));
                temp.setSupply_center(strSupply_center);
                temp.setProduct_model(strProduct_model);
                temp.setSupply_staff(Integer.parseInt(strSupply_staff));
                temp.setDate(strDate);
                temp.setPurchase_price(Integer.parseInt(strPurchase_price));
                temp.setQuantity(Integer.parseInt(strQuantity));
                list.add(temp);
            }
            HashSet<Integer> to_remove = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                if (to_remove.contains(i)) continue;
                int remain = list.get(i).getQuantity();
                for (int j = 0; j < list.size(); j++) {
                    if (i == j) continue;
                    if (list.get(i).getSupply_center().equals(list.get(j).getSupply_center()) &&
                            list.get(i).getProduct_model().equals(list.get(j).getProduct_model())) {
                        to_remove.add(j);
                        remain += list.get(j).getQuantity();
                    }
                }
                list.get(i).setQuantity(remain);
                list.get(i).setRemain(remain);
            }
            for (int i = 0; i < list.size(); i++) {
                if (to_remove.contains(i)) continue;
                productRepository.save(list.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @GetMapping("/getNeverSoldProductCount")
    int getNeverSoldProductCount() {
        return productRepository.findNeverSoldProductCount().size();
    }

    @GetMapping("/getFavoriteProductModel")
    Object[] getFavoriteProductModel() {
        List<Object[]> list = productRepository.FindFavoriteProductModel();
        if (list.isEmpty()) return null;
        list.sort((a, b) -> ((Long) b[1]).compareTo((Long) a[1]));
        return list.get(0);
    }

    @GetMapping("/getAvgStockByCenter")
    List<Object[]> getAvgStockByCenter() {
        List<Object[]> l = productRepository.findSumStockByCenter();
        for (Object[] entry : l) {
            String str = String.format("%.1f", Double.parseDouble(entry[1].toString()) / (Long) entry[2]);
            entry[1] = str;
        }
        l.sort(Comparator.comparing(a -> a[0].toString()));
        return l;
    }

    @GetMapping("/getProductByNumber")
    List<Object[]> getProductByNumber(@RequestParam String num) {
        return productRepository.findProductByNumber(num);
    }
}
