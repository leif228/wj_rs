package com.wujie.ac.app.business.app.service.system.impl;

import com.wujie.ac.app.business.app.service.system.BaseDataService;
import com.wujie.ac.app.business.app.service.system.UserService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.repository.DeviceMapper;
import com.wujie.ac.app.business.repository.NodeMapper;
import com.wujie.ac.app.business.repository.NodeStandbyMapper;
import com.wujie.ac.app.business.repository.WjuserMapper;
import com.wujie.ac.app.business.util.MDA;
import com.wujie.ac.app.business.util.NumConvertUtil;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.NodeVo;
import com.wujie.common.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private DeviceMapper deviceMapper;
    private NodeMapper nodeMapper;
    private NodeStandbyMapper nodeStandbyMapper;
    private WjuserMapper wjuserMapper;
    private BaseDataService baseDataService;
    private static final String COUNTRY = "chn";
    private static final String TRADE = "0";//通用（互联网）:0，电力：1，军队：2，政府：3
    private static final String SPA_STR = "!";//没有选择时fzw地址信息暂用“!”表示   TODO 注意不与area_chang_seq表内容一样

    @Autowired
    public UserServiceImpl(BaseDataService baseDataService, NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
        this.baseDataService = baseDataService;
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
    }

    @Override
    public ApiResult getTreeData(Long nodeId) {
        NodeVo nodeVo = new NodeVo();
        List<NodeVo> list = nodeMapper.getAllChildNodeVosLayer();
        if (list.size() > 0) {
            Map<Integer, List<NodeVo>> map = list.stream().collect(Collectors.groupingBy(NodeVo::getLayer));
            int mapSize = map.size();
            if (mapSize > 0) {
                int firstLayer = 1;
                for (int i = firstLayer; i <= mapSize; i++) {
                    List<NodeVo> list0 = map.get(i);
                    int j = i + 1;
                    if (mapSize == 1) {
                        nodeVo = map.get(firstLayer).get(0);
                        nodeVo.setName(nodeVo.getName() + "(" + nodeVo.getIp() + ":" + nodeVo.getPort() + ")" + nodeVo.getFzwno().substring(3, 12) + (nodeVo.getDeviceType() == 0 ? "管" : "应"));
                        break;
                    }
                    if (j > mapSize)
                        break;
                    List<NodeVo> list1 = map.get(j);
                    for (NodeVo parent : list0) {
                        if (!parent.getName().contains(":"))
                            parent.setName(parent.getName() + "(" + parent.getIp() + ":" + parent.getPort() + ")" + parent.getFzwno().substring(3, 12) + (nodeVo.getDeviceType() == 0 ? "管" : "应"));
                        for (NodeVo child : list1) {
                            if (parent.getRgt() > child.getRgt() && parent.getLft() < child.getLft()) {
                                parent.getChildren().add(child);
                                if (!child.getName().contains(":"))
                                    child.setName(child.getName() + "(" + child.getIp() + ":" + child.getPort() + ")" + child.getFzwno().substring(3, 12) + (nodeVo.getDeviceType() == 0 ? "管" : "应"));
                            }
                        }
                    }
                }
                nodeVo = map.get(firstLayer).get(0);
                log.debug(nodeVo.toString());
            }
        }

        return ApiResult.success(nodeVo);
    }

    @Override
    public ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ApiResult.error(ErrorEnum.NOT_USER_ERR);
        try {
            //一、处理管理服务器注册
            if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
                DeviceVo deviceVo = this.doManageService2(pSort, cSort, aSort, sSort);

                log.info("设备注册第一步成功" + deviceVo.getFzwno());
                return ApiResult.success("设备注册第一步成功", deviceVo);
            }
            //二、处理应用服务器注册
            else if (MDA.numEnum.ONE.ordinal() == Integer.valueOf(deviceSelected)) {
                DeviceVo deviceVo = this.doAppService(wjuser, deviceSelected, pSort, cSort, aSort, sSort);

                log.info("设备注册第一步成功" + deviceVo.getFzwno());
                return ApiResult.success("设备注册第一步成功", deviceVo);
            }
            //三、处理网关、手机注册
            else {
                DeviceVo deviceVo = this.doAppService(wjuser, deviceSelected, pSort, cSort, aSort, sSort);

                log.info("设备注册成功" + deviceVo.getFzwno());
                return ApiResult.success("设备注册成功", deviceVo);
            }
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    private DeviceVo doAppService(Wjuser wjuser, String deviceSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        DeviceVo deviceVo = new DeviceVo();

        String fzwNo = "";
        //查找归属地管理服务器
        NodeStandby preNodeStandby = this.getParentNode2(wjuser.getPSort(), wjuser.getCSort(), wjuser.getASort(), wjuser.getSSort());

        //查看用户是否已经注册，已经注册直接返回fzwno,没有注册则生成fzwno
        Device device = deviceMapper.findByNotAdminUserId(wjuser.getId());

        if (device == null) {
            //生成泛在网编号关系段
            fzwNo = this.doFzwno(2, wjuser.getPSort(), wjuser.getCSort(), wjuser.getASort(), wjuser.getSSort());

            //保存泛在网编号关系段
            device = new Device();
            device.setUserId(wjuser.getId());
            device.setDeviceType(Integer.valueOf(deviceSelected));
            device.setCreatTime(DateUtil.getDate());

            device.setFzwno(fzwNo);
            deviceMapper.insertSelective(device);
        } else {
            fzwNo = device.getFzwno();
        }
        //httpclient去归属地管理服务器生成fzwno设备段
        String fullFzwno = this.getFullFzwno(fzwNo, preNodeStandby);

        //判断用户或公司所在地与设备所在地是否为同一地
        String ustr = "" + wjuser.getPSort() + wjuser.getCSort() + wjuser.getASort() + wjuser.getSSort();
        String dstr = "" + pSort + cSort + aSort + sSort;
        if(!ustr.equals(dstr)){
            //不同则连接设备所在地的管理服务器
            preNodeStandby = this.getParentNode2(pSort, cSort, aSort, sSort);
        }

        deviceVo.setParentNodeId(preNodeStandby.getNodeId());
        deviceVo.setIp(preNodeStandby.getIp());
        deviceVo.setPort(preNodeStandby.getPort());
        deviceVo.setFzwno(fullFzwno);

        return deviceVo;
    }

    //TODO  httpclient去归属地管理服务器生成fzwno设备段
    private String getFullFzwno(String fzwNo, NodeStandby preNodeStandby) {
        return fzwNo + "|sebeiduang";
    }

    /**
     * 1.查找父级节点2.fzwno不跨级生成
     */
    private DeviceVo doManageService2(Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        DeviceVo deviceVo = new DeviceVo();

        //生成泛在网编号
        String fzwNo = this.doFzwno(2, pSort, cSort, aSort, sSort);
        NodeStandby preNodeStandby = null;
        //由下往上查找父,管理服务器只能注册到父级下，不能注册到同级,每个省、市、区、街道只能注册一个
        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
            preNodeStandby = this.getParentNode2(pSort, cSort, aSort, sSort);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                int index = this.manageServiceAddInLayer(preNodeStandby);
                switch (index) {
                    case -1:
                        throw new Exception("当前街道管理服务器已经存在了！不能重复注册！");
                    case 7:
//                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, sSort);
                        break;
                    case 6:
//                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, 0);
                        break;
                    case 5:
//                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
                        break;
                }
            }
        } else if (pSort != 0 && cSort != 0 && aSort != 0 && sSort == 0) {
            preNodeStandby = this.getParentNode2(pSort, cSort, aSort, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                int index = this.manageServiceAddInLayer(preNodeStandby);
                switch (index) {
                    case 7:
                        throw new Exception("当前区管理服务器已经存在了！不能重复注册！");
                    case 6:
//                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, 0);
                        break;
                    case 5:
//                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
                        break;
                }
            }
        } else if (pSort != 0 && cSort != 0 && aSort == 0 && sSort == 0) {
            preNodeStandby = this.getParentNode2(pSort, cSort, 0, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                int index = this.manageServiceAddInLayer(preNodeStandby);
                switch (index) {
                    case 6:
                        throw new Exception("当前市管理服务器已经存在了！不能重复注册！");
                    case 5:
//                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
                        break;
                }
            }
        } else if (pSort != 0 && cSort == 0 && aSort == 0 && sSort == 0) {
            preNodeStandby = this.getParentNode2(pSort, 0, 0, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                throw new Exception("当前省级管理服务器已经存在了！不能重复注册！");
            }
        }

        deviceVo.setParentNodeId(preNodeStandby.getNodeId());
        deviceVo.setIp(preNodeStandby.getIp());
        deviceVo.setPort(preNodeStandby.getPort());
        deviceVo.setFzwno(fzwNo);

        return deviceVo;
    }

    /**
     * 1.查找父级节点2.fzwno可跨级生成
     */
    private DeviceVo doManageService(Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        DeviceVo deviceVo = new DeviceVo();

        //生成泛在网编号
        String fzwNo = "";
        NodeStandby preNodeStandby = null;
        //由下往上查找父,管理服务器只能注册到父级下，不能注册到同级,每个省、市、区、街道只能注册一个
        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
            preNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                int index = this.manageServiceAddInLayer(preNodeStandby);
                switch (index) {
                    case -1:
                        throw new Exception("当前街道管理服务器已经存在了！不能重复注册！");
                    case 7:
                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, sSort);
                        break;
                    case 6:
                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, 0);
                        break;
                    case 5:
                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
                        break;
                }
            }
        } else if (pSort != 0 && cSort != 0 && aSort != 0 && sSort == 0) {
            preNodeStandby = this.getParentNode(pSort, cSort, aSort, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                int index = this.manageServiceAddInLayer(preNodeStandby);
                switch (index) {
                    case 7:
                        throw new Exception("当前区管理服务器已经存在了！不能重复注册！");
                    case 6:
                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, 0);
                        break;
                    case 5:
                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
                        break;
                }
            }
        } else if (pSort != 0 && cSort != 0 && aSort == 0 && sSort == 0) {
            preNodeStandby = this.getParentNode(pSort, cSort, 0, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                int index = this.manageServiceAddInLayer(preNodeStandby);
                switch (index) {
                    case 6:
                        throw new Exception("当前市管理服务器已经存在了！不能重复注册！");
                    case 5:
                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
                        break;
                }
            }
        } else if (pSort != 0 && cSort == 0 && aSort == 0 && sSort == 0) {
            preNodeStandby = this.getParentNode(pSort, 0, 0, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                throw new Exception("当前省级管理服务器已经存在了！不能重复注册！");
            }
        }

        deviceVo.setParentNodeId(preNodeStandby.getNodeId());
        deviceVo.setIp(preNodeStandby.getIp());
        deviceVo.setPort(preNodeStandby.getPort());
        deviceVo.setFzwno(fzwNo);

        return deviceVo;
    }

    private NodeStandby getRoot() {
        return nodeStandbyMapper.selectByPrimaryKey(1l);
    }

    private int manageServiceAddInLayer(NodeStandby preNodeStandby) {
        Device parentDevice = deviceMapper.selectByPrimaryKey(preNodeStandby.getDeviceId());//chn0!!!!02020072100291
        String fzwAreaLimit = parentDevice.getFzwno().substring(0, 8);
        int index = fzwAreaLimit.indexOf(SPA_STR);

        return index;
    }

    private NodeStandby getParentNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        NodeStandby preNodeStandby = null;
        //由下往上查找父
        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, sSort));
            if (preNodeStandby != null)
                return preNodeStandby;

            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, 0));
            if (preNodeStandby != null)
                return preNodeStandby;

            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;

            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;
        } else if (pSort != 0 && cSort != 0 && aSort != 0 && sSort == 0) {
            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, 0));
            if (preNodeStandby != null)
                return preNodeStandby;

            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;

            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;
        } else if (pSort != 0 && cSort != 0 && aSort == 0 && sSort == 0) {
            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;

            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;
        } else if (pSort != 0 && cSort == 0 && aSort == 0 && sSort == 0) {
            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
            if (preNodeStandby != null)
                return preNodeStandby;
        }

        return preNodeStandby;
    }

    private NodeStandby getParentNode2(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        NodeStandby preNodeStandby = null;
        //由下往上查找父
        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, sSort));
        if (preNodeStandby != null)
            return preNodeStandby;

        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, 0));
        if (preNodeStandby != null)
            return preNodeStandby;

        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
        if (preNodeStandby != null)
            return preNodeStandby;

        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
        if (preNodeStandby != null)
            return preNodeStandby;

        return preNodeStandby;
    }

    private String doPreFzwArea(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        String country = COUNTRY;
        String trade = TRADE;//通用（互联网）:0，电力：1，军队：2，政府：3

        String area = this.doFzwArea(pSort, cSort, aSort, sSort);

        String preFzwArea = country + trade + area;

        return preFzwArea;
    }

    private String doFzwArea(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        String pf = SPA_STR, cf = SPA_STR, af = SPA_STR, sf = SPA_STR;//没有选择时fzw地址信息暂用“!”表示

        AreaChangSeq pfzw = baseDataService.fzwaddrBySort(pSort);
        if (pfzw != null)
            pf = pfzw.getFzwStr();

        AreaChangSeq cfzw = baseDataService.fzwaddrBySort(cSort);
        if (cfzw != null)
            cf = cfzw.getFzwStr();

        AreaChangSeq afzw = baseDataService.fzwaddrBySort(aSort);
        if (afzw != null)
            af = afzw.getFzwStr();

        AreaChangSeq sfzw = baseDataService.fzwaddrBySort(sSort);
        if (sfzw != null)
            sf = sfzw.getFzwStr();

        String area = pf + cf + af + sf + "0";

        return area;
    }

    private String doFzwno(int userType, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        String fzwNo = "";

        String country = COUNTRY;
        String trade = TRADE;//通用（互联网）:0，电力：1，军队：2，政府：3
//        String area = wjuser.getIdcard().substring(0, 5);

        String area = this.doFzwArea(pSort, cSort, aSort, sSort);

        String timeStr = DateUtil.getDateTime("yyyyMMdd");
        String seqno = "";//序列号表示该国家该地区当天注册的序号，以16进制字符串的形式表现，如FFFF表示65535号
        Device catMaxFzwno = deviceMapper.findByFzwnoLikeCAT(country + trade + area + timeStr);
        if (null == catMaxFzwno) {
            seqno = NumConvertUtil.IntToHexStringLimit4(1);
        } else {
            String limitPre = catMaxFzwno.getFzwno().substring(0, 21).substring(17);
            int seqInt = NumConvertUtil.HexStringToInt(limitPre);
            seqno = NumConvertUtil.IntToHexStringLimit4(seqInt + 1);//生成当前序号
            if (seqno == null)
                throw new Exception(ErrorEnum.ERR_SEQNO_MAX.getErrMsg());
        }
        //int userType = wjuser.getUserType();//1表示个人2团体、公司等

        fzwNo = country + trade + area + timeStr + seqno + userType;

        return fzwNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult secDeviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId, String fzwno) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ApiResult.error(ErrorEnum.NOT_USER_ERR);

        Device device = new Device();
        device.setUserId(userId);
        device.setDeviceType(Integer.valueOf(deviceSelected));
        device.setDeviceName(deviceName);
        device.setIp(ip);
        device.setPort(port);
        device.setCreatTime(DateUtil.getDate());

        device.setFzwno(fzwno);
        deviceMapper.insertSelective(device);

        //增加下级节点
        if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
            Node preNode = nodeMapper.selectByPrimaryKey(nodeId);
            NodeStandby preNodeStandby = nodeStandbyMapper.findByNodeAndType(nodeId, MDA.numEnum.ZERO.ordinal());

            nodeMapper.updateRgt(preNode.getRgt());
            nodeMapper.updateLft(preNode.getRgt());

            Node currNode = new Node();
            currNode.setName(deviceName);
            currNode.setLft(preNode.getRgt());
            currNode.setRgt(preNode.getRgt() + 1);
            currNode.setCreatTime(DateUtil.getDate());
            nodeMapper.insertSelective(currNode);

            NodeStandby nodeStandby = new NodeStandby();
            nodeStandby.setNodeId(currNode.getId());
            nodeStandby.setDeviceId(device.getId());
            nodeStandby.setIp(ip);
            nodeStandby.setPort(port);
            nodeStandby.setType(MDA.numEnum.ZERO.ordinal());//默认设置为当前节点的主服务器
            nodeStandby.setCreatTime(DateUtil.getDate());
            nodeStandbyMapper.insertSelective(nodeStandby);
        }

        log.info("设备注册成功");

        return ApiResult.success("设备注册成功");
    }


    @Override
    public ApiResult getChildNodes(Long nodeId) {
        Node node = nodeMapper.selectByPrimaryKey(nodeId);
        List<Node> childs = nodeMapper.getChildNodes(node.getLft(), node.getRgt());

        return ApiResult.success(childs);
    }

    @Override
    public ApiResult getFullFzwno(String fzwno, Integer deviceType) {
        String net = "0";
        String stay = "00000000";
        String dtype = "0000";
        String space = "00";
        String seq = "00";

        String full = fzwno + net + stay + dtype + space + seq;

        return ApiResult.success("成功",full);
    }

}
