package com.kodexx.csorm.samples.backend.mock;

import java.util.List;

import com.kodexx.csorm.samples.backend.DataService;
import com.kodexx.csorm.samples.backend.data.Category;
import com.kodexx.csorm.samples.backend.data.File;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

    private static MockDataService INSTANCE;

    private List<File> files;
    private List<Category> categories;
    private int nextFileId = 0;

    private MockDataService() {
        categories = MockDataGenerator.createCategories();
        files = MockDataGenerator.createFiles(categories);
        nextFileId = files.size() + 1;
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
        return INSTANCE;
    }

    @Override
    public synchronized List<File> getAllFiles() {
        return files;
    }

    @Override
    public synchronized List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public synchronized void updateFile(File p) {
        if (p.getId() < 0) {
            // New file
            p.setId(nextFileId++);
            files.add(p);
            return;
        }
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getId() == p.getId()) {
                files.set(i, p);
                return;
            }
        }

        throw new IllegalArgumentException("No file with id " + p.getId()
                + " found");
    }

    @Override
    public synchronized File getFileById(int fileId) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getId() == fileId) {
                return files.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void deleteFile(int fileId) {
        File p = getFileById(fileId);
        if (p == null) {
            throw new IllegalArgumentException("File with id " + fileId
                    + " not found");
        }
        files.remove(p);
    }
}
