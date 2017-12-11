package bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import utils.Consts;

/**
 * @author Andrey Smirnov
 */
public class BotController extends TelegramLongPollingBot
{
    private static final Logger LOGGER = Logger.getLogger(BotController.class);

    public void onUpdateReceived(Update update)
    {
        Message message = update.getMessage();
        if ( message != null && message.hasText() )
        {
            SendMessage answer = BotActions.doSomeAction(message);
            if ( answer != null )
            {
                try
                {
                    sendMessage(answer);
                }
                catch ( TelegramApiException e )
                {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    public String getBotUsername()
    {
        return Consts.BOT_USERNAME;
    }

    public String getBotToken()
    {
        return Consts.BOT_TOKEN;
    }
}



