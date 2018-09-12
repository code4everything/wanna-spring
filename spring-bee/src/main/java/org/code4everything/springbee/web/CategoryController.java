package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.springbee.domain.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/12
 */
@RestController
@RequestMapping("/user/category")
@Api(value = "/user/category", description = "分类接口")
public class CategoryController {

    @PostMapping("/append")
    @ApiOperation("添加分类")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "分类名", required = true)})
    public ResultObject<Category> saveCategory(@RequestParam String name) {
        return new ResultObject<>();
    }

    @GetMapping("/list")
    @ApiOperation("列出我的分类")
    public ResultObject<List<Category>> list() {
        return new ResultObject<>();
    }
}
