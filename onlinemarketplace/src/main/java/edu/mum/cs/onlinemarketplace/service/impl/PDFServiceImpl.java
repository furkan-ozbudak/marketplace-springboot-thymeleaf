package edu.mum.cs.onlinemarketplace.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.UserOrder;
import edu.mum.cs.onlinemarketplace.service.PDFService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public void createPDFFile(UserOrder order, List<Product> products) throws FileNotFoundException, DocumentException {

        String path = "./src/main/resources/static/files/Order-" + order.getId() + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        insertContent(document, String.format("Order number: %d-0", order.getId()), font);
        insertContent(document, String.format("Status: %s", order.getStatus().toUpperCase()), font);
        insertContent(document, String.format(".................................................."), font);
        insertContent(document, String.format("    Buyer: %s ", order.getBuyer().getName()), font);
        insertContent(document, String.format("    Email: %s", order.getBuyer().getEmail()), font);
        insertContent(document, String.format("    Shipping Address:"), font);
        insertContent(document, String.format("        %s", order.getBuyer().getShippingAddress().getStreet()), font);
        insertContent(document, String.format("        %s %s", order.getBuyer().getShippingAddress().getCity(), order.getBuyer().getShippingAddress().getState()), font);
        insertContent(document, String.format("        %s", order.getBuyer().getShippingAddress().getZipCode()), font);
        insertContent(document, String.format(" "), font);
        insertContent(document, String.format("    Seller: %s", order.getSeller().getName()), font);
        insertContent(document, String.format("    Seller Email: %s", order.getSeller().getEmail()), font);
        insertContent(document, String.format("    Seller Address:"), font);
        insertContent(document, String.format("        %s", order.getSeller().getBillingAddress().getStreet()), font);
        insertContent(document, String.format("        %s %s", order.getSeller().getBillingAddress().getCity(), order.getSeller().getBillingAddress().getState()), font);
        insertContent(document, String.format("        %s", order.getSeller().getBillingAddress().getZipCode()), font);
        insertContent(document, String.format(" "), font);
        insertContent(document, String.format("    Products:"), font);
        insertContent(document, String.format("        ID. Product............Price"), font);
        products.stream().forEach(prod -> {
            try{
                insertContent(document, String.format("        %d %s...........$%.2f", prod.getId(), prod.getName(), prod.getPrice()), font);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        insertContent(document, String.format(" "), font);
        insertContent(document, String.format("    TOTAL: $%.2f", order.getTotal()), font);
        insertContent(document, String.format(" "), font);
        insertContent(document, String.format("    Create Date: %s", order.getCreateDate().toString()), font);
        if(order.getDeliveryDate() != null)
            insertContent(document, String.format("    Shipping Date: %s", order.getDeliveryDate().toString()), font);

        document.close();
    }

    public void insertContent(Document document, String content, Font font) throws DocumentException {
        Paragraph paragraph = new Paragraph(content, font);
        document.add(paragraph);
    }
}
