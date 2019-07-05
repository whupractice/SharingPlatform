package com.example.demo.controller;

import com.example.demo.entity.MessageEntity;
import com.example.demo.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：QinYingran
 * @ Description：学生课程消息控制类
 */
@Api(value = "MessageController|学生课程消息控制器")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @ApiOperation(value = "获取所有学生课程消息", notes = "获取所有学生课程消息",httpMethod = "GET")
    @GetMapping("")
    public List<MessageEntity> getAll() {
        return messageService.getAll();
    }

    @ApiOperation(value = "根据学生手机号和课程号获取消息列表", notes = "根据学生手机号和课程号获取消息列表",httpMethod = "GET")
    @GetMapping("/phoneAndLessonId")
    public List<MessageEntity> getByPhoneAndLessonId(@RequestParam @ApiParam(value = "学生手机号") String phone,
                                                     @RequestParam @ApiParam(value = "课程号")String lessonId){
        return messageService.getByPhoneAndLessonId(phone,lessonId);
    }

    @ApiOperation(value = "根据学生手机号获取消息列表", notes = "根据学生手机号获取消息列表",httpMethod = "GET")
    @GetMapping("/phone")
    public List<MessageEntity> getByPhone(@RequestParam @ApiParam(value = "学生手机号") String phone){
        return messageService.getByPhone(phone);
    }

    @ApiOperation(value = "向数据库中插入学生课程消息", notes = "向数据库中插入学生课程消息",httpMethod = "POST")
    @ApiParam(name = "messageEntity",value = "学生课程消息实体,其中任意属性都不能为空")
    @PostMapping("")
    public void insertMessage(@RequestBody MessageEntity messageEntity) {
        messageService.insertMessage(messageEntity);
    }

    @ApiOperation(value = "根据id删除学生课程消息", notes = "根据id删除学生课程消息",httpMethod = "DELETE")
    @ApiParam(name = "id",value = "学生课程消息id")
    @DeleteMapping("")
    public void deleteMessage(@RequestParam long id) {
        messageService.deleteMessage(id);
    }
}
