package factory;

 

import java.nio.file.Paths;
import java.util.ArrayList;

import com.microsoft.playwright.*;
 

public class DriverFactory {
    public Browser browser;
    public static BrowserContext context;
    public static Page page;

 

    public static ThreadLocal<Page> threadLocalDriver = new ThreadLocal<>(); //For Parallel execution
    public static ThreadLocal<BrowserContext> threadLocalContext = new ThreadLocal<>();

 

    //Launches Browser as set by user in config file
    public Page initDriver() {
        BrowserType browserType = null;
//        boolean headless = Boolean.valueOf(WebActions.getProperty("headless"));
//        switch (browserName) {
//            case "firefox":
//                browserType = Playwright.create().firefox();
//                browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless));
//                break;
//            case "chrome":
//                browserType = Playwright.create().chromium();
//                browser = browserType.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
//                break;
//            case "webkit":
//                browserType = Playwright.create().webkit();
//                browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless));
//                break;
//        }
        ArrayList<String> arguments = new ArrayList<>();
		arguments.add("--start-maximized");
        browserType = Playwright.create().chromium();
        browser = browserType.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setSlowMo(400).setArgs(arguments));
        if (browserType == null) throw new IllegalArgumentException("Could not Launch Browser for type" + browserType);
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null).setRecordVideoDir(Paths.get("videos/")));
        //Below line is used to start the trace file
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(false));
        page = context.newPage();
        threadLocalDriver.set(page);
        threadLocalContext.set(context);
        return page;
    }

 

    public static synchronized Page getPage() {
        return threadLocalDriver.get(); // Will return Initialized Thread Local Driver
    }

 

    public static synchronized BrowserContext getContext() {
        return threadLocalContext.get(); // Will return Initialized Thread Local Context
    }

    
    public void closeContext() {  
    	context.tracing().stop(new Tracing.StopOptions()
    			  .setPath(Paths.get("trace.zip")));
            context.close();
    }
 

}