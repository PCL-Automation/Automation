
import org.apache.commons.io.FileUtils;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.screenshot;


public class AfterStepHook extends RunListener  {
    private static final Logger LOGGER = Logger.getLogger( AfterStepHook.class.getName() );
    public static final GifAnimator gifAnimator = new GifAnimator();
    public String toHex(String arg) {
        if (arg == null || arg.length() < 2){
            return arg;
        }
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            LOGGER.info("AfterStepHook.MD4 : Exception:"+e.getMessage());
            return md5;
        }
    }
    @Override
    public void testFinished(Description description) throws Exception {
        String stepname = description.getMethodName();
        if (stepname == null){
            stepname = "No Step";
        }
        String details = "img_"+MD5(stepname);
        try {
            screenshot(details);
            byte[] screenshot = read(details+".png");
            if (screenshot.length > 10) {
                gifAnimator.addImage(stepname, screenshot);
            }
        } catch (WebDriverException | ClassCastException wde) {
            LOGGER.log(Level.SEVERE,"Error Taking Screenshot:",wde);
        }
    }
    /** Read the given binary file, and return its contents as a byte array.*/
    byte[] read(String aInputFileName){
        File file = new File("./build/reports/tests/"+aInputFileName);
        try {
            return FileUtils.readFileToByteArray(file);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Error reading file: ",ex);
        }
        return new byte[0];
    }
}
