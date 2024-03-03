package tn.esprit.Controllers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.utils.MyDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ActivityPDFGenerator {

    public void generateActivityListPDF(String location) {

    }


    private void drawTableHeader(PDPageContentStream contentStream, int startX, int startY, int columnWidth, int rowHeight) throws IOException {

    }

    private void drawTableRow(PDPageContentStream contentStream, int startX, int startY, int columnWidth, int rowHeight, String name, String date, String description) throws IOException {

    }

    private String[] wrapText(String text) {
        String[] words = text.split("\\s+");
        StringBuilder wrappedText = new StringBuilder();
        StringBuilder line = new StringBuilder();
        int wordsPerLine = 4; // Wrap text after every 4 words
        int wordCount = 0;
        for (String word : words) {
            if (wordCount < wordsPerLine) {
                line.append(word).append(" ");
                wordCount++;
            } else {
                wrappedText.append(line.toString().trim()).append("\n");
                line.setLength(0); // Clear the line buffer
                line.append(word).append(" ");
                wordCount = 1; // Reset word count
            }
        }
        // Append the last line
        wrappedText.append(line.toString().trim());
        return wrappedText.toString().split("\n");
    }



}

