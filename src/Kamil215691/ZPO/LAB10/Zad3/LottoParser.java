package Kamil215691.ZPO.LAB10.Zad3;

import Kamil215691.ZPO.LAB6.Zad2.CustomMergeUtilities;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Whitelist;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class LottoParser {

    private final static String lottoUrlPart = "http://megalotto.pl/wyniki/lotto/losowania-z-roku-";
    private final static String miniLottoUrlPart = "http://megalotto.pl/wyniki/mini-lotto/losowania-z-roku-";
    private final static String lottoPlusUrlPart = "http://megalotto.pl/wyniki/lotto-plus/losowania-z-roku-";
    private final static String multiMultiUrlPart = "http://megalotto.pl/wyniki/multi-multi/losowania-z-roku-";
    private final static String ekstraPensjaUrlPart = "http://megalotto.pl/wyniki/ekstra-pensja/losowania-z-roku-";
    private final static String ekstraPremiaUrlPart = "http://megalotto.pl/wyniki/ekstra-premia/losowania-z-roku-";
    private final static String euroJackPotUrlPart = "http://megalotto.pl/wyniki/eurojackpot/losowania-z-roku-";
    private final static String kaskadaUrlPart = "http://megalotto.pl/wyniki/kaskada/losowania-z-roku-";
    private final static String superSzansaUrlPart = "http://megalotto.pl/wyniki/super-szansa/losowania-z-roku-";

    final protected List<String> tags;
    final protected String dateTag = "date_in_list";
    final protected String gameUrl;
    protected Document htmlDocument;

    protected LottoParser(String url, String...tags){
        gameUrl = url;
        this.tags = Arrays.asList(tags);
    }

    public static LottoParser of(String className) throws Exception{
        Class lottoClass = Class.forName("Kamil215691.ZPO.LAB10.Zad3."+className);
        Constructor constructor = lottoClass.getDeclaredConstructor(String.class);
        final String replace = className.replaceFirst("^[A-Z]", ((Character) ((char) (className.charAt(0) - 'A' + 'a'))).toString());
        return (LottoParser) constructor.newInstance(LottoParser.class.getDeclaredField(replace + "UrlPart").get(null));
    }

    public Document getHtml(String postfix) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        String uri = this.gameUrl + postfix;
        URL src = new URL(uri);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(src.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine);
        }catch(IOException io){
            throw new IncorrectDateException("Incorrect url");
        }

        String html = stringBuilder.toString();
        org.jsoup.nodes.Document doc = Jsoup.parse(Jsoup.clean(html, uri, Whitelist.relaxed().addAttributes("div", "class").addAttributes("li", "class").addAttributes("span", "class")));
        (doc).outputSettings().escapeMode(Entities.EscapeMode.xhtml).syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml).charset(StandardCharsets.UTF_8);
        W3CDom dom = new W3CDom();
        return dom.fromJsoup(doc);
    }

    public static class IncorrectDateException extends RuntimeException{

        public IncorrectDateException(String provided_dates_are_invalid) {
            super(provided_dates_are_invalid);
        }
    }

    protected ArrayList<Integer> getResultForDocument(Document doc, XPath xPath, String dateStr, List<String> tags) throws Exception{
        ArrayList<Integer> list = new ArrayList<>();
        for(String tag : tags){
            NodeList nodeList = ((NodeList) xPath.compile("//ul[li[@class='"+dateTag+"' and contains(., '"+dateStr+"')]]/li[@class='"+ tag +"']/text()")
                    .evaluate(doc, XPathConstants.NODESET));
            for(int i = 0; i < nodeList.getLength(); ++i){
                Node node1 = nodeList.item(i);
                list.add(Integer.parseInt(node1.getNodeValue().trim()));
            }
        }
        return list;
    }

    public ArrayList<Integer> getResultFromDate(LocalDate date) throws Exception {
        htmlDocument = getHtml(String.valueOf(date.getYear()));
        XPath xPath = XPathFactory.newInstance().newXPath();
        String dateStr = String.format("%02d-%02d-%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        return getResultForDocument(htmlDocument, xPath, dateStr, tags);
    }

    public HashMap<Integer, Integer> getHistogramFromYear(int year) throws Exception{
        ArrayList<LocalDate> list = getDatesFromYear(year);
        ArrayList<Integer> integers = getResultFromListOfDates(list);
        return computeHistogram(integers);
    }

    public HashMap<Integer, Integer> getHistogramBetweenDates(LocalDate startDate, LocalDate endDate) throws Exception{
        if(startDate.isAfter(endDate)) throw new IncorrectDateException("Provided dates are invalid");
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i = startDate.getYear(); i <= endDate.getYear(); ++i){
            ArrayList<LocalDate> list = new ArrayList<>();
            htmlDocument = getHtml(String.valueOf(i));
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile("//ul/li[@class='"+dateTag+"' ]/text()").evaluate(htmlDocument, XPathConstants.NODESET);
            for(int j = 0; j < nodeList.getLength(); ++j){
                Node node1 = nodeList.item(j);
                DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(node1.getNodeValue().trim(), formatter);
                if(date.equals(startDate) || date.equals(endDate) || (date.isAfter(startDate) && date.isBefore(endDate))){
                    list.add(date);
                }
            }
            integers.addAll(getResultFromListOfDates(list));
        }
        return computeHistogram(integers);
    }

    protected ArrayList<Integer> getResultFromListOfDates(ArrayList<LocalDate> dates){
        ArrayList<Integer> integers = new ArrayList<>();
        XPath xPath = XPathFactory.newInstance().newXPath();
        dates.forEach(localDate -> {
            String dateStr = String.format("%02d-%02d-%d", localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
            try {
                integers.addAll(getResultForDocument(htmlDocument, xPath, dateStr, tags));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return integers;
    }

    protected HashMap<Integer, Integer> computeHistogram(ArrayList<Integer> src){
        HashMap<Integer, Integer> out = new HashMap<>();
        src.forEach(integer -> CustomMergeUtilities.mergeUsingContains(out, integer, 1, Integer::sum));
        return out;
    }

    protected ArrayList<LocalDate> getDatesFromYear(Integer year) throws Exception{
        htmlDocument = getHtml(String.valueOf(year));
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile("//ul/li[@class='"+dateTag+"' ]/text()").evaluate(htmlDocument, XPathConstants.NODESET);
        ArrayList<LocalDate> list = new ArrayList<>();
        for(int i = 0; i < nodeList.getLength(); ++i){
            Node node1 = nodeList.item(i);
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
            list.add(LocalDate.parse(node1.getNodeValue().trim(), formatter));
        }
        return list;
    }
}