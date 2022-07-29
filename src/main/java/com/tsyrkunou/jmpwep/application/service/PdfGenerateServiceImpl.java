package com.tsyrkunou.jmpwep.application.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfGenerateServiceImpl implements PdfGenerateService {

    private final TemplateEngine templateEngine;

    @Value("${pdfdirectory}")
    private String pdfDirectory;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try (FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
            throw new MyAppException(e.getMessage());
        }
    }
}
