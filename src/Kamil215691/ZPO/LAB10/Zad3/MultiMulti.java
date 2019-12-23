package Kamil215691.ZPO.LAB10.Zad3;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;

public class MultiMulti extends LottoParser {
    MultiMulti(String uri){
        super(uri, "numbers_in_list ", "numbers_in_list_new_line", "numbers_in_list  numbers_in_list_multi_multi_plus" );
    }
    private final String extraTag = "pierwsza_liczba_w_nowym_wierszu ";
    private final String anotherTag = "pierwsza_liczba_w_nowym_wierszu  numbers_in_list_multi_multi_plus";
    protected ArrayList<Integer> getResultForDocument(Document doc, XPath xPath, String dateStr, List<String> tags) throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        for (String tag : tags) {
            NodeList nodeList;
            if (tag.equals("numbers_in_list ") || tag.equals( "numbers_in_list  numbers_in_list_multi_multi_plus")) {
                nodeList = ((NodeList) xPath.compile("//ul[li[@class='" + dateTag + "' and contains(., '" + dateStr + "')]]/div/li[@class='" + tag + "']/text()")
                        .evaluate(doc, XPathConstants.NODESET));
            } else {
                nodeList = ((NodeList) xPath.compile("//ul[li[@class='" + dateTag + "' and contains(., '" + dateStr + "')]]/div/li[@class='" + tag + "']/span[@class='"+extraTag+"' or @class='"+anotherTag+"']/text()")
                        .evaluate(doc, XPathConstants.NODESET));
            }
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node1 = nodeList.item(i);
                list.add(Integer.parseInt(node1.getNodeValue().trim()));
            }
        }
        return list;
    }
}
