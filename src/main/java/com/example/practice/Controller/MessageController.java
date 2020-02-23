package com.example.practice.Controller;

import com.example.practice.Model.Message;
import com.example.practice.Model.User;
import com.example.practice.Model.UserHolder;
import com.example.practice.Model.ViewObject;
import com.example.practice.Service.MessageService;
import com.example.practice.Service.SensitiveService;
import com.example.practice.Service.UserService;
import com.example.practice.tools.GetJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    UserHolder userHolder;
    @Autowired
    SensitiveService sensitiveService;
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(path = "/letterDetail")
    String getLetterDetail(@RequestParam(value = "conversationId") String conversationId, Model model) {
        List<ViewObject> viewObjectList = new ArrayList<>();
        List<Message> messageList = messageService.getMessageByConversationId(conversationId);
//        model.addAttribute("messageList", messageList);
        messageService.updateHasRead(userHolder.getUser().getId(),conversationId);
        for (Message message : messageList
        ) {
            ViewObject viewObject = new ViewObject();
            User user = userService.getuserbyid(message.getFromid());
            viewObject.set("user", user);
            viewObject.set("message", message);
            viewObjectList.add(viewObject);
        }

        model.addAttribute("viewObjectList", viewObjectList);
        return "letterDetail";
    }

    @RequestMapping(path = "/msg/addMessage" )
    @ResponseBody
    String sendMessage(@RequestParam(value = "toName") String toName, @RequestParam(value = "content") String content) {
        if (userHolder.getUser() == null) {
            return GetJson.getErroJson(999, "你还没有登录");
        }
        try {
            User toUser = userService.getUserByName(toName);
            if (toUser == null) {
                return GetJson.getErroJson(1, "此用户不存在！");
            }
            Message message = new Message();
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            message.setContent(content);
            message.setCreatedDate(new Date());
            String conversationId = null;
            if (userHolder.getUser().getId() < toUser.getId()) {
                conversationId = "" + userHolder.getUser().getId() + "_" + toUser.getId();
            } else {
                conversationId = "" + toUser.getId() + "_" + userHolder.getUser().getId();
            }
            message.setConversationId(conversationId);
            message.setFromid(userHolder.getUser().getId());
            message.setToid(toUser.getId());
            message.setHasRead(0);
            messageService.sendMessage(message);
        } catch (Exception e) {
            logger.info("发送私信失败！");
            logger.info(e.toString());
            return GetJson.getErroJson(1, "发送私信失败！");
        }
        return GetJson.getJson(0);
    }

    @RequestMapping(path = "/msg/list")
    String getLetterList(Model model) {

        /*  加上用户id查所有message*/
        List<Message> allMessageList = messageService.allMessageList(userHolder.getUser().getId());
        List<ViewObject> viewObjectList=new ArrayList<>();
        for (Message message:allMessageList) {
            User user=userService.getuserbyid(message.getFromid());
            int unread=messageService.getUnread(userHolder.getUser().getId(),message.getConversationId());
            ViewObject viewObject = new ViewObject();
            viewObject.set("user",user);
            viewObject.set("message",message);
            viewObject.set("unread",unread);
            viewObjectList.add(viewObject);
        }

        model.addAttribute("viewObjectList", viewObjectList);

        return "letter";

    }

}
