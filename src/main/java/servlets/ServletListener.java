package servlets;

import bot.BotController;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Andrey Smirnov
 */
public class ServletListener implements ServletContextListener
{
    private static final Logger LOGGER = Logger.getLogger(ServletListener.class);
    private BotSession botSession;

    @Override
    public void contextInitialized(ServletContextEvent servletContext)
    {

        LOGGER.info("Context initialized, let`s do some magic!");
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try
        {
            botSession = telegramBotsApi.registerBot(new BotController());
        }catch (TelegramApiException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContext)
    {
        if (botSession.isRunning())
        {
            botSession.stop();
        }
        LOGGER.info("Context destroyed, all tasks stopped!");
    }
}
