package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/todo")
@Api(value = "/user/todo", description = "代办事项接口")
public class TodoController extends BeeBaseController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService, HttpServletRequest request,
                          RedisTemplate<String, User> userRedisTemplate) {
        super(request, userRedisTemplate);
        this.todoService = todoService;
    }


    @PostMapping("/create")
    @ApiOperation("添加代办事项")
    @ApiImplicitParams({@ApiImplicitParam(name = "doingDate", value = "计划完成日期", required = true, dataTypeClass =
            Date.class), @ApiImplicitParam(name = "content", value = "事项内容", required = true)})
    public ResultObject<Todo> saveTodo(@RequestParam Date doingDate, @RequestParam String content) {
        return parseResult("添加失败", todoService.saveTodo(getUserId(), doingDate, content));
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条代办事项")
    @ApiImplicitParam(name = "todoId", value = "事项编号", required = true)
    public ResultObject<Object> remove(@RequestParam String todoId) {
        return new ResultObject<>();
    }

    @PutMapping("/update")
    @ApiOperation("更新代办事项内容")
    @ApiImplicitParams({@ApiImplicitParam(name = "todoId", value = "事项编号", required = true), @ApiImplicitParam(name =
            "content", value = "事项内容", required = true)})
    public ResultObject<Object> updateContent(@RequestParam String todoId, @RequestParam String content) {
        return new ResultObject<>();
    }

    @PutMapping("/status/toggle")
    @ApiOperation("更新事项状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "todoId", value = "事项编号", required = true)})
    public ResultObject<Object> updateStatus(@RequestParam String todoId, @RequestParam String status) {
        return new ResultObject<>();
    }

    @GetMapping("/date/list")
    @ApiOperation("列出所有日期")
    public ResultObject<List<Date>> listDate() {
        return new ResultObject<>();
    }

    @GetMapping("/list")
    @ApiOperation("列出事项")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataTypeClass = Date.class)
    public ResultObject<List<Todo>> listTodo(@RequestParam Date date) {
        return new ResultObject<>();
    }
}
