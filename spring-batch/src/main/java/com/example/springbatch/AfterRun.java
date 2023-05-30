package com.example.springbatch;

import com.example.springcore.entity.Menu;
import com.example.springcore.entity.MenuDetail;
import com.example.springcore.repository.MenuDetailRepository;
import com.example.springcore.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AfterRun implements CommandLineRunner {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuDetailRepository menuDetailRepository;

    @Override
    public void run(String... args) {
        Menu menu1 = new Menu(1l, "file", "file", new Date(), "file", new Date(), "2000");
        menuRepository.save(menu1);
        Menu menu2 = new Menu(2l, "image", "image", new Date(), "image", new Date(), "500");
        menuRepository.save(menu2);
        Menu menu3 = new Menu(3l, "json", "json", new Date(), "json", new Date(), "900");
        menuRepository.save(menu3);
        List<MenuDetail> menuDetailList = new ArrayList<>();
        MenuDetail menuDetail1 = new MenuDetail();
        menuDetail1.setIdMenu(1l);
        menuDetail1.setOnClick("CreateDoc");
        menuDetail1.setDescription("ValueCreate");
        MenuDetail menuDetail2 = new MenuDetail();
        menuDetail2.setIdMenu(1l);
        menuDetail2.setOnClick("UpdateDoc");
        menuDetail2.setDescription("ValueUpdate");
        menuDetailList.add(menuDetail1);
        menuDetailList.add(menuDetail2);
        MenuDetail menuDetail3 = new MenuDetail();
        menuDetail3.setIdMenu(2l);
        menuDetail3.setOnClick("CreateFunction");
        menuDetail3.setDescription("ValueFunction");
        MenuDetail menuDetail4 = new MenuDetail();
        menuDetail4.setIdMenu(2l);
        menuDetail4.setOnClick("Hidden");
        menuDetail4.setDescription("ValueHidden");
        menuDetailList.add(menuDetail3);
        menuDetailList.add(menuDetail4);
        menuDetailRepository.saveAll(menuDetailList);
    }
}
