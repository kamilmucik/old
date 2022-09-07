package pl.estrix;

import org.junit.Ignore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import pl.estrix.app.lib.download.DownloadUtil;

import javax.xml.transform.TransformerException;
import java.io.File;

@Ignore
public class TransformXMLTest {

//    private DownloadUtil downloadUtil;

    @Before
    public void before() throws Exception {
//        downloadUtil = new DownloadUtil();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void shouldTransformMegasmykFile(){
//        try {
//            downloadUtil.downloadFile("https://www.megasmyk.pl/export/millymally_pelny.xml","c:\\test\\megasmyk.xml");
//            ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource("megasmyk.xsl").getFile());
//
//            downloadUtil.transformFile(
//                    "c:\\test\\megasmyk.xml",
//                    file.getAbsolutePath(),
//                    "c:\\test\\megasmyk_out.xml");
//
//            downloadUtil.parseFile("c:\\test\\megasmyk_out.xml");
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void shouldTransformHurtowniakFile(){
//        try {
////            downloadUtil.downloadFile("https://api.ikonka.com.pl/api2/index.php/request/?format=xml&hash=a357585c63b3c99262ea269a0db4cb3862917562&variant=1","c:\\test\\ikonka.xml");
//            ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource("ikonka.xsl").getFile());
//////
//            downloadUtil.transformFile(
//                    "c:\\test\\ikonka.xml",
//                    file.getAbsolutePath(),
//                    "c:\\test\\ikonka_out.xml");
//
////            file = new File(classLoader.getResource("gatito.xsl").getFile());
////            downloadUtil.transformFile(
////                    "c:\\test\\gatito.xml",
////                    file.getAbsolutePath(),
////                    "c:\\test\\gatito_out.xml");
//
////            downloadUtil.parseFile("c:\\test\\hurtowniak_out.xml");
////            downloadUtil.parseFile("c:\\test\\gatito_out.xml");
//            downloadUtil.parseFile("c:\\test\\ikonka_out.xml");
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }


    }
}
