package com.cassini.test.framework.helpers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class WebDriverHelper extends EventFiringWebDriver {
    private static final Logger LOG = LoggerFactory
            .getLogger(WebDriverHelper.class);
    private static RemoteWebDriver REAL_DRIVER = null;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.quit();
        }
    };
    private static String BROWSER;
    private static String PLATFORM;
    private static String DRIVER_PATH;
   // private static String DRIVER_ROOT_DIR;
    private static String FILE_SEPARATOR;
    private static String SELENIUM_HOST;
    private static String SELENIUM_PORT;
    private static String SELENIUM_REMOTE_URL;
    private static Dimension BROWSER_WINDOW_SIZE;
    private static Integer BROWSER_WINDOW_WIDTH;
    private static Integer BROWSER_WINDOW_HEIGHT;

    static {
        Props.loadRunConfigProps("/environment.properties");

        SELENIUM_HOST = System.getProperty("driverhost");
        SELENIUM_PORT = System.getProperty("driverport");
        FILE_SEPARATOR = System.getProperty("file.separator");
        PLATFORM = Props.getProp("platform");
        BROWSER = System.getProperty("browser", Props.getProp("browser"));
        BROWSER_WINDOW_WIDTH = Integer.parseInt(Props.getProp("browser.width"));
        BROWSER_WINDOW_HEIGHT = Integer.parseInt(Props.getProp("browser.height"));
        BROWSER_WINDOW_SIZE = new Dimension(BROWSER_WINDOW_WIDTH, BROWSER_WINDOW_HEIGHT);

        //System.setProperty("webdriver.firefox.marionette", getDriverPath());
        System.setProperty("webdriver.gecko.driver", getDriverPath());
        System.setProperty("webdriver.chrome.driver", getDriverPath());
        System.setProperty("webdriver.ie.driver", getDriverPath());
        System.setProperty("phantomjs.binary.path", getDriverPath());


        try {
            switch (BROWSER.toLowerCase()) {
                case ("chrome"):
                    startChromeDriver();
                    break;
                case ("firefox"):
                    startFireFoxDriver();
                    break;
                case ("iexplore"):
                    startIEDriver();
                    break;
                case ("phantomjs"):
                    startPhantomJsDriver();
                    break;
                case ("sauce"):
                    startSauceDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser " + BROWSER + " or Platform "
                            + PLATFORM + " type not supported");

            }

        } catch (IllegalStateException e) {
            LOG.error(" Browser parameter " + BROWSER + " Platform parameter " + PLATFORM
                    + " type not supported");
        }
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    private WebDriverHelper() {
        super(REAL_DRIVER);
    }

    public static WebDriver getWebDriver() {
        return REAL_DRIVER;
    }

    private static String getDriverPath() {
        if (BROWSER.equals("firefox") && PLATFORM.contains("win")) {
            DRIVER_PATH = "tools" + FILE_SEPARATOR + "geckodriver"
                    + FILE_SEPARATOR + PLATFORM + FILE_SEPARATOR
                    + "geckodriver.exe";
        } else if (BROWSER.equals("chrome") && PLATFORM.contains("win")) {
            DRIVER_PATH = "tools" + FILE_SEPARATOR + "chromedriver"
                    + FILE_SEPARATOR + PLATFORM + FILE_SEPARATOR
                    + "chromedriver.exe";
        } else if (BROWSER.equals("chrome") && PLATFORM.contains("linux")) {
            DRIVER_PATH = "tools"+ FILE_SEPARATOR + "chromedriver"
                    + FILE_SEPARATOR + PLATFORM + FILE_SEPARATOR
                    + "chromedriver";

        } else if (BROWSER.equals("iexplore") && PLATFORM.contains("win")) {
            DRIVER_PATH = "tools" + FILE_SEPARATOR + "iedriver"
                    + FILE_SEPARATOR + PLATFORM + FILE_SEPARATOR
                    + "IEDriverServer.exe";
        } else if (BROWSER.equals("phantomjs") && PLATFORM.contains("win")) {
            DRIVER_PATH = "tools" + FILE_SEPARATOR + "phantomjs"
                    + FILE_SEPARATOR + PLATFORM + FILE_SEPARATOR
                    + "phantomjs.exe";
        }

        return DRIVER_PATH;
    }

    private static void startIEDriver() {
        DesiredCapabilities capabilities = getInternetExploreDesiredCapabilities();
        if (SELENIUM_HOST == null)
            REAL_DRIVER = new InternetExplorerDriver(capabilities);
        else {
            try {
                REAL_DRIVER = getRemoteWebDriver(capabilities);
            } catch (MalformedURLException e) {
                LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
            }
        }
        REAL_DRIVER.manage().window().setSize(BROWSER_WINDOW_SIZE);
    }

    private static void startFireFoxDriver() {
        DesiredCapabilities capabilities = getFireFoxDesiredCapabilities();

        if (SELENIUM_HOST == null)
            REAL_DRIVER = new FirefoxDriver();
        else {
            try {
                //capabilities.setPlatform(Platform.WINDOWS);

                REAL_DRIVER = getRemoteWebDriver(capabilities);

            } catch (MalformedURLException e) {
                LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
            }
        }
        REAL_DRIVER.manage().window().setSize(BROWSER_WINDOW_SIZE);
    }

    private static void startPhantomJsDriver() {
        DesiredCapabilities capabilities = getPhantomJsCapabilities();
        if (SELENIUM_HOST == null)
            REAL_DRIVER = new PhantomJSDriver(capabilities);
        else {
            try {
                REAL_DRIVER = getRemoteWebDriver(capabilities);
            } catch (MalformedURLException e) {
                LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
            }
        }
        //REAL_DRIVER.manage().window().setSize(BROWSER_WINDOW_SIZE);
        REAL_DRIVER.manage().window().maximize();
    }

    private static void startSauceDriver() {
        DesiredCapabilities capabilities = getSauceCapabilities();
        try {
            REAL_DRIVER = new RemoteWebDriver(new URL("http://username-string:access-key-string@ondemand.saucelabs.com:80/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            LOG.error(" Error Sauce Url " + e.getMessage());
        }
    }


    private static void startChromeDriver() {
        DesiredCapabilities capabilities = getChromeDesiredCapabilities();

        if (SELENIUM_HOST == null)
          REAL_DRIVER = new ChromeDriver(capabilities);
        else {
            try {
                REAL_DRIVER = getRemoteWebDriver(capabilities);
            } catch (MalformedURLException e) {
                LOG.error(SELENIUM_REMOTE_URL + " Error " + e.getMessage());
            }
        }
        REAL_DRIVER.manage().window().setSize(BROWSER_WINDOW_SIZE);
    }

    private static DesiredCapabilities getChromeDesiredCapabilities() {

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--test-type");
       // chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        //chromeOptions.addArguments("--enable-automation");
        capabilities.setCapability("chrome.verbose", false);

        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return capabilities;
    }

    private static DesiredCapabilities getFireFoxDesiredCapabilities() {

        FirefoxOptions options=new FirefoxOptions();
        //options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("moz:firefoxOptions", options);

        FirefoxProfile profile=new FirefoxProfile();
       capabilities.setCapability(FirefoxDriver.PROFILE, profile);

        capabilities.setCapability("marionette", true);
        capabilities.setCapability("platform", "WINDOWS");
        capabilities.setBrowserName("firefox");
        capabilities.setCapability("acceptInsecureCerts", true);
        return capabilities;
        //capabilities.setCapability("platformName", "WINDOWS");
        //capabilities.setCapability("javascriptEnabled", true);
        //capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        //capabilities.setPlatform(Platform.WINDOWS);
        //capabilities.setCapability("disable-restore-session-state", true);


    }

    private static DesiredCapabilities getInternetExploreDesiredCapabilities() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        DesiredCapabilities capabilities = DesiredCapabilities
                .internetExplorer();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        capabilities
                .setCapability(
                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
        capabilities.setVersion("9");
        return capabilities;
    }

    private static DesiredCapabilities getPhantomJsCapabilities() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        /*DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        capabilities
                .setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, getDriverPath());*/
        DesiredCapabilities capabilities=new DesiredCapabilities();
        String[] cli_args = new String[]{ "--ssl-protocol=any", "--ignore-ssl-errors=true", "--web-security=false" };
        capabilities.setCapability( PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cli_args );
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", true);
        return capabilities;
    }

    private static DesiredCapabilities getSauceCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", BROWSER);
        capabilities.setCapability("platform", PLATFORM);
        capabilities.setCapability("sauce-advisor", false);
        capabilities.setCapability("record-video", false);
        capabilities.setCapability("record-screenshots", false);
        return capabilities;
    }

    private static RemoteWebDriver getRemoteWebDriver(DesiredCapabilities capabilities) throws MalformedURLException {
        SELENIUM_REMOTE_URL = "http://" + SELENIUM_HOST + ":" + SELENIUM_PORT + "/wd/hub";
        LOG.info(SELENIUM_REMOTE_URL + " Checking Selenium Remote URL");
        return new RemoteWebDriver(new URL(SELENIUM_REMOTE_URL), (capabilities));
    }


    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException(
                    "You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }
}
