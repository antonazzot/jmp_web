package com.tsyrkunou.jmpwep.application.service.commonservice;

import java.util.Map;

public interface PdfGenerateService {
    String generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}