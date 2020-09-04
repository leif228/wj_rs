package com.wujie.ac.app.business.app.service.system.impl;

import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.BaseDataService;
import com.wujie.ac.app.business.app.service.system.UserService;
import com.wujie.ac.app.business.entity.*;
import com.wujie.ac.app.business.entity.wjhttp.*;
import com.wujie.ac.app.business.repository.*;
import com.wujie.ac.app.business.util.MDA;
import com.wujie.ac.app.business.util.NumConvertUtil;
import com.wujie.ac.app.business.util.date.DateUtil;
import com.wujie.ac.app.framework.util.request.BaseRestfulUtil;
import com.wujie.common.base.ApiResult;
import com.wujie.common.dto.DeviceVo;
import com.wujie.common.dto.NodeVo;
import com.wujie.common.dto.wj.DevtypeDto;
import com.wujie.common.dto.wj.DriverCompDto;
import com.wujie.common.dto.wj.OwerServiceDto;
import com.wujie.common.enums.ErrorEnum;
import com.wujie.common.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private DeviceMapper deviceMapper;
    private NodeMapper nodeMapper;
    private NodeStandbyMapper nodeStandbyMapper;
    private WjuserMapper wjuserMapper;
    private WjuserOwerMapper wjuserOwerMapper;
    private BaseDataService baseDataService;
    private DevtypeMapper devtypeMapper;
    private FzwnoMapper fzwnoMapper;
    private LoginServerMapper loginServerMapper;
    private DriverCompMapper driverCompMapper;
    private NodeInfoOwerMapper nodeInfoOwerMapper;
    private static final String COUNTRY = "chn";
    private static final String TRADE = "0";//通用（互联网）:0，电力：1，军队：2，政府：3
    private static final String SPA_STR = "!";//没有选择时fzw地址信息暂用“!”表示   TODO 注意不与area_chang_seq表内容一样

    @Autowired
    public UserServiceImpl(NodeInfoOwerMapper nodeInfoOwerMapper, WjuserOwerMapper wjuserOwerMapper, DriverCompMapper driverCompMapper, LoginServerMapper loginServerMapper, FzwnoMapper fzwnoMapper, DevtypeMapper devtypeMapper, BaseDataService baseDataService, NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, DeviceMapper deviceMapper) {
        this.nodeInfoOwerMapper = nodeInfoOwerMapper;
        this.wjuserOwerMapper = wjuserOwerMapper;
        this.driverCompMapper = driverCompMapper;
        this.fzwnoMapper = fzwnoMapper;
        this.loginServerMapper = loginServerMapper;
        this.devtypeMapper = devtypeMapper;
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
                        nodeVo.setName(nodeVo.getName() + "(" + nodeVo.getIp() + ":" + nodeVo.getPort() + ")" + nodeVo.getFzwno().substring(3, 12) + (nodeVo.getDeviceType() == 1 ? "管" : "应"));
                        break;
                    }
                    if (j > mapSize)
                        break;
                    List<NodeVo> list1 = map.get(j);
                    for (NodeVo parent : list0) {
                        if (!parent.getName().contains(":"))
                            parent.setName(parent.getName() + "(" + parent.getIp() + ":" + parent.getPort() + ")" + parent.getFzwno().substring(3, 12) + (parent.getDeviceType() == 1 ? "管" : "应"));
                        for (NodeVo child : list1) {
                            if (parent.getRgt() > child.getRgt() && parent.getLft() < child.getLft()) {
                                parent.getChildren().add(child);
                                if (!child.getName().contains(":"))
                                    child.setName(child.getName() + "(" + child.getIp() + ":" + child.getPort() + ")" + child.getFzwno().substring(3, 12) + (child.getDeviceType() == 1 ? "管" : "应"));
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
    public ApiResult seachOwerService(String oid) {
        if (oid == null || "".equals(oid))
            return ApiResult.error("oid参数错误！oid=" + oid);

        if (oid.length() < 9)
            return ApiResult.error("oid参数错误！oid=" + oid);

        OwerServiceDto owerServiceDto = null;

        //root的fzwno为chn0!!!!0000000000000；没有选择时fzw地址信息暂用“!”表示
        String addSorts = oid.substring(4, 8);
        String p_acs = String.valueOf(addSorts.charAt(0));
        String c_acs = String.valueOf(addSorts.charAt(1));
        String a_acs = String.valueOf(addSorts.charAt(2));
        String s_acs = String.valueOf(addSorts.charAt(3));

        //归属服务器为根
        if (p_acs.equals(SPA_STR)) {
            NodeStandby owerNodeStandby = this.getRoot();

            owerServiceDto = new OwerServiceDto();
            owerServiceDto.setIp(owerNodeStandby.getIp());
            owerServiceDto.setPort(owerNodeStandby.getPort());
        } else {
            Integer pSort=0,  cSort=0,  aSort=0,  sSort=0;
            AreaChangSeq pd = baseDataService.sortByFzwaddr(p_acs);
            if(pd != null)
                pSort = pd.getId();
            AreaChangSeq cd = baseDataService.sortByFzwaddr(c_acs);
            if(cd != null)
                cSort = cd.getId();
            AreaChangSeq ad = baseDataService.sortByFzwaddr(a_acs);
            if(ad != null)
                aSort = ad.getId();
            AreaChangSeq sd = baseDataService.sortByFzwaddr(s_acs);
            if(sd != null)
                sSort = sd.getId();

            //查找归属地管理服务器
            NodeStandby owerNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
            //各级都没有找到，直接注册到根
            if (owerNodeStandby == null)
                owerNodeStandby = this.getRoot();

            owerServiceDto = new OwerServiceDto();
            owerServiceDto.setIp(owerNodeStandby.getIp());
            owerServiceDto.setPort(owerNodeStandby.getPort());
        }

        return ApiResult.success(owerServiceDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult deviceRegistManage(Long userId, String deviceSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort, String deviceName, String ip, String port) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ApiResult.error(ErrorEnum.NOT_USER_ERR);

        DeviceVo deviceVo = new DeviceVo();
        try {
            //生成泛在网编号关系段
            String fzwNoRelation = this.doFzwnoRelation(2, pSort, cSort, aSort, sSort);

            //生成泛在网编号设备段
            Devtype devtype = devtypeMapper.selectByPrimaryKey(Integer.valueOf(deviceSelected));
            if (devtype == null)
                return ApiResult.error(ErrorEnum.NOT_DATA_ERR);
            String fzwNoDevice = this.getFzwnoDevice(devtype.getDevTypeNum(), "01");

            String fzwnoFull = fzwNoRelation + fzwNoDevice;

            //查找父级节点
            NodeStandby preNodeStandby = getParentManageNode(pSort, cSort, aSort, sSort);

            deviceVo.setParentNodeId(preNodeStandby.getNodeId());
            deviceVo.setIp(preNodeStandby.getIp());
            deviceVo.setPort(preNodeStandby.getPort());
            deviceVo.setFzwno(fzwnoFull);

            //增加下级节点
            Device device = new Device();
            device.setUserId(userId);
            device.setDeviceName(deviceName);
            device.setIp(ip);
            device.setPort(port);
            device.setFzwno(fzwnoFull);
            device.setCreatTime(DateUtil.getDate());
            device.setDeviceType(Integer.valueOf(deviceSelected));

            deviceMapper.insertSelective(device);

            Node preNode = nodeMapper.selectByPrimaryKey(preNodeStandby.getNodeId());

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
            nodeStandby.setDevtypeId(Integer.valueOf(deviceSelected));
            nodeStandbyMapper.insertSelective(nodeStandby);

        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
        return ApiResult.success("设备注册成功", deviceVo);
    }

    @Override
    public ApiResult recodeOwerNodeInfo(String deviceSelected, String deviceName, String ip, String port, String fzwno) {
        NodeInfoOwer nodeInfoOwer = new NodeInfoOwer();
        nodeInfoOwer.setDeviceType(Integer.valueOf(deviceSelected));
        nodeInfoOwer.setDeviceName(deviceName);
        nodeInfoOwer.setIp(ip);
        nodeInfoOwer.setPort(port);
        nodeInfoOwer.setFzwno(fzwno);
        nodeInfoOwer.setCreatTime(DateUtil.getDate());

        nodeInfoOwerMapper.insertSelective(nodeInfoOwer);

        return ApiResult.success();
    }

    private NodeStandby getParentManageNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        NodeStandby preNodeStandby = null;
        //由下往上查找父,管理服务器只能注册到父级下，不能注册到同级,每个省、市、区、街道只能注册一个
        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
            preNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
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
            preNodeStandby = this.getParentNode(pSort, cSort, aSort, 0);
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
            preNodeStandby = this.getParentNode(pSort, cSort, 0, 0);
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
            preNodeStandby = this.getParentNode(pSort, 0, 0, 0);
            //各级都没有找到，直接注册到根
            if (preNodeStandby == null) {
                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
            } else {//找到就确定父级是那一级，然后在父级的下一级注册
                throw new Exception("当前省级管理服务器已经存在了！不能重复注册！");
            }
        }
        return preNodeStandby;
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResult preDeviceRegist(Long userId, String deviceSelected, Long nodeId, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ApiResult.error(ErrorEnum.NOT_USER_ERR);
        try {
            //一、处理管理服务器注册
            if (MDA.numEnum.ONE.ordinal() == Integer.valueOf(deviceSelected)) {
                DeviceVo deviceVo = this.doManageService2(pSort, cSort, aSort, sSort);

                log.info("设备注册第一步成功" + deviceVo.getFzwno());
                return ApiResult.success("设备注册第一步成功", deviceVo);
            }
            //二、处理应用服务器注册
            else if (MDA.numEnum.TWO.ordinal() == Integer.valueOf(deviceSelected)) {
                DeviceVo deviceVo = this.doAppService(wjuser, deviceSelected, pSort, cSort, aSort, sSort);

                log.info("设备注册第一步成功" + deviceVo.getFzwno());
                return ApiResult.success("设备注册第一步成功", deviceVo);
            }
            //三、处理网关、手机等注册
            else {
                DeviceVo deviceVo = this.doAppService(wjuser, deviceSelected, pSort, cSort, aSort, sSort);

                log.info("设备注册成功" + deviceVo.getFzwno());
                return ApiResult.success("设备注册成功", deviceVo);
            }
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @Override
    public ApiResult searchNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        NodeStandby nodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
        //各级都没有找到，直接注册到根
        if (nodeStandby == null)
            nodeStandby = this.getRoot();

        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setIp(nodeStandby.getIp());
        deviceVo.setPort(nodeStandby.getPort());
        Device loginDevice = deviceMapper.selectByPrimaryKey(nodeStandby.getDeviceId());
        deviceVo.setLoginFzwno(loginDevice.getFzwno());

        return ApiResult.success(deviceVo);
    }

    @Override
    public ApiResult deviceRegistElse(String rootIp, String idCard, String deviceSelected, String deviceName, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        WjuserOwer wjuserOwer = wjuserOwerMapper.findByIdCard(idCard);
        if (wjuserOwer == null)
            return ApiResult.error("注册失败！没有用户信息！");

        DeviceVo deviceVo = new DeviceVo();

        //查归属服务器
        NodeInfoOwer nodeInfoOwer = nodeInfoOwerMapper.selectByPrimaryKey(1l);
        deviceVo.setOwerIp(nodeInfoOwer.getIp());
        deviceVo.setOwerPort(nodeInfoOwer.getPort());
        deviceVo.setOwerFzwno(nodeInfoOwer.getFzwno());

        DeviceVo preDeviceVo = null;
        //查区域服务器
        try {
            preDeviceVo = this.searchNodeHttp(rootIp, pSort, cSort, aSort, sSort);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
//        deviceVo.setParentNodeId(preNodeStandby.getNodeId());
        deviceVo.setIp(preDeviceVo.getIp());
        deviceVo.setPort(preDeviceVo.getPort());
        deviceVo.setLoginFzwno(preDeviceVo.getLoginFzwno());

        ApiResult apiResult = this.genAndSaveFullFzwno(wjuserOwer.getOid(), Integer.valueOf(deviceSelected), deviceName);
        if (!apiResult.get(ApiResult.RETURNCODE).equals(ApiResult.SUCCESS))
            return apiResult;

        deviceVo.setFzwno((String) apiResult.get(ApiResult.CONTENT));

        return ApiResult.success("注册成功！", deviceVo);
    }

    @Transactional(rollbackFor = Exception.class)
    private DeviceVo doAppService(Wjuser wjuser, String deviceSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        DeviceVo deviceVo = new DeviceVo();

        String fzwNo = "";
        //查找归属地管理服务器
        NodeStandby owerNodeStandby = this.getParentNode(wjuser.getPSort(), wjuser.getCSort(), wjuser.getASort(), wjuser.getSSort());
        //各级都没有找到，直接注册到根
        if (owerNodeStandby == null)
            owerNodeStandby = this.getRoot();

        //查看用户是否已经注册，已经注册直接返回fzwno,没有注册则生成fzwno
        Device device = deviceMapper.findByNotAdminUserId(wjuser.getId());

        if (device == null) {
            //生成泛在网编号关系段
            fzwNo = this.doFzwnoRelation(wjuser.getUserType(), wjuser.getPSort(), wjuser.getCSort(), wjuser.getASort(), wjuser.getSSort());

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
//        String fullFzwno = this.getFullFzwno(fzwNo, preNodeStandby, Integer.valueOf(deviceSelected));

        //判断用户或公司所在地与设备所在地是否为同一地
//        String ustr = "" + wjuser.getPSort() + wjuser.getCSort() + wjuser.getASort() + wjuser.getSSort();
//        String dstr = "" + pSort + cSort + aSort + sSort;
//        if (!ustr.equals(dstr)) {
        //不同则连接设备所在地的管理服务器
        NodeStandby preNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
        //各级都没有找到，直接注册到根
        if (preNodeStandby == null)
            preNodeStandby = this.getRoot();
//        }

        deviceVo.setParentNodeId(preNodeStandby.getNodeId());
        deviceVo.setIp(preNodeStandby.getIp());
        deviceVo.setPort(preNodeStandby.getPort());
        Device loginDevice = deviceMapper.selectByPrimaryKey(preNodeStandby.getDeviceId());
        deviceVo.setLoginFzwno(loginDevice.getFzwno());

        deviceVo.setFzwno(fzwNo);

        deviceVo.setOwerIp(owerNodeStandby.getIp());
        deviceVo.setOwerPort(owerNodeStandby.getPort());
        Device owerDevice = deviceMapper.selectByPrimaryKey(owerNodeStandby.getDeviceId());
        deviceVo.setOwerFzwno(owerDevice.getFzwno());

        return deviceVo;
    }

    //  httpclient去归属地管理服务器生成fzwno设备段
//    private String getFullFzwno(String fzwNo, NodeStandby preNodeStandby, Integer deviceType) throws Exception {
//        String url = "http://" + preNodeStandby.getIp() + ":" + "9999/getFullFzwno";
//        String params = "";
//        Map<String, String> map = new HashMap<>();
//        map.put("fzwno", fzwNo);
//        map.put("deviceType", String.valueOf(deviceType));
//        params = new Gson().toJson(map);
//        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
//        if (jsonObject != null) {
//            String code = (String) jsonObject.get("code");
//            if ("0".equals(code)) {
//                String data = (String) jsonObject.get("data");
//                log.info("++++++++++++++++请求fullfzwno成功:" + data);
//                return data;
//            } else {
//                log.info("++++++++++++++++请求fullfzwno失败:" + "服务端错误");
//                throw new Exception("取得fzwno设备段失败：服务端错误");
//            }
//        } else {
//            log.info("++++++++++++++++请求fullfzwno失败:" + "连接错误");
//            throw new Exception("取得fzwno设备段失败:连接错误");
//        }
//    }

    /**
     * 1.查找父级节点2.fzwno不跨级生成
     */
    @Transactional(rollbackFor = Exception.class)
    private DeviceVo doManageService2(Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        DeviceVo deviceVo = new DeviceVo();

        //生成泛在网编号
        String fzwNo = this.doFzwnoRelation(2, pSort, cSort, aSort, sSort);
        NodeStandby preNodeStandby = null;
        //由下往上查找父,管理服务器只能注册到父级下，不能注册到同级,每个省、市、区、街道只能注册一个
        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
            preNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
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
            preNodeStandby = this.getParentNode(pSort, cSort, aSort, 0);
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
            preNodeStandby = this.getParentNode(pSort, cSort, 0, 0);
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
            preNodeStandby = this.getParentNode(pSort, 0, 0, 0);
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
//    private DeviceVo doManageService(Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
//        DeviceVo deviceVo = new DeviceVo();
//
//        //生成泛在网编号
//        String fzwNo = "";
//        NodeStandby preNodeStandby = null;
//        //由下往上查找父,管理服务器只能注册到父级下，不能注册到同级,每个省、市、区、街道只能注册一个
//        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
//            preNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
//            //各级都没有找到，直接注册到根
//            if (preNodeStandby == null) {
//                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
//            } else {//找到就确定父级是那一级，然后在父级的下一级注册
//                int index = this.manageServiceAddInLayer(preNodeStandby);
//                switch (index) {
//                    case -1:
//                        throw new Exception("当前街道管理服务器已经存在了！不能重复注册！");
//                    case 7:
//                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, sSort);
//                        break;
//                    case 6:
//                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, 0);
//                        break;
//                    case 5:
//                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
//                        break;
//                }
//            }
//        } else if (pSort != 0 && cSort != 0 && aSort != 0 && sSort == 0) {
//            preNodeStandby = this.getParentNode(pSort, cSort, aSort, 0);
//            //各级都没有找到，直接注册到根
//            if (preNodeStandby == null) {
//                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
//            } else {//找到就确定父级是那一级，然后在父级的下一级注册
//                int index = this.manageServiceAddInLayer(preNodeStandby);
//                switch (index) {
//                    case 7:
//                        throw new Exception("当前区管理服务器已经存在了！不能重复注册！");
//                    case 6:
//                        fzwNo = this.doFzwno(2, pSort, cSort, aSort, 0);
//                        break;
//                    case 5:
//                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
//                        break;
//                }
//            }
//        } else if (pSort != 0 && cSort != 0 && aSort == 0 && sSort == 0) {
//            preNodeStandby = this.getParentNode(pSort, cSort, 0, 0);
//            //各级都没有找到，直接注册到根
//            if (preNodeStandby == null) {
//                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
//            } else {//找到就确定父级是那一级，然后在父级的下一级注册
//                int index = this.manageServiceAddInLayer(preNodeStandby);
//                switch (index) {
//                    case 6:
//                        throw new Exception("当前市管理服务器已经存在了！不能重复注册！");
//                    case 5:
//                        fzwNo = this.doFzwno(2, pSort, cSort, 0, 0);
//                        break;
//                }
//            }
//        } else if (pSort != 0 && cSort == 0 && aSort == 0 && sSort == 0) {
//            preNodeStandby = this.getParentNode(pSort, 0, 0, 0);
//            //各级都没有找到，直接注册到根
//            if (preNodeStandby == null) {
//                preNodeStandby = this.getRoot();
//                fzwNo = this.doFzwno(2, pSort, 0, 0, 0);
//            } else {//找到就确定父级是那一级，然后在父级的下一级注册
//                throw new Exception("当前省级管理服务器已经存在了！不能重复注册！");
//            }
//        }
//
//        deviceVo.setParentNodeId(preNodeStandby.getNodeId());
//        deviceVo.setIp(preNodeStandby.getIp());
//        deviceVo.setPort(preNodeStandby.getPort());
//        deviceVo.setFzwno(fzwNo);
//
//        return deviceVo;
//    }
    private NodeStandby getRoot() {
        return nodeStandbyMapper.selectByPrimaryKey(1l);
    }

    private int manageServiceAddInLayer(NodeStandby preNodeStandby) {
        Device parentDevice = deviceMapper.selectByPrimaryKey(preNodeStandby.getDeviceId());//chn0!!!!02020072100291
        String fzwAreaLimit = parentDevice.getFzwno().substring(0, 8);
        int index = fzwAreaLimit.indexOf(SPA_STR);

        return index;
    }

//    private NodeStandby getParentNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
//        NodeStandby preNodeStandby = null;
//        //由下往上查找父
//        if (pSort != 0 && cSort != 0 && aSort != 0 && sSort != 0) {
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, sSort));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//        } else if (pSort != 0 && cSort != 0 && aSort != 0 && sSort == 0) {
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//        } else if (pSort != 0 && cSort != 0 && aSort == 0 && sSort == 0) {
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//        } else if (pSort != 0 && cSort == 0 && aSort == 0 && sSort == 0) {
//            preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ZERO.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
//            if (preNodeStandby != null)
//                return preNodeStandby;
//        }
//
//        return preNodeStandby;
//    }

    private NodeStandby getParentNode(Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        NodeStandby preNodeStandby = null;
        //由下往上查找父
        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ONE.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, sSort));
        if (preNodeStandby != null)
            return preNodeStandby;

        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ONE.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, aSort, 0));
        if (preNodeStandby != null)
            return preNodeStandby;

        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ONE.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, cSort, 0, 0));
        if (preNodeStandby != null)
            return preNodeStandby;

        preNodeStandby = nodeStandbyMapper.findParentByDeviceTypeAndNodeStandbyTypeAndFzwArea(MDA.numEnum.ONE.ordinal(), MDA.numEnum.ZERO.ordinal(), this.doPreFzwArea(pSort, 0, 0, 0));
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

    private String doFzwnoRelation(int userType, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
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

    private String doFzwnoOwerRelaion(int userType, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        String fzwNo = "";

        String country = COUNTRY;
        String trade = TRADE;//通用（互联网）:0，电力：1，军队：2，政府：3
//        String area = wjuser.getIdcard().substring(0, 5);

        String area = this.doFzwArea(pSort, cSort, aSort, sSort);

        String timeStr = DateUtil.getDateTime("yyyyMMdd");
        String seqno = "";//序列号表示该国家该地区当天注册的序号，以16进制字符串的形式表现，如FFFF表示65535号
        WjuserOwer catMaxFzwno = wjuserOwerMapper.findByFzwnoLikeCAT(country + trade + area + timeStr);
        if (null == catMaxFzwno) {
            seqno = NumConvertUtil.IntToHexStringLimit4(1);
        } else {
            String limitPre = catMaxFzwno.getOid().substring(0, 21).substring(17);
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

        Device device = null;
        if (MDA.numEnum.ONE.ordinal() == Integer.valueOf(deviceSelected)) {
            device = new Device();

            device.setUserId(userId);
            device.setDeviceName(deviceName);
            device.setIp(ip);
            device.setPort(port);
            device.setFzwno(fzwno);
            device.setCreatTime(DateUtil.getDate());
            device.setDeviceType(Integer.valueOf(deviceSelected));

            deviceMapper.insertSelective(device);
        } else if (MDA.numEnum.TWO.ordinal() == Integer.valueOf(deviceSelected)) {
            //查看用户是否已经注册，已经注册直接返回fzwno,没有注册则生成fzwno
            device = deviceMapper.findByNotAdminUserId(wjuser.getId());
            if (device == null)
                return ApiResult.error(ErrorEnum.NOT_DATA_ERR);

            device.setDeviceName(deviceName);
            device.setIp(ip);
            device.setPort(port);

            deviceMapper.updateByPrimaryKeySelective(device);
        }

        //增加下级节点
        if (MDA.numEnum.ONE.ordinal() == Integer.valueOf(deviceSelected) || MDA.numEnum.TWO.ordinal() == Integer.valueOf(deviceSelected)) {
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
            nodeStandby.setDevtypeId(Integer.valueOf(deviceSelected));
            nodeStandbyMapper.insertSelective(nodeStandby);
        }

        log.info("设备注册成功");

        return ApiResult.success("设备注册成功");
    }


    @Override
    public ApiResult getChildNodes(Long nodeId) {
//        Node node = nodeMapper.selectByPrimaryKey(nodeId);
//        List<Node> childs = nodeMapper.getChildNodes(node.getLft(), node.getRgt());

        List<NodeVo> list = nodeMapper.getChildNodeVos(nodeId);

        return ApiResult.success(list);
    }

    /**
     * 管理服务器上生成fzwno设备端，并保存注册的设备信息，返回fullFzwno.
     */
    @Override
    public ApiResult genAndSaveFullFzwno(String fzwno, Integer deviceType, String deviceName) {
        Devtype devtype = devtypeMapper.selectByPrimaryKey(deviceType);
        if (devtype == null)
            return ApiResult.error(ErrorEnum.NOT_DATA_ERR);

        String deviceno = "";
        //取得最大值
        Fzwno fzwnoMax = fzwnoMapper.findMax(fzwno, devtype.getId());
        if (fzwnoMax != null) {
            String maxDevice = fzwnoMax.getFzwDevice();
            String maxSeq = maxDevice.substring(15);

            int seqInt = NumConvertUtil.HexStringToInt(maxSeq);
            String seqno = NumConvertUtil.IntToHexStringLimit2(seqInt + 1);//生成当前序号
            if (seqno == null)
                return ApiResult.error(ErrorEnum.ERR_SEQNO_DEVICE_MAX);

            fzwnoMax = new Fzwno();

//            String net = "0";//1
//            String stay = "00000000";//8
//            String dtype = devtype.getDevTypeNum();//4
//            String space = "01";//2
//            String seq = seqno;//2
//
//            deviceno = net + stay + dtype + space + seq;
            deviceno = this.getFzwnoDevice(devtype.getDevTypeNum(), seqno);

            fzwnoMax.setCreatTime(DateUtil.getDate());
            fzwnoMax.setDevtypeId(devtype.getId());
            fzwnoMax.setFzwDevice(deviceno);
            fzwnoMax.setFzwRelation(fzwno);
            fzwnoMax.setDeviceName(deviceName);

            fzwnoMapper.insertSelective(fzwnoMax);

        } else {
            fzwnoMax = new Fzwno();

//            String net = "0";//1
//            String stay = "00000000";//8
//            String dtype = devtype.getDevTypeNum();//4
//            String space = "01";//2
//            String seq = "01";//2
//
//            deviceno = net + stay + dtype + space + seq;
            deviceno = this.getFzwnoDevice(devtype.getDevTypeNum(), "01");

            fzwnoMax.setCreatTime(DateUtil.getDate());
            fzwnoMax.setDevtypeId(devtype.getId());
            fzwnoMax.setFzwDevice(deviceno);
            fzwnoMax.setFzwRelation(fzwno);
            fzwnoMax.setDeviceName(deviceName);

            fzwnoMapper.insertSelective(fzwnoMax);
        }

        String full = fzwno + deviceno;

        return ApiResult.success("成功", full);
    }

    private String getFzwnoDevice(String dtype, String seq) {
        String deviceno = "";

        String net = "0";//1
        String stay = "00000000";//8
//        String dtype = devtype.getDevTypeNum();//4
        String space = "01";//2
//        String seq = "01";//2

        deviceno = net + stay + dtype + space + seq;

        return deviceno;
    }

    @Override
    public ApiResult getAllDevType(Long userId) {
        List<DevtypeDto> devtypeDtos = new ArrayList<>();
        List<Devtype> list;

        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser != null && wjuser.getUserName().equals("admin")) {
            list = devtypeMapper.getManageService(1);
        } else {
            list = devtypeMapper.findMElse();
        }

        for (Devtype devtype : list) {
            DevtypeDto devtypeDto = new DevtypeDto();
            BeanUtils.copyProperties(devtype, devtypeDto);

            devtypeDtos.add(devtypeDto);
        }
        return ApiResult.success("成功", devtypeDtos);
    }

    @Override
    public ApiResult owerLoginNotify(String oid, String serverIp, String serverPort, String serverOid, String owerServerOid) {
        LoginServer loginServer = new LoginServer();
        loginServer.setOid(oid);
        loginServer.setServerIp(serverIp);
        loginServer.setServerPort(serverPort);
        loginServer.setServerOid(serverOid);
        loginServer.setOwerServerOid(owerServerOid);
        loginServer.setCreatTime(DateUtil.getDate());

        loginServerMapper.insertSelective(loginServer);
        return ApiResult.success();
    }

    @Override
    public ApiResult deviceComp() {
        List<DriverCompDto> driverCompDtos = new ArrayList<>();
        List<DriverComp> list = driverCompMapper.findAll();
        for (DriverComp driverComp : list) {
            DriverCompDto driverCompDto = new DriverCompDto();
            BeanUtils.copyProperties(driverComp, driverCompDto);
            driverCompDtos.add(driverCompDto);
        }
        return ApiResult.success(driverCompDtos);
    }

    @Override
    public ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        Wjuser wjuser = wjuserMapper.findByUserInfoName(username);
        if (wjuser != null)
            return ApiResult.error(ErrorEnum.PRESENCE_USER_ERR);
        wjuser = wjuserMapper.findByIdCard(idcard);
        if (wjuser != null)
            return ApiResult.error(ErrorEnum.PRESENCE_USER_IDCARD_ERR);

        //查找归属地管理服务器
        NodeStandby owerNodeStandby = this.getParentNode(pSort, cSort, aSort, sSort);
        //各级都没有找到，直接注册到根
        if (owerNodeStandby == null)
            owerNodeStandby = this.getRoot();

        String enPw = MD5.encryptpw(password);

        //同步用户信息到归属地管理服务器
        String oid = "";
        try {
            oid = userRegistOwerHttp(owerNodeStandby.getIp(), username, enPw, idcard, phone, userSelected, pSort, cSort, aSort, sSort);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }

        wjuser = new Wjuser();
        wjuser.setUserName(username);
        wjuser.setPassWord(enPw);
        wjuser.setIdcard(idcard);
        wjuser.setUserType(Integer.valueOf(userSelected));
        wjuser.setPhone(phone);
        wjuser.setCreatTime(DateUtil.getDate());
//        wjuser.setPSort(pSort);
//        wjuser.setCSort(cSort);
//        wjuser.setASort(aSort);
//        wjuser.setSSort(sSort);
        wjuser.setOwerIp(owerNodeStandby.getIp());
        wjuser.setOid(oid);

        wjuserMapper.insertSelective(wjuser);

        return ApiResult.success();
    }

    @Override
    public ApiResult userRegistOwer(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) {
        WjuserOwer wjuserOwer = wjuserOwerMapper.findByUserInfoName(username);
        if (wjuserOwer != null)
            return ApiResult.error(ErrorEnum.PRESENCE_USER_ERR);
        wjuserOwer = wjuserOwerMapper.findByIdCard(idcard);
        if (wjuserOwer != null)
            return ApiResult.error(ErrorEnum.PRESENCE_USER_IDCARD_ERR);

        String fzwno = "";
        try {
            fzwno = this.doFzwnoOwerRelaion(Integer.valueOf(userSelected), pSort, cSort, aSort, sSort);
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }

        wjuserOwer = new WjuserOwer();
        wjuserOwer.setUserName(username);
        wjuserOwer.setPassWord(password);
        wjuserOwer.setIdcard(idcard);
        wjuserOwer.setUserType(Integer.valueOf(userSelected));
        wjuserOwer.setPhone(phone);
        wjuserOwer.setCreatTime(DateUtil.getDate());
        wjuserOwer.setpSort(pSort);
        wjuserOwer.setcSort(cSort);
        wjuserOwer.setaSort(aSort);
        wjuserOwer.setsSort(sSort);
        wjuserOwer.setOid(fzwno);

        wjuserOwerMapper.insertSelective(wjuserOwer);

        return ApiResult.success("成功", fzwno);
    }

    @Override
    public ResponseEntity wjhttp(byte[] data, HttpServletResponse response) {
        log.debug("=====收到wjhttp的长度：" + data.length);
        log.debug("=====收到wjhttp的data：" + Arrays.toString(data));

        ByteBuffer buf = ByteBuffer.wrap(data);
        try {
            WjProtocol wjProtocol = this.decode(buf);
            return this.doTask(wjProtocol, response);

        } catch (Exception e) {
            log.debug("wjhttp===error:" + e.getMessage());
            return this.doError(response, e.getMessage());
        }

    }

    private ResponseEntity doError(HttpServletResponse response, String msg) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//            headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).body(msg);
    }

    private ResponseEntity doTask(WjProtocol wjProtocol, HttpServletResponse response) throws Exception {
        com.alibaba.fastjson.JSONObject objParam = null;
        String tx = null;
        if (WjProtocol.FORMAT_TX.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                String dataStr = new String(wjProtocol.getUserdata());
                tx = dataStr;
            }
        } else if (WjProtocol.FORMAT_JS.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                String jsonStr = new String(wjProtocol.getUserdata());
                objParam = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
            }
        } else if (WjProtocol.FORMAT_AT.equals(wjProtocol.getFormat())) {
            if (wjProtocol.getUserdata() != null) {

                tx = new String(wjProtocol.getUserdata());
            }
        }
        //通用处理
        try {
            String classPath = "com.wujie.ac.app.business.entity.wjhttp";
            String className = "Rec_" + wjProtocol.IntToHexStringLimit2(wjProtocol.getMaincmd()[0]) + wjProtocol.IntToHexStringLimit2(wjProtocol.getMaincmd()[1]) + "_"
                    + wjProtocol.IntToHexStringLimit2(wjProtocol.getSubcmd()[0]) + wjProtocol.IntToHexStringLimit2(wjProtocol.getSubcmd()[1]);
            className = classPath + "." + className;
            log.debug("className:" + className);
            Class genClass = Class.forName(className);
            Rec_task_i rec_task_i = (Rec_task_i) genClass.newInstance();
            rec_task_i.doTask(response, tx, objParam);
            if (rec_task_i instanceof Rec_0100_0100) {
                OwerLogin owerLogin = (OwerLogin) rec_task_i.backUserData();

                LoginServer loginServer = new LoginServer();
                loginServer.setOid(owerLogin.getOID());
                loginServer.setServerIp(owerLogin.getServerIP());
                loginServer.setServerPort(owerLogin.getServerPort());
                loginServer.setServerOid(owerLogin.getServerOID());
                loginServer.setOwerServerOid(owerLogin.getOwnerServerOID());
                loginServer.setCreatTime(DateUtil.getDate());

                loginServerMapper.insertSelective(loginServer);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//            headers.add("Content-Disposition", "attachment; filename=" + file.getName());
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream")).contentLength(rec_task_i.backSendData().length).body(rec_task_i.backSendData());
        } catch (Exception e) {
            log.debug("TaskHandler.doProtocol_报错了:" + e.getMessage());
            throw new Exception("TaskHandler.doProtocol_报错了:" + e.getMessage());
        }
    }

    private WjProtocol decode(ByteBuffer in) throws Exception {

        log.debug("=====收到wjhttp转buf长度：" + in.capacity());
        if (in.capacity() >= WjProtocol.MIN_DATA_LEN) {
            log.debug("开始解码数据……");
            WjProtocol wjProtocol = new WjProtocol();
            //标记读操作的指针
//            in.markReaderIndex();
            byte[] headerbyte = new byte[6];//##6

            in.get(headerbyte);
            log.debug("数据解码的headerbyte===headerbyte[6]：" + Arrays.toString(headerbyte));
            String headerStr = new String(headerbyte);
            wjProtocol.setHeader(headerStr);

            if (WjProtocol.PROTOCOL_HEADER.equals(headerStr)) {
                log.debug("数据开头格式正确");
                //读取字节数据的长度
//                short lenShort = in.readShort();//##2
                byte[] lenShortbyte = new byte[2];//##2
                in.get(lenShortbyte);
                log.debug("数据解码的lenShortbyte===byte[2]：" + Arrays.toString(lenShortbyte));
                wjProtocol.setLen(lenShortbyte);

                int len = wjProtocol.byte2shortSmall(lenShortbyte);
                log.debug("数据解码的长度：" + len);
                int subHeaderLen = len - 8;
                //数据可读长度必须要大于len，因为结尾还有一字节的解释标志位
                int rr = in.remaining();
                if (subHeaderLen < in.remaining()) {
                    log.debug(String.format("数据长度不够，数据协议len长度为：%1$d,数据包实际可读内容为：%2$d正在等待处理拆包……", len, in.remaining()));
                    throw new Exception(String.format("数据长度不够，数据协议len长度为：%1$d,数据包实际可读内容为：%2$d正在等待处理拆包……", len, in.remaining()));
//                    in.rewind();
                    /*
                     **结束解码，这种情况说明数据没有到齐，在父类ByteToMessageDecoder的callDecode中会对out和in进行判断
                     * 如果in里面还有可读内容即in.isReadable为true,cumulation中的内容会进行保留，，直到下一次数据到来，将两帧的数据合并起来，再解码。
                     * 以此解决拆包问题
                     */
//                    return;
                }
                byte verChar = in.get();//##1
                log.debug("数据解码的verChar===byte[1]：" + verChar);
                wjProtocol.setVer(verChar);
                byte encryptChar = in.get();//##1
                log.debug("数据解码的encryptChar===byte[1]：" + encryptChar);
                wjProtocol.setEncrypt(encryptChar);

//                short platShort = in.readShort();//##2
                byte[] platShortbyte = new byte[2];//##2
                in.get(platShortbyte);
                log.debug("数据解码的platShortbyte===byte[2]：" + Arrays.toString(platShortbyte));
                wjProtocol.setPlat(platShortbyte);

//                short maincmdShort = in.readShort();//##2
                byte[] maincmdShortbyte = new byte[2];//##2
                in.get(maincmdShortbyte);
                log.debug("数据解码的maincmdShortbyte===byte[2]：" + Arrays.toString(maincmdShortbyte));
                wjProtocol.setMaincmd(maincmdShortbyte);

//                short subcmdShort = in.readShort();//##2
                byte[] subcmdShortbyte = new byte[2];//##2
                in.get(subcmdShortbyte);
                log.debug("数据解码的subcmdShortbyte===byte[2]：" + Arrays.toString(subcmdShortbyte));
                wjProtocol.setSubcmd(subcmdShortbyte);

                byte[] formatbyte = new byte[2];//##2
                in.get(formatbyte);
                log.debug("数据解码的formatbyte===byte[2]：" + Arrays.toString(formatbyte));
                String format = new String(formatbyte);
                wjProtocol.setFormat(format);

//                short backShort = in.readShort();//##2
                byte[] backShortbyte = new byte[2];//##2
                in.get(backShortbyte);
                log.debug("数据解码的backShortbyte===byte[2]：" + Arrays.toString(backShortbyte));
                wjProtocol.setBack(backShortbyte);

                int dataLen = len - WjProtocol.MIN_DATA_LEN;
                if (dataLen > 0) {
                    byte[] data = new byte[dataLen];
                    in.get(data);//读取核心的数据##n
                    log.debug("数据解码的data===byte[" + dataLen + "]：" + Arrays.toString(data));
                    wjProtocol.setUserdata(data);
                }

                byte checkSumChar = in.get();//##1
                log.debug("数据解码的checkSumChar===byte[1]：" + checkSumChar);
                wjProtocol.setCheckSum(checkSumChar);

                boolean check = wjProtocol.checkXOR(wjProtocol.getCheckSumArray(wjProtocol), checkSumChar);
                if (!check) {
                    log.debug("数据异或校验不对");

                    throw new Exception("数据异或校验不对");
//                    return;
                }

                return wjProtocol;
            } else {
                log.debug("开头不对，可能不是期待的客服端发送的数，将自动略过这一个字节");

                throw new Exception("开头不对，可能不是期待的客服端发送的数，将自动略过这一个字节");
//                return;
            }
        } else {
            log.debug("数据长度不符合要求，期待最小长度是：" + WjProtocol.MIN_DATA_LEN + " 字节");

            throw new Exception("数据长度不符合要求，期待最小长度是：" + WjProtocol.MIN_DATA_LEN + " 字节");
//            return;
        }
    }

    //http去管理服务器用户注册，成功返回oid关系段
    private String userRegistOwerHttp(String ip, String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        String url = "http://" + ip + ":" + "9999/userRegistOwer";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("idcard", idcard);
        map.put("phone", phone);
        map.put("userSelected", userSelected);
        map.put("pSort", String.valueOf(pSort));
        map.put("cSort", String.valueOf(cSort));
        map.put("aSort", String.valueOf(aSort));
        map.put("sSort", String.valueOf(sSort));
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                String data = (String) jsonObject.get(ApiResult.CONTENT);
                log.info("++++++++++++++++请求userRegistOwer成功:" + data);
                return data;
            } else {
                log.info("++++++++++++++++请求userRegistOwer失败:" + "服务端错误");
                throw new Exception("userRegistOwer失败：服务端错误");
            }
        } else {
            log.info("++++++++++++++++请求userRegistOwer失败:" + "连接错误");
            throw new Exception("userRegistOwer失败:连接错误");
        }
    }

    //http去管理服务器用户注册，成功返回oid关系段
    private DeviceVo searchNodeHttp(String rootIp, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        String url = "http://" + rootIp + ":" + "9999/searchNode";
        String params = "";
        Map<String, String> map = new HashMap<>();
        map.put("pSort", String.valueOf(pSort));
        map.put("cSort", String.valueOf(cSort));
        map.put("aSort", String.valueOf(aSort));
        map.put("sSort", String.valueOf(sSort));
        params = new Gson().toJson(map);
        JSONObject jsonObject = BaseRestfulUtil.doPostForJson(url, map);
        if (jsonObject != null) {
            String code = (String) jsonObject.get(ApiResult.RETURNCODE);
            if (ApiResult.SUCCESS.equals(code)) {
                JSONObject data = (JSONObject) jsonObject.get(ApiResult.CONTENT);
                DeviceVo deviceVo = new DeviceVo();
                deviceVo.setLoginFzwno(data.getString("loginFzwno"));
                deviceVo.setIp(data.getString("ip"));
                deviceVo.setPort(data.getString("port"));
                log.info("++++++++++++++++请求searchNodeHttpr成功:" + data.toString());
                return deviceVo;
            } else {
                log.info("++++++++++++++++请求searchNodeHttp失败:" + "服务端错误");
                throw new Exception("searchNodeHttp失败：服务端错误");
            }
        } else {
            log.info("++++++++++++++++请求searchNodeHttp失败:" + "连接错误");
            throw new Exception("searchNodeHttpr失败:连接错误");
        }
    }

}
