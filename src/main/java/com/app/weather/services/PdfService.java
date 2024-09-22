package com.app.weather.services;


import com.app.weather.dtos.WeatherList;
import com.app.weather.dtos.WeatherResponseDto;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.IOException;
import java.util.Comparator;

import static com.app.weather.utility.StringUtility.capitalizeFirstChar;
import static com.app.weather.utility.WeatherUtility.convertTemperatureFromKelvinToCelsius;
@Service
public class PdfService {

    public byte[] generateWeatherReport(WeatherResponseDto weatherResponseDto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (Document document = createPdfDocument(out)) {
            addTitle(document, weatherResponseDto.getCity().getName());
            addWeatherReportTable(document, weatherResponseDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private void addWeatherReportTable(Document document, WeatherResponseDto weatherResponseDto) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

        float[] columnWidths = {1, 2, 2, 2};
        Table table = createWeatherTable(columnWidths, boldFont);

        fillTableWithWeatherData(table, weatherResponseDto);

        document.add(table);
    }

    private void fillTableWithWeatherData(Table table, WeatherResponseDto weatherResponseDto) {
        weatherResponseDto.getWeatherList().stream()
                .sorted(Comparator.comparing(WeatherList::getDtTxt))
                .forEach(w -> {
                    try {
                        String temp = formatTemperature(w.getWeatherMain().getTemp());
                        String minMaxTemp = formatMinMaxTemperature(w);
                        String status = capitalizeFirstChar(w.getWeather().get(0).getDescription());
                        String day = w.getDtTxt();

                        addRowToTable(table, temp, minMaxTemp, status, day);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private String formatTemperature(Double temperature) {
        return String.format("%.2f", convertTemperatureFromKelvinToCelsius(temperature));
    }

    private String formatMinMaxTemperature(WeatherList weather) {
        String minTemp = String.format("%.2f", convertTemperatureFromKelvinToCelsius(weather.getWeatherMain().getTempMin()));
        String maxTemp = String.format("%.2f", convertTemperatureFromKelvinToCelsius(weather.getWeatherMain().getTempMax()));
        return minTemp + " / " + maxTemp;
    }

    private Table createWeatherTable(float[] columnWidths, PdfFont boldFont) {
        Table table = new Table(columnWidths);
        table.setWidth(490);

        table.addHeaderCell(new Paragraph("Current Temperature (°C)").setFont(boldFont).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Paragraph("Min/Max Temperature (°C)").setFont(boldFont).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Paragraph("Status").setFont(boldFont).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Paragraph("Hour").setFont(boldFont).setTextAlignment(TextAlignment.CENTER));

        return table;
    }

    private void addRowToTable(Table table, String temp, String minMaxTemp, String status, String day) {
        table.addCell(new Paragraph(temp).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph(minMaxTemp).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph(status).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph(day).setTextAlignment(TextAlignment.CENTER));
    }

    private Document createPdfDocument(ByteArrayOutputStream out) throws IOException {
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        return new Document(pdfDoc);
    }

    private void addTitle(Document document, String cityName) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

        Paragraph cityTitle = new Paragraph(capitalizeFirstChar(cityName) + " - Weather Report")
                .setFont(boldFont)
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(cityTitle);

        document.add(new Paragraph(" "));
    }
}
