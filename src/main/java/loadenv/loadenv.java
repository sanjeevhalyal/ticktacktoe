package loadenv;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class loadenv {

    private  String hostip;
    private Document doc;

    public loadenv() {

        loadxml();
        sethostip();
    }



     private void loadxml()
    {
        try {


            Path path = FileSystems.getDefault().getPath(".env").toAbsolutePath();

            File inputFile = new File(path.toString());
            System.out.println(inputFile.getAbsolutePath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String gethostip() {
        return hostip;
    }

    public void sethostip() {
        NodeList nList = doc.getElementsByTagName("hostip");
        this.hostip = nList.item(0).getTextContent();
    }
}
