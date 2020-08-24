package com.wujie.ac.app.business.app.service.system.impl;

import com.google.gson.Gson;
import com.wujie.ac.app.business.app.service.system.BaseDataService;
import com.wujie.ac.app.business.app.service.system.UserService;
import com.wujie.ac.app.business.entity.*;
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
import com.wujie.common.enums.ErrorEnum;
import com.wujie.common.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
//        String url = "http://" + preNodeStandby.getIp() + ":" + "8888/getFullFzwno";
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

    //http去管理服务器用户注册，成功返回oid关系段
    private String userRegistOwerHttp(String ip, String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort) throws Exception {
        String url = "http://" + ip + ":" + "8888/userRegistOwer";
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
        String url = "http://" + rootIp + ":" + "8888/searchNode";
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
