package com.subject.service;

import com.subject.BaseTest;
import jxl.write.WriteException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liangqin
 * @date 2019/5/15 19:13
 */
public class ExportServiceTest extends BaseTest {

    @Autowired
    private ExportService exportService;

    @Test
    public void testExporttaskbooksBytid() throws WriteException {
        exportService.exportTaskBooksByTid("110003");
    }
}
