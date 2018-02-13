package ar.com.ak.util;

import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReportUtil {

    public static final String PDF_EXTENSION = "pdf";
    public static final String PDF_MIME_TYPE = "application/pdf";
    public static final String DEFAULT_FILENAME = "tmp";
    public static final String REPORTS_FOLDER = "/jasperReport/";

    private ReportUtil() {
    }

    public static void printListToPDF(String reportName, List<?> elements, Map<String, Object> parameters) {
        try {

            File sourceFile = new File(FacesUtil.getResourceRealPath(REPORTS_FOLDER + reportName));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    new FileInputStream(sourceFile), parameters, new JRBeanCollectionDataSource(elements));

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, FacesUtil.getResponse().getOutputStream());
            exporter.setParameter(JRPdfExporterParameter.PERMISSIONS,
                    new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING));

            exporter.exportReport();

            FacesUtil.downloadFile(
                    reportNameToFileName(jasperPrint.getName(), PDF_EXTENSION), PDF_MIME_TYPE);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String reportNameToFileName(String reportName, String extension) {
        String result = DEFAULT_FILENAME;

        if (Check.isNotEmpty(reportName)) {
            result = reportName;
        }

        return String.format("%1$s_%2$te-%2$ta-%2$tY.%3$s", result, new Date(), extension);
    }
}
