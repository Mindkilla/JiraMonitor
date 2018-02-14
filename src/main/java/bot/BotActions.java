package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import registration.RegistrationUtils;
import utils.Consts;
import utils.JiraApiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Smirnov
 */
public class BotActions {

    public static SendMessage doSomeAction(Message message) {
        String messageText = message.getText();
        switch (messageText) {
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
                User user = message.getFrom();
                if ((message.getText().toLowerCase().contains("/reg "))) {
                    String login = message.getText().toLowerCase().split("\\/reg ")[1];
                    if (!RegistrationUtils.isRegisteredUser(login, user.getId())) {
                        if (RegistrationUtils.sendRegCode(login, user.getId()))
                            return answer(message, Consts.REG_CODE_MSG);
                    } else {
                        return answer(message, "Здравствуйте, " + user.getFirstName() + "! Вы уже зарегистрированы");
                    }
                }
                if ((message.getText().toLowerCase().contains("/code "))) {
                    String code = message.getText().toLowerCase().split("\\/code ")[1];
                    if (RegistrationUtils.checkRegCode(code, user.getId()))
                        return answer(message, Consts.REG_OK_MSG);
                    else {
                        return answer(message, "Что-то пошло не так! Возможно введены не верные данные!");
                    }
                }
                return answer(message, Consts.UNKNOWN_MSG);
        }
    }

    private static SendMessage createAnswer(Message message, String answerMsg) {
        if (isAccessibleToUser(message.getFrom().getId())) {
            return answer(message, answerMsg);
        } else {
            return errorAnswer(message);
        }
    }

    private static SendMessage startAnswer(Message message, String text) {
        SendMessage sMessage = new SendMessage();
        sMessage.setChatId(message.getChatId());
        sMessage.setText(text);
        return sMessage;
    }

    private static SendMessage errorAnswer(Message message) {
        SendMessage sMessage = new SendMessage();
        sMessage.setChatId(message.getChatId());
        sMessage.setText(Consts.ERROR_MSG);
        return sMessage;
    }

    private static SendMessage answer(Message message, String text) {
        SendMessage sMessage = new SendMessage();
        sMessage.setChatId(message.getChatId());
        sMessage.setReplyMarkup(getKeyboard());
        sMessage.setText(text.replaceAll(Consts.BR, "\r\n"));
        return sMessage;
    }

    private static ReplyKeyboardMarkup getKeyboard() {
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
    private static Boolean isAccessibleToUser(Integer id) {
        return RegistrationUtils.isRegisteredUser(id) != null;
    }

    private BotActions() {
    }
}
