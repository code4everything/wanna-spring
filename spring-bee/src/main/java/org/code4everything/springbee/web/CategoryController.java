package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.service.CategoryService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/12
 */
@RestController
@RequestMapping("/user/category")
@Api(tags = "分类接口")
public class CategoryController extends BeeBaseController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(UserService userService, CategoryService categoryService) {
        super(userService);
        this.categoryService = categoryService;
    }

    @PostMapping("/append")
    @ApiOperation("添加分类")
    @ApiImplicitParam(name = "name", value = "分类名", required = true)
    public Response<Category> saveCategory(@RequestParam String name) {
        return successResult(categoryService.appendCategory(getUserId(), name), true);
    }

    @GetMapping("/list")
    @ApiOperation("列出我的分类")
    public Response<List<Category>> list() {
        return parseCollection("您还没有任何添加分类信息哦", categoryService.listCategory(getUserId()), true);
    }

    @DeleteMapping("/{categoryId}/remove")
    @ApiOperation("删除分类")
    public Response removeCategory(@PathVariable String categoryId) {
        categoryService.removeCategory(categoryId);
        return successResult("删除成功");
    }

    @PatchMapping("/{categoryId}/update")
    @ApiOperation("更新分类名称")
    @ApiImplicitParam(name = "name", value = "分类名", required = true)
    public Response updateCategory(@PathVariable String categoryId, @RequestParam String name) {
        categoryService.updateCategory(getUserId(), categoryId, name);
        return successResult("更新成功");
    }
}
