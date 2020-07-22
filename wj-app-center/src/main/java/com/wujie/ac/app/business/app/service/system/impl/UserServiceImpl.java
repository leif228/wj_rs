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
import com.wujie.common.dto.wj.NodeDto;
import com.wujie.common.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                    if(mapSize ==1){
                        nodeVo = map.get(firstLayer).get(0);
                        nodeVo.setName(nodeVo.getName() + "(" + nodeVo.getIp() + ":" + nodeVo.getPort() + ")" + nodeVo.getFzwno().substring(3,12));
                        break;
                    }
                    if (j > mapSize)
                        break;
                    List<NodeVo> list1 = map.get(j);
                    for (NodeVo parent : list0) {
                        if (!parent.getName().contains(":"))
                            parent.setName(parent.getName() + "(" + parent.getIp() + ":" + parent.getPort() + ")" + parent.getFzwno().substring(3,12));
                        for (NodeVo child : list1) {
                            if (parent.getRgt() > child.getRgt() && parent.getLft() < child.getLft()) {
                                parent.getChildren().add(child);
                                if (!child.getName().contains(":"))
                                    child.setName(child.getName() + "(" + child.getIp() + ":" + child.getPort() + ")" + child.getFzwno().substring(3,12));
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

        //生成泛在网编号
        String fzwNo = "";

        String country = "chn";
        String trade = "0";//通用（互联网）:0，电力：1，军队：2，政府：3
//        String area = wjuser.getIdcard().substring(0, 5);

        String pf = "!",cf = "!",af = "!",sf = "!";//没有选择时fzw地址信息暂用“!”表示

        AreaChangSeq pfzw = baseDataService.fzwaddrBySort(wjuser.getPSort());
        if(pfzw != null)
            pf = pfzw.getFzwStr();

        AreaChangSeq cfzw = baseDataService.fzwaddrBySort(wjuser.getCSort());
        if(cfzw != null)
            cf = cfzw.getFzwStr();

        AreaChangSeq afzw = baseDataService.fzwaddrBySort(wjuser.getASort());
        if(afzw != null)
            af = afzw.getFzwStr();

        AreaChangSeq sfzw = baseDataService.fzwaddrBySort(wjuser.getSSort());
        if(sfzw != null)
            sf = sfzw.getFzwStr();

        String area = pf + cf + af + sf + "0";


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
                return ApiResult.error(ErrorEnum.ERR_SEQNO_MAX);
        }
        int userType = wjuser.getUserType();//1表示个人2团体、公司等

        fzwNo = country + trade + area + timeStr + seqno + userType;

        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setIp("");
        deviceVo.setPort("");
        deviceVo.setFzwno(fzwNo);

        //增加下级节点
//        if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
            NodeStandby preNodeStandby = nodeStandbyMapper.findByNodeAndType(nodeId, MDA.numEnum.ZERO.ordinal());
            deviceVo.setIp(preNodeStandby.getIp());
            deviceVo.setPort(preNodeStandby.getPort());
//        }

        //当注册设备类型不是（通用服务器）时，要保存fzwno
        if(MDA.numEnum.ZERO.ordinal() != Integer.valueOf(deviceSelected)){
            Device device = new Device();
            device.setUserId(userId);
            device.setDeviceType(Integer.valueOf(deviceSelected));
            device.setCreatTime(DateUtil.getDate());

            device.setFzwno(fzwNo);
            deviceMapper.insertSelective(device);

            log.info("设备注册成功" + fzwNo);
            return ApiResult.success("设备注册成功", deviceVo);
        }else{
            log.info("设备注册第一步成功" + fzwNo);
            return ApiResult.success("设备注册第一步成功", deviceVo);
        }


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

}
