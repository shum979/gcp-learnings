package me.shubham.utils;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final String INPUT_PATH = "/Users/shugupta10/Pictures/names/yob2005.txt";
    private static final ArrayList<String> statuses = Lists.newArrayList("TESTED", "CONFIRMED", "SUSPECTED", "ISOLATED", "CURED", "DEAD");
    private  static final List<String> countries = Lists.newArrayList("China","Singapore","Republic of Korea","Japan","Malaysia","Australia","Viet","Nam","Philippines","Cambodia","Thailand","India","Nepal","Sri Lanka","United States of America","Canada","Germany","France","The","United","Kingdom","Italy","Russian Federation","Spain","Belgium","Finland","Sweden","United Arab Emirates");
    private  static final Random random = new Random();
    private List<String> names;


    public DataGenerator(){
        try {
            names = Files.readAllLines(Paths.get(INPUT_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateData() {
        //name,gender,age,country,status
        String line = names.get(random.nextInt(names.size()));
        String[] arr = line.split(",");

        int age = random.nextInt(80)+10;
        String country = countries.get(random.nextInt(countries.size()));
        String status = statuses.get(random.nextInt(statuses.size()));


        return String.format("{\"name\" : \"%s\" ,\"gender\" : \"%s\" ,\"age\" : %s ,\"country\" : \"%s\" ,\"status\" : \"%s\" }",arr[0],arr[1],age,country,status);
    }

}
