package com.test.yygh.cmn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.yygh.cmn.service.DictService;
import com.test.yygh.common.result.Result;
import com.test.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DearSil
 * @date 2022/4/7 21:26
 */
@RestController
@RequestMapping("/admin/cmn/dict/")
//解决跨域访问
@CrossOrigin
@Api(tags = "数据字典")
public class CmnController {

    @Autowired
    private DictService dictService;

    @GetMapping("/findChildsData/{id}")
    @ApiOperation("根据父节点获取子节点")
    public Result findChildsData(@ApiParam(name = "id",value = "父节点id") @PathVariable("id") Long id){
        List<Dict> childsList = dictService.findChildsData(id);
        return Result.ok(childsList);
    }

}
