package write_xml_file;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLWriterDOM {
    public static void main(String[] args) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try{
            dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            //add elements to document
            Element rootElement = document.createElementNS("https://www.journaldev.com/employee", "Employees");
            //append root element to document
            document.appendChild(rootElement);

            //append child element to root element
            rootElement.appendChild(getEmployee(document, "1", "Dmitry", "40", "Java Developer", "Male"));
            rootElement.appendChild(getEmployee(document, "2", "Rezida", "36", "FrontEnd Developer", "Female"));

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            //write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("src/write_xml_file/employees.xml"));

            //write data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Node getEmployee(Document doc, String id,
                                    String name, String age,
                                    String role, String gender) {

        Element employee = doc.createElement("Employee");
        employee.setAttribute("id", id);
        employee.appendChild(getEmployeeElements(doc, employee, "name", name));
        employee.appendChild(getEmployeeElements(doc, employee, "age", age));
        employee.appendChild(getEmployeeElements(doc, employee, "role", role));
        employee.appendChild(getEmployeeElements(doc, employee, "gender", gender));

        return employee;
    }
    //utility method to create text node
    private static Node getEmployeeElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
