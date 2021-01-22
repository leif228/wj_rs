 #!/bin/bash
  #description: 启动重启server服务
  #端口号，根据此端口号确定PID

  PORT1=9010
  PORT2=8888
  PORT3=8489
  PORT4=8879
  PORT5=8584
  PORT6=7788
  #启动命令所在目录
  HOME='/home/javajar'
  APP1_NAME=/home/javajar/wj-register-center.jar
  APP2_NAME=/home/javajar/wj-http-center.jar
  APP3_NAME=/home/javajar/wj-dispatch-center.jar
  APP4_NAME=/home/javajar/wj-tcp-center.jar
  APP5_NAME=/home/javajar/wj-app-center.jar
  APP6_NAME=/home/javajar/wj-manage-center.jar
  #查询出监听了PORT端口TCP协议的程序
  pid1=`ps -ef|grep $APP1_NAME|grep -v grep|awk '{print $2}' `
  pid2=`ps -ef|grep $APP2_NAME|grep -v grep|awk '{print $2}' `
  pid3=`ps -ef|grep $APP3_NAME|grep -v grep|awk '{print $2}' `
  pid4=`ps -ef|grep $APP4_NAME|grep -v grep|awk '{print $2}' `
  pid5=`ps -ef|grep $APP5_NAME|grep -v grep|awk '{print $2}' `
  pid6=`ps -ef|grep $APP6_NAME|grep -v grep|awk '{print $2}' `

  start(){
    if [ -n "$pid1" ]; then
       echo "register server already start,pid:$pid1"
       return 0
    fi
    #进入命令所在目录
    cd $HOME
    nohup java -jar $HOME/wj-register-center.jar > $HOME/register.log 2>&1 &
	#启动注册服务器 把日志输出到HOME目录的server.log文件中
    echo "register start at port:$PORT1"
	

    if [ -n "$pid2" ]; then
        echo "http server already start,pid:$pid2"
        return 0
    fi
    #进入命令所在目录
    cd $HOME
    nohup java -jar $HOME/wj-http-center.jar > $HOME/http.log 2>&1 &
    #启动聊天服务器 把日志输出到HOME目录的server.log文件中
    echo "http start at port:$PORT2"
	
	
    if [ -n "$pid3" ]; then
        echo "dispatch server already start,pid:$pid3"
        return 0
    fi
    #进入命令所在目录
    cd $HOME
    nohup java -jar $HOME/wj-dispatch-center.jar > $HOME/dispatch.log 2>&1 &
    #启动聊天服务器 把日志输出到HOME目录的server.log文件中
    echo "dispatch start at port:$PORT3"
	
	
    if [ -n "$pid4" ]; then
        echo "tcp server already start,pid:$pid4"
        return 0
    fi
    #进入命令所在目录
    cd $HOME
    nohup java -jar $HOME/wj-tcp-center.jar > $HOME/tcp.log 2>&1 &
    #启动聊天服务器 把日志输出到HOME目录的server.log文件中
    echo "tcp start at port:$PORT4"
	
	
    if [ -n "$pid5" ]; then
        echo "app server already start,pid:$pid5"
        return 0
    fi
    #进入命令所在目录
    cd $HOME
    nohup java -jar $HOME/wj-app-center.jar > $HOME/app.log 2>&1 &
    #启动聊天服务器 把日志输出到HOME目录的server.log文件中
    echo "app start at port:$PORT5"
	
	
    if [ -n "$pid6" ]; then
        echo "manage server already start,pid:$pid6"
        return 0
    fi
    #进入命令所在目录
    cd $HOME
    nohup java -jar $HOME/wj-manage-center.jar > $HOME/manage.log 2>&1 &
    #启动聊天服务器 把日志输出到HOME目录的server.log文件中
    echo "manage start at port:$PORT6"


 }

 stop(){
   
    #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
    kill -9 $pid1
    
    echo "kill register use signal 2,pid:$pid1"
	

  
    #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
    kill -9 $pid2
    
    echo "kill http use signal 2,pid:$pid2"
	

 
    #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
    kill -9 $pid3
    
    echo "kill dispatch use signal 2,pid:$pid3"
	


    #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
    kill -9 $pid4
   
    echo "kill tcp use signal 2,pid:$pid4"
	

    
    #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
    kill -9 $pid5
    
    echo "kill app use signal 2,pid:$pid5"
	

    
    #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
    kill -9 $pid6
    
    echo "kill manage use signal 2,pid:$pid6"




 }
 status(){
    if [ -z "$pid1" ]; then
       echo "not find program on port:$PORT1"
    else
       echo "program is running,pid:$pid1"
    fi
	

    if [ -z "$pid2" ]; then
            echo "not find program on port:$PORT2"
    else
            echo "program is running,pid:$pid2"
    fi
	

    if [ -z "$pid3" ]; then
            echo "not find program on port:$PORT3"
    else
            echo "program is running,pid:$pid3"
    fi
	

    if [ -z "$pid4" ]; then
            echo "not find program on port:$PORT4"
    else
            echo "program is running,pid:$pid4"
    fi
	

    if [ -z "$pid5" ]; then
            echo "not find program on port:$PORT5"
    else
            echo "program is running,pid:$pid5"
    fi
	

    if [ -z "$pid6" ]; then
            echo "not find program on port:$PORT6"
    else
            echo "program is running,pid:$pid6"
    fi

  }

 case $1 in
    start)
       start
    ;;
    stop)
	stop    
  ;;
    restart)
       $0 stop
       sleep 2
       $0 start
     ;;
    status)
       status
    ;;
    *)
       echo "Usage: {start|stop|restart|status}"
        exit 0
 esac
 fileformat=unix
