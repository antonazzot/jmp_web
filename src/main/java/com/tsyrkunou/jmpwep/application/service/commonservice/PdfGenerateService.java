package com.tsyrkunou.jmpwep.application.service.commonservice;

import java.util.Map;

public interface PdfGenerateService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}