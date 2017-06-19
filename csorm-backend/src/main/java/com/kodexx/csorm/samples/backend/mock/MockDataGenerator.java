package com.kodexx.csorm.samples.backend.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.kodexx.csorm.samples.backend.data.Availability;
import com.kodexx.csorm.samples.backend.data.Category;
import com.kodexx.csorm.samples.backend.data.File;

public class MockDataGenerator {
    private static int nextCategoryId = 1;
    private static int nextFileId = 1;
    private static final Random random = new Random(1);
    private static final String categoryNames[] = new String[] {
            "Comp 111", "Comp 112", "Comp 122", "Comp 1212" };

    private static String[] word1 = new String[] { "Computer Fundamentals", "Operating Systems", "Texas Instruments"};

    private static String[] word2 = new String[] {"Algorithm Analysis", "Security For dummies 101" };

    static List<Category> createCategories() {
        List<Category> categories = new ArrayList<Category>();
        for (String name : categoryNames) {
            Category c = createCategory(name);
            categories.add(c);
        }
        return categories;

    }

    static List<File> createFiles(List<Category> categories) {
        List<File> products = new ArrayList<File>();
        for (int i = 0; i < 100; i++) {
            File p = createFile(categories);
            products.add(p);
        }

        return products;
    }

    private static Category createCategory(String name) {
        Category c = new Category();
        c.setId(nextCategoryId++);
        c.setName(name);
        return c;
    }

    private static File createFile(List<Category> categories) {
        File p = new File();
        p.setId(nextFileId++);
        p.setTitle(generateName());       
        return p;
    }

    private static Set<Category> getCategory(List<Category> categories,
            int min, int max) {
        int nr = random.nextInt(max) + min;
        HashSet<Category> productCategories = new HashSet<Category>();
        for (int i = 0; i < nr; i++) {
            productCategories.add(categories.get(random.nextInt(categories
                    .size())));
        }

        return productCategories;
    }

    private static String generateName() {
        return word1[random.nextInt(word1.length)] + " "
                + word2[random.nextInt(word2.length)];
    }

}
