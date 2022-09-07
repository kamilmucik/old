package pl.estrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

@Ignore
public class JMeterTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void jMeterTest() throws IOException {
        System.out.println("jMeterTest");
        String ROOT = "c:\\app\\apache-jmeter-5.1.1\\apache-jmeter-5.1.1\\";
        String jmeterProperties = ROOT + "bin\\jmeter.properties";
        String outputFileJtl = ROOT + "..\\jmeter.jtl";
        String logFile = ROOT + "..\\jmeter.log";

//        JMeter jmeter = new JMeter();
//        String[] arguments = { "-n", "-t", "c:\\es\\workspace\\estrix-javafx\\020-store-sync2\\trunk\\daemon-application\\src\\main\\resources\\001_e_strix_test.jmx", "-p", jmeterProperties, "-d", ROOT, "-l", outputFileJtl, "-j", logFile };

        String line;
        OutputStream stdin = null;
        InputStream stderr = null;
        InputStream stdout = null;

        // launch EXE and grab stdin/stdout and stderr
//        Process process = Runtime.getRuntime ().exec ("/folder/exec.exe");
//        Process process = Runtime.getRuntime ().exec (ROOT + "bin\\jmeter.bat -n -t c:\\es\\workspace\\estrix-javafx\\020-store-sync2\\trunk\\daemon-application\\src\\main\\resources\\001_e_strix_test.jmx -p " + jmeterProperties + " -d " + ROOT + " -l " + outputFileJtl + " -j " + logFile);
//        stdin = process.getOutputStream ();
//        stderr = process.getErrorStream ();
//        stdout = process.getInputStream ();

        // "write" the parms into stdin
//        line = "param1" + "\n";
//        stdin.write(line.getBytes() );
//        stdin.flush();
//
//        line = "param2" + "\n";
//        stdin.write(line.getBytes() );
//        stdin.flush();
//
//        line = "param3" + "\n";
//        stdin.write(line.getBytes() );
//        stdin.flush();

        stdin.close();

        // clean up if any output in stdout
        BufferedReader brCleanUp =
                new BufferedReader (new InputStreamReader (stdout));
        while ((line = brCleanUp.readLine ()) != null) {
            System.out.println ("[Stdout] " + line);
        }
        brCleanUp.close();

        // clean up if any output in stderr
        brCleanUp =
                new BufferedReader (new InputStreamReader (stderr));
        while ((line = brCleanUp.readLine ()) != null) {
            System.out.println ("[Stderr] " + line);
        }
        brCleanUp.close();
    }
//    @Test
//    public void jMeterTest2() throws IOException {
//        System.out.println("jMeterTest");
//        String ROOT = "c:\\app\\apache-jmeter-5.1.1\\apache-jmeter-5.1.1\\";
//        String jmeterProperties = ROOT + "bin\\jmeter.properties";
//        String outputFileJtl = ROOT + "..\\jmeter.jtl";
//        String logFile = ROOT + "..\\jmeter.log";
//
//        JMeter jmeter = new JMeter();
//        String[] arguments = { "-n", "-t", "c:\\es\\workspace\\estrix-javafx\\020-store-sync2\\trunk\\daemon-application\\src\\main\\resources\\001_e_strix_test.jmx", "-p", jmeterProperties, "-d", ROOT, "-l", outputFileJtl, "-j", logFile };
////        String[] arguments = { "-n", "-t", "c:\\es\\workspace\\estrix-javafx\\020-store-sync2\\trunk\\daemon-application\\src\\main\\resources\\001_e_strix_test.jmx", "-p", jmeterProperties, "-d", ROOT};
//        jmeter.start(arguments);
//
//        // Waiting for JMeter ending
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(baos));
//        boolean running = true;
//        do {
//            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                running = !line.equals("... end of run");
//            }
//        } while (running);
//
//
//    }
}
