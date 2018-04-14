package modify_xml_file;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ModifyXMLDOM {
    public static void main(String[] args) {
        String filePath = "src/write_xml_file/employees.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try{
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            //update attribute value
            updateAttributeValue(doc);

            //update Element value
            updateElementValue(doc);

            //delete element
            deleteElement(doc);

            //add new element
            addElement(doc);

            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/modify_xml_file/employee_updated.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void updateAttributeValue(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element employee;
        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            employee = (Element) employees.item(i);
            String gender = employee.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue();
            if(gender.equalsIgnoreCase("male")){
                //prefix id attribute with M
                employee.setAttribute("id", "M"+employee.getAttribute("id"));
            }else{
                //prefix id attribute with F
                employee.setAttribute("id", "F"+employee.getAttribute("id"));
            }
        }
    }


    private static void updateElementValue(Document doc){
        NodeList employees = doc.getElementsByTagName("Employee");
        Element employee;
        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            employee = (Element) employees.item(i);
            Node name = employee.getElementsByTagName("name").item(0).getFirstChild();
            name.setNodeValue(name.getNodeValue().toUpperCase());
        }
    }

    private static void deleteElement(Document doc){
        NodeList employees = doc.getElementsByTagName("Employee");
        Element employee;
        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            employee = (Element) employees.item(i);
            Node genderNode = employee.getElementsByTagName("gender").item(0);
            employee.removeChild(genderNode);
        }
    }

    private static void addElement(Document doc){
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;

        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            emp = (Element) employees.item(i);
            Element salaryElement = doc.createElement("salary");
            salaryElement.appendChild(doc.createTextNode("10000"));
            emp.appendChild(salaryElement);
        }
    }
}
