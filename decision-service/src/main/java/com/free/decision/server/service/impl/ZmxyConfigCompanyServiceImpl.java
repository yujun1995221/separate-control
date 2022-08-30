package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.ZmxyConfigCompanyMapper;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.ZmxyConfig;
import com.free.decision.server.model.ZmxyConfigCompany;
import com.free.decision.server.service.ZmxyConfigCompanyService;
import com.free.decision.server.service.ZmxyConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 芝麻信用商户决策参数配置实现类
 * @author yxs
 */
@Service
public class ZmxyConfigCompanyServiceImpl implements ZmxyConfigCompanyService {

    private static Logger logger = LoggerFactory.getLogger(ZmxyConfigCompanyServiceImpl.class);

    @Resource
    private ZmxyConfigCompanyMapper zmxyConfigCompanyMapper;

    @Resource
    private ZmxyConfigService zmxyConfigService;

    /**
     * 根据ID查询
     * @param id
     * @return ZmxyConfigCompany
     */
    @Override
    public ZmxyConfigCompany loadById(long id, long companyId) {
        return zmxyConfigCompanyMapper.selectByPrimaryKey(id, companyId);
    }

    /**
     * 根据configId查询
     * @param configId
     * @param companyId
     * @return
     */
    @Override
    public ZmxyConfigCompany loadByConfigId(long configId, long companyId) {
        // 先查询公司配置表中是否存在数据
        ZmxyConfigCompany zmxyconfigCompany = zmxyConfigCompanyMapper.loadByConfigId(configId,companyId);
        // 如果从公司配置表中未查询到数据，则从原始表中查询数据
        if(zmxyconfigCompany == null){
            zmxyconfigCompany = new ZmxyConfigCompany();
            ZmxyConfig zmxyConfig = zmxyConfigService.loadById(configId);
            zmxyconfigCompany.setZmxyScore(zmxyConfig.getZmxyScore());
            zmxyconfigCompany.setAntiFraudScore(zmxyConfig.getAntiFraudScore());
            zmxyconfigCompany.setConfigId(zmxyConfig.getId());
            zmxyconfigCompany.setIsOpen(zmxyConfig.getIsOpen());
            zmxyconfigCompany.setCompanyId(companyId);
        }
        return zmxyconfigCompany;
    }

    /**
     * 新增方法
     * @param zmxyConfigCompany
     * @return Result
     */
    @Override
    public int addZmxyConfigCompany(ZmxyConfigCompany zmxyConfigCompany) {
        return zmxyConfigCompanyMapper.insert(zmxyConfigCompany);
    }

    /**
     * 修改方法
     * @param zmxyScore
     * @param configId
     * @param companyId
     * @return
     */
    @Override
    public Result editZmxyConfigCompany(Integer zmxyScore,long configId,long companyId) {
        // 查询原始数据
        ZmxyConfig zmxyConfig = zmxyConfigService.loadById(configId);
        if(zmxyConfig == null){
            logger.warn("芝麻信用决策配置参数修改失败，configId不存在,configId:{}", configId);
            return new Result(false,"参数不正确");
        }
        ZmxyConfigCompany zmxyConfigCompany = new ZmxyConfigCompany();
        zmxyConfigCompany.setZmxyScore(zmxyScore);
        zmxyConfigCompany.setAntiFraudScore(zmxyConfig.getAntiFraudScore());
        zmxyConfigCompany.setConfigId(configId);
        zmxyConfigCompany.setCompanyId(companyId);
        // 查询公司配置表中是否存在数据
        ZmxyConfigCompany configCompany = zmxyConfigCompanyMapper.loadByConfigId(configId,companyId);
        // 如果不存在，则插入新的数据
        if(configCompany == null){
            zmxyConfigCompany.setIsOpen(zmxyConfig.getIsOpen());
            zmxyConfigCompanyMapper.insert(zmxyConfigCompany);
        }else { // 如果存在，则直接进行更新操作
            zmxyConfigCompanyMapper.updateByPrimaryKey(zmxyConfigCompany);
        }
        return new Result(true,"修改成功");
    }

    /**
     * 修改开启/关闭状态
     * @param isOpen
     * @param configId
     * @param companyId
     * @return
     */
    @Override
    public Result updateOpenType(int isOpen, long configId, long companyId) {
        // 先查询公司配置表中是否存在数据
        ZmxyConfigCompany zmxyConfigCompany = zmxyConfigCompanyMapper.loadByConfigId(configId,companyId);
        // 如果不存在，则将原始配置表数据中的数据获取过来进行插入，同时更改状态
        if(zmxyConfigCompany == null){
            zmxyConfigCompany = new ZmxyConfigCompany();
            ZmxyConfig zmxyConfig = zmxyConfigService.loadById(configId);
            zmxyConfigCompany.setZmxyScore(zmxyConfig.getZmxyScore());
            zmxyConfigCompany.setAntiFraudScore(zmxyConfig.getAntiFraudScore());
            zmxyConfigCompany.setConfigId(configId);
            zmxyConfigCompany.setCompanyId(companyId);
            zmxyConfigCompany.setIsOpen(isOpen);
            zmxyConfigCompanyMapper.insert(zmxyConfigCompany);
        }else { // 如果存在，则直接进行更新状态
            zmxyConfigCompanyMapper.updateOpenType(isOpen, configId, companyId);
        }
        return new Result(true,"操作成功");
    }
}
