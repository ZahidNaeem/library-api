package org.zahid.apps.web.pos.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReportService2 {
    @Autowired
    private DataSource dataSource;
    Logger log = LogManager.getLogger(ReportService2.class);
    //    private static final String logo_path = "/jasper/images/stackextend-logo.png";
    private final String ROOT_DIR = "/META-INF/resources/jasper/";

    public void generateReport(Map<String, Object> parameters, String reportName) throws IOException {
        final String REPORT_TEMPLATE = ROOT_DIR + reportName + ".jrxml";
//        final JRBeanCollectionDataSource DATA_SOURCE = new JRBeanCollectionDataSource(Collections.singletonList("reports"));
        try (InputStream is = getClass().getResourceAsStream(REPORT_TEMPLATE)) {
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(is);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, dataSource.getConnection());

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput(reportName + ".pdf"));

            SimplePdfReportConfiguration reportConfig
                    = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("Zahid");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            log.info("Before Export Report");
            exporter.exportReport();
            log.info("After Export Report");
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Load invoice jrxml template
//    private JasperReport loadTemplate(String reportName) throws JRException {
//        final String REPORT_TEMPLATE = ROOT_DIR + reportName;
//        log.info(String.format("Invoice template path : %s", REPORT_TEMPLATE));
//        final InputStream reportInputStream = getClass().getResourceAsStream(REPORT_TEMPLATE);
//        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
//        return JasperCompileManager.compileReport(jasperDesign);
//    }

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
