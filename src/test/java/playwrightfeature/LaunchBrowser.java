package playwrightfeature;

import java.util.ArrayList;

import org.junit.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.cucumber.java.Before;


public class LaunchBrowser {
	
	 private Page page;
    
//    @Before
//	public void urlLaunch() {
//		Playwright playwright = Playwright.create();
//		
//		ArrayList<String> arguments = new ArrayList<>();
//		arguments.add("--start-maximized");
//		Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setArgs(arguments));
//		BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
//		Page page = browserContext.newPage();
//
//		page.navigate("https://www.tuiflyprjuat.be/flight/");
////		ElementHandle varles = page.waitForSelector("[id='cmCloseBanner']");
////        System.out.println(varles);
////        page.locator("[id='cmCloseBanner']").click();      
////        System.out.println("2222");
//
//	}
    
    public void closePrivacyPopUp() {
        System.out.println(page.waitForSelector("[id='cmCloseBanner']"));
        ElementHandle varles = page.waitForSelector("[id='cmCloseBanner']");
        System.out.println(varles);
        page.locator("[id='cmCloseBanner']").click();
}
    
    public Page getPage() {
        return page;
    }
}
