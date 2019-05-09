package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.model.TodoCountVO;
import org.code4everything.springbee.model.TodoVO;
import org.code4everything.springbee.service.TodoService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/todo")
@Api(tags = "代办事项接口")
public class TodoController extends BeeBaseController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        super(userService);
        this.todoService = todoService;
    }

    @PostMapping("/create")
    @ApiOperation("添加代办事项")
    public Response<Todo> saveTodo(@RequestBody TodoVO todoVO) {
        return successResult(todoService.saveTodo(getUserId(), todoVO), true);
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条代办事项")
    @ApiImplicitParam(name = "todoId", value = "事项编号", required = true)
    public Response<String> remove(@RequestParam String todoId) {
        todoService.remove(todoId);
        return successResult("删除成功");
    }

    @PatchMapping("/update")
    @ApiOperation("更新代办事项内容")
    @ApiImplicitParams({@ApiImplicitParam(name = "todoId", value = "事项编号", required = true), @ApiImplicitParam(name =
            "content", value = "事项内容", required = true)})
    public Response<Todo> updateContent(@RequestParam String todoId, @RequestParam String content) {
        return successResult(todoService.updateTodo(todoId, content), true);
    }

    @PatchMapping("/status/toggle")
    @ApiOperation("更新事项状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "todoId", value = "事项编号", required = true), @ApiImplicitParam(name =
            "status", value = "状态", required = true, allowableValues = "0, 1")})
    public Response<Todo> updateStatus(@RequestParam String todoId, @RequestParam String status) {
        return parseResult("更新失败", todoService.updateTodoStatus(todoId, status), true);
    }

    @GetMapping("/date/list")
    @ApiOperation("列出所有日期")
    public Response<List<String>> listDate() {
        return parseCollection("您还没有待办事项哦", todoService.listDate(getUserId()));
    }

    @GetMapping("/undo/list")
    @ApiOperation("列出某个日期之前未完成的待办事项")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataTypeClass = Date.class)
    public Response<List<Todo>> listUndo(@RequestParam Date date) {
        return parseCollection("没有未完成的待办事项哦", todoService.listUndoBeforeDate(getUserId(), date), true);
    }

    @GetMapping("/list")
    @ApiOperation("列出事项")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataTypeClass = Date.class)
    public Response<List<Todo>> listTodo(@RequestParam Date date) {
        return parseCollection("该日期没有待办事项哦", todoService.listTodo(getUserId(), date), true);
    }

    @GetMapping("/count/list")
    @ApiOperation("列出待办事项统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "开始时间", required = true,
            dataTypeClass = Date.class), @ApiImplicitParam(name = "end", value = "结束时间", required = true,
            dataTypeClass = Date.class)})
    public Response<List<TodoCountVO>> listByDate(@RequestParam Date start, @RequestParam Date end) {
        return parseCollection("您还没有待办事项哦", todoService.listTodoCount(getUserId(), start, end), true);
    }
}
