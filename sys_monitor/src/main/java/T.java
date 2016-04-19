import com.jarvis.supporter.logger.Log4jAdapter;

/**
 * Created by Jarvis on 4/18/16.
 */
public class T {
private static Log4jAdapter logger = Log4jAdapter.getLog4jAdapter(this.getClass());

    public  static void main(String[] args){
        logger.debug("test ... ");

    }


}
