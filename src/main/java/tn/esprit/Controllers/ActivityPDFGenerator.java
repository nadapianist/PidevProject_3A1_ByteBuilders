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
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Load the logo image
            PDImageXObject logoImage = PDImageXObject.createFromFile("C:/Users/nadal/IdeaProjects/Pidev/src/main/resources/Img/logo_tunism-removebg-preview.png", document);

            // Add the logo to the PDF at the top left corner
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(logoImage, 470, 680, logoImage.getWidth() / 4, logoImage.getHeight() / 4);
            }

            // Define table parameters
            int startX = 50;
            int headerStartY = 680; // Starting position for header
            int rowHeight = 130; // Height of each row
            int tableColumnWidth = 150; // Adjusted column width

            // Create content stream after adding the image
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); // Use TIMES_ROMAN font
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Activity List in Location: " + location);
                contentStream.endText();

                contentStream.setFont(PDType1Font.TIMES_BOLD, 10); // Use TIMES_BOLD font for headers

                // Draw table headers
                drawTableHeader(contentStream, startX, headerStartY, tableColumnWidth, 30);

                // Get activity data from the database
                Connection connection = MyDb.getInstance().getCon();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT activity.name, activity.date_act, activity.Description FROM activity " +
                                "JOIN association_activity_location ON activity.id_act = association_activity_location.Activity " +
                                "JOIN location ON association_activity_location.Location = location.ID " +
                                "WHERE location.Name = ?");
                preparedStatement.setString(1, location);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Add activity data to the table
                int rowStartY = headerStartY - 30; // Starting position for table rows
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String date = resultSet.getString("date_act");
                    String description = resultSet.getString("Description");

                    // Draw table row
                    drawTableRow(contentStream, startX, rowStartY, tableColumnWidth, rowHeight, name, date, description);

                    // Move to next row
                    rowStartY -= rowHeight;
                }
            }

            // Save the document
            document.save("pdf/ActivityList.pdf");
            document.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void drawTableHeader(PDPageContentStream contentStream, int startX, int startY, int columnWidth, int rowHeight) throws IOException {
        contentStream.setFont(PDType1Font.TIMES_BOLD, 10); // Use TIMES_BOLD font for headers
        contentStream.setLineWidth(1);

        // Draw horizontal lines for the header row
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(startX + columnWidth * 3, startY);
        contentStream.stroke();

        // Draw vertical lines and headers
        String[] headers = {"Name Activity ", "Date Activity ", "Description Activity "};
        int xOffset = 0;
        for (String header : headers) {
            // Draw left border for each header cell
            contentStream.moveTo(startX + xOffset, startY);
            contentStream.lineTo(startX + xOffset, startY - rowHeight);
            contentStream.stroke();

            // Draw bottom border for each header cell
            contentStream.moveTo(startX + xOffset, startY - rowHeight);
            contentStream.lineTo(startX + xOffset + columnWidth, startY - rowHeight);
            contentStream.stroke();

            // Draw header text
            contentStream.beginText();
            contentStream.newLineAtOffset(startX + xOffset + 5, startY - 10);
            contentStream.showText(header);
            contentStream.endText();

            xOffset += columnWidth;
        }

        // Draw right border for the last header cell
        contentStream.moveTo(startX + xOffset, startY);
        contentStream.lineTo(startX + xOffset, startY - rowHeight);
        contentStream.stroke();
    }

    private void drawTableRow(PDPageContentStream contentStream, int startX, int startY, int columnWidth, int rowHeight, String name, String date, String description) throws IOException {
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 10); // Use TIMES_ROMAN font for table content

        // Draw horizontal lines
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(startX + columnWidth * 3, startY);
        contentStream.stroke();

        // Draw vertical lines and content
        String[] content = {name, date, description};
        int xOffset = 0;
        for (String value : content) {
            // Draw left border
            contentStream.moveTo(startX + xOffset, startY);
            contentStream.lineTo(startX + xOffset, startY - rowHeight);
            contentStream.stroke();

            // Draw bottom border
            contentStream.moveTo(startX + xOffset, startY - rowHeight);
            contentStream.lineTo(startX + xOffset + columnWidth, startY - rowHeight);
            contentStream.stroke();

            // Wrap text if necessary
            String[] wrappedText = wrapText(value);
            int yOffset = 0;
            for (String wrappedLine : wrappedText) {
                contentStream.beginText();
                contentStream.newLineAtOffset(startX + xOffset + 5, startY - 10 - yOffset);
                contentStream.showText(wrappedLine);
                contentStream.endText();
                yOffset += 12; // Adjust for line spacing
            }

            xOffset += columnWidth;
        }

        // Draw right border for the last cell
        contentStream.moveTo(startX + xOffset, startY);
        contentStream.lineTo(startX + xOffset, startY - rowHeight);
        contentStream.stroke();
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

