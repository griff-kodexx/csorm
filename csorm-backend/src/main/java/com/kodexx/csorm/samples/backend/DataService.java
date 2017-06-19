package com.kodexx.csorm.samples.backend;

import java.io.Serializable;
import java.util.Collection;

import com.kodexx.csorm.samples.backend.data.Category;
import com.kodexx.csorm.samples.backend.data.File;
import com.kodexx.csorm.samples.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    public abstract Collection<File> getAllFiles();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateFile(File p);

    public abstract void deleteFile(int fileId);

    public abstract File getFileById(int fileId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

}
