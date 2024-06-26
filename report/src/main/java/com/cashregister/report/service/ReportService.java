package com.cashregister.report.service;

import com.cashregister.report.exception.SaleListNotFound;
import com.cashregister.report.model.SaleItemResponseDto;
import com.cashregister.report.model.SalesInfoDto;
import com.cashregister.report.model.ShoppingList;
import com.cashregister.report.repository.SaleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ReportService {

    final SaleRepository shoppingListRepository;
    final ModelMapper modelMapper;

    public List<SalesInfoDto> getAllSales() {
        log.info("Entering method: getAllSales");
        List<SalesInfoDto> salesInfoDtoList = new ArrayList<>();
        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();

        shoppingLists.forEach(shoppingList -> {
            SalesInfoDto salesInfoDto = getSale(shoppingList.getId());
            salesInfoDtoList.add(salesInfoDto);
        });

        log.info("Exiting method: getAllSales");
        return salesInfoDtoList;
    }

    public SalesInfoDto getSaleInfo(Long id) {
        log.info("Entering method: getSaleInfo with id: {}", id);

        ShoppingList shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new SaleListNotFound("Sales List with " + id + " ID not found"));

        SalesInfoDto salesInfoDto = modelMapper.map(shoppingList, SalesInfoDto.class);
        salesInfoDto.setCashierSurname(shoppingList.getUser().getSurname());
        salesInfoDto.setCashierName(shoppingList.getUser().getName());

        shoppingList.getSaleItemList().forEach(item -> {
            SaleItemResponseDto saleItemResponseDto = modelMapper.map(item, SaleItemResponseDto.class);
            saleItemResponseDto.setName(item.getProduct().getName());
            salesInfoDto.getSaleItemResponseDtoList().add(saleItemResponseDto);
        });

        log.info("Exiting method: getSaleInfo with id: {}", id);
        return salesInfoDto;
    }

    public Page<SalesInfoDto> getAllList(int pageSize, int pageNumber) {
        log.info("Entering method: getAllList with : pageSize: {}, pageNumber: {}",  pageSize, pageNumber);
        Page<ShoppingList> list = shoppingListRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<SalesInfoDto> salesInfoDtoList = new ArrayList<>();

        list.forEach(item -> {
            salesInfoDtoList.add(getSale(item.getId()));
        });

        Page<SalesInfoDto> salesInfoDtoPage = new PageImpl<>(salesInfoDtoList, PageRequest.of(pageNumber, pageSize), list.getTotalElements());
        log.info("Exiting method: getAllList with pageSize: {}, pageNumber: {}", pageSize, pageNumber);
        return salesInfoDtoPage;
    }

    public SalesInfoDto getSale(long id) {
        log.info("Entering method: getSale with id: {}", id);
        ShoppingList shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new SaleListNotFound("Sales List with " + id + " ID not found"));

        SalesInfoDto salesInfoDto = new SalesInfoDto();
        salesInfoDto.setCashierName(shoppingList.getUser().getName());
        salesInfoDto.setCashierSurname(shoppingList.getUser().getSurname());
        salesInfoDto.setSaleDate(shoppingList.getSaleDate());

        shoppingList.getSaleItemList().forEach(shoppingItem -> {
            SaleItemResponseDto saleItemResponseDto = new SaleItemResponseDto();
            saleItemResponseDto.setPrice(shoppingItem.getProduct().getPrice());
            saleItemResponseDto.setName(shoppingItem.getProduct().getName());
            saleItemResponseDto.setType(shoppingItem.getType());
            saleItemResponseDto.setQuantity(shoppingItem.getQuantity());
            saleItemResponseDto.setTotalPrice(shoppingItem.getTotalPrice());
            salesInfoDto.getSaleItemResponseDtoList().add(saleItemResponseDto);
            salesInfoDto.setTotalPrice(salesInfoDto.getTotalPrice() + saleItemResponseDto.getTotalPrice());
        });

        salesInfoDto.setTotalPrice(shoppingList.getTotalPrice());
        log.info("Exiting method: getSale with id: {}", id);
        return salesInfoDto;
    }

    public void createPdfFileSaleList(long id) throws IOException {
        log.info("Entering method: createPdfFileSaleList with id: {}", id);
        SalesInfoDto salesInfoDto = getSale(id);

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDType0Font font = PDType0Font.load(document, new File("path/to/tahoma.ttf"));

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setLeading(14.5f); // line spacing
        contentStream.newLineAtOffset(250, 750);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(salesInfoDto.getSaleDate());

        // Title
        contentStream.showText("FİŞ NUMARASI : " + id);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText("TARİH : " + formattedDate);
        contentStream.newLine();
        contentStream.showText("SAAT  : " + salesInfoDto.getSaleDate().getHours() + ":" + salesInfoDto.getSaleDate().getMinutes());
        contentStream.newLine();
        contentStream.showText("KASİYER: " + salesInfoDto.getCashierName() + " " + salesInfoDto.getCashierSurname());
        contentStream.newLine();
        contentStream.showText("----------------------------------------");
        contentStream.newLine();

        // Items
        List<SaleItemResponseDto> items = salesInfoDto.getSaleItemResponseDtoList();
        for (SaleItemResponseDto item : items) {
            contentStream.showText(item.getName() + " (" + item.getQuantity() + " " + item.getType() + " X " + item.getPrice() + " TL)");
            contentStream.newLine();
            contentStream.showText("Toplam Fiyat: " + item.getTotalPrice() + " TL");
            contentStream.newLine();
            contentStream.newLine();
        }

        // Total
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
        log.info("Exiting method: createPdfFileSaleList with id: {}", id);
    }
}
