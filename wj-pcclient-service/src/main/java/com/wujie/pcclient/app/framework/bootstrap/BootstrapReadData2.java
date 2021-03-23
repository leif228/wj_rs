package com.wujie.pcclient.app.framework.bootstrap;

import com.wujie.pcclient.netty.client.TcpClient;
import com.wujie.pcclient.app.business.util.WechatConstant;
import com.wujie.pcclient.app.framework.util.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * ************************************************
 * 初始化数据
 *
 * @author Mr.Chen->MengDaNai->shishi
 * @version 2.2
 * @date 2018年12月4日 创建文件
 * @see ************************************************
 */
@Configuration
@Slf4j
@Order(value = 2)
public class BootstrapReadData2 extends BaseServiceImpl implements CommandLineRunner {

//    @Autowired
//    private SHCommonDaoImpl shCommonDaoImpl;
    @Autowired
    private WechatConstant wechatConstant;

    @Override
    public void run(String... args) throws Exception {
//		Supplier<List<Map<String, Object>>> deal = () -> {
//			StringBuffer sql = new StringBuffer();
//			sql.append(" select * from system_cfg_tbl ");
//			return shCommonDaoImpl.getSqlListCheakNull(sql.toString(), new HashMap<String, Object>());
//		};
//		List<Map<String, Object>> list = super.base(deal);
//
//		Thread thread=new Thread(()-> {
//			try {
//				Class<?> clazz = Class.forName("com.wujie.tc.app.business.util.system.SystemConfig");
//				Object obj = clazz.newInstance();
//				List<Method> ms = Arrays.asList(clazz.getMethods());
//				ms.forEach(c ->
//					list.stream().filter(m -> c.getName().equalsIgnoreCase("set"+m.get("key_name").toString())).forEach(m -> {
//						try {
//							c.invoke(obj, m.get("key_value"));
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					})
//				);
//				log.info("初始化系统参数成功");
//			} catch (Exception e) {
//				log.error("初始化系统参数异常");
//			}
//		});
//		thread.start();

        Thread thread = new Thread(() -> {
            int type = TcpClient.startTcpClient(wechatConstant);
//            if(type == 1)//1:cconfig没有读到ip和port 0:成功
//                return;

//            while (type != 0){
//                type = TcpClient.startTcpClient(wechatConstant);
//                if(type == 1)
//                    break;
//
//                try {
//                    Thread.sleep(60000l);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            };
        });
        thread.start();
    }
}
