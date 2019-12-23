package Kamil215691.ZPO.LAB10.Zad3;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;

public class EkstraPensja extends LottoParser {

    EkstraPensja(String url){
        super(url,  "numbers_in_list ", "tsn_number_in_list");
    }

    protected ArrayList<Integer> getResultForDocument(Document doc, XPath xPath, String dateStr, List<String> tags) throws Exception{
        ArrayList<Integer> list = new ArrayList<>();
        for(String tag : tags){
            NodeList nodeList;
            if(tag.equals("numbers_in_list ")) {
                nodeList = ((NodeList) xPath.compile("//ul[li[@class='" + dateTag + "' and contains(., '" + dateStr + "')]]/li[@class='" + tag + "']/text()")
                        .evaluate(doc, XPathConstants.NODESET));
            }
            else {
                nodeList = ((NodeList) xPath.compile("//ul[li[@class='" + dateTag + "' and contains(., '" + dateStr + "')]]/li[@class='" + tag + "']/strong/text()")
                        .evaluate(doc, XPathConstants.NODESET));
            }
            for(int i = 0; i < nodeList.getLength(); ++i){
                Node node1 = nodeList.item(i);
                list.add(Integer.parseInt(node1.getNodeValue().trim()));
            }
        }
        return list;
    }

}
