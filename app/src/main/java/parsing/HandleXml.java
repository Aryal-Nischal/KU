package parsing;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HandleXml {
    private List link;
    private List title;
    private List modify;
    private List summary;
    private List subject;
    private List content;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXml(){}

    public HandleXml(String url){
        this.urlString = url;
        link = new ArrayList();
        title = new ArrayList();
        modify = new ArrayList();
        summary = new ArrayList();
        subject = new ArrayList();
        content = new ArrayList();
    }

    public List getLink(){return link;}
    public List getTitle(){
        return title;
    }
    public List getModif(){
        return modify;
    }
    public List getSummary(){return summary;}
    public List getSubject(){return subject;}
    public List getContent(){return content;}


    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){


                    case XmlPullParser.START_TAG:
////////////////////////////////////////////////////////////attribute bata value chaincha vane keep here////////////////////////////////////
                        if(name.equalsIgnoreCase("link")){
                            link.add(myParser.getAttributeValue(null,"href"));
                        }
                        else{}
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equalsIgnoreCase("title")){
                            title.add(text);
                        }
                        if(name.equalsIgnoreCase("modified")){
                            modify.add(text);
                        }
                        if(name.equalsIgnoreCase("summary")){
                            summary.add(text);
                        }
                        if(name.equalsIgnoreCase("dc:subject")){
                            subject.add(text);
                        }
                        if(name.equalsIgnoreCase("content")){
                            content.add(text);
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}