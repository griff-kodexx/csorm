package com.kodexx.csorm.samples.backend;

import org.junit.Before;
import org.junit.Test;
import com.kodexx.csorm.samples.backend.data.File;
import com.kodexx.csorm.samples.backend.mock.MockDataService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Simple unit test for the back-end data service.
 */
public class DataServiceTest {

    private DataService service;

    @Before
    public void setUp() throws Exception {
        service = MockDataService.getInstance();
    }

    @Test
    public void testDataServiceCanFetchProducts() throws Exception {
        assertFalse(service.getAllFiles().isEmpty());
    }

    @Test
    public void testDataServiceCanFetchCategories() throws Exception {
        assertFalse(service.getAllCategories().isEmpty());
    }

    @Test
    public void testUpdateProduct_updatesTheProduct() throws Exception {
        File p = service.getAllFiles().iterator().next();
        p.setTitle("My Test Name");
        service.updateFile(p);
        File p2 = service.getAllFiles().iterator().next();
        assertEquals("My Test Name", p2.getTitle());
    }
}
