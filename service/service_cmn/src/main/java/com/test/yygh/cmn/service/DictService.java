package com.test.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.yygh.model.cmn.Dict;

import java.util.List;

/**
 * @author DearSil
 * @date 2022/4/7 21:22
 */
public interface DictService extends IService<Dict> {
    List<Dict> findChildsData(Long id);
}
