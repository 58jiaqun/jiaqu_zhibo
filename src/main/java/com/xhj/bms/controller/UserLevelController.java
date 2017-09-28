package com.xhj.bms.controller;

import com.xhj.bms.entity.UserLevel;
import com.xhj.bms.service.user.UserLevelService;
import com.xhj.bms.util.ReplyMsg;
import io.swagger.annotations.*;
import org.apache.http.HttpStatus;
import org.nutz.dao.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Jason on 2017/8/8.
 */
@RestController
@RequestMapping("/UserLevel")
@Api(value="UserLevelController", description="用户等级操作类")
public class UserLevelController {
    @Autowired
    private UserLevelService userLevelService;

    @ApiOperation(value="查询用户等级列表", notes="根据条件查询我客户列表")
    @ApiImplicitParam(name = "param", value = "查询入参", required = true, dataType = "UserLevel")
    @RequestMapping(value = "/searchUserLevel",method = RequestMethod.POST)
    public ReplyMsg searchUserLevel(@RequestBody UserLevel param)  throws Exception{
        try {
            QueryResult result = userLevelService.search(param);
            return new ReplyMsg(HttpStatus.SC_OK,"成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReplyMsg(HttpStatus.SC_INTERNAL_SERVER_ERROR,"失败",e.getStackTrace());
        }
    }

    @ApiOperation(value="查询详细信息", notes="根据id查询详细信息")
    @ApiImplicitParam(name = "Id", value = "查询入参", required = true, dataType = "Integer")
    @RequestMapping(value = "/getUserLevelDetail/{Id}",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功",response = UserLevel.class)
    })
    public ReplyMsg getUserLevelDetail(@PathVariable("Id") Integer Id)  throws Exception{
        try {
            return new ReplyMsg(HttpStatus.SC_OK,"成功", userLevelService.getById(Id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ReplyMsg(HttpStatus.SC_INTERNAL_SERVER_ERROR,"失败",e.getStackTrace());
        }
    }

    @ApiOperation(value="删除信息", notes="根据id删除信息")
    @ApiImplicitParam(name = "Id", value = "查询入参", required = true, dataType = "Integer")
    @RequestMapping(value = "/delete/{Id}",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功")
    })
    public ReplyMsg delete(@PathVariable("Id") Integer Id)  throws Exception{
        try {
            userLevelService.delete(Id);
            return new ReplyMsg(HttpStatus.SC_OK,"成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReplyMsg(HttpStatus.SC_INTERNAL_SERVER_ERROR,"失败",e.getStackTrace());
        }
    }

    @ApiOperation(value="新增用户等级", notes="新增用户等级")
    @ApiImplicitParam(name = "param", value = "用户等级", required = true, dataType = "UserLevel")
    @RequestMapping(value = "/addUserLevel",method = RequestMethod.POST)
    public ReplyMsg addUserLevel(@RequestBody UserLevel param)  throws Exception{
        try {
            userLevelService.save(param);
            return new ReplyMsg(HttpStatus.SC_OK,"成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReplyMsg(HttpStatus.SC_INTERNAL_SERVER_ERROR,"失败",e.getStackTrace());
        }
    }
}
