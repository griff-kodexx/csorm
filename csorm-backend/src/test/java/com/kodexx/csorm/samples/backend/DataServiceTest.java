package com.kodexx.csorm.samples.backend;

import com.kodexx.csorm.backend.data.File;
import com.kodexx.csorm.backend.service.DataService;
import com.kodexx.csorm.backend.service.FilesDataService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple unit test for the back-end data service.
 */
public class DataServiceTest {

    private DataService service;

    @Before
    public void setUp() throws Exception {
        service = FilesDataService.getInstance();
    }

    @Test
    public void testDataServiceCanFetchFiles() throws Exception {
        assertFalse(service.getAllFiles().isEmpty());
    }
    
    
    //fix getFileById first
    /*@Test
    public void testUpdateFile_updatesTheFile() throws Exception {
        File p = service.getAllFiles().iterator().next();
        p.setTitle("My Test Name");
        service.updateFile(p);
        File p2 = service.getAllFiles().iterator().next();
        assertEquals("My Test Name", p2.getTitle());
    }*/
}
