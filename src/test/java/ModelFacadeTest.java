import java.util.HashMap;
import java.util.Map;

import java.controller.HTTPManager;
import model.*;
import org.junit.Before;
import org.junit.Test;

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





}
