#!/bin/sh
#
#
APP_DIR=/var/apps
APP_NAME=instructor-eureka-center
APP_SOURCE=/mnt/hgfs/ShareFiles/instructor-eureka-center.jar

usage(){
	echo "Usage: sh eureka-app.sh [start 1~2|kills [1~2]|status|deploy]"
	exit 1
}

kills(){
	if [[ $1 ]]; then
		tpid=`ps -ef|grep "$APP_NAME.*eureka$1"|grep -v grep|grep -v kill|awk '{print $2}'`
		if [[ tpid ]];then
			echo "tpid - $tpid start kill process!"
			kill -9 $tpid
			echo "kill processed!"
		fi
	else
		tpid=`ps -ef|grep $APP_NAME |grep -v grep|grep -v kill|awk '{print $2}'`
		if [[ tpid ]];then
			for arg in ${tpid[@]}
			do
				echo "tpid - ${arg} start kill process!"
				kill -9 ${arg}
				echo "kill processed!"
			done
		fi
	fi
}

start(){
	if [[ $1 ]]; then
		tpid=`ps -ef|grep "$APP_NAME.*eureka$1"|grep -v grep|grep -v kill|awk '{print $2}'`
		if [[ tpid ]]; then
			echo "Failure the eureka$1 profiles has started!"
			usage
		else
			rm -f $APP_DIR/tpid
			nohup java -jar instructor-eureka-center.jar --spring.profiles.active=eureka$1 > /dev/null 2>&1 &
			echo $! > $APP_DIR/tpid
			echo "eureka $1 Start Success!"
		fi
	else
		
		usage	
	fi
}

deploy(){
	kills
	rm -rf $APP_DIR/"$APP_NAME".jar
	cp $APP_SOURCE $APP_DIR
	echo "$APP_NAME deploy Success! Please start it!"
}

status(){
	tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
	if [[ tpid ]]; then
		echo "tpid-$tpid app is running"
	else
		echo 'app is NOT running'
	fi
}

case "$1" in 
	"start")
		start $2;;
	"deploy")
		deploy;;
	"status")
		status;;
	"kills")
		kills $2;;
	*)
usage ;;
esac
