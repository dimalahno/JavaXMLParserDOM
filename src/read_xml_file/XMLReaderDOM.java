package read_xml_file;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
DOM Parser are good for small XML documents but since it loads complete
XML file into memory, it’s not good for large XML files.
*/

public class XMLReaderDOM {
    public static void main(String[] args) {
        String filePath = "src/read_xml_file/employee.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try{
            dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            System.out.println("Root element: " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("Employee");

            //now XML is loaded as Document in memory, lets convert it to Object List
            List<Employee> employeeList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                employeeList.add(getEmployee(nodeList.item(i)));
            }

            employeeList.forEach(System.out::println);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Employee getEmployee(Node node){
        Employee employee = new Employee();
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            employee.setAge(Integer.parseInt(getTagValue("age", element)));
            employee.setName(getTagValue("name", element));
            employee.setGender(getTagValue("gender", element));
            employee.setRole(getTagValue("role", element));
            employee.settNumber(getTagValue("tNumber", element));
        }
        return employee;
    }

    private static String getTagValue(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}