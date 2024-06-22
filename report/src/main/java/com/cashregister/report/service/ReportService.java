package com.cashregister.report.service;


import com.cashregister.report.exception.SaleListNotFound;
import com.cashregister.report.model.SaleItemResponseDto;
import com.cashregister.report.model.SalesInfoDto;
import com.cashregister.report.model.ShoppingList;
import com.cashregister.report.repository.SaleRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    final SaleRepository shoppingListRepository;


    public List<SalesInfoDto> getAllSales() {

        List<SalesInfoDto> salesInfoDtoList = new ArrayList<>();
        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();

        shoppingLists.forEach(shoppingList ->
        {

            SalesInfoDto salesInfoDto = getSale(shoppingList.getId());
            salesInfoDtoList.add(salesInfoDto);
        });

        return salesInfoDtoList;
    }

    public SalesInfoDto getSale(long id) {
        ShoppingList shoppingList = shoppingListRepository.findById(id).orElseThrow(() -> new SaleListNotFound("Sales List with " + id + " ID not found"));
        SalesInfoDto salesInfoDto = new SalesInfoDto();
        salesInfoDto.setCashierName(shoppingList.getUser().getName());
        salesInfoDto.setCashierSurname(shoppingList.getUser().getSurname());
        salesInfoDto.setSaleDate(shoppingList.getSaleDate());
        double totalPrice = 0;


        shoppingList.getSaleItemList().forEach(shoppingItem ->
        {
            SaleItemResponseDto saleItemResponseDto = new SaleItemResponseDto();
            saleItemResponseDto.setPrice(shoppingItem.getProduct().getPrice());
            saleItemResponseDto.setName(shoppingItem.getProduct().getName());
            saleItemResponseDto.setType(shoppingItem.getType());
            saleItemResponseDto.setCount(shoppingItem.getQuantity());
            saleItemResponseDto.setTotalPrice(shoppingItem.getTotalPrice());
            salesInfoDto.getSaleItemResponseDtoList().add(saleItemResponseDto);
            salesInfoDto.setTotalPrice(salesInfoDto.getTotalPrice() + saleItemResponseDto.getTotalPrice());

        });


        return salesInfoDto;


    }

    public void createPdfFileSaleList(long id) throws IOException {

        SalesInfoDto salesInfoDto = getSale(id);

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDType0Font font = PDType0Font.load(document, new File("C:\\Users\\Alper\\Desktop\\hepsi\\cashRegister\\report\\src\\main\\java\\com\\cashregister\\report\\font\\tahoma.ttf"));

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setLeading(14.5f); // line spacing
        contentStream.newLineAtOffset(50, 750);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(salesInfoDto.getSaleDate());

        // Başlık

        contentStream.showText("FİŞ NUMARASI : "+id);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText("TARİH : " +formattedDate);
        contentStream.newLine();
        contentStream.showText("SAAT  : "+salesInfoDto.getSaleDate().getHours()+":"+salesInfoDto.getSaleDate().getMinutes() );
        contentStream.newLine();
        contentStream.showText("KASİYER: " + salesInfoDto.getCashierName() + " " + salesInfoDto.getCashierSurname());
        contentStream.newLine();
        contentStream.showText("----------------------------------------");
        contentStream.newLine();

        // Ürünler
        List<SaleItemResponseDto> items = salesInfoDto.getSaleItemResponseDtoList();
        for (SaleItemResponseDto item : items) {
            contentStream.showText(item.getName() + " (" + item.getCount() + " " + item.getType() + " X " + item.getPrice() + " TL)");
            contentStream.newLine();
            contentStream.showText("Toplam Fiyat: " + item.getTotalPrice() + " TL");
            contentStream.newLine();
            contentStream.newLine();
        }

        // Toplam Tutar
        contentStream.showText("----------------------------------------");
        contentStream.newLine();
        contentStream.showText("GENEL TOPLAM: " + salesInfoDto.getTotalPrice() + " TL");
        contentStream.newLine();
        contentStream.showText("----------------------------------------");
        contentStream.newLine();
        contentStream.showText("KDV FİŞİ DEĞİLDİR");
        contentStream.endText();
        contentStream.close();

        document.save("SaleList-" + id + ".pdf");
        document.close();
    }
}

