package edu.mum.cs.onlinemarketplace.service;

import com.itextpdf.text.DocumentException;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.UserOrder;

import java.io.FileNotFoundException;
import java.util.List;

public interface PDFService {

    void createPDFFile(UserOrder order, List<Product> products) throws FileNotFoundException, DocumentException;
}
