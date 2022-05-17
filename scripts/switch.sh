#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 Port: $IDLE_PORT"
  echo "> Port 전환"

  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

  echo "> 엔진엑스 Reload"
  sudo service nginx reload  #엔진엑스 설정을 다시 불러온다.
}

# "set \$service_url http://127.0.0.1:${IDLE_PORT};" : 하나의 문장을 만들어 파이프라인으로 넘겨주기 위해 echo 사용.
# 엔진엑스가 변경할 프록시 주소를 생성. 쌍따옴표를 사용해야한다. 그렇지 않으면 $service_url을 그대로 인식 못하고 변수를 찾게됨.

# sudo tee /etc/nginx/conf.d/service-url.inc : 앞에서 넘겨준 문장을 service-url.inc에 덮어쓴다.


# nginxt restart 와 reload의 차이: restart와 달리 reload는 끊김없이 다시 불러온다. 하지만 중요설정 파일은 반영 되지 않음.
# 외부 설정 파일을 다시 불러오는 건 reload로도 가능.
