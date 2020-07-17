package com.wujie.ac.app.business.app.service.system.impl;

import com.wujie.ac.app.business.app.service.system.BaseDataService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.repository.*;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.wj.BsAreaDto;
import com.wujie.common.dto.wj.BsCityDto;
import com.wujie.common.dto.wj.BsProvinceDto;
import com.wujie.common.dto.wj.BsStreetDto;
import com.wujie.common.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BaseDataServiceImpl implements BaseDataService {

    private BsProvinceMapper bsProvinceMapper;
    private BsCityMapper bsCityMapper;
    private BsAreaMapper bsAreaMapper;
    private BsStreetMapper bsStreetMapper;
    private AreaChangSeqMapper areaChangSeqMapper;

    @Autowired
    public BaseDataServiceImpl(BsProvinceMapper bsProvinceMapper, BsCityMapper bsCityMapper, BsAreaMapper bsAreaMapper, BsStreetMapper bsStreetMapper, AreaChangSeqMapper areaChangSeqMapper) {
        this.bsProvinceMapper = bsProvinceMapper;
        this.bsCityMapper = bsCityMapper;
        this.bsAreaMapper = bsAreaMapper;
        this.bsStreetMapper = bsStreetMapper;
        this.areaChangSeqMapper = areaChangSeqMapper;
    }

    @Override
    public ApiResult addrInit() {
        Map<String, Object> map = new HashMap<>();

        List<BsProvinceDto> bsProvinceDtos = new ArrayList<>();
        List<BsProvince> plist = bsProvinceMapper.findAll();
        for (BsProvince p : plist) {
            BsProvinceDto bsProvinceDto = new BsProvinceDto();
            BeanUtils.copyProperties(p, bsProvinceDto);
            bsProvinceDtos.add(bsProvinceDto);
        }

        List<BsCityDto> bsCityDtos = new ArrayList<>();
        List<BsCity> clist = new ArrayList<>();
        if (plist.size() > 0) {
            BsProvince bsProvinceFirst = plist.get(0);
            clist = bsCityMapper.findByP(bsProvinceFirst.getProvinceCode());
            for(BsCity c:clist){
                BsCityDto bsCityDto = new BsCityDto();
                BeanUtils.copyProperties(c,bsCityDto);
                bsCityDtos.add(bsCityDto);
            }
        }

        List<BsAreaDto> bsAreaDtos = new ArrayList<>();
        List<BsArea> alist = new ArrayList<>();
        if(clist.size()>0){
            BsCity bsCityFirst = clist.get(0);
            alist = bsAreaMapper.findByC(bsCityFirst.getCityCode());
            for(BsArea a:alist){
                BsAreaDto bsAreaDto = new BsAreaDto();
                BeanUtils.copyProperties(a,bsAreaDto);
                bsAreaDtos.add(bsAreaDto);
            }
        }

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        List<BsStreet> slist = new ArrayList<>();
        if(alist.size()>0){
            BsArea bsAreaFirst = alist.get(0);
            slist = bsStreetMapper.findByA(bsAreaFirst.getAreaCode());
            for(BsStreet s:slist){
                BsStreetDto bsStreetDto = new BsStreetDto();
                BeanUtils.copyProperties(s,bsStreetDto);
                bsStreetDtos.add(bsStreetDto);
            }
        }

        map.put("bsp",bsProvinceDtos);
        map.put("bsc",bsCityDtos);
        map.put("bsa",bsAreaDtos);
        map.put("bss",bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public ApiResult cityByP(Integer id) {
        Map<String, Object> map = new HashMap<>();

        BsProvince bsProvince = bsProvinceMapper.selectByPrimaryKey(id);

        List<BsCityDto> bsCityDtos = new ArrayList<>();
        List<BsCity> clist = new ArrayList<>();
        if (bsProvince != null) {
            clist = bsCityMapper.findByP(bsProvince.getProvinceCode());
            for(BsCity c:clist){
                BsCityDto bsCityDto = new BsCityDto();
                BeanUtils.copyProperties(c,bsCityDto);
                bsCityDtos.add(bsCityDto);
            }
        }else{
            return ApiResult.error(ErrorEnum.NOT_DATA_ERR);
        }

        List<BsAreaDto> bsAreaDtos = new ArrayList<>();
        List<BsArea> alist = new ArrayList<>();
        if(clist.size()>0){
            BsCity bsCityFirst = clist.get(0);
            alist = bsAreaMapper.findByC(bsCityFirst.getCityCode());
            for(BsArea a:alist){
                BsAreaDto bsAreaDto = new BsAreaDto();
                BeanUtils.copyProperties(a,bsAreaDto);
                bsAreaDtos.add(bsAreaDto);
            }
        }

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        List<BsStreet> slist = new ArrayList<>();
        if(alist.size()>0){
            BsArea bsAreaFirst = alist.get(0);
            slist = bsStreetMapper.findByA(bsAreaFirst.getAreaCode());
            for(BsStreet s:slist){
                BsStreetDto bsStreetDto = new BsStreetDto();
                BeanUtils.copyProperties(s,bsStreetDto);
                bsStreetDtos.add(bsStreetDto);
            }
        }

        map.put("bsc",bsCityDtos);
        map.put("bsa",bsAreaDtos);
        map.put("bss",bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public ApiResult areaByC(Integer id) {
        Map<String, Object> map = new HashMap<>();

        BsCity bsCity = bsCityMapper.selectByPrimaryKey(id);

        List<BsAreaDto> bsAreaDtos = new ArrayList<>();
        List<BsArea> alist = new ArrayList<>();
        if(bsCity != null){
            alist = bsAreaMapper.findByC(bsCity.getCityCode());
            for(BsArea a:alist){
                BsAreaDto bsAreaDto = new BsAreaDto();
                BeanUtils.copyProperties(a,bsAreaDto);
                bsAreaDtos.add(bsAreaDto);
            }
        }else{
            return ApiResult.error(ErrorEnum.NOT_DATA_ERR);
        }

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        List<BsStreet> slist = new ArrayList<>();
        if(alist.size()>0){
            BsArea bsAreaFirst = alist.get(0);
            slist = bsStreetMapper.findByA(bsAreaFirst.getAreaCode());
            for(BsStreet s:slist){
                BsStreetDto bsStreetDto = new BsStreetDto();
                BeanUtils.copyProperties(s,bsStreetDto);
                bsStreetDtos.add(bsStreetDto);
            }
        }

        map.put("bsa",bsAreaDtos);
        map.put("bss",bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public ApiResult streetByA(Integer id) {
        Map<String, Object> map = new HashMap<>();

        BsArea bsArea = bsAreaMapper.selectByPrimaryKey(id);

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        List<BsStreet> slist = new ArrayList<>();
        if(bsArea != null){
            slist = bsStreetMapper.findByA(bsArea.getAreaCode());
            for(BsStreet s:slist){
                BsStreetDto bsStreetDto = new BsStreetDto();
                BeanUtils.copyProperties(s,bsStreetDto);
                bsStreetDtos.add(bsStreetDto);
            }
        }

        map.put("bss",bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public AreaChangSeq fzwaddrBySort(Integer sort) {
        AreaChangSeq areaChangSeq = areaChangSeqMapper.selectByPrimaryKey(sort);
        if(areaChangSeq == null)
            return null;
        else
            return areaChangSeq;
    }
}
