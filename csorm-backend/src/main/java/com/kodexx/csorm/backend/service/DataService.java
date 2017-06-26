package com.kodexx.csorm.backend.service;

import com.kodexx.csorm.backend.data.File;
import java.io.Serializable;
import java.util.Collection;

/**
 * Back-end service interface for retrieving and updating files
 */
public abstract class DataService implements Serializable {
    public static DataService get() {
        return FilesDataService.getInstance();
    }

    public abstract Collection<File> getAllFiles();

    public abstract void updateFile(File p);

    public abstract void deleteFile(int fileId);

    public abstract File getFileById(int fileId);


}
