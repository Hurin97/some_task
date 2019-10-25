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
import java.sql.Connection;
import java.sql.SQLException;

public class dom {
    public dom() {
    }

    public void toDbWithDOM(String path) throws IOException, SAXException, ParserConfigurationException, SQLException {
        db db =new db();
        Connection conn =db.getDBConnection();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        document.getDocumentElement().normalize();
        NodeList nodeList= document.getElementsByTagName("order");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement =(Element)node;
                String sum = eElement.getElementsByTagName("summa").item(0).getTextContent();
                NodeList servList =eElement.getElementsByTagName("services");
                for (int tmp = 0; tmp < servList.getLength(); tmp++){
                    Node node1 = servList.item(tmp);
                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                        Element eeElement=(Element) node1;
                        int srv=0;
                        int isClosed=(Boolean.parseBoolean(eeElement.getAttribute("isClosed")))?1:0;
                        String serv_id=eeElement.getElementsByTagName("serv_id").item(0).getTextContent();
                        String bic=eeElement.getElementsByTagName("bic").item(0).getTextContent();
                        String schet=eeElement.getElementsByTagName("schet").item(0).getTextContent();
                        String corr_schet=eeElement.getElementsByTagName("corr_schet").item(0).getTextContent();
                        String sys_mes=eeElement.getElementsByTagName("sys_message").item(0).getTextContent();
                        NodeList pars=eeElement.getElementsByTagName("par");
                        for (int i=0;i<pars.getLength();i++) {
                            Node node2= pars.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element eeeElement=(Element) node2;
                                String step=eeeElement.getAttribute("step");
                                String name=eeeElement.getAttribute("name");
                                String fullname=eeeElement.getAttribute("fullname");
                                String comment=eeeElement.getAttribute("comment");
                                int isEditable= (Boolean.parseBoolean(eeeElement.getAttribute("isEditable")))? 1 : 0;
                                int isScannable=(Boolean.parseBoolean(eeeElement.getAttribute("isScannable")     ))?1:0;
                                int isVisible=  (Boolean.parseBoolean(eeeElement.getAttribute("isVisible")       ))?1:0;
                                int isRequired= (Boolean.parseBoolean(eeeElement.getAttribute("isRequired")      ))?1:0;
                                int isPrinted=  (Boolean.parseBoolean(eeeElement.getAttribute("isPrinted")       ))?1:0;
                                int isVOL=      (Boolean.parseBoolean(eeeElement.getAttribute("isValidateOnLine")))?1:0;
                                String type=eeeElement.getAttribute("type");
                                String min_l=eeeElement.getAttribute("min_length");
                                String max_l=eeeElement.getAttribute("max_length");
                                String double_inp=eeeElement.getAttribute("double_input");
                                String value=eeeElement.getAttribute("value");
                                String reg_exp=eeeElement.getAttribute("reg_exp");
                                String from_debt=eeeElement.getAttribute("from_debt");
                                NodeList par_list =eeeElement.getElementsByTagName("par_list");
                                for (int j=0; j<par_list.getLength();j++) {
                                    Node node3 =par_list.item(j);
                                    if (node3.getNodeType() == Node.ELEMENT_NODE) {
                                        Element eeeeElement=(Element) node3;
                                        String value_=eeeeElement.getAttribute("value");
                                        try {
                                            int par_l=db.insertIntoPar_list(conn,value_);
                                            System.out.println("Par_list record №"+par_l);
                                        } catch (SQLException se) {
                                            se.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                try{
                                    int par=db.insertIntoPar(conn,step,name,fullname,comment,
                                            isEditable, isScannable,isVisible,isRequired,
                                            isPrinted,isVOL,type,min_l,max_l,
                                            double_inp,value,reg_exp,from_debt);
                                    System.out.println("Par record №"+par);
                                     srv=db.insertIntoServ(conn,serv_id,isClosed,bic,schet,corr_schet,par,sys_mes);
                                    System.out.println("Serv record №"+srv);
                                }
                                catch(SQLException se){
                                    se.printStackTrace();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            int ord=db.insertIntoOrder(conn, srv, sum);
                            System.out.println("Order record №"+ord);
                        }
                        catch(SQLException se){
                            se.printStackTrace();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }
}
