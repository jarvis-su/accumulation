package jarvis.samples.xml.sax;

/**
 * Created by Jarvis on 4/21/16.
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXParser {

    class BookHandler extends DefaultHandler {
        private List<String> nameList;
        private boolean title = false;

        public List<String> getNameList() {
            return nameList;
        }

        // Called at start of an XML document
        @Override
        public void startDocument() throws SAXException {
            System.out.println("Start parsing document...");
            nameList = new ArrayList<String>();
        }

        // Called at end of an XML document
        @Override
        public void endDocument() throws SAXException {
            System.out.println("End");
        }

        /**
         * Start processing of an element.
         *
         * @param uri       Namespace URI
         * @param localName The local name, without prefix
         * @param qName     The qualified name, with prefix
         * @param atts      The attributes of the element
         */
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes atts) throws SAXException {
            // Using qualified name because we are not using xmlns prefixes here.
            if (qName.equals("title")) {
                title = true;
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                throws SAXException {
            // End of processing current element
            if (title) {
                title = false;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            // Processing character data inside an element
            if (title) {
                String bookTitle = new String(ch, start, length);
                System.out.println("Book title: " + bookTitle);
                nameList.add(bookTitle);
            }
        }

    }

    public static void main(String[] args) throws SAXException, IOException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        BookHandler bookHandler = (new SAXParser()).new BookHandler();
        parser.setContentHandler(bookHandler);
        parser.parse("books.xml");
        System.out.println(bookHandler.getNameList());
    }
}
