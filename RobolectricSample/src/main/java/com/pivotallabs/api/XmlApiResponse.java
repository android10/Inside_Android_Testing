package com.pivotallabs.api;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XmlApiResponse extends ApiResponse {
    private Document document;

    public XmlApiResponse(int httpCode) {
        super(httpCode);
    }

    @Override
    void consumeResponse(InputStream responseBody) throws IOException, SAXException, ParserConfigurationException {
        if (isSuccess()) {
            document = Xmls.getDocument(responseBody);
        }
    }

    public Document getResponseDocument() {
        return document;
    }
}
