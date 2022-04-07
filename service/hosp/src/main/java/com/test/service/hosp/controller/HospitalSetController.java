package com.test.service.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import com.test.service.hosp.service.HospitalSetService;
import com.test.yygh.common.exception.YyghException;
import com.test.yygh.common.result.Result;
import com.test.yygh.common.utils.MD5;
import com.test.yygh.model.hosp.HospitalSet;
import com.test.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author DearSil
 * @date 2022/4/6 11:25
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {

    //注入Service
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("获取HospitalSet中的所有设置信息")
    //查询HospitalSet中的所有数据
    @GetMapping("/findAll")
    public Result<List<HospitalSet>> findAll() {
        //调用hospitalSetService的方法
        List<HospitalSet> hospitalSets = hospitalSetService.list();
        return Result.ok(hospitalSets);
    }

    @ApiOperation("通过id删除医院配置信息")
    //根据id删除数据
    @DeleteMapping("removeHospSet/{id}")
    public Result<Boolean> removeHospSet(
            @ApiParam(name = "id", value = "医院设置id", required = true) @PathVariable String id) {
        boolean isok = hospitalSetService.removeById(id);
        if (isok) {
            return Result.ok();
        }
        return Result.fail();
    }

    /*
    条件查询带分页
     */
    @ApiOperation("分页查询")
    @PostMapping("/findPage/{current}/{limit}")
    public Result findPage(
            @ApiParam(name = "current", value = "当前页") @PathVariable long current,
            @ApiParam(name = "limit", value = "每页条数") @PathVariable long limit,
            @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);

        //创建条件构造器
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();

        //判断需要的条件是否为空
        if (!StringUtils.isNullOrEmpty(hospitalSetQueryVo.getHosname())) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }

        if (!StringUtils.isNullOrEmpty(hospitalSetQueryVo.getHoscode())) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }

        //调用方法实现分页查询
        Page<HospitalSet> pageData = hospitalSetService.page(page, wrapper);

        //将查询到的数据直接返回
        return Result.ok(pageData);
    }

    /*
    添加医院设置
     */
    @ApiOperation("添加医院设置")
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@ApiParam(name = "hospitaSet", value = "医院设置") @RequestBody HospitalSet hospitalSet) {
        //在添加数据的时候注意一下是否有需要手动设置的参数信息
        //设置状态:1代表能用,0代表不可用
        hospitalSet.setStatus(1);
        //生成签名密钥：随机数+当前时间戳在进行MD5加密
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        boolean issave = hospitalSetService.save(hospitalSet);
        if (issave) {
            return Result.ok();
        }
        return Result.fail();
    }

    /*
    根据ID获取医院设置
     */
    @ApiOperation("根据id来获取医院的设置信息")
    @GetMapping("/getHospSet/{id}")
    public Result getHospSet(@ApiParam(name = "id", value = "医院配置的id") @PathVariable String id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /*
    修改医院设置
     */
    @ApiOperation("修改医院的设置")
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@ApiParam(name = "hospitalSet") @RequestBody HospitalSet hospitalSet) {
        //先根据hospitalSet中的id来获取HospitalSet的值
        boolean isok = hospitalSetService.updateById(hospitalSet);
        if (isok) {
            return Result.ok();
        }
        return Result.fail();
    }

    /*
    批量删除医院设置
     */
    @ApiOperation("批量删除医院设置")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "id的集合") @RequestBody List<String> idList) {
        boolean isok = hospitalSetService.removeByIds(idList);
        if (isok) {
            return Result.ok();
        }
        return Result.fail();
    }

    /*
    医院设置的锁定和解锁接口
        当医院设置是一个解锁状态时，才可以对医院设置进行更改
     */
    @ApiOperation("医院设置的锁定接口")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@ApiParam(name = "id", value = "需要设置医院的id") @PathVariable Long id,
                                  @ApiParam(name = "status", value = "医院设置的状态") @PathVariable Integer status) {
        //根据Id来查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置hospitalSet的值
        hospitalSet.setStatus(status);
        boolean isOk = hospitalSetService.updateById(hospitalSet);
        if (isOk) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 发送签名密钥
     * 实际上是进行一个短信的发送，现在先写一个简单的
     */
    @ApiOperation("发送签名密钥")
    @PutMapping("/sendKey/{id}")
    public Result sendKey(@ApiParam(name = "id", value = "医院设置的id") @PathVariable Long id) {

        //根据Id来查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置hospitalSet的值
        String hoscode = hospitalSet.getHoscode();
        String signKey = hospitalSet.getSignKey();
        //TODO发送短信
        return Result.ok();
    }
}

