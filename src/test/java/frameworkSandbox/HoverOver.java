package frameworkSandbox;

import framework.Commands;
import framework.Drivers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class HoverOver {

    public static WebDriver d;

    @BeforeTest
    public static void beforeTest(){
//        File f = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
//        d = Drivers.sauceLabsConfig(f, PropsSystem.chrome, "Windows 10", "https://www.amazon.com/");
//        d.manage().window().maximize();

        File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        d = Drivers.driverInit("Windows 10", "chrome",
                "https://www.amazon.com/", null, null, false, false);
        PageFactory.initElements(d, HoverOver.class);
    }

    @Test
    public void testSauce() throws Exception {
        Commands.waitForSecs(5000);
//        Commands.waitForURL(d, "newegg");
        Commands.waitForURL(d, "amazon");
        hoverAccountLists();
        clickSignIn();
        waitForPageLoad();
    }

    @FindBy(className = "nav-line-2")
    public static List<WebElement> headerOpts;

    public static void hoverAccountLists(){
        String desc = "Hover over departments";
        Commands.hoverOver(d, headerOpts.get(2), desc);
    }

    @FindBy(className = "nav-action-inner")
    public static List<WebElement> actions;

    public static void clickSignIn(){
        String desc = "Click on sign in";
        Commands.click(actions.get(0), desc);
    }

    @FindBy(name = "email")
    public static WebElement email;

    public static void waitForPageLoad(){
        String desc = "Wait for page load";
        Commands.waitForURL(d, "signin");
        Commands.waitForEl(d, email);
    }

//    @SuppressWarnings("resource")
//    public static String[] readFile(File f, String gatewayApp){
//        String[] kv = new String[2];
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(f));
//            String readLine = "";
//
//            System.out.println("Reading file using Buffered Reader");
//
//            while ((readLine = br.readLine()) != null) {
//                String[] line = readLine.split("=");
//
//                switch(gatewayApp){
//                    case "sauceLabs":
//                        if(line[0].equals("username")){
//                            kv[0] = line[1];
//                        } else if (line[0].equals("accessKey")){
//                            kv[1] = line[1];
//                        } break;
//                    case "practiTest":
//                        if(line[0].equals("practiTestDevEmail")){
//                            kv[0] = line[1];
//                        } else if (line[0].equals("practiTestAccessKey")){
//                            kv[1] = line[1];
//                        } break;
//                    default: throw new NullPointerException();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (!kv[0].equals(null) && !kv[1].equals(null)){
//            return kv;
//        } else {
//            return null;
//        }
//    }
}
