package com.example.demo.controller;

import com.example.demo.entity.MessageEntity;
import com.example.demo.service.MessageService;
import com.example.demo.service.SpecUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private MessageService messageService;

    private SpecUtil specUtil;

    @Autowired
    MessageController(MessageService messageService,SpecUtil specUtil) {
        this.messageService = messageService;
        this.specUtil = specUtil;
    }

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

    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "根据学生手机号获取消息列表", notes = "根据学生手机号获取消息列表",httpMethod = "GET")
    @GetMapping("/phone")
    public List<MessageEntity> getByPhone(@RequestParam @ApiParam(value = "学生手机号") String phone){
        return messageService.getByPhone(phone);
    }

    @ApiOperation(value = "分页根据学生手机号和课程号获取消息列表", notes = "分页根据学生手机号和课程号获取消息列表",httpMethod = "GET")
    @GetMapping("/getPagesByPhoneAndLessonId")
    public Page<MessageEntity> getPagesByPhoneAndLessonId(@PageableDefault(size = 12, sort = {"time"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                             @RequestParam(value = "phone")@ApiParam(value = "学生手机号") String phone,
                                             @RequestParam(value = "lessonId")@ApiParam(value = "课程号") String lessonId) {
        Specification<MessageEntity> specification = specUtil.createSpecificationByPhoneAndLessonId(phone,lessonId);
        return messageService.getAll(specification,pageable);
    }

    @ApiOperation(value = "分页根据学生手机号获取消息列表", notes = "分页根据学生手机号获取消息列表",httpMethod = "GET")
    @GetMapping("/getPagesByPhone")
    public Page<MessageEntity> getPagesByPhone(@PageableDefault(size = 12, sort = {"time"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                               @RequestParam(value = "phone")@ApiParam(value = "学生手机号") String phone) {
        Specification<MessageEntity> specification = specUtil.createSpecificationByPhone(phone);
        return messageService.getAll(specification,pageable);
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
