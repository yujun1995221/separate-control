package com.free.decision.server.service.impl;

import com.free.decision.server.enums.ConfigOpenTypeEnum;
import com.free.decision.server.enums.ConfigShowTypeEnum;
import com.free.decision.server.mapper.CreditConfigCompanyMapper;
import com.free.decision.server.model.CreditConfig;
import com.free.decision.server.model.CreditConfigCompany;
import com.free.decision.server.model.Result;
import com.free.decision.server.service.CreditConfigCompanyService;
import com.free.decision.server.service.CreditConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 多投反欺诈商户决策参数配置实现类
 * @author yxs
 */
@Service
public class CreditConfigCompanyServiceImpl implements CreditConfigCompanyService {

    private static Logger logger = LoggerFactory.getLogger(CreditConfigCompanyServiceImpl.class);

    @Resource
    private CreditConfigCompanyMapper creditConfigCompanyMapper;

    @Resource
    private CreditConfigService creditConfigService;

    /**
     * 根据id查询
     * @param id
     * @return CreditConfigCompany
     */
    @Override
    public CreditConfigCompany loadById(long id, long companyId) {
        return creditConfigCompanyMapper.selectByPrimaryKey(id,companyId);
    }

    /**
     * 根据configId查询
     * @param configId
     * @return
     */
    @Override
    public CreditConfigCompany loadByConfigId(long configId, long companyId) {
        // 先查询公司配置表中是否存在数据
        CreditConfigCompany creditConfigCompany = creditConfigCompanyMapper.loadByConfigId(configId,companyId);
        CreditConfig creditConfig = creditConfigService.loadById(configId);
        // 如果从公司配置表中未查询到数据，则从原始表中查询数据
        if(creditConfigCompany == null){
            creditConfigCompany = new CreditConfigCompany();
            creditConfigCompany.setConfigId(creditConfig.getId());
            creditConfigCompany.setCompanyId(companyId);
            creditConfigCompany.setIsOpen(creditConfig.getIsOpen());
            creditConfigCompany.setVal(creditConfig.getVal());
        }
        // 拆分val值 返回页面进行显示
        if(StringUtils.isNotBlank(creditConfigCompany.getVal())){
            String [] val = creditConfigCompany.getVal().split(",");
            List<String> list = Arrays.asList(val);
            creditConfigCompany.setVal1(val[0]);
            creditConfigCompany.setVal2(list.size() > 1 ? val[1] : "");
        }
        creditConfigCompany.setCode(creditConfig.getCode());
        creditConfigCompany.setName(creditConfig.getName());
        return creditConfigCompany;
    }

    /**
     * 新增方法
     * @param creditConfigCompany
     * @return Result
     */
    @Override
    public Result addCreditConfigCompany(CreditConfigCompany creditConfigCompany) {
        creditConfigCompanyMapper.insert(creditConfigCompany);
        return new Result(true, "新增成功");
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
        CreditConfigCompany creditConfigCompany = creditConfigCompanyMapper.loadByConfigId(configId,companyId);
        CreditConfig creditConfig = creditConfigService.loadById(configId);
        if(ConfigShowTypeEnum.SHOW.getId()!=creditConfig.getIsShow()){
            logger.warn("多头反欺诈决策配置参数修改失败，configId对应的是内置数据，不可修改,configId:{},creditConfig:{}", configId,creditConfig.getIsShow());
            return new Result(false,"参数不正确");
        }
        // 如果不存在，则将原始配置表数据中的数据获取过来进行插入，同时更改状态
        if(creditConfigCompany == null){
            creditConfigCompany = new CreditConfigCompany();
            creditConfigCompany.setVal(creditConfig.getVal());
            creditConfigCompany.setConfigId(configId);
            creditConfigCompany.setCompanyId(companyId);
            creditConfigCompany.setIsOpen(isOpen);
            creditConfigCompanyMapper.insert(creditConfigCompany);
        }else { // 如果存在，则直接进行更新状态
            creditConfigCompanyMapper.updateOpenType(isOpen, configId, companyId);
        }
        return new Result(true,"操作成功");
    }

    /**
     * 修改方法
     * @param val
     * @param configId
     * @param companyId
     * @return
     */
    @Override
    public Result editCreditConfigCompany(String val, long configId, long companyId) {
        CreditConfigCompany creditConfigCompany = new CreditConfigCompany();
        creditConfigCompany.setVal(val);
        creditConfigCompany.setConfigId(configId);
        creditConfigCompany.setCompanyId(companyId);
        // 查询公司配置表中是否存在数据
        CreditConfigCompany configCompany = creditConfigCompanyMapper.loadByConfigId(configId,companyId);
        // 如果不存在，则插入新的数据
        if(configCompany == null){
            creditConfigCompany.setIsOpen(ConfigOpenTypeEnum.OPEN.getId());
            creditConfigCompanyMapper.insert(creditConfigCompany);
        }else { // 如果存在，则直接进行更新操作
            creditConfigCompanyMapper.updateByPrimaryKey(creditConfigCompany);
        }
        return new Result(true, "修改成功");
    }
}
