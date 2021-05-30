import java.util.HashMap;
import java.util.Map;

import model.HTTPManager;
import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.Mockito.*;

public class ModelFacadeTest {

    /** Service under test */
    private Model model = null;

    /** Mock */
    private Map<String, String> database;
    private Database mockDB;
    private HTTPManager mockHTTP;

    @Before
    public void init(){
        database = new HashMap<>();
        mockDB = mock(Database.class);
        mockHTTP = mock(HTTPManager.class);
        model = new ModelFacade(mockHTTP, mockDB);
    }

    @Test
    public void verifyLogInRequest(){
        boolean test = model.logIn("a", "b");
        verify(mockHTTP, atLeast(1)).authenticate("a", "b");
    }

    @Test
    public void verifyWordRequest(){
        model.getWordFromAPI("word");
        verify(mockHTTP, atLeast(1)).getWord(anyString());
    }

    @Test
    public void verifySendEmailRequest(){
        model.sendEmail("someString","someString","someString","someString",
                "someString","someString","someString","someString","someString");
        verify(mockHTTP, atLeast(1)).sendEmail("someString","someString",
                "someString","someString","someString","someString","someString","text/plain","someString","someString");
    }

    @Test
    public void verifyGetWordCheckCache(){
        model.getCachedEntry("word");
        verify(mockDB, atLeast(1)).entityExists("word");
    }

    @Test
    public void verifyGetWordGetFromCache() {
        model.getWordFromAPI("word");
        when(mockDB.entityExists("word")).thenReturn("true");
        verify(mockHTTP, atLeast(1)).getWord(anyString());
    }

    @Test
    public void verifyUpdateCache(){
        JSONParser parser = new JSONParser();
        try{
            JSONObject info = (JSONObject) parser.parse("{\"a\":\"info\"}");
            model.updateDB("a", info);
            verify(mockDB, atLeast(1)).updateEntity(anyString(), anyString());
        } catch (ParseException e){
            fail();
        }
    }


}
