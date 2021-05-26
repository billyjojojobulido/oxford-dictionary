import java.util.HashMap;
import java.util.Map;

import controller.HTTPManager;
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
        model = new ModelFacade();
        database = new HashMap<>();
        mockDB = mock(Database.class);
        mockHTTP = mock(HTTPManager.class);
    }

    @Test
    public void verifyLogInRequest(){
        model.logIn("A", "B");
        verify(mockHTTP, atLeast(1)).credentialIsValid("A", "B");
    }

    @Test
    public void verifySendEmail(){
        model.sendEmail("apikey", "abc@abc.com", "abc@abc.com", "abc@abc.com",
                "bc", "ac", "subject", "bc");
        verify(mockHTTP, atLeast(1)).sendEmail("apikey", "abc@abc.com",
                "abc@abc.com", "abc@abc.com", "bc", "ac", "subject",
                "text/plain", "some sort of email","bc");
    }

    @Test
    public void verifyGetWordCheckCache(){
        model.getWord("word");
        verify(mockDB, atLeast(1)).entityExists("word");
    }

    @Test
    public void verifyGetWordGetFromCache(){
        model.getWord("word");
        when(mockDB.entityExists("word")).thenReturn(null);
        verify(mockHTTP, atLeast(1)).getWord("id","key", "entries","en-us","word");
    }

    @Test
    public void verifyUpdateCache(){
        JSONParser parser = new JSONParser();
        try{
            JSONObject info = (JSONObject) parser.parse("{\"a\":\"info\"}");
            model.updateDB("a", info);
            verify(mockDB, atLeast(1)).updateEntity("a", info);
        } catch (ParseException e){
            fail();
        }
    }


}
