package bot;

import org.telegram.telegrambots.api.objects.User;
import utils.Consts;
import utils.JiraApiUtils;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Andrey Smirnov
 */
public class BotActions
{
    private static final Logger LOGGER = Logger.getLogger(BotActions.class);

    public static SendMessage doSomeAction(Message message)
    {
        String messageText = message.getText();
        switch (messageText)
        {
            case "/start":
                return startAnswer(message, Consts.START_MSG);
            case "/help":
                return startAnswer(message, Consts.START_MSG);
            case "Привет":
                return answer(message, "Привет, " + message.getFrom().getFirstName() + "!");
            case "привет":
                return answer(message, "Привет, " + message.getFrom().getFirstName() + "!");
            case "whoisyourdaddy":
                return answer(message, message.getFrom().getFirstName() + ", моего создателя зовут Андрей Смирнов!");
            case Consts.ACTIVE_CMD:
                return createAnswer(message, JiraApiUtils.getCurrentActive());
            case Consts.CLOSED_CMD:
                return createAnswer(message, JiraApiUtils.getCurrentClosed());
            case Consts.TODAY_CMD:
                return createAnswer(message, JiraApiUtils.todayIssues());
            case Consts.VACATION_CMD:
                return createAnswer(message, JiraApiUtils.getVacation());
            case Consts.INCATIVITY_CMD:
                return createAnswer(message, JiraApiUtils.getInactivity());
            case Consts.LIDER_CMD:
                return createAnswer(message, JiraApiUtils.getLider() + " " + Emoji.SMILING_FACE_WITH_OPEN_MOUTH);
            default:
                if ( Consts.Users.contains(message.getText().toLowerCase()) )
                {
                    User user = message.getFrom();
                    String login = message.getText().toLowerCase();
                    if ( !isRegisteredUser(login, user.getId()) )
                    {
                        registerUser(login, user.getId());
                        return answer(message, Consts.REG_OK_MSG);
                    }
                    else
                    {
                        return answer(message, "Здравствуйте, " + user.getFirstName() + "! Вы уже зарегистрированы");
                    }
                }
                return answer(message, Consts.UNKNOWN_MSG);
        }
    }

    private static SendMessage createAnswer(Message message, String answerMsg)
    {
        if ( isAccessibleToUser(message.getFrom().getId()) )
        {
            return answer(message, answerMsg);
        }
        else
        {
            return errorAnswer(message);
        }
    }

    private static SendMessage startAnswer(Message message, String text)
    {
        SendMessage sMessage = new SendMessage();
        sMessage.setChatId(message.getChatId());
        sMessage.setText(text);
        return sMessage;
    }

    private static SendMessage errorAnswer(Message message)
    {
        SendMessage sMessage = new SendMessage();
        sMessage.setChatId(message.getChatId());
        sMessage.setText(Consts.ERROR_MSG);
        return sMessage;
    }

    private static SendMessage answer(Message message, String text)
    {
        SendMessage sMessage = new SendMessage();
        sMessage.setChatId(message.getChatId());
        sMessage.setReplyMarkup(getKeyboard());
        sMessage.setText(text.replaceAll(Consts.BR, "\r\n"));
        return sMessage;
    }

    private static ReplyKeyboardMarkup getKeyboard()
    {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(Consts.ACTIVE_CMD);
        keyboardFirstRow.add(Consts.CLOSED_CMD);
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(Consts.TODAY_CMD);
        keyboardSecondRow.add(Consts.LIDER_CMD);
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(Consts.VACATION_CMD);
        keyboardThirdRow.add(Consts.INCATIVITY_CMD);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    //Доступ
    private static Boolean isAccessibleToUser(Integer id)
    {
        return isRegisteredUser(id) != null;
    }

    private static void registerUser(String userName, Integer userId)
    {
        File dir = new File(System.getenv("CATALINA_HOME"), "conf");
        dir.mkdirs();
        File propertyFile = new File(dir, "bot_users.properties");
        try
        {
            if ( !propertyFile.exists() )
            {
                propertyFile.createNewFile();
            }
            try (FileWriter out = new FileWriter(propertyFile.getAbsoluteFile(), true))
            {
                //Записываем текст в файл
                out.write(userId + "=" + userName + "\r\n");
            }
        }
        catch ( IOException e )
        {
            LOGGER.error(e);
        }
    }

    private static Boolean isRegisteredUser(String userName, Integer userid)
    {
        String current = isRegisteredUser(userid);
        return current.equals(userName);
    }

    private static String isRegisteredUser(Integer userid)
    {
        Properties usersProp = new Properties();
        String current = "";
        try
        {
            FileInputStream inputStream = new FileInputStream(System.getenv("CATALINA_HOME") + "/" + "conf" + "/" + "bot_users.properties");
            usersProp.load(inputStream);
            current = usersProp.getProperty(userid.toString());
            inputStream.close();
        }
        catch ( IOException e )
        {
            LOGGER.error(e);
        }
        return current;
    }

    private BotActions()
    {
    }
}
