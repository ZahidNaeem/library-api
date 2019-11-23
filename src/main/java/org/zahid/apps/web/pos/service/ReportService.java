package org.zahid.apps.web.pos.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private DataSource dataSource;
    Logger log = LogManager.getLogger(ReportService.class);
    //    private static final String logo_path = "/jasper/images/stackextend-logo.png";
    private final String ROOT_DIR = "/META-INF/resources/jasper/";

    public void generateReport(Map<String, Object> paramsMap, String reportName) throws IOException {
        File pdfFile = File.createTempFile(reportName, ".pdf");
        log.info(String.format("PDF path : %s", pdfFile.getAbsolutePath()));
        try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
            // Load report jrxml template.
            final JasperReport report = loadTemplate(reportName);
            // Create an empty datasource.
//            final JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(Collections.singletonList("reports"));
            JRDataSource dataSource2 = new JREmptyDataSource();
//            JRDataSource dataSource2 = (JRDataSource) dataSource;
            // Render as PDF.
            JasperReportsUtils.renderAsPdf(report, paramsMap, dataSource2, pos);
        } catch (final Exception e) {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }
    }

    // Load invoice jrxml template
    private JasperReport loadTemplate(String reportName) throws JRException {
        final String REPORT_TEMPLATE = ROOT_DIR + reportName + ".jrxml";
        log.info(String.format("Invoice template path : %s", REPORT_TEMPLATE));
        final InputStream reportInputStream = getClass().getResourceAsStream(REPORT_TEMPLATE);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
        return JasperCompileManager.compileReport(jasperDesign);
    }

//    public void exportToPDF(String reportName) {
//        JRPdfExporter exporter = new JRPdfExporter();
//
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(
//                new SimpleOutputStreamExporterOutput("employeeReport.pdf"));
//
//        SimplePdfReportConfiguration reportConfig
//                = new SimplePdfReportConfiguration();
//        reportConfig.setSizePageToContent(true);
//        reportConfig.setForceLineBreakPolicy(false);
//
//        SimplePdfExporterConfiguration exportConfig
//                = new SimplePdfExporterConfiguration();
//        exportConfig.setMetadataAuthor("baeldung");
//        exportConfig.setEncrypted(true);
//        exportConfig.setAllowedPermissionsHint("PRINTING");
//
//        exporter.setConfiguration(reportConfig);
//        exporter.setConfiguration(exportConfig);
//
//        exporter.exportReport();
//    }
}
