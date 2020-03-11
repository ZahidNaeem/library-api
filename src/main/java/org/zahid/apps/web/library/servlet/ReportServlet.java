package org.zahid.apps.web.library.servlet;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "ReportServlet", urlPatterns = {"/reportservlet"})
@RequiredArgsConstructor
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final Logger LOG = LogManager.getLogger(ReportServlet.class);
    
    private final DataSource dataSource;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("doGet");
        Map<String, Object> params = new HashMap();
        String repName = request.getParameter("repName");
        String jsonParams = request.getParameter("params");
        if (jsonParams != null) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonParams);

                Iterator i = obj.keys();
                while (i.hasNext()) {
                    String key = String.valueOf(i.next());
                    String value = String.valueOf(obj.get(key));
                    LOG.info("Json Key: " + key);
                    LOG.info("Json Value: " + value);
                    params.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        //        for (Map.Entry<String, String[]> entry : entrySet) {
        //            LOG.info("Key: " + entry.getKey());
        //            LOG.info("Value: " + entry.getValue().toString());
        //        }
        //        params.put("BindDept", Long.valueOf("10"));
        InputStream is;
        //        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
        //                                                          .getExternalContext()
        //                                                          .getContext();
        LOG.info("Jasper Report File Path: " + getServletContext().getRealPath("/jasper"));
        try {
            is = getServletContext().getResourceAsStream("/jasper/" + repName + ".jasper");

            //            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + repName + ".pdf");


            //    response.setHeader( "Content-Description", getContentDescription() );
            //    response.setHeader( "Content-Disposition", "attachment, filename="
            //      + getFilename() );
            //    response.setHeader( "Content-Type", "application/pdf" );


            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());

            /*****************
             *
             * **************/
            //            JRExporter exporter = new HtmlExporter();
            //            PrintWriter out = response.getWriter();
            //            response.setContentType("text/html");
            //            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //            exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, out);
            //            exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            //            exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            //            exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
            //            exporter.exportReport();
            /*****************
             *
             * **************/

            //            JRPdfExporter exporter = new JRPdfExporter();
            //            //                        exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT,
            //            //                                              "var pp = this.getPrintParams();pp.interactive=pp.constants.interactionLevel.silent;pp.NumCopies=1; this.disclosed= true ;this.print({bUI: false,bSilent: false,bShrinkToFit: true,printParams:pp});");
            //            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT,
            //                                  "this.print({bUI: false, bSilent: true, bShrinkToFit: false});this.closeDoc;");
            //            exporter.exportReport();
            /*****************
             *
             * **************/

            //            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            //            response.getOutputStream().flush();
            //            response.getOutputStream().close();
            /*****************
             *
             * **************/
            //            PrinterJob job = PrinterJob.getPrinterJob();
            //            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            //            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE; // This is the Flavour JasperReports uses
            //
            //            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            //            aset.add(MediaSizeName.ISO_A4); // ..or whatever the document size is

            //            if (job.printDialog(aset))
            //                service = job.getPrintService();
            //Then you export the Jasper Report:
            // Fill the report and get the JasperPrint instance
            //            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            //            // Export the report using the JasperPrint instance
            //            JRExporter exporter = new JRPrintServiceExporter();
            //            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, service.getAttributes());
            //            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            //            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            //            exporter.exportReport();
            /*****************
             *
             * **************/
            /*JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); //The JasperPrint, filled report
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream())); //Your ByteArrayOutputStream
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setPdfJavaScript("var pp = this.getPrintParams();pp.interactive=pp.constants.interactionLevel.silent;pp.NumCopies=1; this.disclosed= true ;this.print({bUI: false,bSilent: false,bShrinkToFit: true,printParams:pp});");
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            response.getOutputStream().flush();
            response.getOutputStream().close();*/
            /*****************
             *
             * **************/
            HtmlExporter exporter = new HtmlExporter();
            //            PrintWriter out = response.getWriter();
            response.setContentType(CONTENT_TYPE);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); //The JasperPrint, filled report
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getOutputStream())); //Your ByteArrayOutputStream
            SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();
            exporter.setConfiguration(configuration);
            //            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //            exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, out);
            //            exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            //            exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            //            exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
            exporter.exportReport();
            response.getOutputStream().flush();
            response.getOutputStream().close();
            /*****************
             *
             * **************/

            //            JasperPrintManager.printReport(jasperPrint, true);
        } catch (Exception ex) {
            LOG.info("Exception: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
//        this.doGet(req, resp);
    }
}
