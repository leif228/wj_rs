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

    private List<BsCityDto> getCs(BsProvinceDto bsProvinceFirst) {
        List<BsCityDto> bsCityDtos = new ArrayList<>();

        if (bsProvinceFirst != null) {
            List<BsCity> clist = bsCityMapper.findByP(bsProvinceFirst.getProvinceCode());
            for (BsCity c : clist) {
                BsCityDto bsCityDto = new BsCityDto();
                BeanUtils.copyProperties(c, bsCityDto);
                bsCityDtos.add(bsCityDto);
            }
        }
        return bsCityDtos;
    }

    private List<BsAreaDto> getAs(BsCityDto bsCityFirst) {
        List<BsAreaDto> bsAreaDtos = new ArrayList<>();

        if (bsCityFirst != null) {
            List<BsArea> alist = bsAreaMapper.findByC(bsCityFirst.getCityCode());
            for (BsArea a : alist) {
                BsAreaDto bsAreaDto = new BsAreaDto();
                BeanUtils.copyProperties(a, bsAreaDto);
                bsAreaDtos.add(bsAreaDto);
            }
        }
        return bsAreaDtos;
    }

    private List<BsStreetDto> getSs(BsAreaDto bsAreaFirst) {
        List<BsStreetDto> bsStreetDtos = new ArrayList<>();

        if (bsAreaFirst != null) {
            List<BsStreet> slist = bsStreetMapper.findByA(bsAreaFirst.getAreaCode());
            for (BsStreet s : slist) {
                BsStreetDto bsStreetDto = new BsStreetDto();
                BeanUtils.copyProperties(s, bsStreetDto);
                bsStreetDtos.add(bsStreetDto);
            }
        }
        return bsStreetDtos;
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
        //增加空选项
        BsCityDto bsCityDtoNull = new BsCityDto();
        bsCityDtos.add(bsCityDtoNull);
        if (bsProvinceDtos.size() > 0) {
            bsCityDtos.addAll(this.getCs(bsProvinceDtos.get(0)));
        }

        List<BsAreaDto> bsAreaDtos = new ArrayList<>();
        //增加空选项
        BsAreaDto bsAreaDtoNull = new BsAreaDto();
        bsAreaDtos.add(bsAreaDtoNull);
        if (bsCityDtos.size() > 1) {
            //bsAreaDtos.addAll(this.getAs(bsCityDtos.get(1)));
        }

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        //增加空选项
        BsStreetDto bsStreetDtoNull = new BsStreetDto();
        bsStreetDtos.add(bsStreetDtoNull);
        if (bsAreaDtos.size() > 1) {
            //bsStreetDtos.addAll(this.getSs(bsAreaDtos.get(1)));
        }

        map.put("bsp", bsProvinceDtos);
        map.put("bsc", bsCityDtos);
        map.put("bsa", bsAreaDtos);
        map.put("bss", bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public ApiResult cityByP(Integer id) {
        Map<String, Object> map = new HashMap<>();

        BsProvince bsProvince = bsProvinceMapper.selectByPrimaryKey(id);
        BsProvinceDto bsProvinceDto = null;
        if (bsProvince != null) {
            bsProvinceDto = new BsProvinceDto();
            BeanUtils.copyProperties(bsProvince, bsProvinceDto);
        }

        List<BsCityDto> bsCityDtos = new ArrayList<>();
        //增加空选项
        BsCityDto bsCityDtoNull = new BsCityDto();
        bsCityDtos.add(bsCityDtoNull);
        bsCityDtos.addAll(this.getCs(bsProvinceDto));

        List<BsAreaDto> bsAreaDtos = new ArrayList<>();
        //增加空选项
        BsAreaDto bsAreaDtoNull = new BsAreaDto();
        bsAreaDtos.add(bsAreaDtoNull);
        if (bsCityDtos.size() > 1) {
            //bsAreaDtos.addAll(this.getAs(bsCityDtos.get(1)));
        }

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        //增加空选项
        BsStreetDto bsStreetDtoNull = new BsStreetDto();
        bsStreetDtos.add(bsStreetDtoNull);
        if (bsAreaDtos.size() > 1) {
            //bsStreetDtos.addAll(this.getSs(bsAreaDtos.get(1)));
        }

        map.put("bsc", bsCityDtos);
        map.put("bsa", bsAreaDtos);
        map.put("bss", bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public ApiResult areaByC(Integer id) {
        Map<String, Object> map = new HashMap<>();

        BsCity bsCity = bsCityMapper.selectByPrimaryKey(id);
        BsCityDto bsCityDto = null;
        if (bsCity != null) {
            bsCityDto = new BsCityDto();
            BeanUtils.copyProperties(bsCity, bsCityDto);
        }

        List<BsAreaDto> bsAreaDtos = new ArrayList<>();
        //增加空选项
        BsAreaDto bsAreaDtoNull = new BsAreaDto();
        bsAreaDtos.add(bsAreaDtoNull);
        bsAreaDtos.addAll(this.getAs(bsCityDto));

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        //增加空选项
        BsStreetDto bsStreetDtoNull = new BsStreetDto();
        bsStreetDtos.add(bsStreetDtoNull);
        if (bsAreaDtos.size() > 1) {
            //bsStreetDtos.addAll(this.getSs(bsAreaDtos.get(1)));
        }

        map.put("bsa", bsAreaDtos);
        map.put("bss", bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public ApiResult streetByA(Integer id) {
        Map<String, Object> map = new HashMap<>();

        BsArea bsArea = bsAreaMapper.selectByPrimaryKey(id);
        BsAreaDto bsAreaDto = null;
        if (bsArea != null) {
            bsAreaDto = new BsAreaDto();
            BeanUtils.copyProperties(bsArea, bsAreaDto);
        }

        List<BsStreetDto> bsStreetDtos = new ArrayList<>();
        //增加空选项
        BsStreetDto bsStreetDtoNull = new BsStreetDto();
        bsStreetDtos.add(bsStreetDtoNull);
        bsStreetDtos.addAll(this.getSs(bsAreaDto));

        map.put("bss", bsStreetDtos);

        return ApiResult.success("成功", map);
    }

    @Override
    public AreaChangSeq fzwaddrBySort(Integer sort) {
        AreaChangSeq areaChangSeq = areaChangSeqMapper.selectByPrimaryKey(sort);
        if (areaChangSeq == null)
            return null;
        else
            return areaChangSeq;
    }

    @Override
    public AreaChangSeq sortByFzwaddr(String fzw_str) {
        AreaChangSeq areaChangSeq = areaChangSeqMapper.sortByFzwaddr(fzw_str);
        if (areaChangSeq == null)
            return null;
        else
            return areaChangSeq;
    }
}
