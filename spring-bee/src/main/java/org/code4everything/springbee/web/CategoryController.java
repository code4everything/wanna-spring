package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/12
 */
@RestController
@RequestMapping("/user/category")
@Api(value = "/user/category")
public class CategoryController extends BeeBaseController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(RedisTemplate<String, User> userRedisTemplate, CategoryService categoryService) {
        super(userRedisTemplate);
        this.categoryService = categoryService;
    }

    @PostMapping("/append")
    @ApiOperation("添加分类")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "分类名", required = true)})
    public ResponseResult<Category> saveCategory(@RequestParam String name) {
        ResponseResult<Category> result = new ResponseResult<>();
        if (categoryService.exists(getUserId(), name)) {
            return result.error("该分类已存在，无需添加");
        }
        return parseResult("添加分类失败", categoryService.appendCategory(getUserId(), name));
    }

    @GetMapping("/list")
    @ApiOperation("列出我的分类")
    public ResponseResult<ArrayList<Category>> list() {
        return parseResult("您还没有任何添加分类信息", categoryService.listCategory(getUserId()));
    }
}
