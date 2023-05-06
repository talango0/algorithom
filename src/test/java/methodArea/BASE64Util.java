package methodArea;

import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author mayanwei
 * @date 2023-04-17.
 */
public class BASE64Util{
    private static Logger log = Logger.getLogger(BASE64Util.class.getName());
    public String encode64(String message) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String result = base64Encoder.encode(message.getBytes());
        log.info(message);
        log.info(result);
        return result;
    }

    public static void main(String[] args) throws IOException {
        BASE64Util base64Util = new BASE64Util();
        String helloWorld = base64Util.encode64("hello world");
        System.in.read();
    }
}
