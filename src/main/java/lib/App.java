package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.print.DocFlavor.URL;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        try {
            
            getNodeByClass();
            getNodesByFullPath();                     
            getSingleNodeByFullPath();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    /**
     * 用物件方式，逐一尋訪子節點，以及取得特定節點資訊。
     * @throws Exception
     */
    public static void getNodeByClass() throws Exception {
        try {
            int book_index=0;
            SAXReader reader = new SAXReader();// 建立解析物件
            Document document = reader.read(new File("src\\main\\resource\\book.xml"));// 載入xml文件
            Element book_root = document.getRootElement();// 得到載入xml文件的根標籤
            Iterator iterator = book_root.elementIterator();// 載入跟標籤以下的所有標籤，返回值是一個迭代器物件
            while (iterator.hasNext()) {// 開始遍歷，呼叫hasNext()的方法，判斷是否有下一個
                book_index++;

                Element book = (Element) iterator.next();// 獲取根標籤以下所有子節點，
                List<Attribute> book_attr = book.attributes();// 獲取跟標籤以下的子標籤的屬性值
                for (Attribute attribute : book_attr) {// 得到所有子標籤的屬性值進行遍歷
                    
                    System.out.println(attribute.getName() + ":" + attribute.getValue());// 得到屬性名和對應的屬性值，即key-value
                }
                Iterator book_node_iter = book.elementIterator();// 獲取根節點以下的子子節點，

                //根據名稱取得子element 
                System.out.println("--Book name:" +   book.element("name").getStringValue());
                while (book_node_iter.hasNext()) {// 遍歷所有的子子節點
                    Element book_node = (Element) book_node_iter.next();// 獲取子子節點 Element                    
                    System.out.println(book_node.getName() + ":" + book_node.getStringValue());
                    String path = book_node.getPath();
                    System.out.println("xpath:" + path);
                    
                } // 獲取標籤名和標籤中的Text值 
                Iterator idxs = book.element("indexs").elementIterator();
                System.out.println("**** indexs ****");
                while(idxs.hasNext()){
                    Element idx = (Element)idxs.next();
                    System.out.println("page" + ":" + idx.element("page").getStringValue());
                    System.out.println("title" + ":" + idx.element("title").getStringValue());
                }
                System.out.println("============第"+(book_index)+"本結束==========");              
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根據節點路徑取得該節點的數值
     * @throws DocumentException
     */
    public static void getNodesByFullPath() throws DocumentException {
        String path = "/books/book/name";
        SAXReader reader = new SAXReader();// 建立解析物件
        Document document = reader.read(new File("src\\main\\resource\\book.xml"));// 載入xml文件        
        List<Node> list = document.selectNodes("/books/book/name");
        for (Node node : list) {
            //node是每一個name元素
            //得到name元素裡面的值
            String s = node.getText();
            System.out.println(s);
        }
    }

    /**
     * get single node，如果有多個相同名稱的node時候，只會取第一個。
     * @throws DocumentException
     */
    public static void getSingleNodeByFullPath() throws DocumentException {
        String path = "/books/book/name";
        SAXReader reader = new SAXReader();// 建立解析物件
        Document document = reader.read(new File("src\\main\\resource\\book.xml"));// 載入xml文件
        Node node = document.selectSingleNode(path);
        System.out.println(String.format("%s:%s", node.getName(), node.getStringValue()));
        
    }
}
