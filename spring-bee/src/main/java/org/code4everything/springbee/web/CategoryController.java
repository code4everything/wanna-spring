package org.code4everything.springbee.web;

import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/12
 */
@RestController
@RequestMapping("/user/category")
@Api(value = "/user/category", description = "分类接口")
public class CategoryController extends BeeBaseController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(HttpServletRequest request, RedisTemplate<String, User> userRedisTemplate,
                              CategoryService categoryService) {
        super(request, userRedisTemplate);
        this.categoryService = categoryService;
    }

    @PostMapping("/append")
    @ApiOperation("添加分类")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "分类名", required = true)})
    public ResultObject<Category> saveCategory(@RequestParam String name) {
        ResultObject<Category> resultObject = new ResultObject<>();
        if (categoryService.exists(getUserId(), name)) {
            return resultObject.copyFrom(CheckResult.getErrorResult("该分类已存在，无需添加"));
        }
        return parseResult(categoryService.appendCategory(getUserId(), name));
    }

    @GetMapping("/list")
    @ApiOperation("列出我的分类")
    public ResultObject<List<Category>> list() {
        return new ResultObject<>();
    }
}
