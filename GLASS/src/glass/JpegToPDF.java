package glass;

import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static glass.GUI.jTextArea_Details;

public class JpegToPDF
{

public static String convertAndCreate(int chartPanelCount, String filePathFirstData_XML)
{
    String path = "";
    try
    {
        //Create Document Object
        Document convertJpegToPdf = new Document();
        //Create PdfWriter for Document to hold physical file
        convertJpegToPdf.setMargins(3, 3, 3, 3);
        convertJpegToPdf.setPageSize(PageSize.A2);

        String timeStamp = new SimpleDateFormat("MMddYY_HHmmss").format(Calendar.getInstance().getTime());
        String name = filePathFirstData_XML.substring(filePathFirstData_XML.lastIndexOf("\\") + 1, filePathFirstData_XML.length());
        // System.out.println(name);
        path = GUI.toolPath + "SavedPDF\\" + name + "_" + timeStamp + ".pdf";
        PdfWriter pw = PdfWriter.getInstance(convertJpegToPdf, new FileOutputStream(path));
        pw.setStrictImageSequence(true);

        convertJpegToPdf.open();
        //set Title
        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 20);
        titleFont.setStyle("bold");
        Paragraph title = new Paragraph("Calibration Data Utility ", titleFont);
        title.setIndentationLeft(400);
        convertJpegToPdf.add(title);

        com.itextpdf.text.Font textFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);

        for (int i = 0; i < chartPanelCount; i++)
        {

            PdfPTable table = new PdfPTable(2);
            PdfPCell cell1 = new PdfPCell();
            PdfPCell cell2 = new PdfPCell();
            Paragraph p = new Paragraph();
            Image convertJepg = Image.getInstance(GUI.toolPath + "Temp\\" + i + ".jpeg");
            p.add(new Chunk(convertJepg, 0, 0, true));
            cell1.addElement(p);

            p = new Paragraph();
            Chunk chunk = new Chunk(jTextArea_Details[i].getText(), textFont);
            // chunk.setLocalDestination(String.valueOf(i));
            p.add(chunk);
            cell2.addElement(p);

            table.addCell(cell1);
            table.addCell(cell2);
            table.completeRow();

            table.setKeepTogether(true);
            table.setWidths(new int[]
            {
                5, 1
            });
            table.setWidthPercentage(100);
            convertJpegToPdf.add(table);

        }

        //Close Document
        convertJpegToPdf.close();
        // ToolGUI.update_jTextField_menuCommands("Successfully Created SavedPDF\\"+name+".pdf");
    } catch (DocumentException | IOException i1)
    {
    }
    return path;
}

}
