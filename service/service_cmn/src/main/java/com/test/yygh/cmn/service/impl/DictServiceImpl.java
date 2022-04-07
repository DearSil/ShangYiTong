package com.test.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.yygh.cmn.mapper.DictMapper;
import com.test.yygh.cmn.service.DictService;
import com.test.yygh.model.cmn.Dict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DearSil
 * @date 2022/4/7 21:23
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> findChildsData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        wrapper.orderByAsc("id");
        List<Dict> lists = baseMapper.selectList(wrapper);
        lists.forEach(dict -> {
            dict.setHasChildren(hasNext(dict.getId()));
        });
        return lists;
    }

    /**
     * 判断id下面是否有子节点
     */
    private boolean hasNext(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(wrapper);
        return integer > 0;
    }
}
